//签收
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
			
			$("#loadId").on("click", ".gun", $.proxy(function (e) {
				this.openView(e);
            }, this));
			
			$("#loadId").on("click", ".input2", $.proxy(function (e) {
				this.openPrePayView(e);
            }, this));
			
			$("#gun2").bind("click", $.proxy(function (e) {
				this.confirmPay(e);
            }, this));
			
			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
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
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/finance/payable/search',
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
		confirmPay:function(e){
			$this = e.target;
			$othis = this;
			var total = 0;
			var ids = new Array();
			$("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
				var tr = $(o).parents("tr");
				ids.push($(tr).attr("lid"));
				total +=parseFloat($(tr).attr("data"));
				var prepay=$(tr).attr("item");
				if(prepay>0)total -=prepay;
			})
			if(ids.length==0){
				layer.msg("请选择确认要收款的配载单");
				return;
			}
			var mess = "共"+ids.length+"单，应收金额 总计"+total+"元，确收已收款?";
			layer.confirm(mess, {
    		  	btn: ['确收','取消']
    		}, function(){
				$.ajax({
					url : "/kd/finance/payable/confirmpay",
					type : 'POST',
					data : {loadIds:ids.join()},
					success:function(data){
						if (data.success) {
							layer.msg("确认成功");
							setTimeout(function(){$othis.search();}, 2000);
						} else {
							layer.msg(data.msg);
						}
					}
				});
    		}, function(){});	
		},
		appendHtml:function(obj, index){
			var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.load_id+"' loadstatus='"+obj.load_fee_status+"' data='"+obj.load_fee+"' item='"+obj.load_fee_prepay+"'>";
			html += "<td><input type=\"checkbox\"/></td>";
			html += "<td>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.load_id+"' style='color: #3974f8;cursor: pointer;'>"+obj.load_sn+"</td>";
			html += "<td>"+obj.truck_id_number+"</td>";
			html += "<td>"+obj.snetworkName+"</td>";
			html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd")+"</td>";
			if(obj.load_fee_status==0){
				html += "<td style=\"color: #ff7801;\">未付款</td>";
			}else{
				html += "<td style=\"color: #ff7801;\">已付款</td>";
			}
			html += "<td style=\"color: #ff7801;\">"+obj.load_fee+"</td>";
			html += "<td style=\"color: #ff7801;\">"+obj.load_fee_prepay+"</td>";
			html += "<td>"+obj.fromCity+"</td>";
			html += "<td>"+obj.toCity+"</td>";
			html += "<td>"+obj.truck_driver_name+"</td>";
			html += "<td>"+obj.truck_driver_mobile+"</td>";
			html += "<td>"+obj.load_count+"</td>";
			html += "<td>"+obj.load_volume+"</td>";
			html += "<td>"+obj.load_amount+"</td>";
			html += "<td class=\"banner-right-padding\">"+obj.load_weight+"</td>";
			html += "<td class=\"banner-right-th\"><span class=\"gun\">修改</span><div class=\"banner-right-list2-tab2\">";
			html += "<dl><dd class=\"input2\"><span>预付</span></dd></dl></div></td></tr>";
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
		openView:function(e){
			var $this = this;
			var tr = $(e.target).parents("tr");
			var sta = $(tr).attr("loadstatus");
			if(sta==true){
				layer.msg("该配载单已付款不能修改");
				return ;
			}
			var loadId = $(tr).attr("lid");
			layer.open({
				  type: 2,
				  title: "配载单信息",
				  area: ['1200px', '900px'],
				  content: ['/kd/finance/payable/modify?loadId='+loadId, 'yes'], //iframe的url，no代表不显示滚动条'/kd/loading/loadchange?truck_id=21'
				  end: function(){ 
					  	$this.search();
					  }
				  });
		},
		openPrePayView:function(e){
			var $this = this;
			var tr = $(e.target).parents("tr");
			var sta = $(tr).attr("loadstatus");
			if(sta==true){
				layer.msg("该配载单已付款不能修改");
				return ;
			}
			var loadId = $(tr).attr("lid");
			layer.open({
				  type: 2,
				  title: "修改预付款",
				  area: ['1200px', '900px'],
				  content: ['/kd/finance/payable/prepaymodify?loadId='+loadId, 'yes'], //iframe的url，no代表不显示滚动条'/kd/loading/loadchange?truck_id=21'
				  end: function(){ 
					  	$this.search();
					  }
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
		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/payable/exportPayablelist?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);	
	
	


