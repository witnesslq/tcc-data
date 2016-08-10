package com.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 处理HttpRequest数据.
 *<p>
 * 文件名： RandomGenerator.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
public class RequestUtil {

	private transient static Log log = LogFactory.getLog(RequestUtil.class);
	private HttpServletRequest request;

	protected RequestUtil() {
	}

	public RequestUtil(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 设置一个cookie，cookie的有效时间为30天。
	 * 
	 * @param response 当前设置的HttpServletResponse。
	 * @param name 要设置的cookie名称。
	 * @param value 要设置的cookie值。
	 * @param path 要设置的cookie路径。
	 */
	public static void setCookie(HttpServletResponse response, String name,
									String value, String path) {
		if (log.isDebugEnabled()) {
			log.debug("Setting cookie '" + name + "' on path '" + path + "'");
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days

		response.addCookie(cookie);
	}

	/**
	 * 获取名称为参数name指定的cookie值。
	 * 
	 * @param request 当前HttpServletRequest。
	 * @param name 要找的cookie名称。
	 * 
	 * @return 如果找到则返回cookie，否则返回null。
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];

			if (thisCookie.getName().equals(name)) {
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;
					break;
				}
			}
		}

		return returnCookie;
	}

	/**
	 * 删除参数name标示的cookie。
	 * 
	 * @param response 当前的HttpServletResponse。
	 * @param cookie 要删除的cookie。
	 * @param path cookie的路径。
	 */
	public static void deleteCookie(HttpServletResponse response, 
									Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * 获取当前应用的URL。
	 * 
	 * @param request 当前应用中的HttpServletRequest。
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的参数值，如果参数不存在则返回参数defaultValue指定的缺省值。
	 * 
	 * @param name 参数名
	 * @param defaultValue 缺省值
	 */
	public String getString(String name, String defaultValue) {
		String tmp = null;
		tmp = request.getParameter(name);
		if (tmp != null && tmp.compareTo("") != 0) {
			return tmp;
		}else{
			tmp = (String)request.getAttribute(name);
			if (tmp != null && tmp.compareTo("") != 0) {
				return tmp.trim();
			}else{
				return defaultValue;
			}
		}
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的参数值，如果参数不存在返回null。
	 * 
	 * @param name 参数名
	 */
	public String getString(String name) {
		String tmp = null;
		tmp = request.getParameter(name);
		if (tmp != null && tmp.compareTo("") != 0) {
			return tmp.trim();
		}else{
			tmp = (String)request.getAttribute(name);
			if (tmp != null && tmp.compareTo("") != 0) {
				return tmp.trim();
			}else{
				return null;
			}
		}
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的整数值，如果参数不存在则返回参数defaultValue指定的缺省值。
	 * 
	 * @param name 参数名
	 * @param defaultValue 缺省值
	 * @return
	 */
	public int getInt(String name, int defaultValue) {
		int result = -1;
		try {
			result = Integer.parseInt(request.getParameter(name));
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的整数值，如果参数不存在则返回-1。
	 * 
	 * @param name 参数名
	 */
	public int getInt(String name) {
		int result = -1;
		try {
			result = Integer.parseInt(request.getParameter(name));
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的Byte类型数值，如果参数不存在则返回参数defaultValue指定的缺省值。
	 * 
	 * @param name 参数名
	 * @param defaultValue 缺省值
	 * @return
	 */
	public byte getByte(String name) {
		byte result = -1;
		try {
			result = Byte.parseByte(request.getParameter(name));
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的Byte类型数值，如果参数不存在则返回-1。
	 * 
	 * @param name 参数名
	 */
	public byte getByte(String name, byte defaultValue) {
		byte result = -1;
		try {
			result = Byte.parseByte(request.getParameter(name));
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的浮点值，如果参数不存在则返回参数defaultValue指定的缺省值。
	 * 
	 * @param name 参数名
	 * @param defaultValue 缺省值
	 * @return
	 */
	public float getFloat(String name, float defaultValue) {
		float result = -1;
		try {
			result = Float.parseFloat(request.getParameter(name));
		} catch (Exception e) {
			result = defaultValue;
		};
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的浮点值，如果参数不存在则返回-1。
	 * 
	 * @param name 参数名
	 */
	public float getFloat(String name) {
		float result = -1;
		try {
			result = Float.parseFloat(request.getParameter(name));
		} catch (Exception e) {
		};
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的Double型数值，如果参数不存在则返回参数defaultValue指定的缺省值。
	 * 
	 * @param name 参数名
	 * @param defaultValue 缺省值
	 * @return
	 */
	public double getDouble(String name, double defaultValue) {
		double result = defaultValue;
		try {
			result = Double.parseDouble(request.getParameter(name));
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的Double型数值，如果参数不存在则返回-1。
	 * 
	 * @param name 参数名
	 */
	public double getDouble(String name) {
		double result = -1;
		try {
			result = Double.parseDouble(request.getParameter(name));
		} catch (Exception e) {
		};
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的长整形数值，如果参数不存在则返回参数defaultValue指定的缺省值。
	 * 
	 * @param name 参数名
	 * @param defaultValue 缺省值
	 * @return
	 */
	public long getLong(String name, long defaultValue) {
		long result = -1;
		try {
			result = Long.parseLong(request.getParameter(name));
		} catch (Exception e) {
			result = defaultValue;
		};
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的长整形数值，如果参数不存在则返回-1。
	 * 
	 * @param name 参数名
	 */
	public long getLong(String name) {
		long result = -1;
		try {
			result = Long.parseLong(request.getParameter(name));
		} catch (Exception e) {
		};
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的参数数组。
	 * 
	 * @param name 参数名
	 * @return
	 */
	public String[] getStringArray(String name) {
		String[] result = request.getParameterValues(name);
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的参数值，并使用参数delim指定的分隔符将参数值切分为字符串数组返回。
	 * 
	 * @param name 参数名
	 * @param delim 分割符
	 * @return
	 */
	public String[] getStringArray(String name, String delim) {
		List<String> tmp = new ArrayList<String>();
		if (request.getParameter(name) != null) {
			Enumeration enumeration = new StringTokenizer(request.getParameter(name), delim);
			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					tmp.add((String) enumeration.nextElement());
				}
			}
		}
		String[] result = new String[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			result[i] = (String) tmp.get(i);
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到int数组，由同名参数的值组成.
	 * 
	 * @param name 参数名。
	 * @return
	 */
	public int[] getIntArray(String name) {
		int[] result = new int[0];
		String[] s = request.getParameterValues(name);
		if (s != null) {
			result = new int[s.length];
			for (int i = 0; i < s.length; i++) {
				try {
					result[i] = Integer.parseInt(s[i]);
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到Integer数组，由同名参数的值组成.
	 * 
	 * @param name 参数名。
	 * @return
	 */
	public Integer[] getIntegerArray(String name) {
		Integer[] result = new Integer[0];
		String[] s = request.getParameterValues(name);
		if (s != null) {
			result = new Integer[s.length];
			for (int i = 0; i < s.length; i++) {
				try {
					result[i] = Integer.valueOf(Integer.parseInt(s[i]));
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中获取参数name指定的参数值，并使用参数delim指定的分隔符将参数值切分为字符串，
	 * 将每个字符串再解析成整数后返回。
	 * 
	 * @param name 参数名
	 * @param delim 分割符
	 * @return 
	 */
	public int[] getIntArray(String name, String delim) {
		Vector tmp = new Vector();
		Enumeration enumeration = new StringTokenizer(request
				.getParameter(name), delim);
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				tmp.addElement((String) enumeration.nextElement());
			}
		}
		int[] result = new int[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			try {
				result[i] = Integer.parseInt((String) tmp.elementAt(i));
			} catch (Exception e) {
				result[i] = -1;
			};
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到long数组。
	 * <p>
	 * 数祖由同名参数的值组成， 例如将request中名为"longArry"的值转化成数组：
	 * <code><pre>
	 * 	RequestUtil req = new RequestUtil(request);
	 * 	req.getLongArray("longArry");
	 * </pre></code>
	 * 
	 * <br>
	 * 
	 * 这样的代码类似于：<br>
	 * <code><pre>
	 * 	String[] tmp = request.getParameterValues("longArry");
	 * 	long[] result = new long[tmp.length()];
	 * 	for (int i=0; i &lt; tmp.length; i++) {
	 * 		try { result[i] = Long.parseLong(tmp[i]);}catch(Exception e){};
	 * 	}
	 * </pre></code>
	 * 
	 * @param name 参数名
	 * @return
	 */
	public long[] getLongArray(String name) {
		long[] result = new long[0];
		String[] s = request.getParameterValues(name);
		if (s != null) {
			result = new long[s.length];
			for (int i = 0; i < s.length; i++) {
				try {
					result[i] = Long.parseLong(s[i]);
				} catch (Exception e) {
				};
			}
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到Long数组。
	 * @param name
	 * @return Long[]
	 * 
	 * @see com.framework.common.util.RequestUtil#getLongArray(String)
	 */
	public Long[] getLongWrapperArray(String name) {
		Long[] result = new Long[0];
		String[] s = request.getParameterValues(name);
		if (s != null) {
			result = new Long[s.length];
			for (int i = 0; i < s.length; i++) {
				try {
					result[i] = Long.valueOf(s[i]);
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到long数组。数祖由分隔符号将获取到的参数值分隔而成。
	 * <p>
	 * 例如将request中名为"longArry"的字符串"12,23,23,23"转化成数组[12, 23, 23, 23]：
	 * <code></pre>
	 * RequestUtil req = new RequestUtil(request);<br>
	 * req.getLongArray("longArry", ",");
	 * </pre></code>
	 * 
	 * @param name 参数名
	 * @param delim 分割符
	 * @return
	 */
	public long[] getLongArray(String name, String delim) {
		Vector tmp = new Vector();
		Enumeration enumeration = new StringTokenizer(request
				.getParameter(name), delim);
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				tmp.addElement((String) enumeration.nextElement());
			}
		}
		long[] result = new long[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			try {
				result[i] = Long.parseLong((String) tmp.elementAt(i));
			} catch (Exception e) {
			};
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到float数组。数组由同名参数的值组成。
	 * 
	 * @param name 参数名
	 * @return
	 * @see com.framework.common.util.RequestUtil#getLongArray(String)
	 */
	public float[] getFloatArray(String name) {
		float[] result = new float[0];
		String[] s = request.getParameterValues(name);
		if (s != null) {
			result = new float[s.length];
			for (int i = 0; i < s.length; i++) {
				try {
					result[i] = Float.parseFloat(s[i]);
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中得到name指定的参数值，然后将参数值由分隔符号分隔而成数组返回。
	 * 
	 * @param name 参数名
	 * @param delim 分割符
	 * @return
	 * @see com.framework.common.util.RequestUtil#getLongArray(String, String)
	 */
	public float[] getFloatArray(String name, String delim) {
		Vector tmp = new Vector();
		Enumeration enumeration = new StringTokenizer(request
				.getParameter(name), delim);
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				tmp.addElement((String) enumeration.nextElement());
			}
		}
		float[] result = new float[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			try {
				result[i] = Float.parseFloat((String) tmp.elementAt(i));
			} catch (Exception e) {
				result[i] = -1;
			};
		}
		return result;
	}

	/**
	 * 从HttpServletRequest中根据年月日的参数构建得到Date对象。<br>
	 * 返回值由request中的year、month、day三个参数值组成。
	 * 
	 * @param year 年的参数名。
	 * @param month 月的参数名。
	 * @param day 日的参数名。
	 * @return
	 */
	public Date getDate(String year, String month, String day) {
		try {
			Calendar cl = new GregorianCalendar(getInt(year), getInt(month),
					getInt(day));
			return cl.getTime();
		} catch (Exception e) {
		}
		return new Date();
	}

	/**
	 * 从request中获取参数，将参数解析成date对象返回。
	 * 如果name对应的参数值为空或者无法解析成日期，则返回null。
	 * 
	 * @param name 参数名称。
	 * @param format 格式字符串，{@link DateUtil#FMT_DATE_YYYY_MM_DD}等。
	 * @return
	 */
	public Date getDate(String name, String format) {
		Date date = new Date();
		try {
			date = DateUtil.parse(request.getParameter(name), format);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 从request中获取参数，将参数解析成date对象返回，如果参数值为空或者无法解析成日期，则返回指定的默认值。
	 * 
	 * @param name 参数名称。
	 * @param defaultDate 默认值。
	 * @param format 格式字符串，{@link DateUtil#FMT_DATE_YYYY_MM_DD}等。
	 * @return
	 */
	public Date getDate(String name, Date defaultDate, String format) {
		String strDate = request.getParameter(name);
		try {
			if (strDate != null) {
				return DateUtil.parse(strDate, format);
			} 
		} catch (Exception e) {
		}
		return defaultDate;
	}

	/**
	 * 从request中获取参数，将参数解析成Timestamp对象返回，如果参数值为空或者无法解析成日期，则返回指定的默认值。
	 * 
	 * @param name 参数名称。
	 * @param defaultTime 默认值。
	 * @param format 格式字符串，{@link DateUtil#FMT_DATE_YYYY_MM_DD}等。
	 * @return
	 * @see com.framework.common.util.RequestUtil#getDate(String, java.util.Date, String)
	 */
	public Timestamp getTimestamp(String name, Timestamp defaultTime, String format) {
		try {
			Date date = this.getDate(name, format);
			if (date != null) {
				return new Timestamp(date.getTime()); 
			}
			
		} catch (Exception e) {
		}
		return defaultTime;
	}

	/**
	 * 从request中获取参数，将参数解析成Timestamp对象返回。
	 * 如果name对应的参数值为空或者无法解析成日期，则返回null。
	 * 
	 * @param name 参数名称。
	 * @param format 格式字符串，{@link DateUtil#FMT_DATE_YYYY_MM_DD}等。
	 * @return
	 */
	public Timestamp getTimestamp(String name, String format) {
		try {
			Date date = this.getDate(name, format);
			if (date != null) {
				return new Timestamp(date.getTime()); 
			}
		} catch (Exception e) {
		}
		
		return null;
	}

	/**
	 * 从HttpServletRequest中得到boolean值。
	 * 如果参数不存在或者为空字符串返回false，否则返回true。
	 * 
	 * @param name 参数名
	 */
	public static boolean getBooleanParameter(HttpServletRequest request,
			String name) {
		String tmp = null;
		tmp = request.getParameter(name);
		if (tmp != null && tmp.compareTo("") != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将指定值与HttpServletRequest中的参数值相比较，以boolean的形式返回。
	 * 
	 * @param name 参数名
	 * @param compareValue 比较值
	 */
	public boolean getBoolean(String name, String comparedValue) {
		String s = request.getParameter(name);
		if (s != null && s.equals(comparedValue)) {
			return true;
		} else {
			return false;
		}
	}


	public String getEncodeUrl() {
		String uri = request.getServletPath().toString();
		String query = request.getQueryString();
		if (query != null && query.length() > 0)
			uri += "?" + query;
		try {
			return java.net.URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return uri;
		}
	}

	/**
	 * 获得相对完整URL地址
	 */
	public String getRelativeFullURL() {
		String uri = request.getServletPath();
		String query = request.getQueryString();
		if (query != null && query.length() > 0)
			uri += "?" + query;
		return uri;
	}

	/**
	 * 将当前请求的URL进行url编码。
	 * 如果参数isEncode为true，则对当前的URL进行编码，否则直接返回URL。
	 * 
	 * @param isEncoode 是否编码的标志。
	 * @return
	 */
	public String handleUrl(boolean isEncode) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		if (query != null && query.length() > 0)
			uri += "?" + query;
		if (!isEncode) {
			return uri;
		}
		try {
			return java.net.URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return uri;
		}
	}

	/**
	 * 将一个字符串进行编码。
	 * 例如中国被编码为"%D6%D0%B9%FA"。
	 * 
	 * @param s 源文。
	 * @return 编码后的码文。
	 */
	public static String encodeUrl(String s) {
		try {
			return java.net.URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return s;
		}
	}

	/**
	 * 将一个字符串进行解码。
	 * 
	 * @param s 源文。
	 * @return 编码后的码文。
	 * @see com.framework.common.util.RequestUtil#encodeUrl(String)
	 */
	public static String decodeUrl(String url) {
		try {
			return java.net.URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return url;
		}
	}

	/**
	 * 获得绝对完整URL地址，包括参数。<br />
	 * 例如http://localhost:8080/youapp/fun/a.jsp?param=pvalue
	 */
	public String getAbsoluteFullURL() {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		url.append(request.getServletPath().toString());
		String query = request.getQueryString();
		if (query != null && query.length() > 0) {
			url.append("?" + query);
		}
		return url.toString();

	}
	
	/**
	 *  判断请求是否是Ajax请求。
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			return true;
		}
		
		return false;
	}

}
