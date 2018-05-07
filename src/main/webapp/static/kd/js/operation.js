//运作明细
(function($){
    function Operation(){
        this.searchParam = "";
        this.init();
    }
    Operation.prototype={
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
                window.location.href = "/kd/report/exportOperation?" + obj.searchParam;
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
                url:'/kd/report/search',
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
            html += "<td class='viewId'  style=\"color: #0A6CFF;\"  data='"+obj.ship_id+"'>"+obj.ship_sn+"</td>";
            html += "<td>"+obj.netWorkName+"</td>";
            html += "<td>"+((obj.ship_customer_number==null)?"":obj.ship_customer_number)+"</td>";
            html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.ship_volume+"</td>";
            html += "<td>"+obj.ship_weight+"</td>";
            html += "<td>"+obj.ship_amount+"</td>";
            html += "<td>"+obj.ship_agency_fund+"</td>";
            if(obj.ship_pay_way==0){
                html += "<td style='color: #ff7801;'>现付</td>";
            }else if(obj.ship_pay_way==1){
                html += "<td style='color: #ff7801;'>提付</td>";
            }else if(obj.ship_pay_way==2) {
                html += "<td style='color: #ff7801;'>到付</td>";
            }else if(obj.ship_pay_way==3){
                html += "<td style='color: #ff7801;'>回单付</td>";
            }else{
                html += "<td style='color: #ff7801;'896>月付</td>";
            }
            html += "<td style='color: #ff7801;'>"+obj.ship_total_fee+"</td>";
            html += "<td>"+obj.ship_fee+"</td>";
            html += "<td>"+obj.ship_pickup_fee+"</td>";
            html += "<td>"+obj.ship_delivery_fee+"</td>";
            html += "<td>"+obj.ship_insurance_fee+"</td>";
            html += "<td>"+obj.ship_package_fee+"</td>";
            html += "<td>"+obj.ship_addon_fee+"</td>";
            var thFee=obj.thFee;
           var  strs=thFee.split(",");
             	
             		var tmep1='<td></td><td></td><td></td><td></td>';
             		var tmep2='<td></td><td></td><td></td><td></td>';
             		var tmep3='<td></td><td></td><td></td><td></td>';
             		var tmep4='<td></td><td></td><td></td><td></td>';
             	for(var i=0;i<strs.length;i++){
             		console.log('strs.length:'+strs.length);
           			 var rowstrs=strs[i].split("|");
             			if(rowstrs[4]=='types1'){
             				tmep1 = "<td>"+rowstrs[0]+"</td><td>"+rowstrs[1]+"</td><td>"+rowstrs[2]+"</td><td>"+rowstrs[3]+"</td>";
             			}
             			if(rowstrs[4]=='types2'){
             				tmep2 = "<td>"+rowstrs[0]+"</td><td>"+rowstrs[1]+"</td><td>"+rowstrs[2]+"</td><td>"+rowstrs[3]+"</td>";
             			}
             			if(rowstrs[4]=='types3'){
             				tmep3 = "<td>"+rowstrs[0]+"</td><td>"+rowstrs[1]+"</td><td>"+rowstrs[2]+"</td><td>"+rowstrs[3]+"</td>";
             			}
             			if(rowstrs[4]=='types4'){
             				tmep4 = "<td>"+rowstrs[0]+"</td><td>"+rowstrs[1]+"</td><td>"+rowstrs[2]+"</td><td>"+rowstrs[3]+"</td>";
             			}
             		
           			
             			
             		}
             			html +=tmep1+tmep2+tmep3+tmep4;
             	
            html += "<td>"+((obj.corpName==null?"":obj.corpName))+"</td>";
            html += "<td>"+((obj.customerName==null?"":obj.customerName))+"</td>";
            if(obj.ship_transfer_time==null){
                html += "<td></td>";
            }else{
                html += "<td>"+new Date(obj.ship_transfer_time).format("yyyy-MM-dd hh:mm")+"</td>";
            }
            html += "<td>"+obj.ship_transfer_fee+"</td>";
            html += "<td>"+((obj.sign_person==null?"":obj.sign_person))+"</td>";
            if(obj.sign_time==null){
                html += "<td></td>";
            }else{
                html += "<td>"+new Date(obj.sign_time).format("yyyy-MM-dd hh:mm")+"</td>";
            }
            html += "<td >"+obj.profit+"</td>";
            html += "<td >"+obj.rate+"%</td>";
            html += " </tr>";
            console.log(html);
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
        new Operation();
    })
})(jQuery);