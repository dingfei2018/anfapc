<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>联系我们</title>
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/contact.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="http://webapi.amap.com/maps?v=1.4.0&key=8325164e247e15eea68b59e89200988b"></script>
		<script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
			
	</head>
	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		       <div class="banner-guno">
			      <img src="${ctx}/static/pc/img/banner_contact.jpg"> 
			   </div>
		     <div class="banner-list">
		   
		      <div class="banner-list-left">
		          <p>联系我们</p>
		          <ul>
		             <li class="banner-list-lefts"><a class="banner-list-lefts-as">联系我们</a></li>
		             <li><a class="banner-list-lefts-a" href="${ctx}/company/introduction?id=${id}">关于我们</a></li>
		          </ul>
		          <div class="banner-list-left1">
		              <img src="${ctx}/static/pc/img/zs_con_icon1.jpg"/>
		              <dl>
		                 <dt>注册信息</dt>
		                 <dd> ${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</dd>
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
		           <span>首页 > 联系我们</span>
		           
		            <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:264px; right:50%; margin-right: -663px;">
				        <ul>
				           <li><a href="${ctx}/"></a></li>
				           <li class="li"><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
		         </div>
		         <h1>${company.corpname}</h1>
		         <ul>
		            <li>联系人：<span>${company.charge_person}</span></li>
		            <c:if test="${shop.show_mobile}">
		            	<c:if test="${empty company.corp_telphone}">
			            	<li>手机：<span>
				           	 ${company.charge_mobile}
				            </span></li>
		            	</c:if>
		            	<c:if test="${not empty company.corp_telphone}">
			            	<li>公司电话： <span>
				           	 ${company.corp_telphone}
				            </span></li>
		            	</c:if>
					</c:if>
					<c:if test="${not shop.show_mobile}">
						<li>公司电话：<span>
			            ${company.corp_telphone}
			            </span></li>
					</c:if> 		   
		            <li>总公司所在地：<span>${full_addr}</span></li>
		         </ul>
		         
		         <div class="banner-list-rightsl">
		             <div id="wrap" class="my-map">
						<div id="mapContainer"></div>
					</div>
		         </div>
		     </div>
		   </div>
		</div>
		
		
		<script type="text/javascript">
			AMap.service('AMap.Geocoder',function(){//回调函数
			    //实例化Geocoder
			    geocoder = new AMap.Geocoder({
			       // city: "010"//城市，默认：“全国”
			    });
			
			    geocoder.getLocation("${full_addr}", function(status, result) {
			        if (status === 'complete' && result.info === 'OK') {
			        	var locate = result.geocodes[0].location
			        	
			        	var map = new AMap.Map("mapContainer", {center: new AMap.LngLat(locate.lng, locate.lat), level: 13});
			            
					    var marker = new AMap.Marker({
				            map:map,
				            bubble:true
				        })
			    		map.on('complete', function(){
			    			map.plugin(["AMap.ToolBar", "AMap.OverView", "AMap.Scale"], function(){
				    			map.addControl(new AMap.ToolBar);
				    			map.addControl(new AMap.Scale);
				    			
			    			});	
			    			$(".amap-logo").hide();
			    		})
			    		var infoWindow;
			    		if(!infoWindow){ 
			    			infoWindow = new AMap.InfoWindow({autoMove: true}); 
			    		}
						infoWindow.setContent("<h5>${company.corpname}</h5><div>${full_addr}</div>");
						infoWindow.open(map,locate);
			        }else{
			           // alert("地图获取失败，请稍后再试");
			        }
			    }); 
			    
			});	
		</script>
		<%@ include file="common/foot.jsp" %>
	</body>
</html>
