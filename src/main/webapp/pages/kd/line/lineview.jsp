<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/lineview.css" />
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
</head>
<body>
	
	<div id="fade3" class="black_overlay3"></div>
	<div id="MyDiv3" class="white_content3">
			<p class="banner-list5-type">
				专线详情
			</p>
			
			<div class="banner-list5-div1">
				<p class="banner-div-title">专线信息</p>
				<ul>
					<li>
						<p>
							出发地：${sysLine.fromAdd }
						</p>
					</li>
					<li>
						<p>
							到达地：${sysLine.toAdd }
						</p>
					</li>
					<li>
						<p>
							出发网点：${sysLine.networkName }
						</p>
					</li>
					<li style="clear: both;">
						<p>
							到达网点：${sysLine.arriveNetworkName }
						</p>
					</li>
					<li>
						<p>
							重货价格：${sysLine.price_heavy }元/公斤
						</p>
					</li>
					<li>
						<p>
							轻货价格：${sysLine.price_small }元/立方
						</p>
					</li>
					<li>
						<p>
							最低收费：${sysLine.starting_price }元
						</p>
					</li>
					
				</ul>
			</div>
			
			<br><br><br><br><br><br><br>
			<button class="banner-list5-div1-button" onclick="closeAll()">关闭</button>
			<br />
		</div>
	</div>
	<script type="text/javascript">
		function closeAll(){
			
			parent.layer.closeAll();
			
		}
	
	
	</script>

	
	
</body>
</html>