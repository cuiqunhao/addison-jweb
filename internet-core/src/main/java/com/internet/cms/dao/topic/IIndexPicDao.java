package com.internet.cms.dao.topic;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.IndexPic;
import com.internet.cms.page.Pager;

public interface IIndexPicDao {
	/**
	 * 根据数量来获取首页图片信息
	 * @param num
	 * @return
	 */
	public List<IndexPic> listIndexPicByNum(int num);
	
	public List<IndexPic> findIndexPic(PageBounds pageBounds);
	/**
	 * 获取所有的首页图片名称
	 * @return
	 */
	public List<String> listAllIndexPicName();
	
	public String getMinAdnMaxPos();
	/**
	 * 更新位置，如果原位置小于新位置，让所有>原始位置，<=新位置的元素全部-1之后更新对象的位置
	 * 如果原位置大于新位置，让所有小于原位置>=新位置的元素全部+1，之后更新当前元素
	 * @param id
	 * @param oldPos原始的位置
	 * @param newPos新的位置
	 */
	public void updatePos(int id,int oldPos,int newPos);

	public void add(IndexPic indexPic);

	public void update(IndexPic indexPic);

	public IndexPic load(int id);

	public void delete(int id);

	public int findIndexPicCount();
}
