package com.supyuan.kd.sign;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运单签收
 * @author liangxp
 *
 * Date:2017年12月19日下午6:17:32 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_ship_sign", key="ship_id")
public class KdShipSign extends KdBaseModel<KdShipSign> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdShipSign dao = new KdShipSign();
	
	
	
	
}
