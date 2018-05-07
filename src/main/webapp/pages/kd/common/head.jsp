<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kd/css/head.css" />
 <div class="content">
	<div class="content-list">
		<div class="content-img" style="cursor: pointer;" onclick="window.location.href='${ctx}/kd'">
			<img src="${ctx}/static/kd/img/content.png">
		</div>
		
		<c:if test="${fn:contains(sessionScope.firstMenuName, '基础资料')}" >
		<div class="content-bas">
			<p>基础资料 <img src="${ctx}/static/kd/img/drop-down.png"></p>
                 <div class="content-bas-list">
                 	<ul>
                 	<c:if test="${fn:length(sessionScope.baseData)>0}">
				 	<c:forEach items="${sessionScope.baseData}" var="menu" varStatus="vs">
				 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
				 	</c:forEach>
				 </c:if>
                 	</ul>
                 </div>
		</div>
		</c:if>
		
		<c:if test="${fn:contains(sessionScope.firstMenuName, '收发货管理')}" >
		<div class="content-bas2">
			<p>收发货管理 <img src="${ctx}/static/kd/img/drop-down.png"></p>
                <div class="content-bas-list">
                	<ul>
                	<c:if test="${fn:length(sessionScope.reDeMage)>0}">
			 	<c:forEach items="${sessionScope.reDeMage}" var="menu" varStatus="vs">
			 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
				 </c:forEach>
			 </c:if>
                	</ul>
                </div>
		</div>
		</c:if>
		
			<c:if test="${fn:contains(sessionScope.firstMenuName, '异常管理')}" >
		<div class="content-bas3">
			<p><a href="#">异常管理</a></p>
			<div class="content-bas-list">
                  	<ul>
                  	<c:if test="${fn:length(sessionScope.abnormal)>0}">
					 	<c:forEach items="${sessionScope.abnormal}" var="menu" varStatus="vs">
					 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
						 </c:forEach>
					 </c:if>
                  	
                  	</ul>
                  </div>
		</div>
		</c:if>
		<c:if test="${fn:contains(sessionScope.firstMenuName, '回单管理')}" >
		<div class="content-bas3">
			<p><a href="#">回单管理</a></p>
			<div class="content-bas-list">
                  	<ul>
                  	<c:if test="${fn:length(sessionScope.receipt)>0}">
					 	<c:forEach items="${sessionScope.receipt}" var="menu" varStatus="vs">
					 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
						 </c:forEach>
					 </c:if>
                  	
                  	</ul>
                  </div>
		</div>
		</c:if>
		
		<c:if test="${fn:contains(sessionScope.firstMenuName, '操作查询')}" >
		<div class="content-bas3">
			<p>操作查询</p>
			<div class="content-bas-lists">
                  	<ul>
                  	<c:if test="${fn:length(sessionScope.LaunchQuery)>0}">
					 	<c:forEach items="${sessionScope.LaunchQuery}" var="menu" varStatus="vs">
					 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
						 </c:forEach>
					 </c:if>
                  	</ul>
                  </div>
		</div>
		</c:if>
		
		<c:if test="${fn:contains(sessionScope.firstMenuName, '财务管理')}" >
		<div class="content-bas3">
			<p><a href="#">财务管理</a></p>
			<div class="content-bas-list">
                  	<ul>
                  	<c:if test="${fn:length(sessionScope.financialMage)>0}">
					 	<c:forEach items="${sessionScope.financialMage}" var="menu" varStatus="vs">
					 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
						 </c:forEach>
					 </c:if>
                  	</ul>
                  </div>
		</div>
		</c:if>

		<c:if test="${fn:contains(sessionScope.firstMenuName, '报表中心')}" >
			<div class="content-bas3">
				<p><a href="#">报表中心</a></p>
				<div class="content-bas-list">
					<ul>
						<c:if test="${fn:length(sessionScope.report)>0}">
							<c:forEach items="${sessionScope.report}" var="menu" varStatus="vs">
								<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
							</c:forEach>
						</c:if>
					</ul>
				</div>
			</div>
		</c:if>
	
		
		<c:if test="${fn:contains(sessionScope.firstMenuName, '公司管理')}" >
		<div class="content-bas3">
			<p>公司管理</p>
			<div class="content-bas-list">
                  	<ul>
                  	<c:if test="${fn:length(sessionScope.baseSet)>0}">
					 	<c:forEach items="${sessionScope.baseSet}" var="menu" varStatus="vs">
					 	<li><a href="${ctx}/${menu.href}">${menu.name}</a></li>
						 </c:forEach>
					 </c:if>
                  	</ul>
                  </div>
		</div>
		
		<input type="text" class="content-bas-input">
		
		
		</c:if>

		<c:if test="${sessionScope.waybillButton}">
		<div class="content-bas5" onclick="addShip();"  ><img src="${ctx}/static/kd/img/button2.png" /> 创建运单</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">

