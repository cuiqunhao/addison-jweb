package com.internet.cms.service.task;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.task.Task;
import com.internet.cms.page.Pager;

public interface ITaskService {
	public boolean addTask(Task task);

	public boolean deleteTask(Task task,String encode);

	public boolean updateTask(Task task);

	public Pager<Task> findApplicantAppList(Task task, PageBounds pb);
	
	public Pager<Task> findHandlerAppList(Task task, PageBounds pb);
	
	public Pager<Task> findApplicantTaskList(Task task, PageBounds pb);
	
	public Pager<Task> findHandlerTaskList(Task task, PageBounds pb);
	
}
