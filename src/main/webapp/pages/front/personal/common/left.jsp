<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/content-left.css?v=${version}"/>
<div class="content-left">
	<ul>
	   <li id="content-left-op" class='<c:if test="${curr eq 1}">active</c:if>'><a href="${ctx}/front/userconter/getCompanyInfo"></a></li>
		<li id="guns" class='<c:if test="${curr eq 1}">active</c:if>'><img class="content-left-img" src="${ctx }/static/pc/img/member_2.jpg"/><a href="${ctx}/front/userconter/getCompanyInfo">基本信息</a>
		<%-- <c:if test="${not empty session_user && session_user.isCompany!=1}">
		<img id="btnt" src="${ctx }/static/pc/img/info_modi.png"/>
		<img id="btnu" src="${ctx }/static/pc/img/info_modi2.png"/>
		</c:if> --%>
		</li>
		
		<li id="gun" class='<c:if test="${curr eq 2}">active</c:if>'><img class="content-left-img" src="${ctx }/static/pc/img/member_3.jpg"/><a href="${ctx}/cpy/admin/cert">实名认证</a>
		<c:if test="${not empty session_user && session_user.isCert!=2}">
		<img id="btn" src="${ctx }/static/pc/img/info_modi.png"/>
		<img id="btns" src="${ctx }/static/pc/img/info_modi2.png"/>
		</c:if>
		</li>
		<li class='<c:if test="${curr eq 3}">active</c:if>'><img class="content-left-img" src="${ctx }/static/pc/img/member_4.jpg"/><a href="${ctx}/front/order/hasGoods">已发的货</a></li>
		<li class='<c:if test="${curr eq 11}">active</c:if>'><img class="content-left-img" src="${ctx }/static/pc/img/changfa.png"/><a href="${ctx}/front/userconter/oftenGoods">常发货物</a></li>
		<c:if test="${session_user.usertype==4 && session_user.isCert==2}">
			<li class="content-li" onclick="closeDiv()"><img class="content-left-img" id="content-li-img" src="${ctx }/static/pc/img/member_9.jpg"/><a href="javascript:void(0);">网站管理
			</a></li>
			<dl id="close-div">
				<dt class='<c:if test="${curr eq 4}">active</c:if>'><a href="${ctx}/cpy/admin">公司概况管理</a></dt>
				<dd class='<c:if test="${curr eq 5 || curr eq 10 }">active</c:if>'><a href="${ctx}/front/branch">网点管理</a></dd>
				<dd class='<c:if test="${curr eq 6 ||curr eq 9}">active</c:if>'><a href="${ctx}/front/userconter/line">专线管理</a></dd>
				<dt class='<c:if test="${curr eq 12 ||curr eq 15}">active</c:if>'><a href="${ctx}/cpy/admin/advisory">资讯管理</a></dt>
				<dd class='<c:if test="${curr eq 13}">active</c:if>'><a href="${ctx}/cpy/admin/corporate">公司文化</a></dd>
				<dd class='<c:if test="${curr eq 14 ||curr eq 16}">active</c:if>'><a href="${ctx}/cpy/admin/notice">公司公告</a></dd>
			</dl>
		</c:if>
		<li class='<c:if test="${curr eq 7}">active</c:if>'><img class="content-left-img" src="${ctx }/static/pc/img/member_7.jpg"/><a href="${ctx}/front/userconter/code">修改密码</a></li>
		<c:if test="${session_user.usertype==4 && session_user.isCert==2}">
			<li id="yulan"><img class="content-left-img" src="${ctx }/static/pc/img/iconn.jpg"/><a href="${ctx}/company?id=${session_user.companyId}" target="_blank">预览网站</a></li>
			<li id="yulan"  class='<c:if test="${curr eq 17}">active</c:if>'><img class="content-left-img" src="${ctx }/static/pc/img/member_5.jpg"/><a href="${ctx}/cpy/admin/settings">网站设置</a></li>
		</c:if>
	</ul>
	
	<p></p>
</div>
<script>

	 if("${curr}"==4||"${curr}"==5||"${curr}"==6||"${curr}"==9||"${curr}"==10||"${curr}"==12||"${curr}"==13||"${curr}"==14||"${curr}"==15||"${curr}"==16){
		$("dl").show();
	 }   
	 
	   function closeDiv(){
		  var p = $("#close-div").css("display");
		  if(typeof(p)=="undefined"||p==""||p=="block"){
		   $("#close-div").css("display","none");// 关闭
		  }else{
		   $("#close-div").css("display","block");//打开
		  }
		}
	   
	  $("#gun").mouseover(function(){
		   $("#btn").hide();
		   $("#btns").show();
	   }).mouseleave(function(){
		   $("#btn").show();
		   $("#btns").hide();
	   }) 
       $("#guns").mouseover(function(){
		   $("#btnt").hide();
		   $("#btnu").show();
	   }).mouseleave(function(){
		   $("#btnt").show();
		   $("#btnu").hide();
	   })    
	   
 	  
</script>