<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>基本信息</title>
		
	 	<%@ include file="common/include.jsp" %>
	 	<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/information2.css?v=${version}"/>
	 	<script src="${ctx }/static/pc/js/jquery.js"></script>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
	</head>
	<body>
		<%@ include file="common/head.jsp" %>
		<div class="content">
            <%@ include file="common/left.jsp" %>
            <div class="content-right">
                <div class="conr_title">基本信息</div>
                <div class="conr_con">
                <img src="${ctx}/static/pc/img/gkmmmhjk.jpg"/>
            	<ul class="content-right-ul">
            	
            	<li><input type="hidden" class="content-right-input" id="companyid" value="${company.id}"></li>
            	   <c:if test="${empty company.is_certification || company.is_certification==3}">
            		<li><span>公司名称：</span><input type="text" class="content-right-input" id="corpname" value="${company.corpname}"  placeholder="请输入公司名称" onblur="checkComName()"></li>
            		
            		</c:if>
            	   <c:if test="${company.is_certification==1}">
            		<li><span>公司名称：</span><input type="text" class="content-right-input" id="corpname" value="${company.corpname}"  placeholder="请输入公司名称" disabled="disabled">&nbsp;&nbsp;<font color="red">审核中</font></li>
            		
            		</c:if>
            		<c:if test="${company.is_certification==2 || company.is_certification==4}">
            		<li><span>公司名称：</span><input type="text" class="content-right-input" id="corpname" value="${company.corpname}" disabled="disabled" >&nbsp;&nbsp;<font color="red">已认证</font></li>
            		</c:if>
            		<li><span>公司地址：</span>
            		<label id="startId" class="label2" >
            		            <select name="province"></select> 
								<select name="city"></select>
								<select name="area"></select>
								<script type="text/javascript">
								$("#startId").region({areaField:"area",domain:"${ctx}",currAreaCode:"${address.regcode}"});
								</script></label><br>
            		<input type="text" class="content-right-input2" id="corpaddr" value="${address.tail_address}" placeholder="请输入公司所在的办公地址"></li>
            		<c:if test="${empty company.is_certification || company.is_certification==3}">
            		<li class="li2"><span class="content-right-span">营业执照编码：</span><input type="text" id="bussinessCode" value="${company.bussiness_code}"  placeholder="请输入正确的营业执照编码"></li>
            		</c:if>
            		<c:if test="${company.is_certification==1}">
            		<li class="li2"><span class="content-right-span">营业执照编码：</span><input type="text" id="bussinessCode" value="${company.bussiness_code}" disabled="disabled" placeholder="请输入正确的营业执照编码"> &nbsp;&nbsp;<font color="red">审核中</font></li>
            		</c:if>
            		<c:if test="${company.is_certification==2 || company.is_certification==4}">
            		<li class="li2"><span class="content-right-span">营业执照编码：</span><input type="text" id="bussinessCode" value="${company.bussiness_code}" disabled="disabled">&nbsp;&nbsp;<font color="red"></font></li>
            		</c:if>
            		<li><span >联系人：</span><input type="text" id="chargePerson" value="${company.charge_person}" placeholder="请填写公司联系人姓名"></li>
            		<li><span >公司电话：</span><input type="text" id="corpTelphone" value="${company.corp_telphone}" placeholder="例如：010-12345678"></li>
            		<li><span class="content-right-spans">手机号码：</span><input type="text" placeholder="13480243331" readonly="readonly" id="mobile" value="${user.mobile}"><a href="${ctx}/front/userconter/getMobile">更换账号</a></li>
            	</ul>
            	<button class="content-button" onclick="saveCompanyInfo();">提交</button>
            	<!---提交成功的回调--->
            	<script>
            		function send(){
            			document.getElementsByClassName("content-buttons")[0].style.display = "block";
            			document.getElementsByClassName("content-button")[0].style.backgroundColor = "#999999";
            			 setTimeout(function(){
            				document.getElementsByClassName("content-buttons")[0].style.display = "none";
            				location.reload();
            			},1500);
            		}
            	</script>
            	<button class="content-buttons" onclick='javascript:window.location.href="${ctx}/front/userconter"'>提交成功</button>
            </div>
		</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		
		
		<script type="text/javascript">
		
		function saveCompanyInfo(){
			var companyid=$("#companyid").val();
			var userid =  $("#userid").val();
			var corpname =  $("#corpname").val();
			var corpaddr =  $("#corpaddr").val();
			var chargePerson =  $("#chargePerson").val();
			var corpTelphone =  $("#corpTelphone").val();
			var bussinessCode = $("#bussinessCode").val();
			   if(corpname == ""  ){
			      Anfa.show("请输入公司名称","#corpname");
			      return false;
			   }
				var sarea = $("#startId").find("select[name=area]").val();
				if(sarea==null||sarea==""){
					if(sarea==null||sarea=="")sarea = $("#startId").find("select[name=city]").val();
					if(sarea==null||sarea==""){
						if(sarea==null||sarea=="")sarea = $("#startId").find("select[name=province]").val();
						if(sarea==null||sarea==""){
							Anfa.show("请选择公司地址","#startId");
							return false;
						}
					}
				} 
			   if(corpaddr == ""  ){
				     Anfa.show("请输入公司详细地址","#corpaddr");
				     return false;
				  }
			   
			  /*  if(bussinessCode == "" ){
				   Anfa.show("请输入营业执照编码","#bussinessCode");
				   return false;
				 }
			   if(bussinessCode.length!=15 && bussinessCode.length!=18 ){
				   Anfa.show("营业执照编码必须是15位或18位","#bussinessCode");
				   return false;
				 } 

			   if(chargePerson == "" ){
				   Anfa.show("请输入联系人","#chargePerson");
				   return false;
				 }

			   if(corpTelphone == "" ){
				   Anfa.show("请输入公司电话","#corpTelphone");
				   return false;
				 }*/
			   var data={"region":sarea,"companyid":companyid,"corpname":corpname,"corpaddr":corpaddr,"bussinessCode":bussinessCode,"chargePerson":chargePerson,"corpTelphone":corpTelphone};
			   $.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/front/userconter/saveCompanyInfo",
					data:data,
						success:function(data){
							if(data.state=="SUCCESS"){
								layer.msg(data.msg,{time: 2000});
								setTimeout( function() {
									location.reload();
								}, 2000);
							}else{
								layer.msg(data.msg,{time: 2000});
							}
			            }
					});
			}
		
			function checkComName(){
				var corpname =  $("#corpname").val();	
				var data={"corpname":corpname};
				  $.ajax({
						type:"post",
						dataType:"json",
						url:"${ctx}/front/userconter/checkCompanyName",
						data:data,
							success:function(data){
								if(data.state=="SUCCESS"){
									 Anfa.show("公司已经存在","#corpname");
								}else{
									//layer.msg(data.msg,{time: 2000});
								}
				            }
						});
			}
		
		
		</script>
	</body>
</html>
