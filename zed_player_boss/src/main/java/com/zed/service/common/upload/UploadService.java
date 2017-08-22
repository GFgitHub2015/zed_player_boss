package com.zed.service.common.upload;

import java.io.File;

public interface UploadService {
	public String put(File file, String remotepath, String fileName) throws Exception;
	
	public String put(File file, String remotepath, String fileName, Boolean isRealFileName) throws Exception;
	
	public void delete(String url) throws Exception;
}
