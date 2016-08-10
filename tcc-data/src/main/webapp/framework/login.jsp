<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>
<html>
<head>
    <title>TCC-CONF - 天润融通</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/framework/stylesheet/logoin.css" />
    <style type="text/css">
    	ul {list-style: none;}
		.error {
		    display: inline;
		    float: left;
		    height: 29px;
		    margin: 7px 0 0;
		    padding: 0 0 0 25px;
		    width: 193px;
		}
		.businessInput {
		    background: url("${ctx}/images/inButBg.gif") repeat-x scroll 0 0 #4f8ed5;
		    border: 1px solid #4f8ed5;
		    color: #fff;
		    cursor: pointer;
		    height: 26px;
		    float:right;
		    line-height: 25px;
		    overflow: visible;
		    padding: 0 8px;
		}
    </style>
    <script type="text/javascript">
    	function showBusinessPage(){
	    	window.location="/framework/index.jsp";
    	}
	</script>
</head>
<body style="height: 100%;">
<div class="wrapper">
    <div class="innerWrapper">
        <!--body-->
        <div class="bodyWrapper">
            <div class="innerBody">

                <div class="contentWrapper">
                    <div class="innerContent">
						
                        <div class="contHeader">
                            <img src="${ctx}/framework/images/logo_reg.png" alt="天润托管座席监控中心" /><span></span>
                        </div>
                        <div class="contBody">
                                <form action="${ctx}/tcc/conf/v10/system/login" method="post">
                            <ul>
                                <li>
                                    <b>用户帐号:</b>
                                    <div class="un">
                                        <input id="username" name="username" type="text" maxlength="20" />
                                    </div>
                                </li>
                                <li>
                                    <b>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</b>
                                    <div class="pw">
                                        <input id="password" name="pwd" type="password" maxlength="20" />
                                    </div>
                                </li>

<!--                                     <li> -->
<!--                                         <b>验证码:</b> -->
<!--                                         <input type="text" id="securityCode" name="securityCode" class="yan" maxlength="4"/> -->
<!--                                         <img id="securityCodeImg" src="" width="80" height="25" style="margin: 10px 0 0 10px;" /> -->
<%--                                         <% --%>
<!-- //                                             // 验证码图片的宽和高要和SecurityCodeUtil中的常量保持一致。 -->
<%--                                         %> --%>
<!--                                         <input type="hidden" name="username" id="userNameToSubmit" /> -->
<!--                                         <input type="hidden" name="authCode" id="authCode" /> -->
<!--                                     </li> -->

                                    <li>
                                        <b>&nbsp;</b>
                                        <input type="submit" class="but" value="登录" />
                                        <input type="reset" class="but" value="重置" />
                                    </li>
                                    <li>
                                    	<b>&nbsp;&nbsp;</b>
		                                <div class="error"><span class="loginErrer" id="loginError"><font style="font-weight:600">${errorCode}</font></span></div>
                                    </li>
<!--                                     <li> -->
<!--                                         <b>&nbsp;</b> -->
<%--                                         <span class="red">${requestScope.username}${requestScope.securityCode }</span> --%>
<!--                                     </li> -->
                            </ul>
                                </form>

                        </div><!--end of contBody -->

                    </div><!--end of innerContent -->

                </div><!--end of contentWrapper -->

                <div class="footerBg"></div>

            </div><!--end of innerBody -->
        </div><!--end of bodyWrapper -->
       <%-- <button class="businessInput" onclick="javascript:showBusinessPage();">企业用户登录入口</button>--%>
        <!--body-->
    </div><!--end of innerWrapper -->
</div><!--end of wrapper -->
</body>

</html>
