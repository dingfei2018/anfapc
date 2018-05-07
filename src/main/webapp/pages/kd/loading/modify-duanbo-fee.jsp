<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/changecost.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
	<div id="fade3" class="black_overlay3"></div>
	<div id="MyDiv3" class="white_content3">
	
		<div class="banner-right-list5">
			
			<p class="banner-list5-type">
				修改费用
			</p>
			<script>
	        document.onkeyup=function(event){
	      	  var e=event||window.event;
	      	  var keyCode=e.keyCode||e.which;
	      	  if(keyCode == 120){
	      		  alert("ddd");
	      	  }
	        }
	        </script>
			<div class="banner-list5-div1">
				<p class="banner-div-title">配载单号：${load.load_sn}</p>
				<ul>
					<li>
						<p>
							车牌号：<span>${load.truck_id_number}</span>
						</p>
					</li>
					<li>
						<p>
							司机：<span>${load.truck_driver_name}</span>
						</p>
					</li>
					<li>
						<p>
							司机电话：<span>${load.truck_driver_mobile}</span>
						</p>
					</li>
					<li>
						<p>
							配载网点：<span>${load.snetworkName}</span>
						</p>
					</li>
					<li>
						<p>
							到货网点：<span>${load.enetworkName}</span>
						</p>
					</li>
					<li>
						<p>
							出发地：<span>${load.fromCity}</span>
						</p>
					</li>
					<li>
						<p>
							到达地：<span>${load.toCity}</span>
						</p>
					</li>
					<li>
						<p>
							运输类型：<span>
							<c:if test="${load.load_transport_type==1}">
							提货
						</c:if>
						<c:if test="${load.load_transport_type==2}">
							短驳
						</c:if>
						<c:if test="${load.load_transport_type==3}">
							干线
						</c:if>
						<c:if test="${load.load_transport_type==4}">
							送货
						</c:if>
						</span>
						</p>
					</li>
					<li>
						<p>
							原配载费用：<span>${load.load_fee}</span>元
						</p>
					</li>
				</ul>
			</div>
			<div class="banner-list5-div2">
				<p class="banner-div-title">更换信息</p>
				<input type="hidden" name="type" value="2">
				<input type="hidden" name="loadId" value="${load.load_id}">
				<p>
					短驳费（元）：<input type="text" id="duanboId" name="loadfee" onkeyup="javascript:checkInputIntFloat(this);"/>
				</p>
			</div>

			<button class="banner-list5-up" onclick="save()">提交</button>
			<br />
		</div>
	</div>
</body>
<script type="text/javascript">
function save(){
	if($("#duanboId").val()==""){
		Anfa.show("请输入要修改的费用","#duanboId");
		return false;
	}
	var sdata = $(".banner-list5-div2").find("input,select,textarea").serialize();
	$.ajax({
		type:'POST',
		url:'/kd/loading/comfirmModifyFee',
		data:sdata,
		success:function(data){
			if (data.success) {
				layer.msg("修改成功");
				setTimeout(function(){parent.layer.closeAll();}, 1000);
			} else {
				layer.msg(data.msg);
			}
		}
	});
}
</script>
</html>