<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title></title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/circuit.css?v=${version}" />
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
		<form action="${ctx}/front/line/getGoltLineParkList" method="post" id="myform">	
		  <p class="p">当前位置：查看黄金专线数量</p>
		  <div class="content-top">
		     <span class="span" id="startId">出发地 : 
		     <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select>
		     </span>
		     <script type="text/javascript">
		           $("#startId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty fromRegionCode?fromCityCode:fromRegionCode}"});
		     </script>
		     <span class="span2" id="endId">到达地 : 
		     <select name="province"></select>
                    <select name="city"></select><!-- 
                    <select name="area"></select> -->
		     </span>
		      <script type="text/javascript">
		     /*  $("#endId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty toRegionCode?toCityCode:toRegionCode}"}); */
		           $("#endId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${toCityCode}"});
		      </script>
		        <input type="hidden" id="fromCityCode" name="fromCityCode" value="${fromCityCode}">
		        <input type="hidden" id="fromRegionCode" name="fromRegionCode" value="${fromRegionCode}">
		        <input type="hidden" id="toCityCode" name="toCityCode" value="${toCityCode}">
		        <input type="hidden" id="toRegionCode" name="toRegionCode" value="${toRegionCode}" >
		        <input type="hidden" id="parkId" name="parkId" value="${parkId}">
		     <button class="button" onclick="search();">查询</button>
		  </div>
		  </form>
		  <c:if test="${fn:length(page.list)>0}">
		  <table cellspacing="0">
		     <tr>
		        <th>序号</th>
		        <th>出发地</th>
		        <th>到达地</th>
		        <th>物流公司名称</th>
		        <th>详细位置</th>
				<th>起步价</th>
				<c:if test="${empty orderby}">		
		        <th onclick="sort('price_heavy','asc')" id="NormalCsss"  style="cursor: pointer;">重货价/公斤</th>
		        </c:if>
		        <c:if test="${orderby  eq 'desc' }">
				<th onclick="sort('price_heavy','asc')" id="${column eq 'price_heavy'? 'SortAscCsss':'NormalCsss'}"  style="cursor: pointer;">重货价/公斤</th>
				</c:if>
				<c:if test="${orderby  eq 'asc' }">
				<th onclick="sort('price_heavy','desc')" id="${column eq 'price_heavy'? 'SortDescCsss':'NormalCsss'}"  style="cursor: pointer;">重货价/公斤</th>
				</c:if>
				<c:if test="${empty orderby}">	
		        <th onclick="sort('price_small','asc')" id="NormalCssc"  style="cursor: pointer;">轻货价/立方</th>
		        </c:if>
		      <c:if test="${orderby  eq 'desc' }">
			  <th onclick="sort('price_small','asc')" id="${column eq 'price_small'? 'SortAscCssc':'NormalCssc'}"   style="cursor: pointer;">轻货价/立方</th>
			  </c:if>
			<c:if test="${orderby  eq 'asc' }">
			<th onclick="sort('price_small','desc')" id="${column eq 'price_small'? 'SortDescCssc':'NormalCssc'}"   style="cursor: pointer;">轻货价/立方</th>
			</c:if>
			<th >发车频次</th>
		    <th>时效性</th>
		     <th>操作</th>
		     </tr>
		     <c:forEach items="${page.list}" var="line" varStatus="vs">
		     <tr>
		        <td>${vs.index + 1}</td>
		        <td>${line.from_addr}</td>
		        <td>${line.to_addr}</td>
		        <td><a href="${ctx}/company?id=${line.company_id}">${line.corpname}</a></td>
		        <td>${line.address}</td>
		        <c:if test="${line.starting_price=='0.00'}">
		        	<td>--</td>
		        </c:if>
		        <c:if test="${line.starting_price!= '0.00'}">
		        <td>${line.starting_price}元</td>
		        </c:if>
		        <c:if test="${line.price_heavy=='0.00'}">
		        <td>面议</td>
		        </c:if>
		        <c:if test="${line.price_heavy!='0.00'}">
		        <td>${line.price_heavy}元/公斤</td>
		        </c:if>
		        <c:if test="${line.price_small=='0.00'}">
		        <td>面议</td>
		        </c:if>
		        <c:if test="${line.price_small!='0.00'}">
		        <td>${line.price_small}元/立方</td>
		        </c:if>
		        <td>1天${line.frequency}次</td>
		        <td>${line.survive_time==0?'--':line.survive_time}${line.survive_time==0?'':'小时'}</td>
		          <td><a href="${ctx }/company/special?id=${line.company_id}" class="content-a">查看</a></td>
		     </tr>
		     </c:forEach>
		  </table>
				<div id="page" style="text-align: center;">
				</div>
		</c:if>
        </div>
			<c:if test="${fn:length(page.list)==0}">
			<div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
			       <p></p>
			     	暂无数据
			</div>
			</c:if>
		<%@ include file="common/loginfoot.jsp" %>
		<script type="text/javascript">
		//排序
	    function sort(column,orderby){
		   var curr='${page.pageNumber}';
		   window.location.href="${ctx}/front/line/getGoltLineParkList?parkId="+$("#parkId").val()+"&pageNo="+curr+"&fromCityCode="+$("#fromCityCode").val()+"&fromRegionCode="+$("#fromRegionCode").val()+"&toCityCode="+$("#toCityCode").val()+"&toRegionCode="+$("#toRegionCode").val()+"&column="+column+"&orderby="+orderby;
		}
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
	    //分页
	    layui.use(['laypage'], function(){
	    	var laypage = layui.laypage;
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
	        	    	  window.location.href="${ctx}/front/line/getGoltLineParkList?parkId="+$("#parkId").val()+"&pageNo="+obj.curr+"&fromCityCode="+$("#fromCityCode").val()+"&fromRegionCode="+$("#fromRegionCode").val()+"&toCityCode="+$("#toCityCode").val()+"&toRegionCode="+$("#toRegionCode").val()+"&column="+column+"&orderby="+orderby;
	        	      }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	    });
		</script>
	</body>
</html>
