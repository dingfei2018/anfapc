<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>配载减单</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/reduce.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/src/loading.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
	    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/loadreduce.js?v=${version}"></script>
		<script src="${ctx}/static/pc/src/loading.js"></script>
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
		<style>
		   .city-picker-span{
		      width: 153px !important;
		   }
		</style>
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
					<li>
						<a href="${ctx}/kd/loading">装车配载</a>
					</li>
					<li>
						<a href="${ctx}/kd/loading/loadlist" class="activet at">发车列表</a>
					</li>
				</ul>
				<br />
				<p class="banner-right-title">减少配载（单号：<span>${load.load_sn}</span>）</p>
				<input type="hidden" id="tloadId" value="${load.load_id}">
				<div class="banner-right-list2">
					<ul class="ul2"> 
						
						<li class="ul2-li">
							<a href="javascript:;" class="banner-right-a3"  id="upAll">减单</a>
						</li>
						<li><div id="page" style="text-align: center;">
						</div></li> 
					</ul>
					<p class="banner-right-p">减单列表</p>
					<div style="overflow: auto; width: 100%;">
						<table border="0" class="tab_css_1"  id="loadId">
							<thead>
								<th><label for="checkbox">全选</label><input type="checkbox" id="checkbox" class="selectAll"/></th>
								<th>序号</th>
								<th>运单号</th>
								<th>开单网点</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>体积</th>
								<th>重量</th>
								<th class="banner-right-padding">件数</th>
								<th class="banner-right-th1">操作</th>
							</thead>
						</table>
						
					</div>
					
				</div>
				
				<div class="banner-right-list2">
					<p class="banner-right-p">减单信息</p>
					<div style="overflow: auto; width: 100%;">
						<table border="0" class="tab_css_1"  id="unloadId">
							<thead>
								<th><input type="checkbox" style="display: none;"/></th>
								<th>序号</th>
								<th>运单号</th>
								<th>开单网点</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>体积</th>
								<th>重量</th>
								<th class="banner-right-padding">件数</th>
								<th class="banner-right-th1">操作</th>
							</thead>
						</table>
						
					</div>
					<ul class="ul2">
						<li><input type="checkbox"  class="selectAll"/>全选</li>
						<li class="ul2-li">
							<a href="javascript:;" class="banner-right-a3" id="removeAll">恢复</a>
						</li>
					</ul>
				</div>

               <div class="banner-right-list3">
               	 <p class="banner-right-p">司机信息</p>
               	    <input type="hidden" name="truck_id">
                	<span>车牌号：</span><input type="text" readonly="readonly" value="${load.truck_id_number}"/>
					<span>司机：</span><input type="text" readonly="readonly" value="${load.truck_driver_name}"/>
					<span>司机电话：</span><input type="text" readonly="readonly" value="${load.truck_driver_mobile}"/><br />
               </div>

               <div class="banner-right-list4">
               	   <p class="banner-right-p">费用</p>
               	   <span>运输类型 : </span>
               	   <c:if test="${load.load_transport_type==1}">
               	   		<input type="text" readonly="readonly" value="干线"/>
               	   </c:if>
               	   <c:if test="${load.load_transport_type==2}">
               	   		<input type="text" readonly="readonly" value="提货"/>
               	   </c:if>
               	   <c:if test="${load.load_transport_type==3}">
               	   		<input type="text" readonly="readonly" value="短驳"/>
               	   </c:if>
               	   <c:if test="${load.load_transport_type==4}">
               	   		<input type="text" readonly="readonly" value="送货"/>
               	   </c:if>
               	   <span>运费 : </span><input type="text"  readonly="readonly" value="${load.load_fee}"/>
               	   <span>分摊方式 : </span>
               	   <c:if test="${load.load_fee_allocation_way==1}">
               	   		<input type="text" readonly="readonly" value="按单"/>
               	   </c:if>
               	   <c:if test="${load.load_fee_allocation_way==2}">
               	   		<input type="text" readonly="readonly" value="按体积"/>
               	   </c:if>
               	   <c:if test="${load.load_fee_allocation_way==3}">
               	   		<input type="text" readonly="readonly" value="按重量"/>
               	   </c:if>
               </div>
               
               <div class="banner-right-list5">
               	   <p class="banner-right-p">发车信息</p>
               	   <span class="span">配载网点 : </span>
               	   <input type="text" readonly="readonly" value="${load.snetworkName}">
				   <span class="span4">出发地：</span>
				   <input type="text" readonly="readonly" value="${load.fromCity}">
               	   <span>到货网点 : </span>
               	   <input type="text" readonly="readonly" value="${load.enetworkName}">
				   <span class="span4">到达地：</span>
				   <input type="text" readonly="readonly" value="${load.toCity}"><br />
               	   <span class="span">发车时间 : </span><input type="text" readonly="readonly" value="<fmt:formatDate value="${load.create_time}" pattern="yyyy-MM-dd"/>">
               	   <div class="banner-right-list5-div">
               	    <span>备注 : </span><textarea  name="load_remark" maxlength="45">${load.load_remark}</textarea>
               	   </div>
               </div>
               
               <div class="banner-right-list6">
               	 <button class="banner-right-list6-list" id="comfirmReduce">确定减单</button>
               </div>
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>