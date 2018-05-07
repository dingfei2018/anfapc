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
				url:'/kd/report/costReport',
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

            var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td>"+index+"</td>";
            html += "<td>"+obj.ctime+"</td>";
            html += "<td>"+obj.netName+"</td>";
			html += "<td  style=\"color: #f88012;\">"+obj.totalFee+"</td>";
			html += "<td>"+(obj.gxcount == 0 ?"":obj.gxcount)+"</td>";
			html += "<td>"+(obj.gxfee == 0 ?"":obj.gxfee)+"</td>";
            html += "<td>"+(obj.thcount==0?"":obj.thcount)+"</td>";
            html += "<td>"+(obj.thfee==0?"":obj.thfee)+"</td>";
            html += "<td>"+(obj.dbcount==0?"":obj.dbcount)+"</td>";
            html += "<td>"+(obj.dbfee==0?"":obj.dbfee)+"</td>";
            html += "<td>"+(obj.shcount==0?"":obj.shcount)+"</td>";
            html += "<td>"+(obj.shfee==0?"":obj.shfee)+"</td>";
            html += "<td>"+(obj.zzcount==0?"":obj.zzcount)+"</td>";
            html += "<td>"+(obj.zzfee==0?"":obj.zzfee)+"</td>";

			return html;
		},

		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/report/exportCostReport?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}

};

$(function(){
	new Payable();
})
})(jQuery);




