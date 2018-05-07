//创建运单
	var netId;
	init();
	 var input=new Array();
    //初始化
    function init(){
    	 $("input[name='kdShip.network_id']").focus();
    	 var d = new Date();
    	 var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
    	 $(".span").html(str);
    	 document.onkeyup = function (event) {
    	        var e = event || window.event;
    	        var keyCode = e.keyCode || e.which;
    	        if (keyCode == 120) {
    	        	saveShip();
    	        }
    	        if (keyCode == 121) {
    	        	saveShip(1);
    	        }
    	        if (keyCode == 121) {
    	        	saveShip(1);
    	        }
    	        if (keyCode == 27) {
    	        	parent.layer.closeAll();
    	        }
    	        if (keyCode == 9&document.activeElement.id=='saveButton') {
    	        	input[0].focus();
    	        }
    	    }
    	 netWorkCombobox();
    	 pnameFilter();
    }
  
 	  	 function keyDown(event) {
 	  		 var inputs=document.getElementById("mm").getElementsByTagName("INPUT");
 	  		
 	  		 for(var i=0;i<inputs.length; i++){
 	  			 if((inputs[i].type=='text'||inputs[i].type=='checkbox')
 	  				&inputs[i].id!='senderName'&inputs[i].id!='toAddr'&inputs[i].id!='toAddrCounty'
 	  				&inputs[i].id!='receiverName'&inputs[i].id!='netWorkId'&inputs[i].id!='fromAddr'
 	  				&inputs[i].id!='totalFee'){
 	  				 input.push(inputs[i]);
 	  				 if(inputs[i].id=='goodsSn'){
 	  					input.push($('#deliveryMethod')[0]); 
 	  				 }
 	  				 if(inputs[i].id=='addonFee'){
 	  					 input.push($('#payWay')[0]); 
 	  				 }
 	  			 }
 	  		 }
 	  		 var focus=document.activeElement; 
 	  		 if(!document.getElementById("mm").contains(focus)){
 	  			 $("input[name='kdShip.network_id']").focus();
 	  			 return; 
 	  		 }
 	  		 var event=window.event||event;
 	  		 var key=event.keyCode;
 	  		 for(var i=0; i<input.length; i++) { 
 	  			 if(input[i]===focus) break;
 	  	   	 }
 	  	   	 switch(key){ 
 	  	   	 	case 17: if(i>0) input[i-1].focus(); break; 
 	  	   	 	case 9: if(i<input.length-1) input[i].focus(); break; 
 	  	   	   } 
 	  	   	} 
    
    //开单网点combobox
    function netWorkCombobox(){
    	
    	$('#netWorkId').combobox({
            url: '/kd/waybill/getNetWorkJson',
            valueField: 'id',
            textField: 'sub_network_name',
            onSelect: function (row) {
                console.log(row);

                $('#fromAddr').val(row.shortAddr);
                $('#fromCode').val(row.region_code);
                $('#scode').val(row.region_code);
                var netWorkId = row.id;
                netId=netWorkId;
                getShipSn(netWorkId);
                toAddrCombobox(netWorkId);
                //
            },

            onLoadSuccess: function () {  //加载完成后,设置选中第一项
                var data = $(this).combobox("getData")[0];
                $(this).combobox('setValue', data.id);
                getShipSn(data.id);
                $('#fromAddr').val(data.shortAddr);
                $('#fromCode').val(data.region_code);
                $('#scode').val(data.region_code);
                console.log(data);
                toAddrCombobox(data.id);
            }
        });
    	
    	 $('#netWorkId').combobox('textbox').bind('focus',function(){
             $('#netWorkId').combobox('showPanel');
         });
    }
    //到达地城市combobox
    function toAddrCombobox(netWorkId){
    	  $('#toAddr').combobox({
              url: '/kd/line/getLineJsonByNetWorkId?netWorkId=' + netWorkId,
              valueField: 'to_city_code',
              textField: 'toAdd',
              onSelect: function (row) {
                  $('#toNetWorkName').val(typeof(row.arriveNetworkName)=='undefined'?"":row.arriveNetworkName);
                  $('#toNetWorkId').val(typeof(row.arrive_network_id)=='undefined'?"":row.arrive_network_id);
                  
                  var code = typeof(row.to_city_code)=='undefined'?"":row.to_city_code;
                  $('#toCode').val(code);
                  $('#rcode').val(code);
                  
                  toAddrCountyCombobox(code);
              },
              onLoadSuccess: function () {  //加载完成后,设置选中第一项
            	  
            	  if(typeof($(this).combobox("getData")[0])!='undefined'){
            		  var data = $(this).combobox("getData")[0];
                      var code=typeof(data.to_city_code)=='undefined'?"":data.to_city_code;
                      $('#toCode').val(code);
                      $('#rcode').val(code);
                      $('#toNetWorkName').val(typeof(data.arriveNetworkName)=='undefined'?"":data.arriveNetworkName);
                      $('#toNetWorkId').val(typeof(data.arrive_network_id)=='undefined'?"":data.arrive_network_id);
                  
                      $(this).combobox('setValue', code);
                      toAddrCountyCombobox(code);
            	  }else{
            		  toAddrCountyCombobox("");
            	  }
              }
          })
          
          $('#toAddr').combobox('textbox').bind('focus',function(){
     		 $('#toAddr').combobox('showPanel');
     	 });
    	
    }
    
    //到达地区级combobox
    function toAddrCountyCombobox(code){
    	  $('#toAddrCounty').combobox({
              url: '/kd/waybill/getCountyJson?code=' + code,
              valueField: 'region_code',
              textField: 'region_name',
              onSelect: function (row) {
                  var code = typeof(row.region_code)=='undefined'?"":row.region_code;
                  $('#toCode').val(code);
                  $('#rcode').val(code);
              }
          })
          
          $('#toAddrCounty').combobox('textbox').bind('focus',function(){
      		 $('#toAddrCounty').combobox('showPanel');
      	 });
    	
    }
    //货物名称combobox过滤
    function pnameFilter() {
    	
    	$('#pname1').combobox({
    	    filter: function(q, row){
    	    	if(q==0&&row.value=='其它'){return true;}if(q==1&&row.value=='配件'){return true;}
    	    	if(q==2&&row.value=='服装'){return true;}if(q==3&&row.value=='食品'){return true;}
    	    	if(q==4&&row.value=='文具'){return true;}if(q==5&&row.value=='化妆品'){return true;}
    	    	if(q==6&&row.value=='涂料'){return true;}if(q==7&&row.value=='电器'){return true;}
    	    	if(q==8&&row.value=='茶叶'){return true;}if(q==9&&row.value=='家具'){return true;}
    	    	return false;
    	    }
    	});
    	$('#pname2').combobox({
    		filter: function(q, row){
    			if(q==0&&row.value=='其它'){return true;}if(q==1&&row.value=='配件'){return true;}
    			if(q==2&&row.value=='服装'){return true;}if(q==3&&row.value=='食品'){return true;}
    			if(q==4&&row.value=='文具'){return true;}if(q==5&&row.value=='化妆品'){return true;}
    			if(q==6&&row.value=='涂料'){return true;}if(q==7&&row.value=='电器'){return true;}
    			if(q==8&&row.value=='茶叶'){return true;}if(q==9&&row.value=='家具'){return true;}
    			return false;
    		}
    	});
    	$('#pname3').combobox({
    		filter: function(q, row){
    			if(q==0&&row.value=='其它'){return true;}if(q==1&&row.value=='配件'){return true;}
    			if(q==2&&row.value=='服装'){return true;}if(q==3&&row.value=='食品'){return true;}
    			if(q==4&&row.value=='文具'){return true;}if(q==5&&row.value=='化妆品'){return true;}
    			if(q==6&&row.value=='涂料'){return true;}if(q==7&&row.value=='电器'){return true;}
    			if(q==8&&row.value=='茶叶'){return true;}if(q==9&&row.value=='家具'){return true;}
    			return false;
    		}
    	});
    	
    }
	
    //获取货号
    function getGoodsSn(){
    	
    	var amount0=$("#product_amount0").val().length==0?0:$("#product_amount0").val();
    	var amount1=$('#product_amount1').val().length==0?0:$("#product_amount1").val();
    	var amount2=$('#product_amount2').val().length==0?0:$("#product_amount2").val();
    	
    	var countNum=parseInt(amount0)+parseInt(amount1)+parseInt(amount2);
    	
    	 $.ajax({
             type: 'post',
             dataType: 'text',
             url: '/kd/waybill/getWaybillNumber?type=goods&netWorkId='+netId+'&shipSn='+$('#shipSn').val()+'&countNum='+countNum,
             success: function (data) {
                 $("#goodsSn").val(data);
             }
         })
    	
    }
    //获取运单号
    function getShipSn(netWorkId) {
    	
        $.ajax({
            type: 'post',
            dataType: 'text',
            url: '/kd/waybill/getWaybillNumber?netWorkId=' + netWorkId,
            success: function (data) {
                $("input[name='kdShip.ship_sn']").val(data);
                netId=netWorkId;
                getGoodsSn();
            }
        })

    }


