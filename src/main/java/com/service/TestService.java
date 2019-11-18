package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public int test() {
		List<Long> ids = jdbcTemplate.queryForList("select ID from n_ns",Long.class);
		System.out.println(ids.size());
		return ids.size();
	}
}
