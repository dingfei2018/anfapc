<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>打印页面</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/print.css" />
	</head>
	<body>
	    <div class="banner">
	        <h1>金鹰物流托运单</h1>
	        <p><b class="b1">托运日期</b> <span>2017/11/18</span> <b class="b2">运单号</b> <span>200 000 100 000 0010 </span></p>
	        <table cellspacing="0">
	            <tr>
	               <td rowspan="4">托运方</td>
	               <td>出发地</td>
	               <td colspan="3">广州市白云区</td>
	               <td rowspan="4">收货方</td>
	               <td>到达地</td>
	               <td>长沙市望城区</td>
	               <td>送货方式</td>
	               <td>自提</td>
	            </tr>
	            <tr>
	               <td>姓名</td>
	               <td colspan="3">张三</td>
	               <td>姓名</td>
	               <td colspan="3">李四</td>
	            </tr>
	            <tr>
	               <td>联系电话</td>
	               <td colspan="3">13588889999</td>
	               <td>联系电话</td>
	               <td colspan="3">13333338888</td>
	            </tr>
	            <tr>
	               <td>详细地址</td>
	               <td colspan="3">新广场1路5号</td>
	               <td>详细地址</td>
	               <td colspan="3">学术路123号</td>
	            </tr>
	            <tr>
	               <td>货物名称</td>
	               <td>包装单位</td>
	               <td>件数</td>
	               <td>体积</td>
	               <td>重量</td>
	               <td>货值</td>
	               <td>运费</td>
	               <td>520</td>
	               <td>提货费</td>
	               <td>0</td>
	            </tr>
	            <tr>
	               <td>化妆品</td>
	               <td>箱</td>
	               <td>10</td>
	               <td>2.5</td>
	               <td>1250</td>
	               <td>25000</td>
	               <td>送货费</td>
	               <td>0</td>
	               <td>保价费</td>
	               <td>0</td>
	            </tr>
	            <tr>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>包装费</td>
	               <td>0</td>
	               <td>其他费</td>
	               <td>0</td>
	            </tr>
	            <tr>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>1</td>
	               <td>合计</td>
	               <td>￥520.0</td>
	               <td>付款方式</td>
	               <td>现付</td>
	            </tr>
	            <tr>
	               <td>代收货款</td>
	               <td>￥25000.0</td>
	               <td>大写</td>
	               <td colspan="3">888888888</td>
	               <td>大写</td>
	               <td colspan="3">888888888</td>
	            </tr>
	            <tr>
	               <td rowspan="4">注意事项</td>
	               <td colspan="9" style="border-bottom: none; border-top: none; text-align: left;">1</td>
	            </tr>
	            <tr>
	               <td colspan="9" style="border-bottom: none; border-top: none; text-align: left;">1</td>
	            </tr>
	            <tr>
	               <td colspan="9" style="border-bottom: none; border-top: none; text-align: left;">1</td>
	            </tr>
	            <tr>
	               <td colspan="9" style="border-bottom: none; border-top: none; text-align: left;">1</td>
	            </tr>
	        </table>
	        <div class="banner2">
	            <table cellspacing="0">
	              <tr>
	                 <td>制单人 : 张三</td>
	                 <td>托运方签字 : 张三</td>
	                 <td>收货方签字盖章 : 张三</td>
	                 <td>收货方身份证号 : 张三</td>
	              </tr>
	              <tr>
	                 <td colspan="2">公司地址 : 广州市白云区石井街道安发市场A栋1-4</td>
	                 <td colspan="2">联系电话 : 020-62198225</td>
	              </tr>
	            </table>
	        </div>
	        <button class="button">打印</button>
	        <button style="margin-left:25px;">返回</button>
	    </div>
	</body>
</html>
