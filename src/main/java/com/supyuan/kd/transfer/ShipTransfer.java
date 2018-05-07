package com.supyuan.kd.transfer;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 中转运单model
 * @author chenan
 * Date:2017年12月14日下午17:30:08 
 */
@ModelBind(table = "kd_ship_transfer", key = "ship_id")
public class ShipTransfer extends KdBaseModel<ShipTransfer> {
	private static final long serialVersionUID = 1L;
	public static final ShipTransfer dao = new ShipTransfer();


}
