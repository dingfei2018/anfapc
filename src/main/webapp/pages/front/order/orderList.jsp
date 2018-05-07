<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>订单列表</title>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/Vehicle/css/Myorder.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<!---详细页面的内容---->
		<div class="content">		
			
		     <ul class="content-ul">
		         <li class="content-nomargin"><a href="${ctx}/front/order?type=1" class=" <c:if test="${status==1}"> actives</c:if>">交易进行中</a></li>
		         <li><a href="${ctx}/front/order?type=2" class=" <c:if test="${status==2}"> actives</c:if>">交易已确认</a></li>
		     </ul>
			
			<div class="content-gu">
			     订单日期：<input type="text" id="startTime" value="${startTime}" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" placeholder="起始时间"/>
			     <span> &nbsp;&nbsp;— </span>
			     <input type="text" id="endTime"  value="${endTime}" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})" placeholder="结束时间"/> 
			     <input type="button" id="search" value="查询" class="content-gu-button"> 
			</div>
			<c:forEach items="${page.list}" var="i">
			<div class="content-gun">
			       <p>
				       <input type="checkbox" value="${i.id}" class="content-gun-to"/>
				       <span class="content-gun-tot">${i.create_time }</span>
				       <b class="content-gun-too">订单号：<strong>${i.order_number}</strong></b>
				       <img class="content-gun-top" src="${ctx }/static/pc/img/del_icono.png" onClick="deleteorder(new Array('${i.id}'))"/>
			      </p>
			      <div class="content-gunpo">
			         <img width="130px" height="88px" src="${i.firstImg}_200X150${fn:substringAfter(i.firstImg, '_')}" class="content-gunpo-img"/>
			          <table>
			              <tr>
			                  <th style="font-size:17px;display:inline-block; width: 118px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a title="${i.from_city}">${i.from_city}</a></th>
			                  <th>&nbsp;&nbsp;&nbsp;&nbsp;<img src="${ctx }/static/pc/img/jiantou3oopp.png"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>
			                  <th style="font-size:17px;display:inline-block; width: 118px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a title="${i.to_city}">${i.to_city}</a></th>
			              </tr>
			              <tr>
			                  <td><button disabled>总重量：${i.all_weight}公斤</button></td>
			                  <td></td>
			                  <td><button disabled>总体积：${i.all_volume}方</button></td>
			              </tr>
			          </table>
			      </div>
			      <div class="content-gunpos">
			      
			           <ul>
			                <li><a href="${ctx}/front/goods/oftendetails?orderid=${i.id}" target="_blank">查看详情</a></li>
			           	 <c:if test="${status==1}">	
			           	 	<li><a style="color:red;">交易进行中</a></li>
			              </c:if>   
			              <c:if test="${status==2}">	
			              	<li><a style="color:red;">交易已确认</a></li>
			              </c:if>
			              <c:if test="${status==2 && i.markid==null}">
			                 <li class="content-gunpos-aos"><a class="content-gunpos-ao"  href="${ctx}/front/evaluate?orderid=${i.id}">评价</a></li>
		                  </c:if>
		                  <c:if test="${status==2 && i.markid!=null}">
			                 <li class="content-gunpos-aos"><a class="content-gunpos-ao" id="active"  href="#">已评价</a></li>
		                  </c:if>
			           </ul>
			      </div>
			</div>
			</c:forEach>
			<c:if test="${fn:length(page.list)>0}">
			<div class="content-right-tabu">
			    <input type="checkbox" id="selectAll" onclick="selectAll()"> 全选
			    <button onclick="deleteAll();">删除</button>
			</div>
		   </c:if>
			<c:if test="${fn:length(page.list)==0}">
				 <div class="content-gun"style="text-align: center;">
				       <p>
				       
				      </p>
				     	<span style="display: inline-block; margin-top: 53px;">暂无数据</span> 
				</div>
			</c:if>
			<div id="page" style="text-align: center;">
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
			var curr = parseInt("${page.pageNumber}");
			$(function(){
				$("#search").bind("click", function(){
					if($("#startTime").val()==""&&$("#endTime").val()==""){
						Anfa.show("查询请选择订单时间","#search");
						return;
					}
					window.location.href="${ctx}/front/order?type=${status}&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
				});
				
				$("#confirmId").bind("click", function(){
					var nu = $("#inputId").val();
					if(nu!=null){
						if(nu>parseInt("${page.totalPage}"))nu= "${page.totalPage}";
						jumpTo(nu);
					}
				});
			})
			
			function deleteAll(){
				var array = new Array();
				$(".content-gun input[type='checkbox']").each(function(i){
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
						url : "${ctx }/front/goods/deleteorder?orderid="+objs,
						success : function(data) {
							if (data.success) {
								var nu = $(".content-gun").length;
								if(nu<=objs.length){
									curr = curr>1?(curr-1):1;
								}
								window.location.href="${ctx}/front/order?type=${status}&pageNo="+curr+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
							} else {
								layer.msg(data.msg);
							}
						}
					});
	    		}, function(){});
			}
			
			
			function jumpTo(index){
				$.ajax({
					type : "post",
					url : "${ctx}/front/order/jsonlist",
					data:{type:"${status}",pageNo:index,startTime:$("#startTime").val(),endTime:$("#endTime").val()},		
					success : function(data) {
						var html = "";
						for(var i=0;i<data.list.length;i++){  
							html += appendHtml(data.list[i])
					    }  
						$(".content-gun").remove();
						$(".content-gu").after(html);
					}
				});
			}
			
			
			function appendHtml(ins){
				var img = ins.firstImg;
				if(img!=null)img =img +"_200X150"+ img.substring(img.lastIndexOf('_')+1) ; 
				var html = "<div class=\"content-gun\">";
				html +="<p>"
			       +"   <input type=\"checkbox\" value=\""+ins.id+"\" class=\"content-gun-to\"/>"
			       +"    <span class=\"content-gun-tot\">"+ins.create_time+"</span>"
			       +"    <b class=\"content-gun-too\">订单号：<strong>"+ins.order_number+"</strong></b>"
			       +"    <img class=\"content-gun-top\" src=\"${ctx }/static/pc/img/del_icono.png\" onClick=\"deleteorder(new Array('"+ins.id+"'))\"/>"
			       +"</p>"
			       +"<div class=\"content-gunpo\">"
			       +"  <img width=\"130px\" height=\"88px\" src=\""+img+"\" class=\"content-gunpo-img\"/>"
			       +"   <table>"
			       +"       <tr>"
			       +"          <th style=\"font-size:17px;display:inline-block; width: 118px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;\"><a title=\""+ins.from_city+"\">"+ins.from_city+"</a></th>"
			       +"          <th>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"${ctx }/static/pc/img/jiantou3oopp.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>"
			       +"          <th style=\"font-size:17px;display:inline-block; width: 118px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;\"><a title=\""+ins.to_city+"\">"+ins.to_city+"</a></th>"
			       +"       </tr>"
			       +"       <tr>"
			       +"           <td><button disabled>总重量："+ins.all_weight+"公斤</button></td>"
			       +"           <td></td>"
			       +"           <td><button disabled>总体积："+ins.all_volume+"方</button></td>"
			       +"       </tr>"
			       +"   </table>"
			       +"</div>"
			       +"<div class=\"content-gunpos\">"
			       +"    <ul><li><a href=\"${ctx}/front/goods/oftendetails?orderid="+ins.id+"\">查看详情</a></li>";
			       if("${status}"==1){
			    	   html += "<li><a style=\"color:red;\">交易进行中</a></li>";
			       }else if("${status}"==2){
			    	   html += "<li><a style=\"color:red;\">交易已确认</a></li>";
			       }
			       
				   if("${status}"==2&&ins.markid==null){
			    	   html += "<li class=\"content-gunpos-aos\"><a class=\"content-gunpos-ao\"  href=\"${ctx}/front/evaluate?orderid="+ins.id+"\">评价</a></li>";
			       }else if("${status}"==2&&ins.markid!=null){
			    	   html += "<li class=\"content-gunpos-aos\"><a class=\"content-gunpos-ao\" id=\"active\"  href=\"#\">已评价</a></li>";
			       }
			       html +="</ul> </div> </div>";
			       return html;
			}
			
			//全选
			function selectAll() {
				if($("#selectAll").is(':checked')){
					$(".content-gun input[type='checkbox']").prop("checked", true);
				}else{
					$(".content-gun input[type='checkbox']").prop("checked", false)
				}
			}
			
 			layui.use(['laypage'], function(){
			    	var laypage = layui.laypage;
				    laypage({
					      cont: 'page'
					      ,pages: '${page.totalPage}' //得到总页数
					      ,curr:'${fn:length(page.list)==0?(page.pageNumber-1):page.pageNumber}'
					      ,skip: true //是否开启跳页
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