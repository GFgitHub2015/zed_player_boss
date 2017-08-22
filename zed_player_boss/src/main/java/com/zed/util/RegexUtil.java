package com.zed.util;

import org.apache.log4j.Logger;

public class RegexUtil {
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 是否是一个[0-9]的整数
	 * @param input 匹配内容
	 * @return true/false
	 */
	public static boolean isNumber(String s)
	{
		if(CommUtil.isEmpty(s))
		{
			return false;
		}
		return s.matches("\\d+");
	}
}
