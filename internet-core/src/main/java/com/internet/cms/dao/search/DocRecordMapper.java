package com.internet.cms.dao.search;

import com.internet.cms.model.search.DocRecord;

public interface DocRecordMapper {
	public int createDoc(DocRecord docrec);

	public DocRecord getDocByFileName(String strName);

	public DocRecord getDocTopOne(String doctype);

	public DocRecord getDocById(int id);

	public int deleteDocById(int id);

	public int deleteAllDoc();
}