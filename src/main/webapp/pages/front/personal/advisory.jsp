<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增资讯</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/advisory.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/kindeditor/themes/default/default.css" />
		
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script charset="utf-8" src="${ctx}/static/pc/kindeditor/kindeditor-min.js"></script>
		<script charset="utf-8" src="${ctx}/static/pc/kindeditor/lang/zh-CN.js"></script>
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
				   <li style="margin-right: 40px;"><a class="active" href="${ctx}/cpy/admin/advisory">新增资讯</a></li>
				   <li><a class="" href="${ctx}/cpy/admin/advisoryList">资讯管理</a></li>
				 </ul>
			
				<div class="content-right-top">
					<p class="p1">资讯标题 :</p>
					<input type="hidden" id="id" value="${news.id}">
					<input class="input1" type="text" id="title" value="${news.title}" maxlength="145">
					<p class="p2">资讯内容 :</p>
					<!--文本编辑器--->
					<div class="p3">
					<textarea name="content" id="content" style="width:840px; height:400px;visibility:hidden;"></textarea>
				   <script>
					   var editor;
					   KindEditor.ready(function(K) {
							editor = K.create('textarea[name="content"]', {
								resizeType : 0,
								filePostName : "fileName",
								uploadJson : '${ctx}/file/kedit/upload',
								allowFileManager : true,
								items : [
									'source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
									'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
									'insertunorderedlist',  '|', 'emoticons', 'table', 'hr' ,'|', 'image']
							});
							editor.html($("#info").html());
						});
				   </script>
				   </div>
				   <button onclick="save()">提交</button>
				   <div style="display: none;"  id="info">
						${news.content}
				   </div>
				</div>
		</div>
		</div>
		<!--底部的内容--->
		<%@ include file="../common/loginfoot.jsp"%>
		<script type="text/javascript">
		//保存
		function save(){
			var title = $("#title").val();
			var content = editor.html();
			if(title==""||content==""){
				layer.msg("请输入标题和内容");
				return;
			}
			var syc = true;
			if(syc){
				syc = false;
				Anfa.ajax({
				  url : "${ctx}/cpy/admin/saveAdvisory",
				  type : 'POST',
				  data : {title:title,content:content,id:$("#id").val()},
				  success:function(data){
					  syc = true;
					  if(data.success==true){
						  layer.msg("提交成功");
						  setTimeout(function(){
							  window.location.href="${ctx}/cpy/admin/advisoryList";
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

