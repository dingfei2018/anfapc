//即将到货
(function ($) {
    function Soon() {
        this.searchParam = "";
        this.init();
    }
    Soon.prototype = {
        init: function () {
            $("#search").bind("click", $.proxy(function () {
                this.search();
            }, this));
            $("#loadId").on("click",".banner-right-th1", $.proxy(function (e) {
                this.confirmArrive(e);
            }, this));

            this.search();
        },
        confirmArrive:function(e){
            var $this = this;
            var tr = $(e.target).parents("tr");
            var loadId = $(tr).attr("lid");
            var sn=$(tr).attr("data-sn");
            var loadType=$(tr).attr("data-type");
            console.log(loadId);
            console.log(sn);
            var mess = "<p>确认<span>"+sn+"</span>已到达？该车配载的运单将进入库存中</p>";
            layer.confirm(mess, {
                title: '确认到达',
                btn: ['确定','取消']
            }, function(){
                $.ajax({
                    url : "/kd/transport/confirmArrive",
                    type : 'POST',
                    data : {loadId:loadId,loadType:loadType},
                    success:function(data){
                        if (data.success) {
                            layer.msg("确认成功");
                            setTimeout(function(){$this.search();}, 2000);
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }, function(){});

        },

        search: function () {
            var param = $("#searchForm").serialize();
            param += "&pageNo=1";
            if (param) this.searchParam = param;
            this.getData(param, 1, this);
        },
        getData: function (param, pageNo, obj) {
            $("#loadingId").mLoading("show");
            if (param) param = param.substring(0, param.indexOf("pageNo=")) + "pageNo=" + pageNo;
            console.log(param);
            $.ajax({
                type: 'post',
                url: '/kd/transport/soonList',
                data: encodeURI(param),
                success: function (data) {
                    console.log(data);
                    var html = "";
                    for (var i = 0; i < data.list.length; i++) {
                        html += obj.appendHtml(data.list[i], i + 1)
                    }
                    $("#loadId > tr").remove();
                    $("#loadId").append(html);
                    obj.page(param, data.totalRow, data.totalPage, data.pageNumber, obj);
                    setTimeout(function () {
                        $("#loadingId").mLoading("hide");
                    }, 200);
                }
            });
        },
        appendHtml: function (obj, index) {
            var type;
            if(obj.load_transport_type==1){
                type="提货";
            }else if(obj.load_transport_type==2){
                type="短驳";
            }else if(obj.load_transport_type==3){
                type="干线";
            }else{
                type="送货";
            }

            var html = '<tr class="tr_css" align="center" data-sn='+obj.truck_id_number+' lid='+obj.load_id+' data-type='+obj.load_transport_type+'>' +
                '<td>' + index + '</td>' +
                '<td>' + obj.load_sn + '</td>' +
                '<td>' + obj.netWorkName + '</td>' +
                '<td>' + obj.endWorkName + '</td>' +
                '<td>' + type + '</td>' +
                '<td>' + obj.load_fee_prepay + '</td>' +
                '<td>' + new Date(obj.load_depart_time).format("yyyy-MM-dd hh:mm") + '</td>' +
                '<td>' + obj.truck_id_number + '</td>' +
                '<td>' + obj.truck_driver_name + '</td>' +
                '<td>' + obj.truck_driver_mobile + '</td>' +
                '<td>' + obj.fromAdd + '</td>' +
                '<td>' + obj.toAdd + '</td>' +
                '<td>' + obj.load_volume + '</td>' +
                '<td>' + obj.load_weight + '</td>' +
                '<td >' + obj.load_amount + '</td>' +
                '<td class="banner-right-th1" ><span>确认到达</span></td>' +
                '</tr>';
            return html;
        },
        page: function (param, totalRow, totalPage, pageNumber, cobj) {
            layui.use(['laypage'], function () {
                var laypage = layui.laypage;
                laypage({
                    cont: 'page'
                    , pages: totalPage //得到总页数
                    , curr: totalPage == 0 ? (pageNumber - 1) : pageNumber
                    , skip: true //是否开启跳页
                    , count: totalRow
                    , jump: function (obj, first) {
                        if (!first) {
                            cobj.getData(param, obj.curr, cobj);
                        }
                    }
                    , skin: '#1E9FFF'
                });
            });
        }
    };

    $(function () {
        new Soon();

    })
})(jQuery);	


	
	


