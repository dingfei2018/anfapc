init(true);
/*$(function() {

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

});*/

function init(flag) {
    $("#loadingId").mLoading("show");
    $("#loadingId2").mLoading("show");
    $('#fifterLeftSn').val("");
    $('#fifterRightSn').val("");

    $("#leftonAll").prop("checked", false);
    $("#rightonAll").prop("checked", false);
    updateabel();
    if(flag) getData();

    orderNum();

    leftTotal();
    rightTotal();

    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
    setTimeout(function(){$("#loadingId2").mLoading("hide");}, 200);
}



function updateabel() {
    var transportType =$('#transportType').val();
        document.getElementById("transportType"+transportType).href="#";
        document.getElementById("transportType"+transportType).className ="activet at";

}
function search() {
    init(true);
}

function getData (){
    $.ajax({
        url : '/kd/finance/payable/trunkLoadListSearch',
        data : {
            truckNumber : $('#truckNumber').val(),
            driverName:$('#driverName').val(),
            transportType:$('#transportType').val(),
            networkId:0,
            networkId:0,
            flag:'all'

        },
        async:false,
        type : 'post',
        dataType : 'json',
        success : function(result) {
            if (result) {
                var row=result.list;
                var html="";
                for (var i = 0; i < row.length; i++) {
                    if(checkShipId(row[i].load_id)) {
                        html += '<tr class="tr_css" align="center" ondblclick="doubleClickConfirm($(this));">'
                            + '<td><input type="checkbox"></td>'
                            + '<td>'+(i+1)+'</td>'
                            + '<td  style="color: #3974f8;cursor: pointer;"><a href="#" onclick="openLoadDiv(' + row[i].load_id + ');">' + row[i].load_sn + '</a></td>'
                            + '<td>' + row[i].snetworkName + '</td>'
                            + '<td>' + row[i].load_depart_time  + '</td>'
                            + '<td>' + row[i].load_fee + '</td>'
                            + '<td>' + row[i].truck_id_number + '</td>'
                            + '<td>' + row[i].truck_driver_name + '</td>'
                            // + '<td>' + row[i].fromCity + '</td>'

                           + '<td>' + row[i].fromCity + '<input type="hidden" value="' + row[i].load_id + '"></td>'
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
        console.log(tr[i].children[8].children[0].value);
            if(shipId==tr[i].children[8].children[0].value) flag=false;
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

function openLoadDiv(loadId) {
    //页面层
    layer.open({
        type : 2,
        title: "配载单详情",
        area: ['1200px', '700px'],
        content: ['/kd/loading/loadingView?loadId='+loadId, 'yes']
    });
}


//左侧列表根据配载单号过滤
function fifterLeftSn(){
    var tr=$('#leftTable tr:gt(0)');
    var shipSn=$('#leftSn').val();
    var truckNumber=$('#leftTruckNumber').val();
    tr.hide();
    $(tr).each(function(){
        var shipSnText=$(this).children('td').eq(2)[0].innerText;
        var TruckNumberText=$(this).children('td').eq(6)[0].innerText;
        if((shipSnText.indexOf(shipSn)>-1)&(TruckNumberText.indexOf(truckNumber)>-1)){
            $(this).show();
        }
    });
}
//左侧列表根据配载单号回车事件
function enterFifterLeftSn(event){

    if(event.keyCode ==13){

        var shipSn=$('#leftSn').val();
        var truckNumber=$('#leftTruckNumber').val();
        var tr=$('#leftTable tr:gt(0)');

        $(tr).each(function(){
            if(!$(this).is(':hidden')){

                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                var TruckNumberText=$(this).children('td').eq(6)[0].innerText;
                var showTr=new Array();
                $('#leftTable tr:gt(0)').each(function(){
                    if(!$(this).is(':hidden')){
                        showTr.push($(this));
                    }
                });
                if((shipSnText.indexOf(shipSn)>=0&&shipSn.length>0)|(TruckNumberText.indexOf(truckNumber)>=0&&truckNumber.length>0)&&showTr.length==1){
                    confirmBycheck($(this));
                    $('#leftSn').val("");
                    $('#leftTruckNumber').val("");
                    fifterLeftSn();
                }
            }

        });
    }


}

//右侧列表根据配载单号过滤
function fifterRightSn(){
    var tr=$('#rightTable tr:gt(0)');
    var shipSn=$('#rightSn').val();
    var truckNumber=$('#rightTruckNumber').val();
    tr.hide();
    $(tr).each(function(){
        var shipSnText=$(this).children('td').eq(2)[0].innerText;
        var TruckNumberText=$(this).children('td').eq(6)[0].innerText;
        if((shipSnText.indexOf(shipSn)>-1)&(TruckNumberText.indexOf(truckNumber)>-1)){
            $(this).show();
        }
    });
}

//右侧列表根据配载单号回车事件
function enterfifterRightSn(event){

    if(event.keyCode ==13){

        var shipSn=$('#rightSn').val();
        var tr=$('#rightTable tr:gt(0)');
        var truckNumber=$('#rightTruckNumber').val();
        $(tr).each(function(){
            if(!$(this).is(':hidden')){

                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                var TruckNumberText=$(this).children('td').eq(6)[0].innerText;
                var showTr=new Array();
                $('#rightTable tr:gt(0)').each(function(){
                    if(!$(this).is(':hidden')){
                        showTr.push($(this));
                    }
                });

                if((shipSnText.indexOf(shipSn)>=0&&shipSn.length>0)|(TruckNumberText.indexOf(truckNumber)>=0&&truckNumber.length>0)&&showTr.length==1){
                    delconfirmBycheck($(this));
                    $('#rightSn').val("");
                    $('#rightTruckNumber').val("");
                    tr.show();
                }
            }

        });
    }


}



//左侧合计
function leftTotal(){
    var fee=0;
    var tr=$('#leftTable tr:gt(0)');
    $(tr).each(function(){
        var feeText=$(this).children('td').eq(5)[0].innerText;
        if(feeText.length>0){
            fee+=parseFloat(feeText);
        }
    });
    $('#leftDan').html(tr.length);
    $('#leftFee').html(fee);

}

//右侧合计
function rightTotal(){
    var fee=0;
    var tr=$('#rightTable tr:gt(0)');
    $(tr).each(function(){
        var feeText=$(this).children('td').eq(5)[0].innerText;
        if(feeText.length>0){
            fee+=parseFloat(feeText);
        }
    });
    $('#rightDan').html(tr.length);
    $('#rightFee').html(fee);

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
    var loadIds=new Array();
    var transportType =$('#transportType').val();
    var tr=$('#rightTable tr:gt(0)');

    var totalFee=0;
    $(tr).each(function(){
        loadIds.push($(this)[0].children[8].children[0].value);
            totalFee+=parseFloat($(this)[0].children[5].innerText);
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
                url:'/kd/finance/payable/settlementLoads?loadIds='+loadIds,
                data:$('#confirmform').serialize(),
                success:function(data){
                    if(data.success){
                        layer.msg("确认成功！",{time: 1000},function(){
                            window.location.href='/kd/finance/payable/loadTransportPage?transportType='+transportType;
                        });
                    }else{
                        layer.msg("确认失败！");
                    }
                }
            });
        }, function() {
        });


}