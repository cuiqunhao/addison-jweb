package com.internet.cms.controller.log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.basic.util.DataUtil;
import com.internet.cms.basic.util.string.StringEncodeUtils;


/**
 * Description: 日志管理
 * All Rights Reserved.
 * @version 1.0  2015年11月6日 上午10:13:44  by addison
 */
@RequestMapping("/admin/log")
@Controller
@AuthClass
public class LogController {
	
	private Map<String,String> logFileMap;
	
	@RequestMapping("/system/list")
	public String getSystemLogList(Model model) {
		URL url = getClass().getClassLoader().getResource("/");
		String serverRoot = new File(url.getPath()).getParentFile().getParentFile().getParentFile().getParent() + File.separator;
		String logFilePath = serverRoot+"logs/internet/";
		File file = new File(logFilePath);
	    
		FileFilter filter = new FileFilter() {                            
			public boolean accept(File file) {                          
				String tmp = file.getName().toLowerCase();                        
				if (tmp.startsWith("accesslogfile.log")) {                            
					return true;                        
				}
				if (tmp.startsWith("errorfile.log")) {                            
					return true;                        
				}
				if (tmp.startsWith("logfile.log")) {                            
					return true;                        
				}
				if (tmp.startsWith("systemfile.log")) {                            
					return true;                        
				}
				return false;             
			}       
		};  
		//map有序
		logFileMap = new LinkedHashMap<String, String>();
		File[] files = file.listFiles(filter);
		for(File f : files){
			//文件最后修改日期+文件路径
			String filePath = f.getPath();
			int lastIndexOf = filePath.indexOf(".");
			String fileCreateDate = filePath.substring(lastIndexOf+1,filePath.length());
			String fileLastModifiedDate = DataUtil.date2Str(new Date(file.lastModified()), "yyyy-MM-dd");
			if(fileCreateDate.equals("log")){
				fileCreateDate = fileLastModifiedDate;
			}
		    logFileMap.put(filePath, fileLastModifiedDate);
		}
		
		model.addAttribute("logFileMap", logFileMap);
		
		return "log/listSystem";
	}
	
	@RequestMapping(value="/system/download",method=RequestMethod.GET)
	public String downLoad(@Param String logPath,Model model,HttpServletResponse response) {
		File file = new File(logPath);
		String fileName = "AccessLogFile.log";
		download(file, fileName,response);
		return null;
	}
	
	@RequestMapping(value="/system/detail",method=RequestMethod.GET)
	public String detail(@Param String logPath,Model model,HttpServletRequest request) {
		request.setAttribute("logPath", logPath);
		return "log/detail";
	}
	
	/**
	 * 下载附件
	 * @param fileName 附件名
	 */
	public void download(File file,String fileName,HttpServletResponse response){
		InputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		download(input, fileName,response);
	}
	
	/**
	 * 下载附件
	 * @param input 附件流
	 * @param fileName 附件名
	 */
	public void download(InputStream input,String fileName,HttpServletResponse response){
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-Type","application/octet-stream; charset=GBK");//
		response.setHeader("Content-Disposition","attachment; filename=" + StringEncodeUtils.gbkToIso88591(fileName));
		
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			IOUtils.copyLarge(input, output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	
}
