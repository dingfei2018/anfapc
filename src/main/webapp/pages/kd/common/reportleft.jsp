<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/css/loadleft1.css"/>
	</head>
		<div class="banner-left">
			<div class="banner-left-padding">
			<c:if test="${fn:length(sessionScope.report)>0}">
				 	<c:forEach items="${sessionScope.report}" var="menu" varStatus="vs">
				 	<div class="banner-left-list1">
					<a href="${ctx }/${menu.href}">
						<img src="${ctx}/${menu.icon}" />
						<p>${menu.name}</p>
					</a>
					</div>
				 	</c:forEach>
			</c:if>
			</div>
		</div>
</html>
