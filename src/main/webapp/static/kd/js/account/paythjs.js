/**
 * 提付对账应付结算
 */
init(true);
$(function() {

    //收货方信息
    $('#receivingName').combogrid({
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
                $('#receivingName').combogrid("grid").datagrid("reload", {'queryName': q});
                $('#receivingName').combogrid("setValue", q);
            }
        },
        fitColumns: true
    });

});

function init(flag) {
    $("#loadingId").mLoading("show");
    $("#loadingId2").mLoading("show");
    $('#fifterLeftSn').val("");
    $('#fifterRightSn').val("");

    $("#leftonAll").prop("checked", false);
    $("#rightonAll").prop("checked", false);

    if(flag) getData();

    orderNum();

    leftTotal();
    rightTotal();

    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
    setTimeout(function(){$("#loadingId2").mLoading("hide");}, 200);
}

function search() {
    init(true);
}

function getData (){
    $.ajax({
        url : '/kd/finance/account/getPayPickuppayAccountData',
        data : {
            receivingName : $('#receivingName').val(),
            reNetWorkId:$('#reNetWorkId').val(),
            payNetWorkId:$('#payNetWorkId').val(),
            flag:'all'
        },
        async:false,
        type : 'post',
        dataType : 'json',
        success : function(result) {
            if (result) {
                var row=result;
                var html="";
                for (var i = 0; i < row.length; i++) {
                    if(checkShipId(row[i].ship_id)) {
                        var minusfee = row[i].minus_fee;
                        var plusfee = row[i].plus_fee;
                        if(minusfee == null) minusfee = 0;
                        if(plusfee == null) plusfee = 0;
                        var shipState=row[i].ship_status==1?"已入库":row[i].ship_status==2?"短驳中":row[i].ship_status==3?"短驳到达"
                            :row[i].ship_status==4?"已发车":row[i].ship_status==5?"已到达":row[i].ship_status==6?"收货中转中":row[i].ship_status==7?"到货中转中"
                                :row[i].ship_status==8?"送货中":"已签收";
                        html += '<tr class="tr_css" align="center" ondblclick="doubleClickConfirm($(this));">'
                            + '<td><input type="checkbox"></td>'
                            + '<td>'+(i+1)+'</td>'
                            + '<td  style="color: #3974f8;cursor: pointer;"><a href="#" onclick="openShipDiv(' + row[i].ship_id + ');">' + row[i].ship_sn + '</a></td>'
                            + '<td>' + row[i].netWorkName + '</td>'
                            + '<td>' + row[i].ship_sn + '</td>'
                            + '<td>' + row[i].create_time + '</td>'
                            + '<td>' + row[i].ship_pickuppay_fee + '</td>'
                            + '<td>' + plusfee + '</td>'
                            + '<td>' + minusfee + '</td>'
                            + '<td>' + shipState + '</td>'
                            + '<td>' + row[i].toNetWorkName + '</td>'
                            + '<td>' + row[i].receiverName + '<input type="hidden" value="' + row[i].ship_id + '"></td>'
                            + '</tr>';
                    }
                }
                $("#leftTable tr:gt(0)").remove();
                $('#leftTable').append(html);
            }
        }
    });
}

function checkShipId(shipId){
    var flag=true;
    var tr=$('#rightTable tr:gt(0)');

    for(var i=0;i<tr.length;i++){
        console.log(tr[i].children[9].children[0].value);
            if(shipId==tr[i].children[9].children[0].value) flag=false;
    }
    return flag;
}

//选中确认
function confirmAll() {

    var check = $("#leftTable input[type='checkbox']");
    var flag=true;
    for (var i = 1; i < check.length; i++) {
        if (check[i].checked ) {
            var row = check[i].parentElement.parentElement;
            confirmBycheck(row);
            flag=false;
        }
    }
    if(flag){
        layer.msg('请选择要确认的数据');
        return;
    }
}

