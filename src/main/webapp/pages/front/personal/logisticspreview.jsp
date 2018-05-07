<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>专线管理</title>
		<%@ include file="common/include.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/publication.css?v=${version}"/>
	 	<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
		
	</head>
	<body>
	<%@ include file="common/head.jsp"%>
		<!--中间内容的部分-->
		
	<div class="content">
		<%@ include file="common/left.jsp"%>
			<div class="content-right">
			        <ul>
						<li style="margin-right: 40px;" class='<c:if test="${curr eq 6}">active</c:if>'><a href="${ctx}/front/userconter/line">专线管理</a></li>
						<li><a class='<c:if test="${curr eq 9}">active</c:if>' href="${ctx}/front/line/preview">发布预览</a></li>
					</ul>
				<div class="content-right-top">
					<c:if test="${not empty page}">
					<p>过往发布的专线列表</p>
					<table cellspacing="0">
						<tr>
							<th style="color:#fff;">出发</th>
							<th>出发地</th>
							<th>到达地</th>
							<!-- <th>公司名称</th> -->
							<th>重货价/公斤</th>
							<th>轻货价/立方</th>
							<th>时效性</th>
							<th>发车频次</th>
							<th>所属网点</th>
							<th>操作</th>
						</tr>
						
						<c:forEach items="${page}" var="item">
						<tr>
						    <td class="btn"><c:if test="${item.is_sale}">
								<img src="${ctx}/static/pc/img/te.gif"/>
							</c:if></td>
							<td><a style="display:inline-block; width: 100px; height:40px; line-height:55px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" title="${item.from_addr}">${item.from_addr}</a></td>
							<td><a style="display:inline-block; width: 100px; height:40px; line-height:55px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" title="${item.to_addr}">${item.to_addr}</a></td>
							<%-- <td>${item.corpname }</td> --%>
							<td>${item.price_heavy }元/公斤</td>
							<td>${item.price_small }元/立方</td>
							<td>${item.survive_time }小时</td>
							<td>${item.frequency }天1次</td>
							<td><a style="display:inline-block; width: 145px; height:40px; line-height:55px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" title="${item.netadd}">${item.netadd}</a></td>
							<td><a id="gtn" href="${ctx}/front/line/preview/getLine?id=${item.id}">修改</a><a id="gtns" href="javascript:deleteline(${item.id})">删除</a></td>
						</tr>
                        </c:forEach>
					</table>
					</c:if>
					<c:if test="${empty page}">
				     	<div style="margin-top: 200px; text-align: center; font-size: 20px; color : #323232;">暂无数据</div>
					</c:if>
			</div>
		</div>
		</div>
		<!--底部的内容--->
			<%@ include file="../common/loginfoot.jsp"%>
			<script type="text/javascript">
			function deleteline(objs) {
				//alert(objs);
				layer.confirm('您确定要删除？', {
				  	btn: ['删除','取消']
				}, function(){
					$.ajax({
						type : "post",
						dataType : "html",
						url : "${ctx }/front/line/preview/delLine?lineid="+objs,
						success : function(data) {
							var obj = new Function("return" + data)();
							if (obj.state == "SUCCESS") {
								window.location.href="${ctx}/front/line/preview";
							} else {
								layer.msg(obj.msg);
							}
						}
					});
				}, function(){});
			}
			
			</script>
	</body>
</html>

