<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>

	<head>
	
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title>按物流园发货</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/delivery.css?v=${version}" />
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
		<form action="${ctx}/park/searchIndexPark" method="post" id="myform">
		  <p class="p"><a href="${ctx}/index">首页</a> > 按物流园发货</p>
		  <div class="content-top">
		     <span class="span">物流园名称 : </span><input class="input" type="text" name="parkName" value="${parkName}" placeholder="请输入物流园名称"/>
		     <span class="span2" id="addCodeId">物流园位置 : 
		     <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select>
		     	<script type="text/javascript">
					       		$("#addCodeId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:"${empty addCode?addcity:addCode}"});
					       </script>
		     </span>
		     <input type="hidden" id="addcity" name="addcity">
			<input type="hidden" id="addCode" name="addCode">
		     <button class="button" onclick="search();">搜索</button>
		     
		  </div>
		  </form>
		    <c:if test="${fn:length(page.list)>0}">
		  <table cellspacing="0">
		     <tr>
		        <th>序号</th>
		        <th>物流园名称</th>
		        <th>所在区/县</th>
		        <th>物流园地址</th>
		        
				<c:if test="${empty orderby}">
		        <th onclick="sort('linecount','asc')" id="NormalCss"  style="cursor: pointer;">专线数量</th>
		        </c:if>
		        <c:if test="${orderby  eq 'desc'}">
				<th onclick="sort('linecount','asc')" id="${column eq 'linecount'? 'SortDescCssd':'NormalCssd'}"  style="cursor: pointer;">专线数量</th>
				</c:if>
				<c:if test="${orderby  eq 'asc'}">
				<th onclick="sort('linecount','desc')" id="${column eq 'linecount'? 'SortAscCssd':'NormalCssd'}" style="cursor: pointer;">专线数量</th>
				</c:if>
		        <c:if test="${empty orderby}">
		        <th onclick="sort('goldcount','asc')" id="NormalCsss"  style="cursor: pointer;">黄金专线</th>
		        </c:if>
		        <c:if test="${orderby  eq 'desc'}">
				<th onclick="sort('goldcount','asc')" id="${column eq 'goldcount'? 'SortDescCsss':'NormalCsss'}"  style="cursor: pointer;">黄金专线</th>
				</c:if>
				<c:if test="${orderby  eq 'asc'}">
				<th onclick="sort('goldcount','desc')" id="${column eq 'goldcount'? 'SortAscCsss':'NormalCsss'}" style="cursor: pointer;">黄金专线</th>
				</c:if>
		        <th>物流园位置</th>
		     </tr>
		     
		  <c:forEach items="${page.list}" var="park" varStatus="vs">
		     <tr>
		        <td>${vs.index+1}</td>
		        <td><a href="${ctx}/front/line/getLineParkList?parkId=${park.id}&tag=2" style="color:#3974f8;">${park.parkName}</a></td>
		        <td>${park.addr}</td>
		        <td>${park.tailAdd}</td>
		        <td><a href="${ctx}/front/line/getLineParkList?parkId=${park.id}&tag=2">${park.linecount}条</a></td>
		        <td><a href="${ctx}/front/line/getGoltLineParkList?parkId=${park.id}&tag=2">${park.goldcount}条</a></td>
		        <td><a class="content-a" target="_blank" href="${ctx}/map/getAddressMap?address=${park.longAddr}&corname=${park.parkName}">查看位置</a></td>
		     </tr>
		    </c:forEach>
		  </table>
		   
				
		  <div id="page" style="text-align: center;">
				</div>
		  </c:if>
		  <c:if test="${fn:length(page.list)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				</div>
			</c:if>
        </div>
		<%@ include file="../common/loginfoot.jsp" %>
	<script type="text/javascript">
	function search(){
		var addcity = $("#addCodeId").find("select[name=city]").val();
		var addCode = $("#addCodeId").find("select[name=area]").val();
		$("#addcity").val(addcity);
		$("#addCode").val(addCode);
	    document.getElementById("myform").submit(); 
	}
    function sort(column,orderby){
	   var parkName='${parkName}';
	   var addcity='${addcity}';
	   var addCode='${addCode}';
	   var tag='${tag}';
	   var orderby=orderby;
	   var column=column;
	   var curr='${page.pageNumber}';
	   window.location.href="${ctx}/park/searchIndexPark?pageNo="+curr+"&addcity="+addcity+"&tag="+tag+"&addCode="+addCode+"&parkName="+parkName+"&column="+column+"&orderby="+orderby;
   }
    layui.use(['laypage'], function(){
    	var laypage = layui.laypage;
 	   var parkName='${parkName}';
 	   var tag='${tag}';
 	   var addcity='${addcity}';
 	   var addCode='${addCode}';
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
        	    	  window.location.href="${ctx}/park/searchIndexPark?pageNo="+obj.curr+"&tag="+tag+"&addcity="+addcity+"&addCode="+addCode+"&parkName="+parkName+"&column="+column+"&orderby="+orderby;
        	      }
        	  }
        	  ,skin: '#1E9FFF'
        });
    });

	</script>
		
	</body>
	
</html>
