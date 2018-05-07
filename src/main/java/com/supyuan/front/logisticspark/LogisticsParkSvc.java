package com.supyuan.front.logisticspark;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.front.company.ShippingOrder;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.kd.customer.Customer;

/**
 * 物流园服务类
 * @author liangxp
 *
 * Date:2017年9月20日上午11:59:16 
 * 
 * @email liangxp@anfawuliu.com
 */
public class LogisticsParkSvc extends BaseService {

	
	/**
	 * 物流园历史交易量按物流园时间分组排序
	 * @author liangxp
	 * Date:2017年9月20日上午11:45:32 
	 *
	 * @param paginator
	 * @param orderBy
	 * @param colum
	 * @return
	 */
	public Page<LogisticsPark> statisticsGroupOrders(Paginator paginator, String parkName, String orderBy, String colum){
		Page<LogisticsPark> parks;
		StringBuilder bu = new StringBuilder(",date_format(s.create_time, '%Y-%m-%d') cdate  from shipping_order s left join logistics_line l on s.line_id=l.id left join logistics_park p on l.logistics_park_id=p.id");
		if(StringUtils.isNotBlank(parkName)){ 
			bu.append("  where p.park_name='" +parkName +"'");
		}
		bu.append(" GROUP BY l.logistics_park_id,cdate  ");
		if(StringUtils.isNotBlank(orderBy)&&StringUtils.isNotBlank(colum)){
			bu.append(" ORDER BY ");
			bu.append(colum);
			bu.append(" ");
			bu.append(StringUtils.isNotBlank(orderBy)?orderBy:"desc");
		}else{
			bu.append(" ORDER BY cdate desc");
		}
		parks = LogisticsPark.dao.paginate(paginator, "select  count(*) amount, p.id, p.park_name", bu.toString());
		return parks;
	}
	
	
	/**
	 * 物流园历史交易量按物流园分组
	 * @author liangxp
	 * Date:2017年9月25日上午11:33:08 
	 *
	 * @param paginator
	 * @param parkName
	 * @param orderBy
	 * @param colum
	 * @return
	 */
	public Page<LogisticsPark> statisticsOrders(Paginator paginator, String parkName, String orderBy, String colum){
		Page<LogisticsPark> parks;
		StringBuilder bu = new StringBuilder(" from (select count(*) amount ,p.park_name, p.id from shipping_order s left join logistics_line l on s.line_id=l.id left join logistics_park p on l.logistics_park_id=p.id where s.ship_status=2  GROUP BY l.logistics_park_id ) m ");
		Object[] args =  new Object[]{};
		if(StringUtils.isNotBlank(parkName)){ 
			bu.append("  where m.park_name like ? ");
			args = new Object[]{parkName +"%"};
		}
		bu.append(" ORDER BY ");
		bu.append(colum);
		bu.append(" ");
		if("desc".equals(orderBy)){
			bu.append(StringUtils.isNotBlank(orderBy)?orderBy:"desc");		
		}else if("asc".equals(orderBy)){
			bu.append(StringUtils.isNotBlank(orderBy)?orderBy:"desc");
		}else{
			bu.append("desc");
		}
		parks = LogisticsPark.dao.paginate(paginator, "select  m.amount, m.id, m.park_name", bu.toString(), args);
		return parks;
	}
	

	
	/**
	 * 通过物流公司查询
	 * @author liangxp
	 * Date:2017年9月21日下午12:00:32 
	 *
	 * @param paginator
	 * @param parkName
	 * @param orderby
	 * @param colum
	 * @return
	 */
	public Page<LogisticsPark> companyParkList(Paginator paginator, String corpName){
		Page<LogisticsPark> parks;
		StringBuilder bu = new StringBuilder(" from company c LEFT JOIN sys_user u on u.userid=c.user_id  left join scm_verify s on s.user_id=c.user_id left join logistics_network l on c.id=l.company_id where  u.status>0 and s.flow_from=1 and (s.status=2 or s.status=4)");
		Object[]args = new Object[]{};
		if(StringUtils.isNotBlank(corpName)){
			args= new Object[]{"%"+ corpName+"%"};
			bu.append("and c.corpname like ? ");
		}
		//bu.append("  and getScmVerifyStatus(c.user_id, 1)=2");
		bu.append("  group by c.id order by rand(), c.id desc");
		parks = LogisticsPark.dao.paginate(paginator, "select c.id, c.corpname, count(l.id) amount", bu.toString(),args);
		return parks;
	}
	
	/**
	 * 首页按物流园名称和物流地址查询
	 * 
	 * @param paginator
	 * @param parkName
	 * @param regCode
	 * @param orderby
	 * @param colum
	 * @return
	 */

