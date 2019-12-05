package com.config.mvc;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.config.security.user.CurrentUser;
import com.config.security.user.WishUserDetails;

import freemarker.template.SimpleHash;

public class FreeMarkerViewCustom extends FreeMarkerView
{
	private boolean exposeRequestParameters = true;

	// exposeHelpers 重写这个也可以
	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
	{
		WishUserDetails wishUserDetails = CurrentUser.getUserDetails();
		if (wishUserDetails != null)
		{
			model.put("wishUserDetails", wishUserDetails);
		}
		model.put("response", response);
		model.put("request", request);

		if (exposeRequestParameters)
		{
			for (Enumeration<String> en = request.getParameterNames(); en.hasMoreElements();)
			{
				String attribute = en.nextElement();
				if (model.containsKey(attribute))
				{
					continue;
				}
				Object attributeValue = request.getParameter(attribute);
				model.put(attribute, attributeValue);
			}
		}
		return super.buildTemplateModel(model, request, response);
	}
}
