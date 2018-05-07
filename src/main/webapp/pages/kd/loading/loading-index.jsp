<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>装车配载</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/stowage2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
<%@ include file="../common/commonhead.jsp" %>
<script src="${ctx}/static/kd/js/load/load-index.js?v=${version}"></script>
<script src="${ctx}/static/pc/src/loading.js"></script>
</head>
<body>
	<%@ include file="../common/head2.jsp"%>
	<%@ include file="../common/head.jsp"%>
	<div class="banner">
		<%@ include file="../common/fahuoleft.jsp"%>
		<script type="text/javascript">
			$(function() {
				var _width = $("body").width();
				var _widths = $(".banner-left").width();
				var _widthd = _width - _widths - 80;
				parseInt(_widthd)
				$('.banner-right').css('width', _widthd + 'px');
			});
			$(window).resize(function() {
				var Width = $(window).width();
				var _widths = $(".banner-left").width();
				var _widthd = Width - _widths - 80;
				parseInt(_widthd)
				$('.banner-right').css('width', _widthd + 'px');
			});
		</script>
		<div class="banner-right">
			<ul>
				<li><a href="#" class="activet at">干线配载</a></li>
				<li><a href="${ctx}/kd/loading/tihuo" class="at">提货配载</a></li>
				<li><a href="${ctx}/kd/loading/duanbo" class="at">短驳配载</a></li>
				<li><a href="${ctx}/kd/loading/songhuo" class="at">送货配载</a></li>
				<li><a href="${ctx}/kd/loading/loadlist" class="at">发车列表</a></li>
			</ul>
			
			<div class="banner-right-list">
				<div class="div">
					<span class="span">配载网点：</span>
					<select class="select"  name="network_id"  id="network_id">
              	   		<option value="0">请选择网点</option>
					<c:forEach items="${networks}" var="net">
						<option value="${net.id}">${net.sub_network_name}</option>
					</c:forEach>
              	    </select>
				</div>
				<div class="div">
					<span class="span">到货网点：</span> 
					<select name="load_next_network_id"  id="load_next_network_id">
               	   		<option value="0">请选择网点</option>
						<c:forEach items="${allnetWorks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
               	    </select>  	
				</div>
				<div class="div">
					<span class="span">车牌号：</span> 
					<input type="hidden" name="truck_id_number"  id="truck_id_number">
					<input type="text" name="truckId" id="truckId"/>
				</div>
				<div class="div"> 
					<span class="span">司机：</span><input type="text" name="truck_driver_name" id="truck_driver_name"/>
				</div>
				<div class="div">
					<span class="span">司机电话：</span><input type="text" name="truck_driver_mobile" id="truck_driver_mobile"/>
				</div>
				<div class="div">
					<span class="span">出发地：</span>
					<input type="hidden" name="load_delivery_from"  id="load_delivery_from">
					<input type="text" id="toAddr1"/>
					<input type="text" id="toAddrCounty1" />
				</div> 
				 <div class="div">
					<span class="span">到达地：</span>
					<input type="hidden" name="load_delivery_to"  id="load_delivery_to">
					<input type="text" id="toAddr2" />
					<input type="text" id="toAddrCounty2" />
				</div>
				<div class="div">
					<span class="span">费用分摊：</span>
					<select name="load_fee_allocation_way">
	               	   <option value="3" >按重量</option>
	               	   <option value="2">按体积</option>
	               	   <option value="1">按单</option>
               	    </select>
				</div>
				<div class="div">
					<span class="span">备注：</span><input type="text" name="load_remark" maxlength="45"/>
				</div>
			</div>

            <div class="banner-right-list2li">
               <div class="banner-right-list2li-tio">
                   <p>现付运输费</p>
                   <input type="text" name="load_nowtrans_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>现付油卡费</p>
                   <input type="text" name="load_nowoil_fee"  onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>回付运输费</p>
                   <input type="text" name="load_backtrans_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
                <div class="banner-right-list2li-tio">
                   <p>到付运输费</p>
                   <input type="text" name="load_attrans_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>整车保险费</p>
                   <input type="text" name="load_allsafe_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>发站装车费</p>
                   <input type="text" name="load_start_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>发站其他费</p>
                   <input type="text" name="load_other_fee" onkeyup="javascript:checkInputIntFloat(this);">
               </div>
               <div class="banner-right-list2li-tio">
                   <p>配载费用合计</p>
                   <input type="text" id="totalId" name="load_fee" value="0" readonly="readonly">
               </div>
            </div>

			<div class="banner-right-list2">
			   <div class="banner-right-list2-left">
			   <div class="banner-right-list2-lefts">
			      <img src="${ctx}/static/kd/img/youce.png" id="loadId" style="cursor: pointer;"/>
			   </div>
			     <div class="div">
					<span class="span">到达地：</span>
					<div class="banner-right-list-liopc">
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" id="endCode" /> <input id="city-picker3" class="form-control" placeholder="请选择省/市/区" readonlytype="text" data-toggle="city-picker">
							</div>
						</div>
					</div>
				</div>
				 <div class="div">
					<span class="span" style="width:70px;">到货网点：</span> 
					<select id="networkId">
               	   		<option value="0">请选择网点</option>
						<c:forEach items="${allnetWorks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
               	    </select>  	
				</div>
				<div class="div">
					<input type="button" class="button" value="查询" id="search">
				</div>
				  </div>
			   <div style="width: 100%;overflow-y:auto; overflow-x:auto; height:848px;">
					<table border="1" class="tab_css_1" id="shiplistId">
						<thead>
							<th><input type="checkbox" class="selectAll"></th>
							<th>序号 </th>
							<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" class="shipfilter" types="1"></div></th>
							<th>到货网点</th>
							<th>到达地</th>
							<th>货物名称</th>
							<th>体积</th>
							<th>重量</th>
							<th>件数</th>
							<th>出发地</th>
							<th>开单网点</th>
						</thead>
					</table>
				</div>
				<p class="p" id="querylistId"></p>
			</div>
            <div class="banner-right-list3">
             <div class="banner-right-list3-left">
                <div class="banner-right-list3-lefts">
                  <img src="${ctx}/static/kd/img/zuoce.png" id="rmloadId" style="cursor: pointer;"/>
                </div>
                <input type="button" class="button" id="fahuo" value="确认配载">
             </div>
                 <div style="width: 100%;overflow-y:auto; overflow-x:auto; height:848px;">
					<table border="1" class="tab_css_1"  id="addshiplistId">
						<thead>
							<th><input type="checkbox"  class="selectAll"></th>
							<th>序号</th>
							<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" class="shipfilter" types="2"></div></th>
							<th>到货网点</th>
							<th>到达地</th>
							<th>货物名称</th>
							<th>体积</th>
							<th>重量</th>
							<th>件数</th>
							<th>出发地</th>
							<th>开单网点</th>
						</thead>
					</table>
				</div>
				<p class="p"  id="loadlistId"></p>
            </div>
		   
		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp"%>
</body>
