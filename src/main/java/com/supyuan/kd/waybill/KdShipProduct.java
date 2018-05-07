package com.supyuan.kd.waybill;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运单货物中间表
 * @author dingfei
 *
 * @date 2017年12月12日 上午10:28:17
 */
@ModelBind(table = "kd_ship_product",key = "ship_id")
public class KdShipProduct  extends KdBaseModel<KdShipProduct> {
	

	private static final long serialVersionUID = 1L;
	
	public static final KdShipProduct dao = new KdShipProduct();

}
