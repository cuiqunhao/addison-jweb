package com.internet.cms.dao.commonlink;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.commonlink.Commonlink;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface ICommonlinkDao{
	public int findCommonlinkCountByNameUrl(Commonlink commonlink);

	public Integer getMaxCommonlinkOrderId(Commonlink commonlink);

	public int addCommonlink(Commonlink commonlink);

	public int deleteCommonlink(Commonlink commonlink);

	public int updateCommonlink(Commonlink commonlink);

	public int findCommonlinkCount(Commonlink commonlink);

	public List<Commonlink> findCommonlinkList( Map<String, Object> params);

	public int findCommonlinkCountByTypeId(Commonlink commonlink);

	public List<Commonlink> findCommonlinkListByTypeId(Commonlink commonlink, PageBounds pb);
}
