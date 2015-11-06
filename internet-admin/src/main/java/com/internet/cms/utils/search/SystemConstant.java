package com.internet.cms.utils.search;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统常量
 */
public class SystemConstant {
	public static String basedir = "\\";

	public static String CONTEXT = "\\internet-admin";

	public static String indexDir = "d:\\lucene\\luceneindex";

	public static String PDFdir = basedir + "datadir\\pdfdir\\";

	public static String PDFTxtdir = "d:\\lucene\\datadir\\pdftxtdir\\";

	public static String ConvertorPATH = "D:\\lucene\\xpdf\\bin32\\pdftotext";

	public static String Docdir = basedir + "datadir\\worddir\\";

	public static String getRootRealPath(String str, HttpServletRequest request) {
		basedir = request.getRealPath("datadir");
		if ("pdf".equals(str)){
			return request.getRealPath(PDFdir);
		}else{
			return request.getRealPath(Docdir);
		}
	}
}
