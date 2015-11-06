package com.internet.cms.model.dept;

public class Dept {
	public static final String ROOT_NAME = "数据字典";
	public static final int ROOT_ID = 0;
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 英文名称
	 */
	private String name_en;
	
	/**
	 * 中文名称
	 */
	private String name_cn;
	
	/**
	 * 状态，0表示启用，1表示停止
	 */
	private int status;
	
	/**
	 * 序号
	 */
	private int orders;
	
	/**
	 * 创建时间
	 */
	private String createtime;
	
	/**
	 * 最后修改时间
	 */
	private String lastupdatetime;
	
	/**
	 * 父类栏目
	 */
	private Dept parent;
	
	/**
	 * 父id
	 */
	private int pid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_cn() {
		return name_cn;
	}

	public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
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

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	
	
}
