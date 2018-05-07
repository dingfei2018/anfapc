
(function($){
	
function wayBillPrint(){
	this.init();
}
wayBillPrint.prototype={
		init:function(){
			/*if(this.checkIsInstall()){
				this.search();
			}*/
			$("#print").bind("click", $.proxy(function (e) {
				this.print();
            }, this));
		},
		createPrintPage:function(data){
			LODOP=getLodop();  
			/*LODOP.PRINT_INITA(10,10,754,453,"打印控件功能演示_Lodop功能_多页文档");
			LODOP.ADD_PRINT_TEXT(21,300,151,30,"自动居中的标题\n");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"Horient",2);
			LODOP.ADD_PRINT_TEXT(63,38,677,330,document.getElementById("doc").value);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
			LODOP.SET_PRINT_STYLEA(0,"Horient",3);
			LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
			LODOP.SET_PRINT_STYLEA(0,"TextNeatRow",false);
			LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"Horient",3);
			LODOP.ADD_PRINT_LINE(414,23,413,725,0,1);
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"Horient",3);
			LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
			LODOP.ADD_PRINT_TEXT(421,37,144,22,"左下脚的文本小标题");
			LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
			LODOP.SET_PRINT_STYLEA(0,"Vorient",1);
			LODOP.ADD_PRINT_TEXT(421,542,165,22,"右下脚的页号：第#页/共&页");
			LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
			LODOP.SET_PRINT_STYLEA(0,"Horient",1);
			LODOP.SET_PRINT_STYLEA(0,"Vorient",1);*/
			//alert(document.getElementsByTagName("body")[0].innerHTML);
			LODOP.SET_PRINT_PAGESIZE(1, 2100,1480,"");
			LODOP.ADD_PRINT_HTML(0,0,"100%","148mm",document.getElementById("doc").innerHTML);	
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
			//LODOP.SET_PRINT_STYLE("PenWidth",1);
			//LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
		},
		print:function(){
			this.createPrintPage();
			LODOP.PREVIEW();	
		},
		checkIsInstall: function() {	 
			try{ 
			     var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
				if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")){
					return true;
				}
			}catch(err){ 
				//alert("Error:本机未安装或需要升级!"); 
	 		} 
			return false;
		}
};
$(function(){
	new wayBillPrint();
})
})(jQuery);	
	
	


