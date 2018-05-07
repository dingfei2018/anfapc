package com.supyuan.kd.product;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 运输货物model
 * @author chenan
 * Date:2017年12月8日下午4:41:08 
 */
@ModelBind(table = "kd_product", key = "product_id")
public class Product extends KdBaseModel<Product> {
	private static final long serialVersionUID = 1L;
	public static final Product dao = new Product();


}
