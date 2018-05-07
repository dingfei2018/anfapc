<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>收货签收</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/stomer.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/sign.js?v=${version}"></script>
	</head>
	<body>
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/fahuoleft.jsp" %>
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
			    <div class="banner-right-list">
			    <form id="searchFrom" onsubmit="return false;">
			      
					<div class="div">
					<span class="span">开单日期：</span><input type="text" name="startTime" onClick="WdatePicker()"/>
					</div>
					
					<div class="div">
					<span class="span">出发地：</span>
					<div class="banner-right-list-liopd">
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" name="startCode">
								<input id="city-picker3" class="form-control"
									placeholder="请选择省/市/区" readonly type="text"
									data-toggle="city-picker">
							</div>
							<script>
								$(function(){
									$(".city-picker-span").css("width","auto");
								})
							</script>
						</div>
					</div>
					</div>
					<div class="div">
					<span class="span">到达地：</span>
					<div class="banner-right-list-liopc">
						
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" name="endCode">
								<input id="city-picker2" class="form-control"
									placeholder="请选择省/市/区" readonly type="text"
									data-toggle="city-picker">
							</div>
							<script>
								$(function(){
									$(".city-picker-span").css("width","auto");
								})
							</script>
						</div>
					</div>
					</div>
					<div class="div">
					<span class="span" >托运方：</span><input type="text"  name="sendName" id="senderId"/><br />
					</div>
					<div class="div">
					
						<span class="span">收货方：</span><input type="text"  name="receiveName"  id="receiveId"/>						
					</div>
					 <div class="div">
					<span class="span">运单号：</span><input type="text" name="shipSn"/>
					</div>
					<div class="div">
					<button id="search">查询</button>
					<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
					</div>
					<!-- <div class="div">
					    
					</div> -->
					</form>
					
				</div>
				<div class="banner-right-list2">
				<ul class="ul2">
					<li style="float: right;margin-right: 10px;">
						<div id="page" style="text-align: center;"></div>
					</li>
				</ul>
					<p class="banner-right-p">签收列表</p>
					<div style="overflow: auto; width: 100%;" id="loadingId">
						<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
							<thead>
<!-- 							<th><input type="checkbox" style="display: none;"/></th> -->
								<th>序号</th>
								<th>运单号</th>
								<th>开单网点</th>
								<th>开单日期</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>体积</th>
								<th>重量</th>
								<th class="banner-right-padding">件数</th>
								<th class="banner-right-th1" >操作</th>
							</thead>
						</table>
						
					</div>
					<%--<ul class="ul2">
						<li>
							<a href="#" class="banner-right-a3 at" style="background: #fff;text-decoration:none;border: none; color:#FF7801;">注意，确认到货后将不能取消</a>
						</li>
					</ul>--%>
				</div>
			</div>
		</div>
		 <%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>