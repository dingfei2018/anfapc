<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>发车列表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/departure.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/load/loadlist.js?v=${version}"></script>
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
				<ul>
					<li><a href="${ctx}/kd/loading" class="at">干线配载</a></li>
					<li><a href="${ctx}/kd/loading/tihuo" class="at">提货配载</a>
					<li><a href="${ctx}/kd/loading/duanbo" class="at">短驳配载</a>
					<li><a href="${ctx}/kd/loading/songhuo" class="at">送货配载</a>
					<li><a href="${ctx}/kd/loading/loadlist" class="activet at">发车列表</a>
					</li>
				</ul>

				<div class="banner-right-list">
					<form onsubmit="return false;" id="searchFrom">
						<div class="div">
						<span class="span">配载网点：</span>
						<select name="networkId">
							<option value="0">请选择网点</option>
							<c:forEach items="${networks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
						</select>
						</div>
						<div class="div">
						<span class="span">到货网点：</span>
						<select name="loadToNetworkId">
							<option value="0">请选择网点</option>
							<c:forEach items="${tonetWorks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
						</select>
						</div>
						<div class="div">
							<span class="span">车牌号：</span><input class="input3" type="text" name="truckNumber" />			
							</div>
						<div class="div">
						<span class="span">司机：</span><input type="text" name="driverName"/>
						</div>
						<div class="div">
						<span class="span">配载状态：</span>
						<select name="status">
							<option value="0">请选择</option>
							<option value="1">配载中</option>
							<option value="2">已发车</option>
							<option value="3">已到达</option>
							<option value="4">已完成</option>
						</select>
						</div>
						<div class="div">
						<span class="span">运输类型：</span>
						<select name="transType">
							<option value="0">请选择</option>
							<option value="1">提货</option>
							<option value="2">短驳</option>
							<option value="3">干线</option>
							<option value="4">送货</option>
						</select>
						</div>
						<div class="div">
							<span class="span">出发地：</span>
							<div class="banner-right-list-liopc">
							
							<div class="form-group">
								<div style="position: relative;">
									<input type="hidden" name="deliveryFrom">
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
							<span class="span">到达地：</span>
							<div class="banner-right-list-liopd">
							
								<div class="form-group">
								<div style="position: relative;">
									<input type="hidden" name="deliveryTo">
									<input id="city-picker2" class="form-control" placeholder="请选择省/市/区" readonly type="text" data-toggle="city-picker">
								</div>
								<script>
									$(function(){
										$(".city-picker-span").css("width","auto");
									});
								</script>
							</div>
						</div>
						
						</div>
						<div class="div">
						<span class="span">发车日期：</span><input type="text" name="time"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</div>						
																	
						<div class="div">
							<span class="span">配载单号：</span><input class="input" type="text"  name="loadSn" />	
						</div>											
						<div class="div">
							<button id="search">查询</button>
							<input class="buttons" type="reset"  onclick="resetCity();" value="重置"/>
						</div>	
						
						<!-- <div class="div">
							
						</div>	 --> 
					</form>
					</div>
				

				<div class="banner-right-list2">
				<ul class="ul2">
						<li>
							<a href="#" class="banner-right-a3 at" data="send">发车</a>
						</li>	
						<li>
							<a href="#" class="banner-right-a3 at" data="tihuo" >提货完成</a>
						</li> 
						<li>
							<a href="#" class="banner-right-a3 at" data="songhuo" >送货完成</a>
						</li>
						<li>
							<a href="#" class="banner-right-a3 at" data="mdfee">修改费用</a>
						</li>
						<li>
							<a href="javascript:;" class="banner-right-a3 at" id="btn" data="truck">更换车辆</a>
						</li>
						<li>
							<a href="javascript:;" class="banner-right-a3 at" id="btns" data="network">修改到货网点</a>
						</li>
						<li>
							<a href="javascript:;" class="banner-right-a3 at" data="add">配载加单</a>
						</li>
						<li>
							<a href="javascript:;" class="banner-right-a3 at" data="reduce">配载减单</a>
						</li>
						<li>
							<a href="javascript:;" class="banner-right-a3 at" data="remove">删除配载</a>
						</li>
						<li>
							<a href="javascript:;" class="banner-right-a3 at" data="export" >导出EXCEL</a>
						</li>
						<li style="float: right;margin-right: 10px;">
							<div id="page" style="text-align: center;">
						</li>
					</ul>
					<p class="banner-right-p">发车列表</p>
					<div style="overflow: auto; width: 100%; " id="loadingId">
						<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
							<thead>
								<th><label for="chooseall">全选</label><input type="checkbox" id="chooseall" name="chooseall"/></th>
								<th>序号</th>
								<th>配载单号</th>
								<th>配载网点</th>
								<th>到货网点</th>
								<th>状态</th>
								<th>发车日期</th>
								<th>车牌号</th>
								<th>司机</th>
								<th>司机电话</th>
								<th>运输类型</th>
								<th>费用</th>
								<th>出发地</th>
								<th>到达地</th>								
								<th>件数</th>
								<th>体积</th>
								<th class="banner-right-padding">重量</th>
								<th class="banner-right-th1">操作</th>
							</thead>
						</table>
						
						</div>
					</div>
					
				</div>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>