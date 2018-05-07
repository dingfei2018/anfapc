<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>异动记录</title>
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
				   <a href="/kd/waybill/shipChangeInfo?shipId=${shipId}"  class=" at" >改单记录</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/transactionInfo?shipId=${shipId}"  class=" activet at" >异动记录</a>
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
       				<th>运单号</th>
       				<th>开单网点</th>
       				<th>托运方</th>
       				<th>到达地</th>
       				<th>开单时间</th>
       				<th>异动结算网点</th>
       				<th>异动增加</th>
       				<th>异动减款</th>
       				<th>异动结算状态</th>
       				<th>异动登记人</th>
       				<th>登记时间</th>
       			</tr>
       			<tr>
					<c:if test="${not empty shipAbnormal}">
       				<td>${shipAbnormal.ship_sn}</td>
       				<td>${shipAbnormal.netWorkName}</td>
       				<td>${shipAbnormal.senderName}</td>
       				<td>${shipAbnormal.toAdd}</td>
       				<td><fmt:formatDate value="${shipAbnormal.create_time}" pattern="yyyy-MM-dd HH:mm " type="date" /></td>
       				<td>${shipAbnormal.abnormalNetWorkName}</td>
       				<td Style="color: #ff7801">${shipAbnormal.plus_fee}</td>
       				<td Style="color: #ff7801">${shipAbnormal.minus_fee}</td>
					<c:if test="${shipAbnormal.fee_status==0}">
       				 <td Style="color: #ff7801">未结算</td>
					</c:if>
					<c:if test="${shipAbnormal.fee_status==1}">
						<td Style="color: #ff7801">已结算</td>
					</c:if>
       				<td>${shipAbnormal.userName}</td>
       				<td><fmt:formatDate value="${shipAbnormal.registerTime}" pattern="yyyy-MM-dd HH:mm " type="date" /></td>
       			</tr>
				</c:if>
       		</table>
       	
       </div>
       
    </div>
</div>

<%@ include file="../common/loginfoot.jsp" %>
    <script src="${ctx}/static/kd/js/waybill/waybill.js?v=${version}"></script>
<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>	
 <script src="${ctx}/static/kd/js/print/waybillprint.js"></script>
</body>
</html>