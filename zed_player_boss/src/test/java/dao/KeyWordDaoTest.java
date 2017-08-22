package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zed.dao.player.recommendkeyword.PlayerRecommendKeyWordDao;
import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:conf/spring.xml" })
public class KeyWordDaoTest {
	@Autowired
	private PlayerRecommendKeyWordDao<PlayerRecommendKeyWord> playerRecommendKeyWordDao;
	
	@Test
	public void deleteAll() {
		playerRecommendKeyWordDao.deleteAll();
	}

}
