<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>按运单查询</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/checkorder.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/kd/js/query/queryship.js?v=${version}"></script>
	</head>
	<body>
	    <!-- 头部文件 -->
		<%@ include file="../common/head2.jsp" %>
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
			    });
	       </script>
				<div class="banner-right">
				<form id="searchFrom" onsubmit="return false;">
				<div class="banner-right-list">
				<div class="div">
					<span class="span">开单网点：</span>
						<select name="networkId" id="networkId">
							<option value="0">请选择网点</option>
							<c:forEach items="${netWorkList}" var="net">
								<option value="${net.id}" ${model.networkId==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>	
				<div class="div">
					<span class="span">开单日期：</span>
					<input type="text" class="Wdate" name="createTimeStart" id="createTimeStart" value="${model.createTimeStart}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>	
					 <div class="div">
					<span class="spanc">至</span><input type="text" class="Wdate" value="${model.createTimeEnd}" id="createTimeEnd" name="createTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/> <br>


					 </div>
			
					
					 
					<div class="div">
					<span class="span">出发地：</span>
					<div class="banner-right-list-liopd">
						
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" id="startCode" name="startCode" value="${model.startCode}">
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
								<input type="hidden" id="endCode" name="endCode" value="${model.endCode}">
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
                 		 <span class="span">运单状态：</span>
                        <select id="shipStatus" name="shipStatus">
                            <option value="" >请选择</option>
                            <option value="1" <c:if test="${model.shipStatus==1}">selected</c:if>>出库中</option>
                            <option value="2" <c:if test="${model.shipStatus==2}">selected</c:if>>运输中</option>
<%--                             <option value="3" <c:if test="${model.shipStatus==3}">selected</c:if>>已中转</option> --%>
                            <option value="4" <c:if test="${model.shipStatus==4}">selected</c:if>>已签收</option>
                        </select>
                  </div>
					<div class="div">
                   <span class="span">托运方：</span><input type="text" id="senderId" name="senderId" />
                  </div>
                  <div class="div">
                      		<span class="span">收货方：</span><input  type="text" id="receiverId" name="receiverId" />
                 	</div>
					<div class="div">
					<span class="span">客户单号：</span><input type="text" name="shipCustomerNumber" id="shipCustomerNumber" value="${model.shipCustomerNumber}"/><br>
					</div>
					<div class="div">
					<span class="span">运单号：</span><input type="text" name="shipSn" id="shipSn" value="${model.shipSn}"/><br>
					</div>
					<div class="div">	
					<button id="search">查询</button>
					<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
					</div>
					<!-- <div class="div">	
					<input class="buttons" type="reset" value="重置"/>
					</div> -->
					</div>
					<div class="banner-right-list2">
					<p class="banner-right-p">按运单查询列表</p>
					<div style="overflow: auto; width: 100%; " id="loadingId">
						<table border="0" class="tab_css_1" id="loadId">
							<thead>
								<th><input type="checkbox" style="display: none;"/></th>
								<th>序号</th>
								<th>运单号</th>
								<th>开单网点</th>
								<th>开单日期</th>
								<th>客户单号</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>体积</th>
								<th>重量</th>
								<th>件数</th>
								<th class="float4">运单状态</th>
								<th class="float2">操作</th>
								
							</thead>
							
							<!-- 列表 -->
							
						</table>

						<div id="page" style="text-align: center;"></div>
				</div>

			</div>

		</div>

				</div>
				</form>
					
			
	
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script> 
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
		   	        	    	   window.location.href="${ctx}/kd/query/ship?pageNo="+obj.curr+"&networkId="+$("#networkId").val()+"&createTimeStart="+$("#createTimeStart").val()+
		   	        	    			   "&createTimeEnd="+$("#createTimeEnd").val()+"&shipSn="+$("#shipSn").val()+"&sendName="+$("#senderId").val()+
		   	        	    			   "&receiveName="+$("#receiveId").val()+"&startCode="+$("#startCode").val()+"&endCode="+$("#endCode").val()
		   	        	    			   +"&shipStatus="+$("#shipStatus").val() +"&shipCustomerNumber="+$("#shipCustomerNumber").val();
		   	        	      }
		   	        	  }
		   	        	  ,skin: '#1E9FFF'
		   	        });
		   	    });
				

		   		/*  $('#senderId').combobox({
			        url:'/kd/customer/queryCustomer?type=2',
			        valueField:'customer_id',
			        textField:'customer_name',
			        height: 30,
			        panelWidth: 162,
			        panelHeight: 'auto'
			    });
			    //收货方信息
			    $('#receiveId').combobox({
			        url:'/kd/customer/queryCustomer?type=1',
			        valueField:'customer_id',
			        textField:'customer_name',
			        height: 30,
			        panelWidth: 162,
			        panelHeight: 'auto'
			    });		 */
			    
		   		function openDiv(shipid){
					//页面层
					layer.open({
					  type: 2,
					  area: ['850px', '700px'], //宽高
					  content: ['${ctx}/kd/waybill/viewDetail?shipId='+shipid, 'yes']
					});
				}
		   		
		   		function openLogDiv(shipid){
					//页面层
					layer.open({
					  type: 2,
					  area: ['850px', '700px'], //宽高
					  content: ['${ctx}/kd/track?shipId='+shipid, 'yes']
					});
				}
		   		
		   		
		   		function openview(shipId){
					layer.open({
						  type: 2,
						  title: "签收照片",
						  area: ['1200px', '700px'],
						  content: ['/kd/query/signupload?shipId='+shipId, 'yes'], //iframe的url，no代表不显示滚动条'/kd/loading/loadchange?truck_id=21'
						  end: function(){ 
							  	$this.search();
							  }
						  });
				}
		   		
		   		
		   		function openTransferAndStowage(shipid){//配载或中转
					//页面层
					layer.open({
					  type: 2,
					  area: ['850px', '700px'], //宽高
					  content: ['${ctx}/kd/query/openTransferAndStowage?shipId='+shipid, 'yes']
					});
				}
		   		
		   	  
		   		function openTrackDiv(shipid){
		   		//页面层
					layer.open({
					  type: 2,
					  area: ['850px', '700px'], //宽高
					  content: ['${ctx}/kd/query/openTrackDiv?shipId='+shipid, 'yes']
					});
				}
		   		
		   		$(function(){
		   			$('#senderId').combogrid({
		   				url : '/kd/customer/searchCustomer?type=2',
		   				idField : 'customer_id',
		   				textField : 'customer_name',
		   				height : 30,
		   				panelWidth : 320,
		   				pagination: true,
		   				columns: [[
		   					{field:'customer_corp_name',title:'公司名',width:200},
		   					{field:'customer_name',title:'姓名',width:150},
		   					{field:'customer_mobile',title:'电话',width:200}
		   				]],
		   				keyHandler:{
		   		            up: function() {},
		   		            down: function() {},
		   		            enter: function() {},
		   		            query: function(q) {
		   		                //动态搜索
		   		               $('#shipperName').combogrid("grid").datagrid("reload", {'queryName': q});
		   		               $('#shipperName').combogrid("setValue", q);
		   		            }
		   		        },
		   				fitColumns: true
		   			});
		   		    
		   			//收货方信息
		   			$('#receiverId').combogrid({ 
		   				url : '/kd/customer/searchCustomer?type=1',
		   				idField : 'customer_id',
		   				textField : 'customer_name',
		   				height : 30,
		   				panelWidth : 320,
		   				pagination: true, 
		   				columns: [[
		   					{field:'customer_corp_name',title:'公司名',width:200},
		   					{field:'customer_name',title:'姓名',width:150},
		   					{field:'customer_mobile',title:'电话',width:200}
		   				]],
		   				keyHandler:{
		   		            up: function() {},
		   		            down: function() {},
		   		            enter: function() {},
		   		            query: function(q) {
		   		                //动态搜索
		   		               $('#receivingName').combogrid("grid").datagrid("reload", {'queryName': q});
		   		               $('#receivingName').combogrid("setValue", q);
		   		            }
		   		        },
		   				fitColumns: true
		   			});
		   		})
		   		
		   	function resetCity(){
       			 $("#city-picker2").citypicker("reset");
       			 $("#city-picker3").citypicker("reset");
       			 
       			 $("#senderId").combogrid("clear");
       			 $("#receiverId").combogrid("clear");
  			  }
		</script>
	</body>

</html>
