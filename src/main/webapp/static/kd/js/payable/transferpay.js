//应付中转
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

            //中转方
            $('#transferName').combogrid({
                url : '/kd/customer/searchCustomer?type=3',
                idField : 'customer_id',
                textField : 'customer_name',
                height : 30,
                panelWidth : 320,
                pagination: true,
                columns: [[
                    {field:'customer_corp_name',title:'公司名',width:200},
                    {field:'customer_name',title:'姓名',width:150},
                    {field:'customer_mobile',title:'电话',width:200}
                ]],
                keyHandler:{
                    up: function() {},
                    down: function() {},
                    enter: function() {},
                    query: function(q) {
                        //动态搜索
                        $('#transferName').combogrid("grid").datagrid("reload", {'queryName': q});
                        $('#transferName').combogrid("setValue", q);
                    }
                },
                fitColumns: true
            });


			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
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
            var transportType = $("#excelExport").attr("transportType");
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/finance/payable/transferSearch',
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
			var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td>"+index+"</td>";
			html += "<td  style=\"color: #3974f8;cursor: pointer;\">"+obj.ship_transfer_sn+"</td>";
            html += "<td>"+obj.transferNetName+"</td>";
            html += "<td>"+obj.transferCorpName+"</td>";
            html += "<td>"+obj.transferName+"</td>";
            html += "<td>"+new Date(obj.ship_transfer_time).format("yyyy-MM-dd")+"</td>";

            if(obj.pay_status==1){
                html += "<td style=\"color: #ff7801;\">已结算</td>";
            }else{
                html += "<td style=\"color: #ff7801;\">未结算</td>";
            }

            html += "<td style=\"color: #ff7801;\">"+obj.ship_transfer_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_sn+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_pickuppay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_agency_fund+"</td>";
            html += "<td>"+obj.shipNetName+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";

            html += "<td>"+obj.ship_volume+"</td>";
            html += "<td>"+obj.ship_weight+"</td>";
            html += "<td>"+obj.ship_amount+"</td>";

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
		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/payable/downLoadTransferPay?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new Payable();
})
})(jQuery);




