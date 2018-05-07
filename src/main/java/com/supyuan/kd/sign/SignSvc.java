package com.supyuan.kd.sign;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.common.ShipStatus;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.StrUtils;


/**
 * 签单服务类
 * @author liangxp
 *
 * Date:2017年11月13日下午5:12:05 
 * 
 * @email liangxp@anfawuliu.com
 */
public class SignSvc  extends BaseService {
	
	
	/**
	 * 签收运单
	 * @author liangxp
	 * Date:2017年11月14日下午4:46:03 
	 *
	 * @param user
	 * @param
	 * @return
	 */
	public boolean signShip(SessionUser user, String gid, SignModel model, List<LibImage> imgs){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdShip signship = new KdShipSvc().findShortShip(model.getShipId()+"");
				if(signship==null){
					return false;
				}
				//保存图片
				boolean res = new LibImageSvc().addLibImages(imgs);
				if(!res)return false;
				Date time = new Date();
				KdShip ship = new KdShip();
				ship.set("ship_id", model.getShipId());
				//ship.set("load_network_id", 0);//清零，不再配载
				ship.set("ship_status", ShipStatus.SIGN.getType());
				ship.set("ship_storage", 2);
				if(model.isHasproxy())ship.set("ship_agency_fund_status", 1);
				if(model.isArrivepay())ship.set("ship_fee_agency", 1);
				ship.set("ship_sign_time", model.getSignTime());
				if(!ship.update(user)) return false;
				
				KdShipSign sign = new KdShipSign();
				sign.set("ship_id", model.getShipId());
				if(imgs.size()>0)sign.set("sign_image_gid", gid);
				sign.set("sign_person", model.getSignName());
				sign.set("sign_id_number", model.getIdNumber());
				sign.set("sign_remark", model.getRemark());
				sign.set("sign_had_agency", model.isHasproxy());
				sign.set("sign_had_delivery_fee", model.isArrivepay());
				sign.set("sign_time", model.getSignTime());
				
				if(!sign.save(user)) return false;
				
				//日志信息
				KdShipTrack kdShipTrack=new KdShipTrack();
				kdShipTrack.set("track_ship_id", model.getShipId());
				kdShipTrack.set("user_id", user.getUserId());
				kdShipTrack.set("track_resource_id", model.getShipId());
				kdShipTrack.set("track_company_id", user.getCompanyId());
				kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_SIGN_NUM);
				kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_SIGN);
				kdShipTrack.set("track_networkid",signship.get("network_id"));
				kdShipTrack.set("track_desc", "您的运单已签收，感谢使用本司物流，期待再次为您再次服务");
				kdShipTrack.set("create_time", new Date());
				if(!kdShipTrack.save()) return false;
				
				
				
				List<KdInOut> data = KdInOut.dao.find("select ship_id  from kd_in_out where ship_id=? and network_id=?", model.getShipId(), signship.getInt("load_network_id"));
				if(data.size()>0){//在当前库存中，未出库操作
					//更新出入库记录 add  by liangxiaoping
					int inOutsave = Db.update("update kd_in_out set out_time=? where ship_id=? and network_id=?", time, model.getShipId(), signship.getInt("load_network_id"));
					if(inOutsave==0)return false;	
				}
				return true;
			}
		});
	}
	
	/**
	 * 签收回单
	 * @author liangxp
	 * Date:2017年11月14日下午4:46:18 
	 *
	 * @param user
	 * @param shipId
	 * @return
	 */
	public boolean signReturn(SessionUser user, Integer shipId){
		KdShip ship = new KdShip();
		ship.set("ship_id", shipId);
		ship.set("ship_receipt_time", new Date());
		if(ship.update(user)){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 签收查询
	 * @author liangxp
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public Page<KdShip>  queryShipList(Paginator paginator, SessionUser user, SignSearchModel model){
		String sql = "select DISTINCT m.* ";
		StringBuilder unionone = new StringBuilder("(SELECT DISTINCT s.ship_id, s.ship_sn,s.ship_sender_id, getCustomerName(s.ship_sender_id) sendName,s.ship_receiver_id, getCustomerName(s.ship_receiver_id) receiverName, s.ship_volume,"
				+ "s.create_time, s.ship_amount, s.ship_weight,s.ship_from_city_code, getRegion(s.ship_from_city_code) fromCity, s.ship_to_city_code, getRegion(s.ship_to_city_code) toCity,getNetworkNameById(s.network_id) snetworkName "
				+ " FROM kd_in_out  o left join kd_ship s  on s.ship_id=o.ship_id "
				+ " where o.out_time>0 and s.ship_status>4 and s.ship_status<9 and o.network_id in  (" + user.toNetWorkIdsStr()+"))");
		
		StringBuilder uniobtwo = new StringBuilder("union (SELECT DISTINCT s.ship_id, s.ship_sn,s.ship_sender_id, getCustomerName(s.ship_sender_id) sendName,s.ship_receiver_id, getCustomerName(s.ship_receiver_id) receiverName, s.ship_volume,"
				+ " s.create_time, s.ship_amount, s.ship_weight,s.ship_from_city_code, getRegion(s.ship_from_city_code) fromCity, s.ship_to_city_code, getRegion(s.ship_to_city_code) toCity,getNetworkNameById(s.network_id) snetworkName"
				+ " FROM kd_in_out  o left join kd_ship s  on s.ship_id=o.ship_id "
				+ " where s.ship_storage<2 and s.ship_status>4 and s.ship_status<9 and s.load_network_id in   (" + user.toNetWorkIdsStr()+"))");
		
		StringBuilder where = new StringBuilder(") m  where 1=1");
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getShipSn())){//运单查询
			where.append(" and m.ship_sn like ?");
			paras.add("%"+ model.getShipSn() +"%");
		}
		
		if(model.getSendName()>0){//开单起始地址
			where.append(" and m.ship_sender_id=?");
			paras.add(model.getSendName());
		}
		
		if(model.getReceiveName()>0){//开单起始地址
			where.append(" and m.ship_receiver_id=?");
			paras.add(model.getReceiveName());
		}
		
		if(StringUtils.isNotBlank(model.getStartCode())){//开单起始地址
			where.append(" and m.ship_from_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getStartCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getEndCode())){//开单结束地址
			where.append(" and m.ship_to_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getEndCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getStartTime())){
			where.append(" and m.create_time>=? and m.create_time<=?");
			paras.add(model.getStartTime());
			paras.add(model.getStartTime() + " 23:59:59");
		}
		where.append(" order by m.create_time desc");
		return KdShip.dao.paginate(paginator, sql, " from (" + unionone.toString() + uniobtwo.toString()+ where.toString(), paras.toArray());
	}
	
	
}
