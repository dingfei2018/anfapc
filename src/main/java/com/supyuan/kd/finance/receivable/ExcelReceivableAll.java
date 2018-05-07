package com.supyuan.kd.finance.receivable;

import com.supyuan.util.excel.poi.FieldComment;

public class ExcelReceivableAll {
	
	@FieldComment(name = "序号")
	private int orderNum;
	@FieldComment(name = "运单号")
	private String shipSn;
	@FieldComment(name = "网点")
	private String shipNetName;
	@FieldComment(name = "货号")
	private String goodsSn;
	@FieldComment(name = "开单日期")
	private String time;
	@FieldComment(name = "出发地")
	private String fromAdd;
	@FieldComment(name = "到达地")
	private String toAdd;
	@FieldComment(name = "托运方")
	private String senderName;
	@FieldComment(name = "收货方")
	private String receiverName;
	@FieldComment(name = "到货网点")
	private String toNetWorkName;
	@FieldComment(name = "运单状态")
	private String shipState;
	@FieldComment(name = "结算状态")
	private String feeState;
	@FieldComment(name = "应收合计")
	private Double totalFee;
	@FieldComment(name = "未结应收合计")
	private Double notTotalFee;
	@FieldComment(name = "现付")
	private Double nowpayFee;
	@FieldComment(name = "未结现付")
	private Double notNowpayFee;
	@FieldComment(name = "提付")
	private Double pickupPay;
	@FieldComment(name = "未结提付")
	private Double notPickupPay;
	@FieldComment(name = "回单付")
	private Double receiptPay;
	@FieldComment(name = "未结回单付")
	private Double notReceiptPay;
	@FieldComment(name = "月付")
	private Double monthPay;
	@FieldComment(name = "未结月付")
	private Double notMonthPay;
	@FieldComment(name = "短欠")
	private Double arrearsPay;
	@FieldComment(name = "未结短欠")
	private double notArrearsPay;
	@FieldComment(name = "贷款扣")
	private double goodsPay;
	@FieldComment(name = "未结贷款扣")
	private double notGoodsPay;
	@FieldComment(name = "客户单号")
	private String shipCustomerNumber;
	@FieldComment(name = "货物名称")
	private String goodsName;
	@FieldComment(name = "体积")
	private double shipVolume;
	@FieldComment(name = "重量")
	private double shipWeight;
	@FieldComment(name = "件数")
	private double shipAmount;

	public ExcelReceivableAll(){
		this.notTotalFee = 0.00;
		this.nowpayFee = 0.00;
		this.notNowpayFee = 0.00;
		this.pickupPay = 0.00;
		this.notPickupPay = 0.00;
		this.receiptPay = 0.00;
		this.notReceiptPay = 0.00;
		this.monthPay = 0.00;
		this.notMonthPay = 0.00;
		this.arrearsPay = 0.00;
		this.notArrearsPay = 0.00;
		this.goodsPay = 0.00;
		this.notGoodsPay = 0.00;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
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

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getToNetWorkName() {
		return toNetWorkName;
	}

	public void setToNetWorkName(String toNetWorkName) {
		this.toNetWorkName = toNetWorkName;
	}

	public String getShipState() {
		return shipState;
	}

	public void setShipState(String shipState) {
		switch (shipState){
			case "1":this.shipState="已入库";
				break;
			case "2":this.shipState="短驳中";
				break;
			case "3":this.shipState="短驳到达";
				break;
			case "4":this.shipState="已发车";
				break;
			case "5":this.shipState="已到达";
				break;
			case "6":this.shipState="收货中转中";
				break;
			case "7":this.shipState="到货中转中";
				break;
			case "8":this.shipState="送货中";
				break;
			default:this.shipState="已签收";
		}
	}

	public String getFeeState() {
		return feeState;
	}

	public void setFeeState(String feeState) {
		switch (feeState){
			case "1":this.feeState="已结算";
				break;
			case "2":this.feeState="未结算";
				break;
			default:this.feeState="部分结算";
		}
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getNotTotalFee() {
		return notTotalFee;
	}

	public void setNotTotalFee(Double notTotalFee) {
		this.notTotalFee = notTotalFee;
	}

	public Double getNowpayFee() {
		return nowpayFee;
	}

	public void setNowpayFee(Double nowpayFee) {
		this.nowpayFee = nowpayFee;
	}

	public Double getNotNowpayFee() {
		return notNowpayFee;
	}

	public void setNotNowpayFee(Double notNowpayFee) {
		this.notNowpayFee = notNowpayFee;
	}

	public Double getPickupPay() {
		return pickupPay;
	}

	public void setPickupPay(Double pickupPay) {
		this.pickupPay = pickupPay;
	}

	public Double getNotPickupPay() {
		return notPickupPay;
	}

	public void setNotPickupPay(Double notPickupPay) {
		this.notPickupPay = notPickupPay;
	}

	public Double getReceiptPay() {
		return receiptPay;
	}

	public void setReceiptPay(Double receiptPay) {
		this.receiptPay = receiptPay;
	}

	public Double getNotReceiptPay() {
		return notReceiptPay;
	}

	public void setNotReceiptPay(Double notReceiptPay) {
		this.notReceiptPay = notReceiptPay;
	}

	public Double getMonthPay() {
		return monthPay;
	}

	public void setMonthPay(Double monthPay) {
		this.monthPay = monthPay;
	}

	public Double getNotMonthPay() {
		return notMonthPay;
	}

	public void setNotMonthPay(Double notMonthPay) {
		this.notMonthPay = notMonthPay;
	}

	public Double getArrearsPay() {
		return arrearsPay;
	}

	public void setArrearsPay(Double arrearsPay) {
		this.arrearsPay = arrearsPay;
	}

	public double getNotArrearsPay() {
		return notArrearsPay;
	}

	public void setNotArrearsPay(double notArrearsPay) {
		this.notArrearsPay = notArrearsPay;
	}

	public double getGoodsPay() {
		return goodsPay;
	}

	public void setGoodsPay(double goodsPay) {
		this.goodsPay = goodsPay;
	}

	public double getNotGoodsPay() {
		return notGoodsPay;
	}

	public void setNotGoodsPay(double notGoodsPay) {
		this.notGoodsPay = notGoodsPay;
	}

	public String getShipCustomerNumber() {
		return shipCustomerNumber;
	}

	public void setShipCustomerNumber(String shipCustomerNumber) {
		this.shipCustomerNumber = shipCustomerNumber;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getShipVolume() {
		return shipVolume;
	}

	public void setShipVolume(double shipVolume) {
		this.shipVolume = shipVolume;
	}

	public double getShipWeight() {
		return shipWeight;
	}

	public void setShipWeight(double shipWeight) {
		this.shipWeight = shipWeight;
	}

	public double getShipAmount() {
		return shipAmount;
	}

	public void setShipAmount(double shipAmount) {
		this.shipAmount = shipAmount;
	}
}


