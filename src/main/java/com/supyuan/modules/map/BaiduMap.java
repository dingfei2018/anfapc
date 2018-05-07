package com.supyuan.modules.map;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyuan.util.HttpClient;


/**
 * 百度地图API
 * 
 * @see http://lbsyun.baidu.com/
 * @see http://developer.baidu.com/map/jsdemo.htm#c1_3/http://lbsyun.baidu.com/cms/jsapi/reference/jsapi_reference.html#a7b0
 * @author max
 *
 */
public class BaiduMap {
	 private static final String BAIDU_API_HOST="api.map.baidu.com";
	 
	 private static final String BAIDU_IP_LOCATION="/location/ip";
	 
	 private static final String BAIDU_DEV_AK="hwdLiBYBfNbLOUfOy5Pfm5s8zKQK0inN";
	 
     @SuppressWarnings("unchecked")
	public Map<String, Object> locationIP(String ip) throws Exception{
    	 Map<String, String> paramValues = new HashMap<String, String>();  
         paramValues.put("ip", ip);  
         paramValues.put("ak", BAIDU_DEV_AK);  
    	 String datajson = HttpClient.Factory().Get(BAIDU_API_HOST, BAIDU_IP_LOCATION, paramValues);
    	 HashMap<String, Object> result = new ObjectMapper().readValue(datajson, HashMap.class);
    	 if( ! result.get("status").equals(0)){
    		 return null;
    	 }
    	 
    	 return  (Map<String, Object>) ((Map<String, Object>) result.get("content")).get("address_detail");
     }
}

