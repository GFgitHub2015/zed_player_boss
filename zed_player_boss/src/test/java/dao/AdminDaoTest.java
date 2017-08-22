package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.dao.system.admin.AdminDao;
import com.zed.domain.system.Admin;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = "classpath:conf/spring.xml")   
//@ContextConfiguration(locations = { "classpath*:conf/spring.xml", "classpath*:conf/spring-mybatis.xml" })   
public class AdminDaoTest {
	@Autowired
	private AdminDao adminDao;
	
	@Test
	public void findById() {
		Admin admin = (Admin) adminDao.findById("colin");
		System.out.println("id:"+admin.getAdminId()+", age: "+admin.getAdminAge());
	}

}
