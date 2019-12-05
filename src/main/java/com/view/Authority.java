package com.view;

import org.springframework.security.core.GrantedAuthority;

//@Entity
//@Table(name = "t_authority")
public class Authority implements GrantedAuthority
{

	private static final long serialVersionUID = -5169504841840786233L;

//	@Id
//	@Column(name = "Authority", length = 50, nullable = false)
	private String authority = "";

//	@Column(name = "CName", length = 50, nullable = false)
	private String name = "";

//	@Column(name = "Description", length = 255, nullable = false)
	private String description = " ";

	
	public Authority(String authority)
	{
		this.authority=authority;
	}
	
	public Authority()
	{
	}
	
	public String getAuthority()
	{
		return authority;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (authority == null)
		{
			if (other.authority != null)
				return false;
		}
		else if (!authority.equals(other.authority))
			return false;
		return true;
	}

	

}
