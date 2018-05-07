<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增车辆录入</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/addcar.css" />
	    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	</head>
	<body>
    <%@ include file="../common/head2.jsp" %>
	 <%@ include file="../common/head.jsp" %>
		<div class="banner">
		 <%@ include file="../common/basezlleft.jsp" %>
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
		 <input type="hidden" id="truck_type" value="${truck.truck_type}" />
		 <input type="hidden" id="truck_hired_mode" value="${truck.truck_hired_mode}" />
		 <input type="hidden" id="type" value="${type}" />
		
			<div class="banner-right">
					<ul>
						<li><a href="${ctx}/kd/truck">车辆列表</a></li>
						<li><a href="${ctx}/kd/truck/add" class="active"><span id="title">新增车辆</span></a></li>
					</ul>
				
				<div class="banner-right-content">
					<form id="truckForm" action="${ctx }/kd/truck/saveTruck">
					 <input type="hidden" name="truck.truck_id" value="${truck.truck_id}" />
					 <input type="hidden" name="filename" value="" id="fileImg" />
					<div class="banner-right-num">
						<p>车牌号：</p>
						<input type="text" name="truck.truck_id_number" id="truckIdNumber" onblur="checkTruckNumber();" value="${truck.truck_id_number}" />
						<span>*</span>
					</div>
					<div class="banner-right-type">
						<p id="y_truckTypeSelect">车厢类型：</p>
						<select name="truck.truck_type" class="easyui-combobox" id="truckTypeSelect">
								<option value="0">请选择</option>
								<option value="1">厢式货车</option>
								<option value="2">面包车</option>
								<option value="3">金杯车(高顶)</option>
								<option value="4">金杯车(低顶)</option>
								<option value="5">中巴货车</option>
								<option value="6">高栏车</option>
								<option value="7">低栏车</option>
								<option value="8">平板车</option>
								<option value="9">高低板车</option>
								<option value="10">半挂车</option>
								<option value="11">自卸车</option>
								<option value="12">冷藏车</option>
								<option value="13">保温车</option>
								<option value="14">罐式车</option>
								<option value="15">铁笼车</option>
								<option value="16">集装箱运输车</option>
								<option value="17">轿车运输车</option>
								<option value="18">大件运输车 </option>
								<option value="19">起重车</option>
								<option value="20">危险品车</option>
								<option value="21">爬梯车 </option>
								<option value="22">中栏车 </option>
								<option value="23">全挂车 </option>
								<option value="24">加长挂车 </option>
						</select>
						<span class="span">*</span>
					</div>
					<div class="banner-right-length">
						<p class="banner-right-length-p">车厢长度(米)：</p>
						<input type="text" id="truckLength" placeholder="输入数字，可以为小数，例如9.6" id="truckLength" name="truck.truck_length" value="${ truck.truck_length}" />
						<span>*</span>
					</div>
					<div class="banner-right-way">
						<p>租赁方式：</p>
						<select name="truck.truck_hired_mode" id="truckHiredModeSelect">
								<option value="0">请选择</option>
								<option value="1">自有车辆</option>
								<option value="2">外包车辆</option>
								<option value="3">临时车辆</option>
						</select>
						<span>*</span>
					</div>
					<div class="banner-right-name">
						<p>司机姓名：</p>
						<input type="text" id="truckDriverName" name="truck.truck_driver_name" value="${ truck.truck_driver_name}" />
					    <span>*</span>
					</div>
					
					<div class="banner-right-tel">
						<p>司机电话：</p>
						<input type="text" id="truckDriverMobile" name="truck.truck_driver_mobile" value="${truck.truck_driver_mobile}"/>					
					    <span>*</span>
					</div>
					
					<div class="banner-right-card">
						<p>司机证件号：</p>
						<input type="text" id="turnkDriverIdNumber" name="truck.truck_driver_id_number" value="${truck.truck_driver_id_number}"/>
					</div>
					<div class="banner-right-carname">
						<p>车主姓名：</p>
						<input type="text"  id="truckOwnerName" name="truck.truck_owner_name" value="${ truck.truck_owner_name}"/>
					</div>
						<div class="banner-right-carname">
						<p>车主电话：</p>
						<input type="text"  id="truckOwnerMobile" name="truck.truck_owner_mobile" value="${truck.truck_owner_mobile }"/>
					</div>
					<div class="banner-right-carid">
						<p>车主证件号：</p>
						<input type="text" id="" name="truck.truck_owner_id_number" value="${truck.truck_owner_id_number }"/>
					</div>
					<div class="banner-right-ps">
						<p>备注：</p>
						<textarea name="truck.truck_remark">${ truck.truck_remark}</textarea>
					</div>
					<div class="content-right-tem2" data="scrol">
						<span>车辆资料： </span>
				<div class="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img" src="${ctx}/static/kd/img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				      	<input type="hidden" data="img" name="filename1">
				        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
				         <a href="javascript:;" id="file">上传电脑中图片
						    <input type="file" name="file" id="">
						 </a>   
				      </div>
				   </div>
				</div>
					 <button class="banner-right-list3-button" onclick="addtr();return false;">继续添加</button>

					 <input type="button" value="提交" class="inputs" onclick="submitForm();" />
					 </form>
				</div>
				
			</div>
		</div>
		
		<%@ include file="../common/loginfoot.jsp" %>
        <script type="text/javascript">
        var otr = $(".gtu1").eq(0).clone();
		function addtr(){
		   var tr = otr.clone();
		   var size = $("input[data='img']").length+1;
		   tr.find("input[data='img']").attr("name", "filename"+size);
		   tr.appendTo(".content-right-tem2");
		}
		
		if($("#type").val()=="update"){
			$("#title").html("修改车辆");
			$("#truckTypeSelect").val($("#truck_type").val());
			$("#truckHiredModeSelect").val($("#truck_hired_mode").val());
			}
		
		function checkTruckNumber(){
			var flag=false;
			if($("#type").val()!="update"){
			$.ajax({
				  url : "${ctx}/kd/truck/checkTruckNumber?truckNumber="+$('#truckIdNumber').val(),
				  type : 'POST',
				  dataType:'json',
				  async: false,
				  success:function(data){
					  if(data){
						  flag= false;
					  }else{
						  Anfa.show("公司下该车牌号已存在","#truckIdNumber"); 
						  flag= true;
					  }
				  }
				});
			}
			
			return flag;
			
		}
			
		/*表单提交*/
		function submitForm(){
			
			if($("#truckIdNumber").val()==""){
				Anfa.show("请填写车牌号","#truckIdNumber");
				return;
			}else if($("#truckIdNumber").val().length>12){
				Anfa.show("请填写正确的车牌号","#truckIdNumber");
				return;
			}
			if($("#type").val()!="update"){
			if(checkTruckNumber()){
				 Anfa.show("公司下该车牌号已存在","#truckIdNumber"); 
				 return;
			}
			}
			 var truckTypeSelect=$('#truckTypeSelect').combobox('getValue');
			
			if(truckTypeSelect==0){
				Anfa.show("请选择车辆类型 ","#y_truckTypeSelect");
				return;
			}
			
			if($("#truckLength").val().length==0){
				Anfa.show("请填写车辆长度","#truckLength");
				return;
			}
			var numTest=/(^[1-9]\d*\.\d*$)|(^\d+$)/;
			if(!numTest.test($("#truckLength").val())){
				Anfa.show("请输入非负数数字类型的正确高度","#truckLength");
				return;
			}
			if($("#truckHiredModeSelect").val()==0){
				Anfa.show("请选择租赁方式","#truckHiredModeSelect");
				return;
			}
			
			
			if($("#truckDriverName").val()==""){
				Anfa.show("请填写司机姓名","#truckDriverName");
				return;
			}else if($("#truckDriverName").val().length>15){
				Anfa.show("请填写正确的司机姓名","#truckDriverName");
				return;
			}
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if($("#truckDriverMobile").val()==""){
				Anfa.show("请填写司机电话","#truckDriverMobile");
				return;
			}else if($("#truckDriverMobile").val().length>11){
				Anfa.show("请填写正确的司机电话","#truckDriverMobile");
				return;
			}else if(!myreg.test($("#truckDriverMobile").val())){
				Anfa.show("请填写正确的司机电话","#truckDriverMobile");
				return;
			}
			
			if($("#turnkDriverIdNumber").val().length>15){
				Anfa.show("请填写正确的证件号码","#turnkDriverIdNumber");
				return;
			}
			
			if($("#truckOwnerName").val().length>15){
				Anfa.show("请填写正确的车主姓名","#truckOwnerName");
				return;
			}
			
			if($("#truckOwnerMobile").val().length>11){
				Anfa.show("请填写正确的车主电话","#truckOwnerMobile");
				return;
			}
			
			$('#fileImg').val(imgs.join());
			
			
			if($("#type").val()=="update"){
				$("#truckForm").attr("action", "${ctx }/kd/truck/updateTruck");
			}
			
				$("#truckForm").submit();
			}
		
		var imgs = new Array();
		$(".content-right-tem2").on("change",  "input[type='file']", upload);
		function upload(){
			var img = $(this).parents(".gtu1").find(".gtu2").find("img");
		    var formData = new FormData(); // FormData 对象
		    var file = $(this)[0].files[0];
		    if(!validate(file)){
		    	return;
		    }
		    formData.append("fileName", file); 
			$.ajax({
			  url : "${ctx}/file/uploadImage?imgType=truck",
			  type : 'POST',
			  contentType: false,    //不可缺
			  processData: false,    //不可缺
			  data : formData,
			  success:function(data){
				  if(data.success==true){
					  $(img).attr("src",data.src);
					  $(img).wrap("<a class=\"example-image-link\" href='"+data.src+"' data-lightbox=\"example-1\">");
					  imgs.push(data.src);
					  formData.append('truck',data.src);
				  }else{
					  layer.msg(data.message);
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
		
      </script>
	</body>
</html>
