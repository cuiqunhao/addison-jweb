package com.internet.cms.dao.bookmark;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.bookmark.Bookmark;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface IBookmarkDao{
	public int findBookmarkCountByNameUrl(Bookmark bookmark);

	public Integer getMaxBookmarkOrderId(Bookmark bookmark);

	public int addBookmark(Bookmark bookmark);

	public int deleteBookmark(Bookmark bookmark);

	public int updateBookmark(Bookmark bookmark);

	public int findBookmarkCount(Bookmark bookmark);

	public List<Bookmark> findBookmarkList( Map<String, Object> params);

	public int findBookmarkCountByTypeId(Bookmark bookmark);

	public List<Bookmark> findBookmarkListByTypeId(Bookmark bookmark, PageBounds pb);
}
