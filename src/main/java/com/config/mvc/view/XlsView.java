package com.config.mvc.view;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.common.excel.PoiExcelWriter;

public class XlsView extends AbstractView
{

	private PoiExcelWriter excelWriter;

	private String fileName;

	private boolean allowCaching = false;

	public XlsView(PoiExcelWriter excelWriter, String fileName)
	{
		this(excelWriter, fileName, false);
	}

	public XlsView(PoiExcelWriter excelWriter, String fileName, boolean allowCaching)
	{
		setContentType("application/octet-stream");
		this.excelWriter = excelWriter;
		this.allowCaching = allowCaching;
		try
		{
			this.fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");
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
		try(ServletOutputStream outputStream = response.getOutputStream();)
		{
			excelWriter.write(outputStream);
		}
	}

}
