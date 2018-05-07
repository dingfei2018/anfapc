<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8" %>
<%@ include file="/resources/includes/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>到车汇总</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/handle.css?v=${version}">
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css"/>
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css"/>
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css"/>
    <%@ include file="../../common/commonhead.jsp" %>
    <script src="${ctx}/static/kd/js/payable/ToSum.js?v=${version}"></script>
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
                <a href="#" class="actives at">到车汇总</a>
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
                <a href="${ctx}/kd/finance/payable/transfer" class="at">中转费</a>
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
                    <span class="span">到货网点：</span>
                    <select name="loadToNetworkId">
                        <option value="0">请选择网点</option>
                        <c:forEach items="${tonetWorks}" var="net">
                            <option value="${net.id}">${net.sub_network_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="div">
                    <span class="span">配载网点：</span>
                    <select name="networkId">
                        <option value="0">请选择网点</option>
                        <c:forEach items="${networks}" var="net">
                            <option value="${net.id}">${net.sub_network_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="div">
                    <span class="span">到车日期：</span><input type="text" name="time"
                                                          onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                </div>
                <div class="div">
                    <span class="span">至：</span><input type="text" name="endTime"
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                </div>
                <div class="div">
                    <span class="span">车牌号：</span><input type="text" name="truckNumber"/>
                </div>
                <div class="div">
                    <span class="span">司机：</span><input type="text" name="driverName"/>
                </div>

                <div class="div">
                    <span class="span">配载单号：</span><input type="text" name="loadSn"/>
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
                    <a href="#" class="banner-right-a3" id="excelExport"
                       style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
                </li>
                <li>
                    <div id="page" style="text-align: center;"></div>
                </li>
            </ul>
            <p class="banner-right-p">发车汇总列表</p>
            <div style="overflow: auto; width: 100%;  " id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <th>序号</th>
                    <th>配载单号</th>
                    <th>到货网点</th>
                    <th>配载网点</th>
                    <th>配载状态</th>
                    <th>发车日期</th>
                    <th>到车日期</th>
                    <th>车牌号</th>
                    <th>司机</th>
                    <th>司机电话</th>
                    <th>费用合计</th>
                    <th>到付运输费</th>
                    <th>未结到付运输费</th>
                    <th>到站卸车费</th>
                    <th>未结到站卸车费</th>
                    <th>到站其他费</th>
                    <th>未结到站其他费</th>
                    </thead>
                </table>


            </div>

        </div>

    </div>
</div>

<%@ include file="../../common/loginfoot.jsp" %>
</body>
</html>
