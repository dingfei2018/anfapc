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
                url:'/kd/customer/customerList',
                data:encodeURI(param),
                success:function(data){
                    var html="";
                    for(var i=0;i<data.list.length;i++){
                       // html += obj.appendHtml(data.list[i], i+1)
                    }
                    $("#table > tr").remove();
                    $("#table").append(html);
                    obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
                    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
                }
            });
        },
        appendHtml:function(obj, index){
            var html = "<tr>";
            html += "<td class=\"banner-right-pleft\"><input type=\"checkbox\" value=\""+obj.customer_id+"\"/></td>";
            html += "<td>"+index+"</td>";
            html += "<td>"+obj.customer_sn+"</td>";
            if(obj.customer_type==1){
            	html += "<td>"+"收货方"+"</td>";
            }else if(obj.customer_type==2){
            	html += "<td>"+"托运方"+"</td>";
            }else if(obj.customer_type==3){
            	html += "<td>"+"中转方"+"</td>";
            }
            
            html += "<td>"+obj.customer_corp_name+"</td>";
            html += "<td>"+obj.customer_name+"</td>";
            html += "<td>"+obj.customer_mobile+"</td>";
            html += "<td>"+obj.customer_address_id+"</td>";
            html += "<td><a class=\"banner-right-a1\" href=\"${ctx}/kd/customer/add?type=update&customerId="+obj.customer_id+"\">修改</a><a class=\"banner-right-a2\" href=\"javascript:deleteCustomer("+obj.customer_id+")\">删除</a></td>"

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
        new ship();
    })
})(jQuery);