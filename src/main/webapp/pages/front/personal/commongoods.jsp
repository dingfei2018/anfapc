<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>常发货物</title>
	 	<%@ include file="common/include.jsp" %>
	 	<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/Commongoods.css?v=${version}"/>
	 	<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	 
	</head>
	<body>
		<%@ include file="common/head.jsp" %>
		<div class="content">
            <%@ include file="common/left.jsp" %>
        <div class="content-right">
			<div class="conr_title" style="margin-bottom: 0px;">常发货物</div>
			
			<c:forEach items="${page.list}" var="i">
				<div class="content-right-tab">
				   <p><input type="checkbox" class="content-right-uo" value="${i.id}"/><span class="content-right-up">${i.create_time}</span><b class="content-right-us">订单号：${i.order_number}</b><img class="content-right-uss" onClick="deleteorder(${i.id})" src="${ctx }/static/pc/img/del_icono.png"/></p>
				   <div class="content-right-tab1">
				      <table>
				          <tr>
				             <th>${i.fromAddr}</th>
				             <th><img src="${ctx }/static/pc/img/jiantou3oopp.png"/></th>
				             <th>${i.toAddr}</th>
				          </tr>
				          <tr>
				             <td><button>总重量：${i.all_weight} 公斤</button></td>
				              <td></td>
				             <td><button>总体积：${i.all_volume} 方</button></td>
				          </tr>
				      </table>
				   </div>
				 <%--   <div class="content-right-tab2">
				          <a href="javascript:void(0);" onclick="setOftenOrder(${i.id})">设为常发货物</a>
				   </div> --%>
				   <div class="content-right-tab3">
		      			<c:if test="${i.ship_status==1}">
		         			<!-- <strong> 交易进行中</strong> -->
		         		</c:if>
		         		<c:if test="${ship_status==2}">
		         			<strong> 交易确认</strong>
		         		</c:if>
				        <a href="${ctx}/front/goods/oftendetails?orderid=${i.id}" target="_blank">查看详情</a>
				      </dl>
				   </div>
				   <div class="content-right-tab4">
			       <a href="${ctx }/front/goods/onceagainorder?orderid=${i.id}">再次发布</a>
				   </div>
				</div>
			</c:forEach>
			<c:if test="${fn:length(page.list)>0}">
				<!-- 全选 删除 -->
				<div class="content-right-tabu">
				    <input id="selectAll"  type="checkbox" onclick="selectAll()"> 全选
				    <button onclick="deleteAll()">删除</button>
				</div>
				<div id="page" style="text-align: center;">
				</div>
			</c:if>
			<c:if test="${fn:length(page.list)==0}">
				<div class="content-right-tab">
				   <p></p>
				   <div class="content-right-tab2">
				         暂无数据
				   </div>
				</div>
			</c:if>
		</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">

		//删除
		function deleteAll(){
			var array = new Array();
			$(".content-right-tab input[type='checkbox']").each(function(i){
				if($(this).prop("checked"))array.push($(this).val());
			});
			deleteorder(array)
		}

		function deleteorder(objs) {
			if(objs==null||objs==""){
				layer.msg("请选择要删除的数据");
				return;
			}
			layer.confirm('您确定要删除？', {
			  	btn: ['删除','取消']
			}, function(){
				$.ajax({
					type : "post",
					dataType : "html",
					url : "${ctx }/front/goods/updateSendStatus?orderid="+objs,
					success : function(data) {
						var obj = new Function("return" + data)();
						if (obj.num == 1) {
							window.location.href="${ctx}/front/userconter/oftenGoods";
						} else {
							layer.msg(obj.msg);
						}
					}
				});
			}, function(){});
		}

		//全选
		function selectAll() {
			if($("#selectAll").is(':checked')){
				$(".content-right-tab input[type='checkbox']").prop("checked", true);
			}else{
				$(".content-right-tab input[type='checkbox']").prop("checked", false)
			}
		}
			
			
			layui.use(['laypage'], function(){
			    	var laypage = layui.laypage;
				    //调用分页
				    laypage({
					      cont: 'page'
					      ,pages: '${page.totalPage}' //得到总页数
					      ,curr:'${page.pageNumber}'
					      ,skip: true
				    	  ,jump: function(obj, first){
				    	      if(!first){
				    	    	  window.location.href='${ctx}/front/userconter/oftenGoods?pageNo='+obj.curr;
				    	      }
				    	  }
				    	  ,skin: '#1E9FFF'
				    });
		    	});
		</script>
	</body>
</html>