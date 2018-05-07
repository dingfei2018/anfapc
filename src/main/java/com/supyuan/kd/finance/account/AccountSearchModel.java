package com.supyuan.kd.finance.account;

/**
 * 对账searchmodel
 */

import com.supyuan.jfinal.base.BindModel;

public class AccountSearchModel extends BindModel{
	private String reNetWorkId; //开单网点
	private String payNetWorkId; //到货网点
	private String state; //结算状态
	private String shipState; //运单状态
	private String shipperName;//托运方
	private String receivingName;//收货方
	private String shipSn; //运单号
	private String startTime;//开始日期;
	private String endTime;//结束日期;
	
	
	public String getReNetWorkId() {
		return reNetWorkId;
	}
	public void setReNetWorkId(String reNetWorkId) {
		this.reNetWorkId = reNetWorkId;
	}
	public String getPayNetWorkId() {
		return payNetWorkId;
	}
	public void setPayNetWorkId(String payNetWorkId) {
		this.payNetWorkId = payNetWorkId;
	}
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
	public String getShipSn() {
		return shipSn;
	}
	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getShipState() {
		return shipState;
	}

	public void setShipState(String shipState) {
		this.shipState = shipState;
	}
	
	
	
	

}
