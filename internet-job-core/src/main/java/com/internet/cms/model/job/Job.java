package com.internet.cms.model.job;

public class Job {
	public static String REF = "CmsTask";
	public static String PROP_USER = "user";
	public static String PROP_JOB_CLASS = "jobClass";
	public static String PROP_SITE = "site";
	public static String PROP_TYPE = "type";
	public static String PROP_INTERVAL_MINUTE = "intervalMinute";
	public static String PROP_TASK_CODE = "taskCode";
	public static String PROP_EXECYCLE = "execycle";
	public static String PROP_CRON_EXPRESSION = "cronExpression";
	public static String PROP_INTERVAL_HOUR = "intervalHour";
	public static String PROP_INTERVAL_UNIT = "intervalUnit";
	public static String PROP_DAY_OF_WEEK = "dayOfWeek";
	public static String PROP_NAME = "name";
	public static String PROP_DAY_OF_MONTH = "dayOfMonth";
	public static String PROP_HOUR = "hour";
	public static String PROP_ENABLE = "enable";
	public static String PROP_CREATE_TIME = "createTime";
	public static String PROP_MINUTE = "minute";
	public static String PROP_ID = "id";
	public static String PROP_REMARK = "remark";

	/**
	 * 任务执行周期cron表达式
	 */
	public static int EXECYCLE_CRON = 2;
	/**
	 * 任务执行周期自定义
	 */
	public static int EXECYCLE_DEFINE = 1;
	/**
	 * 执行周期-分钟
	 */
	public static int EXECYCLE_MINUTE = 1;
	/**
	 * 执行周期-小时
	 */
	public static int EXECYCLE_HOUR = 2;
	/**
	 * 执行周期-日
	 */
	public static int EXECYCLE_DAY = 3;
	/**
	 * 执行周期-月
	 */
	public static int EXECYCLE_WEEK = 4;
	/**
	 * 执行周期-月
	 */
	public static int EXECYCLE_MONTH = 5;
	/**
	 * 首页静态任务
	 */
	public static int TASK_STATIC_INDEX = 1;
	/**
	 * 栏目页静态化任务
	 */
	public static int TASK_STATIC_CHANNEL = 2;
	/**
	 * 内容页静态化任务
	 */
	public static int TASK_STATIC_CONTENT = 3;
	/**
	 * 采集类任务
	 */
	public static int TASK_ACQU = 4;
	/**
	 * 分发类任务
	 */
	public static int TASK_DISTRIBUTE = 5;
	/**
	 * 采集源ID
	 */
	public static String TASK_PARAM_ACQU_ID = "acqu_id";
	/**
	 * 分发FTP ID
	 */
	public static String TASK_PARAM_FTP_ID = "ftp_id";
	/**
	 * 站点 ID
	 */
	public static String TASK_PARAM_SITE_ID = "site_id";
	/**
	 * 栏目 ID
	 */
	public static String TASK_PARAM_CHANNEL_ID = "channel_id";

	private int id;
	private String task_code;
	private String task_type;
	private String task_name;
	private String job_class;
	private Integer execycle;
	private Integer day_of_month;
	private Integer day_of_week;
	private Integer hour;
	private Integer minute;
	private Integer interval_hour;
	private Integer interval_minute;
	private Integer task_interval_unit;
	private String cron_expression;
	private Integer is_enable;
	private String task_remark;
	private String createtime;
	private String lastupdatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTask_type() {
		return task_type;
	}

	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getJob_class() {
		return job_class;
	}

	public void setJob_class(String job_class) {
		this.job_class = job_class;
	}

	public Integer getExecycle() {
		return execycle;
	}

	public void setExecycle(Integer execycle) {
		this.execycle = execycle;
	}

	public Integer getDay_of_month() {
		return day_of_month;
	}

	public void setDay_of_month(Integer day_of_month) {
		this.day_of_month = day_of_month;
	}

	public Integer getDay_of_week() {
		return day_of_week;
	}

	public void setDay_of_week(Integer day_of_week) {
		this.day_of_week = day_of_week;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getInterval_hour() {
		return interval_hour;
	}

	public void setInterval_hour(Integer interval_hour) {
		this.interval_hour = interval_hour;
	}

	public Integer getInterval_minute() {
		return interval_minute;
	}

	public void setInterval_minute(Integer interval_minute) {
		this.interval_minute = interval_minute;
	}

	public Integer getTask_interval_unit() {
		return task_interval_unit;
	}

	public void setTask_interval_unit(Integer task_interval_unit) {
		this.task_interval_unit = task_interval_unit;
	}

	public String getCron_expression() {
		return cron_expression;
	}

	public void setCron_expression(String cron_expression) {
		this.cron_expression = cron_expression;
	}

	public Integer getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
	}

	public String getTask_remark() {
		return task_remark;
	}

	public void setTask_remark(String task_remark) {
		this.task_remark = task_remark;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(String lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public String getTask_code() {
		return task_code;
	}

	public void setTask_code(String task_code) {
		this.task_code = task_code;
	}

}
