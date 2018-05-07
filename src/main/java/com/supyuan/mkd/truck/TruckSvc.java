package com.supyuan.mkd.truck;


import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.kd.truck.Truck;

/**
 * 司机信息
 * @author liangxp
 *
 * Date:2018年2月2日上午9:56:33 
 * 
 * @email liangxp@anfawuliu.com
 */
public class TruckSvc extends BaseService {
	
	
	/**
	 * 用户所属公司司机列表
	 * @author liangxp
	 * @param paginator
	 * @param companyId
	 * @return
	 */
	public Page<Truck> findTruckList(Paginator paginator, int companyId){
		Page<Truck> truckList;
		StringBuilder select = new StringBuilder("select t.truck_id,t.truck_id_number,t.truck_type,t.truck_length,t.truck_driver_name,t.truck_driver_mobile ");
		StringBuilder sql = new StringBuilder();
		sql.append(" from (select truck_id,truck_id_number,truck_type,truck_length,truck_driver_name,truck_driver_mobile from truck where company_id="+companyId+") t");
		truckList = Truck.dao.paginate(paginator,select.toString(),sql.toString());
		return truckList;
	}
	
}
