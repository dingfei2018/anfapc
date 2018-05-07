<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>订单评价</title>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/Vehicle/css/evaluate.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
	 	<script src="${ctx}/static/pc/js/startScore.js"></script> 
       <script src="${ctx}/static/pc/layer/layer/layer.js"></script>
       <script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<div class="content">		
		  <p>填写评价</p>    
		  <div class="content-img">
		     <img src="${order.firstImg}_200X150${fn:substringAfter(order.firstImg, '_')}"/>
		  </div>
		  
		  <table>
		       <tr>
		          <th>${order.from_city}</th>
		          <th><img src="${ctx}/static/pc/img/jiantou3oopp.png"/></th>
		          <th>${order.to_city}</th>
		       </tr>
		       <tr>
		          <td><button disabled>总重量：${order.all_weight}公斤</button></td>
		          <td>&nbsp;</td>
		          <td><button disabled>总体积：${order.all_volume}方</button></td>
		       </tr>
		  </table>
		  <div class="content-tab" id="divform">
		      <span>评分：</span>
				<div id="starttwo" class="block clearfix">
				<input type="hidden" name="critiStars"  id="critiStars">
				<input type="hidden" name="orderid"  id="orderid" value="${order.id}">
			       <div  class="star_score"></div>
			       <!--  <p style="float:left;"><span class="fenshu">1</span> 分</p>  -->
			    </div>
				<script type="text/javascript">
				 scoreFun($("#starttwo"),{
			           fen_d:22,//每一个a的宽度
			           ScoreGrade:5//a的个数 10或者
			         });
				</script>
		      <br/>
		      <span class="content-tab-span">标签：</span>
		       <dl class="content-dl">
		       <c:forEach items="${goodItems}" var="item">
		          <dd><input type="checkbox" name="chk" style="vertical-align:middle;" value="${item.id}"><a style="vertical-align:middle;">${item.name}</a></dd>
		            
		       </c:forEach> 
		
		       <script>
		         var $dd = $("dd");
		         for(var i=0; i<$dd.length; i++){  
        	    	 $dd[i].onclick = function(){
		        		   $(this).find(":checkbox").prop("checked",true); 
		        	 };		   
		         } 
		         
		         var $aa = $("dd a");
		         for(var i=0; i<$dd.length; i++){
		        	 $aa[i].onclick = function(){
		        		 $(this).find(":checkbox").prop("checked",true)
		        	 }
		         } 
		       </script>
		       </dl>
		       
		       <dl id="content-dl">
		       <c:forEach items="${disparityItems}" var="item2">
		          <dd><input type="checkbox" name="chk" style="vertical-align:middle;" value="${item2.id}"><a style="vertical-align:middle;">${item2.name}</a></dd>
		       </c:forEach> 
		
		       <script>
		         var $dd = $("dd");
		         for(var i=0; i<$dd.length; i++){  
        	    	 $dd[i].onclick = function(){
		        		   $(this).find(":checkbox").prop("checked",true); 
		        	 };		   
		         } 
		         
		         var $aa = $("dd a");
		         for(var i=0; i<$dd.length; i++){
		        	 $aa[i].onclick = function(){
		        		 $(this).find(":checkbox").prop("checked",true)
		        	 }
		         } 
		       </script>
		       </dl>
		        <br><br><br>
		       <span class="span">评价：</span>
		       <textarea maxlength="500" id="critiRemark"></textarea>
		       <br>
		       <strong id="gptu">您还可以输入<var style="color: #C00">--</var>个字符</strong>
		       <input class="content-tab-input" type="button" value="提交"> 
		  </div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
		<script>
		 var laypage = layui.laypage;
		 var itemid = "";
		 $(document).ready(function() {
			  var counter = $("#divform textarea").val().length; 
		         $("#gptu var").text(500 - counter);
	            $(document).keyup(function() {
	                var text = $("#divform textarea").val();
	                var counter = text.length;
	                $("#gptu var").text(500 - counter);
	            });
	        });
			$(".content-tab-input").bind("click", function() {
				if ($("#critiStars").val() == "") {
					layer.msg("请选择评分");
					return false;
				}
				if($('input[type=checkbox]:checked').length==0){
					layer.msg("请选择标签");
				return false;
				}
				if($("#critiRemark").val()==""){
				layer.msg("请输入评价内容");
				return false;
				}
				$("input[name='chk']:checkbox").each(function() {
						if ($(this).is(":checked")) {
							itemid += $(this).attr("value") + ",";
				}
				});
				$.ajax({
					type:"post",
					dataType:"json",
					url:"${ctx}/front/evaluate/saveCriti", 
					data:{orderid:$("#orderid").val(),critiStars:$("#critiStars").val(),critiRemark:$("#critiRemark").val(),itemid:itemid},
					success:function(data){
						if(data.state=="SUCCESS"){
							window.location.href="${ctx}/front/order?type=2";
						}else{
							layer.msg(data.msg);
						}

		            }
				});

			});
		</script>
		
	</body>
</html>