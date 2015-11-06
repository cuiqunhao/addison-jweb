package com.internet.cms.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.internet.cms.dao.user.IRoleDao;
import com.internet.cms.dao.user.IUserDao;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.Role;
import com.internet.cms.model.UserRole;

@Service("roleService")
public class RoleService implements IRoleService {
	private IRoleDao roleDao;
	private IUserDao userDao;

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 删除角色信息(在删除角色之前需要判断此角色中是否含有用户信息)
	 * @param id 角色id
	 */	
	@Override
	public int delete(int id) {
		//根据角色id获取用户列表
		List<UserRole> us = userDao.listRoleUsers(id);
		if (us != null && us.size() > 0)
			throw new CmsException("删除的角色对象中还有用户，不能删除");
		//删除角色信息
		return roleDao.delete(id);
	}
	
	/**
	 * 添加角色信息
	 * @param role 角色对象
	 */
	@Override
	public int add(Role role) {
		return roleDao.add(role);
	}

	/**
	 * 更新角色信息
	 * @param role 角色对象
	 */
	@Override
	public int update(Role role) {
		return roleDao.update(role);
	}

	/**
	 * 根据指定角色id获取角色信息
	 * @param id 角色id
	 * @return 指定id的角色信息
	 */
	@Override
	public Role load(int id) {
		return roleDao.load(id);
	}

	/**
	 * 获取所有的角色列表信息
	 */
	@Override
	public List<Role> listRole() {
		return roleDao.listRoles();
	}

	/**
	 * 删除角色下的用户信息
	 * @param rid 角色id
	 */
	@Override
	public int deleteRoleUsers(int rid) {
		return userDao.deleteRoleUsers(rid);
	}

	/**
	 * 根据用户角色对应关系获取登录用户角色列表信息
	 * @param urs 当前登陆用户的用户角色列表信息
	 * @return 根据用户角色对应关系获取登录用户角色信息
	 */
	@Override
	public List<Role> listRole(List<UserRole> urs) {
		StringBuffer sf = new StringBuffer("");
		if (null != urs && urs.size() > 0) {
			sf.append("(");
			for (int i=0;i<urs.size();i++) {
				if(i== urs.size()-1){
					sf.append("'" + urs.get(i).getRoleId() + "'");
				}else{
					sf.append("'" + urs.get(i).getRoleId() + "',");
				}
			}
			sf.append(")");
			
		}
		String ids = sf.toString();
		if(!"".equals(ids) && null != ids){
			return roleDao.listRole(ids);
		}
		return new ArrayList<Role>();
	}

}
