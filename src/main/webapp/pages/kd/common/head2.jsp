<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${ctx}/static/kd/css/imfor.css"/>
<%-- <link rel="stylesheet" href="${ctx}/static/kd/css/layui.css?v=${version}"/> --%>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/layui/layui.js"></script>
<!--

//-->
</script>
<!--头部-->
<div class="head-list">
	<div class="head">
		<c:if test="${!empty session_user.mobile }" >
			<div class="head-left">
					<p><span class="span" id="mobile_span" style="color: #3974f8; cursor: pointer;">${session_user.mobile} </span> 欢迎来到网上物流！</p>
				</div>
					<script type="text/javascript">
					$(function(){
				    	   $("#mobile_span").mouseover(function(){
				    		   $(".banner-imfor-title").show();				    		   
				    	   }).mouseleave(function(){				    		 
				    		   $(".banner-imfor-title").hide();
				    	   }) 
				       });
					</script>
				<div class="head-content">
					<a href="#" id="change_word">修改密码</a>
					<script type="text/javascript">
					$(function(){
				    	   $("#change_word").click(function(){
				    		   layer.open({
				 				  type: 2,
				 				  title: "修改密码",
				 				  area: ['520px', '460px'],
				 				  content: ['/kd/password', 'yes']
				 				  }) 
				       });
					})
					</script>
					<a href="${ctx }/front/user/logout">退出</a>
				</div>
		</c:if> 
		<c:if test="${empty session_user.mobile}" >
			<div class="head-content">
				<a href="${ctx}/front/user">登录</a>
				<a href="${ctx}/front/register" class="head-content-right">免费注册</a>
			</div>
		</c:if>
	
		<c:if test="${not empty session_user && session_user.isCert!=2}">
					 <script type="text/javascript">
					       $(function(){
					    	   $(".banner-right-banner").mouseover(function(){
					    		   $("#kop").show();
					    		   $("#kops").hide();
					    	   }).mouseleave(function(){
					    		   $("#kop").show();
					    		   $("#kops").hide();
					    	   }) 
					       });
					</script> 
		</c:if>
		<div class="banner-right-gjk" ></div>
	</div>
</div>
		<div class="banner-imfor-title">
		<div class="banner-title-imfor">
			<table>
				<tr>					
					<td>手机号：<span>${sessionScope.topUser.mobile }</span></td>
					<td>姓名：<span>${sessionScope.topUser.realname }</span></td>
				</tr>
				<tr>
					<td>固定电话：<span>${sessionScope.topUser.telephone }</span></td>
					<td>邮箱：<span>${sessionScope.topUser.email }</span></td>		
				</tr>
			
			</table>	
			<table>
				<tr>					
					<td style="width: 320px;">角色：<span>${sessionScope.topUser.roleName }</span></td>
				</tr>
				<tr>
					<td style="width: 320px;">归属网点：<span>${sessionScope.topUser.netWorkName }</span></td>		
				</tr>
			</table>	
		</div>
			
		</div>		
		
		