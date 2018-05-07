<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户登录</title>
<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/sigi.css?v=${version}" />
<script type="text/javascript" src="${ctx }/static/pc/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/encrypt/md5.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
</head>
<body onkeydown="if(event.keyCode==13){checkUser()}">
<%@ include file="common/loginhead.jsp" %>
    <div class="contents">
		<div class="content">
	        <div class="content-liso">
				<p>网上物流会员</p>
				<div class="content-left">
				        <label class="label1">
						<input id="username" name="username" type="text"
							placeholder="手机号"    onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
							</label>
							<br />
							<br /> 
							<label class="label2">
						<input id="password"
							name="password" type="password" placeholder="密码" onkeyup="value=value.replace(/\s/g,'')"/>
		                    </label>
				</div>
			    <div class="content-right">
					<a href="${ctx}/front/forget" class="content-right-a">忘记密码</a>
				</div>
			     <input type="button" value="立即登录" onclick="checkUser();" class="content-button"/>
			     <div class="content-bottom">
			        <input type="checkbox" id="isAuto" name="isAuto" value="1" style="cursor: pointer;"> 
			       	<label for="isAuto" style="cursor: pointer;">下次自动登录</label>
			         <a href="${ctx}/front/register/wl">新用户注册</a> 
			     </div>
			</div>
		</div>
	</div>
	<%@ include file="common/loginfoot.jsp" %>

	<script type="text/javascript">
	function checkUser(){
		   var username = document.getElementById("username").value;
		   var password = document.getElementById("password").value;
		   var flag = false;
		   if(username == ""){
			   Anfa.show("请输入手机号","#username");
		       flag = true;
		   }else if(!(/^1[345678]\d{9}$/.test(username))){
			   Anfa.show("请输入正确的手机号","#username");
			   flag = true;
		   }
		   
		   if(password == ""){
			   Anfa.show("请输入密码","#password");
			   flag = true;
		   }
		   if(flag){
			   return;
		   }
		   var pss = hex_md5(password);
		   var isAuto = $("#isAuto").is(':checked')?1:0;
		   $(".content-button").val("正在登录");
		   $(".content-button").attr("disabled", "disabled");
		   var url = getPar("url");
		   if(url==null||url=="")url="${url}"
		   Anfa.ajax({
				type:"post",
				url:"${ctx }/front/user/login",
				data:{username:username,password:pss,isAuto:isAuto,url:url},
				success:function(data){
					if (data.success) {
						setTimeout(function(){
							if(data.data.url){
								window.location.href= data.data.url;
							}else{
								window.location.href="${ctx}/";
							}
						}, 300);
					} else {
						if(data.type==1004){
							Anfa.show(data.msg,"#password");
						}else if(data.type==-1){
							Anfa.show(data.msg,"#username");
						}else{
							Anfa.show(data.msg,"#username");
						}
						$(".content-button").val("登录");
						$(".content-button").removeAttr("disabled");
					}
	            }
			});
		}
	
		function getPar(par){
		    //获取当前URL
		    var local_url = document.location.href; 
		    //获取要取得的get参数位置
		    var get = local_url.indexOf(par +"=");
		    if(get == -1){
		        return false;   
		    }   
		    //截取字符串
		    var get_par = local_url.slice(par.length + get + 1);    
		    //判断截取后的字符串是否还有其他get参数
		    var nextPar = get_par.indexOf("&");
		    if(nextPar != -1){
		        get_par = get_par.slice(0, nextPar);
		    }
		    return get_par;
		}
	</script>
</body>
</html>