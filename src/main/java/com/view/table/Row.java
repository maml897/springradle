package com.view.table;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.utils.Constant;

public class Row
{
	private long id;

	private long tableID;

	private Date date = new Date();

	private int from = Constant.ROW_FROM_EXCEL;// 0:excel导入，1：管理员手动录入

	private int times = 0;// 查询次数

	private String data = "";

	private JSONObject json = null;

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public JSONObject getJson()
	{
		if (json == null)
		{
			if(data!=null) {
				this.json = JSONObject.parseObject(data);
			}
			else {
				this.json =new JSONObject();
			}
		}

		return json;
	}

	public void setJson(JSONObject json)
	{
		this.json = json;
	}

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
