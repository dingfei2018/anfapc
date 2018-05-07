<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>新增网点</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/addnet.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<%@ include file="../common/commonhead.jsp"%>
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
					<ul>
					<li><a href="${ctx}/kd/netWork">网点列表</a></li>
						<li><a href="${ctx}/kd/netWork/addNet" class="active"><span id="updateFlag">新增网点</span></a></li>
					</ul>
				<input type="hidden" id="type" value="${type}">
				<div id="win" class="div-addnet">
					<input type="hidden" id="oldName" value="${netWork.sub_network_name}">
					<input type="hidden" id="oldNetworkSn" value="${netWork.sub_network_sn}">
					<input type="hidden" id="netWorkId" value="${netWork.id}">
					<input type="hidden" id="addId" value="${netWork.addId}">
					<!-- 序号 -->
					<input type="hidden" id="sub_network_num" value="${netWork.sub_network_num}">
					<label class="div-addnet-paading" id="y_park">物流园：</label><input type="text" style="width:320px" value="${netWork.park_id}" name="park_id" id="park_id"/> <span class="red">*</span><br>
					<label>网点名称：</label><input type="text" name="sub_network_name" value="${netWork.sub_network_name}" id="sub_network_name" onblur="checkNetWorkName();"/> <span class="red">*</span><br>
					<label>网点地址：</label><span class="span" id="startId"><select name="province" class="banner-right-select1"></select>
                    <select name="city" class="banner-right-select1"></select>
                    <select name="area" class="banner-right-select1"></select>
                    </span><span class="red">*</span><br>
                    <label>详细地址：</label><input type="text" value="${netWork.tail_address}" name="address" id="address"/> <span class="red">*</span><br>
                    <label class="div-addnet-paading">联系人：</label><input type="text" value="${netWork.sub_leader_name}" name="contact_man" id="contact_man"/> <span class="red">*</span><br>
                    <label>移动电话：</label><input type="text" name="contact_information" value="${netWork.sub_logistic_mobile}" id="contact_information" onblur="checkMobile(this.value);"/> <span class="red">*</span><br>
                    <label>网点代码：</label><input type="text" onblur="checksubnetworksn();" name="sub_network_sn" value="${netWork.sub_network_sn}" id="sub_network_sn"/><span class="div-addnet-ps" >开单时生成货号时将用到 </span><br/>
                    <label>固定电话：</label><input type="text" name="sub_logistic_telphone" value="${netWork.sub_logistic_telphone}" id="sub_logistic_telphone"/><p class="div-addnet-ps" >多个固定电话请用","分割 </p><br/>
                    <input class="div-addnet-button" type="button" value="提交" onclick="submitBranch();"/>
          </div>  
          
          <script type="text/javascript">
          $("#startId").region({domain:"${ctx}", required: true, cityRequired: true,currAreaCode:"${netWork.region_code}"});
          var mobilecheck;
          var type=$('#type').val();
          var oldName=$('#oldName').val();
          var oldNetworkSn=$('#oldNetworkSn').val();
          
          if(type=='update'){
        	  $('#updateFlag').html('修改网点');
          }
          
          function checkMobile(mobile){
  			$.ajax({
  				type:"post",
  				dataType:"json",
  				async: false,
  				url:"${ctx}/kd/user/checkMobile", 
  				data:{mobile:mobile},
  				success:function(data){
  					mobilecheck=data;
  	            }
  			});
  			
  		}
         //物流园信息
  		$('#park_id').combogrid({  
  			url : '/park/queryLogisticsParkList1',
  			idField : 'id',
  			textField : 'park_name',
  			height : 30,
  			panelWidth : 320,
  			pagination: true, 
  			columns: [[
  				{field:'park_name',title:'物流园名称',width:200}
  			]],
  			fitColumns: true
  		}); 
         
          function checkNetWorkName(){
  			var flag=false;
  			var updateFlag=	$('#sub_network_name').val()!=oldName;
  			if(type=="update"&&updateFlag){
  				updateFlag=true;
  			}
  			if(updateFlag){
  			$.ajax({
  				  url : "${ctx}/kd/netWork/checkNetWorkName?netWorkName="+$('#sub_network_name').val(),
  				  type : 'POST',
  				  dataType:'json',
  				  async: false,
  				  success:function(data){
  					  if(data){
  						  flag= false;
  					  }else{
  						  Anfa.show("公司下该网点名称已存在","#sub_network_name"); 
  						  flag= true;
  					  }
  				  }
  				});
  			}
  			return flag;
  		}
          
          //校验网点代码是否公司唯一
          function checksubnetworksn(){
  			var flag=false;
  			var updateFlag=	$('#sub_network_sn').val()!=oldNetworkSn;
  			if(type=="update"&&updateFlag){
  				updateFlag=true;
  			}
  			if(updateFlag){
  			$.ajax({
  				  url : "${ctx}/kd/netWork/checknetworksn?sub_network_sn="+$('#sub_network_sn').val(),
  				  type : 'POST',
  				  dataType:'json',
  				  async: false,
  				  success:function(data){
  					  if(data){
  						  flag= false;
  					  }else{
  						  Anfa.show("公司下该网点代码已存在","#sub_network_sn"); 
  						  flag= true;
  					  }
  				  }
  				});
  			}
  			return flag;
  		}
          
          
          function submitBranch(){
      		var sarea = $("#startId").find("select[name=area]").val();
      		//reg = /^(1\d{10})$/;//正则表达式
      		reg = /(^0\d{2,3}\-\d{7,8}$)|(^1[3|4|5|6|7|8][0-9]{9}$)/;
      		var tel = document.getElementById("contact_information").value;
      		
      		//var region = document.getElementById("region").value;
      		var sub_network_name = document.getElementById("sub_network_name").value;
      		var address = document.getElementById("address").value;
      		var contact_man = document.getElementById("contact_man").value;
      		var contact_information = document.getElementById("contact_information").value;
      		//var sub_logistic_telphone=document.getElementsByTagName("sub_logistic_telphone").value;
      		var telphone_str = $("#sub_logistic_telphone").val();
      		var sub_network_sn = $("#sub_network_sn").val();
      		var sub_network_num = $("#sub_network_num").val();
      		var park_id =$('#park_id').combogrid('getValue')
      		if( park_id == ""){
      			Anfa.show("请选择物流园","#y_park");
      			return;
      		}
      		if( sub_network_name == ""){
      			Anfa.show("网点名称不能为空","#sub_network_name");
      			return;
      		}
      		if(checkNetWorkName()){
      			 Anfa.show("公司下该网点名称已存在","#sub_network_name"); 
      			 return;
      		}
      		if(checksubnetworksn()){
      			 Anfa.show("公司下该网点代码已存在","#sub_network_sn"); 
      			 return;
      		}
      		if(sarea==null||sarea==""){
  				if(sarea==null||sarea=="")sarea = $("#startId").find("select[name=city]").val();
  				if(sarea==null||sarea==""){
  					if(sarea==null||sarea=="")sarea = $("#startId").find("select[name=province]").val();
  					if(sarea==null||sarea==""){
  						Anfa.show("请选择网点地址","#startId");
  						return;
  					}
  				}
  			}
      		
      		if($("#startId").find("select[name=area]").val()==""){
					Anfa.show("请选择区域","#startId");
					return;
				}
      		
      		if( address == ""){
      			Anfa.show("详细地址不能为空","#address");
      			return;
      		}
      		if( contact_man == ""){
      			Anfa.show("联系人不能为空","#contact_man");
      			return;
      		}
      		if(!(reg.test(tel))){
      			  Anfa.show("手机或者固话错误","#contact_information");
      		    return;
      		}
      	    //document.getElementById("contact_information").value = "";
      			else {
      			if(type=="update"){
      				$.ajax({
      					type:"post",
      					dataType:"json",
      					url:"${ctx}/kd/netWork/updateNetWork", 
      					data:{region:sarea,address:address,contact_man:contact_man,contact_information:contact_information,sub_logistic_telphone:telphone_str,sub_network_name:sub_network_name,mobilecheck:mobilecheck
      						,netWorkId:$('#netWorkId').val(),addId:$('#addId').val(),sub_network_sn:sub_network_sn,sub_network_num:$('#sub_network_num').val(),park_id:park_id	},
      					success:function(data){
      						layer.msg("更新成功！",{time: 1000},function(){
      							 window.location.href="${ctx}/kd/netWork";
		                        });
      						
      		            }
      				});
      				
      			}else{
      				$.ajax({
      					type:"post",
      					dataType:"json",
      					url:"${ctx}/kd/netWork/saveNetWork", 
      					data:{region:sarea,address:address,contact_man:contact_man,contact_information:contact_information,sub_logistic_telphone:telphone_str,sub_network_name:sub_network_name,mobilecheck:mobilecheck,sub_network_sn:sub_network_sn,park_id:park_id},
      					success:function(data){
      						layer.msg("新增成功！",{time: 1000},function(){
      							 window.location.href="${ctx}/kd/netWork";
		                        });
      						
      		            }
      				});
      			}
      			}
      		}
          </script>
        </div>
        </div>
        <%@ include file="../common/loginfoot.jsp" %>  
</body>
</html>