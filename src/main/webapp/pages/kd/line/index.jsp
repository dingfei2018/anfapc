<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>专线管理</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/added2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<link rel="stylesheet" href="${ctx }/static/pc/study/css/city-picker.css?v=${version}">
<script src="${ctx}/static/common/js/jquery.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/layui/layui.js"></script>
<%-- <script src="${ctx }/static/pc/study/js/city-picker.data.js"></script>
<script src="${ctx }/static/pc/study/js/city-picker.js"></script> --%>
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css" />
<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
<script src="${ctx}/static/kd/js/line/line.js?v=${version}"></script>
</head>
<body>
	<%@ include file="../common/head2.jsp"%>
    <%@ include file="../common/commonhead.jsp" %>
	<%@ include file="../common/head.jsp"%>
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
			      });
	     </script>
		<div class="banner-right">
			<div class="banner-right-title">
				<ul>
					<li><a href="${ctx}/kd/line" class="activet">专线列表</a></li>
					<li><a href="${ctx}/kd/line/add">新增专线</a></li>
				</ul>
			</div>
			<div class="banner-right-list">
				<form id="searchFrom" onsubmit="return false;">
				 <div class="div">
					<span class="span">出发地：</span>
					<div class="banner-right-list-liopd">
                            <div class="form-group">
                                <div style="position: relative;">
                                    <input type="hidden" name="fromCode" id="fromCode">
                                    <input id="city-picker2" class="form-control"
                                          readonly type="text"  placeholder="请选择省/市"
                                           data-toggle="city-picker" data-type="city">
                                </div>
                                <script>
                                    $(function(){
                                        $(".city-picker-span").css("width","160px");
                                    });
                                </script>
                            </div>
                        </div>
					</div>
					<div class="div">
					<span class="span">到达地：</span>
						<div class="banner-right-list-liopd">
                           
                            <div class="form-group">
                                <div style="position: relative;">
                                    <input type="hidden" name="toCode" id="toCode">
                                    <input id="city-picker3" class="form-control"
                                          readonly type="text"  placeholder="请选择省/市"
                                           data-toggle="city-picker" data-type="city">
                                </div>
                                <script>
                                    $(function(){
                                        $(".city-picker-span").css("width","160px");
                                    });
                                </script>
                            </div>
                        </div>
					</div>
					<div class="div">
					<span>出发网点：</span>
					<select name="netWorkId" id="netWorkId">
						<option value="">请选择网点</option>
						<c:forEach items="${networks}" var="net">
							<option value="${net.id}" ${model.netWorkId==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
						</c:forEach>
					</select>
				    </div>
					<div class="div">
					<span>到达网点：</span>
					<select name="arriveNetWorkId" id="arriveNetWorkId">
						<option value="">请选择网点</option>
						<c:forEach items="${networks}" var="net">
							<option value="${net.id}" ${model.arriveNetWorkId==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
						</c:forEach>
					</select>
				     </div>
					<div class="div">
					<button id="search">查询</button>
					<input class="reset" type="reset" onclick="resetCity();" value="重置"/>
                  </div>
				</form>
			</div>
			<!-- <div style="margin-top:25px;">
                    <a href="#" class="banner-right-a3" onclick="deleteAll();">删除</a>
             </div>   -->
			<div class="banner-right-list2">
				<ul class="ul2">
                	<li>
                   		 <a href="#" class="banner-right-a3" onclick="deleteAll();">删除</a>
                	</li>
            	</ul> 
			<p class="banner-right-p">专线列表</p>
			<div style="overflow: auto; width: 100%;" id="loadingId">
				<table cellspacing="0" class="table"  id="loadId" >
					<thead>
					    <th>全选 <input type="checkbox" id="selectAll" onclick="selectAll()" style="vertical-align:middle;" /></th>
						<th>序号</th>
						<th>专线线路</th>
						<th>出发网点</th>
						<th>到达网点</th>
						<th>所在园区</th>
						<th>重货价格</th>
						<th>轻货价格</th>
						<th>最低收费</th>
						<th>操作</th>
					</thead>

					<%-- <c:if test="${fn:length(page.list)>0}">
						<c:forEach items="${page.list}" var="line" varStatus="vs">
							<tr>
							    <td><span style="color:#fff;">全选</span><input type="checkbox"  value="${line.id}"></td>
								<td>${vs.index+1}</td>
								<td><a class="banner-right-a4" onclick="openDiv(${line.id})">${line.fromAdd }→${line.toAdd }</a></td>
								<td>${line.networkName }</td>
								<td>${line.arriveNetworkName }</td>
								<td>${line.ParkName }</td>
								<td>${line.price_heavy }</td>
								<td>${line.price_small }</td>
								<td>${line.starting_price }</td>
								<td><a class="banner-right-a1" href="#"
									onclick="goUpdate(${line.id})">修改</a><a
									class="banner-right-a2" href="#"
									onclick="deleteLine(${line.id},'${line.networkName }','${line.fromAdd }','${line.toAdd }')">删除</a></td>
							</tr>
						</c:forEach> 
					</c:if> --%>
				</table>
			</div>
			</div>
			<div id="page" style="text-align: center;"></div>
		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp"%>
	<script type="text/javascript">
	//全选
	function selectAll() {
        if($("#selectAll").is(':checked')){
            $(".banner-right-list2 input[type='checkbox']").prop("checked", true);
        }else{
            $(".banner-right-list2 input[type='checkbox']").prop("checked", false);
        }
    }

	//全选删除
/*     var flag=false; */
    function deleteAll(){
        var array = new Array();
        $(".banner-right-list2   td  input[type='checkbox']").each(function(i){
            if($(this).prop("checked")){
                array.push($(this).val());
               /*  if($(this).attr("data")>0) flag=true; */
            }
           });
        delLine(array)
    }
    function delLine(objs) {
        if(objs==null||objs==""){
            layer.msg("请选择要删除的数据");
            return;
        }
       
        layer.confirm(
            '您确定要删除？',
            {
                btn : [ '删除', '取消' ]
            },
            function() {
                $.ajax({
                    type : "post",
                    dataType : "json",
                    url : "${ctx }/kd/line/delLine?lineId=" + objs,
                    success : function(data) {
                        if (data.state == "SUCCESS") {
                            layer.msg("删除成功！");
                            setTimeout(function(){window.location.reload(true);}, 1000);
                        } else {
                            layer.msg("删除失败");
                        }
                    }
                });
            }, function() {
            });
    }
	function openDiv(id){
        //页面层
        layer.open({
            title: '专线详情',
            type: 2,
            area: ['850px', '700px'],
            content: ['${ctx}/kd/line/lineview?id='+id, 'yes']
        });
    }
	
	//分页
    /* layui.use(['laypage'], function(){
    	var laypage = layui.laypage;
        //调用分页
        laypage({
    	      cont: 'page'
    	      ,pages: '${page.totalPage}' //得到总页数
    	      ,curr:'${page.pageNumber}',
    	      count:'${page.totalRow}'
    	      ,skip: true
        	  ,jump: function(obj, first){
        	      if(!first){
        	    	   window.location.href="${ctx}/kd/line?pageNo="+obj.curr+"&netWorkName="+$("#s_netWorkName").val()+"&sub_leader_name="+$("#s_sub_leader_name").val();
        	      }
        	  }
        	  ,skin: '#1E9FFF'
        });
    }); */
	
	/* function search(){
		$('#searchForm').submit();
	} */
	
	function goUpdate(id){
		 //页面层
        layer.open({
            title: '修改专线',
            type: 2,
            area: ['850px', '700px'],
            content: ['${ctx}/kd/line/update?id='+id, 'yes'],
            end: function () {
                //location.reload();
            	$("#search").click();
            }
        });
		/* window.location.href="${ctx}/kd/line/update?id="+id; */
	}
	
	function deleteLine(objs,networkName,fromAdd,toAdd) {
		layer.confirm(
				"确认删除"+networkName+"从"+fromAdd+"到"+toAdd+"的路线吗？",
				{
					btn : [ '删除', '取消' ]
				},
				function() {
					$.ajax({
						type : "post",
						dataType : "json",
						url : "${ctx }/kd/line/delLine?lineId="+ objs,
						success : function(data) {
							
							if (data.state == "SUCCESS") {
								layer.msg("删除成功！",{time: 1000},function(){
									$("#search").click();
									/* window.location.href="${ctx}/kd/line"; */
			                        });
							} else {
								 layer.msg("删除失败");
								/* layer.msg(obj.msg); */
							}
						}
					});
		}, function() {
				});
	}
	</script>
	<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>	
</body>
</html>
