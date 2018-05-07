package com.supyuan.kd.loading;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 装载中间表
 * @author liangxp
 *
 * Date:2017年11月13日下午5:01:59 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_truck_ship")
public class KdTrunkShip extends KdBaseModel<KdTrunkShip> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdTrunkShip dao = new KdTrunkShip();
	
	
	
	
}
