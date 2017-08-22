package com.zed.service.version.version.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.version.version.VersionDao;
import com.zed.domain.version.version.Version;
import com.zed.exception.AppValidationException;
import com.zed.service.version.version.VersionService;
import com.zed.system.page.Page;

@Service("versionService")
public class VersionServiceImpl implements VersionService {
	@Resource(name="versionDao")
	private VersionDao versionDao;

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return versionDao.findByPage(page);
	}

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
	public Version findById(String versionId, String packagename,String channel) throws AppValidationException {
		return (Version) versionDao.findByVersionIdAndPackageName(versionId, packagename,channel);
	}

	@Override
	public void add(Version version) throws AppValidationException {
		versionDao.add(version);		
	}

	@Override
	public void update(Version version) throws AppValidationException {
		versionDao.update(version);		
	}

	/*@Override
	public void delete(String... versionIds) throws AppValidationException {
		versionDao.delete(versionIds);		
	}*/

	@Override
	public void deleteVersion(Version version) throws AppValidationException {
		versionDao.deleteVersion(version);		
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
		versionDao.deleteByVersionIdAndPackageName(versionId, packagename,channel);		
	}

	/**
	 * 渠道版本号是否已添加过
	 * @param versionId
	 * @param channel
	 * @return
	 * @throws AppValidationException
	 */
	public String findMaxId(String versionId, String channel) throws AppValidationException {
		return versionDao.findMaxId(versionId, channel);
	}
}
