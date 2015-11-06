package com.internet.cms.service.topic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.internet.cms.constants.AttachmentConstants;
import com.internet.cms.dao.topic.IAttachmentDao;
import com.internet.cms.model.Attachment;
import com.internet.cms.model.User;
import com.internet.cms.page.PageBoundsUtil;
import com.internet.cms.page.Pager;
import com.internet.cms.page.SystemContext;

@Service("attachmentService")
public class AttachmentService implements IAttachmentService {
	
	private IAttachmentDao attachmentDao;
	
	
	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	
	/**
	 * 删除服务器硬盘上的附件信息
	 * @param a 附件信息
	 */
	public static void deleteAttachFiles(Attachment a) {
		String realPath = SystemContext.getRealPath();
		realPath += AttachmentConstants.UPLOADPATH;
		new File(realPath+a.getNewName()).delete();
	}
	
	@Inject
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	/**
	 * 添加附件信息
	 * @param a 附件对象
	 * @param is 附件输入流对象
	 * @throws IOException 异常信息
	 */
	@Override
	public void add(Attachment a,InputStream is) throws IOException {
		try {
			 //添加附加信息
			attachmentDao.add(a);
			//保存附件信息
			addFile(a,is);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 上传附件信息
	 * @param a 附件对象
	 * @param is 附件输入流
	 * @throws IOException 异常
	 */
	private void addFile(Attachment a,InputStream is) throws IOException {
		//进行文件的存储
		String realPath = SystemContext.getRealPath();
		//附件上传的路径
		String path = realPath+AttachmentConstants.UPLOADPATH;
		//图片缩略图存放路径
		String thumbPath = realPath+AttachmentConstants.THUMBNAIL;
		File fp = new File(path);
		File tfp = new File(thumbPath);
		if(!fp.exists()) {
			fp.mkdirs();
		}
		if(!tfp.exists()) {
			tfp.mkdirs();
		}
		path = path+a.getNewName();
		thumbPath = thumbPath+a.getNewName();
		if(a.getIsImg()==1) {
			BufferedImage oldBi = ImageIO.read(is);
			int width = oldBi.getWidth();
			Builder<BufferedImage> bf = Thumbnails.of(oldBi);
			if(width>AttachmentConstants.IMG_WIDTH) {
				bf.scale((double)AttachmentConstants.IMG_WIDTH/(double)width);
			} else {
				bf.scale(1.0f);
			}
			bf.toFile(path);
			//缩略图的处理
			//1、将原图进行压缩
			BufferedImage tbi = Thumbnails.of(oldBi).scale((AttachmentConstants.THUMBNAIL_WIDTH*1.2)/width).asBufferedImage();
			//2、进行切割并且保存
			Thumbnails.of(tbi).scale(1.0f)
				.sourceRegion(Positions.CENTER, AttachmentConstants.THUMBNAIL_WIDTH, AttachmentConstants.THUMBNAIL_HEIGHT)
				.toFile(thumbPath);
		} else {
			FileUtils.copyInputStreamToFile(is, new File(path));
		}
	}

	/**
	 * 根据附件id删除附件信息
	 * @param id 附件id
	 */
	@Override
	public void delete(int id) {
		Attachment a = attachmentDao.load(id);
		attachmentDao.delete(id);
		deleteAttachFiles(a);
	}

	@Override
	public Attachment load(int id) {
		return attachmentDao.load(id);
	}

	@Override
	public Pager<Attachment> findNoUseAttachment() {
		return attachmentDao.findNoUseAttachment();
	}

	@Override
	public void clearNoUseAttachment() {
		attachmentDao.clearNoUseAttachment();
	}

	@Override
	public List<Attachment> listByTopic(int tid) {
		return attachmentDao.listByTopic(tid);
	}

	@Override
	public List<Attachment> listIndexPic(int num) {
		return attachmentDao.listIndexPic(num);
	}

	@Override
	public Pager<Attachment> findChannelPic(int cid) {
		return attachmentDao.findChannelPic(cid);
	}

	@Override
	public List<Attachment> listAttachByTopic(int tid) {
		return attachmentDao.listAttachByTopic(tid);
	}

	@Override
	public void updateIndexPic(int aid) {
		Attachment att = attachmentDao.load(aid);
		System.out.println(aid+"------------>");
		if(att.getIsIndexPic()==0) {
			att.setIsIndexPic(1);
		} else {
			att.setIsIndexPic(0);
		}
		attachmentDao.update(att);
	}

	@Override
	public void updateAttachInfo(int aid) {
		Attachment att = attachmentDao.load(aid);
		if(att.getIsAttach()==0) {
			att.setIsAttach(1);
		} else {
			att.setIsAttach(0);
		}
		attachmentDao.update(att);
	}

	@Override
	public Pager<Attachment> listAllPic() {
		int count = attachmentDao.listAllIndexPicCount();
		//封装PageBounds对象
		PageBounds pageBounds = PageBoundsUtil.PageBoundsExtend();
		List<Attachment> list = attachmentDao.listAllIndexPic(pageBounds);
		//封装用户分页的Pager对象
		Pager<Attachment> pages = new Pager<Attachment>(count,list);
		return pages;
	}

	@Override
	public long findNoUseAttachmentNum() {
		return attachmentDao.findNoUseAttachmentNum();
	}

}
