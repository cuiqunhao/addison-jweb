package com.internet.cms.controller.task;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.wink.common.annotations.Workspace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.auth.AuthMethod;
import com.internet.cms.basic.util.DataUtil;
import com.internet.cms.basic.util.Utils;
import com.internet.cms.model.User;
import com.internet.cms.model.task.Task;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;
import com.internet.cms.service.task.ITaskService;

@Resource
@Path("/task")
@Service
@Workspace(collectionTitle="taskService",workspaceTitle="Task Service Resource")
public class TaskServiceResource{
	 @Context 
	 HttpServletRequest request;  

	private ITaskService taskService;
	public ITaskService getUserService() {
		return taskService;
	}

	@Autowired
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * 添加我的待办
	 * @param jsonObj 我的待办json数据
	 * @return 0:失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/task")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addTask(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			Task task = new Task();
			buildJSONObjectToTask(task,jsonObj);

			boolean success = taskService.addTask(task);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除我的待办
	 * @param id 分类id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/task/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteTask(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String code = "";
			Task task = new Task();
			boolean success = taskService.deleteTask(task,code);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新我的待办
	 * @param jsonObj 我的待办json数据
	 * @return 0：失败 1：成功
	 * @throws UnsupportedEncodingException 
	 */
	@POST
	@Path("/update/task")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateTask(JSONObject jsonObj) throws UnsupportedEncodingException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			
			Task task = new Task();
			buildJSONObjectToTask(task,jsonObj);
			boolean success = taskService.updateTask(task);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询我创建的待办应用列表(reserve2)
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 我创建待办应用列表json
	 */
	@GET
	@Path("/list/applicant/appname")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findApplicantAppList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("applist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			Task task = new Task();
			task.setReserve2(String.valueOf(user.getId()));
			Pager<Task> taskPage = taskService.findApplicantAppList(task,pb);
			if(null != taskPage && taskPage.getTotal() > 0){
				result.put("count",taskPage.getTotal());
				buildAppNameListToJSONAarray(taskPage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 查询我的申请待办应用列表(reserve1)
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 我的申请待办应用列表json
	 */
	@GET
	@Path("/list/handler/appname")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findHandlerAppList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("applist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			Task task = new Task();
			task.setReserve1(String.valueOf(user.getId()));
			Pager<Task> taskPage = taskService.findHandlerAppList(task,pb);
			if(null != taskPage && taskPage.getTotal() > 0){
				result.put("count",taskPage.getTotal());
				buildAppNameListToJSONAarray(taskPage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 根据应用名查询我创建的待办任务列表(reserve2)
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 我创建待办任务列表json
	 */
	@GET
	@Path("/list/applicant/task/{appname}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findApplicantTaskList(@PathParam("appname") String appname, @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("tasklist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			Task task = new Task();
			task.setReserve2(String.valueOf(user.getId()));
			task.setAppname(appname);
			
			Pager<Task> taskPage = taskService.findApplicantTaskList(task,pb);
			if(null != taskPage && taskPage.getTotal() > 0){
				result.put("count",taskPage.getTotal());
				buildTaskListToJSONAarray(taskPage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 根据应用名查询我创建的待办任务列表(reserve2)
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 我创建待办任务列表json
	 */
	@GET
	@Path("/list/handler/task/{appname}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findHandlerTaskList(@QueryParam("appname") String appname, @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("tasklist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			Task task = new Task();
			task.setReserve1(String.valueOf(user.getId()));
			task.setAppname(appname);
			
			Pager<Task> taskPage = taskService.findHandlerTaskList(task,pb);
			if(null != taskPage && taskPage.getTotal() > 0){
				result.put("count",taskPage.getTotal());
				buildTaskListToJSONAarray(taskPage,jsonArray);
			}
		}
		return result;
	}
	
	private void buildAppNameListToJSONAarray(Pager<Task> taskPage, JSONArray result) {
		if(null != taskPage){
			for(Task task : taskPage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("appname", task.getAppname());
				jsObj.put("count", task.getReserve10());
				result.put(jsObj);
			}
		}
	}
	
	private void buildTaskListToJSONAarray(Pager<Task> taskPage, JSONArray result) {
		if(null != taskPage){
			for(Task task : taskPage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("applicant", task.getApplicant());
				jsObj.put("appname", task.getAppname());
				jsObj.put("appurl", task.getAppurl());
				jsObj.put("handler", task.getHandler());
				jsObj.put("taskdesc", task.getTaskdesc());
				jsObj.put("taskurl", task.getTaskurl());
				jsObj.put("type", task.getType());
				jsObj.put("time", task.getTime());
				jsObj.put("deleteflag", task.getDeleteflag());
				jsObj.put("accessmobile", task.getAccessmobile());
				jsObj.put("createtime", task.getCreatetime());
				jsObj.put("reserve1", task.getReserve1());
				jsObj.put("reserve2", task.getReserve2());
				jsObj.put("reserve3", task.getReserve3());
				jsObj.put("reserve4", task.getReserve4());
				jsObj.put("reserve5", task.getReserve5());
				jsObj.put("reserve6", task.getReserve6());
				jsObj.put("reserve7", task.getReserve7());
				jsObj.put("reserve8", task.getReserve8());
				jsObj.put("reserve9", task.getReserve9());
				jsObj.put("reserve10", task.getReserve10());
				result.put(jsObj);
			}
		}
	}

	private void buildJSONObjectToTask(Task task, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		task.setTaskuuid(Utils.getUuid());
		task.setApplicant(URLDecoder.decode(StringUtils.isEmpty(jsonObj.optString("applicant")) ? user.getUsername() : jsonObj.optString("applicant"),"UTF-8"));
		task.setAppname(URLDecoder.decode(jsonObj.optString("appname"),"UTF-8"));
		task.setAppurl(URLDecoder.decode(jsonObj.optString("appurl"),"UTF-8"));
		task.setHandler(URLDecoder.decode(jsonObj.optString("handler"),"UTF-8"));
		task.setTaskdesc(URLDecoder.decode(jsonObj.optString("taskdesc"),"UTF-8"));
		task.setTaskurl(URLDecoder.decode(jsonObj.optString("taskurl"),"UTF-8"));
		task.setType(URLDecoder.decode(jsonObj.optString("type"),"UTF-8"));
		//系统集成时间
		task.setTime(URLDecoder.decode(jsonObj.optString("time"),"UTF-8"));
		//待办创建时间
		task.setCreatetime(DataUtil.date2Str(new Date()));
		task.setDeleteflag(jsonObj.optInt("deleteflag"));
		task.setAccessmobile(jsonObj.optInt("accessmobile"));
		//责任人userid
		task.setReserve1(URLDecoder.decode(jsonObj.optString("reserve1"),"UTF-8"));
		//创建者userid
		task.setReserve2(null != user ? String.valueOf(user.getId()) : "");
		task.setReserve3(URLDecoder.decode(jsonObj.optString("reserve3"),"UTF-8"));
		task.setReserve4(URLDecoder.decode(jsonObj.optString("reserve4"),"UTF-8"));
		task.setReserve5(URLDecoder.decode(jsonObj.optString("reserve5"),"UTF-8"));
		task.setReserve6(URLDecoder.decode(jsonObj.optString("reserve6"),"UTF-8"));
		task.setReserve7(URLDecoder.decode(jsonObj.optString("reserve7"),"UTF-8"));
		task.setReserve8(URLDecoder.decode(jsonObj.optString("reserve8"),"UTF-8"));
		task.setReserve9(URLDecoder.decode(jsonObj.optString("reserve9"),"UTF-8"));
		task.setReserve10(URLDecoder.decode(jsonObj.optString("reserve10"),"UTF-8"));
		
	}
}
