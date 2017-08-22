package service;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.service.player.hotkeyword.PlayerHotKeyWordService;
import com.zed.util.ConstantType;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class PlayerHotKeyWordServiceTest {
	@Resource(name="playerHotKeyWordService")
	private PlayerHotKeyWordService playerHotKeyWordService;
	
	
//	@Test
	public void addTest(){
		PlayerHotKeyword phk = new PlayerHotKeyword();
		phk.setKeywordId(phk.generateId());
		phk.setAreaCode("86");
		phk.setCreater("creater");
		phk.setUpdater("updater");
		phk.setCreateTime(new Timestamp(new Date().getTime()));
		phk.setUpdateTime(new Timestamp(new Date().getTime()));
		phk.setDescription("description");
		//phk.setSort(15d);
		phk.setStatus(ConstantType.KeyWordType.START.getStatus());
		phk.setKeyword("keyword");
//		playerHotKeyWordService.add(phk);
	}
	
//	@Test
	public void deleteTest(){
//		playerHotKeyWordService.delete(new String[]{"d7f253e1d5154098b23c2a8db101b21d"});
	}
	
	@Test
	public void updateTest(){
		PlayerHotKeyword phk = new PlayerHotKeyword();
		phk.setKeywordId("43c9defe4edb446b9788ee289ba54d22");
		phk.setAreaCode("86");
		phk.setCreater("creater");
		phk.setUpdater("updater");
		phk.setCreateTime(new Timestamp(new Date().getTime()));
		phk.setUpdateTime(new Timestamp(new Date().getTime()));
		phk.setDescription("description1");
		//phk.setSort(15d);
		phk.setStatus(ConstantType.KeyWordType.STOP.getStatus());
		phk.setKeyword("keyword");
//		playerHotKeyWordService.update(phk);
	}
	
//	@Test
	public void updateStatusTest(){
		PlayerHotKeyword phk = new PlayerHotKeyword();
		phk.setKeywordId("43c9defe4edb446b9788ee289ba54d22");
		phk.setAreaCode("86");
		phk.setCreater("creater");
		phk.setUpdater("updater");
		phk.setCreateTime(new Timestamp(new Date().getTime()));
		phk.setUpdateTime(new Timestamp(new Date().getTime()));
		phk.setDescription("description");
		//phk.setSort(15d);
		phk.setStatus(ConstantType.KeyWordType.STOP.getStatus());
		phk.setKeyword("keyword");
//		playerHotKeyWordService.updateStatus(phk);
	}
	
//	@Test
	public void test(){
		System.out.println("#############");
	}
}
