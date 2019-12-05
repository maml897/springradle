package com.view.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.utils.Constant;
public class Column
{
	
	public Map<String, Object> getMap()
	{
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("Title", title);
		map.put("Search", search);
		map.put("TableID", tableID);
		map.put("CShow", show);
		map.put("CType", type);
		map.put("CreateDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createDate));
		map.put("COrder", order);
		map.put("RowName", rowName);
		map.put("SearchShow", searchShow);
		return map;
	}
	
	private long id;

	private long tableID;

	private boolean search = false;

	private boolean show = true;

	private int type = Constant.COLUMN_TYPE_TEXT;
	
	private Date createDate=new Date();
	
	private int order=0;
	
	private String title="";
	
	private String rowName;
	
	private boolean searchShow = true;
	
	public String getRowName()
	{
		return rowName;
	}

	public void setRowName(String rowName)
	{
		this.rowName = rowName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
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

	public long getTableID()
	{
		return tableID;
	}

	public void setTableID(long tableID)
	{
		this.tableID = tableID;
	}

	public boolean isSearch()
	{
		return search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public boolean isSearchShow()
	{
		return searchShow;
	}

	public void setSearchShow(boolean searchShow)
	{
		this.searchShow = searchShow;
	}
	
	

}
