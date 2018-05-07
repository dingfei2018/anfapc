package com.supyuan.kd.loading;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运单配载表
 * @author liangxp
 *
 * Date:2017年11月13日上午10:24:31 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_truck_load", key="load_id")
public class KdTrunkLoad extends KdBaseModel<KdTrunkLoad> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdTrunkLoad dao = new KdTrunkLoad();
	
	
	
	
}
