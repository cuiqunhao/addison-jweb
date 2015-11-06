package com.internet.cms.service.user;

import java.util.List;

import com.internet.cms.model.Group;
import com.internet.cms.model.Role;
import com.internet.cms.model.User;
import com.internet.cms.model.UserGroup;
import com.internet.cms.model.UserRole;
import com.internet.cms.page.Pager;

public interface IUserService {
	/**
	 * 添加用户，需要判断用户名是否存在，如果存在抛出异常
	 * @param user 用户对象
	 * @param rids 用户的所有角色信息
	 * @param gids 用户的所有组信息
	 */
	public void add(User user,Integer[]rids,Integer[]gids);
	
	/**
	 * 根据用户id获取用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	public User load(int id);
	
	/**
	 * 根据用户id获取此用户所对应所有的角色id集合
	 * @param id 用户id
	 * @return 获取此用户所对应所有的角色id集合
	 */
	public List<Integer> listUserRoleIds(int id);
	
	/**
	 * 根据用户id获取此用户所对应所有的群组id集合
	 * @param id 用户id
	 * @return 获取此用户所对应所有的群组id集合
	 */
	public List<Integer> listUserGroupIds(int id);
	
	/**
	 * 删除用户，注意需要把用户和角色和组的对应关系删除
	 * 如果用户存在相应的文章不能删除
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 用户的更新，如果rids中的角色在用户中已经存在，就不做操作
	 * 如果rids中的角色在用户中不存在就要添加，如果用户中的角色不存在于rids中需要进行删除
	 * 对于group而已同样要做这个操作
	 * @param user
	 * @param rids
	 * @param gids
	 */
	public void update(User user,Integer[] rids,Integer[] gids);
	
	/**
	 * 更新用户基本信息
	 * @param user 用户对象
	 */
	public void update(User user);
	
	
	/**
	 * 更新密码方法
	 * @param uid
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(int uid,String oldPwd,String newPwd);
	/**
	 * 更新用户的状态
	 * @param id
	 */
	public void updateStatus(int id);
	/**
	 * 获取用户分页列表集合信息
	 * @return 获取用户分页列表集合信息
	 */
	public Pager<User> findUser();
	
	/**
	 * 获取用户的所有角色信息
	 * @param id
	 * @return
	 */
	public List<Role> listRolesByUid(int id);
	
	public List<UserRole> listUserRoles(int id);
	/**
	 * 获取用户的所有组信息
	 * @param id
	 * @return
	 */
	public List<Group> listGroupsByUid(int id);
	
	
	
	public List<UserGroup> listGroupUsers(int gid);
	public List<UserRole> listRoleUsers(int rid);
	
	public User login(String username,String password);

	public List<User> listUsersByRid(int id);

	public List<User> listUsersByGid(int id);
}
