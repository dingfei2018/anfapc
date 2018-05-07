<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/sureview.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
</head>
<body>
	<div class="banner-sure">
		<h3>完成卸车</h3>
		<p>确认<span>${load.truck_id_number}</span>已完成卸车？</p>
		<div class="sure-button">
			<button class="button1" onclick="confirmUnload()">确定</button>
			<button class="button2"  style="cursor:pointer" onclick="parent.layer.closeAll()">取消</button>
		</div>
	</div>
<script>

	function confirmUnload () {
        $.ajax({
            url : "/kd/transport/confirmUnloading",
            type : 'POST',
            data : {loadId:"${load.load_id}",loadType:${load.load_transport_type}},
            success:function(data){
                if (data.success) {
                    layer.msg("卸车成功");
                    setTimeout(function(){parent.layer.closeAll();}, 1000);
                } else {
                    layer.msg(data.msg);
                }
            }
        });


    }


</script>
</body>
</html>