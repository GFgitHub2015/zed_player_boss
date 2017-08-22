package com.zed.service.common.upload.oss;

import java.io.File;

public interface OSSService {
	public String put(File file, String remotepath, String fileName) throws Exception;
	
	public void delete(String url) throws Exception;
}
