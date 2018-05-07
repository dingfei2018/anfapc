<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>打印页面</title>
		<%-- <link rel="stylesheet" href="${ctx}/static/kd/css/print2.css" /> --%>
	</head>
	<body>
	    <div style="width: 1200px;height: auto;border: 1px solid #ccc;margin: auto;padding-bottom: 40px;">
	        <h1 style="text-align: center;">金鹰物流配载单 PZ201711200001</h1>
	        <div style="width: 1200px;height: 150px;border: 1px solid #ccc;margin-top: 20px;border-left: none;border-right: none;">
	          <table style="width: 100%;height: 150px;text-indent: 20px;">
	             <tr>
	                <td>车牌号 : 粤A12345</td>
	                <td>司机 : 张三</td>
	                <td>司机电话 : 13512345678</td>
	                <td>发车日期 : 2011/11/20</td>
	             </tr>
	             <tr>
	                <td>总件数 : 150</td>
	                <td>总体积 : 46</td>
	                <td>总重量 : 20000</td>
	                <td>运费 : 12000</td>
	             </tr>
	             <tr>
	                <td colspan="1">出发地 : 广州市</td>
	                <td colspan="3">到达地 : 北京市</td>
	             </tr>
	          </table>
	        </div>
	        <div style="width: 1200px;height: auto;border: 1px solid #ccc;margin-top: 20px;border-left: none;border-right: none;">
	            <table style="width: 100%;height: auto;text-indent: 20px;text-align: center;">
	               <tr>
	                  <th>序号</th>
	                  <th>运单号</th>
	                  <th>签收</th>
	                  <th>货物名称</th>
	                  <th>件数</th>
	                  <th>体积</th>
	                  <th>重量</th>
	               </tr>
	               <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	                <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	               <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	                <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	               <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	                <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	               <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	                <tr>
	                  <td>1</td>
	                  <td>20171120001</td>
	                  <td><input type="checkbox"></td>
	                  <td>化妆品</td>
	                  <td>10</td>
	                  <td>3</td>
	                  <td>1500</td>
	               </tr>
	            </table>
	        </div>
	        <div style="width: 1200px;height: 150px;border: 1px solid #ccc;margin-top: 20px;border-left: none;border-right: none;text-indent: 20px;">
	           <table style="width: 100%;height: 150px;text-indent: 63px;text-align: left;">
	              <tr>
	                 <td>配载人 : 张三</td>
	                 <td>司机签收 : 张三</td>
	                 <td>网点签收 : 张三</td>
	              </tr>
	              <tr>
	                 <td>收货地址 : 北京市东城区学府路123号</td>
	                 <td></td>
	                 <td>联系电话 : 010-60155352</td>
	              </tr>
	              <tr>
	                 <td>发货地址 : 广州市白云区石井街道安发货运市场</td>
	                 <td></td>
	                 <td>联系电话 : 020-82190886</td>
	              </tr>
	           </table>
	        </div>
	        <button style="margin-left: 546px;width: 120px;height: 40px;margin-top: 60px;font-size: 18px;color: #fff;border: none;background-color: #3974f8;cursor: pointer;">打印</button>
	    </div>
	</body>
</html>
