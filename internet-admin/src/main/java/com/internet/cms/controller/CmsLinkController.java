package com.internet.cms.controller;

import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.basic.util.UrlUtil;
import com.internet.cms.model.CmsLink;
import com.internet.cms.service.topic.ICmsLinkService;

@Controller
@AuthClass("login")
@RequestMapping("/admin/cmsLink")
public class CmsLinkController {
	private static final Logger logger = Logger.getLogger(ChannelController.class);
	private ICmsLinkService cmsLinkService;
	
	
	public ICmsLinkService getCmsLinkService() {
		return cmsLinkService;
	}

	@Inject
	public void setCmsLinkService(ICmsLinkService cmsLinkService) {
		this.cmsLinkService = cmsLinkService;
	}


	@RequestMapping("/links")
	public String list(Model model,String type) {
		logger.error("CmsLinkController /admin/cmsLink/links/" + type);
		model.addAttribute("datas", cmsLinkService.findByType(UrlUtil.encodeParam(type)));
		model.addAttribute("types",cmsLinkService.listAllType());
		Map<String,Integer> m = cmsLinkService.getMinAndMaxPos();
		model.addAttribute("min", m.get("min"));
		model.addAttribute("max",m.get("max"));
		return "cmsLink/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		logger.error("CmsLinkController GET /admin/cmsLink/add/");
		model.addAttribute(new CmsLink());
		model.addAttribute("types",cmsLinkService.listAllType());
		return "cmsLink/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated CmsLink cmsLink,BindingResult br) {
		logger.error("CmsLinkController POST /admin/cmsLink/add/");
		if(br.hasFieldErrors()) {
			return "cmsLink/add";
		}
		cmsLinkService.add(cmsLink);
		return "redirect:/admin/cmsLink/links";
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable int id) {
		logger.error("CmsLinkController /admin/cmsLink/delete/" + id);
		cmsLinkService.delete(id);
		return "redirect:/admin/cmsLink/links";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable int id,Model model) {
		logger.error("CmsLinkController /admin/cmsLink/update/" + id);
		model.addAttribute(cmsLinkService.load(id));
		model.addAttribute("types",cmsLinkService.listAllType());
		return "cmsLink/update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,@Validated CmsLink cmsLink,BindingResult br) {
		logger.error("CmsLinkController /admin/cmsLink/update/" + id);
		if(br.hasFieldErrors()) {
			return "cmsLink/update";
		}
		CmsLink tcl = cmsLinkService.load(id);
		tcl.setNewWin(cmsLink.getNewWin());
		tcl.setTitle(cmsLink.getTitle());
		tcl.setType(cmsLink.getType());
		tcl.setUrl(cmsLink.getUrl());
		tcl.setUrlClass(cmsLink.getUrlClass());
		tcl.setUrlId(cmsLink.getUrlId());
		cmsLinkService.update(tcl);
		return "redirect:/admin/cmsLink/links";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String show(@PathVariable int id,Model model) {
		logger.error("CmsLinkController /admin/cmsLink/" + id);
		model.addAttribute(cmsLinkService.load(id));
		return "cmsLink/show";
	}
}
