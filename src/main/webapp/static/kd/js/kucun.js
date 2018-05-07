//库存查询
(function($){
	
function Kucun(){
	this.searchParam = "";
	this.init();
}
Kucun.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			
			$(".banner-right-a3").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));
			
			$(".tab_css_1").on("click", ".viewId",this.openShipView);
			
	/*		$('#senderId').combobox({
		        url:'/kd/customer/queryCustomer?type=2',
		        valueField:'customer_id',
		        textField:'customer_name',
		        height: 30,
		        panelWidth: 162,
		        panelHeight: 'auto'
		    });
		    //收货方信息
		    $('#receiveId').combobox({
		        url:'/kd/customer/queryCustomer?type=1',
		        valueField:'customer_id',
		        textField:'customer_name',
		        height: 30,
		        panelWidth: 162,
		        panelHeight: 'auto'
		    });	*/	
			
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
				url:'/kd/kucun/list',
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
		showShop:function(){
			DivUtils.ShowDiv("MyDiv", "运单详情-"+$(this).html());
		},
		appendHtml:function(obj, index){
			var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td data='index'>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.ship_id+"'  style='color: #3974f8;cursor: pointer;'>"+obj.ship_sn+"</td>";
			html += "<td>"+obj.enetworkName+"</td>";
			html += "<td>"+obj.intime+"</td>";
			html += "<td>"+obj.hashours+"</td>";
			html += "<td>"+obj.proname+"</td>";
			html += "<td>"+obj.ship_volume+"</td>";
			html += "<td>"+obj.ship_weight+"</td>";
			html += "<td>"+obj.ship_amount+"</td>";
			html += "<td>"+obj.snetworkName+"</td>";
			html += "<td>"+obj.sendName+"</td>";
			html += "<td>"+obj.receiverName+"</td>";
			html += "<td>"+obj.fromCity+"</td>";
			html += "<td>"+obj.toCity+"</td></tr>";
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
    			window.location.href="/kd/kucun/exportKucunlist?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		},
		openShipView:function(){

			var shipId = $(this).attr("data");
			window.location.href="/kd/waybill/viewDetail?shipId="+shipId;
			/*//页面层
			layer.open({
			  type: 2,
			  area: ['850px', '700px'], //宽高
			  content: ['/kd/waybill/viewDetail?shipId='+shipId, 'yes']
			});*/
		}
};
$(function(){
	new Kucun();
})
})(jQuery);	
	
	


