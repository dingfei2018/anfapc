<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>中转应付</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/pransfer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>

	<body>
	   <%@ include file="../../common/head2.jsp" %>
	
        <%@ include file="../../common/head.jsp" %>

		<div class="banner">
			<%@ include file="../../common/financialleft.jsp" %>
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
						<a href="${ctx }/kd/finance/payable" class="at">配载应付</a>
					</li>
					<li>
						<a href="${ctx }/kd/finance/payable/transferpay" class="actives at">中转应付</a>
					</li>
				</ul>
				<div class="banner-right-list">
					<form action="${ctx }/kd/finance/payable/transferpay" method="post" id="searchForm">
					<div class="div">
					<span class="span">开单网点：</span>
					<select name="shipNetWorkId" id="shipNetWorkId">
                            <option value="0">请选择 </option>
                            <c:forEach var="work" items="${comNetworks}">
                                <option value="${work.id}" <c:if test="${search.shipNetWorkId ==work.id}">selected</c:if>>${work.sub_network_name}</option>
                            </c:forEach>
                     </select>
					</div>
					 <div class="div">
					<span class="span">中转网点：</span>
					<select name="tranNetWorkId" id="tranNetWorkId">
                            <option value="0">请选择 </option>
                            <c:forEach var="work" items="${userNetworks}">
                                <option value="${work.id}" <c:if test="${search.tranNetWorkId ==work.id}">selected</c:if>>${work.sub_network_name}</option>
                            </c:forEach>
                     </select>
                     </div>
					 <div class="div">
					<span class="span">中转时间：</span><input type="text" class="Wdate" name="startTime"  id="startTime" value="${search.startTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})"/>
					</div>
					 <div class="div">
					<span class="spanc">至</span><input type="text" class="Wdate" value="${search.endTime }" name="endTime" id="endTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})"/>
					</div>
					 
					 <div class="div">
					<span class="span">付款状态：</span> <select name="state" id="state">
						<option value="">请选择</option>
						<option <c:if test="${search.state =='1'}">selected</c:if> value="1">已付款</option>
						<option <c:if test="${search.state =='0'}">selected</c:if> value="0">未付款</option>
					</select>
					</div>
					<div class="div">
					<span class="span">中转方：</span><input type="text" name="transferName" id="transferName" value="${search.transferName }" />
					</div>					
					 <div class="div">
					<span class="span">中转单号：</span><input type="text" name="transferSn" id="transferSn" value="${search.transferSn }"/>
					</div>
					  <div class="div">
					<span class="span">运单号：</span><input type="text" name="shipSn" id="shipSn"  value="${search.shipSn }"/>
					</div>
					<div class="div">
					<button onclick="search();" >查询</button>
					<input class="buttons" type="reset" value="重置"/>
					</div>
					</form>
				</div>

				<div class="banner-right-list2">
					<p class="banner-right-p">中转应付列表</p>
					<div style="overflow: auto; width: 100%;" id="loadingId">
					<script >$("#loadingId").mLoading("show");</script>
					
						<table border="0" class="tab_css_1" id="table" style="border-collapse:collapse;">
							<thead>
								<th><input type="checkbox" style="display: none;"/></th>
								<th>序号</th>
								<th>中转单号</th>
								<th>中转网点</th>
								<th>中转方</th>
								<th>中转联系人</th>
								<th>中转日期</th>
								<th>中转费</th>
								<th>付款状态</th>
								<th>运单号</th>
								<th>开单网点</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>托运方</th>
								<th>收货方</th>
								<th>体积</th>
								<th>重量</th>
								<th class="banner-right-padding">件数</th>
								<th class="banner-right-th">操作</th>
							</thead>
							
							 <c:if test="${fn:length(page.list)>0}">
							 <c:forEach items="${page.list}" var="row" varStatus="vs">
							<tr class="tr_css" align="center">
								<td><input type="checkbox"  value="${row.ship_id}" data="${row.ship_transfer_fee }" /></td>
								<td>${vs.index+1}</td>
								<td  style="color: #3974f8;cursor: pointer;">${row.ship_transfer_sn}</td>
								<td>${row.transferNetName}</td>
								<td>${row.transferCorpName}</td>
								<td>${row.transferName}</td>
								<td><fmt:formatDate value="${row.ship_transfer_time}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
								<td style="color: #ff7801;">${row.ship_transfer_fee}</td>
								<td style="color: #ff7801;">
								<c:choose>
								<c:when test="${row.pay_status==1}">已付款</c:when>
								<c:otherwise>
            					未付款
         						</c:otherwise>
								</c:choose>
								</td>
								<td><a href="#" onclick="openDiv(${row.ship_id})" class="btn">${row.ship_sn}</a></td>
								<td>${row.shipNetName}</td>
								<td>${row.fromAdd}</td>
								<td>${row.toAdd}</td>
								<td>${row.senderName}</td>
								<td>${row.receiverName}</td>
								<td>${row.ship_volume}</td>
								<td>${row.ship_weight}</td>
								<td class="banner-right-padding">${row.ship_amount}</td>
								<c:choose>
								<c:when test="${row.pay_status==1}"><td class="banner-right-th"></td></c:when>
								<c:otherwise>
								<td class="banner-right-th" onclick="goupdate(${row.tranferShipId},${row.pay_status})"><img src="${ctx }/static/kd/img/r_icon1.png"/><span>修改</span>
								</td>
         						</c:otherwise>
								</c:choose>
							</tr>
							</c:forEach>
							</c:if>
					
						</table>
						
					<div id="page" style="text-align: center;">
						</div>
						<c:if test="${fn:length(page.list)==0}">
				 		<div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       	<p>
				      	</p>
				     	暂无数据
						</div>
						</c:if>
						
					</div>
					<ul class="ul2">
					<c:if test="${fn:length(page.list)>0}">
						<li><input type="checkbox" id="selectAll" onclick="selectAll();" />全选</li>
						<li>
							<a href="#" class="banner-right-a3" id="gun" onclick="confirmAll();">确认付款</a>
						</li>
						<li>
							<a href="#" onclick="downExcel();" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
						</li>
					</c:if>
					</ul>
				</div>

			</div>
		</div>
	
		<%@ include file="../../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		function search(){
			$('#searchForm').submit();
		}
		
		
		 //全选确认
	    function confirmAll(){
	        var array = new Array();
	        var fee=0;
	        $(".tr_css input[type='checkbox']").each(function(i){
	        	
	            if($(this).prop("checked")) {
	            	fee+=parseFloat($(this).attr('data'));
	            	array.push($(this).val());
	            }
	            
	        });
	        console.log(array);
	        confirm(array,fee)
	    }
		 
	    function confirm(objs,fee) {
	        if(objs==null||objs==""){
	            layer.msg("请选择要确认付款的运单");
	            return;
	        }
	        layer.confirm(
	            '共计'+objs.length+"单，应收金额共计"+fee+"元，确认已付款？",
	            {
	                btn : [ '确认', '取消' ]
	            },
	            function() {
	            	$.ajax({
    					type : "post",
    					dataType : "json",
    					url : '${ctx }/kd/finance/payable/confirmTransferPay?shipIds='+objs,
    					success : function(data) {
    						console.log(data);
							if (data.success==true) {
								layer.msg('确认成功');
								setTimeout("location.reload();",1000)
							} else {
								layer.msg('确认失败');
							}
							
    					}
    				});
	            }, function() {
	            });
	    }
		
		
		function openDiv(shipid){
	        //页面层
	        layer.open({
	            type: 2,
	            area: ['850px', '700px'], //宽高
	            content: ['${ctx}/kd/waybill/viewDetail?shipId='+shipid, 'yes']
	        });
	    }
		
		function goupdate(shipId){
			
			//页面层
			layer.open({
			  type: 2,
			  area: ['800px', '800px'], //宽高
			  content: ['${ctx}/kd/transfer/goUpdate?shipId='+shipId+'&flag=payUpdate', 'no'],
			  end:function(){
				  location.reload();
			  }
			});
			
			
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
	        	    	   window.location.href="${ctx }/kd/finance/payable/transferpay?pageNo="+obj.curr }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	    });
		   
	    function selectAll() {
	        if($("#selectAll").is(':checked')){
	            $("#table  input[type='checkbox']").prop("checked", true);
	        }else{
	            $("#table input[type='checkbox']").prop("checked", false)
	        }
	    }
	    
	    function downExcel(){
	    	
	    	var tranNetWorkId=$('#tranNetWorkId').val();
	    	var startTime=$('#startTime').val();
	    	var endTime=$('#endTime').val();
	    	var shipNetWorkId=$('#shipNetWorkId').val();
	    	var state=$('#state').val();
	    	var transferSn=$('#transferSn').val();
	    	var shipSn=$('#shipSn').val();
	    	var transferName=$('#transferName').val();
	    	
	    	layer.confirm(
	    			'确定导出对账应收Excel吗',
	    			{
	    				btn : [ '确认', '返回' ]
	    			},
	    			function() {
	    				window.location.href='${ctx}/kd/finance/payable/downLoadTransferPay?tranNetWorkId='+tranNetWorkId+
	    						'&shipNetWorkId='+shipNetWorkId+'&state='+state+'&transferName='+transferName+'&transferSn='+transferSn+
	    						'&shipSn='+shipSn+'&startTime='+startTime+'&endTime='+endTime;
	    	}, function() {
	    			});
	    	
	    }
		
	    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
		</script>
	</body>

</html>