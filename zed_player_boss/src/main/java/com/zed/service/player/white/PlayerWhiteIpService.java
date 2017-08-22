package com.zed.service.player.white;

import java.util.HashMap;

import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerWhiteIpService {
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据ip获取对象
	public PlayerWhiteIp findByIp(String ip) throws AppValidationException;
	//添加对象
	public void add(PlayerWhiteIp playerWhiteIp) throws AppValidationException;
//	//修改对象
//	public void update(PlayerWhiteIp playerWhiteIp) throws AppValidationException;
	//根据ip删除对象(可批量)
	public void delete(String ... ips) throws AppValidationException;

}
