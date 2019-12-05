package com.config.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver(FreeMarkerProperties properties, ServletContext servletContext, freemarker.template.Configuration c) throws TemplateModelException
	{
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		properties.applyToMvcViewResolver(resolver);
		resolver.setViewClass(FreeMarkerViewCustom.class);
		
		Map<String,Object> attributes = new HashMap<>();
		attributes.put("base", servletContext.getContextPath());//加载项目根相对路径
		resolver.setAttributesMap(attributes);
		
		//设置包装器
		FreemarkerBeanWraper wrapper=new FreemarkerBeanWraper();
		wrapper.setSimpleMapWrapper(true);
		c.setObjectWrapper(wrapper);
		
		//加载静态方法引用
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		//设置加载器
		MultiTemplateLoader templateLoader1 = (MultiTemplateLoader) c.getTemplateLoader();
		int size = templateLoader1.getTemplateLoaderCount();
		
		TemplateLoader[] templateLoaders = new TemplateLoader[size+1];
		for(int i=0;i<size;i++) {
			templateLoaders[i] = templateLoader1.getTemplateLoader(i);
		}
		
		//设置共享变量
		new SharedVariableSecurity(c);
		return resolver;
	}
}
