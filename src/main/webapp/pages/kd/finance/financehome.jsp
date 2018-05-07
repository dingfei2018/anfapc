<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>财务首页 </title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/finance2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/financehome.js"></script>
	</head>
	<body>
	   <%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
		<%@ include file="../common/financialleft.jsp" %>
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
			<div class="banner-list">
				<div class="banner-right-list">
					<form onsubmit="return false;"  id="searchFrom">
					<span>网点：</span>
					 <select name="networkId" id="netWorkId">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}">${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
					<span>搜索日期：</span><input type="text" name="startTime" id="startTime" class="Wdate" value="${firstDay}"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})" />
					<span class="span3">至</span><input type="text" class="Wdate" name="endTime" id="endTime" value="${currDay}"    onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})" />
					<button id="search">搜索</button>
					</form>
				</div>
				<div class="banner-right-list2">
					<p class="p">数据总览</p>
					<div class="banner-right-list2-left" style="margin-left: 20px;">
						
						<div class="banner-right-list2-left-bottoms">
							<span>营业额</span><br />
							<b id="totalfeeId">0元</b><br />
						</div>
					</div>
					
					<div class="banner-right-list2-left">
						<div class="banner-right-list2-left-top">
							<span>运输成本</span><br />
							<b id="totalOutfeeId">0元</b>
						</div>
						<div class="banner-right-list2-left-bottom">
						</div>
					</div>
					
					<div class="banner-right-list2-left">
						
						<div class="banner-right-list2-left-bottoms">
							<span>毛利</span><br />
							<b id="prefeeId">0元</b><br />
						</div>
					</div>
					
					<div class="banner-right-list2-left">
						
						<div class="banner-right-list2-left-bottoms">
							<span>毛利率</span><br />
							<b id="rateId">0%</b><br />
						</div>
					</div>
				</div>
				
					<div class="banner-right2">
					<p class="banner-right-p">线路毛利总表</p>
					<div style="overflow: auto; " id="loadingId">
						<table border="1" class="tab_css_1" id="loadId">
							<thead>
							    <td><input type="checkbox" style="display: none;"></td>
								<th>序号</th>
								<th>出发地</th>
								<th>到达地</th>								
								<th>运单量</th>
								<th>体积</th>
								<th>重量</th>
								<th>营业额</th>
								<th>成本</th>
								<th>毛利</th>
								<th>毛利率</th>
							</thead>
						</table>
						<div id="page" style="text-align: center;">
						</div>
	            	</div>
				</div>
			</div>
		</div>
	</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
