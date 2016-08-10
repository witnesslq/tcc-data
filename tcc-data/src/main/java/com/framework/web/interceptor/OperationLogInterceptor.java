//package com.framework.web.interceptor;
//
//import com.framework.spring.annotation.OperationLogAnnotation;
//import com.tinet.ccic.v3.app.common.inc.Constants;
//import com.tinet.ccic.v3.app.common.page.LoginEntity;
//import com.tinet.ccic.v3.app.log.model.OperationLogPojo;
//import com.tinet.ccic.v3.app.log.service.OperationLogService;
//import com.tinet.ccic.v3.app.log.util.SaveLogTask;
//
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import java.lang.reflect.Method;
//import java.util.Map;
//
///**
// * <p>
// * 平台操作日志拦截器，拦截controller中带有OperationLogAnnotation注解的方法。
// * 进行操作入库
// * </p>
// *
// * @author zyl
// * @version 1.0
// * @see com.framework.spring.annotation.OperationLogAnnotation
// * @since 2015-01-27 15:00
// */
//public class OperationLogInterceptor implements MethodInterceptor {
//    private static Logger logger = LoggerFactory.getLogger(OperationLogInterceptor.class);
//
//    @Autowired
//    private OperationLogService operationLogService;
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        Object returnObj = null;
//        try {
//            returnObj = invocation.proceed();
//        } catch (Throwable t) {
//            logger.error("拦截controller出错");
//            t.printStackTrace();
//            throw t;
//        } finally {
//            Method method = invocation.getMethod();
//            OperationLogAnnotation annotation = null;
//            if (method != null) {
//                annotation = method.getAnnotation(OperationLogAnnotation.class);
//            }
//            if (annotation != null) {
//                final OperationLogAnnotation finalAnnotation = annotation;
//
//                HttpServletRequest request = null;
//                Object[] args = invocation.getArguments();
//                if (args.length != 0 && HttpServletRequest.class.isInstance(args[0])) {
//                	request = (HttpServletRequest) args[0];
//                }
//                if (request != null) {
////                    HttpSession session = request.getSession();
//                	SaveLogTask saveLogTask = new SaveLogTask(request, finalAnnotation);
//                	new Thread(saveLogTask).start();
//                }
//
//            }
//        }
//        return returnObj;
//    }
//
//    @SuppressWarnings("unused")
//	private void recordLogInfo(HttpServletRequest request, OperationLogAnnotation annotation) {
//        logger.debug("开启新线程进行操作日志保存");
//        String action = annotation.action();
//        String comment = annotation.comment();
//        String module = annotation.module();
//        String[] params = annotation.params();
//        for (int i = 0; i < params.length; i++) {
//            comment = StringUtils.replaceOnce(comment, "?", request.getParameter(params[i]));
//        }
//        HttpSession session = request.getSession();
//        LoginEntity loginEntity = (LoginEntity) session.getAttribute(Constants.LOGINED_KEY);
//        OperationLogPojo operationLogPojo = new OperationLogPojo();
//        operationLogPojo.setAction(action);
//        operationLogPojo.setComment(comment);
//        operationLogPojo.setModule(module);
//        operationLogPojo.setOperatorId(loginEntity.getUser().getId());
//        operationLogPojo.setOperatorName(loginEntity.getUser().getName());
//        operationLogService.save(operationLogPojo);
//    }
//
//}
