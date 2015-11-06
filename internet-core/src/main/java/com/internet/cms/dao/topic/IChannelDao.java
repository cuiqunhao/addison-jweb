package com.internet.cms.dao.topic;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.Channel;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.ChannelType;
import com.internet.cms.model.GroupChannel;

public interface IChannelDao {
	/**
	 * 根据栏目id获取当前栏目的信息
	 * @param id 当前栏目id
	 * @return 栏目的详细信息
	 */
	public Channel load(@Param("id") Integer id);
	
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return 所有的栏目列表信息
	 */
	public List<ChannelTree> generateTree();
	
	/**
	 * 根据父id获取所有的子栏目(根据父亲id加载栏目，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父栏目id
	 * @return 子栏目列表信息
	 */
	public List<Channel> listByParent(@Param("pid") Integer pid);
	/**
	 * 获取子栏目的最大的排序号
	 * @param pid 父栏目id
	 * @return 子栏目的最大的排序号
	 */
	public int getMaxOrderByParent(@Param("pid") Integer pid);
	
	/**
	 * 添加栏目信息
	 * @param channel 栏目对象
	 */
	public void add(Channel channel);
	
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
	 * 通过一个数组来完成排序
	 * @param ids
	 */
	public void updateSort(Integer[] ids);
	
	
	
	/**
	 * 所有的可以发布文章的栏目，栏目的状态必须为启用状态
	 * @param type 启用状态
	 * @return 栏目列表
	 */
	public List<Channel> listPublishChannel(@Param("catalogType") int type);
	/**
	 * 根据栏目类型获取所有的首页栏目
	 * @return
	 */
	public List<Channel> listAllIndexChannel(@Param("catalogType") int type);
	
	
	/**
	 * 删除频道和组的对应关系
	 * @param cid
	 * @return
	 */
	public void deleteChannelGroups(@Param("channelId") int channelId);
	/**
	 * 获取导航栏目中的第一个栏目
	 * @param cid
	 * @return
	 */
	public Channel loadFirstChannelByNav(@Param("pid") int pid);
	
	public List<Channel> listUseChannelByParent(@Param("pid") Integer cid);
	/**
	 * 通过类型来获取所有未停用的栏目
	 * @param ct
	 * @return
	 */
	public List<Channel> listChannelByType(ChannelType ct);
	
	/**
	 * 更新栏目
	 * @param channel 栏目对象
	 */
	public void update(Channel channel);
	public void delete(@Param("id") int id);

	/**
	 * 获取某个组的栏目树
	 * @param gid 群组id
	 * @return 某个组的栏目树
	 */
	public List<ChannelTree> generateGroupChannelTree(@Param("groupId") int gid);

	/**
	 * 获取某个用户的栏目树
	 * @param uid 用户id
	 * @return 某个用户的栏目树
	 */
	public List<ChannelTree> generateUserChannelTree(@Param("userId") int uid);
	
	/**
	 * 删除群组栏目
	 * @param gid 群组id 
	 * @param cid 栏目id
	 */
	public void deleteGroupChannel(@Param("groupId") int gid, @Param("channelId") int cid);

	/**
	 * 清空组所管理的栏目
	 * @param gid 群组id
	 */
	public void clearGroupChannel(@Param("groupId") int gid);

	/**
	 * 根据群组id获取某个组的所有管理栏目的id集合
	 * @param gid 群组id
	 * @return 所对应的栏目id集合信息
	 */
	public List<GroupChannel> listGroupChannelIds(@Param("groupId") int groupId);

	/**
	 * 加载GroupChannel对象
	 * @param groupId 群组id
	 * @param channelId 栏目id
	 * @return 群组栏目对象
	 */
	public GroupChannel loadGroupChannel(@Param("groupId") int groupId, @Param("channelId") int channelId);
	
	/**
	 * 添加群组栏目对象
	 * @param groupId 群组id
	 * @param channelId 栏目id
	 */
	public void addGroupChannel(@Param("groupId") int groupId, @Param("channelId") int channelId);

	/**
	 * 根据栏目id查询当前栏目是否是首页栏目（如果是首页则需要重新更新首页栏目）
	 * @param channelId 栏目id
	 * @return 存在则返回1
	 */
	public int isUpdateIndex(@Param("channelId") int channelId);

	public int listChannelByParentCount(@Param("pid") Integer pid);

	public List<Channel> listChannelByParent(@Param("pid") Integer pid, PageBounds pageBounds);

}
