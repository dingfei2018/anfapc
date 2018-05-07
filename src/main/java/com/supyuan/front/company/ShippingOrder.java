package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运输订单
 * 
 * @author liangxp
 *
 * Date:2017年6月29日下午1:57:29 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "shipping_order")
public class ShippingOrder extends BaseProjectModel<ShippingOrder> {

	private static final long serialVersionUID = 1L;
	
	public static final ShippingOrder dao = new ShippingOrder();

}
