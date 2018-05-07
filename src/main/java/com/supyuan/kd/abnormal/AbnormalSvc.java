package com.supyuan.kd.abnormal;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.bankcard.BankCard;
import com.supyuan.kd.customer.Customer;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.StrUtils;
/**
 * 运单异常svc
 * @author chenan
 * Date:2018年1月17日下午9:41:08 
 * 
 */
public class AbnormalSvc extends BaseService {

	private final static Log log = Log.getLog(AbnormalSvc.class);
	
	/**
	 * 运单异常分页列表
	 * @param paginator
	 * @param user
	 * @param search
	 * @return
	 */
	public Page<Abnormal> getAbnormalPageList(Paginator paginator,SessionUser user,AbnormalSearchModel search,String flag) {
		String shipNetWorkIds=user.toNetWorkIdsStr();
		String abnormalNetWorkIds=user.toNetWorkIdsStr();
		
		if  (StringUtils.isNotBlank(search.getShipNet())) {
			shipNetWorkIds=search.getShipNet();
		}
		if  (StringUtils.isNotBlank(search.getAbnormalNet())) {
			abnormalNetWorkIds=search.getAbnormalNet();
		}
		
		Page<Abnormal> AbnormalList;
		StringBuilder select = new StringBuilder("select a.abnormal_id,a.abnormal_sn,a.ship_id,a.network_id,a.abnormal_type,a.abnormal_status,a.abnormal_desc,a.abnormal_image_gid,a.user_id,a.create_time,");
		StringBuilder sql = new StringBuilder();
		select.append("s.ship_sn,getNetworkNameById(a.network_id) abnormalNet,getNetworkNameById(s.network_id) shipNet,");
		select.append("getRegion(s.ship_from_city_code) fromAddr,getRegion(s.ship_to_city_code) toAddr,");
		select.append("getCustomerName(s.ship_sender_id) senderName,getCustomerName(ship_receiver_id) receiverName,u.realname");
		
		StringBuilder parm = new StringBuilder();
		sql.append(" From kd_abnormal a");
		sql.append(" LEFT JOIN kd_ship s ON s.ship_id=a.ship_id");
		sql.append(" LEFT JOIN sys_user u ON u.userid=a.user_id");
		parm.append(" where 1=1");
		parm.append(" and s.ship_deleted=0 ");
		if(flag.equals("my")){
			parm.append(" and a.user_id="+user.getUserId());
		}else{
			parm.append(" and (s.network_id in ("+shipNetWorkIds+") or a.network_id in ("+abnormalNetWorkIds+"))");
		}
		
		if  (StringUtils.isNotBlank(search.getAbnormalStatus())) {
			parm.append(" and a.abnormal_status="+search.getAbnormalStatus());
		}
		if  (StringUtils.isNotBlank(search.getAbnormalType())) {
			parm.append(" and a.abnormal_type="+search.getAbnormalType());
		}
		if  (StringUtils.isNotBlank(search.getFromAddr())) {
			parm.append(" and s.ship_from_city_code like ('" + StrUtils.getRealRegionCode(search.getFromAddr())+"%')");
		}
		if  (StringUtils.isNotBlank(search.getToAddr())) {
			parm.append(" and s.ship_to_city_code like ('" + StrUtils.getRealRegionCode(search.getToAddr())+"%')");
		}
		if  (StringUtils.isNotBlank(search.getSender())) {
			parm.append(" and s.ship_sender_id="+search.getSender());
		}
		if  (StringUtils.isNotBlank(search.getReceiver())) {
			parm.append(" and s.ship_receiver_id="+search.getReceiver());
		}
		
		parm.append(" order by a.create_time DESC ");
		
		sql.append(parm.toString());
		
		AbnormalList = Abnormal.dao.paginate(paginator,select.toString(),sql.toString());
		return AbnormalList;
	}
	
	public Abnormal getAbnormal(String abnormalId) {
		StringBuffer sql=new StringBuffer();

		sql.append("select a.abnormal_id,a.abnormal_sn,a.ship_id,a.network_id,a.abnormal_type,a.abnormal_status,a.abnormal_desc,a.abnormal_image_gid,a.user_id,a.create_time,");
		sql.append("s.ship_sn,getNetworkNameById(a.network_id) abnormalNet,getNetworkNameById(s.network_id) shipNet,s.create_time as shipTime,");
		sql.append("getRegion(s.ship_from_city_code) fromAddr,getRegion(s.ship_to_city_code) toAddr,");
		sql.append("getCustomerName(s.ship_sender_id) senderName,getCustomerName(s.ship_receiver_id) receiverName,u.realname,getProductName(s.ship_id) productName,s.ship_amount");
		sql.append(" From kd_abnormal a");
		sql.append(" LEFT JOIN kd_ship s ON s.ship_id=a.ship_id");
		sql.append(" LEFT JOIN sys_user u ON u.userid=a.user_id");
		sql.append(" where a.abnormal_id=?");
		Abnormal abnormal=new Abnormal().findFirst(sql.toString(),abnormalId);
	
		return abnormal;
	} 
	
