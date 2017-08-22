package com.zed.domain.player.logicalfile;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.model.BaseModel;
import com.zed.common.util.JsonUtils;

public class PlayerLogicalDownloadTimes  extends BaseModel{

	private static final long serialVersionUID = 1L;
	private String downloadId;
	private String fileId;
	private Double times;
	
	public String getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(String downloadId) {
		this.downloadId = downloadId;
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
		map.put("downloadId", this.getDownloadId());
		map.put("fileId", this.getFileId());
		map.put("times", this.getTimes());
		return map;
	}
	
	public static PlayerLogicalDownloadTimes getPlayerDownloadTimes(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerLogicalDownloadTimes.class);
	}
}