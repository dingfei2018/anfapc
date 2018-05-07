//异常
(function($){
function Abnormal(){
	this.searchParam = "";
	this.init();
}
Abnormal.prototype={
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
				url:'/kd/abnormal/getAbnormalPageList',
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
			
			var status=obj.abnormal_status==0?'待处理':obj.abnormal_status==1?'处理中':'已处理';
			var type=obj.abnormal_type==0?'货损':obj.abnormal_type==1?'少货':obj.abnormal_type==2?'多货':
				obj.abnormal_type==3?'货物丢失':obj.abnormal_type==4?'货单不符':obj.abnormal_type==5?'超重超方':
				obj.abnormal_type==6?'超时':obj.abnormal_type==7?'拒收':obj.abnormal_type==8?'投诉':'其他';
			var html='	<tr class="tr_css" align="center">'+
				'<td>'+index+'</td>'+
				'<td ><a href="#" onclick="openAbnormalDiv('+obj.abnormal_id+')" class="btn" style="color: #3974f8;cursor: pointer;">'+obj.abnormal_sn+'<a/></td>'+
				'<td ><a href="#" onclick="openShipDiv('+obj.ship_id+')" class="btn" style="color: #3974f8;cursor: pointer;">'+obj.ship_sn+'<a/></td>'+
				'<td>'+obj.abnormalNet+'</td>'+
				'<td style="color: #ff7801;">'+status+'</td>'+
				'<td style="color: #ff7801;">'+type+'</td>'+
				'<td>'+obj.shipNet+'</td>'+
				'<td>'+obj.fromAddr+'</td>'+
				'<td>'+obj.toAddr+'</td>'+
				'<td>'+obj.senderName+'</td>'+
				'<td>'+obj.receiverName+'</td>'+
				'<td>'+(obj.realname==null?"":obj.realname)+'</td>'+
				'<td class="banner-right-padding">'+obj.create_time+'</td>'+
				'<td class="banner-right-th1" onclick="openFollowUpDiv('+obj.abnormal_id+","+obj.abnormal_status+')"><span>异常跟进</span></td>'+
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
    			window.location.href="/kd/abnormal/exportAbnormallist?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
		
};

$(function(){
	new Abnormal();
	
})
})(jQuery);	


	
	


