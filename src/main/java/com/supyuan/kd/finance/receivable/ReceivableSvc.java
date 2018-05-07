package com.supyuan.kd.finance.receivable;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.finance.KdShipFee;
import com.supyuan.kd.finance.flow.FlowSvc;
import com.supyuan.kd.finance.flow.KdFlow;
import com.supyuan.kd.finance.flow.KdFlowDetail;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.StrUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.*;

/**
 * 应收管理服务类
 *
 * @author dingfei
 * @create 2017-12-20 14:17
 **/
public class ReceivableSvc extends BaseService {


    /**
     * 运单应收(全部)分页list
     *
     * @param user
     * @param search
     * @param paginator
     * @return
     */
    public Page<Record> getAllShipReList(SessionUser user, ReceivableSearchModel search, Paginator paginator) {
        StringBuilder select = new StringBuilder("select s.ship_id,s.ship_sn,getNetworkNameById (s.network_id) AS netWorkName,getNetworkNameById(s.to_network_id) as toNetWorkName,s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,ifnull(s.ship_customer_number,'') ship_customer_number,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,");
        select.append("ifnull(s.ship_nowpay_fee,'') ship_nowpay_fee,if(s.ship_nowpay_fee>0,(case if(1 in (SELECT fee_type from kd_ship_fee where ship_id=s.ship_id ),1,0) when 1 then 0 when 0 then s.ship_nowpay_fee END),'') as not_nowpay_fee," +
                "ifnull(s.ship_pickuppay_fee,'') ship_pickuppay_fee,if(s.ship_pickuppay_fee>0,(case if(2 in (SELECT fee_type from kd_ship_fee where ship_id=s.ship_id ),1,0) when 1 then 0 when 0 then s.ship_pickuppay_fee END),'') as not_pickuppay_fee," +
                "ifnull(s.ship_receiptpay_fee,'') ship_receiptpay_fee,if(s.ship_receiptpay_fee>0,(case if(3 in (SELECT fee_type from kd_ship_fee where ship_id=s.ship_id ),1,0) when 1 then 0 when 0 then s.ship_receiptpay_fee END),'') as not_receiptpay_fee," +
                "ifnull(s.ship_monthpay_fee,'') ship_monthpay_fee,if(s.ship_monthpay_fee>0,(case if(4 in (SELECT fee_type from kd_ship_fee where ship_id=s.ship_id ),1,0) when 1 then 0 when 0 then s.ship_monthpay_fee END),'') as not_monthpay_fee," +
                "ifnull(s.ship_arrearspay_fee,'') ship_arrearspay_fee,if(s.ship_arrearspay_fee>0,(case if(5 in (SELECT fee_type from kd_ship_fee where ship_id=s.ship_id ),1,0) when 1 then 0 when 0 then s.ship_arrearspay_fee END),'') as not_arrearspay_fee," +
                "ifnull(s.ship_goodspay_fee,'') ship_goodspay_fee,if(s.ship_goodspay_fee>0,(case if(6 in (SELECT fee_type from kd_ship_fee where ship_id=s.ship_id ),1,0) when 1 then 0 when 0 then s.ship_goodspay_fee END),'') as not_goodspay_fee," +
                "s.ship_total_fee,(s.ship_total_fee-sum(ifnull(f.fee,0))) as not_total_fee,case (s.ship_total_fee-sum(ifnull(f.fee,0))) WHEN 0 then 1 WHEN s.ship_total_fee THEN 2 ELSE 3 END as state");

        StringBuilder sql = new StringBuilder();
        sql.append(" from kd_ship s LEFT JOIN kd_ship_fee f ON s.ship_id=f.ship_id and f.fee_type<7 ");
        StringBuilder parm = new StringBuilder();
        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }
        parm.append(" where s.ship_deleted=0 and s.network_id in(" + netWorkIds + ")");
        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus())) {
            switch (search.getFeeStatus()) {
                case "1":
                    parm.append(" and f.id is null GROUP BY s.ship_id order by s.create_time desc");
                    break;
                case "2":
                    parm.append(" GROUP BY s.ship_id having (max(s.ship_total_fee)-sum(f.fee))>0 order by s.create_time desc");
                    break;
                case "3":
                    parm.append(" GROUP BY s.ship_id having (max(s.ship_total_fee)-sum(f.fee))=0 order by s.create_time desc");
                    break;
                default:
            }
        } else {
            parm.append(" GROUP BY s.ship_id order by s.create_time desc");
        }


        sql.append(parm.toString());

        Page<Record> page = Db.paginate(paginator.getPageNo(), paginator.getPageSize(), select.toString(), sql.toString());
        return page;
    }

    /**
     * 运单应收(现付)list
     *
     * @param user
     * @param search
     * @param flag
     * @param paginator
     * @return
     */
    public Object getNowPayShipReList(SessionUser user, ReceivableSearchModel search, boolean flag, Paginator... paginator) {
        StringBuilder select = new StringBuilder("SELECT (case ifnull(f.id,0) when 0 then 2 else 1 end) feeState,s.ship_id,ifnull(s.ship_customer_number,'') ship_customer_number,s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
        select.append("s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,ship_nowpay_fee");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM kd_ship s ");
        sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id and f.fee_type=1 ");

        StringBuilder parm = new StringBuilder();

        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }

        parm.append(" where s.ship_deleted=0 and s.network_id in(" + netWorkIds + ") and s.ship_nowpay_fee>0");

        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus()) && StringUtils.isNotBlank(search.getFeeStatus())) {
            if ("1".equals(search.getFeeStatus())) {
                parm.append(" and f.id is null");
            } else {
                parm.append(" and f.id is not null");
            }
        }

        if (paginator.length == 0 && flag) {
            parm.append(" and f.id is null ");
        }

        parm.append(" order by s.create_time desc");

        sql.append(parm.toString());

        if (paginator.length > 0) {
            Page<Record> page = Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
            return page;
        } else {
            select.append(sql.toString());
            List<Record> list = Db.find(select.toString());
            return list;
        }


    }

    /**
     * 运单应收(提付)list
     *
     * @param user
     * @param search
     * @param flag
     * @param paginator
     * @return
     */
    public Object getPickupPayShipReList(SessionUser user, ReceivableSearchModel search, boolean flag, Paginator... paginator) {
        StringBuilder select = new StringBuilder("SELECT (case ifnull(f.id,0) when 0 then 2 else 1 end) feeState,s.ship_id,ifnull(s.ship_customer_number,'') ship_customer_number,s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,getNetworkNameById(if(s.load_network_id!=s.network_id,s.load_network_id,0)) AS toNetWorkName,");
        select.append("s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,s.ship_pickuppay_fee,sa.minus_fee,sa.plus_fee");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM kd_ship s ");
        sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id and f.fee_type=2 ");
        sql.append(" LEFT JOIN kd_ship_abnormal sa ON sa.ship_id=s.ship_id ");

        StringBuilder parm = new StringBuilder();

        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }

        parm.append(" where s.ship_deleted=0 and s.load_network_id in(" + netWorkIds + ") and s.ship_pickuppay_fee>0");
        if (paginator.length == 0 && flag) {
            parm.append(" and f.id is null ");
        }

        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus()) && StringUtils.isNotBlank(search.getFeeStatus())) {
            if ("1".equals(search.getFeeStatus())) {
                parm.append(" and f.id is null");
            } else {
                parm.append(" and f.id is not null");
            }
        }

        parm.append(" order by s.create_time desc");

        sql.append(parm.toString());

        if (paginator.length > 0) {
            Page<Record> page = Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
            return page;
        } else {
            select.append(sql.toString());
            List<Record> list = Db.find(select.toString());
            return list;
        }
    }

    /**
     * 运单应收(回单付)list
     *
     * @param user
     * @param search
     * @param flag
     * @param paginator
     * @return
     */
    public Object getReceiptPayShipReList(SessionUser user, ReceivableSearchModel search, boolean flag, Paginator... paginator) {
        StringBuilder select = new StringBuilder("SELECT (case ifnull(f.id,0) when 0 then 2 else 1 end) feeState,s.ship_id,ifnull(s.ship_customer_number,'') ship_customer_number,s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
        select.append("s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,s.ship_receiptpay_fee");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM kd_ship s ");
        sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id and f.fee_type=3 ");

        StringBuilder parm = new StringBuilder();

        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }

        parm.append(" where s.ship_deleted=0 and s.network_id in(" + netWorkIds + ") and s.ship_receiptpay_fee>0");
        if (paginator.length == 0 && flag) {
            parm.append(" and f.id is null ");
        }

        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus()) && StringUtils.isNotBlank(search.getFeeStatus())) {
            if ("1".equals(search.getFeeStatus())) {
                parm.append(" and f.id is null");
            } else {
                parm.append(" and f.id is not null");
            }
        }

        parm.append(" order by s.create_time desc");

        sql.append(parm.toString());

        if (paginator.length > 0) {
            Page<Record> page = Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
            return page;
        } else {
            select.append(sql.toString());
            List<Record> list = Db.find(select.toString());
            return list;
        }
    }

    /**
     * 运单应收(月付)list
     *
     * @param user
     * @param search
     * @param flag
     * @param paginator
     * @return
     */
    public Object getMonthPayShipReList(SessionUser user, ReceivableSearchModel search, boolean flag, Paginator... paginator) {
        StringBuilder select = new StringBuilder("SELECT (case ifnull(f.id,0) when 0 then 2 else 1 end) feeState,s.ship_id,ifnull(s.ship_customer_number,'') ship_customer_number,s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
        select.append("s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,s.ship_monthpay_fee");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM kd_ship s ");
        sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id and f.fee_type=4 ");

        StringBuilder parm = new StringBuilder();

        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }

        parm.append(" where s.ship_deleted=0 and s.network_id in(" + netWorkIds + ") and s.ship_monthpay_fee>0");
        if (paginator.length == 0 && flag) {
            parm.append(" and f.id is null ");
        }

        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus()) && StringUtils.isNotBlank(search.getFeeStatus())) {
            if ("1".equals(search.getFeeStatus())) {
                parm.append(" and f.id is null");
            } else {
                parm.append(" and f.id is not null");
            }
        }

        parm.append(" order by s.create_time desc");

        sql.append(parm.toString());

        if (paginator.length > 0) {
            Page<Record> page = Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
            return page;
        } else {
            select.append(sql.toString());
            List<Record> list = Db.find(select.toString());
            return list;
        }
    }

    /**
     * 运单应收(短欠付)list
     *
     * @param user
     * @param search
     * @param flag
     * @param paginator
     * @return
     */
    public Object getArrearsPayShipReList(SessionUser user, ReceivableSearchModel search, boolean flag, Paginator... paginator) {
        StringBuilder select = new StringBuilder("SELECT (case ifnull(f.id,0) when 0 then 2 else 1 end) feeState,s.ship_id,ifnull(s.ship_customer_number,'') ship_customer_number,s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
        select.append("s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,s.ship_arrearspay_fee");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM kd_ship s ");
        sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id and f.fee_type=5 ");

        StringBuilder parm = new StringBuilder();

        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }

        parm.append(" where s.ship_deleted=0 and s.network_id in(" + netWorkIds + ") and s.ship_arrearspay_fee>0");
        if (paginator.length == 0 && flag) {
            parm.append(" and f.id is null ");
        }

        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus()) && StringUtils.isNotBlank(search.getFeeStatus())) {
            if ("1".equals(search.getFeeStatus())) {
                parm.append(" and f.id is null");
            } else {
                parm.append(" and f.id is not null");
            }
        }

        parm.append(" order by s.create_time desc");

        sql.append(parm.toString());

        if (paginator.length > 0) {
            Page<Record> page = Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
            return page;
        } else {
            select.append(sql.toString());
            List<Record> list = Db.find(select.toString());
            return list;
        }
    }

    /**
     * 运单应收(货款付)list
     *
     * @param user
     * @param search
     * @param flag
     * @param paginator
     * @return
     */
    public Object getGoodsPayShipReList(SessionUser user, ReceivableSearchModel search, boolean flag, Paginator... paginator) {
        StringBuilder select = new StringBuilder("SELECT (case ifnull(f.id,0) when 0 then 2 else 1 end) feeState,s.ship_id,ifnull(s.ship_customer_number,'') ship_customer_number,s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName,");
        select.append("s.ship_status,s.goods_sn,s.create_time,");
        select.append("getCustomerName(s.ship_sender_id) AS senderName,getCustomerName(s.ship_receiver_id)AS receiverName,getRegion(s.ship_from_city_code) AS fromAdd,getRegion(s.ship_to_city_code) AS toAdd,");
        select.append("s.ship_volume,s.ship_amount,s.ship_weight,getProductName(s.ship_id) productName,s.ship_goodspay_fee");
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM kd_ship s ");
        sql.append(" LEFT JOIN kd_ship_fee f ON f.ship_id=s.ship_id and f.fee_type=6 ");

        StringBuilder parm = new StringBuilder();

        String netWorkIds = user.toNetWorkIdsStr();
        if (StringUtils.isNotBlank(search.getNetworkId())) {
            netWorkIds = search.getNetworkId();
        }

        parm.append(" where s.ship_deleted=0 and s.network_id in(" + netWorkIds + ") and s.ship_goodspay_fee>0");
        if (paginator.length == 0 && flag) {
            parm.append(" and f.id is null ");
        }

        if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
            parm.append(" and DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') BETWEEN '" + search.getStartTime() + "' AND '" + search.getEndTime() + "'");
        }
        if (StringUtils.isNotBlank(search.getShipSn())) {
            parm.append(" and s.ship_sn like '%" + search.getShipSn() + "%'");
        }
        if (StringUtils.isNotBlank(search.getSenderId())) {
            parm.append(" and s.ship_sender_id=" + search.getSenderId());
        }
        if (StringUtils.isNotBlank(search.getReceiverId())) {
            parm.append(" and s.ship_receiver_id=" + search.getReceiverId());
        }
        if (StringUtils.isNotBlank(search.getFormCode())) {
            parm.append(" and s.ship_from_city_code like '" + search.getFormCode() + "%'");
        }
        if (StringUtils.isNotBlank(search.getToCode())) {
            parm.append(" and s.ship_to_city_code like '" + search.getToCode() + "%'");
        }
        if (!"0".equals(search.getFeeStatus()) && StringUtils.isNotBlank(search.getFeeStatus())) {
            if ("1".equals(search.getFeeStatus())) {
                parm.append(" and f.id is null");
            } else {
                parm.append(" and f.id is not null");
            }
        }

        parm.append(" order by s.create_time desc");

        sql.append(parm.toString());

        if (paginator.length > 0) {
            Page<Record> page = Db.paginate(paginator[0].getPageNo(), paginator[0].getPageSize(), select.toString(), sql.toString());
            return page;
        } else {
            select.append(sql.toString());
            List<Record> list = Db.find(select.toString());
            return list;
        }
    }


    /**
     * 应收列表
     *
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip> queryReceivableList(Paginator paginator, SessionUser user, ReceivableSearchModel model) {

        String select = "SELECT s.ship_id, s.ship_sn,ship_fee_status,getNetworkNameById(s.network_id)AS netName,getCustomerName(s.ship_sender_id) as senderName,getCustomerName(s.ship_receiver_id) as receiverName,getRegion(s.ship_from_city_code) as fromAdd,getRegion(s.ship_to_city_code) as toAdd,s.ship_customer_number,s.create_time,s.ship_pay_way,s.ship_total_fee,getNetworkNameById(s.load_network_id) AS endWorkName,s.ship_agency_fund_status,s.network_id,s.load_network_id";
        StringBuilder bu = new StringBuilder(" from   kd_ship  s  WHERE 1=1 AND s.ship_deleted=0 AND s.network_id  in (" + user.toNetWorkIdsStr() + ")");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getNetworkId())) {
            bu.append(" and s.network_id=?");
            paras.add(model.getNetworkId());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
            bu.append("and s.create_time BETWEEN ? AND ?");
            paras.add(model.getStartTime());
            paras.add(model.getEndTime());
        }
        if (StringUtils.isNotBlank(model.getFeeStatus())) {
            bu.append(" and s.ship_fee_status =?");
            paras.add(model.getFeeStatus());

        }
        if (StringUtils.isNotBlank(model.getShipSn())) {
            bu.append(" and s.ship_sn like ? ");
            paras.add("%" + model.getShipSn() + "%");

        }
        if (StringUtils.isNotBlank(model.getSenderId())) {
            bu.append(" and s.ship_sender_id= ?");
            paras.add(model.getSenderId());

        }
        if (StringUtils.isNotBlank(model.getReceiverId())) {
            bu.append(" and s.ship_receiver_id=?");
            paras.add(model.getReceiverId());

        }
        if (StringUtils.isNotBlank(model.getFormCode())) {
            bu.append(" and s.ship_from_city_code like ? ");
            paras.add(StrUtils.getRealRegionCode(model.getFormCode()) + "%");
        }
        if (StringUtils.isNotBlank(model.getToCode())) {
            bu.append(" and s.ship_to_city_code like ?");
            paras.add(StrUtils.getRealRegionCode(model.getToCode()) + "%");
        }
        if (StringUtils.isNotBlank(model.getCustomerNumber())) {
            bu.append(" and s.ship_customer_number like ?");
            paras.add("%" + model.getCustomerNumber() + "%");

        }
        bu.append(" order by s.create_time desc");
        return KdShip.dao.paginate(paginator, select, bu.toString(), paras.toArray());
    }

    /**
     * 现付结算
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
    public boolean confirmNowPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] ids = shipIds.split(",");
                List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();

                List<Record> ships = Db.find("SELECT ship_nowpay_fee,ship_id,network_id   FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
                LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();
                for (Record record:ships) {
                    Integer key = record.getInt("network_id");
                    networkIds.add(key);
                    if (shipFeeMap.containsKey(key)){
                        double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_nowpay_fee");
                        shipFeeMap.put(key ,ship_fee);
                    }else {
                        shipFeeMap.put(key ,record.getDouble("ship_nowpay_fee"));
                        networkShipIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
                    flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
                    flow.set("fee_type", 1).set("id_type", 1).set("settlement_type", 1).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_nowpay_fee"));
                    kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

                for(String shipId:ids){
                    KdShip KdShip = new KdShip().findById(shipId);

                    KdShipFee shipFee = new KdShipFee();
                    shipFee.set("ship_id", shipId).set("resource_id", shipId).set("resource_type", 1).set("fee_type", 1).set("fee", KdShip.getDouble("ship_nowpay_fee")).set("network_id", KdShip.get("load_network_id"));
                    shipFee.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList.add(shipFee);

                }

                int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
                if (shipFeeSave.length != shipFeeList.size()) return false;


                return true;
            }
        });
        return tx;
    }

    /**
     * 提付结算
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
    public boolean confirmPickupPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] ids = shipIds.split(",");
                List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();
                List<KdShipFee> shipFeeList2 = new ArrayList<KdShipFee>();

                List<Record> ships = Db.find("SELECT s.ship_pickuppay_fee,s.ship_id,s.load_network_id, IFNULL(sa.minus_fee,0.0) minus_fee, IFNULL(sa.plus_fee,0.0) plus_fee FROM kd_ship s LEFT JOIN kd_ship_abnormal sa ON sa.ship_id = s.ship_id WHERE s.ship_id IN  ("+shipIds+") ");
                LinkedHashMap<Integer,Double> shipFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();

                for (Record record:ships) {
                    Integer key = record.getInt("load_network_id");
                    networkIds.add(key);
                    if (shipFeeMap.containsKey(key)){
                        double total_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_pickuppay_fee")+record.getDouble("plus_fee")-record.getDouble("minus_fee");
                        shipFeeMap.put(key ,total_fee);
                    }else {
                        shipFeeMap.put(key ,record.getDouble("ship_pickuppay_fee")+record.getDouble("plus_fee")-record.getDouble("minus_fee"));
                        networkShipIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    int inout_type=0;
                    if(shipFeeMap.get(id)<0) inout_type=1;
                    flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
                    flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
                    flow.set("fee_type", 2).set("id_type", 1).set("settlement_type", 1).set("inout_type", inout_type).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.getDouble("ship_pickuppay_fee")+record.getDouble("plus_fee")-record.getDouble("minus_fee"));
                    kdFlowDetail.set("network_id",record.get("load_network_id")).set("flow_id",kdFlowIdMap.get(record.get("load_network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

                for(String shipId:ids){
                    KdShip KdShip = new KdShip().findById(shipId);

                    KdShipFee shipFee = new KdShipFee();
                    shipFee.set("ship_id", shipId).set("resource_id", shipId).set("resource_type", 1).set("fee_type", 2).set("fee", KdShip.getDouble("ship_pickuppay_fee")).set("network_id", KdShip.get("load_network_id"));
                    shipFee.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList.add(shipFee);

                    KdShipFee shipFee2 = new KdShipFee();
                    shipFee2.set("ship_id", shipId).set("resource_id", shipId).set("resource_type", 1).set("fee_type", 21).set("fee", KdShip.getDouble("ship_pickuppay_fee")).set("network_id", KdShip.get("load_network_id"));
                    shipFee2.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList2.add(shipFee2);

                }

                int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
                int[] shipFeeSave2 = Db.batchSave(shipFeeList2, shipFeeList2.size());
                if (shipFeeSave.length != shipFeeList.size()) return false;
                if (shipFeeSave2.length != shipFeeList.size()) return false;
                return true;
            }
        });
        return tx;
    }

    /**
     * 回单付结算
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
    public boolean confirmReceiptPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] ids = shipIds.split(",");
                List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();

                List<Record> ships = Db.find("SELECT ship_receiptpay_fee,ship_id,network_id   FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
                LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();
                for (Record record:ships) {
                    Integer key = record.getInt("network_id");
                    networkIds.add(key);
                    if (shipFeeMap.containsKey(key)){
                        double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_receiptpay_fee");
                        shipFeeMap.put(key ,ship_fee);
                    }else {
                        shipFeeMap.put(key ,record.getDouble("ship_receiptpay_fee"));
                        networkShipIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
                    flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
                    flow.set("fee_type", 3).set("settlement_type", 1).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
                    flow.set("resource_id",networkShipIdMap.get(id)).set("id_type",1);
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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_receiptpay_fee"));
                    kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

                for (String id : ids) {
                    KdShip kdShip = new KdShip().findById(id);

                    KdShipFee shipFee = new KdShipFee();
                    shipFee.set("ship_id", id).set("resource_id", id).set("resource_type", 1).set("fee_type", 3).set("fee",kdShip.get("ship_receiptpay_fee")).set("network_id",kdShip.get("load_network_id"));
                    shipFee.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList.add(shipFee);
                }
                int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
                if (shipFeeSave.length != shipFeeList.size()) return false;
                return true;
            }
        });
        return tx;
    }

    /**
     * 月结结算
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
    public boolean confirmMonthPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] ids = shipIds.split(",");
                List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();

                List<Record> ships = Db.find("SELECT ship_monthpay_fee,ship_id,network_id   FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
                LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();
                for (Record record:ships) {
                    Integer key = record.getInt("network_id");
                    networkIds.add(key);
                    if (shipFeeMap.containsKey(key)){
                        double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_monthpay_fee");
                        shipFeeMap.put(key ,ship_fee);
                    }else {
                        shipFeeMap.put(key ,record.getDouble("ship_monthpay_fee"));
                        networkShipIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
                    flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
                    flow.set("fee_type", 4).set("settlement_type", 1).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
                    flow.set("resource_id",networkShipIdMap.get(id)).set("id_type",1);

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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_monthpay_fee"));
                    kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

                for (String id : ids) {
                    Double ship_monthpay_fee = new KdShip().findFirst("SELECT ifnull(ship_monthpay_fee,0) as ship_monthpay_fee from kd_ship where ship_id=?", id).getDouble("ship_monthpay_fee");
                    int load_network_id = new KdShip().findFirst("SELECT load_network_id from kd_ship where ship_id=?", id).getInt("load_network_id");

                    KdShipFee shipFee = new KdShipFee();
                    shipFee.set("ship_id", id).set("resource_id", id).set("resource_type", 1).set("fee_type", 4).set("fee", ship_monthpay_fee).set("network_id", load_network_id);
                    shipFee.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList.add(shipFee);
                }
                int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
                if (shipFeeSave.length != shipFeeList.size()) return false;
                return true;
            }
        });
        return tx;
    }

    /**
     * 短欠付结算
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
    public boolean confirmArrearsPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] ids = shipIds.split(",");
                List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();

                List<Record> ships = Db.find("SELECT ship_arrearspay_fee,ship_id,network_id   FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
                LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();
                for (Record record:ships) {
                    Integer key = record.getInt("network_id");
                    networkIds.add(key);
                    if (shipFeeMap.containsKey(key)){
                        double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_arrearspay_fee");
                        shipFeeMap.put(key ,ship_fee);
                    }else {
                        shipFeeMap.put(key ,record.getDouble("ship_arrearspay_fee"));
                        networkShipIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
                    flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
                    flow.set("fee_type", 5).set("id_type", 1).set("settlement_type", 1).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_arrearspay_fee"));
                    kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

                for (String id : ids) {
                    int load_network_id = new KdShip().findFirst("SELECT load_network_id from kd_ship where ship_id=?", id).getInt("load_network_id");
                    Double ship_arrearspay_fee = new KdShip().findFirst("SELECT ifnull(ship_arrearspay_fee,0) as ship_arrearspay_fee from kd_ship where ship_id=?", id).getDouble("ship_arrearspay_fee");

                    KdShipFee shipFee = new KdShipFee();
                    shipFee.set("ship_id", id).set("resource_id", id).set("resource_type", 1).set("fee_type", 5).set("fee", ship_arrearspay_fee).set("network_id", load_network_id);
                    shipFee.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList.add(shipFee);
                }
                int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
                if (shipFeeSave.length != shipFeeList.size()) return false;
                return true;
            }
        });
        return tx;
    }

    /**
     * 贷款扣结算
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
    public boolean confirmGoodsPay(String shipIds, String payType, String flowNo, String voucherNo, String remark, String time, SessionUser user) {

        boolean tx = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] ids = shipIds.split(",");
                List<KdShipFee> shipFeeList = new ArrayList<KdShipFee>();

                List<Record> ships = Db.find("SELECT ship_goodspay_fee,ship_id,network_id   FROM kd_ship WHERE ship_id IN  ("+shipIds+") ");
                LinkedHashMap shipFeeMap = new LinkedHashMap<Integer,Double>();
                LinkedHashMap networkShipIdMap = new LinkedHashMap<Integer,Integer>();
                List<KdFlow> kdFlows = new ArrayList<KdFlow>() ;
                Set<Integer> networkIds = new HashSet<>();
                for (Record record:ships) {
                    Integer key = record.getInt("network_id");
                    networkIds.add(key);
                    if (shipFeeMap.containsKey(key)){
                        double ship_fee = (double)shipFeeMap.get(key)+record.getDouble("ship_goodspay_fee");
                        shipFeeMap.put(key ,ship_fee);
                    }else {
                        shipFeeMap.put(key ,record.getDouble("ship_goodspay_fee"));
                        networkShipIdMap.put(key,record.getInt("ship_id"));
                    }
                }

                for (Integer id:networkIds) {
                    KdFlow flow = new KdFlow();
                    flow.set("fee", shipFeeMap.get(id)).set("pay_type", payType).set("flow_no", flowNo).set("voucher_no", voucherNo).set("remark", remark);
                    flow.set("network_id",id).set("user_id", user.getUserId()).set("create_time", time).set("company_id", user.getCompanyId());
                    flow.set("fee_type", 6).set("id_type", 1).set("settlement_type", 1).set("inout_type", 0).set("flow_sn", new FlowSvc().getFlowSnByComId(user.getCompanyId()));
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
                    kdFlowDetail.set("ship_id",record.get("ship_id")).set("fee",record.get("ship_goodspay_fee"));
                    kdFlowDetail.set("network_id",record.get("network_id")).set("flow_id",kdFlowIdMap.get(record.get("network_id"))).set("create_time",time).set("company_id",user.getCompanyId());
                    kdFlowDetailList.add(kdFlowDetail);
                }
                int[] kdFlowDetailSave = Db.batchSave(kdFlowDetailList, kdFlowDetailList.size());
                if(kdFlowDetailSave.length!=kdFlowDetailList.size())return false;

                for (String id : ids) {
                    int load_network_id = new KdShip().findFirst("SELECT load_network_id from kd_ship where ship_id=?", id).getInt("load_network_id");
                    Double ship_goodspay_fee = new KdShip().findFirst("SELECT ifnull(ship_goodspay_fee,0) as ship_goodspay_fee from kd_ship where ship_id=?", id).getDouble("ship_goodspay_fee");

                    KdShipFee shipFee = new KdShipFee();
                    shipFee.set("ship_id", id).set("resource_id", id).set("resource_type", 1).set("fee_type", 6).set("fee", ship_goodspay_fee).set("network_id", load_network_id);
                    shipFee.set("company_id", user.getCompanyId()).set("create_time", time);
                    shipFeeList.add(shipFee);
                }
                int[] shipFeeSave = Db.batchSave(shipFeeList, shipFeeList.size());
                if (shipFeeSave.length != shipFeeList.size()) return false;
                return true;
            }
        });
        return tx;
    }

    /**
     * excel List
     * @param list
     * @return
     */
    public List<Record> changeExcelList(List<Record> list){
        int i=1;
        for (Record record:list) {
            record.set("orderNum",i);
            if(record.getInt("ship_status")!=0){
                switch (record.getInt("ship_status")){
                    case 1:
                        record.set("shipStatus","已入库");
                        break;
                    case 2:
                        record.set("shipStatus","短驳中");
                        break;
                    case 3:
                        record.set("shipStatus","短驳到达");
                        break;
                    case 4:
                        record.set("shipStatus","已发车");
                        break;
                    case 5:
                        record.set("shipStatus","已到达");
                        break;
                    case 6:
                        record.set("shipStatus","收货中转中");
                        break;
                    case 7:
                        record.set("shipStatus","到货中转中");
                        break;
                    case 8:
                        record.set("shipStatus","送货中");
                        break;
                    case 9:
                        record.set("shipStatus","已签收");
                        break;
                    default:
                        break;
                }
            }
            if(StringUtils.isNotBlank(record.get("feeState").toString())){
                if("1".equals(record.get("feeState").toString())){
                    record.set("state","已结算");
                }else{
                    record.set("state","未结算");
                }

            }
            i++;
        }
        return list;
    }

}
