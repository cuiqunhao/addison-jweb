package com.internet.cms.controller.qr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;

/**
 * Description: 二维码生成
 * All Rights Reserved.
 * @version 1.0  2015年11月6日 上午10:13:58  by addison
 */
@RequestMapping("/admin/qr")
@Controller
@AuthClass
public class QRController {
	
	@RequestMapping("/create")
	public String getMapLine() {
		return "qr/create";
	}
	

	

	
}
