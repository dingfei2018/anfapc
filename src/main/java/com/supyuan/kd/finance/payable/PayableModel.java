package com.supyuan.kd.finance.payable;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 发车列表实体
 * @author liangxp
 *
 * Date:2018年1月2日上午11:44:43 
 * 
 * @email liangxp@anfawuliu.com
 */
public class PayableModel {
	
	@FieldComment(name="配载单号")
	private String loadSn;
	
	@FieldComment(name="车牌号")
	private String  truckIdNumber;
	
	@FieldComment(name="配载网点")
	private String  loadnetwork;
	
	@FieldComment(name="发车日期")
	private String  creatTime;
	
	@FieldComment(name="是否付款")
	private String  feeStatus;
	
	@FieldComment(name="运费")
	private double  loadFee;
	
	@FieldComment(name="预付运费")
	private double  loadFeePrepay;
	
	@FieldComment(name="出发地")
	private String  fromAddr;
	
	@FieldComment(name="到达地")
	private String  toAddr;
	
	@FieldComment(name="司机")
	private String  driverName;
	
	@FieldComment(name="司机")
	private String  driverMobile;
	
	@FieldComment(name="运单件数")
	private int  loadCount;
	
	@FieldComment(name="体积")
	private double  loadVolume;
	
	@FieldComment(name="重量")
	private double  loadweight;
	
	@FieldComment(name="件数")
	private double  loadAmount;

	public String getLoadSn() {
		return loadSn;
	}

	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
	}

	public String getTruckIdNumber() {
		return truckIdNumber;
	}

	public void setTruckIdNumber(String truckIdNumber) {
		this.truckIdNumber = truckIdNumber;
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

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	public double getLoadFee() {
		return loadFee;
	}

	public void setLoadFee(double loadFee) {
		this.loadFee = loadFee;
	}

	public double getLoadFeePrepay() {
		return loadFeePrepay;
	}

	public void setLoadFeePrepay(double loadFeePrepay) {
		this.loadFeePrepay = loadFeePrepay;
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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public int getLoadCount() {
		return loadCount;
	}

	public void setLoadCount(int loadCount) {
		this.loadCount = loadCount;
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

}
