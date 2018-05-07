<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增货物</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/addGoods.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
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
			<input type="hidden" id="type" value="${type}"/>
			<div class="banner-right">
				
					<ul>
						<li><a href="${ctx}/kd/product">货物列表</a></li>
						<li><a href="${ctx}/kd/product/add" class="active"><span id="title">新增货物</span></a></li>
					</ul>
				
				<div class="banner-right-content">
				<form action="${ctx }/kd/product/saveProduct" id="productForm">
				<input type="hidden" name="product.product_id" value="${product.product_id }"/>
				<input type="hidden" id="product_type"  value="${product.product_unit}"/>
				
					<div class="banner-right-num">
						<p>货物编号：</p>
						<input type="text" name="product.product_sn" value="${product.product_sn }" id="productSn" />
					</div>
					<div class="banner-right-type">
						<p>货物名称：</p>
						<input type="text" name="product.product_name" value="${product.product_name }" id="productName"  />
						<span>*</span>
					</div>
					<div class="banner-right-unit">
						<p>包装单位：</p>
						<select name="product.product_unit" id="productUnit">
								<option value="0">请选择</option>
			     				<option value="1">散装</option>
			     				<option value="2">捆装</option>
			     				<option value="3">袋装</option>
			     				<option value="4">箱装</option>
			     				<option value="5">桶装</option>
			     			</select>
						<span>*</span>
					</div>
					<div class="banner-right-amount">
						<p>数量：</p>
						<input type="number" name="product.product_amount" value="${product.product_amount }" id="productAmount"/>
						<span>*</span>
					</div>
					<div class="banner-right-price">
						<p>价格：</p>
						<input type="number" name="product.product_price" value="${product.product_price }" id="productPrice"/>元
						<span>*</span>
					</div>
					<div class="banner-right-volume">
						<p>体积：</p>
						<input type="number" name="product.product_volume" value="${product.product_volume }" id="productVolume"/>立方
						<span>*</span>
					</div>
					<div class="banner-right-weight">
						<p>重量：</p>
						<input type="number" name="product.product_weight" value="${product.product_weight }" id="productWeight" />公斤
						<span>*</span>
					</div>
					<div class="banner-right-id">
						<p>条码：</p>
						<input type="text" name="product.product_barcode" value="${product.product_barcode }" id="productBarcode" />
					</div>
					<div class="banner-right-ps">
						<p>备注：</p>
						<textarea name="product.product_remark" id="productRemark">${product.product_barcode }</textarea>
					</div>
					 <input type="button" value="提交" onclick="submitForm();" class="inputs"  />
				</form>
				
				</div>
			</div>
		</div>

        <%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		
		if($("#type").val()=="update"){
			$("#title").html("修改货物");	
			$("#productUnit").val($("#product_type").val());
			}
			
		
		/*表单提交*/
		function submitForm(){
			
			if($("#productName").val().length==0){
				Anfa.show("请输入货物名称","#productName");
				return;
			}
			if($("#productUnit").val()==0){
				Anfa.show("请选择包装单位","#productUnit");
				return;
			}
			if($("#productAmount").val().length==0){
				Anfa.show("请输入数量","#productAmount");
				return;
			}
			if($("#productPrice").val().length==0){
				Anfa.show("请输入价格","#productPrice");
				return;
			}
			if($("#productVolume").val().length==0){
				Anfa.show("请输入体积","#productVolume");
				return;
			}
			if($("#productWeight").val().length==0){
				Anfa.show("请输入重量","#productWeight");
				return;
			}
			
			
			if($("#type").val()=="update"){
				$("#productForm").attr("action", "${ctx }/kd/product/updateProduct");
			}
			
				$("#productForm").submit();
			}
		
		</script>		
		
	</body>
</html>
