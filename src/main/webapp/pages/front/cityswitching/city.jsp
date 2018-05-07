<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>选择城市</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/cyti.css?v=${version}" />
		<link href="http://www.jq22.com/jquery/bootstrap-3.3.4.css" rel="stylesheet">
		
	    <script src="http://www.jq22.com/jquery/1.11.1/jquery.min.js"></script>
	<%-- 	<script type="text/javascript" src="${ctx }/static/pc/js/jquery.js"></script> --%>
		<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
        
      <script src="http://www.jq22.com/jquery/bootstrap-3.3.4.js"></script>
		<script src="${ctx}/static/pc/Distp/js/distpicker.data.js"></script>
		  <script src="${ctx}/static/pc/Distp/js/distpicker.js"></script>
		  <script src="${ctx}/static/pc/Distp/js/main.js"></script>
        
	</head>
	<body>
		<%@ include file="../common/loginhead.jsp" %>
		
		<!-- 城市切换页面 -->
		<div class="content">
		
		   <div class="content-top">
		       <span>按省份选择：</span>
			    <form class="form-inline" action="${ctx}/toCity" method="post">
			      <div data-toggle="distpicker">
			        <div class="form-group">
			          <label class="sr-only" for="province7">Province</label>
			          <select class="form-control" id="province7"></select>
			        </div>
			        <div class="form-group">
			          <label class="sr-only" for="city7">City</label>
			          <select class="form-control" id="city7" name="cde"></select>
			          <script type="text/javascript">
			             $(function(){
			            	 $("#province7").css("width","150");
			            	 $("#city7").css("width","188")
			             })
			          </script>
			        </div>
			      </div>
				  <input type="submit" value="确定">
			    </form>
				     
		   </div>
      		  
      	   <div class="content-banner">
      	       <span>常用城市：</span>
      	       <ul class="ul1">
      	          <li><a href="${ctx}/toCity?cde=110100">北京</a></li>
      	          <li><a href="${ctx}/toCity?cde=310100">上海</a></li>
      	          <li><a href="${ctx}/toCity?cde=440100">广州</a></li>
      	          <li><a href="${ctx}/toCity?cde=440300">深圳</a></li>
      	          <li><a href="${ctx}/toCity?cde=120100">天津</a></li>
      	          <li><a href="${ctx}/toCity?cde=500100">重庆</a></li>
      	          <li><a href="${ctx}/toCity?cde=420100">武汉</a></li>
      	          <li><a href="${ctx}/toCity?cde=320100">南京</a></li>
      	          <li><a class="content-list" href="${ctx}/toCity?cde=340200">芜湖</a></li>
      	          <li><a href="${ctx}/toCity?cde=430100">长沙</a></li>
      	       </ul>
      	       <ul class="ul2">
      	          <li><a href="${ctx}/toCity?cde=330100">杭州</a></li>
      	          <li><a href="${ctx}/toCity?cde=320500">苏州</a></li>
      	          <li><a href="${ctx}/toCity?cde=210100">沈阳</a></li>
      	          <li><a href="${ctx}/toCity?cde=370100">济南</a></li>
      	          <li><a href="${ctx}/toCity?cde=610100">西安</a></li>
      	          <li><a href="${ctx}/toCity?cde=510100">成都</a></li>
      	          <li><a href="${ctx}/toCity?cde=210200">大连</a></li>
      	          <li><a href="${ctx}/toCity?cde=650121">乌鲁木齐</a></li>
      	          <li><a href="${ctx}/toCity?cde=350200">厦门</a></li>
      	          <li><a href="${ctx}/toCity?cde=340100">合肥</a></li>
      	       </ul>
      	   </div>	  
      		  
      	   <div class="content-footer">
      	       <img src="${ctx }/static/pc/img/cityselect.jpg"/>
      	       <c:forEach items="${regions}" var="region" varStatus="v">
      	            <c:if test="${not empty has && key!=region.key_letter}">
      	            	 </ul>
	      	       		</div>
	      	       		<c:remove var="has"/>
	      	       		<c:remove var="key"/>
      	            </c:if>
      	            <c:if test="${empty has}">
      	            	<c:set var="has" value="1"/>
      	            </c:if>
      	            <c:if test="${key!=region.key_letter}">
      	            	<c:set var="key" value="${region.key_letter}"/>
      	            	<div class="content-gun">
	      	          	  <input type="button" value="${fn:toUpperCase(region.key_letter)}"/>
		      	          <ul>
      	            </c:if>
      	            <c:if test="${empty key || key==region.key_letter}">
		      	          <li><a href="${ctx}/toCity?cde=${region.region_code}">${region.region_name}</a></li>
      	            </c:if>
      	            </c:forEach>
      	       			</ul>
	      	       	</div>
      	   </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>