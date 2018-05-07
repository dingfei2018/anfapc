<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>网点管理</title>
<link rel="stylesheet"
	href="${ctx}/static/pc/Personal/css/preview.css?v=${version}" />
<%@ include file="../personal/common/include.jsp"%>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
</head>
<body>
	<%@ include file="../personal/common/head.jsp"%>
	<!--中间内容的部分-->
	<div class="content">
		<%@ include file="../personal/common/left.jsp"%>
		<div class="content-right">
			<!-- <p class="content-right-p">位置：<b>个人中心<strong>></strong> 网点管理</b></p> -->
			<div class="content-right-top">
				<ul>
					<li style="margin-right: 40px;"><a href="${ctx}/front/branch">网点管理</a></li>
					<li><a class='<c:if test="${curr eq 10}">active</c:if>' href="${ctx}/front/branch/preview">发布预览</a></li>
				</ul>
				<c:if test="${not empty page.list}">
					<p>过往发布的网点列表</p>
					<table cellspacing="0">
						<tr>
							<th name="name" class="sorting">网点地址</th>
							<th>详细地址</th>
							<th>联系人</th>
							<th>联系电话</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.region}</td>
								<td><a style="display: inline-block; width: 260px; line-height:24px; font-size: 14px; word-wrap: break-word;">${item.address}</a></td>
								<td>${item.sub_leader_name}</td>
								<td><a style="display: inline-block; width: 260px; line-height:24px; font-size: 14px; word-wrap: break-word;">${empty item.sub_logistic_telphone?item.sub_logistic_mobile:item.sub_logistic_telphone}</a></td>
								<td><a id="gtn"	href="${ctx}/front/branch/getBranch?id=${item.id}">修改</a>
								<a id="gtns" href="javascript:deletebranch(${item.id})">删除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${empty page.list}">
					<div style="margin-top: 200px; text-align: center; font-size: 20px; color: #323232;">暂无数据</div>
				</c:if>
			</div>
		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp"%>

	<script type="text/javascript">
		function deletebranch(objs) {
			//alert(objs);
			layer
			.confirm(
					'您确定要删除？',
					{
						btn : [ '删除', '取消' ]
					},
					function() {
						$.ajax({
							type : "post",
							dataType : "html",
							url : "${ctx }/front/branch/preview/delBranch?branchid="
									+ objs,
							success : function(data) {
								var obj = new Function("return"
										+ data)();
								if (obj.state == "SUCCESS") {
									window.location.href = "${ctx}/front/branch/preview";
								} else {
									layer.msg(obj.msg);
								}
							}
						});
			}, function() {
					});
		}
	</script>
</body>

</html>
