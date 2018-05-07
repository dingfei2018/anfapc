<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运作明细表</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/operation.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp" %>
    <script src="${ctx}/static/kd/js/operation.js?v=${version}"></script>
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
				<a href="${ctx }/kd/report/reSummary" class="at">应收汇总表</a>
			</li>
			<li>
				<a href="${ctx }/kd/report/paySummary" class="at">应付汇总表</a>
			</li>
			<li>
				<a href="${ctx}/kd/report/operationList" class="activet at">运作明细表</a>
			</li>
			<li>
				<a href="${ctx }/kd/report/preProfit" class="at">毛利汇总表</a>
			</li>
        </ul>
        <form id="searchFrom" onsubmit="return false;">
        <div class="banner-right-list">
          <div class="div"> 
            <span class="span">开单网点：</span>
            <select name="netWorkId" id="netWorkId">
                <option value="">请选择</option>
               <c:forEach var="work" items="${networks}">
                <option value="${work.id}" >${work.sub_network_name}</option>
                </c:forEach>
            </select>
           </div>
           <div class="div">
            <span class="span">开单日期：</span> <input type="text" class="Wdate" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
         </div>
          <div class="div">
            <span class="spanc">至</span><input type="text" class="Wdate"  id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
           </div>        
            <div class="div">
            <span class="span">出发地：</span>
            <div class="banner-right-list-liopc">
                
                <div class="form-group">
                    <div style="position: relative;">
                        <input type="hidden" id="toCode" name="toCode">
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
            <div class="banner-right-list-liopd">
                
                <div class="form-group">
                    <div style="position: relative;">
                        <input type="hidden" id="fromCode" name="fromCode">
                        <input id="city-picker2" class="form-control"
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
            <span class="span">托运方：</span><input type="text" name="senderId" id="senderId" />
             </div>
             <div class="div">
            <span class="span">收货方：</span><input type="text" name="receiveId" id="receiveId" />
          </div>
            <div class="div">
            	<span class="span">客户单号：</span><input type="text" id="customerNumber" name="customerNumber"/>
           </div>
             <div class="div">
            <span class="span">运单号：</span><input type="text" id="shipSn" name="shipSn"  />
           </div>
           <div class="div">
            <button id="search">查询</button>
               <input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
           </div>
           
        </div>
        </form>

        <div class="banner-right-list2">
        <a href="javascript:void(0)" id="excelExport" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
            <div id="page" style="text-align: center;">
            </div>
            <p class="banner-right-p">运作明细表</p>
            <div style="overflow: auto; width: 100%;  " id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <tr> 
                        <th colspan="13"  class="banner-right-border">运单信息</th>
                        <th colspan="8"  class="banner-right-border">应收</th>
                        <th colspan="4"  class="banner-right-border">提货</th>
                        <th colspan="4"  class="banner-right-border">短驳</th>
                        <th colspan="4"  class="banner-right-border">干线</th>
                        <th colspan="4"  class="banner-right-border">送货</th>
                        <th colspan="3"  class="banner-right-border">中转</th>
                        <th colspan="2"  class="banner-right-border">签收</th>
                        <th colspan="2"  class="banner-right-border">毛利</th>
                    </tr>
                    <tr>
                        <th>序号</th>
                        <th>运单号</th>
                        <th>开单网点</th>
                        <th>客户单号</th>
                        <th>开单日期</th>
                        <th>托运方</th>
                        <th>收货方</th>
                        <th>出发地</th>
                        <th>到达地</th>
                        <th>体积</th>
                        <th>重量</th>
                        <th>件数</th>
                        <th>代收货款</th>
                        <th>付款方式</th>
                        <th>合计</th>
                        <th>运费</th>
                        <th>提货费</th>
                        <th>送货费</th>
                        <th>保费</th>
                        <th>包装费</th>
                        <th>其他费</th>
                        <th>车牌号</th>
                        <th>发车日期</th>
                        <th>司机</th>
                        <th>运费</th>
                        <th>车牌号</th>
                        <th>发车日期</th>
                        <th>司机</th>
                        <th>运费</th>
                        <th>车牌号</th>
                        <th>发车日期</th>
                        <th>司机</th>
                        <th>运费</th>
                        <th>车牌号</th>
                        <th>发车日期</th>
                        <th>司机</th>
                        <th>运费</th>
                        <th>中转方</th>
                        <th>中转联系人</th>
                        <th>中转日期</th>
                        <th>中转费</th>
                        <th>签收人</th>
                        <th>签收日期</th>
                        <th>毛利</th>
                        <th>毛利率</th>
                    </tr>
                    </thead>
                    <c:forEach items="${page.list}" var="ship" varStatus="vs">
                    <tr class="tr_css" align="center">
                        <td>${vs.index+1}</td>
                        <td><a href="javascript:openDiv(${ship.ship_id})" class="btn">${ship.ship_sn}</a></td>
                        <td>${ship.netWorkName}</td>
                        <td>${ship.ship_customer_number}</td>
                        <td><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>${ship.senderName}</td>
                        <td>${ship.receiverName}</td>
                        <td>${ship.fromAdd}</td>
                        <td>${ship.toAdd}</td>
                        <td>${ship.ship_volume}</td>
                        <td>${ship.ship_weight}</td>
                        <td>${ship.ship_amount}</td>
                        <td>${ship.ship_agency_fund}</td>
                        <c:if test="${ship.ship_pay_way==1}">
                            <td style="color: #ff7801;">现付</td>
                        </c:if>
                        <c:if test="${ship.ship_pay_way==2}">
                            <td style="color: #ff7801;">提付</td>
                        </c:if>
                        <c:if test="${ship.ship_pay_way==3}">
                            <td style="color: #ff7801;">到付</td>
                        </c:if>
                        <c:if test="${ship.ship_pay_way==4}">
                            <td style="color: #ff7801;">回单付</td>
                        </c:if>
                        <c:if test="${ship.ship_pay_way==5}">
                            <td style="color: #ff7801;">月付</td>
                        </c:if>
                        <td style="color: #ff7801;">${ship.ship_total_fee}</td>
                        <td>${ship.ship_fee}</td>
                        <td>${ship.ship_pickup_fee}</td>
                        <td>${ship.ship_delivery_fee}</td>
                        <td>${ship.ship_insurance_fee}</td>
                        <td>${ship.ship_package_fee}</td>
                        <td>${ship.ship_addon_fee}</td>
                        <c:forEach items="${fn:split(ship.thFee, ',')}" var="s">
                        	<c:if test="${fn:endsWith(s, 'types1')}">
                        		<c:set var="temp">${fn:replace(s, 'types1', '')}</c:set>
                        		<c:if test="${!empty th}">
                        			<c:set var="th">${th}<br>${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        		<c:if test="${empty th}">
                        			<c:set var="th">${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        	</c:if>
                        	<c:if test="${fn:endsWith(s, 'types2')}">
                        		<c:set var="temp">${fn:replace(s, 'types2', '')}</c:set>
                        		<c:if test="${!empty db}">
                        			<c:set var="db">${db}<br>${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        		<c:if test="${empty db}">
                        			<c:set var="db">${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        	
                        	</c:if>
                        	<c:if test="${fn:endsWith(s, 'types3')}">
                        		<c:set var="temp">${fn:replace(s, 'types3', '')}</c:set>
                        		<c:if test="${!empty gx}">
                        			<c:set var="gx">${gx}<br>${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        		<c:if test="${empty gx}">
                        			<c:set var="gx">${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        	
                        	</c:if>
                        	<c:if test="${fn:endsWith(s, 'types4')}">
                        		<c:set var="temp">${fn:replace(s, 'types4', '')}</c:set>
                        		<c:if test="${!empty sh}">
                        			<c:set var="sh">${sh}<br>${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        		<c:if test="${empty sh}">
                        			<c:set var="sh">${fn:replace(temp, '|', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')}</c:set>
                        		</c:if>
                        	</c:if>
                        </c:forEach>
                        <td colspan="4">${th}</td>
                        <td colspan="4">${db}</td>
                        <td colspan="4">${gx}</td>
                        <td colspan="4">${sh}</td>
                        <td>${ship.corpName}</td>
                        <td>${ship.customerName}</td>
                        <td>${ship.ship_transfer_time}</td>
                        <td>${ship.ship_transfer_fee}</td>
                        <td>${ship.sign_person}</td>
                        <td>${ship.sign_time}</td>
                        <td>${ship.profit}</td>
                        <td>${ship.rate}%</td>
                    </tr>
                    <c:set var="th"></c:set>
                    <c:set var="db"></c:set>
                    <c:set var="gx"></c:set>
                    <c:set var="sh"></c:set>
                    </c:forEach>
                </table>

            </div>

            
        </div>

    </div>
</div>

</body>

</html>
