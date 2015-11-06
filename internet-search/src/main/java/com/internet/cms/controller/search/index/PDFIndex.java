package com.internet.cms.controller.search.index;

import java.io.File;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internet.cms.controller.search.pdf.XpdfParser;
import com.internet.cms.model.search.DocRecord;
import com.internet.cms.service.search.DocRecordService;
import com.internet.cms.utils.search.SystemConstant;

/**
 * 创建PDF文档索引
 */
@Service("pdfIndex")
public class PDFIndex {
	@Autowired
	private DocRecordService docRecordService;
	
	/**
	 * 
	 * @throws Exception
	 */
	public void createPDFIndex(HttpServletRequest request) throws Exception {

		XpdfParser xpdfp = new XpdfParser();

		String dataDir = SystemConstant.getRootRealPath("pdf", request);
		//System.out.println("path" + dataDir);

		String indexDir = SystemConstant.indexDir;
		String txtContents = "";
		File[] files = new File(dataDir).listFiles();
		System.out.println(files.length);
		Analyzer analyzer = new PaodingAnalyzer();
		Directory directory = FSDirectory.open(new File(indexDir));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
		IndexWriter writer = new IndexWriter(directory, iwc);
		long ldate = docRecordService.getDocTopOne(".pdf");
		// System.out.println("============1==============");
		for (int i = 0; i < files.length; i++) {
			//System.out.println(files[i].getName() + "============2==============" + ldate);
			if (files[i].lastModified() > ldate) {

				try {
					txtContents = xpdfp.getPDFFileContent(files[i].getCanonicalPath());
				} catch (Exception e) {
					e.printStackTrace();
					if(writer != null){
						writer.close();
					}
				}
				//System.out.println(i + "============3==============" + txtContents);
				DocRecord drec = new DocRecord();
				drec.setDocType(".pdf");
				drec.setLastModify(files[i].lastModified());
				drec.setFileName(files[i].getName());

				Document document = new Document();
				int id = docRecordService.createDoc(drec);
				int index = files[i].getName().indexOf(".pdf");
				//System.out.println(files[i].getName() + "---" + index);

				document.add(new StringField("id", "" + id, Field.Store.YES));
				document.add(new StringField("type", "pdf", Field.Store.YES));
				document.add(new StringField("fileName", files[i].getName().substring(0, index), Field.Store.YES));
				document.add(new TextField("contents", txtContents, Field.Store.YES));
				writer.addDocument(document);
			}
		}
		//System.out.println("------end------1-");
		File file = new File(SystemConstant.PDFTxtdir);
		// if (file.exists()){
		// System.out.println("------1.1-----");
		// DelTempDir.delAllFile(file);
		// }
		//System.out.println("------end------2-");
		writer.close();
		directory.close();
	}

}
