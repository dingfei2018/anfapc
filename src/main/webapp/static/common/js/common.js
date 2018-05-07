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
		layer.tips(message, select, {time: 2000,tipsMore: true,
			  tips: [2, '#ff7800']});
	},20);
}

var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};

function IdentityCodeValid(code) { 
    var tip = "";
    var pass= true;
    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
        pass = false;
    }
   else if(!city[code.substr(0,2)]){
        pass = false;
    }
    else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17]){
                tip = "校验位错误";
                pass =false;
            }
        }
    }
    return pass;
}

function checkInputIntFloat(oInput) { 
	if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,2}/,'')) { 
		oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,2}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,2}/); 
	} 
}