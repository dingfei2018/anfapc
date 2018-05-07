package com.supyuan.kd.finance.payable;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 发车汇总实体
 * @author yuwen
 */
public class ExcelStartSumModel {

	@FieldComment(name = "序号")
	private int orderNum;

	@FieldComment(name="配载单号")
	private String loadSn;

	@FieldComment(name="配载网点")
	private String snetworkName;

	@FieldComment(name="到货网点")
	private String enetworkName;

	@FieldComment(name="发车日期")
	private String  departTime;

	@FieldComment(name="车牌号")
	private String  truckIdNumber;

	@FieldComment(name="司机")
	private String  driverName;

	@FieldComment(name="司机电话")
	private String  driverMobile;

	@FieldComment(name="运输类型")
	private String  transportType;

	@FieldComment(name="配载状态")
	private String  deliveryStatus;

	@FieldComment(name="出发地")
	private String  fromAddr;

	@FieldComment(name="到达地")
	private String  toAddr;

	@FieldComment(name="配载费用合计")
	private double  totalCost;

	@FieldComment(name="提货费")
	private String  upfee;

	@FieldComment(name="未结提货费")
	private String  noUpfee;

	@FieldComment(name="送货费")
	private String  sendfee;

	@FieldComment(name="未结送货费")
	private String  noSendfee;

	@FieldComment(name="短驳费")
	private String  drayage;

	@FieldComment(name="未结短驳费")
	private String  noDrayage;

	@FieldComment(name="现付运输费")
	private String  nowtransfee;

	@FieldComment(name="未结现付运输费")
	private String  noNowtransfee;

	@FieldComment(name="现付油卡费")
	private String  nowoilfee;

	@FieldComment(name="未结现付油卡费")
	private String  noNowoilfee;

	@FieldComment(name="回付运输费")
	private String  backtransfee;

	@FieldComment(name="未结回付运输费")
	private String  noBacktransfee;

	@FieldComment(name="整车保险费")
	private String  allsafefee;

	@FieldComment(name="未结整车保险费")
	private String  noAllsafefee;

	@FieldComment(name="发站装车费")
	private String  startfee;

	@FieldComment(name="未结发站装车费")
	private String  noStartfee;

	@FieldComment(name="发站其他费")
	private String  otherfee;

	@FieldComment(name="未结发站其他费")
	private String  noOtherfee;

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

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getUpfee() {
		return upfee;
	}

	public void setUpfee(String upfee) {
		this.upfee = upfee;
	}

	public String getNoUpfee() {
		return noUpfee;
	}

	public void setNoUpfee(String noUpfee) {
		this.noUpfee = noUpfee;
	}

	public String getSendfee() {
		return sendfee;
	}

	public void setSendfee(String sendfee) {
		this.sendfee = sendfee;
	}

	public String getNoSendfee() {
		return noSendfee;
	}

	public void setNoSendfee(String noSendfee) {
		this.noSendfee = noSendfee;
	}

	public String getDrayage() {
		return drayage;
	}

	public void setDrayage(String drayage) {
		this.drayage = drayage;
	}

	public String getNoDrayage() {
		return noDrayage;
	}

	public void setNoDrayage(String noDrayage) {
		this.noDrayage = noDrayage;
	}

	public String getNowtransfee() {
		return nowtransfee;
	}

	public void setNowtransfee(String nowtransfee) {
		this.nowtransfee = nowtransfee;
	}

	public String getNoNowtransfee() {
		return noNowtransfee;
	}

	public void setNoNowtransfee(String noNowtransfee) {
		this.noNowtransfee = noNowtransfee;
	}

	public String getNowoilfee() {
		return nowoilfee;
	}

	public void setNowoilfee(String nowoilfee) {
		this.nowoilfee = nowoilfee;
	}

	public String getNoNowoilfee() {
		return noNowoilfee;
	}

	public void setNoNowoilfee(String noNowoilfee) {
		this.noNowoilfee = noNowoilfee;
	}

	public String getBacktransfee() {
		return backtransfee;
	}

	public void setBacktransfee(String backtransfee) {
		this.backtransfee = backtransfee;
	}

	public String getNoBacktransfee() {
		return noBacktransfee;
	}

	public void setNoBacktransfee(String noBacktransfee) {
		this.noBacktransfee = noBacktransfee;
	}

	public String getAllsafefee() {
		return allsafefee;
	}

	public void setAllsafefee(String allsafefee) {
		this.allsafefee = allsafefee;
	}

	public String getNoAllsafefee() {
		return noAllsafefee;
	}

	public void setNoAllsafefee(String noAllsafefee) {
		this.noAllsafefee = noAllsafefee;
	}

	public String getStartfee() {
		return startfee;
	}

	public void setStartfee(String startfee) {
		this.startfee = startfee;
	}

	public String getNoStartfee() {
		return noStartfee;
	}

	public void setNoStartfee(String noStartfee) {
		this.noStartfee = noStartfee;
	}

	public String getOtherfee() {
		return otherfee;
	}

	public void setOtherfee(String otherfee) {
		this.otherfee = otherfee;
	}

	public String getNoOtherfee() {
		return noOtherfee;
	}

	public void setNoOtherfee(String noOtherfee) {
		this.noOtherfee = noOtherfee;
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
