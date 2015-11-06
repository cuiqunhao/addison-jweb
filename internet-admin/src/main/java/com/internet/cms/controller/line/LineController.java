package com.internet.cms.controller.line;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;

/**
 * Description: 地图路线管理
 * All Rights Reserved.
 * @version 1.0  2015年11月6日 上午11:22:41  by addison
 */
@RequestMapping("/admin/line")
@Controller
@AuthClass
public class LineController {
	
	@RequestMapping("/map")
	public String getMapLine() {
		return "line/line";
	}
	

	

	
}
