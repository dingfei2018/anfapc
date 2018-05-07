package com.supyuan.front.index;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;

/**
 * 专线
 * 
 * @author TFC <2016-6-25>
 */
public class IndexSvc extends BaseService {

	public List<IndexLine> getHotLines(int size, String code) {
		long total = countCompanyLine(code);
		long startIndex = generateStartIndex(total, size);
		List<IndexLine> lines;
		StringBuffer bu = new StringBuffer("select from_city_code, from_region_code,to_city_code,to_region_code,id,price_heavy,price_small,frequency,getCompanyName(company_id) corpname,getLineRegion(from_city_code,from_region_code) as from_addr, getLineRegion(to_city_code,to_region_code) as to_addr,company_id from logistics_line where  is_live=1 ");
		bu.append("and from_city_code=? GROUP BY company_id order by create_time desc limit ?, ?");
		lines = IndexLine.dao.find(bu.toString(), code, startIndex, size);
		return lines;
	}
	
	public long countCompanyLine(String code){
		long count = Db.queryLong("select count(*) from (select company_id from logistics_line  where from_city_code=?  GROUP BY company_id) s", code);
		return count;
	}
	

	/*
	 * 查询热门专线，带翻页
	 * 
	 * @author TFC <2016-6-25>
	 */
	public Page<IndexLine> getHotLinesWithPage(Paginator paginator, String orderby) {
		Page<IndexLine> lines;
		lines = IndexLine.dao.paginate(paginator,
				"select price_heavy,price_small,frequency,getCompanyName(company_id) corpname,getRegion(from_region_code) as from_addr, getRegion(to_region_code) as to_addr,"
						+ "getCredit(company_id) as credit, getFeedback(company_id) as feedback ",
				" from logistics_line where is_live=1 " + orderby);
		return lines;
	}
	
	

	/*
	 * 根据订单id查询一条
	 */
	public IndexLine getLines(Integer id) {
		 IndexLine lines = IndexLine.dao.findFirst("select *, getCompanyName(company_id) corpname "
						+ " from logistics_line l where  is_live=1 and l.id="+id);
		return lines;
	}
	/*
	 * 查询黄金专线
	 */
	public List<IndexLine> getGoldLines(int size, String code){
		List<IndexLine> lines;
		StringBuffer bu = new StringBuffer(" select from_city_code, from_region_code,to_city_code,to_region_code,id,price_heavy,price_small,frequency,getCompanyName(company_id) corpname,getLineRegion(from_city_code,from_region_code) as from_addr, getLineRegion(to_city_code,to_region_code) as to_addr,company_id ");
		bu.append(" from logistics_line where  is_live=1 and from_city_code="+code+"  order by create_time desc limit ? ");
		lines = IndexLine.dao.find(bu.toString(),size);
		return lines;
		
	}

	
	
	public long generateStartIndex(long total, int size){
		if(total>size){
			long ram = total-size;
			ram = (long)(Math.random()*ram);
			return ram;
		}
		return 0;
	}

}
