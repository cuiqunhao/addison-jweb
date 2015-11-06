package com.internet.cms.dao.topic;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.Topic;
import com.internet.cms.page.Pager;

public interface ITopicDao{
	/**
	 * 根据栏目和标题和状态进行文章的检索
	 * @param uid 用户id
	 * @param cid 栏目id
	 * @param title 标题
	 * @param status 状态
	 * @return 文章列表
	 */
	public List<Topic> find(@Param("uid") Integer uid,@Param("cid") Integer cid,@Param("title") String title,@Param("status") int status,PageBounds pageBounds);
	
	/**
	 * 根据栏目和标题和状态进行文章的检索总数
	 * @param cid 栏目id
	 * @param title 标题
	 * @param status 状态
	 * @return 文章列表总数
	 */
	public int findCount(@Param("uid") Integer uid,@Param("cid") Integer cid,@Param("title") String title,@Param("status") int status);
	
	/**
	 * 添加文章
	 * @param topic 文章对象
	 */
	public void add(Topic topic);
	
	/**
	 * 根据文章id获取文章对象信息 
	 * @param id 文章id
	 * @return 文章对象
	 */
	public Topic load(@Param("id") int id);
	
	/**
	 * 更新文章
	 * @param topic 要更新的文章对象
	 */
	public void update(Topic topic);
	
	/**
	 * 根据文章id删除文章
	 * @param id 文章id
	 */
	public void delete(@Param("id") int id);
	
	
	/**
	 * 根据关键字进行文章的检索，仅仅只是检索关键字类似的
	 * @param keyword
	 * @return
	 */
	public Pager<Topic> searchTopicByKeyword(String keyword);
	/**
	 * 通过某个条件来检索，该条件会在标题，内容和摘要中检索
	 * @param con
	 * @return
	 */
	public Pager<Topic> searchTopic(String con);
	/**
	 * 检索某个栏目的推荐文章，如果cid为空，表示检索全部的文章
	 * @param ci
	 * @return
	 */
	public Pager<Topic> findRecommendTopic(Integer ci);
	/**
	 * 根据栏目和文章的数量获取该栏目中的文章
	 * @param cid
	 * @param num
	 * @return
	 */
	public List<Topic> listTopicByChannelAndNumber(@Param("cid") int cid, @Param("num") int num);
	
	public List<Topic> listTopicsByChannel(@Param("cid") int cid);

	/**
	 * 获取某个栏目中的最新的可用文章
	 * @param cid
	 * @return
	 */
	public Topic loadLastedTopicByColumn(@Param("cid") int cid);

	public List<String> getExistKeyword2Map();
	
	
	
	
}
