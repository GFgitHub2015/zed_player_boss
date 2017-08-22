package com.zed.domain.system;

import java.io.Serializable;
import java.util.Date;

import com.zed.util.CommUtil;

public class OperationLog implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;				//主键
	private String ip;				//IP
	private String operationMenu;	//操作菜单
	private String content;			//内容
	private Date operationTime;		//操作时间
	private String createdBy;		//关联创建人
	
	public OperationLog(){
		
	}
	
	/**
	 * 操作日志构造函数
	 * @param ip IP
	 * @param operationMenu 操作菜单
	 * @param content 内容
	 * @param operationTime 操作时间
	 * @param createdBy 创建人
	 */
	public OperationLog(String ip, String operationMenu,String content, Date operationTime, String createdBy){
		this.ip = ip;
		this.operationMenu = operationMenu;
		this.content = content;
		this.operationTime = operationTime;
		this.createdBy = createdBy;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperationMenu() {
		return operationMenu;
	}
	public void setOperationMenu(String operationMenu) {
		this.operationMenu = operationMenu;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String generateId(){
		return CommUtil.random();
	}
}
