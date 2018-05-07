package com.supyuan.kd.waybill;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 开单实体类
 * @author liangxp
 *
 * Date:2017年11月14日上午9:46:54 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_ship",key = "ship_id")
public class KdShip extends KdBaseModel<KdShip> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdShip dao = new KdShip();
	

}
