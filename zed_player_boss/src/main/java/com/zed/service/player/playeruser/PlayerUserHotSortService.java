package com.zed.service.player.playeruser;

import java.util.HashMap;

import com.zed.domain.player.playeruserhotsort.PlayerUserHotSort;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerUserHotSortService {
	//根据主键获取热门用户排序记录
	public PlayerUserHotSort findById(String userSortId);
	//根据用户id获取热门用户排序记录
	public PlayerUserHotSort findByUserId(String userId);
	//添加热门用户排序记录
	public void addPlayerUserHotSort(PlayerUserHotSort playerUserHotSort);
	//根据主键删除记录
	public void deleteById(String []userSortIds);
	//根据用户id删除记录
	public void deleteByUserId(String []userIds);
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//修改热门用户排序
	public void updateSort(PlayerUserHotSort playerUserHotSort) throws AppValidationException;
	//根据国家码和排序序号查询
	public PlayerUserHotSort findByCountryCodeWithSort(String countryCode, Integer sort) throws AppValidationException;

}
