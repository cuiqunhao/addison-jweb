package com.internet.cms.controller.bookmark;
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
import com.internet.cms.model.bookmark.Bookmark;
import com.internet.cms.model.bookmark.BookmarkType;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;
import com.internet.cms.service.bookmark.IBookmarkService;

@Resource
@Path("/bookmark")
@Service
@Workspace(collectionTitle="bookmarkService",workspaceTitle="Bookmark Service Resource")
public class BookmarkServiceResource{
	 @Context 
	 HttpServletRequest request;  

	private IBookmarkService bookmarkService;
	public IBookmarkService getUserService() {
		return bookmarkService;
	}

	@Autowired
	public void setBookmarkService(IBookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}
	
	/*************************************我的收藏分类****************************************/
	/**
	 * 添加我的收藏分类
	 * @param jsonObj 我的收藏分类json数据
	 * @return 0:失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/bookmarktype")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addBookmarkType(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			BookmarkType bookmarkType = new BookmarkType();
			buildJSONObjectToBookmarkType(bookmarkType,jsonObj);
			//是否已经存在该收藏
			if(hasExistBookmarkType(bookmarkType)){
				//2表示存在
				return result.put("result", 2);
			}
			boolean success = bookmarkService.addBookmarkType(bookmarkType);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除我的收藏分类
	 * @param id 分类id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/bookmarktype/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteBookmarkType(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String userId = String.valueOf(user.getId());
			BookmarkType bookmarkType = new BookmarkType(id,userId);
			boolean success = bookmarkService.deleteBookmarkType(bookmarkType);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新我的收藏分类
	 * @param jsonObj 我的收藏分类json数据
	 * @return 0：失败 1：成功
	 */
	@POST
	@Path("/update/bookmarktype")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateBookmarkType(JSONObject jsonObj){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			String id = jsonObj.optString("id");
			String userId = String.valueOf(user.getId());
			String name = jsonObj.optString("name");
			BookmarkType bookmarkType = new BookmarkType(id,name,userId,DataUtil.date2Str(new Date()));
			boolean success = bookmarkService.updateBookmarkType(bookmarkType);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询我的收藏分类列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 我的收藏分类列表json
	 */
	@GET
	@Path("/list/bookmarktype")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findBookmarkTypeList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("bookmarktypelist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			BookmarkType bookmarkType = new BookmarkType(String.valueOf(user.getId()));
			Pager<BookmarkType> bookmarkPage = bookmarkService.findBookmarkTypeList(bookmarkType,pb);
			if(null != bookmarkPage && bookmarkPage.getTotal() > 0){
				result.put("count",bookmarkPage.getTotal());
				buildBookmarkTypeListToJSONAarray(bookmarkPage,jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 判断是否已经存在当前分类
	 * @param bookmarkType 分类参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistBookmarkType(BookmarkType bookmarkType){
		int count = bookmarkService.findBookmarkTypeCountByName(bookmarkType);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	private void buildJSONObjectToBookmarkType(BookmarkType bookmarkType, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		bookmarkType.setId(Utils.getUuid());
		bookmarkType.setName(URLDecoder.decode(jsonObj.optString("name"),"UTF-8"));
		bookmarkType.setUserId(null != user ? String.valueOf(user.getId()) : "");
		bookmarkType.setCreateDate(DataUtil.date2Str(new Date()));
		bookmarkType.setLastmodifyDate(DataUtil.date2Str(new Date()));
	}
	
	private void buildBookmarkTypeListToJSONAarray(Pager<BookmarkType> bookmarkTypePage,JSONArray result){
		if(null != bookmarkTypePage){
			for(BookmarkType bookmarkType : bookmarkTypePage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", bookmarkType.getId());
				jsObj.put("name", bookmarkType.getName());
				jsObj.put("userId", bookmarkType.getUserId());
				jsObj.put("createDate", bookmarkType.getCreateDate());
				jsObj.put("lastmodifyDate", bookmarkType.getLastmodifyDate());
				result.put(jsObj);
			}
		}
	}

	private JSONObject buildBookmarkTypeJsonObj(JSONObject jsObj,BookmarkType bookmarkType) {
		jsObj.put("id", bookmarkType.getId());
		jsObj.put("name", bookmarkType.getName());
		jsObj.put("userId", bookmarkType.getUserId());
		jsObj.put("createDate", bookmarkType.getCreateDate());
		jsObj.put("lastmodifyDate", bookmarkType.getLastmodifyDate());
		return jsObj;
	}
	
	private JSONObject buildBookmarkJsonObj(JSONObject jsObj,Bookmark bookmark) {
		jsObj.put("id", bookmark.getId());
		jsObj.put("name", bookmark.getName());
		jsObj.put("userId", bookmark.getUserId());
		jsObj.put("createDate", bookmark.getCreateDate());
		jsObj.put("lastmodifyDate", bookmark.getLastmodifyDate());
		return jsObj;
	}
	
	/*************************************我的收藏******************************************/
	/**
	 * 添加我的收藏
	 * @param jsonObj 我的收藏json参数
	 * @return 0：失败 1：成功 2：重复添加
	 * @throws UnsupportedEncodingException 异常
	 */
	@PUT
	@Path("/add/bookmark")
	@Produces ({MediaType.APPLICATION_JSON})  
	@AuthMethod
	public JSONObject addBookmark(JSONObject jsonObj) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		//0表示添加失败
		result.put("result", 0);
		if(null != jsonObj){
			
			Bookmark bookmark = new Bookmark();
			buildJSONObjectToBookmark(bookmark,jsonObj);
			//是否已经存在该收藏
			if(hasExistBookmark(bookmark)){
				//2表示存在
				return result.put("result", 2);
			}
			boolean success = bookmarkService.addBookmark(bookmark);
			if(success){
				//1表示添加成功
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 删除我的收藏
	 * @param id 我的收藏id
	 * @return 0：失败 1：成功
	 */
	@DELETE
	@Path("/delete/bookmark/{id}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject deleteBookmark(@PathParam("id") String id){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != user){
			String userId = String.valueOf(user.getId());
			Bookmark bookmark = new Bookmark();
			bookmark.setId(id);
			bookmark.setUserId(userId);
			boolean success = bookmarkService.deleteBookmark(bookmark);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 更新我的收藏
	 * @param jsonObj 我的收藏参数json
	 * @return 0：失败 1：成功
	 * @throws UnsupportedEncodingException 异常
	 */
	@POST
	@Path("/update/bookmark")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject updateBookmark(JSONObject jsonObj) throws UnsupportedEncodingException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		JSONObject result = new JSONObject();
		result.put("result", 0);
		if(null != jsonObj && null != user){
			Bookmark bookmark = new Bookmark();
			buildJSONObjectToBookmark(bookmark,jsonObj);
			boolean success = bookmarkService.updateBookmark(bookmark);
			if(success){
				result.put("result", 1);
				return result;
			}
		}
		return result;
	}
	
	/**
	 * 查询分类、我的收藏列表
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 分类、我的收藏列表
	 */
	@GET
	@Path("/list/bookmark")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findBookmarkList( @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
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
			
			BookmarkType bookmarkType = new BookmarkType();
			bookmarkType.setUserId(String.valueOf(user.getId()));
			
			//获取我的收藏分类列表
			Pager<BookmarkType> bookmarkTypePage = bookmarkService.findBookmarkTypeList(bookmarkType,pb);
			
			List<String> types = new ArrayList<String>();
			if(null != bookmarkTypePage){
				result.put("count", bookmarkTypePage.getTotal());
				List<BookmarkType> bookmarkTypes = bookmarkTypePage.getDatas();
				//组装我的收藏查询条件（多个分类，主要用作in查询）
				for(BookmarkType linkType : bookmarkTypes){
					types.add(linkType.getId());
				}
				//获取指定分类下的所有收藏列表
				List<Bookmark> bookmarkList = bookmarkService.findBookmarkList(types,bookmarkType);
				//{"result": [{id:"",name:"",userid:"",bookmarks:[{link1},{link2}...]},{id:"",name:"",userid:"",bookmarks:[{link1},{link2}...]}.....]}
				for(BookmarkType linkType : bookmarkTypes){
					JSONObject jsObj = new JSONObject();
					buildBookmarkTypeJsonObj(jsObj,linkType);
					JSONArray array = new JSONArray();
					for(Bookmark link : bookmarkList){
						if(linkType.getId().equals(link.getTypeId())){
							JSONObject obj = new JSONObject();
							buildBookmarkJsonObj(obj,link);
							array.put(obj);
						}
					}
					
					jsObj.put("bookmarks", array);
					jsonArray.put(jsObj);
				}
				result.put("result", jsonArray);
			}
		}
		return result;
	}
	
	/**
	 * 根据分类id，查询当前分类下的我的收藏列表
	 * @param typeid 分类id
	 * @param page 当前页
	 * @param pageNum 每页显示条数
	 * @return 当前分类下的我的收藏列表
	 */
	@GET
	@Path("/list/bookmark/{typeid}")
	@Produces ({MediaType.APPLICATION_JSON})  
	public JSONObject findBookmarkListByTypeId(@PathParam("typeid") String typeid, @DefaultValue("1") @QueryParam("page") int page,  @DefaultValue("10") @QueryParam("pagenum") int pageNum){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		result.put("count", 0);
		result.put("bookmarklist", jsonArray);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		if(null != user){
			PageBounds pb = PageBoundsUtil.PageBoundsOrderExtend("orderId.desc");
			pb.setPage(page);
			pb.setLimit(pageNum);
			
			Bookmark bookmark = new Bookmark();
			bookmark.setUserId(String.valueOf(user.getId()));
			bookmark.setTypeId(typeid);
			
			//获取我的收藏分类列表
			Pager<Bookmark> bookmarkPage = bookmarkService.findBookmarkListByTypeId(bookmark,pb);
			if(null != bookmarkPage && bookmarkPage.getTotal() > 0){
				result.put("count",bookmarkPage.getTotal());
				buildBookmarkListToJSONAarray(bookmarkPage,jsonArray);
			}
		}
		return result;
	}

	private void buildBookmarkListToJSONAarray(Pager<Bookmark> bookmarkPage, JSONArray result) {
		if(null != bookmarkPage){
			for(Bookmark bookmark : bookmarkPage.getDatas()){
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", bookmark.getId());
				jsObj.put("name", bookmark.getName());
				jsObj.put("url", bookmark.getUrl());
				jsObj.put("userid", bookmark.getUserId());
				jsObj.put("typeid", bookmark.getTypeId());
				jsObj.put("createDate", bookmark.getCreateDate());
				jsObj.put("lastmodifyDate", bookmark.getLastmodifyDate());
				result.put(jsObj);
			}
		}
	}

	private void buildJSONObjectToBookmark(Bookmark bookmark, JSONObject jsonObj) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		//添加和修改的uuid使用方式
		bookmark.setId(StringUtils.isNotEmpty(jsonObj.optString("id")) ? jsonObj.optString("id") : Utils.getUuid());
		bookmark.setName(URLDecoder.decode(jsonObj.optString("name"),"UTF-8"));
		bookmark.setUrl(URLDecoder.decode(jsonObj.optString("url"),"UTF-8"));
		bookmark.setUserId(null != user ? String.valueOf(user.getId()) : "");
		bookmark.setTypeId(URLDecoder.decode(jsonObj.optString("typeid"),"UTF-8"));
		bookmark.setCreateDate(DataUtil.date2Str(new Date()));
		bookmark.setLastmodifyDate(DataUtil.date2Str(new Date()));
	}
	
	/**
	 * 判断是否已经存在当前收藏
	 * @param bookmarkType 收藏参数
	 * @return true：表示存在 false：表示不存在
	 */
	private boolean hasExistBookmark(Bookmark bookmark){
		int count = bookmarkService.findBookmarkCountByNameUrl(bookmark);
		if(count > 0){
			return true;
		}
		return false;
	}
}
