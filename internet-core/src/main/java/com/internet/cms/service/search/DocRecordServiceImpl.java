package com.internet.cms.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.cms.dao.search.DocRecordMapper;
import com.internet.cms.model.search.DocRecord;

@Service("docRecordService")
public class DocRecordServiceImpl implements DocRecordService{
	
	@Autowired
	private DocRecordMapper docRecordMapper;
	
	/**
	 * 删除数据库文档记录
	 */
	public int deleteAllDoc() {
		return docRecordMapper.deleteAllDoc();
	}
	
	/**
	 * 创建数据库文档记录
	 */
	public int createDoc(DocRecord docrec) {
		return docRecordMapper.createDoc(docrec);
	}

	public DocRecord getDocByFileName(String strName) {
		return docRecordMapper.getDocByFileName(strName);
	}
	
	/**
	 * 查询最新文档
	 */
	public long getDocTopOne(String doctype) {
		DocRecord dr = (DocRecord) docRecordMapper.getDocTopOne(doctype);
		if (dr == null)
			return 0;
		else
			return dr.getLastModify();
	}

	public DocRecord getDocById(int id) {
		return docRecordMapper.getDocById(id);
	}
	
	/**
	 * 根据ID删除数据库文档记录
	 */
	public int deleteDocById(int id) {
		return docRecordMapper.deleteDocById(id);
	}
}
