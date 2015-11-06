package com.internet.cms.service.topic;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.topic.IChannelDao;
import com.internet.cms.model.Channel;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.ChannelType;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.Topic;
import com.internet.cms.model.dept.Dept;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("channelService")
public class ChannelService implements IChannelService {
	private IChannelDao channelDao;
	private ITopicService topicService;
	
	

	public ITopicService getTopicService() {
		return topicService;
	}
	
	@Inject
	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	
	/**
	 * 根据栏目id获取当前栏目的信息
	 * @param id 当前栏目id
	 * @return 栏目的详细信息
	 */
	public Channel load(int id) {
		return channelDao.load(id);
	}
	
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 */
	@Override
	public List<ChannelTree> generateTree() {
		List<ChannelTree> cts =  channelDao.generateTree();
		initTreeNode(cts);
		return cts;
	}

	/**
	 * 添加栏目
	 * @param channel 栏目对象
	 * @param pid 父栏目
	 */
	public void add(Channel channel, Integer pid) {
		//根据父栏目获取子栏目的最大的排序号
		Integer orders = channelDao.getMaxOrderByParent(pid);
		if(pid!=null&&pid>0) {
			Channel pc = channelDao.load(pid);
			if(pc==null) throw new CmsException("要添加栏目的父类对象不正确!");
			channel.setParent(pc);
		}
		String name = channel.getShowTypeName();
		if("NAV_CHANNEL".equals(name)){
			channel.setCatalogType(1);
		}else if("TOPIC_LIST".equals(name)){
			channel.setCatalogType(2);
		}else if("TOPIC_CONTENT".equals(name)){
			channel.setCatalogType(3);
		}else if("TOPIC_IMG".equals(name)){
			channel.setCatalogType(4);
		}
		channel.setOrders(orders+1);
		//添加栏目信息
		channelDao.add(channel);
	}

	/**
	 * 更新栏目
	 * @param channel 栏目对象
	 */
	public void update(Channel channel) {
		String name = channel.getShowTypeName();
		if("NAV_CHANNEL".equals(name)){
			channel.setCatalogType(1);
		}else if("TOPIC_LIST".equals(name)){
			channel.setCatalogType(2);
		}else if("TOPIC_CONTENT".equals(name)){
			channel.setCatalogType(3);
		}else if("TOPIC_IMG".equals(name)){
			channel.setCatalogType(4);
		}
		//更新栏目
		channelDao.update(channel);
	}

	public void delete(int id) {
		//1、需要判断是否存在子栏目
		List<Channel> cs = channelDao.listByParent(id);
		if(cs!=null&&cs.size()>0) throw new CmsException("要删除的栏目还有子栏目，无法删除");
		//2、需要判断是否存在文章
		List<Topic> ts = topicService.listTopicByChannel(id);
		if(null != ts && ts.size()>0) {
			throw new CmsException("该栏目还有相应的文章信息，不能删除");
		}
		//3、需要删除和组的关联关系
		channelDao.deleteChannelGroups(id);
		channelDao.delete(id);
	}

	public void clearTopic(int id) {
		List<Topic> tops = topicService.listTopicByChannel(id);
		for(Topic t:tops) {
			topicService.delete(t.getId());
		}
	}

	
	/**
	 * 根据父id获取所有的子栏目(根据父亲id加载栏目，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父栏目id
	 * @return 子栏目列表信息
	 */
	public List<Channel> listByParent(Integer pid) {
		return channelDao.listByParent(pid);
	}
	
	/**
	 * 获取所有的导航栏目，栏目的状态必须为已经启用
	 * @return 导航栏目列表信息
	 */
	@Override
	public List<Channel> listTopNavChannel() {
		return channelDao.listTopNavChannel();
	}
	
	
	/**
	 * 初始化顶级根节点及所有父级节点
	 * @param cts
	 */
	public static void initTreeNode(List<ChannelTree> cts) {
		cts.add(0,new ChannelTree(Channel.ROOT_ID,Channel.ROOT_NAME,-1));
		for(ChannelTree ct:cts) {
			if(ct.getPid()==null)ct.setPid(0);
		}
	}
	
	@Override
	public List<ChannelTree> generateTreeByParent(Integer pid) {
		return channelDao.generateTreeByParent(pid);
	}
	@Override
	public void updateSort(Integer[] ids) {
		channelDao.updateSort(ids);
	}
	
	
	
	
	
	
	
	
	/**
	 * 获取所有的可以发布文章的栏目，栏目的状态必须为启用状态
	 */
	@Override
	public List<Channel> listPublishChannel() {
		return channelDao.listPublishChannel(ChannelType.NAV_CHANNEL.ordinal());
	}
	
	
	@Override
	public List<Channel> listAllIndexChannel(ChannelType ct) {
		String name = ct.getName();
		int type = 2;
		if("导航栏目".equals(name)){
			type = 1;
		}else if("文章列表栏目".equals(name)){
			type = 2;
		}else if("文章内容栏目".equals(name)){
			type = 3;
		}else if("图片列表栏目".equals(name)){
			type = 4;
		}
		return channelDao.listAllIndexChannel(type);
	}

	@Override
	public Channel loadFirstChannelByNav(int cid) {
		return channelDao.loadFirstChannelByNav(cid);
	}

	@Override
	public List<Channel> listUseChannelByParent(Integer cid) {
		return channelDao.listUseChannelByParent(cid);
	}

	@Override
	public List<Channel> listChannelByType(ChannelType ct) {
		return channelDao.listChannelByType(ct);
	}
	
	public Pager<Channel> listChannelByParent(Integer pid){
		//获取栏目总数
		int count = channelDao.listChannelByParentCount(pid);
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("orders.desc");
		//获取栏目分页列表集合信息
		List<Channel> list = channelDao.listChannelByParent(pid,pageBounds);
		//封装栏目分页的Pager对象
		Pager<Channel> pages = new Pager<Channel>(count,list);
		return pages;
	}
	

}
