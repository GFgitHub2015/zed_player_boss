package com.zed.service.player.recommendkeyword.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.recommendkeyword.PlayerRecommendKeyWordDao;
import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.rk.PlayerRecommendKeyWordRedisDao;
import com.zed.service.player.recommendkeyword.PlayerRecommendKeyWordService;
import com.zed.system.page.Page;

@Service("playerRecommendKeyWordService")
@SuppressWarnings("unchecked")
public class PlayerRecommendKeyWordServiceImpl implements PlayerRecommendKeyWordService{
	
	@Resource(name="playerRecommendKeyWordDao")
	private PlayerRecommendKeyWordDao playerRecommendKeyWordDao;
	
	@Resource(name="playerRecommendKeyWordRedisDao")
	private PlayerRecommendKeyWordRedisDao playerRecommendKeyWordRedisDao;
	
	@Override
	public PlayerRecommendKeyWord findById(String recommendKeyWordId) throws AppValidationException {
		return (PlayerRecommendKeyWord) playerRecommendKeyWordDao.findById(recommendKeyWordId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerRecommendKeyWordDao.findByPage(page);
	}

	@Override
	public void add(PlayerRecommendKeyWord recommendKeyWord) throws AppValidationException {
		Set<String> areaCodeSet = new HashSet<String>();
		areaCodeSet.add(recommendKeyWord.getAreaCode());
		playerRecommendKeyWordRedisDao.delete(areaCodeSet);
		playerRecommendKeyWordDao.add(recommendKeyWord);
//		if (!playerRecommendKeyWordRedisDao.exist(recommendKeyWord.getAreaCode(), recommendKeyWord.getKeyword())) {
//			playerRecommendKeyWordRedisDao.add(recommendKeyWord);
//		}
	}

	@Override
	public void update(PlayerRecommendKeyWord recommendKeyWord) throws AppValidationException {
		PlayerRecommendKeyWord prk = this.findById(recommendKeyWord.getKeywordId());
		Set<String> areaCodeSet = new HashSet<String>();
		playerRecommendKeyWordDao.update(recommendKeyWord);
		areaCodeSet.add(recommendKeyWord.getAreaCode());
		if (prk != null) {
			areaCodeSet.add(prk.getAreaCode());
		}
		if (!areaCodeSet.isEmpty()) {
			playerRecommendKeyWordRedisDao.delete(areaCodeSet);
		}
	}
	
	@Override
	public void delete(String[] recommendKeyWordId) throws AppValidationException {
		List<PlayerRecommendKeyWord> list = playerRecommendKeyWordDao.findAllByIds(recommendKeyWordId);
		Set<String> areaCodeSet = getListByAreaCode(list);
		playerRecommendKeyWordRedisDao.delete(areaCodeSet);
		playerRecommendKeyWordDao.delete(recommendKeyWordId);
	}
	
	@Override
	public void updateStatus(PlayerRecommendKeyWord recommendKeyWord) throws AppValidationException {
		playerRecommendKeyWordDao.updateStatus(recommendKeyWord);
//		playerRecommendKeyWordRedisDao.update(recommendKeyWord);
		Set<String> areaCodeSet = new HashSet<String>();
		areaCodeSet.add(recommendKeyWord.getAreaCode());
		playerRecommendKeyWordRedisDao.delete(areaCodeSet);
	}
	
	@Override
	public List<String> findAllAreaCode() throws AppValidationException {
		return playerRecommendKeyWordDao.findAllAreaCode();
	}

	@Override
	public Boolean exist(String keyword, String areaCode) throws AppValidationException {
		PlayerRecommendKeyWord recommendKeyWord = playerRecommendKeyWordDao.findPlayerRecommendKeyWordByKeyWordWithArea(keyword, areaCode);
		if(recommendKeyWord!=null){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private Set<String> getListByAreaCode(List<PlayerRecommendKeyWord> playerRecommendKeyWordList) {
		Set<String> listMap = new HashSet<String>();
		for (PlayerRecommendKeyWord playerRecommendKeyWord : playerRecommendKeyWordList) {
			listMap.add(playerRecommendKeyWord.getAreaCode());
		}
		return listMap;
	}

	@Override
	public void add(List<PlayerRecommendKeyWord> hotKeyWordList) throws AppValidationException {
		for (PlayerRecommendKeyWord prk : hotKeyWordList) {
			this.add(prk);
		}
	}

	@Override
	public List<PlayerRecommendKeyWord> findById(String[] recommendKeyWordIds) throws AppValidationException {
		List<PlayerRecommendKeyWord> result = new ArrayList<PlayerRecommendKeyWord>();
		for (String id : recommendKeyWordIds) {
			PlayerRecommendKeyWord prk = this.findById(id);
			if (prk!=null) {
				result.add(prk);
			}
		}
		return result;
	}

	@Override
	public void updateStatus(List<PlayerRecommendKeyWord> recommendKeyWordList) throws AppValidationException {
		for (PlayerRecommendKeyWord playerRecommendKeyWord : recommendKeyWordList) {
			this.updateStatus(playerRecommendKeyWord);
		}
	}

}
