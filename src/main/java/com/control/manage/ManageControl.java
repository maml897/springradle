package com.control.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.excel.PoiExcelReader;
import com.config.mvc.view.StringView;
import com.config.security.user.CurrentUser;
import com.service.TableService;
import com.utils.FileUtils;
import com.view.table.Column;
import com.view.table.Row;
import com.view.table.Table;

@RequestMapping("/manage")
@Controller
public class ManageControl
{

	@Resource
	private TableService tableService;

	@RequestMapping("")
	public String index(Model model)
	{
		return "/manage/index";
	}

	@RequestMapping("tables")
	public String tables(Model model, Page<Table> page)
	{
		page = tableService.getTablePage(page.setPageSize(50), CurrentUser.getUserDetails().getUserID());
		model.addAttribute("page", page);
		return "/manage/tables";
	}

	@RequestMapping("import-table")
	public String inporttable()
	{
		return "/manage/import-table";
	}

	@RequestMapping(value = "upload-excel", produces = "text/plain;charset=UTF-8")
	public View uploadexcel(RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) throws Exception
	{
		if (!file.isEmpty())
		{
			String path = FileUtils.saveFile(file, UUID.randomUUID().toString() + "/" + file.getOriginalFilename());
			redirectAttributes.addAttribute("path", path);
			return new RedirectView("excel", true);
		}
		return new StringView("1");
	}

	@RequestMapping(value = "excel")
	public String excel(Model model, String path) throws Exception
	{
		PoiExcelReader excelReader = new PoiExcelReader(path);
		excelReader.selSheet(0);
		List<String> titles = new ArrayList<>();
		List<List<String>> rows = new ArrayList<>();
		for (int i = 0; i < excelReader.getCurRows(); i++)
		{
			if (i == 0)
			{
				for (int j = 0; j < excelReader.getCurColumns(); j++)
				{
					titles.add(excelReader.getOneCell(j, i).trim());
				}
				continue;
			}

			List<String> row = new ArrayList<>();
			for (int j = 0; j < excelReader.getCurColumns(); j++)
			{
				row.add(excelReader.getOneCell(j, i).trim());
			}
			rows.add(row);
		}

		model.addAttribute("titles", titles);
		model.addAttribute("rows", rows);
		model.addAttribute("path", path);
		return "/manage/excel";
	}

	@ResponseBody
	@RequestMapping(value = "add-table")
	public String addtable(Model model, String path, @RequestParam("types") List<Integer> types) throws Exception
	{
		File file = new File(path);
		List<List<String>> rows = new PoiExcelReader(file).getDatas();
		if (rows.size() == 0)
		{
			return "ok";
		}

		// 设置表头
		List<String> titles = rows.get(0);
		List<Column> columns = new ArrayList<>();
		for (int i = 0; i < titles.size(); i++)
		{
			if (types.get(i) == -1)// 设置不导入的列排除掉
			{
				continue;
			}
			Column column = new Column();
			column.setOrder(i);
			column.setType(types.get(i));
			column.setTitle(titles.get(i));
			columns.add(column);
		}

		List<List<String>> last = new ArrayList<>();
		// 设置数据
		for (int i = 1; i < rows.size(); i++)
		{
			List<String> newRow = new ArrayList<>();
			List<String> row = rows.get(i);
			for (int j = 0; j < row.size(); j++)
			{
				if (types.get(j) == -1)// 设置不导入的列排除掉
				{
					continue;
				}
				newRow.add(row.get(j));
			}
			last.add(newRow);
		}
		tableService.importTable(CurrentUser.getUserDetails().getUserID(), file.getName(), columns, last);
		return "ok";
	}

	@RequestMapping(value = { "rows", "rows/{tableID}" })
	public String rows(Model model, @PathVariable(name = "tableID", required = false) Long tableID, Page<Row> page) throws Exception
	{
		Table table = new Table();
		if (tableID == null)
		{
			table = tableService.getFirstTable(CurrentUser.getUserDetails().getUserID());
			tableID = table.getId();
			if (tableID == 0)
			{
				return "redirect:/";
			}
		}
		else
		{
			table = tableService.getTable(tableID);
		}

		if (table.getUserID() != CurrentUser.getUserDetails().getUserID())
		{
			return "redirect:/403";
		}

		model.addAttribute("columns", tableService.getColumns(tableID));
		model.addAttribute("page", tableService.getRowPage(page.setPageSize(20), tableID));
		return "/manage/rows";
	}

	@RequestMapping(value = { "table-set" })
	public String tableset(Model model, long tableID) throws Exception
	{
		Table table = tableService.getTable(tableID);
		if (table.getUserID() != CurrentUser.getUserDetails().getUserID())
		{
			return "redirect/";
		}
		List<Column> columns = tableService.getColumns(tableID);
		model.addAttribute("columns", columns);
		model.addAttribute("tableID", tableID);
		return "/manage/table-set";
	}

	@RequestMapping(value = { "q-set" })
	public String qset(Model model, long tableID) throws Exception
	{
		Table table = tableService.getTable(tableID);
		if (table.getUserID() != CurrentUser.getUserDetails().getUserID())
		{
			return "redirect/";
		}
		List<Column> columns = tableService.getColumns(tableID);
		model.addAttribute("columns", columns);
		model.addAttribute("tableID", tableID);
		return "/manage/q-set";
	}

	@ResponseBody
	@RequestMapping(value = { "ser-table-order" })
	public String setTableOrder(Model model, @RequestParam(name = "tableIDs") List<Long> tableIDs) throws Exception
	{
		tableService.setTableOrder(tableIDs);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value = { "ser-column-order" })
	public String setColumnOrder(Model model, @RequestParam(name = "columnID") List<Long> columnID) throws Exception
	{
		tableService.setColumnOrder(columnID);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "set-column-searchshow" })
	public String setColumnSearchShow(Model model, @RequestParam(name = "columnID") long columnID, boolean show) throws Exception
	{
		tableService.setColumnSearchShow(columnID, show);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "set-column-type" })
	public String setColumnSearchShow(Model model, @RequestParam(name = "columnID") long columnID, int type) throws Exception
	{
		tableService.setColumnType(columnID, type);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "remove-column" })
	public String removeColumn(Model model, @RequestParam(name = "columnID") long columnID) throws Exception
	{
		tableService.removeColumn(columnID);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "modify-column" })
	public String modifyColumn(Model model, @RequestParam(name = "columnID") long columnID, String name) throws Exception
	{
		tableService.modifyColumn(columnID, name);
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "add-column" })
	public String addColumn(Model model, Column column) throws Exception
	{

		long id = tableService.addColumn(column);
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("order", column.getOrder());
		return object.toJSONString();

	}
}
