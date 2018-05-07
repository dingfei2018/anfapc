<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>货物列表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/goodslist.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
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
	    	})
	</script>
		 <input type="hidden" value="${msg.msg}" id="msg" />	
			<div class="banner-right">
				<ul>
					<li><a href="${ctx}/kd/product" class="banner-right-a4 active">货物列表</a></li>
					<li><a class="banner-right-a4" href="${ctx}/kd/product/add">新增货物</a></li>
				</ul>
				
				<div class="banner-right-list">
				  <ul>
				   <li>
					  <span class="span">货物编号：</span><input type="text" id="productSnSearch" value="${product_sn }"/>
					</li>
					<li>
					  <span class="span">货物名称：</span><input type="text" id="productNameSearch" value="${product_name }" />
					</li>
					<li>
					  <button onclick="search();">查询</button>
					</li>
					</ul>
				</div>
				
				<p class="banner-right-p">货物列表</p>
				<table cellspacing="0" class="table">
					<tr>
						<th></th>
						<th>序号</th>
						<th>货物编号</th>
						<th>货物名称</th>
						<th>包装单位</th>
						<th>数量</th>
						<th>体积</th>
						<th>重量</th>
						<th>价格</th>
						<th>条码</th>
						<th>操作</th>
					</tr>
					
					<c:if test="${fn:length(page.list)>0}">
					<c:forEach items="${page.list}" var="product" varStatus="vs">
					<tr>
						<td><input type="checkbox" value="${product.product_id}"/></td>
						<td>${vs.index+1}</td>
						<td>${product.product_sn}</td>
						<td>${product.product_name}</td>
						<td>
						<c:if test="${product.product_unit==1}">散装</c:if>
						<c:if test="${product.product_unit==2}">捆装</c:if>
						<c:if test="${product.product_unit==3}">袋装</c:if>
						<c:if test="${product.product_unit==4}">箱装</c:if>
						<c:if test="${product.product_unit==5}">桶装</c:if>
						</td>
						<td>${product.product_amount}</td>
						<td>${product.product_volume}</td>
						<td>${product.product_weight}</td>
						<td>${product.product_price}</td>
						<td>${product.product_barcode}</td>
						<td><a class="banner-right-a1" href="${ctx}/kd/product/add?type=update&productId=${product.product_id}">修改</a><a class="banner-right-a2" onclick="javascript:deleteOne(${product.product_id})">删除</a></td>
					</tr>
					</c:forEach>
					</c:if>
					
				</table>
				
				 <div id="page" style="text-align: center;">
				</div>
				<c:if test="${fn:length(page.list)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				 </div>
				</c:if>
				
				<c:if test="${fn:length(page.list)!=0}">
					<ul class="ul2">
						<li><input type="checkbox" id="selectAll" onclick="selectAll();"/><label >全选</label></li>
						<li><a href="#" onclick="deleteAll();" class="banner-right-a3">删除</a></li>
					</ul>
				</c:if>
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
		<script>
		if($("#msg").val()!==""){
			layer.msg($("#msg").val());
			}
		
		 function search(){
			   window.location.href="${ctx}/kd/product?product_sn="+$("#productSnSearch").val()+"&product_name="+$("#productNameSearch").val();
		   }
		
		function deleteOne(objs) {
			layer.confirm(
					'您确定要删除？',
					{
						btn : [ '删除', '取消' ]
					},
					function() {
						$.ajax({
							type : "post",
							dataType : "json",
							url : "${ctx }/kd/product/delete?productId="
									+ objs,
							success : function(data) {
								if (data.state == "SUCCESS") {
									layer.msg('删除成功',{time: 1000},function(){
										location.reload();
								        });
								} else {
									layer.msg(data.msg);
								}
							}
						});
			}, function() {
					});
		}
		
		
		//删除多个
    	function deleteAll(){
			var array = new Array();
			$(".table input[type='checkbox']").each(function(i){
			if($(this).prop("checked"))array.push($(this).val());
			});
			deleteAllTruck(array);
     	}
    		
   		//删除全部
   		function deleteAllTruck(objs) {
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
   					url : "${ctx }/kd/product/delete?productId="
						+ objs,
   					success : function(data) {
						if (data.state == "SUCCESS") {
							layer.msg('删除成功',{time: 1000},function(){
								location.reload();
						        });
						} else {
							layer.msg(data.msg);
						}
   					}
   				});
   			}, function(){});
   		}

        //全选事件
		function selectAll() {
			if($("#selectAll").is(':checked')){
				$(".table input[type='checkbox']").prop("checked", true);
			}else{
				$(".table input[type='checkbox']").prop("checked", false)
			}
		}
        
		//分页
	    layui.use(['laypage'], function(){
	    	var laypage = layui.laypage;
	        //调用分页
	        laypage({
	    	      cont: 'page'
	    	      ,pages: '${page.totalPage}' //得到总页数
	    	      ,curr:'${page.pageNumber}'
	    	      ,skip: true
	        	  ,jump: function(obj, first){
	        	      if(!first){
	        	    	   window.location.href="${ctx}/kd/product?pageNo="+obj.curr+"&product_sn="+$("#productSnSearch").val()+"&product_name="+$("#productNameSearch").val();
	        	    			   }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	    });					
		</script>
	</body>
</html>
