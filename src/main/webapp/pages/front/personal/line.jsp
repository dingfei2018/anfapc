<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>专线管理</title>

<%@ include file="common/include.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/pc/css/Dedicated.css?v=${version}" />
	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
	<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
	<script src="${ctx }/static/pc/js/line.js?v=${version}"></script>
    <script src="${ctx}/static/pc/js/muticity.js"></script>
</head>

<body>
	<!--头部-->
	<%@ include file="common/head.jsp"%>
	<!--中间内容的部分-->
	<div class="content">
		<%@ include file="common/left.jsp"%>
		<div class="content-right">
			  <ul class="ul"> 
				<li style="margin-right: 40px;"><a href="${ctx}/front/userconter/line"  class="active">专线管理</a></li>
				<li class='<c:if test="${curr eq 9}">active</c:if>'><a href="${ctx}/front/line/preview">发布预览</a></li>
			  </ul>
			<div class="content-right-top">
				      <label class="label00"><span class="content-span">所属网点 : </span>
				      <select name="networkid" id="networkid">
				      <option value="">- 请选择 -</option>
				      <c:forEach var="work" items="${list}">
				        <option value="${work.id}">${work.addr}</option>
				      </c:forEach>
				      </select><span class="dot">*</span>
				      </label>
					  <label id="startId" class="label"><span class="content-span">出发地 : </span><select style="margin-left: 15px;" name="province"></select> 
								<select name="city"></select>
								<select name="area"></select>
								<script type="text/javascript">
								$("#startId").region({domain:"${ctx}", required: true, cityRequired: true, currAreaCode:""});
								</script><span class="dot">*</span></label>
					            <label id="endId" class="label2"><span class="content-span">到达地 : </span>
					           <!--  <select style="margin-left: 15px;" name="province"></select> 
								<select name="city"></select>
								<select name="area"></select>
								<script type="text/javascript">
									$("#endId").region({domain:"${ctx}"});
								</script> -->
								<select id="province" style="width:113px; height:33px; margin-left:14px;">
						        </select>
						        <input id="cityId" type="text" style="width: 228px; height:31px; border: 1px solid #dfdfdf; position: relative; margin-left: 5px;" />
								<script type="text/javascript">
								      $("#province").mutiRegion({cityField:"cityId"});
								</script>
								<span class="dot">*</span></label>
				      <label class="label4"><span class="content-span">重货价/公斤 : </span><input type="text"  id="price_heavy" name="price_heavy" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="dot">*</span><span class="txt_bz">元/公斤</span></label>
				      <label class="label5"><span class="content-span">轻货价/立方 : </span><input type="text"  id="price_small" name="price_small" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="dot">*</span><span class="txt_bz">元/立方</span></label>
				      <label class="label8"><span class="content-span">所在物流园 : </span>
				     <!--  <input type="text" class="input" id="parkname" name="parkname"   /> -->
				       <select name="parkid" id="parkid">
				      <option value="">- 请选择 -</option>
				      <c:forEach var="park" items="${logisticsParks}">
				        <option value="${park.id}">${park.park_name}</option>
				      </c:forEach>
				      </select>
				      <span class="dot">*</span></label>
				      <label class="label3"><span class="content-span">时效性 : </span><input type="text"  id="survive_time" name="survive_time" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="txt_bz">小时</span></label>
				      <label class="label6"><span class="content-span">发车频次 : </span><input type="text" id="frequency" name="frequency" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="txt_bz">天/次</span></label>
				      <label class="label7"><span class="content-span">起步价 : </span><input type="text" id="starting_price" name="starting_price" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="txt_bz">元</span></label>
				      
				     <!-- <div class="content-left-ang3">
				      <ul>
				      </ul>  
				     </div>
				     <script type="text/javascript"> 
				     	 $("#parkname").fillCorps("${ctx}");
				     	</script> -->
			         <label class="label9"><span class="content-span">档口位置 : </span><input type="text" id="address"  name="address" /></label>
			         <label class="label10"><span class="isal"><input type="checkbox" id="isSale"></span><span class="is_zs">设为特价专线</span></label>
			</div>
			<p class="p_btn"><button onclick="saveLine();">提交</button>		</p>
		</div>
	
	</div>
	<!--底部的内容--->
	<%@ include file="../common/loginfoot.jsp"%>
	
	<script type="text/javascript">	
	function saveLine(){
		var networkid=$("#networkid").val();
		var parkId=$("#parkid").val();
		var sarea = $("#startId").find("select[name=area]").val();
		//var earea = $("#endId").find("select[name=area]").val();
		var scitycode="";
		var ecitycode="";
		var survive_time= $("#survive_time").val();
		var price_heavy= $("#price_heavy").val();
		var price_small= $("#price_small").val();
		var frequency= $("#frequency").val();
		var parkname= $("#parkname").val();
		var address= $("#address").val();
		var startingprice= $("#starting_price").val();
		if(networkid==""){
			Anfa.show("请选择专线网点","#networkid");
			return false;	
		}
		if(sarea!=null){
			scitycode=$("#startId").find("select[name=city]").val();
			//ecitycode=$("#endId").find("select[name=city]").val();
		}
		if($("#startId").find("select[name=city]").val()==null ||$("#startId").find("select[name=city]").val()==""){
			 Anfa.show("请选择发布专线的出发地","#startId");
			return false;	
		}
		var toCityCode=$("#cityId").attr("data");
		 if($("#cityId").val()==""){
			 Anfa.show("请选择发布专线的到达地","#cityId");
			return false;
			
		} 
		if(price_heavy==""){
			Anfa.show("重货价/公斤不能为空","#price_heavy");
			return false;
		}
		if(price_small==""){
			Anfa.show("轻货价/立方不能为空","#price_small");
			return false;
		}
		if(parkId==""){
			Anfa.show("请选择所在物流园","#parkid");
			return false;
		}
		var isSale=0;
		if($("#isSale").is(':checked'))isSale=1;
		$.ajax({
			type:"post",
			dataType:"json",
			url:"${ctx}/front/line/saveLine", 
			data:{networkId:networkid,fromCityCode:scitycode,fromRegionCode:sarea,toCityCode:toCityCode,survive_time:survive_time,price_heavy:price_heavy,price_small:price_small,frequency:frequency,parkId:parkId,address:address,startingprice:startingprice,isSale:isSale},
			success:function(data){
				if(data.state=="SUCCESS"){
					window.location.href="${ctx}/front/line/preview";
				}else{
					layer.msg(data.message,{time: 2000});
				}

           }
		});
		
	}
	
	
	</script>
	
</body>
</html>


