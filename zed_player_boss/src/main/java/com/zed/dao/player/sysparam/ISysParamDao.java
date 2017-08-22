package com.zed.dao.player.sysparam;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.sysparam.SysParam;

/**
 * @date : 2017年2月20日 下午4:26:23
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
public interface ISysParamDao<T extends Serializable> extends PageDao<SysParam> {

	/**
	 * @date : 2017年2月20日 下午6:45:40
	 * @author : Iris.Xiao
	 * @param paramId
	 * @param paramName
	 * @param countryCode
	 * @return
	 * @description : 是否存在
	*/
	public Object existsSysParam(String paramId,String paramName,String countryCode);
}
