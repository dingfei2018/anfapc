package com.supyuan.kd.finance.payable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.supyuan.kd.finance.KdShipFee;
import com.supyuan.kd.finance.flow.*;
import com.supyuan.kd.finance.flow.FlowSvc;
import com.supyuan.kd.finance.flow.KdFlow;
import com.supyuan.kd.loading.KdLoadGxFee;
import com.supyuan.kd.transfer.ShipTransfer;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.loading.KdLoadFee;
import com.supyuan.kd.loading.KdTrunkLoad;

/**
 * 应付
 * @author liangxp
 *
 * Date:2017年12月20日下午6:27:09 
 * 
 * @email liangxp@anfawuliu.com
 */
public class PayableSvc extends BaseService {
	DecimalFormat df   = new DecimalFormat("#.00");

	/**
	 * 发车汇总查询
	 * @param paginator
	 * @param user
	 * @param model
	 * @param isExcel
	 * @return
	 */
	public Page<KdTrunkLoad>  queryTrunkLoadList(Paginator paginator, SessionUser user, PayableSearchModel model,String type, boolean isExcel){
		String select = "SELECT l.load_id,l.load_sn, l.truck_id, l.load_next_network_id,l.load_transport_type,l.load_depart_time,getRegion(l.load_delivery_from)    fromCity,l.load_arrival_time ," +
				"  getRegion(l.load_delivery_to)    toCity,l.load_delivery_status,l.load_fee,load_fee_fill,IFNULL(l.load_atunload_fee,\"\") AS load_atunload_fee," +
				"  l.load_atunload_fill,IFNULL(l.load_atother_fee,\"\") AS load_atother_fee,l.load_atother_fill, l.company_id, getNetworkNameById(l.network_id) snetworkName," +
				"   getNetworkNameById(l.load_next_network_id)    enetworkName, l.create_time,t.truck_driver_mobile,t.truck_driver_name," +
				"  t.truck_id_number, gx.id, IFNULL(gx.load_nowtrans_fee,\"\") AS load_nowtrans_fee, gx.load_nowtrans_fill,IFNULL(gx.load_nowoil_fee,\"\") AS load_nowoil_fee," +
				"  gx.load_nowoil_fill,IFNULL(gx.load_backtrans_fee,\"\") AS load_backtrans_fee, gx.load_backtrans_fill,IFNULL(gx.load_attrans_fee,\"\") AS load_attrans_fee," +
				"  gx.load_attrans_fill,IFNULL(gx.load_allsafe_fee,\"\") AS load_allsafe_fee,gx.load_allsafe_fill,IFNULL(gx.load_start_fee,\"\") AS load_start_fee, gx.load_start_fill," +
				"  IFNULL(gx.load_other_fee,\"\") AS load_other_fee,gx.load_other_fill,(IFNULL(l.load_atunload_fee,0)+IFNULL(l.load_atother_fee,0)+IFNULL(gx.load_attrans_fee,0)) AS totalFee," +
				"  (IFNULL(l.load_fee,0) +IFNULL(l.load_atunload_fee,0)+IFNULL(l.load_atother_fee,0)) AS totalCost" ;
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load l LEFT JOIN truck t ON l.truck_id = t.truck_id LEFT JOIN kd_load_gx_fee gx ON l.load_id = gx.load_id WHERE load_deleted = 0");
		List<Object> paras = new ArrayList<Object>();
		if ("ToSum".equals(type)){
			bu.append(" and l.load_delivery_status > 2  AND l.load_transport_type > 1 AND l.load_transport_type < 4");
			if (StringUtils.isNotBlank(model.getStartTime())) {
				bu.append(" and l.load_arrival_time>=? ");
				paras.add(model.getStartTime());

			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				bu.append(" and l.load_arrival_time<=?");
				paras.add(model.getEndTime() + " 23:59:59");

			}

		}else {
			if (StringUtils.isNotBlank(model.getStartTime())) {
				bu.append(" and l.load_depart_time>=? ");
				paras.add(model.getStartTime());

			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				bu.append(" and l.load_depart_time<=?");
				paras.add(model.getEndTime() + " 23:59:59");

			}
		}
		if(model.getNetworkId()>0){
			bu.append(" and l.network_id=?");
			paras.add(model.getNetworkId());
		}else{
			bu.append(" and l.network_id in (" + user.toNetWorkIdsStr()+")");
		}
		
		if(model.getLoadToNetworkId()>0){
			bu.append(" and l.load_next_network_id=?");
			paras.add(model.getLoadToNetworkId());
		}
		
		if(StringUtils.isNotBlank(model.getTruckNumber())){
			bu.append(" and t.truck_id_number like ?");
			paras.add(model.getTruckNumber());
		}
		
		if(StringUtils.isNotBlank(model.getDeliveryFrom())){
			bu.append(" and l.load_delivery_from=?");
			paras.add(model.getDeliveryFrom());
		}
		
		if(StringUtils.isNotBlank(model.getDeliveryTo())){
			bu.append(" and l.load_delivery_to=?");
			paras.add(model.getDeliveryTo());
		}

		if(StringUtils.isNotBlank(model.getDeliveryStatus())){
			bu.append(" and l.load_delivery_status=?");
			paras.add(model.getDeliveryStatus());
		}
		if(StringUtils.isNotBlank(model.getTransportType())){
			bu.append(" and l.load_transport_type=?");
			paras.add(model.getTransportType());
		}
		if(StringUtils.isNotBlank(model.getLoadSn())){
			bu.append(" and l.load_sn like ?");
			paras.add(model.getLoadSn());
		}
		
		if(StringUtils.isNotBlank(model.getDriverName())){
			bu.append(" and t.truck_driver_name like ?");
			paras.add(model.getDriverName());
		}
		bu.append(" order by l.load_depart_time desc");
		if(isExcel){
			List<KdTrunkLoad> ships = KdTrunkLoad.dao.find(select + bu.toString(), paras.toArray());
			return new Page<KdTrunkLoad>(ships, 1, 10, 1, ships.size());
		}else{
			return KdTrunkLoad.dao.paginate(paginator, select, bu.toString(), paras.toArray());
		}
	}
	
