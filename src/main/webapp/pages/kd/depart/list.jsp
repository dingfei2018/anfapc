<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>发车查询</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/departure.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<%@ include file="../common/commonhead.jsp" %>
	</head>
	<body>
	
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/fahuoleft.jsp" %>
			<div class="banner-right">
				<ul>
					<li>
						<a href="#">运单列表</a>
					</li>
					<li>
						<a href="#"  class="active">创建列表</a>
					</li>
				</ul>

				<div class="banner-right-list">
					<span>开单网点：</span>
					<select></select>
					<span>开单日期：</span><input type="text" />
					<span>运单号：</span><input type="text" />
					<span>运单状态：</span><input type="text" /><br />
					<span class="span2">托运方：</span><input type="text" />
					<span class="span2">收货方：</span><input type="text" />
					<span class="span3">出发地：</span>
					<select></select>
					<span class="span4">到达地：</span>
					<select></select><br />
					<span>客户单号：</span><input type="text" />
					<button>查询</button>
				</div>

				<div class="banner-right-list2">
					<p class="banner-right-p">客户列表</p>
					<div style="overflow: auto; width: 100%;">
						<table border="0" class="tab_css_1" style="border-collapse:collapse;">
							<thead>
								<th><input type="checkbox" style="display: none;"/></th>
								<th>序号</th>
								<th>配载单号</th>
								<th>配载网点</th>
								<th>发车日期</th>
								<th>车票号</th>
								<th>司机</th>
								<th>运输类型</th>
								<th>出发地</th>
								<th>到达地</th>
								<th>运单状态</th>
								<th>体积</th>
								<th>重量</th>
								<th>件数</th>
								<th class="banner-right-th">操作</th>
							</thead>
							<tr class="tr_css" align="center">
								<td><input type="checkbox"/></td>
								<td>1</td>
								<td>YD00001</td>
								<td>广州市白云区安发货运市场</td>
								<td>2017-11-06</td>
								<td>0s00011</td>
								<td>广州新易泰</td>
								<td>张三</td>
								<td>广州市白云区</td>
								<td>廊坊市</td>
								<td>在途中</td>
								<td>5</td>
								<td>2500</td>
								<td>10</td>
								<td class="banner-right-th"><img src="${ctx}/static/kd/img/r_icon5.png" /><span>附件</span>
								   <div class="banner-right-list2-tab2">
										<dl>
											<dd class="input2"><img src="${ctx}/static/kd/img/r_icon4.png" /><span>打印</span></dd>
										</dl>
									</div>
								</td>
							</tr>
							<tr class="tr_css" align="center">
								<td><input type="checkbox"/></td>
								<td>2</td>
								<td>YD00001</td>
								<td>广州市白云区安发货运市场</td>
								<td>2017-11-06</td>
								<td>000011</td>
								<td>广州新易泰</td>
								<td>张三</td>
								<td>广州市白云区</td>
								<td>廊坊市</td>
								<td>在途中</td>
								<td>5</td>
								<td>2500</td>
								<td>10</td>
								<td class="banner-right-th"><img src="${ctx}/static/kd/img/r_icon5.png" /><span>附件</span>
								   <div class="banner-right-list2-tab2">
										<dl>
											<dd class="input2"><img src="${ctx}/static/kd/img/r_icon4.png" /><span>打印</span></dd>
										</dl>
									</div>
								</td>
							</tr>
							<tr class="tr_css" align="center">
								<td><input type="checkbox"/></td>
								<td>3</td>
								<td>YD00001</td>
								<td>广州市白云区安发货运市场</td>
								<td>2017-11-06</td>
								<td>000011</td>
								<td>广州新易泰</td>
								<td>张三</td>
								<td>广州市白云区</td>
								<td>廊坊市</td>
								<td>在途中</td>
								<td>5</td>
								<td>2500</td>
								<td>10</td>
								<td class="banner-right-th"><img src="${ctx}/static/kd/img/r_icon5.png" /><span>附件</span>
								   <div class="banner-right-list2-tab2">
										<dl>
											<dd class="input2"><img src="${ctx}/static/kd/img/r_icon4.png" /><span>打印</span></dd>
										</dl>
									</div>
								</td>
							</tr>
							
							<tr class="tr_css" align="center">
								<td><input type="checkbox"/></td>
								<td>4</td>
								<td>YD00001</td>
								<td>广州市白云区安发货运市场</td>
								<td>2017-11-06</td>
								<td>000011</td>
								<td>广州新易泰</td>
								<td>张三</td>
								<td>广州市白云区</td>
								<td>廊坊市</td>
								<td>在途中</td>
								<td>5</td>
								<td>2500</td>
								<td>10</td>
								<td class="banner-right-th"><img src="${ctx}/static/kd/img/r_icon5.png" /><span>附件</span>
								    <div class="banner-right-list2-tab2">
										<dl>
											<dd class="input2"><img src="${ctx}/static/kd/img/r_icon4.png" /><span>打印</span></dd>
												<script>
												/*$(function(){
														$(".input2").click(function(){
															DivUtils.ShowDiv('MyDiv','fade','附件');
															//$(".input2").extend().ShowDiv('MyDiv','fade');
														})
													});*/
												</script>
										</dl>
									</div>
								</td>
							</tr>
							<tr class="tr_css" align="center">
								<td><input type="checkbox"/></td>
								<td>5</td>
								<td>YD00001</td>
								<td>广州市白云区安发货运市场</td>
								<td>2017-11-06</td>
								<td>000011</td>
								<td>广州新易泰</td>
								<td>张三</td>
								<td>广州市白云区</td>
								<td>廊坊市</td>
								<td>在途中</td>
								<td>5</td>
								<td>2500</td>
								<td>10</td>
								<td class="banner-right-th"><img src="${ctx}/static/kd/img/r_icon5.png" /><span>附件</span>
									<script>
									$(function(){
											$(".banner-right-th").click(function(){
												DivUtils.ShowDiv('MyDivc','fadec','附件');
												//$(".input2").extend().ShowDiv('MyDiv','fade');
											})
										});
									</script>
								    <div class="banner-right-list2-tab2">
										<dl>
											<dd class="input2"><img src="${ctx}/static/kd/img/r_icon4.png" /><span>打印</span></dd>
										</dl>
									</div>
								</td>
							</tr>
						</table>
						
					</div>
					<ul class="ul2">
						<li><input type="checkbox" />全选</li>
						<li>
							<a href="#" class="banner-right-a3" id="btn">更换车辆</a>
							<script>
							$(function(){
									$("#btn").click(function(){
										DivUtils.ShowDiv('MyDiv','fade','更换车辆');
										//$(".input2").extend().ShowDiv('MyDiv','fade');
									})
								});
							</script>
						</li>
						<li>
							<a href="#" class="banner-right-a3" id="btns">修改到货网点</a>
							<script>
							$(function(){
									$("#btns").click(function(){
										DivUtils.ShowDiv('MyDivs','fades','修改到货网点');
										//$(".input2").extend().ShowDiv('MyDiv','fade');
									})
								});
							</script>
						</li>
						<li>
							<a href="#" class="banner-right-a3">配载加单</a>
						</li>
						<li>
							<a href="#" class="banner-right-a3">配载减单</a>
						</li>
						<li>
							<a href="#" class="banner-right-a3">删除配载</a>
						</li>
						<li>
							<a href="#" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCLE</a>
						</li>
					</ul>
				</div>

			</div>
		</div>
		
		
		<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" class="white_content">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		   <div class="tab-list">
		   	 <p>配载单号 : PZ201711200001</p>
		     <table>
		     	<tr>
		     		<td>原司机 : 刘发林</td>
		     		<td>原司机电话 : 13480255463</td>
		     		<td>原车牌号 : 粤SB6666</td>
		     	</tr>
		     	
		     </table>
		   </div>
		   
		   <div class="tab-list2">
		   	   <p>更换信息 :</p>
		   	    <span class="span">司机 : </span><input type="text" />
		   	    <span>司机电话 : </span><input type="text" />
		   	    <span>车牌号 : </span><input type="text" />
		   </div>
		   
		   <button>提交</button>
		</div>
		
		
		<div id="fades" class="black_overlay"></div>
		<div id="MyDivs" class="white_content">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		   <div class="tab-list">
		   	 <p>配载单号 : PZ201711200001</p>
		     <table>
		     	<tr>
		     		<td>配载网点 : 广州市白云区安发货运市场</td>
		     		<td>发车日期 : 2017-11-06</td>
		     		<td>出发地 : 广州市天河区石牌桥</td>
		     	</tr>
		     	<tr>
		     		<td>运输类型 : 干线</td>
		     		<td>司机 : 刘发林</td>
		     		<td>司机电话 : 13480255463</td>
		     	</tr>
		     	<tr>
		     		<td>车牌号 : 粤SB6666</td>
		     		<td>发车日期 : 2017-11-06</td>
		     		<td>出发地 : 广州市天河区石牌桥</td>
		     	</tr>
		     	<tr>
		     		<td>总件数 : 158</td>
		     		<td>总体积 : 1888888</td>
		     		<td>总重量 : 2500</td>
		     	</tr>
		     </table>
		   </div>
		   
		   <div class="tab-list2">
		   	   <p>更换信息 :</p>
		   	    <strong>原到货网点 : 长沙市望城区安发物流中心</strong><br />
		   	    <span class="span">司机 : </span><input type="text" />
		   	    <span>司机电话 : </span><input type="text" />
		   	    <span>车牌号 : </span><input type="text" />
		   </div>
		   
		   <button>提交</button> <a class="a" href="#">返回</a>
		</div>
		
		
		<div id="fadec" class="black_overlay"></div>
		<div id="MyDivc" class="white_content">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		    <div class="white_contentc">
		    <div class="content-right-tem2" data="scrol">
				<span>车辆照片 :</span>
				<div class="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
				         <a href="javascript:;" id="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				   </div>
			</div><br />
						
						
			 <div class="content-right-tem2" data="scrol" id="content-right-tem2">
				<div class="gtu1" id="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
				         <a href="javascript:;" id="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				   </div>
			</div>
			</div>
			<div class="white_contentcs"></div>
		   <button class="buttons" onclick="addtr()">继续添加</button>
		   <script>
				var fal = 1;
				function addtr(){
					if(fal == 1){
					   var tr = $("#content-right-tem2").clone();
					   tr.appendTo(".white_contentcs");	
					}
    				    fal = 0;
				   alert("已做了限制，要添加的话另外加")
				}
			</script>
		   <button class="button">提交</button> 
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
