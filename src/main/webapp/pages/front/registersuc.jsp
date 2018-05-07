<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="${ctx }/">
<title>注册成功</title>


<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/loginsuc.css?v=${version}" />
</head>
</head>

<body>
    <%@ include file="common/loginhead.jsp" %>
    <div class="content">
	     <div class="content-top">
	         <img src="${ctx }/static/pc/img/dui2.png"/>
	         <span>恭喜您，会员已注册成功！</span>
	     </div>
	     <p>3秒自动跳转到登录页面,<a href="${ctx}/front/user">手动点击立即登录</a></p>
	</div>
	<%@ include file="common/loginfoot.jsp" %>
	<script type="text/javascript">
	    setTimeout(function(){window.location.href="${ctx}/front/user";}, 3000);
	</script>
</body>
</html>