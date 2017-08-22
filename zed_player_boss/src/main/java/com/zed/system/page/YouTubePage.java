package com.zed.system.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YouTubePage<T> implements Serializable {
	
	private static final long serialVersionUID = -1688241932064784307L;
	
	protected String pageToken;								//当前页
    protected long pageSize = 20;							//每一页记录数
    protected String sorting = "";							//排序字段
    protected List<T> result = new ArrayList<T>();  		//返回实体结果集
    protected long totalCount = 0;							//总记录数
    protected String prevPageToken;							//上一页
    protected String nextPageToken;							//下一页
    protected T object;										//单个对象，查询使用
    protected Map<String, Object> paramsMap = new HashMap<String, Object>(); //特殊参数设置(对于对象不能设置的参数，进行补充，如时间段查询)
    
	public String getPageToken() {
		return pageToken;
	}
	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
	public String getPrevPageToken() {
		return prevPageToken;
	}
	public void setPrevPageToken(String prevPageToken) {
		this.prevPageToken = prevPageToken;
	}
	public String getNextPageToken() {
		return nextPageToken;
	}
	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
    
}
