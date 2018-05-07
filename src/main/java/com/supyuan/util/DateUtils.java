/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.supyuan.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 日期处理
 * 
 * 
 * 2014年5月5日 下午12:00:00
 * flyfox 330627517@qq.com
 */
public class DateUtils {
	
	public static final int SECOND = 1;
	public static final int MINUTE_SECOND = 60 * SECOND;
	public static final int HOUR_SECOND = 60 * MINUTE_SECOND;
	public static final int DAY_SECOND = 24 * HOUR_SECOND;
	public static final int WEEK_SECOND = 7 * DAY_SECOND;

	/** 日期格式：yyyy-MM-dd HH:mm:ss.SSS */
	public static final String YMD_HMSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 日期格式：yyyyMMddHHmmssSSS */
	public static final String YMDHMSSS = "yyyyMMddHHmmssSSS";
	/** 日期格式：yyyy-MM-dd HH:mm:ss */
	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式：yyyy-MM-dd HH:mm */
	public static final String YMD_HM = "yyyy-MM-dd HH:mm";
	/** 日期格式：yyyyMMddHHmmss */
	public static final String YMDHMS = "yyyyMMddHHmmss";
	/** 日期格式：yyyy-MM-dd */
	public static final String YMD = "yyyy-MM-dd";
	/** 时间格式：HH:mm:ss */
	public static final String HMS = "HH:mm:ss";
	
	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX = "yyyy-MM-dd";
	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX_YYYYMMDD = "yyyyMMdd";
	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT = new EPNDateFormat(DEFAULT_REGEX);
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS = new EPNDateFormat(
			DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT_YYYYMMDD = new EPNDateFormat(DEFAULT_REGEX_YYYYMMDD);
	
	private static DateFormat yyyyMMformat = new SimpleDateFormat("yyyy-MM");
	
	private static Map<String, EPNDateFormat> formatMap = new HashMap<String, EPNDateFormat>();
	static {
		formatMap.put(DEFAULT_REGEX, DEFAULT_FORMAT);
		formatMap.put(DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS, DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS);
		formatMap.put(DEFAULT_REGEX_YYYYMMDD, DEFAULT_FORMAT_YYYYMMDD);
	}

	private DateUtils() {

	}

	/**
	 * 时间对象格式化成String ,等同于java.text.DateFormat.format();
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return 转化结果
	 */
	public static String format(java.util.Date date) {
		return DEFAULT_FORMAT.format(date);
	}
	
	public static String format(String date) {
		try {
			return DEFAULT_FORMAT.format(DEFAULT_FORMAT.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 时间对象格式化成String ,等同于java.text.SimpleDateFormat.format();
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * @param regex
	 *            定义格式的字符串
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com    
	 * @return 转化结果
	 */
	public static String format(java.util.Date date, String regex) {
		return getDateFormat(regex).format(date);
	}

	private static EPNDateFormat getDateFormat(String regex) {
		EPNDateFormat fmt = formatMap.get(regex);
		if (fmt == null) {
			fmt = new EPNDateFormat(regex);
			formatMap.put(regex, fmt);
		}
		return fmt;
	}

	/**
	 * 尝试解析时间字符串 ,if failed return null;
	 * 
	 * @author wangp
	 * @since 2008.12.20
	 * @param time
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return
	 */
	public static Date parseByAll(String time) {
		Date stamp = null;
		if (time == null || "".equals(time))
			return null;
		Pattern p3 = Pattern.compile("\\b\\d{2}[.-]\\d{1,2}([.-]\\d{1,2}){0,1}\\b");
		if (p3.matcher(time).matches()) {
			time = (time.charAt(0) == '1' || time.charAt(0) == '0' ? "20" : "19") + time;
		}

		stamp = DateUtils.parse(time, "yyyy-MM-ddHH:mm:ss");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyyMMdd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年MM月dd日");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyyMM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年MM月");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年");
		return stamp;
	}

	/**
	 * 解析字符串成时间 ,遇到错误返回null不抛异常
	 * 
	 * @param source
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return 解析结果
	 */
	public static java.util.Date parse(String source) {
		try {
			return DEFAULT_FORMAT.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解析字符串成时间 ,遇到错误返回null不抛异常
	 * 
	 * @param source
	 * @param 格式表达式
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return 解析结果
	 */
	public static java.util.Date parse(String source, String regex) {
		try {
			EPNDateFormat fmt = getDateFormat(regex);
			return fmt.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 取得当前时间的Date对象 ;
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return
	 */
	public static Date getNowDate() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前时间字符串
	 * 
	 * 2014年5月5日 下午12:00:00
	 * flyfox 330627517@qq.com
	 * @return
	 */
	public static String getNow() {
		return getNow(DEFAULT_REGEX);
	}
	
	/**
	 * 获取当前时间字符串
	 * 
	 * 2014年7月4日 下午11:47:10
	 * flyfox 330627517@qq.com
	 * @param regex 格式表达式
	 * @return
	 */
	public static String getNow(String regex) {
		return format(getNowDate(), regex);
	}
	
	/***
	 * 获取指定时间所在天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrenDayBeginTime(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return format(ca.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/***
	 * 获取指定时间所在天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrenDayEndTime(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		return format(ca.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 前一天
	 * @author liangxp
	 * @return
	 */
	public static String getBeforeTime() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DATE, -1);// 日期减1  
		return format(ca.getTime(), DEFAULT_REGEX);
	}
	
	public static String getBeforeTime(String regx) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DATE, -1);// 日期减1  
		return format(ca.getTime(), regx);
	}
	
	
	//计算两个日期相差年数  
	public static int yearDateDiff(String startDate){  
	   Calendar calBegin = Calendar.getInstance();
	   try {
		   calBegin.setTime(DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS.parse(startDate));
	   } catch (ParseException e) {
			e.printStackTrace();
	   }
	   Calendar calEnd = Calendar.getInstance();  
	   calEnd.setTime(new Date());  
	   return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);  
	}
	
	
	public static int getDateDay(String time){  
		Calendar aCalendar = Calendar.getInstance(); 
		try {
			aCalendar.setTime(yyyyMMformat.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day=aCalendar.getActualMaximum(Calendar.DATE);
		return day; 
	}
	
	//计算两个日期相差年数  
	public static int yearDateDiff(Date startDate){  
	   Calendar calBegin = Calendar.getInstance();
	   calBegin.setTime(startDate);
	   Calendar calEnd = Calendar.getInstance();  
	   calEnd.setTime(new Date());  
	   return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);  
	}
	
	public static void main(String[] args) {
		
		
		System.out.println(getDateDay("2017-2"));
	}
	
	
	public static int get11CurrrTime(){
		return (int)(System.currentTimeMillis()/1000);
	}
	
	
	public static List<String> getMonthList(String start, String end){
		List<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    try {
			min.setTime(sdf.parse(start));
			max.setTime(sdf.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	    Calendar curr = max;
	    result.add(sdf.format(curr.getTime()));
	    curr.add(Calendar.MONTH, -1);
	    while (curr.after(min)) {
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.MONTH, -1);
	    }
		return result;
	}
	
	public static List<String> getDayList( String end){
		List<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月日
	    Calendar max = Calendar.getInstance();
	   
	    try {
			max.setTime(sdf.parse(end));
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    
	    for(int i=0;i<=31;i++){
	    	try {
	    		if(i!=0)
	    		max.add(max.DATE, -1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	result.add(sdf.format(max.getTime()));
	    }
	    Collections.reverse(result); 

		return result;
	}

	
	public static String getCurrBeforeYearMonth(){
		 Calendar c = Calendar.getInstance();
		 c.setTime(new Date());
	     c.add(Calendar.YEAR, -1);
	     Date y = c.getTime();
	     String beforeyear = yyyyMMformat.format(y);
	     return beforeyear;
	}
	
	
	public static String getCurrYearMonth(){
	     String curryear = yyyyMMformat.format(new Date().getTime());
	     return curryear;
	}
	
	/**
	 * 当前月第一天
	 * @author liangxp
	 * @return
	 */
	public static String getCurrMonthFirstDay(){
		Calendar cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        return format(cale.getTime());  
	}
	
}

class EPNDateFormat {
	private SimpleDateFormat instance;

	EPNDateFormat() {
		instance = new SimpleDateFormat(DateUtils.DEFAULT_REGEX);
		instance.setLenient(false);
	}

	EPNDateFormat(String regex) {
		instance = new SimpleDateFormat(regex);
		instance.setLenient(false);
	}

	synchronized String format(java.util.Date date) {
		if (date == null)
			return "";
		return instance.format(date);
	}

	synchronized java.util.Date parse(String source) throws ParseException {
		return instance.parse(source);
	}
}
