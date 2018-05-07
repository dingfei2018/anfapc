<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>货款到账</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/hkdz.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<%@ include file="../../common/commonhead.jsp" %>
</head>
<body>
<%@ include file="../../common/head2.jsp"%>
<%@ include file="../../common/head.jsp"%>
<div class="banner">
	<%@ include file="../../common/fahuoleft.jsp"%>
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
        });
	</script>
	<div class="banner-right">

		<ul>
			<li>
				<a href="${ctx }/kd/finance/collect" class="activet at">我的货款</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/collect/collectLoan" class="at">代收货款</a>
			</li>
		</ul>
		<form onsubmit="return false;" id="confirmform">
			<div class="banner-right-list">
				<p class="banner-right-p">货款到账</p>
				<div class="div">
					<span class="span">收支方式：</span>
					<select id="payType" name="payType">
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
					<span class="span">收支账号：</span>
					<input type="text" id="flowNo" name="flowNo" />
				</div>
				<div class="div">
					<span class="span">交易凭证号：</span>
					<input type="text" id="voucherNo" name="voucherNo" />
				</div>
				<div class="div">
					<span class="span">备注：</span>
					<input type="text" id="remark" name="remark"  />
				</div>

			</div>
		</form>

		<div class="banner-right-list2">
			<div class="banner-right-list2-left">
				<div class="banner-right-list2-lefts" >
					<img src="${ctx}/static/kd/img/youce.png" id="loadId"/>
				</div>			
				<div class="div">
					<span class="span">到货网点：</span>
					<select id="loadNetWorkId" name="loadNetWorkId">
						<option value="">请选择 </option>
						<c:forEach var="work" items="${networks}">
							<option value="${work.id}">${work.sub_network_name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="div">
					<span class="span">托运方：</span>
					<input type="text" id="senderId" name="senderId" />
				</div>
				<div class="div">
					<span class="span">收货方：</span>
					<input  type="text" id="receiverId" name="receiverId" />
				</div>
				<div class="div">
					<input type="button" class="button" id="search" value="查询">
					<!-- <input type="button" class="button" onclick="resetCity();" value="重置"> -->
				</div>

			</div>
			<div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId">
				<table border="1" class="tab_css_1" id="shiplistId">
					<thead>
					<th>全选 <input type="checkbox" class="selectAll"></th>
					<th>序号</th>
					<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" class="shipfilter"  types="1"></div></th>
					<th>开单网点</th>
					<th>到货网点</th>
					<th>代收货款</th>
					<th>托运方</th>
					<th>收货方</th>
					<th>货号</th>
					<th>开单日期</th>
					</thead>
				</table>
			</div>
			<p class="p" id="querylistId"></p>
		</div>
		<div class="banner-right-list3">
			<div class="banner-right-list3-left">
				<div class="banner-right-list3-lefts" >
					<img src="${ctx}/static/kd/img/zuoce.png" id="rmloadId"/>
				</div>
				<input type="button" class="button" id="daozhang"  value="确认货款到账">
			</div>
			<div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId2">
				<table border="1" class="tab_css_1"  id="addshiplistId">
					<thead>
					<th>全选 <input type="checkbox" class="selectAll"></th>
					<th>序号</th>
					<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" class="shipfilter" types="2"></div></th>
					<th>开单网点</th>
					<th>到货网点</th>
					<th>代收货款</th>
					<th>托运方</th>
					<th>收货方</th>
					<th>货号</th>
					<th>开单日期</th>
					</thead>
				</table>
			</div>
			<p class="p" id="loadlistId"></p>
		</div>

	</div>
</div>
<%@ include file="../../common/loginfoot.jsp"%>
<script src="${ctx}/static/kd/js/collect/hkdz.js"></script>
</body>
</html>