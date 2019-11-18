package com.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfigurer {
	
	//配置两个数据源
	@Bean(name = "ds")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.qq")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
	
	@Bean(name = "dsNormal")
    @ConfigurationProperties(prefix = "spring.datasource.druid.normal")
    public DataSource dsNormal() {
        return DruidDataSourceBuilder.create().build();
    }
	
	///1
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean(name= "namedJdbcTemplate")
	public NamedParameterJdbcTemplate namedJdbcTemplate() {
		return new NamedParameterJdbcTemplate(jdbcTemplate());
	}

	@Bean(name = "jdbcTemplateTx")
	public PlatformTransactionManager jdbcTemplateQqTx() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	//2
	@Bean(name = "jdbcTemplateNormal")
	public JdbcTemplate jdbcTemplateNormal() {
		return new JdbcTemplate(dsNormal());
	}
	
	@Bean(name= "namedJdbcTemplateNormal")
	public NamedParameterJdbcTemplate namedJdbcTemplateNormal() {
		return new NamedParameterJdbcTemplate(jdbcTemplateNormal());
	}

	@Bean(name = "jdbcTemplateNormalTx")
	public PlatformTransactionManager jdbcTemplateNaomalTx() {
		return new DataSourceTransactionManager(dsNormal());
	}
}
