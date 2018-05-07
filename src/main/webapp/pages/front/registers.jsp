<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>非物流公司注册</title>
<link rel="stylesheet" href="${ctx }/static/pc/zhuc/css/index.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/registers.css?v=${version}" />
<script type="text/javascript" src="${ctx }/static/pc/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
<script src="${ctx }/static/pc/js/menu.js?v=${version}"></script>
</head>
<body onkeydown="if(event.keyCode==13){checkUser()}">
	<%@ include file="common/loginhead.jsp" %>	
	<div class="content">
	   <div class="content-liso">
			<ul>
			   <li><a href="${ctx}/front/register/wl">物流公司</a></li>
			   <li  class="actives">非物流公司</li>
			</ul>
			<input type="hidden" id="token" name="token"  value="${token}">
			<form id="formid" action="" method="post">
			    <input type="hidden" name="type" value="3">
				<div class="register-box">
							<label for="username" class="username_label">手机号码
					<input maxlength="20" type="text" name="phoneNumber" id="phoneNumber" placeholder="请输入手机号"   onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
							</label >
					<!-- <div class="tips"></div> -->
				</div>
				<div class="register-box">
					<label for="username" class="other_label">手机验证码
					<input maxlength="4" type="text" name="smsCode"  id="smsCodeId" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"   onblur="checkCode()" placeholder="请输入验证码"/>
					</label>
					<input class="register-input" id="smsCodeBtnid" name="smsCodeBtn" type="button" value="获取验证码" onclick="sendCode();"/>
					<div class="tips"></div>
				</div>
				<div class="register-box">
					<label for="username" class="other_label">设置密码
					<input maxlength="20" type="password" name="password" id="password" placeholder="请输入密码">
					</label>
					<div class="tips"></div>
				</div>
				<div class="register-box">
					<label for="username" class="other_label">确认密码
					<input maxlength="20" type="password" name="newPassword"  id="newPassword" placeholder="请输入密码">
					</label>
					<div class="tips"></div>
				</div>
				
				<div class="arguement">
				    <input type="checkbox" id="xieyi" checked="checked"  onclick="ins()">阅读并同意
					<a href="${ctx}/front/register/regGreement2" target="_blank">《网上物流会员服务协议》</a>
					<div class="tips"></div>
						</div>
				<div class="submit_btn">
					<input id="login" type="button" value="立 即 注 册" onclick = "checkUser();" class="submit_btn-button"/>
					<a id="btn" href="${ctx}/front/user">已有账号，马上登陆</a>
				</div>
			</form>
		</div>
	</div>
    <!--底部的内容--->
	<%@ include file="common/loginfoot.jsp" %>	
			<script type="text/javascript">
	        /* 修改手机号码*/
			var wait4 = 10;
			function sendCode() {
				var phoneNumber = document.getElementById("phoneNumber").value;
				if(!phoneNumber){
					Anfa.show("请输入手机号","#phoneNumber");
					return;
				}
				var data1={"token":$("#token").val(), "phone":phoneNumber};
				Anfa.ajax({
					type:"post",
					url:"${ctx}/front/register/sendVerificationcode",
					data:data1,
					success:function(data){
						if (data.success) {
							alert(data.msg+'---'+data.data.code);
							var evn = document.getElementById("smsCodeBtnid");
							sendClick(evn);
						}else if(data.type==0){
							Anfa.show(data.msg,"#phoneNumber");
							setTimeout(function() {
								window.location.reload();
							}, 1500);
						}else {
							Anfa.show(data.msg,"#phoneNumber");
						}
					}
				});
			}
			
			function isPasswd(s){  
				var patrn=/^(\w){6,20}$/;  
				if (!patrn.exec(s)) return false
				return true
			}  
			
			
			function sendClick(evn){
				if (wait4 == 0) {
					evn.value = "获取验证码";
					evn.removeAttribute("disabled");
					wait4 = 10;
				} else {
					evn.setAttribute("disabled", true);
					evn.value = "" + wait4 + "秒后获取";
					wait4--;
					setTimeout(function() {
						sendClick(evn);
					}, 1000);
				}
			}
			
			function checkCode() {
				var phoneNumber = document.getElementById("phoneNumber").value;
				var smsCode = document.getElementById("smsCodeId").value;
				if(!phoneNumber||smsCode.length!=4){
					return;
				}
				var data1={"smsCode":smsCode,"phoneNumber":phoneNumber};
				Anfa.ajax({
					type:"post",
					url:"${ctx}/front/user/checkCode",
					data:data1,
					success:function(data){
						if (!data.success) {
							Anfa.show(data.msg,"#smsCodeBtnid");
						}
					}
				});
			}
			
			
			function checkUser(){
				   var phoneNumber = document.getElementById("phoneNumber").value;
				   var password = document.getElementById("password").value;
				   var newPassword = document.getElementById("newPassword").value;
				   var smsCode = document.getElementById("smsCodeId").value;
				   //var type = document.getElementById("type").value;
				   //var checkboxtype = $("input[type='checkbox']").attr('value');
	 		       var flag = false;
			       if(phoneNumber == ""){
			    	   Anfa.show("请输入手机号","#phoneNumber");
				       flag = true;
				   }else if(!(/^1[345678]\d{9}$/.test(phoneNumber))){
					   Anfa.show("请输入正确的手机号","#phoneNumber");
					   flag = true;
				   }
				   if(smsCode == "" || smsCode == "请输入验证码"){
					   Anfa.show("请输入验证码","#smsCodeBtnid");
				       flag = true;
				   }else if(smsCode.length!=4){
					   Anfa.show("请输入正确的验证码","#smsCodeBtnid");
				       flag = true;
				   }
				   if(password == ""){
					   Anfa.show("请输入密码","#password");
					   flag = true;
				   } 
				   
				   if(!isPasswd(password)){
					   Anfa.show("密码须6-20个字母、数字或下划线","#password");
					   flag = true;
				   } 
				   
				   if(newPassword == ""){
					   Anfa.show("请输入确认密码","#newPassword");
					   flag = true;
				   } 
				   if(newPassword != password){
					   Anfa.show("两次密码输入不一致","#newPassword");
					   flag = true;
				   } 
				   if(flag){
					   return;
				   }
				   if(!flag){
					   flag = true;
					   Anfa.ajax({
							type:"post",
							url:"${ctx}/front/register/registersave",
							data:$('#formid').serialize(),
							success:function(data){
								flag = false;
								if (data.success) {
									setTimeout(function(){
										window.location.href="${ctx }/front/register/success";
									},300)
								} else {
									if(data.type==1009){
										Anfa.show("请输入正确的验证码","#smsCodeBtnid");
									}else{
										Anfa.show(data.msg,".submit_btn-button");
									}
								}
				            }
					  });
				   }
			}
			
			function ins(){
				if($("#xieyi").is(':checked')){
					$("#login").removeAttr("disabled");
					$("#login").attr("class", "submit_btn-button");
				}else{
					$("#login").attr("disabled", "disabled");
					$("#login").attr("class", "activeu");
				}
			}
	    </script>
</body>
</html>



