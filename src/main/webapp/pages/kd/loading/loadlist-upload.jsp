<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>上传车辆信息</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/departure.css?v=${version}">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src/loading.css"/>
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/src/loading.js"></script>
	</head>
<body>
	<div id="MyDivc" class="white_content">
	    <div class="white_contentc">
	    	<span class="span">车辆照片 :</span>
			<ul class="content-right-tem4">
				<c:forEach items="${libImages}" var="img">
					<li><img src="${img.img}"/></li>
				</c:forEach>
				<c:if test="${fn:length(libImages)==0}">
					<li><img src="${ctx}/static/kd/img/pic-iu.png" data="old"/></li>
				</c:if>
			</ul>
					
		 <div class="content-right-tem2" data="scrol" id="content-right-tem2">
			<div class="gtu1" id="gtu1">  
			      <div class="gtu2">
						<img style="width: 204px;height: 140px;" class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
			      </div>
			      <div class="gtu3">
			        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
			         <a href="javascript:;" id="file">上传电脑中图片
					    <input type="file" name="" id="">
					 </a>   
			      </div>
			   </div>
		</div>
		</div>
		<div class="white_contentcs"></div>
	   <button class="button" onclick="save()">提交</button> 
	</div>
	<script type="text/javascript">
	var load = new Loading();
	var imgs = new Array();
	$("[type='file']").bind("change", upload);
	function upload(){
		var img = $(this).parents(".gtu1").find(".gtu2").find("img");
	    var formData = new FormData(); // FormData 对象
	    var file = $(this)[0].files[0];
	    if(!validate(file)){
	    	return;
	    }
	    formData.append("fileName", file); 
	    var load = new Loading();
	    showLoading($(this).parents(".gtu1").find(".gtu2"), load);
		$.ajax({
		  url : "/file/uploadImage?imgType=truck",
		  type : 'POST',
		  contentType: false,    //不可缺
		  processData: false,    //不可缺
		  data : formData,
		  success:function(data){
			  if(data.success==true){
				  $(img).attr("src",data.src);
				  $(img).wrap("<a class=\"example-image-link\" href='"+data.src+"' data-lightbox=\"example-1\">");
				  imgs.push(data.src);
				  append(data.src);
			  }else{
				  layer.msg(data.message);
			  }
			  removeLoading(load);
		  }
		});
	}
	
	function append(img){
		$(".content-right-tem4").find("img[data='old']").parent("li").remove();
		$(".content-right-tem4").append("<li><img src=\""+img+"\"/></li>");
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
	
	function save(){
		if(imgs.length==0){
			layer.msg("请上传图片");
			return;
		}
		$.ajax({
			type:'POST',
			url:'/kd/loading/attachmentsave',
			data:{filename:imgs.join(),loadId:"${loadId}"},
			success:function(data){
				if (data.success) {
					layer.msg("保存成功");
					imgs = new Array();
					setTimeout(function(){parent.layer.closeAll();}, 1000);
				} else {
					layer.msg(data.msg);
				}
			}
		});
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
	</script>
</body>
</html>