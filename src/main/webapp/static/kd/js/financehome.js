//签收
(function($){
function Financehome(){
	this.searchParam = "";
	this.init();
}
Financehome.prototype={
		init:function(){
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			this.search();
		},
		search:function(){
			var param = $("#searchFrom").serialize();
			param+="&pageNo=1";
			if(param)this.searchParam = param;
			this.getData(param, 1, this);
		},
		getData:function(param, pageNo, obj){
			if(param)param = param.substring(0,param.indexOf("pageNo="))+"pageNo="+pageNo;
			$("#loadingId").mLoading("show");
			$.ajax({
				type:'GET',
				url:'/kd/finance/search',
				data:encodeURI(param),
				success:function(data){
					var page = data.list;
					var html="";
					for(var i=0;i<page.list.length;i++){  
						html += obj.appendHtml(page.list[i],i, obj)
				    } 
					obj.appendHeadHtml(data.head, obj);
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.list.totalRow, data.list.totalPage, data.list.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},
		appendHeadHtml:function(obj, $obj){
			$("#totalfeeId").text(obj.totalfee+"元");
			var chengben = obj.zzfee+obj.dbfee+obj.thfee+obj.gxfee+obj.shfee;
			chengben = chengben.toFixed(2);
			$("#totalOutfeeId").text(chengben+"元");
			var prefee=(obj.totalfee-chengben);
			prefee = prefee.toFixed(2);
			var html = "<p>提货费<span>"+obj.thfee+"</span>元</p>";
				html +=	"<p>短驳费<span>"+obj.dbfee+"</span>元</p>";
				html +=	"<p>干线费<span>"+obj.gxfee+"</span>元</p>";
				html +=	"<p>送货费<span>"+obj.shfee+"</span>元</p>";
				html +=	"<p>中转费<span>"+obj.zzfee+"</span>元</p>";
			$(".banner-right-list2-left-bottom").empty();
			$(".banner-right-list2-left-bottom").append(html);;
			$("#prefeeId").text(prefee+"元");
			var rate = 0;
			if(prefee>0){
				rate = parseFloat(prefee/obj.totalfee)*100;
				rate = rate.toFixed(2);
			}
			$("#rateId").text(rate+"%");
		},
		appendHtml:function(obj,index, $obj){
			var rate = obj.rate*100;
			rate = rate.toFixed(2);
			var remain = obj.totalfee-obj.allloadfee;
			remain = remain.toFixed(2);
			var html = "<tr class=\"tr_css\" align=\"center\">";
			html += "<td><input type=\"checkbox\"></td>";
			html += "<td>"+(index+1)+"</td>";
			html += "<td>"+obj.fromAdd+"</td>";
			html += "<td>"+obj.toAdd+"</td>";
			html += "<td>"+obj.totalCount+"</td>";
			html += "<td>"+obj.volume+"</td>";
			html += "<td>"+obj.weight+"</td>";
			html += "<td style=\"color: #ff7801;\">"+obj.totalfee+"</td>";
			html += "<td style=\"color: #ff7801;\">"+obj.allloadfee+"</td>";
			html += "<td style=\"color: #ff7801;\">"+remain+"</td>";
			html += "<td style=\"color: #ff7801;\">"+rate+"%</td>";
			html += "</tr>";
			return html;
		},
		page:function(param, totalRow,totalPage, pageNumber, cobj){
			layui.use(['laypage'], function(){
		    	var laypage = layui.laypage;
			    laypage({
				      cont: 'page'
				      ,pages: totalPage //得到总页数
				      ,curr:totalPage==0?(pageNumber-1):pageNumber
				      ,skip: true //是否开启跳页
				      ,count:totalRow
			    	  ,jump: function(obj, first){
			    	      if(!first){
			    	    	  cobj.getData(param, obj.curr, cobj);
			    	      }
			    	  }
			    	  ,skin: '#1E9FFF'
			    });
	    	});
		}
};

$(function(){
	new Financehome();
})
})(jQuery);	
	
	


