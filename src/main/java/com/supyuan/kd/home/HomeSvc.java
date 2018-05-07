package com.supyuan.kd.home;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.kd.role.Menu;
import com.supyuan.util.DateUtils;
/**
 * 首页svc
 * @author chenan
 * Date:2017年12月14日 上午9:46:08
 * 
 */
public class HomeSvc extends BaseService {

	private final static Log log = Log.getLog(HomeSvc.class);
	/**
	 * 获取一级菜单
	 * @param userId
	 * @return
	 */
	public List<Menu> getFirstLevMenu(int userId){
		List<Menu> menuList=Menu.dao.find("SELECT DISTINCT sm.name,sm.id,sm.href from sys_user_role sur,sys_role sr,sys_menu sm,sys_role_menu srm where sm.is_show=1 and sur.role_id=sr.id AND sr.id=srm.role_id AND sm.id=srm.menu_id AND sm.parent_id=0 AND sur.user_id=? ORDER BY sm.sort",userId);
		return menuList;
	}
	
	/**
	 * 获取二级菜单
	 * @param parentId
	 * @param userId
	 * @return
	 */
	public List<Menu> getSecondLevMenu(int parentId,int userId){
		List<Menu> menuList=Menu.dao.find("SELECT DISTINCT sm.name,sm.parent_id,sm.parent_ids,sm.sort,sm.href,sm.target,sm.icon,sm.is_show,sm.permission,sm.create_by,sm.create_date,sm.update_by,sm.update_date,sm.remarks,sm.del_flag from  sys_user_role sur,sys_role sr,sys_menu sm,sys_role_menu srm where sm.is_show=1 and sur.role_id=sr.id AND sr.id=srm.role_id AND sm.id=srm.menu_id AND sm.parent_id=? AND sur.user_id=? ORDER BY sm.sort",parentId,userId);
		return menuList;
	}
	
	public String getMenuName(List<Menu> list){
		StringBuffer name=new StringBuffer();
		for (Menu menu : list) {
			name.append(menu.getStr("name"));
		}
		return name.toString();
	}
	
