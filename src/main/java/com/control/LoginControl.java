package com.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.config.mvc.view.StringView;

@RequestMapping("/login")
@Controller
public class LoginControl
{
	@RequestMapping("/login-fail")
	public View loginfail(@RequestParam(defaultValue = "false") boolean ajax,RedirectAttributes attributes)
	{
		if (ajax)
		{
			return new StringView("fail");
		}
		attributes.addFlashAttribute("flag","false");
		return new RedirectView("/", true);
	}

	@RequestMapping("/login-success")
	public String loginsuccess()
	{
		// 根据权限判断去处
		return "redirect:/manage/tables";
	}
}
