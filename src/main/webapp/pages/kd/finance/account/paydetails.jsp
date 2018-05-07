<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>对账应收</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/receivable.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>

	<script src="${ctx}/static/common/js/jquery.js"></script>
	<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
	<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	<script src="${ctx}/static/pc/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
	<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
</head>

<body>
<%@ include file="../../common/head2.jsp" %>
<%@ include file="../../common/head.jsp" %>
<div class="banner">
	<%@ include file="../../common/financialleft.jsp" %>
	<script type="text/javascript">
        $(function(){
            var _width=$("body").width();
            var _widths = $(".banner-left").width();
            var _widthd = _width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width',_widthd+'px');
        });
        $(window).resize(function(){
            var Width = $(window).width();
            var _widths = $(".banner-left").width();
            var _widthd = Width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width',_widthd+'px');
        })
	</script>
	<div class="banner-right">
		<ul>
			<li>
				<a href="${ctx }/kd/finance/account" class="at">提付应收</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/account/reDetailedIndex" class="at">操作应收</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/account/payIndex" class="at">提付应付</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/account/payDetailedIndex" class="activet at">操作应付</a>
			</li>
		</ul>
		<div class="banner-right-list">
			<form id="searchForm" onsubmit="return false;">
				<div class="div">
					<span class="span">开单网点：</span>
					<select name="reNetWorkId" id="reNetWorkId">
						<option value="">请选择 </option>
						<c:forEach var="work" items="${userNetworks}">
							<option value="${work.id}">${work.sub_network_name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="div">
					<span class="span">到货网点：</span>
					<select name="payNetWorkId"  id="payNetWorkId">
						<option value="">请选择 </option>
						<c:forEach var="work" items="${comNetworks}">
							<option value="${work.id}">${work.sub_network_name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="div">
					<span class="span">开单日期：</span> <input type="text" class="Wdate" name="startTime"  id="startTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})"/>
				</div>
				<div class="div">
					<span class="spanc">至</span><input type="text" class="Wdate"  name="endTime" id="endTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})"/>
				</div>
				<div class="div">
					<span  class="span">对账结算状态：</span><select name="state"  id="state">
					<option value="">请选择 </option>
					<option  value="1">已结算 </option>
					<option value="0">未结算 </option>
				</select>
				</div>
				<div class="div">
					<span  class="span">运单状态：</span><select name="shipState"  id="shipState">
					<option value="">请选择 </option>
					<option  value="1">已入库 </option>
					<option value="2">短驳中 </option>
					<option value="3">短驳到达 </option>
					<option value="4">已发车 </option>
					<option value="5">已到达 </option>
					<option value="6">收货中转中 </option>
					<option value="7">到货中转中 </option>
					<option value="8">送货中 </option>
					<option value="9">已签收 </option>
				</select>
				</div>
				<div class="div">
					<span class="span">托运方：</span><input type="text" name="shipperName" id="shipperName"/>
				</div>
				<div class="div">
					<span class="span">收货方：</span><input type="text" name="receivingName" id="receivingName" />
				</div>
				<div class="div">
					<span class="span">运单号：</span>
					<input type="text"  name="shipSn" id="shipSn"/>
				</div>
				<div class="div">
					<button id="search" >查询</button>
					<input class="buttons" type="reset" onclick="resetSearch();return false;" value="重置"/>
				</div>
			</form>
		</div>

		<div class="banner-right-list2">
			<ul class="ul2">
				<li><a class="a4" onclick="window.location.href='/kd/finance/account/goPayDetailedJS'">结算</a></li>
				<li><a id="excelExport">导出EXCEl</a></li>
			</ul>
			<div style="overflow: auto; width: 100%;" id="loadingId">
				<script >$("#loadingId").mLoading("show");</script>
				<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="table">
					<tr>
						<th>序号</th><th>运单号</th><th>到货网点</th><th>开单网点</th><th>费用合计</th><th>对账结算状态</th><th>运单状态</th>
						<th>提货费</th><th>送货费</th><th>短驳费</th><th>中转费</th><th>现付运输费</th><th>现付油卡费</th><th>回付运输费</th>
						<th>整车保险费</th><th>发站装车费</th><th>发站其他费</th><th>到付运输费</th><th>现付卸车费</th><th>现付其他费</th>
						<th>货号</th><th>开单日期</th><th>出发地</th><th>到达地</th><th>托运方</th><th>收货方</th><th>货物名称</th><th>体积</th><th>重量</th>
					</tr>
				</table>
				<div id="page" style="text-align: center;">
				</div>
			</div>

		</div>
	</div>
</div>
<%@ include file="../../common/loginfoot.jsp" %>
<script src="${ctx}/static/kd/js/account/payczdz.js"></script>
<script type="text/javascript">
    function resetSearch(){
        $('#shipperName').combogrid('clear');
        $('#receivingName').combogrid('clear');
        $('#shipperName').combogrid('setValue', '');
        $('#receivingName').combogrid('setValue', '');
    }

    $('#shipperName').combogrid({
        url : '/kd/customer/searchCustomer?type=2',
        idField : 'customer_id',
        textField : 'customer_name',
        value:'${search.shipperName}',
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
                $('#shipperName').combogrid("grid").datagrid("reload", {'queryName': q});
                $('#shipperName').combogrid("setValue", q);
            }
        },
        fitColumns: true
    });
    //收货方信息
    $('#receivingName').combogrid({
        url : '/kd/customer/searchCustomer?type=1',
        idField : 'customer_id',
        textField : 'customer_name',
        value:'${search.receivingName}',
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
                $('#receivingName').combogrid("grid").datagrid("reload", {'queryName': q});
                $('#receivingName').combogrid("setValue", q);
            }
        },
        fitColumns: true
    });


    //全选确认
    function confirmAll(){
        var array = new Array();
        var fee=0;
        $(".tr_css input[type='checkbox']").each(function(i){
            console.log($(this));


            if($(this).prop("checked")) fee+=parseFloat($(this).attr('data'));

            if($(this).prop("checked")) {
                var recount={"shipId":$(this).val(),"loadId":$(this).attr('name')}
                array.push(recount);
            }

            //if($(this).prop("checked")) netArray.push($(this).attr('name'));
        });
        console.log(array);
        confirm(array,fee)
    }

    function confirm(objs,fee) {
        if(objs==null||objs==""){
            layer.msg("请选择要确认收款的运单");
            return;
        }
        layer.confirm(
            '共计'+objs.length+"单，应收金额共计"+fee+"元，确认已收款？",
            {
                btn : [ '确认', '取消' ]
            },
            function() {
                $.ajax({
                    type : "post",
                    dataType : "json",
                    data:{"dataArray":objs,"dataArrayLength":objs.length},
                    url : '${ctx }/kd/finance/account/confirmRe',
                    success : function(data) {
                        if (data) {
                            layer.msg('确认成功');
                            location.reload();
                        } else {
                            layer.msg('确认失败');
                        }

                    }
                });
            }, function() {
            });
    }


    function openDiv(shipid){
        //页面层
        layer.open({
            type: 2,
            area: ['850px', '700px'], //宽高
            content: ['${ctx}/kd/waybill/viewDetail?shipId='+shipid, 'yes']
        });
    }

    //分页
    layui.use(['laypage'], function(){
        var laypage = layui.laypage;
        //调用分页
        laypage({
            cont: 'page'
            ,pages: '${page.totalPage}' //得到总页数
            ,curr:'${page.pageNumber}'
            ,skip: true
            ,jump: function(obj, first){
                var reNetWorkId=$('#reNetWorkId').val();
                var payNetWorkId=$('#payNetWorkId').val();
                var state=$('#state').val();
                var shipSn=$('#shipSn').val();
                var startTime=$('#startTime').val();
                var endTime=$('#endTime').val();
                var shipperName=$('#shipperName').combobox('getValue');
                var receivingName=$('#receivingName').combobox('getValue');

                if(!first){
                    window.location.href="${ctx}/kd/finance/account?pageNo="+obj.curr+"&reNetWorkId="+reNetWorkId+
                        "&payNetWorkId="+payNetWorkId+"&state="+state+"&shipSn="+shipSn+"&startTime"+startTime+
                        "&endTime="+endTime+"&shipperName="+shipperName+"&receivingName="+receivingName;}
            }
            ,skin: '#1E9FFF'
        });
    });

    function selectAll() {
        if($("#selectAll").is(':checked')){
            $("#table  input[type='checkbox']").prop("checked", true);
        }else{
            $("#table input[type='checkbox']").prop("checked", false)
        }
    }

    function downExcel(){

        var reNetWorkId=$('#reNetWorkId').val();
        var payNetWorkId=$('#payNetWorkId').val();
        var state=$('#state').val();
        var shipSn=$('#shipSn').val();
        var startTime=$('#startTime').val();
        var endTime=$('#endTime').val();
        var shipperName=$('#shipperName').val();
        var receivingName=$('#receivingName').val();

        layer.confirm(
            '确定导出对账应收Excel吗',
            {
                btn : [ '导出', '返回' ]
            },
            function(index) {
                window.location.href='${ctx}/kd/finance/account/downLoadRe?reNetWorkId='+reNetWorkId+
                    '&payNetWorkId='+payNetWorkId+'&state='+state+'&shipSn='+shipSn+'&startTime='+startTime;
                '&endTime='+endTime+'&shipperName='+shipperName+'&receivingName='+receivingName;
                layer.close(index);
            }, function() {
            });

    }

    setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
</script>
</body>
</html>