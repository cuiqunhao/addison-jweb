package com.internet.cms.controller.line;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;

@RequestMapping("/admin/line")
@Controller
@AuthClass
public class LineController {
	
	@RequestMapping("/map")
	public String getMapLine() {
		return "line/line";
	}
	

	

	
}
