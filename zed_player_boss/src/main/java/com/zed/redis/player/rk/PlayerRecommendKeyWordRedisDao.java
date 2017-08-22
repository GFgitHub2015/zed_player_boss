package com.zed.redis.player.rk;

import java.util.List;
import java.util.Set;

import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;

public interface PlayerRecommendKeyWordRedisDao {
	
	//推荐热词是否存在
	public Boolean exist(String areaCode, String keyWord);
	
	//添加推荐热词
	public void add(PlayerRecommendKeyWord playerRecommendKeyWord);
	
	//更新推荐热词
	public void update(PlayerRecommendKeyWord playerRecommendKeyWord);
	
	//根据推荐热词主键删除热词队列中的数据
	public void delete(PlayerRecommendKeyWord playerRecommendKeyWord);
	
	//根据推荐热词地区批量删除热词队列中的数据
	public void delete(Set<String> areaCodeSet);
	
}
