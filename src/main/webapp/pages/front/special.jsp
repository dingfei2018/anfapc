<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title>特价专线</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/speciat.css?v=${version}" />
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
		<%@ include file="common/loginhead.jsp" %>
		<div class="content">
		<p class="p"><a href="${ctx}/index">首页</a> > 特价专线</p>
		<form action="${ctx}/front/line/specialLine" method="post" id="myform">
		  <div class="content-top">
		     <span class="span" id="startId">出发地 : 
		     <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select>
		     	<script type="text/javascript">
    	             $("#startId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty fromRegionCode?fromCityCode:fromRegionCode}"});
		        </script>
		     </span>
		     <span class="span2" id="endId">到达地 : 
		     <select name="province"></select>
                    <select name="city"></select><!-- 
                    <select name="area"></select> -->
		     	<script type="text/javascript">
     	           /*   $("#endId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty toRegionCode?toCityCode:toRegionCode}"}); */
     	             $("#endId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${toCityCode}"});
			    </script>
		     </span>
		             <input type="hidden" id="fromCityCode" name="fromCityCode" >
		             <input type="hidden" id="fromRegionCode" name="fromRegionCode" >
		             <input type="hidden" id="toCityCode" name="toCityCode">
		             <input type="hidden" id="toRegionCode" name="toRegionCode">
		     
		      <button class="button" onclick="search();">查询</button> 
		  </div>
		  </form>
		  <c:if test="${fn:length(page.list)>0}">
		  <table cellspacing="0">
		     <tr>
		        <th>&nbsp;</th>
		        <th>出发地</th>
		        <th style="width:0px;"><img src="${ctx }/static/pc/img/icon_09.png" style="display: none;"/></th>
		        <th>到达地</th>
		        <th>物流公司名称</th>
		        <th>所在物流园</th>
				<th >起步价</th>
				 <c:if test="${empty orderby && empty column }">
		        <th onclick="sort('price_heavy','asc')" id="NormalCsss"  style="cursor: pointer;">重货价/公斤</th>
		        </c:if>
		        <c:if test="${orderby  eq 'desc'}">
				<th onclick="sort('price_heavy','asc')" id="${column eq 'price_heavy'? 'SortAscCsss':'NormalCsss'}" class="content-th" style="cursor: pointer;">重货价/公斤</th>
				</c:if>
				<c:if test="${orderby  eq 'asc'}">
				<th onclick="sort('price_heavy','desc')" id="${column eq 'price_heavy'? 'SortDescCsss':'NormalCsss'}" class="content-th" style="cursor: pointer;">重货价/公斤</th>
				</c:if>
				<c:if test="${empty orderby && empty column }">
		        <th onclick="sort('price_small','asc')" id="NormalCssc"  style="cursor: pointer;">轻货价/立方</th>
		        </c:if>
		        <c:if test="${orderby  eq 'desc'}">
				<th onclick="sort('price_small','asc')" id="${column eq 'price_small'? 'SortAscCssc':'NormalCssc'}" class="content-th" style="cursor: pointer;">轻货价/立方</th>
				</c:if>
				<c:if test="${orderby  eq 'asc'}">
				<th onclick="sort('price_small','desc')" id="${column eq 'price_small'? 'SortDescCssc':'NormalCssc'}" class="content-th" style="cursor: pointer;">轻货价/立方</th>
				</c:if>
				<th>发车频次</th>
				
		        <th>操作</th>
		     </tr>
		     <c:forEach items="${page.list}" var="line" varStatus="vs">
		     <tr>
		        <td><img src="${ctx }/static/pc/img/te.gif"></td>  
		        <td>${line.from_addr}</td>
		        <td><img src="${ctx }/static/pc/img/icon_10.png" /></td>
		        <td>${line.to_addr}</td>
		        <td><a href="${ctx}/company?id=${line.company_id}" target="_blank">${line.corpname}</a></td>
		        <td>${line.parkname}</td>
		        <td>${line.starting_price=='0.00'?'--':line.starting_price}${line.starting_price=='0.00'?'':'元'}</td>
		        <td class="color-yellow" style="color: #ff7801;">${line.price_heavy=='0.00'?'面议':line.price_heavy}${line.price_heavy=='0.00'?'':'元/公斤'}</td>
		        <td class="color-yellow" style="color: #ff7801;">${line.price_small=='0.00'?'面议':line.price_small}${line.price_small=='0.00'?'':'元/立方'}</td>
		        <td>1天${line.frequency}次</td>
		        <%-- <td><a href="${ctx }/front/goods?orderid=${line.id}&fromcitycode=${line.from_city_code}&fromregioncode=${line.from_region_code}&tocitycode=${line.to_city_code}&toregioncode=${line.to_region_code}" class="content-a">发货</a></td> --%>
		        <td><a href="${ctx }/company/special?id=${line.company_id}" class="content-a">查看</a></td>
		     </tr>
		     </c:forEach>
		  </table>
		  <div id="page" style="text-align: center;">
				</div>
		  </c:if>
		  <c:if test="${fn:length(page.list)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p></p>暂无数据
				</div>
			</c:if>
        </div>
		<%@ include file="common/loginfoot.jsp" %>
	    <script type="text/javascript">
		function search(){
			var fromCityCode = $("#startId").find("select[name=city]").val();
			var fromRegionCode = $("#startId").find("select[name=area]").val();
			var toCityCode = $("#endId").find("select[name=city]").val();
			var toRegionCode = $("#endId").find("select[name=area]").val();
			$("#fromCityCode").val(fromCityCode);
			$("#fromRegionCode").val(fromRegionCode);
			$("#toCityCode").val(toCityCode);
			$("#toRegionCode").val(toRegionCode);
		    document.getElementById("myform").submit(); 
		}
	    //排序
	    function sort(column,orderby){
		   var fromCityCode='${fromCityCode}';
		   var fromRegionCode='${fromRegionCode}';
		   var toCityCode='${toCityCode}';
		   var toRegionCode='${toRegionCode}';
		   var curr='${page.pageNumber}';
		   window.location.href="${ctx}/front/line/specialLine?pageNo="+curr+"&fromCityCode="+fromCityCode+"&fromRegionCode="+fromRegionCode+"&toCityCode="+toCityCode+"&toRegionCode="+toRegionCode+"&column="+column+"&orderby="+orderby;
	    }
	  
	    //分页
	    layui.use(['laypage'], function(){
	    	var laypage = layui.laypage;
	    	var fromCityCode='${fromCityCode}';
	    	var fromRegionCode='${fromRegionCode}';
	    	var toCityCode='${toCityCode}';
	    	var toRegionCode='${toRegionCode}';
	    	var orderby='${orderby}';
	    	var column='${column}';
	        //调用分页
	        laypage({
	    	      cont: 'page'
	    	      ,pages: '${page.totalPage}' //得到总页数
	    	      ,curr:'${page.pageNumber}'
	    	      ,skip: true
	        	  ,jump: function(obj, first){
	        	      if(!first){
	        	    	   window.location.href="${ctx}/front/line/specialLine?pageNo="+obj.curr+"&fromCityCode="+fromCityCode+"&fromRegionCode="+fromRegionCode+"&toCityCode="+toCityCode+"&toRegionCode="+toRegionCode+"&column="+column+"&orderby="+orderby;
	        	      }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	     });
		</script>
		
	</body>
	
</html>
