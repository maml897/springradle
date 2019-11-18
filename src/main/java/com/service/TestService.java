package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	@Resource(name="jdbcTemplateNormal")
	private JdbcTemplate jdbcTemplateNormal;
	
	@Resource(name="namedJdbcTemplateNormal")
	private NamedParameterJdbcTemplate namedJdbcTemplateNormal;
	
	public int test() {
		Map<String,Object> map =new HashMap<>();
		List<Long> ids = namedJdbcTemplate.queryForList("select ID from n_ns",map,Long.class);
		return ids.size();
	}
}
