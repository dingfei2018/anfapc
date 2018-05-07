<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>用户列表</title>
	    <link rel="stylesheet" href="${ctx}/static/kd/css/userlist2.css" />
	    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>
	<body>
		<input type="hidden" value="${msg.msg}" id="msg" />
		<input type="hidden" value="${userId}" id="y_userId" />
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/baseinfoleft.jsp" %>
			<script type="text/javascript">
			     $(function(){
					  var _width=$("body").width();
					  var _widths = $(".banner-left").width();
					  var _widthd = _width - _widths - 80;
					  parseInt(_widthd)
					  $('.banner-right').css('width',_widthd+'px');
			     });
			     $(window).resize(function(){ 
			    	  var Width = $(window).width();
		    	      var _widths = $(".banner-left").width();
			  		  var _widthd = Width - _widths - 80;
			  		  parseInt(_widthd)
			  		  $('.banner-right').css('width',_widthd+'px');
			     });
	       </script>
			<div class="banner-right">
				<div class="banner-right-title">
					<ul>
						<li><a href="${ctx}/kd/user" class="activet">用户列表</a></li>
						<li><a href="${ctx}/kd/user/add">新增用户</a></li>
					</ul>
				</div>
				<div class="banner-right-list">
				 <ul>
				<form action="${ctx}/kd/user" method="post" id="searchForm" onsubmit="return false;">
					<li>
					<span>手机号：</span><input type="text" id="s_mobile" name="mobile" value="${ mobile}"/>
					</li>
				   <li>
				   	<span>姓名：</span><input type="text" id="s_realname" name="realname" value="${ realname}"/>
				   </li>
					<li>
					<span>固定电话：</span><input type="text" id="s_tel" name="tel" value="${tel }" />
				    </li>
					<li style="width:150px;">
					<button onclick="search();">查询</button>
                  </li>
                  <li style="width:150px;">
					<input class="buttons" type="reset" value="重置"/>
                  </li>
				</form>
				</ul>
				</div>

				<p class="banner-right-p">用户列表</p>
				<div style="overflow: auto; width: 100%;" id="loadingId">
				<script >$("#loadingId").mLoading("show");</script>
				<table cellspacing="0" class="table">
					<tr>
						<th class="banner-right-pleft"></th>
						<th>序号</th>
						<th>手机号</th>
						<th>姓名</th>
						<th>固定电话</th>
						<th>邮箱</th>
						<th>归属网点</th>
						<th>角色</th>
						<th>操作</th>
					</tr>
					<c:if test="${fn:length(page.list)>0}">
					 <c:forEach items="${page.list}" var="user" varStatus="vs">
					<tr>
						<td class="banner-right-pleft"><input type="checkbox" value="${user.userId}"/></td>
						<td>${vs.index+1}</td>
						<td><a class="banner-right-a4" onclick="javascript:goUpdate(${user.userId})">${user.mobile}</a></td>
						<td>${user.realname}</td>
						<td>${user.telephone}</td>
						<td>${user.email}</td>
						<td>
						 <c:set var="netWork" scope="session" value="${user.netWork}"/> 
						<c:if test="${fn:length(user.netWork)>10}">
						 <a onclick="openNetWork('${netWork}')">${fn:substring(netWork, 0, 10)}...</a> 
						</c:if>
						<c:if test="${fn:length(user.netWork)==0&&(user.roleName eq('系统管理员')||user.roleName eq('总经理'))}">
						全部网点
						</c:if>
						<c:if test="${fn:length(user.netWork)<=10}">
						${user.netWork}
						</c:if>
						</td>
						<td>${user.roleName}</td>
						<td><a class="banner-right-a1" href="javascript:deleteOne(${user.userId})">删除</a><a class="banner-right-a2" href="javascript:updatePassword(${user.userId})">修改密码</a></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
				</div>
				 <c:if test="${fn:length(page.list)>0&&fn:length(page.list)!=1}">
				<div id="page" style="text-align: center;">
				</div>
				</c:if>
				
				
				 <c:if test="${fn:length(page.list)==0}">
				 <div style="text-align: center; margin-top: 40px; font-size: 18px; height:100px; line-height:100px;">
				       <p>
				      </p>
				     	暂无数据
				</div>
			</c:if>
				<c:if test="${fn:length(page.list)!=0}">
				<ul class="ul2">
					<li><input type="checkbox" id="selectAll" onclick="selectAll();" />全选</li>
					<li><a href="#" class="banner-right-a3" onclick="deleteAll();">删除</a></li>
				</ul>
				</c:if>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	<script type="text/javascript">

        if($("#msg").val()!==""){
			layer.msg($("#msg").val());
			}
		
		function search(){
			$('#searchForm').submit();
			
		}
		
		function openNetWork(netName){
			 layer.alert(netName);
		}

		
		   //分页
	    layui.use(['laypage'], function(){
	    	var laypage = layui.laypage;
	        //调用分页
	        laypage({
	    	      cont: 'page'
	    	      ,pages: '${page.totalPage}' //得到总页数
	    	      ,curr:'${page.pageNumber}',
	    	       count:'${page.totalRow}'
	    	      ,skip: true
	        	  ,jump: function(obj, first){
	        	      if(!first){
	        	    	   window.location.href="${ctx}/kd/user?pageNo="+obj.curr+"&mobile"+$('s_mobile').val()+"&realname"+$('s_realname').val()+"&tel"+$('s_tel').val();
	        	      }
	        	  }
	        	  ,skin: '#1E9FFF'
	        });
	    });
		   
		function goUpdate(userId){
			//页面层
			 layer.open({
			  type: 2,
			  area: ['550px', '700px'], //宽高
			  content: ['${ctx}/kd/user/goUpdate?userId='+userId],
			  end: function () {
	                location.reload();
	            }
			});
		}
		   
		//更改密码
		function updatePassword(id){
			var content='<laber id="y_password" style="margin-left:30px; padding:10px 0;  background:#CCC">请输入新密码</laber><input type="text" id="password" style="margin-top: 40px;margin-left:10px;height: 28px;width: 200px;"/>';
			
			layer.open({
				  type: 1,
				  title: '修改密码',
				  btn: ['确认', '取消'],
				  area: ['400px', '200px'], //宽高
				  yes: function(index, layero){
					  if($('#password').val().length==0){
						  Anfa.show("密码不能为空","#y_password");
						  return;
					  }else if($('#password').val().length<6){
						  Anfa.show("密码长度过短","#y_password");
						  return;
					  }
					  $.ajax({
							type:"post",
							dataType:"json",
							url:"${ctx}/kd/user/updatePassWord", 
							data:{password:$('#password').val(),userId:id},
							success:function(data){
								layer.alert(data.msg);
								if(data.state=="SUCCESS"){
									parent.layer.closeAll();
									window.location.href="${ctx}/kd/user";
								}
				            }
						});	  
					  },
				  content: content
				});
		}   
		   
		
		//删除单个
		function deleteOne(objs) {
			
			if(objs==$('#y_userId').val()){
				layer.msg('不能删除自己的账号！');
				return;
			}
			
			layer.confirm(
					'您确定要删除？',
					{
						btn : [ '删除', '取消' ]
					},
					function() {
						$.ajax({
							type : "post",
							dataType : "json",
							url : "${ctx }/kd/user/delete?userId="
									+ objs,
							success : function(data) {
								if (data.state == "SUCCESS") {
									window.location.href="${ctx}/kd/user";
								} else {
									layer.msg(data.msg);
								}
							}
						});
			}, function() {
					});
		}
		//删除多个
    	function deleteAll(){
			var array = new Array();
			$(".table input[type='checkbox']").each(function(i){
			if($(this).prop("checked"))array.push($(this).val());
			});
			deleteUser(array);
	}
    		
    		//删除全部
    		function deleteUser(objs) {
    			for(var i=0;i<objs.length;i++){
					if(objs[i]==$('#y_userId').val()){
						layer.msg('不能删除自己的账号！');
						return;
					}    				
    			}
    			
    	    	if(objs==null||objs==""){
    	    		layer.msg("请选择要删除的数据");
    				return;
    			}
    	    	layer.confirm('您确定要删除？', {
    			  	btn: ['确定','取消']
    			}, function(){
    				$.ajax({
    					type : "post",
    					dataType : "json",
    					url : "${ctx }/kd/user/delete?userId="+objs,
    					success : function(data) {
							if (data.state == "SUCCESS") {
								window.location.href="${ctx}/kd/user";
							} else {
								layer.msg(data.msg);
							}
    					}
    				});
    			}, function(){});
    		}


             //全选事件
    		function selectAll() {
    			if($("#selectAll").is(':checked')){
    				$(".table input[type='checkbox']").prop("checked", true);
    			}else{
    				$(".table input[type='checkbox']").prop("checked", false);
    			}
    		}
             
    		setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);

		</script>
	<script type="text/javascript">

        $("table").on('click',function() {
            var CheckBox = $(".banner-right-pleft  input[type='checkbox']");
            CheckBox.each(function () {
                if (!$(this).is(':checked')) {
                    $("#selectAll").prop("checked", false);
                }

            })
        })
	</script>
	</body>
</html>
