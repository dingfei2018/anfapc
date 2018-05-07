//成本月报表
(function($){
function Payable(){
	this.searchParam = "";
    resetDate();
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
			//param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, this);
		},
		getData:function(param, obj){
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/report/profitReport',
				data:encodeURI(param),
				success:function(data){
                    var html="";
					for(var i=0;i<data.length;i++){
						html += obj.appendHtml(data[i], i+1)
				    }
					$("#table > tr").remove();
					$("#table").append(html);
                    //obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
                    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},

		appendHtml:function(obj, index){

            var maolufee = (obj.totalProfitFee - obj.totalCostFee).toFixed(2);

            var maolulu = (maolufee*100/obj.totalProfitFee).toFixed(2);
            var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td>"+index+"</td>";
            html += "<td>"+obj.ctime+"</td>";
            html += "<td>"+obj.netName+"</td>";
			html += "<td  style=\"color: #f88012;\">"+obj.totalProfitFee+"</td>";
			html += "<td style=\"color: #f88012;\">"+obj.totalCostFee+"</td>";
			html += "<td style=\"color: #f88012;\">"+maolufee+"</td>";
            html += "<td style=\"color: #f88012;\">"+maolulu+"%"+"</td>";
            html += "<td>"+(obj.count1==0?"":obj.count1)+"</td>";
            html += "<td>"+(obj.ship_nowpay_fee==0?"":obj.ship_nowpay_fee)+"</td>";
            html += "<td>"+(obj.ship_pickuppay_fee==0?"":obj.ship_pickuppay_fee)+"</td>";
            html += "<td>"+(obj.ship_receiptpay_fee==0?"":obj.ship_receiptpay_fee)+"</td>";
            html += "<td>"+(obj.ship_monthpay_fee==0?"":obj.ship_monthpay_fee)+"</td>";
            html += "<td>"+(obj.ship_arrearspay_fee==0?"":obj.ship_arrearspay_fee)+"</td>";
            html += "<td>"+(obj.ship_goodspay_fee==0?"":obj.ship_goodspay_fee)+"</td>";
            html += "<td>"+(obj.plus_fee==0?"":obj.plus_fee)+"</td>";
            html += "<td>"+(obj.minus_fee==0?"":obj.minus_fee)+"</td>";
            html += "<td>"+(obj.ship_rebate_fee==0?"":obj.ship_rebate_fee)+"</td>";
            html += "<td>"+(obj.thfee==0?"":obj.thfee)+"</td>";
            html += "<td>"+(obj.dbfee==0?"":obj.dbfee)+"</td>";
            html += "<td>"+(obj.shfee==0?"":obj.shfee)+"</td>";
            html += "<td>"+(obj.zzfee==0?"":obj.zzfee)+"</td>";
            html += "<td>"+(obj.load_nowtrans_fee==0?"":obj.load_nowtrans_fee)+"</td>";
            html += "<td>"+(obj.load_nowoil_fee==0?"":obj.load_nowoil_fee)+"</td>";
            html += "<td>"+(obj.load_backtrans_fee==0?"":obj.load_backtrans_fee)+"</td>";
            html += "<td>"+(obj.load_attrans_fee==0?"":obj.load_attrans_fee)+"</td>";
            html += "<td>"+(obj.load_allsafe_fee==0?"":obj.load_allsafe_fee)+"</td>";
            html += "<td>"+(obj.load_start_fee==0?"":obj.load_start_fee)+"</td>";
            html += "<td>"+(obj.load_other_fee==0?"":obj.load_other_fee)+"</td>";
            html += "<td>"+(obj.load_atunload_fee==0?"":obj.load_atunload_fee)+"</td>";
            html += "<td>"+(obj.load_atother_fee==0?"":obj.load_atother_fee)+"</td>";

			return html;
		},

		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/report/exportProfitReport?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}

};

$(function(){
	new Payable();
})
})(jQuery);




