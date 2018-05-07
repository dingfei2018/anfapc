<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>全部异常</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/abnormals.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
	</head>

	<body>
		<!-- 头部文件 -->
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<!-- 左边菜单 -->
			<%@ include file="../common/abnormalleft.jsp" %> 
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
						<a href="${ctx}/kd/abnormal" class="activet at">全部异常</a>
					</li>
					<li>
						<a href="${ctx}/kd/abnormal/add" class="at">登记异常</a>
					</li>
				</ul>

				<div class="banner-right-list">
				<form id="searchForm">
				<div class="div">
					<span class="span">登记网点：</span>
						 <select name="abnormalNet" id="abnormalNet">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${userNetworks}">
                                <option value="${work.id}">${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
					 </div>
					<div class="div">
					<span class="span">开单网点：</span>
					<select name="shipNet" id="shipNet">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${comNetworks}">
                                <option value="${work.id}">${work.sub_network_name}</option>
                            </c:forEach>
                     </select>
					</div>
					<div class="div">	
				   <span class="span">出发地：</span>
					<div class="banner-right-list-liopd">
						<div class="form-group">
							<div style="position: relative;">
						<input type="hidden" name="fromAddr" >
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
					<div class="banner-right-list-liopc">
						<div class="form-group">
							<div style="position: relative;">
							<input type="hidden" name="toAddr" >
								<input id="city-picker3" class="form-control"
									placeholder="请选择省/市/区" readonly type="text"
									data-toggle="city-picker"/>
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
					<span class="span">异常状态：</span>
					<select name="abnormalStatus">
					 <option value="">请选择 </option>
					<option value="0">待处理</option>
					<option value="1">处理中</option>
					<option value="2">已处理</option>
					</select>
					</div>
					<div class="div">
					<span class="span">异常类型：</span>
					<select name="abnormalType">
					 <option value="">请选择 </option>
					<option value="0">货损</option>
					<option value="1">少货</option>
					<option value="2">多货</option>
					<option value="3">货物丢失</option>
					<option value="4">货单不符</option>
					<option value="5">超重超方</option>
					<option value="6">超时</option>
					<option value="7">拒收</option>
					<option value="8">投诉</option>
					<option value="9">其他</option>
					</select>
					</div>
				   
					<div class="div">
						<span class="span">托运方：</span>
						 <input type="text" name="sender" id="shipperName" />
						</div>
					 <div class="div">
						<span class="span">收货方：</span>
						 <input type="text" name="receiver" id="receivingName"/>
						</div>
						 <div class="div">
						<span class="span">运单号：</span>
						<input type="text" name="shipNet"/>
						</div>>
					<div class="div">
					<button id="search" onclick="return false;">查询</button>
					<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
					</div>
				</form>
				</div>

				<div class="banner-right-list2">
					<ul class="ul2">
						<li>
							<a href="#" class="banner-right-a3 at" id="excelExport">导出EXCLE</a>
						</li>
						<li>
						<div id="page" style="text-align: center;"></div>
					
						</li> 
					</ul>
					<p class="banner-right-p">全部异常列表</p>
					<div style="overflow: auto; width: 100%; " id="loadingId">
						<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="table">
							<thead>
								<th>序号</th>
								<th>异常编号</th>
								<th>运单号</th>
								<th>登记网点</th>
								<th>异常状态</th>
								<th>异常类型</th>
								<th>运单开单网点</th>
								<th>出发地</th> 
								<th>到达地</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>登记人</th>
								<th  class="banner-right-padding">登记日期</th>
								<th class="banner-right-th">操作</th>
							</thead>
						
						</table>
						</div>
					
					</div>
				</div>
			</div>
			<%@ include file="../common/loginfoot.jsp" %>
			<script src="${ctx}/static/kd/js/abnormal/abnormal.js"></script>
			<script type="text/javascript">
			
			function resetCity(){
				$("#city-picker2").citypicker("reset");
				$("#city-picker3").citypicker("reset");
				
				$('#shipperName').combogrid('clear');
				$('#receivingName').combogrid('clear');
			}
			
			function openAbnormalDiv(abnormalId){
		        //页面层
		        layer.open({
		            type: 2,
		            area: ['850px', '700px'], //宽高
		            content: ['/kd/abnormal/goAbnormalDetail?abnormalId='+abnormalId, 'yes']
		        });
		     }
			function openShipDiv(shipid){
		        //页面层
		        layer.open({
		            type: 2,
		            area: ['850px', '700px'], //宽高
		            content: ['/kd/waybill/viewDetail?shipId='+shipid, 'yes']
		        });
		     }
			
			function openFollowUpDiv(abnormalId,abnormalStatus){
				if(abnormalStatus==2){
					layer.msg('异常已处理，不能再登记跟进信息');
					return;
				}
				//页面层
		        layer.open({
		            type: 2,
		            area: ['850px', '700px'], //宽高
		            content: ['/kd/abnormal/goFollowUp?abnormalId='+abnormalId, 'yes'],
		            end:function (){
		            	window.location.reload();
		            }
		        });
			}
			</script>
	</body>
</html>