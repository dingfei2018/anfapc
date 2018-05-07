<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>签收信息</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/tanceng.css?v=${version}">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src/loading.css"/>
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/src/loading.js"></script>
		<script src="${ctx}/static/common/js/common.js"></script>
	</head>
<body>
<div id="fade2" class="black_overlay2"></div>
<div id="MyDiv2" class="white_content2">
	<!--<div class="white_contents">
	新增收货方
	<span class="white_contents-span"></span>
</div>-->

        <div class="banner-list5-div1">
           	<p class="banner-right-p">运单签收</p>
           	<ul>
           <li>
				<p>运单号：<span>${ship.ship_sn}</span></p>
			</li>
			<li>
				<p>收货方：<span>${receivecustomer.customer_name}</span></p>
			</li>
			<li>
				<p>收货方电话：<span>${receivecustomer.customer_mobile}</span></p>
			</li>
           	</ul>  
           </div>
           
           <div class="banner-right-list3">
           	 <p class="banner-right-p">签收信息</p>
           	 <form onsubmit="return false;" id="searchFrom">
           	 	<div class="banner-right-list3-padding">
	           	 	<input type="hidden" name="shipId" value="${ship.ship_id}"/>
	            	<span>签收人：</span><input type="text" name="signName" id="signName" value="${receivecustomer.customer_name}"/><span class="red">*</span>
					<span>身份证号码：</span><input type="text" name="idNumber" id="idNumber"/>
					<span>签收时间：</span><input type="text" name="signTime"  id="signTime" value="${time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><span class="red">*</span><br />
					<span>已收：代收货款 <span>${ship.ship_agency_fund}</span>元 
					<c:if test="${ship.ship_agency_fund>0}">
					<input name="hasproxy" value="1" class="banner-list3-bot" type="checkbox"/>确认
					</c:if>
					</span>
					<span>已收：到付货款 <span>
						${ship.ship_fee}
					</span>元 
						<c:if test="${ship.ship_pay_way==3&&ship.ship_fee>0}">
						<input  name="arrivepay"  class="banner-list3-bot" type="checkbox" value="1" />确认
						</c:if>
					</span>
					
					<div class="banner-right-ps">
						<p>备注：</p>
						<textarea name="remark"></textarea>
					</div>
					<div class="content-right-tem2" data="scrol">
						<span>签收照片 :</span>
					<div class="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				      	<input type="hidden" data="img" name="filename1">
				        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
				         <a href="javascript:;" id="file">上传电脑中图片
						    <input type="file" name="" id="">
						 </a>   
				      </div>
	   				</div>
	   				
					</div>
				</div>
				</form>
				<br />
				<button class="banner-right-list3-button" onclick="addtr()">继续添加</button>
				 <button class="button-sure" onclick="sign()">确认签收</button>
 </div>

</div>
</body>
<script>
var load = new Loading();
var otr = $(".gtu1").eq(0).clone();
function addtr(){
   var tr = otr.clone();
   var size = $("input[data='img']").length+1;
   tr.find("input[data='img']").attr("name", "filename"+size);
   tr.appendTo(".content-right-tem2");
}

$(".content-right-tem2").on("change",  "input[type='file']", upload);

function upload(){
	$this = this;
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
			  $($this).parents(".gtu1").find("input[type='hidden']").val(data.src);
		  }else{
			  layer.msg(data.message);
		  }
		  removeLoading(load);
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

function sign(){
	var signName = $("#signName").val();
	var idNumber = $("#idNumber").val();
	var signTime = $("#signTime").val();
	var flag = false;
    if(signName == ""){
	    Anfa.show("请输入签收人姓名","#signName");
        flag = true;
    }
    if(idNumber!=""&&!IdentityCodeValid(idNumber)){
	    Anfa.show("请输入正确的签收人身份证号","#idNumber");
	     flag = true;
    }
    if(signTime == ""){
	    Anfa.show("请选择时间","#signTime");
	    flag = true;
    }
    if(flag){
	    return;
    }
	var param = $("#searchFrom").serialize();
	$.ajax({
		type:'GET',
		url:'/kd/sign/signShip',
		data:param,
		success:function(data){
			if(data.success==true){
				layer.msg("签收成功");
				setTimeout(function(){parent.layer.closeAll();}, 1000);
			}else{
				layer.msg("签收失败");
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
</html>
