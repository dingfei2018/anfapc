/**
 * 实时后台拉取城市区域用法
 * 获取select上级的元素，然后调用方法region
 * 例如
 * <div id="location">
 * <select id="province" name="province"></select>
 * <select id="city" name="city"></select>
 * <select id="hairarea" name="hairarea"></select>
 * </div>
 * 
 * 使用：
 * $("#location").region();
 * 
 */
$.fn.region=function(parameter){
	 var defaults = {
        provinceUrl:'/addressbook/getprovince?b='+Math.random(),
        cityUrl:'/addressbook/getcity?b='+Math.random(),
        areaUrl:'/addressbook/getcounty?b='+Math.random(),
        domain:'/',
        crossDomain: false,        //是否开启跨域
        dataType:'json',           //数据库类型:'json'或'jsonp'
        provinceField:'province',  //省份字段名
        cityField:'city',         //城市字段名
        areaField:'area',         //地区字段名
        currAreaCode:"",			//指定选中区域
        required: false,           //是否必须选一个
        cityRequired: false,       //required==true时，若只到城市则设置为true
        mutiCitySelect:false,      //城市多选
        callback:function(){},     //加载完后回调
        onChange:function(){}     //地区切换时触发,回调函数传入地区数据
    };
	var $this = $(this);
	var options = $.extend({}, defaults, parameter);
	var $province = $this.find('select[name="'+options.provinceField+'"]'),
    $city = $this.find('select[name="'+options.cityField+'"]'),
    $area = $this.find('select[name="'+options.areaField+'"]'),
	$code = options.currAreaCode;
	if(options.crossDomain==true)options.dataType="jsonp";
	var getData = function(code, dataUrl, callback){
		$.ajax({
		    url:options.domain+dataUrl,
		    type:'GET',
		    dataType:options.dataType,
		    cache:false,
		    crossDomain: options.crossDomain,
		    data:{"code":code},
	        jsonpCallback:"success_jsonpCallback",
		    success:function(data){
		    	callback(data.content);
	        }
		});
	}
	
	var format = {
		province:function(data){
			$province.empty();
            if(!options.required){
            	$province.append('<option value=""> - 请选择 - </option>');
            }
            var tmpCode="";
            $.each(data, function(i, o){
            	var code = o.region_code;
            	if(options.required){
            		if($code){
            			if(code.substring(0,2)==$code.substring(0,2)){
            				tmpCode=code;
            				$province.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
            			}else{
            				$province.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
            			}
            		}else{
            			if(i==0){
            				tmpCode=code;
            				$province.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
            			}else{
            				$province.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
            			}
            		}
            	}else{
            		if(code.substring(0,2)==$code.substring(0,2)){
            			tmpCode=code;
            			$province.append('<option value="'+o.region_code+'" selected="selected">'+o.region_name+'</option>');
            		}else{
            			$province.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
            		}
            	}
            	
            })
            if(tmpCode!=""){
            	 getData(tmpCode, options.cityUrl, format.city);
            }else{
            	options.callback();
            }
            $city.empty();
		},
		city:function(data){
			$city.empty();
            if(!options.required){
            	$city.append('<option value=""> - 请选择 - </option>');
            }
            var tmpCode="";
            var requireCode="";
            $.each(data, function(i, o){
            	var code = o.region_code;
            	if(options.required){
            		if($code){
            			if(code.substring(0,4)==$code.substring(0,4)){
                			tmpCode=code;
                			$city.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
                		}else{
                    		$city.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
                    	}
            		}else{
            			if(i==0){
                			tmpCode=code;
                			$city.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
                		}else{
                    		$city.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
                    	}
            		}
            	}else{
            		if(code.substring(0,4)==$code.substring(0,4)){
                		tmpCode=code;
                		$city.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
                	}else{
                		$city.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
                	}
            	}
            })
            
        	if(tmpCode!=""){
           	 	getData(tmpCode, options.areaUrl, format.area);
            }else{
            	options.callback();
            }
            if(data.length==0)$city.empty();
            $area.empty();
		},
		area:function(data){
			$area.empty();
            if(options.cityRequired||!options.required){
            	$area.append('<option value=""> - 请选择 - </option>');
            }
            $.each(data, function(i, o){
            	var code = o.region_code;
            	if(options.required){
            		if($code){
            			if(code==$code&&!options.cityRequired){//只选择到市不到区则加上options.cityRequired==true,默认是选择到区
                			$area.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
                		}else{
                			$area.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
                		}
            		}else{
            			if(i==0&&!options.cityRequired){//只选择到市不到区则加上options.cityRequired==true,默认是选择到区
                			$area.append('<option value="'+o.region_code+'"  selected="selected">'+o.region_name+'</option>');
                		}else{
                			$area.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
                		}
            		}
            	}else{
            		if(code==$code){
            			$area.append('<option value="'+o.region_code+'"   selected="selected">'+o.region_name+'</option>');
            		}else{
            			$area.append('<option value="'+o.region_code+'">'+o.region_name+'</option>');
            		}
            	}
            	
            })
            if(data.length==0)$city.empty();
            options.callback();
		}	
	};
	
	 //事件绑定
    $province.on('change',function(){
        options.province = $(this).find('option:selected').val(); //选中节点的区划代码
        $code="";
        getData(options.province, options.cityUrl, format.city);
    });
    $city.on('change',function(){
        options.city = $(this).find('option:selected').val(); //选中节点的区划代码
        $code="";
        getData(options.city, options.areaUrl, format.area);
    });
    
    getData(options.province, options.provinceUrl, format.province);
}