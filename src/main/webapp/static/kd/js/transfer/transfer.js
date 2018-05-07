/**
 * 中转
 */
	init(true);
	$(function() {

		//设置当前日期 年月日时分秒
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1
				+ strDate + " " + date.getHours() + seperator2
				+ date.getMinutes() + seperator2 + date.getSeconds();
		$('#time').val(currentdate);

		$('#customerCrop').combogrid({
			url : '/kd/customer/searchCustomer?type=3',
			idField : 'customer_corp_name',
			textField : 'customer_corp_name',
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
	               $('#customerCrop').combogrid("grid").datagrid("reload", {'queryName': q});
	               $('#customerCrop').combogrid("setValue", q);
	            }
	        },onSelect : function(rowIndex, rowData) {
				$('#customerName').val(rowData.customer_name);
				$('#customerId').val(rowData.customer_id);
				$('#customerMobile').val(rowData.customer_mobile);
				$('#customerAddressId').val(rowData.customer_address_id);
			}
			,fitColumns: true
		});
		
		

		$('#netWorkCombo').combobox({
			url : '/kd/user/getNetWorkByUserId',
			valueField : 'id',
			textField : 'sub_network_name'
		});

		$('#shipperName').combogrid({
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
	               $('#shipperName').combogrid("grid").datagrid("reload", {'queryName': q});
	               $('#shipperName').combogrid("setValue", q);
	            }
	        },
			fitColumns: true
		});
	    
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
        $('#filterShipSn').val("");
        $('#filterTranShipSn').val("");

        $("#waybillonAll").prop("checked", false);
        $("#transferonAll").prop("checked", false);

        fifterTranShipSn();

        if(flag) getData();

        orderNum();
        shipTotal();
        transferTotal();
        transferFeeTotal();
        setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
        setTimeout(function(){$("#loadingId2").mLoading("hide");}, 200);
    }
	
	function resetCity(){
		$("#city-picker2").citypicker("reset");
		$("#city-picker3").citypicker("reset");
		 
		$('#shipperName').combogrid('clear');
		$('#receivingName').combogrid('clear');
	}

	function search() {
		init(true);
	}

	function saveTransfer() {

		var numTest = /(^[1-9]\d*\.\d*$)|(^\d+$)/;

		var tr=$('#transferTable tr:gt(0)');
		var showTr=new Array();
		$(tr).each(function(){
            if(!$(this).is(':hidden')){
                showTr.push($(this));
			}
		});
		for(var i=0;i<showTr.length;i++){
			var transferSnInput=showTr[i][0].children[3].children[0];
            $(transferSnInput).attr("name","shipTransfer["+i+"].ship_transfer_sn");
            $(transferSnInput).attr("id","y_shipTransferSn"+i);

            var transferFeeInput=showTr[i][0].children[4].children[0];
            $(transferFeeInput).attr("name","shipTransfer["+i+"].ship_transfer_fee");
            $(transferFeeInput).attr("id","y_shipTransferFee"+i);

            var transferIdInput=showTr[i][0].children[11].children[0];
            $(transferIdInput).attr("name","shipTransfer["+i+"].ship_id");
		}

        if (showTr.length == 0) {
            layer.msg("请选择要中转的数据");
            return;
        }

		var reg = /(^0\d{2,3}\-\d{7,8}$)|(^1[3|4|5|6|7|8][0-9]{9}$)/;

		if ($("#customerCrop").combogrid('getValue').length == 0) {
			Anfa.show("请填写或选择中转方", "#y_transfer");
			return;
		}
		if ($("#customerName").val().length == 0) {
			Anfa.show("请填写联系人", "#customerName");
			return;
		}
		if ($("#customerMobile").val().length == 0) {
			Anfa.show("请输入联系电话", "#customerMobile");
			return;
		} else if (!(reg.test($("#customerMobile").val()))) {
			Anfa.show("手机或者固话输入错误", "#customerMobile");
			return;
		}
		if ($("#time").val().length == 0) {
			Anfa.show("请选择中转时间", "#time");
			return;
		}

        for (var i = 0; i <showTr.length; i++) {
            if (typeof $("#y_shipTransferSn" + i).val() != 'undefined') {
                if ($("#y_shipTransferSn" + i).val().length == 0) {
                    Anfa.show("请输入中转单号", "#y_shipTransferSn" + i);
                    return;
                }

                if ($("#y_shipTransferFee" + i).val().length == 0) {
                    Anfa.show("请输入中转费用", "#y_shipTransferFee" + i);
                    return;
                } else if (!numTest.test($("#y_shipTransferFee" + i).val())) {
                    Anfa.show("请输入非负数正确费用", "#y_shipTransferFee" + i);
                    return;
                }
            }
        }

        $.ajax({
            type:'POST',
            url:'/kd/transfer/save',
            data:$('#transferForm').serialize(),
            success:function(data){
                if(data.success){
                    layer.msg("中转成功！",{time: 1000},function(){
                        window.location.reload();
                    });
                }else{
                    layer.msg("中转失败！");
                }
            }
        });


	}

	function getData (){
        $.ajax({
            url : '/kd/transfer/getWayBill',
            data : {
                shipperName : $('#shipperName').val(),
                receivingName : $('#receivingName').val(),
                startCode : $('#startCode').val(),
                endCode : $('#endCode').val()
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
                            html += '<tr class="tr_css" align="center" ondblclick="doubleClickTransfer($(this));">'
                                + '<td><input type="checkbox"></td>'
                                + '<td>'+(i+1)+'</td>'
                                + '<td  style="color: #3974f8;cursor: pointer;"><a href="#" onclick="openShipDiv(' + row[i].ship_id + ');">' + row[i].ship_sn + '</a></td>'
                                + '<td>' + row[i].toAdd + '</td>'
                                + '<td>' + row[i].productName + '</td>'
                                + '<td>' + row[i].ship_volume + '</td>'
                                + '<td>' + row[i].ship_weight + '</td>'
                                + '<td>' + row[i].ship_amount + '</td>'
                                + '<td>' + row[i].fromAdd + '</td>'
                                + '<td>' + row[i].netWorkAdd + '<input type="hidden" value="' + row[i].ship_id + '"></td>'
                                + '</tr>';
                        }
                    }
                    $("#waybillTable tr:gt(0)").remove();
                    $('#waybillTable').append(html);
                }
            }
        });
    }

	function checkShipId(shipId){
		var flag=true;
        var tr=$('#transferTable tr:gt(0)');
        for(var i=0;i<tr.length;i++){
            if(typeof tr[i].children[11].children[0]!='undefined'){
                if(shipId==tr[i].children[11].children[0].value) flag=false;
            }
        }
        return flag;
	}

	//选中中转
	function transferAll() {

		var check = $("#waybillTable input[type='checkbox']");
		var flag=true;
		for (var i = 1; i < check.length; i++) {
			if (check[i].checked ) {
				var row = check[i].parentElement.parentElement;
				transferBycheck(row);
                flag=false;
			}
		}
		if(flag){
                layer.msg('请选择要中转的运单');
                return;
		}
	}

    //根据选中中转
    function transferBycheck(obj) {
        obj.remove();

        if(typeof obj[0]!='undefined'){
            $(obj[0]).attr("ondblclick","doubleClickdel($(this))");
        }else{
            $(obj).attr("ondblclick","doubleClickdel($(this))");

        }
        $(obj).each(function () {
            $(this).children('td').eq(2).after("<td><input type='text'></input></td><td><input type='text' onblur='transferFeeTotal();' onkeyup='only_num(this)'></input></td>");

            $(this).children('td').eq(0)[0].firstChild.checked=false;
        });
        $("#transferTable").append(obj);
        shipTotal();
        transferTotal();
        init();
    }


    //选中删除
	function delAll() {

		var check = $("#transferTable input[type='checkbox']");

        if(check.length==1){
            layer.msg('请先选择需要中转的运单');
            return;
        }

		var flag=true;
		for (var i = 1; i < check.length; i++) {
			if (check[i].checked ) {
				var row = check[i].parentElement.parentElement;
				delTransferBycheck(row);
                flag=false;
			}
		}

        if(flag){
            layer.msg('请选择要删除的中转单');
            return;
        }
	}

    //根据选中取消中转
    function delTransferBycheck(obj) {
        obj.remove();
        if(typeof obj[0]!='undefined'){
            $(obj[0]).attr("ondblclick","doubleClickTransfer($(this))");
        }else{
            $(obj).attr("ondblclick","doubleClickTransfer($(this))");

        }
        $(obj).each(function () {
            $(this).children('td').eq(4).remove();
            $(this).children('td').eq(3).remove();
            $(this).children('td').eq(0)[0].firstChild.checked=false;
        });
        $("#waybillTable").append(obj);
        transferTotal();
        shipTotal();
        init();
    }

	//运单列表全选事件
	function shiponAll() {
		if ($("#waybillonAll").is(':checked')) {
			$("#waybillTable input[type='checkbox']").prop("checked", true);
		} else {
			$("#waybillTable input[type='checkbox']").prop("checked", false)
		}
	}

	//中转单全选事件
	function onAll() {
		if ($("#transferonAll").is(':checked')) {
			$("#transferTable input[type='checkbox']").prop("checked", true);
		} else {
			$("#transferTable input[type='checkbox']").prop("checked", false)
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

	//运单列表根据运单号过滤
	function fifterShipSn(){
        var tr=$('#waybillTable tr:gt(0)');
        var shipSn=$('#filterShipSn').val();
        tr.hide();
            $(tr).each(function(){
                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                if(shipSnText.indexOf(shipSn)>-1){
                   $(this).show();
                }
            });
	}
	//运单列表根据运单号回车事件
	function enterFifterShipSn(event){

        if(event.keyCode ==13){

            var shipSn=$('#filterShipSn').val();
            var tr=$('#waybillTable tr:gt(0)');

            $(tr).each(function(){
                if(!$(this).is(':hidden')){

                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                var showTr=new Array();
                $('#waybillTable tr:gt(0)').each(function(){
                    if(!$(this).is(':hidden')){
                        showTr.push($(this));
                    }
                    });

                    if(shipSnText.indexOf(shipSn)>-1&&shipSn.length>0&&showTr.length==1){
                        transferBycheck($(this));
                        $('#filterShipSn').val("");
                    }
                }

            });
        }


	}

	//中转单列表根据运单号过滤
	function fifterTranShipSn(){
        var tr=$('#transferTable tr:gt(0)');
        var shipSn=$('#filterTranShipSn').val();
            tr.hide();
            $(tr).each(function(){
                var shipSnText=$(this).children('td').eq(2)[0].innerText;
                if(shipSnText.indexOf(shipSn)>-1){
                   $(this).show();
                }
            });
	}

    //中转单列表根据运单号回车事件
    function enterfifterTranShipSn(event){

        if(event.keyCode ==13){

            var shipSn=$('#filterTranShipSn').val();
            var tr=$('#transferTable tr:gt(0)');

            $(tr).each(function(){

                    var shipSnText=$(this).children('td').eq(2)[0].innerText;
                    var showTr=new Array();
                    $('#transferTable tr:gt(0)').each(function(){
                        if(!$(this).is(':hidden')){
                            showTr.push($(this));
                        }
                    });

                    if(shipSnText.indexOf(shipSn)>-1&&shipSn.length>0&&showTr.length==1){
                        delTransferBycheck($(this));
                    }

            });
        }


    }

	//中转费合计
	function transferFeeTotal(){
        var transferFee=0;
        var tr=$('#transferTable tr:gt(0)');
        $(tr).each(function(){
            var fee=$(this).children('td').eq(4)[0].children[0].value;
            if(fee.length>0){
                transferFee+=parseFloat(fee);
            }
        });
		$('#transferTotalFee').val(transferFee);
	}

    function only_num(obj){
        //得到第一个字符是否为负号
        var num = obj.value.charAt(0);
        //先把非数字的都替换掉，除了数字和.
        obj.value = obj.value.replace(/[^\d\.]/g,'');
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,'');
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g,'.');
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
        //如果第一位是负号，则允许添加
        if(num == '-'){
            obj.value = '-'+obj.value;
        }
    }

    //运单合计
    function shipTotal(){
        var shipJian=0;
        var shipFang=0;
        var shipGong=0;
        var tr=$('#waybillTable tr:gt(0)');
        $(tr).each(function(){
            var jian=$(this).children('td').eq(7)[0].innerText;
            if(jian.length>0){
                shipJian+=parseFloat(jian);
            }
            var fang=$(this).children('td').eq(5)[0].innerText;
            if(fang.length>0){
                shipFang+=parseFloat(fang);
            }
            var gong=$(this).children('td').eq(6)[0].innerText;
            if(gong.length>0){
                shipGong+=parseFloat(gong);
            }
        });
        $('#shipDan').html(tr.length);
        $('#shipJian').html(shipJian);
        $('#shipFang').html(shipFang);
        $('#shipGong').html(shipGong);

	}

    //中转单合计
    function transferTotal(){
        var transferJian=0;
        var transferFang=0;
        var transferGong=0;
        var tr=$('#transferTable tr:gt(0)');
        $(tr).each(function(){
            var jian=$(this).children('td').eq(9)[0].innerText;
            if(jian.length>0){
                transferJian+=parseFloat(jian);
            }
            var fang=$(this).children('td').eq(7)[0].innerText;
            if(fang.length>0){
                transferFang+=parseFloat(fang);
            }
            var gong=$(this).children('td').eq(8)[0].innerText;
            if(gong.length>0){
                transferGong+=parseFloat(gong);
            }
        });
        $('#transferDan').html(tr.length);
        $('#transferJian').html(transferJian);
        $('#transferFang').html(transferFang);
        $('#transferGong').html(transferGong);

	}
	//序列号排序
	function orderNum(){
        var shipTr=$('#waybillTable tr:gt(0)');
        var TranTr=$('#transferTable tr:gt(0)');
        var i=1;
        var j=1;
        $(shipTr).each(function (){
                $($(this)[0].children[1]).html(i);
                i++;
        });
        $(TranTr).each(function (){
                $($(this)[0].children[1]).html(j);
                j++;
        });

    }

    function doubleClickTransfer(obj){
        obj[0].children[0].children[0].checked=true;
        transferAll();
    }
    function doubleClickdel(obj){
        obj[0].children[0].children[0].checked=true;
        delAll();
    }
