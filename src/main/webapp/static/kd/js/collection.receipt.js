//代收回单
(function($){
    function collectionReceipt(){
        this.searchParam = "";
        this.init();
    }
    collectionReceipt.prototype={
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
            //回单回收
            $("#recoveryId").bind("click", $.proxy(function (e) {
                this.confirmRecovery(e);
            }, this));

            //回单寄出
            $("#sendId").bind("click", $.proxy(function (e) {
                this.confirmSend(e);
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



        confirmRecovery:function(e){
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
           if(sta>0){
                layer.msg("只能选择未回收的运单操作");
                return ;
            }
            var mess = "确认已回收"+shipSn[0].shipSn+"等"+ids.length+"单的纸质回单。";
            layer.confirm(mess, {
                title:'回单回收',
                btn: ['确定','取消']
            }, function(){
                $.ajax({
                    url : "/kd/collectionReceipt/recoveryReceipt",
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
        confirmSend:function(e){
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
            if(sta!=1){
                layer.msg("只能选择已回收的运单操作");
                return ;
            }
            var content = "确认已将"+shipSn[0].shipSn+"等"+ids.length+"单的纸质回单已寄出给开单网点？</br>";
             content+='<laber  style="margin-left:30px; padding:10px 0;  background:#CCC">请输入寄给开单网点的邮寄单号，可不填</laber><input type="text" id="preno" style="margin-top: 40px;margin-left:10px;height: 28px;width: 200px;"/>';
            layer.open({
                type: 1,
                title: '回单寄出',
                btn: ['确认', '取消'],
                area: ['600px', '400px'], //宽高
                yes: function(index, layero){
                    $.ajax({
                        type:"post",
                        dataType:"json",
                        url:"/kd/collectionReceipt/sendReceipt",
                        data:{shipIds:ids.join(),loadworkId:loadworkId,preNo:$('#preno').val()},
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
        openLogView:function(){
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
            $("#loadingId").mLoading("show");
            $.ajax({
                type:'GET',
                url:'/kd/collectionReceipt/search',
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
            var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.ship_id+"' data='"+obj.ship_sn+"' receiptStatus='"+obj.ship_receipt_status+"'>";
            html += "<td><input type=\"checkbox\"/> </td>";
            html += "<td>"+index+"<input type='hidden'id='loadworkId' value='"+obj.load_network_id+"'></td>";
            html += "<td class='viewId'  style=\"color: #0A6CFF;\"  data='"+obj.ship_id+"'>"+obj.ship_sn+"</td>";
            html += "<td>"+obj.enetWorkName+"</td>";
            html += "<td>"+obj.netWorkName+"</td>";
            html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd")+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            if(obj.ship_receipt_status==0){
                html += "<td style='color: #ff7801;'>未回收</td>";
            }else if(obj.ship_receipt_status==1){
                html += "<td style='color: #ff7801;'>已回收</td>";
            }else if(obj.ship_receipt_status==2){
                html += "<td style='color: #ff7801;'>已寄出</td>";
            }else if(obj.ship_receipt_status==3){
                html += "<td style='color: #ff7801;'>已接收</td>";
            }else{
                html += "<td style='color: #ff7801;'896>已发放</td>";
            }
            html += "<td class=\"banner-right-padding\">"+obj.receipt_pre_post_no+"</td>";
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
        new collectionReceipt();
    })
})(jQuery);