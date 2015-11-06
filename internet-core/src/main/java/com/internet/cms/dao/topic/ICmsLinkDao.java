package com.internet.cms.dao.topic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.CmsLink;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface ICmsLinkDao{
	
	public int findByTypeCount(@Param("type") String type);
	/**
	 * 根据类型获取超链接，如果type为空就获取所有的超链接，排序方式根据pos
	 * @param type
	 * @return
	 */
	public List<CmsLink> findByType(@Param("type") String type,PageBounds pageBounds);
	/**
	 * 获取某个类型中的所有链接，不进行分页
	 * @param type
	 * @return
	 */
	public List<CmsLink> listByType(@Param("type")  String type);
	/**
	 * 获取超链接的所有类型
	 * @return
	 */
	public List<String> listAllType();
	/**
	 * 获取最大和最小的排序号
	 * @return
	 */
	public String getMinAndMaxPos();
	/**
	 * 更新排序
	 * @param id
	 * @param oldPos
	 * @param newPos
	 */
	public void updatePos(@Param("id") int id,@Param("oldPos") int oldPos, @Param("newPos")int newPos);
	public void add(CmsLink cl);
	public void delete(@Param("id") int id);
	public void update(CmsLink cl);
	public CmsLink load(@Param("id") int id);
}
