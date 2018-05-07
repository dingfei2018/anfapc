<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>系统设置</title>
	    <link rel="stylesheet" href="${ctx}/static/kd/css/userlist.css" />
	    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
	</head>
	<body>
		<input type="hidden" value="${msg.msg}" id="msg" />
		<input type="hidden" value="${userId}" id="y_userId" />
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/baseinfoleft.jsp" %>
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
				<div class="banner-right-title">
				</div>
				<p class="banner-right-p">单号规则设置</p>
				<div style=" width: 100%;" id="loadingId">
			<form onsubmit="return false;" id="myForm">
			     <input type="hidden" name="ruleId" id="ruleId" />
				<input type="hidden" name="printId" id="printId" />
			    <div class="banner-right-list">
			         <div class="banner-right-list-left">
			               <p>单号规则</p>
			         </div>
			         <div class="banner-right-list-right">
                          <ul>
                             <li><input type="radio" name="shipRule" value="1"/> 按网点单号顺序自增，如A网点:0100001、0100002，B网点0200001、0200002</li>
                             <li><input type="radio" name="shipRule" value="2"/> 年月日+按网点单号顺序自增,如A网点1801010100001、1801010100002，B网点1801010200001、1801010200002</li>
                             <li><input type="radio" name="shipRule" value="3"/> 按集团单号顺序自增，如A网点：0000001、0000003，B网点0000002、0000004</li>
                             <li><input type="radio" name="shipRule" value="4"/> 年月日+按集团单号顺序自增，如A网点：1801010000001、1801010000003，B网点1801010000002、1801010000004</li>
                             <li><input type="radio" name="shipRule" value="0"/> 手工录入</li>
                          </ul>			         
			         </div>
			    </div>
			    
			    <div class="banner-right-list" style="height:145px;">
			         <div class="banner-right-list-left" style="height:115px;">
			               <p>货号规则</p>
			         </div>
			         <div class="banner-right-list-right" style="height:135px;">
                          <ul>
                             <li><input type="radio" name="noRule"  value="0"/> 运单号+运单总件数，如0100001-13</li>
                             <li><input type="radio" name="noRule"  value="1"/> 网点代码+年月日+运单号后5位+件数，如A18012600001-13</li>
                             <li><input type="radio" name="noRule"   value="2"/> 手工录入</li>
                          </ul>			         
			         </div>
			    </div>
			    
			    <p class="banner-right-p" style="margin-top:0px;">运输标签打印设置</p>
			    
			    <div class="banner-right-list" style="height:120px;">
			         <div class="banner-right-list-left" style="height:90px;">
			               <p>顶部标签</p>
			         </div>
			         <img style="position: absolute; right: 71px; top:36px; z-index: 2;" src="${ctx}/static/kd/img/biaoqian2.png"/>
			         <div class="banner-right-list-right" style="height:110px;">
                          <ul>
                             <li><input type="radio" name="topLabel" value="0"/> 需要打印公司名称</li>
                             <li><input type="radio" name="topLabel" value="1"/> 不需要打印公司名称</li>
                          </ul>			         
			         </div>
			    </div>
			    
			    <div class="banner-right-list" style="height:260px; margin-top:0px;">
			         <div class="banner-right-list-left" style="height:240px;">
			               <p>底部标签</p>
			         </div>
			         <div class="banner-right-list-right" style="height:260px;">
                          <ul>
                             <li><input type="radio" name="bottomLabel" value="1"/> 已印制标签底部</li>
                             <li><input type="radio" name="bottomLabel" value="2"/> 打印公司总部地址和联系电话</li>
                             <li><input type="radio" name="bottomLabel" value="0"/> 打印开单网点地址和联系电话</li>
                             <li><input type="radio" id="bottomLabel3" name="bottomLabel" value="3" style="vertical-align: top"/> 
                             <textarea placeholder="自定义，不超过40字" id="labelValue" name="labelValue" style="width:500px; height:50px; margin:0 auto;"></textarea></li>
                          </ul>			         
			         </div>
			    </div>
				<button class="button" onclick="submitForm();">保存</button>
				</form> 
				</div>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	 <script type="text/javascript">
	init();
	function init(){
		$.ajax({
			type:'post',
			url:'/kd/setting/getSetJson',
			success:function(data){
				console.log(data);
				if(typeof (data.printId)!='undefined'){
					$('#printId').val(data.printId);
				}
				if(typeof (data.ruleId)!='undefined'){
					$('#ruleId').val(data.ruleId);
				}
				var bottomLabel=data.printSet.bottomLabel;
				var topLabel=data.printSet.topLabel;
				var noRule=data.ruleSet.noRule;
				var shipRule=data.ruleSet.shipRule;
				
				if(shipRule==0){
					$(":radio[name='shipRule'][value='0']").prop("checked", "checked");
					}
				if(shipRule==1){
					$(":radio[name='shipRule'][value='1']").prop("checked", "checked");
					}
				if(shipRule==2){
					$(":radio[name='shipRule'][value='2']").prop("checked", "checked");
					}
				if(shipRule==3){
					$(":radio[name='shipRule'][value='3']").prop("checked", "checked");
					}
				
				if(noRule==0){
					$(":radio[name='noRule'][value='0']").prop("checked", "checked");
					}
				if(noRule==1){
					$(":radio[name='noRule'][value='1']").prop("checked", "checked");
					}
				if(noRule==2){
					$(":radio[name='noRule'][value='2']").prop("checked", "checked");
					}
				
				if(topLabel==0){
					$(":radio[name='topLabel'][value='0']").prop("checked", "checked");
					}
				if(topLabel==1){
					$(":radio[name='topLabel'][value='1']").prop("checked", "checked");
					}
				
				if(bottomLabel==0){
				$(":radio[name='bottomLabel'][value='0']").prop("checked", "checked");
				}
				if(bottomLabel==1){
				$(":radio[name='bottomLabel'][value='1']").prop("checked", "checked");
				}
				if(bottomLabel==2){
				$(":radio[name='bottomLabel'][value='2']").prop("checked", "checked");
				}
				if(bottomLabel==3){
				$(":radio[name='bottomLabel'][value='3']").prop("checked", "checked");
				}
				 if(typeof (data.printSet.labelValue)!='undefined'){
					$('#labelValue').val(data.printSet.labelValue);
				} 
			}
		});
	}
	
	function submitForm(){
		var laberValue=$('#labelValue').val();
		var check3=$(":radio[name='bottomLabel'][value='3']")[0].checked; 
		if(check3&&laberValue.length==0){
			Anfa.show("选择自定义请输入自定义内容","#labelValue");
			return;
		}
		if(!check3&&laberValue.length>0){
			Anfa.show("输入自定义内容后请勾选自定义按钮","#bottomLabel3");
			return;
		}
		if(laberValue.length>=40){
			Anfa.show("输入自定义内容过长请重新输入","#labelValue");
			return;
		}
		
			$.ajax({
				type:'post',
				url:'/kd/setting/updateOrSave',
				data:$('#myForm').serialize(),
				success:function(data){
				console.log(data);
				if(data.success){
					layer.msg("设置成功！",{time: 1000},function(){
						window.location.reload();
                  });
				}else{
					layer.msg("设置失败！");
				}
				
				}
			});
	}
		
	</script>
	</body>
</html>
