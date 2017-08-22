package com.zed.service.player.es;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.logicalfile.PlayerVideoResources;
import com.zed.system.page.Page;

public interface PlayerVideoElasticsearch2Service {
	
	/**
	 * 添加资源到到搜索引擎
	 * @param PlayerVideoResources
	 */
	public void add(PlayerVideoResources playerVideoResources2);
	
	/**
	 * 批量添加资源到到搜索引擎
	 */
	public void add(List<PlayerVideoResources> playerVideoResources2List);
	/**
	 * 搜索引擎查询
	 * @param param 参数key值为：pageSize,pageNo
	 * @param fileName 查询的内容，如：superman、xman等
	 * @return
	 */
	public List<PlayerVideoResources> find(Page<PlayerVideoResources> page, String fileName, String userId, String dimension);
	/**
	 * 搜索引擎查询
	 * @param param 参数key值为：pageSize,pageNo
	 * @param content 查询的内容，如：superman、xman等
	 * @return
	 */
	public List<PlayerVideoResources> find(Page<PlayerVideoResources> page);
	/**
	 * 搜索引擎查询
	 * @param param 参数key值为：pageSize,pageNo
	 * @param content 查询的内容，如：superman、xman等
	 * @return
	 */
	public Page<HashMap> findByPage(Page<HashMap> page);
	
	/**
	 * 根据主键获取对象
	 * @param id
	 * @return
	 */
	public PlayerVideoResources findByFileId(String fileId);
	
	public void deleteByIds(String ...fileIds);
	
	public Boolean deleteIndex();
	public Boolean createIndexAndTypeWithMapping();

}
