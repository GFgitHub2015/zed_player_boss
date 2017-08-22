package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.service.player.es.PlayerUserHotElasticsearchService;
import com.zed.system.page.Page;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class PlayerUserHotElasticsearchServiceTest {
	
	@Autowired
	private PlayerUserHotElasticsearchService playerUserHotElasticsearchService;
	
	@Test
	public void search() throws Exception {
		Map<String, Object>  map = playerUserHotElasticsearchService.getShareCountsWithCountryCodes();
		
		System.out.println(map.toString());
	}
/*	@Test
	public void search() throws Exception {
		playerUserHotElasticsearchService.findByFunctionScore(new HashMap<String, Object>());
		playerUserHotElasticsearchService.findByAggs(new HashMap<String, Object>());
	}
*///	@Test
	public void findByPage() throws Exception {
		Page<HashMap> page = new Page<HashMap>();
		HashMap map = new HashMap();
		map.put("countryCode", "92");
		map.put("status", 0);
		page.setParamsMap(map);
		page.setPageNo(1);
		page = playerUserHotElasticsearchService.findByPage(page);
		List<Map<String, Object>> mapList = page.getObjectResult();
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
//			List<HashMap> hashMapList = new ArrayList<HashMap>();
			for (Map<String, Object> hashMap : mapList) {
				System.out.println(hashMap.toString());
			}
		}
	}

}