//收货人
    $("#receiverName").combogrid({
        panelWidth: 320,
        idField: 'customer_name',
        textField: 'customer_name',
        height: 30,
        pagination: true,
        url: '/kd/customer/searchCustomer?type=1',
        method: 'get',
        columns: [[
            {field: 'customer_name', title: '姓名', width: 150},
            {field: 'customer_mobile', title: '电话', width: 200}
        ]],
        onSelect: function (rowIndex, rowData) {
            $("#receiverMoblie").val(rowData.customer_mobile);
            // $("#endId").region({domain: "/", required: true, cityRequired: true, currAreaCode: rowData.region_code});
            $("#receiverAddress").val(rowData.tail_address);
            $("#ruid").val(rowData.uuid);
            $("#shipReceiverId").val(rowData.customer_id);
            $("#rid").val(rowData.customer_id);
            $("#rcode").val($('#toCode').val());
        },
        fitColumns: true
    })


//托运人
    $("#senderName").combogrid({
        panelWidth: 320,
        idField: 'customer_name',
        textField: 'customer_name',
        height: 30,
        pagination: true,
        url: '/kd/customer/searchCustomer?type=2',
        method: 'get',
        columns: [[
            {field: 'customer_name', title: '姓名', width: 150},
            {field: 'customer_mobile', title: '电话', width: 200}
        ]],
        onSelect: function (rowIndex, rowData) {
            $("#senderMoblie").val(rowData.customer_mobile);
            // $("#startId").region({domain: "/", required: true, cityRequired: true, currAreaCode: rowData.region_code});
            $("#senderAddress").val(rowData.tail_address);
            $("#suid").val(rowData.uuid);
            $("#shipSenderId").val(rowData.customer_id);
            $("#sid").val(rowData.customer_id);
            $("#scode").val(rowData.region_code);
        },
        fitColumns: true
    })
    /**
     * 保存时若无输入货物数据则去掉name
     * @returns
     */
    function changeNameForProduct(){
    	
    	var pname2=$('#pname2').combobox('getValue');
    	var pname3=$('#pname3').combobox('getValue');
    	
    	if(pname2.length==0){
    		$("input[name='kdProduct[1].product_name']").attr("name","");
    		$("input[name='kdProduct[1].product_unit']").attr("name","");
    		$("input[name='kdProduct[1].product_amount']").attr("name","");
    		$("input[name='kdProduct[1].product_volume']").attr("name","");
    		$("input[name='kdProduct[1].product_weight']").attr("name","");
    		$("input[name='kdProduct[1].product_price']").attr("name","");
    	}
    	if(pname3.length==0){
    		$("input[name='kdProduct[2].product_name']").attr("name","");
    		$("input[name='kdProduct[2].product_unit']").attr("name","");
    		$("input[name='kdProduct[2].product_amount']").attr("name","");
    		$("input[name='kdProduct[2].product_volume']").attr("name","");
    		$("input[name='kdProduct[2].product_weight']").attr("name","");
    		$("input[name='kdProduct[2].product_price']").attr("name","");
    	}
    	
    }

 /**
     * 保存运单
     */
    function saveShip(type) {
    	changeNameForProduct();
    	
        if($("input[name='kdShip.ship_is_dispatch']").is(':checked')) {
            $("input[name='kdShip.ship_is_dispatch']").val(1)
        }else{
            $("input[name='kdShip.ship_is_dispatch']").val(0);
        }
        if($("input[name='kdShip.ship_is_rebate']").is(':checked')) {
            $("input[name='kdShip.ship_is_rebate']").val(1)
        }else{
            $("input[name='kdShip.ship_is_rebate']").val(0);
        }
     /*表单验证*/
     if ($("input[name='kdShip.ship_sn']").val()=="") {
         layer.msg("运单号不能为空");
         return false;
     }
     if(checkShipSn()==false){
         return false;
     }
     if($("input[name='kdShip.network_id']").val()==$('#toNetWorkId').val()){
         layer.msg("开单网点不能和到货网点一样!");
         return false;
     }
     if ($('#netWorkId').combobox('getValue')=="") {
         layer.msg("请选择开单网点");
         return false;
     }
     /*托运人的验证*/
     if ($("input[name='customer[0].customer_name']").val()=="") {
         layer.msg("托运人不能为空");
         return false;
     }
     if ($("input[name='customer[0].customer_mobile']").val()=="") {
         layer.msg("联系电话不能为空");
         return false;
     }
     if ($("input[name='customer[0].tail_address']").val()=="") {
         layer.msg("详细地址不能为空");
         return false;
     }
     /*收货人的验证*/
     if ($("input[name='customer[1].customer_name']").val()=="") {
         layer.msg("收货人不能为空");
         return false;
     }
     if ($("input[name='customer[1].customer_mobile']").val()=="") {
         layer.msg("联系电话不能为空");
         return false;
     }
     if ($("input[name='customer[0].tail_address']").val()=="") {
         layer.msg("详细地址不能为空");
         return false;
     }
     var product_name = $("input[name='kdProduct[0].product_name']").val();
     if (product_name == "") {
         layer.msg("货物名称不能为空");
         return false;
     }
     var product_unit = $("input[name='kdProduct[0].product_unit']").val();
     if (product_unit == "") {
    	 layer.msg("包装单位不能为空");
    	 return false;
     }

     var product_amount = $("input[name='kdProduct[0].product_amount']").val();
     if (product_amount == "") {
         layer.msg("件数不能为空");
         return false;
     }else if(product_amount<=0){
         layer.msg("件数必须大于0");
         return true;

     }
     var product_volume = $("input[name='kdProduct[0].product_volume']").val();
     if (product_volume == "") {
         layer.msg("体积/立方不能为空");
         return false;
     }
     var product_weight = $("input[name='kdProduct[0].product_weight']").val();
     if (product_weight == "") {
         layer.msg("重量/立方不能为空");
         return false;
     }
     var product_price = $("input[name='kdProduct[0].product_price']").val();
     if (product_price == "") {
         layer.msg("货值不能为空");
         return false;
     }
     /*费用验证*/
     var kdShip = $("input[name='kdShip.ship_fee']").val();
     if (kdShip == "") {
         layer.msg("运费不能为空");
         return false;
     }
     var ship_insurance_fee = $("input[name='kdShip.ship_insurance_fee']").val();
     if (ship_insurance_fee == "") {
         layer.msg("保价费不能为空");
         return false;
     }

     var kdShipship_package_fee = $("input[name='kdShip.ship_package_fee']").val();
     if (kdShipship_package_fee == "") {
         layer.msg("包装费不能为空");
         return false;
     }

     var kdShipship_delivery_fee = $("input[name='kdShip.ship_delivery_fee']").val();
     if (kdShipship_delivery_fee == "") {
         layer.msg("送货费不能为空");
         return false;
     }
     var kdShipship_pickup_fee = $("input[name='kdShip.ship_pickup_fee']").val();
     if (kdShipship_pickup_fee == "") {
         layer.msg("提货费不能为空");
         return false;
     }
     var kdShipship_addon_fee = $("input[name='kdShip.ship_addon_fee']").val();
     if (kdShipship_addon_fee == "") {
         layer.msg("其他费不能为空");
         return false;
     }
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/kd/waybill/shipSave",
            data: $('#formid').serialize(),
            success: function (data) { 
                if (data.success) {
                	
                	if(typeof(data.shipId)!='undefined'&type==1){
                		layer.msg("开单成功！", {time: 1000}, function () {
                      		printWaybill(data.shipId);
                           });
                	}else{
                		layer.msg("开单成功！", {time: 1000}, function () {
                   		 parent.layer.closeAll();
                        });
                	}
                	
                } else {
                    layer.msg("开单失败！");
                }
            }
        });
    }
    
    //费用合计
