//专线列表
(function($){

    function line(){
        this.searchParam = "";
        this.init();
    }
    line.prototype={
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
                url:'/kd/line/search',
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
        	var html = "<tr>";
            html += "<td><input type=\"checkbox\"  value='"+obj.id+"'></td>";
            html += "<td>"+index+"</td>";
            html += "<td><a class=\"banner-right-a4\" onclick=\"openDiv("+obj.id+")\">"+obj.fromAdd+"→"+obj.toAdd+"</a></td>";
            html += "<td>"+obj.networkName+"</td>";
            html += "<td>"+obj.arriveNetworkName+"</td>";
            html += "<td>"+obj.ParkName+"</td>";
            html += "<td>"+obj.price_heavy+"</td>";
            html += "<td>"+obj.price_small+"</td>";
            html += "<td>"+obj.starting_price+"</td>";

            html += "<td><a class=\"banner-right-a1\" href=\"#\" onclick=\"goUpdate("+obj.id+")\">修改</a>" +
            		"<a class=\"banner-right-a2\" href=\"javascript:void(0)\" onclick=\"deleteLine('"+obj.id+"','"+obj.networkName+"','"+obj.fromAdd+"','"+obj.toAdd+"')\">删除</a></td></tr>";
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
    };
    $(function(){
        new line();
    })
})(jQuery);