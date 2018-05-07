package com.supyuan.kd.finance.payable;

import com.supyuan.util.excel.poi.FieldComment;

import java.math.BigDecimal;

public class ExcelPayRebate {
	
	@FieldComment(name = "序号")
	private int rebateNum;
	
	@FieldComment(name = "运单号")
	private String shipSn;
	
	@FieldComment(name = "网点")
	private String networkName;
	
	@FieldComment(name = "货号")
	private String goodSn;

	@FieldComment(name = "开单日期")
	private String createime;

	@FieldComment(name = "出发地")
	private String fromAdd;

	@FieldComment(name = "到达地")
	private String toAdd;

	@FieldComment(name = "托运方")
	private String senderName;

	@FieldComment(name = "收货方")
	private String receiverName;

	@FieldComment(name = "运单状态")
	private String shipStatus;
	
	@FieldComment(name = "回扣")
	private BigDecimal shipRebateFee;

	@FieldComment(name = "结算状态")
	private String shipFeeStatus;
	
	@FieldComment(name = "应付合计")
	private BigDecimal shipTotalFee;

	@FieldComment(name = "货物名称")
	private String productName;
	
	@FieldComment(name = "体积")
	private String shipVolume;
	
	@FieldComment(name = "重量")
	private String shipWeight;
	
	@FieldComment(name = "件数")
	private String shipAmount;

	@FieldComment(name = "开单人")
	private String userName;


	public int getRebateNum() {
		return rebateNum;
	}

	public void setRebateNum(int rebateNum) {
		this.rebateNum = rebateNum;
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

	public String getGoodSn() {
		return goodSn;
	}

	public void setGoodSn(String goodSn) {
		this.goodSn = goodSn;
	}

	public String getCreateime() {
		return createime;
	}

	public void setCreateime(String createime) {
		this.createime = createime;
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

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	public BigDecimal getShipRebateFee() {
		return shipRebateFee;
	}

	public void setShipRebateFee(BigDecimal shipRebateFee) {
		this.shipRebateFee = shipRebateFee;
	}

	public String getShipFeeStatus() {
		return shipFeeStatus;
	}

	public void setShipFeeStatus(String shipFeeStatus) {
		this.shipFeeStatus = shipFeeStatus;
	}

	public BigDecimal getShipTotalFee() {
		return shipTotalFee;
	}

	public void setShipTotalFee(BigDecimal shipTotalFee) {
		this.shipTotalFee = shipTotalFee;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
