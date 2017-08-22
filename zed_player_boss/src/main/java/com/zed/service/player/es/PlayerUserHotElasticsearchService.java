package com.zed.service.player.es;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zed.domain.player.playeruser.PlayerUserHot;
import com.zed.system.page.Page;

public interface PlayerUserHotElasticsearchService {
	
	/**
	 * 添加热门用户到到搜索引擎
	 * @param PlayerUserHot
	 */
	public void add(PlayerUserHot playerUserHot);
	
	/**
	 * 批量添加热门用户到搜索引擎
	 */
	public void add(List<PlayerUserHot> playerUserHotList);
	/**
	 * 搜索引擎查询
	 * @param param 参数key值为：pageSize,pageNo
	 * @return
	 */
	public List<PlayerUserHot> find(Page<PlayerUserHot> page, String countryCode, String userId, String status);
	/**
	 * 搜索引擎查询
	 * @param param 参数key值为：pageSize,pageNo
	 * @return
	 */
	public List<PlayerUserHot> find(Page<PlayerUserHot> page);
	/**
	 * 搜索引擎查询
	 * @param param 参数key值为：pageSize,pageNo
	 * @return
	 */
	public Page<HashMap> findByPage(Page<HashMap> page);
	
	/**
	 * 根据主键获取对象
	 * @param id
	 * @return
	 */
	public PlayerUserHot findByUserId(String userId);
	
	public void deleteByIds(String ...userIds);
	
	public Boolean deleteIndex();
	public Boolean createIndexAndTypeWithMapping();
	//方案一
	public void findByAggs(Map<String, Object> param);
	//方案二
	public void findByFunctionScore(Map<String, Object> param);
	//获取每个国家所拥有的公开资源
	public Map<String, Object> getShareCountsWithCountryCodes();

}
