<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
		<style>
		    .activety{
		      border-bottom: 1px solid blue;
		      font-weight: bold;
		      color: #3974f8;
		    }
		    #activety{
		      border-bottom: 1px solid blue;
		      font-weight: bold;
		      color: #3974f8;
		    }
		</style>
        <!---头部的内容---->
		<div class="head-list">
			<div class="head">
				<div class="head-left">
					<p><c:if test="${!empty session_user.mobile }"><span></c:if>${session_user.mobile}</span>欢迎来到网上物流！ </p>
				</div><c:if test="${!empty session_user.mobile }">
				<div class="head-content">
					<a href="${ctx }/front/user/logout">退出</a>
				</div>
                 </c:if>
			
				<c:if test="${empty session_user.mobile}" >
					<div class="head-content">
						<a href="${ctx}/front/user">请登录</a><a href="${ctx}/front/register/wl" class="head-center-a">快速注册</a>
					</div>
				</c:if>
				<div class="head-right">
					<ul id="test">
						<li ><a id='<c:if test="${curr eq 22}">activety</c:if>' href="${ctx}/">首页</a></li>
						<li ><a id='<c:if test="${curr eq 23}">activety</c:if>' href="${ctx}/front/order">我的订单</a></li>
						<li ><a href="${ctx}/kd">网上物流TMS</a></li>
						<li class="banner-right-banner"><a id='<c:if test="${curr eq 1}">activety</c:if>' href="${ctx}/front/userconter">个人中心</a></li>
						<!-- <li>我要开单</li> -->
						<li ><a  id='<c:if test="${curr eq 20}">activety</c:if>' href="${ctx}/front/message/showUnRead">消息</a></li>
						<li ><a id='<c:if test="${curr eq 24}">activety</c:if>' href="${ctx}/front/customer">客服服务</a></li>
					</ul>
				</div>
				<c:if test="${not empty session_user &&session_user.isCert!=2}">
					<p class="banner-right-gjk-p"><img id="kop" src="${ctx }/static/pc/img/point2.png"/></p>
					<%-- <p class="banner-right-gjk-ps"><img id="kops" style="display: none" src="${ctx }/static/pc/img/info_modi2.png"/></p> --%>
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
				<c:if test="${not empty anotherpage.list}">
	                  <script>
							$(function(){
								$(".banner-right-gjk").show(); 
							});
					  </script>
				</c:if>
			</div>
		</div>
		<!---轮播图的内容---->
		<div class="main">
			<div class="main-left">
				<a href="${ctx}/index"><img src="${ctx}/static/pc/img/LOGO_04.png" /></a>
			</div>
			
			<div class="main-right" id="flexslider">
				<ul>
			        <li><img src="${ctx}/static/pc/img/b1.jpg" alt="" width="750" height="160" ></li> 
			        
			         <li><img src="${ctx}/static/pc/img/b2.jpg" alt="" width="750" height="160" ></li>
			         
			         <li><img src="${ctx}/static/pc/img/d_6.png" alt="" width="750" height="160" ></li>
				</ul>
			</div>
		</div>
		<script>
		$(document).ready(function(e) {
		    var unslider04 = $('#flexslider').unslider({
				dots: true
			}),
			
			data04 = unslider04.data('unslider');
		    
			$('.unslider-arrow04').click(function() {
		        var fn = this.className.split(' ')[1];
		        data04[fn]();
		    });
			
			if(${!empty session_user.mobile }){
			     var li = document.getElementById("test").getElementsByTagName("li");
			        li[li.length-2].style.borderBottom = "none";
			}
		});
		</script>