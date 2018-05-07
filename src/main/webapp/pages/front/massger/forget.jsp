<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<base href="${ctx }/">
		<title>未读消息</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/forget2.css?v=${version}" />
		<script type="text/javascript" src="${ctx }/static/pc/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
		<%@ include file="../common/loginhead.jsp" %>
		
		<!-- 消息页面 -->
		<div class="content">
		   <div class="content-top">
				<ul>
				    <li class="content-top-left"><a id="<c:if test="${curr eq 20}">activer</c:if>" href="${ctx }/front/message/showUnRead">未读消息</a></li>
				    <li><a href="${ctx }/front/message">已读消息</a></li>
				</ul>
				<c:if test="${not empty page.list}"><div class="content-tops">${unreadcount}</div></c:if>
	       </div>
	       
	       <c:if test="${not empty page.list}">
	       <c:forEach items="${page.list}" var="item">
		       <div class="content-tops" id="content-tops">
		         <input type="checkbox"  value="${item.id}"/>
		        <%--  <img src="${ctx }/static/pc/img/icon1.jpg"/> --%>
                <c:if test="${item.type==1}">
                <b class="activet"> ${item.label}</b>
                </c:if>
                <c:if test="${item.type==2}">
                <b class="activec"> ${item.label}</b>
                </c:if>
                <c:if test="${item.type==3}">
                <b class="actives"> ${item.label}</b>
                </c:if>
				<strong>${item.content}</strong>
		         <span><fmt:formatDate value="${item.created}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		       </div> 
	       </c:forEach>
			
	       <div class="content-bottom">
	          <input type="checkbox" id="selectAll" onclick="selectAll()"/>
	          <span id="checkall"><a>全选</a></span>
			<button id="deleteall" onclick="deleteAll();">删除</button>

			<!-- <button onclick="markedAsRead()">标记为已读</button>
	          <ul>
	             <li><a href="#">全部标记为已读</a></li>
	             <li class="content-bottom-li"><a href="#">清空所有通知</a></li>
	          </ul> -->
			<%-- <c:if test="${empty page.list}">
	                  <script>
						$(function(){
						    $("#selectAll").hide();
						    $("#checkall").hide();
						    $("#deleteall").hide();
						});
						</script>
				</c:if> --%>
	       </div>
	       
		

		<div id="page" style="text-align: center;">
			</div>
			
	       </c:if>
      		<c:if test="${empty page.list}">
      			<div style="text-align: center; margin-top: 60px; font-size: 18px; height:100px; line-height:100px;">暂无数据</div>
      		
      		</c:if>
      		</div>
      		
		</div>

		<%@ include file="../common/loginfoot.jsp" %>
	</body>
	<script type="text/javascript">layui.use(['laypage'], function(){
    	var laypage = layui.laypage;
	    laypage({
		      cont: 'page'
		      ,pages: '${page.totalPage}' //得到总页数
		      ,curr:'${fn:length(page.list)==0?(page.pageNumber-1):page.pageNumber}'
		      ,skip: true //是否开启跳页
	    	  ,jump: function(obj, first){
	    	      if(!first){
	    	    	  window.location.href="${ctx}/front/message/showUnRead2?pageNo="+obj.curr;
	    	      }
	    	  }
	    	  ,skin: '#1E9FFF'
	    });
	});</script>
	<script type="text/javascript"> 
	function deleteAll(){
		var array = new Array();
		$(".content-tops input[type='checkbox']").each(function(i){
			if($(this).prop("checked"))array.push($(this).val());
		});
		deletemessages(array)
	}

	function deletemessages(objs) {
    	if(objs==null||objs==""){
    		layer.msg("请选择要删除的数据");
			return;
		}
    	layer.confirm('您确定要删除？', {
		  	btn: ['确定','取消']
		}, function(){
			$.ajax({
				type : "post",
				dataType : "html",
				url : "${ctx }/front/message/deleteMessage?message_id="+objs,
				success : function(data) {
					var obj = new Function("return" + data)();
					if (obj.num == 1) {
						var nu = parseInt("${fn:length(page.list)}");
						var curr = parseInt("${page.pageNumber}");
						if(nu<=objs.length){
							curr = curr>1?(curr-1):1;
						}
						window.location.href= "${ctx }/front/message/showUnRead";
					} else {
						layer.msg(obj.msg);
					}
				}
			});
		}, function(){});
	}
	function selectAll() {
		if($("#selectAll").is(':checked')){
			$(".content-tops input[type='checkbox']").prop("checked", true);
		}else{
			$(".content-tops input[type='checkbox']").prop("checked", false)
		}
	}
	function markedAsRead(){
		var array = new Array();
		$(".content-tops input[type='checkbox']").each(function(i){
			if($(this).prop("checked"))array.push($(this).val());
		});
		changeMessageState(array);
	}
	function changeMessageState(objs){
		if(objs==null||objs==""){
    		layer.msg("请选择要标记为已读的数据");
			return;
		}
    	layer.confirm('您确定要标记为已读 ？', {
		  	btn: ['确定','取消']
		}, function(){
			$.ajax({
				type : "post",
				dataType : "html",
				url : "${ctx }/front/message/markAsRead?message_id="+objs,
				success : function(data) {
					var obj = new Function("return" + data)();
					if (obj.num == 1) {
						var nu = parseInt("${fn:length(page.list)}");
						var curr = parseInt("${page.pageNumber}");
						if(nu<=objs.length){
							curr = curr>1?(curr-1):1;
						}
						window.location.href= "${ctx }/front/message/showUnRead";
					} else {
						layer.msg(obj.msg);
					}
				}
			});
		}, function(){});
	}
	</script>
	
</html>