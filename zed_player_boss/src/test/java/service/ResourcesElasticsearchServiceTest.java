package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.common.util.JsonUtils;
import com.zed.domain.player.logicalfile.PlayerVideoResources;
import com.zed.service.player.es.PlayerUserHotElasticsearchService;
import com.zed.service.player.es.PlayerVideoElasticsearch2Service;
import com.zed.system.page.Page;

import net.sf.json.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class ResourcesElasticsearchServiceTest {
	
	@Autowired
	private PlayerVideoElasticsearch2Service playerVideoElasticsearch2Service;
	@Autowired
	private PlayerUserHotElasticsearchService playerUserHotElasticsearchService;
	
//	@Test
	public void search() throws Exception {
	}
	@Test
	public void findByPage() throws Exception {
		
		Map<String, Object>  aaa = playerUserHotElasticsearchService.getShareCountsWithCountryCodes();
		
		System.out.println(aaa.toString());
		
		Page<HashMap> page = new Page<HashMap>();
		HashMap map = new HashMap();
//		map.put("fileName", "the 60");
//		map.put("countryCode", "852");
		page.setParamsMap(map);
		page.setPageNo(1);
		page.setPageSize(100);
		page = playerVideoElasticsearch2Service.findByPage(page);
		List<Map<String, Object>> mapList = page.getObjectResult();
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
//			List<PlayerVideoResources2> playerVideoResources2List = new ArrayList<PlayerVideoResources2>();
//			List<HashMap> hashMapList = new ArrayList<HashMap>();
			for (Map<String, Object> hashMap : mapList) {
//				System.out.println(hashMap.toString());
				JSONObject jsonObject = new JSONObject();
				jsonObject.putAll(hashMap);
				PlayerVideoResources pvr = JsonUtils.jsonToObj(jsonObject.toString(),PlayerVideoResources.class);
				/*pvr.setCountryCode("86");
				if (pvr.getFileSuffix().equals("mp4")) {
					pvr.setCountryCode(pvr.getCountryCode()+" 91");
				}
				if (pvr.getFileSuffix().equals("mkv")) {
					pvr.setCountryCode(pvr.getCountryCode()+" 60");
				}
				if (pvr.getFileSuffix().equals("avi")) {
					pvr.setCountryCode(pvr.getCountryCode()+" 852");
				}
				playerVideoResources2List.add(pvr);*/
				System.out.println("CountryCode = "+pvr.getCountryCode()+", FileName = "+pvr.getFileName());
			}
//			playerVideoElasticsearch2Service.add(playerVideoResources2List);
		}
		
	}
	
}
