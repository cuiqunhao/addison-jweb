package com.internet.cms.service.topic;

import java.util.List;

import com.internet.cms.model.Topic;
import com.internet.cms.page.Pager;

public interface ITopicService {
	/**
	 * 添加带有附件信息的文章
	 * @param topic 文章对象
	 * @param cid 文章栏目
	 * @param uid 文章用户
	 * @param aids 文章的附件id数组
	 */
	public void add(Topic topic,int cid,int uid,Integer[] aids);
	/**
	 * 添加不带附件信息的文章对象
	 * @param topic 文章对象
	 * @param cid 文章栏目
	 * @param uid 文章作者
	 */
	public void add(Topic topic,int cid,int uid);
	
	/**
	 * 删除文章，先删除文章的附件信息，还得删除附件的文件对象
	 * @param id 文档id
	 */
	public void delete(int id);
	/**
	 * 更新文章，带附件信息更新
	 * @param topic
	 * @param cid
	 * @param aids
	 */
	public void update(Topic topic,int cid,Integer[] aids);
	/**
	 * 没有带附件信息的文章更新
	 * @param topic
	 */
	public void update(Topic topic,int cid);
	/**
	 * 更新文章的状态
	 * @param id 文档id 
	 */
	public void updateStatus(int id);
	
	/**
	 * 根据文章id获取文章对象信息 
	 * @param id 文章id
	 * @return 文章对象
	 */
	public Topic load(int id);
	
	/**
	 * 根据栏目和标题和状态进行文章的检索
	 * @param cid 栏目id
	 * @param title 标题
	 * @param status 状态
	 * @return 文章列表
	 */
	public Pager<Topic> find(Integer cid,String title,Integer status);
	/**
	 * 根据用户，栏目和标题和状态进行检索
	 * @param uid 用户id
	 * @param cid 栏目id
	 * @param title 标题
	 * @param status 状态
	 * @return 文章列表
	 */
	public Pager<Topic> find(Integer uid,Integer cid,String title,Integer status);
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
	
	public List<Topic> listTopicByChannelAndNumber(int cid,int num);
	
	public List<Topic> listTopicByChannel(int cid);
	
	/**
	 * 判断所添加文章的栏目是否需要进行更新
	 * @param cid 栏目id
	 * @return false：不需要更新栏目 true：需要更新栏目
	 */
	public boolean isUpdateIndex(int cid);
	
	public Topic loadLastedTopicByColumn(int cid);
}
