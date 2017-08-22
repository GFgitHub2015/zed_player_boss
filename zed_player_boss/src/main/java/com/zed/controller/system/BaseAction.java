package com.zed.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.zed.common.ErrorCode;
import com.zed.common.GConstantResponse;
import com.zed.common.exception.AppErrorException;
import com.zed.domain.system.Admin;
import com.zed.domain.system.Resource;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.system.admin.AdminService;
import com.zed.service.system.adminsession.AdminSessionService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstant;
import com.zed.system.GConstantRedirect;
import com.zed.util.CommUtil;
import com.zed.util.DateUtil;
import com.zed.util.MD5;
import com.zed.util.TreeObject;
import com.zed.util.TreeUtil;

public abstract class BaseAction extends ActionSupport 
	implements ServletRequestAware, ServletResponseAware {
	protected static final String aesKey =  SystemConfig.getProperty("http.aes.key");
	protected static final String aesIv = SystemConfig.getProperty("http.aes.iv");
	
	private static final long serialVersionUID = 1L;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@Autowired
	protected OperationLogService logService;
	
	private String id;
	private String actionId;
	
	private String buttonMenuPartentId;
	
	private List<TreeObject> buttonMenuList;
	
	@javax.annotation.Resource
	protected AdminService adminService;
	
	@javax.annotation.Resource
	protected AdminSessionService adminSessionService;
		
	@Before
	public void init() throws Exception{
		setPublicAttr();
	}
	
	public abstract String doExecute() throws Exception;
	
		
	public String execute() throws Exception{
		return doExecute();
	}
			
	private Map<String, String> validation (Map<String, String> param){
		if (param.size()>0) return param;
		else return null;
	}
	
	protected void setPublicAttr(){
		
		Map<String, String> hm = new HashMap<String, String>();
		hm.put(GConstant.GS_PUBLIC_ATTR_CSS_PATH, request.getContextPath()+"/"+"public/"  + "css/");
		hm.put(GConstant.GS_PUBLIC_ATTR_IMG_PATH, request.getContextPath()+"/"+"public/"  + "images/");
		hm.put(GConstant.GS_PUBLIC_ATTR_JS_PATH, request.getContextPath()+"/"+"public/"  + "js/");
		hm.put(GConstant.GS_PUBLIC_ATTR_THEME_PATH, request.getContextPath()+"/"+"theme/");
		hm.put(GConstant.GS_EDITOR_PATH, request.getContextPath()+"/"+"editor/");
		
		request.setAttribute(GConstant.GS_SESSION_PUBLIC_ATTR, validation(hm));
	}
	
	protected String getCookieValue(HttpServletRequest httRquest, String cookieName) {
		Cookie cookies[] = httRquest.getCookies();

		if (cookies != null) {
			for (int i=0; i<cookies.length; i++) {
				if (cookieName.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	protected Cookie getCookie(HttpServletRequest httRquest, String cookieName) {
		Cookie lA_cookies[] = httRquest.getCookies();

		if (lA_cookies != null) {
			for (int i=0; i<lA_cookies.length; i++) {
				if (cookieName.equals(lA_cookies[i].getName())) {
					return lA_cookies[i];
				}
			}
		}
		return null;
	}
	
	public void setErrorDispatch(String errorCode, String preAction) {
		request.setAttribute("errorCode", errorCode);
		request.setAttribute("prevAction", preAction);
	}
	
	public void setSuccessDispatch(String successCode, String nextAction) {
		request.setAttribute("successCode", successCode);
		request.setAttribute("nextAction", nextAction);
	}
	
	public void setResultDispatch(String message,String nextAction) {
		request.setAttribute("message", message);
		request.setAttribute("nextAction", nextAction);
	}
	
	public void setNoMenuErrorPage(String button) {
		request.setAttribute("nomenu", true);
		request.setAttribute("buttonName", button);
	}
	
	/**
	 * 获取IP地址
	 */
	public String getIp() {
        return request.getRemoteAddr();
	}
	
	public Admin getSessionAdmin() throws AppValidationException {
		HttpSession session = request.getSession(false);
		Admin adminObj = (session==null)? null : (Admin) session.getAttribute(GConstant.GS_SESSION_ADMIN_KEY);
		
		if(adminObj == null){
			throw new AppValidationException(ExceptionConstants.INVALIDATESESSION, GConstantRedirect.GS_LOGOFF_ACTION);
		}
		return adminObj;
	}
	
	protected void setSessionAdmin(Admin adminObj) {
		HttpSession session = request.getSession(false);
		session.setAttribute(GConstant.GS_SESSION_ADMIN_KEY, adminObj);
	}
	
	public List<Resource> getSessionMenu() {
		HttpSession session = request.getSession(false);
		List<Resource> menu = (session==null)? null : (List<Resource>) session.getAttribute(GConstant.GS_SESSION_MENU_LIST);
		return menu;
	}
	
	protected void setSessionMenu(List<Resource> menu) {
		HttpSession session = request.getSession(false);
		session.setAttribute(GConstant.GS_SESSION_MENU_LIST, menu);
	}
		

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
		
	public String getActionId() {
		return actionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getButtonMenuPartentId() {
		HttpSession session = request.getSession(false);
		if(session.getAttribute(GConstant.GS_SESSION_BUTTON_PARTENTID)!=null){			
			return session.getAttribute(GConstant.GS_SESSION_BUTTON_PARTENTID).toString();
		}else{
			return "";
		}
		
	}

	public void setButtonMenuPartentId(String buttonMenuPartentId) {
		HttpSession session = request.getSession(false);
		session.setAttribute(GConstant.GS_SESSION_BUTTON_PARTENTID,buttonMenuPartentId);
	}
	
	public String getImplementUrl(String actionName, String parameter){
		
		String urlInit = "";
		
		if(request.getRequestURL().toString().contains(GConstant.GS_HOST_IP_FLAG)){
			urlInit = SystemConfig.getProperty(GConstant.GS_HOST_IP_URL);
		}else{
			urlInit = SystemConfig.getProperty(GConstant.GS_HOST_URL);
		}
		
		String authKeyInit = SystemConfig.getProperty("auth.key");
		String time = DateUtil.dates2String(new Date());
		String authKey = MD5.getHexMD5(time+authKeyInit);
		return urlInit+"/"+actionName+".action?authKey="+authKey+"&sendTime="+time+"&remark=&"+parameter+"=";
	}
	
	public String autoLogOffBySessionTimeOut() throws IOException{
		String responseStr = "";
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter out = null;
		try{
			//清除session
			request.getSession().invalidate();
		}catch(Exception ex){
			Log.getLogger(this.getClass()).error("BaseAction[autoLogOffBySessionTimeOut] failed: ",ex);
		}finally{
			responseStr = "<script>top.location='"+request.getContextPath()+"/sessionTimeOut.jsp'</script>";
			out = response.getWriter();
			out.print(responseStr);
			out.flush();
			out.close();
			return NONE;
		}
	}
	
	public List<TreeObject> getButtonMenuList() {
		try{
			List<TreeObject> buttonList = null;
			
			if(getButtonMenuPartentId()!=null && !getButtonMenuPartentId().equals("")){
				buttonList = TreeUtil.getChildResourcesByParentId((List<Resource>) ServletActionContext.getRequest().getSession(false).getAttribute(GConstant.GS_SESSION_MENU_LIST), Integer.valueOf(getButtonMenuPartentId()));
				return buttonList;
			}else{
				return null;
			}
		}catch(Exception ex){
			Log.getLogger(CommUtil.class).error("BaseAction[getButtonMenuList] failed: " , ex);
			return null;
		}
	}
	

	/**
	 * @date : 2017年1月17日 下午5:20:03
	 * @author : Iris.Xiao
	 * @description : 设置其它参数,用于修改完成之后返回参数值
	*/
	public void setOtherAttr(Map<String,Object> param){
		if(param!=null&&param.size()>0){
			List<Map<String,Object>> attrs = new ArrayList<Map<String,Object>>();
			for(Entry<String, Object> entry: param.entrySet()){
				Map<String,Object> attr = new HashMap<String,Object>();
				attr.put("key", entry.getKey());
				attr.put("value", entry.getValue());
				attrs.add(attr);
			}
			request.setAttribute("otherAttrs", attrs);
		}else{
			request.removeAttribute("otherAttrs");
		}
	}
	

	/**
	 * @date : 2017年6月7日 上午9:41:07
	 * @author : Iris.Xiao
	 * @param map
	 * @throws IOException 
	 * @description : ajax输出
	*/
	public void outputResult(Map<String,?> map,String code,String message) throws IOException{

    	response.setContentType("text/html;charset=utf-8");
		Map<String, Object> mapBody = new HashMap<String, Object>();
		mapBody.put(GConstantResponse.GS_KEY_NAME_RETURN_CODE, code);
		mapBody.put(GConstantResponse.GS_KEY_NAME_RETURN_MSG, message);
		mapBody.put(GConstantResponse.GS_KEY_NAME_SYSTIME, DateUtil.date2String("yyyyMMddHHmmss", new Date()));// 当前系统时间
		if (map != null && !map.isEmpty()) {
			mapBody.put(GConstantResponse.GS_KEY_NAME_RESPONSE_BODY, map);
		}
    	String returnResult= JSON.toJSONString(mapBody);
        response.getWriter().println(returnResult);
        response.getWriter().flush();
        response.getWriter().close();
	}

	/**
	 * @date : 2017年6月7日 上午9:41:07
	 * @author : Iris.Xiao
	 * @param map
	 * @description : ajax输出
	*/
	public void outputResultList(List<?> map,String code,String message) throws IOException{

    	response.setContentType("text/html;charset=utf-8");
		Map<String, Object> mapBody = new HashMap<String, Object>();
		mapBody.put(GConstantResponse.GS_KEY_NAME_RETURN_CODE, code);
		mapBody.put(GConstantResponse.GS_KEY_NAME_RETURN_MSG, message);
		mapBody.put(GConstantResponse.GS_KEY_NAME_SYSTIME, DateUtil.date2String("yyyyMMddHHmmss", new Date()));// 当前系统时间
		if (map != null && !map.isEmpty()) {
			mapBody.put(GConstantResponse.GS_KEY_NAME_RESPONSE_BODY, map);
		}
    	String returnResult= JSON.toJSONString(mapBody);
        response.getWriter().println(returnResult);
        response.getWriter().flush();
        response.getWriter().close();
	}
	


	/**
	 * decrypt data by aes key
	 * 
	 * @param data
	 * @return
	 * @throws AppErrorException
	 */
	public String decryptData(String data) throws AppErrorException {
		String dataDecrypt = data;
		try {
			// decrypt data
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
			dataDecrypt = new String(cipher.doFinal(Hex.decodeHex(dataDecrypt
					.toCharArray())), "UTF-8");
			Log.getLogger(this.getClass()).debug("BaseAction[getCipher] aes key decode= " + dataDecrypt);
		} catch (Exception ex) {
			if (ex instanceof AppErrorException) {
				throw (AppErrorException) ex;
			} else {
				Log.getLogger(this.getClass()).error("BaseAction[decryptData] error:", ex);
				throw new AppErrorException(ErrorCode.AES_ERROR.getCode(),ErrorCode.AES_ERROR.getMessage());
			}
		}
		return dataDecrypt;
	}
	/**
	 * encrypt data by aes key
	 * 
	 * @param data
	 * @return
	 * @throws AppErrorException
	 */
	public String encryptData(String data) throws AppErrorException {
		String dataEncrypt = data;
		try {
			// encrypt data
			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
			dataEncrypt = Hex.encodeHexString(cipher.doFinal(data
					.getBytes("UTF-8")));
			Log.getLogger(this.getClass()).debug("BaseAction[getCipher] aes key encode= " + dataEncrypt);
		} catch (Exception ex) {
			if (ex instanceof AppErrorException) {
				throw (AppErrorException) ex;
			} else {
				Log.getLogger(this.getClass()).error("BaseAction[encryptData] error:", ex);
				throw new AppErrorException(ErrorCode.AES_ERROR.getCode(),ErrorCode.AES_ERROR.getMessage());
			}
		}
		return dataEncrypt;
	}

	/**
	 * param type : Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
	 * 
	 * @param type
	 * @return
	 * @throws AppErrorException
	 */
	protected Cipher getCipher(int type) throws AppErrorException {
		try {

			// set key to SecretKeySpec
			byte[] aesKeyByte = Hex.decodeHex(aesKey.toCharArray());
			;
			SecretKeySpec key = new SecretKeySpec(aesKeyByte, "AES");

			// set iv to IvParameterSpec
			byte[] aesKeyIvByte = Hex.decodeHex(aesIv.toCharArray());
			IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKeyIvByte);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(type, key, ivParameterSpec);
			return cipher;
		} catch (Exception e) {
			e.printStackTrace();
			Log.getLogger(this.getClass()).error("BgetCipher error:", e);
			throw new AppErrorException(ErrorCode.AES_ERROR.getCode(),ErrorCode.AES_ERROR.getMessage());
		}
	}

}
