package com.supyuan.kd.finance.abnormal;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.finance.KdShipFee;
import com.supyuan.kd.finance.flow.FlowSvc;
import com.supyuan.kd.finance.flow.KdFlow;
import com.supyuan.kd.finance.flow.KdFlowDetail;
import com.supyuan.kd.goods.KdProduct;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.network.NetWorkSvc;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.transfer.TransferSearchModel;
import com.supyuan.kd.waybill.*;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.StrUtils;
import com.supyuan.util.UUIDUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 异动
 * @author yuwen
 */
public class KdShipAbnormalSvc extends BaseService {
	private static final Log log = Log.getLog(KdShipAbnormalSvc.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date time=new Date();


	/**
	 * 异动运单列表
	 * @param paginator
	 * @param model
	 * @param user
	 * @return
	 */
	public Page<KdShipAbnormal> getKdShipAbnormalPage(Paginator paginator, ShipAbnormalSearchModel model, SessionUser user,boolean isExcel,String flag){
		StringBuilder select=new StringBuilder(" SELECT sb.id ,sb.cause,s.ship_id, sb.minus_fee,sb.plus_fee,sb.fee_status,getNetworkNameById(sb.network_id) abnormalNetWorkName ,s.goods_sn , getNetworkNameById(s.network_id) netWorkName ,`ship_sn`,s.`create_time`,s.ship_customer_number,getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName,getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,s.ship_status ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();

		sql.append(" FROM kd_ship_abnormal sb LEFT JOIN `kd_ship` s  on sb.ship_id = s.ship_id");
		parm.append(" WHERE 1=1 and  s.`ship_deleted`=0 and s.ship_abnormal_type = 1 ");

		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			parm.append(" and s.`network_id`='"+model.getNetWorkId()+"' ");
		}
		if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
			parm.append(" and  to_days(s.create_time) between to_days('"+model.getStartTime()+"') and to_days('"+model.getEndTime()+"') ");
		}
		if (StringUtils.isNotBlank(model.getShipSn())) {
			parm.append(" and s.`ship_sn` like '%"+model.getShipSn()+"%' ");
		}

		if (StringUtils.isNotBlank(model.getSenderId())) {
			parm.append(" and s.`ship_sender_id`='"+model.getSenderId()+"' ");
		}
		if (StringUtils.isNotBlank(model.getReceiverId())) {
			parm.append(" and s.`ship_receiver_id`='"+model.getReceiverId()+"' ");
		}
		if (StringUtils.isNotBlank(model.getAbnormalNetWorkId())) {
			parm.append(" and sb.network_id ='"+model.getAbnormalNetWorkId()+"' ");
		}
		if (StringUtils.isNotBlank(model.getFeeStatus())) {
			parm.append(" and sb.`fee_status`='"+model.getFeeStatus()+"' ");
		}
		if (StringUtils.isNotBlank(model.getFromCode())) {
			//parm.append(" and s.`ship_from_city_code`like '"+model.getFromCode()+"' ");
			parm.append("and s.`ship_from_city_code`like '"+ StrUtils.getRealRegionCode(model.getFromCode())+"%'");
		}
		if (StringUtils.isNotBlank(model.getToCode())) {
			//parm.append(" and s.`ship_to_city_code`='"+model.getToCode()+"' ");
			parm.append(" and s.`ship_to_city_code` like '"+StrUtils.getRealRegionCode(model.getToCode())+"%' ");
		}
		if("all".equals(flag)){
			parm.append(" and sb.fee_status =  0");
		}
		sql.append(parm.toString());
		sql.append(" and s.company_id="+user.getCompanyId()+" and s.network_id in ("+user.toNetWorkIdsStr()+") ");
		sql.append(" order by s.create_time desc ");


		if(isExcel){
			List<KdShipAbnormal> shipAbnormals = KdShipAbnormal.dao.find(select.toString()+sql.toString());
			return new Page<KdShipAbnormal>(shipAbnormals, 1, 10, 1, shipAbnormals.size());
		}else{
			return KdShipAbnormal.dao.paginate(paginator, select.toString(), sql.toString());
		}
		
	}
	


