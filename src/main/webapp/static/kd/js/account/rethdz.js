//提货对账应收
(function($){
function Rethdz(){
	this.searchParam = "";
	this.init();
}
    Rethdz.prototype={
		init:function(){
			$("#selectAll").bind("click", this.selectAll);
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));

			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));
			
			$(".tab_css_1").on("click", ".viewId",this.openDetail);
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
				type:'post',
				url:'/kd/finance/account/getRePickuppayAccountData',
				data:encodeURI(param),
				success:function(data){
                    var html="";
					for(var i=0;i<data.list.length;i++){
						html += obj.appendHtml(data.list[i], i+1)
				    } 
					$("#table tr:gt(0)").remove();
					$("#table").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},
		appendHtml:function(obj, index){
			var html="";
			var feeState=obj.fee_in_fill==0?"未结算":"已结算";

			var shipState=obj.ship_status==1?"已入库":obj.ship_status==2?"短驳中":obj.ship_status==3?"短驳到达"
                :obj.ship_status==4?"已发车":obj.ship_status==5?"已到达":obj.ship_status==6?"收货中转中":obj.ship_status==7?"到货中转中"
					:obj.ship_status==8?"送货中":"已签收";

        	html+='<tr class="tr_css" align="center">'+
                '<td>'+index+'</td>'+
                '<td><a href="#" onclick="openDiv('+obj.ship_id+')" class="btn" style="color: #3974f8;cursor: pointer;">'+obj.ship_sn+'</a></td>'+
                '<td>'+obj.netWorkName+'</td>'+
                '<td>'+obj.toNetWorkName+'</td>'+
                '<td>'+obj.ship_pickuppay_fee+'</td>'+
                '<td>'+feeState+'</td>'+
                '<td>'+shipState+'</td>'+
                '<td>'+obj.goods_sn+'</td>'+
                '<td>'+obj.create_time+'</td>'+
                '<td>'+obj.fromAdd+'</td>'+
                '<td>'+obj.toAdd+'</td>'+
                '<td>'+obj.senderName+'</td>'+
                '<td>'+obj.receiverName+'</td>'+
                '<td>'+obj.productName+'</td>'+
                '<td>'+obj.ship_volume+'</td>'+
                '<td>'+obj.ship_weight+'</td>';
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
		}
		,
		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/account/downExcelRePickup?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Rethdz();
})
})(jQuery);	
	
	


