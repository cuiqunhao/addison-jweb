package com.internet.cms.service.commonlink;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.commonlink.Commonlink;
import com.internet.cms.model.commonlink.CommonlinkType;
import com.internet.cms.page.Pager;

public interface ICommonlinkService {
	public boolean addCommonlinkType(CommonlinkType cl);

	public boolean deleteCommonlinkType(CommonlinkType commonlinkType);

	public boolean updateCommonlinkType(CommonlinkType commonlinkType);

	public int findCommonlinkTypeCountByName(CommonlinkType commonlinkType);

	public Pager<CommonlinkType> findCommonlinkTypeList(CommonlinkType commonlinkType,PageBounds pb);
	
	//////////////////////////////////////////////

	public int findCommonlinkCountByNameUrl(Commonlink commonlink);

	public boolean addCommonlink(Commonlink commonlink);

	public boolean deleteCommonlink(Commonlink commonlink);

	public boolean updateCommonlink(Commonlink commonlink);

	public List<Commonlink> findCommonlinkList(List<String> types,CommonlinkType commonlinkType);

	public Pager<Commonlink> findCommonlinkListByTypeId(Commonlink commonlink, PageBounds pb);
	
	
	
	
}
