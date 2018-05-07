<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8" %>
<%@ include file="/resources/includes/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>收支流水</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/handle.css?v=${version}">
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css"/>
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css"/>
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css"/>
    <%@ include file="../../common/commonhead.jsp" %>
    <script src="${ctx}/static/kd/js/flow/flow.js?v=${version}"></script>
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
        })
    </script>
    <div class="banner-right">
        <ul>
            <li>
                <a href="#" class="actives at">收支流水</a>
            </li>
            <li>
                <a href="${ctx}/kd/finance/flow/detail" class="at">收支明细</a>
            </li>
        </ul>
        <div class="banner-right-list" style="margin-top:60px;">
            <form id="searchFrom" onsubmit="return false;">
                <div class="div">
                    <span class="span">网点：</span>
                    <select name="networkId">
                        <option value="">请选择网点</option>
                        <c:forEach items="${networks}" var="net">
                            <option value="${net.id}">${net.sub_network_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="div">
                    <span class="span">结算日期：</span><input type="text" name="startTime"
                                                          onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                </div>
                <div class="div">
                    <span class="span">至：</span><input type="text" name="endTime"
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                </div>
                <div class="div">
                    <span class="span">结算类型：</span>
                    <select name="settlementType">
                        <option value="">请选择</option>
                        <option value="0">应付结算</option>
                        <option value="1">应收结算</option>
                        <option value="2">贷款结算</option>
                        <option value="3">网点对账</option>
                    </select>
                </div>
                <div class="div">
                    <span class="span">收支方向：</span>
                    <select name="inoutType">
                        <option value="">请选择</option>
                        <option value="0">收入</option>
                        <option value="1">支出</option>
                    </select>
                </div>
                <div class="div">
                    <span class="span">收支方式：</span>
                    <select name="payType">
                        <option value="">请选择</option>
                        <option value="1">现金</option>
                        <option value="2">油卡</option>
                        <option value="3">支票</option>
                        <option value="4">银行卡</option>
                        <option value="5">微信</option>
                        <option value="6">支付宝</option>
                    </select>
                </div>


                <div class="div">
                    <span class="span">结算流水号：</span><input type="text" name="flowSn"/>
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
            <p class="banner-right-p">收支流水列表</p>
            <div style="overflow: auto; width: 100%;  " id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <th>序号</th>
                    <th>结算流水号</th>
                    <th>网点</th>
                    <th>结算类型</th>
                    <th>方向</th>
                    <th>金额</th>
                    <th>收入</th>
                    <th>支出</th>
                    <th>收支方式</th>
                    <th>收支账号</th>
                    <th>交易凭证号</th>
                    <th>结算时间</th>
                    <th>结算人</th>
                    <th>备注</th>
                    </thead>
                </table>


            </div>

        </div>

    </div>
</div>

<%@ include file="../../common/loginfoot.jsp" %>
</body>
</html>
