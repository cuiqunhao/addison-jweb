package com.internet.cms.controller.search;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.controller.search.result.AdvanceGetSearchResult;
import com.internet.cms.model.search.DocumentEntity;
import com.internet.cms.utils.search.PageNumBean;

@RequestMapping("/")
@Controller
public class AdvanceSearchController {
	/**
	 * 
	 * @throws Exception 
	 */
	@RequestMapping("advanceSearchIndex")
	public ModelAndView advanceSearchIndex(HttpServletRequest request) throws Exception {
		
		int pagetype = Integer.parseInt(request.getParameter("pagetype"));
		String filetype = request.getParameter("filetype");
		String qtype = request.getParameter("qtype");
		String fieldname = request.getParameter("fieldname");
		int totalpage = Integer.parseInt(request.getParameter("totalpage"));
		fieldname = new String(fieldname.getBytes("ISO-8859-1"), "UTF-8");
		
		int currentNum = 1;
		AdvanceGetSearchResult gsr = new AdvanceGetSearchResult(pagetype, filetype, qtype, fieldname, totalpage);
		List<DocumentEntity> list = gsr.getResult(1);
		int recordCount = gsr.getScoreDocs().length;
		System.out.println("----advance-- ---" + list.size() + "-------------");
		PageNumBean pageBean = null;
		System.out.println("------ " + recordCount);
		pageBean = (PageNumBean) request.getAttribute("pageNumBean");
		if (pageBean == null) {
			pageBean = new PageNumBean(1, recordCount, pagetype, pagetype);
			request.setAttribute("pageNumBean", pageBean);
		}

		Integer downPageNum = currentNum + 1;
		if (downPageNum > pageBean.getPageCount()) downPageNum = null;
		Integer upPageNum = currentNum - 1;
		if (upPageNum == 0) upPageNum = null;
		
		pageBean.setUpPageNum(upPageNum);
		pageBean.setDownPageNum(downPageNum);
		pageBean.setCurrentNum(currentNum);
		
		request.setAttribute("pageNumBean", pageBean);
		request.setAttribute("sk", fieldname);
		request.setAttribute("sk1", URLEncoder.encode(fieldname, "UTF-8"));
		
		return new ModelAndView("search/result").addObject("pageUrl", "searchIndex?page=")
				.addObject("rsize", recordCount).addObject("rlist", list);
	}
}
