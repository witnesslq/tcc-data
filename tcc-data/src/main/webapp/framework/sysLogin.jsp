<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8;" >
	<%@ include file="/WEB-INF/pages/common/common.jsp" %>
	<title><%@ include file="/WEB-INF/pages/common/title.jsp" %></title>


	<link type="text/css" href="${ctx}/framework/stylesheet/home/unifyLogin.css" rel="stylesheet" />
	<script src="${ctx}/framework/scripts/js/login.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/framework/scripts/js/pwdValidation.js"></script>
	<style type="text/css">
		html {
			background: #f1f1f1;
		}
	</style>
	<script type="text/javascript">

	function pwdValid(msg){
		if(msg.pwd_valid_day=="-1"){
			//alert("您的密码已经到期，请立即修改密码。");
			$(".pwdmask").show();
			$(".pwdfloatWin .fwCont p").text("您的密码已经到期，请立即修改密码!");
			$(".pwdfloatWin #noBtn").hide();
			$(".pwdfloatWin").show();

			$(".pwdfloatWin .closeBtn").click(function(){
				$(".pwdmask").hide();
				$(".pwdfloatWin").hide();
				$("#password").val("");
				$("#securityCode").val("");
				$("#yzmImg").trigger("click")
				window.location.href="/app/login.jsp";
			});

			$(".pwdfloatWin #yesBtn").click(function(){
			$(".pwdfloatWin").hide();//隐藏密码有效期提示对话框
			$(".floatWindow").show();//显示密码修改对话框
			$(".floatWindow .closeBtn").click(function(){//关闭按钮
				$(".pwdmask").hide();
				$(".floatWindow").hide();
			});
			$("#cno").val(msg.cno);
			$("#enterpriseId").val(msg.enterpriseId);
			$("#userType").val("admin");
			$("#pwdsubmit").click(function(){
				submitButton();
			});

			});
		}
		else if (msg.pwd_valid_day=="0"){//未过期
			//--------密码没有过期，判断密码是否过于简单,提示用户修改------------
			if(msg.pwd_strength_level=="0"||msg.pwd_strength_level=="4"){//0:密码长度不符，4:全是小写或数字
				$(".pwdmask").show();
				if(msg.pwd_strength_level=="0")
					$(".pwdfloatWin .fwCont p").text("密码长度应在8到30个字符之间，立即修改吗?");
				else if(msg.pwd_strength_level=="4")
					$(".pwdfloatWin .fwCont p").text("为了系统安全,密码不应全为数字或字母，立即修改吗?");
				$(".pwdfloatWin .fwIitle h2").text("密码安全提示");
				$(".pwdfloatWin").show();

				$(".pwdfloatWin .closeBtn").click(function(){
					$(".pwdmask").hide();
					$(".pwdfloatWin").hide();
					//window.location.href="/app/login.jsp";
					window.location.href="${version}/system/login";
					$("#password").val("");
					$("#securityCode").val("");
					$("#yzmImg").trigger("click")
				});

				$(".pwdfloatWin #noBtn").click(function(){
					$(".pwdmask").hide();
					$(".pwdfloatWin").hide();
					window.location.href="${version}/system/home";
					});

				$(".pwdfloatWin #yesBtn").click(function(){

					$(".pwdfloatWin").hide();//隐藏密码有效期提示对话框
					$(".floatWindow").show();//显示密码修改对话框
					$(".floatWindow .closeBtn").click(function(){//关闭按钮
						$(".pwdmask").hide();
						$(".floatWindow").hide();
					});
					$("#cno").val(msg.cno);
					$("#enterpriseId").val(msg.enterpriseId);
					$("#userType").val("admin");
					$("#pwdsubmit").click(function(){
						submitButton();
					});
				});

			}else{
				$(".pwdmask").hide();
				$(".pwdfloatWin").hide();
				window.location.href="${version}/system/home";
			}
		}
		else if (msg.pwd_valid_day=="-2" || msg.pwd_valid_day=="-3"){
		      alert("系统错误，请联系管理员!["+msg.pwd_valid_day+"]");
		      window.location.href="/jws/redirect.html";
		}else{
			$(".pwdmask").show();
			$(".pwdfloatWin .fwCont p").text("您的密码距到期时间还有"+msg.pwd_valid_day+"天，立即修改吗?");
			$(".pwdfloatWin").show();

			$(".pwdfloatWin .closeBtn").click(function(){
				$(".pwdmask").hide();
				$(".pwdfloatWin").hide();
				//window.location.href="/app/login.jsp";
				window.location.href="${version}/system/login";
				$("#password").val("");
				$("#securityCode").val("");
				$("#yzmImg").trigger("click")
			});

			$(".pwdfloatWin #noBtn").click(function(){
				$(".pwdmask").hide();
				$(".pwdfloatWin").hide();
				window.location.href="${version}/system/home";
				});

			$(".pwdfloatWin #yesBtn").click(function(){

				$(".pwdfloatWin").hide();//隐藏密码有效期提示对话框
				$(".floatWindow").show();//显示密码修改对话框
				$(".floatWindow .closeBtn").click(function(){//关闭按钮
					$(".pwdmask").hide();
					$(".floatWindow").hide();
				});
				$("#cno").val(msg.cno);
				$("#enterpriseId").val(msg.enterpriseId);
				$("#userType").val("admin");
				$("#pwdsubmit").click(function(){
					submitButton();
				});
			});
		}
	}

		function Msg(cno1,enterpriseId1,userType1,pwd_valid_day1,pwd_strength_level1){
         	this.cno=cno1;
         	this.enterpriseId=enterpriseId1;
         	this.userType=userType1;
         	this.pwd_valid_day=pwd_valid_day1;
         	this.pwd_strength_level=pwd_strength_level1;
    	};
		$(function() {
		//----------------Added by wangguiyu on 2014-8-11------------
		  // $("#loginBtn").click(function(){
					var cno="${cno}";
				    var enterpriseId="${enterpriseId}";
				    var userType="${userType}";
				    var pwd_valid_day="${pwd_valid_day}";
				    var pwd_strength_level="${pwd_strength_level}";
	    			var msg=new Msg(cno,enterpriseId,userType,pwd_valid_day,pwd_strength_level);
					if(userType=="admin"){
						pwdValid(msg);
					}
		//});
		//----------------------------------------------------------------

			$.escapeHTML= function(str) {
				if(str!=undefined&&str!=null&&str.length>0){
					return str.replace(/&/g,'&amp;').replace(/>/g,'&gt;').replace(/</g,'&lt;').replace(/"/g,'&quot');
				}else{
					return "";
				}
			}

			$("#yzmImg").bind("click",function(){//重置验证码 haozh 2012/11/28 add
				 $(this).attr("src","${version}/system/getLoginSecurityCodeImage?" + Math.random());
				 $("#securityCode").focus();
			});

			/*$("#findPswBtnBack").bind("click",function(){//找回密码返回按钮事件 haozh 2012/11/28 add
				$("#loginForm").show();
				$(".findPwdCon").hide();
				$(".loginErrer").css({"visibility":"hidden"}).text("");
				$("#uniqueId_").focus();
				$("#securityCodeLi").css({"display":(Number(${wrongNum}) > 0?"block":"none")});
			});*/

				/*$("#hotline,#user").blur(function(){
					var hotline = $("#hotline").val();
					var user = $("#user").val();
					if(hotline != "" && user != ""){
						$("#pwdCode").parent().show();
					}
				});*/

		/*	if(Number(${wrongNum}) > 0){
				$("#securityCodeLi").removeAttr("style");
			} else {
				$("#securityCodeLi").attr("style","display:none");
			}*/

			if(${not empty requestScope.username}){
				showErrorInfo("${requestScope.username}");
			}
			if(${not empty requestScope.securityCode}){
				showErrorInfo("${requestScope.securityCode}");
			}
			/*var fnGetPassword = function(hotline,user) {
				if( !hotline || !user) return;
				$.ajax({
					type:"get",
					url:"${ctx}/system!findLoginPassword",
					datatype: 'text',
					data: "hotline=" + hotline +"&user="+user ,
					cache: false,
					success: function(msg){
						hiAlert(msg == "success"?"确认码已经发送到您的企业邮箱，一小时后过期，请尽快完成操作。":msg);
					}
				});

			};*/
			/*var retsetPassword = function(hotline,user,code) {
				if( !hotline || !user || !code) return;
				$.ajax({
					type:"get",
					url:"${ctx}/system!requestResetLoginPwd",
					datatype: "json",
					data: "hotline=" + hotline +"&user="+user + "&code="+code,
					cache: false,
					success: function(data){
						data=eval("("+data+")");
						try{
// 							hiAlert(data.result == "success"?"请点击 <font style='cursor: pointer;' color='red' onclick='openResetPwd(\""+data.desc+"\")'><a href='javascript:void(0)'>重置密码</a></font>  执行秘密重置操作！":data.desc);
							if(data.result == "success"){
								hiConfirm("确定继续重置密码？","重置密码",function(r){
									if(r){
										openResetPwd(data.desc);
									}
								})
							}else{
								hiAlert(data.desc);
							}
						}catch(e){
							console.log(data);
						}
					}
				});
			};*/

/*			$("#btnFindPwd").bind("click", function(){
				$("#loginForm").hide();
				$(".findPwdCon").show();
				$(".loginErrer").css({"visibility":"hidden"}).text("");
				$("#securityCodeLi").css({"display":"none"});
				$("#hotline").focus();
			});*/

			/*$("#getCode").click(function(){
				var hotline = $("#hotline").val();
				var user = $("#user").val();
				if(hotline==""){
					hiAlert("请输入热线号码");
					$("#hotline").focus();
					return;
				}
				if(user==""){
					hiAlert("请输入用户账号");
					$("#user").focus();
					return;
				}
				fnGetPassword(hotline,$.escapeHTML(user))
			});*/
			/*$("#findPswBtn").click(function(){
				var hotline = $("#hotline").val();
				var user = $("#user").val();
				var code = $("#pwdCode").val();
				if(hotline==""){
					hiAlert("请输入热线号码");
					$("#hotline").focus();
					return;
				}
				if(user==""){
					hiAlert("请输入用户账号");
					$("#user").focus();
					return;
				}
				if(code==""){
					hiAlert("请输确认码");
					$("#pwdCode").focus();
					return;
				}
				retsetPassword(hotline,user,code)
			});*/

			//获取公告
			<%--$.ajax({--%>
				<%--type:"get",--%>
				<%--url:"${ctx}/interfaceAction/noticeInterface!getPublishedNotice",--%>
				<%--datatype: 'text',--%>
				<%--cache: false,--%>
				<%--success: function(msg){--%>
					<%--if(msg != ""){--%>
						<%--var obj = JSON.parse(msg);--%>
						<%--$("#notice").html(obj.content)--%>
					<%--}--%>
				<%--}--%>
			<%--});--%>

			////登录焦点///////////////////////////////
/*			if(!$("#uniqueId_").val()){
				$("#uniqueId_").focus();
			}else*/

			if(!$("#username_").val()){
				$("#username_").focus();
			}else if(!$("#password").val()){
				$("#password").focus();
			}
			///////////////////////
		});

		function openResetPwd(code){
			var hotline = $("#hotline").val();
			var user = $("#user").val();
			try{
				var url="${ctx}/interface/resetPwd.jsp?code="+code+"&hotline="+hotline+"&user="+user;
				window.open(url,"resetPwd");
			}catch(e){
				hiAlert("发送错误，请刷新重试！");
			}
		}
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
					<a class="hzx" href="${ctx}/framework/userLogin.jsp">企业用户登录</a><a class="hgly">管理员登录</a>
				</div>
				<div class="formCon">
					<form id="loginForm" action="${version}/system/login" method="post" onsubmit="return checkSystemUserLogin('${wrongNum}');">
						<ul>
							<li style="color: red; padding: 5px 20px;" id="loginError"></li>
							<li><label>用户帐号</label><input class="hz_zxh" type="text" id="username_" name="username_" maxlength="20" value="${userNameFromCookie}"></li>
							<li><label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label><input class="hz_password" type="password" id="password" name="password"></li>
							<li id="securityCodeLi"><label>验&nbsp;&nbsp;证&nbsp;&nbsp;码</label><input class="hz_security" type="text" id="securityCode" name="securityCode" style="width:84px;" maxlength="4"><img src="${version}/system/getLoginSecurityCodeImage" alt="验证码" title="点击更新验证码" id="yzmImg">
							<%--<input id="uniqueId" name="uniqueId" type="hidden" />--%>
							<input id="username" name="username" type="hidden" />
							<input name="authCode" id="authCode" type="hidden" />
							<%--<input name="flag" id="flag" type="hidden" value="" />--%>
							<input type="hidden" id="reqSource" name="reqSource" value="1" />
							<%--<input type="hidden" id="pwdLevelAdmin" name="pwdLevelAdmin" value="" />--%>
							</li>
							<li><label></label><input type="submit" id="loginBtn" value="登录系统" class="green_btn">&nbsp;<input type="button" value="找回密码" id="btnFindPwd" class="hui_btn"></li>
						</ul>
					</form>

					<%--<div class="findPwdCon" style="display:none;">--%>
						<%--<ul>--%>
							<%--<li style="font-weight:bold;"><a class="keyIco"></a></li>--%>
							<%--<li>热线号码&nbsp;&nbsp;<input id="hotline" name="hotline" class="hz_code fx" type="text" maxlength="11" /></li>--%>
							<%--<li>用户账号&nbsp;&nbsp;<input id="user" name="user" class="hz_zxh fx" type="text" maxlength="10"/></li>--%>
							<%--<li style="display: none;">确&nbsp;&nbsp;认&nbsp;&nbsp;码&nbsp;&nbsp;<input id="pwdCode" name="pwdCode" class="hz_zxh fx" type="text" maxlength="6" style="width: 93px;"/>--%>
								<%--<input type="button" value="获取确认码" class="hui_btn" id="getCode"/>--%>
							<%--</li>--%>
							<%--<li>--%>
								<%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="findPswBtn" name="submit" class="green_btn" value="确定" /><input type="button" id="findPswBtnBack" name="button" value="返回" class="hui_btn"/>--%>
							<%--</li>--%>
						<%--</ul>--%>
					<%--</div>--%>

				</div>
				<div class="roundCornerCon"></div>
			</div>
			<div class="bgCloudy notice"><span class="nt">公告:</span><div id="notice" style="display:inline-block;"></div></div>
			<div class="foot">
				<div class="f1">
					<a class="hui" href="http://www.ti-net.com.cn/products/products!tostandard" target="_blank">产品介绍</a>|<a class="hui" href="http://www.ti-net.com.cn/service/service!toFAQ" target="_blank">常见问题</a>|<a class="hui" href="http://www.ti-net.com.cn/service/service!toPay" target="_blank">付款方式</a>|<a class="hui" href="http://www.ti-net.com.cn/contact/contact!total" target="_blank">关于我们</a>|<a class="hui" href="http://www.ti-net.com.cn/contact/contact!toContact" target="_blank">联系我们</a>
				</div>
				<div class="f2">
				全国呼叫中心运营许可证：B2-20070013 京ICP备06029233号 北京市公安局西城分局备案号：110102001636
				<br>
				版权所有 北京天润融通科技股份有限公司
				</div>
				<div class="wawa"></div>
			</div>
		</div>
		<!-- ------------密码有效期验证-----start-------- -->
<%--<div class="pwdmask" style="display:none"></div>--%>
		<%--<div class="pwdfloatWin" style="display:none">--%>
			<%--<div class="fwIitle">--%>
				<%--<h2>密码有效期提示</h2>--%>
				<%--<a href="#" class="closeBtn"></a>--%>
			<%--</div>--%>
			<%--<div class="fwCont">--%>
				<%--<p>--%>
					<%--修改密码--%>
				<%--</p>--%>
			<%--</div>--%>
			<%--<div class="clear"></div>--%>
			<%--<div class="fwFoot">--%>
				<%--<div class="winBtn">--%>
					<%--<a href="#" id="yesBtn" class="greenbtn">是</a>--%>
					<%--<a href="#" id="noBtn" class="graybtn">否</a>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>

       <!-- 修改密码对话框 -->
<%--		<div class="floatWindow" style="display:none;width: 400px">
			<div class="fwIitle">
				<h2>
					修改密码
				</h2>
				<a href="#" class="closeBtn"></a>
			</div>
			<div id="editPhone">
				<div class="fwCont">
					<input type="hidden" id="cno" name="cno" value="" />
					<input type="hidden" id="enterpriseId" name="enterpriseId" value="" />
					<input type="hidden" id="pwd_level" name="pwd_level" value="" />
					<input type="hidden" id="userType" name="userType" value="" />
					<div class="winfTitle dgrayfont norfont">
					<span class="norfont grayfont"><label id="oldPasswordError" style="color:#F00"></label></span><br>
						原密码：&nbsp;&nbsp;&nbsp;
						<input name="pwdMd5" id="pwdMd5" type="password" class="txt" style="width: 260px" />
					</div>

					<div class="winfTitle dgrayfont norfont">
						新密码：&nbsp;&nbsp;&nbsp;
						<input name="newPwd" id="newPwd" type="password" class="txt" style="width: 260px" onKeyUp="pwStrength(this.value)" onBlur="pwStrength(this.value)"/>
					</div>

					<div style="position: relative; margin: 10px 0 0 60px">
						<span class="norfont grayfont">密码强度：</span>
						<br>
						<span class="norfont grayfont"><label id="newPasswordError" style="color:#F00"></label></span>
						<span class="pwStrg" style="left: 70px; top: 6px"><span id="pwdclassid" class="l1"></span>
						</span>
					</div>
					<div class="winfTitle dgrayfont norfont">
						确认密码：
						<input name="cfmPsw" id="cfmPsw" type="password" class="txt" style="width: 260px" />
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="fwFoot">
				<div class="winBtn">
					<a href="#" id="pwdsubmit" class="greenbtn">确定</a>
				</div>

			</div>
		</div>--%>
<!-- ------------密码有效期验证--end----------- -->
    </body>
</html>