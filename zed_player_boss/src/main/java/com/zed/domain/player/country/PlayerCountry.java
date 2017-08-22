package com.zed.domain.player.country;

import com.zed.util.CommUtil;

public class PlayerCountry  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String countryId;
	private String countryNameZh;
	private String countryNameEn;
	private Integer status;
	private String countryCode;
	private String zoneTimeId;
	private String alpha2;
	private String alpha3;
	private String numeric3;
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryNameZh() {
		return countryNameZh;
	}
	public void setCountryNameZh(String countryNameZh) {
		this.countryNameZh = countryNameZh;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCountryNameEn() {
		return countryNameEn;
	}
	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}
	public String generateId(){
		return CommUtil.random();
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getZoneTimeId() {
		return zoneTimeId;
	}
	public void setZoneTimeId(String zoneTimeId) {
		this.zoneTimeId = zoneTimeId;
	}
	public String getAlpha2() {
		return alpha2;
	}
	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}
	public String getAlpha3() {
		return alpha3;
	}
	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}
	public String getNumeric3() {
		return numeric3;
	}
	public void setNumeric3(String numeric3) {
		this.numeric3 = numeric3;
	}
}
