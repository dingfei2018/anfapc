package com.supyuan.kd.loading;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

import com.supyuan.jfinal.base.BindModel;

public class LoadListSearchModel extends BindModel{
	private int nextNetworkId;
	private String startTime;
	private String endTime;
	private  String status;

	private String time;
	private String loadSn;
	
	private int networkId;
	
	private int loadToNetworkId;
	
	private int transType;

	private String driverName;
	
	private String truckNumber;
	
	private String deliveryFrom;
	
	private String deliveryTo;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLoadSn() {
		try {
			return StringUtils.isNotBlank(loadSn)? URLDecoder.decode(loadSn , "UTF-8").trim():null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
	}


	public int getLoadToNetworkId() {
		return loadToNetworkId;
	}

	public void setLoadToNetworkId(int loadToNetworkId) {
		this.loadToNetworkId = loadToNetworkId;
	}

	public int getNetworkId() {
		return networkId;
	}

	public void setNetworkId(int networkId) {
		this.networkId = networkId;
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

	public int getNextNetworkId() {
		return nextNetworkId;
	}

	public void setNextNetworkId(int nextNetworkId) {
		this.nextNetworkId = nextNetworkId;
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

	public String getDeliveryTo() {
		return deliveryTo;
	}

	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}




}
