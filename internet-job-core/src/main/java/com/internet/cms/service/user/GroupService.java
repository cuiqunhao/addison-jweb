package com.internet.cms.service.user;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.user.IGroupDao;
import com.internet.cms.dao.user.IUserDao;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.Group;
import com.internet.cms.model.UserGroup;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("groupService")
public class GroupService implements IGroupService{
	private IGroupDao groupDao;
	private IUserDao userDao;
	
	public IGroupDao getGroupDao() {
		return groupDao;
	}
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 添加群组
	 * @param group 群组对象
	 */
	@Override
	public int add(Group group) {
		return groupDao.add(group);
	}

	/**
	 * 删除群组信息(在删除之前先判断群组中是否有用户信息)
	 * @param id 群组id
	 */
	@Override
	public int delete(int id){
		//根据群组id获取群组下用户的信息(在删除之前先判断群组中是否有用户信息)
		List<UserGroup> users = userDao.listGroupUsers(id);
		if(null !=users && users.size()>0) throw new CmsException("删除的组中还有用户，不能删除");
		try{
			//根据群组id删除群组信息
			return groupDao.delete(id);
		}catch(Exception e){
			throw new CmsException("删除的组中有栏目，不能删除");
		}
	}

	/**
	 * 根据群组id获取群组信息
	 * @param id 群组id
	 * @return 群组
	 */
	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}

	/**
	 * 更新群组信息
	 * @param group 群组对象
	 */
	@Override
	public int update(Group group) {
		return groupDao.update(group);
	}
	
	/**
	 * 获取群组列表信息
	 * @return 群组列表信息
	 */
	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}
	
	/**
	 * 获取分页群组列表信息
	 * @return 分页群组列表信息
	 */
	@Override
	public Pager<Group> findGroup() {
		//获取群组总数
		int count = groupDao.findGroupCount();
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("id.desc");
		//获取用户分页列表集合信息
		List<Group> list = groupDao.findGroup(pageBounds);
		//封装用户分页的Pager对象
		Pager<Group> pages = new Pager<Group>(count,list);
		return pages;
	}

	/**
	 * 删除群组中的用户信息
	 * @param gid 群组id
	 */
	@Override
	public int deleteGroupUsers(int gid) {
		return userDao.deleteGroupUsers(gid);
	}
	
	
	
	
	
	
	

}
