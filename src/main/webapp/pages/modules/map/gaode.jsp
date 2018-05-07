<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="keywords">
	<title></title>
	
	</head>
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
		<div id="tip"></div>
	</div>
	
	<script src="http://webapi.amap.com/maps?v=1.4.0&key=8325164e247e15eea68b59e89200988b&plugin=AMap.CitySearch"></script>
	 <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
	<script type="text/javascript">
		AMap.service('AMap.Geocoder',function(){//回调函数
		    //实例化Geocoder
		    geocoder = new AMap.Geocoder({
		        city: "010"//城市，默认：“全国”
		    });
		
		    geocoder.getLocation("广州市番禺区府前路", function(status, result) {
		        if (status === 'complete' && result.info === 'OK') {
		        	var locate = result.geocodes[0].location
		        	
		        	var map = new AMap.Map("mapContainer", {center: new AMap.LngLat(locate.lng, locate.lat), level: 18});
		            
				    var marker = new AMap.Marker({
			            map:map,
			            bubble:true
			        })
		    		map.on('complete', function(){
		    			map.plugin(["AMap.ToolBar", "AMap.OverView", "AMap.Scale"], function(){
			    			map.addControl(new AMap.ToolBar);
			    			map.addControl(new AMap.OverView({isOpen: true}));
			    			map.addControl(new AMap.Scale);
		    			});	
		    		})
		    		var infoWindow;
		    		if(!infoWindow){ 
		    			infoWindow = new AMap.InfoWindow({autoMove: true}); 
		    		}
					infoWindow.setContent("<h5>广州塔塔有限公司</h5><div>广州塔塔有限公司</div>");
					infoWindow.open(map,locate);
		        }else{
		            alert("地图获取失败，请稍后再试");
		        }
		    }); 
		    
		})
		
        //实例化城市查询类
        var citysearch = new AMap.CitySearch();
        //自动获取用户IP，返回当前城市
        citysearch.getLocalCity(function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
                if (result && result.city && result.bounds) {
                    var cityinfo = result.city;
                    var citybounds = result.bounds;
                    console.log(result);
                    document.getElementById('tip').innerHTML = '您当前所在城市：'+cityinfo;
                }
            } else {
                document.getElementById('tip').innerHTML = result.info;
            }
        });
		
	</script>
</body>
</html>

