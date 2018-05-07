package com.supyuan.kd.track;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 货物物流信息
 * @author liangxp
 *
 * Date:2017年12月12日下午1:53:09 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "kd_ship_track", key = "track_id")
public class KdShipTrack extends KdBaseModel<KdShipTrack> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final KdShipTrack dao = new KdShipTrack();
	
	public static final String MODEL_SHIP_CREATE = "开单";
	public static final String MODEL_SHIP_UPDATE="改单";
	public static final String MODEL_SHIP_LOAD_TIHUO= "提货配载";
	public static final String MODEL_SHIP_LOAD_DUANBO= "短驳配载";
	public static final String MODEL_SHIP_LOAD_GANXIAN= "干线配载";
	public static final String MODEL_SHIP_LOAD_SONGHUO= "送货配载";
	public static final String MODEL_SHIP_CONFIRM = "到货确认";
	public static final String MODEL_SHIP_TRANSFER_SHOUHUO = "运单收货中转";
	public static final String MODEL_SHIP_TRANSFER_DAOHUO = "运单到货中转";
	public static final String MODEL_SHIP_SIGN = "客户签收";
	public static final String MODEL_SHIP_HD = "回单";
	public static final  String  MODEL_SHIP_LOAD_UNLOAD="完成卸车";
	
	public static final int MODEL_SHIP_GPS_NUM = 1;
	public static final int MODEL_SHIP_CREATE_NUM = 2;
	public static final int MODEL_SHIP_LOAD_NUM= 3;
	public static final int MODEL_SHIP_CONFIRM_NUM = 4;
	public static final int MODEL_SHIP_TRANSFER_NUM = 5;
	public static final int MODEL_SHIP_SIGN_NUM = 6;
	public static final int MODEL_SHIP_HD_NUM = 7;
	public static final int MODEL_SHIP_UPDATE_NUM = 8;
	public static  final int MODEL_SHIP_LOAD_UNLOAD_NUM=9;


	
}
