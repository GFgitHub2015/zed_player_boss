package com.zed.redis.player.hk;

import java.util.List;

import com.zed.domain.player.hotkeyword.PlayerHotKeyword;

public interface PlayerHotKeyWordRedisDao {
	
	/*//搜索热词是否存在
	public Boolean exist(String keyWord);
		
	//搜索热词id是否存在
	public Boolean existWordKeyId(String wordKeyId);
	
	//获取热词队列所有的热词
	public List<PlayerHotKeyword> findTopPlayerHotKeyword(Integer topSize);
	
	//更新推荐热词热度
	public void zIncrease(String areaCode, String keyWord);
	
	//获取搜索热词的热度
	public Double getScore(String keyWord);
	
	//添加搜索热词
	public void add(PlayerHotKeyword playerHotKeyword);
	
	//批量添加搜索热词
	public void addBatch(List<PlayerHotKeyword> playerHotKeywordList);
	
	//更新搜索热词
	public void update(PlayerHotKeyword playerHotKeyword);
	
	//根据搜索热词主键删除热词队列中的数据
	public void delete(String wordKeyId);
	
	//根据搜索热词主键批量删除热词队列中的数据
	public void delete(String ... wordKeyIds);
	
	//强制更新所有数据
	public Boolean updateAllCache(List<PlayerHotKeyword> playerHotKeywordList);
		
	//分页获取缓存中所有数据信息
	public List<PlayerHotKeyword> findByPage(Integer pageNumber,Integer pageSize);*/

}
