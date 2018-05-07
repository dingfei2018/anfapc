//操作查询运单查询
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
            $(".tab_css_1").on("click", ".viewId",this.openShipView);
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
                url:'/kd/query/shipList',
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
            html += "<td><input type=\"checkbox\" value=\""+obj.ship_id+"\"/></td>";
            html += "<td>"+index+"</td>";
            html += "<td class=\"float5\" onclick=\"javascript:openDiv("+obj.ship_id+")\">"+obj.ship_sn+"</td>";
            html += "<td>"+obj.netName+"</td>";
            html += "<td><fmt:formatDate value=\""+obj.create_time+"\" pattern=\"yyyy-MM-dd HH:mm:ss\"/>"+obj.create_time+"</td>";
            if(obj.ship_customer_number!=null){
            	html += "<td>"+obj.ship_customer_number+"</td>";
            }else{
            	html += "<td>"+"-"+"</td>";
            }
            
            html += "<td>"+obj.fromAdd+"</td>";
            html += "<td>"+obj.toAdd+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            html += "<td>"+obj.ship_volume+"</td>";
            html += "<td>"+obj.ship_weight+"</td>";
            html += "<td>"+obj.ship_amount+"</td>";
            if(obj.ship_status==1){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">已入库</td>"
            } else if(obj.ship_status==2){
                html += "<td class=\"float4\" style=\"color: #ff7801;\">短驳中</td>"
            } else if(obj.ship_status==3){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">短驳到达</td>"
            } else if(obj.ship_status==4){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">已发车</td>"
            } else if(obj.ship_status==5){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">已到达</td>"
            } else if(obj.ship_status==6){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">收货中转中</td>"
            } else if(obj.ship_status==7){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">到货中转中</td>"
            } else if(obj.ship_status==8){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">送货中</td>"
            } else if(obj.ship_status==9){
                html += "<td  class=\"float4\" style=\"color: #ff7801;\">已签收</td>"
            }



            html += "<td class=\"banner-right-th\"><p class=\"float1\"  onclick=\"openTransferAndStowage("+obj.ship_id+")\">配载中转</p>	<div class=\"banner-right-list2-tab2\">	"
                + "<dl><dd class=\"float3\" onclick=\"openTrackDiv("+obj.ship_id+")\" >物流跟踪</dd></dl>"
                + "<dl><dd class=\"float3\" onclick=\"openLogDiv("+obj.ship_id+")\" >操作日志</dd></dl>"
                + "<dl><dd class=\"float3\" onclick=\"openview("+obj.ship_id+")\" >签收图片</dd></dl></td>"

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
        openShipView:function(){
            var shipId = $(this).attr("data");
            //页面层
            layer.open({
                type: 2,
                area: ['850px', '700px'], //宽高
                content: ['/kd/waybill/viewDetail?shipId='+shipId, 'yes']
            });
        }
    };
    $(function(){
        new ship();
    })
})(jQuery);