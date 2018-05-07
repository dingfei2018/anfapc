<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>运单打印设置</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/printsetting.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
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
		    	})
	     </script>
			 <input type="hidden" value="${msg.msg}" id="msg" />
			<div class="banner-right">
				<form id="setForm" action="${ctx}/kd/waybill/updateSet">
				<input type="hidden" name="kdSetting.id" value="${set.id}">
				<input type="hidden" name="kdSetting.name" value="${set.name}">
				<input type="hidden" name="kdSetting.company_id" value="${set.company_id}">
				<div class="banner-right-ps">
					<p>打印注意事项：</p>
					<textarea name="kdSetting.value" id="setValue">${set.value}</textarea>
				</div>
				<div class="banner-right-img">
					<p>示意图：</p>
					<img src="${ctx}/static/kd/img/printsetting.png" />
				</div>
				<input type="button" value="提交" class="inputs" onclick="submitForm()"  />
				</form>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
			if($("#msg").val()!==""){
				layer.msg($("#msg").val());
				}
			
			function submitForm(){
				
				if($("#setValue").val()==""){
					Anfa.show("设置内容不能为空","#setValue");
					return;
				}
				$('#setForm').submit();
			}
		</script>
	</body>
</html>
