<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运单列表</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/waybills.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp" %>
</head>
<body>
        <form action="${ctx}/kd/abnormal/chooseShip"  method="post" >
            <div class="banner-right-list">
                      <div class="div">
                        <span class="span">开单网点：</span>
                        <select name="netWorkId" id="netWorkId">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}" <c:if test="${model.netWorkId ==work.id}">selected</c:if>>${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
                     </div>
                      <div class="div">
	                   <span class="span">开单日期：</span>
	                    <input type="text" class="Wdate" name="startTime" id="startTime" value="${model.startTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
						</div>
						 <div class="div">
						<span class="spanc">至</span><input type="text" class="Wdate" value="${model.endTime }" id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/> 
                       </div>
                       <div class="div">
                    <span class="span">出发地：</span>
                        <div class="banner-right-list-liopd" style="margin-left:-15px;">
                           
                            <div class="form-group">
                                <div style="position: relative;">
                                    <input type="hidden" name="fromCode" id="fromCode">
                                    <input id="city-picker2" class="form-control"
                                          readonly type="text" value="江苏省/常州市/溧阳市"
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
                        <div class="banner-right-list-liopc" style="margin-left:-13px;">
                            <div class="form-group" >
                                <div style="position: relative;">
                                    <input type="hidden" name="toCode" id="toCode">
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
                        <select id="shipSate" name="shipSate">
                            <option value="" >请选择</option>
                            <option value="1" <c:if test="${model.shipSate==1}">selected</c:if>>出库中</option>
                            <option value="2" <c:if test="${model.shipSate==2}">selected</c:if>>运输中</option>
                            <option value="4" <c:if test="${model.shipSate==4}">selected</c:if>>已签收</option>
                        </select>
                 </div> 
                  <div class="div">
                   <span class="span">托运方：</span><input type="text" id="senderId" name="senderId" />
                  </div>
                  <div class="div">
                      		<span class="span">收货方：</span><input  type="text" id="receiverId" name="receiverId" />
                 	</div>
                 	<div class="div">
                 	  		<span class="span">客户单号：</span><input type="text"  id="customerNumber" name="customerNumber"  value="${model.customerNumber}"/>
                  	        </div>
                  	        <div class="div">
                  			 <span class="span">运单号：</span><input type="text" id="shipSn" name="shipSn" value="${model.shipSn}" />
                	 </div>
                	  <div class="div">
                  	 <button>查询</button>
                  	 <input class="buttons" type="reset" value="重置"/>
                  	 </div>
                  	 <!-- <div class="div">
                  	
                  	 </div> -->
            </div>
        </form>

        <div class="banner-right-list2">
            <p class="banner-right-p">运单列表</p>
            <div style="overflow: auto; width: 100%; ">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;">
                    <thead>
                    <th>序号</th>
                    <th>运单号</th>
                    <th>开单网点</th>
                    <th>开单日期</th>
                    <th>客户单号</th>
                    <th>托运方</th>
                    <th>收货方</th>
                    <th>出发地</th>
                    <th>到达地</th>
                    <th>运单状态</th>
                    <th>体积</th>
                    <th>重量</th>
                    <th class="banner-right-padding">件数</th>
                    <th class="banner-right-th">操作</th>
                    </thead>
                    <c:forEach items="${page.list}" var="ship" varStatus="vs">
                        <tr class="tr_css" align="center">
                            <td>${vs.count+(page.pageNumber-1)*page.pageSize}</td>
                            <td onclick="javascript:openDiv(${ship.ship_id})" style="color: #0A6CFF;">${ship.ship_sn}</td>
                            <td>${ship.netWorkName}</td>
                            <td><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>${ship.ship_customer_number}</td>
                            <td>${ship.senderName}</td>
                            <td>${ship.receiverName}</td>
                            <td>${ship.fromAdd}</td>
                            <td>${ship.toAdd}</td>
                            <c:if test="${ship.ship_status==1}">
                                <td>出库中</td>
                            </c:if>
                            <c:if test="${ship.ship_status==2}">
                                <td>运输中</td>
                            </c:if>
                            <c:if test="${ship.ship_status==3}">
                                <td>运输中</td>
                            </c:if>
                            <c:if test="${ship.ship_status==4}">
                                <td>已签收</td>
                            </c:if>
                            <td>${ship.ship_volume}</td>
                            <td>${ship.ship_weight}</td>
                            <td  class="banner-right-padding">${ship.ship_amount}</td>
                            <td class="banner-right-th" >
                             <a href="#" onclick="choose(${ship.ship_id})">选择</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div id="page" style="text-align: center;">
            </div>
        </div>

</div>

<script>
    //托运方
    $('#senderId').combobox({
        url: '${ctx}/kd/customer/queryCustomer?type=2&flag=true',
        valueField: 'customer_id',
        textField: 'customer_name',
        value:'${model.senderId}',
        height:30,
        panelWidth: 300,
        panelHeight: 'auto',
    });

    //收货方
    $('#receiverId').combobox({
        url: '${ctx}/kd/customer/queryCustomer?type=1&flag=true',
        valueField: 'customer_id',
        textField: 'customer_name',
        value:'${model.receiverId}',
        height:30,
        panelWidth: 300,
        panelHeight: 'auto',
    });

    
    //分页
    layui.use(['laypage'], function(){
        var laypage = layui.laypage;
        //调用分页
        laypage({
        	cont: 'page'
            ,pages: '${page.totalPage}' //得到总页数
            ,curr:'${page.pageNumber}'
            ,skip: true //是否开启跳页 
            ,count:'${page.totalRow}'
            ,jump: function(obj, first){
                if(!first){
                    window.location.href="${ctx}/kd/abnormal/chooseShip?pageNo="+obj.curr+"&netWorkId="+$("#netWorkId").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&shipSn="+$("#shipSn").val()+"&shipSate="+$("#shipSate").val()+"&senderId="+$("#senderId").val()+"&receiverId="+$("#receiverId").val()+"&fromCode="+$("#fromCode").val()+"&toCode="+$("#toCode").val()+"&customerNumber="+$("#customerNumber").val();
                }
            }
            ,skin: '#1E9FFF'
        });
    });
   
    function choose(shipId){
    	
    	parent.$("#chooseShipId").val(shipId);
    	parent.layer.closeAll();
    }


</script>

</body>

</html>