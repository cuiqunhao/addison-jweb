package com.internet.cms.dao.task;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.task.Task;

/**
 * add方法可以在service中写，我们让排序号自动向后加
 * @author Administrator
 *
 */
public interface ITaskDao{
	public int addTask(Task task);

	public int deleteTask(String taskuuid,String encode);

	public int updateTask(Task task);

	public int findApplicantAppCount(Task task);

	public List<Task> findApplicantAppList(String reserve2, PageBounds pb);

	public int findHandlerAppCount(Task task);

	public List<Task> findHandlerAppList(String reserve1, PageBounds pb);
	
	public int findApplicantTaskCount(Task task);

	public List<Task> findApplicantTaskList(Task task, PageBounds pb);
	
	public int findHandlerTaskCount(Task task);

	public List<Task> findHandlerTaskList(Task task, PageBounds pb);

	
	
}
