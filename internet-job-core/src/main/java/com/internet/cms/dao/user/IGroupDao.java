package com.internet.cms.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.Group;

public interface IGroupDao {
	/**
	 * 获取群组列表信息
	 * @return 群组列表信息
	 */
	public List<Group> listGroup();

	/**
	 * 获取分页群组列表信息
	 * @return 分页群组列表信息
	 */
	public List<Group> findGroup(PageBounds pageBounds);
	
	/**
	 * 添加群组
	 * @param group 群组对象
	 */
	public int add(Group group);
	
	/**
	 * 获取群组总数信息
	 * @return 群组总数
	 */
	public int findGroupCount();
	
	/**
	 * 根据群组id获取群组信息
	 * @param gid 群组id
	 * @return 群组信息
	 */
	public Group load(int gid);
	
	/**
	 * 根据群组id删除群组信息
	 * @param gid 群组id
	 */
	public int delete(@Param("groupId") int gid);

	/**
	 * 更新群组信息
	 * @param group 群组对象
	 * @return 更新是否成功
	 */
	public int update(Group group);

	public List<Group> listGroupsByUid(@Param("uid") int id);

}
