package com.zed.domain.player.logicalfile;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.model.BaseModel;
import com.zed.common.util.JsonUtils;

public class PlayerLogicalPlayTimes extends BaseModel{

	private static final long serialVersionUID = 1L;
	private String playId;
	private String fileId;
	private Double times;
	
	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Double getTimes() {
		return times;
	}

	public void setTimes(Double times) {
		this.times = times;
	}

	@Override
	public void addInfos() {
	}
	
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("playId", this.getPlayId());
		map.put("fileId", this.getFileId());
		map.put("times", this.getTimes());
		return map;
	}
	
	public static PlayerLogicalPlayTimes getPlayerPlayTimes(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerLogicalPlayTimes.class);
	}
	
}