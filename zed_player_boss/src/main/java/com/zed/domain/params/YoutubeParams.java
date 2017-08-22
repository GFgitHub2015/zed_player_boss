package com.zed.domain.params;

import java.io.Serializable;

/**
 * @date : 2017年7月03日 下午4:10:01
 * @author : X.Long
 * @version : 1.0
 * @description : 第三方YouTube 查询时使用的字，后续需要增加查询字段只需要在该类添加属性
*/
public class YoutubeParams implements Serializable {

	private static final long serialVersionUID = 1L;
	private String q;
	private String pageToken;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getPageToken() {
		return pageToken;
	}
	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}
	
}
