package com.zed.service.common.upload.oss.abstractservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.zed.service.common.upload.oss.OSSService;
import com.zed.util.CommUtil;

public abstract class OSSAbstractService implements OSSService {
	@Autowired
	private OSSClient ossClient;
	
	protected String putObject(File file, String remotepath, String fileName) throws Exception{
		InputStream fileContent=null;
        fileContent=new FileInputStream(file);
        String bucketName = remotepath.split("/")[0];
        String objectKey =remotepath.substring(bucketName.length()+1, remotepath.length()-1)+"/"+ UUID.randomUUID() +"."+ fileName.substring(fileName.lastIndexOf(".")+1);
        
        if (!ossClient.doesBucketExist(bucketName)) {
        	createBucket(bucketName);
		}
        
        //创建上传Object的Metadata 
        ObjectMetadata objectMetadata=new ObjectMetadata();  
        objectMetadata.setContentLength(fileContent.available());  
        objectMetadata.setCacheControl("no-cache");  
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(CommUtil.contentType(fileName.substring(fileName.lastIndexOf(".")+1)));  
        objectMetadata.setContentDisposition("inline;filename=" + file.getName());
        Date expiration = new Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60 * 24 * 100000;
        expiration.setTime(msec);
        objectMetadata.setExpirationTime(expiration);
		ossClient.putObject(bucketName, objectKey, fileContent, objectMetadata);
		ossClient.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicRead);
		
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
		generatePresignedUrlRequest.setExpiration(expiration);
		URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
		return "https://" + url.getHost() + url.getPath();
	}
	
	protected void deleteObject(String url) throws Exception{
		String keyWithBucketName = url.substring(url.indexOf("//")+2);
		String hostName = keyWithBucketName.substring(0, keyWithBucketName.indexOf("/"));
		String bucketName = hostName.substring(0, hostName.indexOf("."));
		String key = keyWithBucketName.substring(keyWithBucketName.indexOf("/")+1);
		ossClient.deleteObject(bucketName, key);
	}
	
	protected Bucket createBucket(String bucketName) throws Exception{
		return ossClient.createBucket(bucketName);
	}
	

}
