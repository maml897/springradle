package com.config.mvc.view;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView
{
	private byte[] bs;

	protected String fileName;

	protected boolean allowCaching = false;

	public DownloadView(byte[] bs, String fileName)
	{
		this(bs, fileName, false);
	}

	public DownloadView(byte[] bs, String fileName, boolean allowCaching)
	{
		setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		this.bs = bs;
		this.allowCaching = allowCaching;
		try
		{
			this.fileName = new String(convertFileName(fileName).getBytes("GBK"), "ISO-8859-1");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		if (!allowCaching)
		{
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		}
		response.setContentLength(bs.length);

		try(ServletOutputStream outputStream = response.getOutputStream();)
		{
			outputStream.write(bs);
		}
	}

	public static String convertFileName(String fileName) {
		String rt = fileName;
		if ("".equals(rt) || rt == null) {
			return "";
		}
		try {
			rt = rt.replace("\\", "、");
			rt = rt.replace("/", "／");
			rt = rt.replace(":", "：");
			rt = rt.replace("*", "＊");
			rt = rt.replace("?", "？");
			rt = rt.replace("\"", "“");
			rt = rt.replace("<", "〈");
			rt = rt.replace(">", "〉");
			rt = rt.replace("|", "｜");
			rt = rt.replace("=", "＝");
			rt = rt.replace(";", "；");
			rt = rt.replace(" ", "_");
			rt = rt.replace("\r", "");
			rt = rt.replace("\n", "");
		} catch (Exception e) {
		}
		return rt;
	}
}
