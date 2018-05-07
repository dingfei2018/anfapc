//已到达
(function($){
function Arrive(){
	this.searchParam = "";
	this.init();
}
    Arrive.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
            $("#loadId").on("click",".banner-right-th1", $.proxy(function (e) {
                this.unLoading(e);
            }, this));
			this.search();
		},
        unLoading:function (e) {
            var $this = this;
            var tr = $(e.target).parents("tr");
            var loadId = $(tr).attr("lid");
            var number=$(tr).attr("number");
            var loadType=$(tr).attr("load-type");
            layer.open({
                type: 2,
                title: false,
                area: ['650px', '400px'],
                content: ['/kd/transport/unLoading?loadId='+loadId+'&loadType='+loadType, 'yes'],
				end:function(){
                    $this.search();
				}
            })
			$(".layui-layer-setwin").hide();
        },

		search:function(){
			var param = $("#searchForm").serialize();
			param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, 1, this);
		},
		getData:function(param, pageNo, obj){
			 $("#loadingId").mLoading("show");
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			console.log(param);
			$.ajax({
				type:'post',
				url:'/kd/transport/arriveList',
				data:encodeURI(param),
				success:function(data){
					console.log(data);
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
			var html='<tr class="tr_css" align="center" lid='+obj.load_id+' number='+obj.truck_id_number+'  load-type='+obj.load_transport_type+'>';
			html +='<td>'+index+'</td>';
            html +='<td>'+obj.load_sn+'</td>';
            html +='<td>'+obj.endNetWorkName+'</td>';
            html +=	'<td>'+obj.netWorkName+'</td>';
            if(obj.load_delivery_status==3){
            html +=	'<td style="color: #ff7801;">已到达</td>';
            }else{
                html +=	'<td style="color: #ff7801;">已完成</td>';
			}
			if(obj.load_transport_type==1){
            html +=	'<td>提货</td>';
            }else if(obj.load_transport_type==2){
                html +=	'<td>短驳</td>';
			}else if(obj.load_transport_type==3){
                html +=	'<td>干线</td>';
			}else{
                html +=	'<td>送货</td>';
			}
            html +=	'<td>'+new Date(obj.load_depart_time).format("yyyy-MM-dd hh:mm")+'</td>';
            html +=	'<td>'+obj.truck_id_number+'</td>';
            html +=	'<td>'+obj.truck_driver_name+'</td>';
            html +=	'<td>'+obj.truck_driver_mobile+'</td>';
            html +=	'<td>'+obj.fromAdd+'</td>';
            html +=	'<td>'+obj.toAdd+'</td>';
            html +=	'<td  class="banner-right-padding">'+new Date(obj.load_arrival_time).format("yyyy-MM-dd hh:mm")+'</td>';
            if(obj.load_delivery_status==3){
            html+= '<td class="banner-right-th1" ><span>完成卸车</span></td>';
			}else{
                html+= '<td ></td>';
			}
            html +=	'</tr>';
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
		}
};

$(function(){
	new Arrive();
	
})
})(jQuery);	


	
	


