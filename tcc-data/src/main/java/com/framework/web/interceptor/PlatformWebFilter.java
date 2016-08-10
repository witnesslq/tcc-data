package com.framework.web.interceptor;

import com.framework.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-9 19:28
 * Email:johnny_lx@yahoo.com
 */
public class PlatformWebFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(PlatformWebFilter.class);

    private String[] skipPath;

    /**
     * 是否在滤过路径中
     *
     * @param path 当前url路径
     * @return true:在过滤路径中 false:不在过滤路径中
     */
    private boolean inSkipPath(String path) {
        for (int i = 0; i < skipPath.length; i++) {
            if (StringUtil.contains(path, skipPath[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.skipPath = filterConfig.getInitParameter("skipPath").split(",");
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String currPath = request.getRequestURI();    //当前请求的URL
        String paramStr = request.getQueryString() == null ? "" : "?" + request.getQueryString();    //请求参数


            if(currPath.endsWith("/help")||currPath.endsWith("/help/")){
                response.sendRedirect("/help/index.html");
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

    }

    @Override
    public void destroy() {

    }
}
