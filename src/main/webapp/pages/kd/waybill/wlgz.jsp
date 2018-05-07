<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>回单日志</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/wlgz.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp" %>
</head>
<body>
	<%@ include file="../common/head2.jsp" %>
	<%@ include file="../common/head.jsp" %>
	<div class="banner">
    <%@ include file="../common/fahuoleft.jsp" %>
    <script type="text/javascript">
		     $(function(){
				  var _width=$("body").width();
				  var _widths = $(".banner-left").width();
				  var _widthd = _width - _widths - 80;
				  parseInt(_widthd);
				  $('.banner-right').css('width',_widthd+'px');
		     });
		     $(window).resize(function(){ 
		    	  var Width = $(window).width();
	    	      var _widths = $(".banner-left").width();
		  		  var _widthd = Width - _widths - 80;
		  		  parseInt(_widthd);
		  		  $('.banner-right').css('width',_widthd+'px');
		     });
	  </script>
    <div class="banner-right">
       <div class="banner-right-ultitle" style="overflow: hidden;">
		   <ul>
			   <li>
				   <a href="/kd/waybill/viewDetail?shipId=${shipId}" class="at">运单信息</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/shipLog?shipId=${shipId}" class="at" >操作日志</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/shipTransportInfo?shipId=${shipId}" class=" activet at" >物流跟踪</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/shipChangeInfo?shipId=${shipId}"  class=" at" >改单记录</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/transactionInfo?shipId=${shipId}"  class="at" >异动记录</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/receiptInfo?shipId=${shipId}" class="at" >回单日志</a>
			   </li>
			   <li>
				   <a href="/kd/waybill/abnormalInfo?shipId=${shipId}" class="at" >异常记录</a>
			   </li>
		   </ul>
	       	</div>
       <div class="banner-right-list">
       		<p>物流信息</p>
       		<table>
       			<tr>
       				
       				<th>操作人</th>
       				<th>跟踪时间</th>
       				<th>跟踪内容</th>
       				<th>客户是否可见</th>
       				<th>操作</th>
       			</tr>

				<c:forEach items="${shipTrackList}" var="shipTrack">
       			<tr>
       				<td>${shipTrack.userName}</td>
       				<td><fmt:formatDate value="${shipTrack.create_time}" pattern="yyyy-MM-dd HH:mm"/></td>
       				<td>${shipTrack.track_desc}</td>
					<c:choose>
						<c:when test="${shipTrack.track_visible}">
							<td Style="color: #ff7801;">否</td>
						</c:when>
						<c:otherwise>
							<td Style="color: #ff7801;">是</td>
						</c:otherwise>
					</c:choose>
       				<td>
						<c:if test="${shipTrack.track_class==10}">
							<button class="layui-btn layui-btn-danger" onclick="deleteTrack(${shipTrack.track_id }, ${ship.ship_id })">删除</button>
						</c:if>
					</td>
       			</tr>
				</c:forEach>
       		</table>
       	
       </div>
       <div class="banner-right-list2">
       		<p>编辑信息</p>
       		<div class="banner-list2-div">
       			<span>跟踪时间：</span>
       			<input class="banner-list2-input1 Wdate" id="createTime"  name="createTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss ',isShowClear:false,readOnly:true})">
       			<span  class="toleft">跟踪内容：</span>
       			<input class="banner-list2-input2" type="text" id="trackDesc"  name="trackDesc" />
       			<input class="toleft" type="checkbox" name="trackVisible"/>
       			<span>客户是否可见<span>(物流查询时，客户可否看此条跟踪信息)</span></span>
       			<a class="banner-right-botton" onclick="saveTrack('${shipId}')">保存</a>
       		</div>
       </div>
       
    </div>
</div>

<%@ include file="../common/loginfoot.jsp" %>
<script>
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
    function saveTrack(shipId){
        if($("#createTime").val()==""){
            layer.msg("请填写跟踪时间");
            return;
		}
        if($("#trackDesc").val()==""){
			layer.msg("请填写跟踪内容");
            return;
        }
        var trackVisible=$("input[name='trackVisible']");
       if((trackVisible).is(':checked')) {
         trackVisible.val(0);
        }else{
            trackVisible.val(1);
        }
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/kd/query/saveTrack",
            data: {"createTime":$("#createTime").val(),"trackDesc":$("#trackDesc").val(),"trackVisible":trackVisible.val(),"shipId":shipId},
            success: function (data) {
                if (data.success) {
                    layer.msg("保存成功！", {time: 1000}, function () {
                        window.location.reload();
                    });
                } else {
                    layer.msg("保存失败！", {time: 1000}, function () {
                    });
                }
            }
        });
    }
</script>
</body>
</html>