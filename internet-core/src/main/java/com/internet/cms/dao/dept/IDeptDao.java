package com.internet.cms.dao.dept;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.dept.Dept;

public interface IDeptDao {
	/**
	 * 根据部门id获取当前部门的信息
	 * @param id 当前部门id
	 * @return 部门的详细信息
	 */
	public Dept load(@Param("id") Integer id);
	
	/**
	 * 根据父id获取所有的子部门(根据父亲id加载部门，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父部门id
	 * @return 子部门列表信息
	 */
	public List<Dept> listByParent(@Param("pid") Integer pid,PageBounds pageBounds);
	
	/**
	 * 添加部门信息
	 * @param dept 部门对象
	 */
	public void add(Dept dept);
	
	/**
	 * 更新部门
	 * @param dept 部门对象
	 */
	public void update(Dept dept);
	
	/**
	 * 刪除部门
	 * @param dept 部门对象
	 */
	public void delete(@Param("id") int id);

	/**
	 * 根据父部门获取子部门的最大的排序号
	 * @param pid 
	 * @return
	 */
	public Integer getMaxOrderByParent(@Param("pid") Integer pid);

	public int listByParentCount(@Param("pid") Integer pid);

	public int findDeptCountByName(Dept dept);

}
