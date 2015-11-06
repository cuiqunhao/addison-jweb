package com.internet.cms.basic.util;

public class FileUtil {
	public static String getFilename(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
	}
}
