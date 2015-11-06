package com.internet.cms.dao.subscribe;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.subscribe.Subscribe;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface ISubscribeDao{
	public int findSubscribeCountByNameUrl(Subscribe subscribe);

	public Integer getMaxSubscribeOrderId(Subscribe subscribe);

	public int addSubscribe(Subscribe subscribe);

	public int deleteSubscribe(Subscribe subscribe);

	public int updateSubscribe(Subscribe subscribe);

	public int findSubscribeCount(Subscribe subscribe);

	public List<Subscribe> findSubscribeList( Map<String, Object> params);

	public int findSubscribeCountByTypeId(Subscribe subscribe);

	public List<Subscribe> findSubscribeListByTypeId(Subscribe subscribe, PageBounds pb);
}
