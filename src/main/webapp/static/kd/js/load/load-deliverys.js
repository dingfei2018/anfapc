//装车配载
(function($){
	
function ShipLoading(){
	this.selectIds = new Array();
	this.searchParam = "";
	this.leftList = new Array();
	this.rightList = new Array();
	this.init();
}
ShipLoading.prototype={
		init:function(){
			$(".selectAll").bind("click", this.selectAll);
			
			$("#loadId").bind("click", $.proxy(function (e) {
				this.load();
            }, this));
			$("#rmloadId").bind("click", $.proxy(function (e) {
				this.rmload();
            }, this));
			
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			
			$(".banner-right-list2li input").bind("change", this.feechange);
			
			$("#network_id").bind("change", $.proxy(function () {
				this.networkchange();
            }, this));
			
			$("#load_next_network_id").bind("change", $.proxy(function () {
				this.loadnetworkchange();
            }, this));
			
			$("#fahuo").bind("click", $.proxy(function () {
				this.fahuo();
            }, this));
			
			$(".shipfilter").bind("keyup",  $.proxy(function (e) {
				if(event.keyCode ==13){
					this.enter(e);
				}else{
				  this.filter(e);
				}
            }, this));
			
			$("#shiplistId").on("dblclick", ".tr_css",$.proxy(function (e) {
				this.leftdbclick(e);
            }, this));
			
			$("#addshiplistId").on("dblclick", ".tr_css",$.proxy(function (e) {
				this.rightdbclick(e);
            }, this));
			
		    $("#truckId").combogrid({
		    	panelWidth: 320,
				idField: 'truck_id_number',
				height:30,
				pagination: true,
				textField: 'truck_id_number',
				url: '/kd/truck/searchTruck',
				method: 'get',
				columns: [[
					{field:'truck_id_number',title:'车牌号',width:200},
					{field:'truck_driver_name',title:'司机名',width:150},
					{field:'truck_driver_mobile',title:'司机电话',width:200}
				]],
				keyHandler:{
		            up: function() {},
		            down: function() {},
		            enter: function() {},
		            query: function(q) {
		                //动态搜索
		               $('#truck_id_number').combogrid("grid").datagrid("reload", {'queryName': q});
		               $('#truck_id_number').combogrid("setValue", q);
		            }
		        },
		        onSelect:function(rowIndex, rowData){
		        	$("#truck_driver_mobile").val(rowData.truck_driver_mobile);
		        	$("#truck_driver_name").val(rowData.truck_driver_name);
		        	$("#truck_id_number").val(rowData.truck_id_number);
		        },
				fitColumns: true
		    });
		    
		    $("#receiveName").combogrid({
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
		            $("#receiveNameId").val(rowData.customer_id);
		        },
		        fitColumns: true
		    });
		    
		    $("#network_id").find("option:eq(1)").attr("selected", true);
		    this.networkchange();
		    var ids = $("#load_next_network_id option").map(function(){return $(this).val();}).get().join(", ");
		    ids = ids.replace(/0, /, "");
		    this.toAddrCombobox(2, ids, this);
		    
		    this.search();
		},
		networkchange:function(){
			this.toAddrCombobox(1, $("#network_id").val(), this);
		},
		toAddrCombobox: function (index, netWorkId, obj){//到达地城市combobox
	    	  $('#toAddr'+index).combobox({
	              url: '/kd/line/getCityByNetWorkId?netWorkId=' + netWorkId,
	              valueField: 'from_city_code',
	              textField: 'fromAdd',
	              width: 75,
	              height:35,
	              onSelect: function (row) {
	                  var code = typeof(row.from_city_code)=='undefined'?"":row.from_city_code;
	                  if(index==1){
	                	  $('#load_delivery_from').val(code);
	                  }else{
	                	  $('#load_delivery_to').val(code);
	                  }
	                  obj.toAddrCountyCombobox(index, code);
	              },
	              onLoadSuccess: function () {  //加载完成后,设置选中第一项
	            	  if(typeof($(this).combobox("getData")[0])!='undefined'){
	            		  var data = $(this).combobox("getData")[0];
	                      var code=typeof(data.from_city_code)=='undefined'?"":data.from_city_code;
	                      if(index==1){
		                	  $('#load_delivery_from').val(code);
		                  }else{
		                	  $('#load_delivery_to').val(code);
		                  }
	                      $(this).combobox('setValue', code);
	                      obj.toAddrCountyCombobox(index, code);
	            	  }else{
	            		  obj.toAddrCountyCombobox(index,"");
	            	  }
	              }
	          })
	          
	          $('#toAddr'+index).combobox('textbox').bind('focus',function(){
	     		 $('#toAddr'+index).combobox('showPanel');
	     	 });
	    	
	    },
	    toAddrCountyCombobox: function (index, code){//到达地区级combobox
	    	  $('#toAddrCounty'+index).combobox({
	              url: '/kd/waybill/getCountyJson?code=' + code,
	              valueField: 'region_code',
	              textField: 'region_name',
	              width: 75,
	              height:35,
	              onSelect: function (row) {
	                  var code = typeof(row.region_code)=='undefined'?"":row.region_code;
	                  if(index==1){
	                	  $('#load_delivery_from').val(code);
	                  }else{
	                	  $('#load_delivery_to').val(code);
	                  }
	              },
	              onLoadSuccess: function () {  //加载完成后,设置选中第一项
            		  var data = $(this).combobox("getData");
            		  var fcode="";
            		  for(var i=0; i<data.length; i++){
            			 if(code==data[i].region_code){
            				 fcode =  data[i].region_code;
            				 break;
            			 } 
            		  }
            		  if(fcode==""&&data.length>0){
            			  fcode = data[0].region_code;
            		  }
                      if(index==1){
	                	  $('#load_delivery_from').val(fcode);
	                  }else{
	                	  $('#load_delivery_to').val(fcode);
	                  }
                      $(this).combobox('setValue', fcode);
	              }
	          })
	          $('#toAddrCounty'+index).combobox('textbox').bind('focus',function(){
	      		 $('#toAddrCounty'+index).combobox('showPanel');
	      	 });
	    },
		selectAll:function(){
			if($(this).is(':checked')){
				$(this).parents(".tab_css_1").find("input[type='checkbox']").prop("checked", true);
			}else{
				$(this).parents(".tab_css_1").find("input[type='checkbox']").prop("checked", false);
			}
		},
		leftdbclick:function(e){
			$(e.target).parent("tr").find("input[type='checkbox']").prop("checked", true);
			this.load();
		},
		rightdbclick:function(e){
			$(e.target).parent("tr").find("input[type='checkbox']").prop("checked", true);
			this.rmload();
		},
		sort:function(id){
			var html = "";
			$("#"+id+" tr:gt(0)").each(function(i,o){
				$(o).find("td:eq(1)").remove();
				$(o).find("td:eq(0)").after("<td>"+(i+1)+"</td>");
				html += $(o).prop("outerHTML");
			});
			$("#"+id+" tr:gt(0)").remove();
			$("#"+id).append(html);
		},
		load:function(){
			var $this=this;
			var index = 0;
			var ln = $("#addshiplistId tr:gt(0)").length;
			$("#shiplistId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
				var tr = $(o).parents("tr");
				var id = $(tr).attr("data");
				var res = $this.addShip(id, $this);
				if(res){
					$(tr).find("td:eq(1)").remove();
					$(tr).find("td:eq(0)").after("<td>"+(ln+i+1)+"</td>");
					var html = $(tr).prop("outerHTML");
					$this.rightList.push(html);
					$this.removeLeftShip(id, $this);
					var id = $(tr).attr("data");
					$("#addshiplistId").append(html);
				}
				$(tr).remove();
				index++;
			})
			if(index==0){
				layer.msg("请选择要配载的运单");
				return;
			}
			$this.compute($this, "shiplistId");
			$this.compute($this, "addshiplistId");
			$(".selectAll").prop("checked", false);
			this.sort("addshiplistId");
		},
		rmload:function(){
			var $this=this;
			var index = 0;
			$("#addshiplistId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
				var tr = $(o).parents("tr");
				var id = $(tr).attr("data");
				$this.removeShip(id,$this);
				$this.removeRightShip(id, $this);
				$(tr).remove();
				index++;
			})
			if(index==0){
				layer.msg("请选择要删除的运单");
				return;
			}
			$this.compute($this, "shiplistId");
			$this.compute($this, "addshiplistId");
			$(".selectAll").prop("checked", false);
			this.getData(this.searchParam, 1, this);
			this.sort("addshiplistId");
		},
		search:function(){
			var param ="type=4&endCode="+$("#endCode").val()+"&receiveName="+$("#receiveNameId").val()+"&pageNo=1";
			this.getData(param, 1, this);
		},
		feechange:function(){
			var total = 0;
			$(".banner-right-list2li input").each(function(i,o){
				if(i>6){
					return false;
				}
				var value = $(o).val();
				if(value!=""){
					value = parseFloat(value);
					total +=value;
				}
			})
			$("#totalId").val(total);
		},
		filter:function(e){
			var $this=this;
			var value = $(e.target).val();
			var shipSn="";
			if($(e.target).attr("types")=="1"){
				$("#shiplistId > tr").remove();
				for(var i=0;i<$this.leftList.length;i++){
					shipSn = $($this.leftList[i]).attr("shipSn");
					if(value==""){
						$("#shiplistId").append($this.leftList[i]);
					}else{
						if(shipSn.indexOf(value)!=-1){
							$("#shiplistId").append($this.leftList[i]);
						}
					}
				}
				$this.compute($this, "shiplistId");
			}else{
				$("#addshiplistId > tr").remove();
				for(var i=0;i<$this.rightList.length;i++){
					shipSn = $($this.rightList[i]).attr("shipSn");
					if(value==""){
						$("#addshiplistId").append($this.rightList[i]);
					}else{
						if(shipSn.indexOf(value)!=-1){
							$("#addshiplistId").append($this.rightList[i]);
						}
					}
				}
				$this.compute($this, "addshiplistId");
			}
		},
		enter:function(e){
			var $this=this;
			$(e.target).val("");
			if($(e.target).attr("types")=="1"){
				var size = $("#shiplistId tr:gt(0)").length;
				if(size==1){
					$("#shiplistId tr:gt(0)").find("input[type='checkbox']").prop("checked", true);
					$this.load();
				}
				$("#shiplistId > tr").remove();
				for(var i=0;i<$this.leftList.length;i++){
					$("#shiplistId").append($this.leftList[i]);
				}
				$this.compute($this, "shiplistId");
			}else{
				var size = $("#addshiplistId tr:gt(0)").length;
				if(size==1){
					$("#addshiplistId tr:gt(0)").find("input[type='checkbox']").prop("checked", true);
					$this.rmload();
				}
				$("#addshiplistId > tr").remove();
				for(var i=0;i<$this.rightList.length;i++){
					$("#addshiplistId").append($this.rightList[i]);
				}
				$this.compute($this, "addshiplistId");
			}
		},
		compute:function(obj, id){
			var totalVolume=0;
			var totalWeight=0;
			var totalAmount=0;
			var index = 0;
			$("#"+id).find("tr:gt(0)").each(function(i,o){
				totalVolume += parseFloat($(o).attr("volume"));
				totalWeight += parseFloat($(o).attr("weight"));
				totalAmount += parseFloat($(o).attr("amount"));
				index++;
			});
			$("#"+id).parent("div").next("p").html("合计"+index+"单，"+totalAmount+"件，"+totalVolume+"方，"+totalAmount+"公斤");
		},
		getData:function(param, pageNo, obj){
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#shiplistId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/loading/search',
				data:encodeURI(param),
				success:function(data){
					var html="";
					var totalWeight=0;
					var totalAmount=0;
					var totalVolume=0;
					var index = 0;
					var temp;
					obj.leftList.splice(0, obj.leftList.length);
					for(var i=0;i<data.list.length;i++){
						var f = true;
						for (var j = 0; j < obj.selectIds.length; j++) {
							if(obj.selectIds[j]==data.list[i].ship_id){
								f = false;
							}
						}
						if(f){
							index++;
							temp = obj.appendHtml(data.list[i], index);
							html += temp;
							obj.leftList.push(temp);
							totalVolume += parseFloat(data.list[i].ship_volume);
							totalWeight += parseFloat(data.list[i].ship_weight);
							totalAmount += parseFloat(data.list[i].ship_amount);
						}
				    } 
					$("#shiplistId > tr").remove();
					$("#shiplistId").append(html);
					$("#querylistId").html("合计"+index+"单，"+totalAmount+"件，"+totalVolume+"方，"+totalAmount+"公斤");
					$(".selectAll").prop("checked", false);
					setTimeout(function(){$("#shiplistId").mLoading("hide");}, 200);
				}
			});
		},
		fahuo:function(){
			if(this.selectIds.length==0){
				layer.msg("请选择要配载的运单");
				return;
			}
			if(!this.validateData()){
				return;
			}
			var sdata = $(".banner-right-list,.banner-right-list2li").find("input,select,textarea").serialize();
			sdata +="&load_transport_type=4&load_next_network_id=0&ids="+this.selectIds.join();
			$.ajax({
				type:'POST',
				url:'/kd/loading/up',
				data:sdata,
				success:function(data){
					if (data.success) {
						layer.msg("配载成功");
						setTimeout(function(){window.location.reload(true);}, 1000);
					} else {
						layer.msg(data.msg);
					}
				}
			});
		},
		validateData:function(){
			if($("#network_id").val()==0){
				Anfa.show("请选择配载网点","#network_id");
				return false;
			}
			
			if($("#loadfeeId").val()==""){
				Anfa.show("请填写提货费","#loadfeeId");
				return false;
			}
			
			if($("#truck_id_number").val()==""){
				Anfa.show("请填写车牌号信息",".textbox-prompt:eq(0)");
				return false;
			}
			
			if($("#truck_driver_name").val()==""){
				Anfa.show("请填写司机姓名","#truck_driver_name");
				return false;
			}
			if($("#truck_driver_mobile").val()==""){
				Anfa.show("请填写司机电话","#truck_driver_mobile");
				return false;
			}else if(!(/^1[345678]\d{9}$/.test($("#truck_driver_mobile").val()))){
			    Anfa.show("请填写正确司机电话","#truck_driver_mobile");
			    return false;
		    }
			
			if($("#load_delivery_from").val()==""){
				Anfa.show("请填选择出发地",".textbox-prompt:eq(1)");
				return false;
			}
			
			if($("#load_delivery_to").val()==""){
				Anfa.show("请填选择到达地",".textbox-prompt:eq(2)");
				return false;
			}
			return true;
		},
		appendHtml:function(obj, index){
			var html = "<tr class=\"tr_css\" align=\"center\" data='"+obj.ship_id+"' volume='"+obj.ship_volume+"' weight='"+obj.ship_weight+"' amount='"+obj.ship_amount+"' shipSn='"+obj.ship_sn+"'>";
			html += "<td><input type=\"checkbox\"/></td>";
			html += "<td>"+index+"</td>";
			html += "<td>"+obj.ship_sn+"</td>";
			html += "<td>"+obj.enetworkName+"</td>";
			html += "<td>"+obj.toCity+"</td>";
			html += "<td>"+obj.proname+"</td>";
			html += "<td>"+obj.ship_volume+"</td>";
			html += "<td>"+obj.ship_weight+"</td>";
			html += "<td>"+obj.ship_amount+"</td>";
			html += "<td>"+obj.fromCity+"</td>";
			html += "<td>"+obj.snetworkName+"</td></tr>";
			return html;
		},
		addShip:function(id, obj){
			var flag = true;
			for(var i=0; i<obj.selectIds.length;i++){
				if(obj.selectIds[i]==id){
					flag = false;
					break;
				}
			}
			if(flag)obj.selectIds.push(id);
			return flag;
		},
		removeShip:function(id, obj){
			var index = -1;
			for(var i=0; i<obj.selectIds.length;i++){
				if(obj.selectIds[i]==id){
					index = i;
					break;
				}
			}
			if(index!=-1)obj.selectIds.splice(index, 1);
		},
		removeLeftShip:function(id, obj){
			var index = -1;
			for(var i=0; i<obj.leftList.length;i++){
				if($(obj.leftList[i]).attr("data")==id){
					index = i;
					break;
				}
			}
			if(index!=-1)obj.leftList.splice(index, 1);
		},
		removeRightShip:function(id, obj){
			var index = -1;
			for(var i=0; i<obj.rightList.length;i++){
				if($(obj.rightList[i]).attr("data")==id){
					index = i;
					break;
				}
			}
			if(index!=-1)obj.rightList.splice(index, 1);
		}
		
};
$(function(){
	new ShipLoading();
})
})(jQuery);	
	
	


