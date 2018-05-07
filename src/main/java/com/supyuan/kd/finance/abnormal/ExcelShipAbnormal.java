package com.supyuan.kd.finance.abnormal;

import com.supyuan.util.excel.poi.FieldComment;

import java.math.BigDecimal;

public class ExcelShipAbnormal {
	
	@FieldComment(name = "序号")
	private int shipAbnormalNum;
	
	@FieldComment(name = "运单号")
	private String shipSn;
	
	@FieldComment(name = "开单网点")
	private String networkName;
	
	@FieldComment(name = "异动结算网点")
	private String abnormalNetWorkName;

	@FieldComment(name = "货号")
	private String goodsSn;

	@FieldComment(name = "运单状态")
	private String shipStatus;

	@FieldComment(name = "异动增加")
	private String plusFee;

	@FieldComment(name = "异动减款")
	private String minusFee;

	@FieldComment(name = "异动结算状态")
	private String feeStatus;

	@FieldComment(name = "开单日期")
	private String createTime;
	
	@FieldComment(name = "出发地")
	private String fromAdd;

	@FieldComment(name = "到达地")
	private String toAdd;
	
	@FieldComment(name = "托运方")
	private String senderName;

	@FieldComment(name = "收货方")
	private String receiverName;

	public int getShipAbnormalNum() {
		return shipAbnormalNum;
	}

	public void setShipAbnormalNum(int shipAbnormalNum) {
		this.shipAbnormalNum = shipAbnormalNum;
	}

	public String getShipSn() {
		return shipSn;
	}

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getAbnormalNetWorkName() {
		return abnormalNetWorkName;
	}

	public void setAbnormalNetWorkName(String abnormalNetWorkName) {
		this.abnormalNetWorkName = abnormalNetWorkName;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	public String getPlusFee() {
		return plusFee;
	}

	public void setPlusFee(String plusFee) {
		this.plusFee = plusFee;
	}

	public String getMinusFee() {
		return minusFee;
	}

	public void setMinusFee(String minusFee) {
		this.minusFee = minusFee;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFromAdd() {
		return fromAdd;
	}

	public void setFromAdd(String fromAdd) {
		this.fromAdd = fromAdd;
	}

	public String getToAdd() {
		return toAdd;
	}

	public void setToAdd(String toAdd) {
		this.toAdd = toAdd;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
}
