<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增角色</title>
		  <link rel="stylesheet" href="${ctx}/static/kd/css/addrole.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	
	</head>
	<body>
	   
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
			    })
	       </script>
			<div class="banner-right">
				
					<div class="banner-right-btn">
					   <ul class="ul2">
							<li><a href="${ctx}/kd/role/">角色列表</a></li>
							<li><a href="add.jsp" class="active1"><span id="title">新增角色</span></a></li>
						</ul>
					</div>
					
				
				<div class="banner-right-content">
				<input type="hidden" value="${type}" id="type"/>
				<input type="hidden" value="${role.id}" id="roleId"/>
					<div class="banner-right-name">
						<label>角色名称：</label>
						<input type="text" id="roleName" value="${role.name}"  disabled="disabled" onblur="checkName();"/>
					</div>
					
					<div >
						<label id="y_tt">权限选择：</label>
						<ul id="tt" class="easyui-tree">
    
						</ul>
						
					</div>
					
				</div>
				<input type="button" value="提交" class="inputs" onclick="add();" />
				
				
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>s
<script type="text/javascript">
if($('#type').val()==""){
	$('#tt').tree({
	    url:'${ctx}/kd/role/getMenuTree',
	    checkbox: true,
	    cascadeCheck:false,
	    onCheck:function(node, checked){
	    	
            var childList = $(this).tree('getChildren',node.target);
			            
            if(childList.length>0){
            	if(checked){
            		for(var i=0;i<childList.length;i++){
                		$('#tt').tree('check', childList[i].target);
                	}
            	}else{
            		for(var i=0;i<childList.length;i++){
                		$('#tt').tree('uncheck', childList[i].target);
                	}
            	}
            }  
          
        }
	});
}


if($('#type').val()=='update'){
	$("#title").html("修改角色");
	$('#tt').tree({
	    url:'${ctx}/kd/role/getCheckMenuTree',
	    checkbox: true,
	    cascadeCheck:false,
	    onCheck:function(node,checked){                 //当点击 checkbox 时触发
	    	 var childList = $(this).tree('getChildren',node.target);
	    		
	            if(childList.length>0){
	            	if(checked){
	            		for(var i=0;i<childList.length;i++){
	                		$('#tt').tree('check', childList[i].target);
	                	}
	            	}else{
	            		for(var i=0;i<childList.length;i++){
	                		$('#tt').tree('uncheck', childList[i].target);
	                	}
	            	}
	            	
	            }                                       
	      }
	});
}

var flag=flag;
function checkName(){
	
	if($('#type').val()==''){
	$.ajax({
		type:"post",
		dataType:"json",
		url:"${ctx}/kd/role/checkRoleName", 
		data:{roleName:$('#roleName').val()},
		async: false,
		success:function(data){
			
			if(data){
				 Anfa.show("公司下该角色名已存在","#roleName");	
				 flag=true;
			}else{
				flag=false;
			}
        }
	});
	}
	
}


function add(){
	
	var nodes = $('#tt').tree('getChecked');
	var checkParent=false;
	for(var i=0;i<nodes.length;i++){
		var partent=$('#tt').tree('getParent',nodes[i].target);
		if(partent!=null){
			if(!partent.checked){
				Anfa.show("选择子菜单后请勾选父级菜单","#roleName");
				return;
			}
		}
	}

	var nodes = $('#tt').tree('getChecked');
	var roleName=$('#roleName').val();
	var ids =new Array();
	for(var i=0;i<nodes.length;i++){
		ids.push(nodes[i].id);
	}
	
	if(roleName.length==0){
		Anfa.show("请输入角色名称","#roleName");
		return;
	}
	if($('#type').val()==''){
	if(flag){
		Anfa.show("公司下该角色名已存在","#roleName");	
		return;
	}
	}
	if(ids.length==0){
		Anfa.show("至少选择一个角色","#y_tt");
		return;
	}
	
	if($('#type').val()==''){
	
	$.ajax({
		type : 'post',
		dataType:'json',
		url : '${ctx }/kd/role/saveRole?menuIds='+ids+'&roleName='+roleName,
		success : function(data) {
			if (data.state == "SUCCESS") {
				window.location.href="${ctx}/kd/role";
			} else {
				layer.msg(data.msg);
			}
		}
	});
	}
	
	if($('#type').val()=='update'){
		$.ajax({
			type : 'post',
			dataType:'json',
			url : '${ctx }/kd/role/updateRole?menuIds='+ids+'&roleName='+roleName+'&roleId='+$('#roleId').val(),
			success : function(data) {
				if (data.state == "SUCCESS") {
					window.location.href="${ctx}/kd/role";
				} else {
					layer.msg(data.msg);
				}
			}
		});
		}

	
	
}

</script>
	</body>
</html>
