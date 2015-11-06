package com.internet.cms.controller.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.wink.common.annotations.Workspace;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.internet.cms.basic.util.fileupload.MonitoredDiskFileItemFactory;
import com.internet.cms.basic.util.fileupload.UploadListener;

@Resource
@Path("/fileupload")
@Service
@Workspace(collectionTitle = "fileuploadService", workspaceTitle = "Fileupload Service Resource")
public class FileUploadServiceResource {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	/**
	 * 文件上传
	 * @return 0：失败 other：上传文件的基本信息
	 * @throws ApplicationException 公共异常
	 * @throws IOException IO异常
	 */
	@POST
	@Path("/fileupload")
	@Produces({ MediaType.APPLICATION_JSON })
	public JSONObject fileupload() throws IOException {
		JSONObject result = new JSONObject();
		result.put("result", 0);
		HttpSession session = request.getSession();
		return doFileUpload(session, request, response);
	}
	
	private JSONObject doFileUpload(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		result.put("result", 0);
		try {
			//创建UploadListener对象
			UploadListener listener = new UploadListener(request.getContentLength());
			listener.start();//启动监听状态
			//将监听器对象的状态保存在Session中
			session.setAttribute("FILE_UPLOAD_STATS", listener.getFileUploadStats());
			//创建MonitoredDiskFileItemFactory对象
			FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
			//通过该工厂对象创建ServletFileUpload对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//将转化请求保存到list对象中
			List<FileItem> items = upload.parseRequest(request);
			//停止使用监听器
			listener.done();
			//循环list中的对象
			for (Iterator<FileItem> i = items.iterator(); i.hasNext();) {
				FileItem fileItem = (FileItem) i.next();
				if (!fileItem.isFormField()) {//如果该FileItem不是表单域
					processUploadedFile(fileItem);//调用processUploadedFile方法,将数据保存到文件中
					fileItem.delete();//内存中删除该数据流
				}
				
				buildFileUploadJson(result, fileItem);
			}
		} catch (Exception e) {
			return result;
		}
		return result;
	}

	private void buildFileUploadJson(JSONObject result, FileItem fileItem) {
		//后缀名
		String suffix = FilenameUtils.getExtension(fileItem.getName());
		String newName = String.valueOf(new Date().getTime())+"."+suffix;
		String oldName = FilenameUtils.getBaseName(fileItem.getName());
		long size = fileItem.getSize();
		String contentType = fileItem.getContentType();
		result.put("newname", newName);
		result.put("oldname", oldName);
		result.put("contenttype", contentType);
		result.put("suffix", suffix);
		result.put("size", size);
		result.put("result", 1);
	}
	
	public void processUploadedFile(FileItem item) throws IOException {
		//获得上传文件的文件名
		String fileName = item.getName().substring(item.getName().lastIndexOf("\\") + 1);
		
		//创建File对象,将上传得文件保存到服务器目录
		String path = "/sz_file";
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(path, fileName);
		InputStream in;
		try {
			in = item.getInputStream();//获得输入数据流文件
			//将该数据流写入到指定文件中
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[4096]; // To hold file contents
			int bytes_read;
			while ((bytes_read = in.read(buffer)) != -1) // Read until EOF
			{
				out.write(buffer, 0, bytes_read);
			}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					;
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					;
				}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
