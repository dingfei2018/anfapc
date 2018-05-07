package com.supyuan.system.common.logistics;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "logistics_park")
public class Syslogistics extends BaseProjectModel<Syslogistics> {

	private static final long serialVersionUID = 1L;
	public static final Syslogistics dao = new Syslogistics();

}
