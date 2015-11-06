package com.internet.cms.service.bookmark;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.bookmark.Bookmark;
import com.internet.cms.model.bookmark.BookmarkType;
import com.internet.cms.page.Pager;

public interface IBookmarkService {
	public boolean addBookmarkType(BookmarkType cl);

	public boolean deleteBookmarkType(BookmarkType bookmarkType);

	public boolean updateBookmarkType(BookmarkType bookmarkType);

	public int findBookmarkTypeCountByName(BookmarkType bookmarkType);

	public Pager<BookmarkType> findBookmarkTypeList(BookmarkType bookmarkType,PageBounds pb);
	
	//////////////////////////////////////////////

	public int findBookmarkCountByNameUrl(Bookmark bookmark);

	public boolean addBookmark(Bookmark bookmark);

	public boolean deleteBookmark(Bookmark bookmark);

	public boolean updateBookmark(Bookmark bookmark);

	public List<Bookmark> findBookmarkList(List<String> types,BookmarkType bookmarkType);

	public Pager<Bookmark> findBookmarkListByTypeId(Bookmark bookmark, PageBounds pb);
	
	
	
	
}
