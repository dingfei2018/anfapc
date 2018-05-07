<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增异动</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/register.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../../common/commonhead.jsp" %>
		<link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
		<script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>
	</head>

	<body>
		<!-- 头部文件 -->
		<%@ include file="../../common/head2.jsp" %>
		<%@ include file="../../common/head.jsp" %>

		<div class="banner">
			<!-- 左边菜单 -->
			<%@ include file="../../common/financialleft.jsp" %>
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
						<a href="${ctx}/kd/finance/abnormal" class="at">异动管理</a>
					</li>
					<li>
						<a href="#" class="active at">新增异动</a>
					</li>
				</ul>

				<div class="banner-right-list">
				<form id="abnormalForm" onsubmit="return false;">
				<input type="hidden" id="chooseShipId" name="ship_id"/>
				     <table>
				     <tr>
				     <td>
				   		<span style="margin-left: 38px;">运单号:</span>
				    	<input type="text" disabled="disabled" id="c_shipSn" /><button onclick="openChooseShip();return false;">选择</button>
				  	 </td>	
				  	 <td>
				  	 
				   	<span style="margin-left: -24px;">开单网点:</span>
				    <input class="banner-right-input2" type="text" id="c_shipNet" disabled="disabled">
				   </td>
				        
				          <td>出发地 : <input type="text" id="c_fromAdd" disabled="disabled"></td>
				        </tr>
				        <tr style="background-color: #fff !important;;">    
				          <td>到达地 : <input type="text" id="c_toAdd" disabled="disabled"></td>
					     	<td>开单日期 : <input type="text" id="c_create_time" disabled="disabled"></td>
				          <td><span class="spant">托运方 : </span><input type="text" id="c_senderName" disabled="disabled"></td>
				         </tr>
				         <tr>
				           <td>收货方 : <input type="text" id="c_receiverName" disabled="disabled"></td>
				       	</tr>
				       
				    </table>



					<hr>
				    <span class="span">异动增加 : </span><input id="plus_fee" name="plus_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></input>
					<span class="span">异动减款 : </span><input id="minus_fee" name="minus_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></input>

					<span class="span">异动结算网点 :</span><select id="net" name="network_id" class="select">
					<%--<option value="">请选择</option>
					<c:forEach var="work" items="${userNetworks}">
						<option value="${work.id}">${work.sub_network_name}</option>
					</c:forEach>--%>
				</select>

				    
				    <div style="width:700px;position: relative; overflow: hidden;margin-top: 30px;height: 300px;margin-left: 170px;">
				       <span>异动原因 :</span> <textarea id="cause" name="cause" style="position:absolute;left: 96px;top: 0px;width: 600px; height: 289px;"></textarea>
				    </div>
				    

          		<br>
          <button class="buttons" onclick="submitForm();return false;">提交</button>
          </form>
				</div>
				</div>
			</div>
			<%@ include file="../../common/loginfoot.jsp" %>
	<script type="text/javascript">


	$(function () {
		$("#plus_fee").blur(function(){
		    if($("#plus_fee").val()){
                document.getElementById("minus_fee").disabled=true;
			}else{
                document.getElementById("minus_fee").disabled=false;
			}
		})
        $("#minus_fee").blur(function(){
            if($("#minus_fee").val()){
                document.getElementById("plus_fee").disabled=true;
            }else{
                document.getElementById("plus_fee").disabled=false;
            }
        })
	})


	var otr = $(".gtu1").eq(0).clone();
	var imgs = new Array();
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
            content: ['/kd/finance/abnormal/chooseShip', 'yes'],
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
    				$('#c_senderName').val(data.senderName);
    				$('#c_receiverName').val(data.receiverName);
    				$('#c_shipNet').val(data.netWorkName);
    				getNetwork(shipId);
    				}
    			});
            }
        });
	}

	function getNetwork(shipId){
	    if(shipId != ""){
            $.ajax({
                type:'post',
                data:{"shipId":shipId},
                url:'/kd/finance/abnormal/getNetWorkByShipId',
                success:function(data){
                    var html="";
                    for(var i=0;i<data.length;i++){
                        html += "<option value="+data[i].id+">"+data[i].sub_network_name+"</option>";
                    }
                    $("#net > option").remove();
                    $("#net").append(html);
                }
            });
		}

	}
	
	function submitForm(){
		
		if($("#c_shipSn").val()==""){
			Anfa.show("请选择运单","#c_shipSn");
			return;
		}
		if($("#net").val()==""){
			Anfa.show("请选择异动结算网点","#net");
			return;
		}
		if($("#plus_fee").val()==""&&$("#minus_fee").val()==""){
			Anfa.show("异动增加或异动减款至少选择一项","#plus_fee");
            Anfa.show("异动增加或异动减款至少选择一项","#minus_fee");
			return;
		}
		if($("#cause").val().trim().length === 0){
			Anfa.show("请填写异动原因","#cause");
			return;
		}

		$.ajax({
			type:'post',
			data:$("#abnormalForm").serialize(),
			url:'/kd/finance/abnormal/save',
			success:function(data){
		//	console.log(data);
			if(data.state=="success"){
				layer.msg(data.msg,{time: 1000},function(){
						 window.location.href="${ctx}/kd/finance/abnormal";
                   });
			}else{
				layer.msg(data.msg);
			}	
				
			}
		});
		
	}
	

	
	
	</script>
	</body>

</html>