<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<div class="banner-left">
	<div class="banner-left-padding"> 
	<c:if test="${fn:length(sessionScope.baseSet)>0}">
				 	<c:forEach items="${sessionScope.baseSet}" var="menu" varStatus="vs">
				 	<div class="banner-left-list1">
					<a href="${ctx }/${menu.href}">
						<img src="${ctx}/${menu.icon}" />
						<p>${menu.name}</p>
					</a>
					</div>
				 	</c:forEach>
	</c:if>


	<!-- <div class="banner-left-list1">
		<a href="${ctx }/kd/user">
						<img src="${ctx}/static/kd/img/survey.jpg" />
						<p>用户管理</p>
					</a>
	</div>
	<div class="banner-left-list1">
					<a href="${ctx}/kd/role">
						<img src="${ctx}/static/kd/img/survey2.jpg" />
						<p>角色管理</p>
					</a>
	</div>
	<div class="banner-left-list1">
					<a href="${ctx}/kd/waybill/set">
						<img src="${ctx}/static/kd/img/survey3.jpg" />
						<p>运单打印设置</p>
					</a>
	</div>
	 -->
	 </div>
</div>
			
