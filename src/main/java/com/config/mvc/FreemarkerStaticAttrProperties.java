package com.config.mvc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "frm.static.attr")
public class FreemarkerStaticAttrProperties
{
	private Map<String, String> bean = new HashMap<>();

	private Map<String, String> str = new HashMap<>();

	private Map<String, String> taglib = new HashMap<>();

	public Map<String, String> getBean()
	{
		return bean;
	}

	public void setBean(Map<String, String> bean)
	{
		this.bean = bean;
	}

	public Map<String, String> getStr()
	{
		return str;
	}

	public void setStr(Map<String, String> str)
	{
		this.str = str;
	}

	public Map<String, String> getTaglib()
	{
		return taglib;
	}

	public void setTaglib(Map<String, String> taglib)
	{
		this.taglib = taglib;
	}
}
