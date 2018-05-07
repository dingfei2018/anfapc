<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>出库记录</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/record.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/kucun-out.js?v=${version}"></script>
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
			     });
	       </script>
			<div class="banner-right">
				<ul>
					<li>
						<a href="${ctx}/kd/kucun" class="at">库存查询</a>
					</li>
					<li>
						<a href="#" class="actives at">出库记录</a>
					</li>
				</ul>
				<div class="banner-right-list">
					<form id="searchFrom" onsubmit="return false;">
					<div class="div">
					<span class="span">库存网点：</span>
					<select  name="loadnetworkId">
						<option value="0">请选择网点</option>
						<c:forEach items="${networks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
					</select>
					</div>
					<div class="div">
					<span class="span">开单网点：</span>
					<select name="networkId">
						<option value="0">请选择网点</option>
						<c:forEach items="${allnetWorks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
					</select>
					</div>
					<div class="div">
					<span class="span">入库日期：</span><input type="text"  name="startTime"  onClick="WdatePicker()"/>
					</div>
					
					<div class="div">
					<span class="span">出库日期：</span><input type="text" name="endTime"  onClick="WdatePicker()"/>
					</div>
					<div class="div">
					<span class="span">出发地：</span>
					<div class="banner-right-list-liopc">
						
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
					<div class="banner-right-list-liopd">
						
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
					<span class="span">托运方：</span><input type="text" name="sendName"  id="senderId"/>
					</div>
					<div class="div">
					<span class="span">收货方：</span><input type="text" name="receiveName" id="receiveId"/>
					</div>					
					<div class="div">
					<span class="span">运单号：</span><input  class="input2" type="text"  name="shipSn"/>
				   </div>
				    <div class="div">
				    <button id="search">查询</button>
				    <input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
				    </div>
					</form>
				</div>

				<div class="banner-right-list2">
				<ul class="ul2">
						<li>
							<a href="#" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
						</li>
					</ul>
					<p class="banner-right-p">出库列表</p>
					<div style="overflow: auto; width: 100%; " id="loadingId">
						<table border="0" class="tab_css_1" id="loadId">
							<thead>
								<th>序号</th>
								<th>运单号</th>
								<th>库存网点</th>
								<th>入库时间</th>
								<th>出库时间</th>
								<th>在库时长（小时）</th>
								<th>货物名称</th>
								<th>体积</th>
								<th>重量</th>
								<th>件数</th>
								<th>开单网点</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>出发地</th>
								<th>到达地</th>
							</thead>
						</table>
						<div id="page" style="text-align: center;">
						</div>
					</div>
							
				</div>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
