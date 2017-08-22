package com.zed.service.common.upload.oss.impl;

import java.io.File;

import com.zed.service.common.upload.oss.abstractservice.OSSAbstractService;

public class OSSServiceImpl extends OSSAbstractService {

	@Override
	public String put(File file, String remotepath, String fileName) throws Exception {
		return putObject(file, remotepath, fileName);
	}

	@Override
	public void delete(String url) throws Exception {
		deleteObject(url);
	}

}
