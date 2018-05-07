//我的回单
(function($){
    function Receipt(){
        this.searchParam = "";
        this.init();
    }
    Receipt.prototype={
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
            //回单接收
            $("#overId").bind("click", $.proxy(function (e) {
                this.confirmOver(e);
            }, this));

            //回单发放
            $("#grantId").bind("click", $.proxy(function (e) {
                this.confirmGrant(e);
            }, this));

            //excel导出
            $("#excelExport").bind("click", $.proxy(function () {
                this.excelExport();
            }, this));

        },
        excelExport:function() {
            var obj = this;
            layer.confirm('您确定要导出excel？', {
                btn: ['导出', '取消']
            }, function (index) {
                window.location.href = "/kd/collectionReceipt/downLoad?" + obj.searchParam;
                layer.close(index);
            }, function () {
            });
        },
        confirmOver:function(e){
        	var flag=false;
            $this = e.target;
             $othis = this;
             var state;
             var loadworkId=$("#loadworkId").val();
             var ids = new Array();
             var shipSn=new Array();
             $("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
                 var tr = $(o).parents("tr");
                 var data={shipSn:$(tr).attr("data")};
                 ids.push($(tr).attr("lid"));
                 shipSn.push(data);
                 state=$(tr).attr("receiptStatus");
             })
             if(state==0||state==2){
                 flag=true;
             }else{
                 flag=false;
             }
             if(ids.length==0){
                 layer.msg("请选择要操作的运单");
                 return;
             }
             if(!flag){
                 layer.msg("只能选择未回收或者已寄出的运单操作");
                 return ;
             }
             var mess = "<p style='color: #333333;font-weight: bold;'>确认已收取"+"<span style='color:blue'>"+shipSn[0].shipSn+"</span>"+"等"+"<span style='color:blue'>"+ids.length+"</span>"+"单纸质回单?</p><p style='font-weight: bold;color: #333333;'>如还未上传签收图片，请确认接收后拍照上传，以便系统存底。</p>";
             layer.confirm(mess, {
                 title:'回单接收',
                 btn: ['确定','取消']
             }, function(){
                 $.ajax({
                     url : "/kd/receipt/receiveReceipt",
                     type : 'POST',
                     data : {shipIds:ids.join(),loadworkId:loadworkId},
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

        confirmGrant:function(e){
            $this = e.target;
            $othis = this;
            var loadworkId=$("#loadworkId").val();
            var ids = new Array();
            var shipSn=new Array();
            var sta;
            $("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
                var tr = $(o).parents("tr");
                sta = $(tr).attr("receiptStatus");
                var data={shipSn:$(tr).attr("data")};
                ids.push($(tr).attr("lid"));
                shipSn.push(data);
            })
            if(ids.length==0){
                layer.msg("请选择要操作的运单");
                return;
            }
            if(sta!=3){
                layer.msg("只能选择已接收的运单操作");
                return ;
            }
            var content = "<p style='color: #333333;font-weight: bold;width:400px;height:30px;margin: auto;margin-top: 30px;text-align: center;'>确认已收取"+"<span style='color:blue'>"+ shipSn[0].shipSn +"</span>"+ "等" +"<span style='color:blue'>"+ ids.length +"</span>"+"单纸质回单？</p><p style='text-align: center;color: #333333;font-weight: bold;'>如没上传签收图片，请确认发放后拍照上传，以便系统存底。</p>";
             content+='<laber  style="margin-left:30px; padding:10px 0;  background:#3974f8;color:#fff;border-radius:3px;">邮寄单号</laber><input type="text" id="sendno" placeholder="可不填" style="margin-top: 40px;margin-left:10px;height: 35px;width: 254px;text-indent: 5px;"/>';
            layer.open({
                type: 1,
                title: '回单发放',
                btn: ['确认', '取消'],
                area: ['400px', '300px'], //宽高
                yes: function(index, layero){
                    $.ajax({
                        type:"post",
                        dataType:"json",
                        url:"/kd/receipt/grantReceipt",
                        data:{shipIds:ids.join(),loadworkId:loadworkId,sendNo:$('#sendno').val()},
                        success:function(data){
                            if(data.success){
                                layer.msg("确认成功")
                                parent.layer.closeAll();
                                setTimeout(function(){$othis.search();}, 2000);
                            }
                        }
                    });
                },
                content: content
            });
        },


    selectAll:function(){
            if($(this).is(':checked')){
                $(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', true)
            }else{
                $(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', false)
            }
        },
        openDetail:function(){
            var shipId = $(this).attr("data");
            layer.open({
                type: 2,
                title: "运单详情",
                area: ['850px', '700px'],
                content: ['/kd/waybill/viewDetail?shipId='+shipId, 'yes']})
        },

        search:function(){
            var param = $("#searchFrom").serialize();
            param+="&pageNo=1";
            if(param)this.searchParam = param;
            this.getData(param, 1, this);
        },
        getData:function(param, pageNo, obj){
            if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
             console.log(param);
            $("#loadingId").mLoading("show");
            $.ajax({
                type:'GET',
                url:'/kd/receipt/search',
                data:encodeURI(param),
                success:function(data){
                	console.log(data);
                	//alert(data.list.length);
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
            var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.ship_id+"' data='"+obj.ship_sn+"' receiptStatus='"+obj.ship_receipt_status+"'>";
            html += "<td><input type=\"checkbox\"/> </td>";
            html += "<td>"+index+"<input type='hidden'id='loadworkId' value='"+obj.load_network_id+"'></td>";
            html += "<td class='viewId'  style=\"color: #0A6CFF;\"  data='"+obj.ship_id+"'>"+obj.ship_sn+"</td>";
            html += "<td>"+obj.netWorkName+"</td>";
            html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            if(obj.ship_receipt_status==0){
                html += "<td style='color: #ff7801;'>未回收</td>";
            }else if(obj.ship_receipt_status==1){
                html += "<td  style='color: #ff7801;'>已回收</td>";
            }else if(obj.ship_receipt_status==2){
                html += "<td  style='color: #ff7801;'>已寄出</td>";
            }else if(obj.ship_receipt_status==3){
                html += "<td  style='color: #ff7801;'>已接收</td>";
            }else{
                html += "<td  style='color: #ff7801;'>已发放</td>";
            }
            html += "<td>"+obj.enetWorkName+"</td>";
            html += "<td>"+obj.receipt_pre_post_no+"</td>";
            html += "<td  class=\"banner-right-padding\">"+obj.receipt_send_post_no+"</td>";
            html += "<td class=\"banner-right-th\">";
            html +=  "<dl><dd class='d1' onclick=\"openSignView("+obj.ship_id+",'"+obj.sign_image_gid+"')\" >签收图片</dd></dl>";
            html += " <div class=\"banner-right-list2-tab2\">";
            html += " <dl><dd class='d2' onclick='openLog("+obj.ship_id+")'>回单日志</dd></dl></div> </td></tr>";
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
        new Receipt();
    })
})(jQuery);