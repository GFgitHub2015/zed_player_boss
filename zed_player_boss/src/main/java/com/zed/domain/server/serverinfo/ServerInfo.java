package com.zed.domain.server.serverinfo;

import java.sql.Timestamp;

import com.zed.common.model.BaseModel;
import com.zed.util.CommUtil;

public class ServerInfo extends BaseModel{
	private static final long serialVersionUID = 1L;
	private String infoId;
	private String serverAddress;
	private String areaCode;
	private String areaName;
	private Timestamp updateTime;
	private String creater;
	private String updater;
	private Byte origin;							//会员来源：来自哪个产品。0为直播，1为3dplayer
	
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Byte getOrigin() {
		return origin;
	}

	public void setOrigin(Byte origin) {
		this.origin = origin;
	}

	@Override
	public void addInfos() {
		
	}
	
	public String generateId(){
		return CommUtil.random();
	}

}
