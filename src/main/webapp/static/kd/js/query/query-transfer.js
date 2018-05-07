//按中转查询
(function($) {

	function querytransfer() {
		this.searchParam = "";
		this.init();
	}
	querytransfer.prototype = {
		init : function() {
			
			//查询
			$("#search").bind("click", $.proxy(function() {
				this.search();
			}, this));

			/*//中转方
			$('#transferName').combobox({
				url : '/kd/customer/queryCustomer?type=3&flag=true',
				valueField : 'customer_id',
				textField : 'customer_name',
				height : 30,
				panelWidth : 162,
				panelHeight : 'auto'
			});*/
			//中转方  
			$('#transferName').combogrid({ 
				url : '/kd/customer/searchCustomer?type=3',
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
		               $('#transferName').combogrid("grid").datagrid("reload", {'queryName': q});
		               $('#transferName').combogrid("setValue", q);
		            }
		        },
				fitColumns: true
			});
		
			this.search();
		},
		
		search : function() {
			var param = $("#searchFrom").serialize();
			param += "&pageNo=1";
			if (param)
				this.searchParam = param;
			this.getData(param, 1, this);
		},
		
		//分页查询
		getData : function(param, pageNo, obj) {
			if (param)
				param = param.substring(0, param.indexOf("pageNo="))
						+ "pageNo=" + pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type : 'GET',
				url : '/kd/query/transfer1',
				data : encodeURI(param),
				success : function(data) {
					var html = "";
					for (var i = 0; i < data.list.length; i++) {
						html += obj.appendHtml(data.list[i], i + 1)
					}
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.totalRow, data.totalPage,
							data.pageNumber, obj);
					setTimeout(function() {
						$("#loadingId").mLoading("hide");
					}, 200);
				}
			});
		},
		
		//循环添加列表数据
		appendHtml : function(obj, index) {
			var html = "<tr class=\"tr_css\" align=\"center\">";
		
			html += "<td>" + index + "</td>";
			html += "<td class='viewId' data='" + obj.ship_id
					+ "'  style='color: #3974f8;cursor: pointer;'>"
					+ obj.ship_transfer_sn + "</td>";
			html += "<td>" + obj.transferCorpName + "</td>";
			html += "<td>" + obj.transferName + "</td>";
			html += "<td>" + obj.transfermobile + "</td>";
			html += "<td>" + obj.ship_transfer_time + "</td>";
			html += "<td>" + obj.tranNetName + "</td>";
			html += "<td>" + obj.ship_sn + "</td>";
			html += "<td>" + obj.shipNetName + "</td>";
			html += "<td>" + obj.fromAdd + "</td>";
			html += "<td>" + obj.toAdd + "</td>";
			html += "<td>" + obj.senderName + "</td>";
			html += "<td class='float4'>" + obj.receiverName + "</td>";
			
			html += "<td class='float1' onclick='javascript:openDiv("+obj.ship_id+")'>" + '中转物流跟踪' + "</td></tr>";
			return html;
		},
		
		//分页
		page : function(param, totalRow, totalPage, pageNumber, cobj) {
			layui.use([ 'laypage' ], function() {
				var laypage = layui.laypage;
				laypage({
					cont : 'page',
					pages : totalPage // 得到总页数
					,
					curr : totalPage == 0 ? (pageNumber - 1) : pageNumber,
					skip : true // 是否开启跳页
					,
					count : totalRow,
					jump : function(obj, first) {
						if (!first) {
							cobj.getData(param, obj.curr, cobj);
						}
					},
					skin : '#1E9FFF'
				});
			});
		},
		
	
	};
	$(function() {
		new querytransfer();
	})
})(jQuery);
