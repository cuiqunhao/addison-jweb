package com.internet.cms.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.dao.topic.IChannelDao;
import com.internet.cms.dao.user.IGroupDao;
import com.internet.cms.dao.user.IUserDao;
import com.internet.cms.model.Channel;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.CmsException;
import com.internet.cms.model.Group;
import com.internet.cms.model.GroupChannel;
import com.internet.cms.model.UserGroup;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;

@Service("groupService")
public class GroupService implements IGroupService{
	private IGroupDao groupDao;
	private IUserDao userDao;
	private IChannelDao channelDao;
	
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	public IGroupDao getGroupDao() {
		return groupDao;
	}
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 添加群组
	 * @param group 群组对象
	 */
	@Override
	public int add(Group group) {
		return groupDao.add(group);
	}

	/**
	 * 删除群组信息(在删除之前先判断群组中是否有用户信息)
	 * @param id 群组id
	 */
	@Override
	public int delete(int id){
		//根据群组id获取群组下用户的信息(在删除之前先判断群组中是否有用户信息)
		List<UserGroup> users = userDao.listGroupUsers(id);
		if(null !=users && users.size()>0) throw new CmsException("删除的组中还有用户，不能删除");
		try{
			//根据群组id删除群组信息
			return groupDao.delete(id);
		}catch(Exception e){
			throw new CmsException("删除的组中有栏目，不能删除");
		}
	}

	/**
	 * 根据群组id获取群组信息
	 * @param id 群组id
	 * @return 群组
	 */
	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}

	/**
	 * 更新群组信息
	 * @param group 群组对象
	 */
	@Override
	public int update(Group group) {
		return groupDao.update(group);
	}
	
	/**
	 * 获取群组列表信息
	 * @return 群组列表信息
	 */
	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}
	
	/**
	 * 获取分页群组列表信息
	 * @return 分页群组列表信息
	 */
	@Override
	public Pager<Group> findGroup() {
		//获取群组总数
		int count = groupDao.findGroupCount();
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsOrderExtend("id.desc");
		//获取用户分页列表集合信息
		List<Group> list = groupDao.findGroup(pageBounds);
		//封装用户分页的Pager对象
		Pager<Group> pages = new Pager<Group>(count,list);
		return pages;
	}

	/**
	 * 删除群组中的用户信息
	 * @param gid 群组id
	 */
	@Override
	public int deleteGroupUsers(int gid) {
		return userDao.deleteGroupUsers(gid);
	}
	
	/**
	 * 添加群组栏目对象
	 * @param gid 群组id
	 * @param cid 栏目id
	 */
	@Override
	public void addGroupChannel(int gid, int cid) {
		Group g = groupDao.load(gid);
		Channel c = channelDao.load(cid);
		if(c==null||g==null)throw new CmsException("要添加的组频道关联对象不存在");
		channelDao.addGroupChannel(gid, cid);
	}
	
	/**
	 * 加载GroupChannel对象
	 * @param gid 群组id
	 * @param cid 栏目id
	 * @return 群组栏目对象
	 */
	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		return channelDao.loadGroupChannel(gid, cid);
	}
	
	/**
	 * 根据群组id获取某个组的所有管理栏目的id集合
	 * @param gid 群组id
	 * @return 所对应的栏目id集合信息
	 */
	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		List<GroupChannel> listGroupChannel = channelDao.listGroupChannelIds(gid);
		List<Integer> ids = new ArrayList<Integer>();
		for(GroupChannel gc: listGroupChannel){
			ids.add(gc.getChannelId());
		}
		return ids;
	}
	
	/**
	 * 初始化顶级根节点及所有父级节点
	 * @param cts
	 */
	public static void initTreeNode(List<ChannelTree> cts) {
		cts.add(0,new ChannelTree(Channel.ROOT_ID,Channel.ROOT_NAME,-1));
		for(ChannelTree ct:cts) {
			if(ct.getPid()==null)ct.setPid(0);
		}
	}
	
	/**
	 * 获取某个组的栏目树
	 * @param gid 群组id
	 * @return 某个组的栏目树
	 */
	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		List<ChannelTree> cts = channelDao.generateGroupChannelTree(gid);
		initTreeNode(cts);
		return cts;
	}
	/**
	 * 获取某个用户的栏目树
	 * @param uid 用户id
	 * @return 某个用户的栏目树
	 */
	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		return channelDao.generateUserChannelTree(uid);
	}
	
	/**
	 * 删除群组栏目
	 * @param gid 群组id 
	 * @param cid 栏目id
	 */
	@Override
	public void deleteGroupChannel(int gid, int cid) {
		channelDao.deleteGroupChannel(gid, cid);
	}
	
	/**
	 * 清空组所管理的栏目
	 * @param gid 群组id
	 */
	@Override
	public void clearGroupChannel(int gid) {
		channelDao.clearGroupChannel(gid);
	}
	
	
	
	
	

}
