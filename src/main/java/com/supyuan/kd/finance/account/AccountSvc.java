package com.supyuan.kd.finance.account;




import java.sql.SQLException;
import java.util.*;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;

import com.supyuan.kd.finance.flow.FlowSvc;
import com.supyuan.kd.finance.flow.KdFlow;
import com.supyuan.kd.finance.flow.KdFlowDetail;
import com.supyuan.kd.waybill.KdShip;
import org.apache.commons.lang.StringUtils;
/**
 * 网点对账管理svc
 * @author chenan
 * Date:2017年12月20日上午10:30:08 
 * 
 */
public class AccountSvc extends BaseService {

	private final static Log log = Log.getLog(AccountSvc.class);


	/**
	 * 获取提付对账应收列表
	 * @param user
	 * @param search
	 * @param flag
	 * @param paginator
	 * @return
	 */
	public Object getRePickuppayAccountList(SessionUser user,AccountSearchModel search,boolean flag,Paginator ... paginator){
		StringBuilder select = new StringBuilder("SELECT f.id,f.fee_in_fill,s.ship_id, s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
		select.append("getNetworkNameById(f.network_id) AS toNetWorkName,ifnull(s.ship_pickuppay_fee,0) as ship_pickuppay_fee,s.ship_status,s.goods_sn,s.create_time,");
		select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
		select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName");
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM kd_ship_fee f");
		sql.append(" LEFT JOIN kd_ship s ON f.ship_id=s.ship_id ");
		StringBuilder parm = new StringBuilder();

		String netWorkIds=user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(search.getReNetWorkId())){
			netWorkIds=search.getReNetWorkId();
		}

		parm.append(" where s.ship_deleted=0 and s.network_id in ("+netWorkIds+") and f.fee_type=21 ");
		if(StringUtils.isNotBlank(search.getPayNetWorkId())){
			parm.append(" and s.to_network_id="+search.getPayNetWorkId());
		}
		if(StringUtils.isNotBlank(search.getShipSn())){
			parm.append(" and s.ship_sn like '%"+search.getShipSn()+"%'");
		}
		if  (StringUtils.isNotBlank(search.getStartTime())&&StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '"+search.getStartTime()+"' AND '"+search.getEndTime()+"'");
		}
		if(StringUtils.isNotBlank(search.getReceivingName())){
			parm.append(" and s.ship_receiver_id =" + search.getReceivingName() );
		}
		if(StringUtils.isNotBlank(search.getShipperName())){
			parm.append(" and s.ship_sender_id =" + search.getShipperName());
		}
		if(StringUtils.isNotBlank(search.getState())){
			parm.append(" and f.fee_in_fill =" + search.getState());
		}
		if(StringUtils.isNotBlank(search.getShipState())){
			parm.append(" and s.ship_status =" + search.getShipState());
		}

		if(paginator.length==0&&flag){
			parm.append(" and f.fee_in_fill =0 ");
		}

		parm.append(" order by f.create_time desc");
		
		sql.append(parm.toString());

		if(paginator.length>0){
			Page<Record> page=Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
			return page;
		}else{
			select.append(sql.toString());
			List<Record> list=Db.find(select.toString());
			return list;
		}


	}

