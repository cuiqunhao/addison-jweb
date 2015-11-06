package com.internet.cms.model.search;

import java.io.Serializable;

/**
 * 文档实体Bean
 */
public class DocumentEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String originalFileName;
	private String id;
	private String filename;
	private String contents;
	private String type;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
