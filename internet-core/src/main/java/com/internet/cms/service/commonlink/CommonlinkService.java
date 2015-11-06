package com.internet.cms.service.commonlink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.commonlink.ICommonlinkDao;
import com.internet.cms.dao.commonlink.ICommonlinkTypeDao;
import com.internet.cms.model.commonlink.Commonlink;
import com.internet.cms.model.commonlink.CommonlinkType;
import com.internet.cms.page.Pager;

@Service("commonlinkService")
public class CommonlinkService implements ICommonlinkService {
	private ICommonlinkTypeDao commonlinkTypeDao;
	private ICommonlinkDao commonlinkDao;

	public ICommonlinkTypeDao getCommonlinkTypeDao() {
		return commonlinkTypeDao;
	}

	@Inject
	public void setCommonlinkTypeDao(ICommonlinkTypeDao commonlinkTypeDao) {
		this.commonlinkTypeDao = commonlinkTypeDao;
	}
	
	@Inject
	public void setCommonlinkDao(ICommonlinkDao commonlinkDao) {
		this.commonlinkDao = commonlinkDao;
	}

	@Override
	public boolean addCommonlinkType(CommonlinkType commonlinkType) {
        //获取最大排序id，让新增的分类排序id自动累加
		Integer maxOrderId = commonlinkTypeDao.getMaxCommonlinkTypeOrderId(commonlinkType);
		if(null == maxOrderId){
			maxOrderId = 0;
		}
		commonlinkType.setOrderId(maxOrderId+1);
		int result  = commonlinkTypeDao.addCommonlinkeType(commonlinkType);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCommonlinkType(CommonlinkType commonlinkType) {
		int result  = commonlinkTypeDao.deleteCommonlinkType(commonlinkType);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateCommonlinkType(CommonlinkType commonlinkType) {
		int result  = commonlinkTypeDao.updateCommonlinkType(commonlinkType);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public int findCommonlinkTypeCountByName(CommonlinkType commonlinkType) {
		int count  = commonlinkTypeDao.findCommonlinkTypeCountByName(commonlinkType);
		return count;
	}

	@Override
	public Pager<CommonlinkType> findCommonlinkTypeList(CommonlinkType commonlinkType,PageBounds pb) {
		//获取用户总数
		int count = commonlinkTypeDao.findCommonlinkTypeCount(commonlinkType);
		//获取用户分页列表集合信息
		List<CommonlinkType> list = commonlinkTypeDao.findCommonlinkTypeList(commonlinkType.getUserId(),pb);
		//封装用户分页的Pager对象
		Pager<CommonlinkType> pages = new Pager<CommonlinkType>(count,list);
		return pages;
	}
	
	////////////////////////////////
	public int findCommonlinkCountByNameUrl(Commonlink commonlink){
		int count  = commonlinkDao.findCommonlinkCountByNameUrl(commonlink);
		return count;
	}
	
	public boolean addCommonlink(Commonlink commonlink){
		//获取最大排序id，让新增的分类排序id自动累加
		Integer maxOrderId = commonlinkDao.getMaxCommonlinkOrderId(commonlink);
		if(null == maxOrderId){
			maxOrderId = 0;
		}
		commonlink.setOrderId(maxOrderId+1);
		int result  = commonlinkDao.addCommonlink(commonlink);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public boolean deleteCommonlink(Commonlink commonlink){
		int result  = commonlinkDao.deleteCommonlink(commonlink);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public boolean updateCommonlink(Commonlink commonlink){
		int result  = commonlinkDao.updateCommonlink(commonlink);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	public List<Commonlink> findCommonlinkList(List<String> types,CommonlinkType commonlink){
		Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("userId", commonlink.getUserId());
         params.put("types", types);
		//获取用户分页列表集合信息
		List<Commonlink> list = commonlinkDao.findCommonlinkList(params);
		return list;
	}
	
	public Pager<Commonlink> findCommonlinkListByTypeId(Commonlink commonlink, PageBounds pb){
		//获取用户总数
		int count = commonlinkDao.findCommonlinkCountByTypeId(commonlink);
		//获取用户分页列表集合信息
		List<Commonlink> list = commonlinkDao.findCommonlinkListByTypeId(commonlink,pb);
		//封装用户分页的Pager对象
		Pager<Commonlink> pages = new Pager<Commonlink>(count,list);
		return pages;
	}

}
