package com.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.config.security.user.AuthorityUtils;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {
	
	public final String ADMIN = AuthorityUtils.ROLE_ADMIN;
	
	public final String USER = AuthorityUtils.ROLE_USER;

	public final HttpServletResponse response;

	private static final String CustomWebSecurityExpressionRoot_ = "CustomWebSecurityExpressionRoot_";

	public static CustomWebSecurityExpressionRoot get(Authentication a, FilterInvocation fi) {
		Object obj = fi.getRequest().getAttribute(CustomWebSecurityExpressionRoot_);
		if (obj == null) {
			obj = new CustomWebSecurityExpressionRoot(a, fi);
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(fi.getRequest().getServletContext());
			ctx.getAutowireCapableBeanFactory().autowireBean(obj);
			fi.getRequest().setAttribute(CustomWebSecurityExpressionRoot_, obj);
		}
		return (CustomWebSecurityExpressionRoot) obj;
	}

	public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
		super(a, fi);
		this.response = fi.getHttpResponse();
	}

}
