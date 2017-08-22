package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.service.player.es.PlayerVideoElasticsearch2Service;
import com.zed.system.page.Page;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml"})
public class PlayerLogicalFileServiceTest {
	@Resource(name="playerVideoElasticsearch2Service")
	private PlayerVideoElasticsearch2Service playerVideoElasticsearch2Service;
	
	@Test
	public void demo(){
		Page<HashMap> page = new Page<HashMap>();
		HashMap map = new HashMap();
		map.put("fileName", "the");
		page.setParamsMap(map);
		page = playerVideoElasticsearch2Service.findByPage(page);
		List<Map<String, Object>> mapList = page.getObjectResult();
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
			List<HashMap> hashMapList = new ArrayList<HashMap>();
			for (HashMap hashMap : hashMapList) {
				System.out.println(hashMap.toString());
			}
		}
	}
	
}
