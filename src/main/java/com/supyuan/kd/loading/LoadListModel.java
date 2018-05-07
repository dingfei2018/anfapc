package com.supyuan.kd.loading;

import java.text.DecimalFormat;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 发车列表实体
 * @author liangxp
 *
 * Date:2018年1月2日上午11:44:43 
 * 
 * @email liangxp@anfawuliu.com
 */
public class LoadListModel {
	
	@FieldComment(name="配载单号")
	private String loadSn;
	
	@FieldComment(name="配载网点")
	private String  loadnetwork;
	
	@FieldComment(name="发车日期")
	private String  creatTime;
	
	@FieldComment(name="车牌号")
	private String  truckIdNumber;
	
	@FieldComment(name="司机")
	private String  driverName;
	
	@FieldComment(name="运输类型")
	private String  transportType;
	
	@FieldComment(name="出发地")
	private String  fromAddr;
	
	@FieldComment(name="到达地")
	private String  toAddr;
	
	@FieldComment(name="运费")
	private double  loadFee;
	
	@FieldComment(name="运单件数")
	private int  shipCount;
	
	@FieldComment(name="体积")
	private double  loadVolume;
	
	@FieldComment(name="重量")
	private double  loadweight;
	
	@FieldComment(name="件数")
	private double  loadAmount;
	
	@FieldComment(name="是否已经到达")
	private String  isArrive;
	
	
	static DecimalFormat  df = new DecimalFormat("#.00");


	public String getLoadSn() {
		return loadSn;
	}


	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
	}


	public String getLoadnetwork() {
		return loadnetwork;
	}


	public void setLoadnetwork(String loadnetwork) {
		this.loadnetwork = loadnetwork;
	}


	public String getCreatTime() {
		return creatTime;
	}


	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}


	public String getTruckIdNumber() {
		return truckIdNumber;
	}


	public void setTruckIdNumber(String truckIdNumber) {
		this.truckIdNumber = truckIdNumber;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getTransportType() {
		if("1".equals(transportType)){
			return "提货";
		}else if("2".equals(transportType)){
			return "短驳";
		}else if("3".equals(transportType)){
			return "干线";
		}else if("4".equals(transportType)){
			return "送货";
		}
		return "";
	}


	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}


	public String getFromAddr() {
		return fromAddr;
	}


	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}


	public String getToAddr() {
		return toAddr;
	}


	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}


	public double getLoadFee() {
		return loadFee;
	}


	public void setLoadFee(double loadFee) {
		this.loadFee = loadFee;
	}


	public int getShipCount() {
		return shipCount;
	}


	public void setShipCount(int shipCount) {
		this.shipCount = shipCount;
	}


	public double getLoadVolume() {
		return loadVolume;
	}


	public void setLoadVolume(double loadVolume) {
		this.loadVolume = loadVolume;
	}


	public double getLoadweight() {
		return loadweight;
	}


	public void setLoadweight(double loadweight) {
		this.loadweight = loadweight;
	}


	public double getLoadAmount() {
		return loadAmount;
	}


	public void setLoadAmount(double loadAmount) {
		this.loadAmount = loadAmount;
	}


	public String getIsArrive() {
		if("1".equals(isArrive)){
			return "配载中";
		}else if("2".equals(isArrive)){
			return "已发车";
		}else if("3".equals(isArrive)){
			return "已到达";
		}else if("4".equals(isArrive)){
			return "已完成";
		}
		return "";
	}


	public void setIsArrive(String isArrive) {
		this.isArrive = isArrive;
	} 

	

}
