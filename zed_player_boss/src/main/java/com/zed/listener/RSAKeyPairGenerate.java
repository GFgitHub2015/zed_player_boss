package	com.zed.listener;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zed.util.RSAUtil;

@SuppressWarnings("unchecked")
public final class RSAKeyPairGenerate implements ServletContextListener{

	public static Long KeyKeepTime;
	public static Hashtable KeyPairListMap = new Hashtable();
	public static Hashtable KeyPairHistoryListMap = new Hashtable();
		
	private Timer timer = null;
	
	public RSAKeyPairGenerate() {}	

    public void contextInitialized(ServletContextEvent sce) {
    	KeyKeepTime = Long.valueOf(SystemConfig.getProperty("rsaKey.history.keepTime"));

    	ArrayList list = initKeyPairList();
		Long nowTime = System.currentTimeMillis();
		KeyPairListMap = new Hashtable();
		KeyPairListMap.put(nowTime, list);
		
		int intervalTime = Integer.valueOf(SystemConfig.getProperty("rsaKey.generate.intervalTime"));
		
		timer = new Timer(true);
    	timer.schedule(new KeyPairHistoryTask(),0,intervalTime*1000*60);
	}
    
    private static ArrayList initKeyPairList(){
    	ArrayList list = new ArrayList();
    	int keyNum = Integer.valueOf(SystemConfig.getProperty("rsaKey.generate.num","5"));

		KeyPair keyPair = null;
		for(int i=0;i<keyNum;i++){
			try {
				keyPair = RSAUtil.generateKeyPair();
				list.add(keyPair);
			} catch (Exception e) {
				Log.getLogger(RSAKeyPairGenerate.class).error("RSAKeyPairGenerate[initKeyPairList] generate key fail"+ e.getMessage());
			}
		}
		return list;
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
		KeyPairListMap.clear();
		KeyPairHistoryListMap.clear();
		timer.cancel();
	}	


	public static Long getKeyKeepTime() {
		return KeyKeepTime;
	}
	
	/**
	 * set time to create the new key pair
	 *
	 */
	private class KeyPairHistoryTask extends TimerTask{

		@Override  
		public void run(){
			Hashtable oldMap = RSAKeyPairGenerate.KeyPairListMap;
			RSAKeyPairGenerate.KeyPairHistoryListMap.clear();
			RSAKeyPairGenerate.KeyPairHistoryListMap.putAll(oldMap);
			
			ArrayList list = RSAKeyPairGenerate.initKeyPairList();
			RSAKeyPairGenerate.KeyPairListMap.clear();
			Long nowTime = System.currentTimeMillis();
			RSAKeyPairGenerate.KeyPairListMap.put(nowTime, list);
		}
	}
	
}
