<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的回单</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/receipt.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp" %>
    <script src="${ctx}/static/kd/js/my.receipt.js?v=${version}"></script>
</head>
<body>
	<%@ include file="../common/head2.jsp" %>
	<%@ include file="../common/head.jsp" %>
	<div class="banner">
    <%@ include file="../common/receiptleft.jsp" %>
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
       <div class="banner-right-ultitle" style="overflow: hidden;">
	       	</div>
         <form id="searchFrom" onsubmit="return false;" >
            <div class="banner-right-list">
                     <div class="div">
                        <span class="span">开单网点：</span>
                        <select name="snetWorkId" id="snetWorkId">
                            <option value="">请选择</option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}" >${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
                   </div>
                    <div class="div">
	                   <span class="spant">代收回单网点 ：</span>
	                         <select class="selects" id="enetWorkId" name="enetWorkId">
                                 <option value="">请选择</option>
                                 <c:forEach items="${allnetWorks}" var="enetworks">
                                 <option value="${enetworks.id}"  >${enetworks.sub_network_name}</option>
                                 </c:forEach>
	                         </select>
						 </div>
						
                       <div class="div">
                    <span class="span">回单状态 ：</span>
                      	<select id="status" name="status">
                      		<option value="">请选择</option>
                      		<option value="0">未回收</option>
                      		<option value="1">已回收</option>
                      		<option value="2">已寄出</option>
                      		<option value="3">已接收</option>
                      		<option value="4">已发放</option>
                      	</select>
                    </div>
                     <div class="div">
                       <span class="span">托运方：</span>
                         <input type="text" id="senderId" name="senderId">
                    </div> 
                     <div class="div">
                       <span class="span">收运方：</span>
                         <input type="text" id="receiveId" name="receiveId">
                    </div>       
                    <div class="div">
                       <span class="spant">代收网点邮寄单号 ：</span>
                         <input class="inputs" type="text" name="prePostNo">
                    </div>  
                      <div class="div">
                       <span class="spant">发放邮寄单号：</span>
                         <input class="inputs" type="text" name="sendPostNo">
                    </div>  
                     <div class="div">
						<span class="span">运单号 ：</span> <input type="text" name="shipSn">
                     </div>
                  	 <div class="div">
                  	  <button id="search">查询</button>
                  	  <input class="buttons" type="reset" value="重置"/>
                  	 </div> 
                  	  
            </div>
        </form>

        <div class="banner-right-list2">
         <ul class="ul2">
               
                <li> 
                    <a href="#" class="banner-right-a3" id="overId">回单接收</a>
                </li>
                <li>
                    <a href="#" class="banner-right-a3" id="grantId">回单发放</a>
                </li>
                <li>
                    <a href="#"  class="banner-right-a3" id="excelExport" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>                </li>
            	<li>
            		<div id="page" style="text-align: center;"> </div>
            	</li>
            	
            </ul>
            <p class="banner-right-p">回单列表</p>
            <div style="overflow: auto; width: 100%;  " id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <th><label>全选</label><input type="checkbox" id="selectAll"/></th> 
                    <th>序号</th>
                    <th>运单号</th>
                    <th>开单网点</th>
                    <th>开单日期</th>
                    <th>出发地</th>
                    <th>到达地</th>
                    <th>托运方</th>
                    <th>收货方</th>
                    <th>回单状态</th>
                    <th>代收回单网点</th>
                    <th>代收网点邮寄单号</th>
                    <th  class="banner-right-padding">发放邮寄单号</th>
                    <th class="banner-right-th">操作</th>
                    </thead>
                </table>
                <div id="page" style="text-align: center;">
                </div>
            </div>
           

            
        </div>

    </div>
</div>
<%@ include file="../common/loginfoot.jsp" %>

<script>
    function openSignView(shipId,gid){
        layer.open({
            type: 2,
            title: "运单详情",
            area: ['850px', '700px'],
            content: ['/kd/collectionReceipt/signEnclosure?shipId='+shipId+'&gid='+gid,'yes']})
    }
	function openLog(shipId){
		//页面层
		layer.open({
		  type: 2,
		  area: ['850px', '700px'], //宽高
		  content: ['${ctx}/kd/track/receipt?shipId='+shipId, 'yes']
		});
	}
   

</script>
</body>

</html>