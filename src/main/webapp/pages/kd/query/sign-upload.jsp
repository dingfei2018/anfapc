<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>签收照片信息</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/departure.css?v=${version}">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src/loading.css"/>
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/src/loading.js"></script>
	</head>
<body>
<div id="MyDivc" class="white_content">
    <div class="white_contentc">
    	<div class="banner-sign-imfo">
	    	<span class="span">运单信息</span>
	    	<ul>
	    		<li>运单号：${ship.ship_sn}</li>
	    		<li>开单网点：${ship.netWorkName}</li>
	    		<li>出发地：${ship.fromAdd}</li>
	    		<li>到达地：${ship.toAdd}</li>
	    		<li>托运方：${ship.senderName}</li>
	    		<li>收货方：${ship.receiverName}</li>
	    		<li>运单状态： <c:if test="${ship.ship_status==1}">
						出库中
					</c:if>
					<c:if test="${ship.ship_status==2 || ship.ship_status==3}">
						运输中
					</c:if>
					<c:if test="${ship.ship_status==4}">
						已签收
					</c:if></li>
	    		<li>客户单号：${ship.ship_customer_number}</li>
	    		<li>开单日期：<fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd"/></li>
	    		<li>体积/方：${ship.ship_volume}</li>
	    		<li>重量/公斤：${ship.ship_weight}</li>
	    		<li>件数：${ship.ship_amount}</li>
	    		
	    	</ul>
	   </div>	
	    <div class="banner-sign-pic">
	    	<span class="span">签收照片 :</span>
			<ul class="content-right-tem4">
				<c:forEach items="${libImages}" var="img">
					<li><img src="${img.img}"/></li>
				</c:forEach>
				<c:if test="${fn:length(libImages)==0}">
					<li><img src="${ctx}/static/kd/img/pic-iu.png" data="old"/></li>
				</c:if>
			</ul>
		</div>				
	<%-- 	 <div class="content-right-tem2" data="scrol" id="content-right-tem2">
			<div class="gtu1" id="gtu1">  
			      <div class="gtu2">
						<img class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
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
   <button class="button" onclick="save()">提交</button> --%>
</div>
<!-- <script type="text/javascript">
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
	  url : "/file/uploadImage?imgType=sign",
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
		url:'/kd/query/signshipimg',
		data:{filename:imgs.join(),shipId:"${shipId}"},
		success:function(data){
			if (data.success) {
				layer.msg("保存成功");
				imgs = new Array();
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

</script> -->
</body>
</html>