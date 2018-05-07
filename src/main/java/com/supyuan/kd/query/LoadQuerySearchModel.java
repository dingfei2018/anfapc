package com.supyuan.kd.query;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

import com.supyuan.jfinal.base.BindModel;

/**
 * 按配载查询参数
 * @author dingfei
 *
 * @date 2017年12月18日 上午11:07:21
 */
public class LoadQuerySearchModel extends BindModel {
	
	private String loadSn;
	
	private String startTime;

	private String endTime;
	
	private String driverName;
	
	private String truckNumber;
	

	private String deliveryFrom;
	
	private String deliveryTo;
	
	private int loadNetworkId;
	
	
	public int getLoadNetworkId() {
		return loadNetworkId;
	}

	public void setLoadNetworkId(int loadNetworkId) {
		this.loadNetworkId = loadNetworkId;
	}

	public String getLoadSn() {
		return loadSn;
	}

	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDriverName() {

		try {
			return StringUtils.isNotBlank(driverName)? URLDecoder.decode(driverName , "UTF-8").trim():null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getTruckNumber() {
		try {
			return StringUtils.isNotBlank(truckNumber)? URLDecoder.decode(truckNumber , "UTF-8").trim():null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}

	public String getDeliveryFrom() {
		return deliveryFrom;
	}

	public void setDeliveryFrom(String deliveryFrom) {
		this.deliveryFrom = deliveryFrom;
	}

	public String getDeliveryTo() {
		return deliveryTo;
	}

	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}

	

}