function shipTotalFee() {
    var total = parseFloat($("input[name='kdShip.ship_fee']").val());
    var total2 = parseFloat($("input[name='kdShip.ship_insurance_fee']").val());
    var total3 = parseFloat($("input[name='kdShip.ship_pickup_fee']").val());
    var total4 = parseFloat($("input[name='kdShip.ship_package_fee']").val());
    var total5 = parseFloat($("input[name='kdShip.ship_delivery_fee']").val());
    var total6 = parseFloat($("input[name='kdShip.ship_addon_fee']").val());

    var total7 = parseFloat($("input[name='kdShip.ship_nowpay_fee']").val());
    var total8 = parseFloat($("input[name='kdShip.ship_pickuppay_fee']").val());
    var total9 = parseFloat($("input[name='kdShip.ship_receiptpay_fee']").val());
    var total10 = parseFloat($("input[name='kdShip.ship_monthpay_fee']").val());
    var total11 = parseFloat($("input[name='kdShip.ship_arrearspay_fee']").val());
    var total12 = parseFloat($("input[name='kdShip.ship_goodspay_fee']").val());

    if (total !== null || total !== undefined || total !== '') {
        var variable1 = total || 0;
    }
    if (total2 !== null || total2 !== undefined || total2 !== '') {
        var variable2 = total2 || 0;
    }
    if (total3 !== null || total3 !== undefined || total3 !== '') {
        var variable3 = total3 || 0;
    }
    if (total4 !== null || total4 !== undefined || total4 !== '') {
        var variable4 = total4 || 0;
    }
    if (total5 !== null || total5 !== undefined || total5 !== '') {
        var variable5 = total5 || 0;
    }
    if (total6 !== null || total6 !== undefined || total6 !== '') {
        var variable6 = total6 || 0;
    }

    if (total7 !== null || total7 !== undefined || total7 !== '') {
        var variable7 = total7 || 0;
    }
    if (total8 !== null || total8 !== undefined || total8 !== '') {
        var variable8 = total8 || 0;
    }
    if (total9 !== null || total9 !== undefined || total9 !== '') {
        var variable9 = total9 || 0;
    }
    if (total10 !== null || total10 !== undefined || total10 !== '') {
        var variable10 = total10 || 0;
    }
    if (total11 !== null || total11 !== undefined || total11 !== '') {
        var variable11 = total11 || 0;
    }
    if (total2 !== null || total2 !== undefined || total2 !== '') {
        var variable12 = total12 || 0;
    }
    var variableo=0;
    var selectval=$("#payWay option:selected").val();

    if(selectval==7){
        variableo=variable7 + variable8 + variable9 + variable10 + variable11 + variable12;
    }else{
        variableo= variable1 + variable2 + variable3 + variable4 + variable5 + variable6;
    }
    variableo.toFixed(2);
    $("input[name='kdShip.ship_total_fee']").val(variableo);
    if(selectval!=7) changePayway();
}

