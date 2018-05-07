<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
        <base href="${ctx }/">
		<title>网上物流首页</title>
		<script src="${ctx }/static/pc/study/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/utils.js"></script>
		<script type="text/javascript">
			function locationTo(){
				if(getCookie("anfaCity")){
					window.location.href="${ctx}/";
				}
				$.ajax({
				    url:"http://restapi.amap.com/v3/ip?key=8325164e247e15eea68b59e89200988b",
				    type:'GET',
				    timeout : 1000,
				    dataType:"json",
				    success:function(data){
				    	//console.log(data.province+data.city);
				    	if(data.infocode=1000){
				    		window.location.href="${ctx}/s?province="+encodeURI(encodeURI(data.province))+"&city="+encodeURI(encodeURI(data.city));
				    	}else{
				    		window.location.href="${ctx}/s?province="+encodeURI(encodeURI("广东省"))+"&city="+encodeURI(encodeURI("广州市"));
				    	}
			        },
			        error : function(tests){
				    	window.location.href="${ctx}/s?province="+encodeURI(encodeURI("广东省"))+"&city="+encodeURI(encodeURI("广州市"));
				    }
				});
			}
			locationTo();
		</script>
	</head>
	<body>
</body>
</html>

