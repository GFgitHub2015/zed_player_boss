package com.zed.service.version.version;

import java.util.HashMap;

import com.zed.domain.version.version.Version;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface VersionService {
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
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
	public Version findById(String versionId, String packagename,String channel) throws AppValidationException;
	//添加对象
	public void add(Version version) throws AppValidationException;
	//修改对象
	public void update(Version version) throws AppValidationException;
	//根据versionId删除对象(可批量)
//	public void delete(String ... versionIds) throws AppValidationException;
	//根据versionId删除对象
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
	
	public String findMaxId(String versionId, String channel);
}
