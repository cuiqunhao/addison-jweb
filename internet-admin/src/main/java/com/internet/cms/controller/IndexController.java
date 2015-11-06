package com.internet.cms.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internet.cms.model.Attachment;
import com.internet.cms.model.Channel;
import com.internet.cms.model.ChannelType;
import com.internet.cms.model.Topic;
import com.internet.cms.page.Pager;
import com.internet.cms.page.SystemContext;
import com.internet.cms.service.topic.IAttachmentService;
import com.internet.cms.service.topic.IChannelService;
import com.internet.cms.service.topic.IKeywordService;
import com.internet.cms.service.topic.ITopicService;
import com.internet.cms.web.BaseInfoUtil;

@Controller
public class IndexController {
	private IChannelService channelService;
	private ITopicService topicService;
	private IAttachmentService attachmentService;
	private IKeywordService keywordService;
	

	public IKeywordService getKeywordService() {
		return keywordService;
	}
	@Inject
	public void setKeywordService(IKeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public IAttachmentService getAttachmentService() {
		return attachmentService;
	}
	
	@Inject
	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	public IChannelService getChannelService() {
		return channelService;
	}
	@Inject
	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}

	public ITopicService getTopicService() {
		return topicService;
	}
	@Inject
	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}

	@RequestMapping({"/","/index"})
	public String index(Model model) {
		model.addAttribute("baseInfo", BaseInfoUtil.getInstacne().read());
		return "index/index";
	}
	
	@RequestMapping("/channel/{cid}")
	public String showChannel(@PathVariable int cid,Model model,HttpServletResponse resp,HttpServletRequest req) throws IOException {
		Channel c = channelService.load(cid);
		//System.out.println(c.getType());
		Channel pc = null;
		String name = c.getShowTypeName();
		if("NAV_CHANNEL".equals(name)) {
			pc = c;
			//如果是导航栏目，需要获取该栏目中的第一个栏目
			c = channelService.loadFirstChannelByNav(c.getId());
		} else {
			pc = channelService.loadFirstChannelByNav(cid);
		}
//		System.out.println(c.getType()==ChannelType.TOPIC_LIST);
//		System.out.println(c.getType());
		if("TOPIC_CONTENT".equals(name)) {
			resp.sendRedirect(req.getContextPath()+"/topic/"+topicService.loadLastedTopicByColumn(cid).getId());
		} else if("TOPIC_IMG".equals(name)){
			SystemContext.setPageSize(16);
			SystemContext.setSort("a.topic.publishDate");
			SystemContext.setOrder("desc");
			Pager<Attachment> atts = attachmentService.findChannelPic(cid);
			model.addAttribute("datas", atts);
		} else if("TOPIC_LIST".equals(name)) {
			model.addAttribute("datas", topicService.find(c.getId(),null,1));
		}
		model.addAttribute("pc", pc);
		model.addAttribute("cs", channelService.listUseChannelByParent(pc != null ? pc.getId():null));
		model.addAttribute("channel", c);
		if("TOPIC_LIST".equals(name)) {
			return "index/channel";
		} else {
			return "index/channel_pic";
		}
	}
	
	@RequestMapping("/topic/{tid}")
	public String showTopic(@PathVariable int tid,Model model) {
		Topic t = topicService.load(tid);
		String keywords = t.getKeyword();
		model.addAttribute("topic", t);
		if(keywords==null||"".equals(keywords.trim())||"\\|".equals(keywords.trim())) {
			model.addAttribute("hasKey", false);
		} else {
			String[] kws = keywords.split("\\|");
			model.addAttribute("hasKey", true);
			model.addAttribute("kws",kws);
		}
		List<Attachment> atts = attachmentService.listAttachByTopic(tid);
		if(atts.size()>0) {
			model.addAttribute("hasAtts", true);
			model.addAttribute("atts", atts);
		} else {
			model.addAttribute("hasAtts",false);
		}
		return "index/topic";
	}
	
	@RequestMapping("/search/{con}")
	public String search(@PathVariable String con,Model model) {
		SystemContext.setOrder("asc");
		SystemContext.setSort("c.orders");
		model.addAttribute("cs", channelService.listChannelByType(ChannelType.NAV_CHANNEL));
		SystemContext.setOrder("desc");
		SystemContext.setSort("t.publishDate");
		Pager<Topic> topics = topicService.searchTopic(con);
		emp(topics,con);
		model.addAttribute("datas", topics);
		model.addAttribute("con", con);
		return "index/search";
	}
	
	@RequestMapping("/keyword/{con}")
	public String keyword(@PathVariable String con,Model model) {
		model.addAttribute("kws", keywordService.getMaxTimesKeyword(9));
		SystemContext.setOrder("desc");
		SystemContext.setSort("t.publishDate");
		Pager<Topic> topics = topicService.searchTopicByKeyword(con);
		emp(topics,con);
		model.addAttribute("datas", topics);
		model.addAttribute("con", con);
		return "index/keyword";
	}

	private void emp(Pager<Topic> topics, String con) {
		for(Topic t:topics.getDatas()) {
			if(t.getTitle().contains(con)) {
				String tt = t.getTitle().replaceAll(con, "<span class='emp'>"+con+"</span>");
				t.setTitle(tt);
			}
		}
	}
}
