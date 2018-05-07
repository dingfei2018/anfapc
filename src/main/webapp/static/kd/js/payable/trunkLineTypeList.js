//按运输类型3干线 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费
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

			var trunkLineType = $("#excelExport").attr("trunkLineType")
			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport(trunkLineType);
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
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/finance/payable/trunkLoadListSearch?transportType=3&trunkLineType='+trunkLineType,
				data:encodeURI(param),
				success:function(data){
                    var html="";
					for(var i=0;i<data.list.length;i++){
						html += obj.appendHtml(data.list[i], i+1, trunkLineType)
				    }
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},

		appendHtml:function(obj, index, trunkLineType){
			var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.load_id+"' loadstatus='"+obj.load_fee_status+"' data='"+obj.load_fee+"'>";
			html += "<td>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.load_id+"' style='color: #3974f8;cursor: pointer;'>"+obj.load_sn+"</td>";
            html += "<td>"+obj.snetworkName+"</td>";
            html += "<td>"+obj.enetworkName+"</td>";
            if(trunkLineType==1){
                html += "<td style=\"color: #ff7801;\">"+obj.load_nowtrans_fee+"</td>";
                if(obj.load_nowtrans_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
				}else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_nowtrans_fee+"</td>";
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
				}
			}else if(trunkLineType==2){
                html += "<td style=\"color: #ff7801;\">"+obj.load_nowoil_fee+"</td>";
                if(obj.load_nowoil_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
                }else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_nowoil_fee+"</td>";
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
                }
            }else if(trunkLineType==3){
                html += "<td style=\"color: #ff7801;\">"+obj.load_backtrans_fee+"</td>";
                if(obj.load_backtrans_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
                }else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_backtrans_fee+"</td>";
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
                }
            }else if(trunkLineType==4){
                html += "<td style=\"color: #ff7801;\">"+obj.load_allsafe_fee+"</td>";
                if(obj.load_allsafe_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
                }else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_allsafe_fee+"</td>";
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
                }
            }else if(trunkLineType==5){
                html += "<td style=\"color: #ff7801;\">"+obj.load_start_fee+"</td>";
                if(obj.load_start_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
                }else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_start_fee+"</td>";
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
                }
            }else if(trunkLineType==6){
                html += "<td style=\"color: #ff7801;\">"+obj.load_other_fee+"</td>";
                if(obj.load_other_fill==1){
                    html += "<td style=\"color: #ff7801;\">"+0+"</td>";
                    html += "<td style=\"color: #ff7801;\">已结算</td>";
                }else{
                    html += "<td style=\"color: #ff7801;\">"+obj.load_other_fee+"</td>";
                    html += "<td style=\"color: #ff7801;\">未结算</td>";
                }
            }else{
				html += "<td></td>";
                html += "<td></td>";
                html += "<td style=\"color: #ff7801;\">未结算</td>";
			}


            html += "<td>"+new Date(obj.load_depart_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.truck_id_number+"</td>";
            html += "<td>"+obj.truck_driver_name+"</td>";
            html += "<td>"+obj.truck_driver_mobile+"</td>";

            if(obj.load_delivery_status==1){
                html += "<td style=\"color: #ff7801;\">配载中</td>";
            }else if (obj.load_delivery_status==2){
                html += "<td style=\"color: #ff7801;\">已发车</td>";
            }else if (obj.load_delivery_status==3){
                html += "<td style=\"color: #ff7801;\">已到达</td>";
            }else{
                html += "<td style=\"color: #ff7801;\">已完成</td>";
            }


            html += "<td>"+obj.fromCity+"</td>";
            html += "<td>"+obj.toCity+"</td>";
            html += "<td>"+obj.load_count+"</td>";
            html += "<td>"+obj.load_volume+"</td>";
            html += "<td>"+obj.load_weight+"</td>";
            html += "<td>"+obj.load_amount+"</td>";

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
		excelExport:function(trunkLineType){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/payable/trunkLoadListExport?"+obj.searchParam+"&transportType=3&trunkLineType="+trunkLineType;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);




