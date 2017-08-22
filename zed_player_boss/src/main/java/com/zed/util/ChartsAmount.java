package com.zed.util;

import java.math.BigDecimal;
import java.util.Arrays;

public class ChartsAmount {

	/***系列名称 */ 	
	private String name;
	/***系列数据*/
	private BigDecimal[] datas;
	
	public ChartsAmount(String name, BigDecimal[] datas) {
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
	public BigDecimal[] getDatas() {
		return datas;
	}
	public void setDatas(BigDecimal[] datas) {
		this.datas = datas;
	}
	@Override
	public String toString() {
		return "{name:" + "\'"+name +"\'"+ ", data:" + Arrays.toString(datas)
				+ "}";
	}
	
}
