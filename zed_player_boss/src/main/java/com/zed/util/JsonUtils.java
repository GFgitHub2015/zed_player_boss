package com.zed.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import net.sf.json.JSONArray;

public final class JsonUtils {
	private JsonUtils() {
	}

	public static Object[] getObjectArray(String str) {
		try {
			JSONArray ja = JSONArray.fromObject(str);
			return ja.toArray();
		} catch (Exception e) {
			String exceptionMessage = "获取Object对象数组失败[getObjectArray]:str参数格式错误!";
			e = new Exception(exceptionMessage);
			e.printStackTrace();
			return null;
		}
	}

	public static String coverStr2URL(String str) {
		Object[] objectArray = getObjectArray(str);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objectArray.length; i++) {
			sb.append(objectArray[i]);
			if (i != objectArray.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 将json字符串转成Map键值对
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapByJsonStr(String jsonStr) {
		if (CommUtil.isEmpty(jsonStr))
			return null;
		return JSON.parseObject(jsonStr, Map.class);
	}

	/**
	 * 将Map键值对转换成json格式
	 * 
	 * @param params
	 * @return
	 */
	public static String getJsonStrByMap(Map<String, Object> params) {
		return JSON.toJSONString(params);
	}

	/**
	 * 对象转成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String objToJson(Object object) {
		return JSON.toJSONString(object);
	}
	
	
	public static String objToJson(Object object,SimplePropertyPreFilter filter) {
		return JSON.toJSONString(object,filter);
	}
	

	/**
	 * 将字符串转成对象
	 * 
	 * @param json
	 * @param classzz
	 * @return
	 */
	public static <T> T jsonToObj(String json, Class<T> classzz) {
		return JSON.parseObject(json, classzz);
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		String json = JsonUtils.getJsonStrByMap(params);
		System.out.println(json);
		System.out.println(JsonUtils.getMapByJsonStr(json).size());

	}
}
