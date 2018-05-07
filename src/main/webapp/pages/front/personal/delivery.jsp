<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>已发的货</title>
		
	 	<%@ include file="common/include.jsp" %>
	 	<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/information.css?v=${version}"/>
	 	<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>

	</head>
	<body>
		<%@ include file="common/head.jsp" %>
		<div class="content">
            <%@ include file="common/left.jsp" %>
        <div class="content-right">
			<ul>
			   <li style="margin-right: 40px;"><a class="${type==1?'active':''}" href="${ctx}/front/order/hasGoods?type=1">交易进行中</a></li>
			   <li><a class="${type==2?'active':''}" href="${ctx}/front/order/hasGoods?type=2">交易已确认</a></li>
			</ul>
			<script >
				/* $(function(){
					$('div').each(function(){
				        $(this).height(this.scrollHeight);
				    });
				}); */
			</script>
			<c:forEach items="${page.list}" var="i">
			 	<div class="content-right-tab">
				   <p><input type="checkbox" class="content-right-uo" value="${i.id}"/><span class="content-right-up">${i.create_time}</span><b class="content-right-us">订单号：${i.order_number}</b><img class="content-right-uss" onClick="deleteorder(new Array('${i.id}'))" src="${ctx }/static/pc/img/del_icono.png"/></p>
				   <div class="content-right-tab1">
				      <table>
				          <tr>
				             <th>${i.from_city}</th>
				             <th><img src="${ctx }/static/pc/img/jiantou3oopp.png"/></th>
				             <th>${i.to_city}</th>
				          </tr>
				          <tr>
				             <td><button>总重量：${i.all_weight} 公斤</button></td>
				             <td></td>
				             <td><button>总体积：${i.all_volume} 方</button></td>
				          </tr>
				      </table>
				   </div>
				   <div class="content-right-tab2">
				   		  <c:if test="${i.send_status==1}">
				   		  	 <a class="as">常发货物</a>
				   		  </c:if>
				   		  <c:if test="${i.send_status==0}">
					          <a href="javascript:void(0);" onclick="setOftenOrder(${i.id})" style="color:#06f;">设为常发货物</a>
				   		  </c:if>
				   </div>
				   <div class="content-right-tab3">
				        <a href="${ctx}/front/goods/oftendetails?orderid=${i.id}" target="_blank">查看详情</a>
				   </div>
				   <div class="content-right-tab4">
			       		<c:if test="${i.ship_status==1}">
		         			<a> 交易进行中</a>
		         		</c:if>
		         		<c:if test="${i.ship_status==2}">
		         			<a> 交易确认</a>
		         		</c:if>
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
				         <script type="text/javascript">
				             $(function(){
				            	 
				            	 $(".content-right-tab2").css({border:"none"});
				             })
				         </script>
				   </div>
				</div>
			</c:if>
		 </div>
		 </div>
		
			<div id="page" style="text-align: center;">
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
		var curr = parseInt("${page.pageNumber}");
		    function selectAll() {
				if($("#selectAll").is(':checked')){
					$(".content-right-tab input[type='checkbox']").prop("checked", true);
				}else{
					$(".content-right-tab input[type='checkbox']").prop("checked", false)
				}
			}
			
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
	    			Anfa.ajax({
						type : "post",
						url : "${ctx }/front/goods/deleteorder?orderid="+objs,
						success : function(data) {
							if (data.success) {
								var nu = $(".content-right-tab").length;
								if(nu<=objs.length){
									curr = curr>1?(curr-1):1;
								}
								window.location.href="${ctx}/front/order/hasGoods?type=${status}&pageNo="+curr;
							} else {
								layer.msg(data.msg);
							}
						}
					});
	    		}, function(){});
			}
			
			function setOftenOrder(id) {
				layer.confirm('您确定要设置为常发货物？', {
	    		  	btn: ['确定','取消']
	    		}, function(){
	    			Anfa.ajax({
						type : "post",
						url : "${ctx }/front/goods/setoftenOrder?orderid="+id,
						success : function(data) {
							if (data.success) {
								layer.msg("设置成功！",{time: 1000},function(){
									location.reload();
								});
							} else {
								layer.msg(data.msg);
							}
						}
					});
	    		}, function(){});
		    }
			
			$(function(){
				$("#confirmId").bind("click", function(){
					var nu = $("#inputId").val();
					if(nu!=null){
						if(nu>parseInt("${page.totalPage}"))nu= "${page.totalPage}";
						jumpTo(nu);
					}
				});
			}) 
			
			function jumpTo(index){
				$.ajax({
					type : "post",
					url : "${ctx}/front/order/djsonlist",
					data:{type:"${status}",pageNo:index,startTime:$("#startTime").val(),endTime:$("#endTime").val()},		
					success : function(data) {
						var html = "";
						for(var i=0;i<data.list.length;i++){  
							html += appendHtml(data.list[i])
					    }  
						$(".content-right-tab").remove();
						$(".content-right").find("ul").after(html);
					}
				});
			}
			
			function appendHtml(i){
				var html = "<div class=\"content-right-tab\">";
				html += "<p><input type=\"checkbox\" class=\"content-right-uo\" value=\""+i.id+"\"/><span class=\"content-right-up\">"+i.create_time+"</span><b class=\"content-right-us\">订单号："+i.order_number+"</b><img class=\"content-right-uss\" onClick=\"deleteorder(new Array('"+i.id+"'))\" src=\"${ctx }/static/pc/img/del_icono.png\"/></p>"
					+"<div class=\"content-right-tab1\">"
					+"  <table>"
					+"	  <tr>"
					+"		 <th>"+i.from_city+"</th>"
					+"		 <th><img src=\"${ctx }/static/pc/img/jiantou3oopp.png\"/></th>"
					+"		 <th>"+i.to_city+"</th>"
					+"	  </tr>"
					+"	  <tr>"
					+"		 <td><button>总重量："+i.all_weight+" 公斤</button></td>"
					+"		  <td></td>"
					+"		 <td><button>总体积："+i.all_volume+" 方</button></td>"
					+"	  </tr>"
					+"  </table>"
					+"</div>";
					if(i.send_status==1){
						html +="<div class=\"content-right-tab2\">"
						+"	  <a href=\"javascript:void(0);\">常发货物</a>"
						+"</div>";
					}else{
						html +="<div class=\"content-right-tab2\">"
						+"	  <a href=\"javascript:void(0);\" onclick=\"setOftenOrder("+i.id+")\">设为常发货物</a>"
						+"</div>";
					}
					
					html +="<div class=\"content-right-tab3\">"
					+"	<a href=\"${ctx}/front/goods/oftendetails?orderid="+i.id+"\">查看详情</a>"
					+"</div>"
					+"<div class=\"content-right-tab4\">";
					if(i.ship_status==1){
						html +="<a> 交易进行中</a>"
					}else if(i.ship_status==2){
						html +="<a> 交易确认</a>"
					}
			       html +="</div> </div>";
			       return html;
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
				    	    	  curr =obj.curr; 
				    	    	  jumpTo(obj.curr);
				    	      }
				    	  }
				    	  ,skin: '#1E9FFF'
				    });
		    	});
		</script>
	</body>
</html>
