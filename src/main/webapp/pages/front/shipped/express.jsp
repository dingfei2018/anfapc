<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>快递单查询</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<%-- <link rel="stylesheet" href="${ctx}/static/pc/shipped/inquiry.css?v=${version}" /> --%>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/express.css?v=${version}" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
		
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/js/dateFormat.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
		<script src="${ctx}/static/pc/js/search.js"></script>
		
	</head>

	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<div class="content">
		  <p class="p">当前位置：单号查询</p>
		  <div class="content-top">
		        <div class="content-top2">
		           <ul>
		             <li><a href="${ctx}/express">货物运输单号查询</a></li>
		             <li><a class="active" href="${ctx}/express/kd">快递单号查询</a></li>
		           </ul>
		            <div class="yd_lb" id="yd_lbs"> 
		            <label class="label1">
		                <input type="text" id="expNoId" placeholder="请输入快递单号"> 
		            </label></div>
		             <div class="yd_lb">
		             <input type="hidden" id="kudnameId"> 
		             <label class="label2">
		                  <input type="text" id="corpId" placeholder="请选择快递公司"> 
		            </label>
		           </div>
		            <div class="div">
		                     <ul>
		                     </ul>
		                   </div>
		                   <script type="text/javascript"> 
						     	$("#corpId").fillExpress();
						   </script> 
					<a href="#content-botton"><input class="button"  id="btnTest" type="button" onclick="loadExpress()" value="立即搜索"></a>
		        </div>
		        <div class="content-botton" id="content-botton">
		        </div>
		  </div>
      
        </div>
		<%@ include file="../common/loginfoot.jsp" %>
		
		<script type="text/javascript">
		
		//$('#kudnameId').comboSelect();
		
		function loadExpress(){
			var expNo = $("#expNoId").val();
			var expCode = $("#kudnameId").val();
			if(expNo==null||expNo==""){
				Anfa.show("请输入快递单号","#expNoId");
				return;
			}
			if(expCode==null||expCode==""){
				Anfa.show("请选择快递公司","#corpId");
				return;
			}
			$(".content-botton").mLoading("show");
			$.ajax({
				type : "post",
				dataType : "html",
				url : "${ctx}/express/kdnaio",
				data:{expNo:expNo,expCode:expCode},		
				success : function(data) {
					var obj = new Function("return" + data)();
					if(!obj.Reason){
						var html = appendHtml(obj);
						$(".content-botton").empty();
						$(".content-botton").append(html);
					}else{
						$(".content-botton").empty();
						$(".content-botton").append("<p>"+$("#corpId").val()+": "+$("#expNoId").val()+"</p><ul><li style=\"text-align: center; height: 150px;line-height: 200px;\">没有查到快递信息，请确认运单号和快递类型是否正确</li></ul>");
					}
					$(".content-botton").mLoading("hide");
				}
			});
		}
		
		
		function appendHtml(obj){
			var html = "<p>"+$("#corpId").val()+": "+$("#expNoId").val()+"</p>";
			html +="<ul>";
			var tm = "";
			for(var i=(obj.Traces.length-1);i>=0;i--){  
				 var tra = obj.Traces[i];
			     tm ="<li><label>"
			     +"<span class=\"span1\">"+new Date(tra.AcceptTime).format("hh:mm")+" <br> "+new Date(tra.AcceptTime).format("yyyy-MM-dd")+"</span>";
			     if(i==(obj.Traces.length-1)&&obj.State==3){
			    	 tm+="<span class=\"span2 active\"><b class=\"actives\"></b>"+tra.AcceptStation+"</span>"
			     }else{
			    	 tm+="<span class=\"span2\"><b  class=\"active\"></b>"+tra.AcceptStation+"</span>"
			     }
			     tm+="</label> </li>";
			     html+=tm;
		    }  
			html += "</ul>";
		    return html;
		}
		
		</script>
		
	</body>
	
</html>
