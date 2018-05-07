package com.supyuan.modules.addressbook;

import java.util.List;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.result.ResultAnfa;

/**
 * 数据地区地址查询Api接口
 * 
 * @author TFC <2017-06-25>
 */
@ControllerBind(controllerKey = "/addressbook")
public class AddressController extends BaseProjectController {

	/**
	 * 获取省份、直辖市、行政区
	 */
	public void getprovince() {
		List<Region> list = new AddressSvc().findProvince();
		ResultAnfa result = new ResultAnfa();
		result.setStatus(ResultAnfa.Status.OK);
		result.setContent(list);
		String callback = getPara("callback");
		renderJson(callbackJson(callback, result));
	}

	/**
	 * 获取地级市
	 */
	public void getcity() {
		String province_code = getPara("code");
		List<Region> list = new AddressSvc().findCityByCode(province_code);
		ResultAnfa result = new ResultAnfa();
		result.setStatus(ResultAnfa.Status.OK);
		result.setContent(list);
		String callback = getPara("callback");
		renderJson(callbackJson(callback, result));
	}

	/**
	 * 获取县城或区
	 */
	public void getcounty() {
		String city_code = getPara("code");
		List<Region> list = new AddressSvc().findCountyByCode(city_code);
		ResultAnfa result = new ResultAnfa();
		result.setStatus(ResultAnfa.Status.OK);
		result.setContent(list);
		String callback = getPara("callback");
		renderJson(callbackJson(callback, result));
	}

	/**
	 * 获取城镇，乡或街道
	 */
	public void gettown() {
		String county_code = getPara("code");
		List<Town> list = new AddressSvc().findTownByCode(county_code);
		String callback = getPara("callback");
		renderJson(callbackJson(callback, list));
	}
}
