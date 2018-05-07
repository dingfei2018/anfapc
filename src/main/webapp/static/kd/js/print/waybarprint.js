//库存查询
(function($){
	
function WaybarPrint(){
	this.searchParam = "";
	this.init();
	this.LODOP;
}
WaybarPrint.prototype={
		init:function(){
			//this.checkIsInstall();
			$("#print").bind("click", $.proxy(function (e) {
				this.printRe();
            }, this));
			setTimeout(function(){
				$("#print").click();
			},1000)
		},
		createPrintPage:function(obj, index,size, fromAdd, toAdd, time, companyName, shipSn){
			var add = (index-1)*10
			obj.LODOP.ADD_PRINT_RECT("3mm", "3mm", "69mm", "69mm", 2, 1)
			if(companyName.length<=4){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "31mm","55mm", "10mm", companyName);
			}else if(companyName==5){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "29mm","55mm", "10mm", companyName);
			}else if(companyName.length==6){
				this.LODOP.ADD_PRINT_TEXT("25mm", "27mm","55mm", "10mm", companyName);
			}else if(companyName.length==7){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "26mm","55mm", "10mm", companyName);
			}else if(companyName.length==8){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "25mm","55mm", "10mm", companyName);
			}else if(companyName.length==9){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "23mm","55mm", "10mm", companyName);
			}else if(companyName.length==10){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "20mm","55mm", "10mm", companyName);
			}else if(companyName.length==11){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "19mm","55mm", "10mm", companyName);
			}else if(companyName.length==12){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "17mm","55mm", "10mm", companyName);
			}else if(companyName.length==13){
				obj.LODOP.ADD_PRINT_TEXT("25mm", "15mm","55mm", "10mm", companyName);
			}else{
				obj.LODOP.ADD_PRINT_TEXT("23mm", "13mm","55mm", "10mm", companyName);
			}
			obj.LODOP.SET_PRINT_STYLEA(2+add,"FontName","隶书");
			obj.LODOP.SET_PRINT_STYLEA(2+add,"FontSize",10);
			obj.LODOP.SET_PRINT_STYLEA(2+add,"FontColor",0);
			obj.LODOP.SET_PRINT_STYLEA(2+add,"Bold",1);
			obj.LODOP.ADD_PRINT_BARCODE("6mm", "12mm","60mm", "15mm", "Code39", shipSn)
			obj.LODOP.ADD_PRINT_RECT("35mm", "6mm", "26mm", "26mm", 2, 1)
			obj.LODOP.ADD_PRINT_TEXT("43mm", "35mm","28mm", "20mm",">");
			obj.LODOP.SET_PRINT_STYLEA(5+add,"FontSize",30);
			obj.LODOP.ADD_PRINT_RECT("35mm", "43mm", "26mm", "26mm", 2, 1)
			
			if(fromAdd.length<=4){
				obj.LODOP.ADD_PRINT_TEXT("46mm", "12mm", "24mm", "26mm", fromAdd);
			}else if(fromAdd.length>4&&fromAdd.length<=8){
				obj.LODOP.ADD_PRINT_TEXT("44mm", "9mm", "24mm", "26mm", fromAdd);
			}else if(fromAdd.length>8&&fromAdd.length<=12){
				obj.LODOP.ADD_PRINT_TEXT("40mm", "9mm", "24mm", "26mm", fromAdd);
			}else if(fromAdd.length>12){
				obj.LODOP.ADD_PRINT_TEXT("36mm", "9mm", "24mm", "26mm", fromAdd.substring(0,16));
			}
			
			obj.LODOP.SET_PRINT_STYLEA(7+add,"FontSize",14);
			obj.LODOP.SET_PRINT_STYLEA(7+add,"Bold",1);
			if(toAdd.length<=4){
				obj.LODOP.ADD_PRINT_TEXT("46mm", "48mm", "24mm", "26mm", toAdd);
			}else if(toAdd.length>4&&toAdd.length<=8){
				obj.LODOP.ADD_PRINT_TEXT("44mm", "47mm", "24mm", "26mm", toAdd);
			}else if(toAdd.length>8&&toAdd.length<=12){
				obj.LODOP.ADD_PRINT_TEXT("40mm", "47mm", "24mm", "26mm", toAdd);
			}else if(toAdd.length>12){
				obj.LODOP.ADD_PRINT_TEXT("36mm", "47mm", "24mm", "26mm", toAdd.substring(0,16));
			}
			obj.LODOP.SET_PRINT_STYLEA(8+add,"FontSize",14);
			obj.LODOP.SET_PRINT_STYLEA(8+add,"Bold",1);
			obj.LODOP.ADD_PRINT_TEXT("66mm", "6mm","35mm", "10mm","托运日期:"+time);
			obj.LODOP.ADD_PRINT_TEXT("65mm", "43mm","35mm", "10mm","件数:"+index+"/"+size);
			obj.LODOP.SET_PRINT_STYLEA(10+add,"FontSize",12);
			obj.LODOP.SET_PRINT_STYLEA(10+add,"Bold",1);
		},
		createNewPrintPage:function(obj, fromnetworkname, tonetworkname, goodsSn, receiverName, time, companyName, detailAddress, telphone){
			obj.LODOP.ADD_PRINT_RECT("3mm", "3mm", "69mm", "69mm", 2, 1)
			if(companyName!=null&& companyName!=""){
				if(companyName.length<=4){
					obj.LODOP.ADD_PRINT_TEXT("10mm", "24mm","55mm", "10mm", companyName);
				}else if(companyName.length==5){
					obj.LODOP.ADD_PRINT_TEXT("10mm", "21mm","55mm", "10mm", companyName);
				}else if(companyName.length==6){
					this.LODOP.ADD_PRINT_TEXT("10mm", "18mm","55mm", "10mm", companyName);
				}else if(companyName.length==7){
					obj.LODOP.ADD_PRINT_TEXT("10mm", "16mm","60mm", "10mm", companyName);
				}else if(companyName.length==8){
					obj.LODOP.ADD_PRINT_TEXT("10mm", "14mm","65mm", "10mm", companyName);
				}else if(companyName.length==9){
					obj.LODOP.ADD_PRINT_TEXT("10mm", "10mm","65mm", "10mm", companyName);
				}else if(companyName.length==10){
					obj.LODOP.ADD_PRINT_TEXT("10mm", "8mm","65mm", "10mm", companyName);
				}else{
					obj.LODOP.ADD_PRINT_TEXT("10mm", "8mm","65mm", "10mm", companyName.substring(0,10));
				}
				obj.LODOP.SET_PRINT_STYLEA(2,"FontName","隶书");
				obj.LODOP.SET_PRINT_STYLEA(2,"FontSize",16);
				obj.LODOP.SET_PRINT_STYLEA(2,"FontColor",0);
				obj.LODOP.SET_PRINT_STYLEA(2,"Bold",1);
			}else{
				obj.LODOP.ADD_PRINT_TEXT("10mm", "8mm","65mm", "10mm", "");
			}
			
			obj.LODOP.ADD_PRINT_LINE("18mm", "5mm", "18mm", "70mm", 2, 0)
			
			obj.LODOP.ADD_PRINT_TEXT("23mm", "5mm","64mm", "20mm","货号:"+goodsSn);
			obj.LODOP.SET_PRINT_STYLEA(4,"FontSize",13);
			obj.LODOP.SET_PRINT_STYLEA(4,"Bold",1);
			
			obj.LODOP.ADD_PRINT_TEXT("32mm", "5mm", "70mm", "25mm", "发往:" + fromnetworkname + "->" + tonetworkname);
			obj.LODOP.SET_PRINT_STYLEA(5,"FontSize",14);
			obj.LODOP.SET_PRINT_STYLEA(5,"Bold",1);
			
			
			obj.LODOP.ADD_PRINT_TEXT("40mm", "5mm","50mm", "20mm","收货人:"+receiverName);
			obj.LODOP.SET_PRINT_STYLEA(6,"FontSize",10);
			obj.LODOP.SET_PRINT_STYLEA(6,"Bold",1);
			obj.LODOP.ADD_PRINT_TEXT("47mm", "5mm","50mm", "20mm","日期:"+time);
			obj.LODOP.SET_PRINT_STYLEA(7,"FontSize",10);
			obj.LODOP.SET_PRINT_STYLEA(7,"Bold",1);
			
			
			if(detailAddress!=null&& detailAddress!=""){
				obj.LODOP.ADD_PRINT_TEXT("55mm", "5mm","68mm", "15mm", detailAddress);
				obj.LODOP.SET_PRINT_STYLEA(8,"FontSize",10);
				obj.LODOP.SET_PRINT_STYLEA(8,"Bold",1);
			}
			
			if(telphone!=null&& telphone!=""){
				if(detailAddress.length>18){
					obj.LODOP.ADD_PRINT_TEXT("65mm", "5mm","50mm", "7mm",telphone);
					obj.LODOP.SET_PRINT_STYLEA(9,"FontSize",10);
					obj.LODOP.SET_PRINT_STYLEA(9,"Bold",1);
				}else{
					obj.LODOP.ADD_PRINT_TEXT("60mm", "5mm","50mm", "7mm",telphone);
					obj.LODOP.SET_PRINT_STYLEA(9,"FontSize",10);
					obj.LODOP.SET_PRINT_STYLEA(9,"Bold",1);
				}
				/*obj.LODOP.ADD_PRINT_TEXT("60mm", "5mm","50mm", "7mm",telphone);
				obj.LODOP.SET_PRINT_STYLEA(8,"FontSize",10);
				obj.LODOP.SET_PRINT_STYLEA(8,"Bold",1);*/
			}
			
		},
		printRe:function(){
			this.LODOP=getLodop();
			this.LODOP.PRINT_INIT("运单条形码打印");
			this.LODOP.SET_PRINT_PAGESIZE(1, 750, 750, "");
			var size = $("#size").val();
			var fromnetworkname = $("#fromnetworkname").val();
			var tonetworkname = $("#tonetworkname").val();
			var goodsSn = $("#goodsSn").val();
			var receiverName = $("#receiverName").val();
			var time = $("#time").val();
			var companyName = $("#companyName").val();
			var detailAddress = $("#detailAddress").val(); 
			var telphone = $("#telphone").val(); 
			this.createNewPrintPage(this ,fromnetworkname, tonetworkname, goodsSn, receiverName, time, companyName, detailAddress, telphone);
			this.LODOP.SET_PRINT_COPIES(size);
			this.LODOP.PREVIEW();
		},
		checkIsInstall: function() {	 
			try{ 
			    var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
				if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")){
					return true;
				}
			}catch(err){ 
				alert("Error:本机未安装或需要升级!"); 
	 		} 
		}
};
$(function(){
	new WaybarPrint();
})
})(jQuery);	
	
	


