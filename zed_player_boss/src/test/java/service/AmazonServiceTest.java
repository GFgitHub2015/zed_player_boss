package service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml"})
public class AmazonServiceTest {
	private static String keyId = "AKIAJCAZBWUR6MZ3LDJQ";
	private static String keySecret = "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";
	private static String bucketName = "3dzed/account/icon";
	private static String uploadFileName = "C:\\Users\\Public\\Pictures\\google\\1280.png";
	private static String endPoint = "https://s3-ap-northeast-1.amazonaws.com";
	
	@Test
	public void demo(){
		//create a credentials
		AWSCredentials credentials = new BasicAWSCredentials(keyId, keySecret);
		//create a clientConfig
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		//create a connection
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		//set a endpoint
		conn.setEndpoint(endPoint);
		//list all buckets
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName() + "\t" + StringUtils.fromDate(bucket.getCreationDate()));
			//list a bucket's content
			ObjectListing objects = conn.listObjects(bucket.getName());
			do{
				for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
					System.out.println(objectSummary.getKey() + "\t" + objectSummary.getSize() + "\t" + StringUtils.fromDate(objectSummary.getLastModified()));
				}
				objects = conn.listNextBatchOfObjects(objects);
			}while(objects.isTruncated());
		}
	}
	
//	@Test
	public void createBucket(){
		//create a connection
		AWSCredentials credentials = new BasicAWSCredentials(keyId, keySecret);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		
		
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(endPoint);
		//delete bucket "3dzedtest"
//		conn.createBucket("3dzedtest2");
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName() + "\t" + StringUtils.fromDate(bucket.getCreationDate()));
		}
	}
	
//	@Test
	public void deleteObject(){
		//create a connection
		AWSCredentials credentials = new BasicAWSCredentials(keyId, keySecret);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		
		
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(endPoint);
		
		conn.deleteObject("3dzedsys2", "account/ico/02855f2a-d776-47cc-8351-34344173cf6f.png");
	}
	
//	@Test
	public void deleteBucket(){
		//create a connection
		AWSCredentials credentials = new BasicAWSCredentials(keyId, keySecret);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		
		
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(endPoint);
		//delete bucket "3dzedtest"
		conn.deleteBucket("3dzedtest");
	}
	
//	@Test
	public void createObject(){
		//create a connection
		AWSCredentials credentials = new BasicAWSCredentials(keyId, keySecret);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		
		
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(endPoint);
		
//		ByteArrayInputStream input = new ByteArrayInputStream("Hello world!".getBytes());
		File file = new File(uploadFileName);
        String objectKey = UUID.randomUUID() +"."+ file.getName().substring(file.getName().lastIndexOf(".")+1);
		conn.putObject("3dzedtest", objectKey, file);
//		conn.putObject("3dzedtest", "hello.txt", input, new ObjectMetadata());
		
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName() + "\t" + StringUtils.fromDate(bucket.getCreationDate()));
			//list a bucket's content
			ObjectListing objects = conn.listObjects(bucket.getName());
			do{
				for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
					System.out.println(objectSummary.getKey() + "\t" + objectSummary.getSize() + "\t" + StringUtils.fromDate(objectSummary.getLastModified()));
				}
				objects = conn.listNextBatchOfObjects(objects);
			}while(objects.isTruncated());
		}
	}
}
