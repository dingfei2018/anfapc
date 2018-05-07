package com.supyuan.modules.map;

import java.util.Map;

import com.supyuan.jfinal.base.BaseService;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.modules.addressbook.Region;

/**
 * 地址，地区数据查询
 * 
 * @author TFC <2016-6-25>
 */
public class MapSvc extends BaseService {
	public Region getRegionFromBaidu(String ip) throws Exception {

		Map<String, Object> result = new BaiduMap().locationIP(ip);

		if (result == null) {
			return null;
		}

		String province = (String) result.get("province");
		String city = (String) result.get("city");
		String county = result.get("district").equals("") ? (String) result.get("district") : "";
		// String town = addr.get("street").equals("") ? (String)
		// addr.get("street") : "";
		Region region = new AddressSvc().getRegionByName(province, city, county);
		System.out.println(region.toString());
		return region;
	}

	public Region getRegionFromGD(String ip) throws Exception {

		Map<String, Object> result = new BaiduMap().locationIP(ip);

		if (result == null) {
			return null;
		}

		String province = (String) result.get("province");
		String city = (String) result.get("city");
		String county = result.get("district").equals("") ? (String) result.get("district") : "";
		// String town = addr.get("street").equals("") ? (String)
		// addr.get("street") : "";
		Region region = new AddressSvc().getRegionByName(province, city, county);
		System.out.println(region.toString());
		return region;
	}

	public Region getRegionFromTaobao(String ip) throws Exception {

		Map<String, Object> result = new TaobaoMap().locationIP(ip);

		if (result == null) {
			return null;
		}

		String province = (String) result.get("region");
		String city = (String) result.get("city");
		String county =  (String) result.get("county") ;
		// String town = addr.get("street").equals("") ? (String)
		// addr.get("street") : "";
		Region region = new AddressSvc().getRegionByName(province, city, county);
		System.out.println(region.toString());
		return region;
	}
}
