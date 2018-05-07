<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>常发货物</title>
<%-- <link rel="stylesheet" href="${ctx }/static/pc/Common/css/Common.css?v=${version}" /> --%>
<link rel="stylesheet" href="${ctx }/static/pc/css/online.css?v=${version}"/> 
<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
<script src="${ctx }/static/pc/study/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/pc/js/unslider.min.js"></script>
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/layui/layui.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
</head>
<body>
	<%@ include file="../common/loginhead.jsp" %>
      <div class="content">
	      <ul class="content-ul">
		         <li class="content-li"><a>常发货物</a></li>
		     </ul>
			<c:forEach items="${page.list}" var="i">
			<div class="content-gun">
			       <p>
				       <input type="checkbox" class="content-gun-to" value="${i.id}"/>
				       <span class="content-gun-tot">${i.create_time}</span>
				       <b class="content-gun-too">订单号：<strong>${i.order_number }</strong></b>
				       <img class="content-gun-top" src="${ctx }/static/pc/img/del_icono.png" onclick="deleteorder(${i.id})"/>
			      </p>
			      <div class="content-gunpo">
			        <%--  <img width="130px" height="88px" src="${i.firstImg}_200X150${fn:substringAfter(i.firstImg, '_')}" class="content-gunpo-img"/> --%>
			          <table>
			              <tr>
			                  <th>${i.fromAddr}</th>
			                  <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${ctx }/static/pc/img/jiantou3oopp.png"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			                  <th>${i.toAddr}</th>
			              </tr>
			              <tr>
			                  <td><button disabled>总重量： ${i.all_weight}公斤</button></td>
			                  <td></td>
			                  <td><button disabled>总体积：${i.all_volume}方</button></td>
			              </tr>
			          </table>
			      </div>
			      
			      <div class="content-gunpos">
			           <ul>   
			              <li><a class="content-gunpos-rights" style="color: #0066FF;" href="${ctx}/front/goods/oftendetails?orderid=${i.id}">查看详情</a></li>
			              <li class="content-gunpos-right"><a href="${ctx }/front/goods/onceagainorder?orderid=${i.id}">再次发布</a></li>
			           </ul>
			      </div>
			</div>
			</c:forEach>
			<c:if test="${fn:length(page.list)>0}">
			<div class="content-right-tabu">
		    <input type="checkbox" id="selectAll" onclick="selectAll();"> 全选
		    <button onclick="deleteAll();">删除</button>
		</div>
			<div id="page" style="text-align: center;">
				</div>
		</c:if>
				<c:if test="${fn:length(page.list)==0}">
				 <div class="content-gun"style="text-align: center;">
				       <p>
				        
				      </p>
				     	<span style="display:inline-block; margin-top: 58px;">没有数据</span>
				</div>
			</c:if>
	    </div>
	    <!---底部的内容-->
        <%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
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
		    	    	  window.location.href='${ctx}/front/goods/oftenlist?pageNo='+obj.curr;
		    	      }
		    	  }
		    	  ,skin: '#1E9FFF'
		    });
		});
		//删除
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
				Anfa.ajax({
					type : "post",
					url : "${ctx }/front/goods/updateSendStatus?orderid="+objs,
					success : function(data) {
						if (data.success) {
							layer.msg("删除成功！",{time: 2000},function(){
								location.reload();
							});
						} else {
							layer.msg(data.msg);
						}
					}
				});
			}, function(){});
		}
		
		//全选
		function selectAll() {
			if($("#selectAll").is(':checked')){
				$(".content-gun input[type='checkbox']").prop("checked", true);
			}else{
				$(".content-gun input[type='checkbox']").prop("checked", false)
			}
		}
		
		</script>
</body>
</html>
