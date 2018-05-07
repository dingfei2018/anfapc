//按运输类型3干线 7:到付运输费  loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
(function($){
function Payable(){
	this.searchParam = "";
	this.init();
}
Payable.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));

			var ExcelParam;
			var trunkLineType = $("#excelExport").attr("trunkLineType");
			var loadAtFeeFlag = $("#excelExport").attr("loadAtFeeFlag");
			if(trunkLineType != null){
                ExcelParam = "&transportType=3&trunkLineType="+trunkLineType;
			}else{
				ExcelParam = "&loadAtFeeFlag="+loadAtFeeFlag;
			}

			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport(ExcelParam);
            }, this));

			$(".tab_css_1").on("click", ".viewId",this.openDetail);
			this.search();
		},
		search:function(){
			var param = $("#searchFrom").serialize();
			param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, 1, this);
		},
		getData:function(param, pageNo, obj){
            var trunkLineType = $("#loadingId").attr("trunkLineType");
            var p = "?transportType=3&trunkLineType="+trunkLineType;
            var loadAtFeeFlag = $("#loadingId").attr("loadAtFeeFlag");//undefined
            if(loadAtFeeFlag!=undefined){
                p = "?loadAtFeeFlag="+loadAtFeeFlag;
			}
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/finance/payable/trunkLoadListSearch'+p,
				data:encodeURI(param),
				success:function(data){
                    var html="";
					for(var i=0;i<data.list.length;i++){
						html += obj.appendHtml(data.list[i], i+1, trunkLineType,loadAtFeeFlag)
				    }
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},

		appendHtml:function(obj, index, trunkLineType,loadAtFeeFlag){
			var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.load_id+"' loadstatus='"+obj.load_fee_status+"' data='"+obj.load_fee+"'>";
			html += "<td>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.load_id+"' style='color: #3974f8;cursor: pointer;'>"+obj.load_sn+"</td>";
            html += "<td>"+obj.enetworkName+"</td>";
            html += "<td>"+obj.snetworkName+"</td>";

            if(obj.load_delivery_status==1){
                html += "<td style=\"color: #ff7801;\">配载中</td>";
            }else if (obj.load_delivery_status==2){
                html += "<td style=\"color: #ff7801;\">已发车</td>";
            }else if (obj.load_delivery_status==3){
                html += "<td style=\"color: #ff7801;\">已到达</td>";
            }else{
                html += "<td style=\"color: #ff7801;\">已完成</td>";
            }


            if(trunkLineType==7){
                if (obj.load_attrans_fill) {
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
                } else {
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
                }
                html += "<td style=\"color: #ff7801;\">"+obj.load_attrans_fee+"</td>";
                if(obj.load_attrans_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
				}else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_attrans_fee+"</td>";
				}
			}else{
            	if(loadAtFeeFlag == 0){
                    if (obj.load_atunload_fill) {
                        html += "<td style=\"color: #ff7801;\">已结算</td>";
                    } else {
                        html += "<td style=\"color: #ff7801;\">未结算</td>";
                    }
                    html += "<td style=\"color: #ff7801;\">"+obj.load_atunload_fee+"</td>";
                    if(obj.load_atunload_fill){
                        html += "<td style=\"color: #ff7801;\">"+0+"</td>";
					}else{
                        html += "<td style=\"color: #ff7801;\">"+obj.load_atunload_fee+"</td>";
					}

				}else if(loadAtFeeFlag ==1){
                    if (obj.load_atother_fill) {
                        html += "<td style=\"color: #ff7801;\">已结算</td>";
                    } else {
                        html += "<td style=\"color: #ff7801;\">未结算</td>";
                    }
                    html += "<td style=\"color: #ff7801;\">"+obj.load_atother_fee+"</td>";
                    if(obj.load_atother_fill){
                        html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    }else{
                        html += "<td style=\"color: #ff7801;\">"+obj.load_atother_fee+"</td>";
                    }
				}else {
                    html += "<td></td>";
                    html += "<td></td>";
                }
			}



            html += "<td>"+new Date(obj.load_depart_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+new Date(obj.load_arrival_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.truck_id_number+"</td>";
            html += "<td>"+obj.truck_driver_name+"</td>";
            html += "<td>"+obj.truck_driver_mobile+"</td>";

            html += "<td>"+obj.fromCity+"</td>";
            html += "<td>"+obj.toCity+"</td>";


			return html;
		},
		page:function(param, totalRow, totalPage, pageNumber, cobj){
			layui.use(['laypage'], function(){
		    	var laypage = layui.laypage;
			    laypage({
				      cont: 'page'
				      ,pages: totalPage //得到总页数
				      ,curr:totalPage==0?(pageNumber-1):pageNumber
				      ,skip: true //是否开启跳页
				      ,count:totalRow
			    	  ,jump: function(obj, first){
			    	      if(!first){
			    	    	  cobj.getData(param, obj.curr, cobj);
			    	      }
			    	  }
			    	  ,skin: '#1E9FFF'
			    });
	    	});
		},

		openDetail:function(){
			var loadId = $(this).attr("data");
			layer.open({
				  type: 2,
				  title: "配载单详情",
				  area: ['1200px', '700px'],
				  content: ['/kd/loading/loadingView?loadId='+loadId, 'yes']})
		},
		excelExport:function(excelParam){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/payable/trunkLoadListExport?"+obj.searchParam+excelParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);




