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
			    	<input type="hidden" id="lineid" name="lineid" value="${line.id}"></input>
				      <label class="label00"><span class="content-span">所属网点 : </span>
				      
				      <select name="networkid" id="networkid">
				      <option value="">请选择</option>
				      <c:forEach var="work" items="${list}">
				       <option value="${work.id}" <c:if test="${line.network_id == work.id}">selected</c:if>>${work.addr}</option>
				      </c:forEach>
				      </select><span class="dot">*</span>
				      </label>
					  <label id="startId" class="label"><span class="content-span">出发地 : </span><select style="margin-left: 15px;" name="province"></select> 
								<select name="city"></select>
								<select name="area"></select>
								<script type="text/javascript">
								$("#startId").region({domain:"${ctx}",required: true, cityRequired: true, currAreaCode:"${empty line.from_region_code?line.from_city_code:line.from_region_code}"});
								</script><span class="dot">*</span></label>
					  <label id="endId" class="label2"><span class="content-span">到达地 : </span>
					            <select style="margin-left: 15px;" name="province"></select> 
								<select name="city"></select>
								<script type="text/javascript">
								$("#endId").region({domain:"${ctx}",required: true, cityRequired: true,currAreaCode:"${line.to_city_code}"});
								</script><span class="dot">*</span></label>
					  
				      <label class="label4"><span class="content-span">重货价/公斤 : </span><input type="text"  id="price_heavy" name="price_heavy" value="${line.price_heavy}" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="dot">*</span><span class="txt_bz">元/公斤</span></label>
				      <label class="label5"><span class="content-span">轻货价/立方 : </span><input type="text"  id="price_small" name="price_small" value="${line. price_small}" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="dot">*</span><span class="txt_bz">元/立方</span></label>
				       <label class="label8"><span class="content-span">所在物流园 : </span>
				       <%-- <input type="text" id="parkname" name="parkname"  value="${line.parkname}" /> --%>
				          <select name="parkid" id="parkid">
				      <option value="">- 请选择 -</option>
				      <c:forEach var="park" items="${logisticsParks}">
				        <option value="${park.id}" <c:if test="${line.logistics_park_id ==park.id}">selected</c:if>>${park.park_name}</option>
				      </c:forEach>
				      </select>
				       <span class="dot">*</span></label>
				      <label class="label3"><span class="content-span">时效性 : </span><input type="text"  id="survive_time" name="survive_time" value="${line.survive_time}" onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="txt_bz">小时</span></label>
				      <label class="label6"><span class="content-span">发车频次 : </span><input type="text" id="frequency" name="frequency"  value="${line.frequency}"  onkeyup="value=value.replace(/[^\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="txt_bz">天/次</span></label>
				      <label class="label7"><span class="content-span">起步价 : </span><input type="text" id="starting_price" name="starting_price" value="${line.starting_price}"  /><span class="txt_bz">元</span></label>
				     
				   <!--    <div class="content-left-ang3">
				      <ul>
				      </ul> 
				     </div>
				     <script type="text/javascript"> 
				     	$("#parkname").fillCorps("${ctx}");
				     	</script> -->
				      <label class="label9"><span class="content-span">档口位置 : </span><input type="text" id="address"  name="address" value="${line.address}" /></label>
				       <label class="label10"><span class="isal"><input type="checkbox" id="isSale" <c:if test="${line.is_sale}" >checked</c:if> ></span><span class="is_zs">设为特价专线</span></label>
				
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
		var lineid=$("#lineid").val();
		var sarea = $("#startId").find("select[name=area]").val();
		var earea = $("#endId").find("select[name=area]").val();
		var scitycode="";
		var ecitycode="";
		var survive_time= $("#survive_time").val();
		var price_heavy= $("#price_heavy").val();
		var price_small= $("#price_small").val();
		var frequency= $("#frequency").val();
		var parkname= $("#parkname").val();
		var address= $("#address").val();
		var startingprice= $("#starting_price").val();
		if(sarea!=null || earea!=null ){
			scitycode=$("#startId").find("select[name=city]").val();
			ecitycode=$("#endId").find("select[name=city]").val();
		}
		if(networkid==""){
			Anfa.show("请选择专线网点","#networkid");
			return false;	
		}
		if($("#startId").find("select[name=province]").val()==null ||$("#startId").find("select[name=province]").val()==""){
			 Anfa.show("请选择发布专线的出发地","#startId");
			return;
			
		}
		if($("#endId").find("select[name=province]").val()==null ||$("#endId").find("select[name=province]").val()==""){
			 Anfa.show("请选择发布专线的到达地","#endId");
			return;
			
		}
		if(price_heavy==""){
			Anfa.show("重货价/公斤不能为空","#price_heavy");
			return ;
		}
		if(price_small==""){
			Anfa.show("轻货价/立方不能为空","#price_small");
			return ;
		}

		if(parkId==""){
			Anfa.show("请选择所在物流园","#parkid");
			return ;
		}
		var isSale=0;
		if($("#isSale").is(':checked'))isSale=1;
		$.ajax({
			type:"post",
			dataType:"json",
			url:"${ctx}/front/line/preview/updateLine", 
			data:{lineid:lineid,networkId:networkid,fromCityCode:scitycode,fromRegionCode:sarea,toCityCode:ecitycode,toRegionCode:earea,survive_time:survive_time,price_heavy:price_heavy,price_small:price_small,frequency:frequency,parkId:parkId,address:address,startingprice:startingprice,isSale:isSale},
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


