<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<div class="banner-left">

	<div class="banner-left-padding">
	<c:if test="${fn:length(sessionScope.LaunchQuery)>0}">
				 	<c:forEach items="${sessionScope.LaunchQuery}" var="menu" varStatus="vs">
				 	<div class="banner-left-list1">
				 	
					<a href="${ctx }/${menu.href}">
						<img src="${ctx}/${menu.icon}" />
						<p>${menu.name}</p>
					</a>
					</div>
				 	</c:forEach>
	</c:if>

	<!--  <div class="banner-left-list1">
		<a href="#"> <img src="${ctx}/static/kd/img/2sf_icon1.jpg" />
			<p>按配载查询</p>
		</a>
	</div>
	<div class="banner-left-list1">
		<a href="#"> <img src="${ctx}/static/kd/img/2sf_icon2.jpg" />
			<p>按运单查询</p>
		</a>
	</div>
	-->
	</div>
</div>