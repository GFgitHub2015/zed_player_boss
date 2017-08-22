package com.zed.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zed.common.util.CommUtil;

public final class DateUtil{

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMATTION = "yyyyMMddHHmmss";
	private static final String DATE_SHORT_FORMAT = "yyyy-MM-dd";
	private static final String DATE_SHORT_FORMATTION = "yyyyMMdd";
	private static final String DATE_SHORT_FORMAT_YEAR = "yyyy";
	private static final String DATE_SHORT_FORMAT_MONTH = "yyyy-MM";
	private static final String DATE_MONTH_DAY_FORMAT = "MM-dd";
	
    /**
     * 标准时间格式转化为字符串
     * @param Date 日期
     * @return String 日期字符串
     */
    public static String date2String(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_FORMAT).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 月日时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串  yyyyMMddHHmmss
     */
    public static String dates2String(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_FORMATTION).format(date.getTime());
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    
    /**
     * 字符串转化为标准时间格式
     * @param dateStr 日期字符串
     * @return Date 日期
     */
    public static Date string2Date(String dateStr){
    	Date date = null;
    	if(dateStr != null && !("").equals(dateStr)){
    		try {
    			date = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
    		} catch (ParseException e) {
    			logger.error("字符串转化为日期出错！");
    			e.printStackTrace();
    		}
    	}
    	return date;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String datesShort2String(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_SHORT_FORMATTION).format(date.getTime());
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String dateShort2String(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String dateShort2String(Long time){
    	String dateStr = "";
    	try {
			if(time != null){
				dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT).format(time);
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 字符串转化为短时间格式
     * @param dateStr 日期字符串
     * @return Date 日期
     */
    public static Date string2DateShort(String dateStr){
    	Date date = null;
    	if(dateStr != null && !("").equals(dateStr)){
    		try {
    			date = new SimpleDateFormat(DATE_SHORT_FORMAT).parse(dateStr);
    		} catch (ParseException e) {
    			logger.error("字符串转化为日期出错！");
    			e.printStackTrace();
    		}
    	}
    	return date;
    }
    
    /**
     * 月日时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String dateMonthAndDay2String(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_MONTH_DAY_FORMAT).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 字符串转化为月日时间格式
     * @param dateStr 日期字符串
     * @return Date 日期
     */
    public static Date string2DateMonthAndDay(String dateStr){
    	Date date = null;
    	if(dateStr != null && !("").equals(dateStr)){
    		try {
    			date = new SimpleDateFormat(DATE_MONTH_DAY_FORMAT).parse(dateStr);
    		} catch (ParseException e) {
    			logger.error("字符串转化为日期出错！");
    			e.printStackTrace();
    		}
    	}
    	return date;
    }
    
    
    /**
     * 年时间格式转化为字符串 yyyy
     * @param date 日期
     * @return String 日期字符串 yyyy
     */
    public static String date2YearString(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT_YEAR).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    /**
     * 年月时间格式转化为字符串 yyyy-MM
     * @param date 日期
     * @return String 日期字符串 yyyy-MM
     */
    public static String date2YearMonthString(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT_MONTH).format(date.getTime());
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    /**
     * 年份的加减运算
     * @param Date
     * @param i
     * @return
     */
    public static Date addOrMinusYear(Date nowDate, int i) { 
        Date rtn = null;  
        GregorianCalendar cal = new GregorianCalendar();  
        cal.setTime(nowDate);  
        cal.add(1, i);  
        rtn = cal.getTime();  
        return rtn;  
    }  
    /**
     * 月份的加减运算
     * @param Date
     * @param i
     * @return
     */
    public static Date addOrMinusMonth(Date nowDate, int i) { 
    	Date rtn = null;  
    	GregorianCalendar cal = new GregorianCalendar();  
    	cal.setTime(nowDate);  
    	cal.add(2, i);  
    	rtn = cal.getTime();  
    	return rtn;  
    }

	/**
	 * 获取时间段内的所有日期
	 * @param dateStart yyyy-MM-dd
	 * @param dateEnd yyyy-MM-dd
	 * @return
     */
	public static List<String> findDates(String dateStart,String dateEnd){
		List<String> lDate = new ArrayList();
		Date dBegin = string2DateShort(dateStart);
		Date dEnd = string2DateShort(dateEnd);
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())){
			lDate.add(dateShort2String(calBegin.getTime()));
			calBegin.add(Calendar.DAY_OF_MONTH,1);
		}
		lDate.add(dateEnd);
		return lDate;
	}
	
	/**
	 * 根据国际时区ID获取时间字符串
	 * @param zoneId
	 * @return
	 */
	public static String getZoneTime(String zoneId){
		Calendar calendar = new GregorianCalendar();
		TimeZone tz = TimeZone.getTimeZone(zoneId);
		calendar.setTimeZone(tz);
		String timeStr =calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+ ":" + calendar.get(Calendar.SECOND);
		return timeStr;
	}
	
	/**
	 * 根据国际时区ID获取时间
	 * @param zoneId
	 * @return
	 */
	public static Date getZoneDate(String zoneId){
		Calendar calendar = new GregorianCalendar();
		TimeZone tz = TimeZone.getTimeZone(zoneId);
		calendar.setTimeZone(tz);
		String timeStr =calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+ ":" + calendar.get(Calendar.SECOND);
		return string2Date(timeStr);
	}
	/**
	 * 根据国际时区偏移量
	 * @param zoneId
	 * @return
	 */
	public static Date getZoneDate(Date date, String zoneId){
		// 1、取得指定时区的当前时间：
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		TimeZone tz = TimeZone.getTimeZone(zoneId);
		calendar.setTimeZone(tz);
		// 2、取得时间偏移量：
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		// 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		Date d = new Date(calendar.getTimeInMillis());
		return d;
	}
	
	/**
	 * 获取所有的国际时区ID
	 * @return
	 */
	public static List<String> getZoneList(){
		List<String> zones = new LinkedList<String>();
		Calendar c = new GregorianCalendar();  
		String s [] = c.getTimeZone().getAvailableIDs();  
        for(int i = 0; i < s.length;i++){
        	zones.add(s[i]);
        }
        return zones;
	}
	
  
    
    
    
    /**  
     * 将日期时间字符串根据转换为指定时区的日期时间.  
     *   
     * @param srcFormater  
     *            待转化的日期时间的格式.  
     * @param srcDateTime  
     *            待转化的日期时间.  
     * @param dstFormater  
     *            目标的日期时间的格式.  
     * @param dstTimeZoneId  
     *            目标的时区编号.  
     *   
     * @return 转化后的日期时间.  
     */  
    public static String string2Timezone(String srcFormater,   
            String srcDateTime, String dstFormater, String dstTimeZoneId) {   
        if (srcFormater == null || "".equals(srcFormater))   
            return null;   
        if (srcDateTime == null || "".equals(srcDateTime))   
            return null;   
        if (dstFormater == null || "".equals(dstFormater))   
            return null;   
        if (dstTimeZoneId == null || "".equals(dstTimeZoneId))   
            return null;   
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);   
        try {   
            int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);   
            Date d = sdf.parse(srcDateTime);   
            long nowTime = d.getTime();   
            long newNowTime = nowTime - diffTime;   
            d = new Date(newNowTime);   
            return date2String(dstFormater, d);   
        } catch (ParseException e) {   
            return null;   
        } finally {   
            sdf = null;   
        }   
    }   
    
