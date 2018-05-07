<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>公司专线</title>
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/special.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
	 	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	 	<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		    <div class="banner-guno">
		    <img src="${ctx}/static/pc/img/banner_line.jpg"> 
		   </div>
		
		     <div class="banner-list">
		   
		      <div class="banner-list-left">
		          <p>物流专线</p>
		          <ul>
		             <li class="banner-list-lefts"><a class="banner-list-lefts-a">物流专线</a></li>
		             <li><a class="banner-list-lefts-as" href="${ctx}/company/branch?id=${id}">网点分布</a></li>
		          </ul>
		          <div class="banner-list-left1">
		              <img src="${ctx}/static/pc/img/zs_con_icon1.jpg"/>
		              <dl>
		                 <dt>注册信息</dt>
		                 <dd>${company.is_certification==2?'后台系统已认证':'后台系统未认证'}</dd>
		              </dl>
		          </div>
		          
		          <div class="banner-list-left2">
		              <img src="${ctx}/static/pc/img/zs_con_icon2.jpg"/>
		              <dl>
		                 <dt>零担运输</dt>
		                 <dd>专线 特大货物 大件</dd>
		              </dl>
		          </div>
		          
		          <div class="banner-list-left3">
		              <img src="${ctx}/static/pc/img/zs_con_icon3.jpg"/>
		              <dl>
		                 <dt>服务内容</dt>
		                 <dd>整车运输 零担运输</dd>
		              </dl>
		          </div>
		          
		          <div class="banner-list-left4">
		              <img src="${ctx}/static/pc/img/zs_con_icon4.jpg"/>
		              <dl>
		                 <dt>联系人 : ${company.charge_person}</dt>
		                 <dd>
							<c:if test="${shop.show_mobile}">
								${empty company.corp_telphone?company.charge_mobile:company.corp_telphone}
							</c:if>		
							<c:if test="${not shop.show_mobile}">
								${company.corp_telphone}
							</c:if>                  
						</dd>
		              </dl>
		          </div>
		      </div>
		      
		     <div class="banner-list-right">
		         <div class="banner-list-rights">
		           <img src="${ctx}/static/pc/img/zs_con_icon22.jpg"/>
		           <span>首页 > 物流专线</span>
		         </div>
		         <c:if test="${not empty companyLines.list}">
		        <table cellspacing="0">
		            <tr>
		                <th>出发地</th>
		                 <th style="width:0px;"><img src="${ctx }/static/pc/img/icon_09.png" style="display: none;" /></th>
		                <th>到达地</th>
		                <th>重货价/公斤</th>
		                <th>轻货价/立方</th>
		                <th>起步价</th>
		                <th>发车频次</th>
		                <th>操作</th>
		            </tr>
		            <c:forEach items="${companyLines.list}" var="line">
						<tr>
							<td class="btn">
							<c:if test="${line.is_sale}">
								<img src="${ctx}/static/pc/img/te.gif"/>
							</c:if>
							<a style="display:inline-block; width: 112px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" title="${line.from_addr}">${line.from_addr}</a></td>
							<td><img src="${ctx }/static/pc/img/icon_10.png" /></td>
							<td><a style="display:inline-block; width: 112px; line-height:34px; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" title="${line.to_addr}" >${line.to_addr}</a></td>
							<c:if test="${line.is_sale}">
							<td style="color:#ff7800;">${line.price_heavy=='0'?'面议':line.price_heavy}${line.price_heavy=='0.00'?'':'元/公斤'}</td>
							<td style="color:#ff7800;">${line.price_small=='0.00'?'面议':line.price_small}${line.price_small=='0.00'?'':'元/立方'}</td>
							</c:if>
							<c:if test="${line.is_sale==false}">
							<td >${line.price_heavy=='0.00'?'面议':line.price_heavy}${line.price_heavy=='0.00'?'':'元/公斤'}</td>
							<td>${line.price_small=='0.00'?'面议':line.price_small}${line.price_small=='0.00'?'':'元/立方'}</td>
							</c:if>
							<td>${line.starting_price=='0.00'?'--':line.price_small}</td>
							<td>${line.frequency}次/天</td>
							<td><%--<a class="as" href="${ctx}/company/order?id=${id}&lineId=${line.id}">发货</a>--%><a class="a" href="javascript:void(0);">查看网点</a></td>
				         		
						</tr>
				        <tr style="display: none;"><td colspan="8"><div style="wdith:898px ;height:auto; background-color: #ececec; padding-top:20px; padding-bottom:20px; line-height:30px;">
				            <p>网点地址 : ${line.region}</p>
				            <p><span>联系人 : ${line.sub_leader_name}</span> <span style="margin-left: 20px;">电话 : 
				            <c:if test="${shop.show_network_mobile}">
								${empty line.sub_logistic_telphone?line.sub_logistic_mobile:line.sub_logistic_telphone}
							</c:if> 
							<c:if test="${not shop.show_network_mobile}">
								${line.sub_logistic_telphone}
							</c:if>
				            </span></p>
				       </div></td></tr>
					</c:forEach>
		        </table>
		         
		         <div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
			        </c:if>
			        <c:if test="${empty companyLines.list}">
			        <div style="text-align: center; margin-top: 260px; font-size: 16px;">暂无数据</div>
			        </c:if>
			        
			        <div id="page" style="text-align: center;">
					</div>
		     </div>   
		   </div>
		</div>
		<%@ include file="common/foot.jsp" %>
		
		<script type="text/javascript">
		$(".a").bind("click", function(){
			var tr = $(this).parents("tr").next("tr");
			if(tr.is(':hidden')){
				tr.show();
			}else{
				tr.hide();
			}
		});
		
		layui.use(['laypage'], function(){
			var laypage = layui.laypage;
		    laypage({
			      cont: 'page'
			      ,pages: '${companyLines.totalPage}' //得到总页数
			      ,curr:'${fn:length(companyLines.list)==0?(companyLines.pageNumber-1):companyLines.pageNumber}'
			      ,skip: true //是否开启跳页
		    	  ,jump: function(obj, first){
		    	      if(!first){
		    	    	  curr =obj.curr; 
		    	    	  window.location.href="${ctx}/company/special?id=${id}&pageNo="+curr;
		    	      }
		    	  }
		    	  ,skin: '#1E9FFF'
		    });
		});


		/* function search(){
			var sarea = $("#startId").find("select[name=area]").val();
			var earea = $("#endId").find("select[name=area]").val();
			if(sarea==null||sarea==""||earea==null||earea==""){
				if(sarea==null||sarea=="")sarea = $("#startId").find("select[name=city]").val();
				if(earea==null||earea=="")earea = $("#endId").find("select[name=city]").val();
				if(sarea==null||sarea==""||earea==null||earea==""){
					if(sarea==null||sarea=="")sarea = $("#startId").find("select[name=province]").val();
					if(earea==null||earea=="")earea = $("#endId").find("select[name=province]").val();
					if(sarea==null||sarea==""||earea==null||earea==""){
						alert("请选择要查找的出发地和目的地");
						return;
					}
				}
			}
			
			$.ajax({
				  url : "${ctx}/company/searchSpecial?id=${id}",
				  type : 'GET',
				  data : {fromRegionCode:sarea,toRegionCode:earea},
				  success:function(data){
					  appendHtml(data);
				  }
			});
		}
		
		function appendHtml(data){
			var html = "<tr><th>出发地</th><th>目的地</th><th>重货价格</th><th>轻货价/立方</th><th>时效性</th><th>发车频率</th></tr>";
			$.each(data, function(index, obj) {
				var small = obj.price_small;
				var heavy = obj.price_heavy;
			    html+="<tr><td>"+obj.from_addr+"</td><td>"+obj.to_addr+"</td><td>"+small.toFixed(2)+"元/公斤</td><td>"+heavy.toFixed(2)+"元/立方</td><td>"+obj.survive_time+"小时</td><td>"+obj.frequency+"次/天</td></tr>";
			});
			$("tbody").empty();
			$("tbody").append(html);
		} */



</script>		
	</body>
</html>
