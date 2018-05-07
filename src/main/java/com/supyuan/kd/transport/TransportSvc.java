package com.supyuan.kd.transport;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.common.ShipStatus;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.loading.LoadListSearchModel;
import com.supyuan.kd.loading.LoadingSvc;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.network.NetWorkSvc;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.StrUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 到货确认服务类
 *
 * @author dingfei
 * @date 2018-02-07 17:39
 **/
public class TransportSvc extends BaseService {
    private final static Log log = Log.getLog(TransportSvc.class);
    DecimalFormat df   = new DecimalFormat("#.00");

    /**
     *  到货确认配载单
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdTrunkLoad> queryConfirmTrunkLoadList(Paginator paginator, SessionUser user, LoadListSearchModel model){

        StringBuilder select = new StringBuilder("SELECT tl.load_id, tl.load_sn,getNetworkNameById(tl.network_id) AS netWorkName,tl.load_depart_time,t.truck_id_number,");
        select.append(" t.truck_driver_name,t.truck_driver_mobile,tl.load_transport_type,getRegion(tl.load_delivery_from) AS fromAdd,getRegion(tl.load_delivery_to) AS toAdd,tl.load_fee, tl.load_count,tl.load_volume,tl.load_weight,");
        select.append("  tl.load_amount,getNetworkNameById(tl.load_next_network_id) AS endNetWorkName,tl.load_delivery_status,tl.load_arrival_time ");
        StringBuilder sql = new StringBuilder();
        StringBuilder parm = new StringBuilder();
        sql.append("  FROM kd_truck_load tl ");
        sql.append(" LEFT JOIN truck t ON t.truck_id=tl.truck_id");
        sql.append(" where 1=1  and tl.load_delivery_status>2  and tl.load_deleted=0 and tl.load_next_network_id in("+user.toNetWorkIdsStr()+") ");
        if (model.getNextNetworkId()>0) {
            parm.append(" and tl. load_next_network_id="+model.getNextNetworkId());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
            parm.append(" and to_days(tl. load_depart_time) BETWEEN to_days('"+model.getStartTime()+" ') AND to_days(' "+model.getEndTime()+" ') ");
        }
        if (StringUtils.isNotBlank(model.getDriverName())) {
            parm.append(" and t. truck_driver_name like ('%" + model.getDriverName() + "%')");
        }
        if (StringUtils.isNotBlank(model.getTruckNumber())) {

            parm.append(" and t. truck_id_number like ('%" + model.getTruckNumber() + "%')");
        }
        if (StringUtils.isNotBlank(model.getLoadSn())) {

            parm.append(" and tl. load_sn like ('%" + model.getLoadSn() + "%')");
        }
        if (model.getNetworkId()>0) {

            parm.append(" and tl. network_id="+model.getNetworkId());
        }
        if (StringUtils.isNotBlank(model.getDeliveryFrom())) {

            parm.append(" and tl. load_delivery_from like '" + StrUtils.getRealRegionCode(model.getDriverName().trim()) + "%'");
        }
        if (StringUtils.isNotBlank(model.getDeliveryTo())) {

            parm.append(" and tl. load_delivery_to like '" + StrUtils.getRealRegionCode(model.getDeliveryTo().trim()) + "%'");
        }
        if(StringUtils.isNotBlank(model.getStatus())){
            parm.append("  and tl.load_delivery_status='"+model.getStatus()+"'");
        }
        sql.append(parm.toString());
        sql.append(" order by tl.load_arrival_time desc ");
        return KdTrunkLoad.dao.paginate(paginator, select.toString(), sql.toString());
    }

    /**
     *  即将到货配载单
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdTrunkLoad> querySoonTrunkLoadList(Paginator paginator,SessionUser user,LoadListSearchModel model){
        StringBuilder select = new StringBuilder("SELECT tl.load_id, tl.load_sn,getNetworkNameById(tl.network_id) AS netWorkName,getNetworkNameById(tl.load_next_network_id) AS endWorkName,tl.load_transport_type,tl.load_fee_prepay,tl.load_depart_time,t.truck_id_number,");
        select.append(" getRegion(tl.load_delivery_from)AS fromAdd,getRegion(tl.load_delivery_to) AS toAdd,t.truck_driver_name,t.truck_driver_mobile,tl.load_volume,tl.load_weight,tl.load_amount,tl.load_count  ");
        StringBuilder sql = new StringBuilder();
        StringBuilder parm = new StringBuilder();
        sql.append("  FROM kd_truck_load tl  ");
        sql.append(" LEFT JOIN truck t ON t.truck_id=tl.truck_id  ");
        sql.append(" where 1=1 and tl.load_delivery_status<3 and tl.load_next_network_id<>0 ");
        sql.append(" and tl. load_deleted=0  and tl.load_next_network_id in("+user.toNetWorkIdsStr()+")");

        if (model.getNextNetworkId()>0) {
            parm.append(" and tl. load_next_network_id="+model.getNextNetworkId());
        }
        if (StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())) {
            parm.append(" and to_days(tl. load_depart_time) BETWEEN to_days('"+model.getStartTime()+" ') AND to_days(' "+model.getEndTime()+" ') ");
        }
        if (StringUtils.isNotBlank(model.getDriverName())) {
            parm.append(" and t. truck_driver_name like ('%" + model.getDriverName() + "%')");
        }
        if (StringUtils.isNotBlank(model.getTruckNumber())) {

            parm.append(" and t. truck_id_number like ('%" + model.getTruckNumber() + "%')");
        }
        if (StringUtils.isNotBlank(model.getLoadSn())) {

            parm.append(" and tl. load_sn like ('%" + model.getLoadSn() + "%')");
        }
        if (model.getNetworkId()>0) {

            parm.append(" and tl. network_id="+model.getNetworkId());
        }
        if (StringUtils.isNotBlank(model.getDeliveryFrom())) {

            parm.append(" and tl. load_delivery_from like '" + StrUtils.getRealRegionCode(model.getDriverName().trim()) + "%'");
        }
        if (StringUtils.isNotBlank(model.getDeliveryTo())) {

            parm.append(" and tl. load_delivery_to like '" + StrUtils.getRealRegionCode(model.getDeliveryTo().trim()) + "%'");
        }
        if(StringUtils.isNotBlank(model.getStatus())){
            parm.append("  and tl.load_delivery_status='"+model.getStatus()+"'");
        }
        sql.append(parm.toString());
        sql.append(" order by tl.load_depart_time desc");
        //sql.append(" group by load_id order by tl.load_depart_time desc");
        return KdTrunkLoad.dao.paginate(paginator, select.toString(), sql.toString());
    }

    /**
     * 确认到达
     * @param loadId
     * @param loadType
     * @param user
     * @return
     */
    public  boolean saveTruckLoad(int loadId,int loadType,SessionUser user){
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                Date time = new Date();
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getTime());
                int shipState=0;
                List<KdShipTrack> tracks  = new ArrayList<KdShipTrack>();
                List<KdInOut> inOuts  = new ArrayList<KdInOut>();
                //更新配载单信息
                if(!(Db.update(" update kd_truck_load  set load_delivery_status=3,load_arrival_time=? where load_id="+loadId,timeStamp)>0)) return false;
                //查询配载单下运单信息
                List<KdShip> ships=new KdShip().find("SELECT ship_id FROM kd_truck_ship WHERE truck_load_id=?",loadId);
                if (loadType==2){
                    shipState = ShipStatus.DUANBOED.type;
                }else{
                    shipState = ShipStatus.ARRIVIED.type;
                }
                for (KdShip kdShip:ships){
                   if(!(Db.update(" update kd_ship  set ship_storage=0,ship_status="+shipState+" where ship_id="+kdShip.get("ship_id"))>0)) return false;
                    KdShip findShortShip = new KdShipSvc().findShortShip(kdShip.get("ship_id").toString());
                    if(findShortShip.getInt("ship_status")==2) {//在运输中的才加出入库（可能会被提前签收）
                        //出入库记录  add by  liangxiaoping
                        KdInOut inout = new KdInOut();
                        inout.set("ship_id", kdShip.get("ship_id"));
                        inout.set("network_id", findShortShip.get("load_network_id"));
                        inout.set("in_time", time);
                        inOuts.add(inout);
                    }
                    NetWork netWorkd = new NetWorkSvc().getNetWork(findShortShip.getInt("load_network_id")+"", user);
                    KdShipTrack kdShipTrack=new KdShipTrack();
                    kdShipTrack.set("track_ship_id", kdShip.get("ship_id"));
                    kdShipTrack.set("user_id", user.getUserId());
                    kdShipTrack.set("track_resource_id", loadId);
                    kdShipTrack.set("track_company_id", user.getCompanyId());
                    kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_CONFIRM_NUM);
                    kdShipTrack.set("track_networkid",findShortShip.get("load_network_id"));
                    kdShipTrack.set("track_desc", "到达" + netWorkd.get("sub_network_name"));
                    kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_CONFIRM);
                    kdShipTrack.set("create_time", time);
                    tracks.add(kdShipTrack);
                }
                //加入出入库记录 add  by liangxiaoping
                int[] inOutsSave = Db.batchSave(inOuts, inOuts.size());
                if(inOutsSave.length!=inOuts.size())return false;

                //到货确认日志
                int[] trackSave = Db.batchSave(tracks, tracks.size());
                if(trackSave.length!=tracks.size())return false;
                return true;
            }
        });
    }

    /**
     * 完成卸车确定
     * @param loadId
     * @param load_atunload_fee
     * @param load_atother_fee
     * @return
     */
    public boolean updateUnloading(int loadId, int loadType,double load_atunload_fee,double load_atother_fee,SessionUser user ){
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                Date time = new Date();
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getTime());
                List<KdShipTrack> tracks  = new ArrayList<KdShipTrack>();
                if(loadType==3) {
                    if (!(Db.update(" update kd_truck_load  set load_delivery_status=4,load_is_unload=1,load_atunload_fee=?,load_atother_fee=?, load_finish_time=? where load_id=" + loadId, load_atunload_fee, load_atother_fee, timeStamp) > 0)) return false;
                     } else{
                       if(!(Db.update(" update kd_truck_load  set load_delivery_status=4,load_is_unload=1,load_finish_time=? where load_id="+loadId,timeStamp)>0)) return false;
                 }
                //查询配载单下运单信息
                List<KdShip> ships=new KdShip().find("SELECT ship_id FROM kd_truck_ship WHERE truck_load_id=?",loadId);
                for (KdShip kdShip:ships){
                    KdShipTrack kdShipTrack=new KdShipTrack();
                    kdShipTrack.set("track_ship_id", kdShip.get("ship_id"));
                    kdShipTrack.set("user_id", user.getUserId());
                    kdShipTrack.set("track_resource_id", loadId);
                    kdShipTrack.set("track_company_id", user.getCompanyId());
                    kdShipTrack.set("track_networkid",kdShip.get("load_network_id"));
                    kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_LOAD_UNLOAD_NUM);
                    kdShipTrack.set("track_desc", KdShipTrack.MODEL_SHIP_LOAD_UNLOAD );
                    kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_LOAD_UNLOAD);
                    kdShipTrack.set("create_time", time);
                    tracks.add(kdShipTrack);
                }
                //到货确认日志
                int[] trackSave = Db.batchSave(tracks, tracks.size());
                if(trackSave.length!=tracks.size())return false;
                return true;
            }
        });

    }


}
