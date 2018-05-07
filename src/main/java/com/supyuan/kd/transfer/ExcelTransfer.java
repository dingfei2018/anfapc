package com.supyuan.kd.transfer;

import java.math.BigDecimal;

import com.supyuan.util.excel.poi.FieldComment;

public class ExcelTransfer {
	
	@FieldComment(name = "序号")
	private int orderNum;
	
	@FieldComment(name = "中转单号")
	private String transferSn;
	
	@FieldComment(name = "中转方")
	private String transferName;
	
	@FieldComment(name = "中转费")
	private BigDecimal transferFee;
	
	@FieldComment(name = "中转日期")
	private String transferTime;
	
	@FieldComment(name = "中转网点")
	private String tranNetName;
	
	@FieldComment(name = "运单号")
	private String shipSn;
	
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
	
	@FieldComment(name = "数量")
	private String shipAmount;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getTransferSn() {
		return transferSn;
	}

	public void setTransferSn(String transferSn) {
		this.transferSn = transferSn;
	}

	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}

	

	public BigDecimal getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(BigDecimal transferFee) {
		this.transferFee = transferFee;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getTranNetName() {
		return tranNetName;
	}

	public void setTranNetName(String tranNetName) {
		this.tranNetName = tranNetName;
	}

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
	

	

}
