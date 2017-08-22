package com.zed.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class Log implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent sce){
		try {
			//logger = (Logger) LoggerFactory.getLogger(getClass());
		}catch(Exception e) {
			System.out.println("Log[contextInitialized] log init error:" + e.getMessage());
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce){
	}

	public static Logger getLogger(Class<?> clazz) {
        return (Logger) LoggerFactory.getLogger(clazz);
    }
}