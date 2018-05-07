<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="${ctx }/static/pc/Common/css/details.css?v=${version}" />
	<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
	<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
	<script src="${ctx }/static/pc/study/js/jquery.js"></script>
	<script src="${ctx }/static/pc/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
	<script src="http://webapi.amap.com/maps?v=1.4.0&key=8325164e247e15eea68b59e89200988b"></script>
	<script src="//webapi.amap.com/ui/1.0/main.js"></script>
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
var data = [[113.332135,33.681472],[114.27696,30.650292],[115.067975,27.091503]]
var map = new AMap.Map('mapContainer', {
	center: new AMap.LngLat(data[2][0], data[2][1]),
	level: 18
});

AMapUI.load(['ui/misc/PathSimplifier'], function(PathSimplifier) {
    if (!PathSimplifier.supportCanvas) {
        alert('当前环境不支持 Canvas！');
        return;
    }
    //启动页面
    initPage(PathSimplifier);
});

function initPage(PathSimplifier) {
    //创建组件实例
    var pathSimplifierIns = new PathSimplifier({
        zIndex: 100,
        map: map, //所属的地图实例
        getPath: function(pathData, pathIndex) {
            //返回轨迹数据中的节点坐标信息，[AMap.LngLat, AMap.LngLat...] 或者 [[lng|number,lat|number],...]
            return pathData.path;
        },
        getHoverTitle: function(pathData, pathIndex, pointIndex) {
            //鼠标悬停在节点之间的连线上
            return pathData.name;
        },
        renderOptions: {
            //轨迹线的样式
            pathLineStyle: {
                strokeStyle: 'red',
                lineWidth: 6,
                dirArrowStyle: true
            }
        }
    });
    //这里构建两条简单的轨迹，仅作示例
    pathSimplifierIns.setData([{name: '平顶山 -> 深圳',path: data}]);
    addMarker(data[0]);
    //addMarker(data[data.length-1]);

    //创建一个巡航器
    var navg0 = pathSimplifierIns.createPathNavigator(0, //关联第1条轨迹
        {
            loop: false, //循环播放
            /* pathNavigatorStyle: {
                width: 16,
                height: 32,
                content: PathSimplifier.Render.Canvas.getImageContent('http://webapi.amap.com/ui/1.0/ui/misc/PathSimplifier/examples/imgs/car.png', onload, onerror),
                strokeStyle: null,
                fillStyle: null
            }, */
            speed: 1000000
        });

    navg0.start();
}

// 实例化点标记
function addMarker(pos) {
	var marker = new AMap.Marker({
           icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
           position: pos
       });
   marker.setMap(map);
}
</script>
</body>
</html>

