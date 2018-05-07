<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/pointslater.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/citys.js"></script>
		<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
	</head>
	<body>
		<!--头部-->
	    <%@ include file="common/loginhead.jsp" %>
		<div class="content">
			<div class="content-left">
				<ul>
					<li class="content-lefts"><span>服务星级</span><img class="content-lefts-imgsu" src="${ctx}/static/pc/img/line.png"/><img class="content-lefts-imgs" src="${ctx}/static/pc/img/jiantou.png"/></li>
					<li><span>运费</span><img class="content-lefts-imgsu" src="${ctx}/static/pc/img/line.png"/><img class="content-lefts-imgs" src="${ctx}/static/pc/img/jiantou.png"/></li>
			    </ul>
			</div>
			<div class="content-right citys" id="location">
				    <span>目的地：</span>
				    <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select>
                     <script type="text/javascript">
		                if(remote_ip_info){
		                    $('#location').citys({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
		                }
		            </script>
				<span>请输入车牌号: </span><input type="text" class="content-input" />
				<img src="${ctx}/static/pc/img/icon_search.png" />
			</div>
			<div class="content-centent">
				<table class="content-table" cellspacing="0">
					<tr>
						<th class="content-th">序号</th>
						<th class="content-th">出发地</th>
						<th class="content-th">到达地</th>
						<th class="content-th">服务星级</th>
						<th class="content-th">司机类型</th>
						<th class="content-th">姓名</th>
						<th class="content-th">车牌号</th>
						<th class="content-th">时效性</th>
						<th class="content-th">运费(元)</th>
						<th class="content-th">联系电话</th>
						<th class="content-th">操作</th>
					</tr>
					<tr>
						<td class="content-td">1</td>
						<td class="content-td">广州 白云区</td>
						<td class="content-td">北京 朝阳区</td>
						<td class="content-td">
						<img src="${ctx}/static/pc/img/star1.png"/>
						<img src="${ctx}/static/pc/img/star1.png"/>
						<img src="${ctx}/static/pc/img/star1.png"/>
						<img src="${ctx}/static/pc/img/star1.png"/>
						<img src="${ctx}/static/pc/img/star1.png"/></td>
						<td class="content-td">个体司机</td>
						<td class="content-td">刘师傅</td>
						<td class="content-td">粤A2526D</td>
						<td class="content-td">2小时</td>
						<td class="content-td">1500</td>
						<td class="content-td">13546849221</td>
						<td class="content-td"><a href="#">发货</a></td>
					</tr>
				</table>
			</div>
			
			<div class="content-centent-bottom">
		         <ul>
		            <li><input type="button" class="content-centent-bottompo"></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		            <li><a href="#">1</a></li>
		         </ul>
		         <input type="button" value="" class="content-centent-bottoms"> 
		         <span class="content-centent-bottomu">共11页 到第
		         <input type="text" class="content-centent-bottomus"> 页 
		         <input type="button" value="确定" class="content-centent-bottomusy"></span>
			</div>
		</div>
		
		<%@ include file="common/loginfoot.jsp" %>
	</body>
</html>
