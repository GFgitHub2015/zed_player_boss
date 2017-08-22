package com.zed.dao.player.recommendkeyword.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.recommendkeyword.PlayerRecommendKeyWordDao;
import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;
import com.zed.exception.AppValidationException;

@Repository("playerRecommendKeyWordDao")
public class PlayerRecommendKeyWordDaoImpl<T> extends AbstractPlayerPageDao<PlayerRecommendKeyWord> implements PlayerRecommendKeyWordDao<PlayerRecommendKeyWord> {

	@Override
	public void updateStatus(PlayerRecommendKeyWord recommendKeyWord) throws AppValidationException {
		update("updateStatus", recommendKeyWord);
	}

	@Override
	public List<PlayerRecommendKeyWord> findAllByStatus(String status, String areaCode) throws AppValidationException {
		Map<String, Object> param = new HashMap<>();
		param.put("status", status);
		param.put("areaCode", areaCode);
		return findList("findAllByStatus",param);
	}

	@Override
	public List<PlayerRecommendKeyWord> findAllByIds(String[] ids) throws AppValidationException {
		return findList("findAllByIds", ids);
	}

	@Override
	public List<PlayerRecommendKeyWord> findTopKeyWordsWithArea(String areaCode, Integer size) throws AppValidationException {
		Map<String, Object> param = new HashMap<>();
		param.put("size", size);
		param.put("areaCode", areaCode);
		return findList("findAllByStatus",param);
	}

	@Override
	public List<String> findAllAreaCode() throws AppValidationException {
		List<String> result = new ArrayList<String>();
		List<PlayerRecommendKeyWord> list = findList("findAllAreaCode");
		for (PlayerRecommendKeyWord recommendKeyWord : list) {
			result.add(recommendKeyWord.getAreaCode());
		}
		return result;
	}

	@Override
	public void deleteAll() throws AppValidationException {
		find("deleteAll");
	}

	@Override
	public void addAll(List<PlayerRecommendKeyWord> recommendKeyWordList) throws AppValidationException {
		addBatch(recommendKeyWordList);
	}

	@Override
	public PlayerRecommendKeyWord findPlayerRecommendKeyWordByKeyWordWithArea(String keyword, String areaCode)
			throws AppValidationException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keyword", keyword);
		param.put("areaCode", areaCode);
		return find("findPlayerRecommendKeyWordByKeyWordWithArea", param);
	}

}
