package com.zed.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class SystemProperties {
	
	private ResourceBundle resourceBundle;
	private String propertiesName;
	private Logger logger = Logger.getLogger(this.getClass());
	public SystemProperties(String propertiesName) {
		super();
		this.propertiesName = propertiesName;
		this.init();
	}
	
	/**
	 * 读取属性文件的值
	 * @param key 属性文件中的key值
	 * @return 属性文件中"="右边的value值
	 */
	public String getValue(String key){
		if (resourceBundle.containsKey(key)) {
			String value = resourceBundle.getString(key).trim();
			logger.debug("key["+key+"]=value["+value+"]");
			return value;
		}
		logger.debug("key["+key+"]不存在！");
		return null;
	}
	
	public Integer getIntValue(String key){
		String n = this.getValue(key);
		if (CommUtil.isEmpty(n)) {
			return null;
		}
		return Integer.valueOf(n);
	}
	public void init(){
		resourceBundle = ResourceBundle.getBundle(this.propertiesName, Locale.CHINESE);
		logger.debug("初始化propertiesName["+this.propertiesName+"]=values["+resourceBundle.toString()+"]");
	}
	
	public static void main(String[] args) {
		SystemProperties sp = new SystemProperties("system");
		String helloworld = sp.getValue("test");
		Integer test1 = sp.getIntValue("test1");
		System.out.println(helloworld);
		System.out.println(test1);
	}
}
