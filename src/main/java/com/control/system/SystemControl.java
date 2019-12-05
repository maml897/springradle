package com.control.system;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "system")
public class SystemControl
{


	@RequestMapping(value = "system")
	public String exam(Model model, @RequestParam(defaultValue = "") String sql,@RequestParam(defaultValue="100")int limit) throws UnsupportedEncodingException
	{
		try
		{
			if (sql.equals(""))
			{
				model.addAttribute("list", new ArrayList<>());
				return "/system/system";
			}
			
			String upsql=sql.toUpperCase();
			if (upsql.indexOf("TABLE") > -1 || upsql.indexOf("DATABASE") > -1 || upsql.indexOf("DROP") > -1 || upsql.indexOf("ALTER") > -1 || upsql.indexOf("UPDATE") > -1
					|| upsql.indexOf("TRUNCATE") > -1){
				model.addAttribute("list", new ArrayList<>());
				return "/system/system";
			}
			
			if(limit>0&&!sql.toLowerCase().contains(" limit "))
			{
				//model.addAttribute("list", queryWish.createNativeQuery(sql, LinkedHashMap.class).setMaxResults(limit).getResultList());
			}
			else
			{
				//model.addAttribute("list", queryWish.createNativeQuery(sql, LinkedHashMap.class).getResultList());
			}
		}
		catch (Exception e)
		{
			StringWriter sw=new StringWriter();
        	e.printStackTrace(new PrintWriter(sw));
        	String result=(sw.getBuffer().toString());
			model.addAttribute("e", result);
		}
		
		model.addAttribute("sql", sql);
		model.addAttribute("limit",limit);
		return "/system/system";
	}
}
