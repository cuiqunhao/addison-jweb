package com.internet.cms.service.user;

import java.util.List;

import com.internet.cms.model.Group;
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
	

	
}
