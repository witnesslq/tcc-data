package com.framework.spring.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;


/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-7-14
 * Time: 20:11
 * Email:johnny_lx@yahoo.com
 */
public class PlatformContextListener extends ContextLoaderListener {

    private static final Logger log = LoggerFactory.getLogger(PlatformContextListener.class);
    private static ApplicationContext ctx = null;
    public void contextInitialized(ServletContextEvent event){
        log.info("......系统启动......");
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        ctx =WebApplicationContextUtils.getRequiredWebApplicationContext(context);

    }

    /**
     * 获取spring的ApplicationContext。
     * @return Spring的ApplicationContext.
     */
    public static ApplicationContext getContext(){
        return ctx;
    }
}
