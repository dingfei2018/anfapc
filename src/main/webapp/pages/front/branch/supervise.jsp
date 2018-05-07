<%@ page contentType="text/html; charset=utf-8;" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>网点管理</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/supervise.css?v=${version}"/>
		<%@ include file="../personal/common/include.jsp" %>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
	    <script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	    <script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	</head>
    <body>
		<%@ include file="../personal/common/head.jsp" %>
		<div class="content">
			<%@ include file="../personal/common/left.jsp" %>
				<div class="content-right">
					<ul>
					   <li style="margin-right: 40px;"><a class="active" href="${ctx}/front/branch">网点管理</a></li>
					   <li><a class='<c:if test="${curr eq 10}">active</c:if>' href="${ctx}/front/branch/preview">发布预览</a></li>
				    </ul>	
		        <div class=" content-right-top">
				
				<label class="label2"><span class="content-span">网点名称 : </span>
				<input type="text" name="sub_network_name" id="sub_network_name" class="content-right-input"><b style="color:red">*</b>
				</label>
				<label class="label"><span class="content-span">网点地址 : </span>
				 	   <span id="startId">
							<select name="province" class="select"></select> 
							<select name="city"></select>
							<select name="area"></select></span>
							<script type="text/javascript">
								$("#startId").region({domain:"${ctx}"});
					   </script><b style="color:red">*</b>
				</label>
				<label class="label2"><span class="content-span">详细地址 : </span>
				<input type="text" name="address" id="address" class="content-right-input"><b style="color:red">*</b>
				</label>
				<label class="label3"><span class="content-span">联系人 : </span>
				<input type="text" name="contact_man" id="contact_man"  class="content-right-input"><b style="color:white;">*</b>
				</label>
				<label class="label4"><span class="content-span">移动电话 : </span>
				<input type="text" name="contact_information" id="contact_information"  class="content-right-input"> <b style="color:red">*</b>
			    </label>
				<label class="label4"><span class="content-span">固定电话 : </span>
				<input type="text" name="sub_logistic_telphone" id="sub_logistic_telphone"  class="content-right-input">
				<p style="color: red;">多个固定电话请用","分割 </p>
				<!--<b style="color:red">*</b><button class="button" onclick="gun()">增加</button> -->
			    </label>
			 <!--    <label class="label3"><span class="content-span" style="color: #fff;">联系人 : </span>
				<input type="text" name="sub_logistic_telphone" id="sub_logistic_telphone"  class="content-right-input"><b style="color:white;">*</b>
				</label> -->
			  </div> 
			<p class="p_btn"><button onclick="submitBranch();" class="content-button">提交</button></p>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		 /*function gun(){
				if($(".label3").length == 2){
					$(".content-right-top").append("<label class='label3'><span class='content-span' style='color: #fff;'>联系人 : </span><input type='text' name='sub_logistic_telphone' id='sub_logistic_telphone'  class='content-right-input'><b style='color:white;'>*</b></label>")
				}else if($(".label3").length == 3){
					$(".content-right-top").append("<label class='label3'><span class='content-span' style='color: #fff;'>联系人 : </span><input type='text' name='sub_logistic_telphone' id='sub_logistic_telphone'  class='content-right-input'><b style='color:white;'>*</b></label>")
				    return;
				}
			} */
		
		function submitBranch(){

            //reg = /^(1\d{10})$/;//正则表达式
            reg = /(^0\d{2,3}\-\d{7,8}$)|(^1[3|4|5|6|7|8][0-9]{9}$)/;
            var tel = document.getElementById("contact_information").value;
            var sub_network_name = document.getElementById("sub_network_name").value;

            //var region = document.getElementById("region").value;
            var address = document.getElementById("address").value;
            var contact_man = document.getElementById("contact_man").value;
            var contact_information = document.getElementById("contact_information").value;
            //var sub_logistic_telphone=document.getElementsByTagName("sub_logistic_telphone").value;
            var telphone_str = $("#sub_logistic_telphone").val();

            if( sub_network_name == ""){
                Anfa.show("网点名称不能为空","#sub_network_name");
                return;
            }
		var sarea = $("#startId").find("select[name=area]").val();
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
			$.ajax({
					type:"post",
					dataType:"html",
					url:"${ctx}/front/branch/submitBranch", 
					data:{region:sarea,address:address,contact_man:contact_man,contact_information:contact_information,sub_logistic_telphone:telphone_str,sub_network_name:sub_network_name},
					success:function(data){
						var obj = new Function("return" + data)();
					    window.location.href="${ctx}/front/branch/preview"
		            }
				});}
		}
		</script>
	</body>
</html>
