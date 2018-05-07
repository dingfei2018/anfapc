<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<title>物流园分布</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/parks.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/js/search.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<style type="text/css">
		   html,#allmap {width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
		#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
		#r-result{height:100%;width:20%;float:left;}
		.anchorBL{display:none;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hwdLiBYBfNbLOUfOy5Pfm5s8zKQK0inN"></script>
	</head>

	<body>
		<%@ include file="../../front/common/loginhead.jsp" %>
		<div class="div_title"><a href="${ctx}/index">首页</a> > 查看物流园分布</div>
		<div class="content">		
		  <div id="left" style="width:300px;height:500px;float:left">
		  <ul class="parklist">
               <c:forEach var="park" items="${parkList}">
				<c:set var="location" value="${fn:split(park.location,',')}" />
				<li data-id="${park.id}"><p>${park.park_name}</p><a>${location[0]}</a>	</li>
			</c:forEach>
			</ul>
    </div>
	<div id="allmap" style="width:900px;height:500px;float:left"></div>
        </div>
		<%@ include file="../../front/common/loginfoot.jsp" %>
	</body>
</html>


<script type="text/javascript">
	// 百度地图API功能
	var infoContent =
	"<h4 style='margin:0 0 5px 0;padding:0.2em 0 ;font-size: 16px'><a style='color: #3974f8' target='_blank' href='${ctx}/front/line/getLineParkList?parkId={p_id}&tag=2'>{p_name} 查看专线>></a></h4>" + 
	"<hr />" + 
	"<p style='margin:0;line-height:1.5;font-size:14px; color: #666;'>{p_fulladdress}</p>" + 
	"</div>";
	var obj= {};
	var labstyle={
			 color : "#666",
			 fontSize : "12px",
			 height : "23px",
			 width: "auto",
			 lineHeight : "23px",
			 text: "center",
			 fontFamily:"微软雅黑",
			 border: "1px solid #75c4fc ",
			 radius: " 3px",
			 
	};
	var map = new BMap.Map("allmap");            
	map.centerAndZoom("广州",12);  
	map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
	
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
	map.addControl(top_right_navigation);  
		
	var parks= ${parkListJson};
	var infoopts = {
			  width : 400,     // 信息窗口宽度
			  height: 112,  // 信息窗口高度
			  offset: new BMap.Size(-5, -10)
	};
	
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			//this.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,infoopts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
	function addLiClickHandler(element,p_id){
		element.addEventListener("click",function(e){
			openInfoById(p_id)}
		);
	}
	function openInfoById(p_id){
		var infoWindow = new BMap.InfoWindow(obj[p_id].content,infoopts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,obj[p_id].point); //开启信息窗口
	}
	
	for(var p in parks) {
	   // console.log(p, parks[p]['attrs']['location']);
	   var p_id =parks[p]['attrs']['id'];
	   var p_name=parks[p]['attrs']['park_name'];
	   var tmp_location=parks[p]['attrs']['location'].split(",");
	   var p_fulladdress = tmp_location[0];
	   var p_location_y = tmp_location[1];
	   var p_location_x = tmp_location[2];
	   //console.log(p_name+"-"+p_fulladdress+"-"+p_location_x+"-"+p_location_y);
	   var content = infoContent.replace(/{p_id}/, p_id).replace(/{p_name}/, p_name).replace(/{p_fulladdress}/, p_fulladdress);
	   var point= new BMap.Point(p_location_y, p_location_x);
	   var marker = new BMap.Marker(point);  // 创建标注
	   map.addOverlay(marker);               // 将标注添加到地图中

	   var labelopts = {
		  position : point,    // 指定文本标注所在的地理位置
		  offset   : new BMap.Size(-40, -55)    //设置文本偏移量
	   }
	   var label = new BMap.Label(p_name, labelopts);  // 创建文本标注对象
	   label.setStyle(labstyle);
	   map.addOverlay(label);   
		
	   addClickHandler(content,marker);
	   var newobj= {};
	   newobj.content=content;
	   newobj.point= point;
	   obj[p_id]=newobj;	   
	}
	var elements = document.querySelectorAll("ul.parklist li");
	for(var i=0;i<elements.length;i++){
		var p_id=elements[i].getAttribute('data-id');
		addLiClickHandler(elements[i],p_id);
	}	
</script>
