package com.zed.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zed.domain.system.Resource;
import com.zed.listener.Log;
import com.zed.system.GConstant;

public class CommUtil {

	static Logger logger =LoggerFactory.getLogger(CommUtil.class);

	public static String null2String(Object s) {
		return ((s == null) ? "" : s.toString());
	}

	public static String null2String(String s) {
		return ((s == null) ? "" : s.toString());
	}
	
    public static String null2String(String s, String defaultStr) {
        return ((s == null) ? defaultStr : s.toString());
    }
    
    public static Boolean null2Boolean(String s, Boolean defaultBoolean) {
        return ((s == null) ? defaultBoolean : Boolean.parseBoolean(s));
    }

	public static int null2Int(Object s) {
		int v = 0;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static int null2Int(String s) {
		int v = 0;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static int null2Int(String s, int defaultValue) {
		int v = defaultValue;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static int null2Int(Object s, int defaultValue) {
		int v = defaultValue;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static long null2Long(String s) {
		long v = 0L;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Long.parseLong(s);
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static long null2Long(Object s) {
		long v = 0L;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Long.parseLong(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static double null2Double(Object s, int defaultValue) {
		double v = defaultValue;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Double.parseDouble(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}
	
	public static double null2Double(Object s) {
		double v = 0.0D;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Double.parseDouble(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}

	public static float null2Float(Object s, int defaultValue) {
		float v = defaultValue;
		if ((s != null) && (!(s.equals("")))) {
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception e) {
				logger.error("将数据进行Null处理时出错");
			}
		}
		return v;
	}
	
	public static int double2Int(double d) {
		int returnValue = 0;
		returnValue = (int) d;
		return returnValue;
	}

	public static String array2String(String[] values, String split) {
		StringBuffer returnValue = new StringBuffer();
		if (values != null) {
			int length = values.length;
			for (int i = 0; i < length; ++i) {
				returnValue.append(values[i].concat(split));
			}
		}
		return returnValue.toString();
	}

	public static String array2String(int[] values, String split) {
		StringBuffer returnValue = new StringBuffer();
		if (values != null) {
			int length = values.length;
			for (int i = 0; i < length; ++i) {
				returnValue.append(String.valueOf(values[i]).concat(split));
			}
		}
		return returnValue.toString();
	}

	public static int[] arrayString2Int(String[] values) {
		int[] returnValue = (int[]) null;
		if (values != null) {
			int length = values.length;
			returnValue = new int[length];
			for (int i = 0; i < length; ++i) {
				returnValue[i] = Integer.parseInt(values[i]);
			}
		}
		return returnValue;
	}

	public static long[] arrayString2Long(String[] values) {
		long[] returnValue = (long[]) null;
		if (values != null) {
			int length = values.length;
			returnValue = new long[length];
			for (int i = 0; i < length; ++i) {
				returnValue[i] = Long.parseLong(values[i]);
			}
		}
		return returnValue;
	}

	public static double[] arrayString2Double(String[] values) {
		double[] returnValue = (double[]) null;
		if (values != null) {
			int length = values.length;
			returnValue = new double[length];
			for (int i = 0; i < length; ++i) {
				returnValue[i] = Double.parseDouble(values[i]);
			}
		}
		return returnValue;
	}
	
	/**
	 * StringArray转化为String
	 */
	public static String arrayString2String(String[] values, String split) {
		String returnValue = null;
		if (values != null) {
			int length = values.length;
			for (int i = 0; i < length; i++) {
				returnValue += values[i] + split;
			}
			if(!returnValue.equals("") && returnValue.length() > 0){
				returnValue = returnValue.substring(returnValue.length() - split.length(), returnValue.length());
			}
		}
		return ((returnValue == null) ? "" : returnValue.toString());
	}
	
	/**
	 * String转化为StringArray
	 */
	public static String[] string2ArrayString(String values, String split) {
		String returnValue[] = null;
		if (values != null) {
			returnValue = values.split(split);
		}
		return ((returnValue == null) ? new String[0] : returnValue);
	}
	
	public static String changeIdToString(String value){
		if(value.indexOf(",")>-1){
			value = "'"+value.replaceAll(",", "','")+"'";
		}
		return value;
	}
	
	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 用来去掉List中空值和相同项的。
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeSameItem(List<String> list) {
		List<String> difList = new ArrayList<String>();
		for (String t : list) {
			if (t != null && !difList.contains(t)) {
				difList.add(t);
			}
		}
		return difList;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<TreeObject> getButtonMenuListByPartentId(String partentId){
		try{
			List<TreeObject> buttonList = null;
			
			if(partentId!=null && !partentId.equals("")){
				HttpSession session = ServletActionContext.getRequest().getSession(false);
//				if (session.getValueNames() != null) {
//					
//				}
				List<Resource> resourceList = (List<Resource>)session.getAttribute(GConstant.GS_SESSION_MENU_LIST);
				buttonList = TreeUtil.getChildResourcesByParentId(resourceList, Integer.valueOf(partentId));
				return buttonList;
			}else{
				return null;
			}
		}catch(Exception ex){
			Log.getLogger(CommUtil.class).error("CommUtil[getButtonMenuListByPartentId] failed: " , ex);
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static String getButtonMenuList(String partentId){
		try{
			if(partentId!=null && !partentId.equals("")){
//				HttpSession session = ServletActionContext.getRequest().getSession(false);
////				if (session.getValueNames() != null) {
////					
////				}
//				List<Resource> resourceList = (List<Resource>)session.getAttribute(GConstant.GS_SESSION_MENU_LIST);
//				return resourceList.toString();
				return partentId;
			}else{
				return "partentId为空";
			}
		}catch(Exception ex){
			Log.getLogger(CommUtil.class).error("CommUtil[getButtonMenuList] failed: " , ex);
			return "获取session.getAttribute('menuList')异常";
		}
	}
	
	/**
	 * 获取IP地址
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.equals("") || "unknown".equalsIgnoreCase(ip)) {
        	ip = request.getHeader("X-Forwarded-For");
            if (ip != null && !ip.equals("") && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个IP值，第一个为真实IP。
                int index = ip.indexOf(',');
                if (index != -1) {
                    ip = ip.substring(0, index);
                }
            } else {
            	ip = request.getRemoteAddr();
            }
        }
        return ip;
	}
	
	public static String getRandomString(int length, int type) { //length表示生成字符串的长度  
	    String base0 = "0123456789";
	    String base1 = "abcdefghijklmnopqrstuvwxyz";
	    String base2 = "abcdefghijklmnopqrstuvwxyz0123456789";
	    String base = "";
	    if(type == 0)
	    {
	    	base = base0;
	    }
	    if(type == 1)
	    {
	    	base = base1;
	    }
	    if(type == 2)
	    {
	    	base = base2;
	    }
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }
	
	public static String getStaticMethod(){
		return "11";
	}
	
	/**
	 * 返回32位的uuid String
	 * @return
	 */
	public static String random(){
		String s=java.util.UUID.randomUUID().toString();
		String s1=replaceString(s, "-", "");
		return s1;
		
	}
	
	public static String replaceString(String value, String oldString,
			String newString) {
		if (value == null || newString == null || oldString == null)
			return null;
		StringBuffer sbf = new StringBuffer();
		sbf.append(value);
		int len = newString.length();
		int l = oldString.length();
		int pos = 0 - len;
		String temp = "";
		if (value.equals("")) {
			return value;
		} else {
			do {
				temp = sbf.toString();
				pos = temp.indexOf(oldString, pos + len);
				if (pos >= 0) {
					sbf.replace(pos, pos + l, newString);
				}
			} while (pos >= 0);
			String aa = new String(sbf);
			return aa;
		}
	}
	
	 /** 
     * 判断上传文件的contentType 
     * @param FilenameExtension 文件后缀 
     * @return String  
     */  
     public static String contentType(String filenameExtension){  
    	 	if(filenameExtension.equalsIgnoreCase("*")){
    			return "application/octet-stream";
    		}
    		if(filenameExtension.equalsIgnoreCase("001")){
    			return "application/x-001";
    		}
    		if(filenameExtension.equalsIgnoreCase("301")){
    			return "application/x-301";
    		}
    		if(filenameExtension.equalsIgnoreCase("323")){
    			return "text/h323";
    		}
    		if(filenameExtension.equalsIgnoreCase("906")){
    			return "application/x-906";
    		}
    		if(filenameExtension.equalsIgnoreCase("907")){
    			return "drawing/907";
    		}
    		if(filenameExtension.equalsIgnoreCase("a11")){
    			return "application/x-a11";
    		}
    		if(filenameExtension.equalsIgnoreCase("acp")){
    			return "audio/x-mei-aac";
    		}
    		if(filenameExtension.equalsIgnoreCase("ai")){
    			return "application/postscript";
    		}
    		if(filenameExtension.equalsIgnoreCase("aif")){
    			return "audio/aiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("aifc")){
    			return "audio/aiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("aiff")){
    			return "audio/aiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("anv")){
    			return "application/x-anv";
    		}
    		if(filenameExtension.equalsIgnoreCase("asa")){
    			return "text/asa";
    		}
    		if(filenameExtension.equalsIgnoreCase("asf")){
    			return "video/x-ms-asf";
    		}
    		if(filenameExtension.equalsIgnoreCase("asp")){
    			return "text/asp";
    		}
    		if(filenameExtension.equalsIgnoreCase("asx")){
    			return "video/x-ms-asf";
    		}
    		if(filenameExtension.equalsIgnoreCase("au")){
    			return "audio/basic";
    		}
    		if(filenameExtension.equalsIgnoreCase("avi")){
    			return "video/avi";
    		}
    		if(filenameExtension.equalsIgnoreCase("awf")){
    			return "application/vndadobeworkflow";
    		}
    		if(filenameExtension.equalsIgnoreCase("biz")){
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("bmp")){
    			return "application/x-bmp";
    		}
    		if(filenameExtension.equalsIgnoreCase("bot")){
    			return "application/x-bot";
    		}
    		if(filenameExtension.equalsIgnoreCase("c4t")){
    			return "application/x-c4t";
    		}
    		if(filenameExtension.equalsIgnoreCase("c90")){
    			return "application/x-c90";
    		}
    		if(filenameExtension.equalsIgnoreCase("cal")){
    			return "application/x-cals";
    		}
    		if(filenameExtension.equalsIgnoreCase("cat")){
    			return "application/vndms-pkiseccat";
    		}
    		if(filenameExtension.equalsIgnoreCase("cdf")){
    			return "application/x-netcdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("cdr")){
    			return "application/x-cdr";
    		}
    		if(filenameExtension.equalsIgnoreCase("cel")){
    			return "application/x-cel";
    		}
    		if(filenameExtension.equalsIgnoreCase("cer")){
    			return "application/x-x509-ca-cert";
    		}
    		if(filenameExtension.equalsIgnoreCase("cg4")){
    			return "application/x-g4";
    		}
    		if(filenameExtension.equalsIgnoreCase("cgm")){
    			return "application/x-cgm";
    		}
    		if(filenameExtension.equalsIgnoreCase("cit")){
    			return "application/x-cit";
    		}
    		if(filenameExtension.equalsIgnoreCase("class")){
    			return "java/*";
    		}
    		if(filenameExtension.equalsIgnoreCase("cml")){
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("cmp")){
    			return "application/x-cmp";
    		}
    		if(filenameExtension.equalsIgnoreCase("cmx")){
    			return "application/x-cmx";
    		}
    		if(filenameExtension.equalsIgnoreCase("cot")){
    			return "application/x-cot";
    		}
    		if(filenameExtension.equalsIgnoreCase("crl")){
    			return "application/pkix-crl";
    		}
    		if(filenameExtension.equalsIgnoreCase("crt")){
    			return "application/x-x509-ca-cert";
    		}
    		if(filenameExtension.equalsIgnoreCase("csi")){ 
    			return "application/x-csi";
    		}
    		if(filenameExtension.equalsIgnoreCase("css")){ 
    			return "text/css";
    		}
    		if(filenameExtension.equalsIgnoreCase("cut")){ 
    			return "application/x-cut";
    		}
    		if(filenameExtension.equalsIgnoreCase("dbf")){ 
    			return "application/x-dbf";
    		}
    		if(filenameExtension.equalsIgnoreCase("dbm")){ 
    			return "application/x-dbm";
    		}
    		if(filenameExtension.equalsIgnoreCase("dbx")){ 
    			return "application/x-dbx";
    		}
    		if(filenameExtension.equalsIgnoreCase("dcd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("dcx")){
    			return "application/x-dcx";
    		}
    		if(filenameExtension.equalsIgnoreCase("der")){ 
    			return "application/x-x509-ca-cert";
    		}
    		if(filenameExtension.equalsIgnoreCase("dgn")){ 
    			return "application/x-dgn";
    		}
    		if(filenameExtension.equalsIgnoreCase("dib")){ 
    			return "application/x-dib";
    		}
    		if(filenameExtension.equalsIgnoreCase("dll")){ 
    			return "application/x-msdownload";
    		}
    		if(filenameExtension.equalsIgnoreCase("doc")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("dot")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("drw")){ 
    			return "application/x-drw";
    		}
    		if(filenameExtension.equalsIgnoreCase("dtd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("dwf")){ 
    			return "Model/vnddwf";
    		}
    		if(filenameExtension.equalsIgnoreCase("dwf")){ 
    			return "application/x-dwf";
    		}
    		if(filenameExtension.equalsIgnoreCase("dwg")){ 
    			return "application/x-dwg";
    		}
    		if(filenameExtension.equalsIgnoreCase("dxb")){ 
    			return "application/x-dxb";
    		}
    		if(filenameExtension.equalsIgnoreCase("dxf")){ 
    			return "application/x-dxf";
    		}
    		if(filenameExtension.equalsIgnoreCase("edn")){ 
    			return "application/vndadobeedn";
    		}
    		if(filenameExtension.equalsIgnoreCase("emf")){ 
    			return "application/x-emf";
    		}
    		if(filenameExtension.equalsIgnoreCase("eml")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("ent")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("epi")){ 
    			return "application/x-epi";
    		}
    		if(filenameExtension.equalsIgnoreCase("eps")){ 
    			return "application/x-ps";
    		}
    		if(filenameExtension.equalsIgnoreCase("eps")){ 
    			return "application/postscript";
    		}
    		if(filenameExtension.equalsIgnoreCase("etd")){ 
    			return "application/x-ebx";
    		}
    		if(filenameExtension.equalsIgnoreCase("exe")){ 
    			return "application/x-msdownload";
    		}
    		if(filenameExtension.equalsIgnoreCase("fax")){ 
    			return "image/fax";
    		}
    		if(filenameExtension.equalsIgnoreCase("fdf")){ 
    			return "application/vndfdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("fif")){ 
    			return "application/fractals";
    		}
    		if(filenameExtension.equalsIgnoreCase("fo")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("frm")){ 
    			return "application/x-frm";
    		}
    		if(filenameExtension.equalsIgnoreCase("g4")){ 
    			return "application/x-g4";
    		}
    		if(filenameExtension.equalsIgnoreCase("gbr")){ 
    			return "application/x-gbr";
    		}
    		if(filenameExtension.equalsIgnoreCase("gcd")){ 
    			return "application/x-gcd";
    		}
    		if(filenameExtension.equalsIgnoreCase("gif")){ 
    			return "image/gif";
    		}
    		if(filenameExtension.equalsIgnoreCase("gl2")){ 
    			return "application/x-gl2";
    		}
    		if(filenameExtension.equalsIgnoreCase("gp4")){ 
    			return "application/x-gp4";
    		}
    		if(filenameExtension.equalsIgnoreCase("hgl")){ 
    			return "application/x-hgl";
    		}
    		if(filenameExtension.equalsIgnoreCase("hmr")){ 
    			return "application/x-hmr";
    		}
    		if(filenameExtension.equalsIgnoreCase("hpg")){ 
    			return "application/x-hpgl";
    		}
    		if(filenameExtension.equalsIgnoreCase("hpl")){ 
    			return "application/x-hpl";
    		}
    		if(filenameExtension.equalsIgnoreCase("hqx")){ 
    			return "application/mac-binhex40";
    		}
    		if(filenameExtension.equalsIgnoreCase("hrf")){ 
    			return "application/x-hrf";
    		}
    		if(filenameExtension.equalsIgnoreCase("hta")){ 
    			return "application/hta";
    		}
    		if(filenameExtension.equalsIgnoreCase("htc")){ 
    			return "text/x-component";
    		}
    		if(filenameExtension.equalsIgnoreCase("htm")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("html")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("htt")){ 
    			return "text/webviewhtml";
    		}
    		if(filenameExtension.equalsIgnoreCase("htx")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("icb")){ 
    			return "application/x-icb";
    		}
    		if(filenameExtension.equalsIgnoreCase("ico")){ 
    			return "image/x-icon";
    		}
    		if(filenameExtension.equalsIgnoreCase("ico")){ 
    			return "application/x-ico";
    		}
    		if(filenameExtension.equalsIgnoreCase("iff")){ 
    			return "application/x-iff";
    		}
    		if(filenameExtension.equalsIgnoreCase("ig4")){ 
    			return "application/x-g4";
    		}
    		if(filenameExtension.equalsIgnoreCase("igs")){ 
    			return "application/x-igs";
    		}
    		if(filenameExtension.equalsIgnoreCase("iii")){ 
    			return "application/x-iphone";
    		}
    		if(filenameExtension.equalsIgnoreCase("img")){ 
    			return "application/x-img";
    		}
    		if(filenameExtension.equalsIgnoreCase("ins")){ 
    			return "application/x-internet-signup";
    		}
    		if(filenameExtension.equalsIgnoreCase("isp")){ 
    			return "application/x-internet-signup";
    		}
    		if(filenameExtension.equalsIgnoreCase("IVF")){ 
    			return "video/x-ivf";
    		}
    		if(filenameExtension.equalsIgnoreCase("java")){ 
    			return "java/*";
    		}
    		if(filenameExtension.equalsIgnoreCase("jfif")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpe")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpe")){ 
    			return "application/x-jpe";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpeg")){
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpg")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpg")){ 
    			return "application/x-jpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("js")){ 
    			return "application/x-javascript";
    		}
    		if(filenameExtension.equalsIgnoreCase("jsp")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("la1")){ 
    			return "audio/x-liquid-file";
    		}
    		if(filenameExtension.equalsIgnoreCase("lar")){ 
    			return "application/x-laplayer-reg";
    		}
    		if(filenameExtension.equalsIgnoreCase("latex")){ 
    			return "application/x-latex";
    		}
    		if(filenameExtension.equalsIgnoreCase("lavs")){
    			return "audio/x-liquid-secure";
    		}
    		if(filenameExtension.equalsIgnoreCase("lbm")){ 
    			return "application/x-lbm";
    		}
    		if(filenameExtension.equalsIgnoreCase("lmsff")){ 
    			return "audio/x-la-lms";
    		}
    		if(filenameExtension.equalsIgnoreCase("ls")){ 
    			return "application/x-javascript";
    		}
    		if(filenameExtension.equalsIgnoreCase("ltr")){ 
    			return "application/x-ltr";
    		}
    		if(filenameExtension.equalsIgnoreCase("m1v")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("m2v")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("m3u")){ 
    			return "audio/mpegurl";
    		}
    		if(filenameExtension.equalsIgnoreCase("m4e")){ 
    			return "video/mpeg4";
    		}
    		if(filenameExtension.equalsIgnoreCase("mac")){ 
    			return "application/x-mac";
    		}
    		if(filenameExtension.equalsIgnoreCase("man")){ 
    			return "application/x-troff-man";
    		}
    		if(filenameExtension.equalsIgnoreCase("math")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("mdb")){ 
    			return "application/msaccess";
    		}
    		if(filenameExtension.equalsIgnoreCase("mdb")){ 
    			return "application/x-mdb";
    		}
    		if(filenameExtension.equalsIgnoreCase("mfp")){ 
    			return "application/x-shockwave-flash";
    		}
    		if(filenameExtension.equalsIgnoreCase("mht")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("mhtml")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("mi")){ 
    			return "application/x-mi";
    		}
    		if(filenameExtension.equalsIgnoreCase("mid")){ 
    			return "audio/mid";
    		}
    		if(filenameExtension.equalsIgnoreCase("midi")){ 
    			return "audio/mid";
    		}
    		if(filenameExtension.equalsIgnoreCase("mil")){ 
    			return "application/x-mil";
    		}
    		if(filenameExtension.equalsIgnoreCase("mml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("mnd")){ 
    			return "audio/x-musicnet-download";
    		}
    		if(filenameExtension.equalsIgnoreCase("mns")){ 
    			return "audio/x-musicnet-stream";
    		}
    		if(filenameExtension.equalsIgnoreCase("mocha")){ 
    			return "application/x-javascript";
    		}
    		if(filenameExtension.equalsIgnoreCase("movie")){
    			return "video/x-sgi-movie";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp1")){ 
    			return "audio/mp1";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp2")){ 
    			return "audio/mp2";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp2v")){ 
    			return "video/mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp3")){ 
    			return "audio/mp3";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp4")){ 
    			return "video/mpeg4";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpa")){ 
    			return "video/x-mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpd")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpe")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpeg")){ 
    			return "video/mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpg")){ 
    			return "video/mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpga")){ 
    			return "audio/rn-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpp")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mps")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpt")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpv")){ 
    			return "video/mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpv2")){ 
    			return "video/mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpw")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpx")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mtx")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("mxp")){ 
    			return "application/x-mmxp";
    		}
    		if(filenameExtension.equalsIgnoreCase("net")){ 
    			return "image/pnetvue";
    		}
    		if(filenameExtension.equalsIgnoreCase("nrf")){ 
    			return "application/x-nrf";
    		}
    		if(filenameExtension.equalsIgnoreCase("nws")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("odc")){ 
    			return "text/x-ms-odc";
    		}
    		if(filenameExtension.equalsIgnoreCase("out")){ 
    			return "application/x-out";
    		}
    		if(filenameExtension.equalsIgnoreCase("p10")){ 
    			return "application/pkcs10";
    		}
    		if(filenameExtension.equalsIgnoreCase("p12")){ 
    			return "application/x-pkcs12";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7b")){ 
    			return "application/x-pkcs7-certificates";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7c")){ 
    			return "application/pkcs7-mime";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7m")){ 
    			return "application/pkcs7-mime";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7r")){ 
    			return "application/x-pkcs7-certreqresp";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7s")){ 
    			return "application/pkcs7-signature";
    		}
    		if(filenameExtension.equalsIgnoreCase("pc5")){ 
    			return "application/x-pc5";
    		}
    		if(filenameExtension.equalsIgnoreCase("pci")){ 
    			return "application/x-pci";
    		}
    		if(filenameExtension.equalsIgnoreCase("pcl")){ 
    			return "application/x-pcl";
    		}
    		if(filenameExtension.equalsIgnoreCase("pcx")){ 
    			return "application/x-pcx";
    		}
    		if(filenameExtension.equalsIgnoreCase("pdf")){ 
    			return "application/pdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("pdf")){ 
    			return "application/pdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("pdx")){ 
    			return "application/vndadobepdx";
    		}
    		if(filenameExtension.equalsIgnoreCase("pfx")){ 
    			return "application/x-pkcs12";
    		}
    		if(filenameExtension.equalsIgnoreCase("pgl")){ 
    			return "application/x-pgl";
    		}
    		if(filenameExtension.equalsIgnoreCase("pic")){ 
    			return "application/x-pic";
    		}
    		if(filenameExtension.equalsIgnoreCase("pko")){ 
    			return "application/vndms-pkipko";
    		}
    		if(filenameExtension.equalsIgnoreCase("pl")){ 
    			return "application/x-perl";
    		}
    		if(filenameExtension.equalsIgnoreCase("plg")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("pls")){ 
    			return "audio/scpls";
    		}
    		if(filenameExtension.equalsIgnoreCase("plt")){ 
    			return "application/x-plt";
    		}
    		if(filenameExtension.equalsIgnoreCase("png")){ 
    			return "image/png";
    		}
    		if(filenameExtension.equalsIgnoreCase("png")){ 
    			return "application/x-png";
    		}
    		if(filenameExtension.equalsIgnoreCase("pot")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppa")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppm")){ 
    			return "application/x-ppm";
    		}
    		if(filenameExtension.equalsIgnoreCase("pps")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppt")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppt")){ 
    			return "application/x-ppt";
    		}
    		if(filenameExtension.equalsIgnoreCase("pr")){ 
    			return "application/x-pr";
    		}
    		if(filenameExtension.equalsIgnoreCase("prf")){ 
    			return "application/pics-rules";
    		}
    		if(filenameExtension.equalsIgnoreCase("prn")){ 
    			return "application/x-prn";
    		}
    		if(filenameExtension.equalsIgnoreCase("prt")){ 
    			return "application/x-prt";
    		}
    		if(filenameExtension.equalsIgnoreCase("ps")){ 
    			return "application/x-ps";
    		}
    		if(filenameExtension.equalsIgnoreCase("ps")){ 
    			return "application/postscript";
    		}
    		if(filenameExtension.equalsIgnoreCase("ptn")){ 
    			return "application/x-ptn";
    		}
    		if(filenameExtension.equalsIgnoreCase("pwz")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("r3t")){ 
    			return "text/vndrn-realtext3d";
    		}
    		if(filenameExtension.equalsIgnoreCase("ra")){ 
    			return "audio/vndrn-realaudio";
    		}
    		if(filenameExtension.equalsIgnoreCase("ram")){ 
    			return "audio/x-pn-realaudio";
    		}
    		if(filenameExtension.equalsIgnoreCase("ras")){ 
    			return "application/x-ras";
    		}
    		if(filenameExtension.equalsIgnoreCase("rat")){ 
    			return "application/rat-file";
    		}
    		if(filenameExtension.equalsIgnoreCase("rdf")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("rec")){ 
    			return "application/vndrn-recording";
    		}
    		if(filenameExtension.equalsIgnoreCase("red")){ 
    			return "application/x-red";
    		}
    		if(filenameExtension.equalsIgnoreCase("rgb")){ 
    			return "application/x-rgb";
    		}
    		if(filenameExtension.equalsIgnoreCase("rjs")){ 
    			return "application/vndrn-realsystem-rjs";
    		}
    		if(filenameExtension.equalsIgnoreCase("rjt")){ 
    			return "application/vndrn-realsystem-rjt";
    		}
    		if(filenameExtension.equalsIgnoreCase("rlc")){ 
    			return "application/x-rlc";
    		}
    		if(filenameExtension.equalsIgnoreCase("rle")){ 
    			return "application/x-rle";
    		}
    		if(filenameExtension.equalsIgnoreCase("rm")){ 
    			return "application/vndrn-realmedia";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmf")){ 
    			return "application/vndadobermf";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmi")){ 
    			return "audio/mid";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmj")){ 
    			return "application/vndrn-realsystem-rmj";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmm")){ 
    			return "audio/x-pn-realaudio";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmp")){ 
    			return "application/vndrn-rn_music_package";
    		}
    		if(filenameExtension.equalsIgnoreCase("rms")){ 
    			return "application/vndrn-realmedia-secure";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmvb")){ 
    			return "application/vndrn-realmedia-vbr";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmx")){ 
    			return "application/vndrn-realsystem-rmx";
    		}
    		if(filenameExtension.equalsIgnoreCase("rnx")){ 
    			return "application/vndrn-realplayer";
    		}
    		if(filenameExtension.equalsIgnoreCase("rp")){ 
    			return "image/vndrn-realpix";
    		}
    		if(filenameExtension.equalsIgnoreCase("rpm")){ 
    			return "audio/x-pn-realaudio-plugin";
    		}
    		if(filenameExtension.equalsIgnoreCase("rsml")){ 
    			return "application/vndrn-rsml";
    		}
    		if(filenameExtension.equalsIgnoreCase("rt")){ 
    			return "text/vndrn-realtext";
    		}
    		if(filenameExtension.equalsIgnoreCase("rtf")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("rtf")){ 
    			return "application/x-rtf";
    		}
    		if(filenameExtension.equalsIgnoreCase("rv")){ 
    			return "video/vndrn-realvideo";
    		}
    		if(filenameExtension.equalsIgnoreCase("sam")){ 
    			return "application/x-sam";
    		}
    		if(filenameExtension.equalsIgnoreCase("sat")){ 
    			return "application/x-sat";
    		}
    		if(filenameExtension.equalsIgnoreCase("sdp")){ 
    			return "application/sdp";
    		}
    		if(filenameExtension.equalsIgnoreCase("sdw")){ 
    			return "application/x-sdw";
    		}
    		if(filenameExtension.equalsIgnoreCase("sit")){ 
    			return "application/x-stuffit";
    		}
    		if(filenameExtension.equalsIgnoreCase("slb")){ 
    			return "application/x-slb";
    		}
    		if(filenameExtension.equalsIgnoreCase("sld")){ 
    			return "application/x-sld";
    		}
    		if(filenameExtension.equalsIgnoreCase("slk")){ 
    			return "drawing/x-slk";
    		}
    		if(filenameExtension.equalsIgnoreCase("smi")){ 
    			return "application/smil";
    		}
    		if(filenameExtension.equalsIgnoreCase("smil")){ 
    			return "application/smil";
    		}
    		if(filenameExtension.equalsIgnoreCase("smk")){ 
    			return "application/x-smk";
    		}
    		if(filenameExtension.equalsIgnoreCase("snd")){ 
    			return "audio/basic";
    		}
    		if(filenameExtension.equalsIgnoreCase("sol")){ 
    			return "text/plain";
    		}
    		if(filenameExtension.equalsIgnoreCase("sor")){ 
    			return "text/plain";
    		}
    		if(filenameExtension.equalsIgnoreCase("spc")){ 
    			return "application/x-pkcs7-certificates";
    		}
    		if(filenameExtension.equalsIgnoreCase("spl")){ 
    			return "application/futuresplash";
    		}
    		if(filenameExtension.equalsIgnoreCase("spp")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("ssm")){ 
    			return "application/streamingmedia";
    		}
    		if(filenameExtension.equalsIgnoreCase("sst")){ 
    			return "application/vndms-pkicertstore";
    		}
    		if(filenameExtension.equalsIgnoreCase("stl")){ 
    			return "application/vndms-pkistl";
    		}
    		if(filenameExtension.equalsIgnoreCase("stm")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("sty")){ 
    			return "application/x-sty";
    		}
    		if(filenameExtension.equalsIgnoreCase("svg")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("swf")){ 
    			return "application/x-shockwave-flash";
    		}
    		if(filenameExtension.equalsIgnoreCase("tdf")){ 
    			return "application/x-tdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("tg4")){ 
    			return "application/x-tg4";
    		}
    		if(filenameExtension.equalsIgnoreCase("tga")){ 
    			return "application/x-tga";
    		}
    		if(filenameExtension.equalsIgnoreCase("tif")){ 
    			return "image/tiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("tif")){ 
    			return "application/x-tif";
    		}
    		if(filenameExtension.equalsIgnoreCase("tiff")){ 
    			return "image/tiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("tld")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("top")){ 
    			return "drawing/x-top";
    		}
    		if(filenameExtension.equalsIgnoreCase("torrent")){ 
    			return "application/x-bittorrent";
    		}
    		if(filenameExtension.equalsIgnoreCase("tsd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("txt")){ 
    			return "text/plain";
    		}
    		if(filenameExtension.equalsIgnoreCase("uin")){ 
    			return "application/x-icq";
    		}
    		if(filenameExtension.equalsIgnoreCase("uls")){ 
    			return "text/iuls";
    		}
    		if(filenameExtension.equalsIgnoreCase("vcf")){ 
    			return "text/x-vcard";
    		}
    		if(filenameExtension.equalsIgnoreCase("vda")){ 
    			return "application/x-vda";
    		}
    		if(filenameExtension.equalsIgnoreCase("vdx")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("vpg")){ 
    			return "application/x-vpeg005";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsd")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsd")){ 
    			return "application/x-vsd";
    		}
    		if(filenameExtension.equalsIgnoreCase("vss")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vst")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vst")){ 
    			return "application/x-vst";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsw")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsx")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vtx")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vxml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("wav")){ 
    			return "audio/wav";
    		}
    		if(filenameExtension.equalsIgnoreCase("wax")){ 
    			return "audio/x-ms-wax";
    		}
    		if(filenameExtension.equalsIgnoreCase("wb1")){ 
    			return "application/x-wb1";
    		}
    		if(filenameExtension.equalsIgnoreCase("wb2")){ 
    			return "application/x-wb2";
    		}
    		if(filenameExtension.equalsIgnoreCase("wb3")){ 
    			return "application/x-wb3";
    		}
    		if(filenameExtension.equalsIgnoreCase("wbmp")){ 
    			return "image/vndwapwbmp";
    		}
    		if(filenameExtension.equalsIgnoreCase("wiz")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("wk3")){ 
    			return "application/x-wk3";
    		}
    		if(filenameExtension.equalsIgnoreCase("wk4")){ 
    			return "application/x-wk4";
    		}
    		if(filenameExtension.equalsIgnoreCase("wkq")){ 
    			return "application/x-wkq";
    		}
    		if(filenameExtension.equalsIgnoreCase("wks")){ 
    			return "application/x-wks";
    		}
    		if(filenameExtension.equalsIgnoreCase("wm")){ 
    			return "video/x-ms-wm";
    		}
    		if(filenameExtension.equalsIgnoreCase("wma")){ 
    			return "audio/x-ms-wma";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmd")){ 
    			return "application/x-ms-wmd";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmf")){ 
    			return "application/x-wmf";
    		}
    		if(filenameExtension.equalsIgnoreCase("wml")){ 
    			return "text/vndwapwml";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmv")){ 
    			return "video/x-ms-wmv";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmx")){ 
    			return "video/x-ms-wmx";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmz")){ 
    			return "application/x-ms-wmz";
    		}
    		if(filenameExtension.equalsIgnoreCase("wp6")){ 
    			return "application/x-wp6";
    		}
    		if(filenameExtension.equalsIgnoreCase("wpd")){ 
    			return "application/x-wpd";
    		}
    		if(filenameExtension.equalsIgnoreCase("wpg")){ 
    			return "application/x-wpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("wpl")){ 
    			return "application/vndms-wpl";
    		}
    		if(filenameExtension.equalsIgnoreCase("wq1")){ 
    			return "application/x-wq1";
    		}
    		if(filenameExtension.equalsIgnoreCase("wr1")){ 
    			return "application/x-wr1";
    		}
    		if(filenameExtension.equalsIgnoreCase("wri")){ 
    			return "application/x-wri";
    		}
    		if(filenameExtension.equalsIgnoreCase("wrk")){ 
    			return "application/x-wrk";
    		}
    		if(filenameExtension.equalsIgnoreCase("ws")){ 
    			return "application/x-ws";
    		}
    		if(filenameExtension.equalsIgnoreCase("ws2")){ 
    			return "application/x-ws";
    		}
    		if(filenameExtension.equalsIgnoreCase("wsc")){ 
    			return "text/scriptlet";
    		}
    		if(filenameExtension.equalsIgnoreCase("wsdl")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("wvx")){ 
    			return "video/x-ms-wvx";
    		}
    		if(filenameExtension.equalsIgnoreCase("xdp")){ 
    			return "application/vndadobexdp";
    		}
    		if(filenameExtension.equalsIgnoreCase("xdr")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xfd")){ 
    			return "application/vndadobexfd";
    		}
    		if(filenameExtension.equalsIgnoreCase("xfdf")){ 
    			return "application/vndadobexfdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("xhtml")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("xls")){ 
    			return "application/vndms-excel";
    		}
    		if(filenameExtension.equalsIgnoreCase("xls")){ 
    			return "application/x-xls";
    		}
    		if(filenameExtension.equalsIgnoreCase("xlw")){ 
    			return "application/x-xlw";
    		}
    		if(filenameExtension.equalsIgnoreCase("xml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xpl")){ 
    			return "audio/scpls";
    		}
    		if(filenameExtension.equalsIgnoreCase("xq")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xql")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xquery")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xsd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xsl")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xslt")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xwd")){ 
    			return "application/x-xwd";
    		}
    		if(filenameExtension.equalsIgnoreCase("x_b")){ 
    			return "application/x-x_b";
    		}
    		if(filenameExtension.equalsIgnoreCase("x_t")){ 
    			return "application/x-x_t";
    		}
    		if(filenameExtension.equalsIgnoreCase("asf")){ 
    			return "video/x-ms-asf";
    		}
    		if(filenameExtension.equalsIgnoreCase("asp")){ 
    			return "text/asp";
    		}
    		if(filenameExtension.equalsIgnoreCase("asx")){ 
    			return "video/x-ms-asf";
    		}
    		if(filenameExtension.equalsIgnoreCase("au")){ 
    			return "audio/basic";
    		}
    		if(filenameExtension.equalsIgnoreCase("avi")){ 
    			return "video/avi";
    		}
    		if(filenameExtension.equalsIgnoreCase("awf")){
    			return "application/vndadobeworkflow";
    		}
    		if(filenameExtension.equalsIgnoreCase("biz")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("bmp")){ 
    			return "application/x-bmp";
    		}
    		if(filenameExtension.equalsIgnoreCase("bot")){ 
    			return "application/x-bot";
    		}
    		if(filenameExtension.equalsIgnoreCase("c4t")){ 
    			return "application/x-c4t";
    		}
    		if(filenameExtension.equalsIgnoreCase("c90")){ 
    			return "application/x-c90";
    		}
    		if(filenameExtension.equalsIgnoreCase("cal")){ 
    			return "application/x-cals";
    		}
    		if(filenameExtension.equalsIgnoreCase("cat")){ 
    			return "application/vndms-pkiseccat";
    		}
    		if(filenameExtension.equalsIgnoreCase("cdf")){ 
    			return "application/x-netcdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("cdr")){ 
    			return "application/x-cdr";
    		}
    		if(filenameExtension.equalsIgnoreCase("cel")){ 
    			return "application/x-cel";
    		}
    		if(filenameExtension.equalsIgnoreCase("cer")){
    			return "application/x-x509-ca-cert";
    		}
    		if(filenameExtension.equalsIgnoreCase("cg4")){ 
    			return "application/x-g4";
    		}
    		if(filenameExtension.equalsIgnoreCase("cgm")){ 
    			return "application/x-cgm";
    		}
    		if(filenameExtension.equalsIgnoreCase("cit")){ 
    			return "application/x-cit";
    		}
    		if(filenameExtension.equalsIgnoreCase("class")){ 
    			return "java/*";
    		}
    		if(filenameExtension.equalsIgnoreCase("cml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("cmp")){ 
    			return "application/x-cmp";
    		}
    		if(filenameExtension.equalsIgnoreCase("cmx")){ 
    			return "application/x-cmx";
    		}
    		if(filenameExtension.equalsIgnoreCase("cot")){ 
    			return "application/x-cot";
    		}
    		if(filenameExtension.equalsIgnoreCase("crl")){ 
    			return "application/pkix-crl";
    		}
    		if(filenameExtension.equalsIgnoreCase("crt")){ 
    			return "application/x-x509-ca-cert";
    		}
    		if(filenameExtension.equalsIgnoreCase("csi")){ 
    			return "application/x-csi";
    		}
    		if(filenameExtension.equalsIgnoreCase("css")){ 
    			return "text/css";
    		}
    		if(filenameExtension.equalsIgnoreCase("cut")){ 
    			return "application/x-cut";
    		}
    		if(filenameExtension.equalsIgnoreCase("dbf")){ 
    			return "application/x-dbf";
    		}
    		if(filenameExtension.equalsIgnoreCase("dbm")){ 
    			return "application/x-dbm";
    		}
    		if(filenameExtension.equalsIgnoreCase("dbx")){ 
    			return "application/x-dbx";
    		}
    		if(filenameExtension.equalsIgnoreCase("dcd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("dcx")){ 
    			return "application/x-dcx";
    		}
    		if(filenameExtension.equalsIgnoreCase("der")){ 
    			return "application/x-x509-ca-cert";
    		}
    		if(filenameExtension.equalsIgnoreCase("dgn")){ 
    			return "application/x-dgn";
    		}
    		if(filenameExtension.equalsIgnoreCase("dib")){ 
    			return "application/x-dib";
    		}
    		if(filenameExtension.equalsIgnoreCase("dll")){ 
    			return "application/x-msdownload";
    		}
    		if(filenameExtension.equalsIgnoreCase("doc")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("dot")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("drw")){ 
    			return "application/x-drw";
    		}
    		if(filenameExtension.equalsIgnoreCase("dtd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("dwf")){ 
    			return "Model/vnddwf";
    		}
    		if(filenameExtension.equalsIgnoreCase("dwf")){ 
    			return "application/x-dwf";
    		}
    		if(filenameExtension.equalsIgnoreCase("dwg")){ 
    			return "application/x-dwg";
    		}
    		if(filenameExtension.equalsIgnoreCase("dxb")){ 
    			return "application/x-dxb";
    		}
    		if(filenameExtension.equalsIgnoreCase("dxf")){ 
    			return "application/x-dxf";
    		}
    		if(filenameExtension.equalsIgnoreCase("edn")){ 
    			return "application/vndadobeedn";
    		}
    		if(filenameExtension.equalsIgnoreCase("emf")){ 
    			return "application/x-emf";
    		}
    		if(filenameExtension.equalsIgnoreCase("eml")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("ent")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("epi")){ 
    			return "application/x-epi";
    		}
    		if(filenameExtension.equalsIgnoreCase("eps")){ 
    			return "application/x-ps";
    		}
    		if(filenameExtension.equalsIgnoreCase("eps")){ 
    			return "application/postscript";
    		}
    		if(filenameExtension.equalsIgnoreCase("etd")){ 
    			return "application/x-ebx";
    		}
    		if(filenameExtension.equalsIgnoreCase("exe")){ 
    			return "application/x-msdownload";
    		}
    		if(filenameExtension.equalsIgnoreCase("fax")){ 
    			return "image/fax";
    		}
    		if(filenameExtension.equalsIgnoreCase("fdf")){ 
    			return "application/vndfdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("fif")){ 
    			return "application/fractals";
    		}
    		if(filenameExtension.equalsIgnoreCase("fo")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("frm")){ 
    			return "application/x-frm";
    		}
    		if(filenameExtension.equalsIgnoreCase("g4")){ 
    			return "application/x-g4";
    		}
    		if(filenameExtension.equalsIgnoreCase("gbr")){ 
    			return "application/x-gbr";
    		}
    		if(filenameExtension.equalsIgnoreCase("gcd")){ 
    			return "application/x-gcd";
    		}
    		if(filenameExtension.equalsIgnoreCase("gif")){ 
    			return "image/gif";
    		}
    		if(filenameExtension.equalsIgnoreCase("gl2")){ 
    			return "application/x-gl2";
    		}
    		if(filenameExtension.equalsIgnoreCase("gp4")){ 
    			return "application/x-gp4";
    		}
    		if(filenameExtension.equalsIgnoreCase("hgl")){ 
    			return "application/x-hgl";
    		}
    		if(filenameExtension.equalsIgnoreCase("hmr")){ 
    			return "application/x-hmr";
    		}
    		if(filenameExtension.equalsIgnoreCase("hpg")){ 
    			return "application/x-hpgl";
    		}
    		if(filenameExtension.equalsIgnoreCase("hpl")){ 
    			return "application/x-hpl";
    		}
    		if(filenameExtension.equalsIgnoreCase("hqx")){ 
    			return "application/mac-binhex40";
    		}
    		if(filenameExtension.equalsIgnoreCase("hrf")){ 
    			return "application/x-hrf";
    		}
    		if(filenameExtension.equalsIgnoreCase("hta")){ 
    			return "application/hta";
    		}
    		if(filenameExtension.equalsIgnoreCase("htc")){ 
    			return "text/x-component";
    		}
    		if(filenameExtension.equalsIgnoreCase("htm")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("html")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("htt")){ 
    			return "text/webviewhtml";
    		}
    		if(filenameExtension.equalsIgnoreCase("htx")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("icb")){ 
    			return "application/x-icb";
    		}
    		if(filenameExtension.equalsIgnoreCase("ico")){ 
    			return "image/x-icon";
    		}
    		if(filenameExtension.equalsIgnoreCase("ico")){ 
    			return "application/x-ico";
    		}
    		if(filenameExtension.equalsIgnoreCase("iff")){ 
    			return "application/x-iff";
    		}
    		if(filenameExtension.equalsIgnoreCase("ig4")){ 
    			return "application/x-g4";
    		}
    		if(filenameExtension.equalsIgnoreCase("igs")){ 
    			return "application/x-igs";
    		}
    		if(filenameExtension.equalsIgnoreCase("iii")){ 
    			return "application/x-iphone";
    		}
    		if(filenameExtension.equalsIgnoreCase("img")){ 
    			return "application/x-img";
    		}
    		if(filenameExtension.equalsIgnoreCase("ins")){ 
    			return "application/x-internet-signup";
    		}
    		if(filenameExtension.equalsIgnoreCase("isp")){ 
    			return "application/x-internet-signup";
    		}
    		if(filenameExtension.equalsIgnoreCase("IVF")){ 
    			return "video/x-ivf";
    		}
    		if(filenameExtension.equalsIgnoreCase("java")){ 
    			return "java/*";
    		}
    		if(filenameExtension.equalsIgnoreCase("jfif")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpe")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpe")){ 
    			return "application/x-jpe";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpeg")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpg")){ 
    			return "image/jpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("jpg")){ 
    			return "application/x-jpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("js")){ 
    			return "application/x-javascript";
    		}
    		if(filenameExtension.equalsIgnoreCase("jsp")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("la1")){ 
    			return "audio/x-liquid-file";
    		}
    		if(filenameExtension.equalsIgnoreCase("lar")){ 
    			return "application/x-laplayer-reg";
    		}
    		if(filenameExtension.equalsIgnoreCase("latex")){ 
    			return "application/x-latex";
    		}
    		if(filenameExtension.equalsIgnoreCase("lavs")){ 
    			return "audio/x-liquid-secure";
    		}
    		if(filenameExtension.equalsIgnoreCase("lbm")){ 
    			return "application/x-lbm";
    		}
    		if(filenameExtension.equalsIgnoreCase("lmsff")){ 
    			return "audio/x-la-lms";
    		}
    		if(filenameExtension.equalsIgnoreCase("ls")){ 
    			return "application/x-javascript";
    		}
    		if(filenameExtension.equalsIgnoreCase("ltr")){ 
    			return "application/x-ltr";
    		}
    		if(filenameExtension.equalsIgnoreCase("m1v")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("m2v")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("m3u")){ 
    			return "audio/mpegurl";
    		}
    		if(filenameExtension.equalsIgnoreCase("m4e")){ 
    			return "video/mpeg4";
    		}
    		if(filenameExtension.equalsIgnoreCase("mac")){ 
    			return "application/x-mac";
    		}
    		if(filenameExtension.equalsIgnoreCase("man")){ 
    			return "application/x-troff-man";
    		}
    		if(filenameExtension.equalsIgnoreCase("math")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("mdb")){ 
    			return "application/msaccess";
    		}
    		if(filenameExtension.equalsIgnoreCase("mdb")){ 
    			return "application/x-mdb";
    		}
    		if(filenameExtension.equalsIgnoreCase("mfp")){ 
    			return "application/x-shockwave-flash";
    		}
    		if(filenameExtension.equalsIgnoreCase("mht")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("mhtml")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("mi")){ 
    			return "application/x-mi";
    		}
    		if(filenameExtension.equalsIgnoreCase("mid")){ 
    			return "audio/mid";
    		}
    		if(filenameExtension.equalsIgnoreCase("midi")){ 
    			return "audio/mid";
    		}
    		if(filenameExtension.equalsIgnoreCase("mil")){ 
    			return "application/x-mil";
    		}
    		if(filenameExtension.equalsIgnoreCase("mml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("mnd")){ 
    			return "audio/x-musicnet-download";
    		}
    		if(filenameExtension.equalsIgnoreCase("mns")){ 
    			return "audio/x-musicnet-stream";
    		}
    		if(filenameExtension.equalsIgnoreCase("mocha")){ 
    			return "application/x-javascript";
    		}
    		if(filenameExtension.equalsIgnoreCase("movie")){ 
    			return "video/x-sgi-movie";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp1")){ 
    			return "audio/mp1";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp2")){ 
    			return "audio/mp2";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp2v")){ 
    			return "video/mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp3")){ 
    			return "audio/mp3";
    		}
    		if(filenameExtension.equalsIgnoreCase("mp4")){ 
    			return "video/mpeg4";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpa")){ 
    			return "video/x-mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpd")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpe")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpeg")){ 
    			return "video/mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpg")){ 
    			return "video/mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpga")){ 
    			return "audio/rn-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpp")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mps")){ 
    			return "video/x-mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpt")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpv")){ 
    			return "video/mpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpv2")){ 
    			return "video/mpeg";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpw")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mpx")){ 
    			return "application/vndms-project";
    		}
    		if(filenameExtension.equalsIgnoreCase("mtx")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("mxp")){ 
    			return "application/x-mmxp";
    		}
    		if(filenameExtension.equalsIgnoreCase("net")){ 
    			return "image/pnetvue";
    		}
    		if(filenameExtension.equalsIgnoreCase("nrf")){ 
    			return "application/x-nrf";
    		}
    		if(filenameExtension.equalsIgnoreCase("nws")){ 
    			return "message/rfc822";
    		}
    		if(filenameExtension.equalsIgnoreCase("odc")){ 
    			return "text/x-ms-odc";
    		}
    		if(filenameExtension.equalsIgnoreCase("out")){ 
    			return "application/x-out";
    		}
    		if(filenameExtension.equalsIgnoreCase("p10")){ 
    			return "application/pkcs10";
    		}
    		if(filenameExtension.equalsIgnoreCase("p12")){ 
    			return "application/x-pkcs12";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7b")){ 
    			return "application/x-pkcs7-certificates";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7c")){ 
    			return "application/pkcs7-mime";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7m")){ 
    			return "application/pkcs7-mime";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7r")){ 
    			return "application/x-pkcs7-certreqresp";
    		}
    		if(filenameExtension.equalsIgnoreCase("p7s")){ 
    			return "application/pkcs7-signature";
    		}
    		if(filenameExtension.equalsIgnoreCase("pc5")){ 
    			return "application/x-pc5";
    		}
    		if(filenameExtension.equalsIgnoreCase("pci")){ 
    			return "application/x-pci";
    		}
    		if(filenameExtension.equalsIgnoreCase("pcl")){ 
    			return "application/x-pcl";
    		}
    		if(filenameExtension.equalsIgnoreCase("pcx")){ 
    			return "application/x-pcx";
    		}
    		if(filenameExtension.equalsIgnoreCase("pdf")){ 
    			return "application/pdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("pdf")){ 
    			return "application/pdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("pdx")){ 
    			return "application/vndadobepdx";
    		}
    		if(filenameExtension.equalsIgnoreCase("pfx")){ 
    			return "application/x-pkcs12";
    		}
    		if(filenameExtension.equalsIgnoreCase("pgl")){ 
    			return "application/x-pgl";
    		}
    		if(filenameExtension.equalsIgnoreCase("pic")){ 
    			return "application/x-pic";
    		}
    		if(filenameExtension.equalsIgnoreCase("pko")){ 
    				
    			return "application/vndms-pkipko";
    		}
    		if(filenameExtension.equalsIgnoreCase("pl")){
    			return "application/x-perl";
    		}
    		if(filenameExtension.equalsIgnoreCase("plg")){
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("pls")){
    			return "audio/scpls";
    		}
    		if(filenameExtension.equalsIgnoreCase("plt")){
    			return "application/x-plt";
    		}
    		if(filenameExtension.equalsIgnoreCase("png")){
    			return "image/png";
    		}
    		if(filenameExtension.equalsIgnoreCase("png")){
    			return "application/x-png";
    		}
    		if(filenameExtension.equalsIgnoreCase("pot")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppa")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppm")){ 
    			return "application/x-ppm";
    		}
    		if(filenameExtension.equalsIgnoreCase("pps")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppt")){ 
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("ppt")){ 
    			return "application/x-ppt";
    		}
    		if(filenameExtension.equalsIgnoreCase("pr")){ 
    			return "application/x-pr";
    		}
    		if(filenameExtension.equalsIgnoreCase("prf")){
    			return "application/pics-rules";
    		}
    		if(filenameExtension.equalsIgnoreCase("prn")){ 
    			return "application/x-prn";
    		}
    		if(filenameExtension.equalsIgnoreCase("prt")){ 
    			return "application/x-prt";
    		}
    		if(filenameExtension.equalsIgnoreCase("ps")){ 
    			return "application/x-ps";
    		}
    		if(filenameExtension.equalsIgnoreCase("ps")){ 
    			return "application/postscript";
    		}
    		if(filenameExtension.equalsIgnoreCase("ptn")){ 
    			return "application/x-ptn";
    		}
    		if(filenameExtension.equalsIgnoreCase("pwz")){
    			return "application/vndms-powerpoint";
    		}
    		if(filenameExtension.equalsIgnoreCase("r3t")){
    			return "text/vndrn-realtext3d";
    		}
    		if(filenameExtension.equalsIgnoreCase("ra")){ 
    			return "audio/vndrn-realaudio";
    		}
    		if(filenameExtension.equalsIgnoreCase("ram")){ 
    			return "audio/x-pn-realaudio";
    		}
    		if(filenameExtension.equalsIgnoreCase("ras")){
    			return "application/x-ras";
    		}
    		if(filenameExtension.equalsIgnoreCase("rat")){
    			return "application/rat-file";
    		}
    		if(filenameExtension.equalsIgnoreCase("rdf")){
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("rec")){ 
    			return "application/vndrn-recording";
    		}
    		if(filenameExtension.equalsIgnoreCase("red")){
    			return "application/x-red";
    		}
    		if(filenameExtension.equalsIgnoreCase("rgb")){
    			return "application/x-rgb";
    		}
    		if(filenameExtension.equalsIgnoreCase("rjs")){ 
    			return "application/vndrn-realsystem-rjs";
    		}
    		if(filenameExtension.equalsIgnoreCase("rjt")){
    			return "application/vndrn-realsystem-rjt";
    		}
    		if(filenameExtension.equalsIgnoreCase("rlc")){ 
    			return "application/x-rlc";
    		}
    		if(filenameExtension.equalsIgnoreCase("rle")){ 
    			return "application/x-rle";
    		}
    		if(filenameExtension.equalsIgnoreCase("rm")){
    			return "application/vndrn-realmedia";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmf")){ 
    			return "application/vndadobermf";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmi")){ 
    			return "audio/mid";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmj")){ 
    			return "application/vndrn-realsystem-rmj";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmm")){ 
    			return "audio/x-pn-realaudio";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmp")){
    			return "application/vndrn-rn_music_package";
    		}
    		if(filenameExtension.equalsIgnoreCase("rms")){ 
    			return "application/vndrn-realmedia-secure";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmvb")){ 
    			return "application/vndrn-realmedia-vbr";
    		}
    		if(filenameExtension.equalsIgnoreCase("rmx")){ 
    			return "application/vndrn-realsystem-rmx";
    		}
    		if(filenameExtension.equalsIgnoreCase("rnx")){
    			return "application/vndrn-realplayer";
    		}
    		if(filenameExtension.equalsIgnoreCase("rp")){ 
    			return "image/vndrn-realpix";
    		}
    		if(filenameExtension.equalsIgnoreCase("rpm")){
    			return "audio/x-pn-realaudio-plugin";
    		}
    		if(filenameExtension.equalsIgnoreCase("rsml")){
    			return "application/vndrn-rsml";
    		}
    		if(filenameExtension.equalsIgnoreCase("rt")){ 
    			return "text/vndrn-realtext";
    		}
    		if(filenameExtension.equalsIgnoreCase("rtf")){
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("rtf")){
    			return "application/x-rtf";
    		}
    		if(filenameExtension.equalsIgnoreCase("rv")){ 
    			return "video/vndrn-realvideo";
    		}
    		if(filenameExtension.equalsIgnoreCase("sam")){ 
    			return "application/x-sam";
    		}
    		if(filenameExtension.equalsIgnoreCase("sat")){ 
    			return "application/x-sat";
    		}
    		if(filenameExtension.equalsIgnoreCase("sdp")){ 
    			return "application/sdp";
    		}
    		if(filenameExtension.equalsIgnoreCase("sdw")){ 
    			return "application/x-sdw";
    		}
    		if(filenameExtension.equalsIgnoreCase("sit")){
    			return "application/x-stuffit";
    		}
    		if(filenameExtension.equalsIgnoreCase("slb")){ 
    			return "application/x-slb";
    		}
    		if(filenameExtension.equalsIgnoreCase("sld")){ 
    			return "application/x-sld";
    		}
    		if(filenameExtension.equalsIgnoreCase("slk")){ 
    			return "drawing/x-slk";
    		}
    		if(filenameExtension.equalsIgnoreCase("smi")){ 
    			return "application/smil";
    		}
    		if(filenameExtension.equalsIgnoreCase("smil")){
    			return "application/smil";
    		}
    		if(filenameExtension.equalsIgnoreCase("smk")){
    			return "application/x-smk";
    		}
    		if(filenameExtension.equalsIgnoreCase("snd")){
    			return "audio/basic";
    		}
    		if(filenameExtension.equalsIgnoreCase("sol")){
    			return "text/plain";
    		}
    		if(filenameExtension.equalsIgnoreCase("sor")){ 
    			return "text/plain";
    		}
    		if(filenameExtension.equalsIgnoreCase("spc")){
    			return "application/x-pkcs7-certificates";
    		}
    		if(filenameExtension.equalsIgnoreCase("spl")){
    			return "application/futuresplash";
    		}
    		if(filenameExtension.equalsIgnoreCase("spp")){
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("ssm")){ 
    			return "application/streamingmedia";
    		}
    		if(filenameExtension.equalsIgnoreCase("sst")){
    			return "application/vndms-pkicertstore";
    		}
    		if(filenameExtension.equalsIgnoreCase("stl")){
    			return "application/vndms-pkistl";
    		}
    		if(filenameExtension.equalsIgnoreCase("stm")){ 
    				
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("sty")){ 
    			return "application/x-sty";
    		}
    		if(filenameExtension.equalsIgnoreCase("svg")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("swf")){ 
    			return "application/x-shockwave-flash";
    		}
    		if(filenameExtension.equalsIgnoreCase("tdf")){ 
    			return "application/x-tdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("tg4")){ 
    			return "application/x-tg4";
    		}
    		if(filenameExtension.equalsIgnoreCase("tga")){ 
    			return "application/x-tga";
    		}
    		if(filenameExtension.equalsIgnoreCase("tif")){ 
    			return "image/tiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("tif")){
    			return "application/x-tif";
    		}
    		if(filenameExtension.equalsIgnoreCase("tiff")){ 
    			return "image/tiff";
    		}
    		if(filenameExtension.equalsIgnoreCase("tld")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("top")){ 
    			return "drawing/x-top";
    		}
    		if(filenameExtension.equalsIgnoreCase("torrent")){ 
    			return "application/x-bittorrent";
    		}
    		if(filenameExtension.equalsIgnoreCase("tsd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("txt")){ 
    			return "text/plain";
    		}
    		if(filenameExtension.equalsIgnoreCase("uin")){ 
    			return "application/x-icq";
    		}
    		if(filenameExtension.equalsIgnoreCase("uls")){
    			return "text/iuls";
    		}
    		if(filenameExtension.equalsIgnoreCase("vcf")){
    			return "text/x-vcard";
    		}
    		if(filenameExtension.equalsIgnoreCase("vda")){ 
    			return "application/x-vda";
    		}
    		if(filenameExtension.equalsIgnoreCase("vdx")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("vpg")){ 
    			return "application/x-vpeg005";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsd")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsd")){ 
    			return "application/x-vsd";
    		}
    		if(filenameExtension.equalsIgnoreCase("vss")){
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vst")){
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vst")){ 
    			return "application/x-vst";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsw")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vsx")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vtx")){ 
    			return "application/vndvisio";
    		}
    		if(filenameExtension.equalsIgnoreCase("vxml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("wav")){ 
    			return "audio/wav";
    		}
    		if(filenameExtension.equalsIgnoreCase("wax")){ 
    			return "audio/x-ms-wax";
    		}
    		if(filenameExtension.equalsIgnoreCase("wb1")){ 
    			return "application/x-wb1";
    		}
    		if(filenameExtension.equalsIgnoreCase("wb2")){ 
    			return "application/x-wb2";
    		}
    		if(filenameExtension.equalsIgnoreCase("wb3")){ 
    			return "application/x-wb3";
    		}
    		if(filenameExtension.equalsIgnoreCase("wbmp")){
    			return "image/vndwapwbmp";
    		}
    		if(filenameExtension.equalsIgnoreCase("wiz")){ 
    			return "application/msword";
    		}
    		if(filenameExtension.equalsIgnoreCase("wk3")){
    			return "application/x-wk3";
    		}
    		if(filenameExtension.equalsIgnoreCase("wk4")){
    			return "application/x-wk4";
    		}
    		if(filenameExtension.equalsIgnoreCase("wkq")){
    			return "application/x-wkq";
    		}
    		if(filenameExtension.equalsIgnoreCase("wks")){ 
    			return "application/x-wks";
    		}
    		if(filenameExtension.equalsIgnoreCase("wm")){ 
    			return "video/x-ms-wm";
    		}
    		if(filenameExtension.equalsIgnoreCase("wma")){ 
    			return "audio/x-ms-wma";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmd")){ 
    			return "application/x-ms-wmd";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmf")){ 
    			return "application/x-wmf";
    		}
    		if(filenameExtension.equalsIgnoreCase("wml")){
    			return "text/vndwapwml";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmv")){ 
    			return "video/x-ms-wmv";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmx")){ 
    			return "video/x-ms-wmx";
    		}
    		if(filenameExtension.equalsIgnoreCase("wmz")){ 
    			return "application/x-ms-wmz";
    		}
    		if(filenameExtension.equalsIgnoreCase("wp6")){
    			return "application/x-wp6";
    		}
    		if(filenameExtension.equalsIgnoreCase("wpd")){ 
    			return "application/x-wpd";
    		}
    		if(filenameExtension.equalsIgnoreCase("wpg")){ 
    			return "application/x-wpg";
    		}
    		if(filenameExtension.equalsIgnoreCase("wpl")){ 
    			return "application/vndms-wpl";
    		}
    		if(filenameExtension.equalsIgnoreCase("wq1")){ 
    			return "application/x-wq1";
    		}
    		if(filenameExtension.equalsIgnoreCase("wr1")){ 
    			return "application/x-wr1";
    		}
    		if(filenameExtension.equalsIgnoreCase("wri")){ 
    			return "application/x-wri";
    		}
    		if(filenameExtension.equalsIgnoreCase("wrk")){ 
    			return "application/x-wrk";
    		}
    		if(filenameExtension.equalsIgnoreCase("ws")){ 
    			return "application/x-ws";
    		}
    		if(filenameExtension.equalsIgnoreCase("ws2")){ 
    			return "application/x-ws";
    		}
    		if(filenameExtension.equalsIgnoreCase("wsc")){ 
    			return "text/scriptlet";
    		}
    		if(filenameExtension.equalsIgnoreCase("wsdl")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("wvx")){ 
    			return "video/x-ms-wvx";
    		}
    		if(filenameExtension.equalsIgnoreCase("xdp")){ 
    			return "application/vndadobexdp";
    		}
    		if(filenameExtension.equalsIgnoreCase("xdr")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xfd")){ 
    			return "application/vndadobexfd";
    		}
    		if(filenameExtension.equalsIgnoreCase("xfdf")){
    			return "application/vndadobexfdf";
    		}
    		if(filenameExtension.equalsIgnoreCase("xhtml")){ 
    			return "text/html";
    		}
    		if(filenameExtension.equalsIgnoreCase("xls")){ 
    			return "application/vndms-excel";
    		}
    		if(filenameExtension.equalsIgnoreCase("xls")){ 
    			return "application/x-xls";
    		}
    		if(filenameExtension.equalsIgnoreCase("xlw")){ 
    			return "application/x-xlw";
    		}
    		if(filenameExtension.equalsIgnoreCase("xml")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xpl")){ 
    			return "audio/scpls";
    		}
    		if(filenameExtension.equalsIgnoreCase("xq")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xql")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xquery")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xsd")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xsl")){ 
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xslt")){
    			return "text/xml";
    		}
    		if(filenameExtension.equalsIgnoreCase("xwd")){ 
    			return "application/x-xwd";
    		}
    		if(filenameExtension.equalsIgnoreCase("x_b")){ 
    			return "application/x-x_b";
    		}
    		if(filenameExtension.equalsIgnoreCase("x_t")){ 
    			return "application/x-x_t";
    		}
    		return "application/octet-stream";
     }  
	
//	public static void main(String[] args) {
//		List<TreeObject> treeObjectList = CommUtil.getButtonMenuListByPartentId("56");
//		if (treeObjectList != null && !treeObjectList.isEmpty() && treeObjectList.size()>0) {
//			for (int i = 0; i < treeObjectList.size(); i++) {
//				System.out.println("treeObjectList["+i+"]:"+treeObjectList.get(i));
//			}
//		}else{
//			System.out.println("该集合为空!");
//		}
//	}

}
