package com.internet.cms.service.dictionary;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.basic.util.DataUtil;
import com.internet.cms.dao.dictionary.IDictionaryDao;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.User;
import com.internet.cms.model.dictionary.Dictionary;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("dictionaryService")
public class DictionaryService implements IDictionaryService {
	private IDictionaryDao dictionaryDao;

	public IDictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}
	@Inject
	public void setDictionaryDao(IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	/**
	 * 根据字典id获取当前字典的信息
	 * @param id 当前字典id
	 * @return 字典的详细信息
	 */
	public Dictionary load(int id) {
		return dictionaryDao.load(id);
	}

	/**
	 * 添加字典
	 * @param dictionary 字典对象
	 * @param pid 父字典
	 */
	public void add(Dictionary dictionary, Integer pid) {
		//根据父字典获取子字典的最大的排序号
		Integer orders = dictionaryDao.getMaxOrderByParent(pid);
		if(pid!=null&&pid>0) {
			Dictionary pc = dictionaryDao.load(pid);
			if(pc==null) throw new CmsException("要添加字典的父类对象不正确!");
			dictionary.setParent(pc);
		}
		dictionary.setOrders(orders+1);
		dictionary.setCreatetime(DataUtil.date2Str(new Date()));
		dictionary.setLastupdatetime(DataUtil.date2Str(new Date()));
		//添加字典信息
		dictionaryDao.add(dictionary);
	}

	/**
	 * 更新字典
	 * @param dictionary 字典对象
	 */
	public void update(Dictionary dictionary) {
		//更新字典
		dictionaryDao.update(dictionary);
	}

	public void delete(int id) {
		//1、需要判断是否存在子字典
		int count = dictionaryDao.listByParentCount(id);
		if(count>0) throw new CmsException("要删除的字典还有子字典，无法删除");
		dictionaryDao.delete(id);
	}
	
	/**
	 * 根据父id获取所有的子字典(根据父亲id加载字典，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父字典id
	 * @return 子字典列表信息
	 */
	public Pager<Dictionary> listByParent(Integer pid) {
		//获取用户总数
		int count = dictionaryDao.listByParentCount(pid);
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
		//获取用户分页列表集合信息
		List<Dictionary> list = dictionaryDao.listByParent(pid,pageBounds);
		//封装用户分页的Pager对象
		Pager<Dictionary> pages = new Pager<Dictionary>(count,list);
		return pages;
	}
}
