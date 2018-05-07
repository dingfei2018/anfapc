<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title>客服服务</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/Beforelogin.css" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css"/>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
	</head>
	<body style="min-height: 960px;">
			
	<%@ include file="../common/loginhead.jsp" %>
		<!---详细页面的内容---->
		<div class="content">			
			<div class="content-main">
					<h2 class="content-title">客服服务</h2>
					<img class="content-pic-tel" src="${ctx}/static/pc/img/kefu-o22_03.jpg" alt="telephone"/>
					<p class="content-tel">020-38856254</p>
					<p class="content-tel-page">24小时真诚为您服务!</p>
					<img class="content-pic-chat" src="${ctx}/static/pc/img/kefu-o22_06.jpg" alt ="chat"/>
					<p class="content-chat">在线客服</p>
					<p class="content-chat-page">专业客服对话：为您解决遇到的问题</p>
				</div>
			
			<div class="content-ul">
				<ul>
					<li id="kfli" class="kfli"><a href="tencent://message/?uin=2128747387&amp;Site=2128747387&amp;Menu=yes"><img src="${ctx}/static/pc/img/gun.gif" alt="点击咨询" align="absmiddle"> 王小姐</a></li>
					<li class="kfli"><a href="tencent://message/?uin=2474761545&amp;Site=2474761545&amp;Menu=yes"><img style="vertical-align: middle;" border="0" src="${ctx}/static/pc/img/gun.gif"/> 黄小姐</a></li>
					<li class="kfli"><a href="tencent://message/?uin=2144605300&amp;Site=2144605300&amp;Menu=yes"><img style="vertical-align: middle;" border="0" src="${ctx}/static/pc/img/gun.gif"/> 宋小姐</a></li>
				</ul>
			</div>
		</div>
    <%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>

