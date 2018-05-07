<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>更换账号</title>
		<%@ include file="common/include.jsp" %>
		<link rel="stylesheet" href="${ctx }/static/pc/Personal/css/replace.css?v=${version}" />		
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>

	</head>
	<body>
	   <div class="header-list">
		<%@ include file="common/head.jsp" %>
		</div>
		<div class="content">
           <%@ include file="common/left.jsp" %>
			<input type="hidden" id="token" name="token"  value="${token}">
            <div class="content-right">
            	<div class="conr_title">更换账号</div>
            		<div class="conr_con">
              	 		 <img src="${ctx}/static/pc/img/modify_phone1.jpg"/>
            		<ul>
            			<li><p>为了确实是您本人的操作，请完成账号${session_user.mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","********$2")}验证</p></li>
            			<li><span class="content-right-span">验证码：</span><input maxlength="4" type="text" name="smsCode"  id="smsCodeId" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" class="content-right-inputs">
            			<input type="hidden" id="phone" name="phone" value="${session_user.mobile}">
            			<input class="content-right-inputss" id="smsCodeBtnid" name="smsCodeBtn" type="button" value="获取验证码"  onclick="sendCode();"/>
            			<li><a href="${ctx}/front/customer">[手机不可用，请联系客服]</a>
            	</ul>
            	<button class="content-right-botton" onclick="updateMobile();">下一步</button>
           	 </div>
			</div>
		 </div>
		<%@ include file="../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		var wait4 = 60;
		function sendCode() {
			var data1={"token":$("#token").val()};
				Anfa.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/front/register/sendCheckPhoneVerificationcode",
					data:data1,
					success:function(data){
						if (data.success) {
							alert(data.msg+'---'+data.data.code);
							var evn = document.getElementById("smsCodeBtnid");
							sendClick(evn);
						}else if(data.type==0){
							Anfa.show(data.msg,"#smsCodeBtnid");
							setTimeout(function() {
								window.location.reload();
							}, 1500);
						} else {
							Anfa.show(data.msg,"#smsCodeBtnid");
						}
					}
				});
		}
		
		function sendClick(evn){
			if (wait4 == 0) {
				evn.value = "获取验证码";
				wait4 = 60;
			} else {
				evn.setAttribute("disabled", true);
				evn.value = "(" + wait4 + ")秒后获取";
				wait4--;
				setTimeout(function() {
					sendClick(evn);
				}, 1000);
			}
		}
		
		function updateMobile(){
			var phoneNumber = $("#phoneNumber").val();
			   var smsCode = document.getElementById("smsCodeId").value;
		       var flag = false;
			   if(smsCode == "" || smsCode == "请输入验证码"){
				   Anfa.show("请输入验证码","#smsCodeBtnid");
			       flag = true;
			   }else if(smsCode.length!=4){
				   Anfa.show("请输入正确的验证码","#smsCodeBtnid");
			       flag = true;
			   }
			   if(flag){
				   return;
			   }
			   if(!flag){
				   flag = true;
				   var data={"smsCode":smsCode};
				   Anfa.ajax({
						type:"post",
						url:"${ctx}/front/userconter/replaceMobile",
						data:data,
						success:function(data){
							flag = false;
							if (data.state=="SUCCESS") {
									window.location.href="${ctx}/front/userconter/checksuccess";
								} else {
								if(data.state=="ERROR"){
									Anfa.show("请输入正确的验证码","#smsCodeBtnid");
								}
							}
			            }
					});
			   }
		}
	</script>
	</body>
</html>
