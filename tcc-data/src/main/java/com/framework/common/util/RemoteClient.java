package com.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
* 网上400客户访问记录实现类
*<p>
* 文件名： RemoteClient.java
*<p>
* Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
* @author 安静波
* @since 1.0
* @version 1.0
*/
public class RemoteClient {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.length() >0 && !"unknown".equalsIgnoreCase(ip)) {
			Pattern pattern = Pattern.compile("[0-9\\.]*");
			Matcher m = pattern.matcher(ip);
			if (!m.matches()) {
				ip = "illegal";
			}
		}
		return ip;
	}

	public static String generateCookieId(String anyStr) {
		return new Date().getTime() + "_" + RequestUtil.encodeUrl(anyStr) + new Random().nextInt();
	}


	public static void setCookie(HttpServletResponse response, String name, String value) {
		Cookie newCookie = new Cookie(name, value); //创建一个Cookie 
		newCookie.setMaxAge(3600 * 24 * 365); //设置有效期为一年 
		response.addCookie(newCookie); //写入Cookie 
	}

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies!=null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

/*	public static void main(String[] args) {
		String url = "http://search.yahoo.com/search;_ylt=AgkX73C7zooaR9DcdT4XTDabvZx4?vc=&fp_ip=cn&vc=countrycn&p=%E4%B8%AD%E5%9B%BD&toggle=1&cop=mss&ei=UTF-8&fr=yfp-t-701";
		System.out.println(RemoteClient.getKeyword(url));
		
		url = "http://www.google.cn/search?q=%E6%8F%90%E5%8F%96+%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E+%E5%85%B3%E9%94%AE%E5%AD%97&hl=zh-CN&newwindow=1&sa=2";
		System.out.println(RemoteClient.getKeyword(url));
		
		url = "http://www.google.com.tw/search?hl=zh-CN&q=%E6%B9%98%E9%8B%BC%E4%B8%AD%E5%9C%8B%E9%A6%99%E7%85%99&btnG=Google+%E6%90%9C%E7%B4%A2&aq=f&oq=";
		System.out.println(RemoteClient.getKeyword(url));
		
		url = "http://www.alltheweb.com/search?cat=web&cs=utf8&q=%E5%A4%A9%E6%B6%A6&rys=0&itag=crv&_sb_lang=pref";
		System.out.println(RemoteClient.getWebSite(url));
		
		url = "http://search.cn.yahoo.com/search?p=呼叫中心";
		System.out.println(RemoteClient.getKeyword(url));
		
		url = "http://www.sogou.com/sohu?query=%BA%F4%BD%D0%D6%D0%D0%C4+%CD%F8%C9%CF400&sourceid=&_ast=1275030656&_asf=www.sogou.com&w=01029901&num=10&p=01040100&dp=1";
		System.out.println(RemoteClient.getKeyword(url));
		
		url = "http://cn.bing.com/search?q=%E5%91%BC%E5%8F%AB%E4%B8%AD%E5%BF%83&go=&form=QBLH&filt=all&qs=n&sk=&sc=3-4";
		System.out.println(RemoteClient.getWebSite(url));
		
		url = "www.search.soso.google.cn";
		System.out.println(RemoteClient.getSearchEngine(url));
	}*/
	
	public static String getWebSite(String refer){
		String websiteReg = "(?:http:\\/\\/)([^\\/]*)";
		Pattern websitePatt = Pattern.compile(websiteReg);
		Matcher websiteMat = websitePatt.matcher(refer);
		StringBuffer website = new StringBuffer();
		while(websiteMat.find()){
			websiteMat.appendReplacement(website, "$1");
		}
		return website.toString();
	}
	
	/*public static String getSearchEngine(String referWebsite){
		String searchEngine = "";
		String[] websiteSplit = referWebsite.split("\\.");
		String[] searchEngineArr = Const.WEB400_VISITOR_SEARCH_ENGINE_ARRAY;
		for (int i = 0; i < websiteSplit.length; i++) {
			for (int j = 0; j < searchEngineArr.length; j++) {
				if (websiteSplit[i].equalsIgnoreCase(searchEngineArr[j])) {
					searchEngine = searchEngineArr[j];
					break;
				}
			}
			
		}
		return searchEngine;
	}*/
	
	public static String getKeyword(String refer) {
		String keywordReg = "(?:yahoo.+?[\\?|&]p=" +
				"|google.+?q=" +
				"|baidu.+?wd=" +
				"|soso.+?w=" +
				"|youdao.+?q=" +
				"|zhongsou.+?w=" +
				"|sogou.+?query=" +
				"|alltheweb.+?q="+
				"|bing.+?q=)" +
				"([^&]*)";
		String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
		Pattern keywordPatt = Pattern.compile(keywordReg);
		Matcher keywordMat = keywordPatt.matcher(refer);
		String keywords = "";
		while (keywordMat.find()) {
			keywords = keywordMat.group(1);
		}
//		System.out.println(keyword);
		if (!keywords.equals("")) {
			Pattern encodePatt = Pattern.compile(encodeReg);
			String unescapeString = RemoteClient.unescape(keywords);
			Matcher encodeMat = encodePatt.matcher(unescapeString);
			String encodeString = "gbk";
			if (encodeMat.matches())
				encodeString = "utf-8";
			try {
				return URLDecoder.decode(keywords, encodeString);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return "";
	}

	private static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
}
