package com.internet.cms.service.bookmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.bookmark.IBookmarkDao;
import com.internet.cms.dao.bookmark.IBookmarkTypeDao;
import com.internet.cms.model.bookmark.Bookmark;
import com.internet.cms.model.bookmark.BookmarkType;
import com.internet.cms.page.Pager;

@Service("bookmarkService")
public class BookmarkService implements IBookmarkService {
	private IBookmarkTypeDao bookmarkTypeDao;
	private IBookmarkDao bookmarkDao;

	public IBookmarkTypeDao getBookmarkTypeDao() {
		return bookmarkTypeDao;
	}

	@Inject
	public void setBookmarkTypeDao(IBookmarkTypeDao bookmarkTypeDao) {
		this.bookmarkTypeDao = bookmarkTypeDao;
	}
	
	@Inject
	public void setBookmarkDao(IBookmarkDao bookmarkDao) {
		this.bookmarkDao = bookmarkDao;
	}

	@Override
	public boolean addBookmarkType(BookmarkType bookmarkType) {
        //获取最大排序id，让新增的分类排序id自动累加
		Integer maxOrderId = bookmarkTypeDao.getMaxBookmarkTypeOrderId(bookmarkType);
		if(null == maxOrderId){
			maxOrderId = 0;
		}
		bookmarkType.setOrderId(maxOrderId+1);
		int result  = bookmarkTypeDao.addBookmarkType(bookmarkType);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteBookmarkType(BookmarkType bookmarkType) {
		int result  = bookmarkTypeDao.deleteBookmarkType(bookmarkType);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateBookmarkType(BookmarkType bookmarkType) {
		int result  = bookmarkTypeDao.updateBookmarkType(bookmarkType);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public int findBookmarkTypeCountByName(BookmarkType bookmarkType) {
		int count  = bookmarkTypeDao.findBookmarkTypeCountByName(bookmarkType);
		return count;
	}

	@Override
	public Pager<BookmarkType> findBookmarkTypeList(BookmarkType bookmarkType,PageBounds pb) {
		//获取用户总数
		int count = bookmarkTypeDao.findBookmarkTypeCount(bookmarkType);
		//获取用户分页列表集合信息
		List<BookmarkType> list = bookmarkTypeDao.findBookmarkTypeList(bookmarkType.getUserId(),pb);
		//封装用户分页的Pager对象
		Pager<BookmarkType> pages = new Pager<BookmarkType>(count,list);
		return pages;
	}
	
	////////////////////////////////
	public int findBookmarkCountByNameUrl(Bookmark bookmark){
		int count  = bookmarkDao.findBookmarkCountByNameUrl(bookmark);
		return count;
	}
	
	public boolean addBookmark(Bookmark bookmark){
		//获取最大排序id，让新增的分类排序id自动累加
		Integer maxOrderId = bookmarkDao.getMaxBookmarkOrderId(bookmark);
		if(null == maxOrderId){
			maxOrderId = 0;
		}
		bookmark.setOrderId(maxOrderId+1);
		int result  = bookmarkDao.addBookmark(bookmark);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public boolean deleteBookmark(Bookmark bookmark){
		int result  = bookmarkDao.deleteBookmark(bookmark);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public boolean updateBookmark(Bookmark bookmark){
		int result  = bookmarkDao.updateBookmark(bookmark);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public List<Bookmark> findBookmarkList(List<String> types,BookmarkType bookmark){
		Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("userId", bookmark.getUserId());
         params.put("types", types);
		//获取用户分页列表集合信息
		List<Bookmark> list = bookmarkDao.findBookmarkList(params);
		return list;
	}
	
	public Pager<Bookmark> findBookmarkListByTypeId(Bookmark bookmark, PageBounds pb){
		//获取用户总数
		int count = bookmarkDao.findBookmarkCountByTypeId(bookmark);
		//获取用户分页列表集合信息
		List<Bookmark> list = bookmarkDao.findBookmarkListByTypeId(bookmark,pb);
		//封装用户分页的Pager对象
		Pager<Bookmark> pages = new Pager<Bookmark>(count,list);
		return pages;
	}

}
