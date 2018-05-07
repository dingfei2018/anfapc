//运单未审核列表
(function($){
    function Unaudited(){
        this.searchParam = "";
        this.init();
    }
    Unaudited.prototype={
        init:function(){
            $("#search").bind("click", $.proxy(function () {
                this.search();
            }, this));

            $("#tongguo").bind("click", $.proxy(function (e) {
                this.tongguo(e);
            }, this));

           $("#loadId").on("click",".chk", $.proxy(function (e) {
                this.check(e);
            }, this));

            $("#excelExport").bind("click", $.proxy(function () {
                this.excelExport();
            }, this));
            this.search();
        },
        check:function(e){
            var checkNowpay=0;
            var checkPickuppay=0;
            var checkReceiptPay=0;
            var checkMonthPay=0;
            var checkArrearsPay=0;
            var checkGoodsPay=0;
            var checkTotal=0;
            var checkShipRebate=0;
            var checkRebate=0;
            var checkReceipts=0;
            var count=0;

            $("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
                var tr = $(o).parents("tr");
                console.log(tr[0].children[7].innerHTML);
                count++;
                checkNowpay+=parseFloat(isNaN(parseFloat(tr[0].children[7].innerHTML))?0:tr[0].children[7].innerHTML);
                checkPickuppay+=parseFloat(isNaN(parseFloat(tr[0].children[8].innerHTML))?0:tr[0].children[8].innerHTML);
                checkReceiptPay+=parseFloat(isNaN(parseFloat(tr[0].children[9].innerHTML)?0:tr[0].children[9].innerHTML));
                checkMonthPay+=parseFloat(isNaN(parseFloat(tr[0].children[10].innerHTML))?0:tr[0].children[10].innerHTML);
                checkArrearsPay+=parseFloat(isNaN(parseFloat(tr[0].children[11].innerHTML))?0:tr[0].children[11].innerHTML);
                checkGoodsPay+=parseFloat(isNaN(parseFloat(tr[0].children[12].innerHTML))?0:tr[0].children[12].innerHTML);
                checkTotal+=parseFloat(isNaN(parseFloat(tr[0].children[13].innerHTML))?0:tr[0].children[13].innerHTML);
                checkShipRebate+=parseFloat(isNaN(parseFloat(tr[0].children[14].innerHTML))?0:tr[0].children[14].innerHTML);
                checkRebate+=parseFloat(isNaN(parseFloat(tr[0].children[15].innerHTML))?0:tr[0].children[15].innerHTML);
                checkReceipts+=parseFloat(isNaN(parseFloat(tr[0].children[16].innerHTML))?0:tr[0].children[16].innerHTML);

            })
            $('#checkDan').html(count);
            $('#checkNowpay').html(checkNowpay);
            $('#checkPickuppay').html(checkPickuppay);
            $('#checkReceiptPay').html(checkReceiptPay);
            $('#checkMonthPay').html(checkMonthPay);
            $('#checkArrearsPay').html(checkArrearsPay);
            $('#checkGoodsPay').html(checkGoodsPay);
            $('#checkTotal').html(checkTotal);
            $('#checkShipRebate').html(checkShipRebate);
            $('#checkRebate').html(checkRebate);
            $('#checkReceipts').html(checkReceipts);

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
                url:'/kd/finance/examine/search?status=0',
                data:encodeURI(param),
                success:function(data){
                    console.log(data);
                    var html="";
                    var shipNowpayFeeTotal=0;
                    var shipPickupPayFeeTotal=0;
                    var shipReceiptPayFeeTotal=0;
                    var shipMonthPayFeeTotal=0;
                    var shipArrearsPayFeeTotal=0;
                    var shipGoodsPayFeeTotal=0;
                    var shipTotalFeeTotal=0;
                    var shipRebateFee=0;
                    var rebateFee=0;
                    var receiptsFeeTotal=0;
                    for(var i=0;i<data.list.length;i++){
                        html += obj.appendHtml(data.list[i], i+1+((pageNo-1)*data.pageSize));
                        shipNowpayFeeTotal += parseFloat(data.list[i].ship_nowpay_fee);
                        shipPickupPayFeeTotal += parseFloat(data.list[i].ship_pickuppay_fee);
                        shipReceiptPayFeeTotal += parseFloat(data.list[i].ship_receiptpay_fee);
                        shipMonthPayFeeTotal += parseFloat(data.list[i].ship_monthpay_fee);
                        shipArrearsPayFeeTotal += parseFloat(data.list[i].ship_arrearspay_fee);
                        shipGoodsPayFeeTotal += parseFloat(data.list[i].ship_goodspay_fee);
                        shipTotalFeeTotal += parseFloat(data.list[i].ship_total_fee);
                        shipRebateFee += parseFloat(data.list[i].ship_rebate_fee);
                        rebateFee += parseFloat(data.list[i].rebateFee);
                        receiptsFeeTotal += parseFloat(data.list[i].receiptsFee);
                    }
                    $('#dan').html(data.list.length);
                    $('#nowpayTotal').html(shipNowpayFeeTotal);
                    $('#pickuppayTotal').html(shipPickupPayFeeTotal);
                    $('#receiptPayTotal').html(shipReceiptPayFeeTotal);
                    $('#monthPayTotal').html(shipMonthPayFeeTotal);
                    $('#arrearsPayTotal').html(shipArrearsPayFeeTotal);
                    $('#goodsPayTotal').html(shipGoodsPayFeeTotal);
                    $('#totalTotal').html(shipTotalFeeTotal);
                    $('#shipRebateTotal').html(shipRebateFee);
                    $('#rebateTotal').html(rebateFee);
                    $('#receiptsTotal').html(receiptsFeeTotal);
                    $("#loadId > tr").remove();
                    $("#loadId").append(html);
                    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
                }
            });
        },
        appendHtml:function(obj, index){
            var html=" <tr lid="+obj.ship_id+"><td><input  class='chk' type=\"checkbox\"></td>";
            html += "<td >"+index+"</td>";
            html += "<td >"+obj.ship_sn+"</td>";
            html += "<td>"+obj.netWorkName+"</td>";
            html += "<td>"+obj.create_time+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_nowpay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_pickuppay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_receiptpay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_monthpay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_arrearspay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_goodspay_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_total_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.ship_rebate_fee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.rebateFee+"</td>";
            html += "<td style=\"color: #ff7801;\">"+obj.receiptsFee+"</td>";
            html += "<td >"+obj.goods_sn+"</td>";
            html += "<td>"+obj.productName+"</td>";
            html += "<td>"+obj.shipName+"</td>";
            html+=" </tr>";
            return html;

        },

        tongguo:function (e) {
            $this = e.target;
            $othis = this;
            var ids = new Array();
            $("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
                var tr = $(o).parents("tr");
                ids.push($(tr).attr("lid"));
            })
            if(ids.length==0){
                layer.msg("请选择要操作的运单");
                return;
            }
            layer.confirm("确认通过已选运单审核吗？", {
                title: '通过审核',
                btn: ['确定通过','取消']
            }, function(){
                $.ajax({
                    type:"post",
                    dataType:"json",
                    url:"/kd/finance/examine/successExamine",
                    data:{shipIds:ids.join()},
                    success:function(data){
                        if (data.success) {
                            layer.msg("审核成功");
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
                window.location.href="/kd/finance/examine/exportUnShip?"+obj.searchParam;
                layer.close(index);
            }, function(){});
        }
    };

    $(function(){
        new Unaudited();
    })
})(jQuery);




