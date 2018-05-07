<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增专线</title>
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/kd/css/addline.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script src="${ctx}/static/pc/js/muticityinline.js"></script>
		
		<!-- 下拉多选框样式 -->
		<style>
		body,td{font-size:12px;color:#000000;}
		.checkbox{width:15px;height:15px;}
		.cked{
			margin:1px;padding:2px;width:98%;display:block;background-color:highlight;color:highlighttext;
		}
		.nock{
			margin:1px;padding:2px;width:98%;display:block;
		}
		</style>
		
		<!-- 下拉多选框 -->
		<script>
					function _checkbox(name,str,defv,values){
						var arr=str.split("^");
						var valarr = values.split(",");
						var ck="",bc="";
						for(var i=0;i<arr.length;i++){
								var t=arr[i];
								var v=arr[i];
								if((","+defv.toLowerCase()+",").replace(/ ,/g, ',').indexOf(","+v+",")!=-1){ck=" checked";cls="cked";}
								else{ck="";cls="nock";}
								var thisstr="<label class=\""+cls+"\" for=\"i_"+name+"_"+i+"\" id=\"l_"+name+"_"+i+"\">";
								thisstr+="<input style='vertical-align: middle;' class=\"checkbox\"  onclick=\"document.getElementById('l_"+name+"_"+i+"').className=(document.getElementById('i_"+name+"_"+i+"').checked)?'cked':'nock';\" type=\"checkbox\""+ck+" name=\""+name+"\" id=\"i_"+name+"_"+i+"\" value=\""+valarr[i]+"\" data=\""+v+"\" \/> ";
								thisstr+=t+"</label>";
								//document.write(thisstr);
								$("#netw").append(thisstr);
							}
					}
					function _getv(o){
						var allvalue="";
						if(typeof(o)=="undefined"){return "";}
						if (typeof(o.length)=="undefined"){
							if(o.checked){return o.value+ ",";}else{return "";}
						}
						for(var i=0;i<o.length;i++){
							if(i==o.length-1){
								if(o[i].checked){
									allvalue +=o[i].value;
									break;
								}
							}
							if(o[i].checked){
								allvalue +=o[i].value+",";
							}
						}
						return allvalue;
					}
					
					function _getValue(o){
						var allvalue="";
						if(typeof(o)=="undefined"){return "";}
						if (typeof(o.length)=="undefined"){
							return "";
						}
						for(var i=0;i<o.length;i++){
							if(o[i].checked){
								allvalue +=o[i].getAttribute("data")+"/";
							}
							
						}
						return allvalue;
					}
					
					var b = true;
					function _sl(o){
						for(var i=0;i<o.length;i++){
							o[i].checked = b
						}
						b = !b;
				}
			</script>
	</head>
	<body>
	 
	 <%@ include file="../common/head2.jsp" %>
	 <%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/baseinfoleft.jsp"%>
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
			    	})
			</script>
			<div class="banner-right">
				<ul>
					<li><a href="${ctx}/kd/line">专线列表</a></li>
					<li><a href="${ctx}/kd/line/add" class="active"><span id="title">新增专线</a></li>
				</ul>
				
				<div class="banner-right-content">				
					<div class="banner-right-from">
						<!-- <p>出发地：</p>
						<select name="" id="" >
									<option value="0"></option>
									<option value="1"></option>
									<option value="2"></option>
									<option value="3"></option>
						</select> -->
						<label id="startId" class="label"><span class="content-span">出发地 : </span>
							<select style="margin-left: 15px; width: 153px" name="province"></select> 
							<select id="startCityId" style="width: 152px; margin-left: 0;" name="city"></select>
							<span class="dot">*</span> 
						</label>
					<!-- <span>*</span>	 -->
					</div>
					
					<div class="banner-right-to">
						<!-- <p>到达地：</p>
						<select name="" id="" >
									<option value="0"></option>
									<option value="1"></option>
									<option value="2"></option>
									<option value="3"></option>
						</select> -->
						<label id="endId" class="label2"><span class="content-span">到达地 : </span>
							<select id="province" style="width:113px; height:33px; margin-left:14px;">
					        </select>
					        <input id="cityId" type="text" name="city" style="width: 185px; height:31px; border: 1px solid #dfdfdf; position: relative; margin-left: 5px;" />
							<script type="text/javascript">
							      $("#province").mutiRegion({cityField:"cityId"});
							</script>
							<span class="choose">（可多选）</span>
							<span class="dot">*</span>
						</label>
						<!-- <span>*</span> -->
					</div>
					
					<div class="banner-right-netfrom">
						<p>出发网点：</p>
						<!-- <select name="networkId" id="networkId" onclick="checkCity()" >
							 <option value="0"></option>
							<option value="1"></option>
							<option value="2"></option>
							<option value="3"></option> 
									
						</select> -->
						
						<!-- <input id="networkId" name="networkId" onclick="checkCity()" style="width: 310px; height:34px; border: 1px solid #dfdfdf; position: relative; margin-left: 10px; ">   -->
						<input class="input" type="text" id="networkId" name="networkId"  onclick="checkCity()" placeholder="请选择(必填)" />
						<span class="choose">（可多选）</span>
						<span>*</span>	
					</div>
					
					<div id="showNetwork" style="display: none;margin-left: 90px;border: 1px solid #dfdfdf;width: 310px;" >
						<!-- 下拉内容 -->
						<div id="netw" style="width:310px;height:150px;overflow:auto;font-size: 16px;">
						<script>
						 $(function(){
							//选择出发地 获取出发网点
							$("#startId").change(function(){
							var cityCode =$("#startCityId option:selected").val();
							$("#netw").empty();
							$("#networkId").val("");
							 $.ajax({
									type:"get",
									dataType:"json",
									url:"${ctx}/kd/line/getNetWork", 
									data:{'fromCityCode':cityCode},
									success:function(data){
										
										var networkName="";
										var networkValue="";
										for(var i=0;i<data.length;i++){  
											if(i==data.length-1){
											networkName += data[i].sub_network_name;
											networkValue += data[i].id;	
											break;
											}
											networkName += data[i].sub_network_name + "^";
											networkValue += data[i].id + ",";	
									    }
										_checkbox("network",networkName,"",networkValue)
						           }
								}); 
						
							})
						}) 
						</script>
						</div>
						<div>
							<button class="shle_left" onclick='_sl($(".checkbox"))'>全选</button>
							<button class="shle_right" onclick='closeShowNetwork()'>确定</button>
						</div>
					</div>
					
					<div class="banner-right-netto">
						<p>到达网点：</p>
						<select name="arriveNetworkId" id="arriveNetworkId"  onclick="checkEndCity()" >
									<!-- <option value="0"></option>
									<option value="1"></option>
									<option value="2"></option>
									<option value="3"></option> -->
						</select>
						
					</div>
					
					<div class="banner-right-heavy">
						<p>重货价格：</p>
						<input class="input" type="text"  onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : ''"  name="price_heavy" id="priceHeavy" />
						<span>元/公斤</span>
					</div>
					
					<div class="banner-right-light">
						<p>轻货价格：</p>
						<input class="input" type="text" onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : ''" name="price_small" id="priceSmall" />					
                   		 <span>元/立方</span>
					</div>
					
					<div class="banner-right-least">
						<p>最低收费：</p>
						<input  class="input" type="text"  onkeyup="this.value=/^\d+\.?\d{0,2}$/.test(this.value) ? this.value : ''"  name="startingprice" id="startingprice"/>
						<span>元</span>
					</div>
					 <input type="button" value="提交" class="inputs" onclick="submitform()" />
					
				</div>
			</div>
		</div>
	
	    <%@ include file="../common/loginfoot.jsp" %>
		<script type="text/javascript">
			/* $(function(){
				//选择出发地 获取出发网点
				$("#startId").change(function(){
					var cityCode =$("#startCityId option:selected").val(); 
					//出发网点
						  $.ajax({
							type:"get",
							dataType:"json",
							url:"${ctx}/kd/line/getNetWork", 
							data:{'fromCityCode':cityCode},
							success:function(data){
								var html="";
								for(var i=0;i<data.length;i++){  
									html += appendHtml(data[i]);
							    } 
								$("#networkId > option").remove();
								$("#networkId").append(html);
								
				           }
						});  
					//出发网点
					 $('#networkId').combobox({    
					    url:'${ctx}/kd/line/getNetWork?fromCityCode='+cityCode,    
					    valueField:'id',   
						multiple:true,
					  formatter: function(row){
				            var opts = $(this).combobox('options');
				            return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
				        },
					    textField:'sub_network_name'   
					});  
			 	})
				
				
			}) */
				
			
			
				$("#startId").region({domain:"${ctx}", required: true, cityRequired: true,currAreaCode:"${customer.region_code}"});
				
				if($("#type").val()=="update"){
				$("#title").html("修改客户")	
				$("#customerTypeSelect").val($("#customer_type").val());
				}
				
				function setCustomerSn(){
					var customerType=$("#customerTypeSelect").val();
					
					$.ajax({
						type : "post",
						dataType :"text",
						url : "${ctx }/kd/customer/getCustomerSn?customer_type="+customerType,
						success : function(data) {
							$('#customerSn').val(data);
							$('#customerSnHid').val(data);
							 
						}
					});
					
					
					
					
				}
				
				
				/*表单提交*/
			 	function submitform(){
					//出发地
					var fromCityCode = $("#startId").find("select[name=city]").val();
					if(!fromCityCode){
						Anfa.show("请选择出发地","#startId");
						return;
					}
					//到达地
					var toCityCode=$("#cityId").attr("data");
					if($("#cityId").val()==""){
						 Anfa.show("请选择到达地","#cityId");
						return false;
					} 
					//出发网点
					//var networkId = $('#networkId').combobox('getValues').toString();
					var networkId = _getv($(".checkbox"));
					if(networkId==""){
						 Anfa.show("请选择出发网点","#networkId");
						return false;
					} 
					//到达网点
					var arriveNetwork=$("#arriveNetworkId").val();
					if(arriveNetwork != ''){
						var strs= new Array();
						strs = networkId.split(",");
						for (i=0;i<strs.length ;i++ ){
							if(arriveNetwork == strs[i]){
								Anfa.show("到达网点不能和出发网点重复","#arriveNetworkId");
								return;
							}
						}
					}
					//重货价格
					var priceHeavy=$("#priceHeavy").val();
					//轻货价格
					var priceSmall=$("#priceSmall").val();
					//最低收费
					var startingprice=$("#startingprice").val();
					$.ajax({
						type:"get",
						dataType:"json",
						url:"${ctx}/kd/line/save", 
						data:{'fromCityCode':fromCityCode,'toCityCode':toCityCode,'networkId':networkId,'arriveNetworkId':arriveNetwork,'price_heavy':priceHeavy,'price_small':priceSmall,'startingprice':startingprice},
						success:function(data){
							if(data.state=="SUCCESS"){
								//window.location.href="${ctx}/kd/line";
								layer.msg(data.message,{time: 3000,end:function(){location.href='${ctx}/kd/line';}});
							}else{
								layer.msg(data.message,{time: 3000});
								//alert("保存失败:"+data.message)
							}
							
			           }
					});
					
					} 
				
				function checkCity(){
					var sarea = $("#startId").find("select[name=city]").val();
					if(!sarea){
						Anfa.show("请先选择出发地","#startId");
						return false;	
					}
					$("#showNetwork").show();
				}
				
				function checkEndCity(){
					var sarea = $("#cityId").attr("data");
					if(!sarea){
						Anfa.show("请先选择到达地","#endId");
						return false;	
					}
				}
				
				
				
				 function appendHtml(obj){
					var html = " <option value=\""+obj.id+"\">"+obj.sub_network_name+"</option> ";
					return html;
					} 
					
					
					
				//出发网点确定按钮
				function closeShowNetwork(){
					$("#networkId").val(_getValue($(".checkbox")));
					
					$("#showNetwork").hide();
				}
				</script>	
	</body>
</html>

