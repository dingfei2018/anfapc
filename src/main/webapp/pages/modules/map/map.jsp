<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>查看位置</title>
	<link rel="stylesheet" href="${ctx }/static/pc/Common/css/details.css?v=${version}" />
	<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
	<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
	<script src="${ctx }/static/pc/study/js/jquery.js"></script>
	<script src="${ctx }/static/pc/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
	<script src="http://webapi.amap.com/maps?v=1.4.0&key=8325164e247e15eea68b59e89200988b"></script>
	<script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
	<style>
		body { margin: 0; font: 13px/1.5 "Microsoft YaHei", "Helvetica Neue", "Sans-Serif"; min-height: 960px; min-width: 600px; }
		.my-map { margin: 0 auto; width: 1200px; height: 652px; }
		.my-map .icon { background: url(http://lbs.amap.com/console/public/show/marker.png) no-repeat; }
		.my-map .icon-cir { height: 31px; width: 28px; }
		.my-map .icon-cir-green { background-position: -11px -155px; }
		.amap-container{height: 100%;}
	</style>
</head>
<body>
	<!---头部的内容--->
	<%@ include file="../../front/common/loginhead.jsp"%>
	<div class="banner">
		<div class="banner-table">
			<div id="wrap" class="my-map">
				<div id="mapContainer"></div>
			</div>
             
		</div>
		<!-- <a class="a" href="javascript:void();" onclick="back()">返回上一层</a> -->
	</div>
	<!---底部的内容-->
	<%@ include file="../../front/common/loginfoot.jsp"%>
<script type="text/javascript">

AMap.service('AMap.Geocoder',function(){//回调函数
    //实例化Geocoder
    geocoder = new AMap.Geocoder({
       /*  city: "010"//城市，默认：“全国” */
    });

    geocoder.getLocation("${address}", function(status, result) {
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
    			$(".amap-logo").hide();
    		})
    		var infoWindow;
    		if(!infoWindow){ 
    			infoWindow = new AMap.InfoWindow({autoMove: true}); 
    		}
			infoWindow.setContent("<h5>${corname}</h5><div>${address}</div>");
			infoWindow.open(map,locate);
        }else{
            //alert("地图获取失败，请稍后再试");
        }
    }); 
    
})

function back(){
	window.location.href=document.referrer;
}
</script>
</body>
</html>

