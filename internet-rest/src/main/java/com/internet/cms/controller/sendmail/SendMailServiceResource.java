package com.internet.cms.controller.sendmail;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.apache.wink.common.annotations.Workspace;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.internet.cms.basic.util.mail.MailUtil;
import com.internet.cms.basic.util.mail.SimpleMailSender;
import com.internet.cms.model.BaseInfo;


@Resource
@Path("/sendmail")
@Service
@Workspace(collectionTitle = "sendmailService", workspaceTitle = "Sendmail Service Resource")
public class SendMailServiceResource {
	
	private static final Logger logger = Logger.getLogger(SendMailServiceResource.class);
	
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	
	/**
	 * 发送邮件
	 * @param obj var com = {"email":"3121026417@qq.com","type":"1","title":"测试邮件Rest服务","content":"测试邮件Rest服务"};
	 * @return
	 * @throws IOException
	 */
	@POST
	@Path("/send")
	@Produces({ MediaType.APPLICATION_JSON })
	public JSONObject fileupload(JSONObject obj) throws IOException {
		JSONObject result = new JSONObject();
		
		result.put("result", 1);
		//发送状态
		String status = "1";
		//统计发送成功条数
		int successCount = 1;	
		//邮件总数
		int count = 1;			
		
		//对方邮箱
		String sendTo = obj.optString("email");	
		//标题
		String title = obj.optString("title");		
		//内容
		String content = obj.optString("content");
		//类型
		String type = obj.optString("type");						
		BaseInfo baseinfo = (BaseInfo)request.getSession().getServletContext().getAttribute("baseInfo");
		if(null != baseinfo){
			sendTo = sendTo.replaceAll("；", ";");
			sendTo = sendTo.replaceAll(" ", "");
			String[] sendToMails = sendTo.split(";");
			count = sendToMails.length;
			try {
				for(int i=1;i<sendToMails.length;i++){
					//邮箱格式不对就跳过
					if(MailUtil.checkEmail(sendToMails[i])){		
						//调用发送邮件函数
						SimpleMailSender.sendEmail(baseinfo.getSmtp(), baseinfo.getPort(), baseinfo.getBusinessMail(), baseinfo.getMailPwd(), sendToMails[i], title, content, type);
						successCount++;
					}else{
						continue;
					}
				}
				status = "1";
			} catch (Exception e) {
				logger.error("邮件发送失败",e);
				status = "error";
			} 
		}else{
			status = "0";
		}
		//发送状态
		result.put("status", status);
		//成功数
		result.put("count", successCount);	
		//失败数
		result.put("failureCount", count - successCount);				
		return result;
	}
	

}
