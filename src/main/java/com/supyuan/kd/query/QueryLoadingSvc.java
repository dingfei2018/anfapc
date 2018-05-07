package com.supyuan.kd.query;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.supyuan.kd.sign.KdShipSign;
import com.supyuan.kd.transfer.ShipTransfer;
import com.supyuan.kd.transfer.TransferMentSearchModel;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.StrUtils;

/**
 * 发车查询类
 * @author dingfei
 *
 * @date 2017年12月18日 上午11:23:28
 */
public class QueryLoadingSvc  extends BaseService {
	
	/**
	 * 发车查询按配载查询
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public Page<KdShip>  queryLoadingList(Paginator paginator, SessionUser user, LoadQuerySearchModel model){
		String select = "SELECT tl.load_volume, tl.load_amount, tl.load_weight, tl.load_count,tl.load_fee,tl.load_sn,getNetworkNameById (tl.network_id) AS netName,getRegion (tl.load_delivery_from) AS fromAdd,getRegion (tl.load_delivery_to) AS toAdd,"
				+ "getNetworkNameById (tl.network_id) AS loadnetName,tl.load_depart_time,t.truck_id_number,tl.load_delivery_status,t.truck_driver_name,t.truck_driver_mobile,"
				+ "tl.load_transport_type,getNetworkNameById (load_next_network_id) networkName,tl.load_id";
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load  tl");
		bu.append(" LEFT JOIN truck t ON t.truck_id = tl.truck_id");
		bu.append(" where 1=1 ");
		
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getLoadSn())){//配载单查询
			bu.append(" and tl.load_sn like ?");
			paras.add("%"+model.getLoadSn()+"%");
		}
		if(model.getLoadNetworkId()>0){
			bu.append(" and tl.network_id=?");
			paras.add(model.getLoadNetworkId());
		}else{
			bu.append(" and tl.network_id in (" + user.toNetWorkIdsStr()+")");
		}

		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			bu.append(" and tl.load_depart_time BETWEEN  '"+model.getStartTime()+"' AND '"+ model.getEndTime()+"'");
			//paras.add(model.getTime());
			//paras.add(model.getTime()+" 23:59:59");
		}

		if(StringUtils.isNotBlank(model.getDriverName())){
			bu.append(" and t.truck_driver_name like ?");
			paras.add("%"+model.getDriverName()+"%");
		}

		if(StringUtils.isNotBlank(model.getTruckNumber())){
			bu.append(" and t.truck_id_number like ?");
			paras.add("%"+model.getTruckNumber()+"%");
		}

		if(StringUtils.isNotBlank(model.getDeliveryFrom())){
			bu.append(" and tl.load_delivery_from like ?");
			paras.add(StrUtils.getRealRegionCode(model.getDeliveryFrom())+"%");
		}

		if(StringUtils.isNotBlank(model.getDeliveryTo())){
			bu.append(" and tl.load_delivery_to like ?");
			paras.add(StrUtils.getRealRegionCode(model.getDeliveryTo())+"%");
		}
		
		bu.append(" order by tl.load_depart_time desc");
		
		Page<KdShip> page=new KdShip().paginate(paginator, select, bu.toString(), paras.toArray());

		return page;
	}
	
	
	public Page<KdShip>  queryShipsList(Paginator paginator, SessionUser user, ShipQuerySearchModel model){
		StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id)AS netName,s.ship_customer_number ,"
										+ " s.ship_volume,s.create_time, s.ship_amount, s.ship_weight,s.ship_status,"
										+ " s.ship_from_city_code,s.ship_sender_id,s.ship_receiver_id, getRegion(s.ship_from_city_code) fromAdd," 
										+ " s.ship_to_city_code, getRegion(s.ship_to_city_code) toAdd, getCustomerName(s.ship_sender_id) AS senderName, getCustomerName(s.ship_receiver_id) AS receiverName");
		StringBuilder where = new StringBuilder(" from kd_ship s where s.ship_deleted=0");
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getShipSn())){//运单查询
			where.append(" and s.ship_sn=?");
			paras.add(model.getShipSn());
			
		}
		
		if(model.getNetworkId()>0){//库存网点
			where.append(" and s.network_id=?");
			paras.add(model.getNetworkId());
		}else{
			where.append(" and s.network_id in (" + user.toNetWorkIdsStr()+")");
		}
		
		if(StringUtils.isNotBlank(model.getSenderId())){
			where.append(" and s.ship_sender_id=?");
			paras.add(model.getSenderId());
		}
		
		if(StringUtils.isNotBlank(model.getReceiverId())){
			where.append(" and s.ship_receiver_id=?");
			paras.add(model.getReceiverId());
		}
		
		
		if(StringUtils.isNotBlank(model.getShipCustomerNumber())){//开单客户单号
			where.append(" and s.ship_customer_number=?");
			paras.add(model.getShipCustomerNumber());
		}
		if (StringUtils.isNotBlank(model.getCreateTimeStart()) && StringUtils.isNotBlank(model.getCreateTimeEnd())) {
			where.append(" and s.create_time BETWEEN '"+ model.getCreateTimeStart() +"' AND  '" + model.getCreateTimeEnd()+"'");
		}
		
		if(StringUtils.isNotBlank(model.getStartCode())){//开单起始地址
			where.append(" and s.ship_from_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getStartCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getEndCode())){//开单结束地址
			where.append(" and s.ship_to_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getEndCode()) +"%");
		}
		
		
		if(StringUtils.isNotBlank(model.getShipStatus())){//开单状态
			if(model.getShipStatus().equals("2")){
				where.append(" and (s.ship_status>1 and s.ship_status<4) ");
			}else {
				where.append(" and s.ship_status=?");
				paras.add(model.getShipStatus());
			}
		}
		
		where.append(" order by s.create_time desc");
		return KdShip.dao.paginate(paginator, select.toString(), where.toString(), paras.toArray());
	}
	

	public boolean signImg(SessionUser user, String gid, String shipId, KdShipSign sign,  List<LibImage> imgs){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				//保存图片
				boolean res = new LibImageSvc().addLibImages(imgs);
				if(!res)return false;
				
				if(sign == null){//之前没有直接保存
					KdShipSign sign = new KdShipSign();
					sign.set("ship_id", shipId);
					sign.set("sign_image_gid", gid);
					if(!sign.save(user)) return false;
				}else if(sign != null && StringUtils.isBlank(sign.getStr("sign_image_gid"))){//之前没有上传更新签收图片
					KdShipSign sign = new KdShipSign();
					sign.set("ship_id", shipId);
					sign.set("sign_image_gid", gid);
					if(!sign.update(user)) return false;
				}
				return true;
			}
		});
	}


	
	
	
	
	
	/**
	 * 根据shipId查询
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public List<KdShip>  queryLoadingByShipId(String shipId){
		String select = "SELECT s.ship_id,tl.load_sn, s.ship_sn,getNetworkNameById(s.network_id)AS netName, s.create_time,getRegion(s.ship_from_city_code)AS fromAdd,"
				+ " getRegion(s.ship_to_city_code) AS toAdd,getNetworkNameById(tl.network_id) AS loadnetName,tl.load_depart_time, s.ship_customer_number AS customerNumber, "
				+ " t.truck_driver_mobile, t.truck_id_number,t.truck_driver_name,tl.load_transport_type,s.ship_volume,s.ship_weight,s.ship_amount,s.ship_status,s.load_network_id, "
				+ " getNetworkNameById (st.network_id) AS netName, c.customer_corp_name, c.customer_name, c.customer_mobile, st.ship_transfer_time, "
				+ " getCustomerName(s.ship_sender_id) senderName,  getCustomerName(s.ship_receiver_id) receiverName";
		StringBuilder bu = new StringBuilder("  FROM kd_truck_ship  ts LEFT JOIN kd_ship  s ON  s.ship_id=ts.ship_id LEFT JOIN kd_truck_load tl ON tl.load_id=ts.truck_load_id LEFT JOIN truck t ON t.truck_id=tl.truck_id  LEFT JOIN kd_ship_transfer st  ON st.ship_id=s.ship_id LEFT JOIN kd_customer c ON c.customer_id=st.transfer_id   where 1=1 and s.ship_deleted=0 and s.ship_id="+shipId);
		

		return KdShip.dao.find(select+bu);
	}

}
