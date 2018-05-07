<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>公司文化</title>
		<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/corporate.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/src/loading.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/src/loading.js"></script>
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
				 <div class="conr_title">企业文化</div>
				<div class="content-right-top">
				 
					<p class="p2" style="margin-top:20px;">公司品牌描述 :</p>
					<!--文本编辑器-->
					<div class="p3">
					<textarea name="content1" style="width:840px; height:400px;visibility:hidden;"></textarea>
				   <script>
					   var editor1;
					   KindEditor.ready(function(K) {
							editor1 = K.create('textarea[name="content1"]', {
								resizeType : 0,
								filePostName : "fileName",
								uploadJson : '${ctx}/file/kedit/upload',
								allowFileManager : true,
								items : [
									'source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
									'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
									'insertunorderedlist', '|', 'emoticons', 'table', 'hr' ,'|', 'image']
							});
							editor1.html($("#info1").html());
						});
				   </script>
				   </div>
				   <button class="button1"  onclick="saveCulture(this,'Desc')">提交</button>
				   
				   <p class="p3" style="margin-top:60px;">上传品牌图片 :</p>
				   <img class="p3-img" src="${ctx}/static/pc/img/shiyi.jpg"/>
				   <div class="gtu1">  
				      <div class="gtu2">
				      	<c:if test="${not empty brand0}">
							<a class="example-image-link" href="${brand0}" data-lightbox="example-1"><img class="gtu2-img"  src="${brand0}" /></a>
						</c:if>
						<c:if test="${empty brand0}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png" />
						</c:if> 
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png" />
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第一张</span>
				   </div>
				   
				   <div class="gtu1">  
				      <div class="gtu2">
				       <c:if test="${not empty brand1}">
							<a class="example-image-link" href="${brand1}" data-lightbox="example-1"><img class="gtu2-img"  src="${brand1}" /></a>
						</c:if>
						<c:if test="${empty brand1}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png" />
						</c:if> 
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png" />
				        <a href="javascript:;" class="file">上传电脑中图片
				       
						    <input type="file" name="" id="">
						 </a>  
				      </div>
				      <span>第二张</span>
				   </div>
				   
				   <div class="gtu1">  
				      <div class="gtu2">
				        <c:if test="${not empty brand2}">
							<a class="example-image-link" href="${brand2}" data-lightbox="example-1"><img class="gtu2-img"  src="${brand2}" /></a>
						</c:if>
						<c:if test="${empty brand2}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png" />
						</c:if> 
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png" />
				        <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>  
				      </div>
				      <span>第三张</span>
				   </div>
				    
				 
				   <button class="button1"  onclick="saveImages('brandImages')">提交</button>
				   
				   <p class="p2">公司价值观 :</p>
					<!--文本编辑器--->
					<div class="p3">
					<textarea name="content2" style="width:840px; height:400px;visibility:hidden;"></textarea>
				   <script>
					   var editor2;
					   KindEditor.ready(function(K) {
							editor2 = K.create('textarea[name="content2"]', {
								resizeType : 0,
								filePostName : "fileName",
								uploadJson : '${ctx}/file/kedit/upload',
								allowFileManager : true,
								items : [
									'source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
									'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
									'insertunorderedlist', '|', 'emoticons', 'table', 'hr' ,'|', 'image']
							});
							editor2.html($("#info2").html());
						});
				   </script>
				   </div>
				   <button class="button1" onclick="saveCulture(this,'Jzg')">提交</button>
				   
				   
				   <p class="p2">公司使命 :</p>
					<!--文本编辑器--->
					<div class="p3">
					<textarea name="content3" style="width:840px; height:400px;visibility:hidden;"></textarea>
				   <script>
					   var editor3;
					   KindEditor.ready(function(K) {
							editor3 = K.create('textarea[name="content3"]', {
								resizeType : 0,
								filePostName : "fileName",
								uploadJson : '${ctx}/file/kedit/upload',
								allowFileManager : true,
								items : [
									'source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
									'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
									'insertunorderedlist', '|', 'emoticons', 'table', 'hr' ,'|', 'image']
							});
							editor3.html($("#info3").html());
						});
				   </script>
				   </div>
				   <button class="button1"  onclick="saveCulture(this,'Sm')">提交</button>
				   
				   
				</div>
			<div style="display: none;"  id="info1">
				${shop.culture_desc}
			</div>
			<div style="display: none;"  id="info2">
				${shop.culture_jzg}
			</div>
			<div style="display: none;"  id="info3">
				${shop.culture_sm}
			</div>
		</div>
		</div>
		<!--底部的内容--->
	    <%@ include file="../common/loginfoot.jsp"%>
		<script type="text/javascript">
		var load = new Loading();
		$("[type='file']").bind("change", upload);
		function upload(){
			var img = $(this).parents(".gtu1").find("img")[0];
		    var formData = new FormData(); // FormData 对象
		    var file = $(this)[0].files[0];
		    if(!validate(file)){
		    	return;
		    }
		    formData.append("fileName", file); 
		    showLoading($(this).parents(".gtu1").find(".gtu2"));
			$.ajax({
			  url : "${ctx}/file/uploadImage?imgType=company",
			  type : 'POST',
			  contentType: false,    //不可缺
			  processData: false,    //不可缺
			  data : formData,
			  success:function(data){
				  if(data.success==true){
					  $(img).attr("src",data.src);
					  $(img).wrap("<a class=\"example-image-link\" href='"+data.src+"' data-lightbox=\"example-1\">");
				  }else{
					  layer.msg(data.message);
				  }
				  removeLoading();
			  }
			});
		}
		
		function validate(file){
			if (file == null){  
		        layer.msg("请选择需要上传的文件!");
		        return false; 
		    }
			var	imgName = file.name;
			if (imgName == ''){  
		        layer.msg("请选择需要上传的文件!");
		        return false; 
		    } else {   
		    	 var size = file.size/(1024*1024);  
		    	 if(size>2){
		    		 layer.msg("文件大小不能超过2MB");
		    		 return false;
		    	 }
		        idx = imgName.lastIndexOf(".");   
		        if (idx != -1){   
		            ext = imgName.substr(idx+1).toUpperCase();   
		            ext = ext.toLowerCase( ); 
		            if (ext != 'jpg' && ext != 'png' && ext != 'jpeg'){
		                layer.msg("只能上传.jpg  .png  .jpeg类型的文件!");
		                return false;  
		            }   
		        } else {  
		        	layer.msg("只能上传.jpg  .png  .jpeg类型的文件!");
		            return false;
		        }   
		    }
			return true;
		}
		
		function showLoading(imgName){
			load.init({
				 target: imgName
			});
			load.start();
		}
		
		function removeLoading(){
			load.stop();
		}
		
		//保存首页宣传图片
		function saveImages(type){
			var img = $(".gtu2-img");
			if($(img[0]).attr("src").indexOf("pic-iu.png")>0||$(img[1]).attr("src").indexOf("pic-iu.png")>0||$(img[2]).attr("src").indexOf("pic-iu.png")>0){
				layer.msg("请上传图片");
				return ;
			}
			$.ajax({
			  url : "${ctx}/cpy/admin/"+type,
			  type : 'POST',
			  data : {image1:$(img[0]).attr("src"),image2:$(img[1]).attr("src"),image3:$(img[2]).attr("src")},
			  success:function(data){
				  if(data.success==true){
					  layer.msg("保存成功");
				  }else{
					  $(obj).removeAttr("disabled");
					  layer.msg(data.msg);
				  }
			  }
			});
		}
		
		//保存介绍信息
		function saveCulture(obj, type){
			var message = "";
			if(type=='Desc')message = editor1.html();
			if(type=='Jzg')message = editor2.html();
			if(type=='Sm')message = editor3.html();
			if(message==""){
				layer.msg("请输入要保存的信息");
				return;
			}
			Anfa.ajax({
			  url : "${ctx}/cpy/admin/saveCulture"+type,
			  type : 'POST',
			  data : {culture:message},
			  success:function(data){
				  if(data.success==true){
					  layer.msg("保存成功");
				  }else{
					  $(obj).removeAttr("disabled");
					  layer.msg(data.msg);
				  }
			  }
			});
		}
		$(function(){
		 	lightbox.option({'resizeDuration': 100,'wrapAround': true})
		})
		</script>			
	</body>
</html>