//根据选中确认
function confirmBycheck(obj) {
    if(typeof obj[0]!='undefined'){
        $(obj[0]).attr("ondblclick","doubleClickdel($(this))");
    }else{
        $(obj).attr("ondblclick","doubleClickdel($(this))");
    }
    obj.remove();
    $(obj).each(function () {
        $(this).children('td').eq(0)[0].firstChild.checked=false;
    });
    $("#rightTable").append(obj);
    leftTotal();
    rightTotal();
    init();
}

//选中删除
function delAll() {

    var check = $("#rightTable input[type='checkbox']");

    if(check.length==1){
        layer.msg('请先选择需要取消的数据');
        return;
    }

    var flag=true;
    for (var i = 1; i < check.length; i++) {
        if (check[i].checked ) {
            var row = check[i].parentElement.parentElement;
            delconfirmBycheck(row);
            flag=false;
        }
    }

    if(flag){
        layer.msg('请选择要取消的数据');
        return;
    }
}

//根据选中取消中转
function delconfirmBycheck(obj) {
    obj.remove();
    if(typeof obj[0]!='undefined'){
        $(obj[0]).attr("ondblclick","doubleClickConfirm($(this))");
    }else{
        $(obj).attr("ondblclick","doubleClickConfirm($(this))");

    }
    $(obj).each(function () {
        $(this).children('td').eq(0)[0].firstChild.checked=false;
    });
    $("#leftTable").append(obj);
    leftTotal();
    rightTotal();
    init();
}

//左边列表全选事件
function onallleft() {
    if ($("#leftonAll").is(':checked')) {
        $("#leftTable input[type='checkbox']").prop("checked", true);
    } else {
        $("#leftTable input[type='checkbox']").prop("checked", false)
    }
}

//右边全选事件
function onallright() {
    if ($("#rightonAll").is(':checked')) {
        $("#rightTable input[type='checkbox']").prop("checked", true);
    } else {
        $("#rightTable input[type='checkbox']").prop("checked", false)
    }
}

function indexOf(arr, item) {

    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === item)
            return i;
        else
            return -1;
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

//左侧列表根据运单号过滤
function fifterLeftSn(){
    var tr=$('#leftTable tr:gt(0)');
    var shipSn=$('#leftShipSn').val();
    tr.hide();
    $(tr).each(function(){
        var shipSnText=$(this).children('td').eq(2)[0].innerText;
        if(shipSnText.indexOf(shipSn)>-1){
            $(this).show();
        }
    });
}
//左侧列表根据运单号回车事件
function enterFifterLeftSn(event){

    if(event.keyCode ==13){

        var shipSn=$('#leftShipSn').val();
        var tr=$('#leftTable tr:gt(0)');

        $(tr).each(function(){
            if(!$(this).is(':hidden')){

                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                var showTr=new Array();
                $('#leftTable tr:gt(0)').each(function(){
                    if(!$(this).is(':hidden')){
                        showTr.push($(this));
                    }
                });

                if(shipSnText.indexOf(shipSn)>-1&&shipSn.length>0&&showTr.length==1){
                    confirmBycheck($(this));
                    $('#leftShipSn').val("");
                    fifterLeftSn();
                }
            }

        });
    }


}

//右侧列表根据运单号过滤
function fifterRightSn(){
    var tr=$('#rightTable tr:gt(0)');
    var shipSn=$('#rightSn').val();
    tr.hide();
    $(tr).each(function(){
        var shipSnText=$(this).children('td').eq(2)[0].innerText;
        if(shipSnText.indexOf(shipSn)>-1){
            $(this).show();
        }
    });
}

//右侧列表根据运单号回车事件
function enterfifterRightSn(event){

    if(event.keyCode ==13){

        var shipSn=$('#rightSn').val();
        var tr=$('#rightTable tr:gt(0)');

        $(tr).each(function(){
            if(!$(this).is(':hidden')){

                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                var showTr=new Array();
                $('#rightTable tr:gt(0)').each(function(){
                    if(!$(this).is(':hidden')){
                        showTr.push($(this));
                    }
                });

                if(shipSnText.indexOf(shipSn)>-1&&shipSn.length>0&&showTr.length==1){
                    delconfirmBycheck($(this));
                    $('#rightSn').val("");
                    tr.show();
                }
            }

        });
    }


}

