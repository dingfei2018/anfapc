<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>应收汇总表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/collectionr.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script type="text/javascript" src="${ctx}/static/common/js/ichart.1.2.1.min.js" ></script>
		<script src="${ctx}/static/kd/js/resummary.js"></script>
	</head>

	<body>
	   <%@ include file="../common/head2.jsp" %>
       <%@ include file="../common/head.jsp" %>

		<div class="banner">
			<%@ include file="../common/financialleft.jsp" %>
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
			    	})
	       </script>
			<div class="banner-right">
				<ul>
					<li>
						<a href="${ctx }/kd/report/reSummary" class="active at">应收汇总表</a>
					</li>
					<li>
						<a href="${ctx }/kd/report/paySummary" class="at">应付汇总表</a>
					</li>
					<li>
						<a href="${ctx}/kd/report/operationList" class="at">运作明细表</a>
					</li>
					<li>
						<a href="${ctx }/kd/report/preProfit" class="at">毛利汇总表</a>
					</li>
				</ul>
				<div class="banner-right-list">
				<form onsubmit="return false;">
				<ul>
					 <li>
					<span class="span">网点：</span>
					<select name="netWorkId" id="netWorkId">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}" <c:if test="${search.reNetWorkId ==work.id}">selected</c:if>>${work.sub_network_name}</option>
                            </c:forEach>
                     </select>
					</li>
					 <li>
					<span class="spanc">月份：</span><input type="text" name="startTime" id="startTime" value="${startTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM'})"/>
					 </li>
					 <li>
					<span class="spanc">至</span><input type="text" name="endTime"  id="endTime" value="${endTime}"   onclick="WdatePicker({dateFmt:'yyyy-MM'})"/>
                    </li>
                    <li>
					<button onclick="search();">查询</button>
					</li>
					<li>
					<input class="buttons" type="reset"  value="重置"/> 
					</li>
					</ul>
					</form>
				</div>
				
				<div style=" float:left; width:99%; height: 400px; border: 1px solid #DCDCDC; margin: auto; margin-top: 40px;">
				
					<div id='canvasDiv'></div>
				</div>

				<div class="banner-right-list2">
					<p class="banner-right-p">应收汇总表</p>
					<div style="overflow: auto; width: 100%;">
						<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="table">
							
						</table>
					</div>
					<ul class="ul2">
						<li>
							<a href="#" onclick="downExcel();" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
<script type="text/javascript">
init();

var datachart = [];
var year = new Array();
var max=0;


function search(){
	
	if($("#startTime").val()==""){
		Anfa.show("请选择开始日期","#startTime");
		return;
	}
	if($("#endTime").val()==""){
		Anfa.show("请选择结束日期","#endTime");
		return;
	}
	if(CompareDate($("#startTime").val(),$("#endTime").val())){
		Anfa.show("开始日期不能大于结束日期","#startTime");
		return;
	}
	
	init();
}

function CompareDate(d1,d2)
{
  return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));
}

function init(){
	
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
	
	
	datachart = [];
	year =new Array();
	$.ajax({
		type:'post',
		url:'${ctx}/kd/report/getReSummaryData',
		dataType : "json",
		data:{"startTime":$('#startTime').val(),"endTime":$('#endTime').val(),"networkId":$('#netWorkId').val()},
		success:function(data){
			layer.close(index);
				var allfee=new Array();
				var netRefee=new Array();
				var content="";
				var th='<thead><th>月份</th><th>单量</th><th>营业额</th><th>网点对账应收</th></thead>';
				for(var i=0;i<data.length;i++){
				var row=data[i];
				if(row.time!='合计'){
					allfee.push(row.allfee);			
					netRefee.push(row.netRefee);
					year.push(row.time);
					}
				
				if(i==data.length-2){
					if(row.netRefee>row.allfee) {
						max=row.netRefee;
					}
					else {
						max=row.allfee;
					}
				}
				
				 content+='<tr><td>'+row.time+'</td>'+
							'<td>'+row.shipCount+'</td>'+
							'<td style="color: #ff7801;">'+row.allfee+'</td>'+
							'<td style="color: #ff7801;">'+row.netRefee+'</td>'+
							'</tr>';
				}
				
				datachart=[
			     	{
			     		name : '营业额',
			     		value:allfee,
			     		color:'#de9972'
			     	},
			     	{
			     		name : '网点对账应收',
			     		value:netRefee,
			     		color:'#28847f'
			     	}
			     ];
				
				content=th+content;
				$('#table').html(content);
				drawing();
		}
	});
}

function drawing(){
	var data = datachart;
	var chart = new iChart.ColumnMulti3D({
	render : 'canvasDiv',
	data: data,
	labels:year,
	title : {
		text : '应收汇总表',
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
			 end_scale:max,
			 scale_space:max/5,
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
		.fillText('金额（元）',x-40,y-28,false,'#6d869f');
		
	}
}));

chart.draw();
}

function downExcel(){
	
	layer.confirm(
			'确定导出应收汇总表Excel吗',
			{
				btn : [ '确认', '返回' ]
			},
			function() {
				window.location.href='${ctx}/kd/report/exportReSummary?startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val()+'&networkId='+$('#netWorkId').val();
	}, function() {
			});
	
}
	
</script>
	</body>
</html>