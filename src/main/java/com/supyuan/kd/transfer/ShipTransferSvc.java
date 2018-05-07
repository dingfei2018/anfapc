package com.supyuan.kd.transfer;





import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.supyuan.kd.finance.KdShipFee;
import com.supyuan.kd.finance.flow.KdFlow;
import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.common.ShipStatus;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.network.NetWorkSvc;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.StrUtils;
/**
 * 中转运单svc
 * @author chenan
 * Date:2017年12月14日下午17:30:08
 * 
 */
public class ShipTransferSvc extends BaseService {

	private final static Log log = Log.getLog(ShipTransferSvc.class);
	
	
	/**
	 * 获取中转运单分页列表
	 * @param paginator
	 * @param search
	 * @param user
	 * @return
	 */
	public Page<ShipTransfer> getShipTransferList(Paginator paginator,TransferMentSearchModel search,SessionUser user) {
		Page<ShipTransfer> ShipTransferList;
		
		StringBuilder select = new StringBuilder("SELECT s.ship_status,s.ship_volume,s.ship_amount,s.ship_weight,l2.sub_network_name as shipNetName,s.ship_sn,st.ship_id,l.sub_network_name as tranNetName,t.customer_corp_name as transferCorpName,t.customer_name as transferName,t.customer_mobile		AS transfermobile,c1.customer_name as senderName,c2.customer_name as receiverName,st.transfer_id,st.network_id,st.ship_transfer_time,st.ship_transfer_sn,st.ship_transfer_fee,st.ship_transfer_fee_status,getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,f.id ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		
		sql.append(" FROM kd_ship_transfer st ");
		sql.append("LEFT JOIN kd_customer t ON st.transfer_id=t.customer_id ");
		sql.append("LEFT JOIN kd_ship s ON st.ship_id= s.ship_id ");
		sql.append("LEFT JOIN kd_customer c1 ON s.ship_sender_id=c1.customer_id ");
		sql.append("LEFT JOIN kd_customer c2 ON s.ship_receiver_id= c2.customer_id ");
		sql.append("LEFT JOIN logistics_network l ON l.id= st.network_id ");
		sql.append("LEFT JOIN logistics_network l2 ON l2.id= s.network_id ");
		sql.append("LEFT JOIN kd_ship_fee f ON f.ship_id= s.ship_id and f.fee_type=10 ");

		parm.append(" where 1=1");
		if  (search.getNetWorkId()!=0) {
			parm.append(" and st.network_id=" + search.getNetWorkId() );
		}
		if  (StringUtils.isNotBlank(search.getStartTime())&&StringUtils.isNotBlank(search.getEndTime())) {
			parm.append(" and st.ship_transfer_time BETWEEN '"+search.getStartTime()+"' AND '"+search.getEndTime()+"'");
		}
		if  (StringUtils.isNotBlank(search.getTransferName())) {
			parm.append(" and t.customer_id =" + search.getTransferName() );
		}
		if  (StringUtils.isNotBlank(search.getShipperName())) {
			parm.append(" and c1.customer_id =" + search.getShipperName() );
		}
		if  (StringUtils.isNotBlank(search.getReceivingName())) {
			parm.append(" and c2.customer_id = " + search.getReceivingName() );
		}
		if (StringUtils.isNotBlank(search.getTransferSn())) {
			parm.append(" and st.ship_transfer_sn like '%" + search.getTransferSn()+"%'");
		}
		if (StringUtils.isNotBlank(search.getShipSn())) {
			parm.append(" and s.ship_sn like '%" + search.getShipSn()+"%'");
		}
		if (StringUtils.isNotBlank(search.getStartCode())) {
			parm.append(" and s.ship_from_city_code like '" + StrUtils.getRealRegionCode(search.getStartCode())+"%'");
		}
		if (StringUtils.isNotBlank(search.getEndCode())) {
			parm.append(" and s.ship_to_city_code like '" + StrUtils.getRealRegionCode(search.getEndCode())+"%'");
		}

		parm.append(" AND s.company_id=? AND st.network_id in("+user.toNetWorkIdsStr()+")");
		parm.append(" ORDER BY st.ship_transfer_time desc");
		sql.append(parm.toString());
		
		ShipTransferList = ShipTransfer.dao.paginate(paginator,select.toString(),sql.toString(),user.getCompanyId());
		return ShipTransferList;
	}
	
	/**
	 * 根据运单id获取中转运单
	 * @param shipId
	 * @return
	 */
	public ShipTransfer getTransferByshipId(String shipId){
		
		StringBuilder select = new StringBuilder("SELECT s.ship_volume,t.customer_id,s.create_time,s.ship_amount,s.ship_weight,l2.sub_network_name as shipNetName,s.ship_sn,st.ship_id,l.sub_network_name as tranNetName,t.customer_corp_name as transferName,t.customer_name as personName,t.customer_mobile as personMobile,c1.customer_name as senderName,c2.customer_name as receiverName,st.transfer_id,st.network_id,st.ship_transfer_time,st.ship_transfer_sn,st.ship_transfer_fee,st.ship_transfer_fee_status,getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" FROM kd_ship_transfer st ");
		sql.append("LEFT JOIN kd_customer t ON st.transfer_id=t.customer_id ");
		sql.append("LEFT JOIN kd_ship s ON st.ship_id= s.ship_id ");
		sql.append("LEFT JOIN kd_customer c1 ON s.ship_sender_id=c1.customer_id ");
		sql.append("LEFT JOIN kd_customer c2 ON s.ship_receiver_id= c2.customer_id ");
		sql.append("LEFT JOIN logistics_network l ON l.id= st.network_id ");
		sql.append("LEFT JOIN logistics_network l2 ON l2.id= s.network_id ");
		parm.append(" where 1=1 ");
		parm.append(" AND s.ship_id=? ");
		sql.append(parm.toString());
		select.append(sql.toString());
		ShipTransfer shipTransfer=ShipTransfer.dao.findFirst(select.toString(),shipId);
		
		return shipTransfer;
	}


	/**
	 * 建立中转
	 * @param ShipTransferList
	 * @param customer
	 * @param time
	 * @param netWork
	 * @param user
	 * @return
	 */
	public boolean  save(List<ShipTransfer> ShipTransferList,Customer customer,String time,String netWork,SessionUser user){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
					if(customer.get("customer_id")==null){
						customer.set("customer_address_id", "1");
						Record record =Db.findFirst("SELECT count(1) as countId from kd_customer where customer_type=3 and company_id="+customer.get("company_id"));
						
						long id=1;
						if(record.getLong("countId")==0){
							
						}else{
							id=record.getLong("countId")+1;
						}
						
						String maxId=id+"";
						switch (maxId.length()) {
						case 1:maxId="00000"+maxId;
							break;
						case 2:maxId="0000"+maxId;
							break;
						case 3:maxId="000"+maxId;
							break;
						case 4:maxId="00"+maxId;
							break;
						case 5:maxId="0"+maxId;
							break;

						default:
							break;
						}
						String no="ZZ"+maxId;
						customer.set("customer_sn", no);
						if(!customer.save()) return false;
					}else{
						if(!customer.update()) return false;
					}
				List<KdInOut> inOuts  = new ArrayList<KdInOut>();
				List<KdShipTrack> tracks = new ArrayList<KdShipTrack>();
				NetWork netWorkd = new NetWorkSvc().getNetWork(netWork, user);
				Date now = new Date();
				for (ShipTransfer shipTransfer : ShipTransferList) {
					KdShip kdship=new KdShip().findById(shipTransfer.getInt("ship_id"));
					
					int ship_status=kdship.getInt("ship_status"); 
					String shipNetWorkId=kdship.get("network_id").toString();
					
					shipTransfer.set("ship_status",ship_status );
					shipTransfer.set("transfer_id", customer.getInt("customer_id"));
					shipTransfer.set("ship_transfer_time", time);
					shipTransfer.set("network_id", netWork);
					shipTransfer.set("delete_flag", 0);
					
					if(!shipTransfer.save()) return false;

					/*String ship_id=shipTransfer.get("ship_id").toString();
					String ship_transfer_fee=shipTransfer.get("ship_transfer_fee").toString();
					String network_id=shipTransfer.get("network_id").toString();*/

					/*if(!new KdFlow().set("resource_id",ship_id).set("resource_type",4).set("fee",ship_transfer_fee).set("network_id",network_id)
							.set("company_id",customer.get("company_id")).save()) return false;*/

					//生成费用
					/*if(!new KdShipFee().set("fee",ship_transfer_fee).set("ship_id",ship_id).set("resource_id",ship_id).set("network_id",network_id)
							.set("fee_type",10).set("create_time",time).set("company_id",user.getCompanyId()).save()) return  false;*/
					//if(!(Db.update("INSERT INTO kd_ship_fee(fee,ship_id,network_id,fee_type,create_time,company_id) VALUES (?,?,?,10,?,?)",ship_transfer_fee,ship_id,network_id,time,customer.get("company_id"))>0)) return false;

					//如中转网点为开单网点，upate 运单状态为6：收货中转中  否则 7：到货中转中
					if(shipNetWorkId.equals(netWork)){
						if(!(Db.update("update kd_ship s SET s.ship_status=?,s.ship_storage=2,ship_before_status=? where s.ship_id=?",ShipStatus.RTRANSFER.type,ship_status,shipTransfer.getInt("ship_id"))>0)) return false;
					}else{
						if(!(Db.update("update kd_ship s SET s.ship_status=?,s.ship_storage=2,ship_before_status=? where s.ship_id=?",ShipStatus.ATRANSFER.type,ship_status,shipTransfer.getInt("ship_id"))>0)) return false;
					}
					
					KdInOut	inout = new KdInOut();
					inout.set("ship_id", shipTransfer.getInt("ship_id"));	
					inout.set("network_id", netWork);		
					inout.set("out_time",now);		
					inOuts.add(inout);
					
					
					KdShipTrack kdShipTrack=new KdShipTrack();
					kdShipTrack.set("track_ship_id", shipTransfer.getInt("ship_id"));
					kdShipTrack.set("user_id", user.getUserId());
					kdShipTrack.set("track_company_id", user.getCompanyId());
					kdShipTrack.set("track_resource_id", shipTransfer.getInt("transfer_id"));
					kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_TRANSFER_NUM);
					kdShipTrack.set("track_networkid",kdship.get("load_network_id"));
					kdShipTrack.set("track_desc", netWorkd.get("sub_network_name")+"出库");
					kdShipTrack.set("create_time", time);
					//如中转网点为开单网点， 运单状态为6：收货中转中  否则 7：到货中转中
					if(shipNetWorkId.equals(netWork)){
						kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_TRANSFER_SHOUHUO);
						kdShipTrack.set("track_status", ShipStatus.RTRANSFER.type);
					}else{
						kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_TRANSFER_DAOHUO);
						kdShipTrack.set("track_status", ShipStatus.ATRANSFER.type);
					}
					tracks.add(kdShipTrack);
					
				}	
				//更新出入库记录 add  by liangxiaoping
				int[] inOutsave = Db.batch("update kd_in_out set out_time=? where ship_id=? and network_id=?", "out_time,ship_id,network_id", inOuts, inOuts.size());
				if(inOutsave.length!=inOuts.size())return false;	
				
