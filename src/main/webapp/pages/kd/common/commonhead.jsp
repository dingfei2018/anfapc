<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${ctx}/static/kd/css/head.css" />
<link rel="stylesheet" href="${ctx}/static/kd/css/left.css" />
<link rel="stylesheet" href="${ctx}/static/pc/study/css/city-picker.css">
<link rel="stylesheet" href="${ctx}/static/pc/study/css/main.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
<script src="${ctx}/static/common/js/jquery.js"></script>
<script src="${ctx}/static/common/js/DivShowUtils.js"></script>
<script src="${ctx}/static/common/js/common.js"></script>
<script src="${ctx }/static/pc/js/dateFormat.js"></script>
<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/static/pc/study/js/city-picker.data.js"></script>
<script src="${ctx}/static/pc/study/js/city-picker.js"></script>
<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/layui/layui.js"></script>
<script src="${ctx}/static/pc/js/data-city.js?v=${version}"></script>
<!--easyui -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
<script>
    function resetCity(){
        $("#city-picker2").citypicker("reset");
        $("#city-picker3").citypicker("reset");
        $("#transferName").combogrid("clear")
        $("#senderId").combogrid("clear")
        $("#receiverId").combogrid("clear")
    }
    $(function(){
        $("#loadId").on('click',function(){
            var CheckBox = $(".banner-right-list2 input[type='checkbox']");
            CheckBox.each(function () {
                if(!$(this).is(':checked')){
                    $("#selectAll").prop("checked", false);
                    $("#chooseall").prop("checked", false);
                }

            })

        }) ;
    });

</script>
