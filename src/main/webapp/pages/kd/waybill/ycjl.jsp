<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>异常记录</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/czrz.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
	<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
<%@ include file="../common/head2.jsp" %>
<%@ include file="../common/head.jsp" %>
<div class="banner">
	<%@ include file="../common/fahuoleft.jsp" %>
	<script type="text/javascript">
        $(function(){
            var _width=$("body").width();
            var _widths = $(".banner-left").width();
            var _widthd = _width - _widths - 80;
            parseInt(_widthd);
            $('.banner-right').css('width',_widthd+'px');
        });
        $(window).resize(function(){
            var Width = $(window).width();
            var _widths = $(".banner-left").width();
            var _widthd = Width - _widths - 80;
            parseInt(_widthd);
            $('.banner-right').css('width',_widthd+'px');
        });
	</script>
	<div class="banner-right">
		<div class="banner-right-ultitle" style="overflow: hidden;">
			<ul>
				<li>
					<a href="/kd/waybill/viewDetail?shipId=${shipId}" class="at">运单信息</a>
				</li>
				<li>
					<a href="/kd/waybill/shipLog?shipId=${shipId}" class="at" >操作日志</a>
				</li>
				<li>
					<a href="/kd/waybill/shipTransportInfo?shipId=${shipId}" class="at" >物流跟踪</a>
				</li>
				<li>
					<a href="/kd/waybill/shipChangeInfo?shipId=${shipId}"  class="at" >改单记录</a>
				</li>
				<li>
					<a href="/kd/waybill/transactionInfo?shipId=${shipId}"  class="at" >异动记录</a>
				</li>
				<li>
					<a href="/kd/waybill/receiptInfo?shipId=${shipId}" class="at" >回单日志</a>
				</li>
				<li>
					<a href="/kd/waybill/abnormalInfo?shipId=${shipId}" class="activet at" >异常记录</a>
				</li>
			</ul>
		</div>
		<div class="banner-right-list">
			<table>
				<tr>
					<th>序号</th>
					<th>异常编号</th>
					<th>登记网点</th>
					<th>异常状态</th>
					<th>异常类型</th>
					<th>异常描述</th>
					<th>托运方</th>
					<th>收货方</th>
					<th>登记人</th>
					<th>登记时间</th>
				</tr>
				<c:forEach items="${abnormals}" var="abnormal" varStatus="vs">
				<tr>
					<td>${vs.count}</td>
					<td>${abnormal.abnormal_sn}</td>
					<td>${abnormal.netWorkName}</td>
					<c:if test="${abnormal.abnormal_status==0}">
					<td Style="color: #ff7801">待处理</td>
					</c:if>
					<c:if test="${abnormal.abnormal_status==1}">
						<td Style="color: #ff7801">处理中</td>
					</c:if><c:if test="${abnormal.abnormal_status==2}">
					<td Style="color: #ff7801">已处理</td>
				</c:if>
					<c:if test="${abnormal.abnormal_type==0}">
					<td>货损</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==1}">
						<td>少货</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==2}">
						<td>多货</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==3}">
						<td>货物丢失</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==4}">
						<td>货单不符</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==5}">
						<td>超重超方</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==6}">
						<td>超时</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==7}">
						<td>拒收</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==8}">
						<td>投诉</td>
					</c:if>
					<c:if test="${abnormal.abnormal_type==9}">
						<td>其他</td>
					</c:if>
					<td>${abnormal.senderName}</td>
					<td>${abnormal.receiverName}</td>
					<td>${abnormal.username}</td>
					<td>><fmt:formatDate value="${track.create_time}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
				</tr>
				</c:forEach>
			</table>

		</div>

	</div>
</div>

<%@ include file="../common/loginfoot.jsp" %>
</body>
</html>