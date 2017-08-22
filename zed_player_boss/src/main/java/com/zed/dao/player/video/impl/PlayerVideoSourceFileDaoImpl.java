package com.zed.dao.player.video.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.video.PlayerVideoSourceFileDao;
import com.zed.domain.player.video.PlayerVideoSourceFile;
import com.zed.exception.AppValidationException;

@Repository("playerVideoSourceFileDao")
public class PlayerVideoSourceFileDaoImpl extends AbstractPlayerPageDao<PlayerVideoSourceFile> implements PlayerVideoSourceFileDao<PlayerVideoSourceFile> {

	@Override
	public List<String> findFileSuffix() throws AppValidationException {
		List<String> fileSuffixList = new ArrayList<>(); 
		List<Object> objectList  = findMore("findFileSuffix");
		for (Object object : objectList) {
			if (object instanceof String) {
				fileSuffixList.add((String)object);
			}
		}
		return fileSuffixList;
	}

	@Override
	public void deleteByResId(String... redIds) throws AppValidationException {
		delete("deleteByResId", redIds);
	}

	@Override
	public List<PlayerVideoSourceFile> findAllByResId(String resId) throws AppValidationException {
		return findList("findAllByResId", resId);
	}

	@Override
	public Long findCountByResIdWithStatus(Integer status, String[] resIds) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>(); 
		params.put("status", status);
		params.put("resIds", resIds);
		Long count = Long.parseLong(findOne("findCountByResIdWithStatus", params).toString());
		return count;
	}


}
