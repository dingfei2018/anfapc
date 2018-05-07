<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>常见问题</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/problem.css"/>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
	</head>
	<body>
		<!--头部-->
	    <%@ include file="../common/loginhead.jsp" %>
		
		<!---联系我们的内容--->
		<div class="content">
			<div class="content-left">
				<div class="content-left-banner">
					<p class="content-left-title">关于我们</p>
					 <img class="content-left-logo" src="${ctx }/static/pc/img/icon_11.png" >
					 <img class="content-left-bg" src="${ctx }/static/pc/img/icon_12.png" >
					<ul>
						<li><a href="${ctx }/front/logistics/aboutus">关于安发</a></li>
						<li><a href="${ctx }/front/logistics/joinus">加入我们</a></li>	
						<li class="content-left-main"><a  class="content-left-main" href="${ctx }/front/logistics/commonproblems">常见问题</a></li>
						<li><a href="${ctx }/front/logistics/contactus">联系我们</a></li>				
					</ul>
					 <b class="content-left-bottom"></b>
				</div>
			</div>
			<div class="content-right">
				<div class="content-right-main">
					 <p class="content-right-page">常见问题</p>
					<div class="content-right-ul">
					 <span>货主类</span>
					  <ul>
					  	<li class="content-right-li">—	注册货主平台需要提交什么资料？</li>
					  	<li>货主注册人需要确保注册信息的真实性，包括：a)姓名；b)公司名称；c)营业执照编码；d)人与身份证合影；e)营业执照；f)公司门头照</li>
					    <li class="content-right-li">—	平台有哪些货物不可以发货？</li>
					    <li>危险品（见禁运物品列表），冷冻物品，特种物品如：a)货物外形尺寸：长度在14米以上或宽度在3.5米以上或高度在3米以上的货物；b)重量在20吨以上的单体货物不可解体的成组（捆）货物</li>
					    <li class="content-right-li">—	我上传的照片你们会查真实性？</li>
					    <li>会的，如果您是企业用户我们会在相关网站核对信息，如果是个人用户，我们会审核您上传照片，核实信息匹配度。</li>
					    <li class="content-right-li">—	我能够使用网上物流发货吗？</li>
					    <li>欢迎您试用，网上物流适合工厂、专线公司、第三方物流公司、运输公司。</li>
					  </ul>
					  <span>车主类</span>
					  <ul>
					  	<li class="content-right-li">—	你们的APP收费吗？</li>
					  	<li>APP客户端是免费的</li>
					  	<li class="content-right-li">—	怎么收不到验证码？</li>
					  	<li>由于网络状况，短信偶尔会有延迟现象，请耐心等待，如较长时间仍然未收到，可重新获取验证码，或直接联系客服。</li>
					    <li class="content-right-li">—	忘记密码了怎么办？</li>
					    <li>请您在登录APP客户端点击“忘记密码”按照提示操作，将手机收到的验证码填好，就可以重置密码了。</li>
					    <li class="content-right-li">—	使用APP费流量么？费电么？</li>
					    <li>承运方APP开发过程中我们已进行严格测试，其流量消耗是很少的，若不使用时，则不产生流量。同时，电量消耗低。</li>
					    <li class="content-right-li">—	为什么注册要实名制？</li>
					    <li>网上物流为车主和货主双方提供具备诚信机制的环境。车主的身份资料可以体现您的真实性，是货品运输安全的基本保障。对于货主我们也有审核真实身份资料的要求，为交易双方做好保障。</li>
					    <li class="content-right-li">—	跟踪的轨迹是否会出现偏差？</li>
					    <li>GPS定位根据国家规定做位置处理，精确度较高，如果有稍微位移，均在国家标准允许范围内。</li>
					  </ul>
					  <span>服务类</span>
					  <ul>
					  	<li class="content-right-li">—	网上物流有客户电话吗？</li>
					  	<li>如果你们有问题可拨打客服电话进行咨询：<%=application.getAttribute("CUSTOMER.SERVICE") %></li>
					  </ul>
					 </div>
				</div>
			</div>
		</div>
		
		<!--底部的内容--->
<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
