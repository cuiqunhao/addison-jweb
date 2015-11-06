package com.internet.cms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.auth.AuthMethod;
import com.internet.cms.web.CmsSessionContext;

@Controller
@AuthClass("login")
public class AdminController {

	@RequestMapping("/admin")
	@AuthMethod
	public String index() {
		return "admin/index";
	}
	
	@AuthMethod
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		CmsSessionContext.removeSession(session);
		session.invalidate();
		return "redirect:/login";
	}
}
