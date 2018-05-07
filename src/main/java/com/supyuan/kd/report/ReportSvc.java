package com.supyuan.kd.report;

import java.text.SimpleDateFormat;
import java.util.*;

import com.supyuan.kd.loading.KdTrunkLoad;
import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.DateUtils;
import com.supyuan.util.StrUtils;


/**
 * 报表服务类
 * @author liangxp
 *
 * Date:2017年12月25日下午5:53:59 
 * 
 * @email liangxp@anfawuliu.com
 */
public class ReportSvc  extends BaseService {
	
	
/*	public List<ProfitCollectModel> profitCollect(String startTime, String endTime, int networkId, SessionUser user){
		
		List<ProfitCollectModel> res = new ArrayList<ProfitCollectModel>();
		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();
		if(StringUtils.isNotBlank(startTime)){
			start = startTime;
		}
		if(StringUtils.isNotBlank(endTime)){
			end = endTime;
		}
		List<String> monthList = DateUtils.getMonthList(start, end);
		if(monthList.size()==0){
			return res;
		}
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		end = end + "-" + DateUtils.getDateDay(end) + " 23:59:59";
		String networkids = user.toNetWorkIdsStr();
		if(networkId>0){
			networkids = networkId+"";
		}
		
		StringBuilder select = 	new StringBuilder("select IFNULL(sum(m.ship_total_fee),0) totalfee,IFNULL(sum(m.tsfee),0) zzfee,IFNULL(sum(m.thfee),0) thfee,IFNULL(sum(m.dbfee),0) dbfee,IFNULL(sum(m.gxfee),0) gxfee,IFNULL(sum(m.shfee),0) shfee,count(*) total, DATE_FORMAT(m.create_time,'%Y-%m') ctime  from (SELECT s.ship_id,s.ship_total_fee,IFNULL(ts.ship_transfer_fee,0) tsfee,"
				+" sum(case tl.load_transport_type WHEN 1 then sf.fee  else 0 end) thfee,sum(case tl.load_transport_type WHEN 2 then sf.fee  else 0 end) dbfee ,"
				+" sum(case tl.load_transport_type WHEN 3 then sf.fee  else 0 end) gxfee ,sum(case tl.load_transport_type WHEN 4 then sf.fee  else 0 end) shfee , s.create_time  "
				+" FROM kd_ship s LEFT JOIN kd_truck_ship ks ON s.ship_id=ks.ship_id  "
				+" LEFT JOIN kd_truck_load tl ON tl.load_id=ks.truck_load_id "
				+" LEFT JOIN kd_ship_transfer ts ON ts.ship_id=s.ship_id  "
				+" LEFT JOIN kd_ship_fee sf  ON sf.ship_id=s.ship_id  AND sf.load_id=ks.truck_load_id " 
				+" where s.ship_deleted=0 and s.network_id in (" + networkids +") and  s.create_time>? and s.create_time<? GROUP BY s.ship_id)m GROUP BY ctime");
		List<Record> ships = Db.find(select.toString(), start, end);
		ProfitCollectModel last = new ProfitCollectModel();
		for (int i = 0; i < monthList.size(); i++) {
			String string = monthList.get(i);
			ProfitCollectModel model = new ProfitCollectModel();
			model.setTime(string);
			for (Record ship : ships) {
				if(StringUtils.isNotBlank(ship.getStr("ctime"))&&ship.getStr("ctime").equals(string)){
					model.setShipCount(ship.getLong("total"));
					model.setTotalfee(ship.getBigDecimal("totalfee").doubleValue());
					model.setGanxianfee(ship.getDouble("gxfee").doubleValue());
					model.setTihuofee(ship.getDouble("thfee").doubleValue());
					model.setDuanbofee(ship.getDouble("dbfee").doubleValue());
					model.setSonghuofee(ship.getDouble("shfee").doubleValue());
					model.setZhongzhuanfee(ship.getBigDecimal("zzfee").doubleValue());
					
					
					last.setGanxianfee(last.getGanxianfee()+model.getGanxianfee());
					last.setTihuofee(last.getTihuofee()+model.getTihuofee());
					last.setDuanbofee(last.getDuanbofee()+model.getDuanbofee());
					last.setSonghuofee(last.getSonghuofee()+model.getSonghuofee());
					last.setShipCount(last.getShipCount()+model.getShipCount());
					last.setTotalfee(last.getTotalfee()+model.getTotalfee());
					last.setZhongzhuanfee(last.getZhongzhuanfee()+model.getZhongzhuanfee());
					break;
				}
			}
			res.add(model);
			if(i==11){
				break;
			}
		}
		Collections.sort(res);
		last.setTime("合计");
		res.add(last);
		return res;
	}
	
	
	*//**
	 * 应收汇总表
	 * @param startTime
	 * @param endTime
	 * @param networkId
	 * @param user
	 * @return
	 *//*
	public List<ReSummaryModel> getReSummaryData(String startTime, String endTime, String networkId, SessionUser user){
		List<ReSummaryModel> dataList=new ArrayList();
		
		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();
		if(StringUtils.isNotBlank(startTime)){
			start = startTime;
		}
		if(StringUtils.isNotBlank(endTime)){
			end = endTime;
		}
		List<String> monthList = DateUtils.getMonthList(start, end);
		if(monthList.size()==0){
			return null;
		}
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		
		String networkids = user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(networkId)){
			networkids = networkId;
		}
		
		StringBuilder sql = new StringBuilder("select Count(s.ship_id) AS countShip,Sum(s.ship_total_fee) as allFee, DATE_FORMAT(s.create_time,'%Y-%m') time  from kd_ship s  where s.ship_deleted=0  and s.network_id in (" + networkids +") and  DATE_FORMAT(s.create_time,'%Y-%m')>=? and DATE_FORMAT(s.create_time,'%Y-%m')<=?  GROUP BY time");
		List<Record> record=Db.find(sql.toString(),start,end);
		StringBuilder sql2=new StringBuilder(); 
		
		sql2.append("SELECT DATE_FORMAT(st.ship_transfer_time,'%Y-%m') time,sum(ifnull(st.ship_transfer_fee,0)) transferFee from kd_ship_transfer st");
		sql2.append(" LEFT JOIN kd_ship s on s.ship_id=st.ship_id ");
		sql2.append(" where s.ship_deleted=0 and s.network_id!=st.network_id ");
		sql2.append(" and st.network_id in ("+networkids+")");
		sql2.append(" and  DATE_FORMAT(st.ship_transfer_time,'%Y-%m')>=? and DATE_FORMAT(st.ship_transfer_time,'%Y-%m')<=?");
		sql2.append(" GROUP BY time");
		
		List<Record> record2=Db.find(sql2.toString(),start,end);
		
		StringBuilder sql3=new StringBuilder(); 
		
		sql3.append(" SELECT sum(ifnull(sf.fee,0)) as loadFee,DATE_FORMAT(tl.create_time,'%Y-%m') time");
		sql3.append(" FROM kd_truck_load tl");
		sql3.append(" LEFT JOIN kd_truck_ship ts ON ts.truck_load_id=tl.load_id");
		sql3.append(" LEFT JOIN kd_ship s ON s.ship_id=ts.ship_id");
		sql3.append(" LEFT JOIN kd_ship_fee sf ON sf.load_id=tl.load_id AND sf.ship_id=s.ship_id AND sf.fee_type=1");
		sql3.append(" where s.ship_deleted=0  and tl.network_id in("+networkids+") and DATE_FORMAT(tl.create_time,'%Y-%m')>=? and DATE_FORMAT(tl.create_time,'%Y-%m')<=?");
		sql3.append(" and tl.network_id != s.network_id");
		sql3.append("  GROUP BY time");
		
		List<Record> record3=Db.find(sql3.toString(),start,end);
		
												
		
		ReSummaryModel last=new ReSummaryModel();
		for (String time : monthList) {
			
			ReSummaryModel model=new ReSummaryModel();
			
			model.setTime(time);
			
			for (Record data : record) {
				if(data.getStr("time").equals(time)){
					model.setShipCount(data.getLong("countShip"));
					model.setAllfee(data.getBigDecimal("allFee").doubleValue());
					last.setAllfee(last.getAllfee()+model.getAllfee());
					last.setShipCount(last.getShipCount()+model.getShipCount());
					break;
				}
			}
			
			for (Record data2 : record2) {
					if(data2.getStr("time").equals(time)){
						model.setNetRefee(data2.getBigDecimal("transferFee").doubleValue());
						last.setNetRefee(last.getNetRefee()+model.getNetRefee());
						break;
				}
			}
			
			for (Record data3 : record3) {
				if(data3.getStr("time").equals(time)){
					model.setNetRefee(model.getNetRefee()+data3.getDouble("loadFee"));
					last.setNetRefee(last.getNetRefee()+model.getNetRefee());
					break;
				}
			}
			
			dataList.add(model);
		}
		last.setTime("合计");
		dataList.add(last);
		
		return dataList;
	}
	
	*//**
	 * 应付汇总表
	 * @param startTime
	 * @param endTime
	 * @param networkId
	 * @param user
	 * @return
	 *//*
	public List<PaySummaryModel> getPaySummaryData(String startTime, String endTime, String networkId, SessionUser user){
		List<PaySummaryModel> dataList=new ArrayList();
		
		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();
		
		if(StringUtils.isNotBlank(startTime)){
			start = startTime;
		}
		if(StringUtils.isNotBlank(endTime)){
			end = endTime;
		}
		
		List<String> monthList = DateUtils.getMonthList(start, end);
		if(monthList.size()==0){
			return null;
		}
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		String networkids = user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(networkId)){
			networkids = networkId;
		}
		
		StringBuilder sql = new StringBuilder("select count(DISTINCT tl.load_id) as countLoad,DATE_FORMAT(tl.create_time,'%Y-%m') time from kd_truck_load tl"+
											" LEFT JOIN kd_truck_ship ts ON ts.truck_load_id=tl.load_id"+
											" LEFT JOIN kd_ship s ON s.ship_id=ts.ship_id"+
											" where s.ship_deleted=0  and s.network_id in("+networkids+") and DATE_FORMAT(tl.create_time,'%Y-%m')>=? and DATE_FORMAT(tl.create_time,'%Y-%m')<=? GROUP BY time");
		List<Record> record=Db.find(sql.toString(),start,end);
		StringBuilder sql2 = new StringBuilder("SELECT"+
												" sum(sf.fee) as loadFee,DATE_FORMAT(tl.create_time,'%Y-%m') time"+
												" FROM kd_truck_load tl"+
												" LEFT JOIN kd_truck_ship ts ON ts.truck_load_id=tl.load_id"+
												" LEFT JOIN kd_ship s ON s.ship_id=ts.ship_id"+
												" LEFT JOIN kd_ship_fee sf ON sf.load_id=tl.load_id AND sf.ship_id=s.ship_id AND sf.fee_type=1"+
												" where s.ship_deleted=0 and s.network_id!=tl.network_id  and s.network_id in("+networkids+")and DATE_FORMAT(tl.create_time,'%Y-%m')>=? and DATE_FORMAT(tl.create_time,'%Y-%m')<=?"+
												" GROUP BY time ");
		
		List<Record> record2=Db.find(sql2.toString(),start,end);
		
		StringBuilder sql3 = new StringBuilder("select count(DISTINCT st.ship_id) as transferCount,DATE_FORMAT(st.ship_transfer_time,'%Y-%m') time from kd_ship_transfer st"+
											   " LEFT JOIN kd_ship s ON s.ship_id=st.ship_id"+
											   " where s.ship_deleted=0  and s.network_id in("+networkids+") and s.network_id!=st.network_id and DATE_FORMAT(st.ship_transfer_time,'%Y-%m')>=? and DATE_FORMAT(st.ship_transfer_time,'%Y-%m')<=? GROUP BY time" );

		List<Record> record3=Db.find(sql3.toString(),start,end);
		
		StringBuilder sql4 = new StringBuilder("SELECT sum(st.ship_transfer_fee) as transferfee,DATE_FORMAT(st.ship_transfer_time,'%Y-%m') time from kd_ship_transfer st left join kd_ship s on st.ship_id=s.ship_id where s.network_id!=st.network_id and s.network_id in("+networkids+") and s.ship_deleted=0  and DATE_FORMAT(st.ship_transfer_time,'%Y-%m')>=? and DATE_FORMAT(st.ship_transfer_time,'%Y-%m')<=? GROUP BY time");
		
		List<Record> record4=Db.find(sql4.toString(),start,end);
		
		PaySummaryModel last=new PaySummaryModel();
		for (String time : monthList) {
			
			PaySummaryModel model=new PaySummaryModel();
			
			model.setTime(time);
			
			for (Record data : record) {
				if(data.getStr("time").equals(time)){
					model.setCountLoad(data.getLong("countLoad"));
					last.setCountLoad(last.getCountLoad()+model.getCountLoad());
					break;
				}
			}
			
			for (Record data2 : record2) {
					if(data2.getStr("time").equals(time)){
						if(data2.getDouble("loadFee")!=null) model.setLoadFee(data2.getDouble("loadFee"));
						last.setLoadFee(last.getLoadFee()+model.getLoadFee());
						break;
				}
				
			}
			for (Record data3 : record3) {
				if(data3.getStr("time").equals(time)){
					model.setTransferCount(data3.getLong("transferCount"));
					last.setTransferCount(last.getTransferCount()+model.getTransferCount());
					break;
			}
			}
			for (Record data4 : record4) {
				if(data4.getStr("time").equals(time)){
					model.setTransferfee(data4.getBigDecimal("transferfee").doubleValue());
					last.setTransferfee(last.getTransferfee()+model.getTransferfee());
					break;
			}
			}
			model.setNetPayfee(model.getTransferfee()+model.getLoadFee());
			last.setNetPayfee(last.getNetPayfee()+model.getNetPayfee());
			
			dataList.add(model);
		}
		last.setTime("合计");
		dataList.add(last);
		
		return dataList;
	}
	*//**
	 * 操作明细表
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 *//*
	public Page<KdShip> queryShipOperation(Paginator paginator, SessionUser user, OperationSearchModel model) {
		String select = "SELECT s.ship_id,getNetworkNameById(s.network_id) AS netWorkName,kc.customer_corp_name as corpName,kc.customer_name as customerName,s.ship_sn,s.ship_customer_number,s.create_time,"+
					   "getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName, "+
					   "getRegion(s.ship_from_city_code) fromAdd,getRegion(s.ship_to_city_code) AS toAdd,s.ship_volume,s.ship_weight,"+
					   "s.ship_amount,s.ship_agency_fund,s.ship_pay_way,s.ship_total_fee, s.ship_fee,s.ship_pickup_fee,s.ship_delivery_fee,"+
					   "s.ship_insurance_fee,s.ship_package_fee,s.ship_addon_fee,t.truck_id_number,tl.load_depart_time,t.truck_driver_name,"+
                   //  "IFNULL(GROUP_CONCAT(t.truck_id_number,'|', t.truck_driver_name,'|', tl.load_depart_time,'|', t.truck_driver_name,'|',sf.fee, '|', tl.load_transport_type),'') AS thFee,"+
				"IFNULL(GROUP_CONCAT(t.truck_id_number,'|',tl.load_depart_time,'|',t.truck_driver_name,'|',sf.fee,'|types',tl.load_transport_type),'') AS thFee,"+
"ts.ship_transfer_time,"+
" IFNULL(ts.ship_transfer_fee,0.0)AS ship_transfer_fee,si.sign_person,si.sign_time,  "+
"ifnull(s.ship_total_fee,0)-sum(ifnull(sf.fee,0))-ifnull(ts.ship_transfer_fee,0) AS profit,"+
"ifnull(ROUND(((ifnull(s.ship_total_fee,0) - SUM(ifnull(sf.fee,0)) - ifnull(ts.ship_transfer_fee,0)) / ifnull(s.ship_total_fee,0))*100,2),0) AS rate  ";
		StringBuilder bu = new StringBuilder(" FROM kd_ship s ");
		bu.append(" LEFT JOIN kd_truck_ship ks ON s.ship_id=ks.ship_id ");
		bu.append(" LEFT JOIN kd_truck_load tl ON tl.load_id=ks.truck_load_id ");
		bu.append(" LEFT JOIN truck t ON t.truck_id=tl.truck_id ");
		bu.append(" LEFT JOIN kd_ship_transfer ts ON s.ship_id=ts.ship_id ");
		bu.append(" LEFT JOIN kd_customer kc ON kc.customer_id=ts.transfer_id ");
		bu.append(" LEFT JOIN kd_ship_sign si ON si.ship_id=ks.ship_id ");
		bu.append(" LEFT JOIN kd_ship_fee sf  ON sf.ship_id=ks.ship_id  AND sf.load_id=ks.truck_load_id ");
		bu.append(" where s.ship_deleted=0  and s.network_id in ("+user.toNetWorkIdsStr()+") ");
 
		List<Object> paras = new ArrayList<Object>();
		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			bu.append(" and s.network_id=?");
			paras.add(model.getNetWorkId());
		}
		if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime()))
		{
			bu.append("and s.create_time BETWEEN ? AND ?");
			paras.add(model.getStartTime());
			paras.add(model.getEndTime());
		}
		if (StringUtils.isNotBlank(model.getShipSn())){
			bu.append(" and s.ship_sn like ? ");
			paras.add("%"+model.getShipSn()+"%");

		}
		if (StringUtils.isNotBlank(model.getSenderId())){
			bu.append(" and s.ship_sender_id= ?");
			paras.add(model.getSenderId());

		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(model.getReceiverId())){
			bu.append(" and s.ship_receiver_id=?");
			paras.add(model.getReceiverId());

		}
		if(StringUtils.isNotBlank(model.getFromCode())){
			bu.append(" and s.ship_from_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getFromCode())+"%");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(model.getToCode())){
			bu.append(" and s.ship_to_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getToCode())+"%");
		}
		if(StringUtils.isNotBlank(model.getCustomerNumber())){
			bu.append(" and s.ship_customer_number like ?");
			paras.add("%"+model.getCustomerNumber()+"%");
		}
		bu.append(" group by  s.ship_id order by  s.create_time desc ");
		return KdShip.dao.paginate(paginator,select.toString(),bu.toString(),paras.toArray());
	}*/


