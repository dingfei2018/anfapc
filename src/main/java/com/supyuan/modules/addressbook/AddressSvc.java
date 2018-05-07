package com.supyuan.modules.addressbook;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.company.Shop;
import com.supyuan.jfinal.base.BaseService;

/**
 * 地址，地区数据查询
 * 
 * @author TFC <2016-6-25>
 */
public class AddressSvc extends BaseService {

	/*
	 * 根据UUID来查询地址数据
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param uuid String 地址索引UUID
	 * 
	 * @return 返回Address实例
	 */
	public Address findByUUID(String uuid) {
		Address addr = Address.dao.findFirst(
				"SELECT uuid,getRegion(region_code) as region, region_code as regcode, getTownName(town_code) as town,tail_address,FROM_UNIXTIME(create_time) as create_time   FROM address_book where uuid ='"+uuid+"'");
		return addr;
	}

	/*
	 * 根据用户id来查询地址数据
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param user_id 用户id
	 * 
	 * @return 返回Address实例
	 */
	public Address findByUserID(int user_id) {
		Company company = new CompanySvc().getCompany(user_id);
		return findByUUID(company.getStr("corp_addr_uuid"));
	}

	/*
	 * 根据店铺id来查询地址数据
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param ship_id 店铺id
	 * 
	 * @return 返回Address实例
	 */
	public Address findByShopID(int shop_id) {
		Shop shop = Shop.dao.findById(shop_id);
		return findByUserID(shop.getInt("user_id"));
	}

	/*
	 * 根据公司id来查询地址数据
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param corp_id 公司id
	 * 
	 * @return 返回Address实例
	 */
	public Address findByCorpID(int corp_id) {
		Company company = new CompanySvc().getCompanyById(corp_id);
		return findByUUID(company.getStr("corp_addr_uuid"));
	}

	/*
	 * 查询所有省份和直辖市、行政区
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @return 返回List<Region>
	 */
	public List<Region> findProvince() {
		List<Region> list = Region.dao.find(
				"SELECT * FROM library_region where region_type = 1 or region_type = 2   order by key_letter  asc; ");
		return list;
	}

	/*
	 * 查询该省份下的地级市
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param code String 省份代号
	 * 
	 * @return 返回List<Region>
	 */
	public List<Region> findCityByCode(String code) {
		List<Region> list = Region.dao.find("SELECT * FROM library_region where parent_code ='" + code
				+ "' and region_type = 3 order by key_letter  asc; ");
		return list;
	}

	/*
	 * 查询该地级市下所有县或者区
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param code String 地级市代号
	 * 
	 * @return 返回List<Region>
	 */
	public List<Region> findCountyByCode(String code) {
		List<Region> list = Region.dao.find("SELECT * FROM library_region where parent_code ='" + code
				+ "' and region_type = 4 order by  key_letter  asc; ");
		return list;
	}

	/*
	 * 查询县下所有城镇、乡或者市区下所有街道
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param code String 县、区代号
	 * 
	 * @return 返回List<Region>
	 */
	public List<Town> findTownByCode(String code) {
		List<Town> list = Town.dao
				.find("SELECT * FROM library_town where region_code ='" + code + "' order by  key_letter  asc; ");
		return list;
	}

	/*
	 * 根据UUID获取短地址，如：广州 白云，深圳 龙岗，衡阳市 祁东县
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param uuid 地址uuid
	 * 
	 * @return 返回短地址字符串
	 */
	public String getShortAddr(String uuid) {
		Record result = Db.findFirst("SELECT getRegion(region_code) as short_addr  FROM address_book where uuid =? limit 1", uuid);
		return result.getStr("short_addr");
	}

	/*
	 * 根据UUID获取详细地址，如：广东广州白云区石牌桥街壬丰大夏1801
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param uuid 地址uuid
	 * 
	 * @return 返回短地址字符串
	 */
	public String getFullAddr(String uuid) {
		Record result = Db.findFirst("SELECT tail_address,getLongRegion(region_code) as head_addr  FROM address_book where uuid = ? limit 1", uuid);
		return result==null?"":result.getStr("head_addr") + result.getStr("tail_address");
	}

	public Region getRegionByName(String province, String city, String county) {
		String region_name = county.equals("") ? city : county;
		String sql = "select * from library_region where region_name=? AND province=? AND city=? limit 1";
		Region region = new Region().dao().findFirst(sql, region_name, province, city);
		return region;
	}
	
	public Region getCityByName(String province, String city) {
		String sql = "select * from library_region where province=? AND city=? limit 1";
		Region region = new Region().dao().findFirst(sql, province, city);
		return region;
	}
	

	public Region getRegiondownName(Integer fromregioncode) {
		String sql = "select * from library_region where region_code=? limit 1";
		Region region = new Region().dao().findFirst(sql, fromregioncode);
		return region;
	}

	/*
	 * 根据专线的起始或目的地地址
	 * 
	 * @author TFC <2016-6-25>
	 * 
	 * @param code String 地区代码
	 * 
	 * @param type Integer 地区类别
	 * 
	 * @return 返回Region
	 */
	public Region getLineRegion(String code, Integer type) {
		String sql = "select * from library_region where region_code=? and region_type=? limit 1";
		Region region = new Region().dao().findFirst(sql, code, type);
		return region;
	}

}
