//代收货款
(function($){
    function Collection(){
        this.searchParam = "";
        this.init();
    }
    Collection.prototype={
        init:function(){
           this.search();
            $("#selectAll").bind("click", this.selectAll);
            $("#search").bind("click", $.proxy(function () {
                this.search();
            }, this));

            $(".tab_css_1").on("click", ".viewId",this.openDetail);
            this.search();
          //托运方
            $('#senderId').combogrid({
    			url : '/kd/customer/searchCustomer?type=2',
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
    	               $('#senderId').combogrid("grid").datagrid("reload", {'queryName': q});
    	               $('#senderId').combogrid("setValue", q);
    	            }
    	        },
    			fitColumns: true
    		});
    	    
    		//收货方信息
    		$('#receiveId').combogrid({ 
    			url : '/kd/customer/searchCustomer?type=1',
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
    	               $('#receiveId').combogrid("grid").datagrid("reload", {'queryName': q});
    	               $('#receiveId').combogrid("setValue", q);
    	            }
    	        },
    			fitColumns: true
    		});
            //excel导出
            $("#excelExport").bind("click", $.proxy(function () {
                this.excelExport();
            }, this));

        },

        openDetail:function(){
            var shipId = $(this).attr("data");
            layer.open({
                type: 2,
                title: "运单详情",
                area: ['850px', '700px'],
                content: ['/kd/waybill/viewDetail?shipId='+shipId, 'yes']})
        },

        excelExport:function() {
            var obj = this;
            layer.confirm('您确定要导出excel？', {
                btn: ['导出', '取消']
            }, function (index) {
                window.location.href = "/kd/finance/collect/downLoad?" + obj.searchParam;
                layer.close(index);
            }, function () {
            });
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
                url:'/kd/finance/collect/collectList',
                data:encodeURI(param),
                success:function(data){
                    var html="";
                    for(var i=0;i<data.list.length;i++){
                        html += obj.appendHtml(data.list[i], i+1+((pageNo-1)*data.pageSize))
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
            html += "<td class='viewId'  style=\"color: #0A6CFF;\"  data='"+obj.ship_id+"'>"+obj.ship_sn+"</td>";
            html += "<td>"+obj.endWorkName+"</td>";
            html += "<td>"+obj.goods_sn+"</td>";
            if(obj.ship_agency_fund_status==1){
                html += "<td style='color: #ff7801;'>未回收</td>";
            }else if(obj.ship_agency_fund_status==2){
                html += "<td style='color: #ff7801;'>已回收</td>";
            }else if(obj.ship_agency_fund_status==3) {
                html += "<td style='color: #ff7801;'>已汇款</td>";
            }else if(obj.ship_agency_fund_status==4){
                html += "<td style='color: #ff7801;'>已到账</td>";
            }else{
                html += "<td style='color: #ff7801;'896>已发放</td>";
            }
            html += "<td>"+obj.ship_agency_fund+"</td>";
            html+="<td>"+obj.netWrokName+"</td>"
            if(obj.ship_status==1){
                html += "<td style=\"color: #ff7801;\">已入库</td>";
            }else if(obj.ship_status==2){
                html += "<td style=\"color: #ff7801;\">短驳中</td>";
            }else if (obj.ship_status==3){
                html += "<td style=\"color: #ff7801;\">短驳到达</td>";
            }else if(obj.ship_status==4){
                html += "<td style=\"color: #ff7801;\">已发车</td>";
            }else if(obj.ship_status==5){
                html += "<td style=\"color: #ff7801;\">已到达</td>";
            }else if(obj.ship_status==6){
                html += "<td style=\"color: #ff7801;\">收货中转中</td>";
            }else if(obj.ship_status==7){
                html += "<td style=\"color: #ff7801;\">到货中转中</td>";
            }else if(obj.ship_status==8){
                html += "<td style=\"color: #ff7801;\">送货中</td>";
            }else {
                html += "<td style=\"color: #ff7801;\">已签收</td>";
            }
            html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.productName+"</td>";
            html += "<td>"+obj.ship_volume+"</td>";
            html += "<td>"+obj.ship_weight+"</td>";
            html += "<td >"+obj.ship_amount+"</td>";
            html += " </tr>";
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
    };

    $(function(){
        new Collection();
    })
})(jQuery);