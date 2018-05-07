<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>网点管理</title>
	    <link rel="stylesheet" href="${ctx}/static/kd/css/added.css" />
	    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>
	<body>
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/baseinfoleft.jsp" %>
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
				<div class="banner-right-title">
					<ul>
						<li><a href="${ctx}/kd/network" class="activet">网点列表</a></li>
						<li><a href="${ctx}/kd/netWork/add">新增网点</a></li>
					</ul>
				</div>
				<div class="banner-right-list">
				<form id="searchForm" action="${ctx}/kd/netWork" method="post" >
				 <ul>
					<li>
					<span>网点名称：</span><input type="text" id="s_netWorkName"  name="netWorkName" value="${ netWorkName}"/>
					</li>
				   <!-- <li>
				   	<span>网点地址：</span><input type="text" name="realname" />
				   </li> -->
					<li>
					<span>联系人：</span><input type="text"  id="s_sub_leader_name" name="sub_leader_name" value="${sub_leader_name }" />
				    </li>
					<li style="width:150px;">
					<button onclick="search();">查询</button>
                  </li>
                  <li style="width:150px;">
					 <input class="reset" type="reset" value="重置"/>
                  </li>
				</ul>
				</form>
				</div>
				
				<p class="banner-right-p">网点列表</p>
				<div style="overflow: auto; width: 100%;" id="loadingId">
				<script >$("#loadingId").mLoading("show");</script>
				<table cellspacing="0" class="table">
					<tr>
						<th>序号</th>
						<th>网点名称</th>
						<th>地址</th>
						<th>联系人</th>
						<th>联系电话</th>
						<th>网点代码</th>
						<th>固定电话</th>
						<th>操作</th>
					</tr>
					
					 <c:if test="${fn:length(page.list)>0}">
					 <c:forEach items="${page.list}" var="netWork" varStatus="vs">
					<tr>
						<td>${vs.index+1}</td>
						<td><a class="banner-right-a4">${netWork.sub_network_name}</a></td>
						<td>${netWork.addr}</td>
						<td>${netWork.sub_leader_name}</td>
						<td>${netWork.sub_logistic_mobile}</td>
						<td>${netWork.sub_network_sn}</td>
						<td>
						 <a style="color: #FF7801;">${netWork.sub_logistic_telphone}</a> 
						</td>
						<td><a class="banner-right-a1" href="#" onclick="goUpdate(${netWork.id})">修改</a><a class="banner-right-a2" href="#" onclick="deleteNetWork(${netWork.id})">删除</a></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
				</div>
				
				<div id="page" style="text-align: center;">
				</div>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	<script type="text/javascript">
	//分页
    layui.use(['laypage'], function(){
    	var laypage = layui.laypage;
        //调用分页
        laypage({
    	      cont: 'page'
    	      ,pages: '${page.totalPage}' //得到总页数
    	      ,curr:'${page.pageNumber}',
    	      count:'${page.totalRow}'
    	      ,skip: true
        	  ,jump: function(obj, first){
        	      if(!first){
        	    	   window.location.href="${ctx}/kd/netWork?pageNo="+obj.curr+"&netWorkName="+$("#s_netWorkName").val()+"&sub_leader_name="+$("#s_sub_leader_name").val();
        	      }
        	  }
        	  ,skin: '#1E9FFF'
        });
    });
	
	function search(){
		$('#searchForm').submit();
	}
	
	function goUpdate(id){
		 window.location.href="${ctx}/kd/netWork/add?id="+id+"&flag=update";
	}
	
	function deleteNetWork(objs) {
		layer.confirm(
				'您确定要删除？',
				{
					btn : [ '删除', '取消' ]
				},
				function() {
					$.ajax({
						type : "post",
						dataType : "json",
						url : "${ctx }/kd/netWork/delNetWork?netWorkId="
								+ objs,
						success : function(data) {
							
							if (data.state == "SUCCESS") {
								layer.msg("删除成功！",{time: 1000},function(){
									window.location.href="${ctx}/kd/netWork";
			                        });
							} else {
								layer.msg(obj.msg);
							}
						}
					});
		}, function() {
				});
	}
	setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
	</script>
	</body>
</html>
