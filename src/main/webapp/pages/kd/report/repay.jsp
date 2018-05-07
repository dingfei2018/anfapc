<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>应付应收总表</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/yfyszb.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp"%>
</head>

<body>
<!-- 头部文件 -->
<%@ include file="../common/head2.jsp"%>

<%@ include file="../common/head.jsp"%>

<div class="banner">
    <!-- 左边菜单 -->
    <%@ include file="../common/reportleft.jsp"%>
    <script type="text/javascript">
        $(function() {
            var _width = $("body").width();
            var _widths = $(".banner-left").width();
            var _widthd = _width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width', _widthd + 'px');
        });
        $(window).resize(function() {
            var Width = $(window).width();
            var _widths = $(".banner-left").width();
            var _widthd = Width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width', _widthd + 'px');
            console.log(_widthd)
        })
    </script>
    <div class="banner-right">

        <form id="searchForm" onsubmit="return false;">
            <div class="banner-right-list">
                <div class="div">
                    <span class="span">开单网点：</span>
                    <select name="networkId">
                        <option value="">请选择</option>
                        <c:forEach var="work" items="${netWorkList}">
                            <option value="${work.id}">${work.sub_network_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="div">
                    <span class="span">月份：</span> <input type="text" value="${nowMonth}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" id="startTime" class="Wdate" style="margin-left:-3px;"/>
                </div>
                <div class="div">
                    <span class="spanc">至</span> <input type="text" value="${nowMonth}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" id="endTime" class="Wdate" style="margin-left:30px;"/>
                </div>
                <div class="div">
                    <button onclick="init();">查询</button>
                    <input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
                </div>
            </div>
        </form>


        <div class="banner-right-list2">
            <ul class="ul2">
                <li style="border: 1px solid #3974f8;"><a id="excelExport" class="banner-right-a3"
                                                          style="color: #3974f8; background: #fff; border: none;">导出EXCEL</a>
                </li>
                <li style="float: right;margin-right: 10px;">
                    <div id="page" style="text-align: center;"></div>
                </li>
            </ul>

            <div style="overflow: auto; width: 100%;" id="loadingId">
                <table border="0" class="tab_css_1" id="table"
                       style="border-collapse: collapse;">
                    <thead>
                    <th colspan="2">费用类型</th>
                    <th>现付</th>
                    <th>提付</th>
                    <th>回单付</th>
                    <th>月结</th>
                    <th>短欠</th>
                    <th>贷款扣</th>
                    <th>异动增加</th>
                    <th>异动减款</th>
                    <th>回扣</th>
                    <th>提货费</th>
                    <th>短驳费</th>
                    <th>送货费</th>
                    <th>中转费</th>
                    <th>现付运输费</th>
                    <th>现付油卡费</th>
                    <th>回付运输费</th>
                    <th>到付运输费</th>
                    <th>整车保险费</th>
                    <th>发站装车费</th>
                    <th>发站其他费</th>
                    <th>到站卸车费</th>
                    <th>到站其他费</th>
                    </thead>
                    <tr>
                        <td rowspan="4">应收</td>
                        <td>数量</td>
                        <td id="xf1"></td>
                        <td id="tf1"></td>
                        <td id="hdf1"></td>
                        <td id="yj1"></td>
                        <td id="dq1"></td>
                        <td id="dkk1"></td>
                        <td id="ydzj1"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>应收合计</td>
                        <td id="xf2"></td>
                        <td id="tf2"></td>
                        <td id="hdf2"></td>
                        <td id="yj2"></td>
                        <td id="dq2"></td>
                        <td id="dkk2"></td>
                        <td id="ydzj2"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>已收</td>
                        <td id="xf3"></td>
                        <td id="tf3"></td>
                        <td id="hdf3"></td>
                        <td id="yj3"></td>
                        <td id="dq3"></td>
                        <td id="dkk3"></td>
                        <td id="ydzj3"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>未收</td>
                        <td id="xf4"></td>
                        <td id="tf4"></td>
                        <td id="hdf4"></td>
                        <td id="yj4"></td>
                        <td id="dq4"></td>
                        <td id="dkk4"></td>
                        <td id="ydzj4"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td rowspan="4">应付</td>
                        <td>数量</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td id="ydjk1"></td>
                        <td id="hk1"></td>
                        <td id="thf1"></td>
                        <td id="dbf1"></td>
                        <td id="shf1"></td>
                        <td id="zzf1"></td>
                        <td id="xfysf1"></td>
                        <td id="xfykf1"></td>
                        <td id="hfysf1"></td>
                        <td id="dfysf1"></td>
                        <td id="zcbxf1"></td>
                        <td id="fzzcf1"></td>
                        <td id="fzqtf1"></td>
                        <td id="dzxcf1"></td>
                        <td id="dzqtf1"></td>
                    </tr>
                    <tr>
                        <td>应付合计</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td id="ydjk2"></td>
                        <td id="hk2"></td>
                        <td id="thf2"></td>
                        <td id="dbf2"></td>
                        <td id="shf2"></td>
                        <td id="zzf2"></td>
                        <td id="xfysf2"></td>
                        <td id="xfykf2"></td>
                        <td id="hfysf2"></td>
                        <td id="dfysf2"></td>
                        <td id="zcbxf2"></td>
                        <td id="fzzcf2"></td>
                        <td id="fzqtf2"></td>
                        <td id="dzxcf2"></td>
                        <td id="dzqtf2"></td>
                    </tr>
                    <tr>
                        <td>已付</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td id="ydjk3"></td>
                        <td id="hk3"></td>
                        <td id="thf3"></td>
                        <td id="dbf3"></td>
                        <td id="shf3"></td>
                        <td id="zzf3"></td>
                        <td id="xfysf3"></td>
                        <td id="xfykf3"></td>
                        <td id="hfysf3"></td>
                        <td id="dfysf3"></td>
                        <td id="zcbxf3"></td>
                        <td id="fzzcf3"></td>
                        <td id="fzqtf3"></td>
                        <td id="dzxcf3"></td>
                        <td id="dzqtf3"></td>
                    </tr>
                    <tr>
                        <td>未付</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td id="ydjk4"></td>
                        <td id="hk4"></td>
                        <td id="thf4"></td>
                        <td id="dbf4"></td>
                        <td id="shf4"></td>
                        <td id="zzf4"></td>
                        <td id="xfysf4"></td>
                        <td id="xfykf4"></td>
                        <td id="hfysf4"></td>
                        <td id="dfysf4"></td>
                        <td id="zcbxf4"></td>
                        <td id="fzzcf4"></td>
                        <td id="fzqtf4"></td>
                        <td id="dzxcf4"></td>
                        <td id="dzqtf4"></td>
                    </tr>

                </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/loginfoot.jsp"%>
<script src="${ctx}/static/kd/js/report/repay.js?v=${version}"></script>
</body>
</html>