package com.config.security.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class WishUserDetails implements UserDetails
{

	private static final long serialVersionUID = 4005078773885150904L;
	
	private long userID;

	private String username = null;

	private String password = null;

	private boolean isEnabled = false;
	
	private String realName="";

	private Collection<? extends GrantedAuthority> authorities = null;

	public WishUserDetails(long userID,String username, String password, boolean isEnabled, Collection<? extends GrantedAuthority> authorities)
	{
		this.username = username;
		this.password = password;
		this.isEnabled = isEnabled;
		this.authorities = authorities;
		this.userID = userID;
	}

	
	public String getRealName()
	{
		return realName;
	}


	public void setRealName(String realName)
	{
		this.realName = realName;
	}


	public long getUserID()
	{
		return userID;
	}


	public void setUserID(long userID)
	{
		this.userID = userID;
	}


	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public String getUsername()
	{
		return this.username;
	}

	@Override
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public boolean isEnabled()
	{
		return this.isEnabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities)
	{
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.getUsername()).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.isEnabled()).append("; ");
		sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
		sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
		sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");

		Collection<? extends GrantedAuthority> authorities = this.getAuthorities();
		if (!authorities.isEmpty())
		{
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (GrantedAuthority auth : authorities)
			{
				if (!first)
				{
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		}
		else
		{
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}
}
