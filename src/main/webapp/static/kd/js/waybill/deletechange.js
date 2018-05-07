//删除变更记录
(function($){
function Change(){
	this.searchParam = "";
	this.init();
}
Change.prototype={
		init:function(){
			$("#selectAll").bind("click", this.selectAll);
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			
			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));
			
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
			
			
			this.search();
		}
		,
		search:function(){
			var param = $("#searchForm").serialize();
			param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, 1, this);
		},
		getData:function(param, pageNo, obj){
			$("#loadingId").mLoading("show");
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			
			$.ajax({
				type:'post',
				url:'/kd/waybill/deleteChangeJson',
				data:encodeURI(param),
				success:function(data){
					console.log(data);
					var html="";
					for(var i=0;i<data.list.length;i++){  
						html += obj.appendHtml(data.list[i], i+1)
				    } 
					$("#table > tr").remove();
					$("#table").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},
		appendHtml:function(obj, index){
			var html='<tr>'+
                '<td>'+index+'</td>'+
                '<td>'+obj.ship_sn+'</td>'+
                '<td>'+obj.netWork+'</td>'+
                '<td>'+obj.shipTime+'</td>'+
                '<td>'+obj.shipperName+'</td>'+
                '<td>'+obj.receivingName+'</td>'+
                '<td>'+obj.fromAdd+'</td>'+
                '<td>'+obj.toAdd+'</td>'+
                '<td>'+obj.userName+'</td>'+
                '<td>'+obj.updateTime+'</td>'+
                '<td style="border-left:1px solid #dcdcdc;"><a title="'+obj.update_content+'" style="display:block; width:300px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">'+obj.update_content+'</a></td>'+
			'</tr>';
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
    			window.location.href="/kd/Change/exportChangelist?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
		
};

$(function(){
	new Change();
	
})
})(jQuery);	


	
	


