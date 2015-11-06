package com.internet.cms.service.subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.subscribe.ISubscribeDao;
import com.internet.cms.dao.subscribe.ISubscribeTypeDao;
import com.internet.cms.model.subscribe.Subscribe;
import com.internet.cms.model.subscribe.SubscribeType;
import com.internet.cms.page.Pager;

@Service("subscribeService")
public class SubscribeService implements ISubscribeService {
	private ISubscribeTypeDao subscribeTypeDao;
	private ISubscribeDao subscribeDao;

	public ISubscribeTypeDao getSubscribeTypeDao() {
		return subscribeTypeDao;
	}

	@Inject
	public void setSubscribeTypeDao(ISubscribeTypeDao subscribeTypeDao) {
		this.subscribeTypeDao = subscribeTypeDao;
	}
	
	@Inject
	public void setSubscribeDao(ISubscribeDao subscribeDao) {
		this.subscribeDao = subscribeDao;
	}

	@Override
	public boolean addSubscribeType(SubscribeType subscribeType) {
        //获取最大排序id，让新增的分类排序id自动累加
		Integer maxOrderId = subscribeTypeDao.getMaxSubscribeTypeOrderId(subscribeType);
		if(null == maxOrderId){
			maxOrderId = 0;
		}
		subscribeType.setOrderId(maxOrderId+1);
		int result  = subscribeTypeDao.addSubscribeType(subscribeType);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteSubscribeType(SubscribeType subscribeType) {
		int result  = subscribeTypeDao.deleteSubscribeType(subscribeType);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateSubscribeType(SubscribeType subscribeType) {
		int result  = subscribeTypeDao.updateSubscribeType(subscribeType);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public int findSubscribeTypeCountByName(SubscribeType subscribeType) {
		int count  = subscribeTypeDao.findSubscribeTypeCountByName(subscribeType);
		return count;
	}

	@Override
	public Pager<SubscribeType> findSubscribeTypeList(SubscribeType subscribeType,PageBounds pb) {
		//获取用户总数
		int count = subscribeTypeDao.findSubscribeTypeCount(subscribeType);
		//获取用户分页列表集合信息
		List<SubscribeType> list = subscribeTypeDao.findSubscribeTypeList(subscribeType.getUserId(),pb);
		//封装用户分页的Pager对象
		Pager<SubscribeType> pages = new Pager<SubscribeType>(count,list);
		return pages;
	}
	
	////////////////////////////////
	public int findSubscribeCountByNameUrl(Subscribe subscribe){
		int count  = subscribeDao.findSubscribeCountByNameUrl(subscribe);
		return count;
	}
	
	public boolean addSubscribe(Subscribe subscribe){
		//获取最大排序id，让新增的分类排序id自动累加
		Integer maxOrderId = subscribeDao.getMaxSubscribeOrderId(subscribe);
		if(null == maxOrderId){
			maxOrderId = 0;
		}
		subscribe.setOrderId(maxOrderId+1);
		int result  = subscribeDao.addSubscribe(subscribe);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public boolean deleteSubscribe(Subscribe subscribe){
		int result  = subscribeDao.deleteSubscribe(subscribe);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public boolean updateSubscribe(Subscribe subscribe){
		int result  = subscribeDao.updateSubscribe(subscribe);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public List<Subscribe> findSubscribeList(List<String> types,SubscribeType subscribe){
		Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("userId", subscribe.getUserId());
         params.put("types", types);
		//获取用户分页列表集合信息
		List<Subscribe> list = subscribeDao.findSubscribeList(params);
		return list;
	}
	
	public Pager<Subscribe> findSubscribeListByTypeId(Subscribe subscribe, PageBounds pb){
		//获取用户总数
		int count = subscribeDao.findSubscribeCountByTypeId(subscribe);
		//获取用户分页列表集合信息
		List<Subscribe> list = subscribeDao.findSubscribeListByTypeId(subscribe,pb);
		//封装用户分页的Pager对象
		Pager<Subscribe> pages = new Pager<Subscribe>(count,list);
		return pages;
	}

}
