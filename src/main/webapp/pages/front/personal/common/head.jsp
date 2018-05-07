<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<!--头部-->
<div class="head-list">
	<div class="head">
		<c:if test="${!empty session_user.mobile }" >
			<%-- <div class="head-left">
				<p>${session_user.mobile} &nbsp;<span class="span">欢迎来到网上物流</span> </p>
			</div>
			<div class="head-content">
				<a href="${ctx }/front/user/logout">退出</a>
			</div> --%>
			<div class="head-left">
					<p><span class="span">${session_user.mobile} </span> 欢迎来到网上物流！</p>
				</div>
				<div class="head-content">
					<a href="${ctx }/front/user/logout">退出</a>
				</div>
		</c:if>
		<c:if test="${empty session_user.mobile}" >
			<div class="head-content">
				<a href="${ctx}/front/user">登录</a>
				<a href="${ctx}/front/register" class="head-content-right">免费注册</a>
			</div>
		</c:if>
		<div class="head-right">
			<ul>
				<li><a href="${ctx}/index">首页</a></li>
				<li><a href="${ctx}/front/order">我的订单</a></li>
				<li class="banner-right-banner"><a id="activety" href="${ctx}/front/userconter">个人中心</a></li>
				<li><a href="${ctx}/kd">我要开单</a></li>
				<li><a href="${ctx}/front/message/showUnRead">消息</a></li>
				<li><a href="${ctx}/front/customer">客服服务</a></li>
			</ul>
		</div>
		<c:if test="${not empty session_user && session_user.isCert!=2}">
			        <p class="banner-right-gjk-p"><img id="kop" src="${ctx }/static/pc/img/point2.png"/></p>
					<%-- <p class="banner-right-gjk-ps"><img id="kops" style="display:none"  src="${ctx }/static/pc/img/info_modi2.png"/></p> --%>
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
<div class="main" style="overflow: hidden">
	<div class="main-left">
		<a href="${ctx}/index"><img src="${ctx}/static/pc/img/LOGO_04.png" /></a>
	</div>
	<div class="main-right" id="flexslider" style="overflow: hidden;">
	
	<ul>
		<li><img src="${ctx}/static/pc/img/b1.jpg" alt="" width="700" height="160" ></li>
        <li><img src="${ctx}/static/pc/img/b2.jpg" alt="" width="700" height="160" ></li>
        <li><img src="${ctx}/static/pc/img/d_6.png" alt="" width="750" height="160" ></li>
	</ul>
	<script>
	  $(function(){
	    var unslider04 = $('#flexslider').unslider({
			dots: true
		}),
		data04 = unslider04.data('unslider');
		$('.unslider-arrow04').click(function() {
	        var fn = this.className.split(' ')[1];
	        data04[fn]();
	    }); 
		$("#flexslider ul").css("height","160");
	});
	</script>
	</div>
</div>