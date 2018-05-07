package com.supyuan.kd.finance;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运单费用实体类
 * @author chenan
 * Date:2018年2月27日下午17:30:08
 */
@ModelBind(table = "kd_ship_fee", key = "id")
public class KdShipFee extends KdBaseModel<KdShipFee> {
	private static final long serialVersionUID = 1L;
	public static final KdShipFee dao = new KdShipFee();


}
