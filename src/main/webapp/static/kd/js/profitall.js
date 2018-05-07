//签收
(function($){
function ProfitAll(){
	this.searchParam = "";
	this.data = new Array();
	this.year = new Array();
	this.max = 0;
	this.init();
}
ProfitAll.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			$(".banner-right-a3").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));
			this.data.push(eval({name : '营业额',value:[],color:'#de9972'}));
			this.data.push(eval({name : '成本',value:[],color:'#28847f'}));
			this.data.push(eval({name : '毛利',value:[],color:'#90abc0'}));
			this.search();
		},
		search:function(){
			if($("#startTime").val()==""){
				Anfa.show("请填写起始月份","#startTime");
				return;
			}
			if($("#endTime").val()==""){
				Anfa.show("请填写终止月份","#endTime");
				return;
			}
			var param = $("#searchFrom").serialize();
			if(param)this.searchParam = param;
			this.data = new Array();
			this.year = new Array();
			this.max = 0;
			this.data.push(eval({name : '营业额',value:[],color:'#de9972'}));
			this.data.push(eval({name : '成本',value:[],color:'#28847f'}));
			this.data.push(eval({name : '毛利',value:[],color:'#90abc0'}));
			this.getData(param, this);
		},
		getData:function(param, obj){
			$("#loadId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/report/preProfitSearch',
				data:encodeURI(param),
				success:function(data){
					var html="";
					for(var i=0;i<data.length;i++){  
						html += obj.appendHtml(data[i], obj)
				    } 
					$("#loadId tr:gt(1)").remove();
					$("#loadId").append(html);
					$("#loadId").mLoading("hide");
					obj.year.reverse();
					for(var i=0;i<obj.data.length;i++){
						var name = obj.data[i].name;
						var t = obj.data[i].value;
						t.reverse();
					}
					obj.drawing(obj);
				}
			});
		},
		appendHtml:function(obj, $obj){
			var rate = obj.preProfitRate*100;
			rate = rate.toFixed(2);
			var allfee = obj.allfee;
			allfee = allfee.toFixed(2);
			var preProfit = obj.preProfit;
			preProfit = preProfit.toFixed(2);
			var html = "<tr>";
			html += "<td>"+obj.time+"</td>";
			html += "<td>"+obj.shipCount+"</td>";
			html += "<td style='color: #ff7801;'>"+obj.totalfee+"</td>";
			html += "<td>"+obj.tihuofee+"</td>";
			html += "<td>"+obj.duanbofee+"</td>";
			html += "<td>"+obj.ganxianfee+"</td>";
			html += "<td>"+obj.songhuofee+"</td>";
			html += "<td>"+obj.zhongzhuanfee+"</td>";
			html += "<td style='color: #ff7801;'>"+allfee+"</td>";
			html += "<td style='color: #ff7801;'>"+preProfit+"</td>";
			html += "<td style='color: #ff7801;'>"+rate+"%</td>";
			html += "</tr>";
			if(obj.time!="合计"){
				$obj.year.push(obj.time); 
				for(var i=0;i<$obj.data.length;i++){
					var name = $obj.data[i].name;
					var t = $obj.data[i].value;
					if(name=="营业额"){
						t.push(obj.totalfee);
					}else if(name=="成本"){
						t.push(obj.allfee);
					}else if(name=="毛利"){
						t.push(obj.preProfit);
					}
				}
				if(this.max<obj.totalfee)this.max = obj.totalfee; 
			}
			return html;
		},
		drawing:function(obj){
			var scale = parseInt((obj.max>0?obj.max:25)/5);
			var chart = new iChart.ColumnMulti3D({
				render : 'canvasDiv',
				data: obj.data,
				labels:obj.year,
				title : {
					text : '毛利汇总表',
					color : '#3e576f'
				},
				footnote : {
					text : '',
					color : '#909090',
					fontsize : 11,
					padding : '0 44'
				},
				width : 850,
				height : 400,
				background_color : '#ffffff',
				legend:{
					enable:true,
					background_color : null,
					align : 'center',
					valign : 'bottom',
					row:1,
					column:'max',
					border : {
						enable : false
					}
				},
				column_width : 8,//柱形宽度
				zScale:8,//z轴深度倍数
				xAngle : 50,
				bottom_scale:1.1,
				label:{
					color:'#4c4f48'
				},
				sub_option:{
					label :false
				},
				tip:{
					enable :true
				},
				text_space : 16,//坐标系下方的label距离坐标系的距离。
				coordinate:{
					background_color : '#d7d7d5',
					grid_color : '#a4a4a2',
					color_factor : 0.24,
					board_deep:10,
					offsety:-10,
					pedestal_height:10,
					left_board:false,//取消左侧面板
					width:750,
					height:240,
					scale:[{
						 position:'left',	
						 start_scale:0,
						 end_scale:obj.max>0?obj.max:1,
						 scale_space:scale,
						 scale_enable : false,
						 label:{
							color:'#4c4f48'
						 }
					}]
				}
			});

			//利用自定义组件构造左侧说明文本
			chart.plugin(new iChart.Custom({
					drawFn:function(){
						//计算位置
						var coo = chart.getCoordinate(),
							x = coo.get('originx'),
							y = coo.get('originy');
						//在左上侧的位置，渲染一个单位的文字
						chart.target.textAlign('start')
						.textBaseline('bottom')
						.textFont('600 11px Verdana')
						.fillText('费用（元）',x-40,y-28,false,'#6d869f');
					}
			}));
			
			chart.draw();
		},
		excelExport:function(){
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/report/exportPreProfit?"+this.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new ProfitAll();
})
})(jQuery);	
	
	


