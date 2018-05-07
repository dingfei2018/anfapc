package com.supyuan.front.company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.front.index.IndexLine;
import com.supyuan.front.scm.ScmVerify;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.modules.addressbook.Address;

/**
 * 
 * @author liangxp
 *
 *         Date:2017年6月28日下午3:06:03
 * 
 * @email liangxp@anfawuliu.com
 */
public class CompanySvc extends BaseService {

	/**
	 * 公司专线集合
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月28日下午4:19:42
	 */
	public List<IndexLine> getCompanyLines(int companyId, int size) {
		List<IndexLine> lines;
		lines = IndexLine.dao
				.find("select getLongRegion(from_region_code) as from_addr, getLongRegion(to_region_code) as to_addr"
						+ " from logistics_line where company_id=? order by create_time limit " + size, companyId);
		return lines;
	}

	/**
	 * 公司专线总数量
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年7月4日上午11:24:11
	 */
	public long countCompanyLines(int companyId) {
		long count = Db.queryLong("select count(*) from logistics_line where is_live=1 and company_id=?", companyId);
		return count;
	}

	/**
	 * 查找公司专线
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月29日下午2:25:17
	 */
	public List<IndexLine> getCompanyLines(int companyId, String fromRegionCode, String toRegionCode, int size) {
		List<IndexLine> lines;
		StringBuilder sql = new StringBuilder("select id, survive_time, price_heavy,price_small,frequency, starting_price,is_sale, getLineRegion(from_city_code,from_region_code) as from_addr, getLineRegion(to_city_code,to_region_code) as to_addr from logistics_line where is_live=1 and company_id=?");
		Object[] args = null;
		if (StringUtils.isNotEmpty(fromRegionCode) && StringUtils.isNotEmpty(toRegionCode)) {
			sql.append("  and from_region_code like ? and to_region_code like ? order by is_sale desc");
			args = new Object[]{companyId, fromRegionCode.replace("00", "")+'%', toRegionCode.replace("00", "")+'%'};
		} else {
			sql.append("  order by is_sale desc");
			args = new Object[]{companyId};
		}
		if (size > 0) {
			sql.append(" limit " + size);
		}
		lines = IndexLine.dao.find(sql.toString(), args);
		return lines;
	}
	
	
	/**
	 * 网点专线
	 * @author liangxp
	 * Date:2017年10月12日上午9:35:08 
	 *
	 * @param companyId
	 * @param networkId
	 * @param size
	 * @return
	 */
	public Page<IndexLine> getNetWorkLines(Paginator paginator, int companyId, int networkId, boolean isHide) {
		//List<IndexLine> lines;
		Page<IndexLine> lines;
		StringBuilder sql = new StringBuilder();
		sql.append(" from logistics_line l left JOIN logistics_network n on l.network_id=n.id ");
		if(networkId>0){
			sql.append(" where l.is_live=1 and l.network_id=" + networkId);
		}else{
			sql.append(" where l.is_live=1 and l.company_id=" + companyId);
		}
		sql.append("  order by is_sale desc,l.id desc ");
		
		StringBuilder select = new StringBuilder("select l.id, l.survive_time, l.price_heavy, l.price_small, l.frequency, l.starting_price, l.is_sale, getLineRegion(l.from_city_code,l.from_region_code) as from_addr, getLineRegion(l.to_city_code,l.to_region_code) as to_addr,getBookLongRegion(n.sub_addr_uuid) region,n.sub_leader_name, n.sub_logistic_telphone");
		if(isHide){
			select.append(", n.sub_logistic_mobile");
		}
		
		lines = IndexLine.dao.paginate(paginator, select.toString(), sql.toString());
		return lines;
	}

	/**
	 * 
	 * @author liangxp Date:2017年7月24日上午10:08:41
	 *
	 * @param lineId
	 * @return
	 */
	public IndexLine getLine(int lineId) {
		IndexLine line = IndexLine.dao.findFirst(
				"select id, survive_time, price_heavy,price_small,frequency,starting_price, company_id, getCompanyName (company_id) AS corpname,getCompanyPhone (company_id) AS phone, from_city_code, to_city_code, from_region_code, getRegion(from_region_code) as from_addr, to_region_code, getRegion(to_region_code) as to_addr from logistics_line where is_live=1 and id=?",
				lineId);
		return line;
	}

	/**
	 * 公司网点集合
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月28日下午4:19:20
	 */
	public List<LogisticsNetwork> getLogisticsNetworks(int companyId, int size, boolean isHide) {
		StringBuilder sql = new StringBuilder("SELECT l.id, (select concat((select e.city from library_region e where e.region_code=a.region_code),'|', getUUIDFullAddress(a.uuid)) from address_book a where a.uuid=l.sub_addr_uuid) address,l.sub_leader_name, ");
		if(isHide)sql.append("l.sub_logistic_mobile,");
		sql.append(" l.sub_logistic_telphone, l.company_id FROM logistics_network l where l.company_id=?");
		if (size > 0) {
			sql.append("  limit " + size);
		}
		List<LogisticsNetwork> LogisticsNetworks = LogisticsNetwork.dao.find(sql.toString(), companyId);
		return LogisticsNetworks;
	}

