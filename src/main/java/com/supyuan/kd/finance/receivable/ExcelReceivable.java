package com.supyuan.kd.finance.receivable;

import com.supyuan.util.excel.poi.FieldComment;

import java.math.BigDecimal;

public class ExcelReceivable {
	
	@FieldComment(name = "序号")
	private int orderNum;
	@FieldComment(name = "运单号")
	private String shipSn;
	@FieldComment(name = "付款状态")
	private String state;
	@FieldComment(name = "开单网点")
	private String shipNetName;
	@FieldComment(name = "付款方式")
	private String shipPayWay;
	@FieldComment(name = "合计")
	private double shipTotalFee;

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

	public double getShipTotalFee() {
		return shipTotalFee;
	}

	public void setShipTotalFee(double shipTotalFee) {
		this.shipTotalFee = shipTotalFee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getShipNetName() {
		return shipNetName;
	}

	public void setShipNetName(String shipNetName) {
		this.shipNetName = shipNetName;
	}

	public String getShipPayWay() {
		return shipPayWay;
	}

	public void setShipPayWay(String shipPayWay) {
		this.shipPayWay = shipPayWay;
	}


	}


