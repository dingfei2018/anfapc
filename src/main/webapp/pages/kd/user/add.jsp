<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增用户</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/adduser.css" />
			<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
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
			<div class="banner-right">
					<ul>
						<li><a href="${ctx}/kd/user">用户列表</a></li>
						<li><a href="${ctx}/kd/user/add" class="active">新增用户</a></li>
					</ul>
				
				<div class="banner-right-content">
				
					<div class="banner-right-tel">
						<p>手机号：</p>
						<input type="text" id="y_mobile" onblur="checkMobile(this.value);"/>
						<span class="red">*</span>
					</div>
					<div class="banner-right-name">
						<p>姓名：</p>
						<input type="text" id="realname" />
						<span class="red">*</span>
					</div>
					<div class="banner-right-tel2">
						<p>固定电话：</p>
						<input type="text" id="telephone"/>
					</div>
					<div class="banner-right-email">
						<p>邮箱：</p>
						<input type="text" id="email"/>
					</div>
					<div class="banner-right-code">
						<p>密码：</p>
						<input type="text" id="password"/>
						<span class="red">*</span>
					</div>
					<div class="banner-right-webs">
						<p id="netWork">归属网点<span class="red">*</span>：</p>
						<ul >
							<li><input type="checkbox" class="inputshort" id="selectAll" onclick="selectAll();"/><p>所有网点</p></li><br />
							<c:if test="${fn:length(netWorkList)>0}">
					 		<c:forEach items="${netWorkList}" var="netWork" varStatus="vs">
					 		<li><input type="checkbox" class="inputshort2" value="${netWork.id}" onclick="check();"/><p class="banner-right-addr">${netWork.sub_network_name}</p></li>
					 		</c:forEach>
					 		</c:if>
						</ul>
					</div>
						
					<b class="clear"></b>
					<div class="banner-right-role">
						<p id="role">角色<span class="red">*</span>：</p>
						<ul>
						<c:if test="${fn:length(roleList)>0}">
					 		<c:forEach items="${roleList}" var="role" varStatus="vs">
					 		<input type="hidden" value="role.id" />
							<li><input type="checkbox" class="inputshort3" value="${ role.roleId}"/><p>${role.roleName }</p></li>
							</c:forEach>
						</c:if>
						</ul>
					</div>
					 <input type="button" value="提交" class="inputs" onclick="save();" />
				</div>
			</div>
			<div class="clean-float" >
			
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		var flag=true;
		
		function checkMobile(mobile){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"${ctx}/kd/user/checkMobile", 
				data:{mobile:mobile},
				success:function(data){
					if(!data){
						 Anfa.show("该手机已注册","#y_mobile");	
					}else flag=false;
	            }
			});
			
		}
		
		
		function save(){
			var mobile=$('#y_mobile').val();
			var realname=$('#realname').val();
			var telephone=$('#telephone').val();
			var email=$('#email').val();
			var password=$('#password').val();
			
			reg = /(^0\d{2,3}\-\d{7,8}$)|(^1[3|4|5|6|7|8][0-9]{9}$)/;
			
			if(!(reg.test(mobile))){
    			  Anfa.show("手机输入错误","#y_mobile");
    		    return;
    		}
			
			if(flag){
				Anfa.show("该手机已注册","#y_mobile");
				return;
			}
			
			if(realname.length==0){
				Anfa.show("姓名不能为空","#realname");
				return;
			}
			
			if(password.length==0){
				Anfa.show("密码不能为空","#password");
				return;
			}else if(password.length<6){
				Anfa.show("密码长度过短","#password");
				return;
				
			}
			
			//选中网点idarray
			var netArray = new Array();
			$(".banner-right-webs input[type='checkbox']").each(function(i){
				if(i!=0){
			if($(this).prop("checked"))netArray.push($(this).val());
				}
			});
			console.log(netArray);
			//选中角色idarray
			var roleArray = new Array();
			$(".banner-right-role input[type='checkbox']").each(function(i){
			if($(this).prop("checked"))roleArray.push($(this).val());
			});
			console.log(roleArray);
			
			if(netArray.length==0){
				Anfa.show("至少选择一个网点","#netWork");
				return;
			}
			if(roleArray.length==0){
				Anfa.show("至少选择一个角色","#role");
				return;
			}
			
			netArray=netArray.toString();
			roleArray=roleArray.toString();
			
			$.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/kd/user/saveUser", 
					data:{mobile:mobile,realname:realname,telephone:telephone,email:email,password:password,netWorkIds:netArray,roleIds:roleArray},
					success:function(data){
						console.log(data);
						if(data.state=="SUCCESS"){
							window.location.href="${ctx}/kd/user";
						}
		            }
				});
			
		}
		
		  //全选事件
		function selectAll() {
			if($("#selectAll").is(':checked')){
				$(".banner-right-webs input[type='checkbox']").prop("checked", true);
			}else{
				$(".banner-right-webs input[type='checkbox']").prop("checked", false)
			}
		}
		 
		function check(){
			var check=$(".banner-right-webs input[type='checkbox']");
			for(var i=1;i<check.length;i++){
				if(check[i].checked==false){
					$("#selectAll").prop("checked", false);
					return;
				}
			}
			$("#selectAll").prop("checked", true);
		}
		  
		
	</script>
	
	</body>
</html>
