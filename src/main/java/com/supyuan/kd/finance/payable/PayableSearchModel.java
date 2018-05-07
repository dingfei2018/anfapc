package com.supyuan.kd.finance.payable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

import com.supyuan.jfinal.base.BindModel;

/**
 * 应付搜索类
 */
public class PayableSearchModel extends BindModel{

	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//配载网点
	private Integer networkId;
	//到货网点
	private Integer loadToNetworkId;
	//配载状态
	private String deliveryStatus;
	//运输类型
	private String transportType;
	//配载单号
	private String loadSn;
	//付款状态
	private String feeStatus;
	//司机名字
	private String driverName;
	//车牌号
	private String truckNumber;
	//出发地
	private String deliveryFrom;
	//到达地
	private String deliveryTo;
	//结算状态 0:未结算 1:结算
	private String fillStatus;

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

	public Integer getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}

	public Integer getLoadToNetworkId() {
		return loadToNetworkId;
	}

	public void setLoadToNetworkId(Integer loadToNetworkId) {
		this.loadToNetworkId = loadToNetworkId;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getLoadSn() {
		return loadSn;
	}

	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	public String getFillStatus() {
		return fillStatus;
	}

	public void setFillStatus(String fillStatus) {
		this.fillStatus = fillStatus;
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

	public String getDeliveryTo() {
		return deliveryTo;
	}

	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}
	
	

}
