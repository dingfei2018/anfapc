package com.supyuan.kd.receipt;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.sign.KdShipSign;
import com.supyuan.kd.sign.SignModel;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;

import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 回单服务类
 *
 * @author dingfei
 * @create 2018-01-17 14:00
 **/
public class ReceiptSvc extends BaseService {

    private final static Log log = Log.getLog(ReceiptSvc.class);

    /**
     * 我的回单列表
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip>  queryMyReceiptList(Paginator paginator, SessionUser user, ReceiptListSearchModel model){
        StringBuilder select=new StringBuilder("select  s.ship_id, s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName, s.create_time, getRegion(s.ship_from_city_code)AS fromAdd,  getRegion(s.ship_to_city_code) AS toAdd,");
        select.append(" getCustomerName(s.ship_sender_id) AS senderName, getCustomerName(s.ship_receiver_id) AS receiverName ,  s.ship_receipt_status,s.load_network_id, getNetworkNameById(s.load_network_id) AS enetWorkName,ifnull(r.receipt_pre_post_no,'')as receipt_pre_post_no ,ifnull(r.receipt_send_post_no,'') as receipt_send_post_no ,sg.sign_image_gid");
        StringBuilder bu=new StringBuilder(" from  kd_ship s LEFT JOIN kd_receipt r  on  s.ship_id=r.ship_id  left join kd_ship_sign sg   on sg.ship_id=s.ship_id   where 1=1 and s.ship_deleted=0 and s.ship_status=4 and s.network_id  in (" + user.toNetWorkIdsStr()+")  ");
        List<Object> paras = new ArrayList<Object>();
        if (model.getSnetWorkId()>0){
            bu.append(" and s.network_id=?"); //开单网点
            paras.add(model.getSnetWorkId());
        }
        if (model.getEnetWorkId()>0){
            bu.append(" and r.network_id=?");//回单网点
            paras.add(model.getEnetWorkId());
        }
        if (model.getSenderId()>0){
            bu.append(" and s.ship_sender_id=?");//托运方
            paras.add(model.getSenderId());
        }
        if (model.getReceiveId()>0){
            bu.append(" and s.ship_receiver_id=?");//收货方
            paras.add(model.getReceiveId());
        }
        if (StringUtils.isNotBlank(model.getStatus())){
            bu.append(" and s.ship_receipt_status=?");//回单状态
            paras.add(model.getStatus());
        }
        if (StringUtils.isNotBlank(model.getPrePostNo())){
            bu.append(" and r.receipt_pre_post_no like ?");//代收邮寄单号
            paras.add("%"+model.getPrePostNo()+"%");
        }
        if (StringUtils.isNotBlank(model.getSendPostNo())){
            bu.append(" and r.receipt_send_post_no like ?");//发放邮寄单号
            paras.add("%"+model.getSendPostNo()+"%");
        }
        if (StringUtils.isNotBlank(model.getShipSn())){
            bu.append(" and s.ship_sn like ?");//运单号
            paras.add("%"+model.getShipSn()+"%");
        }
        bu.append("  order by  sg.sign_time desc ");
        return KdShip.dao.paginate(paginator,select.toString(),bu.toString(),paras.toArray());
    }

    /**
     * 回单接收
     * @param ids
     * @param loadworkId
     * @return
     */
    public boolean receiveReceipt(String ids,String loadworkId,SessionUser user){
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                //更新运单状态
                if(!(Db.update("update kd_ship set ship_receipt_status=3 WHERE ship_id in("+ids+")")>=0)) return false;
                //保存回单信息表
                String [] lids=ids.split(",");
                List<KdReceipt> list=new ArrayList<>();
                Date time = new Date();
                for (String id :lids){
                    KdReceipt kdReceipt=new KdReceipt();
                    kdReceipt.set("ship_id",id);
                    kdReceipt.set("network_id",loadworkId);
                    kdReceipt.set("create_time",time);
                    list.add(kdReceipt);
                    //日志信息
                    KdShipTrack kdShipTrack=new KdShipTrack();
                    kdShipTrack.set("track_ship_id", id);
                    kdShipTrack.set("user_id", user.getUserId());
                    kdShipTrack.set("track_resource_id", id);
                    kdShipTrack.set("track_company_id", user.getCompanyId());
                    kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_HD_NUM);
                    kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_HD);
                    kdShipTrack.set("track_networkid", loadworkId);
                    kdShipTrack.set("track_desc", "回单接收");
                    kdShipTrack.set("create_time", new Date());
                    if(!kdShipTrack.save()) return false;

                }
                int[] size = Db.batchUpdate(list,list.size());
                if(size.length!=list.size())return false;



                return true;
            }
        });
    }

    /**
     * 回单发放
     * @param ids
     * @param loadworkId
     * @param sendNo
     * @return
     */
    public boolean grantReceipt(String ids,String loadworkId,String sendNo,SessionUser user){
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                //更新运单状态
                if(!(Db.update("update kd_ship set ship_receipt_status=4 WHERE ship_id in("+ids+")")>=0)) return false;
                //保存回单信息表
                String [] lids=ids.split(",");
                List<KdReceipt> list=new ArrayList<>();
                Date time = new Date();
                for (String id :lids){
                    KdReceipt kdReceipt=new KdReceipt();
                    kdReceipt.set("ship_id",id);
                    kdReceipt.set("network_id",loadworkId);
                    kdReceipt.set("receipt_send_post_no",sendNo);
                    kdReceipt.set("create_time",time);
                    list.add(kdReceipt);
                    //日志信息
                    KdShipTrack kdShipTrack=new KdShipTrack();
                    kdShipTrack.set("track_ship_id", id);
                    kdShipTrack.set("user_id", user.getUserId());
                    kdShipTrack.set("track_resource_id", id);
                    kdShipTrack.set("track_company_id", user.getCompanyId());
                    kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_HD_NUM);
                    kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_HD);
                    kdShipTrack.set("track_networkid", loadworkId);
                    kdShipTrack.set("track_desc", "回单发放");
                    kdShipTrack.set("create_time", new Date());
                    if(!kdShipTrack.save()) return false;
                }
                int[] size = Db.batchUpdate(list,list.size());
                if(size.length!=list.size())return false;
                return true;
            }
        });
    }

    /**
     * 代收回单列表
     * @param paginator
     * @param user
     * @param model
     * @return
     */
    public Page<KdShip>  queryCollectionReceiptList(Paginator paginator, SessionUser user, ReceiptListSearchModel model){
        StringBuilder select=new StringBuilder("select  s.ship_id, s.ship_sn,getNetworkNameById(s.network_id) AS netWorkName, s.create_time, getRegion(s.ship_from_city_code)AS fromAdd,  getRegion(s.ship_to_city_code) AS toAdd,");
        select.append(" getCustomerName(s.ship_sender_id) AS senderName, getCustomerName(s.ship_receiver_id) AS receiverName ,  s.ship_receipt_status,s.load_network_id, getNetworkNameById(s.load_network_id) AS enetWorkName,ifnull(r.receipt_pre_post_no,'')as receipt_pre_post_no , ifnull(r.receipt_send_post_no,'') as receipt_send_post_no ,sg.sign_image_gid");
        StringBuilder bu=new StringBuilder(" from  kd_ship s LEFT JOIN kd_receipt r on  s.ship_id=r.ship_id  left join kd_ship_sign sg   on sg.ship_id=s.ship_id  where 1=1 and s.ship_deleted=0 and s.ship_status=4 and s.load_network_id  in (" + user.toNetWorkIdsStr()+")  ");
        List<Object> paras = new ArrayList<Object>();
        if (model.getSnetWorkId()>0){
            bu.append(" and s.network_id=?"); //开单网点
            paras.add(model.getSnetWorkId());
        }
        if (model.getEnetWorkId()>0){
            bu.append(" and r.network_id=?");//回单网点
            paras.add(model.getEnetWorkId());
        }
        if (model.getSenderId()>0){
            bu.append(" and s.ship_sender_id=?");//托运方
            paras.add(model.getSenderId());
        }
        if (model.getReceiveId()>0){
            bu.append(" and s.ship_receiver_id=?");//收货方
            paras.add(model.getReceiveId());
        }
        if (StringUtils.isNotBlank(model.getStatus())){
            bu.append(" and s.ship_receipt_status=?");//回单状态
            paras.add(model.getStatus());
        }
        if (StringUtils.isNotBlank(model.getPrePostNo())){
            bu.append(" and r.receipt_pre_post_no like ?");//代收邮寄单号
            paras.add("%"+model.getPrePostNo()+"%");
        }
        if (StringUtils.isNotBlank(model.getSendPostNo())){
            bu.append(" and r.receipt_send_post_no like ?");//发放邮寄单号
            paras.add("%"+model.getSendPostNo()+"%");
        }
        if (StringUtils.isNotBlank(model.getShipSn())){
            bu.append(" and s.ship_sn like ?");//运单号
            paras.add("%"+model.getShipSn()+"%");
        }
        bu.append("  order by  sg.sign_time desc ");
        return KdShip.dao.paginate(paginator,select.toString(),bu.toString(),paras.toArray());
    }

    /**
     * 回单回收
     * @param ids
     * @param loadworkId
     * @return
     */
    public boolean recoveryReceipt(String ids,String loadworkId,SessionUser user){
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                //更新运单状态
                if(!(Db.update("update kd_ship set ship_receipt_status=1 WHERE ship_id in("+ids+")")>=0)) return false;
                //保存回单信息表
                String [] lids=ids.split(",");
                List<KdReceipt> list=new ArrayList<>();
                Date time = new Date();
                for (String id :lids){
                    KdReceipt kdReceipt=new KdReceipt();
                    kdReceipt.set("ship_id",id);
                    kdReceipt.set("network_id",loadworkId);
                    kdReceipt.set("create_time",time);
                    list.add(kdReceipt);
                    //日志信息
                    KdShipTrack kdShipTrack=new KdShipTrack();
                    kdShipTrack.set("track_ship_id", id);
                    kdShipTrack.set("user_id", user.getUserId());
                    kdShipTrack.set("track_resource_id", id);
                    kdShipTrack.set("track_company_id", user.getCompanyId());
                    kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_HD_NUM);
                    kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_HD);
                    kdShipTrack.set("track_networkid", loadworkId);
                    kdShipTrack.set("track_desc", " 回单回收");
                    kdShipTrack.set("create_time", new Date());
                    if(!kdShipTrack.save()) return false;
                }
                int[] size = Db.batchSave(list,list.size());
                if(size.length!=list.size())return false;
                return true;
            }
        });
    }

    /**
     * 回单寄出
     * @param ids
     * @param loadworkId
     * @param preNo
     * @return
     */
    public boolean sendReceipt(String ids,String loadworkId,String preNo,SessionUser user){
        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                //更新运单状态
                if(!(Db.update("update kd_ship set ship_receipt_status=2 WHERE ship_id in("+ids+")")>=0)) return false;
                //保存回单信息表
                String [] lids=ids.split(",");
                List<KdReceipt> list=new ArrayList<>();
                Date time = new Date();
                for (String id :lids){
                    KdReceipt kdReceipt=new KdReceipt();
                    kdReceipt.set("ship_id",id);
                    kdReceipt.set("network_id",loadworkId);
                    kdReceipt.set("receipt_pre_post_no",preNo);
                    kdReceipt.set("create_time",time);
                    list.add(kdReceipt);
                    //日志信息
                    KdShipTrack kdShipTrack=new KdShipTrack();
                    kdShipTrack.set("track_ship_id", id);
                    kdShipTrack.set("user_id", user.getUserId());
                    kdShipTrack.set("track_resource_id", id);
                    kdShipTrack.set("track_company_id", user.getCompanyId());
                    kdShipTrack.set("track_class", KdShipTrack.MODEL_SHIP_HD_NUM);
                    kdShipTrack.set("track_short_desc", KdShipTrack.MODEL_SHIP_HD);
                    kdShipTrack.set("track_networkid", loadworkId);
                    kdShipTrack.set("track_desc", "回单寄出");
                    kdShipTrack.set("create_time", new Date());
                    if(!kdShipTrack.save()) return false;
                }
                int[] size = Db.batchUpdate(list,list.size());
                if(size.length!=list.size())return false;
                return true;
            }
        });
    }
    //回单签收图片保存
    public boolean sign(SessionUser user, String gid, String shipId, List<LibImage> imgs){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				//保存图片
				boolean res = new LibImageSvc().addLibImages(imgs);
				if(!res)return false;
				KdShipSign sign = new KdShipSign().findById(shipId);
				System.out.println(sign);
				if(sign!=null){
                    if(!sign.set("sign_image_gid",gid).update()) return false;
                }else{
				    if (!new KdShipSign().set("sign_image_gid",gid).set("ship_id",shipId).save()) return false;
                }
				return true;
			}
		});
	}


}
