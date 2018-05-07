<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>客户查询</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/customerlist.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
		<%-- <script src="${ctx}/static/kd/js/customer-list.js?v=${version}"></script> --%>
	</head>
	<body>
	    <%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
		<%@ include file="../common/basezlleft.jsp" %>
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
		 <input type="hidden" value="${msg.msg}" id="msg" />
			<div class="banner-right">
				<ul>
					<li><a href="${ctx}/kd/customer"  class="banner-right-a4 activet">客户列表</a></li>
					<li><a class="banner-right-a4" href="${ctx}/kd/customer/add">新增客户</a></li>
				</ul>
				
				<div class="banner-right-list">
				<form id="searchFrom"  onsubmit="return false;">
			    	<ul>
			    	<li>
			    	客户类型：<select name="customer_corp_name" id="customerType" ">
							<option value="">请选择</option>
							<option <c:if test="${customerType ==1}">selected</c:if> value="1">收货方</option>
							<option <c:if test="${customerType ==2}">selected</c:if> value="2">托运方</option>
							<option <c:if test="${customerType ==3}">selected</c:if> value="3">中转方</option>
						</select>
			    	</li>
					<li>
					<span class="span">客户电话：</span><input type="text" id="customer_mobile" value="${customer_mobile}" />
					</li>
					<li>
					<span class="span">客户姓名：</span><input type="text" id="customer_name" value="${ customer_name}" />
					</li>
					<li>
					<span class="span">客户单位：</span><input type="text" id="customer_corp_name" value="${ customer_corp_name}" />
					</li>
					<li>
					   <button onclick="search()">查询</button>
					   <input class="buttons" type="reset" value="重置"/>
					</li>
					<!-- <li style="text-align: left;">
					  <input class="buttons" type="reset" value="重置"/>
					</li> -->
					</ul>
				</form>
				</div>
				
				<p class="banner-right-p">客户列表</p>
				<div style="overflow: auto; width: 100%;" id="loadingId">
				<script >$("#loadingId").mLoading("show");</script>
				<table cellspacing="0" class="tab_css_1" id="table">
					<tr>
						<th class="banner-right-pleft"></th>
						<th>序号</th>
						<th>客户编号</th>
						<th>客户分类</th>
						<th>客户单位</th>
						<th>姓名</th>
						<th>电话</th>
						<th>联系地址</th>
						<th>操作</th>
					</tr>
					 <c:if test="${fn:length(page.list)>0}">
					 <c:forEach items="${page.list}" var="customer" varStatus="vs">
					<tr>
						<td class="banner-right-pleft"><input type="checkbox" value="${customer.customer_id}" /></td>
						<td>${vs.index+1}</td>
						<td>${customer.customer_sn}</td>
						<td>
						<c:if test="${customer.customer_type==1}">收货方</c:if>
						<c:if test="${customer.customer_type==2}">托运方</c:if>
						<c:if test="${customer.customer_type==3}">中转方</c:if>
						</td>
						<td>${customer.customer_corp_name}</td>
						<td>${customer.customer_name}</td>
						<td>${customer.customer_mobile}</td>
						<td>${customer.customer_address_id}</td>
						<td><a class="banner-right-a1" href="${ctx}/kd/customer/add?type=update&customerId=${customer.customer_id}">修改</a><a class="banner-right-a2" href="javascript:deleteCustomer(${customer.customer_id})">删除</a></td>
					</tr>
					</c:forEach>
					</c:if> 
				</table>
				</div>
				
				<div id="page" style="text-align: center;">
				</div>
				
				<c:if test="${fn:length(page.list)!=0}">
				<ul class="ul2">
					<li><input type="checkbox" id="selectAll" onclick="selectAll();" />全选</li>
					<li><a href="#" class="banner-right-a3" onclick="deleteAll();">删除</a></li>
				</ul>
				</c:if>
				
			<c:if test="${fn:length(page.list)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				</div>
			</c:if>
			
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		if($("#msg").val()!==""){
		  layer.msg($("#msg").val());
		}
		
		function deleteCustomer(objs) {
			layer.confirm(
					'您确定要删除？',
					{
						btn : [ '删除', '取消' ]
					},
					function() {
						$.ajax({
							type : "post",
							dataType : "html",
							url : "${ctx }/kd/customer/delCustomer?customerId="
									+ objs,
							success : function(data) {
								var obj = new Function("return"
										+ data)();
								if (obj.state == "SUCCESS") {
									window.location.href="${ctx}/kd/customer";
								} else {
									layer.msg(obj.msg);
								}
							}
						});
			}, function() {
					});
		}
		
		
		//删除多个
    	function deleteAll(){
			var array = new Array();
			$("#table input[type='checkbox']").each(function(i){
			if($(this).prop("checked"))array.push($(this).val());
			});
			deleteAllCustomer(array);
    	}
    		
    		//删除全部
    		function deleteAllCustomer(objs) {
    	    	if(objs==null||objs==""){
    	    		layer.msg("请选择要删除的数据");
    				return;
    			}
    	    	layer.confirm('您确定要删除？', {
    			  	btn: ['确定','取消']
    			}, function(){
    				$.ajax({
    					type : "post",
    					dataType : "json",
    					url : "${ctx }/kd/customer/delCustomer?customerId="+objs,
    					success : function(data) {
							if (data.state == "SUCCESS") {
								window.location.href="${ctx}/kd/customer";
							} else {
								layer.msg(data.msg);
							}
    					}
    				});
    			}, function(){});
    		}
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
	        	    	   window.location.href="${ctx}/kd/customer?pageNo="+obj.curr+"&customer_sn="+$("#customer_sn").val()+"&customer_mobile="+$("#customer_mobile").val()+"&customer_name="+$("#customer_name").val();
	        	      }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	    });
		   
	    function search(){
		   window.location.href="${ctx}/kd/customer?customerType="+$("#customerType").val()+"&customer_mobile="+$("#customer_mobile").val()+"&customer_name="+$("#customer_name").val()+
		   "&customer_corp_name="+$("#customer_corp_name").val();
	   } 
	   
	   //全选事件
		function selectAll() {
			if($("#selectAll").is(':checked')){
				$("#table input[type='checkbox']").prop("checked", true);
			}else{
				$("#table input[type='checkbox']").prop("checked", false)
			}
		}
		
		setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
	</script>	
	</body>
</html>
