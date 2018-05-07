package com.supyuan.kd.network;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 网点model
 * @author chenan
 * Date:2018年1月11日上午9:41:08 
 */
@ModelBind(table = "logistics_network", key = "id")
public class NetWork extends KdBaseModel<NetWork> {
	private static final long serialVersionUID = 1L;
	public static final NetWork dao = new NetWork();


}
