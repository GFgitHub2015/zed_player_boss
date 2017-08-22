package com.zed.domain.player.black;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerBlackAreaCode implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String blackId;
	private String areaCode;
	public String getBlackId() {
		return blackId;
	}
	public void setBlackId(String blackId) {
		this.blackId = blackId;
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
	
	public static PlayerBlackAreaCode getPlayerBlackAreaCode(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerBlackAreaCode.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}

}
