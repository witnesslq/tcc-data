package com.framework.spring.annotation;

import java.lang.annotation.*;

/**
 * <p>平台操作日志注解，此注解加在要进行记录的controller方法上。</p>
 *
 * @author zyl
 * @version 1.0
 * @see com.framework.web.interceptor
 * @since 2015-01-27 15:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogAnnotation {
    /**
     * 操作动作
     * @return
     */
    String action();

    /**
     * 操作模块
     * @return
     */
    //操作模块
    String module();

    //描述

    /**
     * 自定义操作描述
     * @return
     */
    String comment();

    /**
     * 操作描述中替换的参数
     * @return
     */
    String[] params();
}