	/**
	 * 获取操作对账应收列表
	 * @param user
	 * @param search
	 * @param flag
	 * @param paginator
	 * @return
	 */
	public Object getReDetailedAccountList(SessionUser user,AccountSearchModel search,boolean flag,Paginator ... paginator){
		StringBuilder select = new StringBuilder("SELECT GROUP_CONCAT(f.id) as feeId,if(count(f.fee_in_fill)=sum(f.fee_in_fill),1,0) as fee_in_fill,s.ship_id, s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
		select.append("getNetworkNameById(s.to_network_id) AS toNetWorkName,s.ship_status,s.goods_sn,s.create_time,");
		select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
		select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,sum(ifnull(f.fee,0)) totalFee,");
		select.append("sum(IFNULL(CASE when f.fee_type=7 then f.fee END,0))  as thFee,sum(IFNULL(CASE when f.fee_type=8 then f.fee END,0)) as shFee," +
				" sum(IFNULL(CASE when f.fee_type=9 then f.fee END,0))  as dbFee," +
				" sum(IFNULL(CASE when f.fee_type=10 then f.fee END,0))  as zzFee," +
				" sum(IFNULL(CASE when f.fee_type=12 then f.fee END,0))  as xfysFee," +
				" sum(IFNULL(CASE when f.fee_type=13 then f.fee END,0))  as xfykFee," +
				" sum(IFNULL(CASE when f.fee_type=14 then f.fee END,0))  as hfysFee," +
				" sum(IFNULL(CASE when f.fee_type=15 then f.fee END,0))  as zcbxFee," +
				" sum(IFNULL(CASE when f.fee_type=16 then f.fee END,0))  as fzzcFee," +
				" sum(IFNULL(CASE when f.fee_type=17 then f.fee END,0))  as fzqtFee," +
				" sum(IFNULL(CASE when f.fee_type=18 then f.fee END,0))  as dfysFee," +
				" sum(IFNULL(CASE when f.fee_type=19 then f.fee END,0))  as dzxcFee," +
				" sum(IFNULL(CASE when f.fee_type=20 then f.fee END,0))  as dzqtFee");
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM kd_ship_fee f");
		sql.append(" LEFT JOIN kd_ship s ON f.ship_id=s.ship_id ");
		StringBuilder parm = new StringBuilder();

		String netWorkIds=user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(search.getPayNetWorkId())){
			netWorkIds=search.getPayNetWorkId();
		}

		parm.append(" where s.ship_deleted=0 and f.network_id in ("+netWorkIds+") and f.fee_type BETWEEN 7 and 20 and f.fee_type!=11");

		if(StringUtils.isNotBlank(search.getReNetWorkId())){
			parm.append(" and s.network_id="+search.getReNetWorkId());
		}
		if(StringUtils.isNotBlank(search.getShipSn())){
			parm.append(" and s.ship_sn like '%"+search.getShipSn()+"%'");
		}
		if  (StringUtils.isNotBlank(search.getStartTime())&&StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '"+search.getStartTime()+"' AND '"+search.getEndTime()+"'");
		}
		if(StringUtils.isNotBlank(search.getReceivingName())){
			parm.append(" and s.ship_receiver_id =" + search.getReceivingName() );
		}
		if(StringUtils.isNotBlank(search.getShipperName())){
			parm.append(" and s.ship_sender_id =" + search.getShipperName());
		}
		if(StringUtils.isNotBlank(search.getState())){
			parm.append(" and f.fee_in_fill =" + search.getState());
		}
		if(StringUtils.isNotBlank(search.getShipState())){
			parm.append(" and s.ship_status =" + search.getShipState());
		}

		if(paginator.length==0&&flag){
			parm.append(" and f.fee_in_fill=0");
		}

		parm.append(" and f.network_id!=s.network_id group by s.ship_id,f.network_id");
		parm.append(" order by f.create_time desc");

		sql.append(parm.toString());

		if(paginator.length>0){
			Page<Record> page=Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
			return page;
		}else{
			select.append(sql.toString());
			List<Record> list=Db.find(select.toString());
			return list;
		}

	}

