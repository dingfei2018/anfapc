s<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签收</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/picture.css" />
</head>
<body>
   <div class="maip">
          <p>运单中站或配载信息</p>
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
                  <td>重量 : 2335sssssss5公斤</td>
                  <td>件数 : 233</td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>签收照片 :</b>
              
             <%--  <div class="maip-list2-img">
                <img src="${ctx}/static/kd/img/banner-007.png"/>  
              </div>
              
               <div class="maip-list2-img">
                <img src="${ctx}/static/kd/img/banner-007.png"/>  
              </div> --%>
              <ul>
                 <li><img src="${ctx}/static/kd/img/banner-007.png"/></li>
                 <li><img src="${ctx}/static/kd/img/banner-007.png"/></li>
              </ul>
          </div>
         
          <button class="buttons">关闭</button>
   </div>
</body>

</html>