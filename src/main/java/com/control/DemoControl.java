package com.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@RequestMapping("")
@Controller
public class DemoControl {

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "sbwuffrnm";
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
}
