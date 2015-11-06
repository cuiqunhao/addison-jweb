package com.internet.cms.dto;


import java.util.List;

import com.internet.cms.model.User;

public class UserDto {
	private int id;
	/**
	 * 用户登录名称
	 */
	private String username;
	/**
	 * 用户登录密码
	 */
	private String password;
	/**
	 * 用户的中文名称
	 */
	private String nickname;
	/**
	 * 用户的邮件
	 */
	private String email;
	/**
	 * 用户的联系电话
	 */
	private String phone;
	/**
	 * 用户的状态：0表示停用，1表示启用
	 */
	private int status;
	/**
	 * 角色id
	 */
	private Integer[] roleIds;
	/**
	 * 组id
	 */
	private Integer[] groupIds;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public Integer[] getRoleIds() {
		return roleIds;
	}


	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}


	public Integer[] getGroupIds() {
		return groupIds;
	}


	public void setGroupIds(Integer[] groupIds) {
		this.groupIds = groupIds;
	}


	public User getUser() {
		User u = new User();
		u.setId(this.id);
		u.setEmail(email);
		u.setNickname(nickname);
		u.setPassword(password);
		u.setPhone(phone);
		u.setStatus(status);
		u.setUsername(username);
		return u;
	}
	
	/**
	 * 通过用户对象构造UserDto
	 * @param user
	 */
	public UserDto(User user) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhone(user.getPhone());
		this.setStatus(user.getStatus());
		this.setUsername(user.getUsername());
	}
	
	/**
	 * 通过用户对象、角色id集合、群组id集合构造UserDto对象
	 * @param user
	 * @param roleIds
	 * @param groupIds
	 */
	public UserDto(User user,Integer[] roleIds,Integer[] groupIds) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhone(user.getPhone());
		this.setStatus(user.getStatus());
		this.setUsername(user.getUsername());
		this.setGroupIds(groupIds);
		this.setRoleIds(roleIds);
	}
	public UserDto(User user,List<Integer> roleIds,List<Integer> groupIds) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhone(user.getPhone());
		this.setStatus(user.getStatus());
		this.setUsername(user.getUsername());
		this.setGroupIds(list2Array(groupIds));
		this.setRoleIds(list2Array(roleIds));
	}
	private Integer[] list2Array(List<Integer> datas) {
		Integer[] nums = new Integer[datas.size()];
		for(int i=0;i<datas.size();i++) {
			nums[i] = datas.get((int)i);
		}
		return nums;
	}
	public UserDto() {
	}
}
