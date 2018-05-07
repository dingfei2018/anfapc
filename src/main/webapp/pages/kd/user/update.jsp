<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<%boolean flag=false; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/userlist2.css" />
<script src="${ctx}/static/common/js/jquery.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/layui/layui.js"></script>
</head>
<body>
<div >
					<input type="hidden" id="userId" value="${user.userid }">
					<input type="hidden" id="oldmobile" value="${user.mobile }">
					<table>
					<tr>
					<td><label class="banner-update-title1">手机号：</label><input class="title-input" type="text" onblur="checkMobile(this.value);"  id="mobile" value="${user.mobile }"></td>
					<td><label class="banner-update-title2">姓名：</label><input class="title-input" type="text"  id="realname" value="${user.realname }"/></td>
					</tr>
					<tr>
					<td><label class="banner-update-title2">固定电话：</label><input class="title-input" type="text"  id="telephone" value="${user.telephone }"/></td>
					<td><label class="banner-update-title2">邮箱：</label><input class="title-input" type="text"  id="email" value="${user.email }"/></td>
					</tr>
					<tr class="banner-update-tr">
					<td class="banner-update-net">归属网点：(可多选)</td>
					<td class="netids">
					<ul ><input type="hidden" value="${AllNetFlag}" id="allNetFlag"/>
						<c:if test="${AllNetFlag}"><li><input type="checkbox" <c:if test="${fn:length(netWorkList)==fn:length(netIds)}"> checked="checked" </c:if> id="selectAll" onclick="onAll();"/><span id="y_netWork">所有网点</span></li><br /></c:if>
							<c:if test="${fn:length(netWorkList)>0}">
					 		<c:forEach items="${netWorkList}" var="netWork" varStatus="vs">
					 		<c:set value="1" var="flag"  />
					 		
					 		<c:forEach items="${netIds}" var="id" varStatus="vs">
					 		<c:choose>
					 			<c:when test="${id.id==netWork.id }">
					 			<c:set value="2" var="flag"  />
					 			</c:when>
					 		</c:choose>
					 		</c:forEach>
					 		
					 		<c:choose>
            				<c:when test="${flag==2}">
            					<li><input type="checkbox" onclick="check();" value="${netWork.id}" checked="checked"/><span>${netWork.sub_network_name}</span></li>
        	 				</c:when>
            				<c:otherwise>
            					<li><input type="checkbox" onclick="check();"  value="${netWork.id}"/><span>${netWork.sub_network_name}</span></li>
         					</c:otherwise>
        					</c:choose>
					 		
					 		</c:forEach>
					 		</c:if>
						</ul>
					
					</td>
					</tr>
					<tr class="banner-update-tr">
					<td class="banner-update-role" id="role">角色：</td>
					<td class="roleids"> 
					<ul>
						<c:if test="${fn:length(roleList)>0}">
					 		<c:forEach items="${roleList}" var="role" varStatus="vs">
					 		
					 		<c:set value="1" var="rflag"  />
					 		<c:forEach items="${roleIds}" var="id" varStatus="vs">
					 		
					 		<c:choose>
					 			<c:when test="${id.id==role.roleId }">
					 			<c:set value="2" var="rflag"  />
					 			</c:when>
					 		</c:choose>
					 		</c:forEach>
					 		<c:choose>
            				<c:when test="${rflag==2}">
            					<li><input type="checkbox" checked="checked"  value="${ role.roleId}"/><span>${role.roleName }</span></li>
        	 				</c:when>
            				<c:otherwise>
            					<li><input type="checkbox"  value="${ role.roleId}"/><span>${role.roleName }</span></li>
         					</c:otherwise>
        					</c:choose>
							</c:forEach>
						</c:if>
					</ul>
					</td>
					</tr>
					<tr>
					 <td><input  class="button-save" type="button" value="保存" onclick="update();"/></td>
                    <td><input  class="button-cancel" type="button" value="取消" onclick="parent.layer.closeAll();"/></td>
					</tr>
					</table>
          </div>
          <script type="text/javascript">
		         var flag=true;
		          
		         function update(){
		        	 if($('#oldmobile').val()==$('#mobile').val()){
		 				flag=false;
		 			}
		        	var userId=$('#userId').val();
		        	var mobile=$('#mobile').val();
		  			var realname=$('#realname').val();
		  			var telephone=$('#telephone').val();
		  			var email=$('#email').val();
		  			
		  			reg = /(^0\d{2,3}\-\d{7,8}$)|(^1[3|4|5|6|7|8][0-9]{9}$)/;
		  			
		  			if(!(reg.test(mobile))){
		      			  Anfa.show("手机输入错误","#mobile");
		      		    return;
		      		}
		  			
		  			if(flag){
		  				Anfa.show("该手机已注册","#mobile");
		  				return;
		  			}
		  			
		  			if(realname.length==0){
		  				Anfa.show("姓名不能为空","#realname");
		  				return;
		  			}
		  			var all=$('#allNetFlag').val()=='false'?true:false;
		  			//选中网点idarray
		  			var netArray = new Array();
		  			$(".netids input[type='checkbox']").each(function(i){
		  				if(i!=0||all){
		  			if($(this).prop("checked"))netArray.push($(this).val());
		  				}
		  			});
		  			
		  			//选中角色idarray
		  			var roleArray = new Array();
		  			$(".roleids input[type='checkbox']").each(function(i){
		  			if($(this).prop("checked"))roleArray.push($(this).val());
		  			});
		  			console.log(roleArray);
		  			
		  			if(netArray.length==0){
		  				Anfa.show("至少选择一个网点","#y_netWork");
		  				return;
		  			}
		  			if(roleArray.length==0){
		  				Anfa.show("至少选择一个角色","#role");
		  				return;
		  			}
		  			
		  			netArray=netArray.toString();
		  			roleArray=roleArray.toString();
		  			
		  			$.ajax({
		  					type:"post",
		  					dataType:"json",
		  					url:"${ctx}/kd/user/update", 
		  					data:{userId:userId,mobile:mobile,realname:realname,telephone:telephone,email:email,netWorkIds:netArray,roleIds:roleArray},
		  					success:function(data){
		  						console.log(data);
		  						if(data.state=="SUCCESS"){
		  							parent.layer.closeAll();
		  						}else{
		  							layer.msg(data.msg);
		  						}
		  		            }
		  				}); 
		         }
		         
		 		
		 		function checkMobile(mobile){
		 			$.ajax({
		 				type:"post",
		 				dataType:"json",
		 				url:"${ctx}/kd/user/checkMobile", 
		 				data:{mobile:mobile},
		 				success:function(data){
		 					if(!data&&$('#oldmobile').val()!=$('#mobile').val()){
		 						 Anfa.show("该手机已注册","#mobile");	
		 					}else flag=false;
		 					if($('#oldmobile').val()!=$('#mobile').val()){
		 						flag=false;
		 					}
		 	            }
		 			});
		 		}
		         
		          //全选事件
		  		function onAll(){
		  			if($("#selectAll").is(':checked')){
		  				$(".netids input[type='checkbox']").prop("checked", true);
		  			}else{
		  				$(".netids input[type='checkbox']").prop("checked", false)
		  			}
		  		}
		  		 
		  		function check(){
		  			var check=$(".netids input[type='checkbox']");
		  			for(var i=1;i<check.length;i++){
		  				if(check[i].checked==false){
		  					$("#selectAll").prop("checked", false);
		  					return;
		  				}
		  			}
		  			$("#selectAll").prop("checked", true);
		  		}
          </script>
</body>
</html>