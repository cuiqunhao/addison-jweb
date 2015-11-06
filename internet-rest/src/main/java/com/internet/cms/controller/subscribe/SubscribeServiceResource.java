package com.internet.cms.controller.subscribe;
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
import com.internet.cms.model.subscribe.Subscribe;
import com.internet.cms.model.subscribe.SubscribeType;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;
import com.internet.cms.service.subscribe.ISubscribeService;

@Resource
@Path("/subscribe")
@Service
@Workspace(collectionTitle="subscribeService",workspaceTitle="Subscribe Service Resource")
public class SubscribeServiceResource{
	 @Context 
	 HttpServletRequest request;  

	private ISubscribeService subscribeService;
	public ISubscribeService getUserService() {
		return subscribeService;
	}

	@Autowired
	public void setSubscribeService(ISubscribeService subscribeService) {
		this.subscribeService = subscribeService;
	}
	
	/*************************************我的订阅分类****************************************/
	/**
	 * 添加我的订阅分类
	 * @param jsonObj 我的订阅分类json数据
	 * @return 0:失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/subscribetype")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addSubscribeType(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			SubscribeType subscribeType = new SubscribeType();
			buildJSONObjectToSubscribeType(subscribeType,jsonObj);
			//是否已经存在该订阅
			if(hasExistSubscribeType(subscribeType)){
				//2表示存在
				return result.put("result", 2);
			}
			boolean success = subscribeService.addSubscribeType(subscribeType);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除我的订阅分类
	 * @param id 分类id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/subscribetype/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteSubscribeType(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String userId = String.valueOf(user.getId());
			SubscribeType subscribeType = new SubscribeType(id,userId);
			boolean success = subscribeService.deleteSubscribeType(subscribeType);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新公共链接分类
	 * @param jsonObj 我的订阅分类json数据
	 * @return 0：失败 1：成功
	 */
	@POST
	@Path("/update/subscribetype")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateSubscribeType(JSONObject jsonObj){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			String id = jsonObj.optString("id");
			String userId = String.valueOf(user.getId());
			String name = jsonObj.optString("name");
			SubscribeType subscribeType = new SubscribeType(id,name,userId,DataUtil.date2Str(new Date()));
			boolean success = subscribeService.updateSubscribeType(subscribeType);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询我的订阅分类列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 我的订阅分类列表json
	 */
	@GET
	@Path("/list/subscribetype")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findSubscribeTypeList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("subscribetypelist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			SubscribeType subscribeType = new SubscribeType(String.valueOf(user.getId()));
			Pager<SubscribeType> subscribePage = subscribeService.findSubscribeTypeList(subscribeType,pb);
			if(null != subscribePage && subscribePage.getTotal() > 0){
				result.put("count",subscribePage.getTotal());
				buildSubscribeTypeListToJSONAarray(subscribePage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 判断是否已经存在当前分类
	 * @param subscribeType 分类参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistSubscribeType(SubscribeType subscribeType){
		int count = subscribeService.findSubscribeTypeCountByName(subscribeType);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	private void buildJSONObjectToSubscribeType(SubscribeType subscribeType, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		subscribeType.setId(Utils.getUuid());
		subscribeType.setName(URLDecoder.decode(jsonObj.optString("name"),"UTF-8"));
		subscribeType.setUserId(null != user ? String.valueOf(user.getId()) : "");
		subscribeType.setCreateDate(DataUtil.date2Str(new Date()));
		subscribeType.setLastmodifyDate(DataUtil.date2Str(new Date()));
	}
	
	private void buildSubscribeTypeListToJSONAarray(Pager<SubscribeType> subscribeTypePage,JSONArray result){
		if(null != subscribeTypePage){
			for(SubscribeType subscribeType : subscribeTypePage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", subscribeType.getId());
				jsObj.put("name", subscribeType.getName());
				jsObj.put("userId", subscribeType.getUserId());
				jsObj.put("createDate", subscribeType.getCreateDate());
				jsObj.put("lastmodifyDate", subscribeType.getLastmodifyDate());
				result.put(jsObj);
			}
		}
	}

	private JSONObject buildSubscribeTypeJsonObj(JSONObject jsObj,SubscribeType subscribeType) {
		jsObj.put("id", subscribeType.getId());
		jsObj.put("name", subscribeType.getName());
		jsObj.put("userId", subscribeType.getUserId());
		jsObj.put("createDate", subscribeType.getCreateDate());
		jsObj.put("lastmodifyDate", subscribeType.getLastmodifyDate());
		return jsObj;
	}
	
	private JSONObject buildSubscribeJsonObj(JSONObject jsObj,Subscribe subscribe) {
		jsObj.put("id", subscribe.getId());
		jsObj.put("name", subscribe.getName());
		jsObj.put("userId", subscribe.getUserId());
		jsObj.put("createDate", subscribe.getCreateDate());
		jsObj.put("lastmodifyDate", subscribe.getLastmodifyDate());
		jsObj.put("url", subscribe.getUrl());
		jsObj.put("position", subscribe.getPosition());
		return jsObj;
	}
	
	/*************************************我的订阅******************************************/
	/**
	 * 添加我的订阅
	 * @param jsonObj 我的订阅json参数
	 * @return 0：失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/subscribe")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addSubscribe(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			Subscribe subscribe = new Subscribe();
			buildJSONObjectToSubscribe(subscribe,jsonObj);
			//是否已经存在该订阅
			if(hasExistSubscribe(subscribe)){
				//2表示存在
				return result.put("result", 2);
			}
			boolean success = subscribeService.addSubscribe(subscribe);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除我的订阅
	 * @param id 我的订阅id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/subscribe/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteSubscribe(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String userId = String.valueOf(user.getId());
			Subscribe subscribe = new Subscribe();
			subscribe.setId(id);
			subscribe.setUserId(userId);
			boolean success = subscribeService.deleteSubscribe(subscribe);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新我的订阅
	 * @param jsonObj 我的订阅参数json
	 * @return 0：失败 1：成功
	 * @throws UnsupportedEncodingException 异常
	 */
	@POST
	@Path("/update/subscribe")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateSubscribe(JSONObject jsonObj) throws UnsupportedEncodingException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			Subscribe subscribe = new Subscribe();
			buildJSONObjectToSubscribe(subscribe,jsonObj);
			boolean success = subscribeService.updateSubscribe(subscribe);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询分类、我的订阅列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 分类、我的订阅列表
	 */
	@GET
	@Path("/list/subscribe")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findSubscribeList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
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
			
			SubscribeType subscribeType = new SubscribeType();
			subscribeType.setUserId(String.valueOf(user.getId()));
			
			//获取我的订阅分类列表
			Pager<SubscribeType> subscribeTypePage = subscribeService.findSubscribeTypeList(subscribeType,pb);
			
			List<String> types = new ArrayList<String>();
			if(null != subscribeTypePage){
				result.put("count", subscribeTypePage.getTotal());
				List<SubscribeType> subscribeTypes = subscribeTypePage.getDatas();
				//组装我的订阅查询条件（多个分类，主要用作in查询）
				for(SubscribeType linkType : subscribeTypes){
					types.add(linkType.getId());
				}
				//获取指定分类下的所有订阅列表
				List<Subscribe> subscribeList = subscribeService.findSubscribeList(types,subscribeType);
				//{"result": [{id:"",name:"",userid:"",subscribes:[{link1},{link2}...]},{id:"",name:"",userid:"",subscribes:[{link1},{link2}...]}.....]}
				for(SubscribeType linkType : subscribeTypes){
					JSONObject jsObj = new JSONObject();
					buildSubscribeTypeJsonObj(jsObj,linkType);
					JSONArray array = new JSONArray();
					for(Subscribe link : subscribeList){
						if(linkType.getId().equals(link.getTypeId())){
							JSONObject obj = new JSONObject();
							buildSubscribeJsonObj(obj,link);
							array.put(obj);
						}
					}
					
					jsObj.put("subscribes", array);
					jsonArray.put(jsObj);
				}
				result.put("result", jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 根据分类id，查询当前分类下的我的订阅列表
	 * @param typeid 分类id
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 当前分类下的我的订阅列表
	 */
	@GET
	@Path("/list/subscribe/{typeid}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findSubscribeListByTypeId(@PathParam("typeid") String typeid, @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("subscribelist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			
			Subscribe subscribe = new Subscribe();
			subscribe.setUserId(String.valueOf(user.getId()));
			subscribe.setTypeId(typeid);
			
			//获取我的订阅分类列表
			Pager<Subscribe> subscribePage = subscribeService.findSubscribeListByTypeId(subscribe,pb);
			if(null != subscribePage && subscribePage.getTotal() > 0){
				result.put("count",subscribePage.getTotal());
				buildSubscribeListToJSONAarray(subscribePage,jsonArray);
			}
		}
		return result;
	}

	private void buildSubscribeListToJSONAarray(Pager<Subscribe> subscribePage, JSONArray result) {
		if(null != subscribePage){
			for(Subscribe subscribe : subscribePage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", subscribe.getId());
				jsObj.put("name", subscribe.getName());
				jsObj.put("url", subscribe.getUrl());
				jsObj.put("userid", subscribe.getUserId());
				jsObj.put("newwin", subscribe.getNewWin());
				jsObj.put("position", subscribe.getPosition());
				jsObj.put("typeid", subscribe.getTypeId());
				jsObj.put("createDate", subscribe.getCreateDate());
				jsObj.put("lastmodifyDate", subscribe.getLastmodifyDate());
				result.put(jsObj);
			}
		}
	}

	private void buildJSONObjectToSubscribe(Subscribe subscribe, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		//添加和修改的uuid使用方式
		subscribe.setId(StringUtils.isNotEmpty(jsonObj.optString("id")) ? jsonObj.optString("id") : Utils.getUuid());
		subscribe.setName(URLDecoder.decode(jsonObj.optString("name"),"UTF-8"));
		subscribe.setUrl(URLDecoder.decode(jsonObj.optString("url"),"UTF-8"));
		subscribe.setUserId(null != user ? String.valueOf(user.getId()) : "");
		subscribe.setNewWin(jsonObj.optInt("newwin"));
		subscribe.setCreateDate(DataUtil.date2Str(new Date()));
		subscribe.setLastmodifyDate(DataUtil.date2Str(new Date()));
		subscribe.setPosition(jsonObj.optInt("position"));
		subscribe.setTypeId(jsonObj.optString("typeid"));
	}
	
	/**
	 * 判断是否已经存在当前订阅
	 * @param subscribeType 订阅参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistSubscribe(Subscribe subscribe){
		int count = subscribeService.findSubscribeCountByNameUrl(subscribe);
		if(count > 0){
			return true;
		}
		return false;
	}
}
