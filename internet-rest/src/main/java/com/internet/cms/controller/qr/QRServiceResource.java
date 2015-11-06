package com.internet.cms.controller.qr;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.wink.common.annotations.Workspace;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.internet.cms.constants.AttachmentConstants;
import com.internet.cms.page.SystemContext;
import com.internet.cms.utils.MatrixToImageWriter;

/**
 * 生成二维码
 * @author Administrator
 *
 */
@Resource
@Path("/qr")
@Service
@Workspace(collectionTitle = "qrService", workspaceTitle = "QR Service Resource")
public class QRServiceResource {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	/**
	 * 生成二维码
	 * @param obj 二维码基本信息
	 * @return 返回二维码基本信息及图片信息
	 * @throws IOException 异常
	 * @throws WriterException 异常
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/create")
	@Produces({ MediaType.APPLICATION_JSON })
	public JSONObject createQR(JSONObject obj) throws IOException, WriterException {
		
		//扫描的url
		String url = obj.optString("url"); 
		//生成的二维码宽度
	    int width = obj.optInt("width"); 
	    //生成的二维码高度
	    int height = obj.optInt("height"); 
	    //生成的二维码的图片格式 
	    String suffix = obj.optString("suffix");
	    
	    Hashtable hints = new Hashtable(); 
	    //内容所使用编码 
	    hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
	    BitMatrix bitMatrix = new MultiFormatWriter().encode(url,BarcodeFormat.QR_CODE, width, height, hints); 
	    //生成二维码
	    String name = String.valueOf(new Date().getTime())+"."+suffix;
	    //进行文件的存储
  		String realPath = SystemContext.getRealPath();
  		//附件上传的路径
  		String path = realPath+AttachmentConstants.UPLOADPATH;
	    File outputFile = new File(path + name); 
	    MatrixToImageWriter.writeToFile(bitMatrix, suffix, outputFile); 
	    //返回结果
	    JSONObject result = new JSONObject();
	    result.put("name", name);
	    result.put("url", url);
	    result.put("weight", width);
	    result.put("height", height);
	    result.put("suffix", suffix);
	    return result;
		
	}

}
