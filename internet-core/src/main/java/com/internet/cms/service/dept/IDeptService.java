package com.internet.cms.service.dept;

import com.internet.cms.model.dept.Dept;
import com.internet.cms.page.Pager;

public interface IDeptService {
	/**
	 * 根据部门id获取当前部门的信息
	 * @param id 当前部门id
	 * @return 部门的详细信息
	 */
	public Dept load(int id);
	
	/**
	 * 添加部门
	 * @param dept 部门对象
	 * @param pid 父部门
	 */
	public void add(Dept dept,Integer pid);
	/**
	 * 更新部门
	 * @param dept
	 */
	public void update(Dept dept);
	/**
	 * 删除部门
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 根据父id获取所有的子部门(根据父亲id加载部门，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父部门id
	 * @return 子部门列表信息
	 */
	public Pager<Dept> listByParent(Integer pid);

	public int findDeptCountByName(Dept dept);
}
