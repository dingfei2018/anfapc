<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>欢迎来到${company.corpname}!</title>
		<link rel="stylesheet" href="${ctx }/static/pc/stung/css/slide2.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/test.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/lrtk2.css?v=${version}"/>
	    <link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
	    <script src="${ctx}/static/pc/js/jquery.js"></script>
	    <script src="${ctx}/static/pc/js/meno.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/stung/js/slide.js"></script>
	</head>
	<body style="max-height: 0px;">
	   
		<%@ include file="common/header.jsp" %>
				<div class="ck-slide" style="width: 100%; height: 437px; margin:auto;">
				<ul class="ck-slide-wrapper">
				<c:choose>
				<c:when test="${empty scrl0}"><li><a><img src="${ctx}/static/pc/img/banner1.jpg"></a></li></c:when>
				<c:otherwise><li><a><img src="${scrl0}"></a></li></c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${empty scrl1}"><li style="display:none"><a><img src="${ctx}/static/pc/img/banner-02.png"></a></li></c:when>
				<c:otherwise><li style="display:none"><a><img src="${scrl1}"></a></li></c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${empty scrl2}"><li style="display:none"><a><img src="${ctx}/static/pc/img/banner3.jpg"></a></li></c:when>
				<c:otherwise><li style="display:none"><a><img src="${scrl2}"></a></li></c:otherwise>
				</c:choose>
				</ul>
				<!--<a href="javascript:;" class="ctrl-slide ck-prev">上一张</a> <a href="javascript:;" class="ctrl-slide ck-next">下一张</a>-->
				<div class="ck-slidebox" style="bottom: 0px; margin-left: -600px;">
					<div class="slideWrap">
					<!-- <ul class="dot-wrap" style="width: 61px;margin:auto;">
							<li class="current"><em>1</em></li>
							<li><em>2</em></li>
							<li><em>3</em></li>
						</ul>  -->
					</div>
	             <div class="benner-list">
	                <div style="width:1200px;height:44px; z-index: 40;position: absolute;left:0;top:0;overflow: hidden;" id="s3">
		             <img class="benner-list-img1" src="${ctx}/static/pc/img/gonggao.png"/>
		             <p>最新公告 :</p>
			             <ul>
			             <c:forEach items="${notices}" var="n">
				             <%-- <span>▪ ${n.content}</span> --%>
							   <li><a>▪ ${n.content}</a></li>
		                 </c:forEach>
						 </ul>
		             <img id="btn1" class="benner-list-img2" src="${ctx}/static/pc/img/leftll.png"/>
		             <img id="btn2" class="benner-list-img3" src="${ctx}/static/pc/img/rightll.png"/>
	                 </div>
	              <div style="width:1200px;height:44px;background-color:#056bb3;z-index: -31;opacity: 0.6;"></div>
	             </div>
				</div>
			</div>
		<script>
	 	$('.ck-slide').ckSlide({autoPlay: true}); 
		$("#s3").Scroll({line:1,speed:500,timer:4000,up:"btn1",down:"btn2"});
		</script>
		
		<div class="benner">
			<div class="benner-top">
			    <div class="benner-left">
			       <div class="benner-left1">
			          <img src="${ctx}/static/pc/img/zs_company5566.jpg"/>
			          <a class="a" href="${ctx}/company/introduction?id=${id}">MORE+</a>
			         <img class="benner-left1-img" src="${ctx}/static/pc/img/zs_aboutpic.jpg"/> 
			          <p><a class="a1" href="${ctx}/company/introduction?id=${id}">${shop.shop_desc_short}</a></p>
			       </div>
			       <div class="benner-left2">
			          <img src="${ctx}/static/pc/img/zs_news5567.jpg"/>
			          <a class="benner-left2-span" href="${ctx}/company/translate?id=${id}">MORE+</a>
			          <ul>
			              <c:forEach items="${news}" var="n">
				             <li><a class="a" href="${ctx}/company/tranDetail?id=${id}&nid=${n.id}">▪ ${n.title}</a></li>
			              </c:forEach>
			          </ul>
			       </div>
			    </div>
			    <div class="banner-right">
			       <c:if test="${empty cert}">
			       		<img src="${ctx}/static/pc/img/zs_company.jpg"/>
			       </c:if>
			       <c:if test="${not empty cert}">
			       		<img src="${cert}"/>
			       </c:if>
			    </div>
			    <div class="banner-bottom">
			          <div class="banner-bottom-gun1" id="${company.is_certification==2?'':'activea'}">
			           <%--  <img src="${ctx}/static/pc/img/ggjjkk (1).jpg"/> --%>
			         <ul>
			            <li>营业执照</li>
			            <li><span>${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</span></li>
			          </ul>
			          </div>
			          <div class="banner-bottom-gun2"  id="${company.is_certification==2?'':'activeb'}">
			         <%--  <img src="${ctx}/static/pc/img/ggjjkk (3).jpg"/> --%>
			          <ul>
			            <li>身份证</li>
			            <li><span>${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</span></li>
			          </ul>
			          </div>
			          <div class="banner-bottom-gun3"  id="${company.is_certification==2?'':'activec'}">
			         <%--  <img src="${ctx}/static/pc/img/ggjjkk (4).jpg"/> --%>
			          <ul>
			            <li>公司门面照</li>
			            <li><span>${company.is_certification==2?'推广人员已核实':'推广人员未核实'}</span></li>
			          </ul>
			          </div>
						    <c:if test="${shop.show_mobile}">
						          <div class="banner-bottom-gun4"  id="${company.is_certification==2?'':'actived'}">
						          <%-- <img src="${ctx}/static/pc/img/ggjjkk (2).jpg"/> --%>
						          <ul>
						            <li>
											${empty company.corp_telphone?company.charge_mobile:company.corp_telphone}
						            </li>
						            <li><span>${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</span></li>
						          </ul>
						          </div>
							</c:if>
							<c:if test="${not shop.show_mobile}">
								<div class="banner-bottom-gun4"  id="${company.is_certification==2?'':'actived'}">
						          <%-- <img src="${ctx}/static/pc/img/ggjjkk (2).jpg"/> --%>
						          <ul>
						            <li>
											${company.corp_telphone}
						            </li>
						            <li><span>${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</span></li>
						          </ul>
						          </div>
							</c:if>
			    </div>
			</div>
			
			
			<div class="content">
			    <div class="content-left">
			        <img class="content-left-img" src="${ctx}/static/pc/img/zs_h_title2.jpg"/>
			        <c:if test="${not empty companyLines}">
			        <table>
			           <tr>
			              <th style="display:inline-block; width: 150px; line-height:25px;text-indent: 44px;">出发地</th>
			              <th style="width:40px;"><img src="${ctx}/static/pc/img/icon_10.png" style="display: none;"></th>
                          <th style="display:inline-block; width: 150px; line-height:25px;text-indent: -1px;">到达地</th>
                          <th style="">重货价/公斤</th>
                          <th>轻货价/立方</th>
                          <th>起步价</th>
                          <th>发车频次</th>
                          <th>操作</th>
  			           </tr>
  			           <c:forEach items="${companyLines}" var="line">
						<tr height="35">
							<td class="gun" style="display:inline-block; width: 145px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
							<c:if test="${line.is_sale}">
								<img src="${ctx}/static/pc/img/te.gif"/>
							</c:if>
							<a href="${ctx}/company/special?id=${id}" title="${line.from_addr}">${line.from_addr} </a></td>
							<td><img src="${ctx}/static/pc/img/icon_10.png"></td>
							<td class="gun" style="display:inline-block; width: 112px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a title="${line.to_addr}">${line.to_addr}</a></td>
							<c:if test="${line.price_heavy!='0.00'}">
				            	<td>${line.price_heavy}元</td>
				            </c:if>
				            <c:if test="${line.price_heavy=='0.00'}">
				            	<td>面议</td>
				            </c:if>
				            <c:if test="${line.price_small!='0.00'}">
				            	<td>${line.price_small}元</td>
				            </c:if>
				            <c:if test="${line.price_small=='0.00'}">
				            	<td>面议</td>
				            </c:if>
							<td>${line.starting_price=='0.00'?'--':line.starting_price}</td>
							<td>${line.frequency}次/天</td>
							<td><a id="btnud" href="${ctx}/company/special?id=${id}">查看</a></td>
						</tr>
						</c:forEach>
			        </table>
			        <p><a href="${ctx}/company/special?id=${id}">MORE+</a></p>
			        </c:if>
			        <c:if test="${ empty companyLines}">
			        	<div style="text-align: center;margin-top: 60px;color:#666;font-size:12px;">暂无数据</div>
			        </c:if>
			    </div>
			    <div class="content-right">
			        <img class="content-right-img" src="${ctx}/static/pc/img/zs_h_title3.jpg"/>
			        <div id="editInfo" style=" float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
			        <c:if test="${not empty networks}">
			        <table>
			           <tr>
			              <th style="line-height:25px;">网点地址</th>
			              <th>联系人</th>
			              <th>联系电话</th>
			           </tr>
			           <c:forEach items="${networks}" var="net">
						<tr height="35">
							<td style="display:inline-block; width: 112px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a href="${ctx}/company/branch?id=${id}">${fn:substring(net.address, 0, fn:indexOf(net.address,'|'))}</a></td>
							<td>${net.sub_leader_name}</td>
							<td  style="display:inline-block; width: 112px; word-wrap: break-word; line-height: 36px;">
							<c:if test="${shop.show_network_mobile}">
								${empty net.sub_logistic_telphone?net.sub_logistic_mobile:fn:substring(net.sub_logistic_telphone, 0, fn:indexOf(net.sub_logistic_telphone, ','))}
							</c:if> 
							<c:if test="${!shop.show_network_mobile}">
								${fn:substring(net.sub_logistic_telphone, 0, fn:indexOf(net.sub_logistic_telphone, ','))}
							</c:if>
							</td>
						</tr>
						</c:forEach>
			        </table>
			        <p><a href="${ctx}/company/branch?id=${id}">MORE+</a></p>
			        </c:if>
			        <c:if test="${ empty networks}">
			        	<div style="text-align: center;margin-top: 60px;color:#666;font-size:12px;">暂无数据</div>
			        </c:if>
			    </div>
			</div>
		</div>
		      
		<div class="tips">
		     <div class="tipsc">
		         <img class="tipsc-img" src="${ctx}/static/pc/img/zs_h_title4.jpg"/>
		         <div class="tipsc-left">
		            <img src="${ctx}/static/pc/img/zs_left_icon2.png"/>
		         </div>
		          <div class="tipsc-opu1 img">
		              <div class="tipsc-opu1-img">
			              <c:if test="${empty figu0}">
					       		<img/>
					       </c:if>
					       <c:if test="${not empty figu0}">
					       		<a class="example-image-link" href="${figu0}" data-lightbox="example-1"><img src="${figu0}"/></a>
					       </c:if>
		              </div>
		              <p>公司形象照</p>
		          </div>
		          
		          <div class="tipsc-opu2 img">
		              <div class="tipsc-opu2-img">
		              	  <c:if test="${empty figu1}">
					       		<img/>
					       </c:if>
					       <c:if test="${not empty figu1}">
					       		<a class="example-image-link" href="${figu1}" data-lightbox="example-1"><img src="${figu1}"/></a>
					       </c:if>
		              </div>
		              <p>公司照片</p>
		          </div>
		          <div class="tipsc-opu3 img">
		              <div class="tipsc-opu3-img">
		              		<c:if test="${empty figu2}">
					       		<img/>
					       </c:if>
					       <c:if test="${not empty figu2}">
					       		<a class="example-image-link" href="${figu2}" data-lightbox="example-1"><img src="${figu2}"/></a>
					       </c:if>
		              </div>
		              <p>企业宣传照</p>
		          </div>
		         <div class="tipsc-right">
		            <img src="${ctx}/static/pc/img/zs_right_icon2.png"/>
		         </div>
		     </div>
		</div>
		
		 <%@ include file="common/foot.jsp" %> 
		<script type="text/javascript">
		$(function(){
		 	$(".footer").css("background","white"); 
		 	lightbox.option({'resizeDuration': 100,'wrapAround': true})
		})
		</script>
	</body>
</html>