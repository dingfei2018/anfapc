package com.supyuan.kd.kucun;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.StrUtils;

/**
 * 
 * @author liangxp
 *
 * Date:2017年12月7日下午3:22:59 
 * 
 * @email liangxp@anfawuliu.com
 */
public class KucunSvc extends BaseService {
	

	
	/**
	 * 库存查询
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public Page<KdShip> queryList(Paginator paginator, SessionUser user, KucunSearchModel model, boolean isExcel){
		String select = "select k.* ";
		StringBuilder bu = new StringBuilder(" from (select m.* from ( SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) sendName, getCustomerName(s.ship_receiver_id) receiverName, s.ship_volume, "
				+ "s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromCity, getRegion(s.ship_to_city_code) toCity, getNetworkNameById(s.network_id) snetworkName,getNetworkNameById(s.load_network_id) enetworkName, "
				+ "o.in_time intime, TIMESTAMPDIFF(HOUR, o.in_time, now()) hashours, "
				+ "getProductName(s.ship_id) proname"
				+ " FROM kd_in_out  o left join kd_ship s  on s.ship_id=o.ship_id where ship_storage<2 ");
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getShipSn())){//运单查询
			bu.append(" and s.ship_sn like ?");
			paras.add("%"+ model.getShipSn() +"%");
		}
		
		if(model.getSendName()>0){//开单起始地址
			bu.append(" and s.ship_sender_id=?");
			paras.add(model.getSendName());
		}
		
		if(model.getReceiveName()>0){//开单起始地址
			bu.append(" and s.ship_receiver_id=?");
			paras.add(model.getReceiveName());
		}
		
		if(model.getNetworkId()>0){//开单网点
			bu.append(" and s.network_id=?");
			paras.add(model.getNetworkId());
		}
		
		
		if(model.getLoadnetworkId()>0){//库存网点
			bu.append(" and s.load_network_id=?");
			paras.add(model.getLoadnetworkId());
		}else{
			bu.append(" and s.load_network_id in (" + user.toNetWorkIdsStr()+")");
		}
		
		if(StringUtils.isNotBlank(model.getStartCode())){//开单起始地址
			bu.append(" and s.ship_from_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getStartCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getEndCode())){//开单结束地址
			bu.append(" and s.ship_to_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getEndCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getStartTime())){//开单起始时间
			bu.append(" and s.create_time>=? and s.create_time<=?");
			paras.add(model.getStartTime());
			paras.add(model.getStartTime()+" 23:59:59");
		}
		bu.append(" order by o.id desc) m GROUP BY m.ship_id) k order by k.intime desc");
		if(isExcel){
			List<KdShip> ships = KdShip.dao.find(select + bu.toString(), paras.toArray());
			return new Page<KdShip>(ships, 1, 10, 1, ships.size());
		}else{
			return KdShip.dao.paginate(paginator, select, bu.toString(), paras.toArray());
		}
	}
	
	
	/**
	 * 出库查询
	 * @author liangxp
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public Page<KdShip>  outList(Paginator paginator, SessionUser user, KucunSearchModel model, boolean isExcel){
		String select = "SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) sendName, getCustomerName(s.ship_receiver_id) receiverName, s.ship_volume, s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromCity, getRegion(s.ship_to_city_code) toCity, "
				+ " getNetworkNameById(s.network_id) snetworkName,getNetworkNameById(o.network_id) enetworkName, "
				+ "o.in_time intime, o.out_time outtime ,TIMESTAMPDIFF(HOUR,  o.in_time, o.out_time) hashours, getProductName(s.ship_id) proname ";
		StringBuilder bu = new StringBuilder(" FROM kd_in_out o LEFT JOIN kd_ship s  on  o.ship_id=s.ship_id "
				//+ "	LEFT JOIN  kd_ship_transfer f on f.ship_id=s.ship_id"
				//+ "	LEFT JOIN  kd_truck_ship k on s.ship_id=k.ship_id  LEFT JOIN kd_truck_load t on t.load_id=k.truck_load_id  "
				+ " where o.out_time>0");
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getShipSn())){//运单查询
			bu.append(" and s.ship_sn like ?");
			paras.add("%"+ model.getShipSn() +"%");
		}
		
		if(model.getSendName()>0){//开单起始地址
			bu.append(" and s.ship_sender_id=?");
			paras.add(model.getSendName());
		}
		
		if(model.getReceiveName()>0){//开单起始地址
			bu.append(" and s.ship_receiver_id=?");
			paras.add(model.getReceiveName());
		}
		
		if(model.getLoadnetworkId()>0){//库存网点
			bu.append(" and o.network_id=?");
			paras.add(model.getLoadnetworkId());
		}else{
			bu.append(" and o.network_id in (" + user.toNetWorkIdsStr()+")");
		}
		
		if(model.getNetworkId()>0){//开单网点
			bu.append(" and s.network_id=?");
			paras.add(model.getNetworkId());
		}
		
		if(StringUtils.isNotBlank(model.getStartCode())	){//开单起始地址
			bu.append(" and s.ship_from_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getStartCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getEndCode())){//开单结束地址
			bu.append(" and s.ship_to_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getEndCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getEndTime())){//开单起始时间
			bu.append(" and o.out_time>? and o.out_time<=?");
			paras.add(model.getEndTime());
			paras.add(model.getEndTime()+" 23:59:59");
		}
		
		if(StringUtils.isNotBlank(model.getStartTime())){//开单起始时间
			bu.append(" and o.in_time>? and o.in_time<=?");
			paras.add(model.getStartTime());
			paras.add(model.getStartTime()+" 23:59:59");
		}
		
		bu.append(" order by o.out_time desc");
		
		if(isExcel){
			List<KdShip> ships = KdShip.dao.find(select + bu.toString(), paras.toArray());
			return new Page<KdShip>(ships, 1, 10, 1, ships.size());
		}else{
			return KdShip.dao.paginate(paginator, select, bu.toString(), paras.toArray());
		}
	}
	
	
}
