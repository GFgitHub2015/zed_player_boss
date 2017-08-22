package com.zed.util;

import java.util.Arrays;
import java.util.List;

public class Charts {
	
	private String[] xCategories;
	
	private List<ChartsData> chartsDatas;
	private List<ChartsAmount> chartsAmounts;
	
	
	public Charts(String[] xCategories,  List<ChartsData> chartsDatas, List<ChartsAmount> chartsAmounts) {
		this.xCategories = xCategories;
		this.chartsAmounts = chartsAmounts;
	}

	public Charts(String[] xCategories, List<ChartsData> chartsDatas) {
		super();
		this.xCategories = xCategories;
		this.chartsDatas = chartsDatas;
	}

	public String[] getxCategories() {
		return xCategories;
	}

	public void setxCategories(String[] xCategories) {
		this.xCategories = xCategories;
	}

	public List<ChartsData> getChartsDatas() {
		return chartsDatas;
	}

	public void setChartsDatas(List<ChartsData> chartsDatas) {
		this.chartsDatas = chartsDatas;
	}

	
	public List<ChartsAmount> getChartsAmounts() {
		return chartsAmounts;
	}

	public void setChartsAmounts(List<ChartsAmount> chartsAmounts) {
		this.chartsAmounts = chartsAmounts;
	}

	public String castToString() {
		StringBuffer sb= new StringBuffer();
		for (ChartsData chartsData : chartsDatas) {
			sb.append(chartsData.toString());
			sb.append(",");
		}
		return sb.toString()+"{categories:"+Arrays.toString(xCategories)+"}";
	}
	
	public String castToStringAmount() {
		StringBuffer sb= new StringBuffer();
		for (ChartsAmount chartsAmount : chartsAmounts) {
			sb.append(chartsAmount.toString());
			sb.append(",");
		}
		return sb.toString()+"{categories:"+Arrays.toString(xCategories)+"}";
	}
	
} 


