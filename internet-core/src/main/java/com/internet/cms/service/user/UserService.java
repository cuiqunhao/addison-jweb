package com.internet.cms.service.user;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.basic.util.SecurityUtil;
import com.internet.cms.dao.user.IGroupDao;
import com.internet.cms.dao.user.IRoleDao;
import com.internet.cms.dao.user.IUserDao;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.Group;
import com.internet.cms.model.Role;
import com.internet.cms.model.User;
import com.internet.cms.model.UserGroup;
import com.internet.cms.model.UserRole;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service
public class UserService implements IUserService {
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;
	

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	private void addUserRole(User user,int rid) {
		//1、检查角色对象是否存在，如果不存在，就抛出异常
		Role role = roleDao.load(rid);
		if(role==null) throw new CmsException("要添加的用户角色不存在");
		//2、检查用户角色对象是否已经存在，如果存在，就不添加
		UserRole ur = userDao.loadUserRole(user.getId(), role.getId());
		if(ur!=null) return;
		userDao.addUserRole(user.getId(), rid);
	}
	
	private void addUserGroup(User user,int gid) {
		//1、检查组对象是否存在，如果不存在，就抛出异常
		Group group = groupDao.load(gid);
		if(group==null) throw new CmsException("要添加用户的组对象不存在");
		//2、检查用户 组对象是否已经存在，如果存在，就不添加
		UserGroup ug = userDao.loadUserGroup(user.getId(), group.getId());
		if(ug!=null) return;
		userDao.addUserGroup(user.getId(), gid);
	}

	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		User tu = userDao.loadByUsername(user.getUsername());
		if(tu!=null)throw new CmsException("添加的用户对象已经存在，不能添加");
		user.setCreateDate(new Date());
		try {
			user.setPassword(SecurityUtil.md5(user.getUsername(),user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			throw new CmsException("密码加密失败:"+e.getMessage());
		}
		userDao.add(user);
		user = userDao.loadByUsername(user.getUsername());
		//添加角色对象
		for(Integer rid:rids) {
			this.addUserRole(user, rid);
		}
		//添加用户组对象
		for(Integer gid:gids) {
			addUserGroup(user, gid);
		}
	}

	@Override
	public void delete(int id) {
		//TODO 需要进行用户是否有文章的判断
		
		//1、删除用户管理的角色对象
		userDao.deleteUserGroups(id);
		//2、删除用户管理的组对象
		userDao.deleteUserRoles(id);
		//3、删除用户
		userDao.delete(id);
	}

	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		//1、获取用户已经存在的组id和角色id
		List<UserRole> listUserRole = userDao.listUserRoleIds(user.getId());
		List<Integer> erids = new ArrayList<Integer>();
		for(UserRole userrole: listUserRole){
			erids.add(userrole.getRoleId());
		}
		
		List<UserGroup> listUserGroup =  userDao.listUserGroupIds(user.getId());
		List<Integer> egids = new ArrayList<Integer>();
		for(UserGroup usergroup: listUserGroup){
			egids.add(usergroup.getGroupId());
		}

		//2、判断，如果erids中不存在rids就要进行添加
		for(Integer rid:rids) {
			if(!erids.contains(rid)) {
				addUserRole(user, rid);
			}
		}
		for(Integer gid:gids) {
			if(!egids.contains(gid)) {
				addUserGroup(user,gid);
			}
		}
		
		//3、进行删除
		for(Integer erid:erids) {
			if(!ArrayUtils.contains(rids, erid)) {
				userDao.deleteUserRole(user.getId(), erid);
			}
		}
		
		for(Integer egid:egids) {
			if(!ArrayUtils.contains(gids, egid)) {
				userDao.deleteUserGroup(user.getId(), egid);
			}
		}
	}

	@Override
	public void updateStatus(int id) {
		User u = userDao.load(id);
		if(u==null) throw new CmsException("修改状态的用户不存在");
		if(u.getStatus()==0) u.setStatus(1);
		else u.setStatus(0);
		userDao.update(u);
	}

	/**
	 * 获取用户分页列表集合信息
	 */
	@Override
	public Pager<User> findUser() {
		//获取用户总数
		int count = userDao.findUserCount();
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("create_date.desc");
		//获取用户分页列表集合信息
		List<User> list = userDao.findUser(pageBounds);
		//封装用户分页的Pager对象
		Pager<User> pages = new Pager<User>(count,list);
		return pages;
	}

	/**
	 * 根据用户id获取用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	@Override
	public User load(int id) {
		return userDao.load(id);
	}
	
	/**
	 * 根据用户id获取此用户所对应所有的角色id集合
	 * @param id 用户id
	 * @return 获取此用户所对应所有的角色id集合
	 */
	@Override
	public List<Integer> listUserRoleIds(int id) {
		List<UserRole> listUserRole = userDao.listUserRoleIds(id);
		List<Integer> ids = new ArrayList<Integer>();
		for(UserRole userrole: listUserRole){
			ids.add(userrole.getRoleId());
		}
		return ids;
	}
	
	/**
	 * 根据用户id获取此用户所对应所有的群组id集合
	 * @param id 用户id
	 * @return 获取此用户所对应所有的群组id集合
	 */
	@Override
	public List<Integer> listUserGroupIds(int id) {
		List<UserGroup> listUserGroup = userDao.listUserGroupIds(id);
		List<Integer> ids = new ArrayList<Integer>();
		for(UserGroup usergroup: listUserGroup){
			ids.add(usergroup.getGroupId());
		}
		return ids;
	}
	
	

	@Override
	public List<Role> listRolesByUid(int id) {
		return roleDao.listRolesByUid(id);
	}

	@Override
	public List<Group> listGroupsByUid(int id) {
		return groupDao.listGroupsByUid(id);
	}
	
	
	@Override
	public List<UserGroup> listGroupUsers(int gid) {
		return userDao.listGroupUsers(gid);
	}
	@Override
	public List<UserRole> listRoleUsers(int rid) {
		return userDao.listRoleUsers(rid);
	}
	@Override
	public User login(String username, String password) {
		User user = userDao.loadByUsername(username);
		if(user==null) throw new CmsException("用户名或者密码不正确");
		try {
			if(!SecurityUtil.md5(username,password).equals(user.getPassword())) {
				throw new CmsException("用户名或者密码不正确");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new CmsException("密码加密失败:"+e.getMessage());
		}
		if(user.getStatus()==0) throw new CmsException("用户已经停用，请与管理员联系");
		return user;
	}
	
	/**
	 * 更新用户基本信息
	 * @param user 用户对象
	 */
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	@Override
	public void updatePwd(int uid, String oldPwd, String newPwd) {
		try {
			User u = userDao.load(uid);
			if(!SecurityUtil.md5(u.getUsername(),oldPwd).equals(u.getPassword())) {
				throw new CmsException("原始密码输入不正确");
			}
			u.setPassword(SecurityUtil.md5(u.getUsername(), newPwd));
			userDao.update(u);
		} catch (NoSuchAlgorithmException e) {
			throw new CmsException("更新密码失败:"+e.getMessage());
		}
	}
	@Override
	public List<UserRole> listUserRoles(int id) {
		return userDao.listUserRoles(id);
	}
	@Override
	public List<User> listUsersByRid(int id) {
		return userDao.listUsersByRid(id);
	}
	
	public List<User> listUsersByGid(int id){
		return userDao.listUsersByGid(id);
	}

}
