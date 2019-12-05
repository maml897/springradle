package com.view.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.utils.Constant;

public class Table
{
	public Map<String, Object> getMap()
	{
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("Title", title);
		map.put("UserID", userID);
		map.put("CType", type);
		map.put("CreateDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createDate));
		return map;
	}

	private long id;

	private String title;

	private long userID;

	private int type = Constant.TABLT_TYPE_USER;// 0:系统模板，1.用户

	private Date createDate = new Date();

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
}
