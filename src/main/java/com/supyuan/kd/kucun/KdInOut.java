package com.supyuan.kd.kucun;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 出入库表
 * @author liangxp
 *
 * Date:2018年1月12日上午11:26:33 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_in_out")
public class KdInOut extends KdBaseModel<KdInOut> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdInOut dao = new KdInOut();
	
	
	
	
}
