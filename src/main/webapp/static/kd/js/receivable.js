//应收
(function($){
function receivable(){
	this.searchParam = "";
	this.init();
}
receivable.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
	/*		//托运方信息
			$('#senderId').combobox({
				url : '${ctx}/kd/customer/queryCustomer?type=2',
				valueField : 'customer_id',
				textField : 'customer_name',
				height : 30,
				panelWidth : 300,
				panelHeight : 'auto',
			});
			//收货方信息
			$('#receiverId').combobox({
				url : '${ctx}/kd/customer/queryCustomer?type=1',
				valueField : 'customer_id',
				textField : 'customer_name',
				height : 30,
				panelWidth : 300,
				panelHeight : 'auto',
			});
			*/
			
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
			$('#receiverId').combogrid({ 
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
		               $('#receiverId').combogrid("grid").datagrid("reload", {'queryName': q});
		               $('#receiverId').combogrid("setValue", q);
		            }
		        },
				fitColumns: true
			});
			
			this.search();
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
				url:'/kd/finance/receivable/search',
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
			var html = "<tr class='tr_css' align='center' >";
			html += "<td><input type='checkbox' value="+obj.ship_id+" data="+obj.ship_total_fee+" /></td>";
			html += "<td>"+index+"</td>";
			html += "<td ><a href='#' class='btn' onclick=openDiv("+obj.ship_id+")>"+obj.ship_sn+"</a></td>";
			html += "<td>"+obj.netName+"</td>";
			if(obj.ship_fee_status==0){
				html += "<td ><b>未付款</b></td>";
			}else{
				html += "<td ><b>已付款</b></td>";
			}
			if(obj.ship_pay_way==1){
				html += "<td><b>现付</b></td>";
			}else if(obj.ship_pay_way==2){
				html += "<td><b>提付</b></td>";
			}else if(obj.ship_pay_way==3){
				html += "<td><b>到付</b></td>";
			}else if(obj.ship_pay_way==4){
				html += "<td><b>回单付</b></td>";
			}else{
				html += "<td><b>月结</b></td>";
			}
			html += "<td><b>"+obj.ship_total_fee+"</b></td>";
			if(obj.ship_pay_way==3 && (obj.network_id!=obj.load_network_id)){
				html += "<td>"+obj.endWorkName+"</td>";
			}else{
				html += "<td>&nbsp;</td>";
			}
			
			if(obj.ship_pay_way==3 && (obj.network_id!=obj.load_network_id)){
				if (obj.ship_agency_fund_status==0){
					html += "<Td>否</Td>";
				}else if(obj.ship_agency_fund_status==1||obj.ship_agency_fund_status==2||obj.ship_agency_fund_status==3){
					html += " <td>是</td>";
				}
			}else{
				html += "<td>&nbsp;</td>";
			}
			
			html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd  hh:mm")+"</td>";
			if (obj.ship_customer_number == null){
				html += "<td>&nbsp;</td>";
			}else{
				
				html += "<td>"+obj.ship_customer_number+"</td>";
			}
			html += "<td>"+obj.fromAdd+"</td>";
			html += "<td>"+obj.toAdd+"</td>";
			html += "<td>"+obj.senderName+"</td>";
			html += "<td class=\"banner-right-padding\">"+obj.receiverName+"</td>";
			if(obj.ship_fee_status==0){
				html += "<td class=\"banner-right-th\" onclick=openUpdate("+obj.ship_id+")><img src=\"/static/kd//img/r_icon1.png\"/><span>修改</span>";
			}
			html +="</td> </tr>";
			
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
		
		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/finance/payable/exportPayablelist?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	new receivable();
})
})(jQuery);	
	
	


