package com.supyuan.front.line;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.front.index.IndexLine;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.modules.addressbook.Address;

/**
 * 线路查询
 * 
 * @author dingfei <2017年7月5日>
 *
 */
public class LineSvc extends BaseService {

	private final static Log log = Log.getLog(LineSvc.class);
	/**
	 * 首页查询
	 * @param fromCityCode
	 * @param fromRegionCode
	 * @param toCityCode
	 * @param toRegionCode
	 * @param parkName
	 * @param orderby
	 * @return
	 */
	public Page<IndexLine> getLinesByParam(Paginator paginator,String fromCityCode,String fromRegionCode, String toCityCode,String toRegionCode,String columnName,String orderby){
		Page<IndexLine> lines = null;
		StringBuilder sql = new StringBuilder(
				"  from logistics_line line ");
		sql.append(" left join logistics_park park on park.id=line.logistics_park_id where 1=1 and is_live = 1 ");
		 if  (StringUtils.isNotBlank(fromRegionCode)) {
			sql.append(" and from_region_code='" + fromRegionCode + "'");
		}if(StringUtils.isNotBlank(fromCityCode)) {
			sql.append(" and from_city_code='" + fromCityCode + "'");
		}if(StringUtils.isNotBlank(toRegionCode)){
			sql.append(" and to_region_code='" + toRegionCode + "'");
		}if(StringUtils.isNotBlank(toCityCode))  {
		   sql.append(" and to_city_code='" + toCityCode + "'");
		}
		if(StringUtils.isNotBlank(columnName) && orderby.equals("asc")){
			sql.append( "order by " + columnName+"+0" + " desc,rand() ");
		}
		else if(StringUtils.isNotBlank(columnName) && orderby.equals("desc")){
			sql.append( "order by " + columnName+"+0" + " asc,rand() ");
		}
		else{
			sql.append(" order by rand() ");
		}
		lines = IndexLine.dao.paginate(paginator, "select line.id,line.from_city_code,line.from_region_code,line.to_city_code,line.to_region_code,logistics_park_id,company_id, getLineRegion (from_city_code,from_region_code) AS from_addr,getLineRegion (to_city_code,to_region_code) AS to_addr,getCompanyName (company_id) AS corpname,getParkName (logistics_park_id) AS parkname,price_heavy,price_small,frequency,address,starting_price", sql.toString());
		return lines;
		
		
	}
	/**
	 * 
	 * @param fromCityCode
	 * @param fromRegionCode
	 * @param toCityCode
	 * @param toRegionCode
	 * @param corpname
	 * @return
	 */
	public Page<IndexLine> getLineParkList(Paginator paginator,String fromCityCode,String fromRegionCode,String toCityCode, String toRegionCode,String columnName,String orderby,String parkId) {
		Page<IndexLine> lines = null;
		StringBuilder sql = new StringBuilder(
				"  from logistics_line line ");
		sql.append(" left join company com  on com.id= line.company_id where 1=1 and is_live = 1 and line.logistics_park_id='"+parkId+"' ");
		 if  (StringUtils.isNotBlank(fromRegionCode)) {
			sql.append(" and from_region_code='" + fromRegionCode + "'");
		}if(StringUtils.isNotBlank(fromCityCode)) {
			sql.append(" and from_city_code='" + fromCityCode + "'");
		}if(StringUtils.isNotBlank(toRegionCode)){
			sql.append(" and to_region_code='" + toRegionCode + "'");
		}if(StringUtils.isNotBlank(toCityCode))  {
		   sql.append(" and to_city_code='" + toCityCode + "'");
		}
		sql.append(" GROUP BY company_id ");
		if(StringUtils.isNotBlank(columnName) && orderby.equals("asc")){
			sql.append( "order by " + columnName + " desc ");
		}
		if(StringUtils.isNotBlank(columnName) && orderby.equals("desc")){
			sql.append( "order by " + columnName + " asc ");
		}
		lines = IndexLine.dao.paginate(paginator, "select line.id,line.from_city_code,line.from_region_code,line.to_city_code,line.to_region_code,logistics_park_id,company_id, getLineRegion (from_city_code,from_region_code) AS from_addr,getLineRegion (to_city_code,to_region_code) AS to_addr,getCompanyName (company_id) AS corpname,getParkName (logistics_park_id) AS parkname,price_heavy,price_small,frequency,address,starting_price,survive_time", sql.toString());
		return lines;

	}
	

