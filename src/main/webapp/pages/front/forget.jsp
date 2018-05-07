<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title></title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/forget.css?v=${version}" />
		<script type="text/javascript" src="${ctx }/static/pc/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script type="text/javascript">
		   /*忘记密码手机时计时器  */
			var wait4 = 60;
			function sendCode() {
				var phone = $("#phoneNumber").val();
				if(phone == ""){
					 Anfa.show("请输入手机号","#phoneNumber");
					 return;
				}	
				if(!(/^1[345678]\d{9}$/.test(phone))){
				   Anfa.show("请输入正确的手机号","#phoneNumber");
				   return;
			    }
				
				var data1={"token":$("#token").val(), "phone":phone};
				$.ajax({
					type:"post",
					url:"${ctx }/front/forget/forgetMsg",
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
						}else {
							Anfa.show(data.msg,"#phoneNumber");
						}
					}
				});
			}
			function sendClick(evn){
				if (wait4 == 0) {
					evn.value = "获取验证码";
					evn.removeAttribute("disabled");
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
			function checkUser(){
				   var phoneNumber = document.getElementById("phoneNumber").value;
				   var smsCode = document.getElementById("smsCode").value;
				   if(phoneNumber == ""  ){
					   Anfa.show("请输入手机号","#phoneNumber");
					   return;
				   }
				   if(!(/^1[345678]\d{9}$/.test(phoneNumber))){
					   Anfa.show("请输入正确的手机号","#phoneNumber");
					   return;
				    }
				   if(smsCode == ""  ){
					   Anfa.show("请输入验证码","#smsCodeBtn");
					   return;
				   }
	 			   $.ajax({
						type:"post",
						url:"${ctx }/front/forget/forgetsave",
						data:$('#formid').serialize(),
						success:function(data){
							if (data.success) {
								$("#phone").val(data.data.phone);
								$("#uid").val(data.data.uid);
								$("#time").val(data.data.time);
								$("#sign").val(data.data.sign);
								$("#sform").submit();
							}else if(data.type==1009){
								Anfa.show(data.msg,"#smsCodeBtn");
							}else{
								Anfa.show(data.msg,"#phoneNumber");
							}
			            }
					}); 
				}
		</script>
	</head>
	<body>
		<%@ include file="common/loginhead.jsp" %>
		<div class="content">
			<p>忘记密码</p>
			<div class="content-left">
			<input type="hidden" id="token" name="token"  value="${token}">
			<form id="sform" action="${ctx}/front/forget/passwordindex" method="post">
				<input type="hidden" id="phone" name="phone">
				<input type="hidden" id="uid" name="uid">
				<input type="hidden" id="sign" name="sign">
				<input type="hidden" id="time" name="time">
			</form>
			<form id="formid" action="" method="post">
				
				<label>
				   手机号码
				   <input type="text" id="phoneNumber" name="phoneNumber" placeholder="请输入11位手机号码"    onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</label>
				
				<br/>
				<br/>
				<label>
				手机验证码
				<input type="text" id="smsCode" name="smsCode" placeholder="请输入验证码" class="content-right" />
				<!-- <button name="smsCodeBtn" id="smsCodeBtn" onclick="sendCode();" class="content-button">获取验证码</button> -->
				<input name="smsCodeBtn" id="smsCodeBtn" type="button" value="获取验证码" onclick="sendCode();"/>
				</label>
				<br />
				<br />
				<!-- <a href="#">收不到验证码?</a> -->
			</form>
			</div>
			<button onclick = "checkUser();" >下一步</button>
		</div>
		<%@ include file="common/loginfoot.jsp" %>
	</body>
</html>