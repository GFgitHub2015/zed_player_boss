package com.zed.service.player.sysparam.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.exception.AppErrorException;
import com.zed.common.util.CommUtil;
import com.zed.dao.player.sysparam.ISysParamDao;
import com.zed.domain.player.sysparam.SysParam;
import com.zed.redis.player.sysparam.ISysParamRedisDao;
import com.zed.service.player.sysparam.ISysParamService;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月20日 下午4:37:06
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
@Service("sysParamService")
public class SysParamServiceImpl implements ISysParamService{
//	private Logger log = Log.getLogger(this.getClass());
	@Resource(name="sysParamDaoImpl")
	private ISysParamDao<SysParam> sysParamDao;
	@Resource(name="sysParamRedisDaoImpl")
	private ISysParamRedisDao sysParamRedisDao;
	
	/**
	 * @date : 2017年2月20日 下午4:33:44
	 * @author : Iris.Xiao
	 * @param sysParam
	 * @description : 增
	*/
	public void addSysParam(SysParam sysParam){
		if(sysParam==null){
//			log.error("系统参数添加有误");
			throw new AppErrorException("系统参数添加有误");
		}
		String countryCode = sysParam.getCountryCode();
		if(CommUtil.isEmpty(countryCode)){
			sysParam.setCountryCode("0");
		}
		sysParamDao.add(sysParam);
		updateCache(sysParam,true);
	}
	
	/**
	 * @date : 2017年2月20日 下午4:33:56
	 * @author : Iris.Xiao
	 * @param paramId
	 * @description : 删
	*/
	public void deleteSysParam(String paramId){
		if(CommUtil.isEmpty(paramId)){
//			log.error("系统参数删除有误");
			throw new AppErrorException("系统参数删除有误");
		}
		SysParam sysParam = sysParamDao.findById(paramId);
		sysParamDao.delete(paramId);
		sysParamRedisDao.deleteSysParam(sysParam.getParamName(), sysParam.getCountryCode());
	}
	
	/**
	 * @date : 2017年2月20日 下午4:33:56
	 * @author : Iris.Xiao
	 * @param paramId
	 * @description : 改
	*/
	public void updateSysParam(SysParam sysParam){
		SysParam old = sysParamDao.findById(sysParam.getParamId());
		String countryCode = sysParam.getCountryCode();
		if(CommUtil.isEmpty(countryCode)){
			sysParam.setCountryCode("0");
		}
		//这里有可能参数名和国家码都改了,所以先删除原来的缓存
		updateCache(old,false);
		sysParamDao.update(sysParam);
		updateCache(sysParam,true);
	}

	/**
	 * @date : 2017年2月20日 下午4:33:34
	 * @author : Iris.Xiao
	 * @param paramId
	 * @return
	 * @description : 查
	*/
	public SysParam getSysParam(String paramId){
		SysParam sysParam = sysParamDao.findById(paramId);
		return sysParam;
	}
	
	/**
	 * @date : 2017年2月20日 下午4:39:20
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 分页查
	*/
	public Page<SysParam> findByPage(Page<SysParam> page){
		sysParamDao.findByPage(page);
		return page;
	}
	
	/**
	 * @date : 2017年2月20日 下午6:02:33
	 * @author : Iris.Xiao
	 * @param sysParam
	 * @param addFlag 
	 * @description : 刷新缓存
	*/
	public void updateCache(SysParam sysParam,boolean addFlag){
		sysParamRedisDao.deleteSysParam(sysParam.getParamName(),sysParam.getCountryCode());
		if(addFlag){
			sysParamRedisDao.addSysParam(sysParam);
		}
	}
	

	/**
	 * @date : 2017年2月20日 下午6:45:40
	 * @author : Iris.Xiao
	 * @param paramId
	 * @param paramName
	 * @param countryCode
	 * @return
	 * @description : 是否存在
	*/
	public boolean existsSysParam(String paramId,String paramName,String countryCode){
		Object obj = sysParamDao.existsSysParam(paramId, paramName, countryCode);
		if(obj==null){
			return false;
		}
		int count =  Integer.parseInt(obj.toString());
		return count>0?true:false;
	}
	

	/**
	 * @date : 2017年6月15日 上午10:18:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @return
	 * @description :  以map的形式返回参数
	*/
	public Map<String,String> getParamsByChannel( String countryCode){
		Page<SysParam>  page = new Page<SysParam>();
		page.getParamsMap().put("countryCode", countryCode);
		page.setPageSize(99999);
		page.setSorting(" a.order_by ");
		findByPage(page);
		List<SysParam> list = page.getResult();
		Map<String, String> result = new LinkedHashMap<String, String>();
		if(list!=null&&list.size()>0){
			for (SysParam sysParam : list) {
				result.put(sysParam.getParamName(), sysParam.getParamValue());
			}
		}
		return result;
	}
	

	/**
	 * @date : 2017年6月15日 上午11:01:49
	 * @author : Iris.Xiao
	 * @param paramName
	 * @param channel
	 * @return
	 * @description : 查
	*/
	public SysParam getSysParam(String paramName,String channel){
		Page<SysParam>  page = new Page<SysParam>();
		page.getParamsMap().put("countryCode", channel);
		page.getParamsMap().put("paramName", paramName);
		page.setPageSize(99999);
		findByPage(page);
		List<SysParam> list = page.getResult();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
