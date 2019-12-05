package com.config.mvc.view;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class PostbackView extends AbstractView
{
	protected boolean allowCaching = false;

	private String location = "";

	Map<String, String> params = new HashMap<>();

	public PostbackView(String location, Map<String, String> params)
	{
		this.params = params;
		this.location = location;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (location.startsWith("/"))
		{
			location = request.getContextPath() + location;
		}

		response.addHeader("Content-Type", "text/html;charset=utf-8");
		try(PrintWriter pw = new PrintWriter(response.getOutputStream());) {
			// response.addHeader(arg0, arg1);
			pw.write("<!DOCTYPE html><html><body><form action=\"" + location + "\" method=\"POST\">");
			writeFormElements(request, pw);
			writePrologueScript(pw);
			pw.write("</html>");
			pw.flush();
		}
	}

	protected boolean isElementIncluded(String name)
	{
		return !name.startsWith("action:");
	}

	protected void writeFormElement(PrintWriter pw, String name, String value) throws UnsupportedEncodingException
	{
		String encName = URLEncoder.encode(name, "UTF-8");
		String encValue = URLEncoder.encode(value, "UTF-8");
		pw.write("<input type=\"hidden\" name=\"" + encName + "\" value=\"" + encValue + "\"/>");
	}

	private void writeFormElements(HttpServletRequest request, PrintWriter pw) throws UnsupportedEncodingException
	{
		for (String name : params.keySet())
		{
			String values = params.get(name);
			if (isElementIncluded(name))
			{
				writeFormElement(pw, name, values);
			}
		}
	}

	protected void writePrologueScript(PrintWriter pw)
	{
		pw.write("<script>");
		pw.write("setTimeout(function(){document.forms[0].submit();},0);");
		pw.write("</script>");
	}
}
