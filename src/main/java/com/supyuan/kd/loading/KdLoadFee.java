package com.supyuan.kd.loading;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 分摊费用
 * @author liangxp
 *
 * Date:2017年12月22日下午2:14:29 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_ship_fee")
public class KdLoadFee extends KdBaseModel<KdLoadFee> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdLoadFee dao = new KdLoadFee();
	
}
