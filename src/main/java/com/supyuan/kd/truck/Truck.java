package com.supyuan.kd.truck;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 运输车/货车model
 * @author chenan
 * Date:2017年11月16日下午17:30:08 
 */
@ModelBind(table = "truck", key = "truck_id")
public class Truck extends KdBaseModel<Truck> {
	private static final long serialVersionUID = 1L;
	public static final Truck dao = new Truck();


}
