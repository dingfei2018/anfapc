package com.supyuan.kd.setting;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 系统设置 实体类
 * @author chenan
 * @date 2018年1月30日 上午午10:04:02
 */
@ModelBind(table = "kd_setting")
public class KdSetting extends KdBaseModel<KdSetting> {
	
	private static final long serialVersionUID = 1L;
	
	public static final KdSetting dao = new KdSetting();
	
	
}
