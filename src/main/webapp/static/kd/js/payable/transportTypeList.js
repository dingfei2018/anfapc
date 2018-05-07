//按运输类型 1 - 提货，2-短驳，4-送货'
(function($){
function Payable(){
	this.searchParam = "";
	this.init();
}
Payable.prototype={
		init:function(){
			$("#selectAll").bind("click", this.selectAll);
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));

			var transportType = $("#excelExport").attr("transportType")
			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport(transportType);
            }, this));

			$(".tab_css_1").on("click", ".viewId",this.openDetail);
			this.search();
		},
		selectAll:function(){
			if($(this).is(':checked')){
				$(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', true)
		    }else{ 
		    	$(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', false)
		    } 
		},
		search:function(){
			var param = $("#searchFrom").serialize();
			param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, 1, this);
		},
		getData:function(param, pageNo, obj){
            var transportType = $("#excelExport").attr("transportType");
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/finance/payable/trunkLoadListSearch?transportType='+transportType,
				data:encodeURI(param),
				success:function(data){
                    var html="";
					for(var i=0;i<data.list.length;i++){
						html += obj.appendHtml(data.list[i], i+1)
				    } 
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},

		appendHtml:function(obj, index){
			var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.load_id+"' loadstatus='"+obj.load_fee_status+"' data='"+obj.load_fee+"' item='"+obj.load_fee_prepay+"'>";
			html += "<td>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.load_id+"' style='color: #3974f8;cursor: pointer;'>"+obj.load_sn+"</td>";
            html += "<td>"+obj.snetworkName+"</td>";
            html += "<td>"+new Date(obj.load_depart_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.truck_id_number+"</td>";
            html += "<td>"+obj.truck_driver_name+"</td>";
            html += "<td>"+obj.truck_driver_mobile+"</td>";


            html += "<td>"+obj.fromCity+"</td>";
            html += "<td>"+obj.toCity+"</td>";
            if(obj.load_delivery_status==1){
                html += "<td style=\"color: #ff7801;\">配载中</td>";
            }else if (obj.load_delivery_status==2){
                html += "<td style=\"color: #ff7801;\">已发车</td>";
            }else if (obj.load_delivery_status==3){
                html += "<td style=\"color: #ff7801;\">已到达</td>";
            }else{
                html += "<td style=\"color: #ff7801;\">已完成</td>";
            }
            if(obj.load_fee_fill){
                html += "<td style=\"color: #ff7801;\">已结算</td>";
            }else{
                html += "<td style=\"color: #ff7801;\">未结算</td>";
            }

            html += "<td style=\"color: #ff7801;\">"+obj.load_fee+"</td>";
            if(obj.load_fee_fill == 0){
                html += "<td>"+obj.load_fee+"</td>";
            }else{
                html += "<td>0</td>";
            }
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
		excelExport:function(transportType){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/payable/trunkLoadListExport?"+obj.searchParam+"&transportType="+transportType;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);




