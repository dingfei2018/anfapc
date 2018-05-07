//库存查询
(function($){
	
function ship(){
	this.searchParam = "";
	this.init();
}
ship.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			
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
				url:'/kd/query/loadList',
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
			var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td>"+index+"</td>";
			html += "<td class=\"float5\" onclick=\"javascript:openDiv("+obj.load_id+")\">"+obj.load_sn+"</td>";
			html += "<td>"+obj.loadnetName+"</td>";
			html += "<td>"+this.FormatDate(obj.load_depart_time)+"</td>";
			html += "<td>"+obj.truck_id_number+"</td>";
			html += "<td>"+obj.truck_driver_name+"</td>";
			html += "<td>"+obj.truck_driver_mobile+"</td>";
			if(obj.load_transport_type==1){
				html += "<td>提货</td>"
			}
			if(obj.load_transport_type==2){
				html += "<td>短驳</td>"
			}if(obj.load_transport_type==3){
				html += "<td>干线</td>"
			}if(obj.load_transport_type==4){
				html += "<td>送货</td>"
			}
			html += "<td>"+obj.fromAdd+"</td>";
			html += "<td>"+obj.toAdd+"</td>";
			html += "<td>"+obj.load_volume+"</td>";
			html += "<td>"+obj.load_weight+"</td>";
			html += "<td>"+obj.networkName+"</td>";
			
			if(obj.load_delivery_status==1){
				html += "<td class=\"banner-right-padding\" style=\"color: #ff7801;\">在途</td>"
			}if(obj.load_delivery_status==2){
				html += "<td class=\"banner-right-padding\" style=\"color: #ff7801;\">到站</td>"
			}
			
			html += "<td class=\"banner-right-th\"><p class=\"float1\" onclick=\"openDiv("+obj.load_id+")\">配载详情</p>"
				 
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
		FormatDate:function(strTime) {
		    var date = new Date(strTime);
		    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		}
};
$(function(){
	new ship();
})
})(jQuery);	
	
	


