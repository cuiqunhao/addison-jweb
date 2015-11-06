package com.internet.cms.dao.topic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.model.Attachment;
import com.internet.cms.page.Pager;


public interface IAttachmentDao{
	/**
	 * 获取没有被引用的附件
	 * @return
	 */
	public Pager<Attachment> findNoUseAttachment();
	
	public long findNoUseAttachmentNum();
	/**
	 * 清空没有被引用的附件
	 */
	public void clearNoUseAttachment();
	/**
	 * 删除文章id删除附件信息
	 * @param tid 文章id
	 */
	public void deleteByTopic(@Param("tid") int tid);
	/**
	 * 根据文章id获取文章附件列表信息
	 * @param tid 文章id
	 * @return 文章附件列表信息
	 */
	public List<Attachment> listByTopic(@Param("tid") int id);
	/**
	 * 根据一个数量获取首页图片的附件信息
	 * @param num
	 * @return
	 */
	public List<Attachment> listIndexPic(int num);
	/**
	 * 获取某个栏目中的附件图片信息
	 * @param cid
	 * @return
	 */
	public Pager<Attachment> findChannelPic(int cid);
	/**
	 * 获取所有的新闻图片信息
	 * @return
	 */
	public List<Attachment> listAllIndexPic(PageBounds pageBounds);
	/**
	 * 获取某篇文章的属于附件类型的附件对象
	 * @param tid
	 * @return
	 */
	public List<Attachment> listAttachByTopic(@Param("tid") int tid);

	/**
	 * 添加附加信息
	 * @param a 附件信息
	 */
	public void add(Attachment a);

	/**
	 * 根据附件id加载附件信息
	 * @param id 附件id
	 * @return 附件信息
	 */
	public Attachment load(@Param("id") Integer id);

	/**
	 * 根据附件id删除附件信息
	 * @param id 附件id
	 */
	public void delete(@Param("id") int id);

	/**
	 * 更新附件信息
	 * @param att 附件信息
	 */
	public void update(Attachment att);

	public int listAllIndexPicCount();

	
	
}
