package com.supyuan.modules.map;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyuan.util.HttpClient;

public class TaobaoMap {
	private static final String MAP_API_HOST = "ip.taobao.com";
	private static final String MAP_IP_LOCATION = "/service/getIpInfo.php";
	/*
	 * Taobao ip result:
	 * { "code": 0, "data": { "country": "中国", "country_id": "CN", "area": "华南",
	 * "area_id": "800000", "region": "广东省", "region_id": "440000", "city":
	 * "广州市", "city_id": "440100", "county": "天河区", "county_id": "440106",
	 * "isp": "电信", "isp_id": "100017", "ip": "219.137.143.183" } }
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> locationIP(String ip) throws Exception {
		Map<String, String> paramValues = new HashMap<String, String>();
		paramValues.put("ip", ip);
		String datajson = HttpClient.Factory().Get(MAP_API_HOST, MAP_IP_LOCATION, paramValues);
		HashMap<String, Object> result = new ObjectMapper().readValue(datajson, HashMap.class);
		if (!result.get("code").equals(0)) {
			return null;
		}

		return (Map<String, Object>) result.get("data");
	}
}
