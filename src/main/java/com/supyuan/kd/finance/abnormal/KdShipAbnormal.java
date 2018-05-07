package com.supyuan.kd.finance.abnormal;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 *
 *运单异动实体类
 */
@ModelBind(table = "kd_ship_abnormal")
public class KdShipAbnormal extends KdBaseModel<KdShipAbnormal> {

	private static final long serialVersionUID = 1L;
	
	public static final KdShipAbnormal dao = new KdShipAbnormal();

	
	
}
