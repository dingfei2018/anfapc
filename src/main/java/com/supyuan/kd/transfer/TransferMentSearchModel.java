package com.supyuan.kd.transfer;

/**
 * 已转运单searchmodel
 */

import com.supyuan.jfinal.base.BindModel;

public class TransferMentSearchModel extends BindModel{
	private String transferName;//中转方
	private String shipperName;//托运方
	private String receivingName;//收货方
	private String startCode; //出发地
	private String endCode; //到达地
	private String transferSn; //中转单号
	private String shipSn; //运单号
	private int netWorkId; //中转网点
	private String startTime;//中转开始日期;
	private String endTime;//中转结束日期;
	
	
	
	public int getNetWorkId() {
		return netWorkId;
	}
	public void setNetWorkId(int netWorkId) {
		this.netWorkId = netWorkId;
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
	public String getTransferName() {
		return transferName;
	}
	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}
	public String getTransferSn() {
		return transferSn;
	}
	public void setTransferSn(String transferSn) {
		this.transferSn = transferSn;
	}
	public String getShipSn() {
		return shipSn;
	}
	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}
	

}