	public List<HashMap> getAbnormalHandle(String abnormalId) {
		List<HashMap> list=new ArrayList();
		StringBuffer sql=new StringBuffer();
		sql.append(" select handle_id from kd_abnormal_handle where handle_abnormal_id=? order by create_time desc");
		
		List<AbnormalHandle> abnormalHandle=new AbnormalHandle().find(sql.toString(),abnormalId);
		for (AbnormalHandle row : abnormalHandle) {
			HashMap<String,Object> map=new HashMap<>();
			StringBuffer sql2=new StringBuffer();
			sql2.append(" select u.realname,ah.create_time,ah.handle_content,ah.handle_image_gid");
			sql2.append(" From kd_abnormal_handle ah");
			sql2.append(" LEFT JOIN sys_user u ON u.userid=ah.user_id");
			sql2.append(" where handle_id=?");
			AbnormalHandle handle=new AbnormalHandle().findFirst(sql2.toString(),row.getInt("handle_id"));
			map.put("row", handle);
			if(handle.get("handle_image_gid")!=null){
				List<LibImage> img = new LibImageSvc().getLibImages(handle.get("handle_image_gid"));
				map.put("img", img);
			}
			list.add(map);
		}
	
		return list;
	}
	
	
	public boolean  save(Abnormal abnormal,List<LibImage> imgs,String gid){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(imgs.size()>0){
					//保存图片
					boolean res = new LibImageSvc().addLibImages(imgs);
					if(!res)return false;
					abnormal.set("abnormal_image_gid", gid);
				}
				if(!abnormal.save()) return false;
				
				return true;
			}
		});
		return tx;
	}
	
	public boolean  saveHandle(AbnormalHandle abnormalHandle,List<LibImage> imgs,String gid){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(imgs.size()>0){
					//保存图片
					boolean res = new LibImageSvc().addLibImages(imgs);
					if(!res)return false;
					abnormalHandle.set("handle_image_gid", gid);
				}
				if(!abnormalHandle.save()) return false;
				if(!(Db.update("update kd_abnormal set abnormal_status=1 WHERE abnormal_id =?",abnormalHandle.getInt("handle_abnormal_id"))>0)) return false ;
				return true;
			}
		});
		return tx;
	}
	
	public boolean  update(String id){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!(Db.update("update kd_abnormal set abnormal_status=2 WHERE abnormal_id =?",id)>0)) return false ;
				return true;
			}
		});
		return tx;
	}
	
	
	
	/**
	 * 运单异常excel列表
	 * @param
	 * @param user
	 * @param search
	 * @return
	 */
	public List<ExcelAbnormal> getAbnormalExcelList(SessionUser user,AbnormalSearchModel search,String flag) {
		String shipNetWorkIds=user.toNetWorkIdsStr();
		String abnormalNetWorkIds=user.toNetWorkIdsStr();
		
		if  (StringUtils.isNotBlank(search.getShipNet())) {
			shipNetWorkIds=search.getShipNet();
		}
		if  (StringUtils.isNotBlank(search.getAbnormalNet())) {
			abnormalNetWorkIds=search.getAbnormalNet();
		}
		
		List<Abnormal> AbnormalList;
		StringBuilder select = new StringBuilder("select a.abnormal_id,a.abnormal_sn,a.ship_id,a.network_id,a.abnormal_type,a.abnormal_status,a.abnormal_desc,a.abnormal_image_gid,a.user_id,a.create_time,");
		StringBuilder sql = new StringBuilder();
		select.append("s.ship_sn,getNetworkNameById(a.network_id) abnormalNet,getNetworkNameById(s.network_id) shipNet,");
		select.append("getRegion(s.ship_from_city_code) fromAddr,getRegion(s.ship_to_city_code) toAddr,");
		select.append("getCustomerName(s.ship_sender_id) senderName,getCustomerName(ship_receiver_id) receiverName,u.realname");
		
		StringBuilder parm = new StringBuilder();
		sql.append(" From kd_abnormal a");
		sql.append(" LEFT JOIN kd_ship s ON s.ship_id=a.ship_id");
		sql.append(" LEFT JOIN sys_user u ON u.userid=a.user_id");
		parm.append(" where 1=1");
		parm.append(" and s.ship_deleted=0 ");
		if(flag.equals("my")){
			parm.append(" and a.user_id="+user.getUserId());
		}else{
			parm.append(" and (s.network_id in ("+shipNetWorkIds+") or a.network_id in ("+abnormalNetWorkIds+"))");
		}
		
		if  (StringUtils.isNotBlank(search.getAbnormalStatus())) {
			parm.append(" and a.abnormal_status="+search.getAbnormalStatus());
		}
		if  (StringUtils.isNotBlank(search.getAbnormalType())) {
			parm.append(" and a.abnormal_type="+search.getAbnormalType());
		}
		if  (StringUtils.isNotBlank(search.getFromAddr())) {
			parm.append(" and s.ship_from_city_code '" + StrUtils.getRealRegionCode(search.getFromAddr())+"%'");
		}
		if  (StringUtils.isNotBlank(search.getToAddr())) {
			parm.append(" and s.ship_to_city_code '" + StrUtils.getRealRegionCode(search.getToAddr())+"%'");
		}
		if  (StringUtils.isNotBlank(search.getSender())) {
			parm.append(" and s.ship_sender_id="+search.getSender());
		}
		if  (StringUtils.isNotBlank(search.getReceiver())) {
			parm.append(" and s.ship_receiver_id="+search.getReceiver());
		}
		
		parm.append(" order by a.create_time DESC ");
		
		sql.append(parm.toString());
		
		AbnormalList = new Abnormal().find((select.toString()+sql.toString()));
		
		List<ExcelAbnormal> excelList=new ArrayList();
		int orderNum=1;
		for (Abnormal abnormal : AbnormalList) {
			
			ExcelAbnormal excel=new ExcelAbnormal();
			excel.setOrderNum(orderNum);
			excel.setAbnormalSn(abnormal.get("abnormal_sn"));
			excel.setShipSn(abnormal.get("ship_sn"));
			excel.setAbnormalNet(abnormal.get("abnormalNet"));
			int state=abnormal.get("abnormal_status");
			String abnormalState="待处理";
			switch (state) {
			case 1:
				abnormalState="处理中";
				break;
			case 2:
				abnormalState="已处理";
				break;
			default:
				break;
			}
			excel.setAbnormalStatus(abnormalState);
			int type=abnormal.get("abnormal_type");
			String abnormalType="其他";
			switch (type) {
			case 0:
				abnormalType="货损";
				break;
			case 1:
				abnormalType="少货";
				break;
			case 2:
				abnormalType="多货";
				break;
			case 3:
				abnormalType="货物丢失";
				break;
			case 4:
				abnormalType="货单不符";
				break;
			case 5:
				abnormalType="超重超方";
				break;
			case 6:
				abnormalType="超时";
				break;
			case 7:
				abnormalType="拒收";
				break;
			case 8:
				abnormalType="投诉";
				break;
			default:
				break;
			}
			excel.setAbnormalType(abnormalType);
			excel.setShipNet(abnormal.get("shipNet"));
			excel.setFromAdd(abnormal.get("fromAddr"));
			excel.setToAdd(abnormal.get("toAddr"));
			excel.setSenderName(abnormal.get("senderName"));
			excel.setReceiverName(abnormal.get("receiverName"));
			excel.setRealname(abnormal.get("realname"));
			excel.setCreate_time(abnormal.get("create_time").toString());
			excelList.add(excel);
			orderNum++;
		}
		
		return excelList;
	}
	
	
	/**
	 * 我的运单异常excel列表
	 * @param
	 * @param user
	 * @param search
	 * @return
	 */
	public List<ExcelMyAbnormal> getMyAbnormalExcelList(SessionUser user,AbnormalSearchModel search,String flag) {
		String shipNetWorkIds=user.toNetWorkIdsStr();
		String abnormalNetWorkIds=user.toNetWorkIdsStr();
		
		if  (StringUtils.isNotBlank(search.getShipNet())) {
			shipNetWorkIds=search.getShipNet();
		}
		if  (StringUtils.isNotBlank(search.getAbnormalNet())) {
			abnormalNetWorkIds=search.getAbnormalNet();
		}
		
		List<Abnormal> AbnormalList;
		StringBuilder select = new StringBuilder("select a.abnormal_id,a.abnormal_sn,a.ship_id,a.network_id,a.abnormal_type,a.abnormal_status,a.abnormal_desc,a.abnormal_image_gid,a.user_id,a.create_time,");
		StringBuilder sql = new StringBuilder();
		select.append("s.ship_sn,getNetworkNameById(a.network_id) abnormalNet,getNetworkNameById(s.network_id) shipNet,");
		select.append("getRegion(s.ship_from_city_code) fromAddr,getRegion(s.ship_to_city_code) toAddr,");
		select.append("getCustomerName(s.ship_sender_id) senderName,getCustomerName(ship_receiver_id) receiverName,u.realname");
		
		StringBuilder parm = new StringBuilder();
		sql.append(" From kd_abnormal a");
		sql.append(" LEFT JOIN kd_ship s ON s.ship_id=a.ship_id");
		sql.append(" LEFT JOIN sys_user u ON u.userid=a.user_id");
		parm.append(" where 1=1");
		parm.append(" and s.ship_deleted=0 ");
		if(flag.equals("my")){
			parm.append(" and a.user_id="+user.getUserId());
		}else{
			parm.append(" and (s.network_id in ("+shipNetWorkIds+") or a.network_id in ("+abnormalNetWorkIds+"))");
		}
		
		if  (StringUtils.isNotBlank(search.getAbnormalStatus())) {
			parm.append(" and a.abnormal_status="+search.getAbnormalStatus());
		}
		if  (StringUtils.isNotBlank(search.getAbnormalType())) {
			parm.append(" and a.abnormal_type="+search.getAbnormalType());
		}
		if  (StringUtils.isNotBlank(search.getFromAddr())) {
			parm.append(" and s.ship_from_city_code '" + StrUtils.getRealRegionCode(search.getFromAddr())+"%'");
		}
		if  (StringUtils.isNotBlank(search.getToAddr())) {
			parm.append(" and s.ship_to_city_code '" + StrUtils.getRealRegionCode(search.getToAddr())+"%'");
		}
		if  (StringUtils.isNotBlank(search.getSender())) {
			parm.append(" and s.ship_sender_id="+search.getSender());
		}
		if  (StringUtils.isNotBlank(search.getReceiver())) {
			parm.append(" and s.ship_receiver_id="+search.getReceiver());
		}
		
		parm.append(" order by a.create_time DESC ");
		
		sql.append(parm.toString());
		
		AbnormalList = new Abnormal().find((select.toString()+sql.toString()));
		
		List<ExcelMyAbnormal> excelList=new ArrayList();
		int orderNum=1;
		for (Abnormal abnormal : AbnormalList) {
			
			ExcelMyAbnormal excel=new ExcelMyAbnormal();
			excel.setOrderNum(orderNum);
			excel.setAbnormalSn(abnormal.get("abnormal_sn"));
			excel.setShipSn(abnormal.get("ship_sn"));
			int state=abnormal.get("abnormal_status");
			String abnormalState="待处理";
			switch (state) {
			case 1:
				abnormalState="处理中";
				break;
			case 2:
				abnormalState="已处理";
				break;
			default:
				break;
			}
			excel.setAbnormalStatus(abnormalState);
			int type=abnormal.get("abnormal_type");
			String abnormalType="其他";
			switch (type) {
			case 0:
				abnormalType="货损";
				break;
			case 1:
				abnormalType="少货";
				break;
			case 2:
				abnormalType="多货";
				break;
			case 3:
				abnormalType="货物丢失";
				break;
			case 4:
				abnormalType="货单不符";
				break;
			case 5:
				abnormalType="超重超方";
				break;
			case 6:
				abnormalType="超时";
				break;
			case 7:
				abnormalType="拒收";
				break;
			case 8:
				abnormalType="投诉";
				break;
			default:
				break;
			}
			excel.setAbnormalType(abnormalType);
			excel.setShipNet(abnormal.get("shipNet"));
			excel.setFromAdd(abnormal.get("fromAddr"));
			excel.setToAdd(abnormal.get("toAddr"));
			excel.setSenderName(abnormal.get("senderName"));
			excel.setReceiverName(abnormal.get("receiverName"));
			excel.setCreate_time(abnormal.get("create_time").toString());
			excelList.add(excel);
			orderNum++;
		}
		
		return excelList;
	}

	/**
	 * 运单异常记录
	 * @param shipId
	 * @return
	 */
	public List<Abnormal> getAbnormalByShipId(String shipId){
		StringBuilder sb=new StringBuilder("SELECT a.abnormal_id,a.abnormal_sn, getNetworkNameById(a.network_id) AS netWorkName,a.abnormal_type,a.abnormal_status,");
		sb.append(" a.abnormal_desc,getCustomerName(s.ship_sender_id) AS senderName,getCompanyName(s.ship_receiver_id) AS receiverName,  getPCUserName(a.user_id) AS username,a.create_time");
		sb.append(" from kd_abnormal a LEFT JOIN kd_ship s ON a.ship_id=s.ship_id where a.ship_id=? order by a.create_time desc");
		return Abnormal.dao.find(sb.toString(),shipId);

	}

	
}
