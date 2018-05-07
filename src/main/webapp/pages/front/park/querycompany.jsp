<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>物流公司查询</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/shipped.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>

	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<div class="content">
		  <p class="p"><a href="${ctx}/index">首页</a> > 按物流公司发货</p>
		  <div class="content-top">
		     <span>物流公司名称 : </span>
		     <input type="text" class="input" id="corpnameId" placeholder="请输入物流公司名称" value="${corpName}"/>
		     <button class="button" onclick="jumpTo(1)">搜索</button>
		  </div>
		  
		  <table cellspacing="0">
		     <tr>
		        <th>序号</th>
		        <th>公司名称</th>
		        <th>网点数量</th>
		        <th>操作</th>
		     </tr>
		     <c:forEach items="${page.list}" var="i" varStatus="v">
			     <tr>
			        <td>${v.index+1}</td>
			        <td><a href="${ctx}/company?id=${i.id}" target="_blank" >${i.corpname}</a></td>
			        <td><a href="${ctx}/company/branch?id=${i.id}" target="_blank" >${i.amount}</a></td>
			        <td><a href="${ctx}/company/special?id=${i.id}" target="_blank" class="content-a">查看专线</a></td>
			     </tr>
		     </c:forEach>
		     
		     <c:if test="${fn:length(page.list)==0}">
				 <tr>
			        <td colspan="4" style=" height:250px">暂无数据</td>
			     </tr>
			</c:if>
		  </table>
		  	<div id="page" style="text-align: center;">
			</div>
        </div>
		<%@ include file="../common/loginfoot.jsp" %>

		<script type="text/javascript">
		function jumpTo(curr){
			window.location.href="${ctx}/company/queryList?pageNo="+curr+"&corpName="+$("#corpnameId").val();
		}
		
		layui.use(['laypage'], function(){
	    	var laypage = layui.laypage;
		    laypage({
			      cont: 'page'
			      ,pages: '${page.totalPage}' //得到总页数
			      ,curr:'${fn:length(page.list)==0?(page.pageNumber-1):page.pageNumber}'
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
	</body>
	
</html>