	/**
	 * 获取提付对账应付列表
	 * @param user
	 * @param search
	 * @param flag
	 * @param paginator
	 * @return
	 */
	public Object getPayPickuppayAccountList(SessionUser user,AccountSearchModel search,boolean flag,Paginator ... paginator){
		StringBuilder select = new StringBuilder("SELECT f.fee_out_fill,f.fee_in_fill,s.ship_id, s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
		select.append("getNetworkNameById(s.to_network_id) AS toNetWorkName,ifnull(s.ship_pickuppay_fee,0) as ship_pickuppay_fee,s.ship_status,s.goods_sn,s.create_time,");
		select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
		select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName");
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM kd_ship_fee f");
		sql.append(" LEFT JOIN kd_ship s ON f.ship_id=s.ship_id ");
		StringBuilder parm = new StringBuilder();

		String netWorkIds=user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(search.getReNetWorkId())){
			netWorkIds=search.getReNetWorkId();
		}

		parm.append(" where s.ship_deleted=0 and f.network_id in ("+netWorkIds+") and f.fee_type=21 ");
		if(StringUtils.isNotBlank(search.getPayNetWorkId())){
			parm.append(" and s.to_network_id="+search.getPayNetWorkId());
		}
		if(StringUtils.isNotBlank(search.getShipSn())){
			parm.append(" and s.ship_sn like '%"+search.getShipSn()+"%'");
		}
		if  (StringUtils.isNotBlank(search.getStartTime())&&StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '"+search.getStartTime()+"' AND '"+search.getEndTime()+"'");
		}
		if(StringUtils.isNotBlank(search.getReceivingName())){
			parm.append(" and s.ship_receiver_id =" + search.getReceivingName() );
		}
		if(StringUtils.isNotBlank(search.getShipperName())){
			parm.append(" and s.ship_sender_id =" + search.getShipperName());
		}
		if(StringUtils.isNotBlank(search.getState())){
			parm.append(" and f.fee_in_fill =" + search.getState());
		}
		if(StringUtils.isNotBlank(search.getShipState())){
			parm.append(" and s.ship_status =" + search.getShipState());
		}

		if(paginator.length==0&&flag){
			parm.append(" and f.fee_out_fill =0");
		}

		parm.append(" order by f.create_time desc");

		sql.append(parm.toString());

		if(paginator.length>0){
			Page<Record> page=Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
			return page;
		}else{
			select.append(sql.toString());
			List<Record> list=Db.find(select.toString());
			return list;
		}


	}

