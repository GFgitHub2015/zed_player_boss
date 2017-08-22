package com.zed.dao.player.clientinfo.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.clientinfo.PlayerClientInfoDao;
import com.zed.domain.player.clientinfo.PlayerClientInfo;
import com.zed.exception.AppValidationException;
import com.zed.util.CommUtil;

@Repository("playerClientInfoDao")
public class PlayerClientInfoDaoImpl<T> extends AbstractPlayerPageDao<PlayerClientInfo> implements PlayerClientInfoDao<PlayerClientInfo> {

	@Override
	public List<String> findAppVersionList() throws AppValidationException {
		return findByMethodName("findAppVersionList");
	}

	@Override
	public List<String> findChannelList() throws AppValidationException {
		return findByMethodName("findChannelList");
	}

	@Override
	public List<String> findAreaCodeList() throws AppValidationException {
		return findByMethodName("findAreaCodeList");
	}

	@Override
	public List<String> findPackageName() throws AppValidationException {
		return findByMethodName("findPackageName");
	}

	@Override
	public List<String> findLanguageList() throws AppValidationException {
		return findByMethodName("findLanguageList");
	}

	@Override
	public List<String> findSysType() throws AppValidationException {
		return findByMethodName("findSysType");
	}

	@Override
	public PlayerClientInfo findByUserId(String userId) throws AppValidationException {
		return findById("findByUserId", userId);
	}
	
	private List<String> findByMethodName(String methodName) throws AppValidationException{
		List<String> resultList = new ArrayList<>(); 
		List<Object> objectList  = findMore(methodName);
		for (Object object : objectList) {
			if (object instanceof String) {
				resultList.add((String)object);
			}
		}
		return resultList;
	}
	
}
