<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>运营概况</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/operations.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/js/ichart.1.2.1.min.js"></script>
		<script src="${ctx}/static/kd/js/echarts.common.min.js"/></script>
		<script src="${ctx}/static/common/js/ichart.1.2.1.min.js"></script>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
	    <%@ include file="common/head2.jsp" %>
		<%@ include file="common/head.jsp" %>
		<div class="banner">
		 <%@ include file="./common/fahuoleft.jsp" %>
			 <script type="text/javascript">
			     $(function(){
					  var _width=$("body").width();
					  var _widths = $(".banner-left").width();
					  var _widthd = _width - _widths - 80;
					  parseInt(_widthd)
					  $('.banner-right').css('width',_widthd+'px');
			     });
			     $(window).resize(function(){ 
			    	  var Width = $(window).width();
		    	      var _widths = $(".banner-left").width();
			  		  var _widthd = Width - _widths - 80;
			  		  parseInt(_widthd)
			  		  $('.banner-right').css('width',_widthd+'px');
			    	});
	          </script>
			<div class="banner-list">
				 
				<div class="banner-right-list">
					<span>网点：</span>
					 <select name="networkId" id="netWorkId">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}">${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
					<span>搜索日期：</span><input type="text" id="startTime" class="Wdate" value="${firstDay }"  onclick="WdatePicker({isShowClear:false,readOnly:true})" />
					至 <input type="text" class="Wdate" id="endTime" value="${lastDay }" onclick="WdatePicker({isShowClear:false,readOnly:true})" />
					<button onclick="search();">搜索</button>
				</div>
				<div class="banner-right-list2">
					<p>数据总览</p>
					<div class="banner-right-list2-left">
						<div class="banner-right-list2-left-top">
							<span>运单单量</span><br />
							<b id="ship_count"></b>
						</div>
						<div class="banner-right-list2-left-bottom">
							<span>运单货量</span><br />
							<b id="ship_volume"></b><br />
							<b id="ship_weight"></b>
						</div>
					</div>
					
					<div class="banner-right-list2-left">
						<div class="banner-right-list2-left-top">
							<span>到货单量</span><br />
							<b id="reShip_reCount"></b>
						</div>
						<div class="banner-right-list2-left-bottom">
							<span>到货货量</span><br />
							<b id="reShip_reVolume"></b><br />
							<b id="reShip_reWeight"></b>
						</div>
					</div>
					
					<div class="banner-right-list2-left">
						<div class="banner-right-list2-left-bottoms">
							<span>装车配载</span><br />
							<b id="loadTruck_count"></b><br />
							<b id="loadShip_count"></b>
						</div>
					</div>
					
					<div class="banner-right-list2-left">
						<div class="banner-right-list2-left-bottoms">
							<span>已转运单</span><br />
							<b id="transfer_count"></b><br />
						</div>
					</div>
				</div>
					<div style="width: 1200px; height: 400px; border: 1px solid #fff; margin: auto;">
					<div style="width: 540px; height: 400px; border: 1px solid #fff; margin-left: 20px; float: left;" id="canvasDivs">
					</div>
					<div  style="width: 540px; height: 400px; border: 1px solid #fff;  margin-left: 603px" id="canvasDivc">
					</div>
				</div>
								
				<div style="width: 1500px; height: 400px; border: 1px solid #fff; margin: auto; margin-right: 45px; margin-top: 30px;" id="countShip">
				</div>
			</div>
		</div>
		<%@ include file="common/loginfoot.jsp" %>
	<script type="text/javascript">
	init();
	function init (){
		var index = layer.load(1, {
			content: '数据加载中',
			shade: [0.4, '#393D49'],
			time: 10 * 1000,
			success: function(layero) {
			layero.css('padding-left', '30px');
			layero.find('.layui-layer-content').css({
			'padding-top': '40px',
			'width': '70px',
			'background-position-x': '16px'
			});
			}
			});
		var month =new Array();
		var countShip=[];
		var max=0;
		
		var countShipCity=[];
		var stock=[];
		
		var netWorkId=$('#netWorkId').val();
		var startTime=$('#startTime').val();
		var endTime=$('#endTime').val();
		
		$.ajax({
			type:'post',
			url:'${ctx}/kd/getData',
			async: false,
			dataType : "json",
			data:{"netWorkId":netWorkId,"startTime":startTime,"endTime":endTime},
			success:function(data){
				stock=data.stock;
				layer.close(index);
				$('#ship_count').html(data.data.ship.count+"单");
				$('#ship_volume').html(data.data.ship.volume+"立方");
				$('#ship_weight').html(data.data.ship.weight+"公斤");
				$('#reShip_reCount').html(data.data.reShip.reCount+"单");
				$('#reShip_reVolume').html(data.data.reShip.reVolume+"立方");
				$('#reShip_reWeight').html(data.data.reShip.reWeight+"公斤");
				$('#loadTruck_count').html(data.data.loadTruck.count+"车次");
				$('#loadShip_count').html(data.data.loadShip.count+"单");
				$('#transfer_count').html(data.data.transfer.count+"单");
				
				console.log(data);
				for(var i=0;i<data.countShip.length;i++){
				var row=data.countShip[i];
				month.push(row.time.substr(row.time.length-2));	
				countShip.push(row.countShip);
				}
				max=Math.max.apply(null, countShip);//最大值
				
				for(var i=0;i<data.countShipCity.dataList.length;i++){
					var row = data.countShipCity.dataList[i];
					var total=data.countShipCity.total;
					var temp={};
					if(i==0)  temp={'name':row.cityName,'value':row.countShip/total,'color':'#9d4a4a'};
					if(i==1)  temp={'name':row.cityName,'value':row.countShip/total,'color':'#5d7f97'};
					if(i==2)  temp={'name':row.cityName,'value':row.countShip/total,'color':'#97b3bc'};
					if(i==3)  temp={'name':row.cityName,'value':row.countShip/total,'color':'#a5aaaa'};
					if(i==4)  temp={'name':row.cityName,'value':row.countShip/total,'color':'#778088'};
					if(i==5)  temp={'name':row.cityName,'value':row.countShip/total,'color':'#6f83a5'};
					countShipCity.push(temp);
				}
			}
		});
		
		var stockChart = echarts.init(document.getElementById('canvasDivc'));
		
      // 基于准备好的dom，初始化echarts实例
      // 指定图表的配置项和数据
      var stockOption = {
    		title : {
    			text:'库存概括图'
    		},
    	  tooltip : {
    		  trigger: 'axis',
    		  axisPointer : { // 坐标轴指示器，坐标轴触发有效
    		  type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
    		  }
    		  }
    		  ,
    		  grid: {
    			  width:80,
    		  containLabel: false
    		  },
    		  xAxis: {
			  name:'库存总单量：'+stock.all.countShip+'单',   
			  nameTextStyle:{fontSize:20,padding: [20, 0, -10, 0]},
    		  type: 'category',
    		  boundaryGap:true,
    		  splitLine:{show: false},
    		  data: []
    		  },
    		  yAxis: {
 				  show:false,   			  
    			  name:'单量',       			  
    			  splitLine:{show: false},
    		  type: 'value',
    		  },
    		  series: [
    		  {
    		  name: '库存中',
    		  type: 'bar',
    		  stack: '总量',
    		  barWidth:'80',
    		  markPoint:{
    			  label:{
    				  normal:{
    		    		  show: true,
    		    		  position: 'right',
    		    		  fontSize:20,
    		    		  formatter:function(params){
    		    			  var a='已出库'+stock.out.countShip+'单 :'+stock.out.shipAmount+'立方  '+stock.out.shipWeight+'公斤'
    		    			  return a;
    		    		  }
    			  }
    			  }
    		  }
    			  ,
    		  label: {
    		  normal: {
    		  show: true,
    		  position: 'right',
    		  fontSize:20,
    		  formatter:function(params){
    			  var a='';
    			  if(stock.in.countShip!=0){
    			    a='库存中'+stock.in.countShip+'单 :'+stock.in.shipAmount+'立方  '+stock.in.shipWeight+'公斤' 
    			  }
    			  return a;
    		  }
    		  }
    		  },
    		  data: [stock.in.countShip]
    		  },
    		  {
    		  name: '已出库',
    		  type: 'bar',
    		  stack: '总量',
    		  label: {
    		  normal: {
    		  show: true,
    		  position: 'right',
    		  fontSize:20,
    		  formatter:function(params){
    			  var a='';
    			  if(stock.out.countShip!=0){
    			   a='已出库'+stock.out.countShip+'单 :'+stock.out.shipAmount+'立方  '+stock.out.shipWeight+'公斤'
    			  }
    			  return a;
    		  }
    		  }
    		  },
    		  data: [stock.out.countShip]
    		  }
    		  ]
    		  };
		
		
		stockChart.setOption(stockOption);
		
		
		var data = countShipCity;
		
		if(data.length==0){
			data=[{'name':'暂无运单','color':'#6f83a5'}];
		}
		
		
		new iChart.Pie2D({
			render : 'canvasDivs',
			data: data,
			title : '运单到达地分布图',
			legend : {
				enable : true
			},
			showpercent:true,
			decimalsnum:2,
			width : 540,
			height : 400,
			radius:140
		}).draw();
		
		
		// 基于准备好的dom，初始化echarts实例
	    var myChart = echarts.init(document.getElementById('countShip'));

	    // 指定图表的配置项和数据
	    var option = {
	    	    title: {
	    	        text: '近一个月单量变化图'
	    	    },
	    	    legend: {
	    	        data:['单量']
	    	    },
	    	    toolbox: {
	    	        feature: {
	    	            saveAsImage: {}
	    	        }
	    	    },
	    	    tooltip:{show :true
	    	    },
	    	    xAxis: {
	    	        type: 'category',
	    	        name:'日期',
	    	        boundaryGap: false,
	    	        data:month
	    	    },
	    	    yAxis: {
	    	        type: 'value',
	    	        name:'单量',
	    	        max:max
	    	    },
	    	    series: [
	    	        {
	    	            name:'单量',
	    	            type:'line',
	    	            smooth:true,
	    	            stack: '总量',
	    	            data:countShip
	    	        }
	    	    ]
	    	};

	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	}
	
		function search(){
			var netWorkId=$('#netWorkId').val();
			var startTime=$('#startTime').val();
			var endTime=$('#endTime').val();
			
			if(startTime.length==0){
				Anfa.show("请选择开始日期","#startTime");
				return;
			}
			if(endTime.length==0){
				Anfa.show("请选择结束日期","#endTime");
				return;
			}
			init();
			//window.location.href='${ctx}/kd?type=search'+'&netWorkId='+netWorkId+'&startTime='+startTime+'&endTime='+endTime;
		}
	</script>
	</body>
</html>
