<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>fff</title>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/muticity.js"></script>
	</head>
	<body>
        <select id="province" style="width: 63px; height: 25px;">
        </select>
        <input id="cityId" type="text" style="height: 21px; border: 1px solid silver; border-left:none; position: relative; margin-left: -5px;" />
		 <script type="text/javascript">
		      $("#province").mutiRegion({cityField:"cityId"});
		 </script>
	</body>
</html>
