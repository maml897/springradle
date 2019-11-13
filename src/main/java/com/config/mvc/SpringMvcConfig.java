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
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver(FreeMarkerProperties properties, ServletContext servletContext, freemarker.template.Configuration c) throws TemplateModelException
	{
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		properties.applyToMvcViewResolver(resolver);
		Map<String,Object> attributes = new HashMap<>();
		
		//加载项目根相对路径
		attributes.put("base", servletContext.getContextPath());
		
		//加载静态方法引用
		BeansWrapperBuilder swb = new BeansWrapperBuilder(freemarker.template.Configuration.getVersion());
		swb.setSimpleMapWrapper(true);
		BeansWrapper wrapper = swb.build();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		resolver.setAttributesMap(attributes);
		c.setObjectWrapper(wrapper);
		
		MultiTemplateLoader templateLoader1 = (MultiTemplateLoader) c.getTemplateLoader();
		int size = templateLoader1.getTemplateLoaderCount();
		
		TemplateLoader[] templateLoaders = new TemplateLoader[size+1];
		for(int i=0;i<size;i++) {
			templateLoaders[i] = templateLoader1.getTemplateLoader(i);
		}
		return resolver;
	}
}
