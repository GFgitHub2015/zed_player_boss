package com.zed.service.common.upload.upload.abstractservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zed.common.util.ContentTypeUtil;
import com.zed.service.common.aws.abstractservice.AWSAbstractService;
import com.zed.service.common.upload.upload.UploadFileService;
import com.zed.util.CommUtil;

public abstract class UploadFileAbstractService extends AWSAbstractService implements UploadFileService{
	
	protected String putObject(File file, String remotepath, String fileName) throws Exception{
		InputStream fileContent=null;
        fileContent=new FileInputStream(file);
		
		String bucketName = remotepath.split("/")[0];
		String objectKey =remotepath.substring(bucketName.length()+1)+"/"+ UUID.randomUUID() +"."+ fileName.substring(fileName.lastIndexOf(".")+1);
		if (!getConnection().doesBucketExist(bucketName)) {
			createBucket(bucketName);
		}
		
		 //创建上传Object的Metadata 
        ObjectMetadata objectMetadata=new ObjectMetadata();  
        objectMetadata.setContentLength(fileContent.available());  
        objectMetadata.setCacheControl("no-cache");  
        objectMetadata.setHeader("Pragma", "no-cache");
//        objectMetadata.setContentType(CommUtil.contentType(fileName.substring(fileName.lastIndexOf(".")+1)));
        objectMetadata.setContentType(ContentTypeUtil.getContentType(fileName.substring(fileName.lastIndexOf(".")+1)));  
        objectMetadata.setContentDisposition("inline;filename=" + file.getName());
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 100);
		Date expirationDate = cal.getTime();
        
        objectMetadata.setExpirationTime(expirationDate);
		getConnection().putObject(bucketName, objectKey, fileContent, objectMetadata);
		getConnection().setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicRead);
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		URL url = getConnection().generatePresignedUrl(generatePresignedUrlRequest);
		String urlWithOutExpiration = url.toString().substring(0, url.toString().indexOf("?"));
		return urlWithOutExpiration;
//		return url.toString();
	}
	
	protected String putObject(File file, String remotepath, String fileName, Boolean isRealFileName) throws Exception{
		InputStream fileContent=null;
        fileContent=new FileInputStream(file);
		
		String bucketName = remotepath.split("/")[0];
		String latestFileName = UUID.randomUUID().toString();
		if (isRealFileName) {
			latestFileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		String objectKey =remotepath.substring(bucketName.length()+1)+"/"+ latestFileName +"."+ fileName.substring(fileName.lastIndexOf(".")+1);
		if (!getConnection().doesBucketExist(bucketName)) {
			createBucket(bucketName);
		}
		
		 //创建上传Object的Metadata 
        ObjectMetadata objectMetadata=new ObjectMetadata();  
        objectMetadata.setContentLength(fileContent.available());  
        objectMetadata.setCacheControl("no-cache");  
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(CommUtil.contentType(fileName.substring(fileName.lastIndexOf(".")+1)));  
        objectMetadata.setContentDisposition("inline;filename=" + fileName);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 100);
		Date expirationDate = cal.getTime();
        
        objectMetadata.setExpirationTime(expirationDate);
		getConnection().putObject(bucketName, objectKey, fileContent, objectMetadata);
		getConnection().setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicRead);
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		URL url = getConnection().generatePresignedUrl(generatePresignedUrlRequest);
		String urlWithOutExpiration = url.toString().substring(0, url.toString().indexOf("?"));
		return urlWithOutExpiration;
//		return url.toString();
	}
	
	protected void deleteObject(String url) throws Exception{
		String keyWithBucketName = url.substring(url.indexOf("//")+2);
		String hostName = keyWithBucketName.substring(0, keyWithBucketName.indexOf("/"));
		String bucketName = hostName.substring(0, hostName.indexOf("."));
		String key = keyWithBucketName.substring(keyWithBucketName.indexOf("/")+1);
		getConnection().deleteObject(bucketName, key);
	}
	
	protected Bucket createBucket(String bucketName) throws Exception{
		return getConnection().createBucket(bucketName);
	}
	
}
