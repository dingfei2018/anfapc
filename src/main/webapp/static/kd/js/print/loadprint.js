//库存查询
(function($){
	
function LoadPrint(){
	this.searchParam = "";
	this.init();
}
LoadPrint.prototype={
		init:function(){
			//this.checkIsInstall();
			$("#print").bind("click", $.proxy(function (e) {
				this.print();
            }, this));
		},
		createPrintPage:function(data){
			LODOP=getLodop();  
			LODOP.PRINT_INIT("配载单打印");
			LODOP.ADD_PRINT_HTM(10,3,"100%","100%",document.getElementById("printHtml").innerHTML);
			LODOP.PREVIEW();	

		},
		print:function(){
			this.createPrintPage();
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
			return false;
		}
};
$(function(){
	new LoadPrint();
})
})(jQuery);	
	
	


