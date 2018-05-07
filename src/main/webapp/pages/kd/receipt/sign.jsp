<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签收</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/sign.css" />
    <%@ include file="../common/commonhead.jsp" %>
    <link rel="stylesheet" href="${ctx}/static/pc/lightbox2/css/lightbox.min.css">
    <script src="${ctx}/static/pc/lightbox2/js/lightbox-plus-jquery.min.js"></script>

</head>
<style>
.newimgs{
	width: 204px;
	height: 140px;
    float: left;
    margin-top: 30px;
    margin-left: 15px;
}
    .newimgss{
        width: 204px;
        height: 140px;
    }
</style>
<body>
   <div class="maip" style="border:1px solid #fff;">
          <p>签收图片</p>
          <div class="maip-list">
            <table>
               <tr>
                  <td colspan="3"><b>运单信息 :</b></td>
               </tr>
               <tr>
                  <td>运单号 : ${kdShip.ship_sn}</td>
                  <td>开单时间 : <fmt:formatDate value="${kdShip.create_time}" pattern="yyyy-MM-dd"/></td>
                  <td>开单网点 : ${kdShip.netWorkName}</td>
               </tr>
               <tr>
                  <td>托运方 : ${kdShip.senderName}</td>
                  <td>收货方 : ${kdShip.receiverName}</td>
                  <td>出发地 : ${kdShip.fromAdd}</td>
               </tr>
               <tr>
                  <td colspan="3">到达地 : ${kdShip.toAdd}</td>
               </tr>
            </table>
          </div>
          <div class="maip-list2">
              <b>运单信息 :</b>
              <c:forEach items="${libImages}" var="img">
                 <div class="newimgs"> <a  href="${img.img}"  rel="lightbox"> <img class="newimgss"  src="${img.img }"> </a></div>
              </c:forEach>
              
              <c:if test="${fn:length(libImages)==0}">
                 <img src="${ctx}/static/kd/img/pic-iu.png" data="old"/>
              </c:if>
          </div>
       <form onsubmit="return false;" id="searchFrom">
       <input type="hidden" name="shipId" value="${kdShip.ship_id}">
        <input type="hidden" id="gids" name="gid" value="${imgGid}">
          <div class="maip-list3">
          
              <div class="gtu1">  
				      <div class="gtu2">
							<img class="gtu2-img"  style="width: 204px;height: 140px;"  src="${ctx}/static/kd/img/pic-iu.png">
				      </div>
				      <div class="gtu3">
				      	<input type="hidden" data="img" name="filename1">
				        <img class="imgs" src="${ctx}/static/kd/img/add00.png">
                          <a href="javascript:;" id="file">上传电脑中图片
                              <input type="file" name="" id="">
						 </a>   
				      </div>
				   </div>
				   
				   <button class="button" onclick="addtr();return false;" >继续添加</button>
				   <button class="button" onclick="removetr();return false;" style="margin-left: 15px;">删除</button>
          </div>
          
          <button class="buttons" onclick="sign()">提交</button> <%--<a href="#" style="margin-left: 15px;">返回</a>--%>
       </form>
   </div>
   <script type="text/javascript">
       var otr = $(".gtu1").eq(0).clone();
       function addtr(){
           var tr = otr.clone();
           var size = $("input[data='img']").length+1;
           tr.find("input[data='img']").attr("name", "filename"+size);
           tr.appendTo(".maip-list3");
       }
       function removetr(){
           var div=$(".gtu1");
           var length=div.length;
           if(length==1){

           }else{
               div[length-1].remove();
           }
       }

       $(".maip-list3").on("change",  "input[type='file']", upload);
       function upload(){
           $this = this;
           var img = $(this).parents(".gtu1").find(".gtu2").find("img");
           var formData = new FormData(); // FormData 对象
           var file = $(this)[0].files[0];
           if(!validate(file)){
               return;
           }
           formData.append("fileName", file);
           $.ajax({
               url : "/file/uploadImage?imgType=sign",
               type : 'POST',
               contentType: false,    //不可缺
               processData: false,    //不可缺
               data : formData,
               success:function(data){
                   if(data.success==true){
                       $(img).attr("src",data.src);
                       $(img).wrap("<a class=\"example-image-link\" href='"+data.src+"' data-lightbox=\"example-1\">");
                       $($this).parents(".gtu1").find("input[type='hidden']").val(data.src);
                   }else{
                       layer.msg(data.message);
                   }

               }
           });
       }
       function validate(file){
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
       }

       function sign(){
           var flag = false;
           if(flag){
               return;
           }
         if($('input[name="filename1"]')[0].value.length==0){
             layer.msg("请选择上传图片");
             return;
         }
           var param = $("#searchFrom").serialize();
           $.ajax({
               type:'GET',
               url:'/kd/collectionReceipt/uploadImages',
               data:param,
               success:function(data){
                   if(data.success==true){
                       layer.msg("保存成功");
                       setTimeout(function(){parent.layer.closeAll();}, 1000);
                   }else{
                       layer.msg("保存失败");
                   }
               }
           });
       }

   </script>
</body>

</html>