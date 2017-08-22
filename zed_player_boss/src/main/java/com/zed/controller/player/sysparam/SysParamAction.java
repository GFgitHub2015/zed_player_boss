package com.zed.controller.player.sysparam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.sysparam.SysParam;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.service.player.sysparam.ISysParamService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月20日 下午4:47:11
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数配置
*/
@Controller("sysParamAction")
@Scope(value="prototype")
public class SysParamAction extends BaseAction {
	private static final long serialVersionUID = 1468761008362988926L;
	private String paramId;
	private SysParam sysParam;
	private String paramType ;//参数类别
	private String paramName ;//参数名称
	private String paramValue ;//参数值
	private String paramDec ;//参数描述
	private Integer useType ;//0:运营系统，1:客户端
	private String countryCode ;//国家码
	private Page<SysParam> page = new Page<SysParam>();
	private List<Map<String,String>> appInfos = new ArrayList<Map<String,String>>();//所有的app信息
	Map<String,String> paramNameValue = new HashMap<String,String>();//需要用到的参数名称和参数值
	@Resource(name="sysParamService")
	private ISysParamService sysParamService;
	@Resource(name="advertisementDataService")
	private AdvertisementDataService advertisementDataService;
	
	
	/**
	 * @date : 2016年12月28日 下午5:10:30
	 * @author : Iris.Xiao
	 * @return
	 * @description : 列表
	*/
	public String list(){
		Map<String,Object> map = new HashMap<String,Object>();
		page.setSorting("a.param_type,a.order_by, a.CREATE_TIME DESC");
		if (!CommUtil.isEmpty(paramType)) {
			map.put("paramType", paramType);
		}
		if (!CommUtil.isEmpty(paramName)) {
			map.put("paramNameLike", paramName);
		}
		if (!CommUtil.isEmpty(countryCode)) {
			map.put("countryCode", countryCode);
		}
//		if (tagType!=null) {
//			map.put("tagType", tagType);
//		}
		page.setParamsMap(map);
		sysParamService.findByPage(page);
		appInfos = advertisementDataService.getAllAppInfo();
		return SUCCESS;
	}
	
	/**
	 * @date : 2017年6月28日 下午4:26:20
	 * @author : Iris.Xiao
	 * @description : 生成站长banner默认的url,默认下载页
	*/
	public void createBannerDefaultUrl(){
		JSONObject json = new JSONObject();
		json.put("channelType", countryCode);
		Map<String,String> map = sysParamService.getParamsByChannel(countryCode);
		Map<String,String > result = new HashMap<String,String>();
		String pvalue = "";//站长banner下载页参数
		String shareWebIndex ="http://player.3dzed.com/player/sharevideo/shareWebIndex.action";//站长banner下载页url
		if(map!=null&&map.size()>0){
			String packageName= map.get("packageName");
			if(!CommUtil.isEmpty(packageName)){
				json.put("packageName", packageName);
				pvalue = encryptData(json.toString());
				if(map.get("shareWebIndex")!=null){
					shareWebIndex = map.get("shareWebIndex");
				}
				result.put("url", shareWebIndex+"?p="+pvalue);
			}
			
		}
		try {
			outputResult(result, SUCCESS, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 详情
	*/
	public String detail(){
		sysParam = sysParamService.getSysParam(paramId);
		return SUCCESS;
	}

	/**
	 * @date : 2016年12月28日 下午5:10:14
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加
	*/
	public String addSysParam(){
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			sysParam.setParamId(CommUtil.getUUID());
			sysParam.setCreateUser(sessionAdmin.getAdminId());
			sysParam.setCreateTime(DateUtil.getCurTime());
			sysParam.setUpdateTime(DateUtil.getCurTime());
			sysParam.setUpdateUser(sessionAdmin.getAdminId());
			OperationLog oLog = new OperationLog(getIp(), "系统参数配置", "添加:" + sysParam.getParamName() ,new Date() , sessionAdmin.getAdminId());
			sysParamService.addSysParam(sysParam);
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_SYSPARAM_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("sysParamAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2016年12月28日 下午5:10:08
	 * @author : Iris.Xiao
	 * @return
	 * @description : 删除
	*/
	public String deleteSysParam(){

		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			sysParamService.deleteSysParam(paramId);
			OperationLog oLog = new OperationLog(getIp(), "系统参数配置", "删除:" +paramId ,new Date() , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_SYSPARAM_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("sysParamAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	/**
	 * @date : 2016年12月28日 下午5:10:00
	 * @author : Iris.Xiao
	 * @return
	 * @description : 修改
	*/
	public String updateSysParam(){

		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			sysParam.setUpdateTime(DateUtil.getCurTime());
			sysParam.setUpdateUser(sessionAdmin.getAdminId());
			sysParamService.updateSysParam(sysParam);
			OperationLog oLog = new OperationLog(getIp(), "系统参数配置", "修改:" +sysParam.getParamName() ,new Date() , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_SYSPARAM_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("sysParamAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	/**
	 * @date : 2016年12月28日 下午5:10:00
	 * @author : Iris.Xiao
	 * @return
	 * @description : 是否重复
	*/
	public void existsSysParam(){
    	response.setContentType("text/html;charset=utf-8");
    	Map<String,Object> map = new HashMap<String,Object>();
    	boolean exists = sysParamService.existsSysParam(paramId, paramName, countryCode);
    	map.put("exists", exists);
    	String returnResult = JSON.toJSONString(map);
        try {
			response.getWriter().println(returnResult);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
	        try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
    }
	
	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加跳转
	*/
	public String addPage(){
		appInfos = advertisementDataService.getAllAppInfo();
		paramNameValue = sysParamService.getParamsByChannel("name_value_all");
		return SUCCESS;
	}

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 修改跳转
	*/
	public String updatePage(){
		sysParam = sysParamService.getSysParam(paramId);
		appInfos = advertisementDataService.getAllAppInfo();
		paramNameValue = sysParamService.getParamsByChannel("name_value_all");
		return SUCCESS;
	}
	
	
	public String doExecute() throws Exception {
		return null;
	}
	public SysParam getSysParam() {
		return sysParam;
	}
	public void setSysParam(SysParam sysParam) {
		this.sysParam = sysParam;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamDec() {
		return paramDec;
	}
	public void setParamDec(String paramDec) {
		this.paramDec = paramDec;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Page<SysParam> getPage() {
		return page;
	}
	public void setPage(Page<SysParam> page) {
		this.page = page;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public List<Map<String, String>> getAppInfos() {
		return appInfos;
	}

	public void setAppInfos(List<Map<String, String>> appInfos) {
		this.appInfos = appInfos;
	}

	public Map<String, String> getParamNameValue() {
		return paramNameValue;
	}

	public void setParamNameValue(Map<String, String> paramNameValue) {
		this.paramNameValue = paramNameValue;
	}

}
