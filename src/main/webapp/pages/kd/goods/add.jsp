<%@page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增货物录入</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/public.css" />
		<link rel="stylesheet" href="${ctx}/static/kd/css/entering.css" />
		  <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	</head>
	<body>
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="bannero">
		<div class="banner">
			<%@ include file="../common/left.jsp" %>
			<form action="${ctx}/kd/goods/saveProduct" method="post">
			<div class="banner-right"><!-- 
				<div class="banner-right-top">
					<input type="text" placeholder="货物编号" class="banner-right-input"/>
					<input type="text" placeholder="货物名称"/>
					<button class="button">搜索</button>
				</div> -->
				<label class="label1"><b>货物编号 : </b><input type="text" name="kdProduct.product_sn"/><span>*</span></label><br />
				<label class="label2"><b>货物名称 : </b><input type="text" name="kdProduct.product_sn"/><span>*</span></label><br />
				<label class="label3"><b>包装单位 : </b><select name="kdProduct.product_unit">
					<option value="">请选择</option>
					<option value="1">散装</option>
					<option value="2">捆包状包装</option>
					<option value="3">袋状包装</option>
					<option value="4">箱状包装</option>
					<option value="5">桶装包装</option>
					<option value="6">其他形状包装</option>
					<option value="7">裸状包装</option>
				</select><span>*</span></label><br />
				<!--<label class="label4"><b>规格 : </b><select>
					<option>请选择</option>
					<option>收货方</option>
					<option>托运方</option>
				</select><span>*</span></label>-->
				<label class="label4"><b>数量 : </b><input type="text" name="kdProduct.product_amount"/><span>*</span></label><br />
			    <label class="label6"><b>价格 : </b><input type="text" name="kdProduct.product_sn"/> 元 <span class="span">*</span></label><br />
			    <label class="label7"><b>体积 : </b><input type="text" name="kdProduct.product_volume"/> 立方</label><br />
			    <label class="label8"><b>重量 : </b><input type="text" name="kdProduct.product_weight"/> 公斤</label><br />
			    <label class="label8"><b>条码 : </b><input type="text" name="kdProduct.product_barcode"/></label><br />
			    <label class="label9"><b>备注 : </b><textarea name="kdProduct.product_remark"></textarea></label><br />

			    <input type="submit" value="提交" class="inputs"/>
			</div>
			</form>
		</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
