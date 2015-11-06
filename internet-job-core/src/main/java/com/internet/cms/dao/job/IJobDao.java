package com.internet.cms.dao.job;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.job.Job;

public interface IJobDao {
	/**
	 * 根据任务id获取当前任务的信息
	 * @param id 当前任务id
	 * @return 任务的详细信息
	 */
	public Job load(@Param("id") int id);
	
	/**
	 * 根据父id获取所有的子任务(根据父亲id加载任务，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父任务id
	 * @return 子任务列表信息
	 */
	public List<Job> listJob(PageBounds pageBounds);
	
	public List<Job> listJob();
	
	/**
	 * 添加任务信息
	 * @param job 任务对象
	 */
	public void add(Job job);
	
	/**
	 * 更新任务
	 * @param job 任务对象
	 */
	public void update(Job job);
	
	/**
	 * 刪除任务
	 * @param job 任务对象
	 */
	public void delete(@Param("id") int id);

	public int listJobCount();

	public Job loadByTaskCode(@Param("taskcode") String taskcode);

}
