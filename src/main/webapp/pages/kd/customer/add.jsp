<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>客户录入</title>
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/kd/css/addCustomer.css" />
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
			    	})
			</script>
			<div class="banner-right">
				<ul>
					<li><a href="${ctx}/kd/customer">客户列表</a></li>
					<li><a href="${ctx}/kd/customer/add" class="active"><span id="title">新增客户</span></a></li>
				</ul>
				
				<div class="banner-right-content">
				<form id="customerForm" action="${ctx }/kd/customer/saveCustomer" method="post">
				
				<input type="hidden" name="address.region_code" id="address_code"/>
				<input type="hidden" id="type" value="${type}"/>
				<input type="hidden" name="customer.customer_id" value="${customer.customer_id}"/>
				<input type="hidden" id="customer_type"  value="${customer.customer_type}"/>
				<input type="hidden" name="bankCard.bank_card_id" value="${customer.bank_card_id}"/>
				<input type="hidden" name="address.uuid" value="${customer.customer_address_id}"/>
				<input type="hidden" id="customerSnHid" name="customer.customer_sn" value="${customer.customer_sn}"/>
					
					<div class="banner-right-type">
						<p>客户分类：</p>
						<select name="customer.customer_type" id="customerTypeSelect" onchange="setCustomerSn();">
									<option value="0">请选择</option>
									<option value="1">收货方</option>
									<option value="2">托运方</option>
									<option value="3">中转方</option>
						</select>
					<span>*</span>	
					</div>
					
					<div class="banner-right-num">
						<p>客户编号：</p>
						<input type="text" disabled="disabled"  id="customerSn" value="${customer.customer_sn }" />
						<span>*</span>
					</div>
					
					<div class="banner-right-com">
						<p>客户单位：</p>
						<input type="text" name="customer.customer_corp_name" id="customerCorpName" value="${customer.customer_corp_name }"/>
					</div>
					
					<div class="banner-right-name">
						<p>姓名：</p>
						<input type="text" name="customer.customer_name" id="customerName" value="${customer.customer_name }"/>
						<span>*</span>	
					</div>
					
					<div class="banner-right-tel">
						<p>电话：</p>
						<input type="text" name="customer.customer_mobile" id="customerMobile" value="${customer.customer_mobile }"/>
						<span>*</span>
					</div>
					
					<div class="banner-right-adr">
						<p>联系地址：</p>
						
					<span class="span" id="startId"> 
		    		<select name="province" class="banner-right-select1"></select>
                    <select name="city" class="banner-right-select1"></select>
                    <select name="area" class="banner-right-select1"></select>
                    <span>*</span>
                    </span>
					<br />
						
						<input type="text" class="inputshort2" name="address.tail_address" id="tailAddress" value="${customer.tail_address}"/>
					<span>*</span>
					</div>
					
					<div class="banner-right-id">
						<p>身份证号：</p>
						<input type="text" id="customerIdNumber" name="customer.customer_id_number" value="${customer.customer_id_number}"/>
					</div>
					
					<div class="banner-right-bank">
						<p>开户行：</p>
						<input type="text" id="bankName" name="bankCard.bank_name" value="${customer.bank_name}"/>
					</div>
					
					<div class="banner-right-bankNum">
						<p>开户行账号：</p>
						<input type="text" id="bankNumber" name="bankCard.bank_number" value="${customer.bank_number}"/>
					</div>
					
					<div class="banner-right-ps">
						<p>备注：</p>
						<textarea  name="customer.customer_remark">${customer.customer_remark}</textarea>
					</div>
					 <input type="button" value="提交" class="inputs" onclick="submitform();" />
				</form>
				</div>
			</div>
		</div>
	
	    <%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
				
				$("#startId").region({domain:"${ctx}", required: true, cityRequired: true,currAreaCode:"${customer.region_code}"});
				
				if($("#type").val()=="update"){
				$("#title").html("修改客户")	
				$("#customerTypeSelect").val($("#customer_type").val());
				}
				
				function setCustomerSn(){
					var customerType=$("#customerTypeSelect").val();
					
					$.ajax({
						type : "post",
						dataType :"text",
						url : "${ctx }/kd/customer/getCustomerSn?customer_type="+customerType,
						success : function(data) {
							$('#customerSn').val(data);
							$('#customerSnHid').val(data);
							 
						}
					});
					
					
					
					
				}
				
				
				/*表单提交*/
				function submitform(){
					if($("#customerSn").val()==""){
						Anfa.show("请填写客户编号","#customerSn");
						return;
					}else if($("#customerSn").val().length>32){
						Anfa.show("请填写正确的客户编号","#customerSn");
						return;
					}
					
					if($("#customerTypeSelect").val()==0){
						Anfa.show("请选择客户类型","#customerTypeSelect");
						return;
					}
					
					if($("#customerName").val()==""){
						Anfa.show("请填写客户姓名","#customerName");
						return;
					}else if($("#customerName").val().length>15){
						Anfa.show("请填写正确的客户姓名","#customerName");
						return;
					}
					
					if($("#customerMobile").val()==""){
						Anfa.show("请填写客户电话","#customerMobile");
						return;
					}else if($("#customerMobile").val().length>11){
						Anfa.show("请填写正确的客户电话","#customerMobile");
						return;
					}
					
					var sareaCode = $("#startId").find("select[name=area]").val();
					if(sareaCode==null||sareaCode==""){
						if(sareaCode==null||sareaCode=="")sareaCode = $("#startId").find("select[name=city]").val();
						if(sareaCode==null||sareaCode==""){
							if(sareaCode==null||sareaCode==""){
								Anfa.show("请选择联系地址","#startId");
								return;
							} 
						}
					}
					if($("#tailAddress").val()==""){
						Anfa.show("请填写详细地址","#tailAddress");
						return;
					}
					
					if($("#bankName").val().length>0&&$("#bankNumber").val().length==0){
						Anfa.show("填写了开户行必须填写开户行账号","#bankNumber");
						return;
					}
					if($("#bankName").val().length==0&&$("#bankNumber").val().length>0){
						Anfa.show("填写了开户行账号必须填写开户行名称","#bankName");
						return;
					}
					
					
						$("#address_code").val(sareaCode);
						if($("#type").val()=="update"){
							$("#customerForm").attr("action", "${ctx }/kd/customer/updateCustomer");
						}
						$("#customerForm").submit();
					}
				</script>	
	</body>
</html>

