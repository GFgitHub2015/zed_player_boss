package com.zed.dao.version.version;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.version.version.Version;
import com.zed.exception.AppValidationException;

public interface VersionDao<T extends Serializable> extends PageDao<Version> {
	


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
	public Version findByVersionIdAndPackageName(String versionId, String packagename,String channel) throws AppValidationException;
	
	
	public void deleteVersion(Version version) throws AppValidationException;
	
	/**
	 * @date : 2017年2月27日 下午6:42:16
	 * @author : Iris.Xiao
	 * @param versionId
	 * @param packagename
	 * @param channel
	 * @throws AppValidationException
	 * @description : 删除,添加渠道条件
	*/
	public void deleteByVersionIdAndPackageName(String versionId, String packagename,String channel) throws AppValidationException;

	/**
	 * 渠道版本号是否已添加过
	 * @param versionId
	 * @param channel
	 * @return
	 * @throws AppValidationException
	 */
	public String findMaxId(String versionId, String channel) throws AppValidationException;

}
