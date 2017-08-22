package com.zed.service.common.upload.impl;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.service.common.upload.UploadService;
import com.zed.service.common.upload.upload.UploadFileService;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {
	
	@Resource(name="uploadFileService")
	private UploadFileService uploadFileService;
	
	@Override
	public String put(File file, String remotepath, String fileName) throws Exception {
		return uploadFileService.put(file, remotepath, fileName);
	}

	@Override
	public void delete(String url) throws Exception {
		uploadFileService.delete(url);
	}

	@Override
	public String put(File file, String remotepath, String fileName, Boolean isRealFileName) throws Exception {
		return uploadFileService.put(file, remotepath, fileName, isRealFileName);
	}

}
