package com.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实现了基本的basic认证
 * @author maml
 */

@RequestMapping("/basic")
@Controller
public class BasicAuth
{
	@RequestMapping("loggin")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String sessionAuth = (String) request.getSession().getAttribute("auth");

		if (sessionAuth != null)
		{
			System.out.println("this is next step");
			nextStep(request, response);
		}
		else
		{
//https://cloud.tencent.com/developer/section/1190024
			if (!checkHeaderAuth(request, response))
			{
				response.setStatus(401);//必须
				response.setHeader("Cache-Control", "no-store");
				response.setDateHeader("Expires", 0);
				response.setHeader("WWW-authenticate", "Basic Realm=\"test111\"");//必须
			}
		}
	}

	private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String auth = request.getHeader("Authorization");
		System.out.println("auth encoded in base64 is " + getFromBASE64(auth));
		if ((auth != null) && (auth.length() > 6))//只要输入大于6位就算登陆了，此处因该去数据库查询
		{
			auth = auth.substring(6, auth.length());

			String decodedAuth = getFromBASE64(auth);
			System.out.println("auth decoded from base64 is " + decodedAuth);

			request.getSession().setAttribute("auth", decodedAuth);
			return true;
		}
		else
		{
			return false;
		}

	}

	private String getFromBASE64(String s)
	{
		if (s == null)
			return null;
		Base64.Decoder decoder = Base64.getDecoder();
		try
		{
			byte[] b = decoder.decode(s);
			return new String(b);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public void nextStep(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter();
		pw.println("<html> next step, authentication is : " + request.getSession().getAttribute("auth") + "<br>");
		pw.println("<br></html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doGet(request, response);
	}
}
