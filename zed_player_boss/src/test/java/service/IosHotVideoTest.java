package service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.controller.iosplayer.video.IosPlayerVideoAction;
import com.zed.domain.player.spaceactiveuser.PlayerSpaceActiveUser;
import com.zed.service.player.spaceactiveuser.PlayerSpaceActiveUserService;
import com.zed.service.player.spaceapply.PlayerSpaceApplyService;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月9日 下午3:57:51
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml"})
public class IosHotVideoTest {
	
	@Resource(name="iosPlayerHotVideoAction")
	private IosPlayerVideoAction action ;

	@Resource(name="playerSpaceActiveUserService")
	private PlayerSpaceActiveUserService playerSpaceActiveUserService;
	@Test
	public void test(){
//		action.list();
		Page<PlayerSpaceActiveUser> page = new Page<PlayerSpaceActiveUser>();
//		playerSpaceActiveUserService.findMasterStat(page);
		System.out.println(page.getResult().size()+"==============");
	}
}