function checkShipSn(){
    var n=false;
    $.ajax({
        type:"post",
        dataType:"json",
        async: false,
        url:"/kd/waybill/checkShipSn",
        data:{shipSn:$("#shipSn").val()},
        success:function(data){
            if(!data.success){
                layer.msg("运单号存在!请重新输入");
            }else{
                n=true;
            }
        }
    });
    if(n) {
        return true;
    }else{
        return false;
    }

}
function changePayway(){
    var selectval=$("#payWay option:selected").val();
    var total=$("input[name='kdShip.ship_total_fee']").val();
    $("input[name='kdShip.ship_nowpay_fee']").val("");
    $("input[name='kdShip.ship_pickuppay_fee']").val("");
    $("input[name='kdShip.ship_receiptpay_fee']").val("");
    $("input[name='kdShip.ship_monthpay_fee']").val("");
    $("input[name='kdShip.ship_arrearspay_fee']").val("");
    $("input[name='kdShip.ship_goodspay_fee']").val("");

    $("input[name='kdShip.ship_nowpay_fee']").attr("readonly","readonly");
    $("input[name='kdShip.ship_pickuppay_fee']").attr("readonly","readonly");
    $("input[name='kdShip.ship_receiptpay_fee']").attr("readonly","readonly");
    $("input[name='kdShip.ship_monthpay_fee']").attr("readonly","readonly");
    $("input[name='kdShip.ship_arrearspay_fee']").attr("readonly","readonly");
    $("input[name='kdShip.ship_goodspay_fee']").attr("readonly","readonly");
    switch(selectval){
        case '1': $("input[name='kdShip.ship_nowpay_fee']").val(total) ; break;
        case '2': $("input[name='kdShip.ship_pickuppay_fee']").val(total) ;  break;
        case '3': $("input[name='kdShip.ship_receiptpay_fee']").val(total) ;  break;
        case '4': $("input[name='kdShip.ship_monthpay_fee']").val(total) ;  break;
        case '5': $("input[name='kdShip.ship_arrearspay_fee']").val(total) ;  break;
        case '6': $("input[name='kdShip.ship_goodspay_fee']").val(total) ;  break;
        default:
            $("input[name='kdShip.ship_nowpay_fee']").removeAttr("readonly");
            $("input[name='kdShip.ship_pickuppay_fee']").removeAttr("readonly");
            $("input[name='kdShip.ship_receiptpay_fee']").removeAttr("readonly");
            $("input[name='kdShip.ship_monthpay_fee']").removeAttr("readonly");
            $("input[name='kdShip.ship_arrearspay_fee']").removeAttr("readonly");
            $("input[name='kdShip.ship_goodspay_fee']").removeAttr("readonly");
            break;
    }


}





