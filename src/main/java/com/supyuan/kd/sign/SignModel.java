package com.supyuan.kd.sign;

import com.supyuan.jfinal.base.BindModel;

public class SignModel extends BindModel{
	
	private int shipId;
	
	private int networkId;
	
	private String signTime;
	
	private String signName;
	
	private String idNumber;
	
	private boolean arrivepay;
	
	private boolean hasproxy;
	
	private String remark;

	
	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public int getNetworkId() {
		return networkId;
	}

	public void setNetworkId(int networkId) {
		this.networkId = networkId;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}


	public boolean isArrivepay() {
		return arrivepay;
	}

	public void setArrivepay(boolean arrivepay) {
		this.arrivepay = arrivepay;
	}

	public boolean isHasproxy() {
		return hasproxy;
	}

	public void setHasproxy(boolean hasproxy) {
		this.hasproxy = hasproxy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
