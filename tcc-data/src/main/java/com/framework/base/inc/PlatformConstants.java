package com.framework.base.inc;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-25 13:20
 * Email:johnny_lx@yahoo.com
 */
public class PlatformConstants {
	
	/**
	 * 系统定义默认空字符串
	 */
    public static final String DEFAULT_EMPTY_STR = "";
    
    /**
     * 系统定义默认空数值
     */
    public static final Integer DEFAULT_EMPTY_NUM = 0;
    
    /**
     * 定义apach-tomcat路径
     */
    public static String apacheTomcatPath;
    /**
     * 定义ckfinder上传路径
     */
    public static String ckinderUploadPath;

    /**
     * 保存cookie时的cookieName
     */
    public static String cookieDomainName;

    /**
     * 设置cookie有效期是两个星期，根据需要自定义
     */
    public static String cookieMaxAge;

    /**
     * 加密cookie时的平台自定码
     */
    public static String webKey;

    /**
     * http client socketTimeout
     */
    public static String socketTimeout;

    /**
     * http client connectTimeout
     */
    public static String connectTimeout;

    /***
     * SessionUser
     */
    public static String defaultSessionName;
    
    /**
     * websocket登录用户名
     * */
    public static String webSocketUserName;


    public static String ctiLinkConfApiHTTPUrl;

    public void setApacheTomcatPath(String apacheTomcatPath) {
        PlatformConstants.apacheTomcatPath = apacheTomcatPath;
    }

    public void setCkinderUploadPath(String ckinderUploadPath) {
        PlatformConstants.ckinderUploadPath = ckinderUploadPath;
    }

    public void setCookieDomainName(String cookieDomainName) {
        PlatformConstants.cookieDomainName = cookieDomainName;
    }

    public void setCookieMaxAge(String cookieMaxAge) {
        PlatformConstants.cookieMaxAge = cookieMaxAge;
    }

    public void setWebKey(String webKey) {
        PlatformConstants.webKey = webKey;
    }

    public void setSocketTimeout(String socketTimeout) {
        PlatformConstants.socketTimeout = socketTimeout;
    }

    public void setConnectTimeout(String connectTimeout) {
        PlatformConstants.connectTimeout = connectTimeout;
    }

    public static void setWebSocketUserName(String webSocketUserName) {
        PlatformConstants.webSocketUserName = webSocketUserName;
    }

    public static void setDefaultSessionName(String defaultSessionName) {
        PlatformConstants.defaultSessionName = defaultSessionName;
    }

    public void setCtiLinkConfApiHTTPUrl(String ctiLinkConfApiHTTPUrl) {
        PlatformConstants.ctiLinkConfApiHTTPUrl = ctiLinkConfApiHTTPUrl;
    }
}
