package com.zed.dao.player.sysparam.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.common.util.CommUtil;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.sysparam.ISysParamDao;
import com.zed.domain.player.sysparam.SysParam;

/**
 * @date : 2017年2月20日 下午4:26:43
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
@Repository("sysParamDaoImpl")
public class SysParamDaoImpl<T> extends AbstractPlayerPageDao<SysParam> implements ISysParamDao<SysParam> {

	/**
	 * @date : 2017年2月20日 下午6:45:40
	 * @author : Iris.Xiao
	 * @param paramId
	 * @param paramName
	 * @param countryCode
	 * @return
	 * @description : 是否存在
	*/
	public Object existsSysParam(String paramId,String paramName,String countryCode){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!CommUtil.isEmpty(paramId)){
			map.put("paramId", paramId);
		}
		if(CommUtil.isEmpty(countryCode)){
			countryCode = "0";
		}
		map.put("paramName", paramName);
		map.put("countryCode", countryCode);
		return this.findOne("existsSysParam", map);
	}
}