//左侧合计
function leftTotal(){
    var fee=0;
    var plusFee = 0;
    var minusFee=0;
    var tr=$('#leftTable tr:gt(0)');
    $(tr).each(function(){
        var feeText=$(this).children('td').eq(6)[0].innerText;
        var plusfeeText=$(this).children('td').eq(7)[0].innerText;
        var minusfeeText=$(this).children('td').eq(8)[0].innerText;
        if(feeText.length>0){
            fee+=parseFloat(feeText);
            plusFee+=parseFloat(plusfeeText);
            minusFee+=parseFloat(minusfeeText);
        }
    });


    $('#leftDan').html(tr.length);
    $('#leftFee').html(fee);
    $('#leftPlus').html(plusFee);
    $('#leftMinus').html(minusFee);
    $('#leftTotal').html(fee+plusFee-minusFee);

}

//右侧合计
function rightTotal(){
    var fee=0;
    var plusFee = 0;
    var minusFee=0;
    var tr=$('#rightTable tr:gt(0)');
    $(tr).each(function(){
        var feeText=$(this).children('td').eq(6)[0].innerText;
        var plusfeeText=$(this).children('td').eq(7)[0].innerText;
        var minusfeeText=$(this).children('td').eq(8)[0].innerText;
        if(feeText.length>0){
            fee+=parseFloat(feeText);
            plusFee+=parseFloat(plusfeeText);
            minusFee+=parseFloat(minusfeeText);
        }
    });
    $('#rightDan').html(tr.length);
    $('#rightFee').html(fee);
    $('#rightPlus').html(plusFee);
    $('#rightMinus').html(minusFee);
    $('#rightTotal').html(fee+plusFee-minusFee);

}
//序列号排序
function orderNum(){
    var leftTr=$('#leftTable tr:gt(0)');
    var rightTr=$('#rightTable tr:gt(0)');
    var i=1;
    var j=1;
    $(leftTr).each(function (){
            $($(this)[0].children[1]).html(i);
            i++;
    });
    $(rightTr).each(function (){
            $($(this)[0].children[1]).html(j);
            j++;
    });

}

function doubleClickConfirm(obj){
    obj[0].children[0].children[0].checked=true;
    confirmAll();
}
function doubleClickdel(obj){
    obj[0].children[0].children[0].checked=true;
    delAll();
}

//确认提交
function confirm() {
    var shipIds=new Array();

    var totalFee=0;

    var tr=$('#rightTable tr:gt(0)');
    $(tr).each(function(){



        shipIds.push($(this)[0].children[11].children[0].value);

        var feeText=$(this)[0].children[6].innerText
        var plusfeeText=$(this)[0].children[7].innerText
        var minusfeeText=$(this)[0].children[8].innerText
        totalFee+=(feeText+plusfeeText-minusfeeText);
    });

    if (tr.length == 0) {
        layer.msg("请选择要确认的数据");
        return;
    }

    if ($("#payType").val()== 0) {
        Anfa.show("请选择支付方式", "#payType");
        return;
    }

    layer.confirm(
        '确定结算金额总计'+totalFee+"元?",
        {
            btn : [ '确认', '取消' ]
        },
        function() {
            $.ajax({
                type:'POST',
                url:'/kd/finance/account/confirmPayPickuppay?shipIds='+shipIds,
                data:$('#confirmform').serialize(),
                success:function(data){
                    if(data.success){
                        layer.msg("结算成功！",{time: 1000},function(){
                            window.location.href='/kd/finance/account/payIndex';
                        });
                    }else{
                        layer.msg("结算失败！");
                    }
                }
            });
        }, function() {
        });


}