package com.internet.cms.service.topic;

import java.util.List;

import com.internet.cms.model.Channel;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.ChannelType;
import com.internet.cms.page.Pager;

public interface IChannelService {
	/**
	 * 根据栏目id获取当前栏目的信息
	 * @param id 当前栏目id
	 * @return 栏目的详细信息
	 */
	public Channel load(int id);
	
	/**
	 * 添加栏目
	 * @param channel 栏目对象
	 * @param pid 父栏目
	 */
	public void add(Channel channel,Integer pid);
	/**
	 * 更新栏目
	 * @param channel
	 */
	public void update(Channel channel);
	/**
	 * 删除栏目
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 清空栏目中的文章
	 * @param id
	 */
	public void clearTopic(int id);
	
	
	/**
	 * 根据父id获取所有的子栏目(根据父亲id加载栏目，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父栏目id
	 * @return 子栏目列表信息
	 */
	public List<Channel> listByParent(Integer pid);
	
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return 所有的栏目列表信息
	 */
	public List<ChannelTree> generateTree();
	
	/**
	 * 获取所有的导航栏目，栏目的状态必须为已经启用
	 * @return 导航栏目列表信息
	 */
	public List<Channel> listTopNavChannel();
	
	
	/**
	 * 根据父类对象获取子类栏目，并且生成树列表
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateTreeByParent(Integer pid);
	/**
	 * 获取所有的可以发布文章的栏目，栏目的状态必须为启用状态
	 * @return 栏目列表
	 */
	public List<Channel> listPublishChannel();
	
	
	public void updateSort(Integer[] ids);
	
	/**
	 * 获取所有的首页显示栏目目的是进行页面静态化
	 * @param ct
	 * @return
	 */
	public List<Channel> listAllIndexChannel(ChannelType ct);
	
	public Channel loadFirstChannelByNav(int cid);
	
	public List<Channel> listUseChannelByParent(Integer cid);
	
	/**
	 * 通过类型来获取所有未停用的栏目
	 * @param ct
	 * @return
	 */
	public List<Channel> listChannelByType(ChannelType ct);
	
	public Pager<Channel> listChannelByParent(Integer pid);
}
