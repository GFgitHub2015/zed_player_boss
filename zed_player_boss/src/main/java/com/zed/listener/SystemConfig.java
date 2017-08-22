package	com.zed.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class SystemConfig implements ServletContextListener{
		
	public static final String CONFIG_PARAM = "configPath";
	
	private static Properties systemProperty = new Properties();
	
    public void contextInitialized(ServletContextEvent sce) {
    	String configFile = sce.getServletContext().getInitParameter(CONFIG_PARAM);
    	initSystemConfig(configFile);
	}
    
	private void initSystemConfig(String cfgPath) throws IllegalStateException{
		try {
			FileInputStream fileCfg = new FileInputStream(this.getClass().getClassLoader().getResource(cfgPath).getPath());
			systemProperty.load(fileCfg);
			fileCfg.close();
		}catch(IOException x) {
	    	throw new IllegalStateException("SystemConfig[initSystemConfig]:please check configPath in web.xml !");
		}
	}
	
    public void contextDestroyed(ServletContextEvent sce) {
    	systemProperty = null;
	}	
	
	public static String getProperty(String propStr)	{
		return systemProperty.getProperty(propStr);
	}
	
	
	public static String getProperty(String propStr, String defaultValue){
		return systemProperty.getProperty(propStr, defaultValue);
	}
	
	public static Properties getProperties()	{
		return systemProperty;
	}
	
}
