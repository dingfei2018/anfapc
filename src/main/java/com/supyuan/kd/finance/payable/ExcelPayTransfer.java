package com.supyuan.kd.finance.payable;

import java.math.BigDecimal;

import com.supyuan.util.excel.poi.FieldComment;

public class ExcelPayTransfer {
	
	@FieldComment(name = "序号")
	private int orderNum;
	
	@FieldComment(name = "中转单号")
	private String shipTransferSn;
	
	@FieldComment(name = "中转网点")
	private String transferNetName;
	
	@FieldComment(name = "中转方")
	private String transferCorpName;

	@FieldComment(name = "中转联系人")
	private String transferName;
	
	@FieldComment(name = "中转日期")
	private String transferTime;

	@FieldComment(name = "结算状态")
	private String payStatus;
	
	@FieldComment(name = "中转费")
	private BigDecimal transferFee;

	
	@FieldComment(name = "运单号")
	private String shipSn;

	@FieldComment(name = "提付")
	private double shipPickuppayFee;

	@FieldComment(name = "代收货款")
	private BigDecimal shipAgencyFund;
	
	@FieldComment(name = "开单网点")
	private String shipNetName;
	
	@FieldComment(name = "出发地")
	private String fromAdd;
	
	@FieldComment(name = "到达地")
	private String toAdd;
	
	@FieldComment(name = "托运方")
	private String senderName;
	
	@FieldComment(name = "收货方")
	private String receiverName;
	
	@FieldComment(name = "体积")
	private String shipVolume;
	
	@FieldComment(name = "重量")
	private String shipWeight;
	
	@FieldComment(name = "件数")
	private String shipAmount;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getShipTransferSn() {
		return shipTransferSn;
	}

	public void setShipTransferSn(String shipTransferSn) {
		this.shipTransferSn = shipTransferSn;
	}

	public String getTransferNetName() {
		return transferNetName;
	}

	public void setTransferNetName(String transferNetName) {
		this.transferNetName = transferNetName;
	}

	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public BigDecimal getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(BigDecimal transferFee) {
		this.transferFee = transferFee;
	}

	public String getTransferCorpName() {return transferCorpName;}

	public void setTransferCorpName(String transferCorpName) {this.transferCorpName = transferCorpName;}

	public String getPayStatus() {return payStatus;}

	public void setPayStatus(String payStatus) {this.payStatus = payStatus;}

	public String getShipSn() {
		return shipSn;
	}

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}


	public String getShipNetName() {
		return shipNetName;
	}

	public void setShipNetName(String shipNetName) {
		this.shipNetName = shipNetName;
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

	public String getShipVolume() {
		return shipVolume;
	}

	public void setShipVolume(String shipVolume) {
		this.shipVolume = shipVolume;
	}

	public String getShipWeight() {
		return shipWeight;
	}

	public void setShipWeight(String shipWeight) {
		this.shipWeight = shipWeight;
	}

	public String getShipAmount() {
		return shipAmount;
	}

	public void setShipAmount(String shipAmount) {
		this.shipAmount = shipAmount;
	}

	public double getShipPickuppayFee() {
		return shipPickuppayFee;
	}

	public void setShipPickuppayFee(double shipPickuppayFee) {
		this.shipPickuppayFee = shipPickuppayFee;
	}

	public BigDecimal getShipAgencyFund() {
		return shipAgencyFund;
	}

	public void setShipAgencyFund(BigDecimal shipAgencyFund) {
		this.shipAgencyFund = shipAgencyFund;
	}
}
