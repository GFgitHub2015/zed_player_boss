package com.zed.dao.player.hotkeyword;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.exception.AppValidationException;

public interface PlayerHotKeyWordDao<T extends Serializable> extends PageDao<PlayerHotKeyword> {

	//修改状态
	public void updateStatus(PlayerHotKeyword hotKeyWord) throws AppValidationException;
	
	//获取所有的对象
	public List<PlayerHotKeyword> findAllByStatus(String status, String areaCode) throws AppValidationException;
	
	//根据地区获取该地区的top热词
	public List<PlayerHotKeyword> findTopKeyWordsWithArea(String areaCode, Integer size) throws AppValidationException;
	
	//批量查询对象
	public List<PlayerHotKeyword> findAllByIds(String[] ids) throws AppValidationException;
	
	//获取所有的地区编号
	public List<String> findAllAreaCode() throws AppValidationException;
	
	//快速删除所有数据
	public void deleteAll() throws AppValidationException;
	
	//批量添加所有数据
	public void addAll(List<PlayerHotKeyword> playerHotKeywordList) throws AppValidationException;
}
