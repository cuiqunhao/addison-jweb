package com.internet.cms.service.subscribe;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.subscribe.Subscribe;
import com.internet.cms.model.subscribe.SubscribeType;
import com.internet.cms.page.Pager;

public interface ISubscribeService {
	public boolean addSubscribeType(SubscribeType cl);

	public boolean deleteSubscribeType(SubscribeType subscribeType);

	public boolean updateSubscribeType(SubscribeType subscribeType);

	public int findSubscribeTypeCountByName(SubscribeType subscribeType);

	public Pager<SubscribeType> findSubscribeTypeList(SubscribeType subscribeType,PageBounds pb);
	
	//////////////////////////////////////////////

	public int findSubscribeCountByNameUrl(Subscribe subscribe);

	public boolean addSubscribe(Subscribe subscribe);

	public boolean deleteSubscribe(Subscribe subscribe);

	public boolean updateSubscribe(Subscribe subscribe);

	public List<Subscribe> findSubscribeList(List<String> types,SubscribeType subscribeType);

	public Pager<Subscribe> findSubscribeListByTypeId(Subscribe subscribe, PageBounds pb);
	
	
	
	
}
