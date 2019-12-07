package com.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils
{
	@Value("${file.path}")
	private String filePath = "";

	private static FileUtils $this;

	public FileUtils()
	{
		FileUtils.$this = this;
	}

	public static String getFilePath()
	{
		return $this.filePath;
	}

	public static String saveFile(MultipartFile file, String path) throws IllegalStateException, IOException
	{
		if (file.isEmpty())
		{
			return "";
		}
		if (path.equals(""))
		{
			return "";
		}
		if (!path.startsWith("/"))
		{
			path = $this.filePath + "/" + path;
		}
		else
		{
			path = $this.filePath + path;
		}

		File destfile = new File(path);
		if (!destfile.exists())
		{
			destfile.getParentFile().mkdirs();
		}
		file.transferTo(destfile);
		return path;
	}
	
	public static File getFile(String path) throws IllegalStateException, IOException
	{
		
		if (path.equals(""))
		{
			return null;
		}
		if (!path.startsWith("/"))
		{
			path = $this.filePath + "/" + path;
		}
		else
		{
			path = $this.filePath + path;
		}

		File destfile = new File(path);
		return destfile;
	}
}
