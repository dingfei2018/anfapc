<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>快递单查询</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<%-- <link rel="stylesheet" href="${ctx}/static/pc/shipped/inquiry.css?v=${version}" /> --%>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/expresst.css?v=${version}" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/js/dateFormat.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
		<script src="http://webapi.amap.com/maps?v=1.4.0&key=8325164e247e15eea68b59e89200988b"></script>
		<script src="//webapi.amap.com/ui/1.0/main.js"></script>
	</head>

	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<div class="content">
		  <p class="p">当前位置：单号查询</p>
		  <div class="content-top">
		        <div class="content-top2">
	               <ul>
		             <li><a class="active" href="${ctx}/express">货物运输单号查询</a></li>
		             <li><a href="${ctx}/express/kd">快递单号查询</a></li>
		           </ul>
		            <div class="yd_lb" id="yd_lbs"> <label class="label1">
		                  <input type="text" id="expNoId" placeholder="请输入货物运输单号"> 
		            </label></div>
		              <input class="button"  id="btnTest" type="button"  onclick="loadExpress()"  value="立即搜索"> 
		              <a href="#mapContainer"></a>
		        </div>
		        <div class="content-botton">
		             <div class="content-botton-left"></div>
		             <div class="content-botton-right" id="mapContainer"></div>
		        </div>
		  </div>
        </div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		function loadExpress(){
			var expNo = $("#expNoId").val();
			if(expNo==null||expNo==""){
				Anfa.show("请输入货物运输单号","#expNoId");
				return;
			}
			$(".content-botton").mLoading("show");
			$.ajax({
				type : "post",
				url : "${ctx}/express/hydcx",
				data:{expNo:expNo},		
				success : function(data) {
					if(data.success){
						 appendHtml(data.data.list);
						 loadMap(data.data);
					}else{
						$(".content-botton-right").empty();
						$(".content-botton-left").append("<ul><li style=\"text-align: center; height: 150px;line-height: 200px;\">没有查到快递信息，请确认运单号和快递类型是否正确</li></ul>");
					}
					$(".content-botton").mLoading("hide");
					$(".content-botton").show();
					location.href = "#mapContainer"; 
				}
			});
		}
		
		function appendHtml(obj){
			var html ="<ul>";
			var tm = "";
			for(var i=(obj.length-1);i>=0;i--){  
				 var tra = obj[i];
			     tm ="<li><label>"
			     +"<span class=\"span1\">"+new Date(tra.time).format("hh:mm")+" <br> "+new Date(tra.time).format("yyyy-MM-dd")+"</span>";
			     if(i==(obj.length-1)){
			    	 if(tra.status==0){
			    		 tm+="<span class=\"span2 active\"><b class=\"actives\"></b>【出发地】"+tra.address+"</span>"
			    	 }else if(tra.status==1){
			    		 tm+="<span class=\"span2 active\"><b class=\"actives\"></b>【运输中】"+tra.address+"</span>"
			    	 }else if(tra.status==2){
			    		 tm+="<span class=\"span2 active\"><b class=\"actives\"></b>【到达地】"+tra.address+"</span>"
			    	 }
			     }else{
			    	 if(tra.status==0){
			    		 tm+="<span class=\"span2 active\"><b></b>【出发地】"+tra.address+"</span>"
			    	 }else if(tra.status==1){
			    		 tm+="<span class=\"span2 active\"><b></b>【运输中】"+tra.address+"</span>"
			    	 }
			     }
			     tm+="</label> </li>";
			     html+=tm;
		    }  
			html += "</ul>";
			$(".content-botton-left").empty();
			$(".content-botton-left").append(html);
		}
		
		function loadMap(obj){
			var data = eval(obj.locations);
			var map = new AMap.Map('mapContainer', {
				resizeEnable: true,
				center:[data[2][0], data[2][1]],
				isHotspot: true,
				level: 10
			});
			//map.setCenter(new AMap.LngLat(data[2][0], data[2][1]));
		 	AMapUI.load(['ui/misc/PathSimplifier'], function(PathSimplifier) {
			    if (!PathSimplifier.supportCanvas) {
			        alert('当前环境不支持 Canvas！');
			        return;
			    }
			    //启动页面
			    initPage(PathSimplifier, obj,data, map);
			    map.setFitView();
			});
		}
		
		function initPage(PathSimplifier, obj,data, map) {
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
		    pathSimplifierIns.setData([{name: obj.lineDesc,path: data}]);
		    addMarker(data[0]);
		    //addMarker(data[data.length-1]);
		
		    //创建一个巡航器
		    var navg = pathSimplifierIns.createPathNavigator(0, //关联第1条轨迹
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
			    setTimeout(function(){navg.start();}, 1000);
			}
		
			// 实例化点标记
			function addMarker(pos, map) {
				var marker = new AMap.Marker({
			           icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
			           position: pos
			       });
			   marker.setMap(map);
			}
			
			function computeDistance(start, end){
				var lnglat = new AMap.LngLat(start[0], start[1]);
				return lnglat.distance(end);
			}
		
		</script>		
</body>
</html>
