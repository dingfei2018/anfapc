package com.supyuan.modules.map;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyuan.util.HttpClient;

/**
 * @see http://lbs.amap.com/api
 * @author max
 *
 */
public class GdMap {
	private static final String MAP_API_HOST = "restapi.amap.com";

	private static final String API_IP_LOCATION = "/v3/ip";
	private static final String API_GEOCODE = "/v3/geocode/geo";
	private static final String API_DISTANCE = "/v3/distance";
	private static final String API_STATICMAP = "/v3/staticmap";
	private static final String MAP_DEV_AK = "8fbde4c0159e5b4872d1a25619aa44a8";

	@SuppressWarnings("unchecked")
	public Map<String, Object> locationIP(String ip) throws Exception {
		Map<String, String> paramValues = new HashMap<String, String>();
		paramValues.put("ip", ip);
		paramValues.put("key", MAP_DEV_AK);
		String datajson = HttpClient.Factory().Get(MAP_API_HOST, API_IP_LOCATION, paramValues);
		HashMap<String, Object> result = new ObjectMapper().readValue(datajson, HashMap.class);
		if (!result.get("status").equals(1)) {
			return null;
		}

		return result;
	}

	/**
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 * @throws Exception
	 * 
	 * @see http://lbs.amap.com/api/webservice/guide/api/direction#distance
	 */
	public int getDistance(Map<String, String> origin, Map<String, String> destination) throws Exception {
		String location_origin = geocode(origin.get("address"), origin.get("city"));
		String location_destination = geocode(destination.get("address"), destination.get("city"));
		System.out.println(location_origin);
		System.out.println(location_destination);
		Map<String, String> paramValues = new HashMap<String, String>();
		paramValues.put("origins", location_origin);
		paramValues.put("destination", location_destination);
		paramValues.put("key", MAP_DEV_AK);
		String datajson = HttpClient.Factory().Get(MAP_API_HOST, API_DISTANCE, paramValues);

		@SuppressWarnings("unchecked")
		Map<String, Object> result = new ObjectMapper().readValue(datajson, HashMap.class);
		if (!result.get("status").equals("1")) {
			return 0;
		}

		@SuppressWarnings("unchecked")
		int distance = Integer
				.parseInt(((Map<String, Object>) ((ArrayList<Map<String, Object>>) result.get("results")).get(0))
						.get("distance").toString());

		return distance;
	}

	/**
	 * 静态地图
	 * 
	 * @see http://lbs.amap.com/api/webservice/guide/api/staticmaps/
	 * @param address
	 * @param zoom
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public String getStaticMap(String address, String zoom, String size) {
		String location = geocode(address, "");
		Map<String, String> paramValues = new HashMap<String, String>();
		paramValues.put("location", location);
		paramValues.put("zoom", zoom);
		paramValues.put("size", size);
		paramValues.put("key", MAP_DEV_AK);
		paramValues.put("markers", "mid,,A:" + location);
		String mapuri = "http://" + MAP_API_HOST + API_STATICMAP + HttpClient.getParamsOrderByKey("get", paramValues);
		return mapuri;
	}

	/**
	 * @see http://lbs.amap.com/api/webservice/guide/api/georegeo/
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String geocode(String address, String city) {
		Map<String, String> paramValues = new HashMap<String, String>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			paramValues.put("address", URLEncoder.encode(address, "UTF-8"));
			paramValues.put("city", URLEncoder.encode(city, "UTF-8"));
			paramValues.put("key", MAP_DEV_AK);
			String datajson = HttpClient.Factory().Get(MAP_API_HOST, API_GEOCODE, paramValues);
			result = new ObjectMapper().readValue(datajson, HashMap.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!result.get("status").equals("1")) {
			return null;
		}

		return ((Map<String, Object>) ((ArrayList<Map<String, Object>>) result.get("geocodes")).get(0)).get("location")
				.toString();
	}
}
