package com.internet.cms.service.task;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.task.ITaskDao;
import com.internet.cms.model.bookmark.BookmarkType;
import com.internet.cms.model.task.Task;
import com.internet.cms.page.Pager;

@Service("taskService")
public class TaskService implements ITaskService {
	private ITaskDao taskDao;

	public ITaskDao getTaskDao() {
		return taskDao;
	}

	@Inject
	public void setTaskDao(ITaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	@Override
	public boolean addTask(Task task) {
		int result  = taskDao.addTask(task);
		if (result >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTask(Task task,String encode) {
		int result  = taskDao.deleteTask(task.getTaskuuid(),encode);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateTask(Task task) {
		int result  = taskDao.updateTask(task);
		if (result >= 1) {
			return true;
		}
		return false;
	}	
	
	@Override
	public Pager<Task> findApplicantAppList(Task task, PageBounds pb){
		//获取用户总数
		int count = taskDao.findApplicantAppCount(task);

		//获取用户分页列表集合信息
		List<Task> list = taskDao.findApplicantAppList(task.getReserve2(),pb);
		//封装用户分页的Pager对象
		Pager<Task> pages = new Pager<Task>(count,list);
		return pages;
	}
	
	@Override
	public Pager<Task> findHandlerAppList(Task task, PageBounds pb){
		//获取用户总数
		int count = taskDao.findHandlerAppCount(task);

		//获取用户分页列表集合信息
		List<Task> list = taskDao.findHandlerAppList(task.getReserve1(),pb);
		//封装用户分页的Pager对象
		Pager<Task> pages = new Pager<Task>(count,list);
		return pages;
	}
	
	@Override
	public Pager<Task> findApplicantTaskList(Task task, PageBounds pb){
		//获取用户总数
		int count = taskDao.findApplicantTaskCount(task);

		//获取用户分页列表集合信息
		List<Task> list = taskDao.findApplicantTaskList(task,pb);
		//封装用户分页的Pager对象
		Pager<Task> pages = new Pager<Task>(count,list);
		return pages;
	}
	
	@Override
	public Pager<Task> findHandlerTaskList(Task task, PageBounds pb){
		//获取用户总数
		int count = taskDao.findHandlerTaskCount(task);

		//获取用户分页列表集合信息
		List<Task> list = taskDao.findHandlerTaskList(task,pb);
		//封装用户分页的Pager对象
		Pager<Task> pages = new Pager<Task>(count,list);
		return pages;
	}

}
