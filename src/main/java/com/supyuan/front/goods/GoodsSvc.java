package com.supyuan.front.goods;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.front.company.ShippingOrder;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.system.user.SysUser;
/**
 * 订单信息
 * 
 * @author liangxp
 *
 * Date:2017年7月5日下午3:30:08 
 * 
 * @email liangxp@anfawuliu.com
 */
public class GoodsSvc extends BaseService {

	private final static Log log = Log.getLog(GoodsSvc.class);

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
	public Page<ShippingOrder> getShippingOrders(Paginator paginator, int userId, int status, String orderby) {
		Page<ShippingOrder> orders;
		orders = ShippingOrder.dao.paginate(paginator,
				"SELECT id, order_number, getRegion(from_addr_uuid) fromAddr, getRegion(to_addr_uuid) toAddr, create_time, line_id"
						,"FROM shipping_order where user_id=? and ship_status=" + orderby, userId, status);
		return orders;
	}
	
	
	
	/**
	 * 查询一条订单
	 * @author chendekang
	 * Date:2017年7月5日下午5:37:45 
	 * @param user 
	 * @param order
	 * @return
	 */
	public List<Goods> getOrdergoods( SysUser user) {
		List<Order> order = Order.dao.find("SELECT o.id,o.from_addr_uuid,o.to_addr_uuid"
				+ " FROM shipping_order o WHERE o.user_id="+user.getUserid()+" AND o.send_status=1");
		
		List<Goods> list = new ArrayList<Goods>();
		
		Goods ordergoods=null;
		if (order != null && order.size() > 0) {
			for (Order o : order) {
				ordergoods = Goods.dao
						.findFirst("SELECT o.*,getRegion(from_addr) as from_addr,getRegion(to_addr) as to_addr,b.good_name,b.weight,b.weight_unit,b.volume "
								+ " FROM shipping_order o INNER JOIN shipping_goods b ON o.id=b.shipping_order_id WHERE b.shipping_order_id="
								+ o.getInt("id"));
				
				list.add(ordergoods);
			}
		}
		return list;
	}
	
	/**
	 * 我的常发货物带分页
	 */
	public Page<Order> getOrdergoods(Paginator paginator, Integer userId) {
		Page<Order> orders;
		StringBuilder bu = new StringBuilder("FROM shipping_order where  user_id=? and send_status=1 ");
			bu.append(" order by create_time desc ");
		orders = Order.dao.paginate(paginator,
				"SELECT *, getBookRegion(from_addr_uuid) fromAddr, getBookRegion(to_addr_uuid) toAddr,getshopmdimg(user_id) firstImg"
						,bu.toString(), userId);
		return orders;
	}
	
	
	
	/**
	 * 已发的货
	 * @author liangxp
	 * Date:2017年8月1日下午6:27:33 
	 *
	 * @param paginator
	 * @param userId
	 * @param status
	 * @return
	 */
	public Page<Order> getOrders(Paginator paginator, Integer userId, int status, String orderby) {
		Page<Order> orders;
		StringBuilder bu = new StringBuilder("FROM shipping_order where is_live_from=1 and user_id=? and ship_status=? ");
		if(StringUtils.isNotEmpty(orderby)){
			bu.append(orderby);
		}
		orders = Order.dao.paginate(paginator,
				"SELECT *, getBookRegion(from_addr_uuid) fromAddr, getBookRegion(to_addr_uuid) toAddr"
						,bu.toString(), userId, status);
		return orders;
	}
	
	

	public List<Address> getAddress(Integer orderid, SysUser user) {
		List<Order> order = Order.dao.find("SELECT o.id,o.from_addr_uuid,o.to_addr_uuid"
				+ " FROM shipping_order o WHERE o.user_id="+user.getUserid()+" AND o.send_status=1");
		List<Address> address= new ArrayList<Address>();
		if (order != null && order.size() > 0) {
			for (Order o : order) {
				// 发货地此
				Address fromaddr = Address.dao
						.findFirst("SELECT uuid,getRegion(region_code) AS from_addr FROM address_book b WHERE b.uuid="
								+ o.getStr("from_addr_uuid"));
				// 收货地此
				Address toaddr = Address.dao
						.findFirst("SELECT uuid,getRegion(region_code) AS to_addr FROM address_book b WHERE b.uuid="
								+ o.getStr("to_addr_uuid"));
				address.add(fromaddr);
				address.add(toaddr);
			}
		}
		return address;
	}

	
	/**
	 * 查询订单详情
	 * @author chendekang
	 * Date:2017年7月5日下午5:37:45 
	 * @param user 
	 * @param order
	 * @return
	 */
	public List<Goods> getoftendetails(Integer order) {

		List<Goods> ordergoods=null;
				ordergoods = Goods.dao
						.find("SELECT o.*,getRegion(from_addr) as from_addr,getRegion(to_addr) as to_addr,b.good_name,b.weight,b.amount,b.weight_unit,b.volume "
								+ " FROM shipping_order o INNER JOIN shipping_goods b ON o.id=b.shipping_order_id WHERE b.shipping_order_id="
								+ order);

		return ordergoods;
	
	}

	public Order getorderid(Integer orderid) {
		
		Order order = Order.dao.findFirst("SELECT getshopmdimg(user_id) firstImg, getLineRegion(l.from_city_code, l.from_region_code) AS from_city,getLineRegion(l.to_city_code, l.to_region_code) AS to_city,getCompanyName(l.company_id) corpname,o.*"
				+",getCompamyByUid(o.user_id) from_corpname, getBookLongRegion(o.from_addr_uuid) AS from_addr,getBookLongRegion(o.to_addr_uuid) AS to_addr "
				+ "FROM shipping_order o left JOIN logistics_line l ON o.line_id=l.id WHERE o.id="+orderid);
		
		return order;
	}
}
