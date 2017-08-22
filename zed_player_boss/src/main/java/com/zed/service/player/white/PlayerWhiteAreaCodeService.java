package com.zed.service.player.white;

import java.util.HashMap;

import com.zed.domain.player.white.PlayerWhiteAreaCode;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerWhiteAreaCodeService {
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据whiteAreaCodeId获取对象
	public PlayerWhiteAreaCode findById(String whiteAreaCodeId) throws AppValidationException;
	//添加对象
	public void add(PlayerWhiteAreaCode playerWhiteAreaCode) throws AppValidationException;
	//修改对象
//	public void update(PlayerWhiteAreaCode playerWhiteAreaCode) throws AppValidationException;
	//根据whiteAreaCodeId删除对象(可批量)
	public void delete(String ... whiteAreaCodeIds) throws AppValidationException;
	//根据areaCode获取对象
	public PlayerWhiteAreaCode findByAreaCode(String areaCode) throws AppValidationException;
	
	
	

}
