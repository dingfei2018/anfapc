//货款到账
(function($){

    function Hkdz(){
        this.selectIds = new Array();
        this.searchParam = "";
        this.leftList = new Array();
        this.rightList = new Array();
        this.init();
    }
    Hkdz.prototype={
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

            $("#daozhang").bind("click", $.proxy(function () {
                this.daozhang();
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
                layer.msg("请选择要操作的运单");
                return;
            }
            $this.compute($this, "shiplistId");
            $this.compute($this, "addshiplistId");
            $(".selectAll").prop("checked", false);
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
                layer.msg("请选择要移除的运单");
                return;
            }
            $this.compute($this, "shiplistId");
            $this.compute($this, "addshiplistId");
            $(".selectAll").prop("checked", false);
            this.getData(this.searchParam, 1, this);
        },
        search:function(){
            var param ="netWorkId="+$("#netWorkId").val()+"&senderId="+$("input[name='senderId']").val()+"&receiverId="+$("input[name='receiverId']").val()+"&pageNo=1";
            this.getData(param, 1, this);
        },
        filter:function(e){
            var $this=this;
            var value = $(e.target).val();
            var shipSn="";
            if($(e.target).attr("types")=="1"){
                $("#shiplistId tr:gt(0)").remove();
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
                $("#addshiplistId tr:gt(0)").remove();
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
            var total=0;
            var index = 0;
            $("#"+id).find("tr:gt(0)").each(function(i,o){
                total += parseFloat($(o).attr("totalFee"));
                index++;
            });
            $("#"+id).parent("div").next("p").html("合计"+index+"单，代收货款"+total+"元");
        },

        getData:function(param, pageNo, obj){
            if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
            $("#shiplistId").mLoading("show");
            $.ajax({
                type:'GET',
                url:'/kd/finance/collect/getmoneyAccountList',
                data:encodeURI(param),
                success:function(data){
                    var html="";
                    var total=0;
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
                            total += parseFloat(data.list[i].ship_agency_fund);
                        }
                    }
                    $("#shiplistId tr:gt(0)").remove();
                    $("#shiplistId").append(html);
                    $("#querylistId").html("合计"+index+"单，代收货款"+total+"元");
                    $(".selectAll").prop("checked", false);
                    setTimeout(function(){$("#shiplistId").mLoading("hide");}, 200);
                }
            });
        },
        daozhang:function(){
            var $this = this;
            var feeTotal=0;
            if(this.selectIds.length==0){
                layer.msg("请选择要操作的运单");
                return;
            }
            if(!this.validateData()){
                return;
            }
            $("#addshiplistId").find("tr:gt(0)").each(function(i,o){
                feeTotal += parseFloat($(o).attr("totalFee"));

            });
            var mess = "<p>确认"+feeTotal+"元货款已到账</p>";
            layer.confirm(mess, {
                    title: '确认货款已到账',
                    btn: ['确定','取消']
                }, function(){
            $.ajax({
                type:'POST',
                url:'/kd/finance/collect/saveShipFlow?type=4&ids='+$this.selectIds.join(),
                data:$("#confirmform").serialize(),
                success:function(data){
                    if (data.success) {
                        layer.msg("确认货款到账成功");
                        setTimeout(function(){window.location.reload(true);}, 1000);
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
            }, function(){});
        },
        validateData:function(){
            if($('#payType option:selected').val()==""){
               layer.msg("请选择收支方式");
                return false;
            }
            return true;
        },
        appendHtml:function(obj, index){
            var html = "<tr class=\"tr_css\" align=\"center\" data='"+obj.ship_id+"' totalFee='"+obj.ship_agency_fund+"'  shipSn='"+obj.ship_sn+"'>";
            html += "<td><input type=\"checkbox\"/></td>";
            html += "<td>"+index+"</td>";
            html += "<td>"+obj.ship_sn+"</td>";
            html += "<td>"+obj.netWrokName+"</td>";
            html += "<td>"+obj.endWorkName+"</td>";
            html += "<td>"+obj.ship_agency_fund+"</td>";
            html += "<td>"+obj.senderName+"</td>";
            html += "<td>"+obj.receiverName+"</td>";
            html += "<td>"+obj.goods_sn+"</td>";
            html += "<td>"+obj.create_time+"</td></tr>";
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
        new Hkdz();
    })
})(jQuery);




