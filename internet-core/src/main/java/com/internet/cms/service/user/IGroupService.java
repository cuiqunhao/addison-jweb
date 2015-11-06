package com.internet.cms.service.user;

import java.util.List;

import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.Group;
import com.internet.cms.model.GroupChannel;
import com.internet.cms.page.Pager;

public interface IGroupService {
	/**
	 * 获取分页群组列表信息
	 * @return 分页群组列表信息
	 */
	public Pager<Group> findGroup();
	
	/**
	 * 添加群组
	 * @param group 群组对象
	 */
	public int add(Group group);
	
	/**
	 * 获取群组列表信息
	 * @return 群组列表信息
	 */
	public List<Group> listGroup();

	/**
	 * 删除群组信息(在删除之前先判断群组中是否有用户信息)
	 * @param id 群组id
	 */
	public int delete(int id);
	
	/**
	 * 更新群组信息
	 * @param group 群组对象
	 */
	public int update(Group group);

	/**
	 * 根据群组id获取群组信息
	 * @param id 群组id
	 * @return 群组
	 */
	public Group load(int id);
	
	/**
	 * 删除群组中的用户信息
	 * @param gid 群组id
	 */
	public int deleteGroupUsers(int gid);
	
	/**
	 * 添加群组栏目对象
	 * @param gid 群组id
	 * @param cid 栏目id
	 */
	public void addGroupChannel(int gid,int cid);
	
	/**
	 * 加载GroupChannel对象
	 * @param gid 群组id
	 * @param cid 栏目id
	 * @return 群组栏目对象
	 */
	public GroupChannel loadGroupChannel(int gid,int cid);
	
	/**
	 * 根据群组id获取某个组的所有管理栏目的id集合
	 * @param gid 群组id
	 * @return 所对应的栏目id集合信息
	 */
	public List<Integer> listGroupChannelIds(int gid);
	
	/**
	 * 获取某个组的栏目树
	 * @param gid 群组id
	 * @return 某个组的栏目树
	 */
	public List<ChannelTree> generateGroupChannelTree(int gid);
	
	/**
	 * 获取某个用户的栏目树
	 * @param uid 用户id
	 * @return 某个用户的栏目树
	 */
	public List<ChannelTree> generateUserChannelTree(int uid);
	
	/**
	 * 删除群组栏目
	 * @param gid 群组id 
	 * @param cid 栏目id
	 */
	public void deleteGroupChannel(int gid,int cid);
	
	/**
	 * 清空组所管理的栏目
	 * @param gid 群组id
	 */
	public void clearGroupChannel(int gid);
	
}
