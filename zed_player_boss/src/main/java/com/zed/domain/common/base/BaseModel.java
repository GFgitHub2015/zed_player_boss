package com.zed.domain.common.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import com.zed.common.util.DateUtil;
import com.zed.common.util.EntityUtils;
import com.zed.common.util.JsonUtils;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Timestamp createTime;

	public Timestamp getCreateTime() {
		if (createTime == null) {
			createTime = DateUtil.getCurTime();
		}
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 将对象转成Map<String,String>  嵌套的对象也会被转换成string ，后面可以扩展一下。
	 * @return
	 */
	public Map<String, String> toMap() {
		Map<String, String> entity = EntityUtils.objectToHash(this);
		entity.remove("simpleInfo");
		return entity;
	}
	/**
	 * 将对象转Json格式
	 * @return
	 */
	public String toJson() {
		return JsonUtils.objToJson(this);
	}
}
