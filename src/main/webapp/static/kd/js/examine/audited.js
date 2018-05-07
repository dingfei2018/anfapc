//运单已审核列表
(function($){
    function Audited(){
        this.searchParam = "";
        this.init();
    }
    Audited.prototype={
        init:function(){
            $("#selectAll").bind("click", this.selectAll);
            $("#search").bind("click", $.proxy(function () {
                this.search();
            }, this));

            $("#quxiao").bind("click", $.proxy(function (e) {
                this.quxiao(e);
            }, this));
            $("#loadId").on("click", ".banner-right-th", $.proxy(function (e) {
                this.cancel(e);
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
                url:'/kd/finance/examine/search?status=1',
                data:encodeURI(param),
                success:function(data){
                    var html="";
                    for(var i=0;i<data.list.length;i++){
                        html += obj.appendHtml(data.list[i], i+1+((pageNo-1)*data.pageSize));
                    }
                    $("#loadId > tr").remove();
                    $("#loadId").append(html);
                    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
                }
            });
        },
        appendHtml:function(obj, index){
            var html=" <tr lid="+obj.ship_id+"><td><input type=\"checkbox\"></td>";
            html += "<td >"+index+"</td>";
            html += "<td >"+obj.ship_sn+"</td>";
            html += "<td>"+obj.netWorkName+"</td>";
            html += "<td >"+obj.goods_sn+"</td>";
            html += "<td>"+obj.create_time+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
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
            html += "<td style=\"color: #ff7801;\">已审核</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_total_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_rebate_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.receiptsFee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_nowpay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_pickuppay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_receiptpay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_monthpay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_arrearspay_fee+"</td>";
            html += "<td class=\"banner-right-padding\">"+obj.ship_goodspay_fee+"</td>";
            html += " <td class=\"banner-right-th\" ><span>取消审核</span></td>";
            html+=" </tr>";
           // console.log(html);
            return html;
        },

        quxiao:function (e) {
            $this = e.target;
            $othis = this;
            var ids = new Array();
            $("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
                var tr = $(o).parents("tr");
                ids.push($(tr).attr("lid"));
            })
            alert(ids);
            if(ids.length==0){
                layer.msg("请选择要操作的运单");
                return;
            }
            layer.confirm("确认取消已选运单审核吗？", {
                title: '取消审核',
                btn: ['确定取消','取消']
            }, function(){
                $.ajax({
                    type:"post",
                    dataType:"json",
                    url:"/kd/finance/examine/cancelExamine",
                    data:{shipIds:ids.join()},
                    success:function(data){
                        if (data.success) {
                            layer.msg("取消成功");
                            setTimeout(function(){$othis.search();}, 1000);
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }, function(){});
        },

        cancel:function (e) {
            var tr = $(e.target).parents("tr");
            var shipId = $(tr).attr("lid");
            layer.confirm("确认取消已选运单审核吗？", {
                title: '取消审核',
                btn: ['确定取消','取消']
            }, function(){
                $.ajax({
                    type:"post",
                    dataType:"json",
                    url:"/kd/finance/examine/cancelExamine",
                    data:{shipIds:shipId},
                    success:function(data){
                        if (data.success) {
                            layer.msg("取消成功");
                            setTimeout(function(){window.location.reload(true);}, 1000);
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }, function(){});


        },
        excelExport:function(){
            var obj = this;
            layer.confirm('您确定要导出excel？', {
                btn: ['导出','取消']
            }, function(index){
                window.location.href="/kd/finance/examine/exportAuShip?"+obj.searchParam;
                layer.close(index);
            }, function(){});
        }
    };

    $(function(){
        new Audited();
    })
})(jQuery);




