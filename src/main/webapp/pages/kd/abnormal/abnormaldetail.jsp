<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签收</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/followup.css" />
   	<%@ include file="../common/commonhead.jsp" %>
	<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
	<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>	
</head>
<body>
   <div class="maip">
          <p>异常跟进</p>
          <div class="maip-list">
            <table>
               <tr>
                  <td>运单号 : ${abnormal.ship_sn }</td> 
                  <td>开单网点 : ${abnormal.shipNet }</td>
                  <td>开单日期 :<fmt:formatDate value="${abnormal.shipTime }" pattern="yyyy-MM-dd hh:mm:ss"/> </td>
               </tr>
               <tr>
                  <td>出发地 :${abnormal.fromAddr }</td>
                  <td>到达地 :${abnormal.toAddr }</td>
                  <td>货物名称 :${abnormal.productName }</td>
               </tr>
               <tr>
                  <td colspan="3">总件数 :${abnormal.ship_amount } </td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>登记信息 :</b>
              <table style="height:140px;">
               <tr>
                  <td>登记网点 : ${abnormal.abnormalNet }</td>
                  <td>异常类型 : 
                 <c:if test="${abnormal.abnormal_type==0}">货损</c:if>
                 <c:if test="${abnormal.abnormal_type==1}">少货</c:if>
                 <c:if test="${abnormal.abnormal_type==2}">多货</c:if>
                 <c:if test="${abnormal.abnormal_type==3}">货物丢失</c:if>
                 <c:if test="${abnormal.abnormal_type==4}">货单不符</c:if>
                 <c:if test="${abnormal.abnormal_type==5}">超重超方</c:if>
                 <c:if test="${abnormal.abnormal_type==6}">超时</c:if>
                 <c:if test="${abnormal.abnormal_type==7}">拒收</c:if>
                 <c:if test="${abnormal.abnormal_type==8}">投诉</c:if>
                 <c:if test="${abnormal.abnormal_type==9}">其他</c:if>
                  </td>
                  <td>异常状态 : 
                   <c:if test="${abnormal.abnormal_status==0}">待处理</c:if>
                   <c:if test="${abnormal.abnormal_status==1}">处理中</c:if>
                   <c:if test="${abnormal.abnormal_status==2}">已处理</c:if>
                  </td>
               </tr>
               <tr>
                  <td>登记人 : ${abnormal.realname }</td>
                  <td colspan="2">登记时间 :<fmt:formatDate value="${abnormal.create_time }" pattern="yyyy-MM-dd hh:mm:ss"/> </td>
               </tr>
               <tr>
                  <td colspan="3">异常描述 : ${abnormal.abnormal_desc }</td>
               </tr>
               <c:if test="${fn:length(abnormalImg)>0}">
                <c:forEach items="${abnormalImg}" var="img" varStatus="vs">
                <tr>
                <td colspan="3">
                  <a href="${img.img}" rel="lightbox"> <img class="newimgs" src="${img.img}"> </a>
                </td>
                </tr>
                </c:forEach>
                </c:if>
            </table>
          </div>
          <c:if test="${fn:length(handleList)>0}">
              <c:forEach items="${handleList}" var="row" varStatus="vs">
          <div class="maip-list2">
              <b>跟进信息 :</b>
              <table style="height:100px;">
               <tr>
                  <td>跟进人 : ${row.row.realname}</td>
                  <td>跟进时间 :<fmt:formatDate value="${row.row.create_time}" pattern="yyyy-MM-dd hh:mm:ss"/> </td>
               </tr>
               <tr>
                  <td colspan="2">跟进内容 : ${row.row.handle_content}</td>
               </tr> 
               <c:if test="${fn:length(row.img)>0}">
                <c:forEach items="${row.img}" var="img" varStatus="vs">
                <tr>
                <td colspan="2">
                <a  href="${img.img}"  rel="lightbox"> <img class="newimgs" src="${img.img }"></a>
                </td>
                </tr>
                </c:forEach>
                </c:if>
            </table>
            </div>
               </c:forEach>
          </c:if>
   </div>
</body>
</html>