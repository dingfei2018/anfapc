<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密码</title>
	 	<%@ include file="common/include.jsp" %>
	 	<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/password.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		
	</head>
	<body onkeydown="if(event.keyCode==13){checkPwd()}">
		<%@ include file="common/head.jsp" %>
		<div class="content">
           <%@ include file="common/left.jsp" %>
            <div class="content-right">
            <div class="conr_title">修改密码</div>
            	<span>输入新密码：</span><input type="password" id="newPwd" placeholder="请输入新密码"/><br />
            	<span class="content-span">确认密码：</span><input type="password" placeholder="请再次输入新密码" id="checkNewPwd" class="content-input" />
            	<br />
            	<button onclick="checkPwd()">提交</button>
            </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	    <script type="text/javascript">
		
		function checkPwd(){
			  var newPwd =  $("#newPwd").val();
			  var checkNewPwd = $("#checkNewPwd").val();
			   if(newPwd == ""){
			     Anfa.show("请输入密码","#newPwd");
			     return false;
			   }
			   if(checkNewPwd == ""){
				     Anfa.show("请输入确认密码","#checkNewPwd");
				     return false;
				   }
			   if(newPwd!=checkNewPwd){
				   Anfa.show("两次密码输入不一致","#checkNewPwd");
				   $("#newPwd").val("");
				   $("#checkNewPwd").val("");
				   return false;
			   }
			   
			   
			   var  reg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
			   var reg_s=/\s/;
			   if(reg.test(newPwd)||reg_s.test(newPwd)){
				   Anfa.show("您输入的密码含有非法字符！","#newPwd");
				   $("#newPwd").val("");
				   $("#checkNewPwd").val("");
				   return false;
			   }
			   
			   var len_reg = /^\S{6,32}$/;
			   if(!len_reg.test(newPwd)){
			    	Anfa.show("请输入6位~32位的密码","#newPwd");
			    	 $("#newPwd").val("");
					 $("#checkNewPwd").val("");
			    	return false;
			   }
			   
			   
			   var data={"password":checkNewPwd};
			   $.ajax({
					type:"post",
					dataType:"html",
					url:"${ctx}/front/userconter/updatePassword",
					data:data,
						success:function(data){
							var obj = new Function("return" + data)();
							if(obj.state="SUCCESS"){
							window.location.href="${ctx}/front/userconter/success";
							}else{
								alert(obj.msg);
							}
							
			            }
					});
			}
		
		
		</script>
	</body>
</html>
