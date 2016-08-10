//package com.framework.base.interceptor;
//
//import com.framework.base.annotation.ActionLogAnnotation;
//import com.framework.common.util.StringUtil;
//import com.tinet.tcc.app.common.model.LoginEntity;
//import com.tinet.tcc.app.common.util.Constants;
//import com.tinet.tcc.app.log.model.TccConfActionLog;
//import com.tinet.tcc.app.log.service.TccConfActionLogService;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.FlashMap;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.HandlerMapping;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.support.RequestContextUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 操作日志处理拦截器
// * @author yangjun
// */
//public class ActionLogInterceptor implements HandlerInterceptor{
//
//    private static Logger logger = LoggerFactory.getLogger(ActionLogInterceptor.class);
//
//    @Autowired
//    private TccConfActionLogService tccConfActionLogService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//        if(o instanceof HandlerMethod){
//            //获取HandlerMethod准备参数
//            HandlerMethod handlerMethod = (HandlerMethod) o;
//            if (null != handlerMethod) {
//                final HttpServletRequest request = httpServletRequest;
//                final Method method = handlerMethod.getMethod();
//                final ActionLogAnnotation annotation = method.getAnnotation(ActionLogAnnotation.class);
//                if(null != annotation){
//                    new Thread(
//                            new Runnable(){
//                                @Override
//                                public void run() {
//                                    recordLogInfo(request, annotation, method);
//                                }
//                            }
//                    ).start();
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 记录日志信息
//     * @param request
//     * @param annotation
//     */
//    @SuppressWarnings("unused")
//    private void recordLogInfo(HttpServletRequest request, ActionLogAnnotation annotation,Method method) {
//
//        logger.debug("开启新线程进行操作日志保存");
//        String object = annotation.object();
//        String comment = annotation.comment();
//        String params = annotation.params();
//        String type = annotation.type();
//        int result = Constants.LOG_LOGIN_RESULT_SUCCESS;  // 默认执行成功
//        //获取RedirectAttributes redirectAttributes中的errorMessage,来判断方法是否成功执行完成。
//        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
//        if(null != flashMap && !flashMap.isEmpty()){
//            if(null != flashMap.get("errorMessage")){
//                result = Constants.LOG_LOGIN_RESULT_FAILURE;    // controller操作执行失败
//            }
//        }
//
//        logger.debug("annotion:object=[{}],comment=[{}],params=[{}]", new String[]{object, comment, params});
//
//        TccConfActionLog logAction = new TccConfActionLog();
//        try {
//            String[] arrParams = null;
//            if (params.length() > 0) {
//                arrParams = params.split(",");
//            } else {
//                arrParams = new String[]{};
//            }
//
//            for (int i = 0; i < arrParams.length; i++) {
//                comment = StringUtils.replaceOnce(comment, "?", this.getParamValue(request, arrParams[i]));
//            }
//            object = this.getParamValue(request, object);
//            LoginEntity logined = (LoginEntity)request.getSession().getAttribute(Constants.LOGINED_KEY);; // 后台操作
//            if (logined != null) {
//                logAction.setObject(object);
//                logAction.setComment(comment);
//                logAction.setOperator(logined.getOperator());
//                logAction.setOperateType(method.getDeclaringClass().getName() + "." + method.getName());
//                System.out.println(method.getDeclaringClass().getName() + "." + method.getName());
//                logAction.setOperateTime(new Date());
//                logAction.setResult(result);
//                //还有个类名与返回值没有搞定啊。
//                System.out.println(method.getDeclaringClass().getName());
//                System.out.println(method.getName());
//                tccConfActionLogService.saveActionLog(logAction);
//            }
//        }  catch (Exception e) {
//            logger.error("something error when record actionlog: {}", logAction.toString()+" --- the action return result is:");
//        }
//
//    }
//
//
//    /**
//     * 解析form表单数据
//     * @param request
//     * @param param 操作日志要记录的执行操作时提交的参数。
//     * @return 返回操作对象
//     */
//    private String getParamValue(HttpServletRequest request, String param) {
//        String paramValue = "";
//        if (param.indexOf(".") != -1) {
//            //后续补充进行处理...
//            String scope = StringUtils.substringBefore(param, ".");
//            String remain = StringUtils.substringAfter(param, ".");
//            String theone = StringUtils.substringBefore(remain, ".");
//            remain = StringUtils.substringAfter(remain, ".");
//
//            Map<String,Object> vs = new HashMap<String,Object>();
//            if ("session".equals(scope)) {
//            } else if ("request".equals(scope)) {
//            } else {
//                try {
//                    Map<String,String[]> mapParams = request.getParameterMap();
//                    String[] pvobject = (String[])mapParams.get(param);
//                    paramValue=pvobject != null && pvobject.length > 0
//                            ? (pvobject.length > 1 ? ArrayUtils.toString(pvobject) : pvobject[0])
//                            : "";
//                } catch (Exception e) {
//                    return "";
//                }
//            }
//            return paramValue;
//        } else {
//            //直接获取所有参数，返回Map
//            Map<String,String[]> mapParams = request.getParameterMap();
//            String[] pvobject = (String[])mapParams.get(param);
//            if(pvobject != null && pvobject.length > 0){
//                paramValue = pvobject.length > 1 ? ArrayUtils.toString(pvobject) : pvobject[0];
//            }
//            //获取@PathVariable的值
//            if(StringUtil.isNull(paramValue)){
//                Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//                if(null != pathVariables && !pathVariables.isEmpty()){
//                    paramValue = (String)pathVariables.get(param);
//                }
//            }
//            return paramValue;
//        }
//    }
//}
