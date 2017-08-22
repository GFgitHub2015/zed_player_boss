package com.zed.domain.player.white;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;

public class PlayerWhiteIp implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String ip;
	private String areaCode;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ip", this.getIp());
//		map.put("areaCode", this.getAreaCode());
		return map;
	}
	
	public static PlayerWhiteIp getPlayerWhiteIp(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerWhiteIp.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}

}
