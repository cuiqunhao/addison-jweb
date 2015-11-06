package com.internet.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;

@Controller
@RequestMapping("/admin/user")
@AuthClass("login")
public class UserController {

	@RequestMapping("/home")
	public String home(Model model) {
		return "common/home";
	}

}
