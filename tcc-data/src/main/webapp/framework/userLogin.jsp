<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/pages/common/common.jsp" %>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8;" >
	<link type="text/css" href="${ctx}/framework/stylesheet/home/unifyLogin.css" rel="stylesheet" />
	<title><%@ include file="/WEB-INF/pages/common/title.jsp" %></title>
	<script src="${ctx}/framework/scripts/js/login.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/framework/scripts/js/pwdValidation.js"></script>
	<style type="text/css">
		html {
			background: #f1f1f1;
		}
	</style>
	<script type="text/javascript">

		$(function() {
			$.escapeHTML= function(str) {
				if(str!=undefined&&str!=null&&str.length>0){
					return str.replace(/&/g,'&amp;').replace(/>/g,'&gt;').replace(/</g,'&lt;').replace(/"/g,'&quot');
				}else{
					return "";
				}
			}

			/*$("#yzmImg").bind("click",function(){//重置验证码 haozh 2012/11/28 add
				$(this).attr("src","${ctx}/system!getLoginSecurityCodeImage.action?" + Math.random());
				$("#securityCode").focus();
			});*/


			/*if(Number(${wrongNum}) > 0){
				$("#securityCodeLi").removeAttr("style");
			} else {
				$("#securityCodeLi").attr("style","display:none");
			}*/

			//输出错误提示信息
			if(${not empty requestScope.username}){
				showErrorInfo("${requestScope.username}");
			}
			if(${not empty requestScope.securityCode}){
				showErrorInfo("${requestScope.securityCode}");
			}



			////登录焦点///////////////////////////////
			if(!$("#uniqueId_").val()){
				$("#uniqueId_").focus();
			}else if(!$("#username_").val()){
				$("#username_").focus();
			}else if(!$("#password").val()){
				$("#password").focus();
			}
			///////////////////////
		});
	</script>
</head>
<body>
<div class="loginCon">
	<div class="middleBar">
		<div class="logo"></div>
	</div>
	<div class="middleCon">
		<a class="xcPhoto"></a>
		<div class="tabCon">
			<a class="qzx">企业用户登录</a><a class="qgly" href="${ctx}/framework/sysLogin.jsp">管理员登录</a>
		</div>
		<div class="formCon">
			<form id="loginForm" action="${version}/system/business/login" method="post" onsubmit="return checkUserLogin('${wrongNum}');">
				<ul>
					<li style="color: red; padding: 5px 20px;" id="loginError"></li>
					<li><label>企业编号</label><input class="hz_code" id="uniqueId_" name="uniqueId_" type="text" maxlength="11" value="${uniqueIdFromCookie}"></li>
					<li><label>用户帐号</label><input class="hz_zxh" type="text" id="username_" name="username_" maxlength="20" value="${userNameFromCookie}"></li>
					<li><label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label><input class="hz_password" type="password" id="password" name="password"></li>
					<li id="securityCodeLi" style="display:none"><label>验&nbsp;&nbsp;证&nbsp;&nbsp;码</label><input class="hz_security" type="text" id="securityCode" name="securityCode" style="width:84px;" maxlength="4"><img src="${ctx}/system!getLoginSecurityCodeImage.action" alt="验证码" title="点击更新验证码" id="yzmImg">
						<input id="uniqueId" name="uniqueId" type="hidden" />
						<input id="username" name="username" type="hidden" />
						<input name="authCode" id="authCode" type="hidden" />
						<input type="hidden" id="reqSource" name="reqSource" value="1" />
					</li>
					<li><label></label><input type="submit" id="loginBtn" value="登录系统" class="green_btn">&nbsp;<input type="button" value="找回密码" id="btnFindPwd" class="hui_btn"></li>
				</ul>
			</form>
		</div>
		<div class="roundCornerCon"></div>
	</div>
	<div class="bgCloudy notice"><span class="nt">公告:</span><div id="notice" style="display:inline-block;"></div></div>
	<div class="foot">
		<div class="f1">
			<a class="hui" href="http://www.ti-net.com.cn/products/products!tostandard.action" target="_blank">产品介绍</a>|<a class="hui" href="http://www.ti-net.com.cn/service/service!toFAQ.action" target="_blank">常见问题</a>|<a class="hui" href="http://www.ti-net.com.cn/service/service!toPay.action" target="_blank">付款方式</a>|<a class="hui" href="http://www.ti-net.com.cn/contact/contact!total.action" target="_blank">关于我们</a>|<a class="hui" href="http://www.ti-net.com.cn/contact/contact!toContact.action" target="_blank">联系我们</a>
		</div>
		<div class="f2">
			全国呼叫中心运营许可证：B2-20070013 京ICP备06029233号 北京市公安局西城分局备案号：110102001636
			<br>
			版权所有 北京天润融通科技股份有限公司
		</div>
		<div class="wawa"></div>
	</div>
</div>
</body>
</html>