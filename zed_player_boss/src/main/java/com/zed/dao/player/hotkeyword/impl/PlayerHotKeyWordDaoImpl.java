package com.zed.dao.player.hotkeyword.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.hotkeyword.PlayerHotKeyWordDao;
import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.exception.AppValidationException;

@Repository("playerHotKeyWordDao")
public class PlayerHotKeyWordDaoImpl<T> extends AbstractPlayerPageDao<PlayerHotKeyword> implements PlayerHotKeyWordDao<PlayerHotKeyword> {

	@Override
	public void updateStatus(PlayerHotKeyword hotKeyWord) throws AppValidationException {
		update("updateStatus", hotKeyWord);
	}

	@Override
	public List<PlayerHotKeyword> findAllByStatus(String status, String areaCode) throws AppValidationException {
		Map<String, Object> param = new HashMap<>();
		param.put("status", status);
		param.put("areaCode", areaCode);
		return findList("findAllByStatus",param);
	}

	@Override
	public List<PlayerHotKeyword> findAllByIds(String[] ids) throws AppValidationException {
		return findList("findAllByIds", ids);
	}

	@Override
	public List<PlayerHotKeyword> findTopKeyWordsWithArea(String areaCode, Integer size) throws AppValidationException {
		Map<String, Object> param = new HashMap<>();
		param.put("size", size);
		param.put("areaCode", areaCode);
		return findList("findAllByStatus",param);
	}

	@Override
	public List<String> findAllAreaCode() throws AppValidationException {
		List<String> result = new ArrayList<String>();
		List<PlayerHotKeyword> list = findList("findAllAreaCode");
		for (PlayerHotKeyword hotKeyWord : list) {
			result.add(hotKeyWord.getAreaCode());
		}
		return result;
	}
	
	@Override
	public void deleteAll() throws AppValidationException {
		find("deleteAll");
	}
	
	@Override
	public void addAll(List<PlayerHotKeyword> playerHotKeywordList) throws AppValidationException {
		addBatch(playerHotKeywordList);
	}
}
