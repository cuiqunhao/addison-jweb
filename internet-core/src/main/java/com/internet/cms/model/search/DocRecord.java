package com.internet.cms.model.search;

/**
 * 文档实体类
 * 
 * @author Administrator
 *
 */
public class DocRecord {
	private int id;
	private String fileName;
	private String docType;
	private long lastModify;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public long getLastModify() {
		return lastModify;
	}

	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}
}