	/**
	 * @author huangym
	 * 异动新增
	 * @param user
	 * @param ship_id
	 * @param plus_fee
	 * @param minus_fee
	 * @param network_id
	 * @param cause
	 * @param time
	 */
	public boolean saveShipAbnormal(SessionUser user, String ship_id, String plus_fee, String minus_fee, String network_id, String cause, String time) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {

				boolean b = new KdShipAbnormal()
						.set("ship_id", ship_id)
						.set("create_time", time)
						.set("minus_fee", minus_fee)
						.set("plus_fee", plus_fee)
						.set("network_id", network_id)
						.set("cause", cause)
						.set("user_id", user.getUserId())
						.save();
				if(!b){
					return false;
				}
				int update = Db.update("update kd_ship  set ship_abnormal_type=1  where ship_id=?",ship_id );
				if (update>0){
					return true;
				}

				return false;
			}
		});
	}


	/**
	 * 根据异动id查询
	 * @param  shipId
	 * @return
	 */
	public KdShipAbnormal getKdShipAbnormalByShipId(String shipId) {
		KdShipAbnormal kdShipAbnormal = KdShipAbnormal.dao.findFirst(" SELECT sb.id ,sb.network_id,sb.cause,s.ship_id, sb.minus_fee," +
				"sb.plus_fee,sb.fee_status,getNetworkNameById(sb.network_id) abnormalNetWorkName ,s.goods_sn , s.`ship_id`," +
				"getNetworkNameById(s.network_id) netWorkName ,`ship_sn`,s.`create_time`,s.ship_customer_number," +
				"getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName," +
				"getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,s.ship_status,sb.create_time as registerTime,getPCUserName(sb.user_id) as userName " +
				" FROM kd_ship_abnormal sb LEFT JOIN `kd_ship` s  on sb.ship_id = s.ship_id  WHERE 1=1 and  s.`ship_deleted`=0 " +
				" and s.ship_abnormal_type = 1 and sb.ship_id = ?  ",shipId);

		return kdShipAbnormal;
	}

	/**
	 * 删除异动
	 * @param ship_id 运单id
	 * @param id  异动id
	 * @return
	 */
	public boolean deleteShipAbnormal(int ship_id, int id) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {

				int update =  Db.update("update kd_ship set ship_abnormal_type = 0  where ship_id = ?",ship_id);
				if(update==0)return false;
				boolean delete  = Db.deleteById("kd_ship_abnormal",id);

				return delete;
			}
		});

	}


	/**
	 * 异动结算
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
	public boolean AbnormalSettle(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				//修改异动结算状态
				int update = Db.update("UPDATE kd_ship_abnormal SET fee_status = 1  WHERE ship_id IN ("+shipIds+")");
				if (update<1) return  false;

				//插入流水表
				List<Record> shipAbnormals = Db.find("SELECT minus_fee ,plus_fee ,ship_id,network_id  FROM kd_ship_abnormal  WHERE ship_id IN  ("+shipIds+") ");
				LinkedHashMap FeeMap = new LinkedHashMap<Integer,Double>();
				LinkedHashMap networkIdMap = new LinkedHashMap<Integer,Integer>();
				List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
				Set<Integer> networkIds = new HashSet<>();

				for (Record record:shipAbnormals) {
					KdFlow kdFlow = new KdFlow();
					Integer key = record.getInt("network_id");
					networkIds.add(key);
					if (FeeMap.containsKey(key)){
						double fee = (double)FeeMap.get(key)+(record.getDouble("plus_fee")-record.getDouble("minus_fee"));
						FeeMap.put(key ,fee);
					}else {
						FeeMap.put(key ,record.getDouble("plus_fee")-record.getDouble("minus_fee"));
						networkIdMap.put(key,record.getInt("ship_id"));
					}
				}


				for (Integer id:networkIds) {
					KdFlow flow = new KdFlow();
					double fee = (double)FeeMap.get(id);
					if (fee >= 0 ){
						flow.set("inout_type" ,0).set("fee_type",27);
					}else {
						flow.set("inout_type" ,1).set("fee_type",28);
					}

					flow.set("voucher_no",voucherNo).set("remark",remark).set("flow_no",flowNo).set("pay_type",payType).set("fee",Math.abs(fee));
					flow.set("user_id",user.getUserId()).set("network_id",id).set("create_time",time).set("company_id",user.getCompanyId());
					flow.set("id_type",1).set("settlement_type",4).set("flow_sn",new FlowSvc().getFlowSnByComId(user.getCompanyId()));
					flow.set("resource_id",networkIdMap.get(id)).set("id_type",2);

					if(!flow.save()) return  false;
					kdFlows.add(flow);
				}



				//插入流水明细表
				HashMap<Integer,Integer>  kdFlowIdMap = new HashMap<>();
				for (KdFlow flow: kdFlows) {
					kdFlowIdMap.put(flow.getInt("network_id"),flow.getInt("id"));
				}
				List<KdFlowDetail> kdFlowDetailList = new ArrayList<KdFlowDetail>() ;
				for (Record record:shipAbnormals) {
					KdFlowDetail kdFlowDetail = new KdFlowDetail();
					double fee = record.getDouble("plus_fee")-record.getDouble("minus_fee");
					kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",fee).set("network_id",record.get("network_id"));
					kdFlowDetail.set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
					kdFlowDetailList.add(kdFlowDetail);
				}
				int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
				if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

				//插入费用表
				for (Record record:shipAbnormals) {
					KdShipFee shipFee=new KdShipFee();
					shipFee.set("ship_id",record.getInt("ship_id")).set("resource_id",record.getInt("ship_id")).
							set("resource_type",1).set("network_id",record.getInt("network_id"));
					shipFee.set("company_id",user.getCompanyId()).set("create_time",time);
					if (record.getDouble("minus_fee")>0){
						shipFee.set("fee",record.getDouble("minus_fee")).set("fee_type",22);
					}else {
						shipFee.set("fee",record.getDouble("plus_fee")).set("fee_type",23);
					}
					if(!shipFee.save()) return false;
				}

				return true;
			}
		});
		return tx;
	}


	/**
	 * 运单列表
	 *
	 * @param paginator
	 * @param model
	 * @param user
	 * @return
	 */
	public Page<KdShip> getKdShipPageByStock(Paginator paginator, KdShipSearchModel model, SessionUser user) {
		StringBuilder select = new StringBuilder(" SELECT s.`ship_id`,getNetworkNameById(s.network_id) netWorkName ,`ship_sn`,s.`create_time`,s.ship_customer_number,getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName,getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,s.ship_status,s.ship_volume,s.ship_weight,s.ship_amount,s.ship_load_times ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();

		sql.append(" FROM `kd_ship` s ");
		sql.append(" left join kd_in_out io on s.ship_id=io.ship_id ");
		parm.append(" WHERE 1=1 and  s.`ship_deleted`=0 and s.ship_abnormal_type=0 ");
		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			parm.append(" and s.`network_id`='" + model.getNetWorkId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
			parm.append(" and  to_days(s.create_time) between to_days('" + model.getStartTime() + "') and to_days('" + model.getEndTime() + "') ");
		}
		if (StringUtils.isNotBlank(model.getShipSn())) {
			parm.append(" and s.`ship_sn` like '%" + model.getShipSn() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getShipSate())) {
			if (model.getShipSate().equals("2")) {
				parm.append(" and (s.ship_status=2 or s.ship_status=3) ");
			} else {
				parm.append(" and s.`ship_status`='" + model.getShipSate() + "' ");
			}
		}
		if (StringUtils.isNotBlank(model.getSenderId())) {
			parm.append(" and s.`ship_sender_id`='" + model.getSenderId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getReceiverId())) {
			parm.append(" and s.`ship_receiver_id`='" + model.getReceiverId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getFromCode())) {
			//parm.append(" and s.`ship_from_city_code`like '"+model.getFromCode()+"' ");
			parm.append("and s.`ship_from_city_code`like '" + StrUtils.getRealRegionCode(model.getFromCode()) + "%'");
		}
		if (StringUtils.isNotBlank(model.getToCode())) {
			//parm.append(" and s.`ship_to_city_code`='"+model.getToCode()+"' ");
			parm.append(" and s.`ship_to_city_code` like '" + StrUtils.getRealRegionCode(model.getToCode()) + "%' ");
		}
		if (StringUtils.isNotBlank(model.getCustomerNumber())) {
			parm.append(" and s.`ship_customer_number`like '%" + model.getCustomerNumber() + "%' ");
		}

		sql.append(parm.toString());
		sql.append(" and s.company_id=" + user.getCompanyId());
		sql.append(" and io.network_id in (" + user.toNetWorkIdsStr() + ")");
		sql.append(" order by s.create_time desc ");
		return KdShip.dao.paginate(paginator, select.toString(), sql.toString());


	}
}
