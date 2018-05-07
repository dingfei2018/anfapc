package com.supyuan.kd.finance.flow;

import com.supyuan.jfinal.base.BindModel;

/**
 * 收支流水的查询类
 */
public class FlowSearchModel extends BindModel{
	//结算开始时间
	private String startTime;
	//结算结束时间
	private String endTime;
	//网点
	private String networkId;
	//结算类型
	private  String settlementType;
	//收支方向
	private String inoutType;
	//收支方式
	private  String payType;
	//费用类型
	private  String feeType;
	//结算流水号
	private String flowSn;
	//运单号
	private String shipSn;
	//配载单号
	private String loadSn;

	public String getLoadSn() {
		return loadSn;
	}

	public void setLoadSn(String loadSn) {
		this.loadSn = loadSn;
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

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getInoutType() {
		return inoutType;
	}

	public void setInoutType(String inoutType) {
		this.inoutType = inoutType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFlowSn() {
		return flowSn;
	}

	public void setFlowSn(String flowSn) {
		this.flowSn = flowSn;
	}

	public String getShipSn() {
		return shipSn;
	}

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}


}
