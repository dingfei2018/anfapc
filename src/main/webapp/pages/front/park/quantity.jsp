<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title>查看货源数量</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/quantity.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/js/search.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>

	<body>

		<%@ include file="../common/loginhead.jsp" %>
		<div class="content">
		  <p class="p"><a href="${ctx}/index">首页</a> > 查看${parkname}成交量</p>
		  <form action="${ctx}/park/searchParkOrders" method="post">
		  <div class="content-top">
		     <span class="span" id="startId">出发地 : 
		     <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select>
		     </span>
		    
		      <script type="text/javascript">
		      $("#startId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty fromRegionCode?fromCityCode:fromRegionCode}"});
		            </script>
		     <span class="span2" id="endId" >到达地 : 
		     
		     <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select>
		     </span>
		     
		      <script type="text/javascript">
		      $("#endId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty toRegionCode?toCityCode:toRegionCode}"});
		            </script>
		             <input type="hidden" id="parkId" name="parkId" value="${parkId}">
		              <input type="hidden" id="fromCityCode" name="fromCityCode" >
		              <input type="hidden" id="fromRegionCode" name="fromRegionCode" >
		               <input type="hidden" id="toCityCode" name="toCityCode">
		              <input type="hidden" id="toRegionCode" name="toRegionCode">
		     <button class="button" onclick="search();">搜索</button>
		  </div>
		  </form>
		  <table cellspacing="0">
		     <tr>
		        <th>序号</th>
		        <th>订单号</th>
		        <th>出发地</th>
		        <th style="width:0px;"><img src="${ctx }/static/pc/img/icon_09.png" style="display: none;"/></th>
		        <th>到达地</th>
		        <th>承运公司</th>
		        <th>所在物流园</th>
		        <th>交易时间</th>
		     </tr>
		      <c:forEach items="${page.list}" var="order" varStatus="vs">
		     <tr>
		        <td>${vs.index+1}</td>
		        <td>${order.order_number}</td>
		        <td>${order.fromadd}</td>
		         <td><img src="${ctx }/static/pc/img/icon_10.png" /></td>
		        <td>${order.toadd}</td>
		        <td>${order.comname}</td>
		        <td>${order.parkname}</td>
		        <td>${order.create_time}</td>
		     </tr>
		     </c:forEach>
		  </table>
		   <div id="page" style="text-align: center;">
				</div>	
           </div>
				<c:if test="${fn:length(page.list)==0}">
				  <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				</div>
			</c:if>
		<%@ include file="../common/loginfoot.jsp" %>
	  <script type="text/javascript">
		function search(){
			var fromCityCode = $("#startId").find("select[name=city]").val();
			var  fromRegionCode = $("#startId").find("select[name=area]").val();
			 var toCityCode = $("#endId").find("select[name=city]").val();
			var  toRegionCode = $("#endId").find("select[name=area]").val();
			$("#fromCityCode").val(fromCityCode);
			$("#fromRegionCode").val(fromRegionCode);
			$("#toCityCode").val(toCityCode);
			$("#fromRegionCode").val(fromRegionCode);
		    document.getElementById("myform").submit(); 
		}
	  
	    layui.use(['laypage'], function(){
	    	var laypage = layui.laypage;
	    	var fromCityCode='${fromCityCode}';
	    	var fromRegionCode='${fromRegionCode}';
	    	var toCityCode='${toCityCode}';
	    	var toRegionCode='${toRegionCode}';
	    	var parkId='${parkId}';
	        //调用分页
	        laypage({
	    	      cont: 'page'
	    	      ,pages: '${page.totalPage}' //得到总页数
	    	      ,curr:'${page.pageNumber}'
	    	      ,skip: true
	        	  ,jump: function(obj, first){
	        	      if(!first){
	        	    	  window.location.href="${ctx}/park/searchParkOrders?parkId="+parkId+"&pageNo="+obj.curr+"&fromCityCode="+fromCityCode+"&fromRegionCode="+fromRegionCode+"&toCityCode="+toCityCode+"&toRegionCode="+toRegionCode;
	        	      }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	    });
	    
		</script>
		
	</body>
	
</html>
