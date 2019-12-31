package com.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.common.excel.PoiExcelReader;
import com.config.security.user.CurrentUser;
import com.service.TableService;
import com.service.TestService;
import com.utils.FileUtils;
import com.view.table.Column;

@RequestMapping("")
@Controller
public class DemoControl {

	@Resource
	private TestService testService;
	
	@Resource
	private TableService tableService;
	
	@Value("${spring.a:0}")
	private String test;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "sbwuffrnm无"+test;
	}

	@RequestMapping("/ftl")
	public String ftl(Model model) {
		model.addAttribute("sb", "wugou无");
		return "/test";
	}
	
	@RequestMapping("/json")
	public View json(Model model) {
		model.addAttribute("sb", "wugou");
		return new MappingJackson2JsonView();
	}
	
	@ResponseBody
	@RequestMapping("/data")
	public String data(Model model) {
		return testService.test()+"";
	}
	
	@ResponseBody
	@RequestMapping("/postest")
	public String postest(Model model,String firstName,String lastName,@RequestParam("file") MultipartFile file) throws Exception {
		
		if (!file.isEmpty())
		{
			String path = FileUtils.saveFile(file, UUID.randomUUID().toString() + "/" + file.getOriginalFilename());
			
			File file1 = new File(path);
			List<List<String>> rows = new PoiExcelReader(file1).getDatas();
			if (rows.size() == 0)
			{
				return "ok";
			}

			// 设置表头
			List<String> titles = rows.get(0);
			List<Column> columns = new ArrayList<>();
			for (int i = 0; i < titles.size(); i++)
			{
				Column column = new Column();
				column.setOrder(i);
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
					newRow.add(row.get(j));
				}
				last.add(newRow);
			}
			tableService.importTable(CurrentUser.getUserDetails().getUserID(), file.getOriginalFilename(), columns, last);
		}
		return firstName+"--"+lastName;
	}
}
