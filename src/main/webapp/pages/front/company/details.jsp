<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新闻资讯</title>
		<link rel="stylesheet" href="${ctx }/static/pc/stung/css/slide.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/details.css?v=${version}" />
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
		             <li class="banner-list-lefts"><a class="banner-list-lefts-a" href="${ctx}/company/translate?id=${id}">新闻资讯</a></li>
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
		               <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li><br><br><br>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
		              <dl>
		                 <dt>联系人 : ${company.charge_person}</dt>
		                 <dd>
							<c:if test="${shop.show_mobile}">
								${empty company.corp_telphone?company.charge_mobile:company.corp_telphone}
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
		         <div class="banner-list-rightsiu">
		             <h5>${zixun.title}</h5>
		             <span class="strong1">发布者 :</span><span class="span1">${company.corpname}</span>
		             <span class="strong2">发布日期 :</span><span class="span2"><fmt:formatDate value="${zixun.created}" type="date"/></span>
		             <div class="news_detail">&nbsp;&nbsp;&nbsp;${zixun.content}</div>
		         </div>
		           <div class="banner-list-footer2">
		              <c:if test="${not empty beforeNews}"><p class="p1">上一篇：<a href="${ctx}/company/tranDetail?id=${id}&nid=${beforeNews.id}">${beforeNews.title}</a></p></c:if>
		              <c:if test="${not empty afterNews}"><p>下一篇：<a href="${ctx}/company/tranDetail?id=${id}&nid=${afterNews.id}">${afterNews.title}</a></p></c:if>
		              <a  class="news_back" href="${ctx}/company/translate?id=${id}">返回列表</a> 
		           </div>
		        
		     </div>   
		   </div>
		</div>
		<%@ include file="common/foot.jsp" %>
	</body>
</html>
