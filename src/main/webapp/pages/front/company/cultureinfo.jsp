<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>公司文化</title>
		<link rel="stylesheet" href="${ctx }/static/pc/stung/css/slide.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/Culture.css?v=${version}" />
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/stung/js/slide.js"></script>
	</head>
	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		   <div class="banner-guno">
		     <img src="${ctx}/static/pc/img/banner_culture.jpg"> 
		   </div>
		   <div class="banner-list">
		      <div class="banner-list-left">
		          <p>公司文化</p>
		          <ul>
		             <li class="banner-list-lefts"><a class="banner-list-lefts-a">公司品牌</a></li>
		             <li><a class="banner-list-lefts-as" href="${ctx}/company/mission?id=${id}">公司使命</a></li> 
		             <li><a class="banner-list-lefts-as" href="${ctx}/company/sense?id=${id}">公司价值观</a></li>
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
		               <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:264px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
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
		           <img src="${ctx}/static/pc/img/zs_con_icon22.jpg"/>
		           <span>首页 > 企业文化 > 公司品牌</span>
		         </div>
		         
		         <c:if test="${empty brand0 && empty brand1 && empty brand2 && empty shop.culture_desc }">
		            <div style="text-align: center; margin-top: 260px;">暂无数据</div>
		         </c:if>
		         <div class="banner-list-rightsiu">
				     <c:if test="${not empty brand0}">
				       	<div class="banner-list-img">
			               <img src="${brand0}"/>
			             </div>
				     </c:if>
		             <c:if test="${not empty brand1}">
				       	<div class="banner-list-img2">
			               <img src="${brand1}"/>
			             </div>
				     </c:if>
				     <c:if test="${not empty brand2}">
				       	<div class="banner-list-img3">
			               <img src="${brand2}"/>
			             </div>
				     </c:if>
		             <p>${shop.culture_desc}</p>
		         </div>
		     </div>   
		   </div>
		</div>
		<%@ include file="common/foot.jsp" %>
	</body>
</html>
