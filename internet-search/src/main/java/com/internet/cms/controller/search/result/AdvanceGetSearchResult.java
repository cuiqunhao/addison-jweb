package com.internet.cms.controller.search.result;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.internet.cms.model.search.DocumentEntity;
import com.internet.cms.utils.search.SystemConstant;

/**
 *  高级搜索
 */
public class AdvanceGetSearchResult {
	private Directory directory;
	private DirectoryReader reader;
	private String queryStr;
	private int eachePageNum = 10;// 每页显示的个数
	private String docType;
	private String qtype;
	private int totalNum = 100;
	
	public AdvanceGetSearchResult(int eachePageNum, String docType,
			String qtype, String queryStr, int totalNum) {
		this.eachePageNum = eachePageNum;
		this.docType = docType;
		this.qtype = qtype;
		this.queryStr = queryStr;
		this.totalNum = totalNum;
	}

	/**
	 * 
	 * @param queryStr
	 * @param headSearchNum
	 *            得到查询的靠前的结果数
	 * @return
	 * @throws Exception
	 */
	public ScoreDoc[] getScoreDocs() throws Exception {
		directory = FSDirectory.open(new File(SystemConstant.indexDir));
		reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = this.getQuery();
		TopDocs topDocs = searcher.search(query, this.totalNum);
		ScoreDoc[] hits = topDocs.scoreDocs;
		return hits;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Query getQuery() throws Exception {
		PaodingAnalyzer analyzer = new PaodingAnalyzer();
		String field = "contents";
		Query query = null;

		BooleanQuery booleanQuery = new BooleanQuery();

		if (this.qtype.equals("term")) {
			QueryParser parser = new QueryParser(Version.LUCENE_44, field, analyzer);
			query = parser.parse(queryStr);

		} else if ("fuzz".equals(this.qtype)) {
			Term term = new Term(field, queryStr);
			query = new FuzzyQuery(term);
		} else {
			Term term = new Term(field, queryStr);
			query = new PrefixQuery(term);
		}
		if (!"all".equals(this.docType)) {
			Term typeTerm = new Term("type", this.docType);
			TermQuery typeQuery = new TermQuery(typeTerm);
			booleanQuery.add(typeQuery, BooleanClause.Occur.MUST);
		}

		// System.out.println("--this.docType---"+this.docType);
		booleanQuery.add(query, BooleanClause.Occur.MUST);

		return booleanQuery;
	}
	
	/**
	 * 
	 * @param currentPageNum
	 * @return
	 * @throws Exception
	 */
	public List<DocumentEntity> getResult(int currentPageNum) throws Exception {

		List<DocumentEntity> list = new ArrayList<DocumentEntity>();
		directory = FSDirectory.open(new File(SystemConstant.indexDir));
		reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);

		// 高亮显示设置
		Highlighter highlighter = null;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'><b>", "</b></font>");

		Highlighter highlighterTitle = null;

		SimpleHTMLFormatter formatTitle = new SimpleHTMLFormatter("<FONT color=#c60a00>", "</FONT>");

		ScoreDoc[] hits = this.getScoreDocs();
		Query query = this.getQuery();
		highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighterTitle = new Highlighter(formatTitle, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(200));// 这个200是指定关键字字符串的context
		// 的长度，你可以自己设定，因为不可能返回整篇正文内容
		Document doc;
		String fileName = "";
		int totalNumber = currentPageNum * eachePageNum;
		if (totalNumber > hits.length)
			totalNumber = hits.length;
		for (int i = (currentPageNum - 1) * eachePageNum; i < totalNumber; i++) {

			// 打印文档的内容
			doc = searcher.doc(hits[i].doc);
			// System.out.println(doc.toString());
			// if(this.docType.equals(doc.get("type"))){
			// 高亮出显示
			DocumentEntity docEntity = new DocumentEntity();
			TokenStream tokenStream = new PaodingAnalyzer().tokenStream("contents", new StringReader(doc.get("contents")));
			docEntity.setContents(highlighter.getBestFragment(tokenStream, doc.get("contents")));
			// System.out.println("----------"+i+"----------");
			// System.out.println(docEntity.getContents());
			fileName = doc.get("fileName");
			tokenStream = new PaodingAnalyzer().tokenStream("fileName", new StringReader(fileName));
			// 需要注意：在处理时如果文本检索结果中不包含对应的关键字返回一个null
			String forMatt = highlighterTitle.getBestFragment(tokenStream, fileName);
			if (forMatt == null)
				docEntity.setFilename(fileName);
			else
				docEntity.setFilename(forMatt);

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
			// System.out.println(docEntity.getType());

			list.add(docEntity);
			// System.out.println(docEntity.getFilename());
			// System.out.println("--------------------"+doc.get("fileName"));

			// }//end for if
			// else continue;
		}// end for
		return list;
	}
}
