package com.internet.cms.service;

public interface IIndexService {
	/**
	 * 重新生成顶部导航(获取所有的导航栏目，栏目的状态必须为已经启用)
	 */
	public void generateTop();

	public void generateBottom();

	public void generateBody();
}
