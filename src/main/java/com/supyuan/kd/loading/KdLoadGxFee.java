package com.supyuan.kd.loading;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 干线配载费
 * @author liangxp
 *
 * Date:2018年2月9日下午2:01:49 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_load_gx_fee")
public class KdLoadGxFee extends KdBaseModel<KdLoadGxFee> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdLoadGxFee dao = new KdLoadGxFee();
	
	
	
	
}
