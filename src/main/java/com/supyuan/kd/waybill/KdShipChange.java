package com.supyuan.kd.waybill;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运单修改删除变更实体类
 * @author chenan
 * Date:2018年3月16日上午9:46:54
 */
@ModelBind(table = "kd_ship_change",key = "id")
public class KdShipChange extends KdBaseModel<KdShipChange> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdShipChange dao = new KdShipChange();
	

}
