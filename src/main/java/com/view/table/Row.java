package com.view.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.utils.Constant;

public class Row
{
	public Map<String, Object> getMap()
	{
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("CreateDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
		map.put("TableID", tableID);
		return map;
	}

	private long id;

	private long tableID;

	private Date date = new Date();
	
	private int from=Constant.ROW_FROM_EXCEL ;//0:excel导入，1：管理员手动录入
	
	private int times=0;//查询次数
	
	public int getFrom()
	{
		return from;
	}

	public void setFrom(int from)
	{
		this.from = from;
	}

	public int getTimes()
	{
		return times;
	}

	public void setTimes(int times)
	{
		this.times = times;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getTableID()
	{
		return tableID;
	}

	public void setTableID(long tableID)
	{
		this.tableID = tableID;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}
