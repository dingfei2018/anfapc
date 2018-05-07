<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>公司形象</title>
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/figure.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/css/lrtk2.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx}/static/pc/js/jquery.imgbox.pack.js"></script>
	</head>
	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		   <div class="banner-guno">
		    <img src="${ctx}/static/pc/img/banner_pic.jpg"> 
		   </div>
		
		    <div class="banner-list">
		   
		      <div class="banner-list-left">
		          <p>形象展示</p>
		          <ul>
		             <li class="banner-list-lefts"><a class="banner-list-lefts-a">企业形象</a></li>
		             <%-- <li><a class="banner-list-lefts-as" href="${ctx}/company/figure?id=${id}">荣誉资质</a></li> --%>
		          </ul>
		          <div class="banner-list-left1">
		              <img src="${ctx}/static/pc/img/zs_con_icon1.jpg"/>
		              <dl>
		                 <dt>注册信息</dt>
		                 <dd>${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</dd>
		              </dl>
		          </div>
		          
		          <div class="banner-list-left2">
		              <img src="${ctx}/static/pc/img/zs_con_icon2.jpg"/>
		              <dl>
		                 <dt>零担运输</dt>
		                 <dd>专线 特大货物 大件</dd>
		              </dl>
		          </div>
		          
		          <div class="banner-list-left3">
		              <img src="${ctx}/static/pc/img/zs_con_icon3.jpg"/>
		              <dl>
		                 <dt>服务内容</dt>
		                 <dd>整车运输 零担运输</dd>
		              </dl>
		          </div>
		          
		          <div class="banner-list-left4">
		              <img src="${ctx}/static/pc/img/zs_con_icon4.jpg"/>
		              <dl>
		                 <dt>联系人 : ${company.charge_person}</dt>
		                 <dd>
							<c:if test="${shop.show_mobile}">
								${empty company.corp_telphone?company.charge_mobile:company.corp_telphone}
							</c:if>
							<c:if test="${not shop.show_mobile}">
								${company.corp_telphone}
							</c:if>	  
						</dd>
		              </dl>
		          </div>
		          
		      </div>
		      
		     <div class="banner-list-right">
		         <div class="banner-list-rights">
		         <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
			        <ul>
			           <li><a href="${ctx}/"></a></li>
			           <li><a class="content-right-okl" href="#"></a></li>
			        </ul>
			        </div>
		           <img src="${ctx}/static/pc/img/zs_con_icon22.jpg"/>
		           <span>首页 > 形象展示</span>
		         </div>
		     <div class="banner-list-rights-img">    
		       <c:if test="${not empty cert0}">
		       		<div class="banner-list-rights2 img">
		              <a class="example-image-link" href="${cert0}" data-lightbox="example-1"><img src="${cert0}"/></a>
		             <p>${cert0Name}</p>
		         </div>
		       </c:if>
		          
			    <c:if test="${not empty cert1}">
		       		<div class="banner-list-rights3 img">
		             <a class="example-image-link" href="${cert1}" data-lightbox="example-1"><img src="${cert1}"/></a>
		             <p>${cert1Name}</p>
		         </div>
		       </c:if>
		       
		       <c:if test="${not empty cert2}">
		       		<div class="banner-list-rights4 img">
		             <a class="example-image-link" href="${cert2}" data-lightbox="example-1"><img src="${cert2}"/></a>
		             <p>${cert2Name}</p>
		         </div>
		       </c:if>
		       
		       <c:if test="${not empty figu0}">
		       		 <div class="banner-list-rights5 img">
		             <a class="example-image-link" href="${figu0}" data-lightbox="example-1"><img src="${figu0}"/></a>
		             <p>公司网站宣传图片</p>
		         </div>
		       </c:if>    
		         
		      <c:if test="${not empty figu1}">
		       		 <div class="banner-list-rights6 img">
		              <a class="example-image-link" href="${figu1}" data-lightbox="example-1"><img src="${figu1}"/></a>
		             <p>公司网站宣传图片</p>
		         </div>
		       </c:if>   
		       
		        <c:if test="${not empty figu2}">
		       		 <div class="banner-list-rights7 img">
		             <a class="example-image-link" href="${figu2}" data-lightbox="example-1"><img src="${figu2}"/></a>
		             <p>公司网站宣传图片</p>
		         </div>
		       </c:if>   
		     </div>    
	           <c:if test="${empty cert1 and empty cert1 and empty cert2 and empty figu0 and empty figu1 and empty figu2 }">
		         	 <div style="text-align: center; margin-top: 260px;">暂无数据</div>
		         </c:if>
		     </div>   
		   </div>
		</div>
		<%@ include file="common/foot.jsp" %>
		<script type="text/javascript">
		$(function(){
		 	lightbox.option({'resizeDuration': 100,'wrapAround': true})
		})
		</script>
	</body>
</html>