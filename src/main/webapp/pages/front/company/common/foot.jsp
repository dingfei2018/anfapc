<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<!---底部的内容--->
<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/footer.css?v=${version}" />
 <div class="footer">
<div class="footers">
		   <div class="footers-top">
		         <b>友情链接：</b>
		         <ul>
		         	<c:forEach items="${friendlyLinks}" var="friendlyLink"  varStatus="v">
		             	<li ${fn:length(friendlyLinks)==(v.index+1)?"id='li-right'":''}><a href="${friendlyLink.uri_link}" target="_blank">${friendlyLink.uri_label}</a></li>
					</c:forEach>
		         </ul>
		   </div>
		   <div class="footers-top2">
		   
		        <dl>
		            <dt>关于我们</dt>
		            <dd class="footers-top2-dd" ><a href="${ctx}/company/introduction?id=${id}">公司简介</a></dd>
		            <dd><a href="${ctx}/company/contact?id=${id}">联系我们</a></dd>
		        </dl>
		        <dl>
		            <dt>形象展示</dt>
		            <dd class="footers-top2-dd"><a href="${ctx}/company/figure?id=${id}">企业形象</a></dd>
		        </dl>
		        <dl>
		            <dt>网点分布</dt>
		            <dd class="footers-top2-dd"><a href="${ctx}/company/branch?id=${id}">网点分布</a></dd>
		            <dd><a href="${ctx}/company/special?id=${id}">物流专线</a></dd>
		        </dl>
		        <dl>
		            <dt>新闻资讯</dt>
		            <dd class="footers-top2-dd"><a href="${ctx}/company/translate?id=${id}">新闻资讯</a></dd>
		        </dl>
		        <div class="footers-top3">
		           <p>找车源APP</p>
		           <img src="${ctx}/static/pc/img/cheyuan.png"/>
		           <strong class="footers-top3-span">请下载后使用</strong>
		           <span>即将发布</span>
		        </div>
		        <div class="footers-top4">
		           <p>找货源APP</p>
		           <img src="${ctx}/static/pc/img/huoyuan.png"/>
		           <strong class="footers-top4-span">请下载后使用</strong>
		           <span>即将发布</span>
		        </div>
		        <script>
	                $(function(){
	                	$(".footers-top3 p").mouseover(function(){
	                		 
	                		 $("strong").text("即将发布").css("color","red").css("margin-left","10px");
	                	}).mouseout(function(){
	                		
	                		 $("strong").text("请下载后使用").css("color","black").css("margin-left","0px");
	                	});
	                	$(".footers-top3 img").mouseover(function(){
	                		
	                		 $("strong").text("即将发布").css("color","red").css("margin-left","10px");
	                	}).mouseout(function(){
	                		
	                		 $("strong").text("请下载后使用").css("color","black").css("margin-left","0px");
	                	});
	                	
	                	
	                	$(".footers-top4 p").mouseover(function(){
	                		 
	                 		 $("strong").text("即将发布").css("color","red").css("margin-left","12px");
	                	}).mouseout(function(){
	                		
	                		 $("strong").text("请下载后使用").css("color","black").css("margin-left","0px");
	                	});
	                	$(".footers-top4 img").mouseover(function(){
	                		
	                		 $("strong").text("即将发布").css("color","red").css("margin-left","12px");
	                	}).mouseout(function(){
	                		
	                		 $("strong").text("请下载后使用").css("color","black").css("margin-left","0px");
	                	});
	                });
	              </script>
		   </div>
		   
		   <div class="footers-top5">
		      <ul> 
		         <li class="footers-top5-li">${company.corpname}</li>
		         <li>${company.corp_addr}</li>
		         <li class="footers-top5-li">${company.corp_telphone}</li>
		         <li><a href="http://www.2856pt.com">技术支持：安发网络科技有限公司</a></li>
		         <li class="footers-top5-lis">中国最大的供应链一体化方案提供商</li>
		         <li class="footers-top5-lis">Copyright © 2017 网上物流 版权所有</li>
		      </ul>
		      
		   </div>
		   
		</div>
</div>
