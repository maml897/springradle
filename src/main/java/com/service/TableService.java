package com.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.utils.Constant;
import com.view.table.Column;
import com.view.table.Row;
import com.view.table.Table;

@Service
public class TableService
{
	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Resource
	private JdbcTemplate jdbcTemplate;

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
		//1.插入table
		Table table = new Table();
		table.setUserID(userID);
		table.setTitle(tableName);
		
		String sql ="INSERT INTO t_table (Title,UserID,CType,CreateDate)VALUES(:tile,:userID,:type,:createDate)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(table), keyHolder);
		
		//2.插入column

//		List<Column> columns = columnDao.getColumns(tableID);
//		for (Column column : columns)
//		{
//			column.setTableID(table.getId());
			//columnDao.addColumn(column);
//		}
	}

	@Transactional(readOnly = false, value = "jdbcTemplateTm")
	public void importTable(long userID, String tableName, List<Column> columns, List<List<String>> datass)
	{
		//1.插入table
		Table table = new Table();
		table.setUserID(userID);
		table.setTitle(tableName);
		
		String sql ="INSERT INTO m_table (Title,UserID,CType,CreateDate)VALUES(:title,:userID,:type,:createDate)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(table), keyHolder);

		// 插入列
		BeanPropertySqlParameterSource[] beanPropertySqlParameterSources = new BeanPropertySqlParameterSource[columns.size()];
		for (int i = 0; i < columns.size(); i++)
		{
			beanPropertySqlParameterSources[i] = new BeanPropertySqlParameterSource(columns.get(i));
		}
	    sql = "insert into m_column(Title,Search,TableID,CShow,CType,CreateDate,COrder,SearchShow) values(:Title,:Search,"+keyHolder.getKey()+",:Show,:Type,:CreateDate,:Order,:SearchShow)";
	    namedJdbcTemplate.batchUpdate(sql, beanPropertySqlParameterSources);

	    //3.插入数据
	    List<Long> columnIDs = namedJdbcTemplate.queryForList("select ID from m_column where tableID="+keyHolder.getKey(), new HashMap<>(), Long.class);
	    
		String insertSql = "INSERT INTO m_row (CreateDate, TableID,CFrom,Times,data) values (?,?,?,?,?)";
		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException
			{
				java.sql.Date sqlDate = new java.sql.Date(table.getCreateDate().getTime());
				ps.setDate(1, sqlDate);
				ps.setLong(2, keyHolder.getKey().longValue());
				ps.setInt(3, Constant.ROW_FROM_EXCEL);
				ps.setInt(4, 0);
				List<String> datas = datass.get(i);
				JSONObject jsonObject =new JSONObject();
				for(int k=0;k<columnIDs.size();k++)
				{
					jsonObject.put(columnIDs.get(k)+"", datas.get(k));
				}
				ps.setString(5, jsonObject.toJSONString());
			}

			@Override
			public int getBatchSize()
			{
				return datass.size();
			}
		});
	}

	@Transactional(readOnly = false, value = "jdbcTemplateTm")
	public void setColumnOrder(List<Long> columnIDs)
	{
		String sql = "update m_column set COrder=? where ID=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException
			{
				ps.setInt(1, i);
				ps.setLong(2, columnIDs.get(i));
			}

			@Override
			public int getBatchSize()
			{
				return columnIDs.size();
			}
		});
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
	
	@Transactional(readOnly = false, value = "jdbcTemplateTm")
	public void removeColumn(long columnID)
	{
		String sql ="delete from m_column where ID=:id";
		Map<String,Object> map =new HashMap<>();
		map.put("id", columnID);
		namedJdbcTemplate.update(sql, map);
	}
	
	@Transactional(readOnly = false, value = "jdbcTemplateTm")
	public void modifyColumn(long columnID,String name)
	{
		String sql ="update m_column set Title=:title where ID=:id";
		Map<String,Object> map =new HashMap<>();
		map.put("title", name);
		map.put("id", columnID);
		namedJdbcTemplate.update(sql, map);
	}
	
	@Transactional(readOnly = false, value = "jdbcTemplateTm")
	public long addColumn(Column column)
	{
		String sql ="INSERT INTO m_column (Title,Search,TableID,CShow,CType,CreateDate,COrder,SearchShow)VALUES(:title,:search,:tableID,:show,:type,:createDate,:order,:searchShow)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(column), keyHolder);
		return keyHolder.getKey().longValue();
	}
}
