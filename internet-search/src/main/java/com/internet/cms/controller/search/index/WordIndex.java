package com.internet.cms.controller.search.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.cms.model.search.DocRecord;
import com.internet.cms.service.search.DocRecordService;
import com.internet.cms.utils.search.SystemConstant;

/**
 * 创建文档索引
 */
@Service("wordIndex")
public class WordIndex {

	@Autowired
	private DocRecordService docRecordService;

	String indexDir = SystemConstant.indexDir;

	/**
	 * 
	 * @throws IOException
	 */
	public void createWordIndex(HttpServletRequest request) throws IOException {
		String temppath = SystemConstant.getRootRealPath("doc", request);
		//获得文档列表
		File[] files = new File(temppath).listFiles();
		//庖丁分析器
		Analyzer analyzer = new PaodingAnalyzer();
		//创建Directory
		Directory dir = FSDirectory.open(new File(indexDir));
		//创建Writer
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		long ldate = docRecordService.getDocTopOne(".doc");
		String strtmp = "";
		try {
			for (int i = 0; i < files.length; i++) {
				//System.out.println(files[i].lastModified()+"------1--------");
				//System.out.println(ldate+"------2--------");
				if (files[i].lastModified() > ldate) {
					DocRecord drec = new DocRecord();
					strtmp = files[i].getAbsolutePath();
					int beginIndex = strtmp.indexOf("pdfdir") + 7;
					int endIndex = strtmp.indexOf(".doc");
					FileInputStream fis = new FileInputStream(files[i].getAbsolutePath());
					WordExtractor wordExtractor = new WordExtractor(fis);
					
					//创建文档
					Document document = new Document();
					drec.setFileName(files[i].getName());
					drec.setDocType(".doc");
					drec.setLastModify(files[i].lastModified());
					//System.out.println("文件名： " + files[i].getName());

					int index = files[i].getName().indexOf(".doc");
					String fileName = files[i].getName().substring(0, index);
					int id = docRecordService.createDoc(drec);
					document.add(new StringField("id", "" + id, Field.Store.YES));
					document.add(new StringField("type", "doc", Field.Store.YES));
					document.add(new StringField("fileName", fileName, Field.Store.YES));
					document.add(new TextField("contents", wordExtractor.getText(), Field.Store.YES));
					writer.addDocument(document);
				}
			}
			writer.close();
			dir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
