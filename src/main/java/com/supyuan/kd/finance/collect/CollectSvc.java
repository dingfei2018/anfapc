package com.supyuan.kd.finance.collect;

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
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.loading.KdLoadGxFee;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.loading.KdTrunkShip;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.network.NetWorkSvc;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.truck.Truck;
import com.supyuan.kd.truck.TruckSvc;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.StrUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.*;

/**
 * 代收货款服务类
 *
 * @author dingfei
 * @create 2017-12-25 15:05
 **/
public class CollectSvc extends BaseService {

    /**
     * 我的货款列表
     *
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryMyLoanList(Paginator paginator, SessionUser user, CollectSearchModel model) {
        StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) AS netWrokName,s.goods_sn,s.ship_agency_fund_status,"
                + "s.ship_agency_fund,ifnull(s.ship_goodspay_fee,0) as ship_goodspay_fee,getNetworkNameById(s.load_network_id) AS endWorkName,s.ship_status,"
                + "s.create_time,getCustomerName(s.ship_sender_id)AS senderName,getCustomerName(s.ship_receiver_id) AS receiverName,"
                + " getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,getProductName(s.ship_id) AS productName,s.ship_volume,s.ship_weight,s.ship_amount,s.ship_status,s.ship_pay_way,s.network_id,s.load_network_id ");
        StringBuilder bu = new StringBuilder(" FROM  kd_ship s ");
        bu.append(" WHERE 1=1 AND s.ship_deleted=0 and ship_agency_fund>0 and  s.ship_storage<2 and s.network_id  in (" + user.toNetWorkIdsStr() + ") ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getNetWorkId())) {
            bu.append(" and s.network_id=?");
            paras.add(model.getNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getLoadNetWorkId())) {
            bu.append(" and s.load_network_id=?");
            paras.add(model.getLoadNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
            bu.append("and s.create_time BETWEEN ? AND ?");
            paras.add(model.getStartTime());
            paras.add(model.getEndTime());
        }
        if (StringUtils.isNotBlank(model.getFundStatus())) {
            bu.append(" and s.ship_agency_fund_status =?");
            paras.add(model.getFundStatus());

        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());

        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());

        }
        if (StringUtils.isNotBlank(model.getFromCode())) {
            bu.append(" and s.ship_from_city_code like ?");
            paras.add(StrUtils.getRealRegionCode(model.getFromCode()) + "%");
        }
        if (StringUtils.isNotBlank(model.getToCode())) {
            bu.append(" and s.ship_to_city_code like ?");
            paras.add(StrUtils.getRealRegionCode(model.getToCode()) + "%");
        }
        if (StringUtils.isNotBlank(model.getShipSn())) {
            bu.append(" and s.ship_sn like ?");
            paras.add("%" + model.getShipSn() + "%");
        }
        bu.append("order by  s.create_time desc ");
        return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
    }
    /**
     * 代收货款列表
     *
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryCollectList(Paginator paginator, SessionUser user, CollectSearchModel model) {
        StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) AS netWrokName,s.ship_agency_fund_status,s.goods_sn,getProductName(s.ship_id) AS productName,"
                + "s.ship_agency_fund,getNetworkNameById(s.load_network_id) AS endWorkName,s.ship_status,"
                + "s.create_time,getCustomerName(s.ship_sender_id)AS senderName,getCustomerName(s.ship_receiver_id) AS receiverName,"
                + " getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,s.ship_volume,s.ship_weight,s.ship_amount,s.ship_status,s.ship_pay_way,s.network_id,s.load_network_id ");
        StringBuilder bu = new StringBuilder(" FROM  kd_ship s ");
        bu.append(" WHERE 1=1 AND s.ship_deleted=0 and ship_agency_fund>0 and  s.ship_storage<2 and s.load_network_id  in (" + user.toNetWorkIdsStr() + ") ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getNetWorkId())) {
            bu.append(" and s.network_id=?");
            paras.add(model.getNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getLoadNetWorkId())) {
            bu.append(" and s.load_network_id=?");
            paras.add(model.getLoadNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
            bu.append("and s.create_time BETWEEN ? AND ?");
            paras.add(model.getStartTime());
            paras.add(model.getEndTime());
        }
        if (StringUtils.isNotBlank(model.getFundStatus())) {
            bu.append(" and s.ship_agency_fund_status =?");
            paras.add(model.getFundStatus());
        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());
        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());
        }
        if (StringUtils.isNotBlank(model.getFromCode())) {
            bu.append(" and s.ship_from_city_code like ?");
            paras.add(StrUtils.getRealRegionCode(model.getFromCode()) + "%");
        }
        if (StringUtils.isNotBlank(model.getToCode())) {
            bu.append(" and s.ship_to_city_code like ?");
            paras.add(StrUtils.getRealRegionCode(model.getToCode()) + "%");
        }
        if (StringUtils.isNotBlank(model.getShipSn())) {
            bu.append(" and s.ship_sn like ?");
            paras.add("%" + model.getShipSn() + "%");
        }
        bu.append("order by  s.create_time desc ");
        return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());

    }

    /**
     * 货款到账列表
     *
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryAccountList(Paginator paginator, SessionUser user, CollectSearchModel model) {
        StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) AS netWrokName,s.ship_agency_fund_status,s.goods_sn,getProductName(s.ship_id) AS productName,"
                + "s.ship_agency_fund,getNetworkNameById(s.load_network_id) AS endWorkName,s.ship_status,s.create_time,getCustomerName(s.ship_sender_id)AS senderName,getCustomerName(s.ship_receiver_id) AS receiverName");
        StringBuilder bu = new StringBuilder(" FROM  kd_ship s ");
        bu.append(" WHERE 1=1 AND s.ship_deleted=0 and ship_agency_fund>0  and (s.ship_agency_fund_status=1 or s.ship_agency_fund_status=3)  and s.network_id  in (" + user.toNetWorkIdsStr() + ") ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getLoadNetWorkId())) {
            bu.append(" and s.load_network_id=?");
            paras.add(model.getLoadNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());

        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());
        }
        bu.append("order by  s.create_time desc ");
        return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
    }

    /**
     * 货款发放列表
     *
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryGrantList(Paginator paginator, SessionUser user, CollectSearchModel model) {
        StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) AS netWrokName,s.goods_sn,getProductName(s.ship_id) AS productName,"
                + "s.ship_agency_fund,getNetworkNameById(s.load_network_id) AS endWorkName,s.create_time,getCustomerName(s.ship_sender_id)AS senderName,getCustomerName(s.ship_receiver_id) AS receiverName");
        StringBuilder bu = new StringBuilder(" FROM  kd_ship s ");
        bu.append(" WHERE 1=1 AND s.ship_deleted=0 and ship_agency_fund>0   and s.ship_agency_fund_status=4  and s.network_id  in (" + user.toNetWorkIdsStr() + ") ");
        List<Object> paras = new ArrayList<Object>();

        if (StringUtils.isNotBlank(model.getLoadNetWorkId())) {
            bu.append(" and s.load_network_id=?");
            paras.add(model.getLoadNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());
        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());

        }
        bu.append("order by  s.create_time desc ");
        return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
    }


    /**
     * 货款回收列表
     *
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryCallBackList(Paginator paginator, SessionUser user, CollectSearchModel model) {
        StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) AS netWrokName,s.goods_sn,getProductName(s.ship_id) AS productName,"
                + "s.ship_agency_fund,getNetworkNameById(s.load_network_id) AS endWorkName, s.create_time,getCustomerName(s.ship_sender_id)AS senderName,getCustomerName(s.ship_receiver_id) AS receiverName");
        StringBuilder bu = new StringBuilder(" FROM  kd_ship s ");
        bu.append(" WHERE 1=1 AND s.ship_deleted=0 and ship_agency_fund>0  and s.ship_agency_fund_status=1  and s.load_network_id  in (" + user.toNetWorkIdsStr() + ") ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getLoadNetWorkId())) {
            bu.append(" and s.load_network_id=?");
            paras.add(model.getLoadNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());
        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());
        }
        bu.append("order by  s.create_time desc ");
        return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
    }



    /**
     * 货款汇款列表
     *
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryRemittanceList(Paginator paginator, SessionUser user, CollectSearchModel model) {
        StringBuilder select = new StringBuilder("SELECT s.ship_id,s.ship_sn,getNetworkNameById(s.network_id) AS netWrokName,s.goods_sn,getProductName(s.ship_id) AS productName,"
                + "s.ship_agency_fund,getNetworkNameById(s.load_network_id) AS endWorkName,s.create_time,getCustomerName(s.ship_sender_id)AS senderName,getCustomerName(s.ship_receiver_id) AS receiverName");
        StringBuilder bu = new StringBuilder(" FROM  kd_ship s ");
        bu.append(" WHERE 1=1 AND s.ship_deleted=0 and ship_agency_fund>0  and s.ship_agency_fund_status=2  and s.network_id  in (" + user.toNetWorkIdsStr() + ") ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getLoadNetWorkId())) {
            bu.append(" and s.load_network_id=?");
            paras.add(model.getLoadNetWorkId());
        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());
        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());
        }

        bu.append("order by  s.create_time desc ");
        return KdShip.dao.paginate(paginator, select.toString(), bu.toString(), paras.toArray());
    }


    /**
     * 货款确认保存
     * @param user
     * @param shipIds
     * @param payType
     * @param flowNo
     * @param voucherNo
     * @param remark
     * @param type
     * @return
     */
    public boolean saveShipFlow(SessionUser user, String shipIds, int payType, String flowNo, String voucherNo, String remark,int type) {
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                Date time = new Date();
                int update = Db.update("update kd_ship  set ship_agency_fund_status=?  where ship_id in (" + shipIds + ")", type);
                if (update == 0) {
                    return false;
                }
                int inoutType = 0;
                int feeType = 0;
                switch (type) {
                    case 2:
                        inoutType = 0;
                        feeType = 21;
                        break;
                    case 3:
                        inoutType = 1;
                        feeType = 22;
                        break;
                    case 4:
                        inoutType = 0;
                        feeType = 23;
                        break;
                    default:
                        inoutType = 1;
                        feeType = 24;
                }

                //插入流水表
                List<Record> loads = Db.find("SELECT ship_agency_fund,ship_id,network_id   FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
                LinkedHashMap loadFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkLoadIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();
                for (Record record:loads) {
                    Integer key = record.getInt("network_id");
                    networkIds.add(key);
                    if (loadFeeMap.containsKey(key)){
                        double load_fee = (double)loadFeeMap.get(key)+record.getDouble("ship_agency_fund");
                        loadFeeMap.put(key ,load_fee);
                    }else {
                        loadFeeMap.put(key ,record.getDouble("ship_agency_fund"));
                        networkLoadIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    flow.set("fee",loadFeeMap.get(id)).set("pay_type",payType).set("flow_no",flowNo).set("voucher_no",voucherNo).set("remark",remark);
                    flow.set("user_id",user.getUserId()).set("network_id",id).set("create_time",time).set("company_id",user.getCompanyId());
                    flow.set("fee_type",feeType).set("id_type",1).set("settlement_type",2).set("inout_type",inoutType).set("flow_sn",new FlowSvc().getFlowSnByComId(user.getCompanyId()));
                    flow.set("resource_id",networkLoadIdMap.get(id)).set("id_type",1);
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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_agency_fund")).set("network_id",record.get("network_id"));
                    kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;
				return true;
            }
        });
    }


}
