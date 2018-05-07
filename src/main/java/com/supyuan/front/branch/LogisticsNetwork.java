package com.supyuan.front.branch;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "logistics_network")
public class LogisticsNetwork extends BaseProjectModel<LogisticsNetwork> {

	private static final long serialVersionUID = 1L;
	public static final LogisticsNetwork dao = new LogisticsNetwork();
}
