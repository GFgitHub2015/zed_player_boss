package com.zed.domain.player.cdn;

import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.util.CommUtil;

public class PlayerCdn implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	//cdn 使用状态
	public static enum CdnStatus{
		CLOSE(0), 	//关闭
		OPEN(1); 	//打开
		private int status;
		CdnStatus(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	private String cdnId;
	private String cdnName;
	private String cdnDomain;
	private Integer status;
	public String getCdnId() {
		return cdnId;
	}
	public void setCdnId(String cdnId) {
		this.cdnId = cdnId;
	}
	public String getCdnName() {
		return cdnName;
	}
	public void setCdnName(String cdnName) {
		this.cdnName = cdnName;
	}
	public String getCdnDomain() {
		return cdnDomain;
	}
	public void setCdnDomain(String cdnDomain) {
		this.cdnDomain = cdnDomain;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cdnId", this.getCdnId());
		map.put("cdnName", this.getCdnName());
		map.put("cdnDomain", this.getCdnDomain());
		return map;
	}
	
	public static PlayerCdn getPlayerCdn(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerCdn.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
	public String generateId(){
		return CommUtil.random();
	}
}