	public Page<LogisticsPark> searchLogisticsPark(Paginator paginator, String parkName,String regCity, String regCode, String orderby,
			String colum) {
		Page<LogisticsPark> parks;
		StringBuilder bu = new StringBuilder();
		bu.append(" from logistics_park p ");
		bu.append(" left join address_book a on a.uuid=p.park_addr_uuid where 1=1 and del_flag=0");
		if (StringUtils.isNotBlank(parkName)) {
			bu.append("  and  p.park_name like '%"+parkName+"%'");
		}
		if(StringUtils.isNotBlank(regCity)){
			regCity = regCity.substring(0, 4);
			 bu.append("  and  a.region_code like '"+regCity+"%'");
		}
		if (StringUtils.isNotBlank(regCode)) {
		    bu.append("  and  a.region_code= "+regCode+"");
		}
		bu.append(" group by park_name ");
		if (StringUtils.isNotBlank(orderby) && StringUtils.isNotBlank(colum)) {
			bu.append(" order by ");
			bu.append(colum);
			bu.append(" ");
			bu.append(orderby);
		}
		parks = LogisticsPark.dao.paginate(paginator," select p.id,p.park_name as parkName,getBookLongRegion(uuid) as longAddr,getBookRegion(uuid) AS addr, a.tail_address as tailAdd ,(SELECT  COUNT(*) AS linecount FROM logistics_line l WHERE l.logistics_park_id=p.id and is_live=1) AS linecount,(SELECT  COUNT(*) AS linecount FROM logistics_line l WHERE l.logistics_park_id=p.id and is_live=1) AS goldcount", bu.toString());
		return parks;
	}
	/**
	 *  历史交易量查看详情
	 * @param paginator
	 * @param fromCode
	 * @param toCode
	 * @param parkId
	 * @return
	 */
	public Page<ShippingOrder> historyOrders(Paginator paginator,String fromCity, String fromCode,String toCity, String toCode, String parkId) {
		Page<ShippingOrder> orders;

		StringBuilder bu = new StringBuilder();
		bu.append(" from shipping_order s ");
		bu.append(" left join  address_book a  on a.uuid=s.from_addr_uuid ");
		bu.append(" left join address_book a1  on a1.uuid=s.to_addr_uuid ");
		bu.append(" left join logistics_line l  on l.id=s.line_id ");
		bu.append(" where 1=1  and l.logistics_park_id="+parkId+" ");
		if (StringUtils.isNotBlank(fromCode)) {
			bu.append(" and  a.region_code="+fromCode+" ");
		}if(StringUtils.isNotBlank(fromCity)){
			bu.append(" and  a.region_code like '"+fromCity.substring(0, 4)+"%'");
		}
		if (StringUtils.isNotBlank(toCode)) {
			bu.append(" and  a1.region_code="+toCode+"");
		}if(StringUtils.isNotBlank(toCity)){
			bu.append(" and  a1.region_code like '"+toCity.substring(0,4)+"%'");
		}
		bu.append(" order by s.create_time desc");

		orders = ShippingOrder.dao.paginate(paginator, "select s.order_number , a.uuid,getBookRegion(s.from_addr_uuid) as fromadd,getBookRegion(to_addr_uuid) as toadd,getCompanyName(l.company_id) as comname,getParkName(l.logistics_park_id) as parkname,date_format(s.create_time, '%Y/%m/%d %H:%i') as create_time ",bu.toString());
		return orders;

	}
	/**
	 * 根据物流园名称查询
	 * @param corpName
	 * @param userId
	 * @return
	 */
	public List<LogisticsPark> ParkListByParkName(String parkName){
		List<LogisticsPark> parkList;
		StringBuilder bu = new StringBuilder();
		if(StringUtils.isNotBlank(parkName)) {
			bu.append("select id, park_name from logistics_park where park_name like '"+parkName+"%'  and del_flag=0  ORDER BY id desc");
		}else{
			bu.append("select id, park_name from logistics_park where del_flag=0  ORDER BY id desc LIMIT 10");
		}
		parkList = LogisticsPark.dao.find(bu.toString());
		return parkList;
	}
	
	/**
	 * 根据物流园名称查询 分页
	 * @param parkName
	 * @return
	 */
	public Page<LogisticsPark> ParkListByParkName1(Paginator paginator,String parkName){
		
		Page<LogisticsPark> LogisticsParks;
		StringBuilder select = new StringBuilder("select id, park_name ");
		StringBuilder builder=new StringBuilder();
		builder.append(" from logistics_park where del_flag=0");
		if(StringUtils.isNotBlank(parkName)) {
			builder.append("and park_name like '%"+parkName+"%'   ");
		}
		builder.append("  ORDER BY id desc");
		
		LogisticsParks = LogisticsPark.dao.paginate(paginator, select.toString(), builder.toString());
		return LogisticsParks;
	}
	
	
	
	
	public List<LogisticsPark> findAllPark(){
		List<LogisticsPark> parkList;
		parkList = LogisticsPark.dao.find("SELECT id, park_name, getUUIDFullAddressLocation(park_addr_uuid) AS location, park_remark FROM logistics_park WHERE del_flag =0  ORDER BY id DESC");
		return parkList;
	}
	

	/**
	 * 根据物流园Id查询线路并分页
	 * 
	 * @param parkId
	 * @return
	 */

	public LogisticsPark getLineByParkId(String parkId) {
	    return LogisticsPark.dao.findFirst("select id,park_name from logistics_park where id="+parkId);

	}
	
}
