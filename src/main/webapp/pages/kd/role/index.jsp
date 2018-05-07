<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>角色列表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/rolelist.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>
	<body>
	    <input type="hidden" value="${msg.msg}" id="msg" />
	  	<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/baseinfoleft.jsp" %>
			<script type="text/javascript">
			     $(function(){
				  var _width=$("body").width();
				  var _widths = $(".banner-left").width();
				  var _widthd = _width - _widths - 80;
				  parseInt(_widthd)
				  $('.banner-right').css('width',_widthd+'px');
			     });
			     $(window).resize(function(){ 
			    	  var Width = $(window).width();
		    	      var _widths = $(".banner-left").width();
			  		  var _widthd = Width - _widths - 80;
			  		  parseInt(_widthd)
			  		  $('.banner-right').css('width',_widthd+'px');
			    })
	       </script>
			<div class="banner-right">
				<%-- <ul>
					<li><a href="${ctx}/kd/role/" class="activet">角色列表</a></li>
				</ul>
				 --%>
				<p class="banner-right-p">角色列表</p>
				<div style="overflow: auto; width: 100%;" id="loadingId"> 
				<script >$("#loadingId").mLoading("show");</script>
				<table cellspacing="0" class="table">
					<tr>
						<th class="banner-right-pleft"></th>
						<th>序号</th>
						<th>角色名称</th>
						<th>功能权限</th>
						<th>操作</th>
					</tr>
				<c:if test="${fn:length(roleList)>0}">
					 <c:forEach items="${roleList}" var="role" varStatus="vs">
					<tr>
						<td class="banner-right-pleft"><input type="checkbox" value="${role.roleId}"/></td>
						<td>${vs.index+1}</td>
						<td>${role.roleName}</td>
						<td>${role.menuName}</td>
						<td><a class="banner-right-a1" href="${ctx}/kd/role/add?type=update&roleId=${role.roleId}">修改</a></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
				</div>
				
				 <c:if test="${fn:length(roleList)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				</div>
			</c:if>
			</div>
		</div>
       <%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		if($("#msg").val()!==""){
			
			layer.msg($("#msg").val());
			}
		setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
		</script>
	</body>
</html>
