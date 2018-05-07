<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8" %>
<%@ include file="/resources/includes/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>中转费</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/trains.css?v=${version}">
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css"/>
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css"/>
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css"/>
    <%@ include file="../../common/commonhead.jsp" %>
    <script src="${ctx}/static/kd/js/payable/transferpay.js?v=${version}"></script>
</head>
<body>
<%@ include file="../../common/head2.jsp" %>
<%@ include file="../../common/head.jsp" %>
<div class="banner">
    <%@ include file="../../common/financialleft.jsp" %>
    <script type="text/javascript">
        $(function () {
            var _width = $("body").width();
            var _widths = $(".banner-left").width();
            var _widthd = _width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width', _widthd + 'px');
        });
        $(window).resize(function () {
            var Width = $(window).width();
            var _widths = $(".banner-left").width();
            var _widthd = Width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width', _widthd + 'px');
        });
    </script>
    <div class="banner-right">
        <ul>
            <li>
                <a href="${ctx}/kd/finance/payable" class="at">发车汇总</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/toSum" class="at">到车汇总</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=1" class="at">提货费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=4" class="at">送货费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=2" class="at">短驳费</a>
            </li>
            <li>
                <a href="#" class="actives at">中转费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/rebate" class="at">回扣</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=1" class="at">现付运输费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=2" class="at">现付油卡费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=3" class="at">回付运输费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=4" class="at">整车保险费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=5" class="at">发站装车费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=6" class="at">发站其他费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=7" class="at">到付运输费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?loadAtFeeFlag=0" class="at">到站卸车费</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/payable/loadTransportPage?loadAtFeeFlag=1" class="at">到站其他费</a>
            </li>
        </ul>
        <div class="banner-right-list">
            <form id="searchFrom" onsubmit="return false;">
                <div class="div">
                    <span class="span">中转网点：</span>
                    <select name="tranNetWorkId" id="tranNetWorkId">
                        <option value="0">请选择网点 </option>
                        <c:forEach var="work" items="${userNetworks}">
                            <option value="${work.id}" <c:if test="${search.tranNetWorkId ==work.id}">selected</c:if>>${work.sub_network_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="div">
                    <span class="span">中转方：</span>
                    <input id="transferName" name="transferName" type="text" />
                </div>
                <div class="div">
                    <span class="span">中转单号：</span><input type="text" name="transferSn">
                </div>
                <div class="div">
                    <span class="span">中转日期：</span><input type="text" name="startTime"
                                                          onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                </div>
                <div class="div">
                    <span class="span">至：</span><input type="text" name="endTime"
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                </div>
                
                <div class="div">
                    <span class="span">托运方：</span><input type="text" name="senderId"/>
                </div>
                
                <div class="div">
                    <span class="span">收货方：</span><input type="text" name="receiverId"/>
                </div>
                
                <div class="div">
                    <span class="span">出发地：</span>
                    <div class="banner-right-list-liopc">

                        <div class="form-group">
                            <div style="position: relative;">
                                <input type="hidden" name="fromAddCode">
                                <input id="city-picker3" class="form-control"
                                       placeholder="请选择省/市/区" readonly type="text"
                                       data-toggle="city-picker">
                            </div>
                            <script>
                                $(function () {
                                    $(".city-picker-span").css("width", "auto");
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
                                <input type="hidden" name="toAddCode">
                                <input id="city-picker2" class="form-control"
                                       placeholder="请选择省/市/区" readonly type="text"
                                       data-toggle="city-picker">
                            </div>
                            <script>
                                $(function () {
                                    $(".city-picker-span").css("width", "auto");
                                })
                            </script>
                        </div>
                    </div>
                </div>


                <div class="div">
                    <span class="span">结算状态：</span>
                    <select name="state">
                        <option value="">请选择</option>
                        <option value="0">未结算</option>
                        <option value="1">已结算</option>
                    </select>
                </div>
                
                <div class="div">
                    <span class="span">运单号：</span><input type="text" name="shipSn"/>
                </div>
                <div class="div">
                    <button id="search">查询</button>
                    <input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
                </div>
            </form>

        </div>

        <div class="banner-right-list2">
            <ul class="ul2">
	            <li>
	                    <a href="/kd/finance/payable/toTransferSettlement" class="banner-right-a3" id="accountId" style="color:#3974f8; background: #fff;border: none;">结算</a>
	                </li>
                <li>
                    <a href="#" class="banner-right-a3" id="excelExport" style="color:#3974f8; background: #fff;border: none;">导出EXCEL</a>
                </li>
                <li  style="border: none;">
                    <div id="page" style="text-align: center;"></div>
                </li>
            </ul>
            <p class="banner-right-p" >中转费列表</p>
            <div style="overflow: auto; width: 100%;  " id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <th>序号</th>
                    <th>中转单号</th>
                    <th>中转网点</th>
                    <th>中转方</th>
                    <th>中转联系人</th>
                    <th>中转日期</th>
                    <th>结算状态</th>
                    <th>中转费</th>
                    <th>运单号</th>
                    <th>提付</th>
                    <th>代收货款</th>
                    <th>开单网点</th>
                    <th>出发地</th>
                    <th>到达地</th>
                    <th>托运方</th>
                    <th>收货方</th>
                    <th>中转体积</th>
                    <th>中转重量</th>
                    <th>中转件数</th>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../common/loginfoot.jsp" %>
</body>
</html>
