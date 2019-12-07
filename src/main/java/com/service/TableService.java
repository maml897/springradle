package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.Page;
import com.utils.LambdaUtils;
import com.view.table.Column;
import com.view.table.Row;
import com.view.table.Table;

@Service
public class TableService
{

//	@Resource
//	private TableDao tableDao;
//
//	@Resource
//	private ColumnDao columnDao;
//
//	@Resource
//	private RowDao rowDao;
//
//	@Resource
//	private SearchDao searchDao;
	
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public Page<Table> getTablePage(Page<Table> page, long userID)
	{
		List<Table> list = namedJdbcTemplate.query("select * from m_table where userID="+userID, new HashMap<>(),new BeanPropertyRowMapper<>(Table.class));
		page.setTotalNumber(0);
		page.setList(list);
		return page;
	}

	public Table getFirstTable(long userID)
	{
		List<Table> list = namedJdbcTemplate.query("select * from m_table where userID="+userID, new HashMap<>(),new BeanPropertyRowMapper<>(Table.class));
		if(list.isEmpty()) {
			return new Table();
		}
		return list.get(0);
	}

	public Table getTable(long tableID)
	{
		Table obj = namedJdbcTemplate.queryForObject("select * from m_table where id="+tableID, new HashMap<>(),new BeanPropertyRowMapper<>(Table.class));
		return obj;
	}

	public List<Column> getColumns(long tableID)
	{
		List<Column> list = namedJdbcTemplate.query("select ID,Title,Search,SearchShow,CShow as 'show' from m_column where tableID="+tableID +" order by COrder", new HashMap<>(),new BeanPropertyRowMapper<>(Column.class));
		return list;
	}

	public Page<Row> getRowPage(Page<Row> page, long tableID)
	{
		page.setTotalNumber(0);
		List<Row> list = namedJdbcTemplate.query("select * from m_row where tableID="+tableID, new HashMap<>(),new BeanPropertyRowMapper<>(Row.class));
		page.setList(list);
		return page;
	}

	// 从模板选择创建表格
	public void copyTable(long userID, String tableName, long tableID)
	{
		Table table = new Table();
		table.setUserID(userID);
		table.setTitle(tableName);

		//tableDao.addTable(table);

		//List<Column> columns = columnDao.getColumns(tableID);
//		for (Column column : columns)
//		{
//			column.setTableID(table.getId());
			//columnDao.addColumn(column);
//		}
	}

	// 导入表
	@Transactional(readOnly = false)
	public void addTable(long userID, String tableName, List<Column> columns, List<List<String>> datass)
	{
		// 插入表
		Table table = new Table();
		table.setUserID(userID);
		table.setTitle(tableName);
		//tableDao.addTable(table);

		// 插入列
		for (Column column : columns)
		{
			column.setTableID(table.getId());
			//columnDao.addColumn(column);
		}

		List<String> titles = LambdaUtils.list2list(columns, Column::getRowName);
		addRows(table.getId(), titles, datass);
	}

	// 追加数据
	public void addRows(long tableID, List<String> titles, List<List<String>> datass)
	{
		titles.add("TableID");
		titles.add("CreateDate");

		List<List<Object>> lastdatass = new ArrayList<>();
		for (List<String> datas : datass)
		{
			List<Object> objects = new ArrayList<>(datas);
			objects.add(tableID);
			objects.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			lastdatass.add(objects);
		}
		//rowDao.addRows(titles, lastdatass);
	}

	@Transactional(readOnly = false)
	public void setColumnOrder(long columnID, int order)
	{
		//columnDao.setColumnOrder(columnID, order);
	}

	@Transactional(readOnly = false)
	public void setColumnSearchShow(long columnID, boolean show) throws Exception
	{
		//columnDao.setColumnSearchShow(columnID, show);
	}

	@Transactional(readOnly = false)
	public void setColumnType(long columnID, int type) throws Exception
	{
		//columnDao.setColumnType(columnID, type);
	}

	public List<Map<String, Object>> query(long tableID, String key)
	{
//		List<Column> columns = columnDao.getColumns(tableID);
//		return rowDao.getRows(tableID, columns, key);
		return null;
	}

	/**
	 * 获取搜索条件
	 * @param tableID
	 * @return
	 */
	public Map<Long, List<Column>> getSearch(long tableID)
	{
//		Map<Long, List<Column>> map = new LinkedHashMap<>();
//		List<Long> searchIDs = searchDao.getSearchs(tableID);
//		for (long searchID : searchIDs)
//		{
//			List<Column> columns = new ArrayList<>();
//			if (!columns.isEmpty())
//			{
//				map.put(searchID, columns);
//			}
//		}
//		return map;
		return null;
	}
	
	@Transactional(readOnly = false)
	public void removeColumn(long columnID)
	{
//		Column column = columnDao.getColumn(columnID);
//		rowDao.clear(column.getTableID(), column.getRowName());
//		columnDao.removeColumn(columnID);
	}
	
	@Transactional(readOnly = false)
	public void modifyColumn(long columnID,String name)
	{
		//columnDao.updateColumn(columnID, name);
	}
	
	@Transactional(readOnly = false)
	public void addColumn(Column column)
	{
		//columnDao.addColumn(column);
		//rowDao.clear(column.getTableID(), column.getRowName());
	}
	
	@Transactional(readOnly = false)
	public void clearRow(String rowName,long tableID)
	{
		//rowDao.clear(tableID, rowName);
	}
}
