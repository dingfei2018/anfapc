package com.supyuan.kd.goods;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 货物
 * @author liangxp
 *
 * Date:2017年11月14日下午2:13:25 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_product",key="product_id")
public class KdProduct extends KdBaseModel<KdProduct> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdProduct dao = new KdProduct();
	
	
	
	
}
