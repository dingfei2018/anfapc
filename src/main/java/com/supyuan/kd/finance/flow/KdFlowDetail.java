package com.supyuan.kd.finance.flow;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 *
 *收支流水明细实体类
 */
@ModelBind(table = "kd_flow_detail")
public class KdFlowDetail extends KdBaseModel<KdFlowDetail> {

	private static final long serialVersionUID = 1L;
	
	public static final KdFlowDetail dao = new KdFlowDetail();

	
	
}
