package com.internet.cms.controller.qr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;

@RequestMapping("/admin/qr")
@Controller
@AuthClass
public class QRController {
	
	@RequestMapping("/create")
	public String getMapLine() {
		return "qr/create";
	}
	

	

	
}
