<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>网站设置</title>
	 	<%@ include file="common/include.jsp" %>
	 	<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/settings.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	</head>
	<body>
		<%@ include file="common/head.jsp" %>
		<div class="content">
           <%@ include file="common/left.jsp" %>
            <div class="content-right">
            <div class="conr_title">网站设置</div>
            		<form id="formId">
	            	<div class="conr_titles">
	            	    <label>
	            	        <span>营业执照隐藏 :</span>
	            	        <input class="conr_titles-input" name="zhizhao" type="radio" ${shop.show_yyzz?'checked=checked':''}  value="1" />否
	            	        <input class="conr_titles-inputs" name="zhizhao" type="radio" ${shop.show_yyzz?'':'checked=checked'} value="0" />是
	            	    </label><br/>
	            	    <label>
	            	        <span>身份证件隐藏 :</span>
	            	        <input class="conr_titles-input" name="cert" type="radio" ${shop.show_sfz?'checked=checked':''}  value="1" />否
	            	        <input class="conr_titles-inputs" name="cert" type="radio"  ${shop.show_sfz?'':'checked=checked'}  value="0" />是
	            	    </label><br/>
	            	    <label>
	            	        <span class="conr_titles-inputts">网点手机号码隐藏 :</span>
	            	        <input class="conr_titles-input" name="networkphone" checked="checked" type="radio" ${shop.show_network_mobile?'checked=checked':''} value="1" />否
	            	        <input class="conr_titles-inputs" name="networkphone" type="radio" ${shop.show_network_mobile?'':'checked=checked'} value="0" />是
	            	    </label>
	            	    <label>
	            	        <span class="conr_titles-inputt">公司手机号码隐藏 :</span>
	            	        <input class="conr_titles-input" name="telphone" checked="checked" type="radio"  ${shop.show_mobile?'checked=checked':''} value="1" />否
	            	        <input class="conr_titles-inputs" name="telphone" type="radio"${shop.show_mobile?'':'checked=checked'} value="0" />是
	            	    </label><br/>
	            	</div>
            	</form>
	            <button onclick="save()">提交</button>
            </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		function save(){
		 	Anfa.ajax({
				type:"post",
				url:"${ctx }/cpy/admin/setSettings",
				data:$("#formId").serialize(),
				success:function(data){
					if (data.success) {
						layer.msg("保存成功！");
					}else{
						layer.msg("保存失败！");
					}
					setTimeout(function(){
						location.reload();
					}, 1500);
	            }
			});
		}
		</script>
	</body>
</html>
