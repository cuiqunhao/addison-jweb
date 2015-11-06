package com.internet.cms.utils.search;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 字符处理工具类
 */
public class StringUtil {

	public static String getFileName(String strtmp, String flag) {
		int beginIndex = strtmp.indexOf("pdftemp") + 8;
		int endIndex = strtmp.indexOf(flag);
		return strtmp.substring(beginIndex, endIndex);

	}

	public void test1() {
		String str = getFileName("HITS算法在Web挖掘中的应用与改进.txt", ".txt");
		String str1 = gbkToAscii(str);
		System.out.println(str1);
		System.out.println("------2------" + str1);
		String str2 = asciiToGbk(str1);
		System.out.println(str2);
	}

	/**
	 * 此方法把汉字转换为ASCII
	 * 
	 * @param gbkStr
	 * @return
	 */
	public static String gbkToAscii(String gbkStr) {// 字符串转换为ASCII码

		// String gbkStr="新年快乐！";//字符串

		char[] chars = gbkStr.toCharArray(); // 把字符中转换为字符数组
		StringBuffer sb = new StringBuffer();
		// System.out.println("\n\n汉字 ASCII\n-------1---------------");
		for (int i = 0; i < chars.length; i++) {
			// System.out.println(" "+chars[i]+" "+(int)chars[i]);
			sb.append("%" + (int) chars[i]);
		}

		return sb.toString();
	}

	/**
	 * 此方法把ASCII转换为汉字
	 * 
	 * @param asciiStr
	 * @return
	 */
	public static String asciiToGbk(String asciiStr) {// ASCII转换为字符串

		// String asciiStr="22307 35806 24555 20048";//ASCII码
		System.out.println("ASCII 汉字 \n----------1111------------");

		String[] chars = asciiStr.split("%");
		StringBuffer sb = new StringBuffer();
		// System.out.println("ASCII 汉字 \n----------222------------");
		for (int i = 1; i < chars.length; i++) {
			sb.append((char) Integer.parseInt(chars[i]));
			// System.out.println((char)Integer.parseInt(chars[i]));
		}
		return sb.toString();
	}

}