    /**  
     * 将日期时间字符串根据转换为指定时区的日期时间.  
     *   
     * @param srcFormater  
     *            待转化的日期时间的格式.  
     * @param srcDateTime  
     *            待转化的日期时间.  
     * @param dstFormater  
     *            目标的日期时间的格式.  
     * @param dstTimeZoneId  
     *            目标的时区编号.  
     *   
     * @return 转化后的日期时间.  
     */  
    public static Date string2TimezoneDate(String srcFormater,   
    		String srcDateTime, String dstFormater, String dstTimeZoneId) {   
    	if (srcFormater == null || "".equals(srcFormater))   
    		return null;   
    	if (srcDateTime == null || "".equals(srcDateTime))   
    		return null;   
    	if (dstFormater == null || "".equals(dstFormater))   
    		return null;   
    	if (dstTimeZoneId == null || "".equals(dstTimeZoneId))   
    		return null;   
    	SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);   
    	try {   
    		int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);   
    		Date d = sdf.parse(srcDateTime);   
    		long nowTime = d.getTime();   
    		long newNowTime = nowTime - diffTime;   
    		d = new Date(newNowTime);   
    		return d;   
    	} catch (ParseException e) {   
    		return null;   
    	} finally {   
    		sdf = null;   
    	}   
    }   
    
    /**  
     * 日期(时间)转化为字符串.  
     *   
     * @param formater  
     *            日期或时间的格式.  
     * @param aDate  
     *            java.util.Date类的实例.  
     * @return 日期转化后的字符串.  
     */  
    public static String date2String(String formater, Date aDate) {   
        if (formater == null || "".equals(formater))   
            return null;   
        if (aDate == null)   
            return null;   
        return (new SimpleDateFormat(formater)).format(aDate);   
    }   
  
    /**  
     * 获取系统当前默认时区与UTC的时间差.(单位:毫秒)  
     *   
     * @return 系统当前默认时区与UTC的时间差.(单位:毫秒)  
     */  
    private static int getDefaultTimeZoneRawOffset() {   
        return TimeZone.getDefault().getRawOffset();   
    }   
  
    /**  
     * 获取指定时区与UTC的时间差.(单位:毫秒)  
     *   
     * @param timeZoneId  
     *            时区Id  
     * @return 指定时区与UTC的时间差.(单位:毫秒)  
     */  
    private static int getTimeZoneRawOffset(String timeZoneId) {   
        return TimeZone.getTimeZone(timeZoneId).getRawOffset();   
    }   
  
    /**  
     * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)  
     *   
     * @param timeZoneId  
     *            时区Id  
     * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)  
     */  
    private static int getDiffTimeZoneRawOffset(String timeZoneId) {   
        return TimeZone.getDefault().getRawOffset()   
                - TimeZone.getTimeZone(timeZoneId).getRawOffset();   
    }   
  
    /**  
     * 将日期时间字符串根据转换为指定时区的日期时间.  
     *   
     * @param srcDateTime  
     *            待转化的日期时间.  
     * @param dstTimeZoneId  
     *            目标的时区编号.  
     *   
     * @return 转化后的日期时间.  
     * @see #string2Timezone(String, String, String, String)  
     */  
    public static String string2TimezoneDefault(String srcDateTime,   
            String dstTimeZoneId) {   
        return string2Timezone(DATE_FORMAT, srcDateTime,   
        		DATE_FORMAT, dstTimeZoneId);   
    }  
    
    /**  
     * 将日期时间字符串根据转换为指定时区的日期时间.  
     *   
     * @param srcdate  
     *            待转化的日期时间.  
     * @param dstTimeZoneId  
     *            目标的时区编号.  
     *   
     * @return 转化后的日期时间.  
     * @see #string2Timezone(String, String, String, String)  
     */  
    public static Date string2TimezoneDefault(Date srcdate,   
    		String dstTimeZoneId) {
    	return string2TimezoneDate(DATE_FORMAT, date2String(srcdate),   
    			DATE_FORMAT, dstTimeZoneId);   
    }  
	
    /**
	 * @description : 转换YouTube的时间 ,PT10M7S>10:07,PT2H>02:00:00
	*/
	public static String getDurationStr(String durationStr) {
		String timeStr="";
		String hour = "0";
		if(CommUtil.isEmpty(durationStr)){
			return "";
		}
		durationStr = durationStr.replace("PT", "");
		if(durationStr.contains("H")){
			hour = durationStr.substring(0, durationStr.indexOf("H"));
			durationStr = durationStr.substring(durationStr.indexOf("H")+1, durationStr.length());
		}
		String minite = "0";
		if(durationStr.contains("M")){
			minite = durationStr.substring(0, durationStr.indexOf("M"));
			durationStr = durationStr.substring(durationStr.indexOf("M")+1, durationStr.length());
		}
		String second = "0";
		if(durationStr.contains("S")){
			second = durationStr.substring(0, durationStr.indexOf("S"));
			durationStr = durationStr.substring(durationStr.indexOf("S")+1, durationStr.length());
		}
		if("0".equals(hour)){
			timeStr = String.format("%02d:%02d", Integer.parseInt(minite),Integer.parseInt(second));
		}else{
			timeStr = String.format("%02d:%02d:%02d",Integer.parseInt(hour), Integer.parseInt(minite),Integer.parseInt(second));
		}
		return timeStr;
	}
	
	public static void main(String[] args){
		/*Calendar calendar = new GregorianCalendar();
		TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
		calendar.setTimeZone(tz);
		Date oldDate = new Date(calendar.getTimeInMillis());
		System.out.println(date2String(oldDate));
		
		Date newdate = getZoneDate(new Date(), "America/Los_Angeles");
		System.out.println(date2String(newdate));*/
		Date oldDate = new Date();
		System.out.println(date2String(oldDate));
		String newDate = string2TimezoneDefault(date2String(oldDate), "America/Chicago");
		System.out.println(newDate);
	}

}
