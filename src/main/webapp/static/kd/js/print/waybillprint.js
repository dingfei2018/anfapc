	
		function getData(shipId){
			var data=[];
			$.ajax({
				type:'post',
				url:'/kd/print/getwayBillprintData?shipId='+shipId,
				async: false,
				dataType:'json',
				success:function(result){
					data=result;
					console.log(data);
				}
			});
			return data;
		}

		function createPrintPage(shipId){
			LODOP=getLodop();  
			LODOP.SET_PRINT_PAGESIZE(1,2100,1480,"");
			//LODOP.ADD_PRINT_HTML(0,0,"100%","100%",document.getElementById("doc").innerHTML);	
			//LODOP.SET_PRINT_STYLE("PenWidth",1);
			//LODOP.ADD_PRINT_LINE(53,23,52,725,0,1);
			var data=getData(shipId);
			console.log("aa:"+data);
			LODOP.PRINT_INITA(-8,-10,"100%","100%","运单打印");
			LODOP.SET_PRINT_STYLE("FontSize",12);
			var kdShip=data.kdShip;
			//制单日期 运单号
			LODOP.ADD_PRINT_TEXT("15mm","130mm","45mm","100%",kdShip.create_time);
			LODOP.ADD_PRINT_TEXT("15mm","173mm","35mm","100%",kdShip.ship_sn);
			
			if(kdShip.ship_is_dispatch==0){
				LODOP.ADD_PRINT_TEXT("15mm","46mm","35mm","100%","√");	
			}
			if(kdShip.ship_is_dispatch==1){
				LODOP.ADD_PRINT_TEXT("15mm","32mm","35mm","100%","√");	
			}
			
			
			var time=kdShip.create_time;
			var timearray=time.split("-");
			LODOP.ADD_PRINT_TEXT("24mm","90mm","35mm","100%",timearray[0]);//年
			LODOP.ADD_PRINT_TEXT("24mm","103mm","35mm","100%",timearray[1]);//月
			LODOP.ADD_PRINT_TEXT("24mm","113mm","35mm","100%",timearray[2].substring(0,timearray[2].indexOf(" ")));//日
			
			var senderCustomer=data.senderCustomer;
			var receiverCustomer=data.receiverCustomer;
			//起运站
			LODOP.ADD_PRINT_TEXT("24mm","20mm","34mm","100%", (senderCustomer.to_addr).replace(/\s+/g,""));
			//到达站
			LODOP.ADD_PRINT_TEXT("24mm","65mm","34mm","100%",(receiverCustomer.to_addr).replace(/\s+/g,""));
			
			//承运人
			LODOP.ADD_PRINT_TEXT("104mm","75mm","23mm","100%",kdShip.userName);
			
			//托运人信息
			LODOP.ADD_PRINT_TEXT("33mm","40mm","35mm","100%",senderCustomer.customer_name);
			LODOP.ADD_PRINT_TEXT("33mm","80mm","70mm","100%",(senderCustomer.to_addr+senderCustomer.tail_address).replace(/\s+/g,""));
			LODOP.ADD_PRINT_TEXT("33mm","170mm","40mm","100%",senderCustomer.customer_mobile);
			//收货人信息
			LODOP.ADD_PRINT_TEXT("42mm","40mm","35mm","100%",receiverCustomer.customer_name);
			LODOP.ADD_PRINT_TEXT("42mm","80mm","70mm","100%",(receiverCustomer.to_addr+receiverCustomer.tail_address).replace(/\s+/g,""));
			LODOP.ADD_PRINT_TEXT("42mm","170mm","40mm","100%",receiverCustomer.customer_mobile);
			
			var kdProducts=data.kdProducts;
			
			var row1=kdProducts[0];
			var row2=kdProducts[1];
			var row3=kdProducts[2];
			var unit1=row1.product_unit==1?"散装":row1.product_unit==2?"捆装":row1.product_unit==3?"袋装":row1.product_unit==4?"箱装":row1.product_unit==5?"桶装":"";
			var unit2=row2.product_unit==1?"散装":row2.product_unit==2?"捆装":row2.product_unit==3?"袋装":row2.product_unit==4?"箱装":row2.product_unit==5?"桶装":"";
			var unit3=row3.product_unit==1?"散装":row3.product_unit==2?"捆装":row3.product_unit==3?"袋装":row3.product_unit==4?"箱装":row3.product_unit==5?"桶装":"";
			//货物信息 row1
			LODOP.ADD_PRINT_TEXT("61mm","6mm","35mm","100%",row1.product_name); //货物名称
			if((row1.product_name).replace(/\s+/g,"").length>0){
			LODOP.ADD_PRINT_TEXT("61mm","36mm","35mm","100%",kdShip.goods_sn); //货号	
			}
			LODOP.ADD_PRINT_TEXT("61mm","65mm","12mm","100%",unit1); //包装
			LODOP.ADD_PRINT_TEXT("61mm","82mm","12mm","100%",row1.product_amount); //件数
			LODOP.ADD_PRINT_TEXT("61mm","95mm","12mm","100%",row1.product_weight); //重量
			LODOP.ADD_PRINT_TEXT("61mm","110mm","12mm","100%",row1.product_volume); //体积
			LODOP.ADD_PRINT_TEXT("61mm","126mm","12mm","100%",row1.product_price); //货值
			//货物信息 row2
			LODOP.ADD_PRINT_TEXT("69mm","6mm","35mm","100%",row2.product_name); //货物名称
			if((row2.product_name).replace(/\s+/g,"").length>0){
			LODOP.ADD_PRINT_TEXT("69mm","36mm","35mm","100%",kdShip.goods_sn); //货号
			}
			LODOP.ADD_PRINT_TEXT("69mm","65mm","12mm","100%",unit2); //包装
			LODOP.ADD_PRINT_TEXT("69mm","82mm","12mm","100%",row2.product_amount); //件数
			LODOP.ADD_PRINT_TEXT("69mm","95mm","12mm","100%",row2.product_weight); //重量
			LODOP.ADD_PRINT_TEXT("69mm","110mm","12mm","100%",row2.product_volume); //体积
			LODOP.ADD_PRINT_TEXT("69mm","126mm","12mm","100%",row2.product_price); //货值
			
		/*	LODOP.ADD_PRINT_TEXT("69mm","141mm","10mm","100%","100"); //运费
			LODOP.ADD_PRINT_TEXT("69mm","156mm","10mm","100%","100"); //保价费
			LODOP.ADD_PRINT_TEXT("69mm","171mm","10mm","100%","100"); //送货费
			LODOP.ADD_PRINT_TEXT("69mm","185mm","10mm","100%","100"); //其他费
			LODOP.ADD_PRINT_TEXT("69mm","200mm","10mm","100%","100"); //合计
*/			
			//货物信息 row3
			LODOP.ADD_PRINT_TEXT("77mm","6mm","35mm","100%",row3.product_name); //货物名称
			if((row3.product_name).replace(/\s+/g,"").length>0){
			LODOP.ADD_PRINT_TEXT("77mm","36mm","35mm","100%",kdShip.goods_sn); //货号
				}
			LODOP.ADD_PRINT_TEXT("77mm","65mm","12mm","100%",unit3); //包装
			LODOP.ADD_PRINT_TEXT("77mm","82mm","12mm","100%",row3.product_amount); //件数
			LODOP.ADD_PRINT_TEXT("77mm","95mm","12mm","100%",row3.product_weight); //重量
			LODOP.ADD_PRINT_TEXT("77mm","110mm","12mm","100%",row3.product_volume); //体积
			LODOP.ADD_PRINT_TEXT("77mm","126mm","12mm","100%",row3.product_price); //货值
			LODOP.ADD_PRINT_TEXT("77mm","141mm","12mm","100%",kdShip.ship_fee); //运费
			LODOP.ADD_PRINT_TEXT("77mm","156mm","12mm","100%",kdShip.ship_insurance_fee); //保价费
			LODOP.ADD_PRINT_TEXT("77mm","171mm","12mm","100%",kdShip.ship_delivery_fee); //送货费
			LODOP.ADD_PRINT_TEXT("77mm","185mm","12mm","100%",kdShip.ship_addon_fee); //其他费
			LODOP.ADD_PRINT_TEXT("77mm","200mm","12mm","100%",kdShip.ship_total_fee); //合计
			/*LODOP.ADD_PRINT_TEXT("77mm","141mm","10mm","100%","100"); //运费
			LODOP.ADD_PRINT_TEXT("77mm","156mm","10mm","100%","100"); //保价费
			LODOP.ADD_PRINT_TEXT("77mm","171mm","10mm","100%","100"); //送货费
			LODOP.ADD_PRINT_TEXT("77mm","185mm","10mm","100%","100"); //其他费
			LODOP.ADD_PRINT_TEXT("77mm","200mm","10mm","100%","100"); //合计*/
				
			var totalFee=changeMoneyToChinese(kdShip.ship_total_fee);
			console.log(totalFee);
			var wan="零";
			var qian="零";
			var bai="零";
			var shi="零";
			var yuan="零";
			if(totalFee.indexOf("万")>0){
				wan=totalFee.substring(0,totalFee.indexOf("万"));
			}
			if(totalFee.indexOf("仟")>0){
				qian=totalFee.substring(totalFee.indexOf("万")+1,totalFee.indexOf("仟"));
			}
			if(totalFee.indexOf("佰")>0){
				bai=totalFee.substring(totalFee.indexOf("仟")+1,totalFee.indexOf("佰"));
			}
			if(totalFee.indexOf("拾")>0){
				shi=totalFee.substring(totalFee.indexOf("佰")+1,totalFee.indexOf("拾"));
			}
			if(totalFee.indexOf("元")>0){
				
				if((totalFee.indexOf("仟元")>0)|(totalFee.indexOf("万元")>0)){
					
				}else{
					yuan=totalFee.substring(totalFee.indexOf("元")-1,totalFee.indexOf("元"));
				}
			
			}
			
			//合计大写金额
			LODOP.ADD_PRINT_TEXT("86mm","45mm","10mm","100%",wan); //万
			LODOP.ADD_PRINT_TEXT("86mm","63mm","10mm","100%",qian); //千
			LODOP.ADD_PRINT_TEXT("86mm","81mm","10mm","100%",bai); //百
			LODOP.ADD_PRINT_TEXT("86mm","98mm","10mm","100%",shi); //十
			LODOP.ADD_PRINT_TEXT("86mm","115mm","10mm","100%",yuan); //元
			
			var payWay=kdShip.ship_pay_way==1?"现付":kdShip.ship_pay_way==2?"提付":kdShip.ship_pay_way==3?"到付":kdShip.ship_pay_way==4?"会单付":
				kdShip.ship_pay_way==5?"月结":kdShip.ship_pay_way==6?"扣付":"";
			//支付方式
			LODOP.ADD_PRINT_TEXT("86mm","168mm","30mm","100%",payWay); 
			console.log(changeMoneyToChinese(kdShip.ship_agency_fund));
			var dFee=changeMoneyToChinese(kdShip.ship_agency_fund);
			var dwan="零";
			var dqian="零";
			var dbai="零";
			var dshi="零";
			var dyuan="零";
			if(dFee.indexOf("万")>0){
				dwan=dFee.substring(0,dFee.indexOf("万"));
			}
			if(dFee.indexOf("仟")>0){
				dqian=dFee.substring(dFee.indexOf("万")+1,dFee.indexOf("仟"));
			}
			if(dFee.indexOf("佰")>0){
				dbai=dFee.substring(dFee.indexOf("仟")+1,dFee.indexOf("佰"));
			}
			if(dFee.indexOf("拾")>0){
				dshi=dFee.substring(dFee.indexOf("佰")+1,dFee.indexOf("拾"));
			}
			if(dFee.indexOf("元")>0){
				if((dFee.indexOf("仟元")>0)|(dFee.indexOf("万元")>0)){
					
				}else{
					dyuan=dFee.substring(dFee.indexOf("元")-1,dFee.indexOf("元"));
				}
			}
			//代收货款
			LODOP.ADD_PRINT_TEXT("95mm","26mm","20mm","100%",dwan); //万
			LODOP.ADD_PRINT_TEXT("95mm","43mm","10mm","100%",dqian); //千
			LODOP.ADD_PRINT_TEXT("95mm","56mm","10mm","100%",dbai); //百
			LODOP.ADD_PRINT_TEXT("95mm","69mm","10mm","100%",dshi); //十
			LODOP.ADD_PRINT_TEXT("95mm","83mm","10mm","100%",dyuan); //元
			LODOP.ADD_PRINT_TEXT("95mm","113mm","25mm","100%",kdShip.ship_agency_fund); //小计
			//备注
			LODOP.ADD_PRINT_TEXT("113mm","65mm","145mm","100%",kdShip.remark); 
			
			LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/static/kd/img/waybillprint3.jpg'>");
			LODOP.SET_SHOW_MODE("BKIMG_WIDTH","215mm");
			LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","139mm"); //这句可不加，因宽高比例固定按原图的
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);	
			
		}
		function printWaybill(shipId){ 
			createPrintPage(shipId);
			LODOP.PREVIEW();	
		}
		function checkIsInstall() {	 
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

	
	
	


