<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/tanceng2.css" />
<link rel="stylesheet" href="${ctx}/static/kd/css/imfor.css"/>
<%@ include file="common/commonhead.jsp" %>
</head>
<body>
<div class="banner-password-change">
	<p>修改密码</p>
	<div class="banner-password-change-input">
		<form  onsubmit="return false;" id="searchFrom">
			<label>手机 <input class="change-mobile" id="mobile" type="text" name="mobile" value="${mobile}" readonly="readonly"/></label> 
			<br />
			<br />
			<label>旧密码 <input class="change-psw" id="oldpw" type="password" name="opassword"/></label>
			<br />
			<br />
			<label>新密码 <input class="change-npw" id="npw" type="password" name="npassword"/></label>
			<br />
			<br />
			<label>确认新密码 <input class="change-snpw" id="snpw" type="password" name="rnpassword"/></label>
		</form>
	</div>
	<button onclick="modify()">确定</button> 
</div>
<script type="text/javascript">
	function modify(){
		var oldpw = $("#oldpw").val();
		if(oldpw==""){
			Anfa.show("请输入旧密码","#oldpw");
			return;
		}
		var npw = $("#npw").val();
		if(npw==""){
			Anfa.show("请输入新密码","#npw");
			return;
		}
		var snpw = $("#snpw").val();
		if(snpw==""){
			Anfa.show("请输入确认新密码","#snpw");
			return;
		}
		var param = $("#searchFrom").serialize();
		$.ajax({
			type:'POST',
			url:'${ctx}/kd/modifypwd',
			data:param,
			success:function(data){
				if (data.success) {
					layer.msg("修改成功");
					setTimeout(function(){parent.layer.closeAll();}, 1000);
				} else {
					layer.msg(data.msg);
				}
			}
		});
	}
</script>
</body>
</html>