<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/head.css?v=${version}" /> 
 <div class="heads">
	       <div class="heads-test">
	           <a href="${ctx}/"> <img class="heads-test-img" src="${ctx}/static/pc/img/zs_fh_logo.jpg" /></a>
	            
	            <!-- 登录后的状态 -->
	           <c:if test="${!empty session_user.mobile }">
	            		<ul>
 					   <li class="heads-test-li"><a href="${ctx}/front/userconter">${session_user.mobile}&nbsp;&nbsp;&nbsp;欢迎来到网上物流</a></li>
	                   <li><a href="${ctx }/front/user/logout">退出</a></li>
	                   </ul>
				</c:if>
	           
	            <c:if test="${empty session_user.mobile}" >
		            <!-- 登录前的状态 -->
		            <ul>
		               <li class="heads-test-li"><a href="${ctx}/">返回网上物流</a></li>
		               <li><a href="${ctx}/front/user">请登录</a></li>
		               <li><a href="${ctx}/front/register/wl">快速注册</a></li>
		            </ul> 
				</c:if>
	            
	             
	            <div class="head-right">
	              <ul>
	                 <li><img src="${ctx}/static/pc/img/zs_app_icon1.jpg"/><a class="a1" href="#"> 找车源APP</a></li>
	                 <li><img src="${ctx}/static/pc/img/zs_app_icon2.jpg"/><a class="a2" href="#"> 找货源APP</a></li>
	               </ul>
	               <script>
	                $(function(){
	                	$(".head-right a").mouseover(function(){
	                		 $(".a1").text("即将发布").css("color","red").css("margin-left","5px"); 
	                	}).mouseout(function(){
	                		$(".a1").text("找车源APP").css("color","black").css("margin-left","5px"); 
	                	});
	                	
	                	$(".head-right a").mouseover(function(){
	                		$(".a2").text("即将发布").css("color","red").css("margin-left","5px"); 
	                	}).mouseout(function(){
	                		$(".a2").text("找货源APP").css("color","black").css("margin-left","5px");  
	                	})
	                })
	              </script>
	              
	            </div>
	            <span class="head-right-spans" style="color:red">即将发布</span>
	            <span class="head-right-span" style="color:red">即将发布</span>
	       </div>
	    </div>
	<div class="header">
		<h1><a href="${ctx}/company?id=${id}" style="color:#000;text-decoration: none;font-weight: bold;">${company.corpname}</a></h1>
		<%-- <img src="${ctx}/static/pc/img/payui.png"> --%>
		 <ul class="header-ul">
		    <li class="header-li"><a href="#">${years}年</a></li>
	<!-- 	    <li class="header-lis"><a href="#">46</a></li> -->
		 </ul>
		 <div class="header-right">
		    <img src="${ctx}/static/pc/img/zs_tel_icon.jpg"/>
			<p>服务热线</p>
			<%-- <b>网上物流： 第<span> ${years} </span>年</b> --%>
			<b>
			<c:if test="${shop.show_mobile}">
				${empty company.corp_telphone?company.charge_mobile:company.corp_telphone}
			</c:if>
			<c:if test="${not shop.show_mobile}">
				${company.corp_telphone}
			</c:if>
			</b>
		</div>

    <div class="head-kgn">
        <ul>
           		<li>
					<a href="${ctx}/company?id=${id}" id='<c:if test="${curr eq 'index'}">active</c:if>'>首页</a>
				</li>
				<li>
					<a href="${ctx}/company/introduction?id=${id}" id='<c:if test="${curr eq 'introduction'}">active</c:if>'>公司简介</a>
				</li>
				<li>
					<a href="${ctx}/company/culture?id=${id}" id='<c:if test="${curr eq 'culture'}">active</c:if>'>公司文化</a>
				</li>
				<li>
					<a href="${ctx}/company/figure?id=${id}" id='<c:if test="${curr eq 'figure'}">active</c:if>'>形象展示</a>
				</li>
				<li>
					<a href="${ctx}/company/branch?id=${id}" id='<c:if test="${curr eq 'branch'}">active</c:if>'>网点分布</a>
				</li>
				<li>
					<a href="${ctx}/company/special?id=${id}" id='<c:if test="${curr eq 'special'}">active</c:if>'>物流专线</a>
				</li>
				<li>
					<a href="${ctx}/company/contact?id=${id}" id='<c:if test="${curr eq 'contact'}">active</c:if>'>联系我们</a>
				</li>
         </ul>    
        <%--  <div class="head-kgn-right">
            <img src="${ctx}/static/pc/img/zs_xd.gif">
            <a  href="${ctx}/company/order?id=${id}">在线下单</a>
         </div> --%>
    </div> 
</div>