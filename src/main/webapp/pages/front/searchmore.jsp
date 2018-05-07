<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>

		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/showmore.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css"/>
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
	</head>
	<body>
		 <!--头部-->
		<%@ include file="common/loginhead.jsp" %>
		<!--中间详细的内容--->
		<div class="content">
		<form action="${ctx}/front/line/listpage" method="post" id="myform">
			<div class="content-left">
				<ul>
					<li class="content-lefts" id="feedback">好评率<img src="${ctx}/static/pc/img/arrow_downs.png"/></li>
					<li id="credit">诚信指数<img src="${ctx}/static/pc/img/arrow_downs.png"/></li>
					<li id="price_heavy">重货价/公斤<img src="${ctx}/static/pc/img/arrow_downs.png"/></li>
					<li id="price_small">轻货价/立方<img src="${ctx}/static/pc/img/arrow_downs.png"/></li>
					<li class="content-leftf" id="frequency">发车频率<img src="${ctx}/static/pc/img/arrow_downs.png"/></li>
				</ul>
			</div>
			<div class="content-right citys" id="location">
				<span id="endId">到达地：
				    <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select></span>
                     <script type="text/javascript">
                     $("#endId").region({domain:"${ctx}"});
		            </script>
		             <input type="hidden" id="toRegionCode" name="toRegionCode" >
		              <input type="hidden" id="addRegionCode" name="addRegionCode" >
				<span id="addId">物流地址:
				 <select name="province"></select>
                    <select name="city"></select>
                    <select name="area"></select></span>
                     <script type="text/javascript">
                     $("#addId").region({domain:"${ctx}"});
		            </script>
				<a href="#"><img src="${ctx}/static/pc/img/icon_search.png" onclick="search();" /></a>
			</div>
			</form>
			<div class="content-centent">
				<table class="content-table" cellspacing="0">
					<tr>
						<th class="content-th">出发地</th>
						<th class="content-th">到达地</th>
						<th class="content-th">公司名称</th>
						<!-- <th class="content-th">好评率</th>
						<th class="content-th">诚信指数</th> -->
						<th class="content-th">所在物流园</th>
						<th class="content-th">重货价/公斤</th>
						<th class="content-th">轻货价/立方</th>
						<th class="content-th">发车频次</th>
						<th class="content-th">操作</th>
					</tr>
					<c:forEach items="${page.list}" var="line">
					<tr>
						<td class="content-td">${line.from_addr}</td>
						<td class="content-td">${line.to_addr}</td>
						<td class="content-td"><a href="${ctx}/company?id=${line.company_id}">${line.corpname}</a></td>
						<%-- <td class="content-td">${line.feedback}%</td>
						<td class="content-td">${line.credit}</td> --%>
						<td class="content-td"><a href="${ctx}/front/line/getLineByParkId?parkId=${line.logistics_park_id}">${line.parkname}</a></td>
						<td class="content-td">${line.price_heavy}元/公斤</td>
						<td class="content-td">${line.price_small}元/立方</td>
						<td class="content-td">${line.frequency}次/天</td>
						<td class="content-td"><a href="${ctx}/front/goods?orderid=${line.id}&fromregioncode=${line.from_region_code}&toregioncode=${line.to_region_code}">发货</a></td>
					</tr>
				</c:forEach>
				</table>
				
				<div class="content-centento">
					<c:choose>
					<c:when test="${page.pageNumber<=1}">
					<button class="content-centent-button">< 上一页</button>
					</c:when>
					<c:otherwise>
					<button class="content-centent-button" onclick="javascript:previous();">< 上一页</button>	
					</c:otherwise>
					</c:choose>
					<ul>
					<c:forEach begin="1" end="${page.totalPage }" varStatus="page">
					<input type="hidden" name="index" id value="${page.index}">
						<li><a href="javascript:pagenumbernext(${page.index})">${page.index}</a></li>
						</c:forEach>
					</ul>
					<c:choose>
					<c:when test="${page.pageNumber==page.totalPage}">
					<button class="content-centent-buttons">下一页 ></button>&nbsp;&nbsp;
					</c:when>
					<c:otherwise>
						<button class="content-centent-buttons" onclick="javascript:next();">下一页 ></button>&nbsp;&nbsp;
					</c:otherwise>
					</c:choose>
					<span>共 ${page.totalPage} 页，到第 </span><input type="text" placeholder="2" id="pageNumber"/><span> 页</span>
					<button class="content-centent-buttont" onclick="javascript:hrefnext();">确定</button>
				</div>
			</div>
		</div>
		<!--底部的内容--->
		<%@ include file="common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		function search(){
			var earea = $("#endId").find("select[name=area]").val();
			var addarea = $("#addId").find("select[name=area]").val();
			if(earea==null||earea==""){
				alert("请选择要查找的目的地");
				return;
			}
			if(addarea==null||addarea==""){
				alert("请选择要查找物流地址");
				return;
			}
			$("#toRegionCode").val(earea);
			$("#addRegionCode").val(addarea);
		    document.getElementById("myform").submit();  
			}
		  $("li").click(function(e){
		    	var orderby=($(this).attr('id'));
			    var toRegionCode="${toRegionCode}";
			    var addRegionCode="${addRegionCode}";
			    window.location.href="${ctx}/front/line/listpage?toRegionCode="+toRegionCode+"&orderby="+orderby+"&addRegionCode="+addRegionCode;	
		    })
	    <!---上一页-->
		function previous() {
			var toRegionCode = '${toRegionCode}';
			 var addRegionCode="${addRegionCode}";
			var pageNumber = "${page.pageNumber-1}";
			window.location.href = "${ctx}/front/line/listpage?pageNumber="
					+ pageNumber
					+ "&toRegionCode="
					+ toRegionCode
					+ "&addRegionCode=" +addRegionCode;
		}
		<!-- - 数字分页-->
		function pagenumbernext(index) {
			var toRegionCode = '${toRegionCode}';
			 var addRegionCode="${addRegionCode}";
			window.location.href = "${ctx }/front/line/listpage?pageNumber="
					+ index
					+ "&toRegionCode="
					+ toRegionCode
					+ "&addRegionCode=" +addRegionCode;
		}
		<!-- - 下一页-->
		function next() {
			var toRegionCode = '${toRegionCode}';
			var addRegionCode="${addRegionCode}";
			var pageNumber = "${page.pageNumber+1}";
			window.location.href = "${ctx }/front/line/listpage?pageNumber="
					+ pageNumber
					+ "&toRegionCode="
					+ toRegionCode
					+ "&addRegionCode=" + addRegionCode;
		}

		<!-- - 跳转-->
		function hrefnext() {
			var toRegionCode = '${toRegionCode}';
			var addRegionCode="${addRegionCode}";
			var pageNumber = $("#pageNumber").val();
			window.location.href = "${ctx}/front/line/listpage?pageNumber="
					+ pageNumber
					+ "&toRegionCode="
					+ toRegionCode
					+ "&addRegionCode=" + addRegionCode;
		}
	</script>
	</body>
</html>
