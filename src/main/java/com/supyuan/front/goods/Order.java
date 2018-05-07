package com.supyuan.front.goods;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 订单对应表
 */
@ModelBind(table = "shipping_order", key = "id")
public class Order extends BaseProjectModel<Order> {
	private static final long serialVersionUID = 1L;
	public static final Order dao = new Order();

	public Integer getOrderid() {
		return getInt("id") == null ? -1 : getInt("id");
	}
	

}
