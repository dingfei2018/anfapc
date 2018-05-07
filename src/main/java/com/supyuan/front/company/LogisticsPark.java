package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 物流园信息
 * 
 * @author dingfei <2017年7月13日>
 *
 */
@ModelBind(table = "logistics_park")
public class LogisticsPark extends BaseProjectModel<LogisticsPark> {
	private static final long serialVersionUID = 1L;
	public static final LogisticsPark dao = new LogisticsPark();

}
