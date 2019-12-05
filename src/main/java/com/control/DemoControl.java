package com.control;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.service.TestService;

@RequestMapping("")
@Controller
public class DemoControl {

	@Resource
	private TestService testService;
	
	@Value("${spring.a:0}")
	private String test;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "sbwuffrnm"+test;
	}

	@RequestMapping("/ftl")
	public String ftl(Model model) {
		model.addAttribute("sb", "wugou");
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
}
