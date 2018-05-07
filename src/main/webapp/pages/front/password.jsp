<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title>密码修改</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/password.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
		<script type="text/javascript" src="${ctx }/static/pc/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script type="text/javascript">
		function updatepassword(){
			   var password = document.getElementById("password").value;
			   var newPassword = document.getElementById("newPassword").value;
			   var flag  = false;
			   if(password == ""  ){
			     	Anfa.show("密码不能为空","#password");
			     	flag  = true;
			     	return;
			   }
			   if(newPassword == ""  ){
			    	Anfa.show("再一次密码不能为空","#newPassword");
			    	flag  = true;
			    	return;
			   }
			   if(newPassword!=password){
			    	Anfa.show("两次输入的密码不一致","#newPassword");
			    	$("#password").val("");
					$("#newPassword").val("");
			    	flag  = true;
			    	return;
			   }
			   var  reg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
			   var reg_s=/\s/;
			   if(reg.test(password)||reg_s.test(password)){
				   Anfa.show("您输入的密码含有非法字符！","#password");
				   $("#password").val("");
					$("#newPassword").val("");
				   flag  = true;
				   return;
			   }
			   
			   var len_reg = /^\S{6,32}$/;
			   if(!len_reg.test(password)){
			    	Anfa.show("请输入6位~32位的密码","#password");
			    	$("#password").val("");
					$("#newPassword").val("");
			    	flag  = true;
			    	return;
			   }
			
			   
			   if(flag){
				  return; 
			   }
			   if(!flag){
				   flag  = true;
				   $.ajax({
						type:"post",
						url:"${ctx }/front/forget/updatepassword",
						data:$('#formid').serialize(),
						success:function(data){
							if (data.success) {
								setTimeout(function(){
									window.location.href="${ctx}/front/forget/success";
								},300)
							}else if(data.type==-2){
								layer.msg(data.msg);
								setTimeout(function(){
									window.location.href="${ctx}/index";
								},500)
							}else{
								layer.msg(data.msg);
							}
							flag  = false;
			            }
					}); 
			   }
			}
	</script>
	</head>
	<body onkeydown="if(event.keyCode==13){updatepassword()}">
		<%@ include file="common/loginhead.jsp" %>
		<form id="formid" action="" method="post" onclick="return false">
		<div class="content">
			<p>设置密码</p>
			<div class="content-left">
			    <label>设置密码<input id="password" name="password" type="password"  AUTOCOMPLETE="off" placeholder="请输入密码" /></label>
				<br />
				<br />
				 <label>
				确认密码
			<input id="newPassword" name="newPassword" type="password" AUTOCOMPLETE="off"  class="content-input" type="password" placeholder="请输入密码" />
			</label>
			<input type="hidden" name="userid" value="${userid}"> 
			<input type="hidden" name="phone" value="${phone}"> 
			<input type="hidden" name="time" value="${time}"> 
			<input type="hidden" name="sign" value="${sign}"> 
			</div>
			<input type="button" onclick="updatepassword();" class="content-button" value="确定"/>
			<div class="content-right">
			    <p>修改成功</p>
				<button class="content-rights">确定</button>
			</div>
		</div>
		</form>
		<%@ include file="common/loginfoot.jsp" %>
	</body>
</html>