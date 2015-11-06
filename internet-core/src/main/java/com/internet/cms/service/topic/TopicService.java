package com.internet.cms.service.topic;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.topic.IAttachmentDao;
import com.internet.cms.dao.topic.IChannelDao;
import com.internet.cms.dao.topic.ITopicDao;
import com.internet.cms.dao.user.IUserDao;
import com.internet.cms.model.Attachment;
import com.internet.cms.model.Channel;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.Topic;
import com.internet.cms.model.User;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("topicService")
public class TopicService implements ITopicService {
	private ITopicDao topicDao;
	private IAttachmentDao attachmentDao;
	private IChannelDao channelDao;
	private IUserDao userDao;
	
	
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public ITopicDao getTopicDao() {
		return topicDao;
	}
	@Inject
	public void setTopicDao(ITopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Inject
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	/**
	 * 添加带有附件信息的文章
	 * @param topic 文章对象
	 * @param cid 文章栏目
	 * @param uid 文章作者
	 * @param aids 文章附件id数组
	 */
	@Override
	public void add(Topic topic, int cid, int uid, Integer[] aids) {
		Channel c = channelDao.load(cid);
		User u = userDao.load(uid);
		if(c==null||u==null)throw new CmsException("要添加的文章必须有用户和栏目");
		topic.setAuthor(u.getNickname());
		topic.setCname(c.getName());
		topic.setCreateDate(new Date());
		//topic.setChannel(c);
		//topic.setUser(u);
		topic.setCid(c.getId());
		topic.setCname(c.getName());
		topic.setUid(uid);
		//写文档内容
		
		//添加文章
		topicDao.add(topic);
		//添加文章附件
		addTopicAtts(topic, aids);
	}
	
	/**
	 * 添加文章附件信息
	 * @param topic 文章对象
	 * @param aids 附件id集合信息
	 */
	private void addTopicAtts(Topic topic,Integer[] aids) {
		if(aids!=null) {
			for(Integer aid:aids) {
				Attachment a = attachmentDao.load(aid);
				if(a==null) continue;
				a.setTid(topic.getId());
				//a.setTopic(topic);
				//更新attachement
				attachmentDao.update(a);
			}
		}
	}

	/**
	 * 添加不带附件信息的文章对象
	 * @param topic 文章对象
	 * @param cid 文章栏目
	 * @param uid 文章作者
	 */
	@Override
	public void add(Topic topic, int cid, int uid) {
		add(topic,cid,uid,null);
	}

	@Override
	public void delete(int id) {
		//根据文章id获取文章附件列表信息
		List<Attachment> atts = attachmentDao.listByTopic(id);
		//删除文章id删除附件信息
		attachmentDao.deleteByTopic(id);
		//根据文章id删除文章
		topicDao.delete(id);
		//删除硬盘上面的文件
		for(Attachment a:atts) {
			AttachmentService.deleteAttachFiles(a);
		}
	}

	@Override
	public void update(Topic topic, int cid, Integer[] aids) {
		Channel c = channelDao.load(cid);
		if(c==null)throw new CmsException("要更新的文章必须有用户和栏目");
		topic.setCname(c.getName());
		topic.setCid(c.getId());
		//更新文章
		topicDao.update(topic);
		//添加文章附件信息
		addTopicAtts(topic, aids);
	}

	@Override
	public void update(Topic topic, int cid) {
		update(topic,cid,null);
	}

	/**
	 * 根据文章id获取文章对象信息 
	 * @param id 文章id
	 * @return 文章对象
	 */
	@Override
	public Topic load(int id) {
		return topicDao.load(id);
	}

	/**
	 * 根据栏目和标题和状态进行文章的检索
	 * @param cid 栏目id
	 * @param title 标题
	 * @param status 状态
	 * @return 文章列表
	 */
	@Override
	public Pager<Topic> find(Integer cid, String title, Integer status) {
		return this.find(null,cid, title, status);
	}

	/**
	 * 根据用户，栏目和标题和状态进行检索
	 * @param uid 用户id
	 * @param cid 栏目id
	 * @param title 标题
	 * @param status 状态
	 * @return 文章列表
	 */
	@Override
	public Pager<Topic> find(Integer uid, Integer cid, String title,Integer status) {
		//查询文章总数
		int count = topicDao.findCount(uid, cid, title, status);
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("publish_date.desc");
		//获取文章分页列表集合信息
		List<Topic> list = topicDao.find(uid, cid, title, status,pageBounds);
		//封装用户分页的Pager对象
		Pager<Topic> pages = new Pager<Topic>(count,list);
		return pages;
	}

	@Override
	public Pager<Topic> searchTopicByKeyword(String keyword) {
		return topicDao.searchTopicByKeyword(keyword);
	}

	@Override
	public Pager<Topic> searchTopic(String con) {
		return topicDao.searchTopic(con);
	}

	@Override
	public Pager<Topic> findRecommendTopic(Integer ci) {
		return topicDao.findRecommendTopic(ci);
	}
	
	/**
	 * 更新文章的状态
	 * @param id 文档id 
	 */
	@Override
	public void updateStatus(int id) {
		//根据文章id获取文章对象信息
		Topic t = topicDao.load(id);
		if(t.getStatus()==0) {
			t.setStatus(1);
		}else {
			t.setStatus(0);
		}
		//更新文章
		topicDao.update(t);
	}
	
	/**
	 * 判断所添加文章的栏目是否需要进行更新
	 * @param cid 栏目id
	 * @return false：不需要更新栏目 true：需要更新栏目
	 */
	@Override
	public boolean isUpdateIndex(int cid) {
		int count = channelDao.isUpdateIndex(cid);
		if(count<=0){
			return false;
		}
		return true;
	}
	
	
	@Override
	public List<Topic> listTopicByChannelAndNumber(int cid, int num) {
		return topicDao.listTopicByChannelAndNumber(cid, num);
	}
	@Override
	public List<Topic> listTopicByChannel(int cid) {
		return topicDao.listTopicsByChannel(cid);
	}
	
	
	@Override
	public Topic loadLastedTopicByColumn(int cid) {
		return topicDao.loadLastedTopicByColumn(cid);
	}

}
