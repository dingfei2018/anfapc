<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>打印页面</title>
		<%-- <link rel="stylesheet" href="${ctx}/static/kd/css/print.css" /> --%>
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>
		<script src="${ctx}/static/kd/js/print/waybillprint.js"></script>
	</head>
	<body>
	    <div id="doc" style="width: 1200px;height: auto;border: 2px solid;margin: auto;">
	    <div style="width:100%;">
	    <div style="background: #fff;position: relative;width:95%">
	        <h1 style="text-align: center;">${userCompany.corpname }托运单</h1>
	        <p style="margin-top: -30px;" ><b  style="float:left;">托运日期 <span><fmt:formatDate value="${kdShip.create_time }" pattern="yyyy-MM-dd hh:mm:ss"/></span></b> <b style="float:right;">运单号 <span>${kdShip.ship_sn } </span></b></p>
	        <table cellspacing="0" style="width:100%;  border: 1px solid ;margin-top: 8px;text-align: center;border-left: none;border-right: none;">
	            <tr style=" height:30px;">
	               <td rowspan="5" style="width:70px;border: 1px solid ;">托运方</td>
	               <td style="border: 1px solid ;">出发地</td>
	               <td colspan="3" style="border: 1px solid ;">${kdShip.senderAdd }</td>
	               <td rowspan="5" style="width:70px; border: 1px solid ;">收货方</td>
	               <td style="border: 1px solid ;">到达地</td>
	               <td style="border: 1px solid ;">${kdShip.toAdd }</td>
	               <td style="border: 1px solid ;">送货方式</td>
	               <td style="border: 1px solid ;">
	               <c:if test="${kdShip.ship_delivery_method==1}">送货上门</c:if>
	               <c:if test="${kdShip.ship_delivery_method==2}">自提</c:if>
	               <c:if test="${kdShip.ship_delivery_method==3}">送货上楼</c:if>
	               </td>
	            </tr>
	            <tr style=" height:30px;">
	               <td style="border: 1px solid ;">姓名</td>
	               <td colspan="3" style="border: 1px solid ;">${senderCustomer.customer_name }</td>
	               <td style="border: 1px solid ;">姓名</td>
	               <td colspan="3" style="border: 1px solid ;">${receiverCustomer.customer_name}</td>
	            </tr>
	             <tr style=" height:30px;">
	               <td style="border: 1px solid ;">公司名称</td>
	               <td colspan="3" style="border: 1px solid ;">${senderCustomer.customer_corp_name }</td>
	               <td style="border: 1px solid ;">公司名称</td>
	               <td colspan="3" style="border: 1px solid ;">${receiverCustomer.customer_corp_name}</td>
	            </tr>
	            <tr style=" height:30px;">
	               <td style="border: 1px solid ;">联系电话</td>
	               <td colspan="3" style="border: 1px solid ;">${senderCustomer.customer_mobile }</td>
	               <td style="border: 1px solid ;">联系电话</td>
	               <td colspan="3" style="border: 1px solid ;">${receiverCustomer.customer_mobile }</td>
	            </tr>
	            <tr style=" height:30px;">
	               <td style="border: 1px solid ;">详细地址</td>
	               <td colspan="3" style="border: 1px solid ;">${senderCustomer.tail_address }</td>
	               <td style="border: 1px solid ;">详细地址</td>
	               <td colspan="3" style="border: 1px solid ;">${receiverCustomer.tail_address }</td>
	            </tr>
	            <tr style=" height:30px;">
	               <td style="border: 1px solid ;">货物名称</td>
	               <td style="border: 1px solid ;">包装单位</td>
	               <td style="border: 1px solid ;">件数</td>
	               <td style="border: 1px solid ;">体积</td>
	               <td style="border: 1px solid ;">重量</td>
	               <td style="border: 1px solid ;">货值</td>
	               <td style="border: 1px solid ;">运费</td>
	               <td style="border: 1px solid ;">${kdShip.ship_fee }</td>
	               <td style="border: 1px solid ;">提货费</td>
	               <td style="border: 1px solid ;">${kdShip.ship_pickup_fee }</td>
	            </tr>
	            
	             <c:forEach items="${kdProducts}" var="product" varStatus="vs">
	            <tr style=" height:30px;">
	               <td style="border: 1px solid ;">${product.product_name}</td>
	               <td style="border: 1px solid ;">
	                <c:if test="${product.product_unit==1}">散装</c:if>
	                <c:if test="${product.product_unit==2}">袋装</c:if>
	                <c:if test="${product.product_unit==3}">箱装</c:if>
	                <c:if test="${product.product_unit==4}">桶装</c:if>
	               </td>
	               <td style="border: 1px solid ;">${product.product_amount}</td>
	               <td style="border: 1px solid ;">${product.product_volume}</td>
	               <td style="border: 1px solid ;">${product.product_weight}</td>
	               <td style="border: 1px solid ;">${product.product_price}</td>
	               <c:if test="${vs.index==0}"><td style="border: 1px solid ;">送货费</td></c:if>
	               <c:if test="${vs.index==0}"><td style="border: 1px solid ;">${kdShip.ship_delivery_fee }</td></c:if>
	               <c:if test="${vs.index==0}"><td style="border: 1px solid ;">保价费</td></c:if>
	               <c:if test="${vs.index==0}"><td style="border: 1px solid ;">${kdShip.ship_insurance_fee }</td></c:if>
	               <c:if test="${vs.index==1}"><td style="border: 1px solid ;">包装费</td></c:if>
	               <c:if test="${vs.index==1}"><td style="border: 1px solid ;">${kdShip.ship_package_fee }</td></c:if>
	               <c:if test="${vs.index==1}"><td style="border: 1px solid ;">其他费</td></c:if>
	               <c:if test="${vs.index==1}"><td style="border: 1px solid ;">${kdShip.ship_addon_fee }</td></c:if>
	                <c:if test="${vs.index==2}"><td style="border: 1px solid ;">合计</td></c:if>
	               <c:if test="${vs.index==2}"><td style="border: 1px solid ;">${kdShip.ship_total_fee }</td></c:if>
	               <c:if test="${vs.index==2}"><td style="border: 1px solid ;">付款方式</td></c:if>
	               <c:if test="${vs.index==2}"><td style="border: 1px solid ;">
	                <c:if test="${kdShip.ship_pay_way==1}">现付</c:if>
	                <c:if test="${kdShip.ship_pay_way==2}">提付</c:if>
	                <c:if test="${kdShip.ship_pay_way==3}">到付</c:if>
	                <c:if test="${kdShip.ship_pay_way==4}">会单付</c:if>
	                <c:if test="${kdShip.ship_pay_way==5}">月结</c:if>
	                <c:if test="${kdShip.ship_pay_way==6}">扣付</c:if>
	               </td></c:if>
	            </tr>
	            </c:forEach>
	            <tr style=" height:30px;">
	               <td style="border: 1px solid ;">代收货款</td>
	               <td style="border: 1px solid ;">￥${ kdShip.ship_agency_fund}<input type="hidden" value="${ kdShip.ship_agency_fund}" id="ship_agency_fund"></td>
	               <td style="border: 1px solid ;">大写</td>
	               <td colspan="3" style="border: 1px solid ;"><span id="newagency"></span></td>
	               <td style="border: 1px solid ;">大写</td>
	               <td colspan="3" style="border: 1px solid ;"><span id="newtotalfee"><input type="hidden" value="${ kdShip.ship_total_fee}" id="ship_total_fee"></span></td>
	            </tr>
	            <tr style=" height:30px;">
	               <td rowspan="4" style="border: 1px solid ;">注意事项</td>
	               <td colspan="9" style="border: 1px solid ;  text-align: left;">${set.value }</td>
	            </tr>
	            
	        </table>
	        <div> <!-- style=" margin-top: 10px;" -->
	              <ul style="overflow: hidden; margin-left: -35px;margin-top: 5px;">
	                 <li style="float:left;list-style:none; ">制单人 : ${kdShip.userName }</li>
	                 <li style="float:left;list-style:none;margin-left: 50px; " ">托运方签字 : </li>
	                 <li style="float:left;list-style:none;margin-left: 50px; " ">收货方签字盖章 : </li>
	                 <li style="float:left;list-style:none;margin-left: 50px; " ">收货方身份证号 : </li>
	              </ul>
	              <ul style="overflow: hidden; margin-left: -35px; margin-top: -10px;">
	                 <li  style="float:left;list-style:none;">公司地址 : ${kdShip.netAddr }</li>
	                 <li  style="float:left;list-style:none;margin-left: 300px; " ">联系电话 : ${kdShip.netTelPhone }</li>
	              </ul>
	        </div>
	        <div style="width: 8mm;height: 127mm;position: absolute;right:-38px;top: 30px; text-align: center;">
	              ① 存根(白)  <br><br>② 托运方(红) <br><br>③ 收货方签收回单(黄)
	         </div>
	      </div> 
	      </div>
	    </div>
	        <button style="margin-left: 500px;width: 120px;height: 40px;margin-top: 50px;font-size: 18px;color: #fff;border: none;background-color: #3974f8;cursor: pointer;" id="print">打印</button>
	        <button onclick="window.location.href='${ctx}/kd/waybill'" style="margin-left:25px;">返回</button>
	    
	    <script type="text/javascript">
	    console.log(document.getElementById("doc").innerHTML);
	    changeMoey();
	    function changeMoey (){ 
	    	var agency=$('#ship_agency_fund').val();
	    	var totalfee=$('#ship_total_fee').val();
	    	var newagency=changeMoneyToChinese(agency);
	    	var newtotalfee=changeMoneyToChinese(totalfee);
	    	$('#newagency').html(newagency);
	    	$('#newtotalfee').html(newtotalfee);
	    }
	    
	    function changeMoneyToChinese(money){  
            var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字  
            var cnIntRadice = new Array("","拾","佰","仟"); //基本单位  
            var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位  
            var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位  
            //var cnInteger = "整"; //整数金额时后面跟的字符  
            var cnIntLast = "元"; //整型完以后的单位  
            var maxNum = 999999999999999.9999; //最大处理的数字  
              
            var IntegerNum; //金额整数部分  
            var DecimalNum; //金额小数部分  
            var ChineseStr=""; //输出的中文金额字符串  
            var parts; //分离金额后用的数组，预定义  
            if( money == "" ){  
                return "";  
            }  
            money = parseFloat(money);  
            if( money >= maxNum ){  
                $.alert('超出最大处理数字');  
                return "";  
            }  
            if( money == 0 ){  
                //ChineseStr = cnNums[0]+cnIntLast+cnInteger;  
                ChineseStr = cnNums[0]+cnIntLast  
                //document.getElementById("show").value=ChineseStr;  
                return ChineseStr;  
            }  
            money = money.toString(); //转换为字符串  
            if( money.indexOf(".") == -1 ){  
                IntegerNum = money;  
                DecimalNum = '';  
            }else{  
                parts = money.split(".");  
                IntegerNum = parts[0];  
                DecimalNum = parts[1].substr(0,4);  
            }  
            if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换  
                zeroCount = 0;  
                IntLen = IntegerNum.length;  
                for( i=0;i<IntLen;i++ ){  
                    n = IntegerNum.substr(i,1);  
                    p = IntLen - i - 1;  
                    q = p / 4;  
                    m = p % 4;  
                    if( n == "0" ){  
                        zeroCount++;  
                    }else{  
                        if( zeroCount > 0 ){  
                            ChineseStr += cnNums[0];  
                        }  
                        zeroCount = 0; //归零  
                        ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];  
                    }  
                    if( m==0 && zeroCount<4 ){  
                        ChineseStr += cnIntUnits[q];  
                    }  
                }  
                ChineseStr += cnIntLast;  
                //整型部分处理完毕  
            }  
            if( DecimalNum!= '' ){//小数部分  
                decLen = DecimalNum.length;  
                for( i=0; i<decLen; i++ ){  
                    n = DecimalNum.substr(i,1);  
                    if( n != '0' ){  
                        ChineseStr += cnNums[Number(n)]+cnDecUnits[i];  
                    }  
                }  
            }  
            if( ChineseStr == '' ){  
                //ChineseStr += cnNums[0]+cnIntLast+cnInteger;  
                ChineseStr += cnNums[0]+cnIntLast;  
            }/* else if( DecimalNum == '' ){ 
                ChineseStr += cnInteger; 
                ChineseStr += cnInteger; 
            } */  
            return ChineseStr;  
        }  
	    
	    
	    
	    </script>
	</body>
</html>
