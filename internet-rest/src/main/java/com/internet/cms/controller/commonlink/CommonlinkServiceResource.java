package com.internet.cms.controller.commonlink;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.internet.cms.model.commonlink.Commonlink;
import com.internet.cms.model.commonlink.CommonlinkType;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;
import com.internet.cms.service.commonlink.ICommonlinkService;

@Resource
@Path("/commonlink")
@Service
@Workspace(collectionTitle="commonlinkService",workspaceTitle="Commonlink Service Resource")
public class CommonlinkServiceResource{
	 @Context 
	 HttpServletRequest request;  

	private ICommonlinkService commonlinkService;
	public ICommonlinkService getUserService() {
		return commonlinkService;
	}

	@Autowired
	public void setCommonlinkService(ICommonlinkService commonlinkService) {
		this.commonlinkService = commonlinkService;
	}
	
	/*************************************公共连接分类****************************************/
	/**
	 * 添加公共连接分类
	 * @param jsonObj 公共连接分类json数据
	 * @return 0:失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/commonlinktype")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addCommonlinkType(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			CommonlinkType commonlinkType = new CommonlinkType();
			buildJSONObjectToCommonlinkType(commonlinkType,jsonObj);
			//是否已经存在该连接
			if(hasExistCommonlinkType(commonlinkType)){
				//2表示存在
				return result.put("result", 2);
			}
			boolean success = commonlinkService.addCommonlinkType(commonlinkType);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除公共连接分类
	 * @param id 分类id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/commonlinktype/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteCommonlinkType(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String userId = String.valueOf(user.getId());
			CommonlinkType commonlinkType = new CommonlinkType(id,userId);
			boolean success = commonlinkService.deleteCommonlinkType(commonlinkType);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新公共链接分类
	 * @param jsonObj 公共连接分类json数据
	 * @return 0：失败 1：成功
	 */
	@POST
	@Path("/update/commonlinktype")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateCommonlinkType(JSONObject jsonObj){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			String id = jsonObj.optString("id");
			String userId = String.valueOf(user.getId());
			String name = jsonObj.optString("name");
			CommonlinkType commonlinkType = new CommonlinkType(id,name,userId,DataUtil.date2Str(new Date()));
			boolean success = commonlinkService.updateCommonlinkType(commonlinkType);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询公共连接分类列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 公共连接分类列表json
	 */
	@GET
	@Path("/list/commonlinktype")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findCommonlinkTypeList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("commonlinktypelist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			CommonlinkType commonlinkType = new CommonlinkType(String.valueOf(user.getId()));
			Pager<CommonlinkType> commonLinkPage = commonlinkService.findCommonlinkTypeList(commonlinkType,pb);
			if(null != commonLinkPage && commonLinkPage.getTotal() > 0){
				result.put("count",commonLinkPage.getTotal());
				buildCommonlinkTypeListToJSONAarray(commonLinkPage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 判断是否已经存在当前分类
	 * @param commonlinkType 分类参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistCommonlinkType(CommonlinkType commonlinkType){
		int count = commonlinkService.findCommonlinkTypeCountByName(commonlinkType);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	private void buildJSONObjectToCommonlinkType(CommonlinkType commonlinkType, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		commonlinkType.setId(Utils.getUuid());
		commonlinkType.setName(URLDecoder.decode(jsonObj.optString("name"),"UTF-8"));
		commonlinkType.setUserId(null != user ? String.valueOf(user.getId()) : "");
		commonlinkType.setCreateDate(DataUtil.date2Str(new Date()));
		commonlinkType.setLastmodifyDate(DataUtil.date2Str(new Date()));
	}
	
	private void buildCommonlinkTypeListToJSONAarray(Pager<CommonlinkType> commonlinkTypePage,JSONArray result){
		if(null != commonlinkTypePage){
			for(CommonlinkType commonlinkType : commonlinkTypePage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", commonlinkType.getId());
				jsObj.put("name", commonlinkType.getName());
				jsObj.put("userId", commonlinkType.getUserId());
				jsObj.put("createDate", commonlinkType.getCreateDate());
				jsObj.put("lastmodifyDate", commonlinkType.getLastmodifyDate());
				result.put(jsObj);
			}
		}
	}

	private JSONObject buildCommonlinkTypeJsonObj(JSONObject jsObj,CommonlinkType commonlinkType) {
		jsObj.put("id", commonlinkType.getId());
		jsObj.put("name", commonlinkType.getName());
		jsObj.put("userId", commonlinkType.getUserId());
		jsObj.put("createDate", commonlinkType.getCreateDate());
		jsObj.put("lastmodifyDate", commonlinkType.getLastmodifyDate());
		return jsObj;
	}
	
	private JSONObject buildCommonlinkJsonObj(JSONObject jsObj,Commonlink commonlink) {
		jsObj.put("id", commonlink.getId());
		jsObj.put("name", commonlink.getName());
		jsObj.put("userId", commonlink.getUserId());
		jsObj.put("createDate", commonlink.getCreateDate());
		jsObj.put("lastmodifyDate", commonlink.getLastmodifyDate());
		jsObj.put("url", commonlink.getUrl());
		jsObj.put("position", commonlink.getPosition());
		return jsObj;
	}
	
	/*************************************公共连接******************************************/
	/**
	 * 添加公共连接
	 * @param jsonObj 公共连接json参数
	 * @return 0：失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/commonlink")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addCommonlink(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			Commonlink commonlink = new Commonlink();
			buildJSONObjectToCommonlink(commonlink,jsonObj);
			//是否已经存在该连接
			if(hasExistCommonlink(commonlink)){
				//2表示存在
				return result.put("result", 2);
			}
			boolean success = commonlinkService.addCommonlink(commonlink);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除公共连接
	 * @param id 公共连接id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/commonlink/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteCommonlink(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String userId = String.valueOf(user.getId());
			Commonlink commonlink = new Commonlink();
			commonlink.setId(id);
			commonlink.setUserId(userId);
			boolean success = commonlinkService.deleteCommonlink(commonlink);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新公共连接
	 * @param jsonObj 公共连接参数json
	 * @return 0：失败 1：成功
	 * @throws UnsupportedEncodingException 异常
	 */
	@POST
	@Path("/update/commonlink")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateCommonlink(JSONObject jsonObj) throws UnsupportedEncodingException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			Commonlink commonlink = new Commonlink();
			buildJSONObjectToCommonlink(commonlink,jsonObj);
			boolean success = commonlinkService.updateCommonlink(commonlink);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询分类、公共连接列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 分类、公共连接列表
	 */
	@GET
	@Path("/list/commonlink")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findCommonlinkList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("result", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			
			CommonlinkType commonlinkType = new CommonlinkType();
			commonlinkType.setUserId(String.valueOf(user.getId()));
			
			//获取公共连接分类列表
			Pager<CommonlinkType> commonLinkTypePage = commonlinkService.findCommonlinkTypeList(commonlinkType,pb);
			
			List<String> types = new ArrayList<String>();
			if(null != commonLinkTypePage){
				result.put("count", commonLinkTypePage.getTotal());
				List<CommonlinkType> commonlinkTypes = commonLinkTypePage.getDatas();
				//组装公共连接查询条件（多个分类，主要用作in查询）
				for(CommonlinkType linkType : commonlinkTypes){
					types.add(linkType.getId());
				}
				//获取指定分类下的所有连接列表
				List<Commonlink> commonLinkList = commonlinkService.findCommonlinkList(types,commonlinkType);
				//{"result": [{id:"",name:"",userid:"",commonlinks:[{link1},{link2}...]},{id:"",name:"",userid:"",commonlinks:[{link1},{link2}...]}.....]}
				for(CommonlinkType linkType : commonlinkTypes){
					JSONObject jsObj = new JSONObject();
					buildCommonlinkTypeJsonObj(jsObj,linkType);
					JSONArray array = new JSONArray();
					for(Commonlink link : commonLinkList){
						if(linkType.getId().equals(link.getTypeId())){
							JSONObject obj = new JSONObject();
							buildCommonlinkJsonObj(obj,link);
							array.put(obj);
						}
					}
					
					jsObj.put("commonlinks", array);
					jsonArray.put(jsObj);
				}
				result.put("result", jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 根据分类id，查询当前分类下的公共连接列表
	 * @param typeid 分类id
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 当前分类下的公共连接列表
	 */
	@GET
	@Path("/list/commonlink/{typeid}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findCommonlinkListByTypeId(@PathParam("typeid") String typeid, @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("commonlinklist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			
			Commonlink commonlink = new Commonlink();
			commonlink.setUserId(String.valueOf(user.getId()));
			commonlink.setTypeId(typeid);
			
			//获取公共连接分类列表
			Pager<Commonlink> commonLinkPage = commonlinkService.findCommonlinkListByTypeId(commonlink,pb);
			if(null != commonLinkPage && commonLinkPage.getTotal() > 0){
				result.put("count",commonLinkPage.getTotal());
				buildCommonlinkListToJSONAarray(commonLinkPage,jsonArray);
			}
		}
		return result;
	}

	private void buildCommonlinkListToJSONAarray(Pager<Commonlink> commonLinkPage, JSONArray result) {
		if(null != commonLinkPage){
			for(Commonlink commonlink : commonLinkPage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", commonlink.getId());
				jsObj.put("name", commonlink.getName());
				jsObj.put("url", commonlink.getUrl());
				jsObj.put("userid", commonlink.getUserId());
				jsObj.put("newwin", commonlink.getNewWin());
				jsObj.put("position", commonlink.getPosition());
				jsObj.put("typeid", commonlink.getTypeId());
				jsObj.put("createDate", commonlink.getCreateDate());
				jsObj.put("lastmodifyDate", commonlink.getLastmodifyDate());
				result.put(jsObj);
			}
		}
	}

	private void buildJSONObjectToCommonlink(Commonlink commonlink, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		//添加和修改的uuid使用方式
		commonlink.setId(StringUtils.isNotEmpty(jsonObj.optString("id")) ? jsonObj.optString("id") : Utils.getUuid());
		commonlink.setName(URLDecoder.decode(jsonObj.optString("name"),"UTF-8"));
		commonlink.setUrl(URLDecoder.decode(jsonObj.optString("url"),"UTF-8"));
		commonlink.setUserId(null != user ? String.valueOf(user.getId()) : "");
		commonlink.setNewWin(jsonObj.optInt("newwin"));
		commonlink.setCreateDate(DataUtil.date2Str(new Date()));
		commonlink.setLastmodifyDate(DataUtil.date2Str(new Date()));
		commonlink.setPosition(jsonObj.optInt("position"));
		commonlink.setTypeId(jsonObj.optString("typeid"));
	}
	
	/**
	 * 判断是否已经存在当前连接
	 * @param commonlinkType 连接参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistCommonlink(Commonlink commonlink){
		int count = commonlinkService.findCommonlinkCountByNameUrl(commonlink);
		if(count > 0){
			return true;
		}
		return false;
	}
}
