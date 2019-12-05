package com.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "t_user")
public class User implements Serializable
{

	private static final long serialVersionUID = 1946565865390558320L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "ID", nullable = false)
	private long id = 0;

//	@Column(name = "RealName", length = 40, nullable = false)
	private String realName = "";
	
//	@Column(name = "Phone", length = 20, nullable = false, unique = true)
	private String phone = "";
	
//	@Column(name = "Mail", length = 40, nullable = false, unique = true)
	private String mail = "";

//	@Column(name = "Password", length = 40, nullable = false)
	private String password = "";

//	@Column(name = "Enabled", nullable = false, columnDefinition = "bit")
	private boolean enabled = true;

//	@Column(name = "CreateDate", length = 19, nullable = false)
	private Date createDate = new Date();

//	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//	@JoinTable(name = "t_user_authority", joinColumns = { @JoinColumn(name = "UserID", referencedColumnName = "ID") }, inverseJoinColumns = {
//			@JoinColumn(name = "Authority", referencedColumnName = "Authority") })
	private List<Authority> authoritys = new ArrayList<Authority>();

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public List<Authority> getAuthoritys()
	{
		return authoritys;
	}

	public void setAuthoritys(List<Authority> authoritys)
	{
		this.authoritys = authoritys;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
