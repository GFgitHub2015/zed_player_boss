package com.zed.service.common.aws.abstractservice;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.zed.service.common.aws.AWSService;

public abstract class AWSAbstractService implements AWSService{
	private AmazonS3 conn;
	private String accessKey;
	private String secretKey;
	private String endpoint;
	
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public void init(){
		//初始化
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setConnectionTimeout(60*1000*5);
		clientConfig.setProtocol(Protocol.HTTP);
		conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(endpoint);
	}

	protected AmazonS3 getConnection() {
		return conn;
	}
}
