<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>按配载查询</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/checkcar.css" />
			<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/query/loadquery.js?v=${version}"></script>
	</head>
	<body>
	<!-- 头部文件 -->
	
		<%@ include file="../common/head2.jsp"   %>
		<%@ include file="../common/head.jsp" %>

		<div class="banner">
			<!-- 左边菜单 -->
			<%@ include file="../common/startleft.jsp" %>
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
				<form  id="searchFrom" onsubmit="return false;">
				<div class="banner-right-list">
				   <div class="div">
                        <span class="span">配载网点：</span>
                        <select name="loadNetworkId" id="loadNetworkId">
                            <option value="0">请选择 </option>
                            <c:forEach var="network" items="${netWorkList}">
                                <option value="${network.id}" <c:if test="${model.loadNetworkId ==network.id}">selected</c:if>>${network.sub_network_name}</option>
                            </c:forEach>
                        </select>
                      </div> 
				  
					 <div class="div">
					<span class="span">发车日期：</span>
					<input type="text" class="Wdate" name="startTime" id="startTime" value="${model.startTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div> 
					 <div class="div">
					<span class="spanc">至</span><input type="text" class="Wdate" value="${model.endTime }" id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/> <br>
					</div> 
					<div class="div">
						  <span class="span">出发地：</span>	
					<div class="banner-right-list-liopd">
					   
					 		<div class="form-group">
								<div style="position: relative;">
								<input type="hidden" name="deliveryFrom" id="deliveryFrom"  value="${model.deliveryFrom}">
									<input id="city-picker3" class="form-control"
										placeholder="请选择省/市/区" readonly type="text"
										data-toggle="city-picker">
								</div>
								<script>
									$(function(){
										$(".city-picker-span").css("width","auto");
									})
								</script>
							</div>
						</div>
						 </div>	
						   <div class="div">
						  <span class="span">到达地：</span>
					<div class="banner-right-list-liopc">
						
							<div class="form-group" id="banner-right-formgroup">
								<div style="position: relative;">
								<input type="hidden" name="deliveryTo" id="deliveryTo" value="${model.deliveryTo}">
									<input id="city-picker2" class="form-control"
										placeholder="请选择省/市/区" readonly type="text"
										data-toggle="city-picker">
								</div>
								<script>
									$(function(){
										$(".city-picker-span").css("width","160px");
									})
								</script>
							</div>
						</div>
						 </div>
					 <div class="div">
					<span class="span">司机：</span><input type="text" name="driverName" id="driverName" value="${model.driverName}" />
					 </div>	
					 <div class="div">
						<span class="span">车牌号：</span><input type="text" name="truckNumber" id="truckNumber" value="${model.truckNumber}" />
					 </div>
					   <div class="div">
					<span class="span">配载单号：</span><input type="text" name="loadSn" id="loadSn" value="${model.loadSn}" />
					 </div>
					
					     <div class="div">
							<button id="search">查询</button>
							<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
						  </div>
				</div>
				</form>

			<div class="banner-right-list2">
					<p class="banner-right-p">按配载查询列表</p>
					<div style="overflow: auto; width: 100%; "  id="loadingId">
						<table border="0" class="tab_css_1" id="loadId">
							<thead>
								<%--<th><input type="checkbox" style="display: none;"/></th>--%>
								<th>序号</th>
								<!-- <th>运单号</th> -->
								<th>配载单号</th>
								<!-- <th>开单网点</th>
								<th>开单日期</th> -->
								<th>配载网点</th>
								<th>发车日期</th>								
								<th>车牌号</th>								
								<th>司机</th>
								<th>司机电话</th>
								<th>运输类型</th>								
								<th>出发地</th>
								<th>到达地</th>
								<th>体积</th>
								<th>重量</th>
								<!-- <th>件数</th> -->
								<th>到货网点</th>
								<th class="banner-right-padding">是否已到达</th>
								<th class="float2">操作</th>
							</thead>
							<!-- 列表数据 -->
							
						</table>
				</div>
					<%--<ul class="ul2">
						<li><input type="checkbox" id="selectAll" onclick="selectAll();"/><label>全选</label></li>
						<li>
							<a href="#" class="banner-right-a3" onclick="updateAll();">删除</a>
						</li>
						<li>
							<a href="#" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">到出EXCLE</a>
						</li>
					</ul>--%>
							<div id="page" style="text-align: center;"></div>
			</div>
		</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script> 
				function selectAll() {
		            if($("#selectAll").is(':checked')){
		                $(".banner-right-list2  input[type='checkbox']").prop("checked", true);
		            }else{
		                $(".banner-right-list2 input[type='checkbox']").prop("checked", false)
		            }
		        }
				
				//全选删除
		        function updateAll(){
		            var array = new Array();
		            $(".banner-right-list2 input[type='checkbox']").each(function(i){
		                if($(this).prop("checked")) array.push($(this).val());

		            });
		            update(array);
		        }
		        function update(objs) {
		            if(objs==null||objs==""){
		                layer.msg("请选择要删除的数据!");
		                return;
		            }
		            layer.confirm(
		                '您确定要删除吗？',
		                {
		                    btn : [ '确定', '取消' ]
		                },
		                function() {
		                    $.ajax({
		                        type : "post",
		                        dataType : "json",
		                        url : "${ctx }/kd/waybill/delShip?shipId=" + objs,
		                        success : function(data) {
		                        	alert(data);
		                            if (data.success==true) {
		                               window.location.href="${ctx}/kd/query";
		                            } else {
		                                layer.msg(obj.msg);
		                            }
		                        }
		                    });
		                }, function() {
		                });
		        }
		        //删除
		        function oneUpdate(id){
		            layer.confirm(
		                '您确定要删除吗？',
		                {
		                    btn : [ '确定', '取消' ]
		                },
		                function() {
		                    $.ajax({
		                        type : "post",
		                        dataType : "json",
		                        url : "${ctx }/kd/waybill/delShip?shipId==" + id,
		                        success : function(data) {
		                            if (data.success==true) {
		                            	 window.location.href="${ctx}/kd/query";
		                            } else {
		                                layer.msg(obj.msg);
		                            }
		                        }
		                    });
		                }, function() {
		                });
				   }
				
				
				   //分页
		   	    layui.use(['laypage'], function(){
		   	    	var laypage = layui.laypage;
		   	        //调用分页
		   	        laypage({
		   	    	      cont: 'page'
		   	    	      ,pages: '${page.totalPage}' //得到总页数
		   	    	      ,curr:'${page.pageNumber}'
		   	    	      ,skip: true
		   	    	   	  ,count:'${page.totalRow}'
		   	        	  ,jump: function(obj, first){
		   	        	      if(!first){
		   	        	    	   window.location.href="${ctx}/kd/query?pageNo="+obj.curr+"&loadSn="+$("#loadSn").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&driverName="+$("#driverName").val()+"&truckNumber="+$("#truckNumber").val()+"&deliveryFrom="+$("#deliveryFrom").val()+"&deliveryTo="+$("#deliveryTo").val();
		   	        	      }
		   	        	  }
		   	        	  ,skin: '#1E9FFF'
		   	        });
		   	    });

                function openLogDiv(shipid){
                    //页面层
                    layer.open({
                        type: 2,
                        area: ['850px', '700px'], //宽高
                        content: ['${ctx}/kd/track?shipId='+shipid, 'yes']
                    });
                }

		   		function openDiv(loadId){
					//页面层
					layer.open({
					  type: 2,
					  area: ['1000px', '700px'], //宽高
					  content: ['${ctx}/kd/loading/loadingView?loadId='+loadId, 'yes']
					});
				}
                function openview(shipId){
                    layer.open({
                        type: 2,
                        title: "签收照片",
                        area: ['1200px', '700px'],//宽高
                        content: ['/kd/query/signupload?shipId='+shipId, 'yes'], //iframe的url，no代表不显示滚动条'/kd/loading/loadchange?truck_id=21'
                        end: function(){
                            $this.search();
                        }
                    });
                }
		</script>
	</body>

</html>
