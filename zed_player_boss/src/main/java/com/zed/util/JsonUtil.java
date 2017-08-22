package com.zed.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	/**
	 * 适用于内置列表，LIST继续组装到JSON中
	 * @param listName 列表名称
	 * @param list 列表
	 * @return 基本的JSON数据
	 */
	public static String dataResultList(String listName, List<Object> list){
		Map<String, Object> mapBody = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		try {
			mapBody.put(listName, list);
			json.putAll(mapBody);
		} catch (Exception e) {
			String exceptionMessage = "JsonUtil[dataResultList]:JSON数据处理异常";
			e = new Exception(exceptionMessage);
			e.printStackTrace();
		}
		return json.toString();
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
	
	public static Thumbnail getThumbnail(JSONObject jsonObject){
		Thumbnail thumbnail = null;
		try {
			if (jsonObject != null) {
				thumbnail = new Thumbnail();
				thumbnail.setImage(jsonObject.getString("image"));
				thumbnail.setThumbnail(jsonObject.getString("thumbnail"));
			}
			return thumbnail;
		} catch (Exception e) {
			String exceptionMessage = "转换JSONObject对象失败[getThumbnail]:JSONObject对象转换Thumbnail对象失败!";
			e = new Exception(exceptionMessage);
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Thumbnail> getThumbnailList(Object[] objectArray){
		List<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();
		try {
			if (objectArray!=null && objectArray.length>0) {
				for (int i = 0; i < objectArray.length; i++) {
					JSONObject jo = JSONObject.fromObject(objectArray[i]);
					thumbnailList.add(getThumbnail(jo));
				}
			}
			return thumbnailList;
		} catch (Exception e) {
			String exceptionMessage = "获取Thumbnail集合对象失败[getThumbnailList]:Object对象数组转换Thumbnail集合对象失败!";
			e = new Exception(exceptionMessage);
			e.printStackTrace();
			return null;
		}
	}
	
	public static String coverStr2URL(String str){
		Object[] objectArray = getObjectArray(str);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objectArray.length; i++) {
			sb.append(objectArray[i]);
			if (i != objectArray.length-1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
