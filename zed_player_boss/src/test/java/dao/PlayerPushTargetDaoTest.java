package dao;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.mongodb.player.push.PlayerPushTargetMongoDao;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class PlayerPushTargetDaoTest {
	@Resource(name="playerPushTargetMongoDao")
	private PlayerPushTargetMongoDao playerPushTargetMongoDao;
	
	@Test
	public void getAll() {
		String[] ids = new String[]{"bbb545d3a88d4b5bb4c726536b3626ff","2db54847393d4b01b69bc4500a04b090","f1309c35bca849a4bdb17059e4284166"};
		Long count = playerPushTargetMongoDao.findCountByScheduleId("2db54847393d4b01b69bc4500a04b090");
		System.out.println("2db54847393d4b01b69bc4500a04b090 count ==== > "+count);
		Long count2 = playerPushTargetMongoDao.findCountByScheduleId("bbb545d3a88d4b5bb4c726536b3626ff");
		System.out.println("bbb545d3a88d4b5bb4c726536b3626ff count ==== > "+count2);
		Long count3 = playerPushTargetMongoDao.findCountByScheduleId("f1309c35bca849a4bdb17059e4284166");
		System.out.println("f1309c35bca849a4bdb17059e4284166 count ==== > "+count3);
		
		Map<String, Long> map = playerPushTargetMongoDao.findCountMapByScheduleIds(ids);
		System.out.println(map.toString());
	}

}
