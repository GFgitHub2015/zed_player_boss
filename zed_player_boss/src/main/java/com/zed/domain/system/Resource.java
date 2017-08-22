package com.zed.domain.system;

import java.io.Serializable;
import java.util.Date;

import com.zed.util.CommUtil;

public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long resourceId;
	private String resourceName;
	private Long partentId;
	private String partentName;
	private String resourceKey;
	private String resourceUrl;
//	private String resourceImage;
	private Integer resourceOrder;
	private Integer resourceLevel;
	private String description;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;
	private String functionName;
	private String enable;
	
	
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Long getPartentId() {
		return partentId;
	}
	public void setPartentId(Long partentId) {
		this.partentId = partentId;
	}
	public String getResourceKey() {
		return resourceKey;
	}
	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public Integer getResourceOrder() {
		return resourceOrder;
	}
	public void setResourceOrder(Integer resourceOrder) {
		this.resourceOrder = resourceOrder;
	}
	public Integer getResourceLevel() {
		return resourceLevel;
	}
	public void setResourceLevel(Integer resourceLevel) {
		this.resourceLevel = resourceLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getPartentName() {
		return partentName;
	}
	public void setPartentName(String partentName) {
		this.partentName = partentName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public String generateId(){
		return CommUtil.random();
	}
}
