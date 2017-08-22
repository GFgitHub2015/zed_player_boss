package service;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.service.common.upload.UploadService;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class AwsServiceTest {
	
	private static String remotepath = "3dzed-xjp/player/icontest";
	private static String uploadFileName = "C:\\Users\\DELL\\Desktop\\banner2\\img_3.png";
	
	@Autowired
	private UploadService uploadService;
	
	@Test
	public void putObject() throws Exception {
		File file = new File(uploadFileName);
		String url = uploadService.put(file, remotepath,"img_3.png");
		System.out.println("url : "+url);
	}
	
//	@Test
	public void deleteObject() throws Exception {
//		uploadService.delete(url);
	}

}
