package com.internet.cms.controller.search.result;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.internet.cms.model.search.DocumentEntity;
import com.internet.cms.utils.search.SystemConstant;

/**
 * 获得索引查询结果
 */
public class GetSearchResult {

	public static int eachePageNum = 5;//每页显示的个数
	private Directory directory;
	private DirectoryReader reader;
	
	/**
	 * 
	 * @return
	 */
	public IndexSearcher getSearcher(){
		try {
			if(reader == null){
				reader = DirectoryReader.open(directory);
			}else{
				DirectoryReader tr = DirectoryReader.openIfChanged(reader);
				if(tr != null){
					reader.close();
					reader = tr;
				}
			}
			return new IndexSearcher(reader);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取ScoreDoc
	 * @param queryStr
	 * @param headSearchNum 得到查询的靠前的结果数
	 * @return
	 * @throws Exception
	 */
	public ScoreDoc[] getScoreDocs(String queryStr, int headSearchNum)
			throws Exception {
		directory = FSDirectory.open(new File(SystemConstant.indexDir));
		reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = this.getQuery(queryStr, headSearchNum);
		TopDocs topDocs = searcher.search(query, headSearchNum);
		ScoreDoc[] hits = topDocs.scoreDocs;
		return hits;
	}
	
	/**
	 * 获得查询
	 * @param queryStr
	 * @param headSearchNum
	 * @return
	 * @throws Exception
	 */
	public Query getQuery(String queryStr, int headSearchNum) throws Exception {
		PaodingAnalyzer analyzer = new PaodingAnalyzer();
		String field = "contents";
		QueryParser parser = new QueryParser(Version.LUCENE_44, field, analyzer);
		Query query = parser.parse(queryStr);
		return query;
	}
	
	/**
	 * 获得索引结果
	 * @param queryStr
	 * @param currentPageNum
	 * @param topNum
	 * @return
	 * @throws Exception
	 */
	public List<DocumentEntity> getResult(String queryStr, int currentPageNum,int topNum)
			throws Exception {
		//初始化列表
		List<DocumentEntity> list = new ArrayList<DocumentEntity>();
		directory = FSDirectory.open(new File(SystemConstant.indexDir));
		reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);

		//高亮显示设置
		Highlighter highlighter = null;
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'><b>", "</b></font>");
		Highlighter highlighterTitle = null;
		SimpleHTMLFormatter formatTitle = new SimpleHTMLFormatter("<FONT color=#c60a00>", "</FONT>");

		ScoreDoc[] hits = this.getScoreDocs(queryStr, topNum);
		Query query = this.getQuery(queryStr, topNum);
		highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighterTitle = new Highlighter(formatTitle, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(200));//这个200是指定关键字字符串的context
		//的长度，你可以自己设定，因为不可能返回整篇正文内容
		Document doc;
        String fileName="";
		int totalNumber = currentPageNum * eachePageNum;
		if (totalNumber > hits.length)
			totalNumber = hits.length;
		for (int i = (currentPageNum - 1) * eachePageNum; i < totalNumber; i++) {

			//打印文档的内容
			doc = searcher.doc(hits[i].doc);

			//高亮出显示
			DocumentEntity docEntity = new DocumentEntity();
			TokenStream tokenStream = new PaodingAnalyzer().tokenStream("contents", new StringReader(doc.get("contents")));
			docEntity.setContents(highlighter.getBestFragment(tokenStream, doc.get("contents")));
			fileName = doc.get("fileName");
			tokenStream = new PaodingAnalyzer().tokenStream("fileName",new StringReader(fileName));
			//需要注意：在处理时如果文本检索结果中不包含对应的关键字返回一个null
			String forMatt=highlighterTitle.getBestFragment(tokenStream, fileName);
			if(forMatt == null){
				docEntity.setFilename(fileName);
			}else{
				docEntity.setFilename(forMatt);
			}
			String type1 = doc.get("type");
			docEntity.setType(type1);
			docEntity.setId(doc.get("id"));
			if ("pdf".equalsIgnoreCase(type1)) {
				fileName = SystemConstant.CONTEXT + SystemConstant.PDFdir + fileName + "." + type1;
				docEntity.setOriginalFileName(fileName);
			} else if ("doc".equalsIgnoreCase(type1)) {
				fileName = SystemConstant.CONTEXT + SystemConstant.Docdir + fileName + "." + type1;
				docEntity.setOriginalFileName(fileName);
			}
			list.add(docEntity);
		}
		return list;
	}

	@Test
	public  void test1()throws Exception{
		this.getResult("挖掘", 2,200);
	}
}
