package com.internet.cms.basic.util.fileupload;

import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

public class MonitoredDiskFileItem extends DiskFileItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3950942589912862385L;
	private MonitoredOutputStream mos = null;
	private OutputStreamListener listener;
	public MonitoredDiskFileItem(String fieldName, String contentType,
			boolean isFormField, String fileName, int sizeThreshold,
			File repository, OutputStreamListener listener) {
		super(fieldName, contentType, isFormField, fileName, sizeThreshold,
				repository);
		this.listener = listener;
	}
	public OutputStream getOutputStream() throws IOException {
		if (mos == null) {
			mos = new MonitoredOutputStream(super.getOutputStream(), listener);
		}
		return mos;
	}
}
