<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="${ctx }/">
<title>密码修改</title>


<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/passwordsuc.css?v=${version}" />
</head>
</head>

<body>
<%@ include file="common/loginhead.jsp" %>
    <div class="content">
	     <div class="content-top">
	         <img src="${ctx }/static/pc/img/dui2.png"/>
	         <span>恭喜您，密码已经修改成功！</span>
	     </div>
	     <p>3秒自动跳转到登录页面,手动点击&nbsp;<a href="${ctx}/front/user">立即登录</a></p>
	</div>
	<%@ include file="common/loginfoot.jsp" %>
	<script type="text/javascript">
	     setTimeout(function(){window.location.href="${ctx}/front/user/logout";}, 3000);
	</script>
</body>
</html>