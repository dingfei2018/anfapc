//营业额月报表
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
			//param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, this);
		},
		getData:function(param, obj){
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/report/turnoverReport',
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
            var totalTurnover = obj.total_ship_nowpay_fee+obj.total_ship_pickuppay_fee+obj.total_ship_receiptpay_fee+obj.total_ship_monthpay_fee+obj.total_ship_arrearspay_fee+obj.total_ship_goodspay_fee+obj.total_plus_fee-obj.total_minus_fee-obj.total_ship_rebate_fee ;
            var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td>"+index+"</td>";
            html += "<td>"+obj.month+"</td>";
            html += "<td>"+obj.netName+"</td>";
			html += "<td  style=\"color: #f88012;\">"+obj.total_amount+"</td>";
			html += "<td  style=\"color: #f88012;\">"+totalTurnover+"</td>";
            html += "<td>"+(obj.total_ship_nowpay_fee==0?'':obj.total_ship_nowpay_fee)+"</td>";
            html += "<td>"+(obj.total_ship_pickuppay_fee==0?'':obj.total_ship_pickuppay_fee)+"</td>";
            html += "<td>"+(obj.total_ship_receiptpay_fee==0?'':obj.total_ship_receiptpay_fee)+"</td>";
            html += "<td>"+(obj.total_ship_monthpay_fee==0?'':obj.total_ship_monthpay_fee)+"</td>";
            html += "<td>"+(obj.total_ship_arrearspay_fee==0?'':obj.total_ship_arrearspay_fee)+"</td>";
            html += "<td>"+(obj.total_ship_goodspay_fee==0?'':obj.total_ship_goodspay_fee)+"</td>";
            html += "<td>"+(obj.total_plus_fee==0?'':obj.total_plus_fee)+"</td>";
            html += "<td>"+(obj.total_minus_fee==0?'':obj.total_minus_fee)+"</td>";
            html += "<td>"+(obj.total_ship_rebate_fee==0?'':obj.total_ship_rebate_fee)+"</td>";

			return html;
		},

		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/report/exportTurnoverReport?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);




