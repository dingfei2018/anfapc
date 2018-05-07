<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>司机信息</title>
	<link rel="stylesheet" href="${ctx}/static/pc/Issuedgoods/css/Issued.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css"/>
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css"/>
	<script src="${ctx}/static/pc/js/jquery.js"></script>
	<script src="${ctx}/static/pc/js/unslider.min.js"></script>
	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
</head>
<body>
	<!--头部-->
	<%@ include file="../personal/common/head.jsp" %>
		
		<!---中间内容的部分--->
		<div class="content">
			<div class="content-top">
				<p>承运方基本信息</p>
			</div>
			<div class="content-left">
				<ul>
					<li>星级：*****</li>
					<li>司机姓名：刘伟</li>
					<li>车牌号：粤A25869</li>
					<li><a href="javascript:void(0);" onclick="show(this)" data="${ctx}/static/pc/img/image_行驶证.png">查看行驶证</a></li>
				</ul>
			</div>
			<div class="content-content">
				<ul>
					<li></li>
					<li>车辆属性：个体司机</li>
					<li>车长：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;157米</li>
					<li><a href="javascript:void(0);" onclick="show(this)"  data="${ctx}/static/pc/img/image_驾驶证.png">查看驾驶证</a></li>
				</ul>
			</div>
			<div class="content-right">
				<div class="content-img">
					<img src="${ctx}/static/pc/img/image_人车合影.png" />
				</div>
				<a href="javascript:void(0);" onclick="show(this)"  data="${ctx}/static/pc/img/image_人车合影.png">查看人车合影</a>
			</div>
		</div>
		
	<!--底部的内容--->
	<%@ include file="../common/loginfoot.jsp" %>
	<script type="text/javascript">
		function getImageWidth(url,callback){
			var img = new Image();
			img.src = url;
			// 如果图片被缓存，则直接返回缓存数据
			if(img.complete){
			    callback(img.width);
			}else{
		     // 完全加载完毕的事件
			    img.onload = function(){
					callback(img.width);
			    }
		    }
		}
	
		function show(obj){
			var path = $(obj).attr("data");
		 	getImageWidth(path,function(rwidth){
			 	layer.open({
		  		  type: 1,
		  		  title: false,
		  		  closeBtn: 0,
		  		  area: rwidth+'px',
		  		  skin: 'layui-layer-nobg', //没有背景色
		  		  shadeClose: true,
		  		  content: "<img src='"+path+"'/>"
		  		});
		    });
		}
	</script>
</body>
</html>
