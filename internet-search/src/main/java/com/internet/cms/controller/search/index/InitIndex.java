package com.internet.cms.controller.search.index;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.cms.service.search.DocRecordService;

/**
 * 初始化索引
 */
@Service("initIndex")
public class InitIndex {
	
	@Autowired
	private DocRecordService docRecordService;

	public void init() throws IOException {
		// Constant.getRootRealPath("pdf");
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
//		String indexDir = SystemConstant.indexDir;
//		File file = new File(indexDir);
//		// 创建目录
//		Directory directory = FSDirectory.open(file);
//
//		// 表示创建或覆盖当前索引；false表示对当前索引进行追加
//		// Default value is 128
//		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
//		IndexWriter writer = new IndexWriter(directory, iwc);
//		Document document = new Document();
//		writer.addDocument(document);
//		writer.close();

		docRecordService.deleteAllDoc();
	}

}
