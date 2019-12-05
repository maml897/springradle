package com.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class IndexControl
{
	@RequestMapping({"","login"})
	public String login(Model model)
	{
		return "/login";
	}
}
