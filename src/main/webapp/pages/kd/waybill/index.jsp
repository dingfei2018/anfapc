<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运单列表</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/waybills.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp" %>
</head>
<body>
	<%@ include file="../common/head2.jsp" %>
	<%@ include file="../common/head.jsp" %>
	<div class="banner">
    <%@ include file="../common/fahuoleft.jsp" %>
    <script type="text/javascript">
		     $(function(){
				  var _width=$("body").width();
				  var _widths = $(".banner-left").width();
				  var _widthd = _width - _widths - 80;
				  parseInt(_widthd);
				  $('.banner-right').css('width',_widthd+'px');
		     });
		     $(window).resize(function(){ 
		    	  var Width = $(window).width();
	    	      var _widths = $(".banner-left").width();
		  		  var _widthd = Width - _widths - 80;
		  		  parseInt(_widthd);
		  		  $('.banner-right').css('width',_widthd+'px');
		     });
	  </script>
    <div class="banner-right">
       <div class="banner-right-ultitle" style="overflow: hidden;">
				<ul>
		            <li>
		                <a href="/kd/waybill" class="activet at">运单列表</a>
		            </li>
		           <li>
		                <a href="/kd/waybill/updateChangeIndex"  class="at" >改单记录</a>
		            </li>
		            <li>
		                <a href="/kd/waybill/deleteChangeIndex"  class="at" >删除记录</a>
		            </li>
	       		 </ul> 
	       	</div>
        <form id="searchFrom" onsubmit="return false;" >
            <div class="banner-right-list">
                      <div class="div">
                        <span class="span">开单网点：</span>
                        <select name="netWorkId" id="netWorkId">
                            <option value="">请选择 </option>
                            <c:forEach var="work" items="${networks}">
                                <option value="${work.id}">${work.sub_network_name}</option>
                            </c:forEach>
                        </select>
                     </div>
                     <div class="div">
	                   <span class="span">开单日期：</span>
	                    <input type="text" class="Wdate" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
						</div>
						 <div class="div">
						<span class="spanc">至</span><input type="text" class="Wdate"  id="endTime" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
                       </div>
                       <div class="div">
                    <span class="span">出发地：</span>
                        <div class="banner-right-list-liopd">
                            <div class="form-group">
                                <div style="position: relative;">
                                    <input type="hidden" name="fromCode" id="fromCode">
                                    <input id="city-picker3" class="form-control"
                                          readonly type="text"  placeholder="请选择省/市/区"
                                           data-toggle="city-picker">
                                </div>
                            </div>
                        </div>
                      </div>
                      <div class="div">
                       <span class="span">到达地：</span>
                        <div class="banner-right-list-liopc">
                            <div class="form-group" >
                                <div style="position: relative;">
                                    <input type="hidden" name="toCode" id="toCode">
                                    <input id="city-picker2" class="form-control" placeholder="请选择省/市/区"
                                            readonly type="text"
                                           data-toggle="city-picker">
                                </div>
                                <script>
                                    $(function(){
                                        $(".city-picker-span").css("width","160px");
                                    })
                                </script>
                            </div>
                        </div>  
                       </div>        
                  <div class="div">
                  <span class="span">运单状态：</span>
                        <select id="shipSate" name="shipSate">
                            <option value="" >请选择</option>
                            <option value="1" >已入库</option>
                            <option value="2" >短驳中</option>
                            <option value="3" >短驳到达</option>
                            <option value="4" >已发车</option>
                            <option value="5" >已到达</option>
                            <option value="6" >收货中转中</option>
                            <option value="7" >到货中转中</option>
                            <option value="8" >送货中</option>
                            <option value="9">已签收</option>
                        </select>
                 </div> 
                  <div class="div">
                   <span class="span">托运方：</span><input type="text" id="senderId" name="senderId" />
                  </div>
                  <div class="div">
                      		<span class="span">收货方：</span><input  type="text" id="receiverId" name="receiverId" />
                 	</div>
                 	<div class="div">
                 	  		<span class="span">客户单号：</span><input type="text"  id="customerNumber" name="customerNumber" />
                  	        </div>
                  	        <div class="div">
                  			 <span class="span">运单号：</span><input type="text" id="shipSn" name="shipSn" />
                	 </div>
                	  <div class="div">
                  	 <button id="search">查询</button>
                          <input class="buttons" type="reset" onclick="resetCity();" value="重置">
                  	 </div>
            </div>
        </form>
        <div class="banner-right-list2">
              <ul class="ul2">
                <li> 
                    <a href="javascript:void(0)" id="add" class="banner-right-a3" style="color:#3974f8; background: #fff;">开单</a> </li>
                 <li> 
                    <a href="/kd/waybill/shipaAccountIndex" class="banner-right-a3" style="color:#3974f8; background: #fff;">交账汇总</a> </li>
                  <li> 
                    <a href="javascript:void(0)"  id="excelExport" class="banner-right-a3" style="color:#3974f8; background: #fff;">导出EXCEL</a> </li>
            </ul> 
            <p class="banner-right-p" style="margin-top:10px;">运单列表</p>
            <div style="overflow: auto; width: 100%;  " id="loadingId">
                <table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
                    <thead>
                    <th>序号</th>
                    <th>运单号</th>
                    <th>开单网点</th>
                    <th>开单日期</th>
                    <th>客户单号</th>
                    <th>托运方</th>
                    <th>收货方</th>
                    <th>出发地</th>
                    <th>到达地</th>
                    <th>运单状态</th>
                    <th>体积</th>
                    <th>重量</th>
                    <th class="banner-right-padding">件数</th>
                    <th class="banner-right-th">操作</th>
                    </thead>
                </table>
            </div>
          
            <div id="page" style="text-align: center;">
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/loginfoot.jsp" %>
    <script src="${ctx}/static/kd/js/waybill/waybill.js?v=${version}"></script>
<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>	
 <script src="${ctx}/static/kd/js/print/waybillprint.js"></script>
</body>
</html>