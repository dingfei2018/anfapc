//配载列表
(function($){
	
function ShipLoadList(){
	this.searchParam = "";
	this.init();
}
ShipLoadList.prototype={
		init:function(){
			$("#chooseall").bind("click", this.selectAll);
			$(".banner-right-a3").bind("click", $.proxy(function (e) {
				this.show(e);
            }, this));
			$("#search").bind("click", $.proxy(function () {
				this.search();
            }, this));
			$(".banner-right-list2").on("click", ".atta", $.proxy(function (e) {
				this.moreshow(e);
            }, this));
			
			$(".banner-right-list2").on("click", ".print", this.print);
			
			$(".banner-right-list3").on("change",  "input[type='file']", $.proxy(function (e) {
				this.upload(e);
            }, this));
			$(".tab_css_1").on("click", ".viewId",this.openDetail);
			this.search();
		},
		selectAll:function(){
			if($(this).is(':checked')){
				$(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', true)
		    }else{ 
		    	$(this).parents(".banner-right-list2").find("input[type='checkbox']").prop('checked', false)
		    } 
		},
		removeAll:function(){
			var $this=this;
			$("#unloadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
				var tr = $(o).parents("tr");
				var id = $(tr).attr("data");
				$this.removeAllShip($this);
				$(tr).remove();
			})
			this.getData(this.searchParam, 1, this);
			$(".selectAll").prop("checked", false);
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
				url:'/kd/loading/searchLoadlist',
				data:encodeURI(param),
				success:function(data){
					var html="";
					for(var i=0;i<data.list.length;i++){  
						html += obj.appendHtml(data.list[i], i+1)
				    } 
					$("#loadId > tr").remove();
					$("#loadId").append(html);
					obj.page(param, data.totalRow, data.totalPage,data.pageNumber, obj);
					setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
				}
			});
		},
		appendHtml:function(obj, index){
			var html = "<tr class=\"tr_css\" align=\"center\" data='"+obj.load_id+"' trantype='"+obj.load_transport_type+"'  status='"+obj.load_delivery_status+"'>";
			html += "<td><input type=\"checkbox\"/></td>";
			html += "<td data='index'>"+index+"</td>";
			html += "<td class='viewId' data='"+obj.load_id+"' style=\"color: #3974f8;\">"+obj.load_sn+"</td>";
			html += "<td>"+obj.snetworkName+"</td>";
			html += "<td>"+obj.enetworkName+"</td>";
			if(obj.load_delivery_status==1){
				html += "<td>配载中</td>";
			}else if(obj.load_delivery_status==2){
				html += "<td>已发车</td>";
			}else if(obj.load_delivery_status==3){
				html += "<td>已到达</td>";
			}else if(obj.load_delivery_status==4){
				html += "<td>已完成</td>";
			}
			
			html += "<td>"+new Date(obj.create_time).format("yyyy-MM-dd")+"</td>";
			html += "<td>"+obj.truck_id_number+"</td>";
			html += "<td>"+obj.truck_driver_name+"</td>";
			html += "<td>"+obj.truck_driver_mobile+"</td>";
			
			if(obj.load_transport_type==1){
				html += "<td>提货</td>";
			}else if(obj.load_transport_type==2){
				html += "<td>短驳</td>";
			}else if(obj.load_transport_type==3){
				html += "<td>干线</td>";
			}else if(obj.load_transport_type==4){
				html += "<td>送货</td>";
			}
			html += "<td style=\"color: #ff7801;\">"+obj.load_fee+"</td>";
			html += "<td>"+obj.fromCity+"</td>";
			html += "<td>"+obj.toCity+"</td>";
			html += "<td>"+obj.load_amount+"</td>";
			html += "<td>"+obj.load_volume+"</td>";
			html += "<td class=\"banner-right-padding\">"+obj.load_weight+"</td>";
			html += "<td class=\"banner-right-th\" data='"+obj.load_id+"'><span class='atta'>附件</span> <div class=\"banner-right-list2-tab2\">";
			html += "<dl><dd class=\"input2\"><span  class='print'>打印</span></dd></dl></div></td></tr>";
			return html;
		},
		page:function(param, totalRow, totalPage, pageNumber, cobj){
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
		},
		send:function(ids, obj){
			$.ajax({
				type:'POST',
				url:'/kd/loading/send',
				data:{"ids":ids},
				success:function(data){
					if (data.success) {
						layer.msg("成功发车");
						setTimeout(function(){window.location.reload(true);}, 1000);
					} else {
						layer.msg(data.msg);
					}
				}
			});
		},
		tihuo:function(ids, obj){
			$.ajax({
				type:'POST',
				url:'/kd/loading/tihuoOver',
				data:{"ids":ids},
				success:function(data){
					if (data.success) {
						layer.msg("操作成功");
						setTimeout(function(){window.location.reload(true);}, 1000);
					} else {
						layer.msg(data.msg);
					}
				}
			});
		},
		songhuo:function(ids, obj){
			$.ajax({
				type:'POST',
				url:'/kd/loading/songhuoOver',
				data:{"ids":ids},
				success:function(data){
					if (data.success) {
						layer.msg("操作成功");
						setTimeout(function(){window.location.reload(true);}, 1000);
					} else {
						layer.msg(data.msg);
					}
				}
			});
		},
		moreshow:function(e){
			$this=e.target;
			var id = $($this).parent("td").attr("data");
			this.open("loadattachment?loadId="+id, "配载单-附件信息");
		},
		show:function(e){
			$this=e.target;
			$othis=this;
			var id= new Array();
			var index=0;
			var status = new Array();
			var trans = new Array();
			$("#loadId tr:gt(0)").find("input[type='checkbox']:checkbox:checked").each(function(i,o){
				var tr = $(o).parents("tr");
				id.push($(tr).attr("data"));
				status.push($(tr).attr("status"));
				trans.push($(tr).attr("trantype"));
				index++;
			})
			var type = $($this).attr("data");
			var url = "";
			if(type=="remove"){
				if(index==0){
					layer.msg('请选择要删除数据');
					return;
				}
				var flag = false;
				for(var i=0; i<status.length;i++){
					if(status[i]!=1){
						flag = true;
						break;
					}
				}
				if(flag){
					layer.msg('已经发车的配载单不能删除');
					return;
				}
				layer.confirm('您确定要删除？', {
	    		  	btn: ['删除','取消']
	    		}, function(){
	    			$othis.removeload(id.join(),$othis);
	    		}, function(){});	
			}else{
				if(type=="export"){
					this.excelExport($othis);
					return;
				}
				if(index==0){
					layer.msg('请选择数据')
					return;
				}
				if(type=="send"){
					var flag = false;
					for(var i=0; i<status.length;i++){
						if(status[i]!=1){
							flag = true;
							break;
						}
					}
					if(flag){
						layer.msg('请选择未发车的配载单');
						return;
					}
					layer.confirm('您确定要发车么？', {
		    		  	btn: ['确定','取消']
		    		}, function(){
		    			$othis.send(id.join(), $othis);
		    		}, function(){});	
				}else if(type=="tihuo"){
					var flag = false;
					for(var i=0; i<trans.length;i++){
						if(trans[i]!=1){
							flag = true;
							break;
						}
					}
					if(flag){
						layer.msg('请选择提货类型的配载单');
						return;
					}
					for(var i=0; i<status.length;i++){
						if(status[i]==4){
							flag = true;
							break;
						}
					}
					if(flag){
						layer.msg('请选择未完成的配载单');
						return;
					}
					
					layer.confirm('您确定要提货完成么？', {
		    		  	btn: ['确定','取消']
		    		}, function(){
		    			$othis.tihuo(id.join(), $othis);
		    		}, function(){});	

				}else if(type=="songhuo"){
					var flag = false;
					for(var i=0; i<trans.length;i++){
						if(trans[i]!=4){
							flag = true;
							break;
						}
					}
					if(flag){
						layer.msg('请选择送货类型的配载单');
						return;
					}
					
					for(var i=0; i<status.length;i++){
						if(status[i]==4){
							flag = true;
							break;
						}
					}
					if(flag){
						layer.msg('请选择未完成的配载单');
						return;
					}
					
					layer.confirm('您确定要送货完成么？', {
		    		  	btn: ['确定','取消']
		    		}, function(){
		    			$othis.songhuo(id.join(), $othis);
		    		}, function(){});	
				}else if(type=="mdfee"){
                    if(index>1){
                        layer.msg('每次只能选择一条需要修改费用的数据');
                        return;
                    }
					url = "modifyFee?loadId="+id;
					this.open(url, "配载单-修改费用", $othis);
				}else if(type=="truck"){
                    if(index>1){
                        layer.msg('每次只能选择一条需要更换车辆的数据');
                        return;
                    }
					url = "loadchange?loadId="+id;
					this.open(url, "配载单-更改司机信息", $othis);
				}else if(type=="network"){
                    if(index>1){
                        layer.msg('每次只能选择一条需要修改到货网点的数据');
                        return;
                    }
					if(status>2){
						layer.msg('已经到站或已完成的配载不能修改到货网点');
						return;
					}
                    for(var i=0; i<trans.length;i++){
                        if(trans[i]<2||trans[i]>3){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        layer.msg('请选择干线或短驳类型的配载单');
                        return;
                    }
					url = "loadnetwork?loadId="+id;
					this.open(url, "配载单-更改到货网点", $othis);
				}else if(type=="add"){
                    if(index>1){
                        layer.msg('每次只能选择一条需要配载加单的数据');
                        return;
                    }
                    for(var i=0; i<status.length;i++){
                        if(status[i]!=1){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        layer.msg('请选择未发车的配载单');
                        return;
                    }
					window.location.href="/kd/loading/add?loadId="+id;
				}else if(type=="reduce"){
                    if(index>1){
                        layer.msg('每次只能选择一条需要配载减单的数据');
                        return;
                    }
                    for(var i=0; i<status.length;i++){
                        if(status[i]!=1){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        layer.msg('请选择未发车的配载单');
                        return;
                    }
					window.location.href="/kd/loading/reduce?loadId="+id;
				}
			}
		},
		open:function(url, otitle, obj){
			layer.open({
				  type: 2,
				  title: otitle,
				  area: ['1200px', '700px'],
				  content: ['/kd/loading/'+url, 'yes'], //iframe的url，no代表不显示滚动条'/kd/loading/loadchange?truck_id=21'
				  end: function(){ 
					  obj.search();
					  }
				  });
		},
		openDetail:function(){
			var loadId = $(this).attr("data");
			layer.open({
				  type: 2,
				  title: "配载单详情",
				  area: ['1200px', '700px'],
				  content: ['/kd/loading/loadingView?loadId='+loadId, 'yes']})
		},
		removeload:function(ids, obj){
			$.ajax({
				type:'GET',
				url:'/kd/loading/removeload',
				data:{loadIds:ids},
				success:function(data){
					 if(data.success==true){
						 layer.msg("删除成功");
					 }else if(data.type==3001){
						 layer.msg(data.msg);
					 }else if(data.type==3002){
						 layer.msg(data.msg);
					 }else{
						 layer.msg("删除失败");
					 }
					 setTimeout(function(){obj.search();}, 1000);
				}
			});
		},
		upload:function(e){
			$this = e.target;
			$othis = this;
			var img = $($this).parents(".gtu1").find(".gtu2").find("img");
		    var formData = new FormData(); // FormData 对象
		    var file = $($this)[0].files[0];
		    if(!this.validate(file)){
		    	return;
		    }
		    formData.append("imgType", "truck"); 
		    formData.append("fileName", file); 
		    var load = new Loading();
		    this.showLoading($($this).parents(".gtu1").find(".gtu2"), load);
			$.ajax({
			  url : "/file/uploadImage",
			  type : 'POST',
			  contentType: false,    //不可缺
			  processData: false,    //不可缺
			  data : formData,
			  success:function(data){
				  if(data.success==true){
					  $(img).attr("src",data.src);
					  $(img).wrap("<a class=\"example-image-link\" href='"+data.src+"' data-lightbox=\"example-1\">");
				  }else{
					  layer.msg(data.message);
				  }
				  $othis.removeLoading(load);
				  setTimeout(function(){$othis.search();}, 1000);
			  }
			});
			
		},
		showLoading:function(imgName, load){
			load.init({
				 target: imgName
			});
			load.start();
		},
		removeLoading:function(load){
			load.stop();
		},
		validate:function(file){
			if (file == null){  
		        layer.msg("请选择需要上传的文件!");
		        return false; 
		    }
			var	imgName = file.name;
			if (imgName == ''){  
		        layer.msg("请选择需要上传的文件!");
		        return false; 
		    } else {   
		    	 var size = file.size/(1024*1024);  
		    	 if(size>2){
		    		 layer.msg("文件大小不能超过2MB");
		    		 return false;
		    	 }
		        idx = imgName.lastIndexOf(".");   
		        if (idx != -1){   
		            ext = imgName.substr(idx+1).toUpperCase();   
		            ext = ext.toLowerCase( ); 
		            if (ext != 'jpg' && ext != 'png' && ext != 'jpeg'){
		                layer.msg("只能上传.jpg  .png  .jpeg类型的文件!");
		                return false;  
		            }   
		        } else {  
		        	layer.msg("只能上传.jpg  .png  .jpeg类型的文件!");
		            return false;
		        }   
		    }
			return true;
		},
		excelExport:function(obj){
			layer.confirm('您确定要导出excel？', {
    		  	btn: ['导出','取消']
    		}, function(index){
    			window.location.href="/kd/loading/exportLoadlist?"+obj.searchParam;
    			layer.close(index);
    		}, function(){});
		},
		print:function(){
			var loadId = $(this).parents("tr").attr("data");
			window.open("/kd/print/loadprintIndex?loadId="+loadId);
		}
};
$(function(){
	new ShipLoadList();
})
})(jQuery);	
	
	


