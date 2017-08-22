package com.zed.domain.player.white;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerWhiteAreaCode implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String whiteAreaCodeId;
	private String areaCode;
	public String getWhiteAreaCodeId() {
		return whiteAreaCodeId;
	}
	public void setWhiteAreaCodeId(String whiteAreaCodeId) {
		this.whiteAreaCodeId = whiteAreaCodeId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaCode", this.getAreaCode());
		return map;
	}
	
	public static PlayerWhiteAreaCode getPlayerWhiteAreaCode(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerWhiteAreaCode.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}

}
