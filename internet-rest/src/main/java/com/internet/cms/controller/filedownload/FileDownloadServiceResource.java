package com.internet.cms.controller.filedownload;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.wink.common.annotations.Workspace;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.internet.cms.basic.util.FileUtil;
import com.internet.cms.basic.util.filedownload.DownloadManager;





@Resource
@Path("/filedownload")
@Service
@Workspace(collectionTitle = "filedownloadervice", workspaceTitle = "FileDownload Service Resource")
public class FileDownloadServiceResource {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	/**
	 * 多线程文件下载
	 * @param path 远程文件地址
	 * @return
	 * @throws IOException
	 * filedownload/filedownload?path=http://localhost:8080/sz/jdk.rar&size=3&localpath=d:\
	 */
	@GET
	@Path("/filedownload")
	@Produces({ MediaType.APPLICATION_JSON })
	public JSONObject fileupload(@QueryParam("localpath") String localpath,@QueryParam("path") String path,@QueryParam("size") int size) throws IOException {
		JSONObject result = new JSONObject();
		result.put("result", 0);
		try {
			String fileName = FileUtil.getFilename(path); 
			DownloadManager downloadManager = new DownloadManager(localpath + fileName , 5 , path);  
			downloadManager.action();  

			result.put("result", 1);
		} catch (Exception e) {
			return result;
		}
		return result;
	}
	
	
}
