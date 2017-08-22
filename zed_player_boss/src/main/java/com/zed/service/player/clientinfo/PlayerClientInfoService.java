package com.zed.service.player.clientinfo;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.clientinfo.PlayerClientInfo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerClientInfoService {
	//分页查询用户上报信息
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据用户的clientId获取用户上报的信息
	public PlayerClientInfo findById(String clientId) throws AppValidationException;
	//查询所有的app版本号
	public List<String> findAppVersionList() throws AppValidationException;
	//查询所有的渠道号
	public List<String> findChannelList() throws AppValidationException;
	//查询所有的用户的上传的地区码
	public List<String> findAreaCodeList() throws AppValidationException;
	//查询所有的用户的app的包名
	public List<String> findPackageName() throws AppValidationException;
	//查询所有的用户的手机的系统语言
	public List<String> findLanguageList() throws AppValidationException;
	//查询所有的系统类型
	public List<String> findSysType() throws AppValidationException;
	//根据用户的userId获取用户上报的信息
	public PlayerClientInfo findByUserId(String userId) throws AppValidationException;

}
