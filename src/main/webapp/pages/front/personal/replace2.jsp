<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>更换账号</title>
		<%@ include file="common/include.jsp" %>
		<link rel="stylesheet" href="${ctx }/static/pc/Personal/css/replace2.css?v=${version}" />		
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>

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
              	 		 <img src="${ctx}/static/pc/img/modify_phone2.jpg"/>
            		<ul>
            	 	   <li><input type="hidden" class="content-right-input" placeholder="请输入新的手机号" id="mobile" value="${mobile}"></li>
            			<li><span>手机号：</span><input type="text" class="content-right-input" placeholder="请输入新的手机号" id="phoneNumber"></li>
            			<li><span class="content-right-span">验证码：</span><input type="text" id="smsCode" name="smsCode" placeholder="请输入验证码" class="content-right-inputs">
            			<input class="content-right-inputss" id="smsCodeBtn" name="smsCodeBtn" type="button" value="获取验证码" onclick="sendCode();"/>
            	</ul>
            	<button class="content-right-botton" onclick="updateMobile();">下一步</button>
           	 </div>
			</div>
		 </div>
		<%@ include file="../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		var wait4 = 60;
		function sendCode() {
			var phone = $("#phoneNumber").val();
			if(phone == ""  ){
				Anfa.show("请输入手机号码","#phoneNumber");
				flag=true;
			}
			var data1={"token":$("#token").val(),"phone":phone};
				Anfa.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/front/register/sendVerificationcode",
					data:data1,
					success:function(data){
						if (data.success) {
							alert(data.msg+'---'+data.data.code);
							var evn = document.getElementById("smsCodeBtn");
							sendClick(evn);
						}else if(data.type==0){
							Anfa.show(data.msg,"#phoneNumber");
							setTimeout(function() {
								window.location.reload();
							}, 1500);
						} else {
							Anfa.show(data.msg,"#phoneNumber");
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
			   var smsCode = $("#smsCode").val();
			   var flag=false;
			   if(phoneNumber == ""  ){
			    Anfa.show("请输入手机号码","#phoneNumber");
			    flag=true;
			   }
			   if(smsCode == ""  ){
				     Anfa.show("请输入验证码","#smsCode");
				     flag=true;
			  }
			   if(flag){
				  return;
			   }
			  var data={"smsCode":smsCode,"mobile":phoneNumber};
			  Anfa.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/front/userconter/updateMobile",
					data:data,
					success:function(data){
						if(data.state=="SUCCESS"){
							 window.location="${ctx}/pages/front/personal/success2.jsp";
					    }else if(data.type==1009){
					    	Anfa.show("请输入正确的验证码","#smsCode");
					    }
		            }
				});
			}
	</script>
	</body>
</html>