	/**
	 * 获取操作对账应付列表
	 * @param user
	 * @param search
	 * @param flag
	 * @param paginator
	 * @return
	 */
	public Object getPayDetailedAccountList(SessionUser user,AccountSearchModel search,boolean flag,Paginator ... paginator){
		StringBuilder select = new StringBuilder("SELECT  GROUP_CONCAT(f.id) as feeId,if(count(f.fee_out_fill)=sum(f.fee_out_fill),1,0) as fee_out_fill,s.ship_id, s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
		select.append("getNetworkNameById(f.network_id) AS toNetWorkName,s.ship_status,s.goods_sn,s.create_time,");
		select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
		select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,sum(ifnull(f.fee,0)) totalFee,");
		select.append("sum(IFNULL(CASE when f.fee_type=7 then f.fee END,0))  as thFee,sum(IFNULL(CASE when f.fee_type=8 then f.fee END,0)) as shFee," +
				" sum(IFNULL(CASE when f.fee_type=9 then f.fee END,0))  as dbFee," +
				" sum(IFNULL(CASE when f.fee_type=10 then f.fee END,0))  as zzFee," +
				" sum(IFNULL(CASE when f.fee_type=12 then f.fee END,0))  as xfysFee," +
				" sum(IFNULL(CASE when f.fee_type=13 then f.fee END,0))  as xfykFee," +
				" sum(IFNULL(CASE when f.fee_type=14 then f.fee END,0))  as hfysFee," +
				" sum(IFNULL(CASE when f.fee_type=15 then f.fee END,0))  as zcbxFee," +
				" sum(IFNULL(CASE when f.fee_type=16 then f.fee END,0))  as fzzcFee," +
				" sum(IFNULL(CASE when f.fee_type=17 then f.fee END,0))  as fzqtFee," +
				" sum(IFNULL(CASE when f.fee_type=18 then f.fee END,0))  as dfysFee," +
				" sum(IFNULL(CASE when f.fee_type=19 then f.fee END,0))  as dzxcFee," +
				" sum(IFNULL(CASE when f.fee_type=20 then f.fee END,0))  as dzqtFee");
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM kd_ship s ");
		sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id ");
		StringBuilder parm = new StringBuilder();

		String netWorkIds=user.toNetWorkIdsStr();
		if(StringUtils.isNotBlank(search.getPayNetWorkId())){
			netWorkIds=search.getPayNetWorkId();
		}

		parm.append(" where s.ship_deleted=0 and s.network_id in ("+netWorkIds+") and f.fee_type BETWEEN 7 and 20 and f.fee_type!=11");

		if(StringUtils.isNotBlank(search.getReNetWorkId())){
			parm.append(" and f.network_id="+search.getReNetWorkId());
		}
		if(StringUtils.isNotBlank(search.getShipSn())){
			parm.append(" and s.ship_sn like '%"+search.getShipSn()+"%'");
		}
		if  (StringUtils.isNotBlank(search.getStartTime())&&StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '"+search.getStartTime()+"' AND '"+search.getEndTime()+"'");
		}
		if(StringUtils.isNotBlank(search.getReceivingName())){
			parm.append(" and s.ship_receiver_id =" + search.getReceivingName() );
		}
		if(StringUtils.isNotBlank(search.getShipperName())){
			parm.append(" and s.ship_sender_id =" + search.getShipperName());
		}
		if(StringUtils.isNotBlank(search.getState())){
			parm.append(" and f.fee_in_fill =" + search.getState());
		}
		if(StringUtils.isNotBlank(search.getShipState())){
			parm.append(" and s.ship_status =" + search.getShipState());
		}

		if(paginator.length==0&&flag){
			parm.append(" and f.fee_out_fill =0");
		}
		parm.append(" and f.network_id!=s.network_id group by s.ship_id,f.network_id");
		parm.append(" order by f.create_time desc");

		sql.append(parm.toString());

		if(paginator.length>0){
			Page<Record> page=Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
			return page;
		}else{
			select.append(sql.toString());
			List<Record> list=Db.find(select.toString());
			return list;
		}

	}

	/**
	 * 提付应收结算
	 * @param shipIds
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmRePickuppay(String shipIds,String payType,String flowNo,String voucherNo,String remark,String time,SessionUser user) {

		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {

				List<Record> ships = Db.find("SELECT s.ship_pickuppay_fee,s.ship_id,s.load_network_id, IFNULL(sa.minus_fee,0.0) minus_fee, IFNULL(sa.plus_fee,0.0) plus_fee FROM kd_ship s LEFT JOIN kd_ship_abnormal sa ON sa.ship_id = s.ship_id WHERE s.ship_id IN  ("+shipIds+") ");
				LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:ships) {
					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (shipFeeMap.containsKey(key)){
						double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_pickuppay_fee")+record.getDouble("plus_fee")-record.getDouble("minus_fee");
						shipFeeMap.put(key ,ship_fee);
					}else {
						shipFeeMap.put(key ,record.getDouble("ship_pickuppay_fee"));
						networkShipIdMap.put(key,record.getInt("ship_id"));
					}
				}

				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
					flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
					flow.set("fee_type", 25).set("id_type", 1).set("settlement_type", 3).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.getDouble("ship_pickuppay_fee")+record.getDouble("plus_fee")-record.getDouble("minus_fee")).set("network_id",record.get("network_id"));
					kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				if (!(Db.update("update kd_ship_fee set fee_in_fill=1 where ship_id in("+shipIds+") and fee_type=21")>0)) return false;

				return true;
			}
		});
		return tx;
	}

	/**
	 * 操作对账应收结算
	 * @param feeIds
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmReDetailed(String shipIds,String feeIds,String payType,String flowNo,String voucherNo,String remark,String time,SessionUser user) {

		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {

				List<Record> ships = Db.find("SELECT s.ship_id,load_network_id,sum(ifnull(f.fee,0)) totalFee FROM kd_ship s LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id where s.ship_id IN  ("+shipIds+") and f.fee_type BETWEEN 7 and 20 and f.fee_type!=11 ");
				LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:ships) {
					Integer key = record.getInt("load_network_id");
					networkIds.add(key);
					if (shipFeeMap.containsKey(key)){
						double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("totalFee");
						shipFeeMap.put(key ,ship_fee);
					}else {
						shipFeeMap.put(key ,record.getDouble("totalFee"));
						networkShipIdMap.put(key,record.getInt("ship_id"));
					}
				}

				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
					flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
					flow.set("fee_type", 25).set("id_type", 1).set("settlement_type", 3).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("totalFee")).set("network_id",record.get("load_network_id"));
					kdFlowDetail.set("network_id",record.get("load_network_id")).set("flow_id",kdFlowIdMap.get(record.get("load_network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				if (!(Db.update("update kd_ship_fee set fee_in_fill=1 where id in("+feeIds+")")>0)) return false;

				return true;
			}
		});
		return tx;
	}
	/**
	 * 提付应付结算
	 * @param shipIds
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmPayPickuppay(String shipIds,String payType,String flowNo,String voucherNo,String remark,String time,SessionUser user) {

		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {

				List<Record> ships = Db.find("SELECT ship_pickuppay_fee,ship_id,load_network_id FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
				LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:ships) {
					Integer key = record.getInt("load_network_id");
					networkIds.add(key);
					if (shipFeeMap.containsKey(key)){
						double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_pickuppay_fee");
						shipFeeMap.put(key ,ship_fee);
					}else {
						shipFeeMap.put(key ,record.getDouble("ship_pickuppay_fee"));
						networkShipIdMap.put(key,record.getInt("ship_id"));
					}
				}

				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
					flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
					flow.set("fee_type", 26).set("id_type", 1).set("settlement_type", 3).set("inout_type", 1).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_pickuppay_fee")).set("network_id",record.get("load_network_id"));
					kdFlowDetail.set("flow_id",kdFlowIdMap.get(record.get("load_network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				if (!(Db.update("update kd_ship_fee set fee_out_fill=1 where ship_id in("+shipIds+") and fee_type=21")>0)) return false;

				return true;
			}
		});
		return tx;
	}

	/**
	 * 操作对账应付结算
	 * @param feeIds
	 * @param payType
	 * @param flowNo
	 * @param voucherNo
	 * @param remark
	 * @param time
	 * @param user
	 * @return
	 */
	public boolean confirmPayDetailed(String shipIds,String feeIds,String payType,String flowNo,String voucherNo,String remark,String time,SessionUser user) {

		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {

				List<Record> ships = Db.find("SELECT s.ship_id,s.network_id,sum(ifnull(f.fee,0)) totalFee FROM kd_ship s LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id where s.ship_id IN  ("+shipIds+") and f.fee_type BETWEEN 7 and 20 and f.fee_type!=11");
				LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();
				for (Record record:ships) {
					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (shipFeeMap.containsKey(key)){
						double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("totalFee");
						shipFeeMap.put(key ,ship_fee);
					}else {
						shipFeeMap.put(key ,record.getDouble("totalFee"));
						networkShipIdMap.put(key,record.getInt("ship_id"));
					}
				}

				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
					flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
					flow.set("fee_type", 26).set("id_type", 1).set("settlement_type", 3).set("inout_type", 1).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("totalFee")).set("network_id",record.get("network_id"));
					kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				if (!(Db.update("update kd_ship_fee set fee_out_fill=1 where id in("+feeIds+")")>0)) return false;

				return true;
			}
		});
		return tx;
	}

}
