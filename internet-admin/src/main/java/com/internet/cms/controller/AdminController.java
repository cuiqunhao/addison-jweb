package com.internet.cms.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.auth.AuthMethod;
import com.internet.cms.web.CmsSessionContext;

/**
 * Description: 首页登录
 * All Rights Reserved.
 * @version 1.0  2015年11月6日 上午10:11:53  by addison
 */
@Controller
@AuthClass("login")
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class);
	
	/**
	 * Description: 跳转首页
	 * @Version1.0 2015年11月6日 上午10:12:07 by addison
	 * @return
	 */
	@RequestMapping("/admin")
	@AuthMethod
	public String index() {
		logger.error("AdminController /login/admin");
		return "admin/index";
	}
	
	/**
	 * Description: 登出，跳转到登录页
	 * @Version1.0 2015年11月6日 上午10:12:22 by addison
	 * @param session
	 * @return
	 */
	@AuthMethod
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session) {
		logger.error("AdminController /login/admin/logout");
		CmsSessionContext.removeSession(session);
		session.invalidate();
		return "redirect:/login";
	}
}
