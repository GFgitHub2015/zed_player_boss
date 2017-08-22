package com.zed.service.common.upload.upload.impl;

import java.io.File;

import com.zed.service.common.upload.upload.abstractservice.UploadFileAbstractService;
public class UploadFileServiceImpl extends UploadFileAbstractService{
	@Override
	public String put(File file, String remotepath, String fileName) throws Exception {
		return putObject(file, remotepath, fileName);
	}

	@Override
	public void delete(String url) throws Exception {
		deleteObject(url);
	}

	@Override
	public String put(File file, String remotepath, String fileName, Boolean isRealFileName) throws Exception {
		return putObject(file, remotepath, fileName, isRealFileName);
	}

}
