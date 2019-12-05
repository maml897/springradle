package com.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable
{
	private static final long serialVersionUID = 732887907783669834L;

	private int pageNumber = 1;

	private int totalPage = 0;

	private int pageSize = 10;

	private int totalNumber = 0;

	private List<T> list = new ArrayList<>();

	/**
	 * is it a new list? default is false.
	 */
	private boolean newList = false;

	/**
	 * default Constructor
	 */
	public Page()
	{

	}

	public Page(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public void paging()
	{
		if (this.pageNumber <= 0)
		{
			this.pageNumber = 1;
		}
		if (totalNumber > 0)
		{
			if (totalNumber % pageSize == 0)
			{
				totalPage = totalNumber / pageSize;
			}
			else
			{
				totalPage = totalNumber / pageSize + 1;
			}

			if (pageNumber > totalPage)
			{
				pageNumber = totalPage;
			}
		}
		else
		{
			totalPage = 0;
		}
		this.newList = false;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getTotalNumber()
	{
		return totalNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public void setTotalNumber(int totalNumber)
	{
		this.totalNumber = totalNumber;
	}

	public Page<T> setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
		return this;
	}

	public List<T> getList()
	{
		return list;
	}

	public void setList(List<T> list)
	{
		this.list = list;
	}

	public boolean isNewList()
	{
		if (totalNumber == 0)
		{
			return true;
		}
		return newList;
	}

	public void setNewList(boolean newList)
	{
		this.newList = newList;
	}

	public int getPreNumber()
	{
		return this.pageNumber - 1;
	}

	public int getNextNumber()
	{
		return this.pageNumber + 1;
	}
}
