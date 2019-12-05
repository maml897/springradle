package com.config.mvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.config.security.CustomWebSecurityExpressionHandler;

import freemarker.template.Configuration;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class SharedVariableSecurity
{
	static final FilterChain DEFAULT_CHAIN = new FilterChain()
	{
		public void doFilter(ServletRequest req, ServletResponse res) throws IOException, ServletException
		{
		}
	};

	public SharedVariableSecurity(Configuration config)
	{
		config(config);
	}

	private void config(Configuration config)
	{
		// 执行表达式
		TemplateMethodModelEx access = x -> {
			try
			{
				return access(x.get(0).toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		};
		config.setSharedVariable("access", access);

		config.setSharedVariable("division", new TemplateMethodModelEx()
		{
			@SuppressWarnings("rawtypes")
			@Override
			public Object exec(List arguments) throws TemplateModelException
			{
				if (arguments.size() == 3)
				{
					Object obj1 = arguments.get(0).toString();
					Object obj2 = arguments.get(1).toString();
					Object obj3 = arguments.get(2).toString();

					BigDecimal b1 = new BigDecimal(String.valueOf(obj1));
					BigDecimal b2 = new BigDecimal(String.valueOf(obj2));
					if (b2.doubleValue() == 0)
					{
						return 0;
					}
					return b1.divide(b2, Integer.parseInt(obj3.toString()), BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				Object obj1 = arguments.get(0).toString();
				Object obj2 = arguments.get(1).toString();
				BigDecimal b1 = new BigDecimal(String.valueOf(obj1));
				BigDecimal b2 = new BigDecimal(String.valueOf(obj2));
				if (b2.doubleValue() == 0)
				{
					return 0;
				}
				return b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).doubleValue();
			}
		});
	}

	private Authentication getAuthentication()
	{
		Authentication authentication = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null)
		{
			authentication = context.getAuthentication();
		}
		return authentication;
	}

	private boolean access(String spel) throws IOException
	{
		return (Boolean) executeSecuritySpel(spel);
	}

	private Object executeSecuritySpel(String spel) throws IOException
	{
		try
		{
			SecurityExpressionHandler<FilterInvocation> handler = getExpressionHandler();
			Expression expr = handler.getExpressionParser().parseExpression(spel);

			EvaluationContext evaluationContext = handler.createEvaluationContext(getAuthentication(), new FilterInvocation(getRequest(), getResponse(), DEFAULT_CHAIN));
			return expr.getValue(evaluationContext);
		}
		catch (EvaluationException e)
		{
			throw new IllegalArgumentException("Failed to evaluate expression '" + spel + "'", e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SecurityExpressionHandler<FilterInvocation> getExpressionHandler() throws IOException
	{
		ApplicationContext appContext = SecurityWebApplicationContextUtils.findRequiredWebApplicationContext(getRequest().getServletContext());
		Map<String, SecurityExpressionHandler> handlers = appContext.getBeansOfType(SecurityExpressionHandler.class);

		for (SecurityExpressionHandler seh : handlers.values())
		{
			if (seh instanceof CustomWebSecurityExpressionHandler)
			{
				return seh;
			}
		}

		for (SecurityExpressionHandler h : handlers.values())
		{
			if (FilterInvocation.class.equals(GenericTypeResolver.resolveTypeArgument(h.getClass(), SecurityExpressionHandler.class)))
			{
				return h;
			}
		}

		throw new IOException(
				"No visible WebSecurityExpressionHandler instance could be found in the application " + "context. There must be at least one in order to support expressions in JSP 'authorize' tags.");
	}

	private HttpServletRequest getRequest()
	{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	private HttpServletResponse getResponse()
	{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
}
