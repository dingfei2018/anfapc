<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>物流跟踪</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/logistics.css" />
     <%@ include file="../common/commonhead.jsp" %>
</head>
<body>
   <div class="maip">
          <p class="p">物流跟踪</p>
          <div class="maip-list">
            <table>
               <tr>
                  <td colspan="3"><b>运单信息 :</b></td>
               </tr>
               <tr>
                  <td>运单号 : <span>${ship.ship_sn}</span></td>
                  <td>开单网点 : ${ship.netWorkName}</td>
                  <td>开单时间 : <span><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd"/></span></td>
               </tr>
               <tr>
                  <td>出发地 :  <span>${ship.fromAdd}</span></td>
                  <td>到达地 : <span>${ship.toAdd}</span></td>
                  <td>运单状态 : 
                    <c:if test="${ship.ship_status==1}">
						已入库
					</c:if>
					<c:if test="${ship.ship_status==2}">
						短驳中
					</c:if>
					<c:if test="${ship.ship_status==3}">
						短驳到达
					</c:if>
                     <c:if test="${ship.ship_status==4}">
                          已发车
                     </c:if>
                      <c:if test="${ship.ship_status==5}">
                          已到达
                      </c:if>
                      <c:if test="${ship.ship_status==6}">
                          收货中转中
                      </c:if>
                      <c:if test="${ship.ship_status==7}">
                          到货中转中
                      </c:if>
                      <c:if test="${ship.ship_status==8}">
                          送货中
                      </c:if>
                      <c:if test="${ship.ship_status==9}">
                          已签收
                      </c:if>
				  </td>
               </tr>
               <tr>
                  <td>托运方 : <span>${ship.senderName}</span></td>
                  <td>收货方 : <span>${ship.receiverName}</span></td>
                  <td>客户单号 : <span>${ship.ship_customer_number}</span></td>
               </tr>
               <tr>
                  <td>体积 : <span>${ship.ship_volume}立方</span></td>
                  <td>重量 : <span>${ship.ship_weight}公斤</span></td>
                  <td>件数 : <span>${ship.ship_amount}</span></td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>跟踪信息:</b>
              <table cellspacing="0">
                 <tr>
                    <th>操作人</th>
                    <th>跟踪时间</th>
                    <th>跟踪内容</th>
                    <th>物流是否可见</th>
                    <th>操作</th>
                 </tr>
                 <c:forEach items="${shipTrackList}" var="shipTrack">
                 <tr>
                   <td>${shipTrack.userName}</td>
                   <td><fmt:formatDate value="${shipTrack.create_time}" pattern="yyyy-MM-dd HH:mm"/></td>
                   <td>${shipTrack.track_desc}</td>
                   
                   		<c:choose>  
						   <c:when test="${shipTrack.track_visible}">
						   		<td>否</td>
						   </c:when>  
						   <c:otherwise>
						 		  <td>是</td>
						   </c:otherwise>  
						</c:choose> 
					
					
                   <td><c:if test="${shipTrack.track_class==10}">
                   			<button class="layui-btn layui-btn-danger" onclick="deleteTrack(${shipTrack.track_id }, ${ship.ship_id })">删除</button>
                   		</c:if>
                   </td>
                 </tr>
                </c:forEach>
              </table>
          </div>
          <div class="maip-list3">
             <b>记录跟踪:</b>
             <p>跟踪时间 : <input id="createTime" type="text" name="createTime"></p>
             <span class="span2">跟踪内容 :</span><textarea id="trackDesc" name="trackDesc" ></textarea>
              <p class="p">客户是否可见 : <input name="trackVisible" type="radio" value="1" id="trackVisible" />不可见   <input checked="true"  name="trackVisible" type="radio" value="0" />可见  <span>客户在运单物流查询时,可否看见此条跟踪信息</span></p>
          </div>
          <button class="buttons" onclick="saveTrack(${ship.ship_id })">提交</button>
   </div>
   
   <script type="text/javascript">
   
   $('#createTime').datetimebox({
	    value: getTime(),
	    required: true,
	    showSeconds: false
	});
   
   //保存物流跟踪信息
   function saveTrack(shipId){
	   var createTime = $("#createTime").val();

	   if($("#trackDesc").val()==""){
			Anfa.show("请填写跟踪内容","#trackDesc");
			return;
		}
	   var trackDesc = $("#trackDesc").val();
	   var trackVisible=0;
	   
	   var trackVisible= $('input:radio[name="trackVisible"]:checked').val();
	  
	   $.post("${ctx}/kd/query/saveTrack",
			   {"createTime":createTime,"trackDesc":trackDesc,"trackVisible":trackVisible,"shipId":shipId},
			   function(data){
				   if (data.success==true) {
					   window.location.reload(); 
                   }
			   },"json");
   }
   
   //删除
   function deleteTrack(trackId,shipId){
	   layer.confirm(
	            '您确定要删除？',
	            {
	                btn : [ '删除', '取消' ]
	            },
	            function() {
	                 $.ajax({
	                    type : "post",
	                    dataType : "json",
	                    url : "${ctx }/kd/query/deleteTrack?trackId=" + trackId,
	                    success : function(data) {
	                        if (data.success==true) {
	                        	window.location.reload(); 
	                        }
	                    }
	                }); 
	                }, function() {
	            })
   }

   
   
   /* 获取当前系统时间 '3/4/2010 2:3' */
   function getTime(){ 
	   var now=new Date(); //创建Date对象 
	   var year=now.getFullYear(); //获取年份 
	   var month=now.getMonth(); //获取月份 
	   var date=now.getDate(); //获取日期 
	   var hour=now.getHours(); //获取小时 
	   var minu=now.getMinutes(); //获取分钟 
	   month=month+1; 
	   var time=month+"/"+date+"/"+year+" "+hour+":"+minu; //组合系统时间 
	   return time;
	   } 


   </script>
   
</body>

</html>