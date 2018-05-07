<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密码</title>
		<%@ include file="common/include.jsp" %>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/code.css?v=${version}" />
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	</head>
	<body onkeydown="if(event.keyCode==13){validatePassWord()}">
		<%@ include file="common/head.jsp" %>
		<div class="content">
            	<%@ include file="common/left.jsp" %>
            <div class="content-right">
          <div class="conr_title">修改密码</div>
            	<span>旧密码：</span>&nbsp;&nbsp;&nbsp;<input type="password" placeholder="请输入旧密码" id="password" name="password" />
            	<br />
            	<button onclick="validatePassWord();">下一步</button>
            </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		function validatePassWord(){
		   var password = $("#password").val();
		   if(password == ""  ){
		      Anfa.show("请输入旧密码","#password");
		     return false;
		   }
		   var data = {"password":password};
		   $.ajax({
				type:"post",
				dataType:"html",
				url:"${ctx}/front/userconter/validatePassWord",
				data:data,
					success:function(data){
						var obj = new Function("return" + data)();
						if(obj.state == "SUCCESS"){
							 window.location.href = '${ctx}/front/userconter/passwordNext';
						}else{
							 Anfa.show(obj.msg,"#password");
						}
		            }
				});
			}
		</script>
	</body>
</html>
