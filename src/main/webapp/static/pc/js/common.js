var Anfa={}
/**
 * 统一包装ajax方法拦截用户没登陆，登录失效状态
 * liangxp 20170929
 */
Anfa.ajax=function(parameter){
	var defaults = {
	        domain:'',
	        crossDomain: false,      //是否开启跨域
	        type:"POST",
	        type:"POST",
	        async: true,
	        dataType:'json'          //数据库类型:'json'或'jsonp'
	    };
	var options = $.extend({}, defaults, parameter);
	$.ajax({
		type : options.type,
		dataType : options.dataType,
		async : options.async,
		url : options.domain+options.url,
		data:options.data,		
		success : function(data) {
            //判断是否登录失效
			if(data.type==-9){
				window.location.href=options.domain+"/front/user";
			}else{
				options.success(data);
			}
		}
	});
}

//layer 提示
Anfa.show=function(message, select){
	layer.closeAll("tips");
	setTimeout(function(){
		layer.tips(message, select, {time: 3000,tipsMore: true,
			  tips: [2, '#ff7800']});
	},20);
}
