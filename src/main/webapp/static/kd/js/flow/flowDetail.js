//收支明细
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

			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));
			

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
				url:'/kd/finance/flow/detailSearch',
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
			var html = "<tr class=\"tr_css\" align=\"center\" >";
			html += "<td>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.id+"' style='color: #3974f8;cursor: pointer;'>"+obj.flow_sn+"</td>";
            html += "<td>"+((obj.networdName==null)?"" : obj.networdName)+"</td>";

            if(obj.settlement_type==0){
                html += "<td>应付结算</td>";}
			else if(obj.settlement_type==1){
                html += "<td>应收结算</td>";
            }else if (obj.settlement_type==2){
                html += "<td>贷款结算</td>";
			}else if (obj.settlement_type==3){
                html += "<td>网点对账</td>";
            }else{
                html += "<td></td>";
            }
            html += "<td>"+((obj.fee_type==null)?"" : obj.fee_type)+"</td>";
            if(obj.inout_type==1){
                html += "<td style=\"color: #ff7801;\">支出</td>";
                html += "<td style=\"color: #ff7801;\">-"+obj.fee+"</td>";

            }else {
                html += "<td style=\"color: #ff7801;\">收入</td>";
                html += "<td style=\"color: #ff7801;\">"+obj.fee+"</td>";

			}
			if(obj.pay_type==1){
                html += "<td>现金</td>";
            }else if(obj.pay_type==2){
                html += "<td>油卡</td>";
            }else if(obj.pay_type==3){
                html += "<td>支票</td>";
            }else if(obj.pay_type==4){
                html += "<td>银行卡</td>";
            }else if(obj.pay_type==5){
                html += "<td>微信</td>";
            }else if(obj.pay_type==6){
                html += "<td>支付宝</td>";
            }else{
                html += "<td></td>";
            }
            html += "<td>"+((obj.flow_no==null)?"" : obj.flow_no)+"</td>";
            html += "<td>"+((obj.voucher_no==null)?"" : obj.voucher_no)+"</td>";
            html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd  hh:mm")+"</td>";
            html += "<td>"+obj.userName+"</td>";
			if(obj.id_type ==1 ){
                html += "<td>"+obj.ship_sn+"</td>";
                html += "<td>"+obj.shipNetWordName+"</td>";
                html += "<td></td>";
                html += "<td></td>";
			}else if(obj.id_type ==2){
                html += "<td></td>";
                html += "<td></td>";
                html += "<td>"+obj.load_sn+"</td>";
                html += "<td>"+obj.loadNetWordName+"</td>";
			}

			html += "<dl><dd class=\"input2\"></dd></dl></div></td></tr>";
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
    			window.location.href="/kd/finance/flow/downFlowDetail?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);	
	
	


