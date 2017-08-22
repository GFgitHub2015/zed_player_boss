package com.zed.dao.player.recommendkeyword;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;
import com.zed.exception.AppValidationException;

public interface PlayerRecommendKeyWordDao<T extends Serializable> extends PageDao<PlayerRecommendKeyWord> {

	//修改状态
	public void updateStatus(PlayerRecommendKeyWord recommendKeyWord) throws AppValidationException;
	
	//获取所有的对象
	public List<PlayerRecommendKeyWord> findAllByStatus(String status, String areaCode) throws AppValidationException;
	
	//根据地区获取该地区的top热词
	public List<PlayerRecommendKeyWord> findTopKeyWordsWithArea(String areaCode, Integer size) throws AppValidationException;
	
	//批量查询对象
	public List<PlayerRecommendKeyWord> findAllByIds(String[] ids) throws AppValidationException;
	
	//获取所有的地区编号
	public List<String> findAllAreaCode() throws AppValidationException;
	
	//快速删除所有数据
	public void deleteAll() throws AppValidationException;
	
	//批量添加所有数据
	public void addAll(List<PlayerRecommendKeyWord> recommendKeyWordList) throws AppValidationException;
	
	//根据地区和内容获取推荐词对象
	public PlayerRecommendKeyWord findPlayerRecommendKeyWordByKeyWordWithArea(String keyWord, String areaCode) throws AppValidationException;
}
