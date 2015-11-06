package com.internet.cms.controller.search;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.internet.cms.auth.AuthClass;
import com.internet.cms.controller.search.index.InitIndex;
import com.internet.cms.controller.search.index.PDFIndex;
import com.internet.cms.controller.search.index.WordIndex;

/**
 * 创建文档索引, 包括pdf、word文档
 * @author Administrator
 *
 */
@RequestMapping("/")
@Controller
@AuthClass
public class FileIndexController {
	
	@Autowired
	private InitIndex initIndex;
	@Autowired
	private PDFIndex pdfi;
	@Autowired
	private WordIndex wi;
	
	@RequestMapping("fileIndex")
	public String fileIndexCreate(HttpServletRequest request){
		//获得初始化文档类型
		String flag = request.getParameter("flag");
		if(StringUtils.isEmpty(flag)){
			return "search/fileindex";
		}
		if(StringUtils.equals(flag, "init")){
			try {
				initIndex.init();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("索引初始化成功！");
		}else if("pdf".equals(flag)){
			try {
				pdfi.createPDFIndex(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("PDF索引创建成功！");
		}else if("doc".equals(flag)){
			try {
				wi.createWordIndex(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("WORD索引创建成功！");
		}
		return "search/fileindex";
	}
	
	@RequestMapping("initIndex")
	public String initIndex(HttpServletRequest request){
		//获得初始化文档类型
		String flag = request.getParameter("flag");
		if(StringUtils.isEmpty(flag)){
			return "search/fileindex";
		}
		return null;
	}
	
}
