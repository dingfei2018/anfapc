<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>车辆列表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/carlist.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>
	<body>
	   <%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/basezlleft.jsp" %>
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
			 <input type="hidden" value="${msg.msg}" id="msg" />
			 <input type="hidden" value="${truck_hired_mode}" id="s_truck_hired_mode" />
			<div class="banner-right">
				<ul>
					<li><a href="${ctx}/kd/truck" class="activet">车辆列表</a></li>
					<li><a href="${ctx}/kd/truck/add">新增车辆</a></li>
				</ul>
				
				<div class="banner-right-list">
				<form onsubmit="return false;">
				<ul>
				  <li>
					<span class="span">车厢类型：</span>
					<select  class="easyui-combobox" id="truck_type" value="3">
								<option value="">请选择</option>
								<option value="1" >厢式货车</option>
								<option value="2" >面包车</option>
								<option value="3" >金杯车(高顶)</option>
								<option value="4">金杯车(低顶)</option>
								<option value="5">中巴货车</option>
								<option value="6">高栏车</option>
								<option value="7">低栏车</option>
								<option value="8">平板车</option>
								<option value="9">高低板车</option>
								<option value="10">半挂车</option>
								<option value="11">自卸车</option>
								<option value="12">冷藏车</option>
								<option value="13">保温车</option>
								<option value="14">罐式车</option>
								<option value="15">铁笼车</option>
								<option value="16">集装箱运输车</option>
								<option value="17">轿车运输车</option>
								<option value="18">大件运输车 </option>
								<option value="19">起重车</option>
								<option value="20">危险品车</option>
								<option value="21">爬梯车 </option>
								<option value="22">中栏车 </option>
								<option value="23">全挂车 </option>
								<option value="24">加长挂车 </option>
						</select>
						</li>
						<li>
					<span class="span">租赁方式：</span>
					<select name="truck.truck_hired_mode" id="truck_hired_mode">
							<option value="" >请选择</option>
							<option value="1">自由车辆</option>
							<option value="2">外包车辆</option>
							<option value="3">临时车辆</option>
					</select>
					</li>
					<li>
					<span class="span">车牌号：</span><input type="text" id="truck_id_number" value="${truck_id_number }" />
					</li>
					<li>
					<span class="span">司机：</span><input type="text" id="truck_driver_name" value="${truck_driver_name }"/>
					</li>
					<li style="clear:both">
					<span class="span">司机电话：</span><input type="text" id="truck_driver_mobile" value="${truck_driver_mobile }" />
					</li>
					<li>
					  <button onclick="search();">查询</button> 
					  <input class="buttons" type="reset" value="重置"/>
					</li>
					<!-- <li style="text-align: left;">
					   <input class="buttons" type="reset" value="重置"/>
					</li> -->
					 </ul>
					 </form>
				</div>
				
				<p class="banner-right-p">车辆列表</p>
				<div style="overflow: auto; width: 100%;" id="loadingId">
				<script >$("#loadingId").mLoading("show");</script>
				<table cellspacing="0" id="table">
					<tr>
						<th><input type="checkbox" style="display: none;"/></th>
						<th class="banner-right-pleft">序号</th>
						<th>车牌号</th>
						<th>车厢长度（米）</th>
						<th>车厢类型</th>
						<th>租赁方式</th>
						<th>司机</th>
						<th>司机电话</th>
						<th>操作</th>
					</tr>
					<c:if test="${fn:length(page.list)>0}">
					<c:forEach items="${page.list}" var="truck" varStatus="vs">
					<tr>
						<td class="banner-right-pleft"><input type="checkbox" value="${truck.truck_id}"/></td>
						<td>${vs.index+1}</td>
						<td><a class="banner-right-a4" href="${ctx}/kd/truck/add?type=update&truckId=${truck.truck_id}" >${truck.truck_id_number}</a></td>
						<td>${truck.truck_length}</td>
						<td>
						<c:if test="${truck.truck_type==1}">厢式货车</c:if>
						<c:if test="${truck.truck_type==2}">面包车</c:if>
						<c:if test="${truck.truck_type==3}">金杯车(高顶)</c:if>
						<c:if test="${truck.truck_type==4}">金杯车(低顶)</c:if>
						<c:if test="${truck.truck_type==5}">中巴货车</c:if>
						<c:if test="${truck.truck_type==6}">高栏车</c:if>
						<c:if test="${truck.truck_type==7}">低栏车</c:if>
						<c:if test="${truck.truck_type==8}">平板车</c:if>
						<c:if test="${truck.truck_type==9}">高低板车</c:if>
						<c:if test="${truck.truck_type==10}">半挂车</c:if>
						<c:if test="${truck.truck_type==11}">自卸车</c:if>
						<c:if test="${truck.truck_type==12}">冷藏车</c:if>
						<c:if test="${truck.truck_type==13}">保温车</c:if>
						<c:if test="${truck.truck_type==14}">罐式车</c:if>
						<c:if test="${truck.truck_type==15}">铁笼车</c:if>
						<c:if test="${truck.truck_type==16}">集装箱运输车</c:if>
						<c:if test="${truck.truck_type==17}">轿车运输车</c:if>
						<c:if test="${truck.truck_type==18}">大件运输车</c:if>
						<c:if test="${truck.truck_type==19}">起重车</c:if>
						<c:if test="${truck.truck_type==20}">危险品车</c:if>
						<c:if test="${truck.truck_type==21}">爬梯车</c:if>
						<c:if test="${truck.truck_type==22}">中栏车</c:if>
						<c:if test="${truck.truck_type==23}">全挂车</c:if>
						<c:if test="${truck.truck_type==24}">加长挂车</c:if>
						</td>
						<td>
						<c:if test="${truck.truck_hired_mode==1}">自由车辆</c:if>
						<c:if test="${truck.truck_hired_mode==2}">外包车辆</c:if>
						<c:if test="${truck.truck_hired_mode==3}">临时车辆</c:if>
						</td>
						<td>${truck.truck_driver_name}</td>
						<td>${truck.truck_driver_mobile}</td>
						<td><a class="banner-right-a1" href="${ctx}/kd/truck/add?type=update&truckId=${truck.truck_id}">修改</a><a class="banner-right-a2" onclick="javascript:deletetruck(${truck.truck_id})">删除</a></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
				</div>
				
				 <div id="page" style="text-align: center;">
				</div>
				<c:if test="${fn:length(page.list)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				</div>
			</c:if>
			<c:if test="${fn:length(page.list)!=0}">
				<ul class="ul2">
					<li><input type="checkbox" id="selectAll" onclick="selectAll();" />全选</li>
					<li><a href="#" class="banner-right-a3" onclick="deleteAll();">删除</a></li>
				</ul>
			</c:if>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
			<script type="text/javascript">
				if($("#msg").val()!==""){
					
					layer.msg($("#msg").val());
					}
				$('#truck_hired_mode').val($('#s_truck_hired_mode').val());
				
				
				$('#truck_type').combo({
				    value:"${truck_type}",
				    height:30
				});
				
				document.onkeydown = function(e){ 
				    var ev = document.all ? window.event : e;
				    if(ev.keyCode==13) {
				    	search();
				     }
				}
				
				//全选事件
				function selectAll() {
					if($("#selectAll").is(':checked')){
						$("#table input[type='checkbox']").prop("checked", true);
					}else{
						$("#table input[type='checkbox']").prop("checked", false)
					}
				}
				
				 function search(){
					 var truckType=$('#truck_type').combobox('getValue');
					   window.location.href="${ctx}/kd/truck?truck_id_number="+$("#truck_id_number").val()+"&truck_driver_name="+$("#truck_driver_name").val()+"&truck_type="+truckType
					   						+"&truck_hired_mode="+$("#truck_hired_mode").val()+"&truck_driver_mobile="+$("#truck_driver_mobile").val();
				   }
				
				
				function deletetruck(objs) {
					layer.confirm(
							'您确定要删除？',
							{
								btn : [ '删除', '取消' ]
							},
							function() {
								$.ajax({
									type : "post",
									dataType : "html",
									url : "${ctx }/kd/truck/delTruck?truckId="
											+ objs,
									success : function(data) {
										var obj = new Function("return"
												+ data)();
										if (obj.state == "SUCCESS") {
											window.location.href="${ctx}/kd/truck";
										} else {
											layer.msg(obj.msg);
										}
									}
								});
					}, function() {
							});
				}
				
				
				//删除多个
		    	function deleteAll(){
					var array = new Array();
					$("#table input[type='checkbox']").each(function(i){
					if($(this).prop("checked"))array.push($(this).val());
					});
					deleteAllTruck(array);
			    }
		    		
		    		//删除全部
		    		function deleteAllTruck(objs) {
		    	    	if(objs==null||objs==""){
		    	    		layer.msg("请选择要删除的数据");
		    				return;
		    			}
		    	    	layer.confirm('您确定要删除？', {
		    			  	btn: ['确定','取消']
		    			}, function(){
		    				$.ajax({
		    					type : "post",
		    					dataType : "json",
		    					url : "${ctx }/kd/truck/delTruck?truckId="
									+ objs,
		    					success : function(data) {
									if (data.state == "SUCCESS") {
										window.location.href="${ctx}/kd/truck";
									} else {
										layer.msg(data.msg);
									}
		    					}
		    				});
		    			}, function(){});
		    		}
				
				
				
				 //分页
			    layui.use(['laypage'], function(){
			    	var laypage = layui.laypage;
			        //调用分页
			        laypage({
			    	      cont: 'page'
			    	      ,pages: '${page.totalPage}' //得到总页数
			    	      ,curr:'${page.pageNumber}',
			    	      count:'${page.totalRow}'
			    	      ,skip: true
			        	  ,jump: function(obj, first){
			        	      if(!first){
			        	    	  var truckType=$('#truck_type').combobox('getValue');
			        	    	   window.location.href="${ctx}/kd/truck?pageNo="+obj.curr+"&truck_sn="+$("#truck_sn").val()+"&truck_id_number="+$("#truck_id_number").val()+"&truck_driver_name="+$("#truck_driver_name").val()+
			        	    	   "&truck_type="+truckType+"&truck_hired_mode="+$("#truck_hired_mode").val(); 
			        	      }
			        	  }
			        	  ,skin: '#1E9FFF'
			        });
			    });
				
		setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);		
		</script>
	</body>
</html>
