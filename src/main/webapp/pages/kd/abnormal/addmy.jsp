<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登记异常</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/register.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
	</head>

	<body>
		<!-- 头部文件 -->
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>

		<div class="banner">
			<!-- 左边菜单 -->
			<%@ include file="../common/abnormalleft.jsp" %> 
              <script type="text/javascript">
			     $(function(){
					  var _width=$("body").width();
					  var _widths = $(".banner-left").width();
					  var _widthd = _width - _widths - 80;
					  parseInt(_widthd)
					  $('.banner-right').css('width',_widthd+'px');
			     });
			     $(window).resize(function(){ 
			    	  var Width = $(window).width();
		    	      var _widths = $(".banner-left").width();
			  		  var _widthd = Width - _widths - 80;
			  		  parseInt(_widthd)
			  		  $('.banner-right').css('width',_widthd+'px');
			     });
	          </script>
			<div class="banner-right">
				<ul>
					<li>
						<a href="${ctx}/kd/abnormal/myabnormalIndex" class="at" >我登记的异常</a>
					</li>
					<li>
						<a href="${ctx}/kd/abnormal/addmy" class="active at">登记异常</a>
					</li>
				</ul>

				<div class="banner-right-list">
				<form id="abnormalForm" onsubmit="return false;">
				<input type="hidden" id="chooseShipId" name="abnormal.ship_id"/>
				    <table>
				    <tr>
				     <td>				   
				    <span style="margin-left: 38px;">运单号:</span>
				    <input type="text" disabled="disabled" id="c_shipSn" /><button onclick="openChooseShip();return false;">选择</button>
					</td>
					<td>
				   	<span style="margin-left: -24px;">运单开单网点:</span>
				    <input class="banner-right-input2" type="text" id="c_shipNet" disabled="disabled">
				    </td>
				          <td>出发地 : <input type="text" id="c_fromAdd" disabled="disabled"></td>
				      </tr>
				       <tr>      
				          <td>到达地 : <input type="text" id="c_toAdd" disabled="disabled"></td>
					     	<td>开单时间 : <input type="text" id="c_create_time" disabled="disabled"></td>
				      				          
				          <td><span class="spant">货物名称 : </span><input type="text" id="c_productName" disabled="disabled"></td>
				         </tr>
				       <tr>   
				           <td>总件数 : <input type="text" id="c_ship_amount" disabled="disabled"></td>
				       </tr>
				    </table>
				    
				    <span class="span">登记网点 :</span><select id="net" name="abnormal.network_id" class="select">
				       <c:forEach var="work" items="${userNetworks}">
                                <option value="${work.id}">${work.sub_network_name}</option>
                       </c:forEach>
				    </select>
				    
				    <span class="spanst">异常类型 :</span>
				     <select class="select" name="abnormal.abnormal_type" id="abnormalType">
					 <option value="">请选择 </option>
					<option value="0">货损</option>
					<option value="1">少货</option>
					<option value="2">多货</option>
					<option value="3">货物丢失</option>
					<option value="4">货单不符</option>
					<option value="5">超重超方</option>
					<option value="6">超时</option>
					<option value="7">拒收</option>
					<option value="8">投诉</option>
					<option value="9">其他</option>
				    </select>
				    <div style="width:700px;position: relative; overflow: hidden;margin-top: 30px;height: 300px;margin-left: 170px;"> 
				       <span>异常描述 :</span> <textarea id="desc" name="abnormal.abnormal_desc" style="position:absolute;left: 96px;top: 0px;width: 600px; height: 289px;"></textarea>
				    </div>
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
          <button class="buttons" onclick="submitForm();return false;">提交</button>
          </form>
				</div>
				</div>
			</div>
			<%@ include file="../common/loginfoot.jsp" %>
	<script type="text/javascript">
	var imgs = new Array();
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
	
	function openChooseShip(){
		//页面层
        layer.open({
            type: 2,
            area: ['1200px', '700px'], //宽高
            content: ['/kd/abnormal/chooseShip', 'yes'],
            end:function (){
            	var shipId=$('#chooseShipId').val();
            	$.ajax({
    				type:'post',
    				url:'/kd/waybill/getShipByShipId?shipId='+shipId,
    				success:function(data){
    				console.log(data);
    				$('#c_shipSn').val(data.ship_sn);
    				$('#c_fromAdd').val(data.fromAdd);
    				$('#c_toAdd').val(data.toAdd);
    				$('#c_create_time').val(data.create_time);
    				$('#c_productName').val(data.productName);
    				$('#c_ship_amount').val(data.ship_amount);
    				$('#c_shipNet').val(data.netWorkName);
    				}
    			});
            }
        });
	}
	
	function submitForm(){
		
		if($("#c_shipSn").val()==""){
			Anfa.show("请选择运单","#c_shipSn");
			return;
		}
		if($("#abnormalType").val()==""){
			Anfa.show("请选择异常类型","#abnormalType");
			return;
		}
		if($("#net").val()==""){
			Anfa.show("请选择登记网点","#net");
			return;
		}
		if($("#desc").val()==""){
			Anfa.show("请填写异常描述","#desc");
			return;
		}
		$.ajax({
			type:'post',
			data:$("#abnormalForm").serialize(),
			url:'/kd/abnormal/save?filename='+imgs.join(),
			success:function(data){
			console.log(data);
			if(data.success){
				layer.msg("登记成功！",{time: 1000},function(){
						 window.location.href="${ctx}/kd/abnormal/myabnormalIndex";
                   });
			}else{
				layer.msg("登记失败！");
			}	
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
	
	var imgs = new Array();
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