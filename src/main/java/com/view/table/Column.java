package com.view.table;

import java.util.Date;

import com.utils.Constant;

public class Column
{
	private long id;

	private long tableID;

	private boolean search = false;

	private boolean show = true;

	private int type = Constant.COLUMN_TYPE_TEXT;

	private Date createDate = new Date();

	private int order = 0;

	private String title = "";

	private boolean searchShow = true;

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
