//运单列表
(function($){
function Ship(){
	this.searchParam = "";
	this.init();
}
Ship.prototype={
		init:function(){
			$("#selectAll").bind("click", this.selectAll);
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
            $(".tab_css_1").on("click", ".viewId",this.openDetail);
			$("#excelExport").bind("click", $.proxy(function () {
				this.excelExport();
            }, this));

            $("#add").bind("click", $.proxy(function () {
                this.openAdd();
            }, this));
            $("#loadId").on("click", ".up", $.proxy(function (e) {
                this.openUp(e);
            }, this));
            $("#loadId").on("click", ".del", $.proxy(function (e) {
                this.delShip(e);
            }, this));
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
		selectAll:function(){
			if($(this).is(':checked')){
				$(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', true)
		    }else{ 
		    	$(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', false)
		    } 
		},
    openAdd:function(){
        var $this = this;
        //页面层
        layer.open({
            title: false,
            type: 2,
			closeBtn:0,
            area: ['1200px', '720px'],
            content: ['/kd/waybill/addShip','no'],
            end:function () {
                $this.search();
            }
        });
	},

    openUp:function(e){
        var $this = this;
        var tr = $(e.target).parents("tr");
        var counts = $(tr).attr("loadcount");
        if(counts>0){
            layer.msg("已配载的运单不能修改");
            return ;
        }
        var shipId = $(tr).attr("lid");
        //页面层
        layer.open({
            title: '修改运单',
            type: 2,
            area: ['1200px', '720px'],
            content: ['/kd/waybill/updateShip?shipId='+shipId+'&type=update','no'],
            end:function (){
               $this.search();
            }
        });

    },

    delShip:function(e){
        var tr = $(e.target).parents("tr");
        var counts = $(tr).attr("loadcount");
        var shipSn = $(tr).attr("shipsn");
        var shipId = $(tr).attr("lid");
        if(counts>0){
            layer.msg("已配载的运单不能删除");
            return ;
        }

        layer.confirm(
            "<div style='width:300px'><span>删除原因:</span><textarea id='deleteContent' style='width: 200px;height: 150px'></textarea></div>",
            {
                btn : [ '确定', '取消' ]
            },
            function () {
                $.ajax({
                    type : "post",
                    dataType : "json",
                    data:{shipId:shipId,deleteContent:$('#deleteContent').val()},
                    url : "/kd/waybill/delShip",
                    success : function(data) {
                        if (data.success) {
                            layer.msg("删除成功");
                            setTimeout(function(){window.location.reload(true);}, 1000);
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }, function() {

            });

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
				url:'/kd/waybill/search',
				data:encodeURI(param),
				success:function(data){
					var html="";
					for(var i=0;i<data.list.length;i++){  
						html += obj.appendHtml(data.list[i], i+1+((pageNo-1)*data.pageSize));
				    } 
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},
		appendHtml:function(obj, index){
			var html = "<tr class=\"tr_css\" align=\"center\" lid='"+obj.ship_id+"' shipsn='"+obj.ship_sn+"'  status='"+obj.ship_status+"' loadcount='"+obj.ship_load_times+"'>";
			html += "<td>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.ship_id+"' style='color: #3974f8;cursor: pointer;'>"+obj.ship_sn+"</td>";
			html += "<td>"+obj.netWorkName+"</td>";
			html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd hh:mm")+"</td>";
            html += "<td>"+((obj.ship_customer_number==null)?"":obj.ship_customer_number)+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
			if(obj.ship_status==1){
				html += "<td style=\"color: #ff7801;\">已入库</td>";
			}else if(obj.ship_status==2){
				html += "<td style=\"color: #ff7801;\">短驳中</td>";
            }else if (obj.ship_status==3){
                html += "<td style=\"color: #ff7801;\">短驳到达</td>";
			}else if(obj.ship_status==4){
                html += "<td style=\"color: #ff7801;\">已发车</td>";
			}else if(obj.ship_status==5){
                html += "<td style=\"color: #ff7801;\">已到达</td>";
			}else if(obj.ship_status==6){
                html += "<td style=\"color: #ff7801;\">收货中转中</td>";
			}else if(obj.ship_status==7){
                html += "<td style=\"color: #ff7801;\">到货中转中</td>";
			}else if(obj.ship_status==8){
                html += "<td style=\"color: #ff7801;\">送货中</td>";
			}else {
                html += "<td style=\"color: #ff7801;\">已签收</td>";
			}
			html += "<td style=\"color: #ff7801;\">"+obj.ship_volume+"</td>";
			html += "<td>"+obj.ship_weight+"</td>";
			html += "<td class=\"banner-right-padding\">"+obj.ship_amount+"</td>";
			html += "<td class=\"banner-right-th\"><a class=\"up\" id=\"update\" href=\"javascript:void(0)\">修改</a>";
			html += "<div class=\"banner-right-list2-tab2\"> <dl class=\"del\"><dd>删除运单</dd></dl>";
			html+="<dd class=\"input2\" onclick=\"printWaybill("+obj.ship_id+")\">打印运单</dd>";
			html+=" <dd onclick=\"window.open('/kd/print/wayBarPrintIndex?shipId="+obj.ship_id+"')\">打印标签</dd>";
			html+=" </div></td></tr>";
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


    openDetail:function(){
        var shipId = $(this).attr("data");
		window.location.href="/kd/waybill/viewDetail?shipId="+shipId;
    },
		excelExport:function(){
			var obj = this;
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/waybill/downLoad?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		}
};

$(function(){
	 new Ship();
})
})(jQuery);




