package com.zed.service.player.sysparam;

import java.util.Map;

import com.zed.domain.player.sysparam.SysParam;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月20日 下午4:35:54
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
public interface ISysParamService {
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
	public void deleteSysParam(String paramId);
	
	/**
	 * @date : 2017年2月20日 下午4:33:56
	 * @author : Iris.Xiao
	 * @param paramId
	 * @description : 改
	*/
	public void updateSysParam(SysParam sysParam);

	/**
	 * @date : 2017年2月20日 下午4:33:34
	 * @author : Iris.Xiao
	 * @param paramId
	 * @return
	 * @description : 查
	*/
	public SysParam getSysParam(String paramId);
	

	/**
	 * @date : 2017年6月15日 上午11:01:49
	 * @author : Iris.Xiao
	 * @param paramName
	 * @param channel
	 * @return
	 * @description : 查
	*/
	public SysParam getSysParam(String paramName,String channel);
	
	/**
	 * @date : 2017年2月20日 下午4:39:20
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 分页查
	*/
	public Page<SysParam> findByPage(Page<SysParam> page);


	/**
	 * @date : 2017年2月20日 下午6:45:40
	 * @author : Iris.Xiao
	 * @param paramId
	 * @param paramName
	 * @param countryCode
	 * @return
	 * @description : 是否存在
	*/
	public boolean existsSysParam(String paramId,String paramName,String countryCode);
	

	/**
	 * @date : 2017年6月15日 上午10:18:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @return
	 * @description :  以map的形式返回参数
	*/
	public Map<String,String> getParamsByChannel( String countryCode);
}
