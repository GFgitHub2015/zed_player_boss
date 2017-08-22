package com.zed.domain.player.user;

import java.sql.Timestamp;

import com.zed.domain.common.base.BaseModel;


/**
 * @date : 2017年2月15日 上午9:29:19
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public class PlayerUserFeedBack   extends BaseModel {
	private static final long serialVersionUID = -3142281974032697551L;
	
	private String uid ;//主键
	private String uname ;//名字
	private String phone ;//手机号
	private Long moviesCount ;//影片数量
	private String country ;//国家
	private Timestamp createTime ;//创建时间
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getMoviesCount() {
		return moviesCount;
	}
	public void setMoviesCount(Long moviesCount) {
		this.moviesCount = moviesCount;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}