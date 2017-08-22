package com.zed.dao.version.version.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.version.base.abstractdao.AbstractVersionPageDao;
import com.zed.dao.version.version.VersionDao;
import com.zed.domain.version.version.Version;
import com.zed.exception.AppValidationException;

@Repository("versionDao")
public class VersionDaoImpl<T> extends AbstractVersionPageDao<Version> implements VersionDao<Version> {

	/**
	 * @date : 2017年2月27日 下午6:43:03
	 * @author : Iris.Xiao
	 * @param versionId
	 * @param packagename
	 * @param channel
	 * @return
	 * @throws AppValidationException
	 * @description :  查找,添加渠道条件
	*/
	public Version findByVersionIdAndPackageName(String versionId, String packagename,String channel) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("versionId", versionId);
		params.put("packagename", packagename);
		if(channel!=null&&!"".equals(channel)){
			params.put("channel", channel);
		}
		return find("findByVersionIdAndPackageName", params);
	}

	@Override
	public void deleteVersion(Version version) throws AppValidationException {
		getSqlSessionTemplate().delete(getSqlName("deleteVersion"), version);
	}

	/**
	 * @date : 2017年2月27日 下午6:42:16
	 * @author : Iris.Xiao
	 * @param versionId
	 * @param packagename
	 * @param channel
	 * @throws AppValidationException
	 * @description : 删除,添加渠道条件
	*/
	public void deleteByVersionIdAndPackageName(String versionId, String packagename,String channel) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("versionId", versionId);
		params.put("packagename", packagename);
		if(channel!=null&&!"".equals(channel)){
			params.put("channel", channel);
		}
		getSqlSessionTemplate().delete(getSqlName("deleteByVersionIdAndPackageName"), params);
		
	}

	@Override
	public String findMaxId(String versionId, String channel) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("versionId", versionId);
		params.put("channel", channel);
		return getSqlSessionTemplate().selectOne(getSqlName("findMaxId"), params);
	}

}
