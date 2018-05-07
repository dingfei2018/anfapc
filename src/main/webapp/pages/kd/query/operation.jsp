s<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签收</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/operationo.css" />
</head>
<body>
   <div class="maip">
          <p>操作日志</p>
          <div class="maip-list">
            <table>
               <tr>
                  <td colspan="3"><b>运单信息 :</b></td>
               </tr>
               <tr>
                  <td>运单号 : YD00001</td>
                  <td>开单网点 : 广州市白云区</td>
                  <td>开单时间 : 2017-12-31</td>
               </tr>
               <tr>
                  <td>出发地 : 广州市白云区</td>
                  <td>收货方 : 保定市</td>
                  <td>出发地 : 在途中</td>
               </tr>
               <tr>
                  <td>托运方 : 广州新易泰</td>
                  <td>收货方 : 李四</td>
                  <td>客户单号 : 201550555dd</td>
               </tr>
               <tr>
                  <td>体积 : 30立方</td>
                  <td>重量 : 23355公斤</td>
                  <td>件数 : 233</td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>配载列表 :</b>
              <table cellspacing="0">
                 <tr>
                    <th>操作人</th>
                    <th>操作时间</th>
                    <th>操作类型</th>
                 </tr>
                 <tr>
                   <td>李四</td>
                   <td>2017-11-12 13:00</td>
                   <td>送货</td>
                 </tr>
                  <tr>
                   <td>李四</td>
                   <td>2017-11-12 13:00</td>
                   <td>送货</td>
                 </tr>
                  <tr>
                   <td>李四</td>
                   <td>2017-11-12 13:00</td>
                   <td>送货</td>
                 </tr>
                  <tr>
                   <td>李四</td>
                   <td>2017-11-12 13:00</td>
                   <td>送货</td>
                 </tr>
              </table>
          </div>
          
          <button class="buttons">关闭</button>
   </div>
</body>

</html>