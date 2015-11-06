package com.internet.cms.service.topic;

import java.util.List;

import com.internet.cms.model.Keyword;
import com.internet.cms.page.Pager;


public interface IKeywordService {
	public void addOrUpdate(String name);
	/**
	 * 获取引用次数大于等于某个数的关键字
	 * @param num
	 * @return
	 */
	public List<Keyword> getKeywordByTimes(int num);
	/**
	 * 获取引用次数最多个num个关键字
	 * @param num
	 * @return
	 */
	public List<Keyword> getMaxTimesKeyword(int num);
	/**
	 * 查找没有使用的关键字
	 * @return
	 */
	public Pager<Keyword> findNoUseKeyword();
	/**
	 * 清空没有使用的关键字
	 */
	public void clearNoUseKeyword();
	/**
	 * 查找正在被引用的关键字
	 * @return
	 */
	public List<Keyword> findUseKeyword();
	/**
	 * 根据某个条件从keyword表中查询关键字
	 * @param con
	 * @return
	 */
	public List<Keyword> listKeywordByCon(String con);
	
	/**
	 * 根据关键字联想已经在数据库中存在的关键字（创建文档时输入关键字并联想）
	 * @param name 创建文档输入的关键字
	 * @return 关键字
	 */
	public List<String> listKeywordStringByCon(String con);
}
