package com.zed.util;

import java.util.Arrays;

public class ChartsData {

	/***系列名称 */ 	
	private String name;
	/***系列数据*/
	private Integer[] datas;
	
	public ChartsData(String name, Integer[] datas) {
		super();
		this.name = name;
		this.datas = datas;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer[] getDatas() {
		return datas;
	}
	public void setDatas(Integer[] datas) {
		this.datas = datas;
	}
	@Override
	public String toString() {
		return "{name:" + "\'"+name +"\'"+ ", data:" + Arrays.toString(datas)
				+ "}";
	}
	
}
