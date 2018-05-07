package com.supyuan.kd.transfer;

/**
 * 未签收未转运单searchmodel
 */

import com.supyuan.jfinal.base.BindModel;

public class TransferSearchModel extends BindModel{
	
	private String shipperName;//托运方
	private String receivingName;//收货方
	private String startCode; //出发地
	private String endCode; //到达地
	private String shipSn; //运单号
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getReceivingName() {
		return receivingName;
	}
	public void setReceivingName(String receivingName) {
		this.receivingName = receivingName;
	}
	public String getStartCode() {
		return startCode;
	}
	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}
	public String getEndCode() {
		return endCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public String getShipSn() {
		return shipSn;
	}
	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}
	

}
