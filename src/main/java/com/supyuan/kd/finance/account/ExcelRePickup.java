package com.supyuan.kd.finance.account;

import com.supyuan.util.excel.poi.FieldComment;

public class ExcelRePickup {
	
	@FieldComment(name = "序号")
	private int orderNum;

	@FieldComment(name = "运单号")
	private String shipSn;

	@FieldComment(name = "开单网点")
	private String reNetName;

	@FieldComment(name = "到货网点")
	private String payNetName;

	@FieldComment(name = "提付")
	private Double shipPickuppayFee;
	
	@FieldComment(name = "对账结算状态")
	private String state;

	@FieldComment(name = "运单状态")
	private String shipState;
	
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

	@FieldComment(name = "货物名称")
	private String goodsName;

	@FieldComment(name = "体积")
	private double shipVolume;

	@FieldComment(name = "重量")
	private double shipWeight;

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

	public String getReNetName() {
		return reNetName;
	}

	public void setReNetName(String reNetName) {
		this.reNetName = reNetName;
	}

	public String getPayNetName() {
		return payNetName;
	}

	public void setPayNetName(String payNetName) {
		this.payNetName = payNetName;
	}

	public Double getShipPickuppayFee() {
		return shipPickuppayFee;
	}

	public void setShipPickuppayFee(Double shipPickuppayFee) {
		this.shipPickuppayFee = shipPickuppayFee;
	}

	public String getState() {
		return state;
	}

	public void setState(boolean state) {
		if(state){
			this.state="已结算";
		}else{
			this.state="未结算";
		}
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
}
