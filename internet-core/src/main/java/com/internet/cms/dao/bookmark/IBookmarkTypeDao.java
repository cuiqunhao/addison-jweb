package com.internet.cms.dao.bookmark;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.bookmark.BookmarkType;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface IBookmarkTypeDao{
	public int addBookmarkType(BookmarkType bookmarkType);

	public int deleteBookmarkType(BookmarkType bookmarkType);

	public int updateBookmarkType(BookmarkType bookmarkType);

	public int findBookmarkTypeCountByName(BookmarkType bookmarkType);

	public List<BookmarkType> findBookmarkTypeList(String userId,PageBounds pageBounds);

	public Integer getMaxBookmarkTypeOrderId(BookmarkType bookmarkType);

	public int findBookmarkTypeCount(BookmarkType bookmarkType);
	
}
