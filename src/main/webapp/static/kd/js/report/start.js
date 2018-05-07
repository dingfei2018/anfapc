//发车车次毛利表
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


			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));

			this.search();
		},
		search:function(){
			var param = $("#searchForm").serialize();
			param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, 1, this);
		},
		getData:function(param, pageNo, obj){
            if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/report/loadGrossProfit',
				data:encodeURI(param),
				success:function(data){
                    var html="";
					for(var i=0;i<data.list.length;i++){
						html += obj.appendHtml(data.list[i], i+1)
				    }
					$("#table > tr").remove();
					$("#table").append(html);
                    obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
                    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},

		appendHtml:function(obj, index){
            var totalTurnover = obj.total_ship_nowpay_fee + obj.total_ship_pickuppay_fee + obj.total_ship_receiptpay_fee + obj.total_ship_monthpay_fee + obj.total_ship_arrearspay_fee + obj.total_ship_goodspay_fee + obj.total_plus_fee - obj.total_minus_fee - obj.total_ship_rebate_fee ;
            var totalLoadFee = obj.total_load_nowtrans_fee + obj.total_load_nowoil_fee + obj.total_load_backtrans_fee + obj.total_load_attrans_fee + obj.total_load_allsafe_fee + obj.total_load_start_fee + obj.total_load_other_fee;
            var grossProfit = ((totalTurnover-totalLoadFee)/totalTurnover*100).toFixed(2);
            if(totalTurnover<=0){
                grossProfit = 0;
			}
            var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td>"+index+"</td>";
            html += "<td  style=\"color: #3974f8;cursor: pointer;\">"+obj.load_sn+"</td>";
            html += "<td>"+obj.netName+"</td>";
            html += "<td>"+obj.nextNetName+"</td>";
            html += "<td>"+obj.load_depart_time+"</td>";
            html += "<td>"+obj.truck_id_number+"</td>";
            html += "<td>"+obj.truck_driver_name+"</td>";
            html += "<td>"+obj.truck_driver_mobile+"</td>";
			html += "<td  style=\"color: #f88012;\">"+totalTurnover+"</td>";
			html += "<td  style=\"color: #f88012;\">"+totalLoadFee+"</td>";
			html += "<td  style=\"color: #f88012;\">"+(totalTurnover-totalLoadFee)+"</td>";
			html += "<td  style=\"color: #f88012;\">"+grossProfit+"%</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.load_count+"</td>";
            html += "<td>"+obj.load_amount+"</td>";
            html += "<td>"+obj.load_volume+"</td>";
            html += "<td>"+obj.load_weight+"</td>";

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

		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/report/exportLoadGrossProfit?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);




