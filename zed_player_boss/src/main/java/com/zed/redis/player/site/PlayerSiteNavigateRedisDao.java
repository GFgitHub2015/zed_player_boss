package com.zed.redis.player.site;

import com.zed.domain.player.site.PlayerSiteNavigate;

public interface PlayerSiteNavigateRedisDao {
	/*//获取网站导航队列所有的热门网址
	public List<Map<String, Object>> findTopPlayerSiteNavigate(Integer topSize);*/
	
	//批量添加热门网址
//	public void addBatch(List<PlayerSiteNavigate> playerSiteNavigateList);
	
	//添加热门网址
	public void addPlayerSiteNavigate(PlayerSiteNavigate playerSiteNavigate);
	
//	//根据网址id删除
//	public void deleteByIds(String ...siteNavigateIds);
	
	//根据countryCodes 批量删除缓存信息
	public void deleteByCountryCodes(String ... countryCodes);

}