	/**
	 * 配载确认收款
	 * @author liangxp
	 * @param ids
	 * @return
	 */
	public boolean confirmpay(String ids){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				String[] lids = ids.split(",");
				List<KdTrunkLoad> loads = new ArrayList<KdTrunkLoad>();
				List<KdLoadFee> fees = new ArrayList<KdLoadFee>();
				Date time = new Date();
				for (String id : lids) {
					KdTrunkLoad load = new KdTrunkLoad();
					load.set("load_id", id);
					load.set("load_fee_status", 1);
					load.set("load_pay_time", time);
					loads.add(load);
					
					KdLoadFee fee = new KdLoadFee();
					fee.set("load_id", id);
					fee.set("pay_status", 1);
					fees.add(fee);
				}
				
				int[] size = Db.batchUpdate(loads, loads.size());
				if(size.length!=loads.size())return false;
				
				int[] feesize = Db.batchUpdate(fees, fees.size());
				if(feesize.length!=fees.size())return false;
				return true;
			}
		});
	}
	
	/**
	 * 修改分摊费用
	 * @author liangxp
	 * @param ids
	 * @param user
	 * @return
	 */
	public boolean confirmModify(String ids , int allocationWay, int loadId,  double loadfee,  SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdTrunkLoad oload = KdTrunkLoad.dao.findById(loadId);
				if(oload == null) return false;
				if(loadfee<oload.getDouble("load_fee_prepay")){
					 return false;
				}
				
				String[] lids = ids.split(",");
				List<KdLoadFee> loads = new ArrayList<KdLoadFee>();
				for (String id : lids) {
					String[] data = id.split(":");
					KdLoadFee load = new KdLoadFee();
					load.set("id", data[0]);
					load.set("fee", data[1]);
					loads.add(load);
				}
				int[] size = Db.batchUpdate(loads, loads.size());
				if(size.length!=loads.size())return false;
				
				KdTrunkLoad load = new KdTrunkLoad();
				load.set("load_id", loadId);
				load.set("load_fee", loadfee);
				load.set("load_fee_allocation_way", allocationWay);
				boolean update = load.update(user);
				if(!update)return false;
				return true;
			}
		});
	}
	
	
	public boolean confirmPrePayModify(int loadId,  double loadPrePay,  SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdTrunkLoad oload = KdTrunkLoad.dao.findById(loadId);
				if(oload == null) return false;
				double prepay = oload.getDouble("load_fee_prepay");
				if((prepay+loadPrePay)>oload.getDouble("load_fee")) return false;
				KdTrunkLoad load = new KdTrunkLoad();
				load.set("load_id", loadId);
				load.set("load_fee_prepay", prepay+loadPrePay);
				boolean update = load.update(user);
				if(!update)return false;
				return true;
			}
		});
	}
	
	
	
	/**
	 * 获取应付中转列表
	 * @param paginator
	 * @param sessionUser
	 * @param search
	 * @return
	 */
	public Page<Record> getTransferPayList(Paginator paginator,SessionUser sessionUser,PayTransferSearchModel search,boolean isExcel) {
		Page<Record> recordList;
		StringBuilder select = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		
		select.append("SELECT st.ship_id as tranferShipId,s.ship_volume,s.ship_amount,s.ship_weight,s.ship_sn,st.ship_id,t.customer_name AS transferName,t.customer_corp_name as transferCorpName,c1.customer_name AS senderName,");
		select.append("c2.customer_name AS receiverName,st.transfer_id,st.network_id,st.ship_transfer_time,st.ship_transfer_sn,st.ship_transfer_fee,getRegion (s.ship_from_city_code) AS fromAdd,IFNULL(s.ship_pickuppay_fee,0) ship_pickuppay_fee,IFNULL(s.ship_agency_fund,0) ship_agency_fund,");
		select.append("getRegion (s.ship_to_city_code) AS toAdd,st.ship_transfer_fee_status pay_status,getNetworkNameById(s.network_id) as shipNetName,getNetworkNameById(st.network_id) as transferNetName ");
		sql.append(" FROM kd_ship_transfer st ");
		sql.append(" LEFT JOIN kd_customer t ON st.transfer_id = t.customer_id ");
		sql.append(" LEFT JOIN kd_ship s ON st.ship_id = s.ship_id");
		sql.append(" LEFT JOIN kd_customer c1 ON s.ship_sender_id = c1.customer_id");
		sql.append(" LEFT JOIN kd_customer c2 ON s.ship_receiver_id = c2.customer_id");
		parm.append(" where 1=1");
		if  (search.getTranNetWorkId()!=0) {
			parm.append(" and st.network_id=" + search.getTranNetWorkId());
		}
		if  (search.getShipNetWorkId()!=0) {
			parm.append(" and s.network_id=" + search.getShipNetWorkId());
		}
		if  (StringUtils.isNotBlank(search.getState())) {
			parm.append(" and st.ship_transfer_fee_status="+search.getState());
		}
		/*if  (StringUtils.isNotBlank(search.getStartTime())&&StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and st.ship_transfer_time BETWEEN '"+search.getStartTime()+"' AND '"+search.getEndTime()+"'");
		}*/
		if (StringUtils.isNotBlank(search.getStartTime())) {
			parm.append(" and st.ship_transfer_time>= '"+search.getStartTime()+"'");

		}
		if (StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and st.ship_transfer_time<='"+search.getEndTime()+ " 23:59:59'");

		}
		if(StringUtils.isNotBlank(search.getTransferName())){
			parm.append(" and t.customer_id = " + search.getTransferName());
		}
		if(StringUtils.isNotBlank(search.getShipSn())){
			parm.append(" and s.ship_sn like '%"+search.getShipSn()+"%'");
		}
		if(StringUtils.isNotBlank(search.getTransferSn())){
			parm.append(" and st.ship_transfer_sn like '%"+search.getTransferSn()+"%'");
		}
		if(StringUtils.isNotBlank(search.getFromAddCode())){
			parm.append(" and s.ship_from_city_code = " + search.getFromAddCode() );
		}
		if(StringUtils.isNotBlank(search.getToAddCode())){
			parm.append(" and s.ship_to_city_code = " + search.getToAddCode() );
		}
		parm.append(" and st.network_id in("+sessionUser.toNetWorkIdsStr()+")");
		sql.append(parm.toString());

		if(isExcel){
			List<Record> transList = Db.find(select.toString()+sql.toString());
			return new Page<Record>(transList, 1, 10, 1, transList.size());
		}else{
			return Db.paginate(paginator.getPageNo(),paginator.getPageSize(),select.toString(),sql.toString());
		}

	}
	
	/**
	 * 根据运单id更新其对应的中转单状态pay_status状态为1-已付
	 * @param shipIds
	 * @return
	 */
	public boolean  updateTransferPay(String shipIds ) {
		
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!(Db.update("update kd_ship_transfer SET ship_transfer_fee_status=1 WHERE ship_id in("+shipIds+")")>=0)) return false;
				
				return true;
			}
		});
		return tx;
	}

	/**
	 * 根据运输类型查询配载列表
	 * @author huangym
	 */
	/**
	 * 根据运输类型查询配载列表
	 * @author huangym
	 * transportType运输类型 1:提货，2:短驳，3:干线，4:送货
	 * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费
	 * loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
	 */
	public Page<KdTrunkLoad> queryTrunkLoadListByTransportType(Paginator paginator, SessionUser user, PayableSearchModel model, String transportType, String trunkLineType, String loadAtFeeFlag, boolean isExcel,boolean serrlement) {
		StringBuilder select = new StringBuilder("select l.load_id, l.load_fee_fill ,l.load_sn,l.load_depart_time,l.load_arrival_time, l.truck_id, l.load_fee, l.load_volume, l.load_amount, l.load_weight, l.load_count, l.load_next_network_id, l.load_transport_type,"
				+ " getRegion(l.load_delivery_from) fromCity, getRegion(l.load_delivery_to) toCity, l.load_delivery_status, load_image_gid, l.company_id, l.load_fee_prepay, "
				+ " getNetworkNameById(l.network_id) snetworkName,getNetworkNameById(l.load_next_network_id) enetworkName, l.create_time,t.truck_driver_mobile, t.truck_driver_name,t.truck_id_number, l.load_fee_status");
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load l left join truck t on l.truck_id=t.truck_id");

		//干线
		if ("3".equals(transportType)){
			if("1".equals(trunkLineType)){//现付运输费
				select.append(", f.load_nowtrans_fee, f.load_nowtrans_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_nowtrans_fee is not null ");
			}else if ("2".equals(trunkLineType)){//现付油卡费
				select.append(", f.load_nowoil_fee, f.load_nowoil_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_nowoil_fee is not null ");
			}else if ("3".equals(trunkLineType)){//回付运输费
				select.append(", f.load_backtrans_fee, f.load_backtrans_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_backtrans_fee is not null ");
			}else if ("4".equals(trunkLineType)){//整车保险费
				select.append(", f.load_allsafe_fee, f.load_allsafe_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_allsafe_fee is not null ");
			}else if ("5".equals(trunkLineType)){//发站装车费
				select.append(", f.load_start_fee, f.load_start_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_start_fee is not null ");
			}else if ("6".equals(trunkLineType)){//发站其他费
				select.append(", f.load_other_fee, f.load_other_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_other_fee is not null ");
			}else if ("7".equals(trunkLineType)){//到付运输费
				select.append(", f.load_attrans_fee, f.load_attrans_fill");
				bu.append(" left join kd_load_gx_fee f on l.load_id=f.load_id where load_deleted=0 and l.load_transport_type = 3 and f.load_attrans_fee is not null ");
			}
		}else if("1".equals(transportType)){//提货
			bu.append(" where load_deleted=0 and l.load_transport_type = 1 and l.load_fee > 0");
		}else if("2".equals(transportType)){//短驳
			bu.append(" where load_deleted=0 and l.load_transport_type = 2 and l.load_fee > 0");
		}else if("4".equals(transportType)){//送货
			bu.append(" where load_deleted=0 and l.load_transport_type = 4 and l.load_fee > 0");
		}else{
			if("0".equals(loadAtFeeFlag)){//到站卸车费
				select.append(", l.load_atunload_fee, l.load_atunload_fill ");
				bu.append(" where l.load_atunload_fee is not null ");
			}else if("1".equals(loadAtFeeFlag)){//到站其他费
				select.append(", l.load_atother_fee, l.load_atother_fill ");
				bu.append(" where l.load_atother_fee is not null ");
			}
		}

		if(serrlement&&!"3".equals(transportType)){
			bu.append("  and l.load_fee_fill != 1 ");
		}


		List<Object> paras = new ArrayList<Object>();
		//配载网点
		if( model.getNetworkId()>0){
			bu.append(" and l.network_id=?");
			paras.add(model.getNetworkId());
		}else if("0".equals(loadAtFeeFlag)||"1".equals(loadAtFeeFlag)){
			bu.append(" and l.load_next_network_id in (" + user.toNetWorkIdsStr()+")");
		}else{
			bu.append(" and l.network_id in (" + user.toNetWorkIdsStr()+")");
		}


		//到货网点
		if(model.getLoadToNetworkId()!=null && model.getLoadToNetworkId()>0){
			bu.append(" and l.load_next_network_id=?");
			paras.add(model.getLoadToNetworkId());
		}

		//发车日期
		/*if(StringUtils.isNotBlank(model.getStartTime())){
			bu.append(" and l.create_time>=? and l.create_time<=?");
			paras.add(model.getStartTime());
			if(StringUtils.isNotBlank(model.getStartTime())){
				paras.add(model.getEndTime());
			}else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				paras.add(sdf.format(new Date()));
			}
		}*/
		if (StringUtils.isNotBlank(model.getStartTime())) {
			bu.append(" and l.load_depart_time>=? ");
			paras.add(model.getStartTime());

		}
		if (StringUtils.isNotBlank(model.getEndTime())) {
			bu.append(" and l.load_depart_time<=?");
			paras.add(model.getEndTime() + " 23:59:59");

		}

		//出发地
		if(StringUtils.isNotBlank(model.getDeliveryFrom())){
			bu.append(" and l.load_delivery_from=?");
			paras.add(model.getDeliveryFrom());
		}

		//到达地
		if(StringUtils.isNotBlank(model.getDeliveryTo())){
			bu.append(" and l.load_delivery_to=?");
			paras.add(model.getDeliveryTo());
		}

		//车牌号
		if(StringUtils.isNotBlank(model.getTruckNumber())){
			bu.append(" and t.truck_id_number like ?");
			paras.add("%"+model.getTruckNumber()+"%");
		}

		//司机
		if(StringUtils.isNotBlank(model.getDriverName())){
			bu.append(" and t.truck_driver_name like ?");
			paras.add("%"+model.getDriverName()+"%");
		}


		//配载单号
		if(StringUtils.isNotBlank(model.getLoadSn())){
			bu.append(" and l.load_sn like ?");
			paras.add("%"+model.getLoadSn()+"%");
		}



		//结算状态
		if(StringUtils.isNotBlank(model.getFillStatus())){
			String fillStr = "";
			if ("3".equals(transportType)){
				if("1".equals(trunkLineType)){//现付运输费
					fillStr = " f.load_nowtrans_fill";
				}else if ("2".equals(trunkLineType)){//现付油卡费
					fillStr = " f.load_nowoil_fill";
				}else if ("3".equals(trunkLineType)){//回付运输费
					fillStr = " f.load_backtrans_fill";
				}else if ("4".equals(trunkLineType)){//整车保险费
					fillStr = " f.load_allsafe_fill";
				}else if ("5".equals(trunkLineType)){//发站装车费
					fillStr = " f.load_start_fill";
				}else if ("6".equals(trunkLineType)){//发站其他费
					fillStr = " f.load_other_fill";
				}else if ("7".equals(trunkLineType)){//到付运输费
					fillStr = " f.load_attrans_fill";
				}
			}else if("1".equals(transportType)||"2".equals(transportType)||"4".equals(transportType)){//提货
				fillStr = " l.load_fee_fill";

			}else{
				if("0".equals(loadAtFeeFlag)){//到站卸车费
					fillStr = " l.load_atunload_fill";
				}else if("1".equals(loadAtFeeFlag)){//到站其他费
					fillStr = " l.load_atother_fill";
				}
			}

			bu.append(" and "+fillStr+" = ?");
			paras.add(model.getFillStatus());
		}

		bu.append(" order by l.create_time desc");

		if(isExcel){
			List<KdTrunkLoad> ships = KdTrunkLoad.dao.find(select + bu.toString(), paras.toArray());
			return new Page<KdTrunkLoad>(ships, 1, ships.size(), 1, ships.size());
		}else{
			return KdTrunkLoad.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
		}


	}

	/**
	 * 应付回扣列表
	 * @author huangym
	 */
	public Page<KdShip> getRebatePayList(Paginator paginator, SessionUser user, PayRebateSearchModel search, boolean isExcel,boolean isSettlement) {
		StringBuilder select = new StringBuilder("select s.ship_id, s.ship_sn, getNetworkNameById(s.network_id) networkName, s.goods_sn, s.create_time,  getRegion(s.ship_from_city_code) fromCity, getRegion(s.ship_to_city_code) toCity, "
				+ " c1.customer_name AS senderName, c2.customer_name AS receiverName, s.ship_status, s.ship_rebate_fee, s.ship_fee_status, s.ship_total_fee, getProductName(s.ship_id) productName, "
				+ " s.ship_volume, s.ship_weight, s.ship_amount, getPCUserName(s.user_id) userName,s.ship_is_rebate");
		StringBuilder bu = new StringBuilder(" FROM kd_ship s");
		bu.append(" LEFT JOIN kd_customer c1 ON s.ship_sender_id = c1.customer_id");
		bu.append(" LEFT JOIN kd_customer c2 ON s.ship_receiver_id = c2.customer_id");
		bu.append(" where s.ship_deleted=0 ");
		List<Object> paras = new ArrayList<Object>();

		String netWorkIds=user.toNetWorkIdsStr();
		//网点
		if(search.getShipNetWorkId()>0){
			netWorkIds=search.getShipNetWorkId()+"";
		}

		bu.append(" and s.network_id in("+netWorkIds+") and s.ship_rebate_fee>0 ");


		//开单日期
		/*if(StringUtils.isNotBlank(search.getStartTime())){
			bu.append(" and s.create_time>=? and s.create_time<=?");
			paras.add(search.getStartTime());
			if(StringUtils.isNotBlank(search.getStartTime())){
				paras.add(search.getEndTime());
			}else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				paras.add(sdf.format(new Date()));
			}
		}*/
		if (StringUtils.isNotBlank(search.getStartTime())) {
			bu.append(" and s.create_time>=? ");
			paras.add(search.getStartTime());

		}
		if (StringUtils.isNotBlank(search.getEndTime())) {
			bu.append(" and s.create_time<=?");
			paras.add(search.getEndTime() + " 23:59:59");

		}

		//出发地
		if(StringUtils.isNotBlank(search.getFromAddCode())){
			bu.append(" and s.ship_from_city_code=?");
			paras.add(search.getFromAddCode());
		}
		//到达地
		if(StringUtils.isNotBlank(search.getToAddCode())){
			bu.append(" and s.ship_to_city_code=?");
			paras.add(search.getToAddCode());
		}
		//回扣状态
		if(StringUtils.isNotBlank(search.getState())){
			bu.append(" and s.ship_is_rebate=?");
			paras.add(search.getState());
		}
		//托运方
		if(StringUtils.isNotBlank(search.getSenderId())){
			bu.append(" and s.ship_sender_id = ?");
			paras.add(search.getSenderId());
		}
		//收货方
		if(StringUtils.isNotBlank(search.getReceiverId())){
			bu.append(" and s.ship_receiver_id = ?");
			paras.add(search.getReceiverId());
		}
		if(StringUtils.isNotBlank(search.getShipSn())){
			bu.append(" and s.ship_sn = ?");
			paras.add(search.getShipSn());
		}

		if(isExcel){
			List<KdShip> ships = KdShip.dao.find(select + bu.toString(), paras.toArray());
			if(isSettlement){
				bu.append(" and ship_is_rebate=0 ");
			}
			return new Page<KdShip>(ships, 1, ships.size(), 1, ships.size());
		}else{
			return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
		}

	}

	/**
	 * 应付中转结算列表
	 * @param sessionUser
	 * @param model
	 * @return
	 */
	public Page<Record> getAllTransferPayList(SessionUser sessionUser, PayTransferSearchModel model) {
		Page<Record> recordList;
		StringBuilder select = new StringBuilder();
		StringBuilder sql = new StringBuilder();

		select.append("SELECT st.ship_transfer_sn,t.customer_corp_name as transferCorpName, st.ship_transfer_time, st.ship_transfer_fee, s.ship_sn,st.ship_id, s.ship_pickuppay_fee, s.ship_agency_fund, getRegion (s.ship_to_city_code) AS toAdd ");
		sql.append(" FROM kd_ship_transfer st ");
		sql.append(" LEFT JOIN kd_customer t ON st.transfer_id = t.customer_id ");
		sql.append(" LEFT JOIN kd_ship s ON st.ship_id = s.ship_id");
		sql.append(" where st.network_id in("+sessionUser.toNetWorkIdsStr()+") and ship_transfer_fee_status = 0 and ship_transfer_fee > 0 ");
		if(StringUtils.isNotBlank(model.getTransferName())){
			sql.append(" and t.customer_id = " + model.getTransferName());
		}
		if(StringUtils.isNotBlank(model.getToAddCode())){
			sql.append(" and s.ship_to_city_code = " + model.getToAddCode() );
		}

		List<Record> records = Db.find(select.toString() + sql.toString());
		return new Page<Record>(records, 1, records.size(), 1, records.size());
	}

	/**
	 * 根据运费类型进行结算
	 * @param loadIds  配载单号
	 * @param payType  收支方式
	 * @param flowNo   收支账号：
	 * @param voucherNo  交易凭证号
	 * @param remark  备注
	 * @param time    创建时间
	 * @param user    用户
	 * @return
	 */
	public boolean confirmNowPay(String loadIds,String transportType, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				String[] ids=loadIds.split(",");
				if( Db.update("UPDATE kd_truck_load SET load_fee_fill = 1 WHERE load_id in ("+loadIds+") ")<1) return false;
				/*List<Record> shipIds = Db.find("SELECT ship_id FROM kd_truck_ship WHERE truck_load_id IN ("+loadIds+") ");*/


				//插入流水表
				List<Record> loads = Db.find("SELECT load_fee ,load_sn ,load_id,network_id   FROM kd_truck_load WHERE load_id IN  ("+loadIds+") ");
				LinkedHashMap loadFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkLoadIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:loads) {
					KdFlow kdFlow = new KdFlow();
					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (loadFeeMap.containsKey(key)){
						double load_fee = (double)loadFeeMap.get(key)+record.getDouble("load_fee");
						loadFeeMap.put(key ,load_fee);
					}else {
						loadFeeMap.put(key ,record.getDouble("load_fee"));
						networkLoadIdMap.put(key,record.getInt("load_id"));
					}
				}


				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					flow.set("fee",loadFeeMap.get(id)).set("pay_type",payType).set("flow_no",flowNo).set("voucher_no",voucherNo).set("remark",remark);
					flow.set("user_id",user.getUserId()).set("network_id",id).set("create_time",time).set("company_id",user.getCompanyId());
					flow.set("fee_type",1).set("id_type",2).set("settlement_type",0).set("inout_type",1).set("flow_sn",new FlowSvc().getFlowSnByComId(user.getCompanyId()));
					flow.set("resource_id",networkLoadIdMap.get(id)).set("id_type",2);
					if ("1".equals(transportType)) flow.set("fee_type",7).set("resource_type",1);
					if ("2".equals(transportType)) flow.set("fee_type",9).set("resource_type",3);
					if ("4".equals(transportType)) flow.set("fee_type",8).set("resource_type",2);
					if(!flow.save()) return  false;
					kdFlows.add(flow);
				}



				//插入流水明细表
				HashMap<Integer,Integer>  kdFlowIdMap = new HashMap<>();
				for (KdFlow flow: kdFlows) {
					kdFlowIdMap.put(flow.getInt("network_id"),flow.getInt("id"));
				}
				List<KdFlowDetail> kdFlowDetailList = new ArrayList<KdFlowDetail>() ;
				for (Record record:loads) {
					KdFlowDetail kdFlowDetail = new KdFlowDetail();
					kdFlowDetail.set("load_id",record.get("load_id")).set("fee",record.get("load_fee")).set("network_id",record.get("network_id"));
					kdFlowDetail.set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				for (String loadId :ids ) {
					List<KdShip> nships = new KdShipSvc().findLoadShips(Integer.parseInt(loadId));
					KdTrunkLoad kdTrunkLoad = new KdTrunkLoad().findById(loadId);
					int way = kdTrunkLoad.getInt("load_fee_allocation_way");
					//总费用
					double totalfee = kdTrunkLoad.getDouble("load_fee");
					//体积
					double volume = kdTrunkLoad.getDouble("load_volume");
					//重量
					double weight = kdTrunkLoad.getDouble("load_weight");
					//运单数
					int loadCount = kdTrunkLoad.getInt("load_count");
					double avgfee = 0;
					double temp = 0;
					for (int i = 0; i < nships.size(); i++) {
						//费用分摊插入运单费用表
						KdShipFee shipFee=new KdShipFee();
						KdShip sp = nships.get(i);
						if(way==1){//按单
							if(nships.size()==(i+1)){
								shipFee.set("fee", totalfee-avgfee);
							}else{
								 temp = new Double(df.format(totalfee/loadCount));
								shipFee.set("fee", temp);
								avgfee += temp;
							}
						}else if(way==2){//按体积
							if(nships.size()==(i+1)){
								shipFee.set("fee", totalfee-avgfee);

							}else{
								 temp = new Double(df.format(totalfee*sp.getDouble("ship_volume")/volume));
								shipFee.set("fee", temp);
								avgfee += temp;
							}
						}else if(way==3){//按重量
							if(nships.size()==(i+1)){
								shipFee.set("fee", totalfee-avgfee);
							}else{
								 temp = new Double(df.format(totalfee*sp.getDouble("ship_weight")/weight));
								shipFee.set("fee", temp);
								avgfee += temp;
							}
						}
						shipFee.set("ship_id",sp.getInt("ship_id")).set("resource_id",sp.getInt("ship_id")).set("resource_type",1).set("network_id",kdTrunkLoad.getInt("network_id"));
						shipFee.set("company_id",user.getCompanyId()).set("create_time",time);
						if ("4".equals(transportType)) shipFee.set("fee_type",8);
						if ("1".equals(transportType)) shipFee.set("fee_type",7);
						if ("2".equals(transportType)) shipFee.set("fee_type",9);
						if(!shipFee.save()) return false;

					}




                }

				return true;
			}
		});
		return tx;

	}

	/**
	 * 根据运单id计算出运单分摊的费用
	 */


	/**
	 * 中转结算
	 * @param shipIds
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmTransferPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				//String netWorkId=user.toNetWorkIdsStr().split(",")[0];
				String[] shipIdsArr=shipIds.split(",");
				//更新运单中转表状态为已结算
				if( Db.update("UPDATE kd_ship_transfer SET ship_transfer_fee_status = 1 WHERE ship_id IN ("+shipIds+") ")<1) return false;


				List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();
				List<KdFlowDetail> flowDetails = new ArrayList<KdFlowDetail>();



				for (String shipId:shipIdsArr ) {

					//中转费
					BigDecimal transferFee = ShipTransfer.dao.findById(shipId).get("ship_transfer_fee");
					//网点id
					Integer networkId=new ShipTransfer().findById(shipId).get("network_id");
					//配载id
					//插入费用表
					KdShipFee shipFee = new KdShipFee();
					shipFee.set("ship_id",shipId)
							.set("resource_id",shipId)
							.set("resource_type",1)
							.set("fee_type",10)
							.set("fee",transferFee)
							.set("network_id",networkId)
							.set("company_id",user.getCompanyId())
							.set("create_time",new Date());
					shipFeeList.add(shipFee);

				}

				//插入流水表
				List<Record> transfers = Db.find("SELECT network_id, ship_id, ship_transfer_fee   FROM kd_ship_transfer WHERE ship_id IN  ("+shipIds+") ");
				LinkedHashMap transferFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap transferShipMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:transfers) {


					KdFlow kdFlow = new KdFlow();
					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (transferFeeMap.containsKey(key)){
						double load_fee = (double)transferFeeMap.get(key)+record.getBigDecimal("ship_transfer_fee").doubleValue();
						transferFeeMap.put(key ,load_fee);
					}else {
						transferFeeMap.put(key ,record.getBigDecimal("ship_transfer_fee").doubleValue());
						transferShipMap.put(key,record.getInt("ship_id"));
					}

				}
				int count=0;
				for (Integer networkId:networkIds) {

					long num=new FlowSvc().getFlowSnOrderNumByComId(user.getCompanyId());
					num+=count;
					String endSn=new FlowSvc().getFlowSnByOrderNum(num);

					KdFlow flow=new KdFlow();
					flow.set("resource_id",transferShipMap.get(networkId))
							.set("id_type",1)
							.set("resource_type",4)
							.set("flow_sn",endSn)
							.set("settlement_type",0)
							.set("inout_type",1)
							.set("fee",transferFeeMap.get(networkId))
							.set("fee_type",10)
							.set("pay_type",payType)
							.set("flow_no",flowNo)
							.set("voucher_no",voucherNo)
							.set("remark",remark)
							.set("user_id",user.getUserId())
							.set("network_id",networkId)
							.set("company_id",user.getCompanyId())
							.set("create_time",time)
							.save();
					kdFlows.add(flow);

					count++;
				}
				int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
				if(shipFeeSave.length!=shipFeeList.size())return false;


				//插入流水明细表
				HashMap<Integer,Integer>  kdFlowIdMap = new HashMap<>();
				for (KdFlow flow: kdFlows) {
					kdFlowIdMap.put(flow.getInt("network_id"),flow.getInt("id"));
				}
				List<KdFlowDetail> kdFlowDetailList = new ArrayList<KdFlowDetail>() ;
				for (Record record:transfers) {
					KdFlowDetail kdFlowDetail = new KdFlowDetail();
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_transfer_fee")).set("network_id",record.get("network_id"));
					kdFlowDetail.set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;



				return true;
			}
		});
		return tx;

	}


	/**
	 * 回扣结算
	 *
	 * @param shipIds
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmRebate(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

		boolean tx = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {

				List<Record> ships = Db.find("SELECT ship_id, ship_rebate_fee,ship_id,network_id FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
				LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();

				List<KdShipFee> shipFeeList = new ArrayList<>();





				for (Record record:ships) {

					//插入费用表
					KdShipFee shipFee = new KdShipFee();
					shipFee.set("ship_id",record.get("ship_id"))
							.set("resource_id",record.get("ship_id"))
							.set("resource_type",1)
							.set("fee_type",11)
							.set("fee",record.get("ship_rebate_fee"))
							.set("network_id",record.get("network_id"))
							.set("company_id",user.getCompanyId())
							.set("create_time",time);
					shipFeeList.add(shipFee);


					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (shipFeeMap.containsKey(key)){
						double ship_rebate_fee = (double)shipFeeMap.get(key)+record.getBigDecimal("ship_rebate_fee").doubleValue();
						shipFeeMap.put(key ,ship_rebate_fee);
					}else {
						shipFeeMap.put(key ,record.getBigDecimal("ship_rebate_fee").doubleValue());
						networkShipIdMap.put(key,record.getInt("ship_id"));
					}
				}
				int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
				if(shipFeeSave.length!=shipFeeList.size())return false;



				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
					flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
					flow.set("fee_type", 11).set("id_type", 1).set("settlement_type", 2).set("inout_type", 1).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
					flow.set("resource_id",networkShipIdMap.get(id));

					if (!flow.save()) return false;
					kdFlows.add(flow);
				}

				//插入流水明细表
				HashMap<Integer,Integer>  kdFlowIdMap = new HashMap<>();
				for (KdFlow flow: kdFlows) {
					kdFlowIdMap.put(flow.getInt("network_id"),flow.getInt("id"));
				}
				List<KdFlowDetail> kdFlowDetailList = new ArrayList<KdFlowDetail>() ;
				for (Record record:ships) {
					KdFlowDetail kdFlowDetail = new KdFlowDetail();
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_rebate_fee"));
					kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				if(!(Db.update("update kd_ship set ship_is_rebate=1 where ship_id in("+shipIds+")")>0)) return false;

				return true;
			}
		});
		return tx;
	}


	/**
	 * 根据干线类型结算
	 * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费 8:到站卸车费 9:到站其他费
	 * @param loadIds
	 * @param trunkLineType
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmtrunkPay(String loadIds, String trunkLineType, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				//String[] netWorkId=user.toNetWorkIdsStr().split(",");
				String[] ids=loadIds.split(",");
				String fillType = "";
				String table ="kd_load_gx_fee";
				if ("1".equals(trunkLineType)) fillType =  "load_nowtrans_fill";
				else if ("2".equals(trunkLineType)) fillType =  "load_nowoil_fill";
				else if ("3".equals(trunkLineType)) fillType =  "load_backtrans_fill";
				else if ("4".equals(trunkLineType)) fillType =  "load_allsafe_fill";
				else if ("5".equals(trunkLineType)) fillType =  "load_start_fill";
				else if ("6".equals(trunkLineType)) fillType =  "load_other_fill";
				else if ("7".equals(trunkLineType)) fillType =  "load_attrans_fill";
				else if ("8".equals(trunkLineType)){
					table = "kd_truck_load";
					fillType =  "load_atunload_fill";
				}else if ("9".equals(trunkLineType)){
					table = "kd_truck_load";
					fillType =  "load_atother_fill";
				}
				if( Db.update("UPDATE "+table+" SET "+fillType+" = 1 WHERE load_id in ("+loadIds+") ")<1) return false;

				for (String loadId :ids ) {
					List<KdShip> nships = new KdShipSvc().findLoadShips(Integer.parseInt(loadId));
					KdTrunkLoad kdTrunkLoad = new KdTrunkLoad().findById(loadId);
					int way = kdTrunkLoad.getInt("load_fee_allocation_way");
					double totalfee = kdTrunkLoad.getDouble("load_fee");
					double flowFee = 0 ;
					if ("1".equals(trunkLineType)){  totalfee = KdLoadGxFee.dao.findFirst("select load_nowtrans_fee from kd_load_gx_fee where load_id =?",loadId).get("load_nowtrans_fee");flowFee=totalfee;}
					else if ("2".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_nowoil_fee from kd_load_gx_fee where load_id =?",loadId).get("load_nowoil_fee");flowFee=totalfee;}
					else if ("3".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_backtrans_fee from kd_load_gx_fee where load_id =?",loadId).get("load_backtrans_fee");flowFee=totalfee;}
					else if ("4".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_allsafe_fee from kd_load_gx_fee where load_id =?",loadId).get("load_allsafe_fee");flowFee=totalfee;}
					else if ("5".equals(trunkLineType)){totalfee = KdLoadGxFee.dao.findFirst("select load_start_fee from kd_load_gx_fee where load_id =?",loadId).get("load_start_fee");flowFee=totalfee;}
					else if ("6".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_other_fee from kd_load_gx_fee where load_id =?",loadId).get("load_other_fee");flowFee=totalfee;}
					else if ("7".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_attrans_fee from kd_load_gx_fee where load_id =?",loadId).get("load_attrans_fee");flowFee=totalfee;}
					else if ("8".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_atunload_fee from kd_truck_load where load_id =?",loadId).get("load_atunload_fee");flowFee=totalfee;}
					else if ("9".equals(trunkLineType)){ totalfee = KdLoadGxFee.dao.findFirst("select load_atother_fee from kd_truck_load where load_id =?",loadId).get("load_atother_fee");flowFee=totalfee;}

					double volume = kdTrunkLoad.getDouble("load_volume");
					double weight = kdTrunkLoad.getDouble("load_weight");
					int loadCount = kdTrunkLoad.getInt("load_count");
					List<KdShipFee> kdShipFeeList = new ArrayList<KdShipFee>() ;
					double avgfee = 0;
					double temp = 0;
					for (int i = 0; i < nships.size(); i++) {
						//int load_network_id = nships.get(i).getInt("load_network_id");
						//int load_next_network_id = new KdTrunkLoad().findFirst("select load_next_network_id from kd_truck_load where load_id =?",loadId).get("load_next_network_id");
						//费用分摊插入运单费用表
						KdShipFee shipFee=new KdShipFee();
						KdShip sp = nships.get(i);
						if(way==1){//按单
							if(nships.size()==(i+1)){
								shipFee.set("fee", totalfee-avgfee);
							}else{
								temp = new Double(df.format(totalfee/loadCount));
								shipFee.set("fee", temp);
								avgfee += temp;
							}
						}else if(way==2){//按体积
							if(nships.size()==(i+1)){
								shipFee.set("fee", totalfee-avgfee);

							}else{
								temp = new Double(df.format(totalfee*sp.getDouble("ship_volume")/volume));
								shipFee.set("fee", temp);
								avgfee += temp;
							}
						}else if(way==3){//按重量
							if(nships.size()==(i+1)){
								shipFee.set("fee", totalfee-avgfee);
							}else{
								temp = new Double(df.format(totalfee*sp.getDouble("ship_weight")/weight));
								shipFee.set("fee", temp);
								avgfee += temp;
							}
						}
						shipFee.set("ship_id",sp.getInt("ship_id")).set("resource_id",sp.getInt("ship_id")).set("resource_type",1).set("network_id",kdTrunkLoad.getInt("network_id"));
						shipFee.set("company_id",user.getCompanyId()).set("create_time",time);
						if ("1".equals(trunkLineType)) shipFee.set("fee_type",12);
						else if ("2".equals(trunkLineType)) shipFee.set("fee_type",13);
						else if ("3".equals(trunkLineType)) shipFee.set("fee_type",14);
						else if ("4".equals(trunkLineType)) shipFee.set("fee_type",15);
						else if ("5".equals(trunkLineType)) shipFee.set("fee_type",16);
						else if ("6".equals(trunkLineType)) shipFee.set("fee_type",17);
						else if ("7".equals(trunkLineType)) shipFee.set("fee_type",18);
						else if ("8".equals(trunkLineType)) shipFee.set("fee_type",19);
						else if ("9".equals(trunkLineType)) shipFee.set("fee_type",20);
						kdShipFeeList.add(shipFee);
					}
					int[] shipFeeSave = Db.batchSave(kdShipFeeList, kdShipFeeList.size());
					if(shipFeeSave.length!=kdShipFeeList.size())return false;

				}

				//插入流水表
				List<Record> loads = Db.find("SELECT load_atunload_fee, load_atother_fee, load_fee ,load_sn ,load_id,network_id   FROM kd_truck_load WHERE load_id IN  ("+loadIds+") ");
				LinkedHashMap feeMap = new LinkedHashMap<Integer,Double>();


				LinkedHashMap networkLoadIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:loads) {
					double flowFee = 0 ;
					if ("1".equals(trunkLineType)){  flowFee = KdLoadGxFee.dao.findFirst("select load_nowtrans_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_nowtrans_fee");}
					else if ("2".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_nowoil_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_nowoil_fee");}
					else if ("3".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_backtrans_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_backtrans_fee");}
					else if ("4".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_allsafe_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_allsafe_fee");}
					else if ("5".equals(trunkLineType)){flowFee = KdLoadGxFee.dao.findFirst("select load_start_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_start_fee");}
					else if ("6".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_other_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_other_fee");}
					else if ("7".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_attrans_fee from kd_load_gx_fee where load_id =?",record.getInt("load_id")).get("load_attrans_fee");}
					else if ("8".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_atunload_fee from kd_truck_load where load_id =?",record.getInt("load_id")).get("load_atunload_fee");}
					else if ("9".equals(trunkLineType)){ flowFee = KdLoadGxFee.dao.findFirst("select load_atother_fee from kd_truck_load where load_id =?",record.getInt("load_id")).get("load_atother_fee");}

					KdFlow kdFlow = new KdFlow();
					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (feeMap.containsKey(key)){
						double fee = (double)feeMap.get(key)+flowFee;
						feeMap.put(key ,fee);
					}else {
						feeMap.put(key ,flowFee);
						networkLoadIdMap.put(key,record.getInt("load_id"));
					}
				}

				for (Integer id:networkIds) {
					int load_next_network_id = new KdTrunkLoad().findFirst("select load_next_network_id from kd_truck_load where load_id =?",networkLoadIdMap.get(id)).get("load_next_network_id");
					KdFlow flow = new KdFlow();
					flow.set("fee",feeMap.get(id)).set("pay_type",payType).set("flow_no",flowNo).set("voucher_no",voucherNo).set("remark",remark);
					flow.set("user_id",user.getUserId()).set("create_time",time).set("company_id",user.getCompanyId());
					flow.set("fee_type",1).set("id_type",2).set("settlement_type",0).set("inout_type",1).set("flow_sn",new FlowSvc().getFlowSnByComId(user.getCompanyId()));
					flow.set("resource_id",networkLoadIdMap.get(id)).set("id_type",2);
					if ("1".equals(trunkLineType)) flow.set("fee_type",12).set("resource_type",5).set("network_id",id);
					else if ("2".equals(trunkLineType)) flow.set("fee_type",13).set("resource_type",6).set("network_id",id);
					else if ("3".equals(trunkLineType)) flow.set("fee_type",14).set("resource_type",7).set("network_id",id);
					else if ("4".equals(trunkLineType)) flow.set("fee_type",15).set("resource_type",8).set("network_id",id);
					else if ("5".equals(trunkLineType)) flow.set("fee_type",16).set("resource_type",9).set("network_id",id);
					else if ("6".equals(trunkLineType)) flow.set("fee_type",17).set("resource_type",10).set("network_id",id);
					else if ("7".equals(trunkLineType)) flow.set("fee_type",18).set("resource_type",11).set("network_id",load_next_network_id);
					else if ("8".equals(trunkLineType)) flow.set("fee_type",19).set("resource_type",12).set("network_id",load_next_network_id);
					else if ("9".equals(trunkLineType)) flow.set("fee_type",20).set("resource_type",13).set("network_id",load_next_network_id);
					if(!flow.save()) return  false;
					kdFlows.add(flow);
				}

				//插入流水明细表
				HashMap<Integer,Integer>  kdFlowIdMap = new HashMap<>();
				for (KdFlow flow: kdFlows) {
					kdFlowIdMap.put(flow.getInt("network_id"),flow.getInt("id"));
				}
				List<KdFlowDetail> kdFlowDetailList = new ArrayList<KdFlowDetail>() ;
				for (Record record:loads) {
					KdFlowDetail kdFlowDetail = new KdFlowDetail();
					kdFlowDetail.set("load_id",record.get("load_id")).set("fee", feeMap.get(record.get("network_id"))).set("network_id",record.get("network_id"));
					kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				return true;
			}
		});
		return tx;
	}
}