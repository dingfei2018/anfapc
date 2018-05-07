<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>网点分布</title>
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/Branch.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js?v=${version}"></script>
	</head>
	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		   <div class="banner-guno">
		    <img src="${ctx}/static/pc/img/banner_salemap.jpg"> 
		   </div>
		    <div class="banner-list">
		      <div class="banner-list-left">
		          <p>网点分布</p>
		          <ul>
		             <li class="banner-list-lefts"><a class="banner-list-lefts-a">网点分布</a></li>
		             <li><a class="banner-list-lefts-as" href="${ctx}/company/special?id=${id}">物流专线</a></li>
		          </ul>
		          <div class="banner-list-left1">
		              <img src="${ctx}/static/pc/img/zs_con_icon1.jpg"/>
		              <dl>
		                 <dt>注册信息</dt>
		                 <dd> ${company.is_certification==2?'后台系统已认证':'后台系统未认证'} </dd>
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
		           <img src="${ctx}/static/pc/img/zs_con_icon22.jpg"/>
		           <span>首页 > 网点分布</span>
		         </div>
		         
		            <c:if test="${ not empty networks}">
		         <table cellspacing="0">
		            <tr>
		               <th style="width:80px;">网点地址</th>
		               <th>详细地址</th>
		               <th>联系人</th>
		               <th>联系电话</th>
		               <th class="banner-list-rightui">操作</th>
		            </tr>
		            <c:forEach items="${networks}" var="net">
						<tr>
							<td><a style="display:inline-block; width: 80px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">${fn:substring(net.address, 0, fn:indexOf(net.address,'|'))}</a></td>
							<td><a style="display:inline-block; width: 260px; font-size: 14px;">${fn:substring(net.address, fn:indexOf(net.address,'|')+1, fn:length(net.address))}</a></td>
							<td>${net.sub_leader_name}</td>
							<td class="banner-list-rightuis"><a style="display:inline-block; width: 99px; word-wrap: break-word;">
							<c:if test="${shop.show_network_mobile}">
								<c:if test="${empty net.sub_logistic_telphone}">
									${net.sub_logistic_mobile}
								</c:if>
								<c:if test="${not empty net.sub_logistic_telphone}">
									<c:forEach items="${fn:split(net.sub_logistic_telphone, ',')}" var="name">
										<p>${name}</p>
									</c:forEach>
								</c:if>
							</c:if> 
							<c:if test="${not shop.show_network_mobile}">
								<c:if test="${not empty net.sub_logistic_telphone}">
									<c:forEach items="${fn:split(net.sub_logistic_telphone, ',')}" var="name">
										<p>${name}</p>
									</c:forEach>
								</c:if>
							</c:if>
							</a></td>
							<%-- <td class="banner-list-rightui"><a class="a" href="${ctx}/company/special?id=4&nId=${net.id}">查看</a></td> --%>
							<td class="banner-list-rightui"><a class="a" href="${ctx}/company/special?id=${id}&nId=${net.id}">查看专线</a> <a class="as" target="_blank" href="${ctx}/map/getAddressMap?corname=网点地址&address=${fn:substring(net.address, fn:indexOf(net.address,'|')+1, fn:length(net.address))}">查看位置</a></td>
						</tr>
					</c:forEach>
		         </table>
		           <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
			        </c:if>
			        <c:if test="${empty networks}">
			       <div style="text-align: center; margin-top: 260px;">
			         <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>暂无数据</div>
			       </c:if>
		     </div>   
		   </div>
		</div>
		<%@ include file="common/foot.jsp" %>
	</body>
</html>
