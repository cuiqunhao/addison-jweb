package com.internet.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.basic.util.EnumUtils;
import com.internet.cms.dto.AjaxObj;
import com.internet.cms.dto.TreeDto;
import com.internet.cms.model.Channel;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.ChannelType;
import com.internet.cms.page.Pager;
import com.internet.cms.service.IIndexService;
import com.internet.cms.service.topic.IChannelService;

/**
 * Description: 栏目管理
 * All Rights Reserved.
 * @version 1.0  2015年11月6日 上午10:55:08  by addison
 */
@RequestMapping("/admin/channel")
@Controller
@AuthClass
public class ChannelController {
	private static final Logger logger = Logger.getLogger(ChannelController.class);
	
	private IChannelService channelService;
	private IIndexService indexService;

	public IIndexService getIndexService() {
		return indexService;
	}
	
	@Inject
	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}
	public IChannelService getChannelService() {
		return channelService;
	}
	@Inject
	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}


	/**
	 * 跳转到树形页面(页面中有加载栏目树的方法)
	 * @param model
	 * @return
	 */
	@RequestMapping("/channels")
	public String list(Model model) {
		logger.error("ChannelController /admin/channel/channels");
		return "channel/list";
	}
	
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return
	 */
	@RequestMapping("/treeAll")
	public @ResponseBody List<ChannelTree> tree() {
		logger.error("ChannelController /admin/channel/treeAll");
		//把所有的栏目获取并生成一颗完整的树
		return channelService.generateTree();
	}
	
	/**
	 * 根据父id获取所有的子栏目(栏目列表)
	 * @param pid
	 * @param refresh
	 * @param model
	 * @return
	 */
	@RequestMapping("/channels/{pid}")
	public String listChild(@PathVariable Integer pid,@Param Integer refresh,Model model) {
		logger.error("ChannelController /admin/channel/channels/"+pid);
		Channel pc = null;
		if(null == refresh) {
			model.addAttribute("refresh",0);
		} else {
			model.addAttribute("refresh",1);
		}
		//设置跟节点
		if(null == pid||pid<=0) {
			pc = new Channel();
			pc.setName(Channel.ROOT_NAME);
			pc.setId(Channel.ROOT_ID);
		} else{
			//根据栏目id获取当前栏目的信息
			pc = channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		//根据父id获取所有的子栏目
		Pager<Channel> channels  = channelService.listChannelByParent(pid);
		model.addAttribute("datas",channels);
		return "channel/list_child";
	}
	
	@RequestMapping(value="/treeAs",method=RequestMethod.POST)
	public @ResponseBody List<TreeDto> tree(@Param Integer pid) {
		logger.error("ChannelController /admin/channel/treeAs" + pid);
		List<TreeDto> tds = new ArrayList<TreeDto>();
		if(pid==null||pid<=0) {
			tds.add(new TreeDto(0,"网站根栏目",1));
			return tds;
		}
		List<ChannelTree> cts = channelService.generateTreeByParent(pid);
		for(ChannelTree ct:cts) {
			tds.add(new TreeDto(ct.getId(),ct.getName(),1));
		}
		return tds;
	}
	
	private void initAdd(Model model,Integer pid) {
		if(pid==null) pid = 0;
		Channel pc = null;
		if(pid==0) {
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		} else {
			//根据栏目id获取当前栏目的信息
			pc = channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
	}
	
	/**
	 * 跳转到添加界面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.GET)
	public String add(@PathVariable Integer pid,Model model) {
		logger.error("ChannelController /admin/channel/add/" + pid);
		initAdd(model, pid);
		model.addAttribute(new Channel());
		return "channel/add";
	}
	
	/**
	 * 执行添加操作
	 * @param pid
	 * @param channel
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.POST)
	public String add(@PathVariable Integer pid,Channel channel,BindingResult br,Model model) {
		logger.error("ChannelController /admin/channel/add/" + pid);
		if(br.hasErrors()) {
			initAdd(model, pid);
			return "channel/add";
		}
		//添加栏目
		channelService.add(channel, pid);
		//重新生成顶部导航(获取所有的导航栏目，栏目的状态必须为已经启用)
		indexService.generateTop();
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	/**
	 * 跳转到栏目更新界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model) {
		logger.error("ChannelController /admin/channel/update/" + id);
		Channel c = channelService.load(id);
		model.addAttribute("channel", c);
		Channel pc = null;
		if(c.getParent()==null) {
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		} else {
			pc = c.getParent();
		}
		model.addAttribute("pc",pc);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
		return "channel/update";
	}
	
	/**
	 * 更新栏目
	 * @param id
	 * @param channel
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,Channel channel,BindingResult br,Model model) {
		logger.error("ChannelController /admin/channel/update/" + id);
		if(br.hasErrors()) {
			model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));	
			return "channel/update";
		}
		//根据栏目id获取当前栏目的信息
		Channel tc = channelService.load(id);
		int pid = 0;
		if(tc.getParent()!=null) pid = tc.getParent().getId();
		tc.setCustomLink(channel.getCustomLink());
		tc.setCustomLinkUrl(channel.getCustomLinkUrl());
		tc.setIsIndex(channel.getIsIndex());
		tc.setIsTopNav(channel.getIsTopNav());
		tc.setName(channel.getName());
		tc.setRecommend(channel.getRecommend());
		tc.setStatus(channel.getStatus());
		tc.setNavOrder(channel.getNavOrder());
		tc.setShowTypeName(channel.getShowTypeName());
		//更新栏目
		channelService.update(tc);
		indexService.generateTop();
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid,@PathVariable Integer id,Model model) {
		logger.error("ChannelController /admin/channel/delete/" + pid + "/" + id);
		channelService.delete(id);
		indexService.generateTop();
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/channels/updateSort")
	public @ResponseBody AjaxObj updateSort(@Param Integer[] ids) {
		logger.error("ChannelController /admin/channel/channels/updateSort" + ids);
		try {
			channelService.updateSort(ids);
			indexService.generateTop();
		} catch (Exception e) {
			return new AjaxObj(0,e.getMessage());
		}
		return new AjaxObj(1);
	}
}
