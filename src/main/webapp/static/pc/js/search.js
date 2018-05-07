//check

function subcheck(){
	var toCityCode=$("#toCityCode").val();
	var fromCityCode=$("#fromCityCode").val();
	if(fromCityCode==null ||fromCityCode==""){
		alert("请选择出发地");
		return false;
	}
	return true;
	
}


//search
function search(){
	var fromCityCode=$("#fromCityCode").val();
	var fromRegionCode=$("#fromRegionCode").val();
	var toCityCode=$("#toCityCode").val();
	var toRegionCode=$("#toRegionCode").val();
		$(".distpicker1 .select-item").each(function(i){
			var count = $(this).data("count");
			if(count == "city"){
				toCityCode = $(this).data("code");
			} 
			if(count == "district"){
				toRegionCode = $(this).data("code");
			}
		});
		
		$("#distpicker-s .select-item").each(function(i){
			var count = $(this).data("count");
			if(count == "city"){
				fromCityCode = $(this).data("code");
			}
			if(count == "district"){
				fromRegionCode = $(this).data("code");
			}
		});
		$("#toCityCode").val(toCityCode);
		$("#toRegionCode").val(toRegionCode);
		$("#fromCityCode").val(fromCityCode);
		$("#fromRegionCode").val(fromRegionCode);
		$("#myfrom").submit();
}


/**
 * 物流园联动搜索
 */
$.fn.fillPark=function(path){
	var $this = $(this);
	var getData = function (){
		$.ajax({
			type : "post",
			dataType : "json",
			url : path+"/park/queryLogisticsParkList",
			data:{parkName:$this.val()},		
			success : function(data) {
				showdata(data);
			}
		});
	}
	
	var hideDiv=function(){
		$(".content-left-ang3s ul").empty();
		$(".content-left-ang3s").hide();
	}
	
	var showdata=function(data){
		if(data.length==0){
			hideDiv();
			return;
		}
		var html = "";
		for(var i=0;i<data.length;i++){  
			html += "<li data='"+data[i].id+"'><a style='cursor: pointer;'>"+data[i].park_name+"</a></li>"
	    }  
		$(".content-left-ang3s ul").empty();
		$(".content-left-ang3s ul").append(html);
		$(".content-left-ang3s").show();
	}
	
	$(this).bind("keyup focus", function(){
		getData();
	});
	
	$(this).bind("focusout", function(){
		hideDiv();
	});
	
	$(".content-left-ang3s ul").on("mousedown", "li", function(){
		var parkName = $(this).text();
		id=$(this).attr("data");
		$this.val(parkName);
		hideDiv();
	})
}


/**
 * 首页公司搜索联动
 * liangxp
 */
$.fn.fillCorps=function(path){
	var $this = $(this);
	var id=0;
	var getData = function (){
		$.ajax({
			type : "post",
			dataType : "html",
			url : path+"/company/queryCompany",
			data:{corpName:$this.val()},		
			success : function(data) {
				var obj = new Function("return" + data)();
				showdata(obj);
				id=0;
			}
		});
	}
	
	var hideDiv=function(){
		$(".content-left-ang3 ul").empty();
		$(".content-left-ang3").hide();
	}
	
	var showdata=function(data){
		if(data.length==0){
			hideDiv();
			return;
		}
		var html = "";
		for(var i=0;i<data.length;i++){  
			html += "<li data='"+data[i].id+"'><a style='cursor: pointer;'>"+data[i].corpname+"</a></li>"
	    }  
		$(".content-left-ang3 ul").empty();
		$(".content-left-ang3 ul").append(html);
		$(".content-left-ang3").show();
	}
	
	$(this).bind("keyup focus", function(){
		getData();
	});
	
	$(this).bind("focusout", function(){
		hideDiv();
	});
	
	$(".content-left-ang3 ul").on("mousedown", "li", function(){
		var corpName = $(this).text();
		id=$(this).attr("data");
		$this.val(corpName);
		hideDiv();
	})
	
	$this.siblings(".content-left-ang2").bind("click", function(){
		if(id>0){
			window.location.href=path+"/company?id="+id;
		}else{
			window.location.href=path+"/company/queryList?corpName="+$this.val();
		}
	})
}

/**
 * 快递单查询，快递公司联动
 * liangxp
 */
