<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/checkorder.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
</head>
<body>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" class="white_content">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		   <div class="banner-right-list3">
              		<h1>签收照片</h1>
					<p class="banner-list3-title">
						签收照片信息
					</p>
					<div class="content-right-tem2" data="scrol">
						<span>车辆照片 :</span>
					<div class="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img" src="img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="img/add00.png">
				         <a href="javascript:;" id="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				   </div>
				</div><br />
				<button class="banner-right-list3-button">继续添加</button>
				<button class="banner-right-list3-push">提交</button>
				<a class="back" href="#">返回</a>					
               </div>
		   </div>
		  <div id="fade2" class="black_overlay2"></div>
		<div id="MyDiv2" class="white_content2">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		   <div class="banner-right-list4">
              		<h1>操作日志</h1>
					<div class="banner-list4-imfo">
						<ul>
							<li>
								<p>运单号：<span>YD00001</span></p>
								
							</li>
							<li>
								<p>开单日期：<span>2017-11-06</span></p>
								
							</li>
							<li>
								<p>出发地：<span>广州市白云区</span></p>
								
							</li>
							<li>
								<p>到达地：<span>保定市</span></p>
								
							</li>
							<li>
								<p>托运方：<span>广州新易泰</span></p>
								
							</li>
							<li>
								<p>收货方：<span>李四</span></p>
								
							</li>
							<li>
								<p>货物名称：<span>家具</span></p>
								
							</li>
							<li>
								<p>体积：<span>30立方</span></p>
								
							</li>
							<li>
								<p>重量：<span>23355公斤</span></p>
								
							</li>
							<li>
								<p>件数：<span>233</span></p>
								
							</li>
						</ul>					
					</div>
					<div class="banner-list4-log">
						<p>日志信息：</p>
							<table cellspacing="0" border="1">
								<tr>
									<th>操作人</th>
									<th>操作时间</th>
									<th>操作类型</th>
								</tr>
								<tr>
									<td>李四</td>
									<td>2017-11-12 13:00:00</td>
									<td>送货</td>
								</tr>
								<tr>
									<td>李四</td>
									<td>2017-11-12 13:00:00</td>
									<td>送货</td>
								</tr>
								<tr>
									<td>李四</td>
									<td>2017-11-12 13:00:00</td>
									<td>送货</td>
								</tr>
								<tr>
									<td>李四</td>
									<td>2017-11-12 13:00:00</td>
									<td>送货</td>
								</tr>
								<tr>
									<td>李四</td>
									<td>2017-11-12 13:00:00</td>
									<td>送货</td>
								</tr>
							</table>
					</div>
				<button class="banner-list4-button">关闭</button>
			
               </div>
          </div>
          <div id="fade3" class="black_overlay3"></div>
		<div id="MyDiv3" class="white_content3">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		   <div class="banner-right-list5">
              		<h1>运单信息</h1>
              		<p class="banner-list5-type">
              			运单状态：<span></span>
              		</p>	
					<div class="banner-list5-div1">
						<p class="banner-div-title">
							运单信息
						</p>
						<ul>
							<li>
								<p>运单号：<span>YD00001</span></p>
							</li>
							<li>
								<p>开单网点：<span>广州白云区</span></p>
							</li>
							<li>
								<p>开单日期：<span>2017-12-12</span></p>
							</li>
							<li>
								<p>客户单号：<span>E23121423</span></p>
							</li>
							<li>
								<p>送货方式：<span>自提</span></p>
							</li>
							<li>
								<p>代收货款：<span>2500</span>元</p>
							</li>
							<li>
								<p>备注：<span></span></p>
							</li>
						</ul>
					</div>
					<div class="banner-list5-div2">
						<ul class="banner-div2-title">
							<li>托运方：</li>
							<li>收货方：</li>
						</ul>
						<ul class="banner-div2-ul">
							<li>
								<p>姓名：<span>张三</span></p>
							</li>
							<li>
								<p>联系电话：<span>13812345678</span></p>
							</li>
							<li>
								<p>出发地：<span>广州市白云区</span></p>
							</li>
							<li>
								<p>详细地址：<span>石牌桥丰兴广场B座1804</span></p>
							</li>
						</ul>
						<ul class="banner-div2-ul2">
							<li>
								<p>姓名：<span>李四</span></p>
							</li>
							<li>
								<p>联系电话：<span>13812345678</span></p>
							</li>
							<li>
								<p>出发地：<span>广州市白云区</span></p>
							</li>
							<li>
								<p>详细地址：<span>石牌桥丰兴广场B座1804</span></p>
							</li>
						</ul>
						
					</div>	
					<div class="banner-list5-div3">
						<p class="banner-div-title">
							货物清单
						</p>
						<div class="banner-div3-table">
							<table border="0">
								<thead>
									<th>货物编码</th>
									<th>货物名称</th>
									<th>包装单位</th>
									<th>件数</th>
									<th>体积</th>
									<th>重量</th>
									<th>货值</th>
								</thead>
								<tr>
									<td>HE1233123123</td>
									<td>化妆品</td>
									<td>箱</td>
									<td>10</td>
									<td>25立方</td>
									<td>1250公斤</td>
									<td>25000</td>
								</tr>
								<tr>
									<td>HE1233123123</td>
									<td>化妆品</td>
									<td>箱</td>
									<td>10</td>
									<td>25立方</td>
									<td>1250公斤</td>
									<td>25000</td>
								</tr>
							</table>
						</div>							
					</div>
					<div class="banner-list5-div4">
						<p class="banner-div-title">
							运费
						</p>
						<ul>
							<li>
								<p>运费：<span>525</span>元</p>
							</li>
							<li>
								<p>保价费：<span></span>元</p>
							</li>
							<li>
								<p>提货费：<span>2502</span>元</p>
							</li>
							<li>
								<p>包装费：<span></span>元</p>
							</li>
							<li>
								<p>送货费：<span></span>元</p>
							</li>
							<li>
								<p>其他费用：<span>2500</span>元</p>
							</li>
							<li>
								<p>合计：<span>4500</span>元</p>
							</li>
							<li>
								<p>付款方式：<span>现付</span></p>
							</li>
						</ul>
					</div>
					<br />				
               </div>
		   </div>
</body>
</html>