package com.supyuan.kd.waybill;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 运单打印设置实体类
 * @author chenan
 * Date:2017年12月11日上午9:46:54 
 */
@ModelBind(table = "kd_setting")
public class KdSetting extends KdBaseModel<KdSetting> {

	/**
	 * 打印设置
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdSetting dao = new KdSetting();
	

}