$.fn.fillExpress=function(){
	var $this = $(this);
	var express = ["AJ|安捷快递","ANE|安能物流","AXD|安信达快递","BQXHM|北青小红帽","BFDF|百福东方","BTWL|百世快运","CCES|CCES快递","CITY100|城市100","COE|COE东方快递","CSCY|长沙创一","CDSTKY|成都善途速运","DBL|德邦","DSWL|D速物流","DTWL|大田物流","EMS|EMS","FAST|快捷速递","FEDEX|FEDEX联邦(国内件）","FEDEX_GJ|FEDEX联邦(国际件）","FKD|飞康达","GDEMS|广东邮政","GSD|共速达","GTO|国通快递","GTSD|高铁速递","HFWL|汇丰物流","HHTT|天天快递","HLWL|恒路物流","HOAU|天地华宇","hq568|华强物流","HTKY|百世快递","HXLWL|华夏龙物流","HYLSD|好来运快递","JGSD|京广速递","JIUYE|九曳供应链","JJKY|佳吉快运","JLDT|嘉里物流","JTKD|捷特快递","JXD|急先达","JYKD|晋越快递","JYM|加运美","JYWL|佳怡物流","KYWL|跨越物流","LB|龙邦快递","LHT|联昊通速递","MHKD|民航快递","MLWL|明亮物流","NEDA|能达速递","PADTF|平安达腾飞快递","QCKD|全晨快递","QFKD|全峰快递","QRT|全日通快递","RFD|如风达","SAD|赛澳递","SAWL|圣安物流","SBWL|盛邦物流","SDWL|上大物流","SF|顺丰快递","SFWL|盛丰物流","SHWL|盛辉物流","ST|速通物流","STO|申通快递","STWL|速腾快递","SURE|速尔快递","TSSTO|唐山申通","UAPEX|全一快递","UC|优速快递","WJWL|万家物流","WXWL|万象物流","XBWL|新邦物流","XFEX|信丰快递","XYT|希优特","XJ|新杰物流","YADEX|源安达快递","YCWL|远成物流","YD|韵达快递","YDH|义达国际物流","YFEX|越丰物流","YFHEX|原飞航物流","YFSD|亚风快递","YTKD|运通快递","YTO|圆通速递","YXKD|亿翔快递","YZPY|邮政平邮/小包","ZENY|增益快递","ZHQKD|汇强快递","ZJS|宅急送","ZTE|众通快递","ZTKY|中铁快运","ZTO|中通速递","ZTWL|中铁物流","ZYWL|中邮物流","AMAZON|亚马逊物流","SUBIDA|速必达物流","RFEX|瑞丰速递","QUICK|快客快递","CJKD|城际快递","CNPEX|CNPEX中邮快递","HOTSCM|鸿桥供应链","HPTEX|海派通物流公司","AYCA|澳邮专线","PANEX|泛捷快递","PCA|PCA Express","UEQ|UEQ Express"];
	var hideDiv=function(){
		$(".div ul").empty();
		$(".div").hide();
	}
	var Ex={};
	var select="";
	Ex.showdata=function(){
		var data = Ex.filter();
		//没有输入且没有匹配结果的情况下
		if(data.length==0&&!$this.val())data=express;
		var html = "";
		for(var i=0;i<data.length;i++){  
			var eno = data[i].split("|");
			html += "<li data='"+eno[0]+"'><a style='cursor: pointer;text-indent: 0px;'>"+eno[1]+"</a></li>"
	    }  
		$(".div ul").empty();
		$(".div ul").append(html);
		$(".div").show();
	}
	Ex.filter=function(){
		var ename = $this.val();
		var filters = [];
		if(ename==select)return filters;
		if(ename==""||ename==null){
			return filters;
		}
		for(var i=0;i<express.length;i++){  
			var eno = express[i].split("|");
			if(eno[1].substr(0,ename.length).toLowerCase()==ename.toLowerCase()){
				filters.push(express[i]);
			}
	    } 
		return filters;
	}
	$(this).bind("keyup focus", function(){
		Ex.showdata();
	});
	
	$(this).bind("focusout", function(){
		hideDiv();
	});
	$(".div ul").on("mousedown", "li", function(){
		var ename = $(this).text();
		select = ename;
		$("#kudnameId").val($(this).attr("data"));
		$this.val(ename);
		hideDiv();
	})
}

//searchPark
function searchParks(){
	var addcity=$("#addcity").val();
	var addCode=$("#addCode").val();
	var parkName=$("#parkName").val();
	$("#distpicker .select-item").each(function(i){
		var count = $(this).data("count");
		if(count == "city"){
			addcity = $(this).data("code");
		}
		 if (count == "district"){
			addCode = $(this).data("code");
		}
	});
	$("#addcity").val(addcity);
	$("#addCode").val(addCode);
	$("#parkfrom").submit();
}
