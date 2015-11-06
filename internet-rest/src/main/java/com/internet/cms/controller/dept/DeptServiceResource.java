package com.internet.cms.controller.dept;
import java.io.IOException;
import java.net.URLDecoder;

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

import org.apache.wink.common.annotations.Workspace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.auth.AuthMethod;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.User;
import com.internet.cms.model.dept.Dept;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;
import com.internet.cms.service.dept.IDeptService;

@Resource
@Path("/dept")
@Service
@Workspace(collectionTitle="deptService",workspaceTitle="Dept Service Resource")
public class DeptServiceResource{
	
	 @Context 
	 HttpServletRequest request;  

	private IDeptService deptService;
	public IDeptService getUserService() {
		return deptService;
	}

	@Autowired
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}
	
	/**
	 * 添加部门
	 * @param jsonObj 部门json数据
	 * @return 0:失败 1：成功 2：重复添加
	 * @throws IOException 异常
	 */
	@PUT
	@Path("/add/dept/{pid}")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addDept(@PathParam("pid") int pid, JSONObject jsonObj) throws IOException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			try{
				Dept dept = new Dept();
				buildJSONObjectToDept(dept,jsonObj);
				//是否已经存在该部门
				if(hasExistDept(dept)){
					//2表示存在
					return result.put("result", 2);
				}
				deptService.add(dept,pid);
				result.put("result", 1);
				return result;
			}catch(CmsException e){
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除部门
	 * @param id id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/dept/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteDept(@PathParam("id") int id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			try{
				deptService.delete(id);
				result.put("result", 1);
				return result;
			}catch(Exception e){
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新链接
	 * @param jsonObj 部门json数据
	 * @return 0：失败 1：成功
	 * @throws IOException 
	 */
	@POST
	@Path("/update/dept/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateDept(@PathParam("id") int id, JSONObject jsonObj) throws IOException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			try{
				Dept dept = new Dept();
				buildJSONObjectToDept(dept,jsonObj);
				deptService.update(dept);
				result.put("result", 1);
				return result;
			}catch(CmsException e){
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询部门列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 部门列表json
	 */
	@GET
	@Path("/list/dept/{pid}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findDeptList( @PathParam("pid") int pid, @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("deptlist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			Pager<Dept> deptPage = deptService.listByParent(pid);
			if(null != deptPage && deptPage.getTotal() > 0){
				result.put("count",deptPage.getTotal());
				buildDeptListToJSONAarray(deptPage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 根据部门id查询部门列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 部门json
	 */
	@GET
	@Path("/dept/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findDeptById( @PathParam("id") int id){
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			Dept dept = deptService.load(id);
			if(null != dept){
				buildDeptJsonObj(result,dept);
			}
		}
		return result;
	}
	
	/**
	 * 判断是否已经存在当前
	 * @param dept 参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistDept(Dept dept){
		int count = deptService.findDeptCountByName(dept);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	private void buildJSONObjectToDept(Dept dept, JSONObject jsonObj) throws IOException{
		dept.setName_en(URLDecoder.decode(jsonObj.optString("name_en"),"UTF-8"));
		dept.setName_cn(URLDecoder.decode(jsonObj.optString("name_cn"),"UTF-8"));
		dept.setStatus(jsonObj.optInt("status"));
	}
	
	private void buildDeptListToJSONAarray(Pager<Dept> deptPage,JSONArray result){
		if(null != deptPage){
			for(Dept dept : deptPage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", dept.getId());
				jsObj.put("name_en", dept.getName_en());
				jsObj.put("name_cn", dept.getName_cn());
				jsObj.put("name_cn", dept.getName_cn());
				jsObj.put("createtime", dept.getCreatetime());
				jsObj.put("lastupdatetime", dept.getLastupdatetime());
				result.put(jsObj);
			}
		}
	}

	private JSONObject buildDeptJsonObj(JSONObject jsObj,Dept dept) {
		jsObj.put("id", dept.getId());
		jsObj.put("name_en", dept.getName_en());
		jsObj.put("name_cn", dept.getName_cn());
		jsObj.put("name_cn", dept.getName_cn());
		jsObj.put("createtime", dept.getCreatetime());
		jsObj.put("lastupdatetime", dept.getLastupdatetime());
		return jsObj;
	}
}
