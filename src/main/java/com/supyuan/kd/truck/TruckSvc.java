package com.supyuan.kd.truck;




import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.abnormal.AbnormalHandle;
/**
 * 运输车/货车svc
 * @author chenan
 * Date:2017年11月16日下午17:30:08
 * 
 */
public class TruckSvc extends BaseService {

	private final static Log log = Log.getLog(TruckSvc.class);
	
	/**
	 * 运输车/货车分页列表
	 * @param paginator
	 * @param truck_id_number
	 * @param truck_driver_name
	 * @param truck_type
	 * @param truck_hired_mode
	 * @param truck_driver_mobile
	 * @param companyId
	 * @return
	 */
	public Page<Truck> getTruckList(Paginator paginator,String truck_id_number,String truck_driver_name,String truck_type,String truck_hired_mode,String truck_driver_mobile,int companyId) {
		Page<Truck> truckList;
		StringBuilder select = new StringBuilder("select truck_id,truck_id_number,truck_type,truck_length,truck_hired_mode,truck_driver_name,truck_driver_mobile,truck_driver_id_number,truck_owner_name,truck_owner_mobile,truck_owner_id_number,truck_remark,create_time,company_id ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" from truck");
		parm.append(" where 1=1");
		if  (StringUtils.isNotBlank(truck_driver_name)) {
			parm.append(" and truck_driver_name like ('%" + truck_driver_name + "%')");
		}
		if  (StringUtils.isNotBlank(truck_id_number)) {
			parm.append(" and truck_id_number='" + truck_id_number + "'");
		}
		if  (StringUtils.isNotBlank(truck_type)) {
			parm.append(" and truck_type='" + truck_type + "'");
		}
		if  (StringUtils.isNotBlank(truck_hired_mode)) {
			parm.append(" and truck_hired_mode='" + truck_hired_mode + "'");
		}
		if  (StringUtils.isNotBlank(truck_driver_mobile)) {
			parm.append(" and truck_driver_mobile='" + truck_driver_mobile + "'");
		}
		
		parm.append(" and company_id=?");
		sql.append(parm.toString());
		
		truckList = Truck.dao.paginate(paginator,select.toString(),sql.toString(),companyId);
		return truckList;
	}
	
	
	public Page<Truck> getTruckGrid(Paginator paginator,String queryName,int companyId) {
		Page<Truck> truckList;
		StringBuilder select = new StringBuilder("select t.truck_id,t.truck_id_number,t.truck_type,t.truck_length,t.truck_driver_name,t.truck_driver_mobile ");
		StringBuilder sql = new StringBuilder();
		sql.append(" from (select truck_id,truck_id_number,truck_type,truck_length,truck_driver_name,truck_driver_mobile from truck where company_id="+companyId+") t");
		if  (StringUtils.isNotBlank(queryName)) {
			sql.append(" where t.truck_driver_name like '%" + queryName + "%' or t.truck_driver_mobile like '%" + queryName + "%' or t.truck_id_number like '%" + queryName + "%'");
		}
		truckList = Truck.dao.paginate(paginator,select.toString(),sql.toString());
		return truckList;
	}
	
	public boolean  save(Truck truck,List<LibImage> imgs,String gid){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(imgs.size()>0){
					//保存图片
					boolean res = new LibImageSvc().addLibImages(imgs);
					if(!res)return false;
					truck.set("truck_image_gid", gid);
				}
				if(!truck.save()) return false;
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 删除
	 * @param truckId
	 * @return
	 */
	public boolean  delete(String truckId) {
		
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!Truck.dao.deleteById(truckId)) return false;
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 车牌号查找司机
	 * @author liangxp
	 * @param truck_id_number
	 * @param companyId
	 * @return
	 */
	public Truck findTruckByNumber(String truck_id_number, int companyId) {
		StringBuilder select = new StringBuilder("select truck_id,truck_id_number,truck_type,truck_length,truck_hired_mode,truck_driver_name,truck_driver_mobile,truck_driver_id_number,truck_owner_name,truck_owner_mobile,truck_owner_id_number,truck_remark,create_time,company_id ");
		select.append(" from truck where truck_id_number=? and company_id=?");
		return Truck.dao.findFirst(select.toString(), truck_id_number, companyId);
	}
	
	/**
	 * 更新或保存司机信息
	 * @author liangxp
	 * 
	 * @param user
	 * @param truck
	 * @return
	 */
	public boolean saveOrUpdate(SessionUser user, Truck truck){
		Truck oldt = new TruckSvc().findTruckByNumber(truck.getStr("truck_id_number"), user.getCompanyId());
		if(oldt!=null){
			truck.set("truck_id", oldt.getInt("truck_id"));
			if((!oldt.getStr("truck_driver_name").equals(truck.getStr("truck_driver_name"))||(!oldt.getStr("truck_driver_mobile").equals(truck.getStr("truck_driver_mobile"))))){
				oldt.set("truck_driver_name", truck.getStr("truck_driver_name"));
				oldt.set("truck_driver_mobile", truck.getStr("truck_driver_mobile"));
				return oldt.update();
			}
			return true;
		}else{
			return truck.save(user);
		}
	}
	
}
