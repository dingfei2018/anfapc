<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签收</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/Journal.css" />
</head>
<body>
   <div class="maip" style="border:1px">
          <p>回单日志</p>
           <div class="maip-list">
            <table>
               <tr>
                  <td colspan="3"><b>运单信息 :</b></td>
               </tr>
               <tr>
                  <td>运单号 :  ${kdShip.ship_sn}</td>
                  <td>开单时间 :<fmt:formatDate value="${kdShip.create_time}" pattern="yyyy-MM-dd"/></td>
                  <td>开单网点 : ${kdShip.netWorkName}</td>
               </tr>
               <tr>
                  <td>托运方 :${kdShip.senderName}</td>
                  <td>收货方 : ${kdShip.receiverName}</td>
                  <td>出发地 : ${kdShip.fromAdd}</td>
               </tr>
               <tr>
                  <td colspan="3">到达地 : ${kdShip.toAdd}</td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>操作信息 :</b>
              <table cellspacing="0">
                 <tr>
                    <th>操作人</th>
                    <th>操作时间</th>
                    <th>操作类型</th>
                 </tr>
                  <c:forEach  items="${tracks}" var="track">
                 <tr>
                   <td>${track.userName}</td>
                   <td><fmt:formatDate value="${track.create_time}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
                   <td>${track.track_desc}</td>
                 </tr>
                  </c:forEach>
              </table>
          </div>
          <button class="buttons" onclick="parent.layer.closeAll();">关闭</button>
   </div>
</body>

</html>