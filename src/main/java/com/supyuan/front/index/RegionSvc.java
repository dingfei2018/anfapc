package com.supyuan.front.index;

import java.util.List;

import com.supyuan.jfinal.base.BaseService;
import com.supyuan.modules.addressbook.Region;

public class RegionSvc extends BaseService {

	/**
	 * 
	 * @author liangxp
	 * Date:2017年9月1日上午11:52:56 
	 *
	 * @return
	 */
	public List<Region> getRegions() {
		List<Region> regions = Region.dao.find("select * from library_region where region_type=2 or region_type=3 order BY key_letter");
		return regions;
	}


}
