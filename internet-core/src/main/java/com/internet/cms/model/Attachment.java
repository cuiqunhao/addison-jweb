package com.internet.cms.model;

import java.util.Date;

public class Attachment {
	private int id;
	/**
	 * 附件上传之后的名称
	 */
	private String newName;
	/**
	 * 附件的原始名称
	 */
	private String oldName;
	/**
	 * 附件的类型，这个类型和contentType类型一致
	 */
	private String type;
	/**
	 * 附件的后缀名
	 */
	private String suffix;
	/**
	 * 附件的大小
	 */
	private long size;
	/**
	 * 该附件是否是主页图片
	 */
	private int isIndexPic;
	/**
	 * 该附件是否是图片类型,0表示不是，1表示是
	 */
	private int isImg;
	
	/**
	 * 是否是附件信息，0表示不是，1表示是，如果是附件信息就在文章的附件栏进行显示
	 */
	private int isAttach;

	private int tid;
	
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getIsIndexPic() {
		return isIndexPic;
	}

	public void setIsIndexPic(int isIndexPic) {
		this.isIndexPic = isIndexPic;
	}

	public int getIsImg() {
		return isImg;
	}

	public void setIsImg(int isImg) {
		this.isImg = isImg;
	}


	public int getIsAttach() {
		return isAttach;
	}

	public void setIsAttach(int isAttach) {
		this.isAttach = isAttach;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Attachment() {
	}

	public Attachment(int id, String newName, String oldName, String type,
			String suffix, long size, int isIndexPic, int isImg, int isAttach,
			int tid, String topicTitle, Date publishDate, String author) {
		super();
		this.id = id;
		this.newName = newName;
		this.oldName = oldName;
		this.type = type;
		this.suffix = suffix;
		this.size = size;
		this.isIndexPic = isIndexPic;
		this.isImg = isImg;
		this.isAttach = isAttach;
	}

	public Attachment(int id, String newName, String oldName, String type,
			String suffix, long size, int isIndexPic, int isImg, int isAttach) {
		super();
		this.id = id;
		this.newName = newName;
		this.oldName = oldName;
		this.type = type;
		this.suffix = suffix;
		this.size = size;
		this.isIndexPic = isIndexPic;
		this.isImg = isImg;
		this.isAttach = isAttach;
	}

	
	
	
}
