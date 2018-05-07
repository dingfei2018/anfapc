package com.supyuan.kd.abnormal;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 运单异常登记表
 * @author chenan
 * Date:2018年1月17日下午9:41:08 
 */
@ModelBind(table = "kd_abnormal_handle", key = "handle_id")
public class AbnormalHandle extends KdBaseModel<AbnormalHandle> {
	private static final long serialVersionUID = 1L;
	public static final AbnormalHandle dao = new AbnormalHandle();


}