function active() {
	var url=window.location.pathname;
	$("a").each(function(){
		if(url=='/kd/customer'|url=='/kd/customer/add'){
			if($(this).attr('href')=='/kd/customer'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/5l_icon1h.jpg'); 	
					}
				}
		}
		if(url=='/kd/product'|url=='/kd/product/add'){
			if($(this).attr('href')=='/kd/product'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/hw__blue.jpg'); 	
					}
				}
		}
		if(url=='/kd/truck'|url=='/kd/truck/add'){
			if($(this).attr('href')=='/kd/truck'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/hc_blue.jpg'); 	
					}
				}
		}
		if(url=='/kd/waybill'|url=='/kd/waybill/add'|url=='/kd/waybill/updateChangeIndex'|url=='/kd/waybill/deleteChangeIndex'
			|url=='/kd/waybill/shipaAccountIndex'){
			if($(this).attr('href')=='/kd/waybill'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/2sf_icon1h.jpg'); 	
					}
				}
		}
		
		if(url=='/kd/kucun'|url=='/kd/kucun/out'){
			if($(this).attr('href')=='/kd/kucun'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/2sf_icon2h.jpg'); 	
					}
				}
		}
		if(url=='/kd/loading'|url=='/kd/loading/loadlist'|url=='/kd/loading/add'){
			if($(this).attr('href')=='/kd/loading'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/2sf_icon3h.jpg'); 	
					}
				}
		}
		if(url=='/kd/transport'|url=='/kd/transport/confirmtGoods'){
			if($(this).attr('href')=='/kd/transport'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/2sf_icon4h.jpg'); 	
					}
				}
		}
		if(url=='/kd/transfer'|url=='/kd/transfer/transShipMent'){
			if($(this).attr('href')=='/kd/transfer'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/2sf_icon5h.jpg'); 	
					}
				}
		}
		if(url=='/kd/sign'){
			if($(this).attr('href')=='/kd/sign'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/2sf_icon6h.jpg'); 	
					}
				}
		}
		if(url=='/kd/query'){
			if($(this).attr('href')=='/kd/query'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/3l_icon1h.jpg'); 	
					}
				}
		}
		if(url=='/kd/query/ship'){
			if($(this).attr('href')=='/kd/query/ship'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/3l_icon2h.jpg'); 	
					}
				}
		}
		if(url=='/kd/query/transfer'){
			if($(this).attr('href')=='/kd/query/transfer'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/3l_icon3h.jpg'); 	
					}
				}
		}
		if(url=='/kd/finance/receivable'|url=='/kd/finance/receivable/nowpayIndex'|url=='/kd/finance/receivable/goNowpayJS'|url=='/kd/finance/receivable/pickupPayIndex'|url=='/kd/finance/receivable/goPickupPayJS'|url=='/kd/finance/receivable/receiptPayIndex'
			|url=='/kd/finance/receivable/goReceiptPayJS'|url=='/kd/finance/receivable/monthPayIndex'|url=='/kd/finance/receivable/goMonthPayJS'|url=='/kd/finance/receivable/arrearsPayIndex'|url=='/kd/finance/receivable/goArrearsPayJS'|url=='/kd/finance/receivable/goodsPayIndex'|url=='/kd/finance/receivable/goGoodsPayJS'){
			if($(this).attr('href')=='/kd/finance/receivable'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/4l_icon1h.jpg'); 	
					}
				}
		}
		/*if(url=='/kd/finance/payable'| url=='/kd/finance/payable/toSum'| url=='/kd/finance/payable/loadTransportPage' | url=='/kd/finance/payable/transfer' | url=='/kd/finance/payable/toTransferSettlement' | url=='/kd/finance/payable/rebate'| url=='/kd/finance/payable/settlement'){
			if($(this).attr('href')=='/kd/finance/payable'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/4l_icon2h.jpg'); 	
					}
				}
		}*/
        if(url.indexOf('/kd/finance/payable') >= 0){
            if($(this).attr('href')=='/kd/finance/payable'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/4l_icon2h.jpg');
                }
            }
        }
		if(url=='/kd/finance/collect'){
			if($(this).attr('href')=='/kd/finance/collect'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/4l_icon3h.jpg'); 	
					}
				}
		}
        if(url=='/kd/finance/account'|url=='/kd/finance/account/reDetailedIndex'|url=='/kd/finance/account/goRePickupJS'|url=='/kd/finance/account/goReDetailedJS'
            |url=='/kd/finance/account/payIndex'|url=='/kd/finance/account/payDetailedIndex'|url=='/kd/finance/account/goPayPickupJS'|url=='/kd/finance/account/goPayDetailedJS'){
            if($(this).attr('href')=='/kd/finance/account'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/7l_icon9h.png');
                }
            }
        }
        if(url=='/kd/finance/flow'|url=='/kd/finance/flow/detail'){
            if($(this).attr('href')=='/kd/finance/flow'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/7l_icon7h.png');
                }
            }
        }
        if(url=='/kd/finance/abnormal'|url=='/kd/finance/abnormal/toSavePage'|url=='/kd/finance/abnormal/settlement'){
            if($(this).attr('href')=='/kd/finance/abnormal'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/6l_icon6h.png');
                }
            }
        }
		if(url=='/kd/report/reSummary'|url=='/kd/report/paySummary'|url=='/kd/report/operationList'|url=='/kd/report/preProfit'){
			if($(this).attr('href')=='/kd/report/reSummary'|$(this).attr('href')=='/kd/report/paySummary'|$(this).attr('href')=='/kd/report/operationList'|$(this).attr('href')=='/kd/report/preProfit'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/4l_icon5h.jpg'); 	
					}
				}
		}
		if(url=='/kd/user'|url=='/kd/user/add'){
			if($(this).attr('href')=='/kd/user'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/5l_icon1h.jpg'); 	
					}
				}
		}
		if(url=='/kd/role'|url=='/kd/role/add'){
			if($(this).attr('href')=='/kd/role'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/5l_icon2h.jpg'); 	
					}
				}
		}
		if(url=='/kd/waybill/set'){
			if($(this).attr('href')=='/kd/waybill/set'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/5l_icon3h.jpg'); 	
					}
				}
		}
		if(url=='/kd/netWork'|url=='/kd/netWork/add'){
			if($(this).attr('href')=='/kd/netWork'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/wd1.png'); 	
					}
				}
		}
		if(url=='/kd/finance'){
			if($(this).attr('href')=='/kd/finance'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/4licon_16h.png'); 	
					}
				}
		}
		if(url=='/kd/abnormal'|url=='/kd/abnormal/add'){
			if($(this).attr('href')=='/kd/abnormal'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/allyc1.jpg'); 	
					}
				}
		}
		if(url=='/kd/abnormal/myabnormalIndex'|url=='/kd/abnormal/addmy'){
			if($(this).attr('href')=='/kd/abnormal/myabnormalIndex'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/myyc1.jpg'); 	
					}
				}
		}
		if(url=='/kd/receipt'){
			if($(this).attr('href')=='/kd/receipt'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/5l_icon1h.png'); 	
					}
				}
		}
		if(url=='/kd/collectionReceipt'){
			if($(this).attr('href')=='/kd/collectionReceipt'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/5l_icon2h.png'); 	
					}
				}
		}
		if(url=='/kd/operation'){
			if($(this).attr('href')=='/kd/operation'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/yygk1.png'); 	
					}
				}
		}
		if(url=='/kd/setting'){
			if($(this).attr('href')=='/kd/setting'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/7l_icon6h.png'); 	
					}
				}
		}
		if(url=='/kd/line'|url=='/kd/line/add'){
			if($(this).attr('href')=='/kd/line'){
				  var img=$(this).find('img');
					if(typeof(img.attr('src'))!='undefined'){
						$(img).attr('src','${ctx}/static/kd/img/6l_icon6h.png'); 	
					}
				}
		}


        if(url=='/kd/report/toTurnoverReportPage'){
            if($(this).attr('href')=='/kd/report/toTurnoverReportPage'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/8l_icon10h.png');
                }
            }
        }
        if(url=='/kd/report/costReportList'){
            if($(this).attr('href')=='/kd/report/costReportList'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/8l_icon11h.png');
                }
            }
        }
        if(url=='/kd/report/toLoadGrossProfitPage'){
            if($(this).attr('href')=='/kd/report/toLoadGrossProfitPage'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/8l_icon12h.png');
                }
            }
        }
        if(url=='/kd/report/profitReportList'){
            if($(this).attr('href')=='/kd/report/profitReportList'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/8l_icon13h.png');
                }
            }
        }
        if(url=='/kd/report/rePayIndex'){
            if($(this).attr('href')=='/kd/report/rePayIndex'){
                var img=$(this).find('img');
                if(typeof(img.attr('src'))!='undefined'){
                    $(img).attr('src','${ctx}/static/kd/img/8l_icon14h.png');
                }
            }
        }
		
	  });
}

window.onload=function(){
	active();
}
function addShip(){
    //页面层
    layer.open({
       // title: '开单',
		title:false,
        type: 2,
        area: ['1200px', '720px'],
        content: ['/kd/waybill/addShip','no'],

    });
    $(".layui-layer-setwin").remove();

}


</script>








