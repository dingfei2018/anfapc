<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>历史交易量</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/storical.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>

		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/js/search.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>

	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<div class="content">
		  <p class="p"><a href="${ctx}/index">首页</a> > 查看历史交易量</p>
		  <div class="content-top">
		     <span>物流园名称 : </span>
		     <input type="text" class="input" id="parkNameId" value="${parkName}" placeholder="请输入物流园名称"/>
		     <button class="button" onclick="jumpTo(1)">搜索</button>
		  </div>
		  
		  <table cellspacing="0">
		     <tr>
		        <th>序号</th>
		        <th class="${empty orderBy?'NormalCsso': orderBy=='asc'?'SortAscCsso':'SortDescCsso'}" onclick="toOrder()" style="cursor: pointer">交易量</th>
		        <th>物流园名称</th>
		        <th>操作</th>
		     </tr>
		     <c:forEach items="${orders.list}" var="i" varStatus="v">
			      <tr>
			        <td>${v.index+1}</td>
			        <td>${i.amount}</td>
			        <td><a>${i.park_name}</a></td>
			        <td><a class="content-as" href="${ctx}/park/searchParkOrders?parkId=${i.id}">查看</a></td>
			     </tr>
		     </c:forEach>
		     
		     <c:if test="${fn:length(orders.list)==0}">
				 <tr>
			        <td colspan="4" style=" height:250px">暂无数据</td>
			     </tr>
			</c:if>
		  </table>
			<div id="page" style="text-align: center;">
			</div>
        </div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
	<script type="text/javascript">
	var orderBy = "${orderBy}";
	function toOrder(){
		if(orderBy == "asc"){
			orderBy="desc";
		}else{
			orderBy="asc";
		}
		jumpTo(1);
	}
	
	function jumpTo(curr){
		window.location.href="${ctx}/park/alltrade?pageNo="+curr+"&parkName="+$("#parkNameId").val()+"&orderBy=" +orderBy;
	}
	
	layui.use(['laypage'], function(){
    	var laypage = layui.laypage;
	    laypage({
		      cont: 'page'
		      ,pages: '${orders.totalPage}' //得到总页数
		      ,curr:'${fn:length(orders.list)==0?(orders.pageNumber-1):orders.pageNumber}'
		      ,skip: true //是否开启跳页
	    	  ,jump: function(obj, first){
	    	      if(!first){
	    	    	  curr =obj.curr; 
	    	    	  jumpTo(curr);
	    	      }
	    	  }
	    	  ,skin: '#1E9FFF'
	    });
	});
	
	</script>
</html>
