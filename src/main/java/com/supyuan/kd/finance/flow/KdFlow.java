package com.supyuan.kd.finance.flow;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 *
 *收支流水实体类
 */
@ModelBind(table = "kd_flow")
public class KdFlow extends KdBaseModel<KdFlow> {

	private static final long serialVersionUID = 1L;
	
	public static final KdFlow dao = new KdFlow();

	
	
}
