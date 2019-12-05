package com.config.mvc.view;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 返回字符串的view
 * @author maml
 */
public class StringView extends AbstractView
{

	private String content;

	protected boolean allowCaching = false;

	public StringView(String content)
	{
		setContentType(MediaType.TEXT_PLAIN_VALUE);
		this.content = content;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setHeader("Content-Type", "text/plain;charset=utf-8");
		if (!allowCaching)
		{
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		}
		byte[] bs = content.getBytes(response.getCharacterEncoding());
		response.setContentLength(bs.length);
		try(ServletOutputStream outputStream = response.getOutputStream();) {
			outputStream.write(bs);
			outputStream.flush();
			outputStream.close();
		}
	}
}
