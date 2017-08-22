package com.zed.domain.player.sysparam;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;

/**
 * @date : 2017年2月20日 下午2:06:27
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 系统参数
*/
public class SysParam  extends BaseModel {
	private static final long serialVersionUID = -2072333481286657116L;
	private String paramId ;//参数编号
	private String paramType ;//参数类别
	private String paramName ;//参数名称
	private String paramValue ;//参数值
	private String paramDec ;//参数描述
	private String countryCode ;//国家码
	private String appname ;//应用名称
	private Timestamp updateTime ;//修改时间
	private String createUser ;//创建人
	private String updateUser ;//修改人
	private Integer orderBy ;//同一类型参数排序
	
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
