package com.zed.redis.player.sysparam.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.exception.AppErrorException;
import com.zed.common.util.CommUtil;
import com.zed.common.util.StringUtil;
import com.zed.domain.player.sysparam.SysParam;
import com.zed.redis.player.sysparam.ISysParamRedisDao;
import com.zed.util.RedisKey;

/**
 * @date : 2017年2月20日 下午4:31:22
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
@Repository("sysParamRedisDaoImpl")
public class SysParamRedisDaoImpl implements ISysParamRedisDao {

	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;
	/**
	 * @date : 2017年2月20日 下午4:33:44
	 * @author : Iris.Xiao
	 * @param sysParam
	 * @description : 增
	*/
	public void addSysParam(SysParam sysParam){
		if(sysParam==null){
			throw new AppErrorException("参数错误");
		}
		String paramName = sysParam.getParamName();
		if(StringUtil.isBlank(paramName)){
			throw new AppErrorException("参数错误");
		}
		String key = String.format(RedisKey.KEY_PLAYER_SYS_PARAM_HASH, sysParam.getCountryCode());
		cacheRedis.opsForHash().put(key, paramName, sysParam.getParamValue());
	}
	
	/**
	 * @date : 2017年2月20日 下午4:33:56
	 * @author : Iris.Xiao
	 * @param paramId
	 * @description : 删
	*/
	public void deleteSysParam(String paramName,String countryCode){
		if(StringUtil.isBlank(paramName)){
			throw new AppErrorException("参数错误");
		}
		if(StringUtil.isBlank(countryCode)){
			countryCode="0";
		}
		String key = String.format(RedisKey.KEY_PLAYER_SYS_PARAM_HASH, countryCode);
		cacheRedis.opsForHash().delete(key, paramName);
	}

	/**
	 * @date : 2017年2月20日 下午4:33:34
	 * @author : Iris.Xiao
	 * @param paramId
	 * @return
	 * @description : 查
	*/
	public String getSysParam(String paramName,String countryCode){
		if(StringUtil.isBlank(paramName)){
			throw new AppErrorException("参数错误");
		}
		if(StringUtil.isBlank(countryCode)){
			countryCode="0";
		}
		String key = String.format(RedisKey.KEY_PLAYER_SYS_PARAM_HASH, countryCode);
		Object obj = cacheRedis.opsForHash().get(key, paramName);
		return obj==null?null:obj.toString();
	}
}
