<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>已转运单</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/shipmen.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<link href="${ctx}/static/pc/study/css/city-picker.css" rel="stylesheet">
		<link href="${ctx}/static/pc/study/css/main.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/study/js/city-picker.data.js"></script>
		<script src="${ctx}/static/pc/study/js/city-picker.js"></script>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>

	<body>
		<%@ include file="../common/head2.jsp" %>
		 <%@ include file="../common/head.jsp" %>
		<input type="hidden" value="${result.msg }" id="msg"/>
		<div class="banner">
			<%@ include file="../common/fahuoleft.jsp" %>
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
						<a href="${ctx }/kd/transfer" class="at">建立中转</a>
					</li>
					<li>
						<a href="${ctx }/kd/transfer/transShipMent" class="activet at">已转运单</a>
					</li>
				</ul>

				<div class="banner-right-list">
					<form id="searchForm" onsubmit="return false;"; method="post">
					<div class="div">
					<span class="span">中转网点：</span>
					<select name="netWorkId" id="netWorkCombo">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}" >${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
                        </div>
					<div class="div">
					<span class="span">中转日期：</span><input style="margin-left:0px;" type="text" class="Wdate" name="startTime" id="startTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})"/>
					</div>
					
					<div class="div">
					<span class="spanc">至</span><input type="text" class="Wdate"  id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})"/><br>
					</div>
					<div class="div">
				   <span class="span">出发地：</span>
					<div class="banner-right-list-liopd">
						
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" id="startCode" value="${search.startCode }"  name="startCode"/>
								<input id="city-picker2" class="form-control" placeholder="请选择省/市/区" readonly type="text" data-toggle="city-picker">
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
						
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" id="endCode" value="${search.endCode }"  name="endCode"/>
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
					<span class="span">托运方：</span><input id="shipperName" name="shipperName" type="text" />
				  </div>
				  <div class="div">
						<span class="span">收货方：</span><input value="${search.receivingName }" id="receivingName" name="receivingName" type="text" />					
					</div>
				  <div class="div">
					<span class="span">中转方：</span><input id="transferName" name="transferName" type="text" />
					</div>
					<div class="div">
					<span class="span">中转单号：</span><input id="transferSn" name="transferSn"  type="text" />
					</div> 
					<div class="div">
						<span class="span">运单号：</span><input value="${search.shipSn }" id="shipSn" name="shipSn" type="text" />
					    </div>
					    <div class="div">
						<button onclick="search();">查询</button>
					    <input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
					</div>
					<!-- <div class="div">
						
					</div> -->
					</form>
				</div>

				<div class="banner-right-list2">
					<p class="banner-right-p">中转列表</p>
					<div style="overflow: auto; width: 100%; " id="loadingId">
						<table border="0" id="table" class="tab_css_1" style="border-collapse:collapse;">
							
						</table>
					</div>
					
					<div id="page" style="text-align: center;">
						</div>
					<ul class="ul2">
						<li>
							<a href="javascript:downExcel();" class="banner-right-a3 at" style="background: #fff;text-decoration:underline;border: none; color:#3974f8;">导出EXCEL</a>
						</li>
					</ul>
					
				</div>
				
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
				<script type="text/javascript">
				
				function resetCity(){
					$("#city-picker2").citypicker("reset");
					$("#city-picker3").citypicker("reset");
					 
					$('#shipperName').combogrid('clear');
					$('#receivingName').combogrid('clear');
					$('#transferName').combogrid('clear');
				}
				
				init(1);
				$('#shipperName').combogrid({
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
				$('#transferName').combogrid({
					url : '/kd/customer/searchCustomer?type=3',
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
			               $('#transferName').combogrid("grid").datagrid("reload", {'queryName': q});
			               $('#transferName').combogrid("setValue", q);
			            }
			        },
					fitColumns: true
				});
			    //收货方信息
			    $('#receivingName').combogrid({
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
				
				
				if($('#msg').val().length!=0){
						parent.layer.closeAll();
				}
				
				function search(){
					init(1);
				}
				
				function init(pageNo){
					 $("#loadingId").mLoading("show");
					$.ajax({
								url : '/kd/transfer/getTransShipMentData?pageNo='+pageNo,
								data : $("#searchForm").serialize(),
								type : 'post',
								dataType : 'json',
								async : false,
								success : function(result) {
								   var data=result.list;
								   var html='<thead><th>序号</th><th>中转单号</th>'+
										'<th>中转方</th><th>中转联系人</th><th>中转费</th><th>中转日期</th>'+
										'<th>中转网点</th><th>运单号</th><th>开单网点</th><th>出发地</th>'+
										'<th>到达地</th><th>托运方</th><th>收货方</th><th>体积</th>'+
										'<th>重量</th><th class="banner-right-padding">件数</th>'+
										'<th class="banner-right-th">操作</th>'+
									'</thead>';
								for(var i=0;i<data.length;i++){
									var transfer=data[i];
									html+='<tr class="tr_css" align="center">'+
									 '<td>'+(i+1)+'</td>'+
									 '<td  style="color: #3974f8;cursor: pointer;">'+transfer.ship_transfer_sn+'</td>'+
									 '<td>'+transfer.transferCorpName+'</td>'+
									 '<td>'+transfer.transferName+'</td>'+
									 '<td style="color: #ff7801;">'+transfer.ship_transfer_fee+'</td>'+ 
									 '<td>'+transfer.ship_transfer_time+'</td>'+
									 '<td>'+transfer.tranNetName+'</td>'+
									 '<td><a href="#" onclick="openShipDiv('+transfer.ship_id+')">'+transfer.ship_sn+'</a></td>'+
									 '<td>'+transfer.shipNetName+'</td>'+
									 '<td>'+transfer.fromAdd+'</td>'+
									 '<td>'+transfer.toAdd+'</td>'+
									 '<td>'+transfer.senderName+'</td>'+
									 '<td>'+transfer.receiverName+'</td>'+
									 '<td>'+transfer.ship_volume+'</td>'+
									 '<td>'+transfer.ship_weight+'</td>'+
									 '<td class="banner-right-padding">'+transfer.ship_amount+'</td>'+
									 '<td class="banner-right-th1"><img src="${ctx }/static/kd/img/r_icon1.png" /><span onclick="goupdate('+transfer.ship_id+','+transfer.ship_status+','+transfer.id+');">修改</span>'+
									 '<div class="banner-right-list2-tab2">'+
									 '<dl><dd class="input2"><img src="${ctx }/static/kd/img/r_icon10.png" /><span onclick="deleteTransfer('+transfer.ship_id+',\''+transfer.ship_sn+'\','+transfer.ship_status+','+transfer.network_id+','+transfer.id+')">取消中转</span></dd>'+
									 '</dl>'+
									 ' </div></td></tr>';
									 
								}
									 $('#table').html(html);
									 setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
										
										//分页
										layui.use([ 'laypage' ], function() {
											var laypage = layui.laypage;
											//调用分页
											laypage({
												cont : 'page',
												pages : result.totalPage //得到总页数
												,
												curr : result.pageNumber,
												skip : true,
												count : result.totalRow,
												jump : function(obj, first) {
													if (!first) {
														init(obj.curr);
														//window.location.href="${ctx}/kd/transfer/getWayBill?pageNo="+obj.curr+"&customer_sn="+$("#customer_sn").val()+"&customer_mobile="+$("#customer_mobile").val()+"&customer_name="+$("#customer_name").val();
													}
												},
												skin : '#1E9FFF'
											});
										});

								}
							});					
					
				}
				
				function goupdate(shipId,state,id){
					if(state=='9'){
						layer.msg('不能修改已签收运单');	
						return;
					}
                    if(id!=null){
                        layer.msg('不能修改已结算中转单');
                        return;
					}
					//页面层
					layer.open({
					  type: 2,
					  area: ['1200px', '800px'], //宽高
					  content: ['${ctx}/kd/transfer/goUpdate?shipId='+shipId, 'no'],
					  end:function(){
						  location.reload();
					  }
					});
					
					
				}
				
				function deleteTransfer(objs,shipSN,state,transferNetworkId,id) {
					
					if(state=='9'){
						layer.msg('不能取消已签收运单');	
						return;
					}
                    if(id!=null){
                        layer.msg('不能取消已结算的中转单');
                        return;
                    }
					
					layer.confirm(
							'确定取消运单'+shipSN+'的中转吗',
							{
								btn : [ '确认取消中转', '返回' ]
							},
							function() {
								$.ajax({
									type : "post",
									dataType : "json",
									url : "${ctx }/kd/transfer/delTransfer?shipId="
											+ objs+"&transferNetworkId="+transferNetworkId,
									success : function(data) {
										console.log(data);
										
										if (data.type == "1") {
											layer.msg("取消成功！",{time: 1000},function(){
												window.location.href="${ctx}/kd/transfer/transShipMent";
						                        });
											
										} else {
											layer.msg(data.msg);
										}
									}
								});
					}, function() {
							});
				}
				
				function downExcel(){
					
					layer.confirm(
							'确定导出已转运单列表Excel吗',
							{
								btn : [ '确认', '返回' ]
							},
							function() {
								
								var netWorkId=$('#netWorkCombo').val();
				    	    	  var startTime=$('#startTime').val();
				    	    	  var endTime=$('#endTime').val();
				    	    	  var transferName=$('#transferName').combobox('getValue');
				    	    	  var transferSn=$('#transferSn').val();
				    	    	  var shipperName=$('#shipperName').combobox('getValue');
				    	    	  var receivingName=$('#receivingName').combobox('getValue');
				    	    	  var startCode=$('#startCode').val();
				    	    	  var endCode=$('#endCode').val();
				    	    	  var shipSn=$('#shipSn').val();
				    	    	   window.location.href="${ctx}/kd/transfer/downLoad?netWorkId="+netWorkId+"&startTime="+startTime+"&endTime="+endTime+"&transferName="+transferName+
				    	    	   						"&transferSn="+transferSn+"&shipperName="+shipperName+"&receivingName="+receivingName+
				    	    	   						"&startCode="+startCode+"&endCode="+endCode+"&shipSn="+shipSn;
								
					}, function() {
							});
					
				}
				
				function openShipDiv(shipid){
			        //页面层
			        layer.open({
			            type: 2,
			            area: ['850px', '700px'], //宽高
			            content: ['/kd/waybill/viewDetail?shipId='+shipid, 'yes']
			        });
			     }
				
				</script>
	</body>

</html>