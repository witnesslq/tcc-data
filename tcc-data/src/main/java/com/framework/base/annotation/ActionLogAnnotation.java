package com.framework.base.annotation;

import java.lang.annotation.*;

/**
 * 记录操作日志的注解。
 * 使用这个注解的action函数，如果前台调用了这个函数，将记录操作员进行的动作。其中两个信息将被提取。
 * <li>type - 当前操作来自哪里，前台或后台。</li>
 * <li>object - 当前操作员的操作对象，一般为所操作记录的id。</li>
 * <li>comment - 当前操作员进行的业务动作，比如"添加权限"这样具有业务意义的字符串。</li>
 * <li>params - 当前业务操作的参数，比如"parama,paramb"，那么日志记录器会从request中获取这两个参数，记录下来。</li>
 *<p>
 * 文件名： ActionLogAnnotation.java
 *<p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD.  All rights reserved.
 * @author 周营昭
 * @since 1.0
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLogAnnotation {

    /**
     * @return
     */
    String type() default "";

    /**
     * @return
     */
    String object();

    /**
     * 操作动作
     * @return
     *//*
    String action();

    *//**
     * 操作模块
     * @return
     *//*
    String module();*/

    /**
     * 自定义操作描述
     * @return
     */
    String comment();

    /**
     * 操作描述中替换的参数
     * @return
     */
    String params() default "";
}
