package com.supyuan.kd.transfer;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 中转方model
 * @author chenan
 * Date:2017年12月15日下午17:30:08 
 */
@ModelBind(table = "kd_transfer", key = "transfer_id")
public class Transfer extends KdBaseModel<Transfer> {
	private static final long serialVersionUID = 1L;
	public static final Transfer dao = new Transfer();


}
