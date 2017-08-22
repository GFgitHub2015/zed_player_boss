package com.zed.system;

public class GConstant {
	
	//session key
	public final static String GS_SESSION_ADMIN_KEY = "adminObj";
	public final static String GS_SESSION_MENU_LIST = "menuList";
	public final static String GS_SESSION_PUBLIC_ATTR = "publicAttr";
	public final static String GS_SESSION_LOGON_BY_OTHERS = "logonByOthers";
	public final static String GS_SESSION_PREVENT_REFRESH = "preventRefresh";
	public final static String GS_SESSION_RSA_KEYPAIR = "rsakeyPair";
	public final static String GS_SESSION_RSA_KEYPAIR_KEYINDEX = "rsakeyIndex";
	public final static String GS_SESSION_RSA_KEYPAIR_KEYINDEX_TIME = "rsakeyIndexTime";
	public final static String GS_SESSION_BUTTON_PARTENTID = "buttonMenuPartentId";
	

	public final static String GS_HOST_IP_FLAG = "8001";
	public final static String GS_HOST_IP_URL = "app.host.ip.url";
	public final static String GS_HOST_URL = "app.host.url";
	
	public final static String GS_PUBLIC_DEFAULT = "publicDefault";
	public final static String GS_PUBLIC_ATTR_CSS_PATH = "cssPath"; 
	public final static String GS_PUBLIC_ATTR_IMG_PATH = "imagePath"; 
	public final static String GS_PUBLIC_ATTR_JS_PATH = "jsPath"; 
	public final static String GS_PUBLIC_ATTR_THEME_PATH = "themePath"; 
	public final static String GS_EDITOR_PATH = "editorPath"; 
    public static final String GS_DEVICE_TYPE_LIGHTING = "lighting";
    public static final String GS_DEVICE_TYPE_DOSER= "doser";
	//exception
    public final static int GS_EXCEPTION_SUCCESS = 0;
    
    public static final String [] GS_TYPE = {"0","1","2","3","4","5","6","7","8","9"};
    
    public static final String DEFAULT = "default";
    
    
    //key name
  	public final static String GS_KEY_NAME_RETURN_CODE = "returnCode";		//返回提示编码
  	public final static String GS_KEY_NAME_SYSTIME = "systemTime";			//系统时间
  	public final static String GS_KEY_NAME_RETURN_MSG = "returnMsg";		//返回提示信息
  	public final static String GS_KEY_NAME_RESPONSE_BODY = "responseBody";	//返回主体信息
    //处理成功
  	public final static String GS_RETURN_CODE_SUCCESS = "1000";				
  	public final static String GS_RETURN_MESSAGE_1000 = "处理成功";	
  	//*****************3001-4000为后台与接口错误*****************
  	public final static String GS_RETURN_CODE_MENU_RESET_ERROR = "3001";	
  	public final static String GS_RETURN_MESSAGE_3001 = "微信公众号菜单重置失败";
  	
	public static enum CountryType{
		India("91"),//印度
		China("86"),//天朝
		Indonesia("62"),//印尼
		Thailand("66"),//泰国
		Vietnam("84");//越南
		private String type;
		CountryType(String type){
			this.type = type;
		}
		public String getStatus() {
			return type;
		}
		
		public static CountryType getType(String operate){
			CountryType[] types = CountryType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getStatus().equals(operate)){
					return types[i];
				}
			}
			return CountryType.China;
		}
	}
}

