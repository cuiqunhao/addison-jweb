package com.internet.cms.service.dept;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.basic.util.DataUtil;
import com.internet.cms.dao.dept.IDeptDao;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.dept.Dept;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("deptService")
public class DeptService implements IDeptService {
	private IDeptDao deptDao;

	public IDeptDao getDeptDao() {
		return deptDao;
	}
	@Inject
	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}
	
	/**
	 * 根据部门id获取当前部门的信息
	 * @param id 当前部门id
	 * @return 部门的详细信息
	 */
	public Dept load(int id) {
		return deptDao.load(id);
	}

	/**
	 * 添加部门
	 * @param dept 部门对象
	 * @param pid 父部门
	 */
	public void add(Dept dept, Integer pid) {
		//根据父部门获取子部门的最大的排序号
		Integer orders = deptDao.getMaxOrderByParent(pid);
		if(pid!=null&&pid>0) {
			Dept pc = deptDao.load(pid);
			if(pc==null) throw new CmsException("要添加部门的父类对象不正确!");
			dept.setParent(pc);
		}
		dept.setOrders(orders+1);
		dept.setCreatetime(DataUtil.date2Str(new Date()));
		dept.setLastupdatetime(DataUtil.date2Str(new Date()));
		//添加部门信息
		deptDao.add(dept);
	}

	/**
	 * 更新部门
	 * @param dept 部门对象
	 */
	public void update(Dept dept) {
		//更新部门
		deptDao.update(dept);
	}

	public void delete(int id) {
		//1、需要判断是否存在子部门
		int count = deptDao.listByParentCount(id);
		if(count>0) throw new CmsException("要删除的部门还有子部门，无法删除");
		deptDao.delete(id);
	}
	
	/**
	 * 根据父id获取所有的子部门(根据父亲id加载部门，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父部门id
	 * @return 子部门列表信息
	 */
	public Pager<Dept> listByParent(Integer pid) {
		//获取用户总数
		int count = deptDao.listByParentCount(pid);
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
		//获取用户分页列表集合信息
		List<Dept> list = deptDao.listByParent(pid,pageBounds);
		//封装用户分页的Pager对象
		Pager<Dept> pages = new Pager<Dept>(count,list);
		return pages;
	}
	
	public int findDeptCountByName(Dept dept){
		int count = deptDao.findDeptCountByName(dept);
		return count;
	}
}
