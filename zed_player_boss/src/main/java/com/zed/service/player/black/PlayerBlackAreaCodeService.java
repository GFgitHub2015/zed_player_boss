package com.zed.service.player.black;

import java.util.HashMap;

import com.zed.domain.player.black.PlayerBlackAreaCode;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerBlackAreaCodeService {
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据blackId获取对象
	public PlayerBlackAreaCode findById(String blackId) throws AppValidationException;
	//添加对象
	public void add(PlayerBlackAreaCode playerBlackAreaCode) throws AppValidationException;
	//根据blackId删除对象(可批量)
	public void delete(String ... blackIds) throws AppValidationException;
	//根据areaCode获取对象
	public PlayerBlackAreaCode findByAreaCode(String areaCode) throws AppValidationException;
	
	
	

}
