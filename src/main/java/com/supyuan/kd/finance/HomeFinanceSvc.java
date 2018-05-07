package com.supyuan.kd.finance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.util.DateUtils;

/**
 * 财务首页
 * @author liangxp
 *
 * Date:2018年1月6日下午4:54:27 
 * 
 * @email liangxp@anfawuliu.com
 */
public class HomeFinanceSvc extends BaseService {
	
	
	
	public Page<HomeFinanceModel>  queryHomeFinanceList(Paginator paginator, SessionUser user, HomeSearchModel model){
		String select = "select k.fromAdd,k.toAdd,k.volume,k.weight,k.totalCount, IFNULL(k.totalloadfee+IFNULL(k.transferfee,0), 0) allloadfee, k.totalfee, IFNULL(k.totalfee-IFNULL(k.totalloadfee,0)-IFNULL(k.transferfee,0), 0) alloutfee ,IFNULL((k.totalfee-IFNULL(k.totalloadfee,0)-IFNULL(k.transferfee,0))/k.totalfee,0) rate";
		StringBuilder start = 	new StringBuilder("  from (select m.scitycode scode,m.ecitycode ecode,count(*) totalCount,"
				+" getRegion(CONCAT(m.scitycode,'00')) as fromAdd, getRegion(CONCAT(m.ecitycode,'00')) AS toAdd,sum(m.ship_volume) volume, sum(m.ship_weight) weight,"
				+" sum(m.allloadfee) totalloadfee,sum(m.ship_total_fee) totalfee,sum(m.ship_transfer_fee) transferfee from (select left(s.ship_from_city_code, 4) scitycode, left(s.ship_to_city_code, 4) ecitycode,"
				+" s.ship_volume,s.ship_weight, s.ship_total_fee, sum(f.fee) allloadfee, sf.ship_transfer_fee"
				+" from  kd_ship s LEFT JOIN kd_truck_ship ts on ts.ship_id=s.ship_id"
				+" LEFT JOIN kd_truck_load l on l.load_id=ts.truck_load_id LEFT JOIN kd_ship_transfer sf on sf.ship_id=s.ship_id "
				+" LEFT JOIN kd_ship_fee f  ON f.ship_id=s.ship_id  AND f.load_id=l.load_id  where s.ship_deleted=0 ");
		StringBuilder bu = new StringBuilder(" GROUP BY s.ship_id) m GROUP BY scode, ecode) k ORDER BY rate desc");
		/*String s = DateUtils.getCurrBeforeYearMonth();
		String e = DateUtils.getCurrYearMonth();
		if(StringUtils.isNotBlank(model.getStartTime())){
			s = model.getStartTime();
		}
		if(StringUtils.isNotBlank(model.getEndTime())){
			e = model.getEndTime();
		}
		List<String> monthList = DateUtils.getMonthList(s, e);
		if(monthList.size()>12){
			s = monthList.get(11);
		}
		e = e + "-" + DateUtils.getDateDay(e) + " 23:59:59";*/
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getStartTime())){
			start.append(" and s.create_time>=?");
			paras.add(model.getStartTime()+ "-01");
		}
		
		if(StringUtils.isNotBlank(model.getEndTime())){
			start.append(" and s.create_time<=?");
			paras.add(model.getEndTime() + "-" + DateUtils.getDateDay(model.getEndTime()) + " 23:59:59");
		}
		
		if(StringUtils.isNotBlank(model.getNetworkId())){
			start.append(" and s.network_id=?");
			paras.add(model.getNetworkId());
		}else{
			start.append(" and s.network_id in (" + user.toNetWorkIdsStr()+")");
		}
		return HomeFinanceModel.dao.paginate(paginator, select, start.toString() + bu.toString(), paras.toArray());
	}
	
	
	public HomeFinanceAllModel  findTotal(SessionUser user, HomeSearchModel model){
		String select = "select IFNULL(sum(m.ship_total_fee),0) totalfee,IFNULL(sum(m.tsfee),0) zzfee,IFNULL(sum(m.thfee),0) thfee,IFNULL(sum(m.dbfee),0) dbfee,IFNULL(sum(m.gxfee),0) gxfee,IFNULL(sum(m.shfee),0) shfee";
		StringBuilder start = 	new StringBuilder("  from (SELECT s.ship_id,s.ship_total_fee,IFNULL(ts.ship_transfer_fee,0) tsfee,"
				+" sum(case tl.load_transport_type WHEN 1 then sf.fee  else 0 end) thfee,sum(case tl.load_transport_type WHEN 2 then sf.fee  else 0 end) dbfee ,"
				+" sum(case tl.load_transport_type WHEN 3 then sf.fee  else 0 end) gxfee ,sum(case tl.load_transport_type WHEN 4 then sf.fee  else 0 end) shfee "
				+" FROM kd_ship s LEFT JOIN kd_truck_ship ks ON s.ship_id=ks.ship_id  "
				+" LEFT JOIN kd_truck_load tl ON tl.load_id=ks.truck_load_id "
				+" LEFT JOIN kd_ship_transfer ts ON ts.ship_id=s.ship_id  "
				+" LEFT JOIN kd_ship_fee sf  ON sf.ship_id=s.ship_id  AND sf.load_id=ks.truck_load_id "
				+" where s.ship_deleted=0 ");
		
		List<Object> paras = new ArrayList<Object>();
		if(StringUtils.isNotBlank(model.getStartTime())){
			start.append(" and s.create_time>=?");
			paras.add(model.getStartTime()+ "-01");
		}
		
		if(StringUtils.isNotBlank(model.getEndTime())){
			start.append(" and s.create_time<=?");
			paras.add(model.getEndTime() + "-" + DateUtils.getDateDay(model.getEndTime()) + " 23:59:59");
		}
		if(StringUtils.isNotBlank(model.getNetworkId())){
			start.append(" and s.network_id=?");
			paras.add(model.getNetworkId());
		}else{
			start.append(" and s.network_id in (" + user.toNetWorkIdsStr()+")");
		}
		start.append(" GROUP BY s.ship_id)m ");
		return HomeFinanceAllModel.dao.findFirst(select + start.toString(), paras.toArray());
	}
	
	

}