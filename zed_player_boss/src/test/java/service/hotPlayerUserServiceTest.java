package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.service.player.playeruser.impl.HotPlayerUserServiceImpl;
import com.zed.system.page.Page;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml"})
public class hotPlayerUserServiceTest {
	@Resource(name="hotPlayerUserServiceImpl")
	private HotPlayerUserServiceImpl hotPlayerUserServiceImpl;
	
	@Test
	public void demo(){
		Page<HashMap> page = new Page<HashMap>();
		HashMap map = new HashMap();
//		map.put("userRoleStatus", userRoleStatus);
//		map.put("userId", userId);
//		map.put("status", status);
		map.put("countryCode", 86);
		page.setParamsMap(map);
		page = hotPlayerUserServiceImpl.findByPage(page);
		List<HashMap> mapList = page.getResult();
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
			List<HashMap> hashMapList = new ArrayList<HashMap>();
			for (HashMap hashMap : hashMapList) {
				System.out.println(hashMap.toString());
			}
		}
	}
	
}
