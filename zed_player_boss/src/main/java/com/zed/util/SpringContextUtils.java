package com.zed.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		if (applicationContext == null) {
			return null;
		}
		
		return applicationContext.getBean(beanId);
	}

	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		this.applicationContext = applicationcontext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}