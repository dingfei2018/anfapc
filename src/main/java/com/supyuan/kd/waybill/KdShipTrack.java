package com.supyuan.kd.waybill;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 运单物流信息表
 * @author dingfei
 *
 * @date 2017年12月12日 上午10:39:31
 */
@ModelBind(table = "kd_ship_track",key = "track_id")
public class KdShipTrack extends KdBaseModel<KdShipTrack> {

	private static final long serialVersionUID = 1L;
	
	public static final KdShipTrack dao = new KdShipTrack();

}
