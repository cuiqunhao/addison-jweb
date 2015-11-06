package com.internet.cms.controller.search;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.controller.search.result.GetSearchResult;
import com.internet.cms.model.search.DocumentEntity;
import com.internet.cms.utils.search.PageNumBean;

/**
 * 索引文档搜索
 * @author Administrator
 *
 */
@RequestMapping("/")
@Controller
public class SearchController {
	
	/**
	 * 文档搜索
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("searchIndex")
	public ModelAndView searchIndex(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		String fieldname = request.getParameter("fieldname");
		if (fieldname == null) {
			fieldname = request.getParameter("sk");// tomcat默认采用ISO-8859-1方式获取URI中的参数
			//System.out.println(fieldname);
			// fieldname=new String(fieldname.getBytes("ISO-8859-1"),"utf-8");
			// //转换为utf-8
			//System.out.println("-------1  " + fieldname + " ------ ");
		}
		fieldname = new String(fieldname.getBytes("ISO-8859-1"), "UTF-8");
		if (page == null) page = "1";
		int currentNum = Integer.valueOf(page);
		//System.out.println("------page---" + currentNum + "-------------");
		GetSearchResult gsr = new GetSearchResult();
		List<DocumentEntity> list = gsr.getResult(fieldname, currentNum, 200);
		int recordCount = gsr.getScoreDocs(fieldname, 200).length;// 得到总的记录数
		//System.out.println("------page---" + list.size() + "-------------");
		PageNumBean pageBean = null;
		//System.out.println("------page---" + recordCount);
		pageBean = (PageNumBean) request.getAttribute("pageNumBean");
		
		if (pageBean == null) {
			pageBean = new PageNumBean(1, recordCount, GetSearchResult.eachePageNum, 5);
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
		
		return new ModelAndView("search/result").addObject("pageUrl", "searchIndex.do?page=")
				.addObject("rsize", recordCount).addObject("rlist", list);
	}
}