				//操作日志
				int[] trackSave = Db.batchSave(tracks, tracks.size());
				if(trackSave.length!=tracks.size())return false;
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 更新中转运单
	 */
	public boolean  update(ShipTransfer shipTransfer){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!shipTransfer.update()) return false;
				if(!(Db.update("update kd_ship_fee set fee="+shipTransfer.get("ship_transfer_fee")+" where fee_type=2 and ship_id="+shipTransfer.get("ship_id"))>=0)) return false;
				
				return true;
			}
		});
		return tx;
	}
	
	public boolean  delete(String id, String transferNetworkId, SessionUser user){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				int ship_status=new ShipTransfer().findById(id).getInt("ship_status");
				if(!ShipTransfer.dao.deleteById(id)) return false;
				if(!(Db.update("DELETE FROM kd_ship_fee WHERE fee_type=2 and ship_id =?",id)>=0)) return false ;
				if(!(Db.update("update kd_ship set ship_storage=0,ship_status=? WHERE ship_id =?",ship_status,id)>0)) return false ;
				if(!(Db.update("update kd_in_out set out_time=null where ship_id=? and network_id=?",id,transferNetworkId)>=0)) return false ;

				String track_desc = NetWork.dao.findById(transferNetworkId).getStr("sub_network_name");
				new KdShipTrack()
						.set("user_id",user.getUserId())
						.set("track_ship_id",id)
						.set("track_resource_id",id)
						.set("track_company_id",user.getCompanyId())
						.set("track_desc",track_desc+"取消中转")
						.set("track_short_desc","取消中转")
						.set("track_class",5)
						.set("track_visible",0)
						.set("track_status",ship_status)
						.set("create_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
						.set("track_networkid",Integer.parseInt(transferNetworkId))
						.save();


				return true;
			}
		});
		return tx;
	}
	
}
