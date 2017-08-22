package com.zed.domain.server.modelinfo;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;

public class PlayerModelInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String model;
	private Integer dimension;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getDimension() {
		return dimension;
	}
	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", this.getModel());
		map.put("dimension", this.getDimension());
		return map;
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
}