	/**
	 * 公司基本信息
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月28日下午4:19:58
	 */
	public Company getCompany(int userId) {
		Company company = Company.dao.findFirst(
				"SELECT id, corpname, bussiness_code, charge_person, charge_mobile, corp_telphone, cert_img_uuid,corp_addr_uuid,getBookLongRegion(corp_addr_uuid) as corp_addr, getScmVerifyStatus(user_id, 1) is_certification, user_id"
						+ " FROM company where user_id=? order by id asc limit 1",
				userId);
		return company;
	}

	/**
	 * 公司基本信息
	 * 
	 * @author liangxp Date:2017年7月11日上午9:36:02
	 *
	 * @param id
	 * @return
	 */
	public Company getCompanyById(int id) {
		Company company = Company.dao.findFirst("SELECT id, corpname, bussiness_code, charge_person, charge_mobile, corp_telphone, cert_img_uuid,corp_addr_uuid,getBookLongRegion(corp_addr_uuid) as corp_addr, getScmVerifyStatus(user_id, 1) is_certification, user_id FROM company where id=?", id);
		return company;
	}

	/**
	 * 公司店铺信息
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月29日上午10:21:46
	 */
	public Shop getShop(int userId) {
		Shop shop = Shop.dao.findFirst(
				"SELECT id, shop_name, shop_subdomain, shop_desc_short, shop_desc, figure_img_gid, scroll_img_gid, create_time, update_time, theme, user_id"
						+ ", culture_desc, culture_img_gid, culture_jzg, culture_sm, show_yyzz, show_sfz, show_mobile, show_network_mobile FROM shop where user_id=?",
				userId);
		return shop;
	}

	/**
	 * 公司友情链接集合
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月28日下午4:22:24
	 */
	public List<FriendlyLink> getFriendlyLink(int shopId) {
		StringBuilder sql = new StringBuilder(
				"SELECT id, uri_link, uri_label, uri_remark, shop_id FROM library_friendly_link");
		if (shopId > 0) {
			sql.append(" where shop_id=" + shopId);
		}
		List<FriendlyLink> friendlyLink = FriendlyLink.dao.find(sql.toString());
		return friendlyLink;
	}

	/**
	 * 公司服务信息列表
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月28日下午4:26:17
	 */
	public List<CorpService> getCorpService(int shopId) {
		List<CorpService> corpService = CorpService.dao.find(
				"SELECT id, title, short_desc, long_desc, page_link, shop_id FROM library_corp_service where shop_id=?",
				shopId);
		return corpService;
	}

	/**
	 * 下运输订单
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年6月29日下午2:01:00
	 */
	public boolean addShippingOrder(ShippingOrder shippingOrder) {
		return shippingOrder.save();
	}
	
	
	/**
	 * 模糊查询公司
	 * @author liangxp
	 * Date:2017年9月21日下午1:54:42 
	 *
	 * @param corpName
	 * @param 当前用户id
	 * @return
	 */
	public List<Company> companyParkList(String corpName, int userId){
		List<Company> cps;
		StringBuilder bu = new StringBuilder();
		Object[] args = null;
		if(StringUtils.isNotBlank(corpName)) {//没有登录直接输入
			bu.append("select t.* from (select DISTINCT c.id, c.corpname,INSTR(c.corpname,?) AS a from company c LEFT JOIN sys_user u on u.userid=c.user_id  inner join scm_verify s on s.user_id=c.user_id where   u.status>0 and c.corpname like ? and s.flow_from=1 and (s.status=2 or s.status=4)  ORDER BY id desc limit 10) t order by t.a ");
			args = new Object[]{corpName,"%"+corpName+"%"};
		}else{
			if(userId>0){
				bu.append("select DISTINCT c.id,c.corpname from shipping_order o LEFT JOIN sys_user u on u.userid=o.user_id  left join company c on o.line_user_id=c.user_id where    u.status>0 and o.user_id=? order by o.create_time desc limit 10");
				args = new Object[]{userId};
			}else{
				return new ArrayList<Company>();
			}
		}
		cps = Company.dao.find(bu.toString(), args);
		return cps;
	}
	
	/**
	 * 通过用户ID查询公司是否存在
	 */
	public boolean  existCompany(int userId){
		StringBuilder bu = new StringBuilder();
		bu.append("select id, corpname from company  where  user_id=?");
		Company company=Company.dao.findFirst(bu.toString(), userId);
		if(company==null||company.get("corpname")==null||company.get("corpname").equals("")){
			return true;
		}
		return false;
		
	}
	public  ScmVerify checkVerifyStatus(int userId){
		StringBuilder bu = new StringBuilder();
		bu.append(" select * from scm_verify  where  user_id="+userId+" and status=4 ");
		return ScmVerify.dao.findFirst(bu.toString());
		
		
	}
	
	public boolean  saveCompany(Company company, Address address,SessionUser user){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(!company.update()) return false;
				if(!address.save()) return false;
				if(user.getUsertype() == 4){
					Shop shop =new CompanySvc().getShop(user.getUserId());
					shop.set("id", shop.get("id"));
					shop.set("create_time", new Date());
					shop.set("shop_name", company.get("corpname"));
					if(!shop.update()) return false;
				}
				ScmVerify scm=checkVerifyStatus(user.getUserId());
				if(scm==null){
				ScmVerify sv = new ScmVerify();
				sv.set("status", 4);
				sv.set("flow_from", 1);
				sv.set("flow_id", user.getUserId());
				sv.set("created", new Date());
				sv.set("user_id", user.getUserId());
				if(!sv.save()) return false;
				}
				return true;
				
			}
		});
		return tx;
	}
	
	
	
}
