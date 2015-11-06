package com.internet.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;


@Controller
@RequestMapping("/admin/rest")
@AuthClass
public class RestController {
	@RequestMapping("/index")
	public String index(Model model,Integer type) {
		model.addAttribute("type", type);
		return "rest/index";
	}
	
	@RequestMapping(value="/fileupload")
	public String fileupload(Model model,Integer type) {
		model.addAttribute("type", type);
		return "rest/fileupload";
	}
}
