package com.supyuan.front.logisticspark;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "logistics_park")
public class LogisticsPark extends BaseProjectModel<LogisticsPark> {

	private static final long serialVersionUID = 1L;
	
	public static final LogisticsPark dao = new LogisticsPark();

}
