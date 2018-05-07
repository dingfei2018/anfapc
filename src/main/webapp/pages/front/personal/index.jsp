<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>个人中心</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/test.css?v=${version}"/>
	 	<%@ include file="common/include.jsp" %>
	 	
	</head>
	<body style="height=1500px">
					
	<%@ include file="../common/loginhead.jsp" %>
		<div class="content">
           <%@ include file="common/left.jsp" %>
            <div class="content-right">
            
            	<div class="content-right-ops">
            	   <img src="${ctx}/static/pc/img/renzhenui.jpg"/>
            	</div>
            	
            	
            	<div class="content-right-ups">
            	   <img src="${ctx}/static/pc/img/renzhen_3.jpg"/>
            	   <ul>
            	      <li>尊敬的 <span>${session_user.mobile}</span> 用户</li>
            	      <li>请先完善基本信息，然后进行实名制认证，即可获得专属企业网站，开启网上物流 !</li>
            	      <li><a href="${ctx}/front/userconter/getCompanyInfo">完善基本信息</a></li>
            	   </ul>
            	</div>
            	
            	<div class="content-right-upso">
            	  <img src="${ctx}/static/pc/img/renzhen_4.jpg"/>
            	</div>
            	
            </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
