package com.supyuan.front.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.front.company.ShippingOrder;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;

/**
 * 订单信息
 * 
 * @author liangxp
 *
 * Date:2017年7月5日下午3:30:08 
 * 
 * @email liangxp@anfawuliu.com
 */
public class OrderSvc extends BaseService {

	/**
	 * 订单详情
	 * @author liangxp
	 * Date:2017年7月5日下午5:38:10 
	 *
	 * @param orderId
	 * @return
	 */
	public List<ShippingOrder> getShippingOrderById(int orderId) {
		List<ShippingOrder> orders;
		orders = ShippingOrder.dao
				.find("SELECT id, order_number, getBookRegion(from_addr_uuid) fromAddr, getBookRegion(to_addr_uuid) toAddr, getBookLongRegion(from_addr_uuid) fromLongAddr, getBookLongRegion(to_addr_uuid) toLongAddr, sender, sender_mobile, receiver, receiver_mobile,  create_time, line_id"
						+ " FROM shipping_order where id=?", orderId);
		return orders;
	}
	
	/**
	 * 订单列表
	 * @author liangxp
	 * Date:2017年7月5日下午5:37:45 
	 *
	 * @param paginator
	 * @param userId
	 * @param status
	 * @param orderby
	 * @return
	 */
	public Page<ShippingOrder> getShippingOrders(int type, Paginator paginator, int userId, int status,String startTime, String endTime, String orderby) {
		Page<ShippingOrder> orders;
		StringBuilder bu = new StringBuilder("FROM shipping_order o left join logistics_line l on o.line_id=l.id where  o.ship_status=? ");
		if(type==0){
			bu.append(" and ((o.user_id="+userId+" and o.is_live_from=1) or (line_user_id="+userId+" and is_live_to=1)) ");
		}else{
			bu.append(" and o.user_id="+userId+" and o.is_live_from=1 ");
		}
		List<Object> args = new ArrayList<Object>();
		args.add(status);
		if(StringUtils.isNotEmpty(startTime)){
			bu.append(" and o.create_time>=?");
			args.add(startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			bu.append(" and o.create_time<=?");
			args.add(endTime + " 23:59:59");
		}
		
		if(StringUtils.isNotEmpty(orderby)){
			bu.append(orderby);
		}
		
		orders = ShippingOrder.dao.paginate(paginator,
				"SELECT o.id, o.order_number,o.ship_status,o.send_status, getshopmdimg(o.user_id) firstImg, getshopmdimg(o.line_user_id) lineFirstImg, getLineRegion(l.from_city_code, l.from_region_code) AS from_city,getLineRegion(l.to_city_code, l.to_region_code) AS to_city, o.create_time, o.line_id,o.all_weight,o.all_volume,getmarkid(o.id, "+userId+") as markid"
						,bu.toString(), args.toArray());
		return orders;
	}
	
	/**
	 * 历史订单总数
	 * @author liangxp
	 * Date:2017年9月25日下午6:03:33 
	 *
	 * @return
	 */
	public long countOrders(){
		return Db.queryLong("select count(*) from shipping_order where ship_status=2");
	}
}
