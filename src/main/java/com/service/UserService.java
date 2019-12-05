package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.security.user.WishUserDetails;
import com.view.Authority;
import com.view.User;
@Service
public class UserService implements UserDetailsService
{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = new User();
		if (user.getId() == 0)
		{
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		if (user.getAuthoritys().size() == 0)
		{
			throw new UsernameNotFoundException("User " + username + " has no GrantedAuthority");
		}

		List<GrantedAuthority> grantedAuthoritys = buildGrantedAuthority(user);
		List<GrantedAuthority> authorities = new ArrayList<>();
		grantedAuthoritys.stream().forEach(authority -> {
			if (!authorities.contains(authority))
			{
				authorities.add(authority);
			}
		});

		WishUserDetails userDetails = new WishUserDetails(user.getId(), user.getUserName(), user.getPassword(), user.isEnabled(), authorities);
		userDetails.setUserID(user.getId());
		userDetails.setRealName(user.getRealName());
		return userDetails;
	}

	// 构建用户权限
	private List<GrantedAuthority> buildGrantedAuthority(User user) throws RuntimeException
	{
		List<GrantedAuthority> listGrantedAuthorities = new ArrayList<GrantedAuthority>();
		for (Authority authority : user.getAuthoritys())
		{
			listGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
		return listGrantedAuthorities;
	}
	
	@Transactional(readOnly = false)
	public void addUser(User user) throws UsernameNotFoundException
	{
	}
	
	public User getUser(long id) throws UsernameNotFoundException
	{
		return null;
	}
	
	public User getUser(String phoneOrMail) throws UsernameNotFoundException
	{
		return null;
	}

}
