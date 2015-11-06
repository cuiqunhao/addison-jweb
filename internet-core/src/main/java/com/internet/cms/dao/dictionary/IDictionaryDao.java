package com.internet.cms.dao.dictionary;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.dictionary.Dictionary;

public interface IDictionaryDao {
	/**
	 * 根据字典id获取当前字典的信息
	 * @param id 当前字典id
	 * @return 字典的详细信息
	 */
	public Dictionary load(@Param("id") Integer id);
	
	/**
	 * 根据父id获取所有的子字典(根据父亲id加载字典，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父字典id
	 * @return 子字典列表信息
	 */
	public List<Dictionary> listByParent(@Param("pid") Integer pid,PageBounds pageBounds);
	
	/**
	 * 添加字典信息
	 * @param dictionary 字典对象
	 */
	public void add(Dictionary dictionary);
	
	/**
	 * 更新字典
	 * @param dictionary 字典对象
	 */
	public void update(Dictionary dictionary);
	
	/**
	 * 刪除字典
	 * @param dictionary 字典对象
	 */
	public void delete(@Param("id") int id);

	/**
	 * 根据父字典获取子字典的最大的排序号
	 * @param pid 
	 * @return
	 */
	public Integer getMaxOrderByParent(@Param("pid") Integer pid);

	public int listByParentCount(@Param("pid") Integer pid);

}
