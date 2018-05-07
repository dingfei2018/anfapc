<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>货物列表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/public.css" />
		<link rel="stylesheet" href="${ctx}/static/kd/css/entry.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	</head>
	<body>
	<%@ include file="../common/head2.jsp" %>
	<%@ include file="../common/head.jsp" %>
	
		<div class="bannero">
		<div class="banner">
			<%@ include file="../common/left.jsp" %>
			<div class="banner-right">
				<div class="banner-right-top">
					<input type="text" placeholder="货物编号" class="banner-right-input"/>
					<input type="text" placeholder="货物名称"/>
					<button class="button">搜索</button>
					<a href="${ctx}/kd/goods/add"><button class="button2"><img src="${ctx}/static/kd/img/button3.png" > 新增货物</button></a>
				</div>
				
				<table cellspacing="0">
					<tr>
						<th>货物编号</th>
						<th>货物名称</th>
						<th>货物单位</th>
						<th>规格</th>
						<th>数量</th>
						<th>体积</th>
						<th>重量</th>
						<th>价格</th>
						<th>操作</th>
					</tr>
					<tr>
						<td>610-21-768-3</td>
						<td>大米</td>
						<td>条</td>
						<td>箱</td>
						<td>5</td>
						<td>15立方</td>
						<td>1500公斤</td>
						<td>16000元</td>
						<td><a class="banner-a" href="${ctx}/pages/kd/goods/add.jsp">修改</a><a class="banner-a2">删除</a></td>
					</tr>
					<tr>
						<td>610-21-768-3</td>
						<td>大米</td>
						<td>条</td>
						<td>箱</td>
						<td>5</td>
						<td>15立方</td>
						<td>1500公斤</td>
						<td>16000元</td>
						<td><a class="banner-a" href="${ctx}/pages/kd/goods/add.jsp">修改</a><a class="banner-a2">删除</a></td>
					</tr>
				</table>
			</div>
		</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
