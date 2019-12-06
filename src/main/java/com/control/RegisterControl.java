package com.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.config.mvc.view.PostbackView;
import com.service.UserService;
import com.view.Authority;
import com.view.User;

@RequestMapping("/register")
@Controller
public class RegisterControl
{
	@Resource
	private UserService userService;

	@RequestMapping("")
	public String register(Model model, @RequestParam(defaultValue = "") String realName, @RequestParam(defaultValue = "") String phone, @RequestParam(defaultValue = "") String mail)
	{
		model.addAttribute("realName", realName);
		model.addAttribute("phone", phone);
		model.addAttribute("mail", mail);
		return "/register";
	}

	@ResponseBody
	@RequestMapping("/check")
	public String check(Model model, String value)
	{
		User user = userService.getUser(value);
		return (user.getId() == 0) + "";
	}

	@RequestMapping("/register-process")
	public View registerprocess(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, User user)
	{
		boolean phone = userService.checkPhone(user.getPhone());
		if (phone)
		{
			redirectAttributes.addAttribute("realName", user.getRealName());
			redirectAttributes.addAttribute("phone", user.getPhone());
			redirectAttributes.addAttribute("mail", user.getMail());
			redirectAttributes.addFlashAttribute("password", user.getPassword());
			redirectAttributes.addFlashAttribute("flag", "phone");
			return new RedirectView("/register", true);
		}
		if (!user.getMail().equals(""))
		{
			boolean mail = userService.checkPhone(user.getMail());
			if (mail)
			{
				redirectAttributes.addAttribute("realName", user.getRealName());
				redirectAttributes.addAttribute("phone", user.getPhone());
				redirectAttributes.addAttribute("mail", user.getMail());
				redirectAttributes.addFlashAttribute("password", user.getPassword());
				redirectAttributes.addFlashAttribute("flag", "mail");
				return new RedirectView("/register", true);
			}
		}

		user.setAuthoritys(Arrays.asList(new Authority("ROLE_USER")));
		userService.addUser(user);
		Map<String, String> map = new HashMap<>();
		map.put("username", user.getPhone());
		map.put("password", user.getPassword());
		return new PostbackView(request.getContextPath() + "/SystemLogin", map);
	}
}
