<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的货款</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/collection.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../../common/commonhead.jsp" %>
</head>

<body> 
<!-- 头部文件 -->
<%@ include file="../../common/head2.jsp" %>

<%@ include file="../../common/head.jsp" %>

<div class="banner">
    <!-- 左边菜单 -->
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
						<a href="${ctx}/kd/finance/collect" class="activet am">我的货款</a>
					</li>
					<li>
						<a href="${ctx}/kd/finance/collect/collectLoan" class="am">代收货款</a>
					</li>
				</ul>
        <form id="searchFrom" onsubmit="return false;">
            <div class="banner-right-list">
                <div class="div">
                        <span class="span">开单网点：</span>
                        <select name="netWorkId" id="netWorkId">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}" >${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                     <div class="div">
                        <span class="span">到货网点：</span>
                         <select name="nextNetWorkId" id="nextNetWorkId">
                         <option value="">请选择 </option>
                         <c:forEach var="work" items="${networks}">
                             <option value="${work.id}">${work.sub_network_name}</option>
                         </c:forEach>
                     </select>
                    </div>
                 <div class="div">
                        <span class="span">开单日期：</span> <input type="text" class="Wdate" name="startTime" id="startTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
                    </div>
                     <div class="div">
                        <span class="spanc">至</span><input   type="text" class="Wdate"  id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
                   </div>                 
                    <div class="div">
                        <span class="span">运单号：</span><input type="text" name="shipSn" id="shipSn"  />
                   </div>
                   <div class="div">
                        <span class="span">出发地：</span>
                        <div class="banner-right-list-liopc">

                            <div class="form-group">
                                <div style="position: relative;">
                                    <input type="hidden" name="fromCode" id="fromCode">
                                    <input id="city-picker3" class="form-control"  placeholder="请选择省/市/区" readonly type="text" data-toggle="city-picker">
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
                                    <input type="hidden" name="toCode" id="toCode">
                                    <input id="city-picker2" class="form-control" placeholder="请选择省/市/区" readonly type="text" data-toggle="city-picker">
                                </div>
                                <script>
                                    $(function(){
                                        $(".city-picker-span").css("width","auto");
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                      
                     <div class="div">
                        <span class="span">托运方：</span><input type="text" name="senderId"  id="senderId" />
                    </div>
                     <div class="div">
                        <span class="span">收货方：</span><input type="text" name="receiveId" id="receiveId"/>
                   </div>
                    <div class="div">
                        <span class="span">货款状态：</span>
                        <select name="fundStatus" id="fundStatus">
                            <option value="">请选择</option>
                            <option value="1">未回收</option>
                            <option value="2">已回收</option>
                            <option value="3">已汇款</option>
                            <option value="4">已到账</option>
                            <option value="5">已发放</option>
                        </select>
                    </div>
                     
                <div class="div">
                <button id="search">查询</button>
                <input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
				</div>
            </div>
        </form>

        <div class="banner-right-list2">
          <ul class="ul2">
                <li>
                    <a href="${ctx}/kd/finance/collect/moneyAccount" class="banner-right-a3 at" >货款到账</a>

                </li>  
                <li> 
                    <a href="${ctx}/kd/finance/collect/moneyGrant" class="banner-right-a3 at" >货款发放</a>
                </li> 
                <li>
                    <a href="jvascript:void(0)" id="excelExport" class="banner-right-a3 at" >导出EXCEL</a>

                </li>

                <div id="page" style="text-align: center;">
                </div>
            </ul>
            <p class="banner-right-p">我的货款列表</p>
            <div style="overflow: auto; width: 100%;" id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <th>序号</th>
                    <th>运单号</th>
                    <th>开单网点</th>
                    <th>货号</th>
                    <th>货款状态</th>
                    <th>代收货款(元)</th>
                    <th>贷款扣</th>
                    <th>到货网点</th>
                    <th>运单状态</th>
                    <th>开单日期</th>
                    <th>托运方</th>
                    <th>收货方</th>
                    <th>出发地</th>
                    <th>到达地</th>
                    <th>货物名称</th>                 
                    <th>体积</th>
                    <th>重量</th>
                    <th>件数</th>
                    </thead>
                </table>

            </div>
          
        </div>

    </div>
</div>

<%@ include file="../../common/loginfoot.jsp" %>
<script src="${ctx}/static/kd/js/collect/myloan.js?v=${version}"></script>
</body>

</html>
