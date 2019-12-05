package com.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.TableService;
import com.utils.LambdaUtils;
import com.view.table.Column;

@RequestMapping(value={"/query","q"})
@Controller
public class QueryControl
{
	
	@Resource
	private TableService tableService;
	
	/**
	 * 查询
	 * @param model
	 * @param userID 查询用的ID
	 * @param tableID 查询表，如果位0，则查询整个userID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{tableID}")
	public String index(Model model, @PathVariable("tableID") long tableID) throws Exception
	{
		List<Column> columns = tableService.getColumns(tableID);
		columns=LambdaUtils.filter(columns, Column::isSearch);
		model.addAttribute("tableID", tableID);
		model.addAttribute("columns", columns);
		return "/query";
	}
	
	@RequestMapping("/q")
	public String query(Model model, @RequestParam(defaultValue = "0") long tableID,String key) throws Exception
	{
		List<Map<String, Object>> list=tableService.query(tableID, key);
		model.addAttribute("list", list);
		model.addAttribute("columns", tableService.getColumns(tableID));
		model.addAttribute("key", key);
		return "/query-result";
	}
}
