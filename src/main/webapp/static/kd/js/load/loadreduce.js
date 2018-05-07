//装车配载
(function($){
	
function LoadingReduce(){
	this.selectIds = new Array();
	this.leftList = new Array();
	this.rightList = new Array();
	this.init();
}
LoadingReduce.prototype={
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
			
			$("#comfirmReduce").bind("click", $.proxy(function () {
				this.comfirmReduce();
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
			
		    this.getData(1, this);
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
		rmload:function(){
			var $this=this;
			var index = 0;
			var ln = $("#shiplistId tr:gt(0)").length;
			$("#addshiplistId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
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
					$("#shiplistId").append(html);
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
			this.sort("shiplistId");
			//this.getData(1, this);
		},
		load:function(){
			var $this=this;
			var index = 0;
			$("#shiplistId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
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
			//console.log(this.selectIds);
			this.getData(1, this);
			this.sort("shiplistId");
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
		getData:function(pageNo, obj){
			$("#addshiplistId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/loading/loadshiplist',
				data:{loadId:$("#addloadId").val(),pageNo:pageNo},
				success:function(data){
					var html="";
					var totalWeight=0;
					var totalAmount=0;
					var totalVolume=0;
					var index = 0;
					var temp;
					obj.rightList.splice(0, obj.rightList.length);
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
							obj.rightList.push(temp);
							totalVolume += parseFloat(data.list[i].ship_volume);
							totalWeight += parseFloat(data.list[i].ship_weight);
							totalAmount += parseFloat(data.list[i].ship_amount);
						}
						
				    } 
					$("#addshiplistId > tr").remove();
					$("#addshiplistId").append(html);
					$("#loadlistId").html("合计"+index+"单，"+totalAmount+"件，"+totalVolume+"方，"+totalAmount+"公斤");
					$(".selectAll").prop("checked", false);
					setTimeout(function(){$("#addshiplistId").mLoading("hide");}, 200);
				}
			});
		},
		comfirmReduce:function(){
			if(this.selectIds.length==0){
				layer.msg("请选择要减少的运单");
				return;
			}
			var $this = this;
			layer.confirm('您确定要减少运单么？', {
    		  	btn: ['确定','取消']
    		}, function(){
    			$.ajax({
    				type:'POST',
    				url:'/kd/loading/comfirmreduce',
    				data:{shipIds:$this.selectIds.join(),loadId:$("#addloadId").val()},
    				success:function(data){
    					if (data.success) {
    						layer.msg("修改成功");
    						setTimeout(function(){
								window.location.href="/kd/loading/loadlist";
							}, 1000);
    					} else if(data.type==3003){
    						layer.msg(data.msg);
    					}else {
    						layer.msg("修改失败");
    					}
    				}
    			});
    		}, function(){});	
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
	new LoadingReduce();
})
})(jQuery);	
	
	


