package com.config.security.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser
{
	public static WishUserDetails getUserDetails()
	{
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null)
		{
			return null;
		}
		Authentication authentication = context.getAuthentication();
		if (authentication.getPrincipal() == null)
		{
			return null;
		}
		
		if (authentication.getPrincipal() instanceof WishUserDetails)
		{
			return (WishUserDetails) authentication.getPrincipal();
		}
		else
		{
			return null;
		}
	}
}
