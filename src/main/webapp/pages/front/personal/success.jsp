<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密码成功</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/Success.css?v=${version}"/>
	</head>
	<%@ include file="common/include.jsp" %>
	<body onkeydown="if(event.keyCode==13){showTime()}">
		<%@ include file="common/head.jsp" %>
		<div class="content">
          <%@ include file="common/left.jsp" %>
            <div class="content-right">
            <div class="conr_title">修改密码</div>
            	<p>密码修改成功，请牢记！</p>  	
		       <div id="sub-text" align="center"><font>3s</font> 后将跳转到登录页面</div>
            	<button class="content-right-botton" onclick='javascript:window.location.href="${ctx}/pages/front/login.jsp"'>确定</button>
            </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<%
		session.invalidate();
		%>
		<script type="text/javascript">
		
		//认证成功跳转
		var time=setInterval (showTime, 1000);
		var second=5;
		function showTime()
		{ 
		if(second==0)
		{
	  window.location="${ctx}/pages/front/login.jsp";
		clearInterval(time);
		}
		$("#sub-text").html('<font>'+second+'s</font> 后将跳转到登录页面。');
		second--;
		}
		
		</script>
	</body>
</html>
