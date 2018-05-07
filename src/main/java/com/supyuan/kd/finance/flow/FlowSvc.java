package com.supyuan.kd.finance.flow;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlowSvc {
    /**
     * 查询收支流水
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdFlow> queryFlowList(Paginator paginator, SessionUser user, FlowSearchModel model) {
        StringBuilder select = new StringBuilder( "SELECT id,resource_id,flow_sn,settlement_type,inout_type,fee,fee_type,pay_type,flow_no,voucher_no, remark,getPCUserName(user_id) AS userName,getNetworkNameById(network_id) AS networdName,create_time");
        StringBuilder bu=new StringBuilder(" FROM  kd_flow " );
        bu.append(" WHERE 1=1  ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getNetworkId())){
            bu.append(" and network_id=?");
            paras.add(model.getNetworkId());
        }else{
            bu.append("  AND network_id  in (" + user.toNetWorkIdsStr()+") ");
        }
        if(StringUtils.isNotBlank(model.getSettlementType())){
            bu.append(" and settlement_type=?");
            paras.add(model.getSettlementType());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime()))
        {
            bu.append("and create_time BETWEEN ? AND ?");
            paras.add(model.getStartTime());
            paras.add(model.getEndTime());
        }
        if (StringUtils.isNotBlank(model.getInoutType())){
            bu.append(" and inout_type =?");
            paras.add(model.getInoutType());

        }
        if (StringUtils.isNotBlank(model.getPayType())){
            bu.append(" and pay_type= ?");
            paras.add(model.getPayType());

        }
        if(StringUtils.isNotBlank(model.getFeeType())){
            bu.append(" and fee_type=?");
            paras.add(model.getFeeType());

        }
        if(StringUtils.isNotBlank(model.getFlowSn())){
            bu.append(" and  flow_sn like ?");
            paras.add(model.getFlowSn());
        }

        bu.append("order by  create_time desc ");
        return KdFlow.dao.paginate(paginator,select.toString(),bu.toString(),paras.toArray());

    }
    /**
     * 查询收支明细
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdFlow> queryFlowListDetail(Paginator paginator, SessionUser user, FlowSearchModel model) {
        StringBuilder select = new StringBuilder( "SELECT f.id,f.resource_id,f.flow_sn,f.id_type, f.settlement_type, f.inout_type, fd.fee, f.fee_type,f.pay_type,f.flow_no,f.voucher_no, f.remark,getPCUserName(f.user_id) AS userName,getNetworkNameById(fd.network_id) AS networdName,f.create_time, s.ship_sn,getNetworkNameById(s.network_id) AS shipNetWordName,t.load_sn,getNetworkNameById(t.network_id) AS loadNetWordName");
        StringBuilder bu=new StringBuilder("  FROM kd_flow_detail fd LEFT JOIN kd_flow f ON fd.flow_id = f.id LEFT JOIN kd_ship s ON fd.ship_id = s.ship_id LEFT JOIN kd_truck_load t ON fd.load_id = t.load_id" );
        bu.append(" WHERE 1=1 AND f.network_id  in (" + user.toNetWorkIdsStr()+") ");
        List<Object> paras = new ArrayList<Object>();
        if (StringUtils.isNotBlank(model.getNetworkId())){
            bu.append(" and f.network_id=?");
            paras.add(model.getNetworkId());
        }
        if(StringUtils.isNotBlank(model.getSettlementType())){
            bu.append(" and f.settlement_type=?");
            paras.add(model.getSettlementType());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime()))
        {
            bu.append("and fd.create_time BETWEEN ? AND ?");
            paras.add(model.getStartTime());
            paras.add(model.getEndTime());
        }
        if (StringUtils.isNotBlank(model.getInoutType())){
            bu.append(" and f.inout_type =?");
            paras.add(model.getInoutType());

        }
        if (StringUtils.isNotBlank(model.getPayType())){
            bu.append(" and f.pay_type= ?");
            paras.add(model.getPayType());

        }
        if(StringUtils.isNotBlank(model.getFeeType())){
            bu.append(" and f.fee_type=?");
            paras.add(model.getFeeType());

        }
        if(StringUtils.isNotBlank(model.getFlowSn())){
            bu.append(" and  f.flow_sn like ?");
            paras.add(model.getFlowSn());
        }
        if(StringUtils.isNotBlank(model.getShipSn())){
            bu.append(" and  s.ship_sn like ?");
            paras.add(model.getShipSn());
        }
        if(StringUtils.isNotBlank(model.getLoadSn())){
            bu.append(" and  t.load_sn like ?");
            paras.add(model.getLoadSn());
        }

        bu.append("order by  fd.create_time desc ");
        return KdFlow.dao.paginate(paginator,select.toString(),bu.toString(),paras.toArray());

    }

    /**
     * 根据公司id获取当天流水号
     * @param companyId
     * @return
     */
    public String getFlowSnByComId(int companyId){
        Record record = Db.findFirst("SELECT count(1) countId from kd_flow where company_id=? and  DATE_FORMAT(create_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')",companyId);
        long id=record.getLong("countId")==0?1:record.getLong("countId")+1;
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String ymd = simpleDateFormat.format(date);
        String maxId=id+"";
        switch (maxId.length()) {
            case 1:maxId="JS"+ymd+"000"+maxId;
                break;
            case 2:maxId="JS"+ymd+"00"+maxId;
                break;
            case 3:maxId="JS"+ymd+"0"+maxId;
                break;
            default:maxId="JS"+ymd+maxId;
                break;
        }
        return maxId;
    }

    /**
     * 根据公司id获取当天流水排序号
     * @param companyId
     * @return
     */
    public long getFlowSnOrderNumByComId(int companyId){
        Record record = Db.findFirst("SELECT count(1) countId from kd_flow where company_id=? and  DATE_FORMAT(create_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')",companyId);
        long id=record.getLong("countId")==0?1:record.getLong("countId")+1;
        return id;
    }

    /**
     * 根据序号获取当天流水号
     * @param num
     * @return
     */
    public String getFlowSnByOrderNum(long num){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String ymd = simpleDateFormat.format(date);
        String maxNUM=num+"";
        switch (maxNUM.length()) {
            case 1:maxNUM="JS"+ymd+"000"+maxNUM;
                break;
            case 2:maxNUM="JS"+ymd+"00"+maxNUM;
                break;
            case 3:maxNUM="JS"+ymd+"0"+maxNUM;
                break;
            default:maxNUM="JS"+ymd+maxNUM;
                break;
        }
        return maxNUM;
    }

}
