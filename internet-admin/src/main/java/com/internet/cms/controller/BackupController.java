package com.internet.cms.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.basic.util.BackupFileUtil;
import com.internet.cms.page.SystemContext;
import com.internet.cms.service.IIndexService;

@AuthClass
@Controller
@RequestMapping("/admin")
public class BackupController {
	private IIndexService indexService;
	
	
	public IIndexService getIndexService() {
		return indexService;
	}
	@Inject
	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}

	@RequestMapping(value="/backup/add",method=RequestMethod.GET)
	public String backup() {
		return "backup/add";
	}
	
	@RequestMapping(value="/backup/add",method=RequestMethod.POST)
	public String backup(String backupFilename) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		bfu.backup(backupFilename);
		return "redirect:/admin/backups";
	}
	
	@RequestMapping(value="/backups")
	public String list(Model model) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		model.addAttribute("backups",bfu.listBackups());
		return "backup/list";
	}
	
	@RequestMapping("delete/{name}")
	public String delete(@PathVariable String name,String type) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		bfu.delete(name+"."+type);
		return "redirect:/admin/backups";
	}
	
	@RequestMapping("resume/{name}")
	public String resume(@PathVariable String name,String type) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		bfu.resume(name+"."+type);
		indexService.generateTop();
		indexService.generateBody();
		indexService.generateBottom();
		return "redirect:/admin/backups";
	}
	
}
