package com.config.security;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.service.UserService;

@Configuration
public class HttpSecurityConfigDefault extends WebSecurityConfigurerAdapter
{
	@Resource
	private UserService useService;

	@Bean
	public SecurityExpressionHandler<FilterInvocation> expressionHandler()
	{
		SecurityExpressionHandler<FilterInvocation> expressionHandler = new CustomWebSecurityExpressionHandler();
		return expressionHandler;
	}

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/**").antMatchers("/");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{

		// http.exceptionHandling().accessDeniedPage("/error/403");
		// 关闭csrf 防止循环定向
		http.csrf().disable();
		http.authorizeRequests().expressionHandler(expressionHandler());
		// http.cors().disable();
		http.headers().frameOptions().disable();
		http.sessionManagement().invalidSessionUrl("/").maximumSessions(10).expiredUrl("/error/sessionexpired");

		// 通过formLogin()定义当需要用户登录时候，转到的登录页面。
		http.formLogin().loginPage("/").loginProcessingUrl("/SystemLogin").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/login/login-success", true).failureUrl("/");

		// 注销
		http.logout().logoutUrl("/SystemLogout").logoutSuccessHandler(logoutSuccessHandler());
		configAuthorizeRequests(http);
	}

	private void configAuthorizeRequests(HttpSecurity http) throws Exception
	{
		// 认证的顺序是从前到后，如果前边的路径匹配了，后边的就不匹配了。
		// 以下代码指定了/和/index不需要任何认证就可以访问，其他的路径都必须通过身份验证。
		http.authorizeRequests().antMatchers("/").permitAll(); // , "/index.shtml","/speed-test.shtml"

		// 添加业务需要的验证路径，判断顺序是从上到下，请注意顺序
		configAuthorizeCommon(http);

		// 默认的全部需要验证，不验证的在前边进行配置
		http.authorizeRequests().antMatchers("/r/**").access("isAnonymous() or isFullyAuthenticated()");
		http.authorizeRequests().anyRequest().fullyAuthenticated();
	}

	// common
	private void configAuthorizeCommon(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().antMatchers("/system/**").hasAnyAuthority("ROLE_ADMIN").antMatchers("/system/**").access("hasAnyAuthority(ADMIN)").antMatchers("/demo/**")
				.access("isAnonymous() or isFullyAuthenticated()").antMatchers("/manage/**").access("hasAnyAuthority(ADMIN,MANAGE)").antMatchers("/**").access("hasAnyAuthority(USER)");
	}

	// -----------Bean--------------------------

	@Bean
	public SimpleUrlLogoutSuccessHandler logoutSuccessHandler()
	{
		SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
		logoutSuccessHandler.setDefaultTargetUrl("/");
		logoutSuccessHandler.setTargetUrlParameter("logoutSuccessUrl");

		return logoutSuccessHandler;
	}
}
