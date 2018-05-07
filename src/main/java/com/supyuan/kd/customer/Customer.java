package com.supyuan.kd.customer;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 客户资料model
 * @author chenan
 * Date:2017年11月14日上午10:30:08 
 */
@ModelBind(table = "kd_customer", key = "customer_id")
public class Customer extends KdBaseModel<Customer> {
	private static final long serialVersionUID = 1L;
	public static final Customer dao = new Customer();


}
