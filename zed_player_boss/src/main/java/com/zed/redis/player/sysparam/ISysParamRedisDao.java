package com.zed.redis.player.sysparam;

import com.zed.domain.player.sysparam.SysParam;

/**
 * @date : 2017年2月20日 下午4:31:06
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
public interface ISysParamRedisDao {
	/**
	 * @date : 2017年2月20日 下午4:33:44
	 * @author : Iris.Xiao
	 * @param sysParam
	 * @description : 增
	*/
	public void addSysParam(SysParam sysParam);
	
	/**
	 * @date : 2017年2月20日 下午4:33:56
	 * @author : Iris.Xiao
	 * @param paramId
	 * @description : 删
	*/
	public void deleteSysParam(String paramName,String countryCode);

	/**
	 * @date : 2017年2月20日 下午4:33:34
	 * @author : Iris.Xiao
	 * @param paramId
	 * @return
	 * @description : 查
	*/
	public String getSysParam(String paramName,String countryCode);
}
