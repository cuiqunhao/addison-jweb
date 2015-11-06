package com.internet.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;

@Controller
@RequestMapping("/admin/xx")
@AuthClass("login")
public class HomeController {
	
	/**
	 * 获取用户分页列表集合信息
	 * @param model
	 * @return 获取用户分页列表集合信息
	 */
	@RequestMapping("/home")
	public String listUser(Model model) {
		return "common/home.jsp";
	}
}
