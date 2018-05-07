/**
 * 物流园联动搜索
 */
$.fn.fillCorps=function(path){
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
			html += "<li data='"+data[i].id+"'><a style='cursor: pointer;'>"+data[i].park_name+"</a></li>"
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
		var parkName = $(this).text();
		id=$(this).attr("data");
		$this.val(parkName);
		hideDiv();
	})
}