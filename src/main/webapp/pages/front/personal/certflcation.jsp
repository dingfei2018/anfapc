<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>实名认证</title>
<link rel="stylesheet" href="${ctx}/static/pc/Personal/css/certification.css?v=${version}" />
<link rel="stylesheet" href="${ctx}/static/pc/src/loading.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/lrtk2.css?v=${version}" />
<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
<script src="${ctx}/static/pc/js/jquery.js"></script>
<script src="${ctx}/static/pc/src/loading.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
<%@ include file="common/include.jsp"%>
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="content">
		<%@ include file="common/left.jsp"%>
		<div class="content-right">
			<div class="conr_title">实名制认证</div>
		      <p style="text-align: center;"><img class="content-right-imgo" src="${ctx}/static/pc/img/renzheng_3.png" /></p>
			<div class="content-right-rg">
			
			<div class="pic_left">
			    <img class="content-right-rg-img" src="${ctx}/static/pc/img/shen_04.png" />
					<div class="gtu1">  
				      <div class="gtu2">
							<c:if test="${not empty cert0}">
								<a class="example-image-link" href="${cert0}" data-lightbox="example-1"><img class="gtu2-img" src="${cert0}" /></a>
							</c:if>
							<c:if test="${empty cert0}">
								<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
							</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <!-- <span>第一张</span> -->
				   </div>
					</div>
					<div class="pic_right">
			    <img class="content-right-rg-imgs" src="${ctx}/static/pc/img/shen_02.png" />
			</div>
			</div>

			<div class="content-right-rg">
			<div class="pic_left">
					<img class="content-right-rg-img" src="${ctx}/static/pc/img/yingye_01.png" />
					<div class="gtu1">  
				      <div class="gtu2">
							<c:if test="${not empty cert1}">
								<a class="example-image-link" href="${cert1}" data-lightbox="example-1"><img class="gtu2-img" src="${cert1}" /></a>
							</c:if>
							<c:if test="${empty cert1}">
								<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
							</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <!-- <span>第一张</span> -->
				   </div></div>
					<div class="pic_right">
			    <img class="content-right-rg-imgs" src="${ctx}/static/pc/img/yingy_02.png" />
			</div></div>


			<div class="content-right-rg">
			<div class="pic_left">
					<img class="content-right-rg-img" src="${ctx}/static/pc/img/mentou_01.png" />
					<div class="gtu1">  
				      <div class="gtu2">
							<c:if test="${not empty cert2}">
								<a class="example-image-link" href="${cert2}" data-lightbox="example-1"><img class="gtu2-img" src="${cert2}" /></a>
							</c:if>
							<c:if test="${empty cert2}">
								<img class="gtu2-img" src="${ctx}/static/pc/img/pic-iu.png">
							</c:if>
				      </div>
				      <div class="gtu3">
				        <img class="imgs" src="${ctx}/static/pc/img/add00.png">
				         <a href="javascript:;" class="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
				      <!-- <span>第一张</span> -->
				   </div></div>
					<div class="pic_right">
			     <img class="content-right-rg-imgs" src="${ctx}/static/pc/img/mentou_02.png" />
			</div></div>

			<!-- || company.is_certification==0 -->
			<c:if test="${certSize <3}">
				<button onclick="saveImages(this,'certImages')">提交审核</button>
			</c:if>
			<c:if test="${certSize ==3 }">
				<c:if test="${scm.status==1}">
					<button>资质审核中</button>
				</c:if>
				<c:if test="${scm.status==2}">
					<button>资质审核已通过</button>
				</c:if>
				<c:if test="${scm.status==3}">
				
				<p class="not">审核未通过 : ${scm.reason}</p>
					<button onclick="saveImages(this,'certImages')">重新提交审核</button>
				</c:if>
			</c:if>

		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp"%>
</body>
		<script>
		var load = new Loading();
		$("[type='file']").bind("change", upload);
		function upload(){
			var img = $(this).parents(".gtu1").find(".gtu2").find("img");
		    var formData = new FormData(); // FormData 对象
		    var file = $(this)[0].files[0];
		    if(!validate(file)){
		    	return;
		    }
		    
		    formData.append("fileName", file); 
		    showLoading($(this).parents(".gtu1").parent("div").find(".gtu2"));
			$.ajax({
			  url : "${ctx}/file/uploadImage?imgType=cert",
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
		function saveImages(obj, type){
			var img = $(obj).parent(".content-right").find(".gtu2-img");
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
				  if(data.success==true){
					  layer.msg("保存成功");
					  setTimeout(function(){
						  location.reload();
					 }, 1000);
				  }else{
					  layer.msg(data.msg);
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
