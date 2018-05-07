<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>即将到货</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/soon.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
	<%@ include file="../common/commonhead.jsp" %>
</head>

<body>
<!-- 头部文件 -->

<%@ include file="../common/head2.jsp" %>
<%@ include file="../common/head.jsp" %>

<div class="banner">
	<!-- 左边菜单 -->
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
        });
	</script>
	<div class="banner-right">
		<ul>
			<li>
				<a href="${ctx}/kd/transport" class="activet at">即将到货</a>
			</li>
			<li>
				<a href="/kd/transport/arrive" class=" at">已到达</a>
			</li>
		</ul>

		<div class="banner-right-list">
				<form  name="searchForm" onsubmit="return false">
					<div class="div">
						<span class="span" style="padding-left: 7px;">到货网点：</span>
						<select name="nextNetworkId" id="nextNetworkId">
							<option value="">请选择 </option>
							<c:forEach var="work" items="${networks}">
								<option value="${work.id}" >${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span">配载网点：</span>
						<select name="networkId" id="networkId">
							<option value="">请选择 </option>
							<c:forEach var="work" items="${networks}">
								<option value="${work.id}">${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>

					<div class="div">
						<span class="span">出发地：</span>
						<div class="banner-right-list-liopd">

							<div class="form-group">
								<div style="position: relative;">
									<input type="hidden" name="deliveryFrom" id="deliveryFrom">
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
						<div class="banner-right-list-liopc">
							<div class="form-group">
								<div style="position: relative;">
									<input type="hidden" name="deliveryTo" id="deliveryTo">
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
						<span class="span">车牌号：</span><input type="text" name="truckNumber" id="truckNumber"  />
					</div>
					<div class="div">
						<span class="span">发车日期：</span><input style="margin-left:0px;" type="text" class="Wdate" name="startTime" id="startTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>
					<div class="div">
						<span class="spanc">至</span><input  type="text" class="Wdate" id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>
					<div class="div">
						<button id="search">查询</button>
					<%--	<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>--%>
					</div>
				</form>
		</div>

		<div class="banner-right-list2">
			<p class="banner-right-p">到货列表</p>
			<div style="overflow: auto; width: 100%;  " id="loadingId">
				<table border="0" sclass="tab_css_1" style="border-collapse:collapse;" id="loadId">
					<thead>
					<th>序号</th>
					<th>配载单号</th>
					<th>配载网点</th>
					<th>到货网点</th>
					<th>运输类型</th>
					<th>到付运输费</th>
					<th>发车日期</th>
					<th>车牌号</th>
					<th>司机</th>
					<th>司机电话</th>
					<th>出发地</th>
					<th>到达地</th>
					<th>体积</th>
					<th>重量</th>
					<th >件数</th>
					<th class="banner-right-th">操作</th>
					</thead>
				</table>
			</div>
			<div id="page" style="text-align: center;"></div>
		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp" %>
	<script src="${ctx}/static/kd/js/transport/soon.js"></script>
</body>

</html>