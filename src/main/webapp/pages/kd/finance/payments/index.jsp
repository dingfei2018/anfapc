<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>收支流水</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/payments.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
	  <%@ include file="../../common/commonhead.jsp" %>
	<script src="${ctx}/static/kd/js/transport/soon.js"></script>
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
        });
	</script>
	<div class="banner-right">
		<ul>
			<li>
				<a href="#" class="activet at">收支流水</a>
			</li>
			<li>
				<a href="#" class=" at">收支明细</a>
			</li>
		</ul>

		<div class="banner-right-list">
			<ul>
				<form  name="searchForm" onsubmit="return false">
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
						<span class="span">结算日期（改）：</span><input style="margin-left:0px;" type="text" class="Wdate" name="startTime" id="startTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>
					<div class="div">
						<span class="spanc">至</span><input  type="text" class="Wdate" id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>
					<div class="div">
						<span class="span">结算类型：</span>
						<select >
							
						</select>
					</div>
					<div class="div">
						<span class="span">收支方向 ：</span><select></select>
					</div>
					<div class="div">
						<span class="span">收支方式 ：</span><select></select>
					</div>
					<div class="div">
						<span class="span">流水结算号：</span><select></select>
					</div>
					<div class="div">
						<button id="search">查询</button>
					<%--	<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>--%>
					</div>
				</form>
			</ul>
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
			<!-- <p class="banner-right-p">到货列表</p> -->
			<div style="overflow: auto; width: 100%;  " id="loadingId">
				<table border="0" sclass="tab_css_1" style="border-collapse:collapse;" id="loadId">
					<thead>
					<th>流水结算号</th>
					<th>开单网点</th>
					<th>结算类型</th>
					<th>结算方向</th>
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
<%@ include file="../../common/loginfoot.jsp" %>
</body>

</html>