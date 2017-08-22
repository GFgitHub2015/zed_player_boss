package com.zed.service.common.upload.upload;

import java.io.File;

import com.zed.service.common.aws.AWSService;

public interface UploadFileService extends AWSService {
	public String put(File file, String remotepath, String fileName) throws Exception;
	
	public String put(File file, String remotepath, String fileName, Boolean isRealFileName) throws Exception;
	
	public void delete(String url) throws Exception;
	
	
}
