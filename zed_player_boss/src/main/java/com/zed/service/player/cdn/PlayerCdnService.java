package com.zed.service.player.cdn;

import java.util.HashMap;

import com.zed.domain.player.cdn.PlayerCdn;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerCdnService {
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据cdnId获取对象
	public PlayerCdn findById(String cdnId) throws AppValidationException;
	//添加对象
	public void add(PlayerCdn playerCdn) throws AppValidationException;
	//修改对象
	public void update(PlayerCdn playerCdn) throws AppValidationException;
	//根据cdnId删除对象(可批量)
	public void delete(String ... cdnIds) throws AppValidationException;
	//修改对象的状态
	public void updateStatus(PlayerCdn playerCdn) throws AppValidationException;
	
	public PlayerCdn getPlayerCdn() throws AppValidationException;
}
