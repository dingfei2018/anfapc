package com.supyuan.front.order;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.company.ShippingOrder;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.message.InMessage;
import com.supyuan.modules.message.InMessageSvc;
/**
 * 订单信息
 * @author liangxp
 *
 * Date:2017年7月10日下午2:55:23 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/front/order")
public class OrderController extends BaseProjectController {
	
	private static final String path = "/pages/front/order/";
	private Long countOfUnRead;
	
	public void index() {
		String para = getPara("type");
		if(StringUtils.isEmpty(para)||!NumberUtils.isNumber(para)){
			para = "1";
		}
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int status = Integer.parseInt(para);
		setAttr("status", status);
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		Page<ShippingOrder> shippingOrders = dataList(0, status, startTime, endTime);
		setAttr("page", shippingOrders);

		setAttr("curr", 23);
		fillMessage();
		renderJsp(path + "orderList.jsp");
	}
	
	/**
	 * 已发的货
	 * @author liangxp
	 * Date:2017年8月23日下午3:39:28 
	 *
	 */
	public void hasGoods() {
		String para = getPara("type");
		if(StringUtils.isEmpty(para)||!NumberUtils.isNumber(para)){
			para = "1";
		}
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int status = Integer.parseInt(para);
		setAttr("status", status);
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		setAttr("curr", 3);
		setAttr("type", para);
		Page<ShippingOrder> shippingOrders = dataList(1, status, startTime, endTime);
		setAttr("page", shippingOrders);
		renderJsp("/pages/front/personal/delivery.jsp");
	}
	
	
	/**
	 * 
	 * @author liangxp
	 * Date:2017年9月2日下午3:06:09 
	 *
	 * @param type 0:订单列表 1:已发的货
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<ShippingOrder> dataList(int type, int status, String startTime, String endTime){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<ShippingOrder> shippingOrders = new OrderSvc().getShippingOrders(type, paginator, user.getUserId(), status, startTime, endTime, "order by create_time desc");
		return shippingOrders;
	}
	
	
	public void jsonlist(){
		String para = getPara("type");
		if(StringUtils.isEmpty(para)||!NumberUtils.isNumber(para)){
			para = "1";
		}
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int status = Integer.parseInt(para);
		setAttr("status", status);
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		Page<ShippingOrder> shippingOrders = dataList(0, status, startTime, endTime);
		renderJson(shippingOrders);
	}
	
	
	public void djsonlist(){
		String para = getPara("type");
		if(StringUtils.isEmpty(para)||!NumberUtils.isNumber(para)){
			para = "1";
		}
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int status = Integer.parseInt(para);
		setAttr("status", status);
		setAttr("startTime", startTime);
		setAttr("endTime", endTime);
		Page<ShippingOrder> shippingOrders = dataList(1, status, startTime, endTime);
		renderJson(shippingOrders);
	}
	
	/**
	 * yangry
	 * 2017年9月2日
	 */
	private void fillMessage() {
		SessionUser user = getSessionSysUser();
		int user_id =0;
        //获得用户id
		if(user!=null){
		 user_id = user.getUserId(); 
		}
		System.out.print("*******________user_id"+user_id);
 
		Paginator paginator = getPaginator();
		paginator.setPageSize(5);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<InMessage> inMessage = new InMessageSvc().getUnReadMessagePages(paginator, user_id);
		countOfUnRead = new InMessageSvc().countReadMessage(user_id);
		setAttr("unreadcount",countOfUnRead);

		setAttr("anotherpage", inMessage);
		
	}
	
}
