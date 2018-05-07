<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/changecost.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
	<div id="fade3" class="black_overlay3"></div>
	<div id="MyDiv3" class="white_content3">
	
		<div class="banner-right-list5">
			
			<p class="banner-list5-type">
				修改费用
			</p>
			<script>
	        document.onkeyup=function(event){
	      	  var e=event||window.event;
	      	  var keyCode=e.keyCode||e.which;
	      	  if(keyCode == 120){
	      		  alert("ddd");
	      	  }
	        }
	        </script>
			<div class="banner-list5-div1">
				<p class="banner-div-title">配载单号：${load.load_sn}</p>
				<ul>
					<li>
						<p>
							车牌号：<span>${load.truck_id_number}</span>
						</p>
					</li>
					<li>
						<p>
							司机：<span>${load.truck_driver_name}</span>
						</p>
					</li>
					<li>
						<p>
							司机电话：<span>${load.truck_driver_mobile}</span>
						</p>
					</li>
					<li>
						<p>
							配载网点：<span>${load.snetworkName}</span>
						</p>
					</li>
					<li>
						<p>
							到货网点：<span>${load.enetworkName}</span>
						</p>
					</li>
					<li>
						<p>
							出发地：<span>${load.fromCity}</span>
						</p>
					</li>
					<li>
						<p>
							到达地：<span>${load.toCity}</span>
						</p>
					</li>
					<li>
						<p>
							运输类型：<span>
							<c:if test="${load.load_transport_type==1}">
							提货
						</c:if>
						<c:if test="${load.load_transport_type==2}">
							短驳
						</c:if>
						<c:if test="${load.load_transport_type==3}">
							干线
						</c:if>
						<c:if test="${load.load_transport_type==4}">
							送货
						</c:if>
						</span>
						</p>
					</li>
					<li>
						<p>
							原配载费用：<span>${load.load_fee}</span>元
						</p>
					</li>
				</ul>
			</div>
			<div class="banner-list5-div2">
				<p class="banner-div-title">更换信息</p>
				<input type="hidden" name="type" value="3">
				<input type="hidden" name="loadId" value="${load.load_id}">
				 <div class="banner-right-list2li">
            		   <div class="banner-right-list2li-tio">
                   <p>现付运输费</p>
                   <input type="text" name="load_nowtrans_fee" id="load_nowtrans_fee" onkeyup="javascript:checkInputIntFloat(this);">
           		    </div>
               	<div class="banner-right-list2li-tio">
                   <p>现付油卡费</p>
                   <input type="text" name="load_nowoil_fee"  id="load_nowoil_fee"  onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>回付运输费</p>
                   <input type="text" name="load_backtrans_fee"  id="load_backtrans_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
                <div class="banner-right-list2li-tio">
                   <p>到付运输费</p>
                   <input type="text" name="load_attrans_fee"  id="load_attrans_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>整车保险费</p>
                   <input type="text" name="load_allsafe_fee"  id="load_allsafe_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>发站装车费</p>
                   <input type="text" name="load_start_fee"  id="load_start_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>发站其他费</p>
                   <input type="text" name="load_other_fee"  id="load_other_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio" style="width: 12.00%";> 
                   <p>配载费用合计</p>
                   <input type="text" id="totalId" name="loadfee" readonly="readonly">
               </div>
            </div>
			</div>

			<button class="banner-list5-up"  onclick="save()">提交</button>
			<br />
		</div>
	</div>
</body>
<script type="text/javascript">
$(".banner-right-list2li input").bind("change", this.feechange);
function feechange(){
	var total = 0;
	$(".banner-right-list2li input").each(function(i,o){
		if(i>6){
			return false;
		}
		var value = $(o).val();
		if(value!=""){
			value = parseFloat(value);
			total +=value;
		}
	})
	$("#totalId").val(total);
}

function save(){
	if($("#load_nowtrans_fee").val()==""&&$("#load_nowoil_fee").val()==""&&$("#load_backtrans_fee").val()==""&&$("#load_attrans_fee").val()==""&&$("#load_allsafe_fee").val()==""&&$("#load_start_fee").val()==""&&$("#load_other_fee").val()==""){
		layer.msg("请输入要修改的费用");
		return false;
	}
	var sdata = $(".banner-list5-div2").find("input,select,textarea").serialize();
	$.ajax({
		type:'POST',
		url:'/kd/loading/comfirmModifyFee',
		data:sdata,
		success:function(data){
			if (data.success) {
				layer.msg("修改成功");
				setTimeout(function(){parent.layer.closeAll();}, 1000);
			} else {
				layer.msg(data.msg);
			}
		}
	});
}
</script>
</html>