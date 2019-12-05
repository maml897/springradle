package com.config.mvc.view;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.common.excel.PoiExcelSheet;
import com.common.excel.PoiExcelWriter;

public class ExcelView extends AbstractView
{
	private PoiExcelWriter excelWriter;

	private String fileName;

	private boolean allowCaching = false;
	

	public ExcelView(String fileName,List<List<String>> list)
	{
		this(fileName, false,list);
	}

	public ExcelView(String fileName, boolean allowCaching,List<List<String>> list)
	{
		setContentType("application/octet-stream");
		this.allowCaching = allowCaching;
		try
		{
			this.fileName = new String(fileName.getBytes("gbk"), "ISO-8859-1");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		excelWriter = PoiExcelWriter.createPoiExcelWriterXLS();
		PoiExcelSheet excelSheet = excelWriter.createSheet("信息");
		int col = 0;
		for (List<String> item : list)
		{
			for(int i=0;i<item.size();i++) {
				excelSheet.addCell(i, col, item.get(i));
			}
			col++;
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
