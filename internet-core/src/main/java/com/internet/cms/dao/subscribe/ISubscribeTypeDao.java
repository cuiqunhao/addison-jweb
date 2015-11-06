package com.internet.cms.dao.subscribe;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.subscribe.SubscribeType;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface ISubscribeTypeDao{
	public int addSubscribeType(SubscribeType subscribeType);

	public int deleteSubscribeType(SubscribeType subscribeType);

	public int updateSubscribeType(SubscribeType subscribeType);

	public int findSubscribeTypeCountByName(SubscribeType subscribeType);

	public List<SubscribeType> findSubscribeTypeList(String userId,PageBounds pageBounds);

	public Integer getMaxSubscribeTypeOrderId(SubscribeType subscribeType);

	public int findSubscribeTypeCount(SubscribeType subscribeType);
	
}
