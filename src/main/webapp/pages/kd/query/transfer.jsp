<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>按中转查询</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/transfert.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
<%@ include file="../common/commonhead.jsp"%>
<script src="${ctx}/static/kd/js/query/query-transfer.js?v=${version}"></script>

</head>
<body>
	<!-- 头部文件 -->

	<%@ include file="../common/head2.jsp"%>
	<%@ include file="../common/head.jsp"%>

	<div class="banner">
		<!-- 左边菜单 -->
		<%@ include file="../common/startleft.jsp"%>
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

				<div class="banner-right-list">
			<form id="searchFrom" onsubmit="return false;">
					<div class="div">
						<span class="span">中转网点：</span> <select name="netWorkId"
							id="netWorkCombo">
							<option value="">请选择</option>
							<c:forEach var="work" items="${networks}">
								<option value="${work.id}"
									<c:if test="${search.netWorkId ==work.id}">selected</c:if>>${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span">中转日期：</span><input style="margin-left: 0px;"
							type="text" class="Wdate" name="startTime" id="startTime"
							value="${search.startTime }"
							onclick="WdatePicker()" />
					</div>
					<div class="div">
						<span class="spanc">至</span><input type="text" class="Wdate"
							value="${search.endTime }" id="endTime" name="endTime"
							onclick="WdatePicker()" /><br>
					</div>
					<div class="div">
						<span class="span">中转方：</span><input id="transferName"
							name="transferName" 
							value="${search.transferName }" type="text" />

					</div>
					<div class="div">
						<span class="span">中转单号：</span><input id="transferSn"
							name="transferSn" value="${search.transferSn }" type="text" />
					</div>

					<div class="div">
					<button id="search">查询</button>
					<input class="buttons" type="reset" value="重置"/>
					</div>
					
					</form>
				</div>
				<div class="banner-right-list2">
					<p class="banner-right-p">按中转查询列表</p>
					<div style="overflow: auto; width: 100%;" id="loadingId">
						<table border="0" class="tab_css_1" id="loadId">
							<thead>
								<th><input type="checkbox" style="display: none;" /></th>
								<th>中转单号</th>
								<th>中转方</th>
								<th>中转联系人</th>
								<th>中转联系电话</th>
								<!-- <th>中转费</th> -->
								<th>中转日期</th>
								<th>中转网点</th>
								<th>运单号</th>
								<th>开单网点</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>托运方</th>
								<th class="float4">收货方</th>
								<!-- <th>体积</th>
								<th>重量</th> -->

								<th class="float2">操作</th>

							</thead>
					
						</table>
						<div id="page" style="text-align: center;">
						</div>

					</div>
					<div id="page" style="text-align: center;"></div>
				</div>
		</div>

	</div>
	</form>



	</div>
	<%@ include file="../common/loginfoot.jsp"%>
	<script> 

		//分页
		layui.use(['laypage'], function(){
			var laypage = layui.laypage;
		    //调用分页
		    laypage({
			      cont: 'page'
			      ,pages: '${page.totalPage}' //得到总页数
			      ,curr:'${page.pageNumber}'
			      ,skip: true,
			       count:'${page.totalRow}'
		    	  ,jump: function(obj, first){
		    	      if(!first){
		    	    	  var netWorkId=$('#netWorkCombo').val();//网点ID
		    	    	  var startTime=$('#startTime').val();//开始时间
		    	    	  var endTime=$('#endTime').val();//结束时间
		    	    	  var transferName=$('#transferName').combobox('getValue');//中转方
		    	    	  var transferSn=$('#transferSn').val();//中转单号
		    	   
		    	    	
		    	    	   window.location.href="${ctx}/kd/query/transfer?pageNo="+obj.curr
		    	    			      +"&netWorkId="+netWorkId+"&startTime="+startTime+"&endTime="+endTime+"&transferName="+transferName+
		    	    	   						"&transferSn="+transferSn; 
		    	      }
		    	  }
		    	  ,skin: '#1E9FFF'
		    });
		});
				
		function openDiv(shipid){
	   		//页面层\
				layer.open({
				  type: 2,
				  area: ['850px', '700px'], //宽高
				  content: ['${ctx}/kd/track/transfer?shipId='+shipid, 'yes']
				});
			}
		   		
		   		
		   	
		</script>
</body>

</html>
