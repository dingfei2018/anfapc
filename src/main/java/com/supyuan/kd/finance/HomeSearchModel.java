package com.supyuan.kd.finance;

import com.supyuan.jfinal.base.BindModel;

public class HomeSearchModel extends BindModel{
	
	private String startTime;
	
	private String endTime;
	
	private String networkId;

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

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	

}
