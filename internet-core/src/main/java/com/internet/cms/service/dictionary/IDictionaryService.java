package com.internet.cms.service.dictionary;

import java.util.List;

import com.internet.cms.model.dictionary.Dictionary;
import com.internet.cms.page.Pager;

public interface IDictionaryService {
	/**
	 * 根据字典id获取当前字典的信息
	 * @param id 当前字典id
	 * @return 字典的详细信息
	 */
	public Dictionary load(int id);
	
	/**
	 * 添加字典
	 * @param dictionary 字典对象
	 * @param pid 父字典
	 */
	public void add(Dictionary dictionary,Integer pid);
	/**
	 * 更新字典
	 * @param dictionary
	 */
	public void update(Dictionary dictionary);
	/**
	 * 删除字典
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 根据父id获取所有的子字典(根据父亲id加载字典，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父字典id
	 * @return 子字典列表信息
	 */
	public Pager<Dictionary> listByParent(Integer pid);
}
