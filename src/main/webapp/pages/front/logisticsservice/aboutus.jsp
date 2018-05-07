<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>关于安发</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/Contactus.css"/>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
	</head>
	<body>
    <!---头部的内容--->
	<%@ include file="../common/loginhead.jsp" %>

		<!---联系我们的内容--->
		<div class="content">
			<div class="content-left">
				<div class="content-left-banner">
					<p class="content-left-title">关于我们</p>
					 <img class="content-left-logo" src="${ctx }/static/pc/img/icon_11.png" >
					 <img class="content-left-bg" src="${ctx }/static/pc/img/icon_12.png" >
					<ul>
						<li class="content-left-main"><a class="content-left-main" href="${ctx }/front/logistics/aboutus">关于安发</a></li>
						<li><a href="${ctx }/front/logistics/joinus">加入我们</a></li>	
						<li><a href="${ctx }/front/logistics/commonproblems">常见问题</a></li>
						<li><a href="${ctx }/front/logistics/contactus">联系我们</a></li>				
					</ul>
					 <b class="content-left-bottom"></b>
				</div>
			</div>
				<div class="content-right">
					<div class="content-right-main">
						<p class="content-right-page">关于安发</p>
						<div class="content-right-page1">	
							<span>公司简介：</span>
							<p>安发网络科技（广州）有限公司(简称：安发网络)是中国供应链服务生态系的参与者，主要通过互联网、移动互联网、物联网、云计算和大数据等先进技术，将工厂、商贸企业、个人等物流需求方与千万家专线、城配、车队等中小微物流供应方连接互动，依托线下物流产业园，实现物流规模效应和数据共享经济，为供应链各方参与者创造价值。</p>
							<p>安发网络的经营理念是通过旗下品牌 ANFA 云平台，提高内部管理和物流服务水平，降低运营成本，打造一个高效、开放和良性发展的供应链服务生态体系，满足各方参与者的需求。公司本着“让物流更生态”的使命，安发网络致力于供应链结构效率的提升并为供应链生态系统各方参与者创造价值。</p>
							<span>企业文化：</span>
							<ul>
								<li>目 标： 中国最具价值的供应链服务生态系统建设者</li>
								<li>经营理念：共生、共享、共赢、共发展</li>
								<li>使命愿景：致力于供应链结构效率的提升，致力于为供应链生态系统参与者创造价值</li>
								<li>品牌立场：中小型物流企业代言人，供应链生态建设者和服务者。</li>
								<li>核心价值：无忧服务、共同发展，良性供应链生态</li>
								<li>价值观：诚信为本、勇于创新、开放公平、激情付出、怀抱感恩</li>
								<li>工作作风：结果导向、嫉慢如仇、团队合作、拥抱变化</li>
								<li>利益优先排序：客户第一、员工第二、股东第三</li>
								<li>使 命： 让物流更生态</li>
							</ul>
							<span>旗下品牌介绍 ：</span>
							<p>网上物流：ANFA 云平台是安发网络旗下专注于互联网物流020交易平台，该平台为客户提供全流程供应链管理方案。客户通过 网上物流交易020平台可以绑定发货方和具体承运方，实时监控货运运输状态。通过承运方APP和货源方APP，管理人员可以实时查看所有运营报表和异常情况，省心省力。网上物流可以实现所有客户的各项定制化服务，满足客户的个性化需求。</p>
						    <p>安财：安财是安发网络旗下品牌，是为供应链生态系统参与各方提供必要的金融和保险解决方案。安财通过 ANFA 云平台的数据沉淀，让数据有价，为供应链系统参与各方提供相应的资金使用授信。参与各方可以有效利用自己的授信，拓展业务，降低运营成本，获得更高的利润。</p>
						    <p>安智：安智是安发网络旗下基于 ANFA 云平台数据积累的智慧成果输出平台，为供应链生态系统参与各方提供商业决策指导和管理提升解决方案。安智是 ANFA 云平台和安财的衔接，同时又是数据价值的体现，通过分析定期输出中观和宏观数据，为供应链系统参与者提供决策依据。</p>
						</div>
				</div>
			</div>
		</div>
		
		<!--底部的内容--->
<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
