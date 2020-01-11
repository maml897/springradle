package com.view.table;

import java.util.Date;

import com.utils.Constant;

public class Table
{
	private long id;

	private String title;
	
	private String icon;
	private String color;

	private long userID;

	private int type = Constant.TABLT_TYPE_USER;// 0:系统模板，1.用户
	
	private int childs=0;//0是文件，大于0是文件夹，表示子文件的数量
	
	private long fatherID=0;//0是没有在文件夹中，其余的是属于某个文件夹

	private Date createDate = new Date();

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public long getUserID()
	{
		return userID;
	}

	public void setUserID(long userID)
	{
		this.userID = userID;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getChilds()
	{
		return childs;
	}

	public void setChilds(int childs)
	{
		this.childs = childs;
	}

	public long getFatherID()
	{
		return fatherID;
	}

	public void setFatherID(long fatherID)
	{
		this.fatherID = fatherID;
	}
	
}
