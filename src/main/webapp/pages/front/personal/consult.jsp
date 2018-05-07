<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>资讯管理</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/consult.css?v=${version}"/>
		<%@ include file="common/include.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	</head>
	<body>
	<%@ include file="common/head.jsp"%>
		<!--中间内容的部分-->
		
	<div class="content">
		<%@ include file="common/left.jsp"%>
			<div class="content-right">
			<ul>
			   <li style="margin-right: 40px;"><a href="${ctx}/cpy/admin/advisory">新增资讯</a></li>
			   <li><a class='active' href="${ctx}/cpy/admin/advisoryList">资讯管理</a></li>
			</ul>
				<div class="content-right-top">
					<%-- <ul>
						<li><a href="${ctx}/cpy/admin/advisory">新增资讯</a></li>
						<li class='active'><a href="${ctx}/cpy/admin/advisoryList">资讯管理</a></li>
					</ul> --%>
					<table cellspacing="0">
					    <tr>
					       <th>序号</th>
					       <th>文章标题</th>
					       <th>新增日期</th>
					       <th>操作</th>
					    </tr>
					    <c:forEach items="${page.list}" var="n" varStatus="v">
						    <tr>
						       <td><input type="checkbox" value="${n.id}" /> ${v.index+1}</td>
						       <td><a href="#">${fn:substring(n.title,0,20)}</a></td>
						       <td><fmt:formatDate value="${n.created}" type="date"/></td>
						       <td><a id="gtn" href="${ctx}/cpy/admin/advisory?id=${n.id}">修改</a><a id="gtns" href="javascript:void(0);"  onclick="deleteNew(new Array('${n.id}'))">删除</a></td>
						    </tr>
					    </c:forEach>
					    <c:if test="${fn:length(page.list)==0}">
						    <tr>
						       <td colspan="4">暂无数据</td>
						    </tr>
						</c:if>
					</table>
					<c:if test="${fn:length(page.list)!=0}">
						<p><input type="checkbox" id="selectAll" onclick="selectAll()"> 全选 <a href="javascript:void(0);"  onclick="deleteAll();">删除</a></p>
					</c:if>
					<div id="page" style="text-align: center;">
					</div>
				</div>
		</div>
		</div>
		<!--底部的内容--->
        <%@ include file="../common/loginfoot.jsp"%>
			
		<script type="text/javascript">
		
		var curr = parseInt("${page.pageNumber}");
		
		function deleteAll(){
			var array = new Array();
			$("table input[type='checkbox']").each(function(i){
				if($(this).prop("checked"))array.push($(this).val());
			});
			deleteNew(array)
		}
		
		function deleteNew(objs) {
			if(objs==null||objs==""){
				layer.msg("请选择要删除的数据");
				return;
			}
			layer.confirm('您确定要删除？', {
			  	btn: ['删除','取消']
			}, function(){
				Anfa.ajax({
					type : "post",
					url : "${ctx }/cpy/admin/removeAdvisory?newIds="+objs,
					success : function(data) {
						if (data.success==true) {
							var nu = $("table tr").length-1;
							if(nu<=objs.length){
								curr = curr>1?(curr-1):1;
							}
							window.location.href="${ctx}/cpy/admin/advisoryList?pageNo="+curr;
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
				$("table input[type='checkbox']").prop("checked", true);
			}else{
				$("table input[type='checkbox']").prop("checked", false)
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
		
		
		function jumpTo(index){
			$.ajax({
				type : "post",
				url : "${ctx}/cpy/admin/jsonList",
				data:{type:1,pageNo:index},		
				success : function(data) {
					var html = "";
					for(var i=0;i<data.page.list.length;i++){  
						html += appendHtml(data.page.list[i])
				    }  
					$("table tr").each(function(i){
						   if(i!=0)$(this).remove();
					 });
					$("table tr").after(html);
				}
			});
		}
		
		
		function appendHtml(ins){
			var html="<tr><td><input type=\"checkbox\" value='"+ins.id+"'/> 1</td>"
			       +"<td>"+ins.title+"</td><td>"+ins.created+"</td>"
			       +"<td><a id=\"btn\" href='${ctx}/cpy/admin/advisory?id="+ins.id+"'>修改</a><a id=\"btns\" href=\"javascript:void(0);\"  onclick=\"deleteNew(new Array('"+ins.id+"'))\">删除</a></td>";
		       html +="</tr>";
		       return html;
		}
		
		</script>
	</body>
</html>

