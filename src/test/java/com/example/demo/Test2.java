package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Test2 {
	public static void main(String[] args) throws IOException {
		Configuration configuration= new Configuration();
        configuration.setDefaultEncoding("utf-8");
        // 要填入模本的数据文件
        Map dataMap = new HashMap();
        dataMap.put("realName", "张三");

        // 设置模本装置方法和路径
        configuration.setClassForTemplateLoading(Test2.class, "");
        Template t = null;
        try {
            t = configuration.getTemplate("word.ftl");

        } catch (IOException e) {
            e.printStackTrace();
        }
       
        Writer out = null;

        try {
        	
        	out=new StringWriter();
        	
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        
        
        try {
            t.process(dataMap, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(out.toString());
    }
}