	/**
	 * 营业额月报表列表
	 * @author huangym
	 * @param user
	 * @param model
	 */
	public List<KdShip> queryTurnoverReportList(SessionUser user, OperationSearchModel model){
		String select = "SELECT s.network_id, getNetworkNameById(s.network_id) AS netName, COUNT(s.ship_id) AS total_amount , COALESCE(SUM(s.ship_nowpay_fee), 0) AS total_ship_nowpay_fee , COALESCE(SUM(s.ship_pickuppay_fee), 0) AS total_ship_pickuppay_fee , COALESCE(SUM(s.ship_receiptpay_fee), 0) AS total_ship_receiptpay_fee , COALESCE(SUM(s.ship_monthpay_fee), 0) AS total_ship_monthpay_fee , COALESCE(SUM(s.ship_arrearspay_fee), 0) AS total_ship_arrearspay_fee , COALESCE(SUM(s.ship_goodspay_fee), 0) AS total_ship_goodspay_fee , COALESCE(SUM(sa.plus_fee), 0) AS total_plus_fee , COALESCE(SUM(sa.minus_fee), 0) AS total_minus_fee,COALESCE(SUM(s.ship_rebate_fee),0) AS total_ship_rebate_fee, date_format(s.create_time, '%Y-%m') AS month ";
		StringBuilder bu = new StringBuilder(" FROM kd_ship s LEFT JOIN kd_ship_abnormal sa ON s.ship_id = sa.ship_id ");

		if(StringUtils.isNotBlank(model.getStartTime())&&StringUtils.isNotBlank(model.getEndTime())){
			bu.append(" WHERE s.create_time BETWEEN '"+model.getStartTime()+"-01' AND '"+model.getEndTime()+"-31' ");
		}else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//当前日期
			String now = format.format(new Date());
			//六个月前日期
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
			Date m6 = c.getTime();
			String mon6 = format.format(m6);
			bu.append(" WHERE s.create_time BETWEEN '"+mon6+"' AND '"+now+"' ");
		}
		if(StringUtils.isNotBlank(model.getNetWorkId())){
			bu.append(" AND s.network_id = "+model.getNetWorkId());
		}else {
			bu.append(" AND s.network_id IN ("+user.toNetWorkIdsStr()+") ");
		}
		bu.append(" GROUP BY date_format(s.create_time, '%Y-%m'), s.network_id; ");

