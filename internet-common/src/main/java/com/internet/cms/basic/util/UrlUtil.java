package com.internet.cms.basic.util;

import java.io.UnsupportedEncodingException;

public class UrlUtil {
	public static String encodeParam(String name){
		if(!"".equals(name) && null != name){
			try {
				name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				name = "";
			}
		}
		return name;
	}

}
