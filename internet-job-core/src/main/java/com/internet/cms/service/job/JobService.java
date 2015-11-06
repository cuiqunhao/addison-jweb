package com.internet.cms.service.job;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.basic.util.DataUtil;
import com.internet.cms.dao.job.IJobDao;
import com.internet.cms.model.job.Job;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("jobService")
public class JobService implements IJobService {
	private static final Logger log = LoggerFactory.getLogger(JobService.class);
	private IJobDao jobDao;

	public IJobDao getJobDao() {
		return jobDao;
	}
	@Inject
	public void setJobDao(IJobDao jobDao) {
		this.jobDao = jobDao;
	}
	
	/**
	 * 根据任务id获取当前任务的信息
	 * @param id 当前任务id
	 * @return 任务的详细信息
	 */
	public Job load(int id) {
		return jobDao.load(id);
	}

	/**
	 * 添加任务
	 * @param job 任务对象
	 * @param pid 父任务
	 */
	public void add(Job job) {
		UUID uuid=UUID.randomUUID();
		job.setTask_code(uuid.toString());
		job.setCreatetime(DataUtil.date2Str(new Date()));
		job.setLastupdatetime(DataUtil.date2Str(new Date()));
		
		//添加任务信息
		jobDao.add(job);
		
		//启用则启动任务
		if(1 == job.getIs_enable()){
			startTask(job,uuid.toString());
		}
	}
	
	private void startTask(Job task,String taskCode){
		try {
			String cronExpress = getCronExpressionFromDB(taskCode);
			if(cronExpress.indexOf("null")==-1){
				JobDetail jobDetail = new JobDetail();
				jobDetail.setName(taskCode);
				jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
				jobDetail.setJobClass(getClassByTask(task.getJob_class()));
				CronTrigger cronTrigger = new CronTrigger("cron_" + taskCode,Scheduler.DEFAULT_GROUP, jobDetail.getName(),Scheduler.DEFAULT_GROUP);
				cronTrigger.setCronExpression(cronExpress);
				scheduler.scheduleJob(jobDetail, cronTrigger); 
			}
		} catch (ParseException e) {
			log.error("startTask ParseException", e.getMessage());
		} catch (Exception e) {
			log.error("startTask Exception", e.getMessage());
		}
	}

	/**
	 * 更新任务
	 * @param job 任务对象
	 */
	public void update(Job job) {
		String oldTaskCode = jobDao.load(job.getId()).getTask_code();
		try {
			//结束定时调度
			endTask(oldTaskCode);
			
			job.setLastupdatetime(DataUtil.date2Str(new Date()));
			UUID uuid=UUID.randomUUID();
			job.setTask_code(uuid.toString());
			jobDao.update(job);
			
			//启用则启动任务
			if(1 == job.getIs_enable()){
				startTask(job,job.getTask_code());
			}
		} catch (SchedulerException e) {
			log.error("JobService endTask SchedulerException",e);
		}
	}

	public void delete(int id) {
		Job job = jobDao.load(id);
		if(null != job){
			try {
				endTask(job.getTask_code());
				
				jobDao.delete(id);
			} catch (SchedulerException e) {
				log.error("JobService delete SchedulerException",e);
			}
		}
	}
	
	/**
	 * 根据父id获取所有的子任务(根据父亲id加载任务，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去)
	 * @param pid 父任务id
	 * @return 子任务列表信息
	 */
	public Pager<Job> listJob() {
		//获取用户总数
		int count = jobDao.listJobCount();
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("createtime.desc");
		//获取用户分页列表集合信息
		List<Job> list = jobDao.listJob(pageBounds);
		//封装用户分页的Pager对象
		Pager<Job> pages = new Pager<Job>(count,list);
		return pages;
	}
	
	/**
	 * 系统初始加载任务
	 */
	public void loadTask()throws Exception{
		List<Job> tasks = (List<Job>)jobDao.listJob();
		if(tasks.size()>0){
			for (int i = 0; i < tasks.size(); i++) {
				Job task=tasks.get(i);
				//任务开启状态 执行任务调度
				if(1 == task.getIs_enable()){
					try {
						JobDetail jobDetail = new JobDetail();
						//设置任务名称
						if(StringUtils.isNotBlank(task.getTask_code())){
							jobDetail.setName(task.getTask_code());
						}else{
							UUID uuid=UUID.randomUUID();
							jobDetail.setName(uuid.toString());
							task.setTask_code(uuid.toString());
						}
						jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
						//设置任务执行类
						jobDetail.setJobClass(getClassByTask(task.getJob_class()));
						//添加任务参数
						//jobDetail.setJobDataMap(getJobDataMap(task.getAttr()));
						CronTrigger cronTrigger = new CronTrigger("cron_" + i,Scheduler.DEFAULT_GROUP, jobDetail.getName(),Scheduler.DEFAULT_GROUP);
						
						cronTrigger.setCronExpression(getCronExpressionFromDB(task.getTask_code()));
						//调度任务
						scheduler.scheduleJob(jobDetail, cronTrigger); 
					} catch (SchedulerException e) {
						log.error("JobService SchedulerException", e.getMessage());
					} catch (ClassNotFoundException e) {
						log.error("JobService ClassNotFoundException", e.getMessage());
					} catch(Exception e){
						log.error("JobService Exception", e.getMessage());
					}
				}
			}
		}
	}
	
	public String getCronExpressionFromDB(String taskCode) throws Exception {
		//设置任务规则
		Job task = jobDao.loadByTaskCode(taskCode);
		if(null != task){
			if (Job.EXECYCLE_CRON == task.getExecycle()) {
				return task.getCron_expression();
			} else {
				Integer execycle = task.getTask_interval_unit();
				String excep="" ;
				if (execycle.equals(Job.EXECYCLE_MONTH)) {
					excep="0  "+task.getMinute() +" "+task.getHour()+" "+ task.getDay_of_month() +" * ?";
				} else if (execycle.equals(Job.EXECYCLE_WEEK)) {
					excep="0  "+task.getMinute() +" "+task.getHour()+" "+" ? " +" * "+task.getDay_of_week();
				} else if (execycle.equals(Job.EXECYCLE_DAY)) {
					excep="0  "+task.getMinute() +" "+task.getHour()+" "+" * * ?";
				} else if (execycle.equals(Job.EXECYCLE_HOUR)) {
					excep="0 0 */"+task.getInterval_hour()+" * * ?";
				} else if (execycle.equals(Job.EXECYCLE_MINUTE)) {
					excep="0  */"+task.getInterval_minute() +" * * * ?";
				}
				return excep;
			}
		}
		return "";
	}
	
	private void endTask(String taskName) throws SchedulerException{
		scheduler.deleteJob(taskName, Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 
	 * @param taskClassName 任务执行类名
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private Class getClassByTask(String taskClassName) throws ClassNotFoundException{
		return Class.forName(taskClassName);
	}

	@Autowired
	private Scheduler scheduler;

}
