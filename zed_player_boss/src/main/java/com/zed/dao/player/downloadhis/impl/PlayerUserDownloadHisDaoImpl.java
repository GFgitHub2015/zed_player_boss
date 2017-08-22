package com.zed.dao.player.downloadhis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.downloadhis.PlayerUserDownloadHisDao;
import com.zed.domain.player.downloadhis.PlayerUserDownloadHis;
import com.zed.exception.AppValidationException;

@Repository("playerUserDownloadHisDao")
public class PlayerUserDownloadHisDaoImpl<T> extends AbstractPlayerPageDao<PlayerUserDownloadHis> implements PlayerUserDownloadHisDao<PlayerUserDownloadHis> {

	@Override
	public List<String> findHisIdByUserId(String userId) throws AppValidationException {
		return findByMethodName("findHisIdByUserId", userId);
	}
	
	
	private List<String> findByMethodName(String methodName, String agr) throws AppValidationException{
		List<String> resultList = new ArrayList<>(); 
		List<Object> objectList  = findMore(methodName, agr);
		for (Object object : objectList) {
			if (object instanceof String) {
				resultList.add((String)object);
			}
		}
		return resultList;
	}

}
