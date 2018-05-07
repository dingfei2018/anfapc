<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>改动记录</title>
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
				   <a href="/kd/waybill/shipChangeInfo?shipId=${shipId}"  class="activet  at" >改单记录</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/transactionInfo?shipId=${shipId}"  class="at" >异动记录</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/receiptInfo?shipId=${shipId}" class="at" >回单日志</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/abnormalInfo?shipId=${shipId}" class="at" >异常记录</a>
			   </li>
		   </ul>
	       	</div>
       <div class="banner-right-list">
       		<table>
       			<tr>
       				<th>序号</th>
       				<th>运单号</th>
       				<th>登记网点</th>
       				<th>开单时间</th>
       				<th>托运方</th>
       				<th>收货方</th>
       				<th>修改人</th>
       				<th>修改时间</th>
       				<th>修改内容</th>
       			</tr>
				<c:if test="${fn:length(page.list)>0}">
					<c:forEach items="${page.list}" var="change" varStatus="vs">
						<tr>
							<td>${vs.index+1}</td>
							<td>${change.ship_sn}</td>
							<td>${change.netWork}</td>
							<td>${change.shipTime}</td>
							<td>${change.shipperName}</td>
							<td>${change.receivingName}</td>
							<td>${change.userName}</td>
							<td>${change.updateTime}</td>
							<td>${change.update_content}</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
       </div>
    </div>
</div>

<%@ include file="../common/loginfoot.jsp" %>
</body>
</html>