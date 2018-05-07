package com.supyuan.kd.waybill;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.util.typechange.TypeChange;
import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.goods.KdProduct;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.network.NetWorkSvc;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.transfer.TransferSearchModel;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.StrUtils;
import com.supyuan.util.UUIDUtil;

/**
 * 运单
 * 
 * @author dingfei
 *
 * @date 2017年12月8日 下午4:08:25
 */
public class KdShipSvc extends BaseService {
	private static final Log log = Log.getLog(KdShipSvc.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date time = new Date();

	/**
	 * 创建运单
	 *
	 * @param kdProducts
	 * @param customers
	 * @param kdShip
	 * @param addresses
	 * @return
	 */
	public boolean saveShip(List<KdProduct> kdProducts, List<Customer> customers, KdShip kdShip, List<Address> addresses, SessionUser user) {

		boolean tx = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				update(addresses, customers, user);
				kdShip.set("ship_sender_id", customers.get(0).get("customer_id"));
				kdShip.set("ship_receiver_id", customers.get(1).get("customer_id"));
				kdShip.set("ship_from_city_code", addresses.get(0).get("region_code"));
				kdShip.set("ship_to_city_code", addresses.get(1).get("region_code"));
				kdShip.set("ship_status", 1);
				kdShip.set("ship_before_status", 1);
				if (!kdShip.save()) return false;
				for (int i = 0; i < kdProducts.size(); i++) {
					KdProduct kdProduct = kdProducts.get(i);
					if (!kdProduct.save()) return false;
					KdShipProduct kShipProduct = new KdShipProduct();
					kShipProduct.set("ship_id", kdShip.get("ship_id"));
					kShipProduct.set("product_id", kdProduct.get("product_id"));
					if (!kShipProduct.save()) return false;
				}

				//出入库记录  add by  liangxiaoping	
				KdInOut inout = new KdInOut();
				inout.set("ship_id", kdShip.get("ship_id"));
				inout.set("in_time", kdShip.get("create_time"));
				inout.set("network_id", kdShip.get("network_id"));
				if (!inout.save()) return false;


				NetWork netWork = new NetWorkSvc().getNetWork(kdShip.getInt("network_id") + "", user);

				KdShipTrack kdShipTrack = new KdShipTrack();
				kdShipTrack.set("track_ship_id", kdShip.get("ship_id"));
				kdShipTrack.set("user_id", user.getUserId());
				kdShipTrack.set("track_company_id", user.getCompanyId());
				kdShipTrack.set("track_resource_id", kdShip.get("ship_id"));
				kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_CREATE_NUM);
				kdShipTrack.set("track_networkid",kdShip.getInt("network_id"));
				kdShipTrack.set("track_desc", netWork.getStr("sub_network_name") + "开单");
				kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_CREATE);
				kdShipTrack.set("create_time", time);
				if (!kdShipTrack.save()) return false;
				return true;
			}

		});
		return tx;
	}

	/**
	 * 更新运单信息
	 *
	 * @param kdProducts
	 * @param customers
	 * @param kdShip
	 * @param addresses
	 * @param user
	 * @return
	 */
	public boolean updateShip(List<KdProduct> kdProducts, List<Customer> customers, KdShip kdShip, List<Address> addresses, SessionUser user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		boolean tx = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				//插入运单修改变更表
				if(getUpdateContent(kdShip,customers,kdProducts,addresses).length()>0){
					KdShipChange kdShipChange=new KdShipChange();
					kdShipChange.set("ship_id",kdShip.get("ship_id"));
					kdShipChange.set("update_type",1);
					kdShipChange.set("update_content",getUpdateContent(kdShip,customers,kdProducts,addresses));
					kdShipChange.set("user_id",user.getUserId());
					kdShipChange.set("create_time",time);

					if(!kdShipChange.save()) return  false;
					kdShip.set("ship_is_update",1);
				}


				update(addresses, customers, user);
				if(kdShip.get("ship_is_dispatch")==null){
					kdShip.set("ship_is_dispatch",0);
				}else{
					kdShip.set("ship_is_dispatch",1);
				}
				if(kdShip.get("ship_is_rebate")==null){
					kdShip.set("ship_is_rebate",0);
				}else{
					kdShip.set("ship_is_rebate",1);
				}

				kdShip.set("ship_sender_id", customers.get(0).get("customer_id"));
				kdShip.set("ship_receiver_id", customers.get(1).get("customer_id"));
				kdShip.set("ship_from_city_code", addresses.get(0).get("region_code"));
				kdShip.set("ship_to_city_code", addresses.get(1).get("region_code"));
				if (!kdShip.update()) return false;
				//先删除运单货物中间表和运单货物信息
				int delete = Db.update("delete sp,p from  kd_ship_product sp left join kd_product p on sp.product_id=p.product_id where sp.ship_id=" + kdShip.get("ship_id"));
				if (delete == 0) return false;
				for (int i = 0; i < kdProducts.size(); i++) {
					KdProduct kdProduct = kdProducts.get(i);
					if (!kdProduct.save()) return false;
					KdShipProduct kShipProduct = new KdShipProduct();
					kShipProduct.set("ship_id", kdShip.get("ship_id"));
					kShipProduct.set("product_id", kdProduct.get("product_id"));
					if (!kShipProduct.save()) return false;
				}

				//删除出入库记录
				int deleteout = Db.update("delete from kd_in_out where ship_id=" + kdShip.get("ship_id"));
				if (deleteout == 0) return false;
				//出入库记录  add by  liangxiaoping	
				KdInOut inout = new KdInOut();
				inout.set("ship_id", kdShip.get("ship_id"));
				inout.set("in_time", kdShip.get("create_time"));
				inout.set("network_id", kdShip.get("network_id"));
				if (!inout.save()) return false;
				NetWork netWork = new NetWorkSvc().getNetWork(kdShip.getInt("network_id") + "", user);
				KdShipTrack kdShipTrack = new KdShipTrack();
				kdShipTrack.set("track_ship_id", kdShip.get("ship_id"));
				kdShipTrack.set("user_id", user.getUserId());
				kdShipTrack.set("track_company_id", user.getCompanyId());
				kdShipTrack.set("track_resource_id", kdShip.get("ship_id"));
				kdShipTrack.set("track_networkid", kdShip.get("network_id"));
				kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_UPDATE_NUM);
				kdShipTrack.set("track_desc", netWork.getStr("sub_network_name") + "改单");
				kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_UPDATE);
				kdShipTrack.set("create_time", time);
				if (!kdShipTrack.save()) return false;
				return true;
			}

		});
		return tx;
	}

	/**
	 * 保存/更新客户信息
	 *
	 * @param customers
	 * @param addresses
	 * @param user
	 */
	public void update(List<Address> addresses, List<Customer> customers, SessionUser user) {
		boolean tx = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				if (addresses.get(0).get("uuid") == null) {
					addresses.get(0).set("uuid", UUIDUtil.UUID());
					addresses.get(0).set("create_time", sdf.format(time));
					addresses.get(0).set("user_id", user.getUserId());
					if (!addresses.get(0).save()) return false;
				} else {
					if (!addresses.get(0).update()) return false;
				}
				if (addresses.get(1).get("uuid") == null) {
					addresses.get(1).set("uuid", UUIDUtil.UUID());
					addresses.get(1).set("create_time", sdf.format(time));
					addresses.get(1).set("user_id", user.getUserId());
					if (!addresses.get(1).save()) return false;
				} else {
					if (!addresses.get(1).update()) return false;
				}
				if (customers.get(0).get("customer_id") == null) {
					customers.get(0).set("customer_sn", getCustomerSn(user, "2"));
					customers.get(0).set("customer_address_id", addresses.get(0).get("uuid"));
					customers.get(0).set("company_id", user.getCompanyId());
					customers.get(0).set("network_id", 1);
					customers.get(0).set("create_time", sdf.format(time));
					if (!customers.get(0).save()) return false;
				} else {
					if (!customers.get(0).update()) return false;
				}
				if (customers.get(1).get("customer_id") == null) {
					customers.get(1).set("customer_sn", getCustomerSn(user, "1"));
					customers.get(1).set("customer_address_id", addresses.get(1).get("uuid"));
					customers.get(1).set("company_id", user.getCompanyId());
					customers.get(1).set("network_id", 1);
					customers.get(1).set("create_time", sdf.format(time));
					if (!customers.get(1).save()) return false;
				} else {
					if (!customers.get(1).update()) return false;
				}
				return true;
			}
		});

	}


	/**
	 * 运单列表
	 *
	 * @param paginator
	 * @param model
	 * @param user
	 * @return
	 */
	public Page<KdShip> getKdShipPage(Paginator paginator, KdShipSearchModel model, SessionUser user) {
		StringBuilder select = new StringBuilder(" SELECT s.`ship_id`,getNetworkNameById(s.network_id) netWorkName ,`ship_sn`,s.`create_time`,s.ship_customer_number,getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName,getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,s.ship_status,s.ship_volume,s.ship_weight,s.ship_amount,s.ship_load_times ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();

		sql.append(" FROM `kd_ship` s ");
		parm.append(" WHERE 1=1 and  s.`ship_deleted`=0 ");
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
			parm.append(" and s.`ship_status`='" + model.getShipSate() + "' ");

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
		sql.append(" and s.company_id=" + user.getCompanyId() + " and s.network_id in (" + user.toNetWorkIdsStr() + ") ");
		sql.append(" order by s.create_time desc ");
		return KdShip.dao.paginate(paginator, select.toString(), sql.toString());


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
		parm.append(" WHERE 1=1 and  s.`ship_deleted`=0 ");
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


	/**
	 * 获取未签收且未中转的运单分页列表
	 *
	 * @param paginator
	 * @param transferSearchModel
	 * @param sessionUser
	 * @return
	 */
	public Page<KdShip> getNoSignKdShipPage(Paginator paginator, TransferSearchModel transferSearchModel, SessionUser sessionUser) {

		String select = "SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) shipperName, getCustomerName(s.ship_receiver_id) receivingName, s.ship_volume, "
				+ "s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromAdd, getRegion(s.ship_to_city_code) toAdd,getNetworkNameById(s.network_id) netWorkAdd";

		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" FROM kd_ship s  where ship_deleted=0 ");

		if (StringUtils.isNotBlank(transferSearchModel.getShipperName())) {
			parm.append(" and s.ship_sender_id  =" + transferSearchModel.getShipperName());
		}
		if (StringUtils.isNotBlank(transferSearchModel.getReceivingName())) {
			parm.append(" and s.ship_receiver_id  = " + transferSearchModel.getReceivingName());
		}
		if (StringUtils.isNotBlank(transferSearchModel.getShipSn())) {
			parm.append(" and s.`ship_sn`  like '%" + transferSearchModel.getShipSn() + "%'");
		}
		if (StringUtils.isNotBlank(transferSearchModel.getStartCode())) {
			parm.append(" and s.`ship_from_city_code` like '" + StrUtils.getRealRegionCode(transferSearchModel.getStartCode()) + "%'");
		}
		if (StringUtils.isNotBlank(transferSearchModel.getEndCode())) {
			parm.append(" and s.`ship_to_city_code` like '" + StrUtils.getRealRegionCode(transferSearchModel.getEndCode()) + "%'");
		}

		//parm.append(" and s.`ship_id` not in (SELECT ship_id from kd_ship_transfer)");
		parm.append(" and s.`ship_status` < 6 and s.`ship_storage`<2 ");
		parm.append(" and s.`company_id` = ?");
		parm.append(" and s.`load_network_id` in (" + sessionUser.toNetWorkIdsStr() + ")");
		parm.append(" ORDER BY s.`create_time` desc");
		sql.append(parm.toString());
		return KdShip.dao.paginate(paginator, select.toString(), sql.toString(), sessionUser.getCompanyId());
	}

	/**
	 * 获取未签收且未中转的运单列表
	 *
	 * @param transferSearchModel
	 * @param sessionUser
	 * @return
	 */
	public List<KdShip> getNoSignKdShip(TransferSearchModel transferSearchModel, SessionUser sessionUser) {

		String select = "SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) shipperName, getCustomerName(s.ship_receiver_id) receivingName, s.ship_volume, "
				+ "s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromAdd, getRegion(s.ship_to_city_code) toAdd,getNetworkNameById(s.network_id) netWorkAdd,getProductName(s.ship_id) as productName";

		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" FROM kd_ship s  where ship_deleted=0 ");

		if (StringUtils.isNotBlank(transferSearchModel.getShipperName())) {
			parm.append(" and s.ship_sender_id  =" + transferSearchModel.getShipperName());
		}
		if (StringUtils.isNotBlank(transferSearchModel.getReceivingName())) {
			parm.append(" and s.ship_receiver_id  = " + transferSearchModel.getReceivingName());
		}
		if (StringUtils.isNotBlank(transferSearchModel.getShipSn())) {
			parm.append(" and s.`ship_sn`  like '%" + transferSearchModel.getShipSn() + "%'");
		}
		if (StringUtils.isNotBlank(transferSearchModel.getStartCode())) {
			parm.append(" and s.`ship_from_city_code` like '" + StrUtils.getRealRegionCode(transferSearchModel.getStartCode()) + "%'");
		}
		if (StringUtils.isNotBlank(transferSearchModel.getEndCode())) {
			parm.append(" and s.`ship_to_city_code` like '" + StrUtils.getRealRegionCode(transferSearchModel.getEndCode()) + "%'");
		}

		parm.append(" and s.`ship_status` < 7 and s.`ship_status`!=4 and s.`ship_storage`<2 ");
		parm.append(" and s.`company_id` = ?");
		parm.append(" and s.`load_network_id` in (" + sessionUser.toNetWorkIdsStr() + ")");
		parm.append(" ORDER BY s.`create_time` desc");
		sql.append(parm.toString());
		return new KdShip().find(select.toString() + sql.toString(), sessionUser.getCompanyId());
	}


	/**
	 * 删除运单/改变运单状态
	 *
	 * @param shipId
	 * @return
	 */
	public boolean deleteShip(String shipId,SessionUser user,String deleteContent) {
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdShipChange shipChange=new KdShipChange();
				shipChange.set("ship_id",shipId);
				shipChange.set("update_type",2);
				shipChange.set("update_content",deleteContent);
				shipChange.set("user_id",user.getUserId());
				shipChange.set("create_time",time);
				if(!shipChange.save()) return  false;

				//删除出入库记录
				int deleteout = Db.update("delete from kd_in_out where ship_id in (" + shipId + ")");
				if (deleteout == 0) return false;
				int update = Db.update("update kd_ship  set ship_deleted=1  where ship_id in (" + shipId + ")");
				if (update > 0) {
					return true;
				}
				return true;
			}
		});
	}

	/**
	 * 运单信息
	 *
	 * @param shipId
	 * @return
	 */
	public KdShip findShipList(String shipId){
	StringBuilder sql=new StringBuilder(" SELECT  getRealNameByUserId(s.user_id) as userName,s.user_id,a.uuid as suuid,a1.uuid as ruuid, s.ship_id,s.ship_sn,s.network_id,s.ship_sender_id,c.customer_corp_name AS sendCorpName,c.customer_mobile AS sendMobile,s.ship_from_city_code,a.tail_address AS senderAdd,s.ship_receiver_id, c1.customer_corp_name AS receiverCorpName,c1.customer_mobile AS receiverMobile,");
	sql.append("s.ship_to_city_code,a1.tail_address AS receiverAdd, getNetworkNameById(s.network_id) AS netWorkName,ship_status,s.create_time,s.ship_customer_number,s.ship_delivery_method,s.ship_agency_fund,s.remark,s.ship_fee,s.ship_insurance_fee,");
	sql.append(" s.ship_delivery_fee,s.ship_package_fee,s.ship_delivery_fee,s.ship_addon_fee,s.ship_total_fee,s.ship_pay_way,s.ship_pickup_fee,getCustomerName(s.ship_sender_id) AS senderName,");
	sql.append(" getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,");
	sql.append(" getBookLongRegion(ln.sub_addr_uuid) as netAddr,ln.sub_logistic_telphone as netTelPhone,");
	sql.append(" s.ship_nowpay_fee,s.ship_pickuppay_fee,s.ship_receiptpay_fee,s.ship_monthpay_fee,s.ship_arrearspay_fee,s.ship_goodspay_fee,s.ship_receipt_require, ");
	sql.append(" s.ship_receipt_num,s.ship_receipt_remark,s.goods_sn,s.ship_rebate_fee,s.ship_is_rebate,s.ship_is_dispatch,s.to_network_id,getNetworkNameById(s.to_network_id) as toNetWorkName,");
	//费用是否结算
	sql.append(" CASE (s.ship_total_fee-SUM(IFNULL(f.fee,0))) WHEN 0 THEN 1 WHEN s.ship_total_fee THEN 2 ELSE 3 END AS state ");
	sql.append(" from  kd_ship s LEFT JOIN kd_customer c  ON  c.customer_id=s.ship_sender_id  ");
	sql.append(" LEFT JOIN address_book a ON a.uuid=c.customer_address_id  ");
	sql.append(" LEFT JOIN  kd_customer c1 ON c1.customer_id=s.ship_receiver_id  ");
	sql.append(" LEFT JOIN address_book a1 ON a1.uuid=c1.customer_address_id ");
	sql.append(" LEFT JOIN logistics_network ln ON s.network_id=ln.id ");
	sql.append(" LEFT JOIN kd_ship_fee f ON s.ship_id=f.ship_id AND f.fee_type<7 ");
	sql.append("where s.ship_id=?");
	return KdShip.dao.findFirst(sql.toString(), shipId);
	}

	/**
	 * 运单托运方收货方 客户信息
	 *
	 * @param shipId
	 * @param type
	 * @return
	 */

	public Customer getCustomer(String shipId, int type) {
		StringBuilder sql = new StringBuilder(" select  c.customer_name,c.customer_corp_name,c.customer_mobile,getBookRegion(c.customer_address_id) as to_addr, a.tail_address  ");
		sql.append("from kd_customer c  ");
		sql.append(" left join address_book a  on a.uuid=c.customer_address_id ");
		if (type == 1) {
			sql.append(" left join kd_ship s on s.ship_sender_id=c.customer_id ");
		} else {
			sql.append(" left join kd_ship s on s.ship_receiver_id=c.customer_id ");
		}
		sql.append(" where s.ship_id=? ");
		return Customer.dao.findFirst(sql.toString(), shipId);

	}


	/**
	 * 运单商品列表
	 *
	 * @param shipId
	 * @return
	 * @author liangxp
	 */
	public List<KdProduct> findShipProducts(String shipId) {
		StringBuilder sql = new StringBuilder("SELECT p.product_id, p.product_sn, p.product_name, p.product_unit, p.product_amount, p.product_volume, p.product_weight, p.product_price");
		sql.append(" FROM kd_ship_product s LEFT JOIN kd_product p on s.product_id=p.product_id where s.ship_id=?");
		return KdProduct.dao.find(sql.toString(), shipId);
	}


	/**
	 * 查询指定id的运单列表
	 *
	 * @param shipIds
	 * @return
	 * @author liangxp
	 */
	public List<KdShip> findShips(String shipIds) {
		StringBuilder sql = new StringBuilder("SELECT s.ship_id, s.network_id, s.company_id, s.load_network_id , s.before_load_network_id, s.ship_volume, s.ship_weight, s.ship_load_times,s.ship_status,s.ship_before_status ");
		sql.append(" FROM kd_ship s where s.ship_id in (" + shipIds + ")");
		return KdShip.dao.find(sql.toString());
	}

	public List<KdShip> findLoadShips(int loadId) {
		StringBuilder sql = new StringBuilder("SELECT s.ship_id, s.network_id, s.company_id, s.load_network_id , s.before_load_network_id, s.ship_volume, s.ship_weight, s.ship_load_times ");
		sql.append(" FROM kd_ship s left join kd_truck_ship ts on s.ship_id=ts.ship_id left join kd_truck_load l on l.load_id=ts.truck_load_id  where l.load_id=?");
		return KdShip.dao.find(sql.toString(), loadId);
	}


	/**
	 * 运单简单信息
	 *
	 * @param shipId
	 * @return
	 * @author liangxp
	 */
	public KdShip findShortShip(String shipId) {
		StringBuilder sql = new StringBuilder(" select s.ship_id,s.ship_sn,getCustomerName(s.ship_sender_id) as senderName,"
				+ "getCustomerName(s.ship_receiver_id)as receiverName,getRegion(s.ship_from_city_code) as fromAdd,s.ship_status,ship_agency_fund,"
				+ "getRegion(s.ship_to_city_code) as toAdd,s.ship_volume,s.ship_amount,s.ship_weight,s.create_time,s.load_network_id,s.network_id,getNetworkNameById(s.network_id) AS netWorkName");
		sql.append(" from  kd_ship s where s.ship_id=? ");
		return KdShip.dao.findFirst(sql.toString(), shipId);
	}


	public KdShip findShortShip(String shipId, SessionUser user) {
		StringBuilder sql = new StringBuilder(" select s.ship_id,s.ship_sn,getCustomerCorpName(s.ship_sender_id) as senderName,"
				+ "getCustomerCorpName(s.ship_receiver_id)as receiverName,getRegion(s.ship_from_city_code) as fromAdd,"
				+ "getRegion(s.ship_to_city_code) as toAdd,s.ship_volume,s.ship_amount,s.ship_weight,s.create_time,s.load_network_id,s.network_id,s.company_id");
		sql.append(" from  kd_ship s where s.ship_id=? and company_id=?");
		return KdShip.dao.findFirst(sql.toString(), shipId, user.getCompanyId());
	}


	/**
	 * 运单商品列表
	 *
	 * @param shipId
	 * @return
	 * @author chenan
	 */
	public List<KdProduct> findShipProductsToPrint(String shipId) {
		StringBuilder sql = new StringBuilder("SELECT p.product_id, p.product_sn, p.product_name, p.product_unit, p.product_amount, p.product_volume, p.product_weight, p.product_price");
		sql.append(" FROM kd_ship_product s LEFT JOIN kd_product p on s.product_id=p.product_id where s.ship_id=?");
		List<KdProduct> list = new ArrayList();
		List<KdProduct> list2 = new ArrayList();
		list = KdProduct.dao.find(sql.toString(), shipId);
		switch (list.size()) {
			case 0:
				for (int i = 0; i < 3; i++) {
					KdProduct kdProduct = new KdProduct();
					kdProduct.set("product_name", " ");
					kdProduct.set("product_amount", " ");
					kdProduct.set("product_volume", " ");
					kdProduct.set("product_weight", " ");
					kdProduct.set("product_price", " ");
					kdProduct.set("product_sn", " ");
					kdProduct.set("product_unit", " ");
					list2.add(kdProduct);
				}
				break;
			case 1:
				for (int i = 0; i < 3; i++) {
					KdProduct kdProduct = new KdProduct();
					kdProduct.set("product_name", " ");
					kdProduct.set("product_amount", " ");
					kdProduct.set("product_volume", " ");
					kdProduct.set("product_weight", " ");
					kdProduct.set("product_price", " ");
					kdProduct.set("product_sn", " ");
					kdProduct.set("product_unit", " ");
					if (i == 0) {
						kdProduct = list.get(0);
					}
					list2.add(kdProduct);
				}
				break;
			case 2:
				for (int i = 0; i < 3; i++) {
					KdProduct kdProduct = new KdProduct();
					kdProduct.set("product_name", " ");
					kdProduct.set("product_amount", " ");
					kdProduct.set("product_volume", " ");
					kdProduct.set("product_weight", " ");
					kdProduct.set("product_price", " ");
					kdProduct.set("product_sn", " ");
					kdProduct.set("product_unit", " ");
					if (i < 2) {
						kdProduct = list.get(i);
					}
					list2.add(kdProduct);
				}
				break;
			case 3:
				for (int i = 0; i < 3; i++) {
					KdProduct kdProduct = new KdProduct();
					kdProduct.set("product_name", " ");
					kdProduct.set("product_amount", " ");
					kdProduct.set("product_volume", " ");
					kdProduct.set("product_weight", " ");
					kdProduct.set("product_price", " ");
					kdProduct.set("product_sn", " ");
					kdProduct.set("product_unit", " ");
					if (i < 3) {
						kdProduct = list.get(i);
					}
					list2.add(kdProduct);
				}
				break;
			default:
				break;
		}
		if (list.size() > 3) {
			StringBuilder sql2 = new StringBuilder("SELECT  sum(p.product_amount) product_amount, sum(p.product_volume) product_volume, sum(p.product_weight) product_weight, sum(p.product_price) product_price");
			sql2.append(" FROM kd_ship_product s LEFT JOIN kd_product p on s.product_id=p.product_id where s.ship_id=?");
			KdProduct kdProduct = new KdProduct().findFirst(sql2.toString(), shipId);
			kdProduct.set("product_name", "全部");
			kdProduct.set("product_sn", "");
			for (int i = 0; i < 2; i++) {
				list2.add(list.get(i));
			}
			list2.add(kdProduct);
		}

		return list2;
	}

	/**
	 * 查询运单号是否存在
	 *
	 * @param shipSn
	 * @param sessionUser
	 * @return
	 */
	public boolean checkShipSn(String shipSn, SessionUser sessionUser) {

		List<KdShip> list = KdShip.dao.find("select  ship_sn  from  kd_ship where  ship_sn=? and company_id=" + sessionUser.getCompanyId(), shipSn);
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;


	}

	/**
	 * 查询运单下的配载单列表
	 * @param shipId
	 * @return
	 */
	public List<KdTrunkLoad> getTrunkLoadList(String shipId){
		String select = "SELECT  tl.load_sn,getNetWorkName(tl.network_id) AS loadWorkName,load_delivery_status,t.truck_id_number,t.truck_driver_name, t.truck_driver_mobile, tl.load_depart_time,tl.load_transport_type,tl.load_depart_time,load_arrival_time";
		StringBuilder bu = new StringBuilder(" from kd_truck_ship ts LEFT JOIN kd_truck_load tl  ON tl.load_id=ts.truck_load_id  LEFT JOIN truck t ON t.truck_id=tl.truck_id where ts.ship_id=?");
		return KdTrunkLoad.dao.find(select+bu.toString(),shipId);

	}

	public KdSetting getPrintSetting(int compayId) {
		KdSetting set = KdSetting.dao.findFirst("SELECT id,value,name,company_id from kd_setting where company_id=?", compayId);
		if (set == null) {
			set = KdSetting.dao.findFirst("SELECT id,value,name,company_id from kd_setting where company_id=?", 0);
		}

		return set;
	}

	/**
	 * 获取运单修改变更记录列表
	 */
	public Page<KdShipChange> getShipChangePage(Paginator paginator,SessionUser sessionUser,KdShipSearchModel model,String ... shipId) {

		String select = "SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) shipperName, getCustomerName(s.ship_receiver_id) receivingName, "
				+ "getNetworkNameById(s.network_id) netWork,getPCUserName(c.user_id) userName,c.create_time as updateTime,s.create_time as shipTime,c.update_content,"
				+ "getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd";

		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" FROM kd_ship_change c left join kd_ship s on c.ship_id=s.ship_id  where ship_deleted=0 and c.update_type=1");
		parm.append(" and s.`network_id` in (" + sessionUser.toNetWorkIdsStr() + ")");

		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			parm.append(" and s.`network_id`='" + model.getNetWorkId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
			parm.append(" and  to_days(s.create_time) between to_days('" + model.getStartTime() + "') and to_days('" + model.getEndTime() + "') ");
		}
		if (StringUtils.isNotBlank(model.getShipSn())) {
			parm.append(" and s.`ship_sn` like '%" + model.getShipSn() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getSenderId())) {
			parm.append(" and s.`ship_sender_id`='" + model.getSenderId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getReceiverId())) {
			parm.append(" and s.`ship_receiver_id`='" + model.getReceiverId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getFromCode())) {
			parm.append("and s.`ship_from_city_code`like '" + StrUtils.getRealRegionCode(model.getFromCode()) + "%'");
		}
		if (StringUtils.isNotBlank(model.getToCode())) {
			parm.append(" and s.`ship_to_city_code` like '" + StrUtils.getRealRegionCode(model.getToCode()) + "%' ");
		}

		if(shipId.length>0){
			parm.append(" and s.ship_id="+shipId[0]);
		}

		parm.append(" ORDER BY c.`create_time` desc");
		sql.append(parm.toString());
		return KdShipChange.dao.paginate(paginator, select.toString(), sql.toString());
	}


	/**
	 * 获取运单删除变更记录列表
	 */
	public Page<KdShipChange> getShipDeleteChangePage(Paginator paginator,SessionUser sessionUser,KdShipSearchModel model) {

		String select = "SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) shipperName, getCustomerName(s.ship_receiver_id) receivingName, "
				+ "getNetworkNameById(s.network_id) netWork,getPCUserName(c.user_id) userName,c.create_time as updateTime,s.create_time as shipTime,c.update_content,"
				+ "getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd";

		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" FROM kd_ship_change c left join kd_ship s on c.ship_id=s.ship_id  where ship_deleted=1 and c.update_type=2");
		parm.append(" and s.`network_id` in (" + sessionUser.toNetWorkIdsStr() + ")");

		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			parm.append(" and s.`network_id`='" + model.getNetWorkId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
			parm.append(" and  to_days(s.create_time) between to_days('" + model.getStartTime() + "') and to_days('" + model.getEndTime() + "') ");
		}
		if (StringUtils.isNotBlank(model.getShipSn())) {
			parm.append(" and s.`ship_sn` like '%" + model.getShipSn() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getSenderId())) {
			parm.append(" and s.`ship_sender_id`='" + model.getSenderId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getReceiverId())) {
			parm.append(" and s.`ship_receiver_id`='" + model.getReceiverId() + "' ");
		}
		if (StringUtils.isNotBlank(model.getFromCode())) {
			parm.append("and s.`ship_from_city_code`like '" + StrUtils.getRealRegionCode(model.getFromCode()) + "%'");
		}
		if (StringUtils.isNotBlank(model.getToCode())) {
			parm.append(" and s.`ship_to_city_code` like '" + StrUtils.getRealRegionCode(model.getToCode()) + "%' ");
		}

		parm.append(" ORDER BY c.`create_time` desc");
		sql.append(parm.toString());
		return KdShipChange.dao.paginate(paginator, select.toString(), sql.toString());
	}

	public String getCustomerSn(SessionUser sessionUser, String customerType) {
		Record record = Db.findFirst("SELECT count(1) as countId from kd_customer where customer_type=" + customerType + " and company_id=" + sessionUser.getCompanyId());
		long id = 1;
		if (record.getLong("countId") == 0) {

		} else {
			id = record.getLong("countId") + 1;
		}

		String maxId = id + "";
		switch (maxId.length()) {
			case 1:
				maxId = "00000" + maxId;
				break;
			case 2:
				maxId = "0000" + maxId;
				break;
			case 3:
				maxId = "000" + maxId;
				break;
			case 4:
				maxId = "00" + maxId;
				break;
			case 5:
				maxId = "0" + maxId;
				break;

			default:
				break;
		}
		String no = "";
		if (!customerType.equals("")) {
			switch (Integer.valueOf(customerType)) {
				case 1:
					no = "SH" + maxId;
					break;
				case 2:
					no = "TY" + maxId;
					break;
				case 3:
					no = "ZZ" + maxId;
					break;
				default:
					break;
			}
		}
		return no;

	}



	/**
	 * 运单修改变更内容
	 * @param ship
	 * @param customers
	 * @param newProducts
	 * @return
	 */
	public String getUpdateContent(KdShip ship,List<Customer> customers,List<KdProduct> newProducts,List<Address> addresses) {
		String shipId = ship.get("ship_id").toString();
		KdShip oldShip = new KdShip().findById(shipId);

		StringBuffer content = new StringBuffer();


		if(ship.get("ship_to_city_code")!=null){
			String cityCode=oldShip.get("ship_to_city_code")==null?"":oldShip.getStr("ship_to_city_code");
			if (!cityCode.trim().equals(ship.getStr("ship_to_city_code").trim())) {
				String oldAddr=Db.findFirst("SELECT getCityRegion(?) addr",cityCode).getStr("addr");
				String newAddr=Db.findFirst("SELECT getCityRegion(?) addr",ship.getStr("ship_to_city_code")).getStr("addr");
				content.append("【到达地】由"+oldAddr+"改为"+newAddr);
			}
		}

		if(ship.get("to_network_id")!=null){
			int toNetWorkId=oldShip.get("to_network_id")==null?0:oldShip.getInt("to_network_id");
			if (toNetWorkId!=ship.getInt("to_network_id")) {
				String oldNetWorkName=Db.findFirst("SELECT SELECT getNetworkNameById(?) netWorkName",toNetWorkId).getStr("netWorkName");
				String newNetWorkName=Db.findFirst("SELECT getNetworkNameById(?) netWorkName",ship.get("to_network_id")).getStr("netWorkName");
				content.append("【到货网点】由"+oldNetWorkName+"改为"+newNetWorkName);
			}
		}

		if(ship.get("ship_is_dispatch")!=null){
			if(oldShip.getBoolean("ship_is_dispatch")){
			}else{
				content.append("【是否急件】由否改为是");
			}
		}else{
			if(!oldShip.getBoolean("ship_is_dispatch")){
			}else{
				content.append("【是否急件】由是改为否");
			}

		}

		if(ship.get("ship_fee")!=null){
			double fee=ship.get("ship_fee")==null?0.0:ship.getDouble("ship_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_fee"))) {
				content.append("【运费】由"+fee+"改为"+ship.getDouble("ship_fee"));
			}
		}

		if(ship.get("ship_pickup_fee")!=null){
			double fee=oldShip.get("ship_pickup_fee")==null?0.0:oldShip.getDouble("ship_pickup_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_pickup_fee"))) {
				content.append("【提货费】由"+fee+"改为"+ship.getDouble("ship_pickup_fee"));
			}
		}

		if(ship.get("ship_delivery_fee")!=null){
			double fee=oldShip.get("ship_delivery_fee")==null?0.0:oldShip.getDouble("ship_delivery_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_delivery_fee"))) {
				content.append("【送货费】由"+fee+"改为"+ship.getDouble("ship_delivery_fee"));
			}
		}

		if(ship.get("ship_insurance_fee")!=null){
			double fee=oldShip.get("ship_insurance_fee")==null?0.0:oldShip.getDouble("ship_insurance_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_insurance_fee"))) {
				content.append("【保费】由"+fee+"改为"+ship.getDouble("ship_insurance_fee"));
			}
		}

		if(ship.get("ship_package_fee")!=null){
			double fee=oldShip.get("ship_package_fee")==null?0.0:oldShip.getDouble("ship_package_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_package_fee"))) {
				content.append("【包装费】由"+fee+"改为"+ship.getDouble("ship_package_fee"));
			}
		}

		if(ship.get("ship_addon_fee")!=null){
			double fee=oldShip.get("ship_addon_fee")==null?0.0:oldShip.getDouble("ship_addon_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_addon_fee"))) {
				content.append("【其他费】由"+fee+"改为"+ship.getDouble("ship_addon_fee"));
			}
		}

		if(ship.get("ship_pay_way")!=null){
			if (oldShip.getInt("ship_pay_way")!=ship.getInt("ship_pay_way")) {
				content.append("【付款方式】由"+TypeChange.payWayChange(oldShip.getInt("ship_pay_way"))+"改为"+TypeChange.payWayChange(ship.getInt("ship_pay_way")));
			}
		}

		if(ship.get("ship_nowpay_fee")!=null){
			double fee=oldShip.get("ship_nowpay_fee")==null?0.0:oldShip.getDouble("ship_nowpay_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_nowpay_fee"))) {
				content.append("【现付费】由"+fee+"改为"+ship.getDouble("ship_nowpay_fee"));
			}
		}
		if(ship.get("ship_pickuppay_fee")!=null){
			double fee=oldShip.get("ship_pickuppay_fee")==null?0.0:oldShip.getDouble("ship_pickuppay_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_pickuppay_fee"))) {
				content.append("【提付费】由"+fee+"改为"+ship.getDouble("ship_pickuppay_fee"));
			}
		}
		if(ship.get("ship_receiptpay_fee")!=null){
			double fee=oldShip.get("ship_receiptpay_fee")==null?0.0:oldShip.getDouble("ship_receiptpay_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_receiptpay_fee"))) {
				content.append("【回单付费】由"+fee+"改为"+ship.getDouble("ship_receiptpay_fee"));
			}
		}
		if(ship.get("ship_monthpay_fee")!=null){
			double fee=oldShip.get("ship_monthpay_fee")==null?0.0:oldShip.getDouble("ship_monthpay_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_monthpay_fee"))) {
				content.append("【月付费】由"+fee+"改为"+ship.getDouble("ship_monthpay_fee"));
			}
		}
		if(ship.get("ship_arrearspay_fee")!=null){
			double fee=oldShip.get("ship_arrearspay_fee")==null?0.0:oldShip.getDouble("ship_arrearspay_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_arrearspay_fee"))) {
				content.append("【短欠付费】由"+fee+"改为"+ship.getDouble("ship_arrearspay_fee"));
			}
		}
		if(ship.get("ship_goodspay_fee")!=null){
			double fee=oldShip.get("ship_goodspay_fee")==null?0.0:oldShip.getDouble("ship_goodspay_fee");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_goodspay_fee"))) {
				content.append("【贷款付费】由"+fee+"改为"+ship.getDouble("ship_goodspay_fee"));
			}
		}


		if(ship.get("ship_receipt_require")!=null){
			int receiptRequire=oldShip.get("ship_receipt_require")==null?0:oldShip.get("ship_receipt_require");
			if (receiptRequire!=ship.getInt("ship_receipt_require")) {
				content.append("【回单要求】由"+TypeChange.receiptRequireChange(receiptRequire)+"改为"+TypeChange.receiptRequireChange(ship.getInt("ship_receipt_require")));
			}
		}

		if(ship.get("ship_receipt_num")!=null){
			int receiptNum=oldShip.get("ship_receipt_num")==null?0:oldShip.getInt("ship_receipt_num");
			if (receiptNum!=ship.getInt("ship_receipt_num")) {
				content.append("【回单份数】由"+receiptNum+"改为"+ship.getInt("ship_receipt_num"));
			}
		}

		if(ship.get("ship_receipt_remark")!=null){
			String receiptRemark=oldShip.get("ship_receipt_remark")==null?" ":oldShip.getStr("ship_receipt_remark");
			if (!receiptRemark.trim().equals(ship.getStr("ship_receipt_remark").trim())) {
				content.append("【回单备注】由"+receiptRemark+"改为"+ship.getStr ("ship_receipt_remark"));
			}
		}

		if(ship.get("goods_sn")!=null){
			if (!oldShip.getStr("goods_sn").trim().equals(ship.getStr("goods_sn").trim())) {
				content.append("【货号】由"+oldShip.get("goods_sn")+"改为"+ship.get("goods_sn"));
			}
		}

		if(ship.get("ship_delivery_method")!=null){
			int deliveryMethod=oldShip.get("ship_delivery_method")==null?0:oldShip.getInt("ship_delivery_method");
			if (deliveryMethod!=ship.getInt("ship_delivery_method")) {
				content.append("【送货方式】由"+TypeChange.deliveryMethodChange(deliveryMethod)+"改为"+TypeChange.deliveryMethodChange(ship.getInt("ship_delivery_method")));
			}
		}

		if(ship.get("ship_agency_fund")!=null){
			double fee=oldShip.get("ship_agency_fund")==null?0.0:oldShip.getDouble("ship_agency_fund");
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getDouble("ship_agency_fund"))) {
				content.append("【代收货款】由"+fee+"改为"+ship.getDouble("ship_agency_fund"));
			}
		}

		if(ship.get("ship_customer_number")!=null){
			String customerNumber=oldShip.get("ship_customer_number")==null?" ":oldShip.getStr("ship_customer_number");
			if (!customerNumber.trim().equals(ship.getStr("ship_customer_number").trim())) {
				content.append("【客户单号】由"+customerNumber+"改为"+ship.getStr("ship_customer_number"));
			}
		}

		if(ship.get("ship_rebate_fee")!=null){
			double fee=oldShip.get("ship_rebate_fee")==null?0.0:oldShip.getBigDecimal("ship_rebate_fee").doubleValue();
			if (Double.doubleToLongBits(fee)!=Double.doubleToLongBits(ship.getBigDecimal("ship_rebate_fee").doubleValue())) {
				content.append("【回扣】由"+fee+"改为"+ship.getBigDecimal("ship_rebate_fee").doubleValue());
			}
		}

		if(ship.get("ship_is_rebate")!=null){
			if(oldShip.getBoolean("ship_is_rebate")){
			}else{
				content.append("【回扣已返】由否改为是");
			}
		}else{
			if(!oldShip.getBoolean("ship_is_rebate")){
			}else{
				content.append("【回扣已返】由是改为否");
			}

		}

		if(ship.get("remark")!=null){
			String remark=oldShip.get("remark")==null?" ":oldShip.get("remark");
			if (!remark.trim().equals(ship.getStr("remark").trim())) {
				content.append("【开单备注】由"+remark+"改为"+ship.getStr("remark"));
			}
		}

		String customerContent=getUpdateCustomerContent(shipId,customers,addresses);
		String productContent=getUpdateProductsContent(shipId,newProducts);
		content.append(customerContent);
		content.append(productContent);
		return content.toString();
	}

	/**
	 * 收货方托运方变更信息
	 * @param shipId
	 * @param customers
	 * @return
	 */
	public String getUpdateCustomerContent(String shipId,List<Customer> customers,List<Address> address ) {

		StringBuffer content = new StringBuffer();

		Customer sender=customers.get(0); //托运方
		Customer receiver=customers.get(1); //收货方

		int senderId=new KdShip().findFirst("select ship_sender_id from kd_ship where ship_id=?",shipId).getInt("ship_sender_id");
		int receiverId=new KdShip().findFirst("select ship_receiver_id from kd_ship where ship_id=?",shipId).getInt("ship_receiver_id");

		Customer oldSender=new Customer().findFirst("select customer_name,customer_address_id,customer_mobile,a.tail_address addr from kd_customer c left join address_book a on c.customer_address_id=a.uuid  where c.customer_id=?",senderId);
		Customer oldReceiver=new Customer().findFirst("select customer_name,customer_address_id,customer_mobile,a.tail_address addr from kd_customer c left join address_book a on c.customer_address_id=a.uuid where c.customer_id=?",receiverId);


		String senderName=oldSender.get("customer_name");
		if(!senderName.trim().equals(sender.getStr("customer_name").trim())){
			content.append("【托运方名称】由"+senderName+"改为"+sender.get("customer_name"));
		}

		String senderMobile=oldSender.get("customer_mobile");
		if(!senderMobile.trim().equals(sender.getStr("customer_mobile").trim())){
			content.append("【托运方联系电话】由"+senderMobile+"改为"+sender.get("customer_mobile"));
		}

		if(address.get(0)!=null){
			String addr=oldSender.get("addr")==null?" ":oldSender.getStr("addr");
			if(!addr.trim().equalsIgnoreCase(address.get(0).getStr("tail_address"))){
				content.append("【托运方详细地址】由"+addr+"改为"+address.get(0).getStr("tail_address"));
			}
		}


		String receiverName=oldReceiver.get("customer_name");
		if(!senderName.trim().equals(sender.getStr("customer_name").trim())){
			content.append("【收货方名称】由"+receiverName+"改为"+receiver.get("customer_name"));
		}

		String receiverMobile=oldReceiver.get("customer_mobile");
		if(!senderMobile.trim().equals(sender.getStr("customer_mobile").trim())){
			content.append("【收货方联系电话】由"+receiverMobile+"改为"+receiver.get("customer_mobile"));
		}

		if(address.get(1)!=null){
			String addr=oldReceiver.get("addr")==null?" ":oldReceiver.getStr("addr");
			if(!addr.trim().equalsIgnoreCase(address.get(1).getStr("tail_address"))){
				content.append("【收货方详细地址】由"+addr+"改为"+address.get(1).getStr("tail_address"));
			}
		}

		return content.toString();
	}


	/**
	 * 货物变更信息
	 * @param shipId
	 * @param newProducts
	 * @return
	 */
	public String getUpdateProductsContent(String shipId, List<KdProduct> newProducts) {
		StringBuffer content = new StringBuffer();

		List<KdProduct> products =findShipProducts(shipId);

		if (newProducts.size()>=1) {

			String productName1=products.size()<1?" ":products.get(0).get("product_name")==null?" ":products.get(0).get("product_name");
			int productUnit1=products.size()<1?0:products.get(0).getInt("product_unit")==null?0:products.get(0).get("product_unit");
			int productAmount1=products.size()<1?0:products.get(0).getInt("product_amount")==null?0:products.get(0).getInt("product_amount");
			Double productVolume1=products.size()<1?0.0:products.get(0).getDouble("product_volume")==null?0.0:products.get(0).getDouble("product_volume");
			Double productWeight1=products.size()<1?0.0:products.get(0).getDouble("product_weight")==null?0.0:products.get(0).getDouble("product_weight");
			Double productPrice1=products.size()<1?0.0:products.get(0).getBigDecimal("product_price")==null?0.0:products.get(0).getBigDecimal("product_price").doubleValue();

			KdProduct product1=newProducts.get(0);

			if (product1.get("product_name") != null) {
				if (!productName1.equals(product1.get("product_name"))) {
					content.append("【货物1名称】由"+productName1+"改为"+product1.get("product_name"));
				}
			}
			if (product1.get("product_unit") != null) {
				if (productUnit1!=product1.getInt("product_unit")) {
					content.append("【货物1单位】由"+ TypeChange.productUnitChange(productUnit1)+"改为"+TypeChange.productUnitChange(product1.getInt("product_unit")));
				}
			}
			if (product1.get("product_amount") != null) {
				if (productAmount1!=product1.getInt("product_amount")) {
					content.append("【货物1件数】由"+ productAmount1+"改为"+product1.getInt("product_amount"));

				}
			}
			if (product1.get("product_volume") != null) {
				if (Double.doubleToLongBits(productVolume1)!=Double.doubleToLongBits(product1.getDouble("product_volume"))) {
					content.append("【货物1体积】由"+ productVolume1+"改为"+product1.getDouble("product_volume"));
				}
			}
			if (product1.get("product_weight") != null) {
				if (Double.doubleToLongBits(productWeight1)!=Double.doubleToLongBits(product1.getDouble("product_weight"))) {
					content.append("【货物1重量】由"+ productWeight1+"改为"+product1.getDouble("product_weight"));
				}
			}
			if (product1.get("product_price") != null) {
				if (Double.doubleToLongBits(productPrice1)!=Double.doubleToLongBits(product1.getBigDecimal("product_price").doubleValue())) {
					content.append("【货物1声明货值】由"+ productPrice1+"改为"+product1.getBigDecimal("product_price").doubleValue());
				}
			}
		}


		if (newProducts.size()>=2) {

			String productName2=products.size()<2?" ":products.get(1).get("product_name")==null?" ":products.get(1).get("product_name");
			int productUnit2=products.size()<2?0:products.get(1).getInt("product_unit")==null?0:products.get(1).get("product_unit");
			int productAmount2=products.size()<2?0:products.get(1).getInt("product_amount")==null?0:products.get(1).getInt("product_amount");
			Double productVolume2=products.size()<2?0.0:products.get(1).getDouble("product_volume")==null?0.0:products.get(1).getDouble("product_volume");
			Double productWeight2=products.size()<2?0.0:products.get(1).getDouble("product_weight")==null?0.0:products.get(1).getDouble("product_weight");
			Double productPrice2=products.size()<2?0.0:products.get(1).getBigDecimal("product_price")==null?0.0:products.get(1).getBigDecimal("product_price").doubleValue();

			KdProduct product2=newProducts.get(1);

			if (product2.get("product_name") != null) {
				if (!productName2.equals(product2.get("product_name"))) {
					content.append("【货物2名称】由"+productName2+"改为"+product2.get("product_name"));
				}
			}
			if (product2.get("product_unit") != null) {
				if (productUnit2!=product2.getInt("product_unit")) {
					content.append("【货物2单位】由"+ TypeChange.productUnitChange(productUnit2)+"改为"+TypeChange.productUnitChange(product2.getInt("product_unit")));
				}
			}
			if (product2.get("product_amount") != null) {
				if (productAmount2!=product2.getInt("product_amount")) {
					content.append("【货物2件数】由"+ productAmount2+"改为"+product2.getInt("product_amount"));

				}
			}
			if (product2.get("product_volume") != null) {
				if (Double.doubleToLongBits(productVolume2)!=Double.doubleToLongBits(product2.getDouble("product_volume"))) {
					content.append("【货物2体积】由"+ productVolume2+"改为"+product2.getDouble("product_volume"));
				}
			}
			if (product2.get("product_weight") != null) {
				if (Double.doubleToLongBits(productWeight2)!=Double.doubleToLongBits(product2.getDouble("product_weight"))) {
					content.append("【货物2重量】由"+ productWeight2+"改为"+product2.getDouble("product_weight"));
				}
			}
			if (product2.get("product_price") != null) {
				if (Double.doubleToLongBits(productPrice2)!=Double.doubleToLongBits(product2.getBigDecimal("product_price").doubleValue())) {
					content.append("【货物2声明货值】由"+ productPrice2+"改为"+product2.getBigDecimal("product_price").doubleValue());
				}
			}
		}


		if (newProducts.size()>=3) {

			String productName3=products.size()<3?" ":products.get(2).get("product_name")==null?" ":products.get(2).get("product_name");
			int productUnit3=products.size()<3?0:products.get(2).getInt("product_unit")==null?0:products.get(2).get("product_unit");
			int productAmount3=products.size()<3?0:products.get(2).getInt("product_amount")==null?0:products.get(2).getInt("product_amount");
			Double productVolume3=products.size()<3?0.0:products.get(2).getDouble("product_volume")==null?0.0:products.get(2).getDouble("product_volume");
			Double productWeight3=products.size()<3?0.0:products.get(2).getDouble("product_weight")==null?0.0:products.get(2).getDouble("product_weight");
			Double productPrice3=products.size()<3?0.0:products.get(2).getBigDecimal("product_price")==null?0.0:products.get(2).getBigDecimal("product_price").doubleValue();

			KdProduct product3=newProducts.get(2);

			if (product3.get("product_name") != null) {
				if (!productName3.equals(product3.get("product_name"))) {
					content.append("【货物3名称】由"+productName3+"改为"+product3.get("product_name"));
				}
			}
			if (product3.get("product_unit") != null) {
				if (productUnit3!=product3.getInt("product_unit")) {
					content.append("【货物3单位】由"+ TypeChange.productUnitChange(productUnit3)+"改为"+TypeChange.productUnitChange(product3.getInt("product_unit")));
				}
			}
			if (product3.get("product_amount") != null) {
				if (productAmount3!=product3.getInt("product_amount")) {
					content.append("【货物1件数】由"+ productAmount3+"改为"+product3.getInt("product_amount"));

				}
			}
			if (product3.get("product_volume") != null) {
				if (Double.doubleToLongBits(productVolume3)!=Double.doubleToLongBits(product3.getDouble("product_volume"))) {
					content.append("【货物3体积】由"+ productVolume3+"改为"+product3.getDouble("product_volume"));
				}
			}
			if (product3.get("product_weight") != null) {
				if (Double.doubleToLongBits(productWeight3)!=Double.doubleToLongBits(product3.getDouble("product_weight"))) {
					content.append("【货物3重量】由"+ productWeight3+"改为"+product3.getDouble("product_weight"));
				}
			}
			if (product3.get("product_price") != null) {
				if (Double.doubleToLongBits(productPrice3)!=Double.doubleToLongBits(product3.getBigDecimal("product_price").doubleValue())) {
					content.append("【货物3声明货值】由"+ productPrice3+"改为"+product3.getBigDecimal("product_price").doubleValue());
				}
			}
		}

		return content.toString();
	}

	/**
	 * 交账汇总list
	 * @param model
	 * @param user
	 * @return
	 */
	public List<KdShip> findShipList(KdShipSearchModel model, SessionUser user,String flag){
		StringBuilder select = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		select.append("select ship_sn,getNetworkNameById(s.network_id) as netWorkName,s.create_time,getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName,");
		select.append("getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,ifnull(ship_nowpay_fee,0) as ship_nowpay_fee,ifnull(ship_pickuppay_fee,0) as ship_pickuppay_fee,ifnull(ship_receiptpay_fee,0) as ship_receiptpay_fee,ifnull(ship_monthpay_fee,0) as ship_monthpay_fee,ifnull(ship_arrearspay_fee,'') as ship_arrearspay_fee,ifnull(ship_goodspay_fee,'') as ship_goodspay_fee,");
		select.append("ship_total_fee,ifnull(ship_rebate_fee,'') as ship_rebate_fee,ifnull(f.fee,'') as rebateFee,(ship_total_fee-ifnull(ship_rebate_fee,0)) as receiptsFee,goods_sn,getProductName(s.ship_id) as productName,getPCUserName(s.user_id) as shipName");
		sql.append(" FROM `kd_ship` s LEFT JOIN kd_ship_fee f ON s.ship_id=f.ship_id and f.fee_type=11");
		parm.append(" WHERE  s.`ship_deleted`=0 ");
		String netWorks=user.toNetWorkIdsStr();
		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			netWorks=model.getNetWorkId();
			parm.append(" and s.`network_id` =" +netWorks+ "");
		}
		if("all".equals(flag)){
				parm.append(" and s.`network_id` in(" +netWorks+ ")");
		}else{
			parm.append(" and s.user_id=" +user.getUserId());
		}

		if (StringUtils.isNotBlank(model.getStartTime())) {
			parm.append(" and  to_days(s.create_time) =to_days('"+model.getStartTime().trim()+"')");
		}

		sql.append(parm.toString());
		sql.append(" order by s.create_time desc ");
		select.append(sql.toString());
		return KdShip.dao.find(select.toString());
	}

	/**
	 * 查询审核运单列表
	 * @param model
	 * @param status 0：未审核 1：已审核
	 * @param user
	 * @return
	 */
	public Page<KdShip> getExamineShip(KdShipSearchModel model,int status, SessionUser user,Paginator paginator ){
		StringBuilder select= new StringBuilder();
		StringBuilder parm=new StringBuilder();
		StringBuilder from=new StringBuilder();
		 select.append(" select s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) as netWorkName,s.create_time,getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName,s.ship_status,");
		select.append(" getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,ifnull(ship_nowpay_fee,0) as ship_nowpay_fee,ifnull(ship_pickuppay_fee,0) as ship_pickuppay_fee,ifnull(ship_receiptpay_fee,0) as ship_receiptpay_fee,ifnull(ship_monthpay_fee,0) as ship_monthpay_fee,ifnull(ship_arrearspay_fee,0) as ship_arrearspay_fee,ifnull(ship_goodspay_fee,0) as ship_goodspay_fee,");
		select.append(" ship_total_fee,ifnull(ship_rebate_fee,0) as ship_rebate_fee,ifnull(f.fee,0) as rebateFee,(ship_total_fee-ifnull(ship_rebate_fee,0)) as receiptsFee,goods_sn,getProductName(s.ship_id) as productName,getPCUserName(s.user_id) as shipName");
		from.append(" FROM `kd_ship` s LEFT JOIN kd_ship_fee f ON s.ship_id=f.ship_id and f.fee_type=11");
		parm.append(" WHERE  s.`ship_deleted`=0 and s.ship_examine_type="+status);
	if (StringUtils.isNotBlank(model.getNetWorkId())){
			parm.append(" and s.network_id="+model.getNetWorkId());
		}else{
			parm.append(" and s.network_id in (" + user.toNetWorkIdsStr() + ")");
		}
		if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
			parm.append(" and  to_days(s.create_time) between to_days('" + model.getStartTime() + "') and to_days('" + model.getEndTime() + "') ");
		}
		if(StringUtils.isNotBlank(model.getShipSn())){
			parm.append(" and s.ship_sn="+model.getShipSn());
		}
		parm.append(" order by s.create_time desc");
		from.append(parm.toString());
		return KdShip.dao.paginate(paginator,select.toString(),from.toString());

	}

	/**
	 * 运单审核通过
	 * @param shipIds
	 * @return
	 */
	public boolean  successExamine(String shipIds){
		int update = Db.update(" update kd_ship set ship_examine_type=1 where ship_id in ("+shipIds+")");
		if(update>0){
			return true;
		}
		return false;
	}

	/**
	 * 取消审核
	 * @param shipIds
	 * @return
	 */
	public boolean cancelExamine(String shipIds){
		int update = Db.update(" update kd_ship set ship_examine_type=0 where ship_id in ("+shipIds+")");
		if(update>0){
			return true;
		}
		return false;
	}

}
