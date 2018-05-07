<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>添加弧线</title>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hwdLiBYBfNbLOUfOy5Pfm5s8zKQK0inN"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>
	<style type="text/css">
		html,body{
			width:100%;
			height:100%;
			margin:0;
			overflow:hidden;
			font-family:"微软雅黑";
		}
	</style>
</head>
<body>
	<div style="width:100%;height:100%;border:1px solid gray" id="container"></div>
</body>
</html>
<script type="text/javascript">
	

	
	
	// 百度地图API功能
	var map = new BMap.Map("container");
	map.centerAndZoom(new BMap.Point(112.435052,34.667093), 7);
	map.enableScrollWheelZoom();
	var startPosition=new BMap.Point(104.616201,31.492595),
		currentPosition=new BMap.Point(112.435052,34.667093),
		endPosition=new BMap.Point(117.14476,36.67855);	

	  
	var endMarker = new BMap.Marker(endPosition);  // 创建标注
	map.addOverlay(endMarker);              // 将标注添加到地图中
	
	var startMarker = new BMap.Marker(startPosition);  // 创建标注
	map.addOverlay(startMarker);              // 将标注添加到地图中
	
	//var myIcon = new BMap.Icon("${ctx}/static/pc/map/huoche.gif", new BMap.Size(200,119));
	//var currentMarker = new BMap.Marker(currentPosition,{icon:myIcon});  // 创建标注
	var currentMarker = new BMap.Marker(currentPosition);
	currentMarker.setAnimation(BMAP_ANIMATION_BOUNCE);
	map.addOverlay(currentMarker);              // 将标注添加到地图中
	
	var endOpts = {
	  width : 200,     // 信息窗口宽度
	  height: 70,     // 信息窗口高度
	}
	var endInfoWindow = new BMap.InfoWindow("收货地址：山东济南历城区盖家沟农贸场4号库d厅3号7库", endOpts);  // 创建信息窗口对象 
	endMarker.addEventListener("click", function(){          
		map.openInfoWindow(endInfoWindow,endPosition); //开启信息窗口
	});
	
	var startOpts = {
	  width : 200,     // 信息窗口宽度
	  height: 70,     // 信息窗口高度
	}
	var startInfoWindow = new BMap.InfoWindow("发货地址：四川省绵阳市安县花荄8号路口", startOpts);  // 创建信息窗口对象 
	startMarker.addEventListener("click", function(){          
		map.openInfoWindow(startInfoWindow,startPosition); //开启信息窗口
	});
	
	
	var polyline = new BMap.Polyline([
		startPosition,
		new BMap.Point(105.869876,32.472895),
		new BMap.Point(107.0657,33.110034),
		new BMap.Point(108.942225,34.385632),
		currentPosition
	], {strokeColor:"green", strokeWeight:4, strokeOpacity:0.5});   //创建折线
	map.addOverlay(polyline);   //增加折线
	
	map.openInfoWindow(endInfoWindow,endPosition);
</script>