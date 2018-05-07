package com.supyuan.kd.loading;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "kd_ship_arrival")
public class KdShipArrival extends BaseProjectModel<KdShipArrival>  {
	private static final long serialVersionUID = 1L;
	public static final KdShipArrival dao=new KdShipArrival();

}
