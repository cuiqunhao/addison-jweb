package com.internet.cms.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.Group;
import com.internet.cms.page.Pager;
import com.internet.cms.service.user.IGroupService;
import com.internet.cms.service.user.IUserService;

@RequestMapping("/admin/group")
@Controller
@AuthClass
public class GroupController {
	private IGroupService groupService;
	private IUserService userService;
	

	public IUserService getUserService() {
		return userService;
	}

	@Inject
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IGroupService getGroupService() {
		return groupService;
	}

	@Inject
	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * 获取群组列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/groups")
	public String list(Model model) {
		//获取分页群组列表信息
		Pager<Group> datas = groupService.findGroup();
		model.addAttribute("datas",datas);
		return "group/list";
	}
	
	/**
	 * 跳转到添加界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute(new Group());
		return "group/add";
	}
	
	/**
	 * 添加群组
	 * @param group
	 * @param br
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated Group group,BindingResult br) {
		if(br.hasErrors()) {
			return "group/add";
		}
		//添加群组
		groupService.add(group);
		return "redirect:/admin/group/groups";
	}
	
	/**
	 * 跳转到更新界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable int id,Model model) {
		model.addAttribute(groupService.load(id));
		return "group/update";
	}
	
	/**
	 * 更新群组信息
	 * @param id
	 * @param group
	 * @param br
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,@Validated Group group,BindingResult br) {
		if(br.hasErrors()) {
			return "group/update";
		}
		//根据群组id获取群组信息
		Group ug = groupService.load(id);
		ug.setDescr(group.getDescr());
		ug.setName(group.getName());
		//更新群组信息
		groupService.update(ug);
		return "redirect:/admin/group/groups";
	}
	
	/**
	 * 删除群组信息(在删除之前先判断群组中是否有用户信息)
	 * @param id 群组id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		//删除群组信息(在删除之前先判断群组中是否有用户信息)
		groupService.delete(id);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping("/{id}")
	public String show(@PathVariable int id,Model model) {
		model.addAttribute(groupService.load(id));
		model.addAttribute("us", userService.listUsersByGid(id));
		return "group/show";
	}
	
	/**
	 * 清除群组中的用户
	 * @param id 群组id
	 * @return
	 */
	@RequestMapping("/clearUsers/{id}")
	public String clearGroupUsers(@PathVariable int id) {
		groupService.deleteGroupUsers(id);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping("/listChannels/{gid}")
	public String listChannels(@PathVariable int gid,Model model) {
		model.addAttribute(groupService.load(gid));
		return "/group/listChannel";
	}
	
	@RequestMapping("/groupTree/{gid}")
	public @ResponseBody List<ChannelTree> groupTree(@PathVariable Integer gid) {
		return groupService.generateGroupChannelTree(gid);
	}
	
	@RequestMapping("/setChannels/{gid}")
	public String setChannels(@PathVariable int gid,Model model) {
		model.addAttribute(groupService.load(gid));
		model.addAttribute("cids",groupService.listGroupChannelIds(gid));
		return "/group/setChannel";
	}
}
