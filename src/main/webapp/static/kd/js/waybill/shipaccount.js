/**
 * 交账汇总
 */
init();

function init() {
    $("#loadingId").mLoading("show");
    getData ();
    checkTotal();
    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
}

function search() {
    init();
}

function getData (){

    var flag=$("#flag").is(":checked")?'all':'';
    $.ajax({
        url : '/kd/waybill/getShipAccountJson',
        data : {
            netWorkId:$('#netWorkId').val(),
            flag:flag,
            startTime:$('#startTime').val()
        },
        async:false,
        type : 'post',
        dataType : 'json',
        success : function(result) {
            console.log(result);
            if (result) {
                var row=result;
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

                for (var i = 0; i < row.length; i++) {
                    var ship_nowpay_fee=row[i].ship_nowpay_fee==0?"":row[i].ship_nowpay_fee;
                    var ship_pickuppay_fee=row[i].ship_pickuppay_fee==0?"":row[i].ship_pickuppay_fee;
                    var ship_receiptpay_fee=row[i].ship_receiptpay_fee==0?"":row[i].ship_receiptpay_fee;
                    var ship_monthpay_fee=row[i].ship_monthpay_fee==0?"":row[i].ship_monthpay_fee;
                    var ship_arrearspay_fee=row[i].ship_arrearspay_fee==0?"":row[i].ship_arrearspay_fee;
                    var ship_goodspay_fee=row[i].ship_goodspay_fee==0?"":row[i].ship_goodspay_fee;

                    shipNowpayFeeTotal+=parseFloat(fee=typeof(ship_nowpay_fee)=='number'?ship_nowpay_fee:0);
                    shipPickupPayFeeTotal+=parseFloat(fee=typeof(ship_pickuppay_fee)=='number'?ship_pickuppay_fee:0);
                    shipReceiptPayFeeTotal+=parseFloat(fee=typeof(ship_receiptpay_fee)=='number'?ship_receiptpay_fee:0);
                    shipMonthPayFeeTotal+=parseFloat(fee=typeof(ship_monthpay_fee)=='number'?ship_monthpay_fee:0);
                    shipArrearsPayFeeTotal+=parseFloat(fee=typeof(ship_arrearspay_fee)=='number'?ship_arrearspay_fee:0);
                    shipGoodsPayFeeTotal+=parseFloat(fee=typeof(ship_goodspay_fee)=='number'?ship_goodspay_fee:0);


                    shipTotalFeeTotal+=parseFloat(fee=typeof(row[i].ship_total_fee)=='number'?row[i].ship_total_fee:0);
                    shipRebateFee+=parseFloat(fee=typeof(row[i].ship_rebate_fee)=='number'?row[i].ship_rebate_fee:0);
                    rebateFee+=parseFloat(fee=typeof(row[i].rebateFee)=='number'?row[i].rebateFee:0);
                    receiptsFeeTotal+=parseFloat(fee=typeof(row[i].receiptsFee)=='number'?row[i].receiptsFee:0);

                    html += '<tr class="tr_css" align="center" ondblclick="doubleClickConfirm($(this));">'
                        + '<td><input type="checkbox" onclick="checkTotal();"></td>'
                        + '<td>'+(i+1)+'</td>'
                        + '<td  style="color: #3974f8;cursor: pointer;"><a href="#" onclick="openShipDiv(' + row[i].ship_id + ');">' + row[i].ship_sn + '</a></td>'
                        + '<td>' + row[i].netWorkName + '</td>'
                        + '<td>' + row[i].create_time + '</td>'
                        + '<td>' + row[i].senderName + '</td>'
                        + '<td>' + row[i].toAdd+ '<input type="hidden" value="' + row[i].ship_id + '"></td>'
                        + '<td>' + ship_nowpay_fee + '</td>'
                        + '<td>' + ship_pickuppay_fee + '</td>'
                        + '<td>' + ship_receiptpay_fee + '</td>'
                        + '<td>' + ship_monthpay_fee + '</td>'
                        + '<td>' + ship_arrearspay_fee + '</td>'
                        + '<td>' + ship_goodspay_fee + '</td>'
                        + '<td>' + row[i].ship_total_fee + '</td>'
                        + '<td>' + row[i].ship_rebate_fee + '</td>'
                        + '<td>' + row[i].rebateFee + '</td>'
                        + '<td>' + row[i].receiptsFee + '</td>'
                        + '<td>' + row[i].goods_sn + '</td>'
                        + '<td>' + row[i].productName + '</td>'
                        + '<td>' + row[i].shipName + '</td>'
                        + '</tr>';
                }
                $('#dan').html(row.length);
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
                $("#table tr:gt(0)").remove();
                $('#table').append(html);
            }
        }
    });
}


//全选事件
function onall() {
    if ($("#onall").is(':checked')) {
        $("#table input[type='checkbox']").prop("checked", true);
    } else {
        $("#table input[type='checkbox']").prop("checked", false)
    }
}


function openShipDiv(shipid) {
    //页面层
    layer.open({
        type : 2,
        area : [ '850px', '700px' ], //宽高
        content : [ '/kd/waybill/viewDetail?shipId=' + shipid, 'yes' ]
    });
}

//选中合计
function checkTotal(){
    var tr=$('#table tr:gt(0)');
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
    $(tr).each(function(){
        if($(this).children('td').eq(0)[0].children[0].checked==true){
            count++;
            console.log(parseFloat($(this).children('td').eq(8)[0].innerText));
            checkNowpay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(7)[0].innerText))?0:$(this).children('td').eq(7)[0].innerText);
            checkPickuppay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(8)[0].innerText))?0:$(this).children('td').eq(8)[0].innerText);
            checkReceiptPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(9)[0].innerText))?0:$(this).children('td').eq(9)[0].innerText);
            checkMonthPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(10)[0].innerText))?0:$(this).children('td').eq(10)[0].innerText);
            checkArrearsPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(11)[0].innerText))?0:$(this).children('td').eq(11)[0].innerText);
            checkGoodsPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(12)[0].innerText))?0:$(this).children('td').eq(12)[0].innerText);
            checkTotal+=parseFloat(isNaN(parseFloat($(this).children('td').eq(13)[0].innerText))?0:$(this).children('td').eq(13)[0].innerText);
            checkShipRebate+=parseFloat(isNaN(parseFloat($(this).children('td').eq(14)[0].innerText))?0:$(this).children('td').eq(14)[0].innerText);
            checkRebate+=parseFloat(isNaN(parseFloat($(this).children('td').eq(15)[0].innerText))?0:$(this).children('td').eq(15)[0].innerText);
            checkReceipts+=parseFloat(isNaN(parseFloat($(this).children('td').eq(16)[0].innerText))?0:$(this).children('td').eq(16)[0].innerText);
        }else{
            $("#onall").prop("checked", false)
        }
    });
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

}

