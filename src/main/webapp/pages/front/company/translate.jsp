<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新闻资讯</title>
		<link rel="stylesheet" href="${ctx }/static/pc/stung/css/slide.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/translate.css?v=${version}" />
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/stung/js/slide.js"></script>
	</head>
	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		   <div class="banner-guno">
		       <img src="${ctx}/static/pc/img/banner_about.jpg"> 
		   </div>
		   <div class="banner-list">
		      <div class="banner-list-left">
		          <p>新闻资讯</p>
		          <ul>
		             <li class="banner-list-lefts"><a class="banner-list-lefts-a">新闻资讯</a></li>
		            <%--  <li><a class="banner-list-lefts-as" href="${ctx}/company/contact?id=${id}">联系我们</a></li> --%>
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
		           <span>首页 > 新闻资讯</span>
		         </div>
		         <c:if test="${not empty news}">
		         <div class="banner-list-rightsiu">
		              <ul>
		              	  <c:forEach items="${news}" var="n">
			                 <li><a href="${ctx}/company/tranDetail?id=${id}&nid=${n.id}">▪ ${n.title} </a><span><fmt:formatDate value="${n.created}" type="date"/></span></li>
			              </c:forEach>
		              </ul>
		         </div>
		         </c:if>
		         <c:if test="${ empty news}">
		         	<div style="text-align: center; margin-top: 260px;">暂无数据</div>
		         </c:if>
		     </div>   
		   </div>
		</div>
		<%@ include file="common/foot.jsp" %>
	</body>
</html>