	/**
	 * 获取首页展示数据
	 * @param companyId
	 * @param netWorkId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public HashMap<String,Record> getData(int companyId,String netWorkId,String startTime,String endTime){
		
		
		StringBuffer shipsql=new StringBuffer("SELECT COUNT(ship_id) as count,IFNULL(sum(ship_volume),0) as volume,IFNULL(sum(ship_weight),0) as weight from kd_ship s where s.ship_deleted=0 AND s.company_id=? ");
		StringBuffer reShipsql=new StringBuffer("SELECT COUNT(s.ship_id) as reCount,IFNULL(sum(s.ship_volume),0) as reVolume,IFNULL(sum(s.ship_weight),0) as reWeight from kd_ship s inner join kd_ship_arrival sa on s.ship_id=sa.ship_id INNER join kd_in_out io ON io.ship_id=s.ship_id and io.network_id in ("+netWorkId+")  where s.ship_deleted=0 AND DATE_FORMAT(sa.create_time, '%Y-%m-%d %H:%i')=DATE_FORMAT(io.in_time, '%Y-%m-%d %H:%i') AND s.company_id=? ");
		StringBuffer transfersql=new StringBuffer("SELECT count(st.ship_id) as count from kd_ship_transfer st,kd_ship s where s.ship_deleted=0 AND st.ship_id=s.ship_id AND s.company_id=? ");
		StringBuffer loadShipsql=new StringBuffer("SELECT count(kts.ship_id) as count FROM kd_truck_load ktl LEFT JOIN kd_truck_ship kts ON ktl.load_id=kts.truck_load_id where ktl.company_id=? ");
		StringBuffer loadTrucksql=new StringBuffer("SELECT count(truck_id) as count from kd_truck_load where company_id=? ");
		
		if(StringUtils.isNotBlank(netWorkId)){
			shipsql.append("AND s.network_id in("+netWorkId+")");
			transfersql.append("AND st.network_id in("+netWorkId+")");
			loadShipsql.append("AND ktl.network_id in("+netWorkId+")");
			loadTrucksql.append("AND network_id in("+netWorkId+")");
		}
		if(StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
			shipsql.append("  AND DATE_FORMAT(s.create_time,'%Y-%m-%d') BETWEEN '"+startTime+"' AND '"+endTime+"'");
			reShipsql.append("  AND DATE_FORMAT(s.create_time,'%Y-%m-%d') BETWEEN '"+startTime+"' AND '"+endTime+"'");
			transfersql.append("  AND DATE_FORMAT(ship_transfer_time,'%Y-%m-%d') BETWEEN '"+startTime+"' AND '"+endTime+"'");
			loadShipsql.append("  AND DATE_FORMAT(ktl.create_time,'%Y-%m-%d') BETWEEN '"+startTime+"' AND '"+endTime+"'");
			loadTrucksql.append("  AND DATE_FORMAT(create_time,'%Y-%m-%d') BETWEEN '"+startTime+"' AND '"+endTime+"'");
		}
		
		
		Record ship=Db.findFirst(shipsql.toString(),companyId);
		Record reShip=Db.findFirst(reShipsql.toString(),companyId);
		Record transfer=Db.findFirst(transfersql.toString(),companyId);
		Record loadShip=Db.findFirst(loadShipsql.toString(),companyId);
		Record loadTruck=Db.findFirst(loadTrucksql.toString(),companyId);
		HashMap data=new HashMap<>();
		data.put("ship", ship);
		data.put("reShip", reShip);
		data.put("transfer", transfer);
		data.put("loadShip", loadShip);
		data.put("loadTruck", loadTruck);
		return data;
	}
	
	public List<Record> getCountShipDataByEndTime(String endTime,String netWorkId){
		List<String> timeList=DateUtils.getDayList(endTime);
		List<Record> dataList=new ArrayList();
		StringBuffer sql=new StringBuffer("SELECT count(ship_id) countShip,DATE_FORMAT(create_time,'%Y-%m-%d') as time from kd_ship ");
		sql.append(" where 1=1 ");
		sql.append(" and ship_deleted=0 and network_id in ("+netWorkId+") ");
		sql.append(" and DATE_FORMAT(create_time, '%Y-%m-%d')>='"+timeList.get(0)+"' and DATE_FORMAT(create_time, '%Y-%m-%d')<='"+timeList.get(timeList.size()-1)+"'");
		sql.append(" GROUP BY time");
		List<Record> record = Db.find(sql.toString());
		for (String time : timeList) {
			Record data=new Record();
			data.set("time", time);
			if(record.size()==0){
				data.set("countShip", 0);
			}else{
				for (Record temp : record) {
					if(temp.getStr("time").equals(time)){
						data.set("countShip", temp.get("countShip"));
						break;
					}else{
						data.set("countShip", 0);
					}
					}
			}
			
			dataList.add(data);
		}
		
		return dataList;
	}
	
	
	public HashMap<String,Object> getCountShipCity(String startTime,String endTime,String netWorkId){
		StringBuffer sql=new StringBuffer();
		HashMap<String,Object> dataMap=new HashMap<String,Object>();
		sql.append("SELECT COUNT(ship_id) countShip,");
		sql.append("CASE l.region_type WHEN 4 THEN getRegionName(l2.region_code) WHEN 2 THEN getRegionName(l.region_code) when 3 THEN getRegionName(l.region_code)  END as cityName");
		sql.append(" FROM kd_ship s");
		sql.append(" LEFT JOIN library_region l ON s.ship_to_city_code=l.region_code");
		sql.append(" LEFT JOIN library_region l2 ON l2.region_code=l.parent_code");
		sql.append(" where 1=1 and s.ship_deleted=0 and s.network_id in ("+netWorkId+") and DATE_FORMAT(s.create_time, '%Y-%m-%d')>='"+startTime+"' and DATE_FORMAT(s.create_time, '%Y-%m-%d')<='"+endTime+"'" );
		sql.append(" GROUP BY cityName ORDER BY countShip desc limit 5");
		
		List<Record> record = Db.find(sql.toString());
		long totalCount=0;
		for (Record record2 : record) {
			totalCount+=record2.getLong("countShip");
		}
		Record all=Db.findFirst("SELECT count(ship_id) countShip from kd_ship  where ship_deleted=0 and network_id in ("+netWorkId+") and DATE_FORMAT(create_time, '%Y-%m-%d')>='"+startTime+"' and DATE_FORMAT(create_time, '%Y-%m-%d')<='"+endTime+"'" );
		dataMap.put("total",all.getLong("countShip"));
		all.set("cityName", "其他");
		all.set("countShip", all.getLong("countShip")-totalCount);
		if(all.getLong("countShip")!=0){
			record.add(all);
		}
		dataMap.put("dataList", record);
		return dataMap;
	}
	
	public HashMap<String,Object> getStockData(String startTime,String endTime,String netWorkId){
		StringBuffer sql=new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		HashMap<String,Object> dataMap=new HashMap<String,Object>();
		
		sql.append("select IFNULL(sum(s.ship_volume),0) shipAmount,IFNULL(sum(s.ship_weight),0) shipWeight,IFNULL(count(s.ship_id),0) countShip");
		sql.append(" FROM kd_in_out o");
		sql.append(" LEFT JOIN kd_ship s ON s.ship_id = o.ship_id");
		sql.append(" WHERE o.out_time > 0");
		sql.append(" and s.ship_deleted=0");
		sql.append(" and o.network_id IN("+netWorkId+")");
		sql.append(" and DATE_FORMAT(o.in_time, '%Y-%m-%d') >='"+startTime+"' and DATE_FORMAT(o.in_time, '%Y-%m-%d')<='"+endTime+"'");

		
		Record out = Db.findFirst(sql.toString());
		
		sql2.append("select IFNULL(sum(t.ship_volume),0) shipAmount,IFNULL(sum(t.ship_weight),0) shipWeight,IFNULL(count(ship_id),0) countShip from(select m.* from (SELECT s.ship_volume,s.ship_weight,s.ship_id ");
		sql2.append(" FROM kd_in_out o");
		sql2.append(" LEFT JOIN kd_ship s ON s.ship_id = o.ship_id");
		sql2.append(" WHERE ship_storage < 2");
		sql2.append(" and s.ship_deleted=0");
		sql2.append(" and s.load_network_id IN("+netWorkId+")");
		sql2.append(" and DATE_FORMAT(o.in_time, '%Y-%m-%d') >='"+startTime+"' and DATE_FORMAT(o.in_time, '%Y-%m-%d')<='"+endTime+"')m group by m.ship_id)t");
		System.out.println("sql1:"+sql.toString());
		System.out.println("sql2:"+sql2.toString());
		
		Record in = Db.findFirst(sql2.toString());
		
		Record all=new Record();
		all.set("shipAmount", out.getDouble("shipAmount")+in.getDouble("shipAmount"));
		all.set("shipWeight", out.getDouble("shipWeight")+in.getDouble("shipWeight"));
		all.set("countShip", out.getLong("countShip")+in.getLong("countShip"));
		
		dataMap.put("out", out);
		dataMap.put("in", in);
		dataMap.put("all", all);
		return dataMap;
	}
	
	
	
	
}