	/**
	 * 根据公司id查找专线
	 * @author liangxp
	 * Date:2017年7月13日下午2:20:43 
	 *
	 * @param companyId
	 * @param fromRegionCode
	 * @param toRegionCode
	 * @return
	 */
	public List<IndexLine> getLineByCompanyId(Integer companyId, String fromRegionCode, String toRegionCode) {
		List<IndexLine> list = null;
		StringBuilder sql = new StringBuilder(
				"select line.id,line.from_region_code,line.to_region_code, company_id, starting_price, getCompanyName (company_id) AS corpname,getCompanyPhone (company_id) AS phone,survive_time,  price_heavy,price_small,frequency from logistics_line line");
		 sql.append(" where  is_live = 1 and line.company_id=? and from_region_code=? and to_region_code=?");
		list = IndexLine.dao.find(sql.toString(), companyId, fromRegionCode, toRegionCode);
		return list;
	}
	
	
	/**
	 * 专线id查询专线
	 * @author liangxp
	 * Date:2017年8月17日下午3:38:06 
	 *
	 * @param id
	 * @return
	 */
	public IndexLine getLineById(Integer id) {
		IndexLine line = null;
		StringBuilder sql = new StringBuilder(
				"select id,from_city_code,from_region_code, getLineRegion(from_city_code,from_region_code) fromAddress,to_city_code,to_region_code, getLineRegion(to_city_code,to_region_code) toAddress, company_id, starting_price, getUserIdByCompanyId (company_id) AS userId,getCompanyPhone (company_id) AS phone,getParkName (logistics_park_id) AS parkname,survive_time,  price_heavy,price_small,frequency,address,is_sale,network_id,logistics_park_id from logistics_line");
		sql.append(" where  is_live = 1 and id=?");
		line = IndexLine.dao.findFirst(sql.toString(), id);
		return line;
	}
	/**
	 *  根据公司ID查询专线列表
	 * @param paginator
	 * @param companyId
	 * @return
	 */
	public List<IndexLine> getPageLine(Integer userId){
		List<IndexLine> lines = null;
		StringBuffer sql=new StringBuffer();
		sql.append(" select l.id,l.price_heavy,l.price_small,l.survive_time,l.frequency,getLineRegion (from_city_code,from_region_code) AS from_addr,getLineRegion (to_city_code,to_region_code) as to_addr,getBookLongRegion(n.sub_addr_uuid) as netadd,is_sale");
		sql.append("  from logistics_line l ");
		sql.append(" left join logistics_network n  on n.id=l.network_id ");
		sql.append(" left join address_book a  on a.uuid=n.sub_addr_uuid ");
		sql.append(" where  l.is_live=1  and l.company_id=? order by l.is_sale desc ");
		lines=IndexLine.dao.find(sql.toString(),userId);
		return lines;
		
	}
	/**
	 * 特价专线
	 * @param paginator
	 * @param fromCityCode
	 * @param fromRegionCode
	 * @param toCityCode
	 * @param toRegionCode
	 * @param columnName
	 * @param orderby
	 * @return
	 */
	public Page<IndexLine> getSpecialLines(Paginator paginator,String fromCityCode,String fromRegionCode, String toCityCode,String toRegionCode,String columnName,String orderby){
		Page<IndexLine> lines = null;
		StringBuilder sql = new StringBuilder(
				"  from logistics_line line ");
		sql.append(" left join logistics_park park on park.id=line.logistics_park_id where 1=1 and is_live = 1 and is_sale=1");
		 if  (StringUtils.isNotBlank(fromRegionCode)) {
			sql.append(" and from_region_code='" + fromRegionCode + "'");
		}if(StringUtils.isNotBlank(fromCityCode)) {
			sql.append(" and from_city_code='" + fromCityCode + "'");
		}if(StringUtils.isNotBlank(toRegionCode)){
			sql.append(" and to_region_code='" + toRegionCode + "'");
		}if(StringUtils.isNotBlank(toCityCode))  {
		   sql.append(" and to_city_code='" + toCityCode + "'");
		}
		if(StringUtils.isNotBlank(columnName) && orderby.equals("asc")){
			sql.append( "order by " + columnName+"+0" + " desc,rand() ");
		}
		else if(StringUtils.isNotBlank(columnName) && orderby.equals("desc")){
			sql.append( "order by " + columnName+"+0" + " asc,rand() ");
		}
		else{
			sql.append(" order by rand() ");
		}
		lines = IndexLine.dao.paginate(paginator, "select line.id,line.from_city_code,line.from_region_code,line.to_city_code,line.to_region_code,logistics_park_id,company_id, getLineRegion (from_city_code,from_region_code) AS from_addr,getLineRegion (to_city_code,to_region_code) AS to_addr,getCompanyName (company_id) AS corpname,getParkName (logistics_park_id) AS parkname,price_heavy,price_small,frequency,address,starting_price", sql.toString());
		return lines;
	}
	/**
	 * 验证是否发布过该专线
	 * @param fromCityCode
	 * @param fromRegionCode
	 * @param toCityCode
	 * @param networkId
	 * @return
	 */
	public List<IndexLine> checkLine(String fromCityCode,String fromRegionCode, String toCityCode,int networkId){
		List<IndexLine> lines=null;
		StringBuilder sql=new StringBuilder("select line.id, getRegion(from_city_code) AS formcity,getRegion(to_city_code) AS tocity, getNetworkNameById(network_id) AS network from logistics_line line ");
		if(StringUtils.isNotBlank(fromRegionCode)){
			sql.append(" where  from_region_code='" + fromRegionCode + "'");
		}
		if(StringUtils.isNotBlank(fromCityCode)){
			sql.append(" and from_city_code='" + fromCityCode + "'");
		}
		if(StringUtils.isNotBlank(toCityCode)){
			
			  sql.append(" and to_city_code='" + toCityCode + "'");
		}
		sql.append(" and network_id="+networkId);
		lines=IndexLine.dao.find(sql.toString());
		
		return lines;	
	}
	/**
	 *  保存专线
	 * @param line
	 * @param address
	 * @return
	 */
	public boolean  saveLines(Address address,List<IndexLine> lines){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(!address.save()) return false;
				int[] batchSave = Db.batchSave(lines, lines.size());
				if(batchSave.length!=lines.size())return false;
				return true;	
			}
		});
		return tx;
	}
	/**
	 * 修改专线
	 * @param address
	 * @param indexLine
	 * @return
	 */
	public boolean updateLines(Address address,IndexLine indexLine){
		

		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(!address.save()) return false;
				if(!indexLine.update())return false;
				return true;	
			}
		});
		return tx;
		
	}
	

}
