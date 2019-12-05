package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Test {
	public static void main(String[] args) throws IOException {
		Configuration configuration= new Configuration();
        configuration.setDefaultEncoding("utf-8");
        // 要填入模本的数据文件
        Map dataMap = new HashMap();
        dataMap.put("realName", "张三");

        // 设置模本装置方法和路径
        File templePath = new File("E:/freemarkerword/");
        configuration.setDirectoryForTemplateLoading(templePath);
        Template t = null;
        try {
            // temple.ftl为要装载的模板
            t = configuration.getTemplate("word.ftl");

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出文档路径及名称
        File outFile = new File("E:/freemarkerword/"+new Date().getTime()+".docx");
        if (!outFile.getParentFile().exists()) {
        	                outFile.getParentFile().mkdirs();
        	           }
        Writer out = null;

        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "utf-8"));
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
    }
}
