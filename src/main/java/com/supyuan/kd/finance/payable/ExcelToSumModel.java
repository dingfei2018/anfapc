package com.supyuan.kd.finance.payable;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 到车汇总实体
 * @author yuwen
 */
public class ExcelToSumModel {

	@FieldComment(name = "序号")
	private int orderNum;

	@FieldComment(name="配载单号")
	private String loadSn;

	@FieldComment(name="配载网点")
	private String snetworkName;

	@FieldComment(name="到货网点")
	private String enetworkName;

	@FieldComment(name="配载状态")
	private String  deliveryStatus;

	@FieldComment(name="发车日期")
	private String  departTime;

	@FieldComment(name="到车日期")
	private String  arrivalTime;

	@FieldComment(name="车牌号")
	private String  truckIdNumber;

	@FieldComment(name="司机")
	private String  driverName;

	@FieldComment(name="司机电话")
	private String  driverMobile;

	@FieldComment(name="费用合计")
	private double  totalCost;



	@FieldComment(name="到付运输费")
	private String  attransfee;

	@FieldComment(name="未结到付运输费")
	private String  noAttransfee;

	@FieldComment(name="到站卸车费")
	private String  atunloadfee;

	@FieldComment(name="未结到站卸车费")
	private String  noAtunloadfee;

	@FieldComment(name="到站其他费")
	private String  atotherfee;

	@FieldComment(name="未结到站其他费")
	private String  noAtotherfee;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getLoadSn() {
		return loadSn;
	}

	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
	}

	public String getSnetworkName() {
		return snetworkName;
	}

	public void setSnetworkName(String snetworkName) {
		this.snetworkName = snetworkName;
	}

	public String getEnetworkName() {
		return enetworkName;
	}

	public void setEnetworkName(String enetworkName) {
		this.enetworkName = enetworkName;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
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

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getAttransfee() {
		return attransfee;
	}

	public void setAttransfee(String attransfee) {
		this.attransfee = attransfee;
	}

	public String getNoAttransfee() {
		return noAttransfee;
	}

	public void setNoAttransfee(String noAttransfee) {
		this.noAttransfee = noAttransfee;
	}

	public String getAtunloadfee() {
		return atunloadfee;
	}

	public void setAtunloadfee(String atunloadfee) {
		this.atunloadfee = atunloadfee;
	}

	public String getNoAtunloadfee() {
		return noAtunloadfee;
	}

	public void setNoAtunloadfee(String noAtunloadfee) {
		this.noAtunloadfee = noAtunloadfee;
	}

	public String getAtotherfee() {
		return atotherfee;
	}

	public void setAtotherfee(String atotherfee) {
		this.atotherfee = atotherfee;
	}

	public String getNoAtotherfee() {
		return noAtotherfee;
	}

	public void setNoAtotherfee(String noAtotherfee) {
		this.noAtotherfee = noAtotherfee;
	}
}
