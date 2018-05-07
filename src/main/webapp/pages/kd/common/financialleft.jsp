<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<div class="banner-left">
	<div class="banner-left-padding">
	<c:if test="${fn:length(sessionScope.financialMage)>0}">
				 	<c:forEach items="${sessionScope.financialMage}" var="menu" varStatus="vs">
				 	<div class="banner-left-list1">
					<a href="${ctx }/${menu.href}">
						<img src="${ctx}/${menu.icon}" />
						<p>${menu.name}</p>
					</a>
					</div>
				 	</c:forEach>
	</c:if>

				<!-- <div class="banner-left-list1">
					<img src="${ctx}/static/kd/img/4l_icon1.jpg" />
					<p>应收管理</p>
				</div>
				<div class="banner-left-list1">
					<img src="${ctx}/static/kd/img/4l_icon2.jpg" />
					<p>应付管理</p>
				</div>
				<div class="banner-left-list1">
					<img src="${ctx}/static/kd/img/4l_icon3.jpg" />
					<p>代收贷款</p>
				</div>
				<div class="banner-left-list1">
					<img src="${ctx}/static/kd/img/4l_icon4.jpg" />
					<p>网点对账</p>
				</div>
		
				<div class="banner-left-list1">
					<img src="${ctx}/static/kd/img/4l_icon5.jpg" />
					<p>报表中心</p>
				</div>
				 -->
	</div>
</div>
			
