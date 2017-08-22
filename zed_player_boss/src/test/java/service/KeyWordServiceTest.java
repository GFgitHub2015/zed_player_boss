package service;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.service.player.hotkeyword.PlayerHotKeyWordService;
import com.zed.service.player.recommendkeyword.PlayerRecommendKeyWordService;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class KeyWordServiceTest {
	@Autowired
	private PlayerRecommendKeyWordService playerRecommendKeyWordService;
	@Autowired
	private PlayerHotKeyWordService playerHotKeyWordService;
	
//	@Test
	public void addDatas(){
		
		for (int i = 0; i < 100000; i++) {
			PlayerHotKeyword hk = new PlayerHotKeyword();
			hk.setAreaCode("86");
			hk.setCreater("aaa");
			hk.setCreateTime(new Timestamp(new Date().getTime()));
			hk.setDescription("key word "+i);
			hk.setKeyword("key word "+i);
			hk.setKeywordId(hk.generateId());
			//hk.setSort(0d+i);
			hk.setStatus(1);
//			playerHotKeyWordService.add(hk);
		}
	}
	
	@Test
	public void updateData(){
	}

}
