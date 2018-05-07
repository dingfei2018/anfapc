//签收
(function($){
function Sign(){
	this.searchParam = "";
	this.init();
}
Sign.prototype={
		init:function(){
			//$("#selectAll").bind("click", this.selectAll);
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			
			$("#loadId").on("click", ".banner-right-th", $.proxy(function (e) {
				this.openview(e);
            }, this));
			
			$(".a3").bind("click", this.excelExport);
			
			/*$('#senderId').combobox({
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
		    });			*/
			
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
				url:'/kd/sign/search',
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
			var html = "<tr class=\"tr_css\" align=\"center\" data='"+obj.ship_id+"'>";
			html += "<td data='index'>"+index+"</td>";
			html += "<td  style='color: #3974f8;cursor: pointer;'>"+obj.ship_sn+"</td>";
			html += "<td>"+obj.snetworkName+"</td>";
			html += "<td>"+obj.create_time+"</td>";
			html += "<td>"+obj.fromCity+"</td>";
			html += "<td>"+obj.toCity+"</td>";
			html += "<td>"+obj.sendName+"</td>";
			html += "<td>"+obj.receiverName+"</td>";
			html += "<td>"+obj.ship_volume+"</td>";
			html += "<td>"+obj.ship_weight+"</td>";
			html += "<td class=\"banner-right-padding\">"+obj.ship_amount+"</td>";
			
			if(obj.ship_status==3){
				html += "<td><span>已签收</span></td></tr>";
			}else{
				html += "<td class=\"banner-right-th\"><span>签收</span></td></tr>";
			}
			
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
		openview:function(e){
			var $this = this;
			var tr = $(e.target).parents("tr");
			var shipId = $(tr).attr("data");
			layer.open({
				  type: 2,
				  title: "签单信息",
				  area: ['1200px', '900px'],
				  content: ['/kd/sign/signupload?shipId='+shipId, 'yes'], //iframe的url，no代表不显示滚动条'/kd/loading/loadchange?truck_id=21'
				  end: function(){ 
					  	$this.search();
					  }
				  });
		}
};

$(function(){
	new Sign();
})
})(jQuery);	
	
	


