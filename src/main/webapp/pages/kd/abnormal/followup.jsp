<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签收</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/followup.css" />
   	<%@ include file="../common/commonhead.jsp" %>
   	<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
	<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
</head>
<body>
   <div class="maip">
          <p>异常跟进</p>
          <div class="maip-list">
            <table>
               <tr>
                  <td>运单号 : ${abnormal.ship_sn }</td> 
                  <td>开单网点 : ${abnormal.shipNet }</td>
                  <td>开单日期 : <fmt:formatDate value="${abnormal.shipTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
               </tr>
               <tr>
                  <td>出发地 :${abnormal.fromAddr }</td>
                  <td>到达地 :${abnormal.toAddr }</td>
                  <td>货物名称 :${abnormal.productName }</td>
               </tr>
               <tr>
                  <td colspan="3">总件数 :${abnormal.ship_amount } </td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>登记信息 :</b>
              <table style="height:140px;">
               <tr>
                  <td>登记网点 : ${abnormal.abnormalNet }</td>
                  <td>异常类型 : 
                 <c:if test="${abnormal.abnormal_type==0}">货损</c:if>
                 <c:if test="${abnormal.abnormal_type==1}">少货</c:if>
                 <c:if test="${abnormal.abnormal_type==2}">多货</c:if>
                 <c:if test="${abnormal.abnormal_type==3}">货物丢失</c:if>
                 <c:if test="${abnormal.abnormal_type==4}">货单不符</c:if>
                 <c:if test="${abnormal.abnormal_type==5}">超重超方</c:if>
                 <c:if test="${abnormal.abnormal_type==6}">超时</c:if>
                 <c:if test="${abnormal.abnormal_type==7}">拒收</c:if>
                 <c:if test="${abnormal.abnormal_type==8}">投诉</c:if>
                 <c:if test="${abnormal.abnormal_type==9}">其他</c:if>
                  </td>
                  <td>异常状态 : 
                   <c:if test="${abnormal.abnormal_status==0}">待处理</c:if>
                   <c:if test="${abnormal.abnormal_status==1}">处理中</c:if>
                   <c:if test="${abnormal.abnormal_status==2}">已处理</c:if>
                  </td>
               </tr>
               <tr>
                  <td>登记人 : ${abnormal.realname }</td>
                  <td colspan="2">登记时间 :<fmt:formatDate value="${abnormal.create_time }" pattern="yyyy-MM-dd hh:mm:ss"/> </td>
               </tr>
               <tr>
                  <td colspan="3">异常描述 : ${abnormal.abnormal_desc }</td>
               </tr>
               <c:if test="${fn:length(abnormalImg)>0}">
                <c:forEach items="${abnormalImg}" var="img" varStatus="vs">
                <tr>
                <td colspan="3">
                 <a  href="${img.img}"  rel="lightbox"> <img class="newimgs" src="${img.img }"> </a>
                </td>
                </tr>
                </c:forEach>
                </c:if>
            </table>
          </div>
          <c:if test="${fn:length(handleList)>0}">
              <c:forEach items="${handleList}" var="row" varStatus="vs">
          <div class="maip-list2">
              <b>跟进信息 :</b>
              <table style="height:100px;">
               <tr>
                  <td>跟进人 : ${row.row.realname}</td>
                  <td>跟进时间 :<fmt:formatDate value="${row.row.create_time}" pattern="yyyy-MM-dd hh:mm:ss"/> </td>
               </tr>
               <tr>
                  <td colspan="2">跟进内容 : ${row.row.handle_content}</td>
               </tr> 
               <c:if test="${fn:length(row.img)>0}">
                <c:forEach items="${row.img}" var="img" varStatus="vs">
                <tr>
                <td colspan="2">
                <a  href="${img.img}"  rel="lightbox"> <img class="newimgs" src="${img.img }"></a>
                </td>
                </tr>
                </c:forEach>
                </c:if>
            </table>
            </div>
               </c:forEach>
          </c:if>
          	<form id="handleForm">
          <div style="width:700px;position: relative; overflow: hidden;margin-top: 24px;height: 240px;margin-left: 20px;">
          <b style="color:#3974f8;">跟进信息 :</b><br><br>
          				<input type="hidden" id="u_abnormalId" value="${abnormal.abnormal_id }" name="abnormalHandle.handle_abnormal_id"/>
				       <span>跟进内容 :</span>   <textarea id="handleContent" name="abnormalHandle.handle_content" style="position:absolute;left: 96px;top:46px;width: 600px; height: 190px;"></textarea>
				    </div>
          </form>
				    
        <div class="maip-list3">
          
                   <div class="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				      	<input type="hidden" data="img" name="filename1">
				        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
				         <a href="#ss" id="file">上传电脑中图片
						    <input type="file" name="file" id="">
						 </a>   
				      </div>
				   </div>
          			</div>
          			 <button class="button" onclick="addtr();return false;">继续添加</button>
				   <button class="button" onclick="removetr();return false;" style="margin-left: 15px;">删除</button>
          			<br>
          <button class="buttons" onclick="submitForm();">提交</button> <a href="#" style="margin-left: 15px;">返回</a>
   </div>
 <script type="text/javascript">
 
 var imgs = new Array();
 
 function submitForm(){
	 
	 if( $('#handleContent').val()==""){
			Anfa.show("请填写跟进内容","#handleContent");
			return;
		}
	 
		$.ajax({
			type:'post',
			url:'/kd/abnormal/saveHandle?filename='+imgs.join(),
			data:$("#handleForm").serialize(),
			success:function(data){
				console.log(data);
			 	if(data.success){
			 		layer.msg("跟进成功");
			 		$('#handleContent').val("");
			 		layer.confirm(
							'异常处理是否已完成？',
							{
								btn : [ '标记为已处理完成', '关闭' ]
							},
							function() {
								$.ajax({
									type : "post",
									url : "/kd/abnormal/update?abnormalId="
											+ $('#u_abnormalId').val(),
									success : function(data) {
										if(data.success){
											
											layer.msg("已标记已处理！",{time: 1000},function(){
												parent.layer.closeAll();
						                   });	
											
										
										}
									}
								});
					}, function() {
						parent.layer.closeAll();
							});
			 	}
			}
		});
 }
 
	var otr = $(".gtu1").eq(0).clone();
	function addtr(){
		   var tr = otr.clone();
		   var size = $("input[data='img']").length+1;
		   tr.find("input[data='img']").attr("name", "filename"+size);
		   tr.appendTo(".maip-list3");
		}
	function removetr(){
		var div=$(".gtu1");
		var length=div.length;
		if(length==1){
		}else{
		div[length-1].remove();
		imgs.splice(length-1,1);
		}
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
	
	$(".maip-list3").on("change",  "input[type='file']", upload);
	function upload(){
		var img = $(this).parents(".gtu1").find(".gtu2").find("img");
	    var formData = new FormData(); // FormData 对象
	    var file = $(this)[0].files[0];
	    if(!validate(file)){
	    	return;
	    }
	    formData.append("fileName", file); 
		$.ajax({
		  url : "${ctx}/file/uploadImage?imgType=abnormal",
		  type : 'POST',
		  contentType: false,    //不可缺
		  processData: false,    //不可缺
		  data : formData,
		  success:function(data){
			  if(data.success==true){
				  $(img).attr("src",data.src);
				  $(img).wrap("<a class=\"example-image-link\" href='"+data.src+"' data-lightbox=\"example-1\">");
				  imgs.push(data.src);
				  formData.append('abnormal',data.src);
			  }else{
				  layer.msg(data.message);
			  }
		  }
		});
	}
 </script>  
</body>
</html>