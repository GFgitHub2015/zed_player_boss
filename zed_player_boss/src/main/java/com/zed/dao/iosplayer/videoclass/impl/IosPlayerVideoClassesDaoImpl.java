package com.zed.dao.iosplayer.videoclass.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.iosplayer.videoclass.IosPlayerVideoClassesDao;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.domain.iosplayer.videoclass.IosPlayerVideoClasses;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.exception.AppValidationException;

@Repository("iosPlayerVideoClassesDao")
public class IosPlayerVideoClassesDaoImpl<T> extends AbstractPlayerPageDao<IosPlayerVideoClasses> implements IosPlayerVideoClassesDao<IosPlayerVideoClasses> {

	@Override
	public List<IosPlayerVideoClasses> findBySortAndClassKey(Integer sort, String classKey) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sort", sort);
		params.put("classKey", classKey);
		params.put("origin", IosPlayerVideoClasses.Origin.PLAYER.getOrigin());
		params.put("status", IosPlayerVideoClasses.Status.START.getStatus());
		return findList("findBySortAndClasskey", params);
	}

	@Override
	public IosPlayerVideoClasses findByClasskey(String classKey) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("classKey", classKey);
		params.put("origin", IosPlayerVideoClasses.Origin.PLAYER.getOrigin());
		params.put("status", IosPlayerVideoClasses.Status.START.getStatus());
		return find("findByClasskey", params);
	}

	@Override
	public IosPlayerVideoClasses getLastSort() throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("origin", IosPlayerVideoClasses.Origin.PLAYER.getOrigin());
		params.put("status", IosPlayerVideoClasses.Status.START.getStatus());
		return find("getLastSort", params);
	}

	@Override
	public List<IosPlayerVideoClasses> findBySortBy(Integer sort, String sorting, String comperatorStr) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (comperatorStr.contains(">")) {
			params.put("g", "gt");
			if (comperatorStr.contains("=")) {
				sort--;
			}
		}
		
		if (comperatorStr.contains("<")) {
			params.put("l", "lt");
			if (comperatorStr.contains("=")) {
				sort++;
			}
		}
		params.put("sort", sort);
		params.put("origin", IosPlayerVideoClasses.Origin.PLAYER.getOrigin());
		params.put("status", IosPlayerVideoClasses.Status.START.getStatus());
		params.put("sorting", sorting);
		return findList("findBySortBy", params);
	}

	@Override
	public void deleteFromDisk(String[] ids) throws AppValidationException {
		delete("deleteFromDisk", ids);
	}

	@Override
	public List<IosPlayerVideoClasses> findByDescriptionAndClassKey(String description, String classKey)
			throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("description", description);
		params.put("classKey", classKey);
		params.put("origin", PlayerVideoClasses.Origin.PLAYER.getOrigin());
		params.put("status", PlayerVideoClasses.Status.START.getStatus());
		return findList("findByDescriptionAndClassKey", params);
	}

}
