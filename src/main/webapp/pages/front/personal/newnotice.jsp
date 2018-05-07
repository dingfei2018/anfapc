<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增公告</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/newcement.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/kindeditor/themes/default/default.css" />
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	 	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<%@ include file="common/include.jsp"%>
		
	</head>
	<body>
	<%@ include file="common/head.jsp"%>
	<!--中间内容的部分-->
		
	<div class="content">
		<%@ include file="common/left.jsp"%>
			<div class="content-right">
			<ul>
			   <li style="margin-right: 40px;"><a class="active" href="${ctx}/cpy/admin/notice">新增公告</a></li>
			   <li><a class="" href="${ctx}/cpy/admin/noticeList">公告管理</a></li>
			</ul>
				<div class="content-right-top">
					<input type="hidden" id="id" value="${news.id}">
					<p class="p2">公告描述 :</p>
					<!--文本编辑器--->
					<div class="p3">
					<textarea name="content" id="content" style="width:840px; height:150px;" maxlength="60">${news.content}</textarea>
				    <span class="span">您还可以输入<b>60</b>个字</span>
				   </div>
				  
				  <!--  {minDate:'%y-%M-{%d+1}'} -->
				   <p class="p4">公告有效期 :</p><input class="input" type="text" value="<fmt:formatDate value="${news.valided}" type="date"/>" onClick="WdatePicker()" id="time" placeholder="有效时间">
				 
				</div>
				<p class="p_btn"><button onclick="save()">提交</button></p>
		</div>
		</div>
		<!--底部的内容--->
			<%@ include file="../common/loginfoot.jsp"%>
		<script type="text/javascript">
		
		$(document).ready(function() {
			  var counter = $(".content-right textarea").val().length; 
		     $(".span b").text(60 - counter);
		    $(document).keyup(function() {
		        var text = $(".content-right textarea").val();
		        var counter = text.length;
		        $(".span b").text(60 - counter);
		    });
		});
		
		//保存
		function save(){
			var time = $("#time").val();
			var content = $("#content").val();
			if(time==""||content==""){
				layer.msg("请描述公告和有效时间");
				return;
			}
			var syc = true;
			if(syc){
				syc = false;
				Anfa.ajax({
				  url : "${ctx}/cpy/admin/saveNotice",
				  type : 'POST',
				  data : {time:time,content:content,id:$("#id").val()},
				  success:function(data){
					  syc = true;
					  if(data.success==true){
						  layer.msg("保存成功");
						  setTimeout(function(){
							  window.location.href="${ctx}/cpy/admin/noticeList";
						  },1000);
					  }else{
						  layer.msg(data.msg);
					  }
				  },
				  error:function(data){
					  syc = true;
				  }
				});
			}
		}
		</script>				
	</body>
</html>

