package com.supyuan.modules.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.jfinal.core.ActionKey;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.logisticspark.LogisticsPark;
import com.supyuan.front.logisticspark.LogisticsParkSvc;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.Region;
import com.supyuan.modules.result.ResultAnfa;

/**
 * 地图数据相关查询Api接口
 * 
 * @author TFC <2017-06-25>
 */
@ControllerBind(controllerKey = "/map")
public class MapController extends BaseProjectController {
	private static final String path = "/pages/modules/map/";

	/**
	 * ip 地址定位
	 * 
	 * @param ip
	 *            String 客户端ip地址
	 * @throws Exception
	 */
	public void locationIP() throws Exception {
		String ip = getPara("ip");
		Region region = new MapSvc().getRegionFromTaobao(ip);
		ResultAnfa result = new ResultAnfa();
		if (region != null) {
			result.setStatus(ResultAnfa.Status.OK);
			result.setMsg("");
			result.setContent(region);
		} else {
			result.setStatus(ResultAnfa.Status.NOT_FIND);
			result.setMsg("无法定位");
		}
		String callback = getPara("callback");
		renderJson(callbackJson(callback, result));
	}

	public void getDistance() throws Exception {
		String[] from_addr = getPara("from").split("\\|");
		String[] to_addr = getPara("to").split("\\|");

		Map<String, String> origin = new HashMap<String, String>();
		origin.put("address", from_addr[0]);
		origin.put("city", from_addr.length>1 ? from_addr[1] : "" );
		Map<String, String> destination = new HashMap<String, String>();
		destination.put("address", to_addr[0]);
		destination.put("city", to_addr.length>1 ? to_addr[1] : "" );
        System.out.println(origin);
        System.out.println(destination);
		GdMap gd = new GdMap();
		int distance = gd.getDistance(origin, destination);
		int distance_km= distance/1000;
        
		Map<String, Integer> content = new HashMap<String, Integer>();
		content.put("distance", distance);
		content.put("distance_km", distance_km);
		
		ResultAnfa result = new ResultAnfa();
		if (distance != 0) {
			result.setStatus(ResultAnfa.Status.OK);
			result.setMsg("");
			result.setContent(content);
		} else {
			result.setStatus(ResultAnfa.Status.NOT_FIND);
			result.setMsg("无法测距离");
		}
		String callback = getPara("callback");
		renderJson(callbackJson(callback, result));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void getLocationMap() {
		String address = getPara("address");
		String zoom = getPara("zoom") != null ? getPara("zoom"): "15";
		String size = getPara("size") != null ? getPara("size"): "750*300";
		String mapuri = new GdMap().getStaticMap(address,zoom,size);
		
		setAttr("mapuri", mapuri);

		renderJsp(path + "map.jsp");

	}
	public void getAddressMap() {
		String address = getPara("address");
		String corname = getPara("corname");
		setAttr("address", address);
		setAttr("corname", corname);
		renderJsp(path + "map.jsp");
	}
	
	
	public void getPathMap() {
		renderJsp(path + "path.jsp");
	}
	
	//@ActionKey("/map/allparks")
	@ActionKey("/park/around")
	public void showParkMap() throws Exception {
		List<LogisticsPark> logisticsParkList = new LogisticsParkSvc().findAllPark();
		System.out.println(logisticsParkList);
		setAttr("parkList", logisticsParkList);
		
		Gson gson = new Gson();
		setAttr("parkListJson", gson.toJson(logisticsParkList));
		renderJsp(path + "parkMap.jsp");
	}
	
	public void getshipRouter() throws Exception {	
		renderJsp(path + "ship_router.jsp");
	}
}
