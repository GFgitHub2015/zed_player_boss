package com.zed.domain.player.playerpromotioninfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerPromotionInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String imageUrl;
	private String title;
	private String link;
	private String content;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer status;
	private Date createTime;
	
	private Timestamp upTime ;//上架时间
	private Timestamp downTime ;//下架时间
	private Long promoType ;//'0:其他活动,1:热门推荐,2:视频活动,';
	private Long topFlag ;//是否置顶
	private String countryCode;			//国家码
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", this.getTitle());
		map.put("link", this.getLink());
		map.put("id", this.getId());
		map.put("content", this.getContent());
		map.put("imageUrl",this.getImageUrl());
		map.put("countryCode",this.getCountryCode());
		return map;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpTime() {
		return upTime;
	}
	public void setUpTime(Timestamp upTime) {
		this.upTime = upTime;
	}
	public Timestamp getDownTime() {
		return downTime;
	}
	public void setDownTime(Timestamp downTime) {
		this.downTime = downTime;
	}
	public Long getTopFlag() {
		return topFlag;
	}
	public void setTopFlag(Long topFlag) {
		this.topFlag = topFlag;
	}
	public Long getPromoType() {
		return promoType;
	}
	public void setPromoType(Long promoType) {
		this.promoType = promoType;
	}
	
	
}
