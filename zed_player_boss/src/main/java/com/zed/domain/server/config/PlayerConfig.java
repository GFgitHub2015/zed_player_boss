package com.zed.domain.server.config;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerConfig implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	//google 审核状态
	public static enum ReviewStatus{
		REVIEWING(0), 	//审核中
		REVIEWED(1); 	//审核通过
		private int status;
		ReviewStatus(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	
	private String configId;
	private Integer reviewStatus;  //google审核状态  0:审核中 1:审核通过
	private String version;
	private String packageName;	//app包名
	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", this.getVersion());
		map.put("reviewStatus", this.getReviewStatus());
		map.put("packageName", this.getPackageName());
		return map;
	}
	
	public static PlayerConfig getPlayerConfig(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerConfig.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
