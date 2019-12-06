package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Map<String,Object> map =new HashMap<>();
		map.put("username", username);
		List<User> users = namedJdbcTemplate.query("select UserName,Password,RealName,ID from t_user where Enabled=true and (UserName=:username or Phone=:username)", map,new BeanPropertyRowMapper<>(User.class));
		
		if(users==null ||users.size()==0) {
			throw new UsernameNotFoundException("User " + username + " has no GrantedAuthority");
		}
		
		User user =users.get(0);
		
		map =new HashMap<>();
		map.put("userID", user.getId());
		List<Authority> authoritys = namedJdbcTemplate.query("select Authority from t_user_authority where UserID=:userID", map,new BeanPropertyRowMapper<>(Authority.class));;
		if (authoritys==null || authoritys.size() == 0)
		{
			throw new UsernameNotFoundException("User " + username + " has no GrantedAuthority");
		}

		List<GrantedAuthority> grantedAuthoritys = new ArrayList<>();
		for (Authority authority : authoritys)
		{
			grantedAuthoritys.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}

		WishUserDetails userDetails = new WishUserDetails(user.getId(), user.getUserName(), user.getPassword(), user.isEnabled(), grantedAuthoritys);
		userDetails.setUserID(user.getId());
		userDetails.setRealName(user.getRealName());
		return userDetails;
	}
	
	@Transactional(readOnly = false, value = "jdbcTemplateTm")
	public void addUser(User user) throws UsernameNotFoundException
	{
		//插入用户
		String sql ="INSERT INTO t_user (CreateDate,Enabled,Password,RealName,Phone,Mail,UserName)VALUES(:createDate,:enabled,:password,:realName,:phone,:mail,:userName)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user), keyHolder);
		
		//插入权限
		sql ="INSERT INTO t_user_authority (UserID,Authority)VALUES("+keyHolder.getKey()+",'ROLE_USER')";
		namedJdbcTemplate.update(sql, new HashMap<>());
	}
	
	public User getUser(String phoneOrMail) throws UsernameNotFoundException
	{
		return null;
	}
	
	public boolean checkPhone(String phone) throws UsernameNotFoundException
	{
		Map<String,Object> map =new HashMap<>();
		map.put("phone", phone);
		List<Long> IDs = namedJdbcTemplate.queryForList("select ID from t_user where Phone=:phone", map, Long.class);
		return !IDs.isEmpty();
	}
	
	public boolean checkMail(String mail) throws UsernameNotFoundException
	{
		Map<String,Object> map =new HashMap<>();
		map.put("mail", mail);
		List<Long> IDs = namedJdbcTemplate.queryForList("select ID from t_user where Mail=:mail", map, Long.class);
		return !IDs.isEmpty();
	}

}