		return KdShip.dao.find(select+bu.toString());
	}

	public Page<KdTrunkLoad> queryLoadGrossProfit(Paginator paginator, SessionUser user, OperationSearchModel model, boolean isExcel) {
		String select = "SELECT tl.load_id, tl.load_sn, getNetworkNameById(tl.network_id) AS netName , getNetworkNameById(tl.load_next_network_id) AS nextNetName, tl.load_depart_time, t.truck_id_number , t.truck_driver_name, t.truck_driver_mobile, s.network_id, getRegion(tl.load_delivery_from) AS fromAdd , getRegion(tl.load_delivery_to) AS toAdd, tl.load_count, tl.load_amount , tl.load_volume, tl.load_weight , COALESCE(SUM(s.ship_nowpay_fee), 0) AS total_ship_nowpay_fee , COALESCE(SUM(s.ship_pickuppay_fee), 0) AS total_ship_pickuppay_fee , COALESCE(SUM(s.ship_receiptpay_fee), 0) AS total_ship_receiptpay_fee , COALESCE(SUM(s.ship_monthpay_fee), 0) AS total_ship_monthpay_fee , COALESCE(SUM(s.ship_arrearspay_fee), 0) AS total_ship_arrearspay_fee , COALESCE(SUM(s.ship_goodspay_fee), 0) AS total_ship_goodspay_fee , COALESCE(SUM(sa.plus_fee), 0) AS total_plus_fee , COALESCE(SUM(sa.minus_fee), 0) AS total_minus_fee , COALESCE(SUM(gf.load_nowtrans_fee), 0) AS total_load_nowtrans_fee , COALESCE(SUM(gf.load_nowoil_fee), 0) AS total_load_nowoil_fee , COALESCE(SUM(gf.load_backtrans_fee), 0) AS total_load_backtrans_fee , COALESCE(SUM(gf.load_attrans_fee), 0) AS total_load_attrans_fee , COALESCE(SUM(gf.load_allsafe_fee), 0) AS total_load_allsafe_fee , COALESCE(SUM(gf.load_start_fee), 0) AS total_load_start_fee , COALESCE(SUM(gf.load_other_fee), 0) AS total_load_other_fee, COALESCE(SUM(s.ship_rebate_fee),0) AS total_ship_rebate_fee ";
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load tl ");
		bu.append(" LEFT JOIN kd_truck_ship ts ON ts.truck_load_id = tl.load_id");
		bu.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		bu.append(" LEFT JOIN kd_ship_abnormal sa ON s.ship_id = sa.ship_id");
		bu.append(" LEFT JOIN truck t ON tl.truck_id=t.truck_id");
		bu.append(" LEFT JOIN kd_load_gx_fee gf ON gf.load_id = tl.load_id  WHERE tl.load_transport_type = 3 ");
		if(StringUtils.isNotBlank(model.getNetWorkId())){
			bu.append(" AND tl.network_id = "+model.getNetWorkId());
		}else {
			bu.append(" AND tl.network_id in("+ user.toNetWorkIdsStr() +") ");
		}

		if(StringUtils.isNotBlank(model.getStartTime())&&StringUtils.isNotBlank(model.getEndTime())){
			bu.append(" AND tl.create_time BETWEEN '"+model.getStartTime()+"-01' AND '"+model.getEndTime()+"-31'");
		}else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//当前日期
			String now = format.format(new Date());
			//六个月前日期
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
			Date m6 = c.getTime();
			String mon6 = format.format(m6);
			bu.append(" AND s.create_time BETWEEN '"+mon6+"' AND '"+now+"' ");
		}

		if(StringUtils.isNotBlank(model.getTruckIdNumber())){
			bu.append(" AND t.truck_id_number like '%"+model.getTruckIdNumber()+"%' ");
		}

		if(StringUtils.isNotBlank(model.getLoadSn())){
			bu.append(" AND tl.load_sn like '%"+model.getLoadSn()+"%' ");
		}
		bu.append(" GROUP BY tl.load_id ORDER BY tl.create_time DESC");

		if(isExcel){
			List<KdTrunkLoad> trunkLoads = KdTrunkLoad.dao.find(select + bu.toString());
			return new Page<KdTrunkLoad>(trunkLoads, 1, trunkLoads.size(), 1, trunkLoads.size());
		}else{
			return KdTrunkLoad.dao.paginate(paginator,select,bu.toString());
		}
	}


	/**
	 * 应收应付汇总表
	 * @param startTime
	 * @param endTime
	 * @param networkId
	 * @param user
	 * @return
	 */
	public List<RePayModel> getRePayModelData(String startTime, String endTime, String networkId, SessionUser user){
		String networkids = user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(networkId)){
			networkids = networkId;
		}

		List<RePayModel> dataList=getInitRePayList();

		//运单费用 应收
		StringBuilder shipSql = new StringBuilder();
		shipSql.append("SELECT count(s.ship_id) as shipCount,ship_pay_way,");
		shipSql.append("sum(CASE s.ship_pay_way WHEN 1 THEN s.ship_nowpay_fee WHEN 2 THEN s.ship_pickuppay_fee WHEN 3 THEN ship_receiptpay_fee WHEN 4 THEN ship_monthpay_fee WHEN 5 THEN ship_arrearspay_fee WHEN 6 THEN ship_goodspay_fee ELSE 0 END) as shipFee,");
		shipSql.append("sum(IFNULL(f.fee,0)) as getFee,");
		shipSql.append("(sum(CASE s.ship_pay_way WHEN 1 THEN s.ship_nowpay_fee WHEN 2 THEN s.ship_pickuppay_fee WHEN 3 THEN ship_receiptpay_fee WHEN 4 THEN ship_monthpay_fee WHEN 5 THEN ship_arrearspay_fee WHEN 6 THEN ship_goodspay_fee ELSE 0 END)-sum(ifnull(f.fee,0))) as noGetFee");
		shipSql.append(" FROM kd_ship s LEFT JOIN kd_ship_fee f ON s.ship_id=f.ship_id and f.fee_type<7");
		shipSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		shipSql.append(" group by s.ship_pay_way" );
		List<Record> shipFee=Db.find(shipSql.toString());
		//异动增加 应收
		StringBuilder abnormalAddSql = new StringBuilder();
		abnormalAddSql.append("SELECT count(s.ship_id) as shipCount,sum(ifnull(a.plus_fee,0)) as feeTotal,");
		abnormalAddSql.append("7 as type,sum(ifnull(f.fee,0)) as getFee,(sum(ifnull(a.plus_fee,0))-sum(ifnull(f.fee,0))) as noGetFee");
		abnormalAddSql.append(" FROM kd_ship_abnormal a  LEFT JOIN kd_ship s ON s.ship_id=a.ship_id");
		abnormalAddSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=23");
		abnormalAddSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and a.minus_fee=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> abnormalAdd=Db.find(abnormalAddSql.toString());
		//异动减款 应付
		StringBuilder abnormalReduceSql = new StringBuilder();
		abnormalReduceSql.append("SELECT count(s.ship_id) as shipCount,sum(ifnull(a.minus_fee,0)) as feeTotal,");
		abnormalReduceSql.append("8 as type,sum(ifnull(f.fee,0)) as getFee,(sum(ifnull(a.minus_fee,0))-sum(ifnull(f.fee,0))) as noGetFee");
		abnormalReduceSql.append(" FROM kd_ship_abnormal a LEFT JOIN kd_ship s ON s.ship_id=a.ship_id");
		abnormalReduceSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=22");
		abnormalReduceSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and a.plus_fee=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> abnormalReduce=Db.find(abnormalReduceSql.toString());
		//回扣
		StringBuilder rebateSql = new StringBuilder();
		rebateSql.append("SELECT count(s.ship_id) as shipCount,sum(ifnull(s.ship_rebate_fee,0)) as feeTotal,");
		rebateSql.append("9 as type,ifnull(f.fee,0) as getFee,(sum(ifnull(s.ship_rebate_fee,0))-sum(ifnull(f.fee,0))) as noGetFee");
		rebateSql.append(" FROM kd_ship s ");
		rebateSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=11");
		rebateSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and s.ship_rebate_fee>0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> rebate=Db.find(rebateSql.toString());
		//提货短驳送货费
		StringBuilder loadSql = new StringBuilder();
		loadSql.append("SELECT sum(ifnull(t.load_fee,0)) as feeTotal,case t.load_transport_type when 1 then 10 when 2 then 11 when 4 then 12 else 0 end type,count(s.ship_id) as shipCount,");
		loadSql.append("sum(ifnull(f.fee,0)) as getFee,(sum(ifnull(t.load_fee,0))-sum(ifnull(f.fee,0))) as notGetFee ");
		loadSql.append(" FROM kd_truck_load t ");
		loadSql.append(" LEFT JOIN kd_truck_ship ts ON  t.load_id = ts.truck_load_id");
		loadSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id and f.fee_type in(7,8,9)");
		loadSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		loadSql.append(" AND t.load_transport_type is not null GROUP BY t.load_transport_type" );
		List<Record> loadFee=Db.find(loadSql.toString());
		//中转费
		StringBuilder transferSql = new StringBuilder();
		transferSql.append("SELECT sum(st.ship_transfer_fee) as feeTotal,sum(f.fee) as getFee,(sum(st.ship_transfer_fee)-ifnull(sum(f.fee),0)) as noGetFee,13 as type,count(st.ship_id) as shipCount");
		transferSql.append(" FROM kd_ship_transfer st");
		transferSql.append(" LEFT JOIN kd_ship s on s.ship_id=st.ship_id");
		transferSql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=st.ship_id AND f.fee_type=10");
		transferSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> transferFee=Db.find(transferSql.toString());
		//现付运输费
		StringBuilder loadNowtransSql = new StringBuilder();
		loadNowtransSql.append("SELECT sum(g.load_nowtrans_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_nowtrans_fee)-ifnull(sum(f.fee),0)) as noGetFee,14 as type,sum(if(g.load_nowtrans_fee>0,1,0)) shipCount");
		loadNowtransSql.append(" FROM kd_load_gx_fee g");
		loadNowtransSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadNowtransSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadNowtransSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadNowtransSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=12");
		loadNowtransSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadNowtrans=Db.find(loadNowtransSql.toString());
		//现付油卡费
		StringBuilder loadNowoilSql = new StringBuilder();
		loadNowoilSql.append("SELECT sum(g.load_nowoil_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_nowoil_fee)-ifnull(sum(f.fee),0)) as noGetFee,15 as type,sum(if(g.load_nowoil_fee>0,1,0)) shipCount");
		loadNowoilSql.append(" FROM kd_load_gx_fee g");
		loadNowoilSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadNowoilSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadNowoilSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadNowoilSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=13");
		loadNowoilSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadNowoilFee=Db.find(loadNowoilSql.toString());
		//回付运输费
		StringBuilder loadBacktransSql = new StringBuilder();
		loadBacktransSql.append("SELECT sum(g.load_backtrans_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_backtrans_fee)-ifnull(sum(f.fee),0)) as noGetFee,16 as type,sum(if(g.load_backtrans_fee>0,1,0)) shipCount");
		loadBacktransSql.append(" FROM kd_load_gx_fee g");
		loadBacktransSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadBacktransSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadBacktransSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadBacktransSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=14");
		loadBacktransSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadBacktransFee=Db.find(loadBacktransSql.toString());
		//到付运输费
		StringBuilder loadAttransSql = new StringBuilder();
		loadAttransSql.append("SELECT sum(g.load_attrans_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_attrans_fee)-ifnull(sum(f.fee),0)) as noGetFee,17 as type,sum(if(g.load_attrans_fee>0,1,0)) shipCount");
		loadAttransSql.append(" FROM kd_load_gx_fee g");
		loadAttransSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadAttransSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadAttransSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadAttransSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=18");
		loadAttransSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadAttransFee=Db.find(loadAttransSql.toString());
		//整车保险费
		StringBuilder loadAllsafeSql = new StringBuilder();
		loadAllsafeSql.append("SELECT sum(g.load_allsafe_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_allsafe_fee)-ifnull(sum(f.fee),0)) as noGetFee,18 as type,sum(if(g.load_allsafe_fee>0,1,0)) shipCount");
		loadAllsafeSql.append(" FROM kd_load_gx_fee g");
		loadAllsafeSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadAllsafeSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadAllsafeSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadAllsafeSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=15");
		loadAllsafeSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadAllsafeFee=Db.find(loadAllsafeSql.toString());
		//发站装车费
		StringBuilder loadStartSql = new StringBuilder();
		loadStartSql.append("SELECT sum(g.load_start_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_start_fee)-ifnull(sum(f.fee),0)) as noGetFee,19 as type,sum(if(g.load_start_fee>0,1,0)) shipCount");
		loadStartSql.append(" FROM kd_load_gx_fee g");
		loadStartSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadStartSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadStartSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadStartSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=16");
		loadStartSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadStartFee=Db.find(loadStartSql.toString());
		//发站其他费
		StringBuilder loadOtherSql = new StringBuilder();
		loadOtherSql.append("SELECT sum(g.load_other_fee) feeTotal,sum(f.fee) as getFee,(sum(g.load_other_fee)-ifnull(sum(f.fee),0)) as noGetFee,20 as type,sum(if(g.load_other_fee>0,1,0)) shipCount");
		loadOtherSql.append(" FROM kd_load_gx_fee g");
		loadOtherSql.append(" LEFT JOIN kd_truck_load t ON t.load_id = g.load_id");
		loadOtherSql.append(" LEFT JOIN kd_truck_ship ts ON t.load_id = ts.truck_load_id");
		loadOtherSql.append(" LEFT JOIN kd_ship s ON s.ship_id = ts.ship_id");
		loadOtherSql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id = f.ship_id AND f.fee_type=17");
		loadOtherSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadOtherFee=Db.find(loadOtherSql.toString());
		//到站卸车费
		StringBuilder loadAtunloadSql = new StringBuilder();
		loadAtunloadSql.append("SELECT 21 as type,sum(load_atunload_fee) as feeTotal,ifnull(sum(f.fee),0) as getFee,sum(if(load_atunload_fee>0,1,0)) as shipCount,(sum(load_atunload_fee)-ifnull(sum(f.fee),0)) as noGetFee");
		loadAtunloadSql.append(" FROM kd_truck_load tl");
		loadAtunloadSql.append(" LEFT JOIN kd_truck_ship ts ON ts.truck_load_id=tl.load_id");
		loadAtunloadSql.append(" LEFT JOIN kd_ship s ON ts.ship_id=s.ship_id");
		loadAtunloadSql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id AND f.fee_type=19");
		loadAtunloadSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadAtunloadFee=Db.find(loadAtunloadSql.toString());
		//到站其他费
		StringBuilder loadAtotherSql = new StringBuilder();
		loadAtotherSql.append("SELECT 22 as type,sum(load_atother_fee) as feeTotal,ifnull(sum(f.fee),0) as getFee,sum(if(load_atother_fee>0,1,0)) as shipCount,(sum(load_atother_fee)-ifnull(sum(f.fee),0)) as noGetFee");
		loadAtotherSql.append(" FROM kd_truck_load tl");
		loadAtotherSql.append(" LEFT JOIN kd_truck_ship ts ON ts.truck_load_id=tl.load_id");
		loadAtotherSql.append(" LEFT JOIN kd_ship s ON ts.ship_id=s.ship_id");
		loadAtotherSql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id AND f.fee_type=20");
		loadAtotherSql.append(" where s.network_id in("+networkids+") and s.ship_deleted=0 and DATE_FORMAT(s.create_time,('%Y-%m')) BETWEEN '"+startTime.trim()+"' and '"+endTime.trim()+"'" );
		List<Record> loadAtotherFee=Db.find(loadAtotherSql.toString());


		for(RePayModel model:dataList){
			for(Record ship:shipFee){
				int type=ship.get("ship_pay_way")==null?0:ship.getInt("ship_pay_way");
				if(model.getFeetype()==type){
					if(ship.get("shipCount")!=null){
						model.setReceivablesShipCount(ship.getLong("shipCount"));
					}
					if(ship.get("shipFee")!=null){
						model.setReceivablesTotal(ship.getDouble("shipFee"));
					}
					if(ship.get("getFee")!=null){
						model.setReceivablesGet(ship.getDouble("getFee"));
					}
					if(ship.get("noGetFee")!=null){
						model.setReceivablesNOGet(ship.getDouble("noGetFee"));
					}
					break;
				}
			}
			//异动增款
			for(Record abnormal:abnormalAdd){
				if(model.getFeetype()==abnormal.getLong("type")){
					if(abnormal.get("shipCount")!=null){
						model.setReceivablesShipCount(abnormal.getLong("shipCount"));
					}
					if(abnormal.get("feeTotal")!=null){
						model.setReceivablesTotal(abnormal.getDouble("feeTotal"));
					}
					if(abnormal.get("getFee")!=null){
						model.setReceivablesGet(abnormal.getDouble("getFee"));
					}
					if(abnormal.get("noGetFee")!=null){
						model.setReceivablesNOGet(abnormal.getDouble("noGetFee"));
					}
					break;
				}
			}
			//异动减款
			for(Record abnormal:abnormalReduce){
				if(model.getFeetype()==abnormal.getLong("type")){
					if(abnormal.get("shipCount")!=null){
						model.setPayShipCount(abnormal.getLong("shipCount"));
					}
					if(abnormal.get("feeTotal")!=null){
						model.setPayTotal(abnormal.getDouble("feeTotal"));
					}
					if(abnormal.get("getFee")!=null){
						model.setPayGet(abnormal.getDouble("getFee"));
					}
					if(abnormal.get("noGetFee")!=null){
						model.setPayNOGet(abnormal.getDouble("noGetFee"));
					}
					break;
				}
			}
			//回扣
			for(Record fee:rebate){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(fee.getLong("shipCount"));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getBigDecimal("feeTotal").doubleValue());
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//提货短驳送货
			for(Record fee:loadFee){
				if(model.getFeetype()==fee.getInt("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(fee.getLong("shipCount"));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("notGetFee")!=null){
						model.setPayNOGet(fee.getDouble("notGetFee"));
					}
					break;
				}
			}
			//中转
			for(Record fee:transferFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(fee.getLong("shipCount"));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getBigDecimal("feeTotal").doubleValue());
					}
					if(fee.get("getFee")!=null) {
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null) {
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//现付运输费
			for(Record fee:loadNowtrans){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//现付油卡费
			for(Record fee:loadNowoilFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//回付运输费
			for(Record fee:loadBacktransFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//到付运输费
			for(Record fee:loadAttransFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//整车保险费
			for(Record fee:loadAllsafeFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//发站装车费
			for(Record fee:loadStartFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//发站其他费
			for(Record fee:loadOtherFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//到站卸车费
			for(Record fee:loadAtunloadFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}
			//到站其他费
			for(Record fee:loadAtotherFee){
				if(model.getFeetype()==fee.getLong("type")){
					if(fee.get("shipCount")!=null){
						model.setPayShipCount(Integer.valueOf(fee.get("shipCount").toString()));
					}
					if(fee.get("feeTotal")!=null){
						model.setPayTotal(fee.getDouble("feeTotal"));
					}
					if(fee.get("getFee")!=null){
						model.setPayGet(fee.getDouble("getFee"));
					}
					if(fee.get("noGetFee")!=null){
						model.setPayNOGet(fee.getDouble("noGetFee"));
					}
					break;
				}
			}



		}
		return dataList;
	}

	public List<RePayModel> getInitRePayList(){
		List<RePayModel> list=new ArrayList<RePayModel>();

		RePayModel nowPay=new RePayModel();
		nowPay.setOrderNum(1);
		nowPay.setFeetype(1);
		nowPay.setFeeName("现付");
		list.add(nowPay);

		RePayModel tfPay=new RePayModel();
		tfPay.setOrderNum(2);
		tfPay.setFeetype(2);
		tfPay.setFeeName("提付");
		list.add(tfPay);

		RePayModel hdPay=new RePayModel();
		hdPay.setOrderNum(3);
		hdPay.setFeetype(3);
		hdPay.setFeeName("回单付");
		list.add(hdPay);

		RePayModel yjPay=new RePayModel();
		yjPay.setOrderNum(4);
		yjPay.setFeetype(4);
		yjPay.setFeeName("月结");
		list.add(yjPay);

		RePayModel dqPay=new RePayModel();
		dqPay.setOrderNum(5);
		dqPay.setFeetype(5);
		dqPay.setFeeName("短欠");
		list.add(dqPay);

		RePayModel dkkPay=new RePayModel();
		dkkPay.setOrderNum(6);
		dkkPay.setFeetype(6);
		dkkPay.setFeeName("贷款扣");
		list.add(dkkPay);

		RePayModel ydzjPay=new RePayModel();
		ydzjPay.setOrderNum(7);
		ydzjPay.setFeetype(7);
		ydzjPay.setFeeName("异动增加");
		list.add(ydzjPay);

		RePayModel ydjkPay=new RePayModel();
		ydjkPay.setOrderNum(8);
		ydjkPay.setFeetype(8);
		ydjkPay.setFeeName("异动减款");
		list.add(ydjkPay);

		RePayModel hkPay=new RePayModel();
		hkPay.setOrderNum(9);
		hkPay.setFeetype(9);
		hkPay.setFeeName("回扣");
		list.add(hkPay);

		RePayModel thPay=new RePayModel();
		thPay.setOrderNum(10);
		thPay.setFeetype(10);
		thPay.setFeeName("提货费");
		list.add(thPay);

		RePayModel dbPay=new RePayModel();
		dbPay.setOrderNum(11);
		dbPay.setFeetype(11);
		dbPay.setFeeName("短驳费");
		list.add(dbPay);

		RePayModel shPay=new RePayModel();
		shPay.setOrderNum(12);
		shPay.setFeetype(12);
		shPay.setFeeName("送货费");
		list.add(shPay);

		RePayModel zzPay=new RePayModel();
		zzPay.setOrderNum(13);
		zzPay.setFeetype(13);
		zzPay.setFeeName("中转费");
		list.add(zzPay);

		RePayModel xfysPay=new RePayModel();
		xfysPay.setOrderNum(14);
		xfysPay.setFeetype(14);
		xfysPay.setFeeName("现付运输费");
		list.add(xfysPay);

		RePayModel xfykPay=new RePayModel();
		xfykPay.setOrderNum(15);
		xfykPay.setFeetype(15);
		xfykPay.setFeeName("现付油卡费");
		list.add(xfykPay);

		RePayModel hfysPay=new RePayModel();
		hfysPay.setOrderNum(16);
		hfysPay.setFeetype(16);
		hfysPay.setFeeName("回付运输费");
		list.add(hfysPay);

		RePayModel dfysPay=new RePayModel();
		dfysPay.setOrderNum(17);
		dfysPay.setFeetype(17);
		dfysPay.setFeeName("到付运输费");
		list.add(dfysPay);

		RePayModel zcbxPay=new RePayModel();
		zcbxPay.setOrderNum(18);
		zcbxPay.setFeetype(18);
		zcbxPay.setFeeName("到付运输费");
		list.add(zcbxPay);

		RePayModel fzzcPay=new RePayModel();
		fzzcPay.setOrderNum(19);
		fzzcPay.setFeetype(19);
		fzzcPay.setFeeName("发站装车费");
		list.add(fzzcPay);

		RePayModel fzqtPay=new RePayModel();
		fzqtPay.setOrderNum(20);
		fzqtPay.setFeetype(20);
		fzqtPay.setFeeName("发站其他费");
		list.add(fzqtPay);

		RePayModel dzxcPay=new RePayModel();
		dzxcPay.setOrderNum(21);
		dzxcPay.setFeetype(21);
		dzxcPay.setFeeName("到站卸车费");
		list.add(dzxcPay);

		RePayModel dzqtPay=new RePayModel();
		dzqtPay.setOrderNum(22);
		dzqtPay.setFeetype(22);
		dzqtPay.setFeeName("到站其他费");
		list.add(dzqtPay);

		return list;
	}

	/**
	 * 成本月报表
	 * @param user
	 * @param model
	 * @return
	 */
	public List<KdShip> queryCostReportList(SessionUser user, OperationSearchModel model) {

		String select = "SELECT * ,(gxfee+thfee+shfee+dbfee+zzfee) totalFee FROM (SELECT getNetworkNameById (tl.network_id) AS netName, tl.network_id,(SELECT IFNULL(SUM(ship_transfer_fee),0) FROM kd_ship_transfer WHERE network_id = tl.network_id AND  " +
				"  DATE_FORMAT(ship_transfer_time, '%Y-%m') = DATE_FORMAT(tl.create_time, '%Y-%m'))    zzfee, " +
				" (SELECT COUNT(1) FROM kd_ship_transfer WHERE network_id = tl.network_id AND DATE_FORMAT(ship_transfer_time, '%Y-%m') = DATE_FORMAT(tl.create_time, '%Y-%m'))    zzcount, " +
				"  IFNULL(SUM(CASE tl.load_transport_type WHEN 1 THEN tl.load_fee ELSE 0 END),0)    thfee,IFNULL(SUM(CASE tl.load_transport_type WHEN 2 THEN tl.load_fee ELSE 0 END),0) dbfee, " +
				"  IFNULL(SUM(CASE tl.load_transport_type WHEN 3 THEN tl.load_fee ELSE 0 END),0)   gxfee,IFNULL(SUM(CASE tl.load_transport_type WHEN 4 THEN tl.load_fee ELSE 0 END),0)   shfee, " +
				"  SUM(CASE tl.load_transport_type WHEN 1 THEN 1 ELSE 0 END)    thcount, SUM(CASE tl.load_transport_type WHEN 2 THEN 1 ELSE 0 END)    dbcount, " +
				"  SUM(CASE tl.load_transport_type WHEN 3 THEN 1 ELSE 0 END)    gxcount,SUM(CASE tl.load_transport_type WHEN 4 THEN 1 ELSE 0 END)  shcount,COUNT(1), DATE_FORMAT(tl.create_time, '%Y-%m') ctime ";
		StringBuilder bu = new StringBuilder("  FROM kd_truck_load tl ");
		bu.append("  WHERE tl.network_id IN ("+user.toNetWorkIdsStr()+") GROUP BY ctime,tl.network_id) m where 1=1");
		bu.append(" and ctime BETWEEN '"+model.getStartTime()+"' AND '"+model.getEndTime()+"' ");

		if(StringUtils.isNotBlank(model.getNetWorkId())){
			bu.append(" AND m.network_id = "+model.getNetWorkId());
		}

		bu.append(" ORDER BY m.ctime desc ");

		return KdShip.dao.find(select+bu.toString());

	}

	/**
	 * 利润月报表
	 * @param startTime
	 * @param endTime
	 * @param networkId
	 * @param user
	 * @return
	 */
	public List<Record> getProfitReportDate(String startTime, String endTime, String networkId, SessionUser user){
		List<ProfitReportModel> dataList=new ArrayList();

		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();

		if(StringUtils.isNotBlank(startTime)){
			start = startTime;
		}
		if(StringUtils.isNotBlank(endTime)){
			end = endTime;
		}

		List<String> monthList = DateUtils.getMonthList(start, end);
		if(monthList.size()==0){
			return null;
		}
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		String networkids = user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(networkId)){
			networkids = networkId;
		}

		StringBuilder sql = new StringBuilder("SELECT  m.*,(ship_nowpay_fee+ship_pickuppay_fee+ship_receiptpay_fee+ship_monthpay_fee+ship_arrearspay_fee+ship_goodspay_fee-ship_rebate_fee+plus_fee-minus_fee)totalProfitFee," +
				"(thfee+shfee+dbfee+zzfee+load_nowtrans_fee+load_nowoil_fee+load_backtrans_fee+load_attrans_fee+load_allsafe_fee+load_start_fee+load_other_fee+load_atunload_fee+load_atother_fee) totalCostFee FROM ( " +
				" SELECT kd.network_id,COUNT(1) count1,getNetworkNameById (kd.network_id) AS netName, IFNULL(SUM(ship_nowpay_fee),0)  ship_nowpay_fee,  IFNULL(SUM(ship_pickuppay_fee),0) ship_pickuppay_fee," +
				" IFNULL(SUM(ship_receiptpay_fee),0) ship_receiptpay_fee,IFNULL(SUM(ship_monthpay_fee),0) ship_monthpay_fee, IFNULL(SUM(ship_arrearspay_fee),0) ship_arrearspay_fee," +
				" IFNULL(SUM(ship_goodspay_fee),0) ship_goodspay_fee, IFNULL(SUM(ship_rebate_fee),0) ship_rebate_fee,IFNULL(SUM(minus_fee),0) minus_fee,IFNULL(SUM(plus_fee),0) plus_fee, " +
                "  IFNULL(SUM(CASE fee_type WHEN 7 THEN f.fee ELSE 0 END),0 )    thfee,IFNULL(SUM(CASE fee_type WHEN 8 THEN f.fee ELSE 0 END),0 )    shfee," +
                "  IFNULL(SUM(CASE fee_type WHEN 9 THEN f.fee ELSE 0 END),0 )    dbfee,IFNULL(SUM(CASE fee_type WHEN 10 THEN f.fee ELSE 0 END),0 )    zzfee," +
                "  IFNULL(SUM(CASE fee_type WHEN 12 THEN f.fee ELSE 0 END),0 )    load_nowtrans_fee,IFNULL(SUM(CASE fee_type WHEN 13 THEN f.fee ELSE 0 END),0 )    load_nowoil_fee," +
                "  IFNULL(SUM(CASE fee_type WHEN 14 THEN f.fee ELSE 0 END),0 )    load_backtrans_fee,IFNULL(SUM(CASE fee_type WHEN 15 THEN f.fee ELSE 0 END),0 )    load_allsafe_fee," +
                "  IFNULL(SUM(CASE fee_type WHEN 16 THEN f.fee ELSE 0 END),0 )    load_start_fee,IFNULL(SUM(CASE fee_type WHEN 17 THEN f.fee ELSE 0 END),0 )    load_other_fee," +
                "  IFNULL(SUM(CASE fee_type WHEN 18 THEN f.fee ELSE 0 END),0 )    load_attrans_fee,IFNULL(SUM(CASE fee_type WHEN 19 THEN f.fee ELSE 0 END),0 )    load_atunload_fee," +
                "  IFNULL(SUM(CASE fee_type WHEN 20 THEN f.fee ELSE 0 END),0 )    load_atother_fee," +
				" DATE_FORMAT(kd.create_time, '%Y-%m')    ctime FROM kd_ship kd  LEFT JOIN kd_ship_abnormal ka ON kd.ship_id = ka.ship_id  LEFT JOIN kd_ship_fee f " +
                " ON kd.ship_id = f.ship_id WHERE kd.ship_is_update = 0  " +
				" and kd.network_id in("+networkids+")and DATE_FORMAT(kd.create_time,'%Y-%m')>=? and DATE_FORMAT(kd.create_time,'%Y-%m')<=? GROUP BY ctime, kd.network_id ) m " +
				" ORDER BY ctime desc");
		List<Record> record=Db.find(sql.toString(),start,end);
/*
		for (String time : monthList) {

			ProfitReportModel model = new ProfitReportModel();

			model.setTime(time);

			for (Record data : record) {
				if(data.getStr("ctime").equals(time)){
					model.setNetName(data.get("netName"));
					//营业额
					double turnover = data.getDouble("ship_nowpay_fee")+data.getDouble("ship_pickuppay_fee")+data.getDouble("ship_receiptpay_fee")
							         +data.getDouble("ship_monthpay_fee")+data.getDouble("ship_arrearspay_fee")+data.getDouble("ship_goodspay_fee")
									 +data.getDouble("plus_fee")-data.getDouble("minus_fee")-data.getDouble("ship_rebate_fee");
					model.setTurnover(turnover+"");
					model.setAmount(data.get("count1"));
					model.setShip_nowpay_fee(data.get("ship_nowpay_fee"));
					model.setShip_pickuppay_fee(data.get("ship_pickuppay_fee"));
					model.setShip_receiptpay_fee(data.get("ship_receiptpay_fee"));
					model.setShip_monthpay_fee(data.get("ship_monthpay_fee"));
					model.setShip_arrearspay_fee(data.get("ship_arrearspay_fee"));
					model.setShip_goodspay_fee(data.get("ship_goodspay_fee"));
					model.setPlus_fee(data.get("plus_fee"));
					model.setMinus_fee(data.get("minus_fee"));
					model.setShip_rebate_fee(data.get("ship_rebate_fee"));

				}
			}





			dataList.add(model);
		}*/


		return record;
	}
}
