package com.internet.cms.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.model.BaseInfo;
import com.internet.cms.page.SystemContext;
import com.internet.cms.service.IIndexService;
import com.internet.cms.service.topic.IAttachmentService;
import com.internet.cms.service.topic.IIndexPicService;
import com.internet.cms.web.BaseInfoUtil;

@RequestMapping("/admin/system")
@Controller
@AuthClass
public class SystemController {
	private IAttachmentService attachmentService;
	private IIndexPicService indexPicService;
	private IIndexService indexService;

	public IIndexService getIndexService() {
		return indexService;
	}

	@Inject
	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}

	public IAttachmentService getAttachmentService() {
		return attachmentService;
	}

	@Inject
	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public IIndexPicService getIndexPicService() {
		return indexPicService;
	}

	@Inject
	public void setIndexPicService(IIndexPicService indexPicService) {
		this.indexPicService = indexPicService;
	}

	@RequestMapping("/baseinfo")
	public String showBaseInfo() {
		return "system/showBaseInfo";
	}

	@RequestMapping(value = "/baseinfo/update", method = RequestMethod.GET)
	public String updateBaseInfo(HttpSession session, Model model) {
		model.addAttribute("baseInfo", session.getServletContext()
				.getAttribute("baseInfo"));
		return "system/updateBaseInfo";
	}

	@RequestMapping(value = "/baseinfo/update", method = RequestMethod.POST)
	public String updateBaseInfo(@Validated BaseInfo baseInfo,
			BindingResult br, HttpSession session) {
		if (br.hasErrors()) {
			return "system/updateBaseInfo";
		}
		BaseInfo bi = BaseInfoUtil.getInstacne().write(baseInfo);
		session.getServletContext().setAttribute("baseInfo", bi);
		indexService.generateBottom();
		indexService.generateTop();
		return "redirect:/admin/system/baseinfo";
	}

	@RequestMapping("/cleans")
	public String listCleans(Model model) {
		model.addAttribute("attNums",
				attachmentService.findNoUseAttachmentNum());
		model.addAttribute("indexPics",
				listNoUseIndexPicNum(indexPicService.listAllIndexPicName()));
		return "system/cleans";
	}

	private File[] listPicFile() {
		String path = SystemContext.getRealPath();
		File f = new File(path + "/resources/indexPic");
		File[] fs = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return false;
				return true;
			}
		});
		return fs;
	}

	@RequestMapping("/cleanList/{name}")
	public String cleanList(@PathVariable String name, Model model) {
		if (name.equals("atts")) {
			model.addAttribute("datas", attachmentService.findNoUseAttachment());
			return "system/cleanAtts";
		} else if (name.equals("pics")) {
			model.addAttribute("datas",
					listNoUseIndexPic(indexPicService.listAllIndexPicName()));
			return "system/cleanPics";
		}
		return "";
	}

	@RequestMapping("/clean/{name}")
	public String clean(@PathVariable String name, Model model)
			throws IOException {
		if (name.equals("atts")) {
			attachmentService.clearNoUseAttachment();
		} else if (name.equals("pics")) {
			indexPicService
					.cleanNoUseIndexPic(listNoUseIndexPic(indexPicService
							.listAllIndexPicName()));
		}
		return "redirect:/admin/system/cleans";
	}

	/**
	 * 获取没有使用的首页图片数量
	 * 
	 * @param pics
	 * @return
	 */
	private int listNoUseIndexPicNum(List<String> pics) {
		File[] fs = listPicFile();
		int count = 0;
		for (File file : fs) {
			if (!pics.contains(file.getName()))
				count++;
		}
		return count;
	}

	/**
	 * 获取没有使用的首页图片列表
	 * 
	 * @param pics
	 * @return
	 */
	private List<String> listNoUseIndexPic(List<String> pics) {
		File[] fs = listPicFile();
		List<String> npics = new ArrayList<String>();
		for (File f : fs) {
			if (!pics.contains(f.getName()))
				npics.add(f.getName());
		}
		return npics;
	}
}
