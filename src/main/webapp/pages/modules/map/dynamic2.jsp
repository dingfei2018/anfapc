<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="keywords" content="高德地图,DIY地图,高德地图生成器">
	<meta name="description" content="高德地图，DIY地图，自己制作地图，生成自己的高德地图">
	<title>高德地图 - DIY我的地图</title>
	<style>
		body { margin: 0; font: 13px/1.5 "Microsoft YaHei", "Helvetica Neue", "Sans-Serif"; min-height: 960px; min-width: 600px; }
		.my-map { margin: 0 auto; width: 800px; height: 400px; }
		.my-map .icon { background: url(http://lbs.amap.com/console/public/show/marker.png) no-repeat; }
		.my-map .icon-cir { height: 31px; width: 28px; }
		.my-map .icon-cir-green { background-position: -11px -155px; }
		.amap-container{height: 100%;}
	</style>
</head>
<body>
	<div id="wrap" class="my-map">
		<div id="mapContainer"></div>
	</div>
	<script src="http://webapi.amap.com/maps?v=1.3&key=8325164e247e15eea68b59e89200988b"></script>
	<script>
	!function(){
		var infoWindow, map, level = 18,f=true,
			center = {lng: 113.332836, lat: 23.132629},
			features = [{type: "Marker", name: "广州塔塔有限公司", desc: "广东省广州市白云区大兴区安发物流园36-37号", color: "green", icon: "cir", offset: {x: "0", y: "0"}, lnglat: {lng: "113.332847", lat: "23.132649"}}];

		function loadFeatures(){
			for(var feature, data, i = 0, len = features.length, j, jl, path; i < len; i++){
				data = features[i];
				switch(data.type){
					case "Marker":
						feature = new AMap.Marker({ map: map, position: new AMap.LngLat(data.lnglat.lng, data.lnglat.lat),
							zIndex: 3, extData: data, offset: new AMap.Pixel(data.offset.x, data.offset.y), title: data.name,
							content: '<div class="icon icon-' + data.icon + ' icon-'+ data.icon +'-' + data.color +'"></div>' });
						break;
					case "Polyline":
						for(j = 0, jl = data.lnglat.length, path = []; j < jl; j++){
							path.push(new AMap.LngLat(data.lnglat[j].lng, data.lnglat[j].lat));
						}
						feature = new AMap.Polyline({ map: map, path: path, extData: data, zIndex: 2,
							strokeWeight: data.strokeWeight, strokeColor: data.strokeColor, strokeOpacity: data.strokeOpacity });
						break;
					case "Polygon":
						for(j = 0, jl = data.lnglat.length, path = []; j < jl; j++){
							path.push(new AMap.LngLat(data.lnglat[j].lng, data.lnglat[j].lat));
						}
						feature = new AMap.Polygon({ map: map, path: path, extData: data, zIndex: 1,
							strokeWeight: data.strokeWeight, strokeColor: data.strokeColor, strokeOpacity: data.strokeOpacity,
							fillColor: data.fillColor, fillOpacity: data.fillOpacity });
						break;
					default: feature = null;
				}
				if(feature){ 
					
					if(f == true){
						
						var mycars = new Array();
						mycars[0]=data.lnglat.lng;
						mycars[1]=data.lnglat.lat;
						 mapFeatureClicks(data.name,data.name,mycars);
						 f = false;
					}
					  
					  AMap.event.addListener(feature, "click", mapFeatureClick);
				}
			}
		}
		 
	
		 
		 function mapFeatureClicks(name,miaoshu,add){
			if(!infoWindow){ infoWindow = new AMap.InfoWindow({autoMove: true}); }
			//var extData = e.target.getExtData();
			infoWindow.setContent("<h5>" + name + "</h5><div>" + miaoshu + "</div>");
			//113.332903,23.132619
			infoWindow.open(map,add);
		} 
		
		function mapFeatureClick(e){
			if(!infoWindow){ infoWindow = new AMap.InfoWindow({autoMove: true}); }
			var extData = e.target.getExtData();
			infoWindow.setContent("<h5>" + extData.name + "</h5><div>" + extData.desc + "</div>");
			//alert(e.lnglat);
			infoWindow.open(map, e.lnglat);
		}
		map = new AMap.Map("mapContainer", {center: new AMap.LngLat(center.lng, center.lat), level: level});
		new AMap.TileLayer.RoadNet({map: map, zIndex: 2});
		loadFeatures();
        
        
		map.on('complete', function(){
			map.plugin(["AMap.ToolBar", "AMap.OverView", "AMap.Scale"], function(){
				map.addControl(new AMap.ToolBar);
			map.addControl(new AMap.OverView({isOpen: true}));
			map.addControl(new AMap.Scale);
			});	
		})
		
	}();
	</script>
	<div style="width:100%;clear:both"></div>
	<div style="width:100%;    text-align: center;margin:20px auto">
	   <iframe width='800px' height='500px' frameborder='0' scrolling='no' marginheight='0' marginwidth='0' src='https://yuntu.amap.com/share/AzEbue'></iframe>
	</div>
	<div style="width:100%;    text-align: center;margin:20px auto">
	   <iframe width='1024px' height='600px' frameborder='0' scrolling='no' marginheight='0' marginwidth='0' src='https://yuntu.amap.com/share/2i2q2a'></iframe>
	</div>
</body>
</html>

