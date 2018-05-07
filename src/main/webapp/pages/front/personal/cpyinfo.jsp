<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>公司概况</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/css/Website.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src/loading.css"/>
		<link rel="stylesheet" href="${ctx}/static/pc/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/lrtk2.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
		
		
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script charset="utf-8" src="${ctx}/static/pc/kindeditor/kindeditor-min.js"></script>
		<script charset="utf-8" src="${ctx}/static/pc/kindeditor/lang/zh-CN.js"></script>
        <script src="${ctx}/static/pc/src/loading.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/static/pc/js/unslider.min.js"></script>
	</head>
	<body>
	    <%@ include file="common/head.jsp"%>
			
		<div class="content">
			<%@ include file="common/left.jsp" %>
			<!--公司网站首页宣传图片--->
			<%-- <img class="content-right-imgs" src="${ctx}/static/pc/img/gkmmmhjk.png"/> --%>
			<div class="content-right">
			<div class="conr_title">公司概况管理</div>
				<p>公司Banner编辑 ( 首页 ) </p>
				<div class="banner_con">
				<div class="banner_left" id="scrolId">
				<div class="content-right-tem" data="scrol">
					<div class="gtu1">  
				      <div class="gtu2">
						<c:if test="${not empty scrl0}">
							<a class="example-image-link" href="${scrl0}" data-lightbox="example-1"><img class="gtu2-img" src="${scrl0}" /></a>
						</c:if>
						<c:if test="${empty scrl0}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
						</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第一张</span>
				   </div>
				</div>
				
				<div class="content-right-tem2" data="scrol">
					<div class="gtu1">  
				      <div class="gtu2">
						<c:if test="${not empty scrl1}">
							<a class="example-image-link" href="${scrl1}" data-lightbox="example-1"><img class="gtu2-img" src="${scrl1}" /></a>
						</c:if>
						<c:if test="${empty scrl1}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
						</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第二张</span>
				   </div>
				</div>
				
				<div class="content-right-tem3" data="scrol">
					<div class="gtu1">  
				      <div class="gtu2">
						<c:if test="${not empty scrl2}">
							<a class="example-image-link" href="${scrl2}" data-lightbox="example-1"><img class="gtu2-img" src="${scrl2}" /></a>
						</c:if>
						<c:if test="${empty scrl2}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
						</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第三张</span>
				   </div>
				</div>
				</div>
			    <div class="content-right-temt">
			    	 <img src="${ctx}/static/pc/img/zx_01.png"/>	    	
			    </div>
			    </div>
			    <p class="p_btn"><button onclick="saveImages(this,'homeImages','scrolId')">保存</button>  </p>  
			
			
			<!--公司简介--->
			<div class="content-tent">
			    <p class="p">公司概况编辑（首页）</p><br>
			    <textarea class="textarea" id="shortInfo" placeholder="概况在首页显示，最多可以显示160字"  maxlength="160">${shortDesc}</textarea>
			    <span class="span">您还可以输入<b style="font-weight: normal;color:red;">160</b>个字</span>
				<p>公司简介编辑</p><br>
				<!--文本编辑器--->
				<textarea name="content" style="width:840px; height:400px;visibility:hidden;"></textarea>
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
								'insertunorderedlist', '|', 'emoticons', 'table', 'hr' ,'|', 'image']
						});
						editor.html($("#info").html());
					});
				 </script>
				
			</div>
			 <p class="p_btn"> <button onclick="saveIntroduction(this)">保存</button> </p>  
			
			<!--形象图片--->
			<div class="content-pictures">
				<p>形象展示编辑 ( 首 页 )</p>
				<!--第一个图片上传的div--->
				<div class="banner_con">
				<div class="banner_left"  id="certId">
				<div class="content-pictures-left"  data="cert">
					<div class="gtu1">  
				      <div class="gtu2">
							<c:if test="${not empty figu0}">
								<a class="example-image-link" href="${figu0}" data-lightbox="example-1"><img class="gtu2-img" src="${figu0}" /></a>
							</c:if>
							<c:if test="${empty figu0}">
								<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
							</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第一张</span>
				   </div>
				</div>
				
				<!--第二个图片上传的div--->
				<div class="content-pictures-left2" data="cert">
					<div class="gtu1">  
				      <div class="gtu2">
						<c:if test="${not empty figu1}">
							<a class="example-image-link" href="${figu1}" data-lightbox="example-1"><img class="gtu2-img" src="${figu1}" /></a>
						</c:if>
						<c:if test="${empty figu1}">
							<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
						</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第二张</span>
				   </div>
				</div>
				
				<!--第三个图片上传的div--->
				<div class="content-pictures-left3" data="cert">
					<div class="gtu1">  
				      <div class="gtu2">
							<c:if test="${not empty figu2}">
								<a class="example-image-link" href="${figu2}" data-lightbox="example-1"><img class="gtu2-img" src="${figu2}" /></a>
							</c:if>
							<c:if test="${empty figu2}">
								<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
							</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <span>第三张</span>
				   </div>
				</div>
				</div>
				<!--右边的说明-->
				<div class="content-explain">
					
					<!-- <b>小提示：</b>
					<p>形象图片是物流公司展示自己的一张名</p>
					<p>片，门头照，作业照，公司对外形象都</p>
					<p>可以作为宣传照，宣传照不能是营业执</p>
					<p>照，身份证照片，物流人员的生活照。</p> -->
					 <img src="${ctx}/static/pc/img/zx-02.png"/>
				</div>
				</div>
				
			</div>
			 <p class="p_btn"> <button  onclick="saveImages(this,'figureImages','certId')">保存</button> </p>  
		</div>
		
		<div style="display: none;"  id="info">
			${introduction}
		</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
	<script>
	$(document).ready(function() {
		  var counter = $(".content-tent textarea").val().length; 
	       $(".span b").text(160 - counter);
	      $(document).keyup(function() {
	          var text = $(".content-tent textarea").val();
	          var counter = text.length;
	          $(".span b").text(160 - counter);
	      });
	  });
	
	var load = new Loading();
	$("[type='file']").bind("change", upload);
	function upload(){
		var img = $(this).parents(".gtu1").find(".gtu2").find("img");
		var imgType = $(this).parents(".gtu1").parent("div").attr("data");
	    var formData = new FormData(); // FormData 对象
	    var file = $(this)[0].files[0];
	    var size = 2;
	    if(imgType=="scrol")size = 5;
	    if(!validate(file,size)){
	    	return;
	    }
	    var typeValue = imgType==null?"cert":imgType; 
	    formData.append("fileName", file); 
	    showLoading($(this).parents(".gtu1").parent("div").find(".gtu2"));
		$.ajax({
		  url : "${ctx}/file/uploadImage?imgType="+typeValue,
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
	
	function validate(file, osize){
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
	    	 if(size>osize){
	    		 layer.msg("文件大小不能超过"+osize+"MB");
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
	function saveImages(obj, type, element){
		var img = $("#"+element).find(".gtu2-img");
		if(!img[0].hasAttribute("src")||!img[1].hasAttribute("src")||!img[2].hasAttribute("src")){
			layer.msg("请上传图片");
			return ;
		}
		if($(img[0]).attr("src").indexOf("pic-iu.png")>0||$(img[1]).attr("src").indexOf("pic-iu.png")>0||$(img[2]).attr("src").indexOf("pic-iu.png")>0){
			layer.msg("请上传图片");
			return ;
		}
		$(obj).attr("disabled", "disabled");
		Anfa.ajax({
		  url : "${ctx}/cpy/admin/"+type,
		  type : 'POST',
		  data : {image1:$(img[0]).attr("src"),image2:$(img[1]).attr("src"),image3:$(img[2]).attr("src")},
		  success:function(data){
			  if (data.success) {
				  layer.msg("保存成功");
			  } else {
				 layer.msg(data.msg);
			  }
			  $(obj).removeAttr("disabled");
		  }
		});
	}
	
	//保存介绍信息
	function saveIntroduction(obj){
		var message = editor.html();
		var shortInfo = $("#shortInfo").val();
		if(message==""||shortInfo==""){
			layer.msg("请输入简介信息");
			return;
		}
		$(obj).attr("disabled", "disabled");
		Anfa.ajax({
		  url : "${ctx}/cpy/admin/saveIntroduction",
		  type : 'POST',
		  data : {introduction:message,shortInfo:shortInfo},
		  success:function(data){
			  if(data.success==true){
				  layer.msg("保存成功");
			  }else{
				  layer.msg(data.message);
			  }
			  $(obj).removeAttr("disabled");
		  }
		});
	}
	 $(function(){
	 	lightbox.option({'resizeDuration': 100,'wrapAround': true})
	})
	</script>
</html>
