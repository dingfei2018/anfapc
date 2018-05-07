<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>首页</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/translater.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/js/ichart.1.2.1.min.js"></script>
		<script src="${ctx}/static/kd/js/echarts.common.min.js"/></script>
		<script src="${ctx}/static/common/js/ichart.1.2.1.min.js"></script>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
	    <%@ include file="common/head2.jsp" %>
		<%@ include file="common/head.jsp" %>
		<div class="banner">
			
			<div class="banner-list">
				<div class="banner-right-list3">
					<ul>
					   <li><a href="${ctx}/pages/kd/index.jsp">操作流程</a></li>
					   <li><a href="${ctx}/pages/kd/indexr.jsp" class="active">财务中心</a></li>
					</ul>
				</div>
				<div class="banner-list-tir">
				         <div class="banner-list-tirs"><p>运单审核</p></div>
				         <img src="${ctx}/static/kd/img/ionc-o.png"/>
				</div>
				<div class="banner-list-ti">
				     <p>应收结算</p>
				     <div class="banner-list-tis">
				           <ul>
				              <li><a href="#">现付</a></li>
				              <li><a href="#">提付</a></li>
				              <li><a href="#">回单付</a></li>
				              <li><a href="#">月结</a></li>
				              <li><a href="#">短欠</a></li>
				              <li><a href="#">贷款扣</a></li>
				              <li><a href="#">异动增加</a></li>
				           </ul>
				     </div>
				</div>
				<div class="banner-list-ti">
				     <p>应付结算</p>
				     <div class="banner-list-tis">
				           <ul>
				              <li><a href="#">提货费</a></li>
				              <li><a href="#">送货费</a></li>
				              <li><a href="#">短驳费</a></li>
				              <li><a href="#">中转费</a></li>
				              <li><a href="#">回扣</a></li>
				              <li><a href="#">现付运输费</a></li>
				              <li><a href="#">现付油卡费</a></li>
				              <li><a href="#">回付运输费</a></li>
				              <li><a href="#">整车保险费</a></li>
				              <li><a href="#">发站其他费</a></li>
				              <li><a href="#">到付其他费</a></li>
				              <li><a href="#">到付运输费</a></li>
				              <li><a href="#">到站卸车费</a></li>
				              <li><a href="#">到站其他费</a></li>
				              <li><a href="#">异动减款</a></li>
				           </ul>
				     </div>
				</div>
				<div class="banner-list-ti">
				     <p>贷款管理</p>
				     <div class="banner-list-tis">
				           <ul>
				              <li><a href="#">贷款回收</a></li>
				              <li><a href="#">贷款汇款</a></li>
				              <li><a href="#">贷款到账</a></li>
				              <li><a href="#">贷款发放</a></li>
				           </ul>
				     </div>
				</div>
				<div class="banner-list-ti">
				     <p>网点对账</p>
				     <div class="banner-list-tis">
				           <ul>
				              <li><a href="#">提付应收</a></li>
				              <li><a href="#">操作应收</a></li>
				              <li><a href="#">提付应收</a></li>
				              <li><a href="#">操作应收</a></li>
				           </ul>
				     </div>
				</div>
				<div class="banner-list-tir" style="float: right;">
				          <img style="left:0px;" src="${ctx}/static/kd/img/ionc-o.png"/>
				         <div style="margin-left:45px;" class="banner-list-tirs"><p>收支流水</p></div>
				</div>
			</div>
		</div>
		<%@ include file="common/loginfoot.jsp" %>
	 <script type="text/javascript">
	</script>  
	</body>
</html>
